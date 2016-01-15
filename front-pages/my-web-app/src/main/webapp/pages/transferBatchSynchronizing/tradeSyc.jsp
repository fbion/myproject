<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交易管理-支付交易查询</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="tradeSyc.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
.Wdate{
	width:140px;
}
</style>
</head>
<body style="padding: 4px; overflow: hidden;">
	<!--div class="l-loading" style="display: block" id="pageloading"></div-->
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div position="center">
			<!--1. 查询条件区 -->
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryForm" method="post" >
					<table style="top: 5px">
						<tr>
							<td width="10%" align="right" nowrap="nowrap"><label for="insideAccountNo">交易流水号：</label></td>
							<td align="left" nowrap="nowrap"><input name="tradeNo" type="text" id="tradeNo" ltype="text" style="width:141px"/></td>
							<td width="10%" align="left" nowrap="nowrap"><input type="button" id="query1" onclick="window.queryList.sync();" value="同步" class="l-button l-button-submit" /></td>
							<td width="10%" align="left" nowrap="nowrap"><input type="button" onclick="window.queryList.reset();" value="重置" class="l-button l-button-submit" /></td>
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
	<div class="l-grid2" style="width: 1553px; left: 27px;"> 
		<div class="l-grid-header-inner" style="width: 1568px;">
			<table class="l-grid-header-table" cellpadding="0" cellspacing="0">
				<tbody>
					<tr class="l-grid-hd-row">
						<td class="l-grid-hd-cell" id="gridList|hcell|c102" columnindex="1" columnname="tradeExternalNo" style="height: 28px; width: 188px;"><div class="l-grid-hd-cell-inner" style="height: 28px;"><span class="l-grid-hd-cell-text">流水号</span></div></td>
						<td class="l-grid-hd-cell" id="gridList|hcell|c103" columnindex="2" columnname="tradeType" style="height: 28px; width: 94px; cursor: default;"><div class="l-grid-hd-cell-inner" style="height: 28px;"><span class="l-grid-hd-cell-text">科目</span></div></td>
						<td class="l-grid-hd-cell" id="gridList|hcell|c104" columnindex="3" columnname="accountNo" style="height: 28px; width: 188px;"><div class="l-grid-hd-cell-inner" style="height: 28px;"><span class="l-grid-hd-cell-text">转出账户</span></div></td>
						<td class="l-grid-hd-cell" id="gridList|hcell|c105" columnindex="4" columnname="collectMoneyAccountNo" style="height: 28px; width: 188px;"><div class="l-grid-hd-cell-inner" style="height: 28px;"><span class="l-grid-hd-cell-text">转入账户</span></div></td>
						<td class="l-grid-hd-cell" id="gridList|hcell|c106" columnindex="5" columnname="orderAmount" style="height: 28px; width: 125px;"><div class="l-grid-hd-cell-inner" style="height: 28px;"><span class="l-grid-hd-cell-text">交易金额（元）</span></div></td>
						<td class="l-grid-hd-cell" id="gridList|hcell|c107" columnindex="6" columnname="payConfirmDate" style="height: 28px; width: 157px;"><div class="l-grid-hd-cell-inner" style="height: 28px;"><span class="l-grid-hd-cell-text">交易时间</span></div></td>
						<td class="l-grid-hd-cell" id="gridList|hcell|c108" columnindex="7" columnname="tradeStatus" style="height: 28px; width: 125px;"><div class="l-grid-hd-cell-inner" style="height: 28px;"><span class="l-grid-hd-cell-text">交易状态</span></div></td>
						<td class="l-grid-hd-cell" id="gridList|hcell|c109" columnindex="8" columnname="isClearing" style="height: 28px; width: 188px;"><div class="l-grid-hd-cell-inner" style="height: 28px;"><span class="l-grid-hd-cell-text">交易同步状态</span></div></td>
						<td class="l-grid-hd-cell l-grid-hd-cell-last" id="gridList|hcell|c110" columnindex="9" columnname="externalNo" style="height: 28px; width: 266px; cursor: default;"><div class="l-grid-hd-cell-inner" style="height: 28px;"><span class="l-grid-hd-cell-text">外部流水</span></div></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="l-grid-body l-grid-body2 l-scroll" style="height: 464px;">
			<div class="l-grid-body-inner" style="width: 1528px;">
				<table class="l-grid-body-table" cellpadding="0" cellspacing="0">
						<tbody>
							<tr id="gridList|2|r1001" class="l-grid-row l-grid-row-over">
								<td id="gridList|2|r1001|c102" class="l-grid-row-cell " style="width:188px;  "><div class="l-grid-row-cell-inner" style="width:180px;height:28px;min-height:28px; text-align:left;"><a href="javascript:window.queryList.detail('BN20150210055308144572')" class="a-line" title="点我查看详细信息">BN20150210055308144572</a></div></td>
								<td id="gridList|2|r1001|c103" class="l-grid-row-cell " style="width:94px;  "><div class="l-grid-row-cell-inner" style="width:86px;height:28px;min-height:28px; text-align:center;">转账(付款)</div></td>
								<td id="gridList|2|r1001|c104" class="l-grid-row-cell " style="width:188px;  "><div class="l-grid-row-cell-inner" style="width:180px;height:28px;min-height:28px; text-align:left;">MA1423483735703382</div></td>
								<td id="gridList|2|r1001|c105" class="l-grid-row-cell " style="width:188px;  "><div class="l-grid-row-cell-inner" style="width:180px;height:28px;min-height:28px; text-align:left;">MA1423143523675161</div></td>
								<td id="gridList|2|r1001|c106" class="l-grid-row-cell " style="width:125px;  "><div class="l-grid-row-cell-inner" style="width:117px;height:28px;min-height:28px; text-align:right;">0.01</div></td>
								<td id="gridList|2|r1001|c107" class="l-grid-row-cell " style="width:157px;  "><div class="l-grid-row-cell-inner" style="width:149px;height:28px;min-height:28px; text-align:center;">2015-02-10 05:53:08</div></td>
								<td id="gridList|2|r1001|c108" class="l-grid-row-cell " style="width:125px;  "><div class="l-grid-row-cell-inner" style="width:117px;height:28px;min-height:28px; text-align:center;">支付成功</div></td>
								<td id="gridList|2|r1001|c109" class="l-grid-row-cell " style="width:188px;  "><div class="l-grid-row-cell-inner" style="width:180px;height:28px;min-height:28px; text-align:center;">已同步</div></td>
								<td id="gridList|2|r1001|c110" class="l-grid-row-cell l-grid-row-cell-last " style="width:266px;  "><div class="l-grid-row-cell-inner" style="width:258px;height:28px;min-height:28px; text-align:center;"></div></td>
							</tr>
						</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>