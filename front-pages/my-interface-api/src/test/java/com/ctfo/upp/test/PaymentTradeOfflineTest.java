package com.ctfo.upp.test;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.ctfo.upp.http.HttpUtils;

public class PaymentTradeOfflineTest extends TestBase {

	@Test
	public void createApply() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("accountNo", "MA1428618381831298");

			JSONObject jsonMap = JSONObject.fromObject(mpParams);
//			String json = jsonMap.toString();
			String json = "{\"amountArriveTime\":\"1433224915000\",\"applyId\":\"\",\"applyName\":\"tyzfpt\",\"applyStatus\":\"4100\",\"applyTime\":\"1433224929253\",\"approvalPersonId\":\"\",\"approvalPersonName\":\"tyzfpt\",\"description\":\"1\",\"fundsConfirmId\":\"\",\"fundsConfirmTime\":\"\",\"id\":\"\",\"insideAccountNo\":\"MA1433152242903907\",\"orderId\":\"\",\"payType\":\"4200\",\"payeeName\":\"1\",\"payeeNo\":\"1\",\"remitterBankCode\":\"CMBC\",\"remitterBankcardNo\":\"1\",\"remitterId\":\"\",\"remitterName\":\"1\",\"stepNo\":\"4304\",\"storeId\":\"MA1433152242903907\",\"tradeAmount\":\"1\",\"tradeAmountUpper\":\"壹分整\",\"tradeExternalNo\":\"\",\"tradeType\":\"4001\",\"voucherDesc\":\"\",\"voucherFileName\":\"http://file.sinoiov.net/upp/photo/14332249276438418.jpg\",\"voucherType\":\"\"}";
			String url = UPP_URL + "/paymentTradeApply/createApply";

			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);

			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getVoucherByApplyId() {
		try {
			Map<String, String> mpParams = new HashMap<String, String>();
			mpParams.put("applyId", "6266696e-572c-4d42-9e54-ee0f2e0b0406");
			
			JSONObject jsonMap = JSONObject.fromObject(mpParams);
			String json = jsonMap.toString();
			
			String url = UPP_URL + "/paymentTradeApply/getVoucherByApplyId";
			
			json = HttpUtils.sendRequest(json, url, chptPrivateKey, uppPublicKey, chptCode);
			
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
