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
<legend>锁定账户接口</legend>
	<form name="lockForm" method="post" action="<%=root %>/upp/accountController/lockAccount/">
		<div id="tableDiv" style="overflow: auto;height:150px;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>账号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="lockAccountNo" id="lockAccountNo" ltype="text" value="MA1417075872544666" validate="{required:true,minlength:1,maxlength:30}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>锁定原因:</td>
					<td align="left" class="l-table-edit-td">
					    <textarea name="lockReasons"  id="lockReasons" validate="{required:true,minlength:1,maxlength:50}" resize="none" cols=40 rows=3>测试锁定账户外部接口</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="5" align="center">
						<input type="button" value="锁定" id="Button1" onclick="document.forms[0].action='<%=root %>/upp/accountController/lockAccount/';document.forms[0].action=document.forms[0].action+document.getElementById('lockAccountNo').value+'/'+document.getElementById('lockReasons').value;document.forms[0].submit();" class="l-button l-button-submit" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</fieldset>

</body>
</html>