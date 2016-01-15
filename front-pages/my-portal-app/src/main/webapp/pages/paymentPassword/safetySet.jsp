<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String mobileNo = (String)session.getAttribute("mobileNo");
	String accountNo = (String)session.getAttribute("accountNo");
%>
<jsp:include page="/pages/all_js.jsp" />
<jsp:include page="/pages/all_css.jsp" />
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>车旺-个人中心-安全设置</title>
	<script type="text/javascript">
		var mobileNo = '<%=mobileNo%>';
		var accountNo = '<%=accountNo%>';
		if(!mobileNo||!accountNo||mobileNo=="null"||accountNo=="null"){
			window.location.href=root+"/account/payAgain.action";
		}
	</script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/account/question.css" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/paymentPassword/safetySet.js" ></script>
</head>
<body>
	<!--right-->
	<div class="rightsideBar w880 fr" style="float: left; margin: 0;padding: 0;">
		<div class="pub-tit"><span class="t-style">安全设置</span></div>
		<div class="ques-cont safe-inst">
			<table>
				<tbody>
					<tr>
						<td width=235>
							<img src="../images/account/s1.png">
							账户安全等级
						</td>
						<td width=357>
							<div class="strength tr">
								<i id="strength_L" class="i1" style="background-color: #FF4500;">弱</i>
								<i id="strength_M" class="i2">中</i>
								<i id="strength_H" class="i3">强</i>
							</div>
						</td>
						<td width=298>&nbsp;</td>
					</tr>
					<tr>
						<td>
							<img src="../images/account/s2.png">
							绑定手机号
						</td>
						<td>
							<span class="s-span1" id="mobileNo"></span>
						</td>
						<td>
							<a id="changeMobileNo">修改</a>
						</td>
					</tr>
					<tr>
						<td>
							<img src="../images/account/s3.png">
							支付密码
						</td>
						<td>
							<img src="../images/account/s6.png" class="ml0 mt39"  id="pay-pwd-img">
							<span class="s-span2">在账户资金变动，修改账户信息时需要输入的密码</span>
						</td>
						<td id="pwd-button">
						</td>
					</tr>
					<tr>
						<td>
							<img src="../images/account/s4.png">
							安全保护问题
						</td>
						<td>
							<img src="../images/account/s6.png" class="ml0 mt39" id="pwd-query-img">
							<span class="s-span3">您设置了<span class="s-span4" id="pwdQueryNum">0</span>个安全保护问题</span>
						</td>
						<td id="pwd-query-button">
						</td>
					</tr>
					<tr>
						<td>
							<img src="../images/account/s5.png">
							实名认证
						</td>
						<td>
							<img src="../images/account/s8.png" class="ml0 mt39">&nbsp;
						</td>
						<td>
							<a href="#">未开通</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>