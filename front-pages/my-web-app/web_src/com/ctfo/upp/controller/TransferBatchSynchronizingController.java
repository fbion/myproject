package com.ctfo.upp.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountHistoryResponseYB;
import com.ctfo.gateway.bean.yibao.business.accountaccess.QueryAccountInfoResponseYB;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dto.beans.YbPayTradeHistoryRecord;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.transferBatchSynch.ITransferBatchSyncSercive;

@Controller
public class TransferBatchSynchronizingController{
	
	private static Log logger = LogFactory.getLog(TransferBatchSynchronizingController.class);
	
	@Autowired
	private ITransferBatchSyncSercive transferBatchSyncSercive;
	
	@RequestMapping(value="/transferBatchSynchronizingByPTradeNos.action")
	public PaginationResult<?>  transferBatchSynchronizingByPTradeNos(HttpServletRequest request){

		String param = request.getParameter("paramList");
		PaginationResult<PaymentTrade> result= new PaginationResult<PaymentTrade>();
		try {
			List<PaymentTrade>   list= transferBatchSyncSercive.transferBatchSynchronizingByPTradeNos(param);
			result.setData(list);
		} catch (UPPException e) {
			logger.error("以交易流水同步异常！", e);
		}
		return result;
	}
	
	@RequestMapping(value="/transferBatchSynchronizingByTime.action")
	public String transferBatchSynchronizingByTime(HttpServletRequest request){

		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		try {
			transferBatchSyncSercive.transferBatchSynchronizingByTime(startTime,endTime);
		} catch (UPPException e) {
			logger.error("以交易时间同步异常！", e);
		}
		return "pages/transferBatchSynchronizing/transferBatchSynchronizing";
	}
	
	@RequestMapping(value="/sendLastTradeSign.action")
	public String sendLastTradeSign(HttpServletRequest request){

		String sendLastTradeSign = request.getParameter("sendLastTradeSign");
		
		try {
			transferBatchSyncSercive.sendLastTradeSign(sendLastTradeSign);
		} catch (UPPException e) {
			logger.error("发送最后一次交易标识异常！", e);
		}
		return "pages/transferBatchSynchronizing/transferBatchSynchronizing";
	}
	
	@RequestMapping(value="/accountReconciliationByTime.action")
	public String accountReconciliationByTime(HttpServletRequest request){

		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String numberSign = request.getParameter("numberSign");
	
		try {
			transferBatchSyncSercive.accountReconciliationByTime(startTime,endTime,numberSign);
		} catch (UPPException e) {
			logger.error("交易同步异常！", e);
		}
		return "pages/transferBatchSynchronizing/transferBatchSynchronizing";
	}
	@RequestMapping(value="/unlockThirdPartPayAccount.action")
	public String unlockThirdPartPayAccount(HttpServletRequest request){
		String insideAccountNo = request.getParameter("accountNo");
		try {
			transferBatchSyncSercive.unlockThirdPartPayAccount(insideAccountNo);
		} catch (UPPException e) {
			logger.error("交易同步异常！", e);
		}
		return "pages/transferBatchSynchronizing/transferBatchSynchronizing";
	}
	@RequestMapping(value="/queryThirdPartPayAccount.action")
	public ModelAndView queryThirdPartPayAccount(HttpServletRequest request, ModelAndView mv){
		String insideAccountNo = request.getParameter("accountNo");
		try {
			QueryAccountInfoResponseYB response = transferBatchSyncSercive.queryThirdPartPayAccount(insideAccountNo);
			mv.addObject("isResult", true);
			mv.addObject("accountNo", response.getAccountNo());
			mv.addObject("balance",response.getTotalBalance());
			mv.addObject("unTakeCashBalance",response.getNoncashableBalance());
			mv.addObject("status",response.getAccountStatus());
			mv.addObject("freezeReason",response.getAccountFreezeReason());

			JSONObject.fromObject(mv);
			mv.setViewName("pages/transferBatchSynchronizing/transferBatchSynchronizing");
		} catch (UPPException e) {
			logger.error("交易同步异常！", e);
		}
		return mv;
	}
}
