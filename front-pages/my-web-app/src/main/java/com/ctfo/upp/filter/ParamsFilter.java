package com.ctfo.upp.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

/**
 * 
 * @author HollyLee
 *
 */
public class ParamsFilter implements Filter {

	protected Log log = LogFactory.getLog(getClass());

	public void init(FilterConfig filterconfig) throws ServletException {

	}

	public void doFilter(ServletRequest servletrequest,
			ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {		
		HttpServletRequest httpservletrequest = (HttpServletRequest) servletrequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletresponse;
		httpservletrequest.setCharacterEncoding("UTF-8");
		try {		
			commonLayerParameterChecking(httpservletrequest);
		} catch (NonAllowableCharacterException e) {
			log.warn("参数中含有非法字符串:", e);
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.getWriter().append("参数中含有非法字符串");
			httpServletResponse.getWriter().flush();
			httpServletResponse.getWriter().close();
			//httpServletResponse.sendRedirect(httpservletrequest.getContextPath()+ "/pages/error.jsp?type=8");
			return;
		} catch (Exception e) {
			log.warn("校验非法字符串异常：", e);
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.getWriter().append("校验非法字符串异常");
			httpServletResponse.getWriter().close();
			//httpServletResponse.sendRedirect(httpservletrequest.getContextPath()+ "/pages/error.jsp?type=1");
			return;
		}
		filterchain.doFilter(servletrequest, servletresponse);
	}

	public void setFilterConfig(FilterConfig filterconfig) {

	}

	public void destroy() {

	}

	/**
	 * Common Layer security checking Check all request parameters for non
	 * allowable character If found throws exception.
	 * 
	 * @param request
	 * @throws java.lang.Exception
	 */
	public void commonLayerParameterChecking(HttpServletRequest request)
			throws NonAllowableCharacterException, Exception {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		String exceptionalURLCheckCount = PropertyManager
				.getValue("filter.none.checkcharacter.URI.count");

		for (int i = 1; i <= Integer.parseInt(exceptionalURLCheckCount); i++) {
			hm.put(PropertyManager.getValue("filter.none.checkcharacter.URI." + i), new Integer(i));
		}

		if (hm != null
				&& !hm.isEmpty()
				&& hm.get(request.getRequestURI().substring(
						request.getContextPath().length(),
						request.getRequestURI().length())) == null
				&& request.getParameterNames() != null) {
			
			log.debug("当前校验的URL："+request.getScheme()+" \t"+request.getRequestURI().substring(
						request.getContextPath().length(),
						request.getRequestURI().length()));
			Enumeration<String> e = request.getParameterNames();
			while (e.hasMoreElements()) 
			{
				Object o = e.nextElement();
				//checkNonAllowableParam((String) o); //struts漏洞过滤
				checkNonAllowableCharacter(request.getParameter((String) o).toLowerCase());
				checkNonAllowableString(request.getParameter((String) o).toLowerCase());
				String[] values = request.getParameterValues((String) o);
				
				if (values != null) 
				{
					for (int i = 0; i < values.length; i++) 
					{
						checkNonAllowableCharacter(values[i].toLowerCase());
						checkNonAllowableString(values[i].toLowerCase());
					}
				}
			}
		}
	}

	/**
	 * To check all request parameter value contains non-allowable character for
	 * eg < > % ~
	 * 
	 * @param String
	 *            input
	 */
	private void checkNonAllowableCharacter(String input)
			throws NonAllowableCharacterException {
		StringTokenizer st = new StringTokenizer("S" + input + "E",
				PropertyManager.getValue("string.public.common.specialchar"));
		int iCount = st.countTokens();

		if (iCount > 1) {
			log.warn("输入字符串：["+input+"] 需要比较的字符串：["+PropertyManager.getValue("string.public.common.specialchar")+"]");
			throw new NonAllowableCharacterException(input);
		}
	}
	
	private void checkNonAllowableParam(String input) throws NonAllowableCharacterException {
		String exceptionalURLCheckContent = PropertyManager
				.getValue("string.public.common.specialParam");
		int count = exceptionalURLCheckContent.split(",").length;
		for (int i = 0; i < count; i++) {
			if(Pattern.matches(exceptionalURLCheckContent.split(",")[i], input)){
				log.warn("输入字符串：["+input+"] 需要比较的字符串：["+exceptionalURLCheckContent.split(",")[i]+"]");
				throw new NonAllowableCharacterException(input);
			}
		}
	}

	private void checkNonAllowableString(String input) throws NonAllowableCharacterException {
		String exceptionalURLCheckContent = PropertyManager
				.getValue("string.public.common.specialString");
		int count = exceptionalURLCheckContent.split(",").length;
		for (int i = 0; i < count; i++) {

			int iCount = input.indexOf(exceptionalURLCheckContent.split(",")[i]);
			if (iCount != -1) {
				log.warn("输入字符串：["+input+"] 需要比较的字符串：["+exceptionalURLCheckContent.split(",")[i]+"]");
				throw new NonAllowableCharacterException(exceptionalURLCheckContent.split(",")[i]);
			}
			if(input.startsWith(exceptionalURLCheckContent.split(",")[i])){
				log.warn("输入字符串：["+input+"] 需要比较的字符串：["+exceptionalURLCheckContent.split(",")[i]+"]");
				throw new NonAllowableCharacterException(input);
			}
		}
		
		//用于判断特殊字符：create,insert,select,delete,update,drop 匹配格式:字符前空格数为零或大于零 字符后空格数为一或大于一
		String exceptionalURLCheckContentAddBlankOne = PropertyManager.getValue("string.public.common.specialStringAddBlankOne");
        int countTemp1 = exceptionalURLCheckContentAddBlankOne.split(",").length;
        for (int i = 0; i < countTemp1; i++) 
        {
	        if (patternContentByReg(input,"\\s*"+exceptionalURLCheckContentAddBlankOne.split(",")[i]+"\\s+")) 
	        {
		       log.warn("输入字符串：["+input+"] 需要比较的正则字符串：["+"\\s+"+exceptionalURLCheckContentAddBlankOne.split(",")[i]+"\\s+"+"]");
		       throw new NonAllowableCharacterException(input);
	        }
        }

      //用于判断特殊字符：and,exec,count,chr,mid,master,or,truncate,char,declare,join 匹配格式:字符前空格数为一或大于一 字符后空格数为一或大于一
		String exceptionalURLCheckContentAddBlankTwo = PropertyManager
				.getValue("string.public.common.specialStringAddBlankTwo");
		int countTemp = exceptionalURLCheckContentAddBlankTwo.split(",").length;
		for (int i = 0; i < countTemp; i++) {
			if (patternContentByReg(input,"\\s+"+exceptionalURLCheckContentAddBlankTwo.split(",")[i]+"\\s+")) {
				// DefaultLogger.warn(this, "Non-allowable character FOUND=" +
				// input);
				log.warn("输入字符串：["+input+"] 需要比较的正则字符串：["+"\\s+"+exceptionalURLCheckContentAddBlankTwo.split(",")[i]+"\\s+"+"]");
				throw new NonAllowableCharacterException(input);
			}
		}
	}

	public Properties loadProperties() throws Exception {

		Properties properties = new Properties();
		InputStream input = null;

		try {
			input = this.getClass().getResourceAsStream("/filterkey.properties");
			properties.load(input);
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				input.close();
			} catch (IOException ioe) {
				log.error(ioe.getMessage(), ioe);
			}
		}
		return properties;
	}

	public static boolean patternContentByReg(String content, String reg){
		org.apache.oro.text.regex.Pattern pattern = null;
		Perl5Matcher matcher = new Perl5Matcher();
		Perl5Compiler compiler = new Perl5Compiler();
		PatternMatcherInput input = new PatternMatcherInput(content);
		try {
			pattern = compiler.compile(reg, compiler.CASE_INSENSITIVE_MASK);
			// 使用find()方法查找第一个匹配的对象
			if (matcher.contains(input, pattern)) {
				matcher.getMatch();
				return true;
			}
			// 继续查找下一个匹配对象
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
