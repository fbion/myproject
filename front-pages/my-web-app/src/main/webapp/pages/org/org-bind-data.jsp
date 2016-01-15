<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>绑定系统的数据类型</title>

<link href="../../css/all.2.0.css" rel="stylesheet" type="text/css" />

<jsp:include page="../all_js.jsp" />

<script src="org-bind-data.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="overflow: visible; padding: 10px; padding-left: 40px;">
<br/>
<br/>
		<table cellpadding="0" cellspacing="0" class="l-table-edit"	width="760" >
			<tr>
				<td align="right" class="l-table-edit-td">绑定外部系统名称:</td>
				<td align="left" class="l-table-edit-td">
					<span id="systemName" />
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">数据类型:</td>
				<td align="left" class="l-table-edit-td">
					<span id="dataType" />
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">数据内容:</td>
				<td align="left" class="l-table-edit-td">
					<textarea cols="100" rows="12" class="l-textarea" id="content" name="content" style="width: 400px"></textarea>
				</td>
			</tr>
		</table>

</body>
</html>