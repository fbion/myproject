package com.sinoiov.upp.portal.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.upp.exception.UPPException;
import com.sinoiov.upp.portal.util.ProtalConvertUtils;

@Service
public class MobileTelephoneNoteProvingImpl extends AbstractService implements IMobileTelephoneNoteProving{

	private static Log logger = LogFactory.getLog(MobileTelephoneNoteProvingImpl.class);
	
	public String getMobileTelephoneNoteProving(String mobileNo,String accountNo) throws UPPException {
	     String result = "";
	     String uri;
//		 int max=9;
//	     int min=0;
//	     Random random = new Random();
//	     for(int i=0;i<6;i++){
//	    	 int s = random.nextInt(max)%(max-min+1) + min;
//	    	 result += s;
//	     }
//	     SmsSender.getInstance().sendSms(mobileNo, result);
	    
		try {
			if("openAccount".equals(accountNo)){
		    	accountNo = ProtalConvertUtils.getValue("MERCHANT_CODE");
		    }
		    Map map = new HashMap();
		    map.put("mobileNo", mobileNo);
		    map.put("accountNo", accountNo);
			// 获取调用地址
			uri = ProtalConvertUtils.getValue("UPP_ACCOUNT_GETSMSCODE");
			// 将参数转换为JSON格式,执行加密调用方法
			String json = super.sendRequest(map, uri);
			
			JSONObject jsonObject = JSONObject.fromObject(json);
			result = (String)jsonObject.get("result");
		
		} catch (Exception e) {
			logger.error("获取短信验证码异常！", e);
			throw new UPPException("获取短信验证码异常！", e);
		}
	     
	   return result;
		
	}

	@Override
	public String getMobileTelephoneNoteProvingAgain(String mobileNo,String accountNo) throws UPPException {
		String result = "";
	     String uri;
		try {
			if("openAccount".equals(accountNo)){
		    	accountNo = ProtalConvertUtils.getValue("MERCHANT_CODE");
		    }
		    Map map = new HashMap();
		    map.put("mobileNo", mobileNo);
		    map.put("accountNo", accountNo);
			// 获取调用地址
			uri = ProtalConvertUtils.getValue("UPP_ACCOUNT_GETSMSCODE_AGAIN");
			// 将参数转换为JSON格式,执行加密调用方法
			String json = super.sendRequest(map, uri);
			
			JSONObject jsonObject = JSONObject.fromObject(json);
			result = (String)jsonObject.get("result");
		
		} catch (Exception e) {
			logger.error("获取短信验证码异常！", e);
			throw new UPPException("获取短信验证码异常！", e);
		}
	     
	   return result;
	}

}
