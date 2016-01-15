package com.ctfo.upp.gatewayservice.facade;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.base.ResponseBean;
import com.ctfo.gateway.bean.yibao.business.accountaccess.AccountBalanceInquiry;
import com.ctfo.gateway.bean.yibao.business.accountaccess.BankCardRechargeBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeInfoBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.RechargeSyncBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TradeSyncBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.TransactionBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.WithDrawBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.WithDrawProcessBeanRequestYB;
import com.ctfo.gateway.bean.yibao.business.entrustsettle.WithDrawQuickBeanRequestYB;
import com.ctfo.gateway.intf.TradeServiceFacade;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.gatewayservice.service.yibao.intf.TradeServiceYB;

@Service("tradeServiceFacade")
public class TradeServiceFacadeImpl extends FacadeSupport implements TradeServiceFacade {
	
	@Autowired(required=false)
	private TradeServiceYB tradeServiceYB;
	
	@Override
	public ResponseBean transaction(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof TransactionBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return tradeServiceYB.transacte((TransactionBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 转账发生错误  <<<<<<<<<<", e);
		}
	}

	@Override
	public ResponseBean bankCardRecharge(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof BankCardRechargeBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return tradeServiceYB.bankCardRecharge((BankCardRechargeBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 银行卡充值发生错误  <<<<<<<<<<", e);
		}
	}

	@Override
	public ResponseBean withdraw(RequestBean requestBean) throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof WithDrawBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return tradeServiceYB.withDraw((WithDrawBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 提现发生错误  <<<<<<<<<<", e);
		}
	}
	
	@Override
	public ResponseBean withdrawQuick(RequestBean requestBean)
			throws UPPException {
		try {
			if(requestBean instanceof WithDrawQuickBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return tradeServiceYB.withdrawQuick((WithDrawQuickBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 提现发生错误  <<<<<<<<<<", e);
		}
	}
	
	@Override
	public ResponseBean rechargeSync(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof RechargeSyncBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return tradeServiceYB.rechargeSync((RechargeSyncBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 充值同步发生错误  <<<<<<<<<<", e);
		}
	}
	
	@Override
	public ResponseBean withDrawProcess(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof WithDrawProcessBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				WithDrawProcessBeanRequestYB bean = (WithDrawProcessBeanRequestYB)requestBean;
				if(bean.getType().equals("2") && StringUtils.isBlank(bean.getBatchNo())){
					throw new Exception(">>>>>>>>>> 快速提现必须填写batchNo <<<<<<<<<<");
				}
				return tradeServiceYB.withDrawProcess(bean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误 <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 提现流程查询发生错误 <<<<<<<<<<", e);
		}
	}

	@Override
	public ResponseBean rechargeInfo(RequestBean requestBean)
			throws UPPException {
		// TODO Auto-generated method stub
		try {
			if(requestBean instanceof RechargeInfoBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				return tradeServiceYB.rechargeInfo((RechargeInfoBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new UPPException(">>>>>>>>>> 充值信息查询发生错误  <<<<<<<<<<", e);
		}
	}
	@Override
	public ResponseBean tradeSync(RequestBean requestBean) throws UPPException {
		try {
			if(requestBean instanceof TradeSyncBeanRequestYB){
				super.validateObjectNecessary(requestBean);
				TradeSyncBeanRequestYB t = (TradeSyncBeanRequestYB)requestBean;
				for(Object object : t.getList()){
					if(!(object instanceof WithDrawBeanRequestYB) && !(object instanceof TransactionBeanRequestYB) && !(object instanceof RechargeSyncBeanRequestYB)){
						throw new Exception(">>>>>>>>>> list里头存在错误对象  <<<<<<<<<<");
					}
				}
				return tradeServiceYB.tradeSync((TradeSyncBeanRequestYB)requestBean);
			}else{
				throw new Exception(">>>>>>>>>> 请求参数对象错误  <<<<<<<<<<");
			}
		} catch (Exception e) {
			throw new UPPException(">>>>>>>>>> 交易批量同步发生错误  <<<<<<<<<<"+e.getMessage(), e);
		}
	}
	@Override
	public ResponseBean accountBalanceInquiry(RequestBean requestBean) throws UPPException{
		try{
			if(requestBean instanceof AccountBalanceInquiry){
				return tradeServiceYB.accountBalanceInquiryHandle(requestBean);
			}
			return null;
		} catch (Exception e) {
			throw new UPPException(">>>>>>>>>> 查询账户信息  <<<<<<<<<<", e);
		}
	}
	public TradeServiceYB getTradeServiceYB() {
		return tradeServiceYB;
	}

	public void setTradeServiceYB(TradeServiceYB tradeServiceYB) {
		this.tradeServiceYB = tradeServiceYB;
	}
}
