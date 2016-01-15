<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>线下流程管理-查看凭证</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script src="<%=application.getContextPath()%>/js/imageTrans/CJL.0.1.min.js" type="text/javascript"></script> 
<script src="<%=application.getContextPath()%>/js/imageTrans/ImageTrans.js" type="text/javascript"></script> 
<script type="text/javascript" src="showImg.js?v=g_version"></script> 
<style>
	#idContainer{border:1px solid #000;width:900px; height:310px; background:#FFF center no-repeat;}
</style>
</head>

<body style="height: 50%; padding: 35px;overflow: visible;">
	<div id="idContainer"> </div>
	<input id="idLeft" type="button" value="向左旋转" />
	<input id="idRight" type="button" value="向右旋转" />
	<input id="idVertical" type="button" value="垂直翻转" />
	<input id="idHorizontal" type="button" value="水平翻转" />
	<input id="idReset" type="button" value="重置" />
</body>
</html>