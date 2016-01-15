<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<% String root = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	
    <title>用户信息</title>

<link href="../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="all_js.jsp" />
   
    <script type="text/javascript">
   		var root = "<%=root%>";
   		var iroleid=parent.INDEX_USER_ROLEID;
   		var id=parent.INDEX_USER_ID;
   		var userType=parent.INDEX_USER_TYPE;
   		var sgin = parent.INDEX_SYS_SIGN;
   		
   		var userLogin=parent.INDEX_USER_LOGIN ;
   		var userName=parent.INDEX_USER_NAME ;
   		var roleName=parent.INDEX_USER_ROLENAME;
   		
   		
   		$(function(){
   			$("#userLogin").html(userLogin);
   			$("#userName").html(userName);
   			$("#roleGrid").html(roleName);
   		});
   		
    </script>
</head>

<body style="padding:10px;background:#EAEEF5;overflow-x:auto;"> 

<div style="padding-left:20px;margin:auto;">
 	<div id="userDetailForIndex">
		<ul style="line-height: 30px; margin-left: 10px;">
			<li>登录名称：<span id="userLogin"></span></li>
			<li>用户姓名：<span id="userName" ></span></li>
			<%--
			   <li>用户类型：<span id="userType" ></span></li>
			 --%>
			<li>角色信息：
				<span id="roleGrid"></span>    			         
			</li>
		</ul>
	</div>

</div>

</body>
</html>
