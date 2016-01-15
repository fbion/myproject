<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>码表管理</title>
<jsp:include page="../../all_css.jsp"/>
<jsp:include page="../../all_js.jsp"/>
<script src="<%=application.getContextPath()%>/js/ctfoSelect/treeSelect.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<script src="code-list.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 4px;">
		<!-- 左边树 -->
		<div position="left" title="左边树形" id="accordion1"></div>
		<div position="center">
			<div id="accordion1" class="conBoxNoBorder">
				<form1 id="queryCodeForm">
					<div id="queryCodeDiv" style="top: 10px; border-top: 20px;">
						<table style="top: 5px;">
							<tr>
								<td width="8%" align="right">编码类型：</td>
								<td width="18%"><span id="pCodeName"></span>
								 <input id="pCodeId" type="hidden" /> <input id="pCode" type="hidden" />
								</td>
								<td width="10%" align="right">编码值：</td>
								<td width="10%"><input name="code" type="text" id="code" /></td>
								<td width="10%" align="right">编码名称：</td>
								<td width="10%"><input name="name" type="text"
									id="codeName" /></td>
								<td width="10%" align="right"><input id="query1"
									type="button" onclick="window.codeList.search();"
									value="查询" class="l-button l-button-submit" /></td>
								<td width="10%" align="right"><input id="queryInfo"
									type="button" onclick="window.codeList.advancedSearch();"
									value="高级查询" class="l-button l-button-submit" /></td>
								<td width="10%" align="left"><input id="resettt"
									type="button" onclick="window.codeList.reset();"
									value="重置" class="l-button l-button-submit" /></td>
								<td width="10%"></td>
							</tr>
						</table>
					</div>
				</form1>
			</div>
			<!-- 表格内容  -->
			<div id="codeGrid" class="grid"></div>
		</div>
	</div>
</body>
</html>
