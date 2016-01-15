<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商户管理</title>
<jsp:include page="../all_css.jsp"/>
<jsp:include page="../all_js.jsp"/>
<script src="merchant-list.js?v=g_version" type="text/javascript" charset="utf-8"></script> 
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 4px;">
		<div position="center">
			<div id="accordion1" >
				<form1 id="queryMerchantForm">
					<div id="queryCodeDiv" style="top: 10px; border-top: 20px;">
					<table style="top: 5px">
						<tr>
							<td width="10%" align="right" nowrap="nowrap"><label for="storeName">商户名称：</label></td>
							<td align="left" nowrap="nowrap"><input id="storeName" type="text" name="equal[storeName]"/></td>
							<td width="18%" align="right" nowrap="nowrap"><label for="storeCode">商户编号：</label></td>
							<td align="left" nowrap="nowrap"><input name="storeCode" type="text" id="storeCode" /></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="storeType">商户类型：</label></td>
							<td align="left" nowrap="nowrap"><input name="storeType" type="text" id="storeType" /></td>
						</tr>
						<tr style="height: 30px">
							<td width="10%" align="right" nowrap="nowrap"><label for="contactUser">联系人：</label></td>
							<td align="left" nowrap="nowrap"><input id="contactUser" name="contactUser" type="text" id="codeName" /></td>
							<td width="18%" align="right" nowrap="nowrap"><label for="contactPhone">联系电话：</label></td>
							<td align="left" nowrap="nowrap"><input name="contactPhone" type="text" id="contactPhone" /></td>
							<td width="10%" align="right" nowrap="nowrap"><label for="storeStatus">状态：</label></td>
							<td align="left" nowrap="nowrap"><input name="storeStatus" type="text" id="storeStatus" /></td>
						</tr>
						<tr style="height: 30px">
							<td width="10%" align="right" nowrap="nowrap"><label for="regTimeStart">接入时间：</label></td>
							<td  width="15%" align="left" nowrap="nowrap" colspan="5">
								<input name="regTime" id="regTimeStart" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'regTimeEnd\')}'})" class="Wdate"/>~
								<input id="regTimeEnd" name="regTime" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'regTimeStart\')}'})" class="Wdate" />
							</td>
							<td width="10%" align="left" nowrap="nowrap"><input type="button" onclick="window.merchantList.search();" value="查询" class="l-button l-button-submit" /></td>
							<!-- <td width="10%" align="left" nowrap="nowrap"><input type="button" onclick="window.merchantList.exportAllExcel();" value="导出全部" class="l-button l-button-submit" /></td> -->
							<td width="10%" align="left" nowrap="nowrap"><input type="button" onclick="window.merchantList.reset();" value="重置" class="l-button l-button-submit" /></td>
						</tr>
					</table>
					</div>
				</form1>
			</div>
			<!-- 表格内容  -->
			<div id="merchantGrid" class="grid"></div>
			<div style="display: none;"></div>
			<div id="excelExoprt-div-id" style="display: none;"></div>
		</div>
	</div>
</body>
</html>
