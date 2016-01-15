<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交易管理-交易订单查询</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="order-List.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
.Wdate{
	width:140px;
}
</style>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<input type="hidden" id="accountNo"/>
		<input type="hidden" id="workOrderNo"/>
		<input type="hidden" id="tradeExternalNo"/>
		<div position="center">
			<!--1. 查询条件区 -->
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryForm" method="post" >
					<table style="top: 5px">
						<tr>
							<td width="10%" align="right" nowrap="nowrap"><label for="orderNo">订单号：</label></td>
							<td align="left" nowrap="nowrap"><input name="orderNo" type="text" id="orderNo" ltype="text" style="width:141px"/></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="tradeExternalNo_q">流水号：</label></td>
							<td align="left" nowrap="nowrap"><input name="tradeExternalNo_q" type="text" id="tradeExternalNo_q" ltype="text" style="width:160px"/></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="orderType">科目：</label></td>
							<td align="left" nowrap="nowrap" ><input name="orderType" type="text" id="orderType" ltype="combobox"/></td>
						</tr>
						<tr style="height: 40px">
							<td width="10%" align="right" nowrap="nowrap"><label for="orderStatus">订单状态：</label></td>
							<td align="left" nowrap="nowrap"><input name="orderStatus" type="text" id="orderStatus" ltype="combobox"/></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="accountNo_q">账户号：</label></td>
							<td align="left" nowrap="nowrap"><input name="accountNo_q" type="text" id="accountNo_q" ltype="text" style="width:140px"/></td>
								
							<td width="10%" align="right" nowrap="nowrap">会员编号：</td>
							<td align="left" nowrap="nowrap" ><input name="ownerUserNo" type="text" id="ownerUserNo" ltype="text" style="width:141px"/></td>						
							
						</tr>
						<tr style="height: 40px">
							<td width="10%" align="right" nowrap="nowrap"><label for="workOrderNo_q">业务订单号：</label></td>
							<td align="left" nowrap="nowrap"><input name="workOrderNo_q" type="text" id="workOrderNo_q" ltype="text" style="width:141px"/></td>						
							<td width="10%" align="right" nowrap="nowrap"><label for="accountTimeStart">交易时间：</label></td>
							<td align="left" nowrap="nowrap" colspan="1">
								<input name="accountTimeStart" type="text" id="accountTimeStart" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'accountTimeEnd\')}'})" class="Wdate"/>~
								<input name="accountTimeEnd" type="text" id="accountTimeEnd" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'accountTimeStart\')}'})" class="Wdate"/>
							</td>
							
							
							<td width="20%" align="center" nowrap="nowrap"><input type="button" id="query1" onclick="window.queryList.search();" value="查询" class="l-button l-button-submit" /></td>
							<td align="center" nowrap="nowrap"><input type="button" onclick="window.queryList.reset();" value="重置" class="l-button l-button-submit" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!--2. 列表页面 -->
			<div id="gridList" class="grid"></div>
			<div style="display: none;"></div>
			<div id="excelExoprt-div-id" style="display: none;"></div>
		</div>
	</div>
</body>
</html>