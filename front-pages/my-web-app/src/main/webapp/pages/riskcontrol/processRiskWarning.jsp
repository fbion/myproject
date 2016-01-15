<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ctfo.upp.util.Converter"%>
<%@page import="com.ctfo.upp.view.beans.Index"%>	
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
    String root = request.getContextPath();
    Index index = (Index)session.getAttribute(Converter.SESSION_INDEX);
%> 
<title>增加风控规则</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="processRiskWarning.js?v=g_version"></script> 
<script type="text/javascript" src="<%=application.getContextPath()%>/js/DateUtil.js"></script>
<script type="text/javascript">
	var INDEX_USER_NAME="<%=index.getUserName()%>";
</script>
</head>
<body style="height: 90%; padding: 20px;">
	<div id="showBigImg" onclick="javascript:$(this).hide();" style="position:absolute;display: none; cursor: pointer; width:400px;height: 400px;z-index: 1000;" title="点击关闭大图"></div>	
	<div id="tableDiv" style="overflow: auto; height: 400px;">
	
	<form name="riskForm" method="post" id="riskForm">
		<table align="center">
			<tr>
				<td>处理结果记录: (不超过200个字符)</td>
			</tr>
			<tr>
				<td>
					<textarea id="processResult" maxlength="200" name="processResult" rows="10" cols="45">
					</textarea>					
				</td>
			</tr>
		</table>

		
	</form>
	
	<form name="form1" method="post" id="form1">
	
	</form>
	</div>
	<div style="display: none"></div>
</body>
</html>