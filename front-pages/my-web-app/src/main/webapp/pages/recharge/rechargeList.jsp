<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线下流程管理-充值列表页面</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="rechargeList.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div position="center">
			<!--1. 查询条件区 -->
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryForm">
					<table style="top: 5px" border="0">
						<tr>
							<td align="right" nowrap="nowrap"><label for="storeId">账户号：</label></td>
							<td align="left" nowrap="nowrap" colspan="2"><input name="storeId" type="text" id="storeId" /></td>
							<td align="right" nowrap="nowrap"><label for="tradeAmount">充值金额：</label></td>
							<td align="left" nowrap="nowrap"><input name="tradeAmount" type="text" id="tradeAmount"/></td>
							<td align="right" nowrap="nowrap"><label for="payType">付款方式：</label></td>
							<td align="left" nowrap="nowrap"><input name="payType" type="text" id="payType" ltype="combobox"/></td>
							<td></td>
							<td></td>
						</tr>
						<tr style="line-height: 30px">
							<td align="right" nowrap="nowrap"><label for="remitterBankCode">付款人开户行：</label></td>
							<td align="left" nowrap="nowrap" colspan="2"><input name="remitterBankCode" type="text" id="remitterBankCode" ltype="combobox"/></td>
							<td align="right" nowrap="nowrap"><label for="remitterBankcardNo">付款人银行卡号：</label></td>
							<td align="left" nowrap="nowrap"><input name="remitterBankcardNo" type="text" id="remitterBankcardNo"/></td>
							<td align="right" nowrap="nowrap"><label for="applyStatus">申请状态：</label></td>
							<td align="left" nowrap="nowrap"><input name="applyStatus" type="text" id="applyStatus" ltype="combobox"/></td>
							<td></td>
							<td></td>
						</tr>
						<tr style="line-height: 30px">
							<td align="right" nowrap="nowrap"><label for="ownerUserNo">会员编号：</label></td>
							<td align="left" nowrap="nowrap">
								<input name="ownerUserNo" type="text" id="ownerUserNo" ltype="text"/>
							</td>
							<td align="right" nowrap="nowrap"><label for="incomeChannel">来款渠道：</label></td>
							<td align="left" nowrap="nowrap">
								<input name="incomeChannel" type="text" id="incomeChannel" ltype="combobox"/>
							</td>	
							<td>
								<input name="incomeChannelSub" type="text" id="incomeChannelSub" ltype="combobox"/>
							</td>
							<td width="10%" align="right" nowrap="nowrap"><label for="applyTimeStart">充值时间：</label></td>
							<td align="left" nowrap="nowrap" colspan="3">
								<input name="applyTimeStart" type="text" id="applyTimeStart" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'applyTimeEnd\')}'})" class="Wdate"/>~
							    <input name="applyTimeEnd" type="text" id="applyTimeEnd" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'applyTimeStart\')}'})" class="Wdate"/>
							</td>
								
							
							
							<td align="right" nowrap="nowrap"><input id="queryBtn" type="button" onclick="window.queryList.search();" value="查询" class="l-button l-button-submit" /></td>
							<td width="10%" align="right" nowrap="nowrap"><input id="queryReset" type="button" onclick="window.queryList.reset();" value="重置" class="l-button l-button-submit" /></td>
							<td></td>
							<td></td>
							<!-- 
							<td width="10%" align="right" nowrap="nowrap"><label for="payWay">付款方式：</label></td>
							<td align="left" nowrap="nowrap" >
								<input name="payWay" type="text" id="payWay" ltype="combobox"/>
							</td>	
							 -->					
						</tr>
					</table>
				</form>
			</div>
			<!--2. 列表页面 -->
			<div id="gridList" class="grid"></div>
			<div style="display: none;"></div>
		</div>
	</div>
</body>
</html>