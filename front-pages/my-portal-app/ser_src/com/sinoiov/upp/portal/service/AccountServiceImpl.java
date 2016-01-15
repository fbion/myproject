package com.sinoiov.upp.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctfo.account.dao.beans.Account;
import com.ctfo.csm.uaa.dao.beans.UAAUser;
import com.ctfo.file.bean.ImageSizeBean;
import com.ctfo.upp.business.dto.UPPCashierAccountDto;
import com.ctfo.upp.dict.ReturnCodeDict;
import com.ctfo.upp.exception.UPPException;
import com.ctfo.upp.models.DynamicSqlParameter;
import com.ctfo.upp.models.PaginationResult;
import com.ctfo.upp.root.utils.AmountUtil;
import com.sinoiov.pltp.common.redis.CacheTableInfo;
import com.sinoiov.pltp.common.redis.CommonCache;
import com.sinoiov.pltp.member.model.system.supports.UserVO;
import com.sinoiov.upp.portal.controller.vo.CashierAccountDTO;
import com.sinoiov.upp.portal.util.ProtalConvertUtils;


/**
 * 账户服务实现
 * 
 * @author lipeng01
 */
@Service
public class AccountServiceImpl extends AbstractService implements AccountService {
	private static Log logger = LogFactory.getLog(AccountServiceImpl.class);
	public static final String SUCCESS = "1";
	public static final String ERROR = "-1";
	public static final String SESSION_INDEX="session-index";
	public static final String SESSION_TICKET_ID="session-ticket-id";
	public static final String SESSION_REMOTE_USER="session-remote-user";
	
	@Autowired
	private IQueryUaaUserInfo queryUaaUserInfo;

	private static ImageSizeBean imageSizeBean = new ImageSizeBean();
	static {
		imageSizeBean.setBigWidth(400);
		imageSizeBean.setBigHeight(200);
		imageSizeBean.setMaxWater(true);
		imageSizeBean.setMinWidth(110);
		imageSizeBean.setMinHeight(55);
	}

	/**
	 * wjg
	 */
	public Account findAccountInfo(Map<String, String> map) throws UPPException {
		try {

			String uri = ProtalConvertUtils.getValue("UPP_QUERY_ACCOUNT");

			String json = super.sendRequest(map, uri);

			JSONObject dataMap = JSONObject.fromObject(json);
			JSONObject jsonMap = (JSONObject)dataMap.get("data");

			// String url = jsonMap.getString("url");
			Account account = new Account();
			UPPCashierAccountDto accountDto = new UPPCashierAccountDto();
			accountDto = (UPPCashierAccountDto) JSONObject.toBean(jsonMap, UPPCashierAccountDto.class);
			String[] ignoreProperties = { "partShowIdcardNo", "isOperMess", "version", "createTime", "usableBalance", "frozenBalance", "unableTakecashBalance", "totalBalance" };
			BeanUtils.copyProperties(accountDto, account, ignoreProperties);
			account.setTotalBalance((accountDto.getTotalBalance() == null ? 0L : AmountUtil.getAmount(accountDto.getTotalBalance())));
			account.setUsableBalance(accountDto.getUsableBalance() == null ? 0L : AmountUtil.getAmount(accountDto.getUsableBalance()));
			logger.info("解密后结果:" + json);

			return account;

		} catch (Exception e) {
			logger.error("账户查询异常！", e);
			throw new UPPException("账户查询异常！", e);
		}
	}
	/**
	 * wjg
	 */
	public String quaryAccountCurrent(DynamicSqlParameter requestParam) throws UPPException {
		String uri;
		try {
			// 获取调用地址
			uri = ProtalConvertUtils.getValue("UPP_QUERY_ACCOUNT_CURRENT");
			// 将参数转换为JSON格式
			String json = super.sendRequest(requestParam, uri);
			JSONObject jsonMap = JSONObject.fromObject(json);
			JSONObject dataMap = (JSONObject)jsonMap.get("data");
			//将获取的json转换成类
			Map map=new HashMap();
			map.put("data", Map.class);
			List<Map> list=JSONArray.toList(JSONArray.fromObject(JSONObject.fromObject(dataMap.toString()).get("data").toString()), Map.class);
  			//将类的list属性冒泡排序
//			for(int i=0;i<list.size();i++) {
//				System.out.println(DateUtils.timeStamp2Date(Long.valueOf(list.get(i).get("accountTime").toString())));
//				for(int j=i+1;j<list.size();j++) {
//					Map a;
//					Long time1 = Long.valueOf(list.get(i).get("accountTime").toString());
//					Long time2 = Long.valueOf(list.get(j).get("accountTime").toString());
//					if(time1-time2<0){
//						a=list.get(i);
//						list.set(i,list.get(j));
//						list.set(j,a);
//					}
//				}
//			}
			JSONObject jso=JSONObject.fromObject(dataMap.toString());
			jso.remove("data");
			PaginationResult ps=(PaginationResult) JSONObject.toBean(jso, PaginationResult.class,map);
			ps.setData(list);
			return JSONObject.fromObject(ps).toString();
		} catch (Exception e) {
			logger.error("分页账户流水查询异常！", e);
			throw new UPPException("分页账户流水查询异常！", e);
		}
	}

