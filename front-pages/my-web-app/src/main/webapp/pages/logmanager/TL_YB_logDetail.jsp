<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>详细信息</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/logmanager/TL_YB_logDetail.js" ></script>
</head>
<body style="padding: 4px; overflow: visible;">
	
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">

		<table cellpadding="0" cellspacing="0" class="l-table-edit" align="left">
			<tr>
				<td align="right" class="l-table-edit-td">ID：</td>
				<td id="ID"></td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">方法名称：</td>
				<td id="name"></td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">类名称：</td>
				<td id="className"></td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">类型：</td>
				<td id="type"></td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">发送时间：</td>
				<td id="requestTime"></td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">响应时间：</td>
				<td id="responseTime"></td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">发送：</td>
				<td>
					<textarea rows="5" cols="100" id="request"></textarea>
				</td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">响应：</td>
				<td>
					<textarea rows="5" cols="100" id="response"></textarea>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
