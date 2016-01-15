<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String mobileNo = (String)session.getAttribute("mobileNo");
	String accountNo = (String)session.getAttribute("accountNo");
%>

<html>
<head>
	<title>车旺-个人中心-安全设置-修改绑定手机号首页</title>
	<jsp:include page="/pages/all_js.jsp" />
	<jsp:include page="/pages/all_css.jsp" />
	<script type="text/javascript">
		//重载验证码  
		function reCode(obj){
			obj.src = "<%=request.getContextPath()%>/imgCheckCode?d=" + new Date();
		}
		var mobileNo = '<%=mobileNo%>';
		var accountNo = '<%=accountNo%>';
	</script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/account/question.css" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/paymentPassword/changeMobileNoHome.js" ></script>
</head>
<body>
	<!--right-->
	<div class="w800 fr" style="float: left; margin: 0;padding: 0;">
	<div class="pub-tit"><span class="t-style">修改绑定手机号</span></div>
		<div class="ques-cont">
			<ul>
				<li>请确认当前绑定的<span id="mobileNo"></span>能接收短信</li>
				<li>
					<img src="../images/account/icon5.png" class="pt34">
					<div>
						无法接受短信<span class="span1">原手机号已丢失或停用 可选择此方式</span>
					</div>
					<div class="ml230">
						<span class="span1 span2">请拨打客服热线</span><span class="span3">4000966666</span>
					</div>
				</li>
				<li class="cl">
					<img src="../images/account/icon3.png">
					<div>
						能接受短信<span class="span1">通过“验证短信+验证支付密码” 方式修改</span>
					</div>
					<a id="toChangeMobileNoPage"><input class="tjbtn tjbtn2 ml200" type="button" value="立即修改"></a>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>