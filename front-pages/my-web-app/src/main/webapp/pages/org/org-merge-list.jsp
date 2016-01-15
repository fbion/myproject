<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>组织合并任务页面</title>

<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />

<script src="org-merge-list.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div>
		<div class="l-loading" style="display: block" id="pageloading">
		</div>
		<div id="layout1"	style="width: 100%; margin: 0 auto; margin-top: 0px;">
			<div position="center">
				<div class="conBoxNoBorder">
					<form id="queryForm">
						<div id="queryOrgDiv" style="top: 10px; border-top: 20px;">
							<table style="top: 10px;">
								<tr>
									<td width="10%" align="right">任务名称：</td>
									<td width="25%"> <input	type="text" id="taskName" name="taskName" /></td>
									<td width="10%" align="right">状态：</td>
									<td width="15%"><input name="taskStatus" type="text" id="taskStatus" ltype="text" /></td>
									<td width="15%" align="right"><input id="query1" 	type="button" onclick="window.orgMergeList.search();" value="查询" class="l-button l-button-submit" /></td>
									<td width="15%" align="right"><input id="queryInfo" type="button" onclick="window.orgMergeList.advancedSearch();" 	value="高级查询" class="l-button l-button-submit" /></td>
									<td width="15%" align="right"><input id="reset" 	type="button" onclick="window.orgMergeList.resetText();" 	value="重置" class="l-button l-button-submit" /></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
				<!-- 表格内容的DIV  -->
				<div id="orgGridList" class="grid"></div>
				<div style="display: none;"></div>
			</div>
		</div>
	</div>
</body>
</html>