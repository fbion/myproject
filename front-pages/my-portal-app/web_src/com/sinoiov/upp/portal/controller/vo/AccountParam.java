package com.sinoiov.upp.portal.controller.vo;


/**
 * 表单参数
 * 
 * @author jichao
 */
public class AccountParam {
	/**
	 * 开通账号/修改密码表单参数
	 */
	private String ownerUserId;//用户ID
	private String ownerLoginName;//用户名称 
	private String insideAccountNo; // 内部账号编号
	private String payPassword;// 支付密码
	private String payPasswordNew;//新支付密码
	private String payPasswordComfirm;// 确认支付密码 
	private String mobileNo;// 绑定手机号
	private String isOperMess;// 短信是否可用
	private String smsCheckCode;// 短信验证码
	private String imgCheckCode;// 图片验证码

	/**
	 * 实名认证表单参数
	 */
	private String userName;// 真实姓名
	private String bankcardNo;// 银行卡号或对公账号
	private String bankCode;// 银行代码
	private String branchBankName;// 开户行名称
	private String branchBankProvince;// 开户行省份
	private String branchBankCity;// 开户行城市
	private String idcardType;// 证件类型
	private String idcardNo;// 证件号码
	private String isMainCard ;//银行卡类型：0不是主卡，1是主卡
	private String accountNoType;//帐卡类型:0 是普通账号，1是对公账号
	private String payChannel;//web支付，wap网页支付
	/**
	 * 收银台参数
	 */
	private String storeId;//商户ID 
	private String payType;//支付类型:  网银支付:1     账户支付:2      快捷支付:3
	private String bankType;//选择的银行类型
	private String insideAccountName ;//内部账号
	private String amount;//订单金额
	private String usableBalance;//账户可用金额
	private String collectMoneyAccountNo;//收款内部账号
	private String serviceProviderCode;//支付平台编码  易宝1002;
	private String clentType;//终端类型 PC等
	/**
	 * payPassword 支付密码(已有)
	 * accountNo 付款内部账户号(已有)
	 */
	
	
	/**
	 * 充值操作
	 */
	private String userId;//用户ID
	private String accountNo;//用户账户
	private String storeCode;//来自哪个商户(商户编号)
	//private String amount;//可用余额/订单金额
	private String bankCardCode;//银行卡代码
	/*商品类别  1=虚拟产品,3=公共事业缴费,4=手机充值,6=公益事业,7=实物电商 ,8=彩票业务,10=行政教育,11=线下服务业,13=微信实物电商
	14=微信虚拟电商,15=保险行业,16=基金行业,17=电子票务,18=金融投资,19=大额支付,20=其他 ,21=旅游机票,22=畅付D*/
	private String productCatalog;
	private String productName;//商品名称 
	private String userIp;//Client IP
	//标识类型:0=IMEI,1=MAC地址,2=用户ID,3=用户Email,4=用户手机号 ,5=用户身份证号,6=用户纸质订单协议号
	private String identityType;
	private String identityId;//标识ID
	private String callbackUrl;//后台回调URL
	private String fcallbackUrl;//前台回调URL
	private String workOrderNo;//业务订单号
	
	 

	public String getBankCardCode() {
		return bankCardCode;
	}

	public void setBankCardCode(String bankCardCode) {
		this.bankCardCode = bankCardCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getInsideAccountNo() {
		return insideAccountNo;
	}

	public String getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public String getOwnerLoginName() {
		return ownerLoginName;
	}

	public void setOwnerLoginName(String ownerLoginName) {
		this.ownerLoginName = ownerLoginName;
	}

	public String getAccountNoType() {
		return accountNoType;
	}

	public void setAccountNoType(String accountNoType) {
		this.accountNoType = accountNoType;
	}

	public String getIsMainCard() {
		return isMainCard;
	}

	public void setIsMainCard(String isMainCard) {
		this.isMainCard = isMainCard;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getInsideAccountName() {
		return insideAccountName;
	}

	public void setInsideAccountName(String insideAccountName) {
		this.insideAccountName = insideAccountName;
	}
	 
	 
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setInsideAccountNo(String insideAccountNo) {
		this.insideAccountNo = insideAccountNo;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getPayPasswordNew() {
		return payPasswordNew;
	}

	public void setPayPasswordNew(String payPasswordNew) {
		this.payPasswordNew = payPasswordNew;
	}

	public String getPayPasswordComfirm() {
		return payPasswordComfirm;
	}

	public void setPayPasswordComfirm(String payPasswordComfirm) {
		this.payPasswordComfirm = payPasswordComfirm;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getIsOperMess() {
		return isOperMess;
	}

	public void setIsOperMess(String isOperMess) {
		this.isOperMess = isOperMess;
	}

	public String getSmsCheckCode() {
		return smsCheckCode;
	}

	public void setSmsCheckCode(String smsCheckCode) {
		this.smsCheckCode = smsCheckCode;
	}

	public String getImgCheckCode() {
		return imgCheckCode;
	}

	public void setImgCheckCode(String imgCheckCode) {
		this.imgCheckCode = imgCheckCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBankcardNo() {
		return bankcardNo;
	}

	public void setBankcardNo(String bankcardNo) {
		this.bankcardNo = bankcardNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public String getBranchBankProvince() {
		return branchBankProvince;
	}

	public void setBranchBankProvince(String branchBankProvince) {
		this.branchBankProvince = branchBankProvince;
	}

	public String getBranchBankCity() {
		return branchBankCity;
	}

	public void setBranchBankCity(String branchBankCity) {
		this.branchBankCity = branchBankCity;
	}

	public String getIdcardType() {
		return idcardType;
	}

	public void setIdcardType(String idcardType) {
		this.idcardType = idcardType;
	}

	public String getIdcardNo() {
		return idcardNo;
	}

	public void setIdcardNo(String idcardNo) {
		this.idcardNo = idcardNo;
	}

	public String getProductCatalog() {
		return productCatalog;
	}

	public void setProductCatalog(String productCatalog) {
		this.productCatalog = productCatalog;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getIdentityType() {
		return identityType;
	}

	public void setIdentityType(String identityType) {
		this.identityType = identityType;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getFcallbackUrl() {
		return fcallbackUrl;
	}

	public void setFcallbackUrl(String fcallbackUrl) {
		this.fcallbackUrl = fcallbackUrl;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public String getCollectMoneyAccountNo() {
		return collectMoneyAccountNo;
	}

	public void setCollectMoneyAccountNo(String collectMoneyAccountNo) {
		this.collectMoneyAccountNo = collectMoneyAccountNo;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getServiceProviderCode() {
		return serviceProviderCode;
	}

	public void setServiceProviderCode(String serviceProviderCode) {
		this.serviceProviderCode = serviceProviderCode;
	}

	public String getClentType() {
		return clentType;
	}

	public void setClentType(String clentType) {
		this.clentType = clentType;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getUsableBalance() {
		return usableBalance;
	}

	public void setUsableBalance(String usableBalance) {
		this.usableBalance = usableBalance;
	}


}
