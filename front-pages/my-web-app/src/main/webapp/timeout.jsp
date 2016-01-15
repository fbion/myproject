<%@page import="java.util.Map"%>
<%@page import="com.ctfo.upp.utils.SpringBUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%
System.out.println("================session time out=====================");

try{  
	//清空session用户信息
	request.getSession().setAttribute("user", null);
	//清空cookies  	
   Cookie[] cookies=request.getCookies(); 
   for(int i=0;i<cookies.length;i++){  
    //System.out.println(cookies[i].getName() + ":" + cookies[i].getValue());  
    Cookie cookie = new Cookie(cookies[i].getName(), null);  
    cookie.setMaxAge(0);  
    cookie.setPath("/");//根据你创建cookie的路径进行填写      
    response.addCookie(cookie);  
   }  
   request.getSession().invalidate();
}catch(Exception ex){  
   System.out.println("..........delete Cookies Exception!"+ex.getMessage());  
}    
System.out.println("================logout=====================");

String casServerLogoutUrl = ((Map)SpringBUtils.getBean("casMap")).get("casServerLogoutUrl").toString();

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>跳转页面</title>
</head>

<body>

<script type="text/javascript"> 
  
	//alert('操作时间过长，请重新登录后再操作!');
	top.location="<%=casServerLogoutUrl%>";
	
</script>


</body>
</html>