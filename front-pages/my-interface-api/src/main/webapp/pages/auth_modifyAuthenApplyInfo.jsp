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
		<legend>修改草稿状态下的实名认证申请信息</legend>
		<form name="modifyForm" method="post"
			action="<%=root %>/upp/realNameAuthenController/modifyAuthenApplyInfo">
			<div id="tableDiv" style="overflow: auto; height:230px;">
				<table cellpadding="0" cellspacing="0" class="l-table-edit"
					align="center">
					<tr>
						<td align="right" class="l-table-edit-td"><span
							style="color: red;">*&nbsp;</span>账户ID:</td>
						<td align="left" class="l-table-edit-td"><input type="text"
							name="accontId" id="accontId" ltype="text"
							value="e74c18ae-39d0-496b-885f-e68eb4c9bfc2"
							validate="{required:true,minlength:1,maxlength:30}" /></td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td"><span
							style="color: red;">*&nbsp;</span>申请时间毫秒:</td>
						<td align="left" class="l-table-edit-td"><input type="text"
							name="applyTime" id="applyTime" ltype="text"
							value="<%=System.currentTimeMillis()%>"
							validate="{required:true,minlength:1,maxlength:30}" /></td>
						<!-- 0 1车主 2 -->
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td"><span
							style="color: red;">*&nbsp;</span>账号:</td>
						<td align="left" class="l-table-edit-td"><input type="text"
							name="insideAccountNo" id="insideAccountNo" ltype="text"
							value="MA1417409019576304"
							validate="{required:true,minlength:1,maxlength:20}" /></td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td"><span
							style="color: red;">*&nbsp;</span>姓名:</td>
						<td align="left" class="l-table-edit-td"><input type="text"
							name="userName" id="userName" value="test" ltype="text"
							validate="{required:true,minlength:1,maxlength:20}" /></td>
					</tr>

					<tr>
						<td align="right" class="l-table-edit-td"><span
							style="color: red;">*&nbsp;</span>身份证号:</td>
						<td align="left" class="l-table-edit-td"><input type="text"
							name="idcardNo" id="idcardNo" ltype="text"
							validate="{required:true,minlength:1,maxlength:20}" /></td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td"><span
							style="color: red;">*&nbsp;</span>银行卡号:</td>
						<td align="left" class="l-table-edit-td"><input type="text"
							name="bankcardNo" id="bankcardNo" ltype="text"
							validate="{required:true,minlength:1,maxlength:20}" /></td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td"><span
							style="color: red;">*&nbsp;</span>银行编码:</td>
						<td align="left" class="l-table-edit-td"><input type="text"
							name="bankCode" id="bankCode" value="CCB" ltype="text"
							validate="{required:true,minlength:1,maxlength:20}" /></td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td"><span
							style="color: red;">*&nbsp;</span>银行名称:</td>
						<td align="left" class="l-table-edit-td"><input type="text"
							name="branchBankName" id="branchBankName" ltype="text"
							validate="{required:true,minlength:1,maxlength:20}" /></td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td"><span
							style="color: red;">*&nbsp;</span>实名认证申请类型:</td>
						<td align="left" class="l-table-edit-td"><select
							type="select" name="applyType" id="applyType" ltype="text"
							validate="{required:true,minlength:1,maxlength:30}">
								<option value="1">实名认证开户申请</option>
								<option value="2">实名认证</option>
						</select></td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td"><span
							style="color: red;">*&nbsp;</span>实名认证状态:</td>
						<td align="left" class="l-table-edit-td"><select
							type="select" name="applyStatus" id="applyStatus" ltype="text"
							validate="{required:true,minlength:1,maxlength:30}">
								<option value="1">最终状态(发起认证请求，通过后保存申请信息)</option>
								<option value="0">草稿状态(不发起认证，只保存申请信息)</option>
						</select></td>
					</tr>
					<tr>
						<td colspan="5" align="center"><input type="submit"
							value="修改草稿状态下实名认证信息" id="Button1" class="l-button l-button-submit" /></td>
					</tr>
				</table>
			</div>
		</form>
	</fieldset>
</body>
</html>