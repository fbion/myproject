package com.ctfo.upp.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctfo.upp.http.HttpUtils;
import com.ctfo.upp.test.product.MyOrder;

public class PaymentTradeTest extends TestBase {

	@Test
	public void batchRechargeToUserAccount() {
		try {
			List<MyOrder> orderDtos = new ArrayList<MyOrder>();
			MyOrder dto = new MyOrder();
			dto.setCallbackUrl("http://pay.sinoiov3.net/gatewayService/callbackForBatchRecharge.action");
			dto.setAccountNo("MA1423460374157329");
			dto.setCollectMoneyAccountNo("MA1423460374157329");
			dto.setOrderAmount("1.1");
			dto.setProductCatalog("12");
			dto.setProductName("充值");
			dto.setRemarks("充值");
			dto.setStoreCode(chptCode);
			dto.setUserId("6907eef5-677f-4d16-b070-e5af06e10555");
			dto.setWorkOrderNo("RB201508089989990088");
			dto.setOwnerUserNo("150100434350");
			dto.setTradeExternalNo("dqwqwq123346789876659889");
			orderDtos.add(dto);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", orderDtos);
			
			JSONObject jsonMap = JSONObject.fromObject(map);
			String json = jsonMap.toString();
			
			//json = "{\"data\":["+jsonMap.toString()+"]}";
				
			
			System.out.println("==="+json);

			String url = UPP_URL + "/paymentTrade/batchRechargeToUserAccount";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println("result: " + json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void freezeBalance() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountNo", "MA1428618381831298");
			map.put("orderAmount", "0.02");
			map.put("workOrderNo", "fab3yyyyyyy");

			JSONObject jsonMap = JSONObject.fromObject(map);
			String json = jsonMap.toString();
			String url = UPP_URL + "/paymentTrade/freezeBalance";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void unFreezeBalance() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountNo", "MA1428618381831298");
			map.put("orderAmount", "0.02");
			map.put("workOrderNo", "fab3yyyyyyy");

			JSONObject jsonMap = JSONObject.fromObject(map);
			String json = jsonMap.toString();
			String url = UPP_URL + "/paymentTrade/unFreezeBalance";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void rechargeToUserAccount() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("collectMoneyAccountNo", "MA1421346329090228");
			map.put("orderAmount", "0.01");
			// map.put("storeCode", "ssdkf-77f-kkjf6");
			map.put("userId", "ssdkf-77f-kkjf6");
			map.put("workOrderNo", "34567891299");

			JSONObject jsonMap = JSONObject.fromObject(map);

			String json = jsonMap.toString();
			String url = UPP_URL + "/paymentTrade/rechargeToUserAccount";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Test
	public void transferAccounts() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountNo", "MA1423460374157329");// 账户
			// map.put("accountNo", "MA1423386493521886");//账户
			map.put("userId", "f1e6a22s-950c-4135-a298-d0a2c25b387a");
			map.put("workOrderNo", "ratest_2015253215665511");// 业务订单号
			map.put("orderAmount", "0.01");// 金额
			map.put("storeCode", chptCode);
			map.put("collectMoneyUserId", "f2e6a22p-950c-4135-a298-d0a2c25b387b");
			map.put("collectMoneyAccountNo", "MA1439023276039684");
			map.put("tradeExternalNo", "TTRF14390232760396999");

			JSONObject jsonMap = JSONObject.fromObject(map);

			String json = jsonMap.toString();
			String url = UPP_URL + "/paymentTrade/transferAccounts";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.getMessage();
		}
	}

}
