/**
 * 北京中交兴路车联网科技有限公司 2011 版权所有.
 */
package com.ctfo.sinoiov.mobile.webapi.bean.request;

import com.ctfo.sinoiov.mobile.webapi.bean.request.BaseBeanReq;

/**
 * 
 * 查询账户流水request
 * 
 * @author dxs
 */
public class QueryPayCenterTradeListReq extends BaseBeanReq {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 账户号
	 */
	private String accountNo;

	/**
	 * deduction支出; recorded收入
	 */
	private String bookAccountType;

	/**
	 * 开始页码
	 */
	private String page = "1";

	/**
	 * 每页多少
	 */
	private String rows = "10";

	/**
	 * 共计多少页
	 */
	private String pagesize = "1";

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getBookAccountType() {
		return bookAccountType;
	}

	public void setBookAccountType(String bookAccountType) {
		this.bookAccountType = bookAccountType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getPagesize() {
		return pagesize;
	}

}
