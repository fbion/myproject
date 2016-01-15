$(function() {
	//修改导航栏
	parent.$("#naveBar").html("<li class=\"fl pr2 color_chip\"><a onclick=\"cheWangHomePage()\">车旺首页</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"cheWangAccountCenter()\">账户中心</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"goAccountHome()\">资金帐户</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"goSafetySet()\">安全设置</a></li>");
	
	$('#changeCryptoguard').click(function(){
		window.location.href=root+"/paymentPassword/changeCryptoguardPage.action";
	});
});