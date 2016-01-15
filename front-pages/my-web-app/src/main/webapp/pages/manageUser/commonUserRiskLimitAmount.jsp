<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>增加风控规则</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="commonUserRiskLimitAmount.js?v=g_version"></script> 
<script type="text/javascript" src="<%=application.getContextPath()%>/js/DateUtil.js"></script>
</head>
<body style="height: 90%; padding: 20px;">
	<div id="showBigImg" onclick="javascript:$(this).hide();" style="position:absolute;display: none; cursor: pointer; width:400px;height: 400px;z-index: 1000;" title="点击关闭大图"></div>	
	<div id="tableDiv" style="overflow: auto; height: 400px;">
	
	<form name="riskForm" method="post" id="riskForm">
			
		<div id="limitGuideLine" style="position:absolute;display: none; cursor: pointer; width:700px; height: 400px;z-index: 1000;">
			<!-- 3 limitGuideLine -->
			<table border="1" width="100%">
				<tr>
					<th>业务类型</th>
					<th>单次金额限制</th>
					<th>单日金额限制</th>
					<th>单日笔数限制</th>
				</tr>
				<tr>
					<td class="l-table-edit-td">扣款</td>
					<td class="l-table-edit-td"><input name="deduct_limit_once" type="text" id="deduct_limit_once" ltype="combobox"/></td>
					<td class="l-table-edit-td"><input name="deduct_limit_daily" type="text" id="deduct_limit_daily" ltype="combobox"/></td>
					<td class="l-table-edit-td"><input name="deduct_limit_daily_times" type="text" id="deduct_limit_daily_times" ltype="combobox"/></td>
				</tr>
				<tr>
					<td class="l-table-edit-td">提现</td>
					<td class="l-table-edit-td"><input name="withdraw_limit_once" type="text" id="withdraw_limit_once" ltype="combobox"/></td>
					<td class="l-table-edit-td"><input name="withdraw_limit_daily" type="text" id="withdraw_limit_daily" ltype="combobox"/></td>
					<td class="l-table-edit-td"><input name="withdraw_limit_daily_times" type="text" id="withdraw_limit_daily_times" ltype="combobox"/></td>
				</tr>	
				<tr>
					<td class="l-table-edit-td">转账</td>
					<td class="l-table-edit-td"><input name="transfer_limit_once" type="text" id="transfer_limit_once" ltype="combobox"/></td>
					<td class="l-table-edit-td"><input name="transfer_limit_daily" type="text" id="transfer_limit_daily" ltype="combobox"/></td>
					<td class="l-table-edit-td"><input name="transfer_limit_daily_times" type="text" id="transfer_limit_daily_times" ltype="combobox"/></td>
				</tr>	
				<tr>
					<td class="l-table-edit-td" align="right">
						状态: 
					</td>
					<td class="l-table-edit-td" colspan="3">
						<input name="acc_risk_status" type="text" id="acc_risk_status" ltype="combobox"/>
					</td>
				</tr>													
			</table>
			
		</div>	
		
	</form>
	
	<form name="form1" method="post" id="form1">
	
	</form>
	</div>
	<div style="display: none"></div>
</body>
</html>