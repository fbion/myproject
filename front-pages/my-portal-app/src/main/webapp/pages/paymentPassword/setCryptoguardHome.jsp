<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String mobileNo = (String)session.getAttribute("mobileNo");
	String accountNo = (String)session.getAttribute("accountNo");
%>

<html>
<head>
	<script type="text/javascript">
		var mobileNo = '<%=mobileNo%>';
		var accountNo = '<%=accountNo%>';
	</script>
	<title>车旺-个人中心-安全设置-增加密保问题首页</title>
	<jsp:include page="/pages/all_js.jsp" />
	<jsp:include page="/pages/all_css.jsp" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/account/question.css" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/paymentPassword/setCryptoguardHome.js" ></script>
</head>
<body>
	<!--right-->
	<div class="w800 fr" style="float: left; margin: 0;padding: 0;">
		<div class="pub-tit"><span class="t-style">添加安全保护问题</span></div>
		<div class="ques-cont">
			<ul>
			    <li>你正在为资金账户添加安保问题，请选择添加方式</li>
			    <li>
			    	<img src="../images/account/icon3.png" class="pt31">
				    <div>
				      	  通过“验证短信+验证支付密码”<span class="span1">如果<span id="mobileNo"></span>正常使用中，且记得支付密码，请选择此方式</span>
				    </div>
				    <a id="setCryptoguard"><input class="tjbtn tjbtn2" type="button" value="立即添加"></a>
			    </li>
			</ul>
		</div>
	</div>	
</body>
</html>