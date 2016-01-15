<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>扣款申请新增页面</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="payment-add.js?v=g_version" charset="utf-8"></script>
<style type="text/css">
.Wdate{
	height: 19px;
}
.l-text-field{
	width: 177px;
}
</style>
</head>
<body style="height: 90%; padding: 20px;">
	<div id="tableDiv" style="overflow: auto; height: 350px;">
		<form name="form1" method="post" id="form1">
			<input type="hidden" id="id" name="id" />
			<input type="hidden" id="ownerUserNo" name="ownerUserNo"/>
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td colspan="4">
						<div id="step1"></div>
					</td>
				</tr>
				<tr>
					<!-- <td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>付款时间:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="applyTime" id="applyTime" validate="{required:true,minlength:1,maxlength:30}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" style="width: 177px;" /></td> -->
				<!-- 	<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>资金到账时间:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="amountArriveTime" id="amountArriveTime" ltype="text" validate="{required:true,minlength:1,maxlength:30}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" /></td> -->
				</tr>
				<!-- <tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>资金确认人:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="fundsConfirmId" id="fundsConfirmId" ltype="text" validate="{required:true,minlength:1,maxlength:30}" /></td>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>资金确认时间:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="fundsConfirmTime" id="fundsConfirmTime" ltype="text" validate="{required:true,minlength:1,maxlength:30}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" /></td>
				</tr> -->
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>扣款金额(元):</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="tradeAmount" id="tradeAmount" ltype="number" validate="{required:true}" /></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>账户号:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="insideAccountNo" id="insideAccountNo" ltype="combobox" validate="{required:true}" /></td>
				</tr>
				<tr>
					<!-- <td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>付款人:</td>
					<td align="left" class="l-table-edit-td"><input type="text" name="remitterName" id="remitterName" ltype="text" validate="{required:true}" /></td> -->
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>扣款原因:</td>
					<td align="left" class="l-table-edit-td"><textarea type="text" name="description" id="description" ltype="text" validate="{required:true}" style="width: 332px;height:93px "></textarea></td>
				</tr>
			</table>
		</form>
	</div>
	<div style="display: none"></div>
</body>
</html>