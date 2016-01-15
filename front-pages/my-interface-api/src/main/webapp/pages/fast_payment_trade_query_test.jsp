<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>运营管理平台－支付中心－一键支付模块交易查询接口测试</title>
</head>

<body style="height: 90%; padding: 20px;">
<fieldset>
<legend>一键支付模块交易查询接口测试,该接口仅用于测试,业务不一定使用.</legend>
	<form name="submitForm" method="post" action="<%=root %>/upp/fastPaymentTradeQueryController/tradeQuery">
		<div id="tableDiv" style="overflow: auto; height:160px;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商户号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="merchantCode" id="merchantCode" ltype="text"  validate="{required:true,minlength:1,maxlength:30}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>业务订单号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="orderId" id="orderId" ltype="text"  validate="{required:true,minlength:1,maxlength:30}" />
					</td>
				</tr>
				<tr>
					<td colspan="5" align="center">
						<input type="submit" value="查询" id="Button1" class="l-button l-button-submit" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</fieldset>

</body>
</html>