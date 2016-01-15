<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线下流程管理-充值审核申请上传汇款凭证</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="rechargeUpload.js?v=g_version"></script> 
<script type="text/javascript" src="<%=application.getContextPath()%>/js/DateUtil.js"></script>
</head>
<body style="height: 90%; padding: 20px;">
<div id="tableDiv" style="overflow: auto; height: 350px;">
	<form id="uploadForm" name="uploadForm"  action = "<%=application.getContextPath()%>/recharge/upload.action" enctype="multipart/form-data" method="post" targer="screct_frame">
	<iframe name="screct_frame" style="display:none;"></iframe>
		<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
		<tr>
			<td colspan="2">
				<div id="step1"></div>
			</td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>汇款凭证:</td>
			<td align="left" class="l-table-edit-td">
				<input type="file" name="fileName" id="fileName"/>
			</td>
			<!-- <td><input type="button" id="upload" value="上传" name="upload" onclick="window.rechargeUpload.upload();"/></td> -->
		</tr>
	   </table>
	</form>
</div>
<div style="display: none"></div>
</body>
</html>