<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线下流程管理-财务审批</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="rechargeFinanceDeal.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="padding: 4px; overflow: visible;">
	<div id="showBigImg" onclick="javascript:$(this).hide();" style="position:absolute;display: none; cursor: pointer; width:400px;height: 400px;z-index: 1000;" title="点击关闭大图"></div>
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div id="gridList" class="grid">
			<input type="hidden" id="id" name="id"/>
			<form id="form1" method="post">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="left">
					<tr>
						<td colspan="6">
							<div id="step1"></div>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							付款人:
						</td>
						<td align="left" class="l-table-edit-td" id="remitterName">
						</td>
						<td align="right" class="l-table-edit-td">
							付款人账号：
						</td>
						<td align="left" class="l-table-edit-td" id="remitterBankcardNo">
						</td>
						<td align="right" class="l-table-edit-td">
							付款人开户行:
						</td>
						<td align="left" class="l-table-edit-td" id="remitterBankCode">
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							收款人:
						</td>
						<td align="left" class="l-table-edit-td" id="payeeName">
						</td>
						<td align="right" class="l-table-edit-td">
							收款人账号：
						</td>
						<td align="left" class="l-table-edit-td" id="payeeNo">
						</td>
						<td align="right" class="l-table-edit-td">
							记账日期:
						</td>
						<td align="left" class="l-table-edit-td" id="amountArriveTime">
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							汇款金额（小写）：
						</td>
						<td align="left" class="l-table-edit-td" id="tradeAmount">
						</td>
						<td align="right" class="l-table-edit-td">
							汇款金额（大写）：
						</td>
						<td align="left" class="l-table-edit-td"  colspan="3" id="tradeAmountUpper">
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							用途：
						</td>
						<td align="left" class="l-table-edit-td" id="description">
						</td>
						<td align="right" class="l-table-edit-td">
							付款凭证附件：
						</td>
						<td align="left" class="l-table-edit-td" colspan="3">
							<a href="javascript:void(0)"  id="imgHref" title="点击查看大图" onclick="window.finaceDeal.showImg()">查看</a>
							<a href="javascript:void(0)" style="display: none;" id="imgHref" title="点击查看大图" onclick="window.finaceDeal.queryBigImg()">查看</a>
							<img id="passImg_img"  height="40" width="40" style="cursor: pointer;display: none;"/>
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<div id="step2"></div>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							账户编码：
						</td>
						<td align="left" class="l-table-edit-td" id="storeId" colspan="3">
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<div id="step3"></div>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							申请时间：
						</td>
						<td align="left" class="l-table-edit-td" id="applyTime">
						</td>
						<td align="right" class="l-table-edit-td">
							申请人：  
						</td>
						<td align="left" class="l-table-edit-td"  colspan="3" id="approvalPersonName">
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<div id="step4"></div>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							审批人：
						</td>
						<td align="left" class="l-table-edit-td" id="dealApprovalPersonName">
						</td>  
						<td align="right" class="l-table-edit-td">
							审批时间：
						</td>
						<td align="left" class="l-table-edit-td" id="operTime" colspan="3">
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							审批结果：
						</td>
						<td align="left" class="l-table-edit-td" colspan="5" id="approvalResult">
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							审核意见或建议：
						</td>
						<td align="left" class="l-table-edit-td" colspan="5" id="approvalSuggest">
							
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<div id="step5"></div>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							意见：
						</td>
						<td align="left" class="l-table-edit-td" colspan="5">
							<input type="radio"  name="financeApprovalSuggest" value="同意" checked="checked" />同意&nbsp;&nbsp;
							<input type="radio"  name="financeApprovalSuggest" value="不同意"/>不同意
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							审核意见或建议：
						</td>
						<td align="left" class="l-table-edit-td" colspan="5" >
							<textarea rows="3" cols="10" style="width: 273px; height: 57px;" id="financeApprovalSuggest"></textarea>
						</td>
					</tr>
				</table>
				</form>
		</div>
		<div style="display: none;"></div>
	</div>
</body>
</html>