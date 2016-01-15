package com.ctfo.upp.accountservice.payment.internal.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.account.dao.beans.AccountExampleExtended;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.payment.dao.beans.HPaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTrade;
import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.payment.dto.beans.TransferAccountsResult;
import com.ctfo.payment.intf.internal.IPaymentBaseManager;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.manager.support.UppGenericManagerImpl;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.upp.manager.account.IAccountManager;

//@Service("paymentBaseManagerService")
public class PaymentBaseManagerImpl extends UppGenericManagerImpl<Object, Object>  implements IPaymentBaseManager{
	private static final Log logger = LogFactory
			.getLog(PaymentBaseManagerImpl.class);
	@SuppressWarnings("rawtypes")
	@Autowired
	@Qualifier("mongoService")
	private IMongoService mongoService;
	

	@Autowired
	@Qualifier("accountManagerService")
	private IAccountManager internalAccountManager;
	

	@Override
	public PaymentTrade queryPaymentTransById(String paymentTransUUID) throws UPPException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaymentTrade> queryPaymentTrans(PaymentTradeExampleExtended exampleExtended) throws UPPException {
		List<PaymentTrade> list = new ArrayList<PaymentTrade>();
		try {
			list = super.getModels(exampleExtended);
		} catch (Exception e) {
			logger.debug("查询业务订单数据时出错！", e);
			throw new UPPException("查询业务订单数据时出错！", e);
		}
		return list;
	}

	@Override
	public PaginationResult<PaymentTrade> queryPaymentTransByPage(PaymentTradeExampleExtended exampleExtended) throws UPPException {
		PaginationResult<PaymentTrade> paginator = new PaginationResult<PaymentTrade>();
		try {
			paginator = super.paginateModels(exampleExtended);
		} catch (Exception e) {
			logger.debug("根据条件对象分页查询业务订单数据时出错！", e);
			throw new UPPException("根据条件对象分页查询业务订单数据时出错！", e);
		}
		return paginator;
	}

	@Override
	public int updatePaymentTransStatus(String id, String status,Long batchInternalNo)
			throws UPPException {
		try {
			this.savePaymentTradeHistory(id);//保存历史

			PaymentTrade paymentTrade = new PaymentTrade();
			paymentTrade.setId(id);
			paymentTrade.setTradeStatus(status);
			paymentTrade.setBatchInternalNo(batchInternalNo);
			
			return super.updateModelPart(paymentTrade);//修改状态
		} catch (Exception e) {
			logger.error("更新交易状态异常", e);
			throw new UPPException("更新交易状态异常",e);
		}
		
	}
	
	@Override
	public int updatePaymentTransStatus(String id, String status)
			throws UPPException {
		try{

			this.savePaymentTradeHistory(id);//保存历史

			PaymentTrade paymentTrade = new PaymentTrade();
			paymentTrade.setId(id);
			paymentTrade.setTradeStatus(status);
			
			return super.updateModelPart(paymentTrade);//修改状态

		}catch(Exception e){
			logger.error("更新交易状态异常", e);
			throw new UPPException("更新交易状态异常",e);
		}
	}
	@Override
	public int updatePaymentTransStatus(String id, String status, String externalNo, Long confirmDate)
			throws UPPException {
		try{
			
			this.savePaymentTradeHistory(id);//保存历史
			
			PaymentTrade paymentTrade = new PaymentTrade();
			paymentTrade.setId(id);
			paymentTrade.setTradeStatus(status);
			paymentTrade.setExternalNo(externalNo);
			paymentTrade.setPayConfirmDate(confirmDate>0?confirmDate:new Date().getTime());//确认时间
			
			return super.updateModelPart(paymentTrade);//修改状态
			
		}catch(Exception e){
			logger.error("更新交易状态异常", e);
			throw new UPPException("更新交易状态异常",e);
		}
	}
	
