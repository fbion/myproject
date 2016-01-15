package com.sinoiov.upp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ctfo.base.intf.internal.ISmsCodeManager;
import com.ctfo.base.intf.internal.MobileSmsBean;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.root.utils.SmsSender;
import com.ctfo.upp.soa.ServiceFactory;

@Service("smsCodeService")
public class SmsCodeServiceImpl extends AbstractService implements ISmsCodeService {

	private ISmsCodeManager smsCodeManager;

	private SmsCodeServiceImpl() {
		smsCodeManager = (ISmsCodeManager) ServiceFactory.getFactory().getService(ISmsCodeManager.class);
	}

	@Override
	public String removeSmsCodeByMoblieNo(String moblieNo){
		String recordId = null;

		try {
			recordId = smsCodeManager.removeSmsCodeByMoblieNo(moblieNo);
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("根据手机号删除短信验证码异常：" + e.getLocalizedMessage(), e);
		}

		return recordId;
	}

	// TODO 现在的业务是获取后马上把短信删除,这里可以把两个方法合并
	@Override
	public String getSmsCodeByMoblieNo(String moblieNo){
		String smsCode = null;

		try {
			smsCode = smsCodeManager.getSmsCodeByMoblieNo(moblieNo);
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);		
		} catch (Exception e) {
			logger.error("根据手机号查询短信验证码异常：" + e.getLocalizedMessage(), e);
		}

		return smsCode;
	}

	@Override
	public String getSmsText(String code){
		// TODO 未实现
		return null;
	}

	@Override
	public String getSmsCode(String mobileNo){
		try {
			// 随机生成短信
			String smsCode = SmsSender.getSmsCode();
			List<String> param = new ArrayList<String>();
			param.add(smsCode);
			// 保存短信内容到mongo
			MobileSmsBean smsBean = new MobileSmsBean();
			smsBean.setMobileNo(mobileNo);
			smsBean.setSmsCode(smsCode);
			String mongoid = smsCodeManager.saveSmsCode(smsBean);
			// 发送短信
			String sendBackCode = SmsSender.getInstance().sendSmsByTemplate(mobileNo, "tyzfpt1001", param);
			logger.info("发送短信验证码:" + mobileNo + "[" + smsCode + "], mongoid:[" + mongoid + "], sendBackCode:["
					+ sendBackCode + "]");
			return smsCode;
		} catch (UPPException e) {
			logger.error(e.getLocalizedMessage(), e);
		} catch (Exception e) {
			logger.error("获取短信验证码异常：" + e.getLocalizedMessage(), e);
		}
		return null;
	}

}
