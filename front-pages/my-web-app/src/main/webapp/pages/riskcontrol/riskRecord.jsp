<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>交易管理-交易订单查询</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="riskRecord.js?v=g_version" type="text/javascript" charset="utf-8"></script>
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
							<td width="60px" align="right" nowrap="nowrap">预警时间:</td>
							<td width="400px" colspan="3" align="left" nowrap="nowrap">
								<input type="text" id="starttime" style="height: 25px; width: 150px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>-
								<input type="text" id="endtime" style="height: 25px; width: 150px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
							</td>
							<td width="60px" align="right" nowrap="nowrap">处理状态:</td>
							<td width="60px">
								<td align="left" nowrap="nowrap"><input name="processStatus" type="text" id="processStatus" ltype="combobox"/></td>
							</td>
							<td width="10%" align="left" nowrap="nowrap"><input type="button" id="query1" onclick="window.recordList.search();" value="查询" class="l-button l-button-submit" /></td>
							<td width="10%" align="left" nowrap="nowrap"><input type="button" onclick="window.recordList.reset();" value="重置" class="l-button l-button-submit" /></td>
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