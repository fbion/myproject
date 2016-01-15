<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>组织结构管理</title>

<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />

<script src="../../js/ctfoSelect/orgTreeSelect.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<script src="org-list.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div>
		<div class="l-loading" style="display: block" id="pageloading">
		</div>
		<div id="layout1"
			style="width: 100%; margin: 0 auto; margin-top: 0px;">
			<!-- 左边树 -->
			<div position="left" id="accordion1">
			</div>
			<div position="center">
				<div class="conBoxNoBorder">
					<form id="queryForm">
						<div id="queryOrgDiv" style="top: 10px; border-top: 20px;">
							<input type="hidden" id="orgType" name="orgType" />
							<input type="hidden" id="subPageOrgId" name="subPageOrgId" />
							<table style="top: 10px;">
								<tr>

									<td width="10%" align="right">信息范围：</td>
									<td width="25%"><div id="pTreeIdText"></div> <input
										type="hidden" id="pTreeId" name="pTreeId" /></td>
									<td width="10%" align="right">组织名称：</td>
									<td width="15%"><input name="orgName_find" type="text"
										id="orgName_find" ltype="text" /></td>
									<td width="15%" align="right"><input id="query1"
										type="button" onclick="window.orgList.search();" value="查询"
										class="l-button l-button-submit" /></td>
									<td width="15%" align="right"><input id="queryInfo"
										type="button" onclick="window.orgList.advancedSearch();"
										value="高级查询" class="l-button l-button-submit" /></td>
									<td width="10%" align="right"><input id="resettt"
										type="reset" value="重置" class="l-button l-button-submit" /></td>
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