<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户密码修改</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="resetPassword.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="height: 90%; padding: 20px;">
	<div id="tableDiv" style="overflow: auto; height: 400px;">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
			<tr>
				<td>
					<div id="step1"></div>
				</td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" id="accountNOTd"></td>
			</tr>
			<tr>
				<td align="left" class="l-table-edit-td" id="newPassSpan"></td>
			</tr>
		</table>
	</div>
	<div style="display: none"></div>
</body>
</html>
