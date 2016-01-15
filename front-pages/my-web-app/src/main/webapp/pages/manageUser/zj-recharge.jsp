<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账户管理-中交账户-充值</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="zj-recharge.js?v=g_version" type="text/javascript" charset="utf-8"></script>
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
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>用户ID:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="userId" id="userId" ltype="text" validate="{required:true,minlength:1,maxlength:60}" /></td>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>accountNo:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="accountNo" id="accountNo" ltype="text" validate="{required:true,minlength:1,maxlength:60}" /></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>充值金额:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="amount" id="amount" ltype="text" validate="{required:true,minlength:1,maxlength:60}" /></td>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商户编号:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="storeCode" id="storeCode" ltype="text" validate="{required:true,minlength:1,maxlength:60}" /></td>
				</tr>

				<tr>
					<td align="right" class="l-table-edit-td">终端类型:</td>
					<td align="left" class="l-table-edit-td"><input name="clentType" type="radio" id="PC" value="PC" checked="checked"  readonly="readonly"/>PC</td>
					<td align="right" class="l-table-edit-td">支付渠道:</td>
					<td align="left" class="l-table-edit-td"><input name="payChannel" type="radio" id="NET" value="NET" checked="checked" readonly="readonly"/>网银</td>
				</tr>

				<tr>
					<td align="right" class="l-table-edit-td">银行信息:</td>
					<td align="left" class="l-table-edit-td" colspan="3">
						<input type="text" id="bank" ltype="combobox" validate="{required:true}"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">银行信息:</td>
					<td align="left" class="l-table-edit-td" colspan="3">
						<input type="text" id="bankCardCode" name="bankCardCode" readonly="readonly"/> 
						<input type="text" id="bankCardType" name="bankCardType" value="DEBIT"/> 
						<input type="text" id="bankCardName" name="bankCardName" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">备注:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="remarks" id="remarks" /></td>
				</tr>

			</table>
		</form>
	</div>
	<div style="display: none"></div>
</body>
</html>