<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>系统管理-参数配置管理</title>
<link href="../../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../../all_js.jsp" />
<script src="paramList.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div position="center">
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryParamForm">
						<table style="top: 5px">
							<tr>
								<td width="10%" align="right" nowrap="nowrap"><label for="paramName">参数名称：</label></td>
								<td width="10%" align="left" nowrap="nowrap"><input name="paramName" type="text" id="paramName" ltype="combobox" /> <input id="id" type="hidden" /></td>
								<td width="10%" align="right" nowrap="nowrap"><label for="paramDataType">数据类型：</label></td>
								<td width="30%" align="left" nowrap="nowrap"><input name="paramDataType" type="text" id="paramDataType" ltype="combobox" /></td>
								<td width="10%" align="right" nowrap="nowrap"><input id="queryParamBtn" type="button" onclick="window.paramList.search();" value="查询" class="l-button l-button-submit" /></td>
								<td width="10%" align="left" nowrap="nowrap"><input id="queryInfo" type="button" onclick="window.paramList.advancedSearch();" value="高级查询" class="l-button l-button-submit" /></td>
								<td width="10%" align="right" nowrap="nowrap"><input id="query1" type="button" onclick="window.paramList.reset();" value="重置" class="l-button l-button-submit" /></td>
							</tr>
						</table>
				</form>
			</div>
			<!-- 列表页面 -->
			<div id="paramGridList" class="grid"></div>
			<div style="display: none;"></div>
		</div>
	</div>
</body>
</html>