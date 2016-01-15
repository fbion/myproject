<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账户管理-普通账户列表</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="merchant-list.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
.l-selected .l-grid-row-cell,.l-selected{
	color:white;
    background:#0066CC;
}
</style>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div position="center">
			<!--1. 查询条件区 -->
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryCommonUserInfoForm">
					<table style="top:2px">
						<tr>
							<td width="8%" align="right" nowrap="nowrap"><label for="insideAccountNo">账户号：</label></td>
							<td align="left" nowrap="nowrap"><input name="insideAccountNo" type="text" id="insideAccountNo" /></td>
							<td align="right" nowrap="nowrap"><label for="ownerLoginName">手机号：</label></td>
							<td align="left" nowrap="nowrap"><input id="mobileNo" type="text" name="mobileNo" /></td>
						</tr>
						<tr>
							<td width="8%" align="right" nowrap="nowrap"><label for="beforTime">创建时间：</label></td>
							<td align="left" nowrap="nowrap"><input id="beforTime" name="beforTime" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" /> -<input name="endTime" id="endTime" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" /></td>
							<td width="8%"align="left" nowrap="nowrap"><input type="button" id="submitButton" value="查询" onclick="window.accountList.search();" class="l-button l-button-submit" /></td>
							<td width="8%" align="left" nowrap="nowrap"><input id="queryReset" type="button" onclick="window.accountList.reset();" value="重置" class="l-button l-button-submit" /></td>
						</tr>
					</table>
				</form>
			</div>
			<!--2. 列表页面 -->
			<div id="grid"></div>
			<div style="display: none;"></div>
		</div>
	</div>
</body>
</html>