	@Override
	public PaymentTrade createPaymentTrans(PaymentTrade paymentTrans)
			throws UPPException {
		try{
			
			paymentTrans.setCreateTime(new Date().getTime());//交易创建时间
			paymentTrans.setCreateSubareaTime(new Date());//当前时间
			paymentTrans.setPayConfirmDate(new Long(0));//支付确认时间
			paymentTrans.setPayPoundScale(new Long(0));//手续费
			paymentTrans.setVersion(0);
			paymentTrans.setTradeStatus(PayDict.PAY_TRADE_STATUS_INIT);//初始状态
			//必填项
			if(StringUtils.isBlank(paymentTrans.getOrderId()))
				throw new UPPException("订单号不能为空");
			if(StringUtils.isBlank(paymentTrans.getAccountNo()))
				throw new UPPException("账号不能为空");
			if(StringUtils.isBlank(paymentTrans.getTradeExternalNo()))
				throw new UPPException("内部交易流水号不能为空");
			if(paymentTrans.getOrderAmount()<0)
				throw new UPPException("交易金额必须大于0");
			
			String uuid = super.addModel(paymentTrans);
			paymentTrans.setId(uuid);
			
			return paymentTrans;
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("创建交易时异常", e);
			throw new UPPException("创建交易时异常");
		}
	}

	@Override
	public int modifyPaymentTrans(PaymentTrade paymentTrans)
			throws UPPException {

		try{
			this.savePaymentTradeHistory(paymentTrans.getId());//保存历史

			paymentTrans.setCreateTime(new Date().getTime());
			
			return super.updateModelPart(paymentTrans);//修改状态

		}catch(Exception e){
			logger.error("更新交易状态异常", e);
			throw new UPPException("更新交易状态异常",e);
		}
	}

	@Override
	public PaymentTrade queryPaymentTransByOrderId(String orderId) throws UPPException {
		PaymentTradeExampleExtended paymentTradeExampleExtended = new PaymentTradeExampleExtended();
		paymentTradeExampleExtended.createCriteria().andOrderIdEqualTo(orderId);
		PaymentTrade paymentTrade = new PaymentTrade();
		try {
			List<PaymentTrade> list = super.getModels(paymentTradeExampleExtended);
			if(list.size()>0){
				paymentTrade = list.get(0);
			}else
				return null;
		} catch (Exception e) {
			logger.error("通过交易订单ID查询支付交易表信息异常 ", e);
			throw new UPPException("通过交易订单ID查询支付交易表信息异常", e);
		}
		return paymentTrade;
	}
	

