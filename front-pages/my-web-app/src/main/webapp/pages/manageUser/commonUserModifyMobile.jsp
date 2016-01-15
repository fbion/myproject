<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>增加风控规则</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="commonUserModifyMobile.js?v=g_version"></script> 
<script type="text/javascript" src="<%=application.getContextPath()%>/js/DateUtil.js"></script>
</head>
<body style="height: 90%; padding: 20px;">
	<div id="showBigImg" onclick="javascript:$(this).hide();" style="position:absolute;display: none; cursor: pointer; width:400px;height: 300px;z-index: 1000;" title="点击关闭大图"></div>	
	<div id="tableDiv" style="overflow: auto; height: 400px;">
	
	<form name="form" method="post" id="form">
		<table>
			<tr>
				<td  class="l-table-edit-td">原手机号</td>
				<td  class="l-table-edit-td"><input name="oldMobileNo" type="text" id="oldMobileNo" ltype="text" style="width:141px"/></td>
			</tr>
			<tr>
				<td  class="l-table-edit-td">新手机号</td>
				<td  class="l-table-edit-td"><input name="newMobileNo" type="text" id="newMobileNo" ltype="text" style="width:141px"/></td>
			</tr>			
		</table>		
	</form>
	</div>
	<div style="display: none"></div>
</body>
</html>