	/**
	 * wjg
	 */
	public Map<String,String> judgeAccount(Map<String, String> map) throws UPPException {

		String uri;
		String accountNo = "";
		String mobileNo = "";
		try {
			// 获取调用地址
			uri = ProtalConvertUtils.getValue("UPP_QUERY_ACCOUNT_BY_OWERUSERID");
			// 将参数转换为JSON格式,执行加密调用方法
			String json = super.sendRequest(map, uri);
			// 转换返回值
			JSONObject jsonMap = JSONObject.fromObject(json);
			Map<String,String> resultmap = new HashMap<String, String>();
			if("1".equals(jsonMap.get("result").toString())){
				JSONObject dataMap = (JSONObject)jsonMap.get("data");
				if (dataMap.get("insideAccountNo") != null) {
					accountNo = dataMap.get("insideAccountNo").toString();
					mobileNo = dataMap.get("mobileNo").toString();
					resultmap.put("accountNo", accountNo);
					resultmap.put("mobileNo", mobileNo);
				}else if(ReturnCodeDict.ACCOUNT_NOT_EXIST.equals(jsonMap.get("errorcode").toString())||
						"null".equals(jsonMap.get("errorcode"))||"".equals(jsonMap.get("errorcode"))){
					accountNo=ReturnCodeDict.ACCOUNT_NOT_EXIST;
					resultmap.put("accountNo", accountNo);
				}else if(ReturnCodeDict.ACCOUNT_CREATE_R.equals(jsonMap.get("errorcode").toString())){
					accountNo=ReturnCodeDict.ACCOUNT_CREATE_R;
					resultmap.put("accountNo", accountNo);
				}
			}else if("-1".equals(jsonMap.get("result"))){
				if(ReturnCodeDict.ACCOUNT_NOT_EXIST.equals(jsonMap.get("errorcode").toString())||
						"null".equals(jsonMap.get("errorcode"))||"".equals(jsonMap.get("errorcode"))){
					accountNo=ReturnCodeDict.ACCOUNT_NOT_EXIST;
					resultmap.put("accountNo", accountNo);
				}else if(ReturnCodeDict.ACCOUNT_CREATE_R.equals(jsonMap.get("errorcode").toString())){
					accountNo=ReturnCodeDict.ACCOUNT_CREATE_R;
					resultmap.put("accountNo", accountNo);
				}
			}
			return resultmap;
		} catch (Exception e) {
			logger.error("根据用户ID查找账户信息异常！", e);
			throw new UPPException("根据用户ID查找账户信息异常！", e);
		}
	}

	/**
	 * wjg
	 */
	public String openAnAccount(Map<String, String> map) throws UPPException {
		String uri;
		try {
			String accountNo = "";
			// 获取调用地址
			uri = ProtalConvertUtils.getValue("UPP_CREATE_ACCOUNT_PASSWORD");
			// 将参数转换为JSON格式,执行加密调用方法
			String json = super.sendRequest(map, uri);
			// 转换返回值
			JSONObject jsonObject = JSONObject.fromObject(json);
			if("1".equals(jsonObject.get("result").toString())){
				JSONObject dataMap = (JSONObject)jsonObject.get("data");
				accountNo = dataMap.get("accountNo").toString();
			}
			if("-1".equals(jsonObject.get("result").toString())){
				if(ReturnCodeDict.MOBILE_NO_SMSCODE.equals(jsonObject.get("errorcode").toString())){
					accountNo = jsonObject.get("errorcode").toString();
				}else if(ReturnCodeDict.ACCOUNT_CREATE_R.equals(jsonObject.get("errorcode").toString())){
					accountNo = jsonObject.get("errorcode").toString();
				}
			}
			return accountNo;
		} catch (Exception e) {
			logger.error("根据用户ID查找账户信息异常！", e);
			throw new UPPException("根据用户ID查找账户信息异常！", e);
		}
	}

