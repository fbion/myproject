package com.sinoiov.upp.business.payment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.file.bean.DynamicMongoParameter;
import com.ctfo.file.boss.IMongoService;
import com.ctfo.payment.dao.beans.OfflineRechargeApply;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyApproval;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyApprovalExampleExtended;
import com.ctfo.payment.dao.beans.OfflineRechargeApplyExampleExtended;
import com.ctfo.payment.dao.beans.OrderInfo;
import com.ctfo.payment.dao.beans.RechargeApplyVoucher;
import com.ctfo.payment.dao.beans.RechargeApplyVoucherExampleExtended;
import com.ctfo.upp.dict.PayDict;
import com.ctfo.upp.dict.PayOrderSubjectDict;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.Converter;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.models.Sms;
import com.ctfo.upp.models.SmsPerson;
import com.ctfo.upp.root.utils.AmountUtil;
import com.ctfo.upp.root.utils.CnUpperCaserUtils;
import com.ctfo.upp.root.utils.SmsSender;
import com.ctfo.upp.root.utils.TimeHandleUtil;
import com.ctfo.upp.utils.SpringBUtils;
import com.google.code.morphia.query.Query;
import com.sinoiov.root.util.TradeNumberHelper;
import com.sinoiov.upp.business.AbstractBusiness;
import com.sinoiov.upp.manager.account.IAccountManager;
import com.sinoiov.upp.manager.payment.IApplyApproveManager;
import com.sinoiov.upp.manager.payment.IApplyManager;
import com.sinoiov.upp.manager.payment.IApplyVoucherMagager;
import com.sinoiov.upp.manager.payment.IOrderManager;
import com.sinoiov.upp.manager.payment.IPaymentTransManager;
import com.sinoiov.upp.service.dto.ApplyDto;
import com.sinoiov.upp.service.dto.ApprovalDto;
import com.sinoiov.upp.service.dto.VoucherDto;
import com.sinoiov.upp.util.DefaultConfig;

