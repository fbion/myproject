package com.ctfo.sinoiov.mobile.webapi.util;

/**
 * @Title: 错误提示类
 * @Description:
 * @Author:fx
 * @Since:2015-1-20
 * @Version:1.1.0
 */
public class ErrorMsgConstants {
	public class Common {
		public final static String E000001 = "参数为空，请检查!";
		public final static String E000002 = "验签失败，请检查签名域!";
		public final static String E000003 = "请求的接口未定义!";
		public final static String E000004 = "解密失败!";
		public final static String E000005 = "Json格式不正确，请检查!";
		public final static String E000006 = "系统繁忙 - ERROR CODE:01";
		public final static String E000007 = "系统繁忙 - ERROR CODE:02";
		public final static String E000008 = "系统繁忙 - ERROR CODE:";
		public final static String E000009 = "加密失败!";
		public final static String E000010 = "验证码为空!";
		public final static String E000011 = "验证码错误!";
		public final static String E000012 = "发送失败!";
		public final static String E000013 = "tokenId校验失败,请重新登录!";
		public final static String E000014 = "用户未认证！";
		public final static String E000015 = "功能优化，请升级客户端！";
		public final static String E000016 = "手机号码规则不符合!";
		public final static String E000017 = "手机号码为空!";
		public final static String E000018 = "短信验证码为空!";
		public final static String E000019 = "短信内容为空!";
	}

	// 登录、退出验证
	public class Login {
		public final static String E110001 = "请求参数为NULL!";
		public final static String E110002 = "用户名为空!";
		public final static String E110003 = "用户密码为空!";
		public final static String E110004 = "令牌信息为空!";
		public final static String E110005 = "登录失败,用户名或密码错误!";
		public final static String E110006 = "用户类型为空!";
		public final static String E110007 = "PN类型为空!";
		public final static String E110008 = "用户编号为空!";
		public final static String E110009 = "用户操作失败!";
		public final static String E110010 = "用户已被禁用!";
	}

	// 相关用户操作的验证提示
	public class UserError {
		public final static String E120001 = "请求参数为NULL!";
		public final static String E120002 = "用户编码为空!";
		public final static String E120003 = "用户类型为空!";
		public final static String E120004 = "令牌信息为空!";
		public final static String E120005 = "旧密码为空!";
		public final static String E120006 = "新密码为空!";
		public final static String E120007 = "手机号码为空!";
		public final static String E120008 = "手机号码规则不符合!";
		public final static String E120009 = "原密码错误!";
		public final static String E120010 = "密码长度不足!";
		public final static String E120011 = "用户不存在!";
		public final static String E120012 = "用户操作失败!";
		public final static String E120013 = "读取数据失败!";
		public final static String E120014 = "PNID为空!";
		public final static String E120015 = "密码强度为空!";
		public final static String E120016 = "用户名或用户ID不正确";
		public final static String E120017 = "手机号不正确";
		public final static String E120018 = "身份证号不正确";
	}

	// 输入金额错误提示
	public class MoneyError {
		public final static String E160001 = "输入金额为空!";
		public final static String E160002 = "输入金额错误!";
	}

	// 输入付款人信息错误提示
	public class PayerError {
		public final static String E170001 = "付款人姓名不正确或为空!";
		public final static String E170002 = "付款人标识不正确或为空!";
	}

	// 输入付款人信息错误提示
	public class PayeeRrror {
		public final static String E190001 = "收款人不存在或错误!";
	}

	public class BillRrror {
		// 发票错误提示
		public final static String E200001 = "参数为空!";
		public final static String E200002 = "用户ID为空!";
		public final static String E200003 = "发票类型为空!";
		public final static String E200004 = "发票抬头为空!";
		public final static String E200005 = "操作失败!";
		public final static String E200006 = "记录ID为空!";
		public final static String E200007 = "金额为空!";
		public final static String E200008 = "用户名称为空!";
		public final static String E200009 = "注册手机号为空!";
		public final static String E200010 = "金额为空!";
		public final static String E200011 = "月份为空!";
		public final static String E200012 = "开票功能已优化，请升级版本";
	}

	public class PayError {
		// 支付接口错误
		public final static String E240001 = "调用支付接口异常!";
		public final static String E240002 = "短信验证码不正确!";
		public final static String E240003 = "原密码不对!";
		public final static String E240004 = "参数为空!";
		public final static String E240005 = "账户号码为空!";
		public final static String E240006 = "支付密码为空!";
		public final static String E240007 = "旧支付密码为空!";
		public final static String E240008 = "新支付密码为空!";
		public final static String E240009 = "用户ID为空!";
		public final static String E240010 = "业务订单号为空!";
		public final static String E240011 = "前台回调地址为空!";
		public final static String E240012 = "商品类型为空!";
		public final static String E240013 = "商品名称为空!";
		public final static String E240014 = "标识类型为空!";
		public final static String E240015 = "标识ID为空!";
		public final static String E240016 = "终端类型为空!";
		public final static String E240017 = "终端标示ID为空!";
		public final static String E240018 = "客户端IP为空!";
		public final static String E240019 = "业务订单号为空!";
		public final static String E240020 = "业务订单号为空!";
		public final static String E240021 = "业务订单号为空!";
		public final static String E240022 = "业务订单号为空!";
		public final static String E240023 = "支付渠道为空!";
		public final static String E240024 = "支付密码不正确，请重新输入!";
		public final static String E240025 = "SecurityTicket为空!";
	}

	public class PayCenterTradeInfo {
		public final static String E250001 = "用户ID为空!";
		public final static String E250002 = "用户账号为空!";
		public final static String E250003 = "交易ID为空!";
	}
}
