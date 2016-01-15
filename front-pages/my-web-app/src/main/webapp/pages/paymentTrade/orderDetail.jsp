<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>订单详情</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="orderDetail.js?v=g_version" type="text/javascript" charset="utf-8"></script>
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
				<td align="right" class="l-table-edit-td">订单号：</td>
				<td align="left" id="orderNo"></td>
				<td align="right" class="l-table-edit-td">订单状态：</td>
				<td align="left" id="orderStatus"></td>
				<td align="right" class="l-table-edit-td">科目：</td>
				<td align="left" id="orderType"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">交易流水号：</td>
				<td align="left" id="tradeExternalNo"></td>
					<td align="right" class="l-table-edit-td">所属商户：</td>
				<td align="left" id="storeCode"></td>
				<td align="right" class="l-table-edit-td">转入账户：</td>
				<td align="left" id="accountNo"></td>
				
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">转出账户：</td>
				<td align="left" id="collectMoneyAccountNo"></td>
				<td align="right" class="l-table-edit-td">交易金额：</td>
				<td align="left" id="orderAmount"></td>
				<td align="right" class="l-table-edit-td">交易时间：</td>
				<td align="left" id="createTime"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">支付平台：</td>
				<td align="left" id="serviceProviderCode"></td>
				<td align="right" class="l-table-edit-td">转入账户所属：</td>
				<td align="left" id="inOwnerLoginName"></td>
				<td align="right" class="l-table-edit-td">转出账户所属：</td>
				<td align="left" id="outOwnerLoginName"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">业务订单号：</td>
				<td align="left" id="workOrderNo"></td>
				<td align="right" class="l-table-edit-td">付款用户ID：</td>
				<td align="left" id="userId"></td>
				<td align="right" class="l-table-edit-td">收款用ID：</td>
				<td align="left" id="collectMoneyUserId"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">终端类型：</td>
				<td align="left" id="clentType"></td>
				<td align="right" class="l-table-edit-td">付款渠道：</td>
				<td align="left" id="payChannel"></td>
				<td align="right" class="l-table-edit-td">商品编码：</td>
				<td align="left" id="productCatalog"></td>
			</tr>	
			<tr>
				<td align="right" class="l-table-edit-td">商品名称：</td>
				<td align="left" id="productName"></td>
				<td align="right" class="l-table-edit-td">CLENT_ID：</td>
				<td align="left" id="clentId"></td>
				<td align="right" class="l-table-edit-td">终端UA：</td>
				<td align="left" id="userUa"></td>
			</tr>	
			<tr>
				<td align="right" class="l-table-edit-td">用户IP：</td>
				<td align="left" id="userIp"></td>
				<td align="right" class="l-table-edit-td">用户标识：</td>
				<td align="left" id="identityId"></td>
				<td align="right" class="l-table-edit-td">用户标识类型：</td>
				<td align="left" id="identityType"></td>
			</tr>									
			<tr>
				<td align="right" class="l-table-edit-td">商户后台回调地址：</td>
				<td align="left" id="callbackUrl"></td>
				<td align="right" class="l-table-edit-td">商户前台回调地址：</td>
				<td align="left" id="fCallbackUrl"></td>
				<td align="right" class="l-table-edit-td">付款确认时间：</td>
				<td align="left" id="payConfirmDate"></td>
			</tr>	
			<tr>
				<td align="right" class="l-table-edit-td">是否结算：</td>
				<td align="left" id="isClearing"></td>
			</tr>
			
		</table>
	</div>
	<div style="display: none"></div>
</body>
</html>