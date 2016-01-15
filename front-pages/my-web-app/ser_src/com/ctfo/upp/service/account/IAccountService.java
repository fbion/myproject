package com.ctfo.upp.service.account;

import java.util.List;
import java.util.Map;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.upp.bean.UnfreezeAmountDto;
import com.ctfo.upp.dto.UPPAccountDto;
import com.ctfo.upp.dto.UPPOrderRechargeDto;
import com.ctfo.upp.excelbeans.ManageAccountExcel;
import com.ctfo.upp.excelbeans.ZJAccountExcel;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.sinoiov.upp.service.dto.AccountDto;

/***
* 类描述：账户管理-Service
* @author：liuguozhong  
* @date：2015年1月16日下午2:30:26 
* @version 1.0
* @since JDK1.6
*/ 
public interface IAccountService {
	/**
	 * 检查开户传参数非空校验
	 * @param uppAccountDto
	 * @return
	 */
	public PaginationResult<?> checkOpenAccount(UPPAccountDto uppAccountDto)throws UPPException;
	/***
	 * 中交账户-开户
	 * @param UPPAccountDto 开户实体
	 * @return 成功返回 true,失败返回false
	 * @throws UPPException
	 */
	public String createAccount(UPPAccountDto uppAccountDto) throws UPPException;
	/***
	 * 中交账户充值
	 * @param orderDto
	 * @return
	 * @throws UPPException
	 */
	public Map<String, Object> recharge(UPPOrderRechargeDto orderDto) throws UPPException;
	/**
	 * 分页获取全部普通账户信息
	 * @return
	 * @throws UPPUPPException
	 */
	public PaginationResult<Account> queryAccountByPage(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 根据ID查询详细
	 * @param id
	 * @return
	 * @throws UPPException
	 */
	public AccountDto getAccountById(String id)throws UPPException;
	/**
	 * 注销账户
	 * @param accountNo
	 * @throws UPPException
	 */
	public void revokeAccount(String accountNo) throws UPPException;
	/**
	 * 锁定账户
	 * @param accountNo
	 * @throws UPPException
	 */
	public void lockAccount(String accountNo) throws UPPException;
	/**
	 * 解锁账户
	 * @param accountNo
	 * @throws UPPException
	 */
	public void DelockAccount(String accountNo) throws UPPException;
	/**
	 * 重置密码
	 * @param insideAccountNo
	 * @param newPassword
	 * @throws UPPException
	 */
	public boolean resetPassword(String insideAccountNo,String newPassword) throws UPPException;
	/**
	 * 分页获取全部中交账户信息
	 * @param accountNo
	 * @throws UPPException
	 */
	public PaginationResult<AccountDto> queryZJAccount(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 通过条件导出普通账户列表Excel
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	public List<ManageAccountExcel> exportExcel(DynamicSqlParameter requestParam) throws UPPException;
	/***
	 * 通过条件导出中交账户列表Excel(旧版)
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	public List<ZJAccountExcel> exportZJExcel(DynamicSqlParameter requestParam) throws UPPException;
	/**
	 * 按条件导出账户列表(新版)
	 * @param requestParam
	 * @throws UPPException
	 */
	public void exportExcelNew(DynamicSqlParameter requestParam) throws UPPException; 
	/**
	 * 查询账户余额
	 * @param type  中交  或者  普通账户
	 * @return
	 * @throws UPPException
	 */
	public Long accountBalance(String type)throws UPPException;
	
	
	/**
	 * 重置安全问题
	 * @throws UPPException
	 */
	public boolean resetSecurityProblem(String accountNo) throws UPPException;
	
	/**
	 * 查询是否设置安全问题
	 * @param accountNo
	 * @return
	 * @throws UPPException
	 */
	public boolean hasSecurityQuestion(String accountNo) throws UPPException;
	
	/**
	 * 修改手机号
	 * @param accountNo
	 * @param mobileNo
	 * @return
	 * @throws UPPException
	 */
	public String modifyMobile(String accountNo, String mobileNo) throws UPPException;
	
	/**
	 * 解冻账户金额
	 * @param dto
	 * @return
	 * @throws UPPException
	 */
	public String unfreezeAmount(UnfreezeAmountDto dto) throws UPPException;
	
	/**
	 * 按条件查询普通账户总余额
	 * @param requestParam
	 * @return
	 * @throws UPPException
	 */
	public double getTotalCommonCountBalance(DynamicSqlParameter requestParam) throws UPPException;
}
