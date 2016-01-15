package com.sinoiov.upp.aop;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.http.HttpUtils;
import com.sinoiov.pay.utils.LogRecordUtil;
import com.sinoiov.pay.utils.UPPJsonUtil;
import com.sinoiov.upp.service.exception.UPPServiceException;

/**
 * controllor异常和日志处理切面
 * 
 * @author sunchuanfu
 */
@Aspect
public class ControllerAroundAspectPocessor {
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 定义切面到com.sinoiov.upp.controller包下
	 */
	@Pointcut(value = "execution(* com.sinoiov.upp.controller..*(..))")
	public void controllerPointCut() {
	}

	private static Map<String, String> methodAndErrorMsg = new HashMap<String, String>();

	@SuppressWarnings("unchecked")
	@Around(value = "controllerPointCut()")
	public void around(ProceedingJoinPoint pjp) throws Exception {
		String method = null;
		List<String> lst = null;
		Object[] args = null;
		String returnJson = null;
		String resultJson = null;
		String paramJson = null;
		String merchantcode = null;
		try {
			// 拦截到的方法名
			method = pjp.getSignature().getName();
			// 拦截到方法参数
			args = pjp.getArgs();
			// 执行方法
			lst = (List<String>) pjp.proceed();
			// 返回结果
			returnJson = lst.get(0);// 返回给接口发布系统调用方的JSON结果(最终返回给用户)
			resultJson = lst.get(1);// 接口发布系统调用后台接口返回JSON结果
			paramJson = lst.get(2);// 请求JSON参数(被记录进日志)
			merchantcode = lst.get(3);// 商户编码
		} catch (UPPServiceException ue) {
			if(method.indexOf("get")>-1 || method.indexOf("query")>-1)
				logger.warn(ue.getLocalizedMessage());
			else
				logger.error(ue.getLocalizedMessage());
			resultJson = UPPJsonUtil.getJson(HttpUtils.ERROR, ue.getErrorCode(), ue.getLocalizedMessage());
			returnJson = resultJson;
		} catch (Exception e) {
			if(method.indexOf("get")>-1 || method.indexOf("query")>-1)
				logger.warn(methodAndErrorMsg.get(method), e);
			else
				logger.error(methodAndErrorMsg.get(method), e);
			resultJson = UPPJsonUtil.getJson(HttpUtils.ERROR, ReturnCodeDict.FAIL, methodAndErrorMsg.get(method));
			returnJson = resultJson;
		} catch (Throwable e) {
			if(method.indexOf("get")>-1 || method.indexOf("query")>-1)
				logger.warn(methodAndErrorMsg.get(method), e);
			else
				logger.error(methodAndErrorMsg.get(method), e);
			resultJson = UPPJsonUtil.getJson(HttpUtils.ERROR, ReturnCodeDict.FAIL, methodAndErrorMsg.get(method));
			returnJson = resultJson;
		} finally {
			// 通过HttpResponse方式返回JSON格式数据
			HttpServletResponse response = (HttpServletResponse) args[0];
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.print(returnJson);
			pw.flush();
			pw.close();
			// 记录日志
			LogRecordUtil.saveRecord(paramJson, resultJson, merchantcode, method);
		}
	}
	
