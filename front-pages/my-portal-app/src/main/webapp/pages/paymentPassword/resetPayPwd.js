//数据对象模板
var getMobileProvingCodeUrl = root+'/modileTelephoneNoteProving/getMobileNoProving.action';
var provingMobileCodeUrl = root+'/paymentPassword/provingMobileCode.action';
var setPayPwdUrl = root+'/paymentPassword/setPayPwd.action';
var cryptoguardProvingUrl = root+'/paymentPassword/cryptoguardProving.action';
var getCryptoguardUrl = root+'/paymentPassword/getCryptoguard.action';
var tableOne = true;
var tableTwo = true;
var noteProvingclick=true;
$(function() {
	//更改导航栏内容
	parent.$("#naveBar").append("<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a href=\"#\">重置密码</a></li>");
	//加载绑定手机号
	if(mobileNo){
		var reg = /(\d{3})\d{4}(\d{4})/;
		var mobile=mobileNo.replace(reg,"$1****$2");
		$(".mobileNo").html(mobile);
	}
	//获取密保问题
	getCryptoguardList();
	//跳转表格2
	$("#table-one-button").click(function(){
		gotoTableTwo();
	});
	//跳转表格3
	$("#table-two-button").click(function(){
		gotoTableThree();
	});
	//跳转表格3
	$("#table-three-button").click(function(){
		gotoTableFour();
	});
	//跳转资金账户首页
	$("#back-home").click(function(){
		window.location.href=root+"/account/provingAccount.action";
	});
	//忘记密保问题
	$("#forget-cryptoguard").click(function(){
		window.location.href=root+"/paymentPassword/changeCryptoguardHomePage.action";
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
	//验证密保答案状态
	$("#cryptoguard-answer").blur(function(){
		if($("#cryptoguard-answer").val()&&cryptoguardReg.test($("#cryptoguard-answer").val())){
			$('#cryptoguard-answer-back').html('');
		}else{
			$('#cryptoguard-answer-back').attr('style','color:red;');
			$('#cryptoguard-answer-back').html('请正确输入密保答案！');
		}
	});
	//验证安全问题选项改变情况答案内容
	$('#cryptoguard-query').change(function(){
		$('#cryptoguard-answer').val('');
		$('#cryptoguard-query-back').html('');
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

//获取密保问题数列
function getCryptoguardList(){
	$.ajax({
	    url:getCryptoguardUrl,
	    dataType:"json",
	    data:{},
	    type:"POST",
	    success:function(data){
	    	if(data){
	    		for(var key in data){
	    			$('#cryptoguard-query').append('<option value=\"'+key+'\">'+data[key]+'</option>');
	    		} 
	    	}
	    }
	});
}

//下一步跳转至表格2
function gotoTableTwo(){
	var imgText = $('#proving-img-text').val();
	var mobileCode = $('#proving-note-code').val();
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
	$.ajax({
	    url:provingMobileCodeUrl,
	    dataType:"json",
	    data:{'mobileCode':mobileCode,'imgProvingText':imgText},
	    type:"POST",
	    success:function(data){
	    	if(data=="1"){
	    		tableOne=true;
	    		$("#greed-line").attr("class", "green-line2");
	    		$("#icon-step-one").attr("class", "icon-step1");
	    		$("#icon-step-two").attr("class", "icon-step3");
	    		$("#icon-step-three").attr("class", "icon-step4");
	    		$("#icon-step-four").attr("class", "icon-step4");
	    		$("#txt-step-one").attr("class", "txt-step1");
	    		$("#txt-step-two").attr("class", "txt-step1 w100");
	    		$("#txt-step-three").attr("class", "txt-step3 w100");
	    		$("#txt-step-four").attr("class", "txt-step4");
	    		$("#form-one").attr("style", "display: none;");
	    		$("#form-two").attr("style", "display: block;");
	    		$("#form-three").attr("style", "display: none;");
	    		$("#form-four").attr("style", "display: none;");
	    	}
	    	if(data=="-1"){
	    		alert('短信验证码验证失败！');
	    	}
	    	if(data=="-2"){
	    		$('#proving-note-text-back').attr('style','color:red;');
				$('#proving-note-text-back').html('请正确输入短信验证码！');
	    	}
	    	if(data=="-7"){
	    		$('#proving-img-text-back').attr('style','color:red;');
	    		$('#proving-img-text-back').html('请正确输入图形验证码！');
	    	}
	    	if(data=="-4"){
	    		alert('账户被锁定！');
	    	}
	    }
	});
}
//下一步跳转至表格3
function gotoTableThree(){
	var cryptoguardQuery = $('#cryptoguard-query').val();
	var cryptoguardAnswer = $('#cryptoguard-answer').val();
	if(cryptoguardQuery){
		$('#cryptoguard-query-back').html('');
	}else{
		$('#cryptoguard-query-back').attr('style','color:red;');
		$('#cryptoguard-query-back').html('请选择安全密保问题！');
		return;
	}
	if(cryptoguardAnswer&&cryptoguardReg.test(cryptoguardAnswer)){
		$('#cryptoguard-answer-back').html('');
	}else{
		$('#cryptoguard-answer-back').attr('style','color:red;');
		$('#cryptoguard-answer-back').html('请输入安全密保答案！');
		return;
	}
	$.ajax({
	    url:cryptoguardProvingUrl,
	    dataType:"json",
	    data:{'cryptoguardQuery':cryptoguardQuery,'cryptoguardAnswer':cryptoguardAnswer},
	    type:"POST",
	    success:function(data){
	    	if(data=="1"&&tableOne){
	    		tableTwo = true;
	    		$("#greed-line").attr("class", "green-line");
	    		$("#icon-step-one").attr("class", "icon-step1");
	    		$("#icon-step-two").attr("class", "icon-step2");
	    		$("#icon-step-three").attr("class", "icon-step3");
	    		$("#icon-step-four").attr("class", "icon-step4");
	    		$("#txt-step-one").attr("class", "txt-step1");
	    		$("#txt-step-two").attr("class", "txt-step1 w100");
	    		$("#txt-step-three").attr("class", "txt-step1 w100");
	    		$("#txt-step-four").attr("class", "txt-step4");
	    		$("#form-one").attr("style", "display: none;");
	    		$("#form-two").attr("style", "display: none;");
	    		$("#form-three").attr("style", "display: block;");
	    		$("#form-four").attr("style", "display: none;");
	    	}
	    	if(data=="-1"){
	    		alert('验证密保问题失败！');
	    	}
	    	if(data=="-6"){
	    		$('#cryptoguard-query-back').attr('style','color:red;');
	    		$('#cryptoguard-query-back').html('请选择安全密保问题！');
	    	}
	    	if(data=="-5"){
	    		$('#cryptoguard-answer-back').attr('style','color:red;');
	    		$('#cryptoguard-answer-back').html('请输入正确的安全密保答案！');
	    	}
	    	if(data=="-4"){
	    		alert('账户被锁定！');
	    	}
	    }
	});
}
//下一步跳转至表格4
function gotoTableFour(){
	var newPayPwd = $('#new-pay-pwd').val();
	var payPwdproving = $('#pay-pwd-proving').val();
	var mobileCode = $('#proving-note-code').val();
	if(!newPayPwd||!pwdReg.test(newPayPwd)){
		$('#new-pay-pwd-back').attr('style','color:red;');
		$('#new-pay-pwd-back').html('请正确输入新支付密码！');
		return;
	}
	if(provingPwd(newPayPwd)){
		$('#new-pay-pwd-back').attr('style','color:red;');
		$('#new-pay-pwd-back').html('密码安全等级过低，请重新设置！');
		return
	}
	if(newPayPwd!=payPwdproving){
		$('#pay-pwd-proving-back').attr('style','color:red;');
		$('#pay-pwd-proving-back').html('两次输入的支付密码不一致！');
		return;
	}
	$.ajax({
	    url:setPayPwdUrl,
	    dataType:"json",
	    data:{'newPayPwd':hex_md5(newPayPwd),'syscode':mobileCode},
	    type:"POST",
	    success:function(data){
	    	if(data=="1"&&tableOne&&tableTwo){
	    		$("#greed-line").attr("class", "green-line3");
	    		$("#icon-step-one").attr("class", "icon-step1");
	    		$("#icon-step-two").attr("class", "icon-step1");
	    		$("#icon-step-three").attr("class", "icon-step1");
	    		$("#icon-step-four").attr("class", "icon-step1");
	    		$("#txt-step-one").attr("class", "txt-step1");
	    		$("#txt-step-two").attr("class", "txt-step1 w100");
	    		$("#txt-step-three").attr("class", "txt-step1 w100");
	    		$("#txt-step-four").attr("class", "txt-step1");
	    		$("#form-one").attr("style", "display: none;");
	    		$("#form-two").attr("style", "display: none;");
	    		$("#form-three").attr("style", "display: none;");
	    		$("#form-four").attr("style", "display: block;");
	    	}
	    	if(data=="-1"){
	    		alert('设置密码失败！');
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