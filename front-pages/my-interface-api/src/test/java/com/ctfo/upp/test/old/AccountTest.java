package com.ctfo.upp.test.old;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctfo.upp.http.HttpUtils;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.root.utils.MD5Util;

public class AccountTest extends TestBase {

	@Test
	public void createAccount() {
		try {
			// TODO 能不能删除或修改已主册的手机号
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("mobileNo", "13681157875");
			mpParas.put("ownerLoginName", "mytest-9839");
			mpParas.put("ownerUserId", "ssdkf-77999-5384");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();

			String url = UPP_URL + "/account/createAccount";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void createAccountSmsCode() {
		// TODO
	}

	/**
	 * 根据账号查询账户 queryAccountInfo
	 */
	@Test
	public void getAccountByAccountNo() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1423575567469620");
			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();
			String url = UPP_URL + "/account/queryAccountInfo";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void getAccountByUserId() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();

			mpParas.put("userId", "022d1da3-6bd6-4ce2-9921-74eaf53aec65");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();
			String url = UPP_URL + "/account/queryAccountInfoByUserId";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void queryAccountDetailList() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("insideAccountNo", "MA1423460374157329");

			DynamicSqlParameter parameter = new DynamicSqlParameter();
			parameter.setEqual(mpParams);
		
			JSONObject jsonMap = JSONObject.fromObject(parameter);
			String json = jsonMap.toString();

			String url = UPP_URL + "/account/queryAccountDetailList";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println("result: " + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryAccountList() {
		try {
			DynamicSqlParameter parameter = new DynamicSqlParameter();
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("insideAccountNo", "MA1423460374157329");
			parameter.setEqual(mpParas);
			JSONObject jsonMap = JSONObject.fromObject(parameter);
			String json = jsonMap.toString();
			String url = UPP_URL + "/account/queryAccountList";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * queryPayOrderInfo
	 */
	@Test
	public void queryPayOrderInfoByWorkOrderNo() {
		try {
			// TODO 成功，只是没有数据
			Map<String, Object> mpParas = new HashMap<String, Object>();
			mpParas.put("workOrderNo", "RB20150521140133370");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();

			String url = UPP_URL + "/account/queryPayOrderInfo";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void revokeAccount() {
		// TODO
	}

	@Test
	public void setPayPassword() {
		try {
			// TODO 参数不确定
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1423460374157329");
			mpParas.put("mobileNo", "18500851753");
			mpParas.put("smsCode", "abcd1234");
			mpParas.put("MD5", MD5Util.getEncryptedPwd("abcd1234"));

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();
			String url = UPP_URL + "/account/setPayPassword";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * queryAccountHistoryById
	 */
	@Test
	public void getAccountDetailById() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("id", "0d881075-8560-4fe0-8a1e-011a5b7d1f9a");
			
			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();
			String url = UPP_URL + "/account/queryAccountHistoryById";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Test
	public void getSmsCode() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1423460374157329");
			mpParas.put("mobileNo", "13681157875");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();
			String url = UPP_URL + "/account/getSmsCode";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void isPayPassword() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1423460374157329");
			mpParas.put("MD5", MD5Util.getEncryptedPwd("abc5d1234"));
			mpParas.put("smsCode", "260653");
			mpParas.put("mobileNo", "13681157875");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();

			String url = UPP_URL + "/account/isPayPassword";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void isSetPayPassword() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1423460374157329");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();

			String url = UPP_URL + "/account/isSetPayPassword";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void lockAccount() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1423460374157329");
			mpParas.put("lockReasons", "testtest");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();

			String url = UPP_URL + "/account/lockAccount";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void unlockAccount() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1426130117585200");
			
			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();

			String url = UPP_URL + "/account/unlockAccount";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modifyMobileNo() {
		try {
			//1.验证短信验证码，支付密码,获取 ticket
			Map<String, Object> paras = new HashMap<String, Object>();
			paras.put("accountNo", "MA1423460374157329");
			paras.put("mobileNo", "13681157875");
			paras.put("payPassword", MD5Util.getEncryptedPwd("abcd1234"));
			paras.put("smsCode", "757264");
			JSONObject jsonMap1 = JSONObject.fromObject(paras);
			String json = jsonMap1.toString();
			String url = UPP_URL + "/accountSafety/validate";
			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);
			JSONObject tem = JSONObject.fromObject(json);	
			String tiket = JSONObject.fromObject(tem.getString("data")).getString("data");
			
			//2.验证安全票据，修改手机号码
			Map<String, Object> mpParas = new HashMap<String, Object>();
			mpParas.put("accountNo", "MA1423460374157329");
			mpParas.put("mobileNo", "13681157875");
			mpParas.put("securityTicket", tiket);
			mpParas.put("smsCode", "757264");			
			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			json = jsonMap.toString();			
			url = UPP_URL + "/account/modifyMobileNo";		
			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println("result: " + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * editAccountHistory
	 */
	@Test
	public void modifyOrderRemark() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("remarks", "88888888888888");
			mpParas.put("id", "70ceb9d9-0feb-465b-a197-527bfa5009ce");//订单的ID
			
			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();
			String url = UPP_URL + "/account/modifyOrderRemarkByOrderId";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modifyPayPassword() {
		try {

			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1423460374157329");
			mpParas.put("oldMD5", MD5Util.getEncryptedPwd("2414691"));
			mpParas.put("newMD5", MD5Util.getEncryptedPwd("123456"));
			mpParas.put("smsCode", "757264");
			mpParas.put("mobileNo", "13681157875");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);
			String json = jsonMap.toString();
			String url = UPP_URL + "/account/modifyPayPassword";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryAccountByPage() {
		try {
			DynamicSqlParameter parameter = new DynamicSqlParameter();
			Map<String, String> mpParas = new HashMap<String, String>();
			//mpParas.put("insideAccountNo", "MA1423460374157329");
			parameter.setEqual(mpParas);
			parameter.setPage(1);
			parameter.setRows(30);
			JSONObject jsonMap = JSONObject.fromObject(parameter);
			String json = jsonMap.toString();
			String url = UPP_URL + "/account/queryAccountByPage";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据查询条件查询账户流水集合 queryAccountHistory
	 */
	@Test
	public void queryAccountDetailByPage() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("insideAccountNo", "MA1423143316478148");
			
			Map<String, Object> inMap = new HashMap<String, Object>();
			inMap.put("bookaccountType", "deduction");

			DynamicSqlParameter parameter = new DynamicSqlParameter();
			parameter.setEqual(mpParams);
			parameter.setInMap(inMap);
			parameter.setPage(1);
			parameter.setRows(10);
			parameter.setSort("desc");
			parameter.setOrder("accountTime");

			JSONObject jsonMap = JSONObject.fromObject(parameter);
			String json = jsonMap.toString();

			String url = UPP_URL + "/account/queryAccountHistory";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println("result: " + json);
			
			jsonMap = JSONObject.fromObject(json);
			
			jsonMap = JSONObject.fromObject(jsonMap.get("data"));
			
			JSONArray array = jsonMap.getJSONArray("data");
			Iterator it = array.iterator();
			while(it.hasNext()){
				System.out.println(""+it.next());
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void sumAccountBalance() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("accountNo", "MA1433830498076521");
			//mpParams.put("startTime", "1432722976566");
			//mpParams.put("endTime", "1832722976566");

			JSONObject jsonMap = JSONObject.fromObject(mpParams);
			String json = jsonMap.toString();

			String url = UPP_URL + "/account/sumAccountBalance";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println("result: " + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void getCashierDeductMoneyUrl() {
		try {
			Map<String, String> mpParas = new HashMap<String, String>();
			mpParas.put("accountNo", "MA1423460374157329");// 账户
			mpParas.put("merchantOrderNo", "YW1421346329044");// 业务订单号
			mpParas.put("merchantOrderRemark", "油卡充值");// 备注
			mpParas.put("merchantOrderAmount", "0.01");// 金额
			mpParas.put("callbackURL", "http://www.4000966666.com");// 前台回调
			mpParas.put("fcallbackURL", "http://upp.sinoiov.net/xxx");// 后台回调
			mpParas.put("userId", "14d3e933-a1e7-4d01-9733-e57f39726244");// 用户id
			mpParas.put("productCatalog", "1");// 商品类型
			mpParas.put("productName", "油卡充值");// 商品名称
			// mpParas.put("userIP", "192.168.100.64");//客户端IP
			mpParas.put("identityType", "2");// 标是类型
			mpParas.put("identityId", "14d3e933-a1e7-4d01-9733-e57f39726244");// 标示ID
			mpParas.put("clentType", "2");// 终端类型（0IMEI，1MAC，2UUID，3其他）
			mpParas.put("clentId", "14d3e933-a1e7-4d01-9733-e57f39726244");// 终端标示ID
			mpParas.put("userUa", "http://icard.sinoiov.net/images/ua.gif");// 商品UA
			mpParas.put("userLoginName", "malqtest");

			JSONObject jsonMap = JSONObject.fromObject(mpParas);

			String json = jsonMap.toString();

			// 收银台地址
			String myurl = "http://cashier.test-95155.com/accountController/cashier.action";

			String url = HttpUtils.getCashierDeductMoneyUrl(json, myurl, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(url);
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
