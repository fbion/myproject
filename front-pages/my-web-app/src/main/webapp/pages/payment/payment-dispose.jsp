<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>线下流程管理-扣款审批页面</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="payment-dispose.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
td {
	width: 120px;
}
</style>
</head>
<body style="overflow: visible; padding: 10px; padding-left: 40px;">
	<div id="layout" style="width: 99.2%; margin: 0 auto; margin-top: 4px;">
		<input type="hidden" name="id" id="id" /> <input type="hidden" name="stepNo" id="stepNo">
		<div id="myPanel">
			<form id="form1">
				<div id="baseForm" style="clear: both;">
					<fieldset>
						<legend>
							<h2>
								<img src="<%=request.getContextPath()%>/images/group.gif" width="20px" height="20px">&nbsp;&nbsp;扣款基础信息
							</h2>
						</legend>
						<hr style="width: 100%" />
						<table id="baseTb" cellpadding="0" cellspacing="0" align="left">
						</table>
					</fieldset>
				</div>
			</form>
		</div>
		<div id="salesman">
			<form id="paramform">
				<div id="salesmanDiv" style="clear: both;">
					<fieldset>
						<legend>
							<h2>
								<img src="<%=request.getContextPath()%>/images/group.gif" width="20px" height="20px">&nbsp;&nbsp;业务经理审批
							</h2>
						</legend>
						<hr style="width: 100%" />
						<table id="salesmanTb" cellpadding="0" cellspacing="0" align="left">
						</table>
					</fieldset>
				</div>
			</form>
		</div>
		<div id="financial">
			<form id="financialForm">
				<div id="financialDiv" style="clear: both;"></div>
			</form>
		</div>
	</div>
</body>
<body style="padding: 4px; overflow: visible;">
	<div class="l-loading" style="display: none" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<form id="form1" method="post">
			<input type="hidden" id="id" name="id" />
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="left">
				<tr>
					<td colspan="4">
						<div id="step1"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">付款人：</td>
					<td align="left" id="remitterName"></td>
					<td align="right" class="l-table-edit-td">扣款原因：</td>
					<td align="left" id="description"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">付款时间：</td>
					<td align="left" id="applyTime"></td>
					<td align="right" class="l-table-edit-td">资金到账时间：</td>
					<td align="left" id="amountArriveTime"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">账户号：</td>
					<td align="left" id="applyTime"></td>
					<td align="right" class="l-table-edit-td">扣款金额：</td>
					<td align="left" id="amountArriveTime"></td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">申请人：</td>
					<td align="left" id="applyTime"></td>
					<td align="right" class="l-table-edit-td">申请时间：</td>
					<td align="left" id="amountArriveTime"></td>
				</tr>
				<!-- 业务经理审批 -->
				<tr>
					<td colspan="4">
						<div id="step2"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">意见：</td>
					<td align="left" class="l-table-edit-td" colspan="3"><input type="radio" id="agree" name="approvalResult" checked="checked" value="同意" />同意&nbsp;&nbsp; <input type="radio" id="disagree" name="approvalResult" value="不同意" />不同意</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">审批意见或建议：</td>
					<td align="left" class="l-table-edit-td" colspan="3"><textarea rows="3" cols="75" id="approvalSuggest" name="approvalSuggest"></textarea></td>
				</tr>
				<!-- 财务经理审批 -->
				<tr id="financialTitleTr" style="display: none;">
					<td colspan="4">
						<div id="step3"></div>
					</td>
				</tr>
				<tr id="financialAgreeTr" style="display: none;">
					<td align="right" class="l-table-edit-td">意见：</td>
					<td align="left" class="l-table-edit-td" colspan="3"><input type="radio" id="financialAgree" name="financialAgree" checked="checked" value="同意" />同意&nbsp;&nbsp; <input type="radio" id="financialDisagree" name="financialAgree" value="不同意" />不同意</td>
				</tr>
				<tr id="financialSuggestTr" style="display: none;">
					<td align="right" class="l-table-edit-td">审批意见或建议：</td>
					<td align="left" class="l-table-edit-td" colspan="3"><textarea rows="3" cols="75" id="financialSuggest" name="financialSuggest"></textarea></td>
				</tr>
			</table>
		</form>
		<div style="display: none;"></div>
	</div>
</body>
</html>