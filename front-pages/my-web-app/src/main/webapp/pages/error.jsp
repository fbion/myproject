<%@ page language="java" contentType="text/html; charset=UTF-8"
	isErrorPage="true" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*"%>
<%
	response.setStatus(HttpServletResponse.SC_OK);
	String mes = request.getAttribute("message") == null ? "" : request.getAttribute("message").toString();
	String root = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>500</title>
<link href="<%=root%>/css/500/500.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="common_mistake">
		<div class="common_mistakeMain">
			<div class="common_mistakeFont"></div>
			<div class="common_blackFont">
				<font color="red"><%=mes%>
				</font>
			</div>
			<div class="common_main">
				<div class="common_mainLeft">
					<div class="common_tu"></div>
				</div>
				<div class="common_mainRight">
					<div class="common_mainRightFont">在地址中可能存在键入错误，请检查地址栏
						当你点击某个链接时，它可能已过期，或者名称已被更改</div>
					<div class="common_help">
						点击 <a href="<%=root%>/logout.jsp">退出系统<a/> 。 如还有疑问，请联系管理员。
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