@Service("paymentTradeOfflineBusiness")
public class PaymentTradeOfflineBusinessImpl extends AbstractBusiness implements
		IPaymentTradeOfflineBusiness {
	
	private static Log logger = LogFactory.getLog(PaymentTradeOfflineBusinessImpl.class);
	
	final static String RECHARGE_SUCCESS_TEMPLATE="tyzfpt1006";
	final static String RECHARGE_FAIL_TEMPLATE="tyzfpt1007";
	final static String PAY_SUCCESS_TEMPLATE="tyzfpt1008";
	final static String PAY_FAIL_TEMPLATE="";
	
	
	@Autowired
	@Qualifier("applyManager")
	private IApplyManager applyManager;
	@Autowired
	@Qualifier("applyApproveManager")
	private IApplyApproveManager applyApproveManager;
	@Autowired
	@Qualifier("applyVoucherMagager")
	private IApplyVoucherMagager applyVoucherMagager;
	@Autowired
	@Qualifier("orderManager")
	private IOrderManager orderManager;
	@Autowired
	@Qualifier("paymentTransManager")
	private  IPaymentTransManager  paymentTransManager;
	@Autowired
	@Qualifier("accountManager")
	private IAccountManager accountManager;
	
	private IMongoService mongoService;
	private IMongoService getMessMongoService() {
		if(mongoService==null){
			mongoService = (IMongoService) SpringBUtils.getBean("mongoService");
			mongoService.setDatasource("BUSINESS");
		}
		return mongoService;
	} 
	
	/**
	 * 创建离线申请相关订单
	 * @param apply
	 * @param dto
	 * @return
	 * @throws UPPException
	 */
	private OrderInfo createOrderByApply(OfflineRechargeApply apply,ApprovalDto dto) throws UPPException{
		OrderInfo orderInfo = new OrderInfo();	
		orderInfo.setOrderAmount(apply.getTradeAmount());
		
		orderInfo.setServiceProviderCode(PayDict.PLATFORM_CODE_ZJXL_PAY);
		orderInfo.setStoreCode(apply.getStoreId());
		orderInfo.setOrderStatus(PayDict.PAY_ORDER_STATUS_PAY_SUCCESS);
		orderInfo.setOwnerUserNo(apply.getOwnerUserNo());
		if(StringUtils.isNotBlank(apply.getInsideAccountNo())){
			orderInfo.setCollectMoneyAccountNo(apply.getInsideAccountNo());
			orderInfo.setAccountNo(apply.getInsideAccountNo());
			Account tema = accountManager.getAccountByAccountNo(apply.getInsideAccountNo());
			orderInfo.setUserId(tema.getOwnerUserId());
			orderInfo.setCollectMoneyUserId(tema.getOwnerUserId());
			if(StringUtils.isBlank(orderInfo.getOwnerUserNo()) && StringUtils.isNotBlank(tema.getOwnerUserNo()))
				orderInfo.setOwnerUserNo(tema.getOwnerUserNo());
		}
		if(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_CZ.equals(apply.getTradeType())){
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_OFFLINE_RECHARGE);
			
		}else if(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_KK.equals(apply.getTradeType())){
			orderInfo.setOrderSubject(PayOrderSubjectDict.ORDER_BUSINESS_SUBJECT_OFFLINE_PAYOUT);
		}else{
			UPPException e = new UPPException("线下申请交易类型不正确");
			e.setErrorCode(ReturnCodeDict.OFFLINE_APPLY_TRADE_TYPE_ERROR);
			throw e;
		}
		return orderInfo;
	}
	private List<ApplyDto> copyOfflineRechargeApply(Collection<OfflineRechargeApply> collection)throws Exception{
		List<ApplyDto> list = new ArrayList<ApplyDto>();
		ApplyDto dto = null;
		String[] ignoreProperties = { "applyTime", "amountArriveTime", "tradeAmount", "fundsConfirmTime" };
		for(OfflineRechargeApply apply : collection){
			dto = new ApplyDto();
			BeanUtils.copyProperties(apply, dto, ignoreProperties);
			dto.setApplyTime(String.valueOf(apply.getApplyTime()));
			dto.setAmountArriveTime(String.valueOf(apply.getAmountArriveTime()));
			dto.setTradeAmount(String.valueOf(apply.getTradeAmount()));
			dto.setFundsConfirmTime(String.valueOf(apply.getFundsConfirmTime()));
			//凭证信息
			List<VoucherDto> listvdto = this.getVoucherByApplyId(apply.getId());
			if(listvdto!=null && listvdto.size()>0){
				dto.setChannel(listvdto.get(0)!=null?listvdto.get(0).getChannel():"");
				dto.setSubChannel(listvdto.get(0)!=null?listvdto.get(0).getSubChannel():"");
			}
			list.add(dto);
		}
		return list;
	}
	
	private List<VoucherDto> copyRechargeApplyVoucher(Collection<RechargeApplyVoucher> collection,String tradType)throws Exception{
		List<VoucherDto> list = new ArrayList<VoucherDto>();
		VoucherDto dto = null;
		String[] ignoreProperties = {"voucherTime"};
		StringBuffer voucherFileName = new StringBuffer();
		for(RechargeApplyVoucher voucher : collection){
			dto = new VoucherDto();
			BeanUtils.copyProperties(voucher, dto, ignoreProperties);
			if(StringUtils.isNotBlank(voucher.getVoucherFileName())){
				if(tradType.equals(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_XPYZ)){
					voucherFileName.append(voucher.getVoucherFileName());
					voucherFileName.append(",");
					dto.setVoucherFileName(voucherFileName.toString());
				}else{
					voucherFileName.append(voucher.getVoucherFileName());
					dto.setVoucherFileName(voucherFileName.toString());
				}
				/*if(voucher.getVoucherFileName().indexOf("http://")<0){
					voucherFileName = DefaultConfig.getValue("IMAGE_URL")+voucherFileName;
				}else if(voucher.getVoucherFileName().indexOf("https://")<0){
					voucherFileName = DefaultConfig.getValue("IMAGE_URL")+voucherFileName;
				}*/
				
			}
			dto.setVoucherTime(voucher.getVoucherTime()==null?"":voucher.getVoucherTime().toString());
			list.add(dto);
		}
		return list;
	}
	
	private List<ApprovalDto> copyApprovalDto(Collection<OfflineRechargeApplyApproval> collection)throws Exception{
		List<ApprovalDto> list = new ArrayList<ApprovalDto>();
		ApprovalDto dto = null;
		//String[] ignoreProperties = {};
		for(OfflineRechargeApplyApproval approval : collection){
			dto = new ApprovalDto();
			BeanUtils.copyProperties(approval, dto);			
			list.add(dto);
		}
		return list;
	}
	
	
	private void copyApplyDto(ApplyDto dto, OfflineRechargeApply apply, RechargeApplyVoucher voucher)throws Exception{
		
		String[] ignoreProperties = { "id", "applyTime", "amountArriveTime", "tradeAmount", "fundsConfirmTime" };
		BeanUtils.copyProperties(dto, apply, ignoreProperties);

		apply.setApplyStatus(PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_Y);// 申请状态
		apply.setPayType(PayDict.OFFLINERECHARGEAPPLY_PAY_TYPE_NPOS);
		if(dto.getTradeType()==null||"".equals(dto.getTradeType()) || "1".equals(dto.getTradeType())){
			apply.setTradeType(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_CZ);// 线下充值
		}else{
			apply.setTradeType(dto.getTradeType());// 线下充值
			
		}
		apply.setApplyTime(Long.valueOf(dto.getApplyTime()));
		apply.setAmountArriveTime(StringUtils.isNotBlank(dto.getAmountArriveTime())?Long.valueOf(dto.getAmountArriveTime()):System.currentTimeMillis());
		apply.setTradeAmount(AmountUtil.getAmount(dto.getTradeAmount()));
		String tradeAmountUpper = dto.getTradeAmountUpper();
		apply.setTradeAmountUpper(StringUtils.isNotBlank(tradeAmountUpper) ? tradeAmountUpper
				: CnUpperCaserUtils.getNum2CnString(String.valueOf(dto.getTradeAmount())));
		apply.setApplyName(dto.getApprovalPersonName());// 申请人
		if(dto.getStepNo()==null ||"".equals(dto.getStepNo())){
			apply.setStepNo(PayDict.OFFLINERECHARGEAPPLY_APPLYAUDIT_ONE);// 出纳-到款确认步骤号
		}else{
			apply.setStepNo(dto.getStepNo());// 出纳-到款确认步骤号
		}
		
		apply.setStoreId(dto.getInsideAccountNo());

		// 存储凭证信息
	//	RechargeApplyVoucher rechargeApplyVoucher = new RechargeApplyVoucher();
//		voucher.setApplyId(apply.getId());
		//凭证图片
		voucher.setVoucherFileName(dto.getVoucherFileName());
		//rechargeApplyVoucher.setVoucherFileName(StringUtils.isNotEmpty(voucherFileName) ? "voucherFileName" : "");

	}
	
	private void copyVoucherDto(VoucherDto dto, RechargeApplyVoucher rechargeApplyVoucher)throws Exception{
		String[] ignoreProperties = { "voucherTime" };
		BeanUtils.copyProperties(dto, rechargeApplyVoucher,ignoreProperties);
		if(dto.getVoucherTime()!=null&&!"".equals(dto.getVoucherTime())){
		rechargeApplyVoucher.setVoucherTime(Long.valueOf(dto.getVoucherTime()));
		}
	}
	private void copyApprovalDto(ApprovalDto dto, OfflineRechargeApplyApproval rechargeApplyApproval)throws Exception{
		String[] ignoreProperties = {"operTime"};
		BeanUtils.copyProperties(dto, rechargeApplyApproval, ignoreProperties);
		rechargeApplyApproval.setOperTime(System.currentTimeMillis());
	}
	
	private RechargeApplyVoucher addVoucher(RechargeApplyVoucher rechargeApplyVoucher) throws Exception{
		//状态检查
// 		OfflineRechargeApply tem = applyManager.getApplyById(rechargeApplyVoucher.getApplyId());
//		if(!PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_Y.equals(tem.getApplyStatus()))
//			throw new UPPException(ReturnCodeDict.FAIL, "申请正在审批中，不允许增加凭证信息!");	
		
		List<RechargeApplyVoucher> list=applyVoucherMagager.getApplyVoucherByApplyId(rechargeApplyVoucher.getApplyId());
		if(list!=null&&list.size()>0){
			rechargeApplyVoucher.setId(list.get(0).getId());
			rechargeApplyVoucher.setVoucherFileName(list.get(0).getVoucherFileName());
			applyVoucherMagager.modifyTradeVoucher(rechargeApplyVoucher);
			return rechargeApplyVoucher;
		}else{
			return  applyVoucherMagager.addTradeVoucher(rechargeApplyVoucher);
		}
		
	}
	
	//发送短信
	private void sendShortMsg(String accountNo,  String template, Long date, Long applyAmount) {
		try {	
			Account account = accountManager.getAccountByAccountNo(accountNo);
			String mobileNo = account.getMobileNo();
			List<String> list = new ArrayList<String>();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(RECHARGE_SUCCESS_TEMPLATE.equals(template)){
				list.add(sf.format(new Date()));
				list.add(AmountUtil.getAmount(applyAmount));
				list.add(AmountUtil.getAmount(account.getUsableBalance()));
			}else if(PAY_SUCCESS_TEMPLATE.equals(template)){
				list.add("");
				list.add(sf.format(new Date()));
				list.add(AmountUtil.getAmount(applyAmount));
				list.add(AmountUtil.getAmount(account.getUsableBalance()));
			}else if(RECHARGE_FAIL_TEMPLATE.equals(template)){
				list.add(sf.format(date));
				list.add(AmountUtil.getAmount(applyAmount));
			}else if(PAY_FAIL_TEMPLATE.equals(template)){
				list.add("");
			}
			if(StringUtils.isNotBlank(mobileNo) && StringUtils.isNotBlank(template))
				SmsSender.getInstance().sendSmsByTemplate(mobileNo, template, list);
			
		} catch (Exception e) {
			logger.warn("充值/扣款发送短信异常:"+e.getLocalizedMessage(), e);
		}
	}

	
	/**
	 * 判断通过或不通过(审批状态和金额):
	 * 		如果通过:(1)增加申请金额(2).修改申请状态;
	 * 		如果不通过:(1)修改申请状态
	 * @param dto
	 * @throws Exception
	 */
	private void handleApply(ApprovalDto dto)throws Exception{
		if(StringUtils.isBlank(dto.getRechargeApplyId()))
			throw new UPPException(ReturnCodeDict.IS_NULL,"申请ID不能为空");
		OfflineRechargeApply rechargeApply = new OfflineRechargeApply();
		OfflineRechargeApply apply = applyManager.getApplyById(dto.getRechargeApplyId());			
		rechargeApply.setId(apply.getId());
		String template=RECHARGE_SUCCESS_TEMPLATE;
		if(PayDict.OFFLINERECHARGEAPPLY_APPROVAL_Y.equals(dto.getApprovalResult())){
			//充值申请
			if(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_CZ.equals(apply.getTradeType())){
				this.handleRechargeApply(dto, rechargeApply, apply);
			}else if(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_KK.equals(apply.getTradeType())){//扣款申请
				this.handlePayApply(dto, rechargeApply, apply);
				template = PayDict.OFFLINERECHARGEAPPLY_APPLYAUDIT_TWO.equals(dto.getStepNo())?PAY_SUCCESS_TEMPLATE:"";
			}else if(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_XPYZ.equals(apply.getTradeType())){//小票验证申请
				this.handleValidateApply(dto, rechargeApply);
				template = "";
			}
		}else{
			if(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_XPYZ.equals(apply.getTradeType())){//小票验证申请	
				rechargeApply.setApplyStatus(dto.getApplyStatus());
				rechargeApply.setStepNo(dto.getStepNo());
			}else{
				rechargeApply.setApplyStatus(PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_N);//状态未通过
				rechargeApply.setStepNo(PayDict.OFFLINERECHARGEAPPLY_APPLYAUDIT_END);//状态未通过	
				template = PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_CZ.equals(apply.getTradeType())?RECHARGE_FAIL_TEMPLATE:PAY_FAIL_TEMPLATE;
			}
		}
		//增加当前审批人
		rechargeApply.setApprovalPersonId(dto.getApprovalUserId());
		rechargeApply.setApprovalPersonName(dto.getApprovalPersonName());
		applyManager.modifyApply(rechargeApply);
		//发送短信
		this.sendShortMsg(apply.getInsideAccountNo(), template, apply.getApplyTime(), apply.getTradeAmount());
	}
	//处理扣款申请
	private void handlePayApply(ApprovalDto dto, OfflineRechargeApply rechargeApply, OfflineRechargeApply apply)throws Exception{
			
		if(PayDict.OFFLINERECHARGEAPPLY_APPLYAUDIT_YWJL.equals(dto.getStepNo())){//业务审批
			//步骤
			rechargeApply.setStepNo(PayDict.OFFLINERECHARGEAPPLY_APPLYAUDIT_TWO);
			//申请状态:审批中
			rechargeApply.setApplyStatus(PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_NY);
		}else if(PayDict.OFFLINERECHARGEAPPLY_APPLYAUDIT_TWO.equals(dto.getStepNo())){//财务经理审批	
			OrderInfo orderInfo = this.createOrderByApply(apply, dto);
			orderInfo.setPayChannel(PayDict.CHANNEL_ACCOUNT);
			orderInfo.setProductCatalog(PayDict.PAY_ORDER_PRODUCT＿CATALOG_ORTHER);
			orderInfo.setProductName("账户扣款");
			orderInfo.setProductDesc("线下账户扣款");
			orderInfo.setOrderType(PayDict.PAY_ORDER_PRODUCT＿CATALOG_ORTHER);
			orderInfo.setWorkOrderNo("OFFLINEPAYAPPLY"+new Date().getTime());
			orderInfo.setOrderSubject(PayDict.ORDER_BUSINESS_SUBJECT_OFFLINE_PAY);
			orderInfo.setTradeExternalNo(TradeNumberHelper.getTradeExternalNo());
			//保存订单
			orderInfo = orderManager.createOrderInfo(orderInfo);
			//处理订单
			paymentTransManager.accountDeductMoneyDirectHandle(orderInfo);
			//处理申请状态:审批完成
			rechargeApply.setFundsConfirmId(dto.getApprovalPersonName());
			rechargeApply.setFundsConfirmTime(dto.getOperTime());
			rechargeApply.setApplyStatus(PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_SUCCESS);
			rechargeApply.setStepNo(PayDict.OFFLINERECHARGEAPPLY_APPLYAUDIT_END);
		}
	}
	//处理小票验证申请
	private void handleValidateApply(ApprovalDto dto, OfflineRechargeApply rechargeApply)throws Exception{
		rechargeApply.setFundsConfirmId(dto.getApprovalPersonName());
		rechargeApply.setFundsConfirmTime(dto.getOperTime());
		rechargeApply.setApplyStatus(dto.getApplyStatus());
		rechargeApply.setStepNo(dto.getStepNo());
		//rechargeApply.setApplyStatus(PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_SUCCESS);
		//rechargeApply.setStepNo(PayDict.OFFLINERECHARGEAPPLY_APPLYAUDIT_ONE);
	}
	//处理充值申请
	private void handleRechargeApply(ApprovalDto dto, OfflineRechargeApply rechargeApply, OfflineRechargeApply apply)throws Exception{
		Long limtAmount = Long.parseLong(DefaultConfig.getValue("OFFLINE_CHECK_AMOUNT"));
		if(apply.getTradeAmount() > limtAmount){//财务经理
			rechargeApply.setStepNo(PayDict.OFFLINERECHARGEAPPLY_APPLYAUDIT_TWO);//步骤
			rechargeApply.setApplyStatus(PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_NY);//状态出纳审批完成,财务经理未审核
		}else{
			OrderInfo orderInfo = this.createOrderByApply(apply, dto);
			orderInfo.setPayChannel(PayDict.CHANNEL_ACCOUNT);
			orderInfo.setProductCatalog(PayDict.PAY_ORDER_PRODUCT＿CATALOG_ACC);
			orderInfo.setProductName("账户充值");
			orderInfo.setProductDesc("线下账户充值");
			orderInfo.setOrderType(PayDict.ORDER_SUBJECT_RECHARGE_OFFLINE);
			orderInfo.setWorkOrderNo("OFFLINERECHARGEAPPLY"+new Date().getTime());
			orderInfo.setOrderSubject(PayDict.ORDER_BUSINESS_SUBJECT_OFFLINE_RECHARGE);
			orderInfo.setTradeExternalNo(TradeNumberHelper.getTradeExternalNo());
			//保存订单
			orderInfo = orderManager.createOrderInfo(orderInfo);
			//处理订单
			paymentTransManager.accountRecordedMoneyHandle(orderInfo);
			//处理申请状态
			rechargeApply.setFundsConfirmId(dto.getApprovalPersonName());
			rechargeApply.setFundsConfirmTime(dto.getOperTime());
			rechargeApply.setApplyStatus(PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_SUCCESS);//审批完成
			rechargeApply.setStepNo(PayDict.OFFLINERECHARGEAPPLY_APPLYAUDIT_END);
		}
	}
	//设置线下申请审核联合查询条件
	private void setQueryApplyExampleExtended(OfflineRechargeApplyExampleExtended exampleExtended, DynamicSqlParameter requestParam)throws Exception{
		RechargeApplyVoucherExampleExtended voucEE = new RechargeApplyVoucherExampleExtended();
		voucEE.setSelectedField(RechargeApplyVoucher.fieldApplyId());
		RechargeApplyVoucherExampleExtended.Criteria voucCriteria = null;
		if(requestParam.getEqual()!=null){
			if(StringUtils.isNotBlank(requestParam.getEqual().get("channel"))){
				voucCriteria = voucCriteria==null?voucEE.createCriteria():voucCriteria;
				voucCriteria.andChannelEqualTo(requestParam.getEqual().remove("channel"));
			}	
			if(StringUtils.isNotBlank(requestParam.getEqual().get("subChannel"))){
				voucCriteria = voucCriteria==null?voucEE.createCriteria():voucCriteria;
				voucCriteria.andSubChannelEqualTo(requestParam.getEqual().remove("subChannel"));
			}
		}		
		OfflineRechargeApplyExampleExtended.Criteria criteria = (OfflineRechargeApplyExampleExtended.Criteria)Converter.paramToExampleExtendedCriteria(requestParam, exampleExtended);
		if(voucCriteria!=null){
			criteria.andIdIn(Arrays.asList(voucEE));
		}	
	}
	
	
	/**
	 * 获取当前星期几
	 * @param dt
	 * @return
	 */
	private String getWeekOfDate(Date date) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }
	
	private void sendApplyMessage(ApplyDto dto){
		try{
			
			Sms ms = (Sms)this.getMessMongoService().get(Sms.class, "pay781357");
			if(1==ms.getStatus()){
				//查询要给发送短信的人员
				Query<SmsPerson> query = this.getMessMongoService().getQuery(SmsPerson.class);
				this.getMessMongoService().convertParamToQuery(query, new DynamicMongoParameter());
				List<SmsPerson> list = this.getMessMongoService().query(SmsPerson.class, query);			
				Date date = new Date();
				//获取当前系统日期（星期几）
				String day = this.getWeekOfDate(date);
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
				//当前系统时间（时分秒）
				String currentTime = df.format(date);
				//将得到的当前系统时间（时分秒）转化为秒数
				Long longTime = TimeHandleUtil.totalSec(currentTime);
				for(SmsPerson sms : list){
					String smsDay = sms.getDay();
					String startTime = sms.getStartTime();
					String endTime = sms.getEndTime();
					if(!StringUtils.isBlank(startTime)&&!StringUtils.isBlank(endTime)){
						Long longStartTime = TimeHandleUtil.totalSec(startTime);
						Long longEndTime = TimeHandleUtil.totalSec(endTime);
						if(smsDay!=null&&startTime!=null&&endTime!=null){
							if(smsDay.indexOf(day)!=-1 && longTime>=longStartTime && longTime<=longEndTime){
								//发送短信
								List<String> contentsList = new ArrayList<String>();
								contentsList.add(dto.getInsideAccountNo());
								//当前时间
								contentsList.add(TimeHandleUtil.formatDate(date,"yyyy-MM-dd HH:mm:ss"));
								contentsList.add(dto.getTradeAmount());
								if(StringUtils.isNotBlank(sms.getMobileNo()))
									SmsSender.getInstance().sendSmsByTemplate(sms.getMobileNo(), "pay781357", contentsList);
							}
						}
					}
				}
			}
			
		}catch(Exception e){
			logger.warn("发送短信异常："+e.getMessage(), e);
		}
		
	}
	
	/**
	 * 1.创建申请
	 * 2.保存凭证
	 */
	@Override
	public ApplyDto createApply(ApplyDto dto) throws UPPException {
		try{
			OfflineRechargeApply apply = new OfflineRechargeApply(); 
			RechargeApplyVoucher voucher = new RechargeApplyVoucher();
			this.copyApplyDto(dto, apply, voucher);			
			//申请号
			apply.setApplyNo(TradeNumberHelper.getPayOrderNo("4"));	
			//会员编号
			if(StringUtils.isBlank(apply.getOwnerUserNo()) && !apply.getTradeType().equals(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_XPYZ)){
				Account account = accountManager.getAccountByAccountNo(apply.getInsideAccountNo());
				apply.setOwnerUserNo(this.getOwnerUserNo(account.getOwnerUserId()));
			}
			//付款账户中文名称
			if(apply.getTradeType().equals(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_KK)){
				Account account = accountManager.getAccountByAccountNo(apply.getInsideAccountNo());
				apply.setRemitterName(this.getUserName(account.getOwnerUserId()));
			}
			//业务员所属地区
			if(StringUtils.isBlank(apply.getApplyPersonPost())){
				apply.setApplyPersonPost(this.getOwnerUserPost(apply.getApplyName()));
			}
			
			apply = applyManager.createApply(apply);
			dto.setId(apply.getId());

			//保存凭证
			if(voucher!=null && StringUtils.isNotBlank(voucher.getVoucherFileName())){
				voucher.setApplyId(apply.getId());
				if(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_XPYZ.equals(apply.getTradeType())){
					applyVoucherMagager.addTradeVoucher(voucher);
				}else{
					this.addVoucher(voucher);
				}
			}
			
			if(PayDict.OFFLINERECHARGEAPPLY_TRADETYPE_CZ.equals(apply.getTradeType()))
				this.sendApplyMessage(dto);//发送短信
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("创建线下交易申请异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "创建线下交易申请异常:"+e.getLocalizedMessage());
		}
		return dto;
	}
	
	
	@Override
	public String modifyApply(ApplyDto dto)throws UPPException {
		try{		
			//状态检查
			OfflineRechargeApply tem = applyManager.getApplyById(dto.getId());
			if(!PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_Y.equals(tem.getApplyStatus()))
				throw new UPPException(ReturnCodeDict.FAIL, "申请正在审批中，不允许修改申请信息!");
			
			OfflineRechargeApply apply = new OfflineRechargeApply(); 
			RechargeApplyVoucher voucher = new RechargeApplyVoucher();
			this.copyApplyDto(dto, apply, voucher);
			//不能修改项
			apply.setApplyNo(null);
			applyManager.modifyApply(apply);
			
			return SUCCESS;		
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("修改线下交易申请异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改线下交易申请异常:"+e.getLocalizedMessage());
		}
	}
	@Override
	public String removeApply(String id) throws UPPException {
		try{			
			//状态检查
			OfflineRechargeApply tem = applyManager.getApplyById(id);
			if(!PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_Y.equals(tem.getApplyStatus()))
				throw new UPPException(ReturnCodeDict.FAIL, "申请正在审批中，不允许删除申请信息!");			
			applyManager.removeApply(id);		
			return SUCCESS;			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("删除线下交易申请异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "删除线下交易申请异常:"+e.getLocalizedMessage());
		}
	}
	
	
	@Override
	public List<ApplyDto> queryApply(
			DynamicSqlParameter requestParam) throws UPPException {
		try{			
			OfflineRechargeApplyExampleExtended exampleExtended = new OfflineRechargeApplyExampleExtended();
			this.setQueryApplyExampleExtended(exampleExtended, requestParam);
			Collection<OfflineRechargeApply> collection = applyManager.queryApply(exampleExtended);
			return this.copyOfflineRechargeApply(collection);
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件查询线下交易申请异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询线下交易申请异常:"+e.getLocalizedMessage());
		}
	}
	
	@Override
	public PaginationResult<ApplyDto> queryApplyByPage(
			DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<ApplyDto> result = new PaginationResult<ApplyDto>();
		try{
			
			OfflineRechargeApplyExampleExtended exampleExtended = new OfflineRechargeApplyExampleExtended();
			this.setQueryApplyExampleExtended(exampleExtended, requestParam);
			
			PaginationResult<OfflineRechargeApply> tem = applyManager.queryApplyPage(exampleExtended);				
			result.setTotal(tem.getTotal());
			result.setData(this.copyOfflineRechargeApply(tem.getData()));
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件查询线下交易申请分页异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询线下交易申请分页异常:"+e.getLocalizedMessage());
		}
		return result;
	}
	@Override
	public ApplyDto getApplyById(String id)
			throws UPPException {
		try{
			OfflineRechargeApply apply = applyManager.getApplyById(id);
			Collection<OfflineRechargeApply> collection = new ArrayList<OfflineRechargeApply>();
			collection.add(apply);
			return this.copyOfflineRechargeApply(collection).get(0);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据ID线下交易申请异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据ID线下交易申请异常:"+e.getLocalizedMessage());
		}
	}
	
	@Override
	public VoucherDto addVoucher(VoucherDto dto) throws UPPException {
		try{
			//状态检查
			RechargeApplyVoucher rechargeApplyVoucher = new RechargeApplyVoucher();
			this.copyVoucherDto(dto, rechargeApplyVoucher);
			rechargeApplyVoucher = this.addVoucher(rechargeApplyVoucher);
			dto.setId(rechargeApplyVoucher.getId());
			return dto;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("增加凭证信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "增加凭证信息异常:"+e.getLocalizedMessage());
		}
	}
	
	@Override
	public String modifyVoucher(VoucherDto dto)
			throws UPPException {
		try{
			//状态检查			
			OfflineRechargeApply tem = applyManager.getApplyById(dto.getApplyId());
			if(!PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_Y.equals(tem.getApplyStatus()))
				throw new UPPException(ReturnCodeDict.FAIL, "申请正在审批中，不允许修改凭证信息!");		
			RechargeApplyVoucher rechargeApplyVoucher = new RechargeApplyVoucher();
			this.copyVoucherDto(dto, rechargeApplyVoucher);
			rechargeApplyVoucher.setId(tem.getId());
			applyVoucherMagager.modifyTradeVoucher(rechargeApplyVoucher);			
			return SUCCESS;			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("修改凭证信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改凭证信息异常:"+e.getLocalizedMessage());
		}
	}
	@Override
	public List<VoucherDto> getVoucherByApplyId(String applyId)
			throws UPPException {
		try{	
			List<RechargeApplyVoucher> list = applyVoucherMagager.getApplyVoucherByApplyId(applyId);	
			OfflineRechargeApply apply = applyManager.getApplyById(applyId);
			return this.copyRechargeApplyVoucher(list,apply.getTradeType());	
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据申请ID查询凭证信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据申请ID查询凭证信息异常:"+e.getLocalizedMessage());
		}
	}
	@Override
	public String removeVoucherByApplyId(String applyId) throws UPPException {
		try{			
			List<RechargeApplyVoucher> list = applyVoucherMagager.getApplyVoucherByApplyId(applyId);
			for(RechargeApplyVoucher bean : list){
				applyVoucherMagager.removeTradeVoucher(bean.getId());
			}
			return SUCCESS;			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据申请ID删除凭证信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据申请ID删除凭证信息异常:"+e.getLocalizedMessage());
		}
	}
	@Override
	public String removeVoucherById(String id)
			throws UPPException {
		try{			
			applyVoucherMagager.removeTradeVoucher(id);			
			return SUCCESS;			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据ID删除凭证信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据ID删除凭证信息异常:"+e.getLocalizedMessage());
		}
	}
	
	
	/**
	 * 1.增加审批信息
	 */
	@Override
	public ApprovalDto addApproval(ApprovalDto dto)throws UPPException {
		try{
			//验证业务必项	
			OfflineRechargeApplyApproval rechargeApplyApproval = new OfflineRechargeApplyApproval();
			this.copyApprovalDto(dto, rechargeApplyApproval);
			rechargeApplyApproval = applyApproveManager.addApplyApprove(rechargeApplyApproval);
			dto.setId(rechargeApplyApproval.getId());
			//处理申请逻辑
			this.handleApply(dto);
			return dto;
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("增加审批信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "增加审批信息异常:"+e.getLocalizedMessage());
		}
	}
	@Override
	public String modifyApproval(ApprovalDto dto)throws UPPException {
		try{
			//设置不能修改项	
			OfflineRechargeApplyApproval rechargeApplyApproval = new OfflineRechargeApplyApproval();
			this.copyApprovalDto(dto, rechargeApplyApproval);
			rechargeApplyApproval.setId(dto.getId());
			applyApproveManager.modifyApplyApprove(rechargeApplyApproval);
			//处理申请逻辑
			this.handleApply(dto);
			return SUCCESS;	
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("修改审批信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "修改审批信息异常:"+e.getLocalizedMessage());
		}
	}
	@Override
	public String removeApproval(String id) throws UPPException {
		try{		
			applyApproveManager.removeApplyApprove(id);			
			return SUCCESS;		
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("删除审批信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "删除审批信息异常:"+e.getLocalizedMessage());
		}
	}
	
	@Override
	public List<ApprovalDto> queryApproval(
			DynamicSqlParameter requestParam) throws UPPException {
		try{			
			OfflineRechargeApplyApprovalExampleExtended exampleExtended = new OfflineRechargeApplyApprovalExampleExtended();
			Converter.paramToExampleExtended(requestParam, exampleExtended);		
			List<OfflineRechargeApplyApproval> list = applyApproveManager.queryApplyApprove(exampleExtended);
			return this.copyApprovalDto(list);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件查询审批信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询审批信息异常:"+e.getLocalizedMessage());
		}
	}
	@Override
	public ApprovalDto getApprovalById(String id)
			throws UPPException {
		try{			
			OfflineRechargeApplyApproval approval = applyApproveManager.getApplyApprovalById(id);
			List<OfflineRechargeApplyApproval> list = new ArrayList<OfflineRechargeApplyApproval>();
			list.add(approval);
			return this.copyApprovalDto(list).get(0);
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据ID查询审批信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据ID查询审批信息异常:"+e.getLocalizedMessage());
		}
	}
	@Override
	public PaginationResult<ApprovalDto> queryApprovalByPage(
			DynamicSqlParameter requestParam) throws UPPException {
		PaginationResult<ApprovalDto> result = new PaginationResult<ApprovalDto>();
		try{			
			OfflineRechargeApplyApprovalExampleExtended exampleExtended = new OfflineRechargeApplyApprovalExampleExtended();
			Converter.paramToExampleExtended(requestParam, exampleExtended);		
			PaginationResult<OfflineRechargeApplyApproval> tem =  applyApproveManager.queryApplyApprovalPage(exampleExtended);			
			if(tem !=null){
				result.setTotal(tem.getTotal());
				result.setData(this.copyApprovalDto(tem.getData()));	
			}
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("根据条件查询审批分页信息异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "根据条件查询审批分页信息异常:"+e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	public boolean isVoucherUnique(VoucherDto dto) throws UPPException {
		
		try{
			if(StringUtils.isBlank(dto.getApplyId()))
				throw new UPPException(ReturnCodeDict.IS_NULL,"申请ID不能为空");
			
			//String applyId = dto.getApplyId();		
			//OfflineRechargeApply apply = applyManager.getApplyById(applyId);
			//if(PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_SUCCESS.equals(apply.getApplyStatus())){
				OfflineRechargeApplyExampleExtended applyEE = new OfflineRechargeApplyExampleExtended();
				applyEE.createCriteria().andApplyStatusEqualTo(PayDict.OFFLINERECHARGEAPPLY_APPLYSTATUS_SUCCESS);
				RechargeApplyVoucherExampleExtended exampleExtended = new RechargeApplyVoucherExampleExtended();
				if(PayDict.VOUCHER_CHANNEL_POS.equals(dto.getChannel())){
					exampleExtended.createCriteria().andChannelEqualTo(dto.getChannel())
					.andSubChannelEqualTo(dto.getSubChannel())
					.andMerchantCodeEqualTo(dto.getMerchantCode())
					.andTerminalCodeEqualTo(dto.getTerminalCode())
					.andVoucherCodeEqualTo(dto.getVoucherCode())
					.andApplyIdIn(Arrays.asList(applyEE))
					.andVoucherTimeEqualTo(Long.parseLong(dto.getVoucherTime()));
				}else if(PayDict.VOUCHER_CHANNEL_OHER.equals(dto.getChannel())){
					exampleExtended.createCriteria().andChannelEqualTo(dto.getChannel())
					.andSubChannelEqualTo(dto.getSubChannel())
					.andVoucherTradeNoEqualTo(dto.getVoucherTradeNo())
					.andApplyIdIn(Arrays.asList(applyEE))
					.andVoucherTimeEqualTo(Long.parseLong(dto.getVoucherTime()));
				}
				
				int count = applyVoucherMagager.countTradeVoucher(exampleExtended);
				return count>0?true:false;
			//}
			
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("验证凭证信息唯一异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "验证凭证信息唯一异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public int countApply(DynamicSqlParameter requestParam) throws UPPException {
		try{
			OfflineRechargeApplyExampleExtended exampleExtended = new OfflineRechargeApplyExampleExtended();
			this.setQueryApplyExampleExtended(exampleExtended, requestParam);
			return applyManager.countApply(exampleExtended);	
		}catch(UPPException ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw ue;
		}catch(Exception e){
			logger.error("验证凭证信息唯一异常！", e);
			throw new UPPException(ReturnCodeDict.ERROR, "验证凭证信息唯一异常:"+e.getLocalizedMessage());
		}
	}

	@Override
	public List<ApplyDto> handleApplyData(List<ApplyDto> list) throws UPPException {
		try{
			OfflineRechargeApply apply = null;
			for(ApplyDto dto : list){
				if(StringUtils.isNotBlank(dto.getOwnerUserNo()) || StringUtils.isNotBlank(dto.getApplyPersonPost())){
					apply = new OfflineRechargeApply();	
					apply.setId(dto.getId());
					if(StringUtils.isNotBlank(dto.getOwnerUserNo()))
						apply.setOwnerUserNo(dto.getOwnerUserNo());
					if(StringUtils.isNotBlank(dto.getApplyPersonPost()))
						apply.setApplyPersonPost(dto.getApplyPersonPost());
					applyManager.modifyApply(apply);
				}	
			}
			return list;
		}catch(Exception ue){
			logger.error(ue.getLocalizedMessage(), ue);
			throw new UPPException("处理申请旧数据时异常", ue);
		}
	}
	

}
