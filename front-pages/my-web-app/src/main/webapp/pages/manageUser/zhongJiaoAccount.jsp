<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账户管理-中交账户列表</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="zhongJiaoAccount.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div position="center">
			<!--1. 查询条件区 -->
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryCommonUserInfoForm">
					<table style="margin: 5px 0px 0px 61px;">
						<tr>
							<td width="10%" align="right" nowrap="nowrap"><label for="insideAccountNo">账户号：</label></td>
							<td align="left" nowrap="nowrap"><input name="insideAccountNo" type="text" id="insideAccountNo" /></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="ownerLoginName">账户名称：</label></td>
							<td align="left" nowrap="nowrap"><input id="ownerLoginName" type="text" name="ownerLoginName" /></td>
						</tr>
						<tr>
							<td width="10%" align="right" nowrap="nowrap"><label for="totalBalance">账户总余额：</label></td>
							<td align="left" nowrap="nowrap"><input name="totalBalance" type="text" id="totalBalance" /></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="beforTime">创建时间：</label></td>
							<td align="left" nowrap="nowrap"><input id="beforTime" name="beforTime" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate" /> -<input name="endTime" id="endTime" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beforTime\')}'})" class="Wdate" /></td>
							<td width="12%"align="left" nowrap="nowrap"><input type="button" id="submitButton" value="查询" onclick="window.zjaccountList.search();" class="l-button l-button-submit" /></td>
							<!-- <td width="12%" align="left" nowrap="nowrap"><input type="button" id="ExportButton" value="导出全部" onclick="window.zjaccountList.exportAllAccount()" class="l-button l-button-submit" /></td> -->
							<td width="12%" align="left" nowrap="nowrap"><input id="queryReset" type="button" onclick="window.zjaccountList.reset();" value="重置" class="l-button l-button-submit" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!--2. 列表页面 -->
			<div id="grid"></div>
			<div id="excelExoprt-div-id" style="display: none;"></div>
			<div style="display: none;"></div>
		</div>
	</div>
</body>
</html>
