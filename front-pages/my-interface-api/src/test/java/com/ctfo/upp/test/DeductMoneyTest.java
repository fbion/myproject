package com.ctfo.upp.test;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctfo.upp.http.HttpUtils;

public class DeductMoneyTest extends TestBase {

	/**
	 * 收银台：网银扣款申请
	 */
	@Test
	public void payApplyForCashierNet() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("accountNo", "MA1421346329090228");// 账户
			mpParams.put("merchantOrderNo", "MA1421346329090228");// 业务订单号
			mpParams.put("merchantOrderRemark", "MA1421346329090228");// 备注
			mpParams.put("merchantOrderAmount", "0.01");// 金额
			mpParams.put("callbackURL", "http://www.95155.com");// 前台回调
			mpParams.put("fcallbackURL", "http://icard.sinoiov.net/xxx");// 后台回调
			mpParams.put("payType", "ACCOUNT");// 支付类型（NET，FASTPAY，ACCOUNT）
			mpParams.put("userId", "ddddddddd");// 用户id
			mpParams.put("productCatalog", "20");// 商品类型
			mpParams.put("productName", "油卡充值");// 商品名称
			mpParams.put("userIP", "192.168.100.64");// 客户端IP
			mpParams.put("identityType", "2");// 标是类型
			mpParams.put("identityId", "ddddddddd");// 标示ID
			mpParams.put("clentType", "2");// 终端类型（0IMEI，1MAC，2UUID，3其他）
			mpParams.put("clentId", "ddddddddd");// 终端标示ID
			mpParams.put("userUa", "http://icard.sinoiov.net/images/ua.gif");// 商品UA  bankCardCode

			JSONObject jsonMap = JSONObject.fromObject(mpParams);

			String json = jsonMap.toString();
			String url = UPP_URL + "/deductMoney/deductMoneyApply";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * 收银台：快捷支付扣款申请 deductMoneyApplyFast
	 */
	@Test
	public void payApplyForCashierFast() {
		// TODO

	}

	/**
	 * 收银台：账户支付扣款申请 deductMoneyCashier
	 */
	@Test
	public void payApplyForCashierAccount() {
		// TODO

	}

	/**
	 * 移动端：账户支付扣款申请 mobileDeductMoneyApply
	 */
	@Test
	public void payApplyForMobileAccount() {
		// TODO
	}

	/**
	 * 移动端：快捷支付扣款申请
	 */
	@Test
	public void payApplyForMobileFast() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("accountNo", "MA1423460374157329");// 账户
			mpParams.put("merchantOrderNo", "YW14213463290903");// 业务订单号
			mpParams.put("merchantOrderRemark", "油卡充值");// 备注
			mpParams.put("merchantOrderAmount", "1.00");// 金额
			mpParams.put("callbackURL", "http://www.95155.com");// 前台回调
			mpParams.put("fcallbackURL", "http://cc.4000966666.net/GateWayService/callback/yeepay/mobile/callback");// 后台回调
			mpParams.put("userId", "58.83.203.207");// 用户id
			mpParams.put("productCatalog", "1");// 商品类型
			mpParams.put("productName", "油卡充值");// 商品名称

			mpParams.put("identityType", "2");// 标是类型
			mpParams.put("identityId", "ea32affa-3e4-41c7-962d-9addd9d3e7fe");// 标示ID
			mpParams.put("clentType", "2");// 终端类型（0IMEI，1MAC，2UUID，3其他）
			mpParams.put("clentId", "ea32affa-3e4-41c7-962d-9addd9d3e7fe");// 终端标示ID
			mpParams.put("userUa", "http://www.95155.com/images/ua.gif");// 商品UA
			mpParams.put("userLoginName", "13681157875");

			JSONObject jsonMap = JSONObject.fromObject(mpParams);

			String json = jsonMap.toString();

			String url = UPP_URL + "/deductMoney/deductMoneyApplyFastFroMoblie";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * 扣款确认 deductMoneySure
	 */
	@Test
	public void paySure() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("accountNo", "MA1421346329090228");// 账户
			mpParams.put("orderNo", "3244444444");// 支付订单号
			mpParams.put("merchantOrderNo", "12345678");// 业务订单号
			mpParams.put("result", "1");// 结果(1成功，－1失败)
			mpParams.put("orderAmount", "0.01");// 金额

			JSONObject jsonMap = JSONObject.fromObject(mpParams);

			String json = jsonMap.toString();
			String url = UPP_URL + "/deductMoney/deductMoneySure";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * 扣款审请并确认接口 deductMoney
	 */
	@Test
	public void payApplyAndSure() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("accountNo", "MA1423219683987469");// 账户
			mpParams.put("userId", "fcc3cd9e-0511-41b0-86f8-302b4fe6d59a");
			mpParams.put("workOrderNo", "1238889599998");// 业务订单号
			mpParams.put("orderAmount", "0.01");// 金额
			mpParams.put("storeCode", chptCode);

			JSONObject jsonMap = JSONObject.fromObject(mpParams);

			String json = jsonMap.toString();
			String url = UPP_URL + "/deductMoney/deductMoney";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
