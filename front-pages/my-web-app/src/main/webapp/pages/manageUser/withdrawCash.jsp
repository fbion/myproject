<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>提现</title>
	<jsp:include page="/pages/all_css.jsp"/>
	<jsp:include page="/pages/all_js.jsp"/>
	<script src="<%=request.getContextPath()%>/pages/manageUser/withdrawCash.js" type="text/javascript" charset="utf-8"></script> 
</head>
<style type="text/css">
input{
	width: 200px;
	height: 30px;
}
table{
	margin-top: 20px;
}
tr{
	height: 45px;
}
td{
	width: 400px;
}
.nameRows{
	text-align:right;
	width: 80px;
}
#layout{
	padding-left: 80px;
} 

</style> 
<body>
<div id="layout" style="width: 100%; margin: 0 auto; margin-top: 4px;">
		<form method="post" action="<%=request.getContextPath()%>/" onsubmit="return earlyWarning()">
			<input type="hidden" id="insideAccountNo" name="insideAccountNo"/>
			<input type="hidden" id="result" value="${result}"/>
			<table style="margin-left: 60px;">
				<tr>
					<td class="nameRows">
						账户余额：
					</td>
					<td>
						<input id="totalBalance" type="text" name="totalBalance" readonly="readonly" style="color: red;"/>
					</td>
				</tr>
				<tr>
					<td class="nameRows">
						可用余额：
					</td>
					<td>
						<input name="usableBalance" type="text" id="usableBalance" readonly="readonly" style="color: red;"/>
					</td>
					
				</tr>
				<tr>
					<td class="nameRows">
						提现金额：
					</td>
					<td>
						<input name="email" type="text" id="email" />元
					</td>
				</tr>
				
				<tr>
					<td class="nameRows">
						手机号：
					</td>
					<td>
						<input name="mobileNo" type="text" id="mobileNo" readonly="readonly"/>
					</td>
				</tr>
		
				<tr>
					<td class="nameRows">
						验证码：
					</td>
					<td>
						<input name="" type="text" id="" style="width: 100px;"/>
						<a href="">获取验证码</a>
					</td>
				</tr>
				
				<tr>
					<td class="nameRows">
						开户行地区：
					</td>
					<td>
						<input name="branchBankProvince" type="text" id="branchBankProvince" style="width: 80px;"/>
						<input name="branchBankCity" type="text" id="branchBankCity" style="width: 150px;"/>
					</td>
				</tr>
				
				<tr>
					<td class="nameRows">
						开户行名称：
					</td>
					<td>
						<input name="" type="text" id="" style="width: 80px;"/>
						<input name="" type="text" id="" style="width: 150px;"/>
					</td>
				</tr>
				
				<tr>
					<td class="nameRows">
						账户姓名：
					</td>
					<td>
						<input name="" type="text" id=""/>
					</td>
				</tr>
				
				<tr>
					<td class="nameRows" colspan="2" style="text-align: left; padding-left: 120px;">
						<input type="submit" value="确定" style="width: 100px;"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
