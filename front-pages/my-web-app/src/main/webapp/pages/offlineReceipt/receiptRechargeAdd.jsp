<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>线下流程管理-小票充值审核申请</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<link rel="stylesheet" href="<%=application.getContextPath()%>/css/uploadify.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=application.getContextPath()%>/css/upload.css" rel="stylesheet" />
<script src="<%=application.getContextPath()%>/js/provincesdata.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=application.getContextPath()%>/js/jquery.provincesCity.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=application.getContextPath()%>/js/upload/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=application.getContextPath()%>/js/upload/ctfo_fileUpload.js"></script>
<script type="text/javascript" src="receiptRechargeAdd.js?v=g_version"></script> 
<script type="text/javascript" src="<%=application.getContextPath()%>/js/DateUtil.js"></script>
</head>
<body style="height: 90%; padding: 20px;">
	<div id="showBigImg" onclick="javascript:$(this).hide();" style="position:absolute;display: none; cursor: pointer; width:400px;height: 400px;z-index: 1000;" title="点击关闭大图"></div>	
	<div id="tableDiv" style="overflow: auto; height: 400px;">
	<form name="form1" method="post" id="form1">
		<input type="hidden" id="id" name="id" /> 
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td colspan="4">
						<div id="step1"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>付款人:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="remitterName" id="remitterName" ltype="text"  />
					</td>
				<!-- 	<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>付款方式:</td>
					<td align="left" class="l-table-edit-td">
						<input name="payType" type="text" id="payType" ltype="combobox"/>
					</td> -->
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>业务员:</td>
					<td align="left" class="l-table-edit-td" >
					    <input type="text" name="applyName" id="applyName" ltype="text" validate="{required:true}" readonly="readonly" />  
					</td>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>所属地区:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="applyPersonPost" id="applyPersonPost" ltype="combobox" validate="{required:true}" /> 
						<span id="area"></span>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>客户名称:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="customerName" id="customerName" ltype="text"  />
					</td>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>来款金额:</td>
					<td align="left" class="l-table-edit-td">
						<input name="tradeAmount" type="text" id="tradeAmount" ltype="text" onkeyup="window.receiptRechargeAdd.convert();"/>
					</td>
					<td>
						<input type="text" name="tradeAmountUpper" id="tradeAmountUpper"   readonly="readonly" style="border-style:none"/>
					</td>
					<!-- <td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>汇款金额大写:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="tradeAmountUpper" id="tradeAmountUpper"   readonly="readonly"/>
					</td> -->
				</tr>
				<tr>
					<td colspan="4">
						<div id="step2"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">汇款凭证:</td>
					<td >
						<div id="payPicId_file1" class="fl img_tops"></div>
						<div class="cb"></div>
					</td>
					
					<td align="right" class="l-table-edit-td">油卡分配凭证:</td>
					<td>
						<div id="payPicId_file2" class="fl img_tops"></div>
						<div class="cb"></div>
					</td>
				</tr>
				
			</table>
	</form>
	</div>
	<div style="display: none"></div>
</body>
</html>