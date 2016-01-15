<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>普通账户交易流水详情</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="generalDetail.js?v=g_version" type="text/javascript" charset="utf-8"></script>
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
				<td align="right" class="l-table-edit-td">内部账户流水号：</td>
				<td align="left" id="insideExternalNo"></td>
				<td align="right" class="l-table-edit-td">记账类型：</td>
				<td align="left" id="bookaccountType"></td>
				<td align="right" class="l-table-edit-td">内部账号：</td>
				<td align="left" id="insideAccountNo"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">科目：</td>
				<td align="left" id="accountSubject"></td>
					<td align="right" class="l-table-edit-td">记账时间：</td>
				<td align="left" id="accountTime"></td>
				<td align="right" class="l-table-edit-td">交易金额：</td>
				<td align="left" id="accountMoney"></td>
				
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">记账日期：</td>
				<td align="left" id="accountDate"></td>
				<td align="right" class="l-table-edit-td">内部交易流水号：</td>
				<td align="left" id="tradeExternalNo"></td>
				<td align="right" class="l-table-edit-td">商户编号：</td>
				<td align="left" id="storeCode"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">操作人标识：</td>
				<td align="left" id="operatorId"></td>
				<td align="right" class="l-table-edit-td">交易后账户余额：</td>
				<td align="left" id="currentMoney"></td>
				<!-- 
				<td align="right" class="l-table-edit-td">记账分区时间：</td>
				<td align="left" id="accountSubareaTime"></td>
				 -->
				<td align="right" class="l-table-edit-td">外部账户对应账号标识：</td>
				<td align="left" id="externalAccountNo"></td>
			</tr>
			<!-- 
			<tr>
				<td align="right" class="l-table-edit-td">交易后账户余额：</td>
				<td align="left" id="currentMoney"></td>
			</tr>
			 -->
			
		</table>
	</div>
	<div style="display: none"></div>
</body>
</html>