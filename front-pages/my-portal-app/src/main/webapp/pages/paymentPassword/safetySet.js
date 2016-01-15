
var accountPasswordTypeUrl = root+'/account/accountPasswordType.action';
var judgePaymentPwdQueryUrl = root+'/account/judgePaymentPwdQuery.action';
var judgePayPwd = false;
var judgePayPwdQuery = false;

$(function() {
	//修改导航栏
	parent.$("#naveBar").html("<li class=\"fl pr2 color_chip\"><a onclick=\"cheWangHomePage()\">车旺首页</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"cheWangAccountCenter()\">账户中心</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"goAccountHome()\">资金帐户</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"goSafetySet()\">安全设置</a></li>");
	//加载绑定手机号
	if(mobileNo){
		var reg = /(\d{3})\d{4}(\d{4})/;
		var mobile=mobileNo.replace(reg,"$1****$2");
		$("#mobileNo").html(mobile);
	}
	//判断交易密码是否是原始密码
	accountPasswordType();
	//查询是否设置密保问题
	judgePaymentPwdQuery();
	
	//修改绑定手机号码
	$("#changeMobileNo").click(function(){
		window.location.href=root+"/paymentPassword/chengeMobileNoHomePage.action";
	});
});

/* Ajax查询账户密码判断 */
function accountPasswordType() {
	$.ajax({
		url : accountPasswordTypeUrl,
		data : {'accountNo':accountNo},
		dataType : 'JSON',
		success : function(data){
			if(data&&data.accountPasswordType){
				if(data.accountPasswordType=="1"){
					judgePayPwd=true;
					$("#pay-pwd-img").attr('src','../images/account/s7.png');
					$("#pwd-button").html("<a id=\"reset-pay-pwd\">重置</a>");
					//重置交易密码
					$("#reset-pay-pwd").click(function(){
						window.location.href=root+"/paymentPassword/resetPayPwdHomePage.action";
					});
					//根据交易密码和密保问题判断安全等级强度
					judgeSafety();
				}else{
					$("#pay-pwd-img").attr('src','../images/account/s6.png');
					$("#pwd-button").html("<a id=\"set-pay-pwd\">设置</a>");
					//设置交易密码
					$("#set-pay-pwd").click(function(){
						window.location.href=root+"/paymentPassword/setPayPwdPage.action";
					});
				}
			}
		}
	});
}

//查询是否设置密保问题
function judgePaymentPwdQuery(){
	$.ajax({
		url : judgePaymentPwdQueryUrl,
		data : {'accountNo':accountNo},
		dataType : 'JSON',
		success : function(data){
			if(data&&data.pwdQuery){
				if(data.pwdQuery=="1"){
					judgePayPwdQuery=true;
					$("#pwd-query-img").attr('src','../images/account/s7.png');
					$("#pwd-query-button").html("<a id=\"change-pwd-query\">修改</a>");
					$("#pwdQueryNum").html(data.pwdQueryNum);
					//修改密保问题
					$("#change-pwd-query").click(function(){
						window.location.href=root+"/paymentPassword/changeCryptoguardHomePage.action";
					});
					//根据交易密码和密保问题判断安全等级强度
					judgeSafety();
				}
			}else{
				$("#pwd-query-img").attr('src','../images/account/s6.png');
				$("#pwd-query-button").html("<a id=\"set-pwd-query\">设置</a> ");
				//设置密保问题
				$("#set-pwd-query").click(function(){
					window.location.href=root+"/paymentPassword/setCryptoguardHomePage.action";
				});
			}
		}
	});
}
//判断安全等级
function judgeSafety(){
	if(judgePayPwd&&judgePayPwdQuery){
		$("#strength_L").attr('style','background-color: #228B22;');
		$("#strength_M").attr('style','background-color: #228B22;');
		$("#strength_H").attr('style','background-color: #228B22;');
	}else if(judgePayPwd){
		$("#strength_L").attr('style','background-color: #EEAD0E;');
		$("#strength_M").attr('style','background-color: #EEAD0E;');
	}
}