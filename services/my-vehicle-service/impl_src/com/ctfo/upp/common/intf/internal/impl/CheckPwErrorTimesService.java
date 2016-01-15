package com.ctfo.upp.common.intf.internal.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.intf.external.CheckPwErrorTimes;
import com.ctfo.account.intf.external.ICheckPwErrorTimes;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.payment.intf.external.IPaymentTradeManager;
import com.ctfo.upp.dict.AccountDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.root.utils.UUIDUtil;
import com.google.code.morphia.query.Query;
import com.sinoiov.upp.manager.account.IAccountManager;
import com.sinoiov.upp.util.DefaultConfig;

/***
 * 类描述：用户支付密码输入错误次数Service
 * 
 * @author：lipeng01
 * @date：2015年3月10日 15:12:00
 * @version 1.0
 * @since JDK1.6
 */
//@Service("checkPwErrorTimesService")
public class CheckPwErrorTimesService implements ICheckPwErrorTimes {
	private static final Log logger = LogFactory.getLog(CheckPwErrorTimesService.class);
	@Autowired
	@Qualifier("mongoService")
	private IMongoService mongoService;
	@Autowired
	@Qualifier("accountManager")
	private IAccountManager accountManager;
	// 解锁账户service
	@Resource
	private IPaymentTradeManager paymentTradeManager ;

	@Override
	public String saveErrorTimes(CheckPwErrorTimes errorTimesBean) throws UPPException {
		if (errorTimesBean == null) {
			throw new UPPException("记录用户支付密码输入错误次数实体为空！");
		}
		try {

			String uuid = UUIDUtil.newUUID();
			errorTimesBean.setId(uuid);
			errorTimesBean.setInputTime(new Date().getTime());
			mongoService.save(errorTimesBean);
			return uuid;

		} catch (Exception e) {
			logger.error(e);
			throw new UPPException("保存支付密码输入错误次数异常，原因：" + e.getLocalizedMessage(), e);
		}
	}

	@Override
	public String removeErrorTimesByUserId(String userId) throws UPPException {
		String result = "-1";
		CheckPwErrorTimes errorTimesBean = null;
		if (StringUtils.isEmpty(userId)) {
			throw new UPPException("用户userId为空");
		}
		try {
			errorTimesBean = getErrorTimesByUserId(userId);
			if (errorTimesBean != null) {
				//删除记录
				mongoService.delete(CheckPwErrorTimes.class, errorTimesBean.getId());
			}
		} catch (Exception e) {
			logger.error("删除当前用户支付密码输入错误异常",e);
		}
		result = "1";

		return result;
	}

	@Override
	public CheckPwErrorTimes getErrorTimesByUserId(String userId) throws UPPException {
		if (StringUtils.isEmpty(userId)) {
			throw new UPPException("当前登录用户ID为空");
		}

		try {
			Query<CheckPwErrorTimes> query = mongoService.getQuery(CheckPwErrorTimes.class);
			query.field("userId").equal(userId);
			List<CheckPwErrorTimes> list = mongoService.query(CheckPwErrorTimes.class, query);
			if (list != null && list.size() > 0) {
				CheckPwErrorTimes result = list.get(0);
				return result;
			}
		} catch (Exception e) {
			logger.error("根据userId查询支付密码输入错误次数异常");
			throw new UPPException("根据userId查询支付密码输入错误次数异常", e);
		}
		return null;
	}

	@Override
	public void removeAllTimeOut() throws UPPException {
		try {
			// 查找所有记录
			Query<CheckPwErrorTimes> query = mongoService.getQuery(CheckPwErrorTimes.class);
			List<CheckPwErrorTimes> list = mongoService.query(CheckPwErrorTimes.class, query);
			Long currentTime = System.currentTimeMillis();
			String temp = DefaultConfig.getValue("CHECK_PW_ERROR_TIMES");
		//	String temp = "86400000";
			int timeOut = Integer.parseInt(temp);
			List<CheckPwErrorTimes> timeOutlist = new ArrayList<CheckPwErrorTimes>();
			// 超时记录
			for(CheckPwErrorTimes checkPwError : list){
				//测试代码用
				if((currentTime-checkPwError.getInputTime())>timeOut){
					//删除此记录
				    if(checkPwError.getTimes()==3){
				    	timeOutlist.add(checkPwError);
				    }
				    removeErrorTimesByUserId(checkPwError.getUserId());
					
				}
			}
			if(timeOutlist.size()>0){
				//解锁操作
				checkAccount(timeOutlist);
			}
			
			for (CheckPwErrorTimes tempBean : timeOutlist) {
				mongoService.delete(CheckPwErrorTimes.class, tempBean.getId());
			}
		} catch (Exception e) {
			logger.error("删除所有超时账户支付密码输入错误记录异常");
			throw new UPPException("删除所有超时账户支付密码输入错误记录异常", e);
		}
	}

	/**
	 * 根据userID查询当前用户状态，如果为锁定状态则解锁。
	 */
	private void checkAccount(List<CheckPwErrorTimes> tempBeanList) throws UPPException {
		for (CheckPwErrorTimes tempBean : tempBeanList) {
			Account account = accountManager.getAccountByUserId(tempBean.getUserId());
			if (AccountDict.ACCOUNT_STATUS_LOCKED.equals(account.getAccountStatus())) {
				paymentTradeManager.unlockAccount(account.getInsideAccountNo());
			}
		}
	}

	@Override
	public void updateThis(CheckPwErrorTimes checkPwErrorTimes) throws UPPException {
		if (checkPwErrorTimes == null) {
			throw new UPPException("记录用户支付密码输入错误次数实体为空！");
		}
		try {
			mongoService.update(checkPwErrorTimes);
		} catch (Exception e) {
			logger.error("更新用户支付密码输入错误次数实体异常",e);
			throw new UPPException("更新用户支付密码输入错误次数实体异常",e);
		}
		
	}

}
