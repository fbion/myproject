<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商户管理-商户详情</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="merchant-detail.js?v=g_version"></script>
</head>
<body style="padding: 4px; overflow: visible;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" align="left">
			<tr>
				<td colspan="6">
					<div id="step1"></div>
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">商户名称:</td>
				<td align="left" id="storeName"></td>
				<td align="right" class="l-table-edit-td">商户类型:</td>
				<td align="left" id="storeType"></td>
				<td align="right" class="l-table-edit-td">联系人:</td>
				<td align="left" id="contactUser"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">联系电话:</td>
				<td align="left" id="contactPhone"></td>
				<td align="right" class="l-table-edit-td">商户状态:</td>
				<td align="left" id="storeStatus"></td>
				<td align="right" class="l-table-edit-td">商户编码:</td>
				<td align="left" id="storeCode"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">接入时间:</td>
				<td align="left" id="regTime"></td>
				<td align="right" class="l-table-edit-td">操作人:</td>
				<td align="left" id="opUser" colspan="3"></td>
				<!-- <td align="right" class="l-table-edit-td">商户秘钥:</td>
				<td align="left" id="privateKey"></td> -->
			</tr>
		</table>
	</div>
	<div style="display: none"></div>
</body>
</html>