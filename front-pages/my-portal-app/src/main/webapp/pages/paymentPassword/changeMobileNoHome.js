//数据对象模板
var tableOne = true;
var tableTwo = true;
$(function() {
	//修改导航栏
	parent.$("#naveBar").html("<li class=\"fl pr2 color_chip\"><a onclick=\"cheWangHomePage()\">车旺首页</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"cheWangAccountCenter()\">账户中心</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"goAccountHome()\">资金帐户</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"goSafetySet()\">安全设置</a></li>");
	
	if(mobileNo){
		var reg = /(\d{3})\d{4}(\d{4})/;
		var mobile=mobileNo.replace(reg,"$1****$2");
		$("#mobileNo").html(mobile);
	}
	//跳转表格2
	$("#toChangeMobileNoPage").click(function(){
		window.location.href=root+"/paymentPassword/chengeMobileNoPage.action";
	});
});