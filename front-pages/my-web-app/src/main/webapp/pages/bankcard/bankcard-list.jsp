<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账户管理-银行卡查询</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script src="bankcard-list.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div position="center">
			<!--1. 查询条件区 -->
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryBankCardForm">
						<table style="margin: 5px 0px 0px 61px;">
							<tr>
							<td width="10%" align="right" nowrap="nowrap"><label for="accountId">账户号：</label></td>
							<td align="left" nowrap="nowrap"><input name="accountId" type="text" id="accountId" /></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="branchBankName">银行名称：</label></td>
							<td align="left" nowrap="nowrap" colspan="2"><input id="branchBankName" type="text" name="branchBankName" /></td>
						</tr>
						<tr>
							<td width="10%" align="right" nowrap="nowrap"><label for="accCardType">卡类型：</label></td>
							<td align="left" nowrap="nowrap"><input name="accCardType" type="text" id="accCardType" ltype="combobox"/></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="createTimeStart">创建时间：</label></td>
							<td align="left" nowrap="nowrap"><input id="createTimeStart" name="createTimeStart" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" /> -<input name="createTimeEnd" id="createTimeEnd" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" /></td>
							<td width="12%" align="right" nowrap="nowrap"><input type="button" onclick="window.bankCardList.search();" value="查询" class="l-button l-button-submit" /></td>
							<td width="12%" align="left" nowrap="nowrap"><input  type="button" onclick="window.bankCardList.exportExcelAll();" value="导出" class="l-button l-button-submit" /></td>
							<td width="10%" align="right" nowrap="nowrap"><input type="button" onclick="window.bankCardList.reset();" value="重置" class="l-button l-button-submit" /></td>
							</tr>
						</table>
				</form>
			</div>
			<!-- 表格内容  -->
			<div id="bankCardGrid" class="grid"></div>
			<div id="excelExoprt-div-id" style="display: none;"></div>
			<div style="display: none;"></div>
		</div>
	</div>
</body>
</html>
