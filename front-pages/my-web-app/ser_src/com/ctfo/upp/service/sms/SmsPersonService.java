package com.ctfo.upp.service.sms;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.file.bean.DynamicMongoParameter;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.sms.bean.SmsPerson;

/**
 * 短信模板人员接口
 * 
 * @author lipeng
 */
public interface SmsPersonService {

	/**
	 * 增加短信模板设置
	 */
	@AnnotationName(name = "增加短信模板人员设置")
	public void add(Object model) throws UPPException;

	/**
	 * 修改短信模板设置
	 */
	@AnnotationName(name = "修改短信模板人员设置")
	public void update(Object model) throws UPPException;

	/**
	 * 删除短信模板人员设置
	 */
	@AnnotationName(name = "删除短信模板人员设置")
	public boolean delete(String id) throws UPPException;

	/**
	 * 根据templateCode查询短信模板配置
	 */
	@AnnotationName(name = "根据ID查询短信模板配置")
	public SmsPerson queryById(String uuid) throws UPPException;

	/**
	 * 条件查询短信模板配置集合
	 */
	@AnnotationName(name = "条件查询短信模板配置集合")
	public PaginationResult<SmsPerson> query(DynamicMongoParameter requestParam) throws UPPException;

/*	
	*//**
	 * 下发短信
	 * @param mobileNum
	 * @param templetNo
	 * @param contents
	 * @throws UPPException
	 *//*
	public void sendShortMess(String mobileNum, String templetNo, List<String> contents)
			throws UPPException;

	public void sendShortMessByAccountNo(String accountNo, String templetNo,
			List<String> contents) throws UPPException;*/

}
