<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线下流程管理-扣款列表页面</title>
<jsp:include page="../all_css.jsp"/>
<jsp:include page="../all_js.jsp" />
<script src="payment-list.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
form#queryPaymentForm table>tbody>tr>td{
	padding-bottom: 5px;
}
</style>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div position="center">
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryPaymentForm">
						<table style="top: 5px">
							<tr>
								<td width="10%" align="right" nowrap="nowrap"><label for="ownerUserNo">会员编号：</label></td>
								<td align="left" nowrap="nowrap"><input name="ownerUserNo" type="text" id="ownerUserNo" ltype="text" /> </td>
								<td width="10%" align="right" nowrap="nowrap"><label for="storeId">账户号：</label></td>
								<td align="left" nowrap="nowrap"><input name="storeId" type="text" id="storeId" ltype="combobox" /> </td>
								<!-- <td width="10%" align="right" nowrap="nowrap"><label for="payType">付款类型：</label></td>
								<td align="left" nowrap="nowrap"><input name="payType" type="text" id="payType" ltype="combobox" /></td> -->
								<td width="15%" align="right" nowrap="nowrap"><label for="remitterName">付款人姓名：</label></td>
								<td align="left" nowrap="nowrap"><input name="remitterName" type="text" id="remitterName" ltype="combobox" /> </td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td width="10%" align="right" nowrap="nowrap"><label for="fundsConfirmId">资金确认人：</label></td>
								<td align="left" nowrap="nowrap"><input name="fundsConfirmId" type="text" id="fundsConfirmId" ltype="combobox" /> </td>
								<td width="10%" align="right" nowrap="nowrap"><label for="applyStatus">申请状态：</label></td>
								<td align="left" nowrap="nowrap"><input name="applyStatus" type="text" id="applyStatus" ltype="combobox" /> </td>
								<td width="15%" align="right" nowrap="nowrap"><label for="tradeAmount">扣款金额：</label></td>
								<td align="left" nowrap="nowrap"><input name="tradeAmount" type="text" id="tradeAmount" ltype="combobox" /> </td>
							</tr>
							<tr>
								<td width="10%" align="right" nowrap="nowrap"><label for="applyTime">付款时间：</label></td>
								<td align="left" nowrap="nowrap">
									<input name="applyTime" type="text" id="applyTimeStart" ltype="combobox" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'applyTimeEnd\')}'})" class="Wdate"/>~
									<input name="applyTime" type="text" id="applyTimeEnd" ltype="combobox" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'applyTimeStart\')}'})" class="Wdate"/>
								</td>
								<td width="10%" align="right" nowrap="nowrap"><label for="amountArriveTime">资金到账时间：</label></td>
								<td width="10%" align="left" nowrap="nowrap" colspan="3">
									<input name="amountArriveTime" type="text" id="amountArriveTimeStart" ltype="combobox" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'amountArriveTimeEnd\')}'})" class="Wdate"/>~
									<input name="amountArriveTime" type="text" id="amountArriveTimeEnd" ltype="combobox" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'amountArriveTimeStart\')}'})" class="Wdate"/>
								</td>
								<td width="10%" align="right" nowrap="nowrap">
									<input id="queryParamBtn" type="button" onclick="window.paymentList.search();" value="查询" class="l-button l-button-submit" />
								</td>
								<td width="10%" align="right" nowrap="nowrap">
									<input id="query1" type="button" onclick="window.paymentList.reset();" value="重置" class="l-button l-button-submit" />
								</td>
							</tr>
						</table>
				</form>
			</div>
			<!-- 列表页面 -->
			<div id="paymentGridList" class="grid"></div>
			<div style="display: none;"></div>
		</div>
	</div>
</body>
</html>