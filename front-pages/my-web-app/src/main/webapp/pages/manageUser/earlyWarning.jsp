<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账户管理-中交账户列表-预警</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="earlyWarning.js?v=g_version" charset="utf-8"></script>
</head>
<body style="padding: 2px;">
	<div id="tableDiv" style="overflow: auto; height: 400px;">
		<form name="form1" method="post" id="form1">
			<input type="hidden" id="insideAccountNo" name="insideAccountNo"/>
			<input type="hidden" id="paramId" name="paramId"/>
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td colspan="4">
						<div id="step1"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">账户号：</td>
					<td align="left" class="l-table-edit-td" id="insideAccountNoTD"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">账户名称：</td>
					<td align="left" class="l-table-edit-td" id="ownerLoginNameTD"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><label style="color: red;">*&nbsp;</label>阀值金额：</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="remainder" id="remainder"  validate="{required:true}" /></td>
				</tr>
				<tr>
					<td></td>
					<td style="text-align: left;"><label style="color: red;">*(注：账户资金低于阀值金额时，预警提醒)</label></td>
				</tr>
			</table>
		</form>
	</div>
	<div style="display: none"></div>
</body>
</html>