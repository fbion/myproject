<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>普通账户列表-解冻金额</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="unfreezeAmount.js?v=g_version"></script> 
</head>
<body style="height: 90%; padding: 20px;">
	<form name="form1" method="post" id="form1">
		<div id="tableDiv" style="overflow: auto; height: 350px;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td colspan="2">
						<div id="step1"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>解冻金额:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="orderAmount" id="orderAmount" ltype="text" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>账户编号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="accountNo" id="accountNo" ltype="text" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>业务订单号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="workOrderNo" id="workOrderNo" ltype="text" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>交易流水号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="tradeExternalNo" id="tradeExternalNo" ltype="text" validate="{required:true}" />
					</td>
				</tr>
						
			</table>
		</div>
	</form>
	<div style="display: none"></div>
</body>
</html>