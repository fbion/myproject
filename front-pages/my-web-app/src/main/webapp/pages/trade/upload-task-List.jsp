<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>下载任务-列表</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="upload-task-List.js?v=g_version" type="text/javascript" charset="utf-8"></script>
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
				<form id="queryForm" method="post" >
					<table style="top: 5px">
						<tr>
							<td width="10%" align="right" nowrap="nowrap"><label for="orderNo">任务ID：</label></td>
							<td align="left" nowrap="nowrap"><input name="orderNo" type="text" id="taskId" ltype="text" style="width:141px"/></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="tradeExternalNo">任务名称：</label></td>
							<td align="left" nowrap="nowrap"><input name="tradeExternalNo" type="text" id="taskName" ltype="text" style="width:160px"/></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="status">状态：</label></td>
							<td align="left" nowrap="nowrap" colspan="4"><input name="status" type="text" id="status" ltype="combobox"/></td>
						</tr>
						
						<tr style="height: 40px">
							<td width="10%" align="right" nowrap="nowrap"><label for="accountTime">交易时间：</label></td>
							<td align="left" nowrap="nowrap" colspan="4">
								<input name="createTimeStart" type="text" id="createTimeStart" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})" class="Wdate"/>~
								<input name="createTimeEnd" type="text" id="createTimeEnd" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'createTimeStart\')}'})" class="Wdate"/>
							</td>
							<td width="10%" align="left" nowrap="nowrap"><input type="button" id="query1" onclick="window.queryList.search();" value="查询" class="l-button l-button-submit" /></td>
							<td width="5%"></td>
							<td width="10%" align="left" nowrap="nowrap"><input type="button" onclick="window.queryList.reset();" value="重置" class="l-button l-button-submit" /></td>
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