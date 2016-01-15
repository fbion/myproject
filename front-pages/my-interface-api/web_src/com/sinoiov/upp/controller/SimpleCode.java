package com.sinoiov.upp.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.upp.bean.PayCommonRequestParams;
import com.sinoiov.upp.service.ISimpleCodeService;

@Scope("prototype")
@Controller
@RequestMapping(value = "/simpleCode")
public class SimpleCode extends BaseController {
	@Autowired
	private ISimpleCodeService simpleCodeService;

	/**
	 * 查询码表信息接口
	 * <p>
	 * 描述：根据类型查询支付系统所设置的码表信息集合
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/queryList", produces = "text/plain;charset=UTF-8")
	public List<String> queryList(HttpServletResponse response, PayCommonRequestParams params) throws Exception {
		String paramJson = super.getParamJson(params.getData(), params.getEncryptkey(), params.getMerchantcode());
		JSONObject jsonMap = JSONObject.fromObject(paramJson);

		String type = super.getStringFromJsonMap(jsonMap, "type");
		if (StringUtils.isBlank(type))
			throw new UPPException(ReturnCodeDict.FAIL, "类型不能为空");

		List simpleCodes = simpleCodeService.queryList(type);

		PaginationResult prSimpleCodes = new PaginationResult();
		prSimpleCodes.setData(simpleCodes);

		return super.commonControllerReturn(params.getMerchantcode(), paramJson, prSimpleCodes);
	}

}
