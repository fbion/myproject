<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<title>车旺-个人中心-安全设置-修改密保首页</title>
	<jsp:include page="/pages/all_js.jsp" />
	<jsp:include page="/pages/all_css.jsp" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/account/question.css" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/paymentPassword/changeCryptoguardHome.js" ></script>
</head>
<body>
	<!--right-->
	<div class="w800 fr" style="float: left; margin: 0;padding: 0;">
		<div class="pub-tit"><span class="t-style">修改安全保护问题</span></div>
		<div class="ques-cont">
			<ul>
			    <li>你正在为资金账户修改安保问题，请选择修改方式</li>
			    <li>
			    	<img src="../images/account/icon1.png">
				    <div>
				        	通过“回答安全保护问题+验证支付密码”<span class="span1">如果你记得预留的安全保护问题及支付密码，请选择此方式</span>
				    </div>
				    <a><input class="tjbtn tjbtn2"  id="changeCryptoguard" type="button" value="立即修改"></a>
			    </li>
			    <li class="cl">
			    	<img src="../images/account/icon2.png">
				    <div>
				       	 忘记安保问题<span class="span1">忘记安保问题，请选择此方式</span>
				    </div>
				    <div class="ml230">
					    <span class="span1 span2">请拨打客服热线</span><span class="span3">4000966666</span>
				    </div>
			    </li>
			</ul>
		</div>
	</div>	
</body>
</html>