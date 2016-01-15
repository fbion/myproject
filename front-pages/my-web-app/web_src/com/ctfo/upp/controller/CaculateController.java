package com.ctfo.upp.controller;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.security.encrypt.RSA;

/***
* 类描述：商户管理-商户接入密钥生产Controller
* @author：liuguozhong  
* @date：2015年1月19日上午11:49:04 
* @version 1.0
* @since JDK1.6
*/ 
@Scope("prototype")
@Controller
@RequestMapping(value = "/caculate")
public class CaculateController extends BaseController{
	
	private static Log logger = LogFactory.getLog(CaculateController.class);
	
	/**
	 * 生成密钥对
	 * @param orderDto
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/caculateRSA",produces = "application/json")
	public ResponseEntity<String> caculateRSA(){
		
		ResponseEntity<String> responseEntity = null;
		
		String json="";
		
		try {
			
			// 生成RSA密钥对
			Map<String, String> m = RSA.generateKeyPair();
			String publickey = m.get("publicKey");
			String privatekey = m.get("privateKey");
			
			json = "{\"publicKey\":\""+publickey+"\", \"privateKey\":\""+privatekey+"\"}";
					
			logger.debug("json:"+ json);
					
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			json = "{\"data\":{\"result\":\"-1\", \"errorMes\":\""+ue.getLocalizedMessage()+"\"}}";
		} catch (Exception e) {
			logger.error("生成密钥对！",e);
			json = "{\"data\":{\"result\":\"-1\", \"errorMes\":\"生成密钥对\"}}";
		}finally{			
			HttpHeaders httpHeaders = new HttpHeaders();			
			responseEntity = new ResponseEntity<String>(json, httpHeaders, HttpStatus.CREATED);			
		}
		return responseEntity;
	}
	
	
	
}
