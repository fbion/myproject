<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商户管理-商户信息修改</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="merchant-edit.js?v=g_version"></script>
</head>
<body style="padding: 2px;">
	<div id="tableDiv" style="overflow: auto; height: 400px;">
		<form name="form1" method="post" id="form1">
			<input type="hidden" id="id" name="id" />
			<input type="hidden" name="existStoreName" id="existStoreName" />
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td colspan="4">
						<div id="step1"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商户名称:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="storeName" id="storeName" ltype="text" validate="{required:true}" /></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商户类型:</td>
					<td align="left" class="l-table-edit-td"><input name="storeType" id="storeType" type="text" ltype="combobox" /></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>联系人:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="contactUser" id="contactUser" ltype="text" validate="{required:true}" /></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="width: 15%;"><span style="color: red;">*&nbsp;</span>电话:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="contactPhone" id="contactPhone" ltype="text" validate="{required:true,minlength:11,maxlength:11}" /></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" style="width: 15%;">状态:</td>
					<td align="left" class="l-table-edit-td" id="storeStatusTD"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>公钥:</td>
					<td align="left" class="l-table-edit-td"><textarea  id="privateKey" ltype="text" validate="{required:true}" cols="70" rows="5"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"></td>
					<td align="left" class="l-table-edit-td">
						<a href="javascript:window.merchantEdit.secretKey()">点此生成秘钥</a>&nbsp;&nbsp;
						<a href="javascript:window.merchantEdit.reset()">恢复原公钥</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="display: none"></div>
</body>
</html>