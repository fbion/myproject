package com.ctfo.sinoiov.mobile.webapi.base.intf;

/**
 * 手机客户端接口调用公共接口
 * 
 * @author wangpeng
 */
public interface ICallService {

	/**
	 * 
	 * @param args
	 *            客户端上传参数json
	 * @param obj
	 *            文件名和文件
	 * @return
	 */
	public String call(String args, Object... obj);

}
