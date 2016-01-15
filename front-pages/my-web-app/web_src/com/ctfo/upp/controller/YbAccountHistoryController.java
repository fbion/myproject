package com.ctfo.upp.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.payment.dto.beans.AccountInfoDto;
import com.ctfo.payment.dto.beans.YBTradeRecordContrastDto;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.TimeHandleUtil;
import com.ctfo.upp.service.transferBatchSynch.ITransferBatchSyncSercive;

@Component
@Controller
@Scope("prototype")
@RequestMapping("/accountHistory")
public class YbAccountHistoryController {
	private static Log logger = LogFactory.getLog(YbAccountHistoryController.class);
	
	@Autowired
	private ITransferBatchSyncSercive transferBatchSyncSercive;
	
	
	
	@RequestMapping(value="/queryAccountHistory.action" ,produces = "application/json")
	@ResponseBody
	public PaginationResult<?> queryAccountHistory(HttpServletRequest request ,HttpServletResponse response){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String insideAccountNo = request.getParameter("insideAccountNo");
		String startTime = request.getParameter("startTime");
		PaginationResult<YBTradeRecordContrastDto> result= new PaginationResult<YBTradeRecordContrastDto> ();
		
		try {
			if(insideAccountNo==null||"".equals(insideAccountNo)){
//				List<YbPayTradeHistoryRecord> res = new ArrayList<YbPayTradeHistoryRecord>();
//				YbPayTradeHistoryRecord record=new YbPayTradeHistoryRecord();
//				record.setTradeDesc("无");
//				record.setAccountType("无");
//				record.setTradeTime(System.currentTimeMillis());
//				res.add(record);
//				result.setData(res);
				return null;
			}
			List<YBTradeRecordContrastDto> res = transferBatchSyncSercive.queryAccountHistory(insideAccountNo,TimeHandleUtil.getTimesmorning(startTime),TimeHandleUtil.getTimesnight(startTime));
			result.setData(res);
//			String data=JSONArray.fromObject(res).toString();
//			response.getWriter().print(data);
		} catch (Exception e) {
			logger.error("交易同步异常！", e);
		}
		return result;
	}
	@RequestMapping(value="/sendLastTradeSign.action")
	public String sendLastTradeSign(HttpServletRequest request){
		
		String startTime = request.getParameter("startTime");
		if(startTime==null){
			return "";
		}
		try {
			transferBatchSyncSercive.sendLastTradeSign(startTime);
		} catch (UPPException e) {
			logger.error("发送最后一次交易标识异常！", e);
		}
		return "pages/transferBatchSynchronizing/transferBatchSynchronizing";
	}
	@RequestMapping(value="/queryThirdPayAccountByDay.action")
	@ResponseBody
	public PaginationResult<?> queryThirdPayAccountByDay(HttpServletRequest request ,HttpServletResponse response){
		PaginationResult<AccountInfoDto> result= new PaginationResult<AccountInfoDto> ();
		
		return result;
	}
}
