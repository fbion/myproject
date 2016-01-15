<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线下流程管理-小票充值详情</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="receiptRechargeDetail.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
td{
	width:120px;
}
</style>
</head>
<body style="padding: 4px; overflow: visible;">
	<div id="showBigImg" onclick="javascript:$(this).hide();" style="position:absolute;display: none; cursor: pointer; width:400px;height: 400px;z-index: 1000;" title="点击关闭大图"></div>
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<div id="gridList" class="grid">
		<form id="form1" method="post">
		<input type="hidden" id="id" name="id" /><!-- 充值ID -->
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="left">
					<tr>
						<td colspan="6">
							<div id="step1"></div>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" >
							付款人：
						</td>
						<td align="left"  id="remitterName">
						</td>
						<td align="right" class="l-table-edit-td">
							业务员：
						</td>
						<td align="left"  id="applyName">
						</td>
						<td align="right" class="l-table-edit-td">
							业务所属地区：
						</td>
						<td align="left"  id="applyPersonPost">
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							客户名称：
						</td>
						<td align="left" id="customerName">
						</td>
						<td align="right" class="l-table-edit-td">
							来款金额：
						</td>
						<td align="left"  id="tradeAmount">
						</td>
						<td align="right" class="l-table-edit-td" >
							充值申请时间：
						</td>
						<td align="left"  id="applyTime">
						</td>
					</tr>
					
					<tr>
						<!-- <td align="right" class="l-table-edit-td">
							用途：
						</td>
						<td align="left"  id="description">
						</td> -->
						<td align="right" class="l-table-edit-td">
							凭证附件：
						</td>
						<td align="left" class="l-table-edit-td" colspan="3">
							<a href="javascript:void(0)"  id="imgHref" title="点击查看大图" onclick="window.dealList.showImg(0)">查看</a>
							<a href="javascript:void(0)" style="display: none;" id="imgHref" title="点击查看大图" onclick="window.dealList.queryBigImg()">查看</a>
							<img id="passImg_img"  height="40" width="40" style="cursor: pointer;display: none;"/>
						</td>
					    <td align="right" class="l-table-edit-td">
							油卡分配凭证：
						</td>
						<td align="left" class="l-table-edit-td" colspan="3">
							<a href="javascript:void(0)"  id="imgHref" title="点击查看大图" onclick="window.dealList.showImg(1)">查看</a>
							<a href="javascript:void(0)" style="display: none;" id="imgHref" title="点击查看大图" onclick="window.dealList.queryBigImg()">查看</a>
							<img id="passImg_img"  height="40" width="40" style="cursor: pointer;display: none;"/>
						</td> 
					</tr>
					<!-- 凭证信息 START-->
					<tr>
						<td colspan="6">
							<div id="step1_1"></div>
						</td>
					</tr>
					
					<tr>
						<td align="right" class="l-table-edit-td">
							来款渠道:
						</td>
						<td align="left" style="width: 200px" >
							<input class="voucherCombox" name="incomeChannel" type="text" id="incomeChannel" ltype="combobox"/>
						</td>
						<td align="left" style="width: 200px" >
							<input class="voucherCombox" name="incomeChannelSub" type="text" id="incomeChannelSub" ltype="combobox"/>
						</td>
						<td></td>
					</tr>
					
					<tr class="posVoucher">
						<td align="right" class="l-table-edit-td">商户编号:</td>
						<td align="left">
							<input class="voucherCombox" name="posMerchantCode" type="text" id="posMerchantCode" ltype="combobox"/>
						</td>
						<td align="right" class="l-table-edit-td">终端号:</td>	
						<td align="left" id="terminalCode"></td>								
					</tr>
					<tr class="posVoucher">
						<td align="right" class="l-table-edit-td">凭证号:</td>
						<td align="left" id="voucherCode">
						</td>
						<td align="right" class="l-table-edit-td">日期时间:</td>	
						<td align="left" id="voucherTime">
						</td>	
					</tr>							
					<tr class="pubVoucher">
						<td align="right" class="l-table-edit-td">交易流水号:</td>
						<td align="left" id="voucherTradeNo"></td>
						<td align="right" class="l-table-edit-td">时间戳:</td>
						<td align="left" id="pubVoucherTime">
						</td>
					</tr>					 
					<!-- 凭证信息 end -->
					<tr>
						<td colspan="6" align="left">
							<div id="step4"></div>
						</td>
					</tr>
					<!-- 出纳审核结果   开始 -->
					<tr>
						<td align="right" class="l-table-edit-td">
							审批人：
						</td>
						<td align="left" class="l-table-edit-td" id="dealApprovalPersonName">
						</td>  
						<td align="right" class="l-table-edit-td">
							审批时间：
						</td>
						<td align="left" class="l-table-edit-td" id="dealOperTime" colspan="3">
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							审批结果：
						</td>
						<td align="left" class="l-table-edit-td"  id="dealApprovalResult">
						</td>
						<td align="right" class="l-table-edit-td">
							审核意见或建议：
						</td>
						<td align="left" class="l-table-edit-td" colspan="3" id="dealApprovalSuggest">
						</td>
					</tr>
					<!-- 出纳审核结果   结束 -->
					<tr>
						<td colspan="6" align="left">
							<div id="step5"></div>
						</td>
					</tr>
					<!-- 审核结果   开始 -->
					<tr>
						<td align="right" class="l-table-edit-td">
							审批人：
						</td>
						<td align="left" class="l-table-edit-td" id="dealApprovalPersonNameFinal">
						</td>  
						
			
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							分配结果：
						</td>
						<td align="left" class="l-table-edit-td"  id="dealApprovalResultFinal">
						</td>
						<td align="right" class="l-table-edit-td">
							备注：
						</td>
						<td align="left" class="l-table-edit-td" colspan="3" id="dealApprovalSuggestFinal">
						</td>
					</tr>
					<!-- 审核结果   结束 -->
				</table>
				</form>
		</div>
		<div style="display: none;"></div>
	</div>
</body>
</html>