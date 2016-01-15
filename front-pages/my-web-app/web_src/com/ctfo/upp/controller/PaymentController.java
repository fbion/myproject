package com.ctfo.upp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.payment.PaymentService;
import com.ctfo.upp.service.recharge.bean.TradeApplyDto;
import com.ctfo.upp.util.Converter;

@Scope("prototype")
@Controller
@RequestMapping(value = "/payment")
public class PaymentController extends BaseController {
	private static Log logger = LogFactory.getLog(PaymentController.class);
	private PaginationResult<?> result = new PaginationResult<Object>();
	public PaymentController() {
		model = new TradeApplyDto();
	}

	@Resource
	private PaymentService paymentService;

	/**
	 * 添加扣款信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addPayment", produces = "application/json")
	@ResponseBody
	public PaginationResult<?> addPayment() {
		try {
			String user = index.getUserLogin();
			result = paymentService.addPayment((TradeApplyDto) model, user);
		} catch (UPPException e) {
			logger.error("添加扣款信息异常", e);
		}
		return result;
	}

	/**
	 * 修改扣款信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/modifyPayment", produces = "application/json")
	@ResponseBody
	public PaginationResult<?> modifyPayment() {
		try {
			String user = index.getUserLogin();
			result = paymentService.modifyPayment((TradeApplyDto) model, user);
		} catch (UPPException e) {
			result.setMessage(Converter.OP_FAILED);
			logger.error("修改扣款信息失败", e);
		}
		return result;
	}

	/**
	 * 分页查询扣款信息异常
	 * 
	 * @param requestParam
	 * @return
	 */
	@RequestMapping(value = "/queryPayment", produces = "application/json")
	@ResponseBody
	public PaginationResult<?> queryPayment(DynamicSqlParameter requestParam) {
		try {
			result = paymentService.queryPayment(requestParam);
		} catch (UPPException e) {
			logger.error("分页查询扣款信息异常", e);
		}
		return result;
	}

	/**
	 * 通过ID查找扣款信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/queryById", produces = "application/json")
	@ResponseBody
	public OfflineRechargeApply queryPaymentById(@RequestParam String id) {
		OfflineRechargeApply offline = new OfflineRechargeApply();
		try {
			offline = paymentService.queryPaymentById(id);
		} catch (UPPException e) {
			logger.error("通过ID查询扣款信息异常", e);
		}
		return offline;
	}

	/**
	 * 修改操作步骤,当前处理人及申请状态
	 * 
	 * @return
	 */
	@RequestMapping(value = "/editStepNp.action", produces = "application/json")
	@ResponseBody
	public PaginationResult<?> approvePayment() {
		String user = index.getUserLogin();
		OfflineRechargeApply applyBean=new OfflineRechargeApply();
		TradeApplyDto dto=(TradeApplyDto)model;
		applyBean.setId(dto.getId());
		applyBean.setStepNo(dto.getStepNo());
		applyBean.setApplyStatus(dto.getApplyStatus());
		try {
			result = paymentService.updateStepNp(applyBean, user);
		} catch (UPPException e) {
			logger.error("修改操作步骤失败", e);
		}
		return result;
	}

	/**
	 * 比较两个时间大小
	 * 
	 * @param timeStart
	 * @param timeEnd
	 * @return
	 */
	@RequestMapping(value = "/checkTime.action", produces = "application/json")
	@ResponseBody
	public String checkTime(@RequestParam String timeStart, @RequestParam String timeEnd) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (timeStart != null || !"".equals(timeStart)) {
			try {
				String start = formatDate.format(formatDate.parse(timeStart));
				String end = formatDate.format(formatDate.parse(timeEnd));
				Date dataStart = formatDate.parse(start);
				Date dataEnd = formatDate.parse(end);
				boolean flag = dataStart.before(dataEnd);
				if (flag) {
					return "true";
				}
				return "false";
			} catch (ParseException e) {
				logger.error("时间转换失败",e);
			}
		}

		return "false";
	}
}
