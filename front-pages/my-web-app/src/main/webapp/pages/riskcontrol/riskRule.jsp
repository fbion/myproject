<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线下流程管理-充值列表页面</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="riskRule.js?v=g_version" type="text/javascript" charset="utf-8"></script>
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
							<td width="10%" align="right" nowrap="nowrap"><label for="riskType">风控指标：</label></td>
							<td align="left" nowrap="nowrap"><input name="riskType" type="text" id="riskType" ltype="combobox"/></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="riskStatus">状态：</label></td>
							<td width="10%" align="left" nowrap="nowrap"><input name="riskStatus" type="text" id="riskStatus" ltype="combobox"/></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="riskTime">时间：</label></td>
							<td align="left" nowrap="nowrap" colspan="5">
								<input name="riskTimeStart" type="text" id="riskTimeStart" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'riskTimeEnd\')}'})" class="Wdate"/>~
							    <input name="riskTimeEnd" type="text" id="riskTimeEnd" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'riskTimeStart\')}'})" class="Wdate"/>
							</td>	
							<td align="right" nowrap="nowrap"><input id="queryBtn" type="button" onclick="window.queryList.search();" value="查询" class="l-button l-button-submit" /></td>
							<td width="10%" align="right" nowrap="nowrap"><input id="queryReset" type="button" onclick="window.queryList.reset();" value="重置" class="l-button l-button-submit" /></td>		
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