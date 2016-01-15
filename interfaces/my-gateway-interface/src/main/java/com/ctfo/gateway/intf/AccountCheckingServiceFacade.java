package com.ctfo.gateway.intf;

import com.ctfo.gateway.bean.base.RequestBean;
import com.ctfo.gateway.bean.base.ResponseBean;
import com.ctfo.upp.exception.UPPException;

public interface AccountCheckingServiceFacade {
	/**
	 * 对账，账户同步
	 * @param CheckAccountBeanRequestYB
	 * @return CheckAccountBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean checkAccount(RequestBean requestBean) throws UPPException;
	/**
	 * 手工通知第三方
	 * @param FireClientNoticeRequestYB
	 * @return FireClientNoticeBeanResponseYB
	 * @throws UPPException
	 */
	public ResponseBean fireClientNotice(RequestBean requestBean) throws UPPException;
	/**
	 * 查询失败的通知记录
	 * @param requestBean
	 * @return
	 * @throws UPPException
	 */
	public ResponseBean queryFailNoticeRecords(RequestBean requestBean) throws UPPException;
}
