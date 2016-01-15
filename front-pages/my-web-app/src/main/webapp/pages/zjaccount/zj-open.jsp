<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账户管理-中交账户开户-开户</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="zj-open.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="padding: 4px; overflow: visible;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="tableDiv" style="overflow: auto; height: 400px;">
		<form name="form1" method="post" id="form1">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td colspan="4">
						<div id="step1"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">支付平台编号:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="serviceProviderCode" id="serviceProviderCode" ltype="text" value="1002" readonly/> <span style="color: red;">第一期只支持易宝，所以编码默认为 1002</span></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>所属用户UUID:</td>
					<td align="left" class="l-table-edit-td">
						<select  name="ownerUserId" id="ownerUserId" validate="{required:true}" onchange='$("#ownerLoginName").val($("#ownerUserId").find("option:selected").text())'>
						   <option value="f1e6a22s-950c-4135-a298-d0a2c25b387a">中交收益</option>
						   <option value="f2e6a22p-950c-4135-a298-d0a2c25b387b">中交支出</option>
						   <option value="f3e6a22s-950c-4135-a298-d0a2c25b387c">中交备付金</option>
						   <option value="f4e6a22p-950c-4135-a298-d0a2c25b387d">中交加急提现</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">所属用户统一认证登陆名:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="ownerLoginName" id="ownerLoginName" value=""  readonly="readonly" /></td>
				</tr>

				<tr>
					<td align="right" class="l-table-edit-td">交易密码:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="payPassword" id="payPassword" value="abcd1234" readonly="readonly"/></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">手机号码:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="mobileNo" id="mobileNo" value="13681157875" readonly="readonly" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div style="display: none"></div>
</body>
</html>