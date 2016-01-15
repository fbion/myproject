package com.sinoiov.upp.aop;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.sinoiov.upp.service.exception.UPPServiceException;

/**
 * service异常和日志处理切面
 * TODO 这里的切面先屏蔽，以后整理service层
 * 
 * @author sunchuanfu
 */
@Aspect
public class ServiceAspectPocessor {
	protected final Log logger = LogFactory.getLog(getClass());

//	/**
//	 * 定义切面到com.sinoiov.upp.service包下
//	 */
//	@Pointcut(value = "execution(* com.sinoiov.upp.service..*(..))")
//	public void servicePointCut() {
//	}
//
//	private static Map<String, String> methodAndErrorMsg = new HashMap<String, String>();
//
//	static {
//		methodAndErrorMsg.put("createAccount", "开户时产生错误");
//		// TODO
//	}
//
//	@Around(value = "servicePointCut()")
//	public void around(ProceedingJoinPoint pjp) throws Exception {
//		String method = null;
//		try {
//			method = pjp.getSignature().getName();
//			pjp.proceed();
//		} catch (UPPServiceException e) {
//			logger.error(e.getLocalizedMessage(), e);
//			throw e;
//		} catch (UPPException e) {
//			logger.error(e.getLocalizedMessage(), e);
//			throw new UPPServiceException(StringUtils.isBlank(e.getErrorCode()) ? ReturnCodeDict.FAIL
//					: e.getErrorCode(), e.getLocalizedMessage(), e);
//		} catch (Exception e) {
//			logger.error(methodAndErrorMsg.get(method) + e.getLocalizedMessage(), e);
//			throw new UPPServiceException(ReturnCodeDict.FAIL, methodAndErrorMsg.get(method) + e.getLocalizedMessage(),
//					e);
//		} catch (Throwable e) {
//			logger.error(methodAndErrorMsg.get(method) + e.getLocalizedMessage(), e);
//			throw new UPPServiceException(ReturnCodeDict.FAIL, methodAndErrorMsg.get(method) + e.getLocalizedMessage(),
//					e);
//		}
//	}

}