	/**
	 * wjg
	 */
	public String povringPayPassword(Map<String, String> map) throws UPPException {
		String result = "-1";
		try {
			String uri = ProtalConvertUtils.getValue("UPP_ACCOUNT_ISSETPAYPASSWORD");
			String json = super.sendRequest(map, uri);
			JSONObject objectMap = JSONObject.fromObject(json);
			String jsonResult = (String)objectMap.get("result");
			if("1".equals(jsonResult)){
				JSONObject dataMap = (JSONObject)objectMap.get("data");
				result = dataMap.get("data").toString();
			}
			logger.info("解密后结果:" + json);
			return result;
		} catch (Exception e) {
			logger.error("验证支付密码异常！", e);
			throw new UPPException("验证支付密码异常！", e);
		}
	}
	/**
	 * wjg
	 * 获取用户的用户信息
	 */
	public String getUserMessage(HttpServletRequest request) throws UPPException {

		
//		HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest() ;
		
		HttpSession session = request.getSession();
//		String ownerLoginName = request.getRemoteUser();
		String ownerLoginName = "";
		String ticket = "";
			/**************************获取ksessionId ticketID*************************************/
			String ksessionId = null;
			if(request.getCookies() != null) {
				for(Cookie c : request.getCookies()) {
					if("KSESSIONID".equalsIgnoreCase(c.getName())){
						ksessionId = c.getValue();
						session.setAttribute(SESSION_TICKET_ID, ksessionId);
						break;
					}
				}
			}
			logger.info("----------------从Cookie中取到的ksessionId为-------------------：" + ksessionId);
			
			if(ksessionId != null) {
				try {
					ticket = CommonCache.getFromRedis(CacheTableInfo.UAA_CASE_LOGIN_4TOKEN, ksessionId);
					
					String ticketOld = (String) session.getAttribute(SESSION_TICKET_ID);
					if(!(ticketOld != null && ticketOld.equalsIgnoreCase(ticket))) {
						logger.info("ticketOld与ticket不同,将使用新的ticket; ticketOld:" + ticketOld + "\t ticket:" + ticket);
						session.setAttribute(SESSION_TICKET_ID, ticket);
						session.removeAttribute(SESSION_REMOTE_USER);
						session.removeAttribute(SESSION_INDEX);
						session.removeAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
						Object o = null;
						if(ticket != null) {
							o = CommonCache.getFromRedis(CacheTableInfo.UAA_CASE_LOGIN_4TOKEN, ticket, UserVO.class);
						}
						if(o != null && o instanceof UserVO) {
							UserVO user = (UserVO)o;
							ownerLoginName = user.getLoginName();
						}else{
							logger.info("获取用户信息失败!从物流的redis里获取用户数据:" + (o==null? "空" : o.getClass()));
						}
					} 
				} catch (Exception e) {
					logger.warn("调用物流交易平台获取用户信息服务发生异常!", e);
				}
				
			}
			logger.info("-------------------从redis中取到的ticket为---------------------：" + ticket);
			
			
			/***************************************获取登录信息************************************/
			
			if(StringUtils.isBlank(ownerLoginName)){
				Object object = session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
				if(object != null){
					Assertion assertion = (Assertion)object;
					if(assertion != null && assertion.getPrincipal() != null){
						AttributePrincipal principal = assertion.getPrincipal();
						ownerLoginName = principal.toString();
					}
				}
			}
	
		
		if(StringUtils.isNotBlank(ownerLoginName)){
			session.setAttribute("loginName", ownerLoginName);
			CashierAccountDTO accountDto = null;
			accountDto = queryUaaUserInfo.getUaaUserInfo(ownerLoginName);
			if(accountDto!=null){
				session.setAttribute("ownerLoginName",ownerLoginName);
				session.setAttribute("ownerUserId",accountDto.getUserId());
				session.setAttribute("mobileNo",accountDto.getMobileNo());
				session.setAttribute("accountDto", accountDto);
			}
		}
		
		return ownerLoginName;
	}
	/**
	 * wjg
	 * 为查询条件赋值
	 */
	public DynamicSqlParameter voluationParam(HttpServletRequest request) throws UPPException {
		DynamicSqlParameter requestParam = new DynamicSqlParameter();
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		String order = request.getParameter("order");
		String sort = request.getParameter("sort");
		String insideAccountNo = request.getParameter("insideAccountNo");
		String bookaccountType = request.getParameter("bookaccountType");
		String startLong = request.getParameter("startLong");
		String endLong = request.getParameter("endLong");
		String orderStatus = request.getParameter("orderStatus");
		if(page!=null){
			requestParam.setPage(Integer.valueOf(page));
		}
		if(rows!=null){
			requestParam.setRows(Integer.valueOf(rows));
		}
		if(order!=null){
			requestParam.setOrder(order);
		}
		if(sort!=null){
			requestParam.setSort(sort);
		}
		Map<String,String> equalMap = new HashMap<String, String>();
		Map<String,Object> inMap = new HashMap<String, Object>();
		if(insideAccountNo!=null){
			equalMap.put("insideAccountNo", insideAccountNo);
		}
		if(bookaccountType!=null){
			equalMap.put("bookaccountType", bookaccountType);
		}else{
			inMap.put("bookaccountType", "deduction,recorded");
		}
		if(orderStatus!=null){
			equalMap.put("orderStatus", orderStatus);
		}
		requestParam.setEqual(equalMap);
		requestParam.setInMap(inMap);
		Map<String,String> startwithMap = new HashMap<String, String>();
		if(startLong!=null){
			startwithMap.put("accountTime", startLong);
		}
		requestParam.setStartwith(startwithMap);
		Map<String,String> endwithMap = new HashMap<String, String>();
		if(startLong!=null){
			endwithMap.put("accountTime", endLong);
		}
		requestParam.setEndwith(endwithMap);
		return requestParam;
	}
	/**
	 * wjg
	 * 修改备注
	 */
	public String updateRemarksById(Map<String, String> map) throws UPPException {
		String result = "0";
		try {
			String uri = ProtalConvertUtils.getValue("UPP_EDIT_ORDER_REMARKS");
			String json = super.sendRequest(map, uri);
			JSONObject objectMap = JSONObject.fromObject(json);
			String jsonResult = (String)objectMap.get("result");
			if("1".equals(jsonResult)){
				result = "1";
			}
			logger.info("解密后结果:" + json);
			return result;
		} catch (Exception e) {
			logger.error("修改备注异常！", e);
			throw new UPPException("修改备注异常！", e);
		}
	}
	/**
	 * wjg
	 * 获取备注
	 */
	public Map findRemarksById(String id) throws UPPException {
		try {
			Map<String ,String> map = new HashMap<String, String>();
			map.put("orderId", id);
			String uri = ProtalConvertUtils.getValue("QUERY_ORDERINFO_BY_ID");
			String json = super.sendRequest(map, uri);
			JSONObject objectMap = JSONObject.fromObject(json);
			String jsonResult = (String)objectMap.get("result");
			Map<String,String> resultMap = new HashMap<String,String>();
			if("1".equals(jsonResult)){
				JSONObject dataMap = (JSONObject)objectMap.get("data");
				String remarks = dataMap.get("remarks").toString();
				resultMap.put("remarks",remarks);
			}
			logger.info("解密后结果:" + json);
			return resultMap;
		} catch (Exception e) {
			logger.error("获取备注异常！", e);
			throw new UPPException("获取备注异常！", e);
		}
	}
	/**
	 * wjg
	 * 验证是否设置密保问题及密保问题数量
	 */
	public Map<String, String> judgePaymentPwdQuery(Map<String, String> map)throws UPPException {
		Map<String, String> result = new HashMap<String, String>();
		try {
			String uri = ProtalConvertUtils.getValue("QUERY-SECURITY-QUESTION");
			String json = super.sendRequest(map, uri);
			JSONObject objectMap = JSONObject.fromObject(json);
			String jsonResult = (String)objectMap.get("result");
			if("1".equals(jsonResult)){
				JSONObject dataMap = (JSONObject)objectMap.get("data");
				String pwdQuery = dataMap.get("data").toString();
				if(!StringUtils.isBlank(pwdQuery)){
					String[] split = pwdQuery.split(",");
					int length = split.length;
					result.put("pwdQuery", "1");
					result.put("pwdQueryNum", String.valueOf(length));
				}
			}
		} catch (Exception e) {
			logger.error("验证是否设置密保问题及密保问题数量！", e);
			throw new UPPException("验证是否设置密保问题及密保问题数量！", e);
		}
		return result;
	}

}
