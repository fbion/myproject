<%@page import="java.util.Map"%>
<%@page import="com.ctfo.upp.utils.SpringBUtils"%>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%
	String userName = request.getRemoteUser();
	String casRemoteLogoutUrl = ((Map)SpringBUtils.getBean("casMap")).get("casRemoteLogoutUrl").toString();
	String  casRemoteLoginUrl =  ((Map)SpringBUtils.getBean("casMap")).get("casRemoteLoginUrl").toString();
	String  localLoginUrl =  ((Map)SpringBUtils.getBean("casMap")).get("localLoginUrl").toString();
	String  casTicketValidatorUrl =  ((Map)SpringBUtils.getBean("casMap")).get("casTicketValidatorUrl").toString();
	String  casServiceUrl =  ((Map)SpringBUtils.getBean("casMap")).get("casServerNameUrl").toString()+"/"+request.getContextPath();
%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中交兴路车联网 - 统一支付结算管理系统</title>
<link rel="shortcut icon" href='images/favicon.ico' type="image/x-icon" />
<link rel="stylesheet" href="css/login/base.css" type="text/css" />
<link rel="stylesheet" href="css/login/login.css" type="text/css" />
<link rel="stylesheet" href="css/login/lrtk.css" type="text/css" />
<script type="text/javascript">
<!--
   var userName = "<%=userName%>";
   if((!userName || userName=="null") && this.parent!=this){
	   top.location="<%=casRemoteLogoutUrl%>";
   }
//-->
</script>
<script type="text/javascript" src="js/login/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/login/global.js"></script>
<script type="text/javascript" src="js/login/tab.js"></script>
<script type="text/javascript">

	$(function() {
		initTab();
		//解决页面在IE中长时出现空白页的问题,10分钟重新初始化一次
		setInterval('initHtml()',160000);
	});
	
	function initHtml(){		
		var html = '<ul id="js_banner_img" class="banner_img clear">';
		for(var i=0;i<3;i++){
			html += '<li class="bgli0'+(i+1)+'"><div class="banner_inner"><div class="child child1" data-z="2"><img src="images/login/banner_'+(i+1)+'.png" /></div></div></li>';
		}
		html +='</ul>';
		$('#js_banner_div').html('');
		$('#js_banner_div').html(html);
		initTab();
	}
	
	function initTab(){
		var $window = $(window), window_width = parseInt($window.width()), window_height = parseInt($window.height());	
		$('#js_banner, #js_banner_img li').width(window_width);
		var urlParam = G.util.parse.url(), startFrame = 0;
		if (urlParam.search && ('banner' in urlParam.search)){
			startFrame = (parseInt(urlParam.search['banner'])-1) || 0;
		}

		new $.Tab({
			target : $('#js_banner_img li'),
			effect : 'slide3d',
			animateTime : 1000,
			stay : 4000,
			playTo : startFrame,
			autoPlay : true,
			merge : true,
			prevBtn : $('#js_banner_pre'),
			nextBtn : $('#js_banner_next')
		});
		
		$('#js_banner_img').css('left','-' + (parseInt(window_width * startFrame)) + 'px');
		$('#js_login_info').css('right', parseInt((window_width - 960) / 2 + 30));
		$('#login_footer').css('height', parseInt(window_height - 110 - 400));
	}
