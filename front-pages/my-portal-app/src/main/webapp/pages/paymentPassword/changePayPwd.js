var getMobileProvingCodeUrl = root+'/modileTelephoneNoteProving/getMobileNoProving.action';
var ProvingIdentityUrl = root+'/paymentPassword/provingPayPwd.action';
var changePayPwdUrl = root+'/paymentPassword/changePayPwd.action';
var noteProvingclick=true;
var tableOne=false;
$(function() {
	//更改导航栏内容
	parent.$("#naveBar").append("<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a href=\"#\">修改密码</a></li>");
	//加载绑定手机号
	if(mobileNo){
		var reg = /(\d{3})\d{4}(\d{4})/;
		var mobile=mobileNo.replace(reg,"$1****$2");
		$(".mobileNo").html(mobile);
	}
	//跳转表格2
	$("#table-one-button").click(function(){
		gotoTableTwo();
	});
	//跳转表格3
	$("#table-two-button").click(function(){
		gotoTableThree();
	});
	//跳转资金账户首页
	$("#back-home").click(function(){
		window.location.href=root+"/account/provingAccount.action";
	});
	//忘记密码跳到安全设置页面
	$("#forget-pay-pwd").click(function(){
		window.location.href=root+"/paymentPassword/resetPayPwdHomePage.action";
	});
	//验证身份获取短信验证码
	$("#get-proving-note-code").click(function(){
		var imgText = $('#proving-img-text').val();
		if(!imgText){
			$('#proving-img-text-back').attr('style','color:red;');
			$('#proving-img-text-back').html('请输入图形验证码！');
			return;
		}
		if(!imgReg.test(imgText)){
			$('#proving-img-text-back').attr('style','color:red;');
			$('#proving-img-text-back').html('请正确输入图形验证码！');
			return;
		}
		if(noteProvingclick){
			noteProving(imgText,$('#get-proving-note-code'),mobileNo);
		}
	});
	//点击帮助
	$("#helpButton").toggle(function(){
		$("#helpContext").css("display","block");
	},
	function(){
		$("#helpContext").css("display","none");
	});
	//验证密码强度
	$("#new-pay-pwd").keyup(function(){
		provingPWDStringency($('#new-pay-pwd').val());
	});
	//验证身份图片验证码输入状态
	$("#proving-img-text").blur(function(){
		if($("#proving-img-text").val()&&imgReg.test($("#proving-img-text").val())){
			$('#proving-img-text-back').html('');
		}else{
			$('#proving-img-text-back').attr('style','color:red;');
			$('#proving-img-text-back').html('请正确输入图形验证码！');
		}
	});
	//验证身份短信验证码状态
	$("#proving-note-code").blur(function(){
		if($("#proving-note-code").val()&&mobileCodeReg.test($("#proving-note-code").val())){
			$('#proving-note-text-back').html('');
		}else{
			$('#proving-note-text-back').attr('style','color:red;');
			$('#proving-note-text-back').html('请正确输入短信验证码！');
		}
	});
	//从新选择效验方式
	$("#to_changePwd_home").click(function(){
		window.location.href=root+"/paymentPassword/resetPayPwdHomePage.action";
	});
	//验证身份交易密码输入状态
	$("#proving-pay-pwd").blur(function(){
		if($("#proving-pay-pwd").val()&&pwdReg.test($("#proving-pay-pwd").val())){
			$('#proving-pay-pwd-back').html('');
		}else{
			$('#proving-pay-pwd-back').attr('style','color:red;');
			$('#proving-pay-pwd-back').html('请正确输入支付密码！');
		}
	});
	//验证新密码
	$("#new-pay-pwd").blur(function(){
		if(provingPwd($("#new-pay-pwd").val())){
			$('#new-pay-pwd-back').attr('style','color:red;');
			$('#new-pay-pwd-back').html('密码安全等级过低，请重新设置！');
		}else if($("#new-pay-pwd").val()&&pwdReg.test($("#new-pay-pwd").val())){
			$('#new-pay-pwd-back').html('');
		}else{
			$('#new-pay-pwd-back').attr('style','color:red;');
			$('#new-pay-pwd-back').html('请正确输入新支付密码！');
		}
	});
	//验证重复新密码
	$("#pay-pwd-proving").blur(function(){
		if($("#pay-pwd-proving").val()==$("#new-pay-pwd").val()){
			$('#pay-pwd-proving-back').html('');
		}else{
			$('#pay-pwd-proving-back').attr('style','color:red;');
			$('#pay-pwd-proving-back').html('两次输入的支付密码不一致！');
		}
	});
});

