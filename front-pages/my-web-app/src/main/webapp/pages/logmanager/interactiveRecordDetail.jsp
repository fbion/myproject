<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>详细信息</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/logmanager/interactiveRecordDetail.js" ></script>
</head>
<body style="padding: 4px; overflow: visible;">
	
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">

		<table cellpadding="0" cellspacing="0" class="l-table-edit" align="left">
			<tr>
				<td align="right" class="l-table-edit-td">ID：</td>
				<td id="ID"></td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">创建时间：</td>
				<td id="createTime"></td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">交互发生的日期：</td>
				<td id="saveDate"></td>
			</tr>
			
			<tr >
				<td align="right" class="l-table-edit-td">操作编码：</td>
				<td id="operCode"></td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">商户编码：</td>
				<td id="storeCode"></td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">记录该信息的系统：</td>
				<td id="recordSystem"></td>
			</tr>
			
			<tr >
				<td align="right" class="l-table-edit-td">请求参数：</td>
				<td id="jsonData"></td>
			</tr>
			<tr >
				<td align="right" class="l-table-edit-td">响应数据：</td>
				<td id="returnData"></td>
			</tr>
			
		</table>
	</div>
</body>
</html>
