<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ctfo.upp.util.Converter"%>
<%@page import="com.ctfo.upp.view.beans.Index"%>	
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
    String root = request.getContextPath();
    Index index = (Index)session.getAttribute(Converter.SESSION_INDEX);
%> 
<title>增加风控规则</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />

<script type="text/javascript" src="riskAdd.js?v=g_version"></script> 
<script type="text/javascript" src="<%=application.getContextPath()%>/js/DateUtil.js"></script>
<script type="text/javascript">
	var INDEX_USER_NAME="<%=index.getUserName()%>";
</script>
</head>
<body style="height: 90%; padding: 20px;">
	<div id="showBigImg" onclick="javascript:$(this).hide();" style="position:absolute;display: none; cursor: pointer; width:400px;height: 400px;z-index: 1000;" title="点击关闭大图"></div>	
	<div id="tableDiv" style="overflow: auto; height: 400px;">
	
	<form name="riskForm" method="post" id="riskForm">
	
	<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
		<tr>
			<td width="10%" align="right" nowrap="nowrap"><label for="riskType">风控指标：</label></td>
			<td align="left" nowrap="nowrap"><input onchange="changeGuideLine();" name="riskType" type="text" id="riskType" ltype="combobox"/></td>
		</tr>
	</table>
	
	
		<div id="ipGuideLine" style="position:absolute;display: none; cursor: pointer; width:400px;height: 400px;z-index: 1000;">
			<!-- 1 ipGuideLine -->
			<table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td">风控类型:</td>
					<td class="l-table-edit-td">事后</td>
					<td class="l-table-edit-td"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">IP地域:</td>
					<td class="l-table-edit-td"><input class="ip" name="ipArea" type="text" id="ipArea" ltype="combobox"/></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">当日请求数阀值:</td>
					<td class="l-table-edit-td"><input class="ip" name="ipReqDaily" type="text" id="ipReqDaily" ltype="combobox"/></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">处理办法:</td>
					<td class="l-table-edit-td"><input class="ip" name="ipProcessMethod" type="text" id="ipProcessMethod" ltype="combobox"/></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">状态:</td>
					<td class="l-table-edit-td"><input class="ip" name="ipRiskStatus" type="text" id="ipRiskStatus" ltype="combobox"/></td>
				</tr>																
			</table>
		</div>
			
		<div id="limitGuideLine" style="position:absolute;display: none; cursor: pointer; width:700px; height: 400px;z-index: 1000;">
			<!-- 3 limitGuideLine -->
			<table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td">风控类型:</td>
					<td class="l-table-edit-td">事中</td>
				</tr>
				<tr>
					<td class="l-table-edit-td" colspan="2">同账号额度限制</td>
				</tr>
				<tr>
					<td class="l-table-edit-td" colspan="2" width="100%">
						<table border="1" width="100%">
							<tr>
								<th>业务类型</th>
								<th>单次金额限制</th>
								<th>单日金额限制</th>
								<th>单日笔数限制</th>
							</tr>
							<tr>
								<td class="l-table-edit-td">扣款</td>
								<td class="l-table-edit-td"><input class="amount" name="deduct_limit_once" type="text" id="deduct_limit_once" ltype="combobox"/></td>
								<td class="l-table-edit-td"><input class="amount" name="deduct_limit_daily" type="text" id="deduct_limit_daily" ltype="combobox"/></td>
								<td class="l-table-edit-td"><input class="amount" name="deduct_limit_daily_times" type="text" id="deduct_limit_daily_times" ltype="combobox"/></td>
							</tr>
							<tr>
								<td class="l-table-edit-td">提现</td>
								<td class="l-table-edit-td"><input class="amount" name="withdraw_limit_once" type="text" id="withdraw_limit_once" ltype="combobox"/></td>
								<td class="l-table-edit-td"><input class="amount" name="withdraw_limit_daily" type="text" id="withdraw_limit_daily" ltype="combobox"/></td>
								<td class="l-table-edit-td"><input class="amount" name="withdraw_limit_daily_times" type="text" id="withdraw_limit_daily_times" ltype="combobox"/></td>
							</tr>	
							<tr>
								<td class="l-table-edit-td">转账</td>
								<td class="l-table-edit-td"><input class="amount" name="transfer_limit_once" type="text" id="transfer_limit_once" ltype="combobox"/></td>
								<td class="l-table-edit-td"><input class="amount" name="transfer_limit_daily" type="text" id="transfer_limit_daily" ltype="combobox"/></td>
								<td class="l-table-edit-td"><input class="amount" name="transfer_limit_daily_times" type="text" id="transfer_limit_daily_times" ltype="combobox"/></td>
							</tr>														
						</table>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">处理办法:</td>
					<td class="l-table-edit-td"><input class="amount" name="amountAccProcessMethod" type="text" 
						id="amountAccProcessMethod" ltype="combobox"/></td>
				</tr>	
				<tr>
					<td align="right" class="l-table-edit-td">状态:</td>
					<td class="l-table-edit-td"><input class="amount" name="amountRiskStatus" type="text" id="amountRiskStatus" ltype="combobox"/></td>
				</tr>						
				
			</table>
			
		</div>	
		
		<div id="frequencyGuideLine" style="position:absolute;display: none; cursor: pointer; width:600px;height: 400px;z-index: 1000;">
			<!-- 2 frequencyGuideLine -->
			<table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td width="30%" align="right" class="l-table-edit-td">风控类型:</td>
					<td width="30%" class="l-table-edit-td">事后</td>
					<td width="30%" class="l-table-edit-td"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td" width="60px">同ip操作次数阀值:</td>
					<td class="l-table-edit-td"><input class="frequency" name="frequencyTimesSecondes_times" type="text" id="frequencyTimesSecondes_times" ltype="combobox"/></td>
					<td class="l-table-edit-td"><input class="frequency" name="frequencyTimesSecondes_Seconds" type="text" id="frequencyTimesSecondes_Seconds" ltype="combobox"/></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"></td>
					<td class="l-table-edit-td"><input class="frequency" name="frequencyTimesDay_times" type="text" id="frequencyTimesDay_times" ltype="combobox"/></td>
					<td class="l-table-edit-td"><input class="frequency" name="frequencyTimesDay_day" type="text" id="frequencyTimesDay_day" ltype="combobox"/></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">处理办法:</td>
					<td class="l-table-edit-td"><input class="frequency" name="ipFreProcessMethod" type="text" id="ipFreProcessMethod" ltype="combobox"/></td>
					<td align="right" class="l-table-edit-td"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">同账号操作次数阀值:</td>
					<td class="l-table-edit-td"><input class="frequency" name="accountFrequencyTimesSecondes_times" 
						type="text" id="accountFrequencyTimesSecondes_times" ltype="combobox"/></td>
					<td class="l-table-edit-td"><input class="frequency" name="accountFrequencyTimesSecondes_Seconds" 
						type="text" id="accountFrequencyTimesSecondes_Seconds" ltype="combobox"/></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"></td>
					<td class="l-table-edit-td"><input class="frequency" name="accountFrequencyTimesDay_times" 
						type="text" id="accountFrequencyTimesDay_times" ltype="combobox"/></td>
					<td class="l-table-edit-td"><input class="frequency" name="accountFrequencyTimesDay_day" 
						type="text" id="accountFrequencyTimesDay_day" ltype="combobox"/></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">处理办法:</td>
					<td class="l-table-edit-td"><input class="frequency" name="accFreProcessMethod" type="text" 
						id="accFreProcessMethod" ltype="combobox"/></td>
					<td align="right" class="l-table-edit-td"></td>
				</tr>	
				<tr>
					<td align="right" class="l-table-edit-td">状态:</td>
					<td class="l-table-edit-td"><input class="frequency" name="freRiskStatus" type="text" id="freRiskStatus" ltype="combobox"/></td>
					<td align="right" class="l-table-edit-td"></td>
				</tr>																
																			
			</table>
		</div>	
		
		<div id="accountGuideLine" style="position:absolute;display: none; cursor: pointer; width:700px;height: 400px;z-index: 1000;">
			<!-- 4 accountGuideLine -->
			<table width="100%" cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td">风控类型:</td>
					<td class="l-table-edit-td" colspan="3">事后</td>
				</tr>
				<tr>
					<td width="130" align="right" class="l-table-edit-td">用户账户余额最大值:</td>
					<td class="l-table-edit-td"><input class="account" name="accountRemainMax" type="text" id="accountRemainMax" ltype="combobox"/></td>
					<td  class="l-table-edit-td" align="left">万元</td>
					<td align="right" class="l-table-edit-td">用户账户余额最小值:</td>
					<td class="l-table-edit-td"><input class="account" name="accountRemainMin" type="text" id="accountRemainMin" ltype="combobox"/></td>
					<td  class="l-table-edit-td" align="left">元</td>
				</tr>		
				<tr>
					<td align="right" class="l-table-edit-td">账户单日进项笔数:</td>
					<td class="l-table-edit-td"><input class="account" name="accountIncomeTimesDaily" type="text" id="accountIncomeTimesDaily" 
						ltype="combobox"/></td>
					<td  class="l-table-edit-td" align="left"></td>
					<td align="right" class="l-table-edit-td">账户单日进项总金额:</td>
					<td class="l-table-edit-td"><input class="account" name="accountIncomeAmountDaily" type="text" id="accountIncomeAmountDaily" 
						ltype="combobox"/></td>
					<td  class="l-table-edit-td" align="left">万元</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">账户单日出项笔数:</td>
					<td class="l-table-edit-td"><input class="account" name="accountOutcomeTimesDaily" type="text" id="accountOutcomeTimesDaily" 
						ltype="combobox"/></td>
					<td  class="l-table-edit-td" align="left"></td>
					<td align="right" class="l-table-edit-td">账户单日出项总金额:</td>
					<td class="l-table-edit-td"><input class="account" name="accountOutcomeAmountDaily" type="text" 
						id="accountOutcomeAmountDaily" ltype="combobox"/></td>
					<td  class="l-table-edit-td" align="left">万元</td>					
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">处理办法:</td>
					<td class="l-table-edit-td"><input class="account" name="accProcessMethod" type="text" 
						id="accProcessMethod" ltype="combobox"/></td>
					<td  class="l-table-edit-td" align="left"></td>
					<td align="right" class="l-table-edit-td">状态:</td>
					<td class="l-table-edit-td"><input class="account" name="accRiskStatus" type="text" id="accRiskStatus" ltype="combobox"/></td>
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