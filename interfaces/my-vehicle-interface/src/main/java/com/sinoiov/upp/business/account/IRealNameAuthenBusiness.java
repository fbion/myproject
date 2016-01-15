package com.sinoiov.upp.business.account;

import com.ctfo.account.dao.beans.AuthenApplyInfo;
import com.ctfo.upp.exception.UPPException;

public interface IRealNameAuthenBusiness {
	
	/***
	 * 提交实名认证信息
	 * 
	 * @param authenApplyInfo
	 *            实名认证实体
	 * @return
	 * @throws UPPException
	 */
	public AuthenApplyInfo addAuthenApplyInfo(AuthenApplyInfo authenApplyInfo) throws UPPException;

	/***
	 * 修改实名认证申请信息，如果状态为最终状态，则发给第三方支付进行认证
	 * 
	 * @param authenApplyInfo
	 * @throws UPPException
	 */
	public String modifyAuthenApplyInfo(AuthenApplyInfo authenApplyInfo) throws UPPException;

	/***
	 * 查询实名认证状态
	 * 
	 * @param idCard
	 *            身份证号
	 * @return
	 * @throws UPPException
	 */
	public String queryAuthenState(String idCard) throws UPPException;

}
