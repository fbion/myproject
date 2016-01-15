package com.sinoiov.upp.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.upp.exception.UPPException;
import com.sinoiov.upp.portal.service.IMobileTelephoneNoteProving;

@Controller
@RequestMapping(value="modileTelephoneNoteProving")
public class ModileTelephoneNoteProving {
	
	private static Log logger = LogFactory.getLog(ModileTelephoneNoteProving.class);
	
	@Autowired
	IMobileTelephoneNoteProving mobileService;
	
	@RequestMapping(value="getMobileNoProving.action")
	@ResponseBody
	public Map<String,String> getMobileNoProving(HttpServletRequest request){
		//调用获取短信验证码service
		String ProvingCode = "";
		Map<String,String> map = new HashMap<String,String>();
		String imgProvingText = request.getParameter("imgProvingText");
		String accountNo = request.getParameter("accountNo");
		String mobileNo = request.getParameter("mobileNo");
		HttpSession session = request.getSession();
		String imgCheckCode = (String)session.getAttribute("imgCheckCode");
		if(imgProvingText==null||!imgProvingText.toUpperCase().equals(imgCheckCode)){
			ProvingCode = "-2";
			map.put("result", ProvingCode);
			return map;
		}
		try {
			ProvingCode = mobileService.getMobileTelephoneNoteProving(mobileNo,accountNo);
		} catch (UPPException e) {
			logger.error("获取短信验证码错误！", e);
		}
		map.put("result", ProvingCode);
		return map;
	}
	
	@RequestMapping(value="getMobileNoProvingAgain.action")
	@ResponseBody
	public Map<String,String> getMobileNoProvingAgain(HttpServletRequest request){
		//调用获取短信验证码service
		String ProvingCode = "";
		Map<String,String> map = new HashMap<String,String>();
		String imgProvingText = request.getParameter("imgProvingText");
		String accountNo = request.getParameter("accountNo");
		String mobileNo = request.getParameter("mobileNo");
		HttpSession session = request.getSession();
		String imgCheckCode = (String)session.getAttribute("imgCheckCode");
		if(imgProvingText==null||!imgProvingText.toUpperCase().equals(imgCheckCode)){
			ProvingCode = "-2";
			map.put("result", ProvingCode);
			return map;
		}
		try {
			ProvingCode = mobileService.getMobileTelephoneNoteProvingAgain(mobileNo,accountNo);
		} catch (UPPException e) {
			logger.error("获取短信验证码错误！", e);
		}
		map.put("result", ProvingCode);
		return map;
	}
}
