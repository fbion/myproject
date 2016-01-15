<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>短信模板</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script>
var listDataStr = '${list}';
</script>
<script type="text/javascript" src="<%=application.getContextPath()%>/pages/sms/index.js"></script>
 </head>
<body style="padding: 4px; overflow: hidden;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div position="center">
			<div id="accordion1" class="conBoxNoBorder">
				<form id="queryParamForm">
						<table style="top: 5px">
							<tr>
								<td width="10%" align="right" nowrap="nowrap"><label for="templateCode">模板编码：</label></td>
								<td width="10%" align="left" nowrap="nowrap"><input name="templateCode" type="text" id="templateCode" class="liger-textbox"  /></td>
								<td width="10%" align="right" nowrap="nowrap"><label for="templateName">模板名称：</label></td>
								<td width="30%" align="left" nowrap="nowrap"><input name="templateName" type="text" id="templateName" class="liger-textbox" /></td>
								<td width="10%" align="right" nowrap="nowrap"><input id="queryParamBtn" type="button" onclick="query();" value="查询" class="l-button l-button-submit" /></td>
								<td width="10%" align="right" nowrap="nowrap"><input id="query1" type="button" onclick="reset();" value="重置" class="l-button l-button-submit" /></td>
							</tr>
						</table>
				</form>
			</div>
			<!-- 列表页面 -->
			<div id="mygrid" style="margin: 0; padding: 0"> </div>
			<div style="display: none;"></div>
		</div>
	</div>
 </body>
</html>
