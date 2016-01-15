<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fragment" uri="http://www.95155.com/fragment"%>
<jsp:include page="/pages/all_js.jsp" />
<jsp:include page="/pages/all_css.jsp" />

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>支付中心-前台开户</title>
	<jsp:include page="/pages/account/inc/resource.jsp" />
	<%
		String mobileNo = (String)session.getAttribute("mobileNo");
	%>
	<script type="text/javascript">
		var mobileNo = '<%=mobileNo%>';
		//重载验证码  
		function reCode(obj){
		    obj.src = "<%=request.getContextPath() %>/imgCheckCode?d=" + new Date();
		}
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/utils/md5.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/account/open.js"></script>
	<style type="text/css">
	.acc-strength{
		padding-left: 145px;
	}
	.acc-strength span{
		display: block;
		width: 40px;
		float: left;
		margin-left: 10px;
		text-align: center;
	}
	
	</style>

</head>
<body>
    <!--right-->
	<div class="w800 fr" style="float: left; margin: 0;padding: 0;">
		<div class="rightsideBar_title">
			<h3 class="fb f14 inline fl">开户</h3>
		</div>
		<div class="mian_rightsideBar pt30 pb30">
		    <div class="lists-form">
				<table>
					<tr>
						<td class="lab"><span class="required">*</span>支付密码：</td>
						<td class="fld">
							<input name=payPassword type="password" id="payPassword" style="width: 270px;">
						</td>
						<td><span id="newPasswordSpan">密码由6-8位的数字，字母组成。</span></td>
					</tr>
					<tr>
						<td></td>
						<td class="acc-strength" align="right">
							<span id="pay-pwd-strength_L" style="background-color: #CFCFCF">弱</span>
							<span id="pay-pwd-strength_M" style="background-color: #CFCFCF">中</span>
							<span id="pay-pwd-strength_H" style="background-color: #CFCFCF">强</span>
						</td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td id="pwdStringency">
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="lab"><span class="required">*</span>确认支付密码：</td>
						<td class="fld">
							<input name="payPasswordComfirm" type="password" id="payPasswordComfirm" style="width: 270px;">
						</td>
						<td><span id="newPasswordRepeatSpan"></span></td>
					</tr>
					<tr>
						<td class="lab"><span class="required">*</span>手机号：</td>
						<td class="fld">
							<input type="text" id="mobileNo" disabled="disabled">
						</td>
						<td class="mark"></td>
					</tr>
					<tr>
						<td class="lab"><span class="required">*</span>图片验证码：</td>
						<td class="fld nextsel nextselnum2">
							<input type="text" name="imgCheckCode" id="imgCheckCode" size="10" maxlength="4"/>    
							<img alt="看不清楚，换一张" src="<%=request.getContextPath()%>/imgCheckCode" onclick="reCode(this);" style="cursor: hand;margin-left: 10px"/>  
						</td>
						<td class="mark"></td>
					</tr>
					<tr>
						<td class="lab"><span class="required">*</span>短信验证码：</td>
						<td class="fld nextsel nextselnum2">
							<input name="smsCheckCode" type="text" id="smsCheckCode" size="30">
							<a id="noteProving" class="nextsela">获取验证码</a>
						</td>
						<td class="mark"></td>
					</tr>
				</table>
			</div>
			<div class="next-lists-form">
			    <table>
					<tr>
						<td class="lab"></td>
						<td class="nextsel">
							<input type="button" id="submitButton" value="确定开户" >
						</td>
						<td class="mark"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>