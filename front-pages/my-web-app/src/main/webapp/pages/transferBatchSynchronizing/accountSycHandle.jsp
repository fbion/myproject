<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交易管理-手工账户同步</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="accountSycHandle.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
.Wdate{
	width:140px;
}
</style>
</head>
<body>
<div class="l-loading" style="display: none" id="pageloading"></div>
<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div position="center">
			<!--1. 查询条件区 -->
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryForm" method="post" >
					<table style="top: 5px">
					
						<tr>
							<td width="10%" align="right" nowrap="nowrap"><label for="accountTimeStart">交易时间：</label></td>
							<td align="left" nowrap="nowrap" colspan="6">
								<input name="accountTimeStart" type="text" id="accountDate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
							</td>
							<td width="10%" align="left" nowrap="nowrap"><input type="button" id="sendSycAccountReq"  value="同步" class="l-button l-button-submit" /></td>
							<td width="10%" align="left" nowrap="nowrap"><input type="button"  value="重置" class="l-button l-button-submit" /></td>
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