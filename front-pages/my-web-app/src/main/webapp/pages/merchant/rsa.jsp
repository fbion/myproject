<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>生成密钥对</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="rsa.js?v=g_version"></script> 
</head>
<body style="padding: 2px;">
		<form name="form1" method="post" id="form1">
			<input type="hidden" id="id" name="id" />
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td colspan="2">
						<div id="step1"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>公钥：</td>
					<td align="left" class="l-table-edit-td">
						<textarea id="publickey" rows="4" cols="96" readonly="readonly"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>私钥：</td>
					<td align="left" class="l-table-edit-td">
						<textarea id="privatekey" rows="11" cols="96" readonly="readonly"></textarea>
					</td>
				</tr>
				</table>
		</form>
</body>
</html>