package com.ctfo.upp.test.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctfo.upp.http.HttpUtils;
import com.sinoiov.upp.service.dto.OrderDto;

public class PayTest extends TestBase{
	
	@Test
	public void freezeBalance() {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("accountNo", "MA1423668758146640");
			map.put("orderAmount", "0.01");
			map.put("workOrderNo", "fab3yyyyyy99");

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
			map.put("accountNo", "MA1437577261212911");
			map.put("orderAmount", "1.00");
			map.put("workOrderNo", "A_rc20150801185416689");
			map.put("tradeExternalNo", "BN20150801185502541561");

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
	public void batchRechargeToUserAccount() {
		try {
//			String json = "{\"data\":[{\"callbackUrl\":\"http://192.168.15.224:8082/gatewayService/callbackForBatchRecharge.action\","
//					+ "\"collectMoneyAccountNo\":\"MA1429693938230813\",\"fcallbackUrl\":\"test.sinoiov.com\","
//					+ "\"orderAmount\":\"0.01\",\"productCatalog\":\"11\",\"productName\":\"油卡充值\",\"remarks\":\"test batchrechargeToUserAccount\","
//					+ "\"storeCode\":\"201501201602491637563\",\"userId\":\"2333\","
//					+ "\"workOrderNo\":\"122555599995555522\"}]}";
			//{"data":[{"accountNo":"","callbackUrl":"http://pay.sinoiov3.net/gatewayService/callbackForBatchRecharge.action","clentId":"","clentType":"","collectMoneyAccountNo":"MA1436085972181830","collectMoneyUserId":"","createTime":"","fcallbackUrl":"","id":"","identityId":"","identityType":"","orderAmount":"1.0","orderNo":"","orderStatus":"","orderSubject":"","orderType":"","ownerUserNo":"150700478778","payChannel":"","payConfirmDate":"","productCatalog":"12","productDesc":"","productName":"充值","remarks":"充值","serviceProviderCode":"","storeCode":"201502111739308719358","tradeExternalNo":"dqwqwq1233467898766544","userId":"6907eef5-677f-4d16-b070-e5af06e10555","userIp":"","userUa":"","workOrderNo":"RB2015080899877"}]}
				
			List<MyOrder> orderDtos = new ArrayList<MyOrder>();
			MyOrder dto = new MyOrder();
			dto.setCallbackUrl("http://pay.sinoiov3.net/gatewayService/callbackForBatchRecharge.action");
			dto.setAccountNo("MA1436085972181830");
			dto.setCollectMoneyAccountNo("MA1436085972181830");
			dto.setOrderAmount("1.0");
			dto.setProductCatalog("12");
			dto.setProductName("充值");
			dto.setRemarks("充值");
			dto.setStoreCode(chptCode);
			dto.setUserId("6907eef5-677f-4d16-b070-e5af06e10555");
			dto.setWorkOrderNo("RB201508089989999");
			dto.setOwnerUserNo("150700478778");
			dto.setTradeExternalNo("dqwqwq123346789876659999");
			orderDtos.add(dto);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", orderDtos);
			
			JSONObject jsonMap = JSONObject.fromObject(map);
			String json = jsonMap.toString();
			
			System.out.println("==="+json);

			String url = UPP_URL + "/paymentTrade/batchRechargeToUserAccount";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println("result: " + json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
