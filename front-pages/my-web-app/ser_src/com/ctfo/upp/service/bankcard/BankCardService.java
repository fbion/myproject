package com.ctfo.upp.service.bankcard;

import java.util.List;

import com.ctfo.csm.annotations.AnnotationName;
import com.ctfo.upp.excelbeans.BankCardExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;


/**
* 类描述：  账户管理-银行卡查询 service
* @author：lipeng01  
* @date：2014-12-9 下午3:00:00    
* @version 1.0
* @since JDK1.6
*/ 
@AnnotationName(name = "账户管理-银行卡查询")
public interface BankCardService{
	/**
	 * 银行卡信息查询
	 * @param requestParam 查询条件
	 * @return PaginationResult
	 * 		返回对象
	 * @throws UPPException
	 * 		
	 */
	@AnnotationName(name = "银行卡信息查询")
	public PaginationResult<?> queryBankCardByPage(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 按条件导出银行卡信息
	 * @param requestParam
	 * @return List<BankCardExcel>
	 * @throws UPPException
	 */
	@AnnotationName(name = "导出银行卡信息")
	public List<BankCardExcel> exportExcel(DynamicSqlParameter requestParam) throws UPPException;
	
}