//下一步跳转至表格2
function gotoTableTwo(){
	var mobileCode = $('#proving-note-code').val();
	var payPwd = $('#proving-pay-pwd').val();
	var imgText = $('#proving-img-text').val();
	if(!imgText||!imgReg.test(imgText)){
		$('#proving-img-text-back').attr('style','color:red;');
		$('#proving-img-text-back').html('请正确输入图形验证码！');
		return;
	}
	if(!mobileCode||!mobileCodeReg.test(mobileCode)){
		$('#proving-note-text-back').attr('style','color:red;');
		$('#proving-note-text-back').html('请正确输入短信验证码！');
		return;
	}
	if(!payPwd||!pwdReg.test(payPwd)){
		$('#proving-pay-pwd-back').attr('style','color:red;');
		$('#proving-pay-pwd-back').html('请正确输入支付密码！');
		return;
	}
	$.ajax({
	    url:ProvingIdentityUrl,
	    dataType:"json",
	    data:{'payPwd':hex_md5(payPwd),'mobileCode':mobileCode,'imgProvingText':imgText},
	    type:"POST",
	    success:function(data){
	    	if(data=="1"){
	    		tableOne=true;
	    		$("#greed-line").attr("class", "green-line2");
	    		$("#icon-step-one").attr("class", "icon-step1");
	    		$("#icon-step-two").attr("class", "icon-step3");
	    		$("#icon-step-three").attr("class", "icon-step4");
	    		$("#txt-step-one").attr("class", "txt-step1");
	    		$("#txt-step-two").attr("class", "txt-step1");
	    		$("#txt-step-three").attr("class", "txt-step3");
	    		$("#form-one").attr("style", "display: none;");
	    		$("#form-two").attr("style", "display: block;");
	    		$("#form-three").attr("style", "display: none;");
	    	}
	    	if(data=="-4"){
	    		alert("账户被锁定！");
	    	}
	    	if(data=="-3"){
	    		$('#proving-pay-pwd-back').attr('style','color:red;');
				$('#proving-pay-pwd-back').html('请正确输入支付密码！');
	    	}
	    	if(data=="-2"){
	    		$('#proving-note-text-back').attr('style','color:red;');
				$('#proving-note-text-back').html('请正确输入短信验证码！');
	    	}
	    	if(data=="-1"){
	    		alert("身份验证失败！");
	    	}
	    	if(data=="-7"){
	    		$('#proving-img-text-back').attr('style','color:red;');
	    		$('#proving-img-text-back').html('请正确输入图形验证码！');
	    	}
	    }
	});
}
//下一步跳转至表格3
function gotoTableThree(){
	var payPwd = $('#proving-pay-pwd').val();
	var newPayPwd = $('#new-pay-pwd').val();
	var payPwdproving = $('#pay-pwd-proving').val();
	var mobileCode = $('#proving-note-code').val();
	if(provingPwd(newPayPwd)){
		$('#new-pay-pwd-back').attr('style','color:red;');
		$('#new-pay-pwd-back').html('密码安全等级过低，请重新设置！');
		return
	}
	if(!newPayPwd||!pwdReg.test(newPayPwd)){
		$('#new-pay-pwd-back').attr('style','color:red;');
		$('#new-pay-pwd-back').html('请正确输入新支付密码！');
		return;
	}
	if(newPayPwd!=payPwdproving){
		$('#pay-pwd-proving-back').attr('style','color:red;');
		$('#pay-pwd-proving-back').html('两次输入的支付密码不一致！');
		return;
	}
	$.ajax({
	    url:changePayPwdUrl,
	    dataType:"json",
	    data:{'newPayPwd':hex_md5(newPayPwd),'oldPayPwd':hex_md5(payPwd)},
	    type:"POST",
	    success:function(data){
	    	if(data=="1"&&tableOne){
	    		$("#greed-line").attr("class", "green-line");
	    		$("#icon-step-one").attr("class", "icon-step1");
	    		$("#icon-step-two").attr("class", "icon-step2");
	    		$("#icon-step-three").attr("class", "icon-step2");
	    		$("#txt-step-one").attr("class", "txt-step1");
	    		$("#txt-step-two").attr("class", "txt-step1 w100");
	    		$("#txt-step-three").attr("class", "txt-step1");
	    		$("#form-one").attr("style", "display: none;");
	    		$("#form-two").attr("style", "display: none;");
	    		$("#form-three").attr("style", "display: block;");
	    	}
	    	if(data=="-1"){
	    		alert('修改支付密码失败！');
	    		window.setTimeout(function(){
					window.location.href=root+"/account/safetySet.action";
				}, 5000);
	    	}
	    	if(data=="-2"){
	    		$('#new-pay-pwd-back').attr('style','color:red;');
	    		$('#new-pay-pwd-back').html('请正确输入新支付密码！');
	    	}
	    }
	});
}
//验证密码强度
function provingPWDStringency(pwd){
	var num = 0;
	var lowercase = 0;
	var capital = 0;
	var specialChar = 0;
	for(var i=0;i<pwd.length;i++){
		charCode = pwd.charCodeAt(i);
		if (charCode >= 48 && charCode <= 57){
			num = 1;
			} //数字
		else if (charCode >= 65 && charCode <= 90){
			capital = 1;
			} //大写
		else if (charCode >= 97 && charCode <= 122){
			lowercase = 1;
			} //小写
		else {
			specialChar=1;
		}
	}
    testnum = num+capital+lowercase+specialChar;
    if(!pwd){
    	$('#pay-pwd-strength_L').attr('style','background-color: #CFCFCF');
    	$('#pay-pwd-strength_M').attr('style','background-color: #CFCFCF');
    	$('#pay-pwd-strength_H').attr('style','background-color: #CFCFCF');
    }
    if(pwd&&testnum==1){
    	$('#pay-pwd-strength_L').attr('style','background-color: #FF4500');
    	$('#pay-pwd-strength_M').attr('style','background-color: #CFCFCF');
    	$('#pay-pwd-strength_H').attr('style','background-color: #CFCFCF');
    }
    if(pwd&&testnum==2){
    	$('#pay-pwd-strength_L').attr('style','background-color: #EEAD0E');
    	$('#pay-pwd-strength_M').attr('style','background-color: #EEAD0E');
    	$('#pay-pwd-strength_H').attr('style','background-color: #CFCFCF');
    }
    if(pwd&&testnum==4||pwd&&testnum==3){
    	$('#pay-pwd-strength_L').attr('style','background-color: #228B22');
    	$('#pay-pwd-strength_M').attr('style','background-color: #228B22');
    	$('#pay-pwd-strength_H').attr('style','background-color: #228B22');
    }
    
}
//获取短信验证码
function noteProving(imgText,NoteObject,mobile){
	$.ajax({
	    url:getMobileProvingCodeUrl,
	    dataType:"json",
	    data:{mobileNo:mobile,'accountNo':accountNo,'imgProvingText':imgText},
	    type:"POST",
	    success:function(data){
	    	if(data&&data.result=="-2"){
				alert("图形验证码错误！");
				return
			}
			if(data&&data.result=="1"){
				var dateTime = 60;
				intervaljob=window.setInterval(function(){
					var data=dateTime--;
					if(data>0){
						noteProvingclick=false;
						NoteObject.val("获取成功"+data+"s");
					}else{
						noteProvingclick=true;
						NoteObject.val("获取短信验证码");
						window.clearTimeout(intervaljob);
					}
				}, 1000); 
			}
			if(data&&data.result=="-1"){
				var dateTime = 5;
				intervaljob=window.setInterval(function(){
					var data=dateTime--;
					if(data>0){
						noteProvingclick=false;
						NoteObject.val("获取失败"+data+"s");
					}else{
						noteProvingclick=true;
						NoteObject.val("获取短信验证码");
						window.clearTimeout(intervaljob);
					}
				}, 1000); 
			}
	    }
	});
}
//验证密码是否是连续或重复的字符
function provingPwd(pwd){
	// 全部重复
    var repeat = true;
    // 连续字符
    var series = true;
    var len = pwd.length;
    var first = pwd.charAt(0);
    for(var i=1;i<len;i++){
        repeat = repeat && pwd.charAt(i) == first;
        series = series && pwd.charCodeAt(i) == pwd.charCodeAt(i-1) + 1;
    }
    if(!series){
    	series = true;
    	for(var i=1;i<len;i++){
            series = series && pwd.charCodeAt(i-1) == pwd.charCodeAt(i) + 1;
        }
    }
    return (repeat || series);
}