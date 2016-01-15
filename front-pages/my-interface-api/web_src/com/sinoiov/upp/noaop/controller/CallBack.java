package com.sinoiov.upp.noaop.controller;

import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.security.ConvertUtils;
import com.ctfo.util.DateUtil;
import com.sinoiov.pay.utils.UPPConfigUtil;

@Controller
@Scope("prototype")
@RequestMapping(value = "/callback")
public class CallBack {
	private static Log logger = LogFactory.getLog(CallBack.class);

	/**
	 * 回跳手机端接口 
	 * <p>描述：处理支付成功后，回跳手机端所需页面（html5）
	 */
	@RequestMapping(value = "/mobileCallback", method = RequestMethod.GET)
	public ModelAndView mboileCallback(@RequestParam("data") String data, @RequestParam("encryptkey") String encryptkey) {
		ModelAndView mv = new ModelAndView();
		try {
			logger.info("---收到前台回调参数:" + data);
			logger.info("---收到前台回调参数:" + encryptkey);

			String yb_code = UPPConfigUtil.getValue("YB_FAST_MER_ID");
			String yb_public = UPPConfigUtil.getPublicKey(yb_code);
			
			String gateay_code = UPPConfigUtil.getValue("UPP_GATEWAY_MER_ID");
			String gateay_privatekey=UPPConfigUtil.getPrivateKey(gateay_code);
			
			// 解密
			String json = ConvertUtils.decodeParamJson(data, encryptkey, gateay_privatekey, yb_public);

			// realData:{"amount":1,"bank":"招商银行","bankcode":"CMBCHINA",
			// "cardtype":2,"lastno":"3703","merchantaccount":"YB01000000144",
			// "orderid":"70020150209162432106","status":1,"yborderid":411502097300509070}

			JSONObject jsonMap = JSONObject.fromObject(json);

			String status = jsonMap.get("status") == null ? "" : jsonMap.getString("status");
			String amount = jsonMap.get("amount") == null ? "" : jsonMap.getString("amount");
			String bank = jsonMap.get("bank") == null ? "" : jsonMap.getString("bank");
			String bankcode = jsonMap.get("bankcode") == null ? "" : jsonMap.getString("bankcode");
			if (StringUtils.isNotBlank(status)) {
				mv.addObject("status", status);
				mv.addObject("amount", AmountUtil.getAmount(Long.parseLong(amount)));
				mv.addObject("bank", bank);
				mv.addObject("bankcode", bankcode);
				mv.addObject("payTime", DateUtil.getDateStr(DateUtil.DEFAULT_FORMATSHORT, new Date()));
				mv.setViewName("mobile_success");
			} else {
				mv.addObject("status", status);
				mv.addObject("amount", AmountUtil.getAmount(Long.parseLong(amount)));
				mv.addObject("bank", bank);
				mv.addObject("bankcode", bankcode);
				mv.addObject("payTime", DateUtil.getDateStr(DateUtil.DEFAULT_FORMATSHORT, new Date()));
				mv.setViewName("mobile_failuer");
			}

			return mv;
		} catch (Exception e) {
			logger.error("前台跳转业务系统异常!", e);
		}
		return mv;
	}

}
