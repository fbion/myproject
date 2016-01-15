package com.ctfo.upp.service.sms;

import java.util.List;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.file.bean.DynamicMongoParameter;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.service.sms.bean.Sms;

/**
 * 短信模板接口
 * 
 * @author jichao
 */
public interface SmsService {

	/**
	 * 增加短信模板设置
	 */
	@AnnotationName(name = "增加短信模板设置")
	public void add(Object model) throws UPPException;

	/**
	 * 修改短信模板设置
	 */
	@AnnotationName(name = "修改短信模板设置")
	public void update(Object model) throws UPPException;

	/**
	 * 删除短信模板设置(ids格式：1，2，3)
	 */
	@AnnotationName(name = "删除短信模板设置")
	public boolean delete(String templateCodes) throws UPPException;

	/**
	 * 根据查询短信模板配置
	 */
	@AnnotationName(name = "根据ID查询短信模板配置")
	public Sms queryById(String templateCode) throws UPPException;

	/**
	 * 条件查询短信模板配置集合
	 */
	@AnnotationName(name = "条件查询短信模板配置集合")
	public PaginationResult<Sms> query(DynamicMongoParameter requestParam) throws UPPException;

	/**
	 * 短信模板是否存在
	 * @return
	 */
	@AnnotationName(name = "短信模板是否存在")
	public boolean isExists(String templateCode) throws Exception ;
	
	/**
	 * 下发短信
	 * @param mobileNum
	 * @param templetNo
	 * @param contents
	 * @throws UPPException
	 */
	public void sendShortMess(String mobileNum, String templetNo, List<String> contents)
			throws UPPException;

	public void sendShortMessByAccountNo(String accountNo, String templetNo,
			List<String> contents) throws UPPException;

}
