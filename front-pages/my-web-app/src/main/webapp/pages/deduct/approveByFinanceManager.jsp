<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>扣款申请审批--财务经理审批</title>
<jsp:include page="/pages/all_css.jsp" />
<jsp:include page="/pages/all_js.jsp" />
<script type="text/javascript"
	src="<%=application.getContextPath()%>/pages/deduct/approveByFinanceManager.js"></script>

</head>

<body>
	<table width="800" border="0" align="center" cellpadding="0"
		cellspacing="0">
		<tr>
			<td height="50" align="center"><h2>扣款申请</h2></td>
		</tr>
		<tr>
			<td><table width="800" border="0" align="center" cellpadding="0"
					cellspacing="0" style="font-size: 12px">
					<tr>
						<td width="100" height="30" align="center">扣款原因：</td>
						<td width="200">&nbsp;&nbsp;&nbsp;&nbsp;收取管理费</td>
						<td width="100" style="text-align: center">&nbsp;&nbsp;&nbsp;&nbsp;账户编码：</td>
						<td width="400">&nbsp;&nbsp;&nbsp;&nbsp;PT1009998011(车主A),PT1009998012(货主B),PT1009998013(车主C)</td>
					</tr>
					<tr>
						<td height="30" align="center">扣款金额：</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;200元</td>
						<td style="text-align: center">申 &nbsp;请 &nbsp;人：</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;业务员</td>
					</tr>
					<tr>
						<td height="30" align="center">申请时间：</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;2014-08-31 09:20:29</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td height="50" align="center"><h2>业务经理-审批&nbsp;</h2></td>
		</tr>
		<tr>
			<td height="50" align="center"><table width="800" border="0"
					align="center" cellpadding="0" cellspacing="0"
					style="font-size: 12px">
					<tr>
						<td width="100" height="30" align="center">意见：</td>
						<td width="200">&nbsp;&nbsp;&nbsp;&nbsp;同意</td>
						<td width="100" style="text-align: center">审核意见或建议：</td>
						<td width="400">&nbsp;&nbsp;&nbsp;&nbsp;同意扣款</td>
					</tr>
					<tr>
						<td height="30" align="center">审批人：</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;200元</td>
						<td style="text-align: center">审批时间：</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;2014-08-31 10:20:29</td>
					</tr>
				</table></td>
		</tr>
		<tr>
			<td height="50" align="center"><h2>财务经理-审批</h2></td>
		</tr>
		<tr>
			<td><table border="0" align="center" cellpadding="0"
					cellspacing="0" width="100%" style="font-size: 12px">
					<tr>
						<td align="center">意见：</td>
						<td height="30" colspan="3">&nbsp;&nbsp;&nbsp;&nbsp; <input
							type="radio" name="radio" id="radio" value="radio"> 同意
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="radio"
							id="radio2" value="radio"> 不同意
						</td>
					</tr>
					<tr>
						<td align="center">审核意见或建议：</td>
						<td height="30" colspan="3"><textarea name="textarea"
								id="textarea" cols="80" rows="8"></textarea></td>
					</tr>
					<tr>
						<td width="100" height="50" align="center"><input
							type="button" id="jsave" value="　提交　" /></td>
						<td height="30" colspan="3">&nbsp;</td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>
