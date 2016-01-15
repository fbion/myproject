package com.ctfo.upp.service.paymenttrade;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.payment.dao.beans.PaymentTradeExampleExtended;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.AbstractService;
import com.ctfo.upp.util.Converter;
import com.ctfo.upp.util.DefaultConfig;
import com.sinoiov.upp.service.dto.TradeDto;

@Service("paymentTradeService")
public class PaymentTradeServiceImpl extends AbstractService implements PaymentTradeService {
	
	private static Log logger = LogFactory.getLog(PaymentTradeServiceImpl.class);
	//获取资金账户表manager
//	private IPaymentTradeManager manager = null;
//	private IPaymentTradeManager getManager() {
//		if (this.manager == null) {
//			manager = (IPaymentTradeManager) ServiceFactory.getFactory().getService(IPaymentTradeManager.class);
//		}
//		return this.manager;
//	}
	@Override
	public PaginationResult<?> queryPaymentTradeByPage(
			DynamicSqlParameter requestParam) throws UPPException {	
		try {
			PaymentTradeExampleExtended ptEE = new PaymentTradeExampleExtended();
			Converter.paramToExampleExtended(requestParam, ptEE);
//			return getManager().queryPaymentTradeByPage(ptEE);
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_PAYMENT_TRADE_BY_PAGE_PARAM: " + JSONObject.fromObject(requestParam).toString());
			String json = super.sendRequest(JSONObject.fromObject(requestParam)
					.toString(), DefaultConfig
					.getValue("UPP_QUERY_PAYMENT_TRADE_BY_PAGE"));
			logger.info("UPP_HTTP_INTERFACE_UPP_QUERY_PAYMENT_TRADE_BY_PAGE: " + json);
			super.getPaginationResult(json, TradeDto.class);
			return super.getPaginationResult(json, TradeDto.class);
//			if (dataObj != null) {
//				return (PaginationResult)JSONObject.toBean(dataObj, PaginationResult.class);
//			}
			
		} catch (Exception e) {
			throw new UPPException(e.getMessage());
		}
	}

}
