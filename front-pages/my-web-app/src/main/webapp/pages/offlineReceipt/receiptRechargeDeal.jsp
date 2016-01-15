<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线下流程管理-出纳-到款确认</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="receiptRechargeDeal.js?v=g_version" type="text/javascript" charset="utf-8"></script>
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
							<input name="incomeChannel" type="text" id="incomeChannel" ltype="combobox"/>
						</td>
						<td align="left" style="width: 200px" >
							<input name="incomeChannelSub" type="text" id="incomeChannelSub" ltype="combobox"/>
						</td>
					</tr>
					
					<tr class="posVoucher" style="display:none">
						<td width="25%" align="right" class="l-table-edit-td">商户编号:</td>
						<td width="25%" align="left" style="width: 200px">
							<input name="posMerchantCode" type="text" id="posMerchantCode" ltype="combobox"/>
						</td>
						<td width="25%" align="right" class="l-table-edit-td">终端号:</td>	
						<td width="25%" align="left"><input type="text" id="terminalCode" name="terminalCode"/></td>								
					</tr>
					<tr class="posVoucher" style="display:none">
						<td align="right" class="l-table-edit-td">凭证号:</td>
						<td align="left" >
							<input type="text" id="voucherCode" name="voucherCode"/>
						</td>
						<td align="right" class="l-table-edit-td">日期时间:</td>	
						<td align="left" >
							<input name="voucherTime" type="text" id="voucherTime" 
							onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"/>
						</td>	
					</tr>							
					<tr class="pubVoucher" style="display:none">
						<td align="right" class="l-table-edit-td">交易流水号:</td>
						<td align="left"><input name="voucherTradeNo" type="text" id="voucherTradeNo"/></td>
						<td align="right" class="l-table-edit-td">时间戳:</td>
						<td align="left"><input name="pubVoucherTime" type="text" id="pubVoucherTime" 
							onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"/>
						</td>
					</tr>					 
					
					<tr>
						<td align="right" class="l-table-edit-td">
							校验结果:
						</td>
						<td id="voucherCheckResult"></td>
						
						<td colspan="2" align="right">
							<input type="button" onclick="window.dealList.checkVoucher();" value="排重校验" 
								class="l-button l-button-submit" />
						</td>
					</tr>

					<!-- 凭证信息 END-->					
					
					<tr>
						<td colspan="6">
							<div id="step4"></div>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							意见：
						</td>
						<td align="left" class="l-table-edit-td">
							<input type="radio" id="agree" name="approvalResult"  checked="checked" value="同意"/>同意&nbsp;&nbsp;
							<input type="radio" id="disagree" name="approvalResult" value="不同意" />不同意
						</td>
						<td align="right" class="l-table-edit-td">
							到账时间：
						</td>
						<td align="left" class="l-table-edit-td" colspan="3" >
							<input name="time" type="text" ltype="text" id="time"   class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" onblur="window.dealList.validate()" />
							<font color="red" id="times"></font>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">
							审批意见或建议：
						</td>
						<td align="left" class="l-table-edit-td" colspan="5">
							<textarea rows="3" cols="75" id="approvalSuggest" name="approvalSuggest" ></textarea>
						</td>
					</tr>
				</table>
				</form>
		</div>
		<div style="display: none;"></div>
	</div>
</body>
</html>