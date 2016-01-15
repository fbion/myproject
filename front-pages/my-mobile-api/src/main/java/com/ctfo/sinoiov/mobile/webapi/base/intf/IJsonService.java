package com.ctfo.sinoiov.mobile.webapi.base.intf;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Body;
import com.ctfo.sinoiov.mobile.webapi.bean.common.Parameter;
import com.ctfo.sinoiov.mobile.webapi.exception.ClientException;

/**
 * 解析公共json方法接口
 * 
 * @author wangpeng
 */
public interface IJsonService {

	/**
	 * 执行服务
	 * 
	 * @param Json对象
	 * @return
	 * @throws ClientException
	 */
	public Body execute(Parameter request, Object... obj) throws ClientException;

	/**
	 * 校验业务数据合法性
	 * 
	 * @param request
	 */
	@AnnotationName(name = "校验业务数据合法性")
	void validate(Parameter request) throws ClientException;

	/**
	 * 根据不同的command转换成不同的Body对象
	 * 
	 * @param request
	 */
	@AnnotationName(name = "转换参数类型")
	Parameter convertParameter(String request);

}
