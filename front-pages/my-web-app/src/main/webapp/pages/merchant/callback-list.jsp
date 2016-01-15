<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商户管理-回调列表</title>
 <jsp:include page="../all_css.jsp"/>
<jsp:include page="../all_js.jsp"/> 
<script src="callback-list.js?v=g_version" type="text/javascript" charset="utf-8"></script> 
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 4px;">
		<input type="hidden" id="storeId" name="storeId"/>
		<!-- 表格内容  -->
		<div id="callbackGrid" class="grid"></div>
	</div>
</body>
</html>
