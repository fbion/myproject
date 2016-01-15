<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商户管理-商户接入页面</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="merchant-add.js?v=g_version"></script>
</head>
<body style="padding: 2px;">
	<div id="tableDiv" style="overflow: auto; height: 400px;">
		<form name="form1" method="post" id="form1">
			<input type="hidden" id="id" name="id" />
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td colspan="4">
						<div id="step1"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商户名称:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="storeName" id="storeName" ltype="text" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商户类型:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="storeType" id="storeType" ltype="combobox" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>联系人:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="contactUser" id="contactUser" ltype="text" validate="{required:true,minlength:1,maxlength:30}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>电话:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="contactPhone" id="contactPhone"  validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>状态:</td>
					<td align="left" class="l-table-edit-td">未激活</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>公钥:</td>
					<td align="left" class="l-table-edit-td">
						<textarea type="text" id="privateKey" ltype="text" validate="{required:true}" cols="70" rows="5"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>私钥:</td>
					<td align="left" class="l-table-edit-td">
						<textarea type="text" id="privateKey" ltype="text" validate="{required:true}" cols="70" rows="10"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">生成密钥对:</td>
					<td align="left" class="l-table-edit-td">
						<a href="javascript:window.merchant.secretKey()">点此生成秘钥对</a>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">接入协议:</td>
					<td align="left" class="l-table-edit-td">
						<input id="check1" name="check1" class="check1" type="checkbox" checked="checked" />
						<a href="javascript:window.merchant.showClause()">我同意【服务协议】和【隐私条款】</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="display: none"></div>
</body>
</html>