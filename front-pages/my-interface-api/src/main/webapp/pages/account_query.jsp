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
<legend>测试查询账户信息接口</legend>
	<form name="queryForm" method="post" action="<%=root %>/upp/accountController/queryAccountInfo/">
		<div id="tableDiv" style="overflow: auto;height:50px;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>账号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="accountNo" id="accountNo" ltype="text" value="MA1419400019885725" validate="{required:true,minlength:1,maxlength:30}" />
					</td>
				</tr>
				<tr>
					<td colspan="5" align="center">
						<input type="button" value="查询" id="Button1" onclick="document.forms[0].action='<%=root %>/upp/accountController/queryAccountInfo/';document.forms[0].action=document.forms[0].action+document.getElementById('accountNo').value;document.forms[0].submit();" class="l-button l-button-submit" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</fieldset>
</body>
</html>