	@Override
	public int removePaymentTrans(String paymentTransUUID) throws UPPException {
		// TODO Auto-generated method stub
		return 0;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public PaymentTrade queryPaymentTransTradeExternalNo(String tradeExternalNo) throws UPPException {
		try {
			PaymentTradeExampleExtended ptee = new PaymentTradeExampleExtended();
			ptee.createCriteria().andTradeExternalNoEqualTo(tradeExternalNo);
			List list = super.getModels(ptee);
			if (list != null && list.size() == 1)
				return (PaymentTrade) list.get(0);

		} catch (Exception e) {
			logger.error("更据内部交易流水号查询交易异常", e);
			throw new UPPException(e.getMessage());
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleTradeRecord(String tradeNo) {
		try {
			mongoService.delete(TransferAccountsResult.class, tradeNo);
		} catch (Exception e) {
			logger.error(tradeNo + ":该交易删除备份失败... ...");
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 保存交易历史
	 * @param id
	 * @return
	 * @throws UPPException
	 */
	private String savePaymentTradeHistory(String id) throws UPPException{
		try{
			PaymentTrade paymentTrade = new PaymentTrade();
			paymentTrade.setId(id);
			paymentTrade = (PaymentTrade) super.getModelById(paymentTrade);
			HPaymentTrade hPaymentTrade = new HPaymentTrade();
			PropertyUtils.copyProperties(hPaymentTrade, paymentTrade);
			hPaymentTrade.setRecordCreateTime(new Date().getTime());//创建时间
			hPaymentTrade.setPayTradeId(paymentTrade.getId());
			return super.addModel(hPaymentTrade);
		}catch(Exception e){
			logger.error("保存交易历史异常", e);
			throw new UPPException("保存交易历史异常",e);
		}
	}
	
	@Override
	public String getUserIdByAccountNo(String accountNo) throws UPPException {
		AccountExampleExtended accountexampleExtended = new AccountExampleExtended();
		accountexampleExtended.createCriteria().andInsideAccountNoEqualTo(accountNo);
		List<Account> accountList = internalAccountManager.queryAccount(accountexampleExtended);
		if (accountList == null || accountList.isEmpty()) {
			throw new UPPException("未查到对应账户");
		} else {
			return accountList.get(0).getOwnerUserId();
		}
	}

	@Override
	public int queryPaymentTradeCount(PaymentTradeExampleExtended paymentTradeExampleExtended) throws UPPException {
		try {
			return super.countModels(paymentTradeExampleExtended);
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}

	@Override
	public int updatePaymentTransClearingStatus(String id, String state)
			throws UPPException {
		try{
			
			this.savePaymentTradeHistory(id);//保存历史
			
			PaymentTrade paymentTrade = new PaymentTrade();
			paymentTrade.setId(id);
			paymentTrade.setIsClearing(state);			
			return super.updateModelPart(paymentTrade);//修改状态
			
		}catch(Exception e){
			logger.error("更新交易状态异常", e);
			throw new UPPException("更新交易状态异常",e);
		}
	}
	@Override
	public int updatePaymentTransClearingStatus(String id, String state,Long batchInternalNo)
			throws UPPException {
		try{
			
			this.savePaymentTradeHistory(id);//保存历史
			
			PaymentTrade paymentTrade = new PaymentTrade();
			paymentTrade.setId(id);
			paymentTrade.setIsClearing(state);
			paymentTrade.setBatchInternalNo(batchInternalNo);
			return super.updateModelPart(paymentTrade);//修改状态
			
		}catch(Exception e){
			logger.error("更新交易状态异常", e);
			throw new UPPException("更新交易状态异常",e);
		}
	}
	
	/***
	 * webAPP-交易订单管理-交易订单查询：按订单号导出单条订单信息
	 * @param orderNo
	 */
	@Override
	public List<Map<?,?>> queryPaymentTransByOrderNo(String orderNo) throws UPPException {
		List<Map<? extends Object,? extends Object>> order = new ArrayList<Map<? extends Object,? extends Object>>();
		Map<String, Object> param=new HashMap<String ,Object>();
		if(StringUtils.isNotBlank("orderNo")){
			param.put("orderNo", orderNo.trim());
		}
		try {
			order = super.queryListBySQL("TB_TRADE_ORDER_EXTEND.queryTradeOrderExpList", param);
		} catch (Exception e) {
			logger.error("通过条件导出交易订单异常");
			throw new UPPException("通过条件导出交易订单异常", e);
		}
		return order;
	}
	@Override
	public List<Map<?,?>> queryPaymentTransByParam(Map<String, Object> param) throws UPPException {
		List<Map<? extends Object,? extends Object>> order = new ArrayList<Map<? extends Object,? extends Object>>();
		try {
			order = super.queryListBySQL("TB_TRADE_ORDER_EXTEND.queryTradeOrderExpList", param);
		} catch (Exception e) {
			logger.error("通过条件导出交易订单异常");
			throw new UPPException("通过条件导出交易订单异常", e);
		}
		return order;
	}
	
	/**
	 * webAPP交易订单管理-交易订单查询，导出功能使用方法
	 * @param requestParam
	 */
	@Override
	public List<Map<?,?>> queryPaymentAll(DynamicSqlParameter requestParam) throws UPPException {
		List<Map<? extends Object,? extends Object>> order = new ArrayList<Map<? extends Object,? extends Object>>();
		Map<String, Object> param = SqlParamCondition(requestParam);
		try {
			order = super.queryListBySQL("TB_TRADE_ORDER_EXTEND.queryTradeOrderExpList", param);
		} catch (Exception e) {
			logger.error("通过条件导出交易订单异常");
			throw new UPPException("通过条件导出交易订单异常", e);
		}
		return order;
	}
	/***
	 * webAPP交易订单管理-交易订单查询，自定义查询SQL
	 * @param requestParam
	 */
	@Override
	public List<Map<?,?>> queryPaymentTransByPage(DynamicSqlParameter requestParam) throws UPPException {
		List<Map<? extends Object,? extends Object>> order = new ArrayList<Map<? extends Object,? extends Object>>();
		Map<String, Object> param = SqlParamCondition(requestParam);
		int page = requestParam.getPage();
    	int rows = requestParam.getRows();
    	param.put("start", String.valueOf((page-1)*rows));
    	param.put("limit", String.valueOf(requestParam.getRows()));
		try {
			order = super.queryListBySQL("TB_TRADE_ORDER_EXTEND.queryTradeOrderList", param);
		} catch (Exception e) {
			logger.error("通过条件查询交易订单异常");
			throw new UPPException("通过条件查询交易订单异常", e);
		}
		return order;
	}
	
	public int queryPaymentTradeCount(DynamicSqlParameter requestParam)throws UPPException{
		Map<String, Object> param = SqlParamCondition(requestParam);
		try {
			int totalOrder = (Integer) super.queryObjectBySQL("TB_TRADE_ORDER_EXTEND.totalCountTradeOrderList", param);
			return totalOrder;
		} catch (Exception e) {
			logger.error("通过条件查询交易订单异常");
			throw new UPPException("通过条件查询交易订单异常", e);
		}
	}
	/***
	 * SQL查询页面传值,查询条件
	 * @param requestParam
	 * @return
	 */
	private Map<String, Object> SqlParamCondition(DynamicSqlParameter requestParam) {
		Map<String, Object> param=new HashMap<String ,Object>();
		//需要加条件判断
		//1.订单号
		if(requestParam.getEqual()!=null){
			if(requestParam.getEqual().get("orderNo")!=null){
				param.put("orderNo", requestParam.getEqual().get("orderNo"));
			}
			if(requestParam.getEqual().get("tradeExternalNo")!=null){
				param.put("tradeExternalNo", requestParam.getEqual().get("tradeExternalNo"));
			}
			if(requestParam.getEqual().get("orderType")!=null){
				param.put("orderType", requestParam.getEqual().get("orderType"));
			}
			if(requestParam.getEqual().get("orderStatus")!=null){
				param.put("orderStatus", requestParam.getEqual().get("orderStatus"));
			}
			if(requestParam.getEqual().get("insideAccountNo")!=null){//转入账户
				param.put("collectMoneyAccountNo", requestParam.getEqual().get("insideAccountNo"));
			}
			if(requestParam.getEqual().get("accountNo")!=null){//转出账户
				param.put("accountNo", requestParam.getEqual().get("accountNo"));
			}
		}
		//交易时间
		if(requestParam.getStartwith()!=null&&requestParam.getStartwith().get("startTime")!=null){
			param.put("startTime", requestParam.getStartwith().get("startTime"));
		}
		if(requestParam.getEndwith()!=null&&requestParam.getEndwith().get("endTime")!=null){
			param.put("endTime", requestParam.getEndwith().get("endTime"));
		}
		 //排序
		 if(StringUtils.isEmpty(requestParam.getOrder())&&StringUtils.isEmpty(requestParam.getSort())){
			 param.put("orderByClause", "createTime desc");
		 }else{
			 param.put("orderByClause", requestParam.getOrder() +" "+ requestParam.getSort());
		 }
		return param;
	}
	
	public void unLockAccount(String accountNo)throws UPPException{
		try{
			internalAccountManager.unlockAccount(accountNo);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
//		internalAccountManager.unlockAccount_(accountNo);
	}
}
