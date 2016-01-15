<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>扣款审核申请</title>
<jsp:include page="/pages/all_css.jsp" />
<jsp:include page="/pages/all_js.jsp" />
<script type="text/javascript" src="<%=application.getContextPath()%>/pages/deduct/applyfor.js"></script>
 
 </head>

<body>
	<table width="500" border="0" align="center" cellpadding="0"
		cellspacing="0" style="font-size: 12px">
		<tr>
			<td colspan="2" align="center" height="50px">
				<h2>扣款审核申请</h2>
			</td>
		</tr>
		<tr>
			<td width="100" height="40">* 扣款原因：</td>
			<td><input type="text" name="textfield" id="textfield"
				 class="liger-textbox" style="width: 200px" /></td>
		</tr>
		<tr>
			<td width="100" height="40">* 账户编码：</td>
			<td><input type="text" name="textfield2" id="textfield2"
				 class="liger-textbox" style="width: 200px" /></td>
		</tr>
		<tr>
			<td width="100" height="40">* 扣款金额：</td>
			<td><input type="text" name="textfield3" id="textfield3"
				 class="liger-textbox" style="width: 200px" /></td>
		</tr>
		<tr>
			<td><input type="button" id="jsave" value="　提交　" /></td>
			<td width="100" height="40">&nbsp;</td>
		</tr>
	</table>
</body>
</html>
