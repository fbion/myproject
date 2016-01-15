<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%
String mes = request.getAttribute("message") == null?"":request.getAttribute("message").toString();

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

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>跳转页面</title>
</head>
<body style="background: white; font-size: 12px;">
<br/>
<br/>

 <%-- <img src="<%=request.getContextPath()%>/images/error.jpg" alt="错误" /> --%>

 <div style="font-size:18px;font: bold;">信息提示:<span style="color: red;"><%=mes %></span>，系统将在[<span id="jump" style="color: red;font-weight:800;">30</span>]秒钟内跳转到登录页面，或者单击<a href="javascript:toLogin();">这里</a>跳转到登录页面</div>
    

<script type="text/javascript">  
	
	var message = "<%=mes%>";
	//alert(message);
	if(message!=""){
		setTimeout( "toLogin()",30000);		
	}else{
		//toLogin();
	}
	
	function toLogin(){	
		top.location='<%= ((java.util.Map)(com.ctfo.upp.utils.SpringBUtils.getBean("casMap"))).get("casServerLogoutUrl") %>';
	}	
	
	
	function countDown(secs){
	    jump.innerText=secs;
	    if(--secs>0)
	        setTimeout("countDown("+secs+" )",1000);
	}
	countDown(30);
		
</script>

</body>
</html>