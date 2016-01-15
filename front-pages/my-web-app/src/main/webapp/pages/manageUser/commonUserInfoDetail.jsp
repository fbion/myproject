<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>账户详情</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script src="commonUserInfoDetail.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="padding: 4px; overflow: visible;">
	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 0px;">
		<table cellpadding="0" cellspacing="0" class="l-table-edit" align="left">
			<tr>
				<td colspan="6">
					<div id="step1"></div>
				</td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">内部账号：</td>
				<td align="left" id="insideAccountNo"></td>
				<td align="right" class="l-table-edit-td">账户所属：</td>
				<td align="left" id="ownerLoginName"></td>
				<td align="right" class="l-table-edit-td">账户类型：</td>
				<td align="left" id="accountType"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">账户总余额(元)：</td>
				<td align="left" id="totalBalance"></td>
				<td align="right" class="l-table-edit-td">可用余额(元)：</td>
				<td align="left" id="usableBalance"></td>
				<td align="right" class="l-table-edit-td">冻结余额(元)：</td>
				<td align="left" id="frozenBalance"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">不可提现金额：</td>
				<td align="left" id="unableTakecashBalance"></td>
				<td align="right" class="l-table-edit-td">是否开通短信通知：</td>
				<td align="left" id="isOperMess"></td>
				<td align="right" class="l-table-edit-td">身份证号：</td>
				<td align="left" id="partShowIdcardNo"></td>
			</tr>
			<tr>
				<td align="right" class="l-table-edit-td">手机号：</td>
				<td align="left" id="mobileNo"></td>
				<td align="right" class="l-table-edit-td">账户状态：</td>
				<td align="left" id="accountStatus"></td>
				<td align="right" class="l-table-edit-td">创建时间：</td>
				<td align="left" id="createTime"></td>
			</tr>
			
		</table>
	</div>
	<div style="display: none"></div>
</body>
</html>