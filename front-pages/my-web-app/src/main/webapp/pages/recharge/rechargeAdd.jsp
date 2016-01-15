<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线下流程管理-充值审核申请</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="rechargeAdd.js?v=g_version"></script> 
<script type="text/javascript" src="<%=application.getContextPath()%>/js/DateUtil.js"></script>
</head>
<body style="height: 90%; padding: 20px;">
	<div id="showBigImg" onclick="javascript:$(this).hide();" style="position:absolute;display: none; cursor: pointer; width:400px;height: 400px;z-index: 1000;" title="点击关闭大图"></div>	
	<div id="tableDiv" style="overflow: auto; height: 400px;">
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
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>付款人:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="remitterName" id="remitterName" ltype="text" validate="{required:true}" />
					</td>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>付款人开户行:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="remitterBankCode" id="remitterBankCode" ltype="combobox" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>付款人账号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="remitterBankcardNo" id="remitterBankcardNo" ltype="text"  />
					</td>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>付款方式:</td>
					<td align="left" class="l-table-edit-td">
						<input name="payType" type="text" id="payType" ltype="combobox"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div id="step2"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>收款人:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="payeeName" id="payeeName" ltype="text" validate="{required:true,minlength:1,maxlength:30}" />
					</td>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>收款人账号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="payeeNo" id="payeeNo" ltype="text" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>记账日期:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="amountArriveTime" id="amountArriveTime" ltype="text" validate="{required:true}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate"/>
					</td>
				</tr>	
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>汇款金额:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="tradeAmount" id="tradeAmount" ltype="text" onkeyup="window.rechargeAdd.convert();" />
					</td>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>汇款金额大写:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="tradeAmountUpper" id="tradeAmountUpper"   readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>用途:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="description" id="description" ltype="text" validate="{required:true}" />
					</td>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>账户号:</td>
					<td align="left" class="l-table-edit-td">
						<input name="insideAccountNo" type="text" id="insideAccountNo" ltype="combobox" />
					</td>
				</tr>	
				 <tr>
					<td align="right" class="l-table-edit-td">汇款凭证:</td>
					<td>
						<input type="button" id="uploadBtn" value="上传汇款凭证" name="uploadBtn" onclick="window.rechargeAdd.upload();"/>&nbsp;
						<input type="hidden" id="voucherFileName" name="voucherFileName"/>
					</td>
					<td>
						<a href="javascript:void(0)" style="display: none;" id="imgHref" title="点击查看大图" onclick="window.rechargeAdd.showImg()">查看汇款凭证</a>
						<img id="passImg_img"  height="40" width="40" style="cursor: pointer;display: none;"/>
					</td>
				</tr>	
			</table>
	</form>
	</div>
	<div style="display: none"></div>
</body>
</html>