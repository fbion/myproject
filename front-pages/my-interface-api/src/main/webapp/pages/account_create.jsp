<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>运营管理平台－支付中心－账户模块外部接口测试</title>
</head>

<body style="height: 90%; padding: 20px;">
<fieldset>
<legend>开户接口</legend>
	<form name="submitForm" method="post" action="<%=root %>/upp/accountController/createAccount">
		<div id="tableDiv" style="overflow: auto; height:160px;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>支付平台编号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="serviceProviderCode" id="serviceProviderCode" ltype="text" value="1002" readonly validate="{required:true,minlength:1,maxlength:30}" />
					    <span style="color: red;">第一期只支持易宝，所以编码默认为 1002</span>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>账户类型:</td>
					<td align="left" class="l-table-edit-td">
						<select type="select" name="accountType" id="accountType" ltype="text" validate="{required:true,minlength:1,maxlength:30}">
						   <option value="1">普通</option>
						   <option value="0">中交</option>
						</select>
					</td>
					<!-- 0 1车主 2 -->
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>所属用户UUID:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="ownerUserId" id="ownerUserId" ltype="text" value="90e63c0e-f2d2-48b2-8b87-2fa1c9dd4750" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>所属用户统一认证登陆名:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="ownerLoginName" id="ownerLoginName" value="test" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>				
							
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>交易密码:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="payPassword" id="payPassword" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>手机号码:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="mobileNo" id="mobileNo" value="18911171270" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				<tr>
					<td colspan="5" align="center">
						<input type="submit" value="开户" id="Button1" class="l-button l-button-submit" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</fieldset>

</body>
</html>