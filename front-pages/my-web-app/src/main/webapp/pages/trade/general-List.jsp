<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账户管理-普通账户交易流水页面</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="general-List.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
.Wdate{
	width:140px;
}
</style>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div position="center">
			<!--1. 查询条件区 -->
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryForm">
					<table style="top: 5px">
						<tr>
							<td width="10%" align="right" nowrap="nowrap"><label for="TRADEEXTERNALNO">流水号：</label></td>
							<td align="left" nowrap="nowrap"><input name="TRADEEXTERNALNO" type="text" id="TRADEEXTERNALNO" /></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="BOOKACCOUNTTYPE">记账类型：</label></td>
							<td align="left" nowrap="nowrap"><input name="BOOKACCOUNTTYPE" type="text" id="BOOKACCOUNTTYPE" ltype="combobox"/></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="ACCOUNTSUBJECT">科目：</label></td>
							<td align="left" nowrap="nowrap" colspan="2"><input name="ACCOUNTSUBJECT" type="text" id="ACCOUNTSUBJECT" ltype="combobox"/></td>
						</tr>
						<tr style="height: 40px">
							<td width="10%" align="right" nowrap="nowrap"><label for="INSIDEACCOUNTNO">账户号：</label></td>
							<td align="left" nowrap="nowrap"><input name="INSIDEACCOUNTNO" type="text" id="INSIDEACCOUNTNO" /></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="ownerUserNo">会员编号：</label></td>
							<td align="left" nowrap="nowrap"><input name="ownerUserNo" type="text" id="ownerUserNo" /></td>
							<td width="15%" align="right" nowrap="nowrap" ><label for="APPLYTIMESTART">交易时间：</label></td>
							<td align="left" nowrap="nowrap" colspan="4"><input name="ACCOUNTTIMESTART" type="text" id="ACCOUNTTIMESTART" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'ACCOUNTTIMEEND\')}'})" class="Wdate"/>~<input name="ACCOUNTTIMEEND" type="text" id="ACCOUNTTIMEEND" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'ACCOUNTTIMESTART\')}'})" class="Wdate"/></td>
							
							
							<td width="10%" align="left" nowrap="nowrap"><input type="button" onclick="window.queryList.search();" value="查询" class="l-button l-button-submit" /></td>
							<!-- <td width="10%" align="left" nowrap="nowrap"><input type="button" onclick="window.queryList.exportExcelAll();" value="导出全部" class="l-button l-button-submit" /></td>	 -->
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
</body>
</html>