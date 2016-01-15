<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交易订单详情</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="paymentDetail.js?v=g_version" type="text/javascript" charset="utf-8"></script>
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
				<td align="right" class="l-table-edit-td">创建分区时间：</td>
				<td align="left" id="createSubareaTime"></td>
				<td align="right" class="l-table-edit-td">创建时间：</td>
				<td align="left" id="createTime"></td>
				<td align="right" class="l-table-edit-td">支付机构确认时间：</td>
				<td align="left" id="payConfirmDate"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">交易金额：</td>
				<td align="left" id="orderAmount"></td>
					<td align="right" class="l-table-edit-td">支付手续费：</td>
				<td align="left" id="payPoundScale"></td>
				<td align="right" class="l-table-edit-td">支付平台标识：</td>
				<td align="left" id="serviceProviderCode"></td>
				
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">支付通道：</td>
				<td align="left" id="payChannel"></td>
				<td align="right" class="l-table-edit-td">支付流水号：</td>
				<td align="left" id="externalNo"></td>
				<td align="right" class="l-table-edit-td">支付结算状态：</td>
				<td align="left" id="paySettleStatus"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">支付对账状态：</td>
				<td align="left" id="payCheckStatus"></td>
				<td align="right" class="l-table-edit-td">交易状态：</td>
				<td align="left" id="tradeStatus"></td>
				<td align="right" class="l-table-edit-td">批次ID：</td>
				<td align="left" id="payId"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">银行编码：</td>
				<td align="left" id="bankCode"></td>
				<td align="right" class="l-table-edit-td">银行名称：</td>
				<td align="left" id="bankName"></td>
				<td align="right" class="l-table-edit-td">支付方式：</td>
				<td align="left" id="paymentType"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">内部交易流水号：</td>
				<td align="left" id="tradeExternalNo"></td>
				<td align="right" class="l-table-edit-td">交易类型：</td>
				<td align="left" id="tradeType"></td>
				<td align="right" class="l-table-edit-td">记账状态：</td>
				<td align="left" id="bookAccountStatus"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">支付平台订单号：</td>
				<td align="left" id="payOrderId"></td>
				<td align="right" class="l-table-edit-td">卡类型：</td>
				<td align="left" id="cardType"></td>
				<td align="right" class="l-table-edit-td">付款内部账号：</td>
				<td align="left" id="accountNo"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">收款内部账号：</td>
				<td align="left" id="collectMoneyAccountNo"></td>
				<td align="right" class="l-table-edit-td">提现银行卡号：</td>
				<td align="left" id="bankCardNo"></td>
				<td align="right" class="l-table-edit-td">批次内序号：</td>
				<td align="left" id="batchInternalNo"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">终端类型：</td>
				<td align="left" id="clentType"></td>
			</tr>
			
		</table>
	</div>
	<div style="display: none"></div>
</body>
</html>