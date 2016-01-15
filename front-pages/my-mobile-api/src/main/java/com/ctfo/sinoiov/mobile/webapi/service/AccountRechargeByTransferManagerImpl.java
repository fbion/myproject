package com.ctfo.sinoiov.mobile.webapi.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.ctfo.sinoiov.mobile.webapi.base.intf.IJsonService;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Body;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Head;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Parameter;
import com.ctfo.sinoiov.mobile.webapi.bean.request.AccountRechargeByTransferReq;
import com.ctfo.sinoiov.mobile.webapi.bean.response.AccountRechargeByTransferRsp;
import com.ctfo.sinoiov.mobile.webapi.bean.vo.UppResult;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.Common;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.MoneyError;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayError;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayeeRrror;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.PayerError;
import com.ctfo.sinoiov.mobile.webapi.util.ErrorMsgConstants.UserError;
import com.ctfo.sinoiov.mobile.webapi.util.FileUploadImage;
import com.ctfo.sinoiov.mobile.webapi.util.PayConstants;
import com.ctfo.upp.http.HttpUtils;
import com.ctfo.util.DateUtil;

/**
 * 银行转账进行账户充值
 * 
 * @author dxs
 */
@Service("accountRechargeByTransferManagerImpl")
public class AccountRechargeByTransferManagerImpl implements IJsonService {
	private static final Log logger = LogFactory.getLog(AccountRechargeByTransferManagerImpl.class);

	@Override
	public Body execute(Parameter request, Object... obj) throws ClientException {
		AccountRechargeByTransferRsp rsp = new AccountRechargeByTransferRsp();
		AccountRechargeByTransferReq req = null;

		try {
			req = (AccountRechargeByTransferReq) request.getBody();
			logger.info(String.format("账号充值申请服务", JSONObject.fromObject(req)));
			logger.info(String.format("上传凭证图片操作"));

			Map<String, Object> params = new HashMap<String, Object>();

			String picUrl = this.uploadImage(params, (File[]) obj[0], (String[]) obj[1]);

			// 清空参数重新赋值
			// 凭证图片地址
			params.put("voucherFileName", picUrl);
			// 申请人标识(统一认证UUID)
			params.put("applyId", req.getUserId());
			// 付款人标识(统一认证UUID)
			req.setPayer(req.getUserId());
			params.put("remitterId", req.getPayer());
			// 付款人姓名
			params.put("remitterName", req.getPayerName());
			// 付款人银行卡号或账号
			params.put("remitterBankcardNo", req.getPayAccount());
			// 交易金额（小写）
			params.put("tradeAmount", req.getRemittance());
			// 交易类型,1:充值，2：扣款
			params.put("tradeType", "1");
			// 收款人账号
			params.put("payeeNo", req.getPayee());
			// 收款人名称
			params.put("payeeName", req.getPayeeName());
			// 申请时间
			params.put("applyTime", req.getPayTime());
			// 资金确认时间
			params.put("amountArriveTime", DateUtil.getCurrentUtcMsTime().toString());
			params.put("insideAccountNo", req.getInsideAccountNo());
			params.put("approvalPersonName", req.getPayerName());

			UppResult uppResult = this.callUPP(params, "UPP_ACCOUNT_RECHARGE_BY_TRANSFER");
			if (uppResult.getResult().equals(UppResult.FAILURE)) {
				throw new ClientException(PayError.E240001, uppResult.getError());
			}
			rsp.setAttachId(picUrl);
			rsp.setResult("0");
		} catch (ClientException e) {
			logger.error(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			rsp.setResult("1");
			logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.error("账户充值失败:" + e);
			logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			throw new ClientException("资金账户充值失败,userId=" + req.getUserId(), e);
		}

		return rsp;
	}

	@Override
	public void validate(Parameter request) throws ClientException {
		if (request == null || request.getBody() == null) {
			throw new ClientException("E120001", UserError.E120001);
		}
		AccountRechargeByTransferReq req = (AccountRechargeByTransferReq) request.getBody();

		if (StringUtils.isBlank(req.getUserId())) {
			throw new ClientException("E240009", PayError.E240009);
		}
		if (StringUtils.isBlank(req.getPayerName())) {
			throw new ClientException("E170001", PayerError.E170001);
		}
		if (StringUtils.isBlank(req.getPayAccount())) {
			throw new ClientException("E160002", MoneyError.E160002);
		}
		if (StringUtils.isBlank(req.getPayee()) || StringUtils.isBlank(req.getPayeeName())) {
			throw new ClientException("E190001", PayeeRrror.E190001);
		}
		if (StringUtils.isBlank(req.getRemittance())) {
			throw new ClientException("E160002", MoneyError.E160002);
		}
	}

	@Override
	public Parameter convertParameter(String request) {
		try {
			Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
			classMap.put("head", Head.class);
			classMap.put("body", AccountRechargeByTransferReq.class);
			Parameter param = (Parameter) JSONObject.toBean(JSONObject.fromObject(request), Parameter.class, classMap);
			return param;
		} catch (Exception e) {
			throw new ClientException("E000005", Common.E000005);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String uploadImage(Map<String, Object> params, File[] files, String[] fileNames) throws Exception {
		try {
			logger.info("开始上传文件");
			File[] newFiles = new File[files.length];
			String webSite = PayConstants.getConfigValue("UPP_INTERFACE_URL");
			String methodUrlValue = PayConstants.getConfigValue("UPP_UPLOAD_PIC");
			Map map = new HashMap<String, String>();
			map.putAll(params);

			for (int i = 0; i < files.length; i++) {
				int suxInx = fileNames[i].lastIndexOf(".");
				String suxName = "jpg";
				if (suxInx != -1) {
					suxName = fileNames[i].substring(suxInx);
				}
				int filePathInx = files[i].getPath().lastIndexOf(File.separatorChar);
				String filePath = files[i].getPath().substring(0, filePathInx);
				String newFileStr = filePath + File.separatorChar + UUID.randomUUID().toString() + suxName;
				newFiles[i] = FileUploadImage.FileCopy(files[i].getPath(), newFileStr);
			}

			String picUrl = FileUploadImage.upload(webSite + methodUrlValue, map, newFiles);
			logger.info(String.format("上传文件结束，返回结果：%s", picUrl));
			return picUrl;
		} catch (Exception e) {
			logger.info(String.format("上传文件异常"), e);
			throw new Exception("上传文件异常", e);
		}
	}

	private UppResult callUPP(Object param, String methodUrlKey) throws Exception {
		UppResult uppResult = null;
		try {
			String methodUrlValue = PayConstants.getConfigValue(methodUrlKey);
			String requestJson = JSONObject.fromObject(param).toString();
			// 发送POST消息(明文的数据在下面的方法中做了加密，实际POST发送给支付平台是加密后的数据)
			String returnJson = HttpUtils.sendRequest(requestJson, 
					PayConstants.getConfigValue("UPP_INTERFACE_URL") + methodUrlValue,
					PayConstants.getPrivateKey(PayConstants.getConfigValue("MOBILE_API_MERCHANT_CODE")), 
					PayConstants.getPublicKey(PayConstants.getConfigValue("INTERFACE_MERCHANT_CODE")), 
					PayConstants.getConfigValue("MOBILE_API_MERCHANT_CODE"));
			uppResult = (UppResult) JSONObject.toBean(JSONObject.fromObject(returnJson), UppResult.class);
		} catch (Exception e) {
			logger.error("调用支付中接口出错：", e);
			throw e;
		}
		return uppResult;
	}

}