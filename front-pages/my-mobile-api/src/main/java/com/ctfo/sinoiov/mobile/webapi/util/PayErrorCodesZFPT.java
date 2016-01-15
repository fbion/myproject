package com.ctfo.sinoiov.mobile.webapi.util;

/**
 * 支付中心返回网关的错误码
 * 
 * @author sunchuanfu
 */
public class PayErrorCodesZFPT {
	public static final String SUCCESS                            = "0000"; // 执行成功
	public static final String FAIL                               = "1111"; // 执行失败
	public static final String PARAM_NOT_STANDARD                 = "1001"; // 参数不符合规格
	public static final String BALANCE_IS_NOT_ENOUGH              = "1002"; // 余额不足
	public static final String GATEWAY_FAIL                       = "1005"; // 调用网关结果返回失败
	public static final String ACCOUNT_NOT_EXIST                  = "1100"; // 账户不存在
	public static final String ACCOUNT_STATUS_ERROR               = "1101"; // 账户状态异常
	public static final String ACCOUNT_STATUS_LOCKED              = "1102"; // 账户被锁定
	public static final String ACCOUNT_NOT_CERTIFICATED           = "1103"; // 账户未进行实名认证
	public static final String ACCOUNT_CONCURRENT_OPERATION       = "1104"; // 并发操作账户
	public static final String ACCOUNT_FROZEN_AMOUNT_INSUFFICIENT = "1105"; // 账户冻结金额不足
	public static final String ACCOUNT_USABLE_AMOUNT_INSUFFICIENT = "1106"; // 账户可用金额不足
	public static final String FREEZE_RECORD_NOT_EXIST            = "1107"; // 冻结记录不存在
	public static final String ACCOUNT_UNLOCK_FAILURE             = "1108"; // 账户解锁失败
	public static final String ACCOUNT_CREATE_R                   = "1109"; // 创建重复账号
	public static final String ACCOUNT_N                          = "1110"; // 账号错误
	public static final String ACCOUNT_SET_PWD_Y                  = "1118"; // 账户已设置过密码
	public static final String MOBILENO_N                         = "1200"; // 手机号码不正确
	public static final String ACCOUNT_MOBILENO_N                 = "1201"; // 不是账户所绑定的手机号码
	public static final String MOBILE_NO_SMSCODE                  = "1202"; // 短信验证码不正确
	public static final String OLD_MD5                            = "1203"; // 原密码不对
	public static final String USER_ID_N                          = "1204"; // 用户ID错误
	public static final String USER_LOGIN_NAME_N                  = "1205"; // 用户登录名错误
	public static final String ORDER_NO_N                         = "1301"; // 支付订单号为空
	public static final String ERROR                              = "2222"; // 执行异常
}
