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
  <form name="modifyPayPasswordForm" method="post" action="<%=root %>/upp/accountController/modifyPayPassword/">
		<div id="tableDiv" style="overflow: auto;height:90px;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>账号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="modifyAccountNo" id="modifyAccountNo" ltype="text" value="MA1418622298531566" validate="{required:true,minlength:1,maxlength:30}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>旧密码:</td>
					<td align="left" class="l-table-edit-td">
						<input type="password" name="originalPassword" id="originalPassword" ltype="text" validate="{required:true,minlength:1,maxlength:8}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>新密码:</td>
					<td align="left" class="l-table-edit-td">
						<input type="password" name="newPassword" id="newPassword" ltype="text" validate="{required:true,minlength:1,maxlength:8}" />
					</td>
				</tr>
				<tr>
					<td colspan="5" align="center">
						<input type="button" value="修改" id="Button1" onclick="document.forms[0].action='<%=root %>/upp/accountController/modifyPayPassword/';document.forms[0].action=document.forms[0].action+document.getElementById('modifyAccountNo').value+'/'+document.getElementById('originalPassword').value+'/'+document.getElementById('newPassword').value;document.forms[0].submit();" class="l-button l-button-submit" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>