</script>
</head>
<body>
	<div class="login_header">
		<div class="w960 mAuto">
		    <img src="images/login/logo.png" alt="logo" class="logo" />
			<img src="images/login/header_tel.png" alt="联系电话：400-096-6666"
				class="fr mr40" />
		</div>
	</div>
	<div id="js_banner" class="banner">
	
		<div id="js_login_info" class="login_info">
			<form id="myLoginForm" action="<%=casRemoteLoginUrl%>" method="post" onsubmit="return checkPass();">
				<input type="hidden" name="service" value="<%=casServiceUrl%>" />
				<input type="hidden" name="loginUrl" value="<%=localLoginUrl%>"  />
				 <input type="hidden" name="submit" value="true" />
				
					<input type="text" name="username" id="username" class="login_info_user" value="" />
					<input type="password" name="password" id="password" class="login_info_pw" value="" />
					<input type="text" name="authcode" id="authcode" class="login_info_vc" value="" />
					<img src="<%=casTicketValidatorUrl%>/captcha.jpg" title="看不清，点击换一张" class="login_info_vc_img" onclick="this.src='<%=casTicketValidatorUrl%>/captcha.jpg?'+Math.random()" />
					<input type="submit" class="btn_blue mt15 ml30" value="登 陆" /> 
					<div id="errorMsg" style="color: red;font-weight: bold;margin-top: 10px;margin-left: 30px"></div>
					<!-- <a href="javascript:void(0)" class="fr mr40 mt10 color_gray">忘记密码？</a> -->
				
			</form>
		</div>
		
		<div id="js_banner_div">
			<ul id="js_banner_img" class="banner_img clear">
				<li class="bgli01">
					<div class="banner_inner">
						<div class="child child1" data-z="2">
							<img src="images/login/banner_1.png" />
						</div>
					</div>
				</li>
				<li class="bgli02">
					<div class="banner_inner">
						<div class="child child1" data-z="2">
							<img src="images/login/banner_2.png" />
						</div>
					</div>
				</li>
				<li class="bgli03">
					<div class="banner_inner">
						<div class="child child1" data-z="2">
							<img src="images/login/banner_3.png" />
						</div>
					</div>
				</li>
			</ul>
		</div>
		
		<div class="banner_common">
			<a id="js_banner_pre" href="javascript:;" class="banner_pre"></a> <a
				id="js_banner_next" href="javascript:;" class="banner_next"></a>
		</div>
	</div>
	<div id="login_footer" class="login_footer">
		<div class="w960 mAuto tc">
			<img src="images/login/footer_img1.png" alt="" class="mt50" /> <img
				src="images/login/footer_img2.png" alt="" class="mt50 ml80" /> <img
				src="images/login/footer_img3.png" alt="" class="mt50 ml80" />
		</div>
	</div>
</body>
<script type="text/javascript">
function checkPass(){
	var userName = $.trim($("#username").val());
	var password = $.trim($("#password").val());
	var authcode = $.trim($("#authcode").val());
	if(!userName){
		$("#errorMsg").html("用户名不能为空");
		return false;
	}
	$("#username").val(userName);
	if(!password){
		$("#errorMsg").html("密码不能为空");
		return false;
	}
	$("#password").val(password);
	if(!authcode){
		$("#errorMsg").html("验证码不能为空");
		return false;
	}
	$("#authcode").val(authcode);
	return true;
};

var errorCode="<%=request.getParameter("errorCode")%>";
if(errorCode == 501){
	$("#errorMsg").html("验证码不能为空");
}else if(errorCode == 502){
	$("#errorMsg").html("验证码错误");
}else if(errorCode == 503){
	$("#errorMsg").html("用户名或密码不能为空");
}else if(errorCode == 504){
	$("#errorMsg").html("服务暂不可用，请稍后再试");
}else if(errorCode == 505){
	$("#errorMsg").html("该用户不存在");
}else if(errorCode == 506){
	$("#errorMsg").html("您的账号已被停用");
}else if(errorCode == 507){
	$("#errorMsg").html("您的账号已被锁定，请稍后再试");
}else if(errorCode == 508){
	$("#errorMsg").html("用户名或密码错误");
}
var loginCount="<%=request.getParameter("loginCount")%>";
if(loginCount && loginCount>1){
	$("#errorMsg").html($("#errorMsg").html()+"。您还有"+loginCount+"次尝试的机会");
}else if (loginCount === "0"){
	$("#errorMsg").html("您的账号已被锁定，请稍后再试");
}
</script>
</html>