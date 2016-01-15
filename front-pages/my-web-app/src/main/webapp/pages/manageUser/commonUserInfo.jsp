<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账户管理-普通账户列表</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="commonUserInfo.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="padding: 4px; overflow: hidden;">
<input type="hidden" id="accountNo"/>
<input type="hidden" id="workOrderNo"/>
<input type="hidden" id="tradeExternalNo"/>
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div position="center">
			<!--1. 查询条件区 -->
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryCommonUserInfoForm">
					<table style="top: 5px">
						<tr>
							<td width="10%" align="right" nowrap="nowrap"><label for="insideAccountNo">账户号：</label></td>
							<td align="left" nowrap="nowrap"><input name="insideAccountNo" type="text" id="insideAccountNo" /></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="accountStatus">账户状态：</label></td>
							<td align="left" nowrap="nowrap"><input name="accountStatus" type="text" id="accountStatus" ltype="combobox" /></td>
						</tr>
						<tr>
						    <td width="10%" align="right" nowrap="nowrap"><label for="ownerLoginName">手机号：</label></td>
							<td align="left" nowrap="nowrap"><input id="mobileNo" type="text" name="mobileNo" /></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="beforTime">创建时间：</label></td>
							<td align="left" nowrap="nowrap" colspan="3"><input id="beforTime" name="beforTime" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate" /> -<input name="endTime" id="endTime" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beforTime\')}'})" class="Wdate" /></td>
							<!-- <td width="10%"></td> -->
							<td width="10%" align="right" nowrap="nowrap"><label for="ownerUserNo">会员编号：</label></td>
							<td align="left" nowrap="nowrap"><input id="ownerUserNo" type="text" name="ownerUserNo" /></td>
							<td width="10%"align="left" nowrap="nowrap"><input type="button" id="submitButton" value="查询" onclick="window.accountList.search();" class="l-button l-button-submit" /></td>
							<td width="10%" align="left" nowrap="nowrap"><input id="queryReset" type="button" onclick="window.accountList.reset();" value="重置" class="l-button l-button-submit" /></td>
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
