<%@page import="java.util.Map"%>
<%@page import="com.ctfo.upp.utils.SpringBUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.ctfo.upp.cashier.util.CashierConvertUtils"%>
<%
try{  
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


/* String casLogoutUrl = ((Map)SpringBUtils.getBean("casMap")).get("casServerLogoutUrl").toString(); */
String casLogoutUrl = CashierConvertUtils.getValue("logoutUrl");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>跳转页面</title>
</head>

<body>
正在退出......

<script type="text/javascript"> 
  
	top.location="<%=casLogoutUrl%>";
	
</script>


</body>
</html>