	static {
		// CashierForFastController
		methodAndErrorMsg.put("queryBindBankCard", "查询用户绑卡记录发生异常");
		methodAndErrorMsg.put("queryBankCard", "查询银行卡信息发生异常");
		methodAndErrorMsg.put("sendMessageCode", "发送短信验证码发生异常");
		methodAndErrorMsg.put("unBindCard", "解绑银行卡发生异常");
		methodAndErrorMsg.put("queryOrderRechargeOverOrNot", "查询业务订单号是否已经充值发生异常");
		// FastPayController
		methodAndErrorMsg.put("rechargeByApiSaveRequest", "Api储蓄卡支付请求发生异常");
		methodAndErrorMsg.put("rechargeByApiCreditRequest", "Api信用卡支付请求发生异常");
		methodAndErrorMsg.put("rechargeByApiSure", "Api快捷支付确认发生异常");
		methodAndErrorMsg.put("rechargeByPC", "PC端快捷支付请求发生异常");
		methodAndErrorMsg.put("rechargeByMobile", "手机端快捷支付请求发生异常");
		methodAndErrorMsg.put("rechargeByApiRequest", "Api绑卡快捷支付请求发生异常");
		// WebPayController
		methodAndErrorMsg.put("rechargeByWeb", "网银充值请求发生异常");
		// AccountSafety
		methodAndErrorMsg.put("createAccountSafety", "增加账户安全信息异常");
		methodAndErrorMsg.put("modifyAccountSafety", "修改账户安全信息异常");
		methodAndErrorMsg.put("resetSecurityQuestion", "重置账户安全信息异常");
		methodAndErrorMsg.put("querySecurityQuestion", "根据账号查询账户设置的安全问题名称编码列表异常");
		methodAndErrorMsg.put("validate", "验证账户相关各种信息(短信\\支付密码\\单个安全问题)异常");
		// Account
		methodAndErrorMsg.put("createAccount", "开户(创建的账户支付密码为默认密码)异常");
		methodAndErrorMsg.put("createAccountSmsCode", "开户(带短信验证码)异常");
		methodAndErrorMsg.put("getAccountByAccountNo", "根据账号查询账户信息接口异常");
		methodAndErrorMsg.put("getAccountByUserId", "根据用户ID查询账户信息异常");
		methodAndErrorMsg.put("getAccountDetailById", "根据流水ID查询对应的账户流水信息异常");
		methodAndErrorMsg.put("getSmsCode", "获取短信验证码异常");
		methodAndErrorMsg.put("getSmsCodeForModifyMobileNo", "获取短信验证码(修改手机号码时使用)异常");
		methodAndErrorMsg.put("isPayPassword", "检查账户支付密码是否正确异常");
		methodAndErrorMsg.put("isSetPayPassword", "检查账户是否设置过支付密码异常");
		methodAndErrorMsg.put("lockAccount", "锁定帐户异常");
		methodAndErrorMsg.put("modifyMobileNo", "修改账户绑定手机号码异常");
		methodAndErrorMsg.put("modifyOrderRemark", "根据ID修改账户流水备注异常");
		methodAndErrorMsg.put("modifyPayPassword", "修改支付密码异常");
		methodAndErrorMsg.put("modifyPayPasswordByTicket", "根据安全票据修改支付密码异常");
		methodAndErrorMsg.put("queryAccountByPage", "分页查询账户流水异常");
		methodAndErrorMsg.put("queryAccountDetailByPage", "分页查询账户流水异常");
		methodAndErrorMsg.put("countAccountDetail", "统计账户流水数量异常");
		methodAndErrorMsg.put("queryAccountDetailList", "查询账户流水列表异常");
		methodAndErrorMsg.put("queryAccountList", "查询账户列表异常");
		methodAndErrorMsg.put("queryPayOrderInfoByWorkOrderNo", "根据业务订单号查询订单信息");
		methodAndErrorMsg.put("revokeAccount", "注销账户异常");
		methodAndErrorMsg.put("setPayPassword", "设置支付密码异常");
		methodAndErrorMsg.put("setPayPasswordByTicket", "根据安全票据设置支付密码异常");
		methodAndErrorMsg.put("sumAccountBalance", "统计账户流水异常");
		methodAndErrorMsg.put("unlockAccount", "解锁账户异常");		
		// ClearingAndSettlement
		methodAndErrorMsg.put("contrastOrder", "与商户对帐异常");
		// DeductMoney
		methodAndErrorMsg.put("payApplyAndSure", "扣款审请并确认异常");
		methodAndErrorMsg.put("payApplyForCashierAccount", "收银台：账户支付扣款申请异常");
		methodAndErrorMsg.put("payApplyForCashierFast", "收银台：快捷支付扣款申请异常");
		methodAndErrorMsg.put("payApplyForCashierNet", "收银台网银扣款申请异常");
		methodAndErrorMsg.put("payApplyForMobileAccount", "移动端：账户支付扣款申请异常");
		methodAndErrorMsg.put("payApplyForMobileFast", "移动端：快捷支付扣款申请");
		methodAndErrorMsg.put("paySure", "扣款确认异常");
		// PaymentTrade
		methodAndErrorMsg.put("recharge", "线上账户充值异常");
		methodAndErrorMsg.put("rechargeToUserAccount", "从中交备付金给用户账户充值异常");
		methodAndErrorMsg.put("batchRechargeToUserAccount", "批量为用户充值异常");
		methodAndErrorMsg.put("transferAccounts", "转账接口异常");
		methodAndErrorMsg.put("freezeBalance", "冻结账户金额异常");
		methodAndErrorMsg.put("unFreezeBalance", "解冻账户金额异常");
		methodAndErrorMsg.put("withdrawcash", "提现接口异常");
		methodAndErrorMsg.put("queryPaymentTradeByPage", "分页查询交易记录异常");
		methodAndErrorMsg.put("queryPaymentTradeList", "查询交易记录集合异常");
		methodAndErrorMsg.put("queryOrderByPage", "批量为用户充值异常");
		methodAndErrorMsg.put("getOrderById", "根据Id查询交易订单异常");
		methodAndErrorMsg.put("getOrderByOrderNo", "根据订单号查询订单异常");
		methodAndErrorMsg.put("getCallbackOrderResult", "获取回调订单结果异常");
		// PaymentTradeOffline
		methodAndErrorMsg.put("createApply", "线下帐户充值申请异常");
		methodAndErrorMsg.put("addApproval", "增加审批异常");
		methodAndErrorMsg.put("queryApproval", "条件查询审批信息异常");
		methodAndErrorMsg.put("getApprovalById", "根据ID查询线下申请异常");
		methodAndErrorMsg.put("queryApplyByPage", "分页查询线下申请异常");
		methodAndErrorMsg.put("getApplyById", "根据Id查询线下充值申请异常");
		methodAndErrorMsg.put("addVoucher", "添加审批凭证异常");
		methodAndErrorMsg.put("modifyApproval", "修改审批信息异常");
		methodAndErrorMsg.put("getVoucherByApplyId", "根据申请ID查询凭证信息异常");
		methodAndErrorMsg.put("isVoucherUnique", "验证凭证信息是否重复异常");
		methodAndErrorMsg.put("removeVoucherById", "删除交易凭证信息异常");
		methodAndErrorMsg.put("modifyApply", "修改线下申请异常");
		// RealNameAuthen
		methodAndErrorMsg.put("submitAuthenApplyInfo", "实名认证时出现异常");
		methodAndErrorMsg.put("queryAuthenState", "查询实名认证状态异常");
		methodAndErrorMsg.put("modifyAuthenApplyInfo", "修改实名认证状态异常");
		// SimpleCode
		methodAndErrorMsg.put("queryList", "查询码表信息异常");
		// Statistics
		methodAndErrorMsg.put("sumAccount", "统计账户余额异常");
		methodAndErrorMsg.put("sumAccountDetail", "统计账户流水异常");
	}

}
