var getMobileProvingCodeUrl = root+'/modileTelephoneNoteProving/getMobileNoProving.action';
var ProvingIdentityUrl = root+'/paymentPassword/provingPayPwd.action';
var addCryptoguardUrl = root+'/paymentPassword/addTryptoguard.action';
var getCryptoguardQueryCodeUrl = root+'/paymentPassword/getCryptoguardQueryCode.action';
var noteProvingclick=true;
var tokenNum;
var param = {};
var tableOne=false;
var codeData = {};
$(function() {
	//更改导航栏内容
	parent.$("#naveBar").append("<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a href=\"#\">设置密保</a></li>");
	//加载绑定手机号
	if(mobileNo){
		var reg = /(\d{3})\d{4}(\d{4})/;
		var mobile=mobileNo.replace(reg,"$1****$2");
		$("#mobileNo").html(mobile);
	}
	//获取密保问题的码表信息
	getCryptoguard();
	//跳转表格2
	$("#table-one-button").click(function(){
		gotoTableTwo();
	});
	//跳转表格3
	$("#table-two-button").click(function(){
		gotoTableThree();
	});
	//重置密码保护问题
	$("#submit-button").click(function(){
		changeCryptoguard();
	});
	//跳转资金账户首页
	$("#back-home").click(function(){
		window.location.href=root+"/account/provingAccount.action";
	});
	//返回修改
	$("#back-change-home").click(function(){
		$("#greed-line").attr("class", "green-line2");
		$("#icon-step-one").attr("class", "icon-step1");
		$("#icon-step-two").attr("class", "icon-step3");
		$("#icon-step-three").attr("class", "icon-step4");
		$("#txt-step-one").attr("class", "txt-step1");
		$("#txt-step-two").attr("class", "txt-step1 w100");
		$("#txt-step-three").attr("class", "txt-step3");
		$("#form-one").attr("style", "display: none;");
		$("#form-two").attr("style", "display: block;");
		$("#form-three").attr("style", "display: none;");
		$("#form-four").attr("style", "display: none;");
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
	//验证身份交易密码输入状态
	$("#proving-pay-pwd").blur(function(){
		if($("#proving-pay-pwd").val()&&pwdReg.test($("#proving-pay-pwd").val())){
			$('#proving-pay-pwd-back').html('');
		}else{
			$('#proving-pay-pwd-back').attr('style','color:red;');
			$('#proving-pay-pwd-back').html('请正确输入支付密码！');
		}
	});
	
	//验证密保答案一输入状态
	$("#answer-one").blur(function(){
		if($("#answer-one").val()&&cryptoguardReg.test($("#answer-one").val())){
			$('#answer-one-back').html('');
		}else{
			$('#answer-one-back').attr('style','color:red;');
			$('#answer-one-back').html('请正确输入密保答案一！');
		}
	});
	//验证安全问题选项改变情况答案内容
	$('#query-one').change(function(){
		$('#answer-one').val('');
		$('#query-one-back').html('');
		if($('#query-one').val()==$('#query-two').val()||$('#query-one').val()==$('#query-three').val()){
			$('#query-one-back').attr('style','color:red;');
			$('#query-one-back').html('不能重复选取密保问题！');
		}
	});
	//验证密保答案二输入状态
	$("#answer-two").blur(function(){
		if($("#answer-two").val()&&cryptoguardReg.test($("#answer-one").val())){
			$('#answer-two-back').html('');
		}else{
			$('#answer-two-back').attr('style','color:red;');
			$('#answer-two-back').html('请正确输入密保答案二！');
		}
	});
	//验证安全问题选项改变情况答案内容
	$('#query-two').change(function(){
		$('#answer-two').val('');
		$('#query-two-back').html('');
		if($('#query-one').val()==$('#query-two').val()||$('#query-two').val()==$('#query-three').val()){
			$('#query-two-back').attr('style','color:red;');
			$('#query-two-back').html('不能重复选取密保问题！');
		}
	});
	//验证密保答案三输入状态
	$("#answer-three").blur(function(){
		if($("#answer-three").val()&&cryptoguardReg.test($("#answer-one").val())){
			$('#answer-three-back').html('');
		}else{
			$('#answer-three-back').attr('style','color:red;');
			$('#answer-three-back').html('请正确输入密保答案三！');
		}
	});
	//验证安全问题选项改变情况答案内容
	$('#query-three').change(function(){
		$('#answer-three').val('');
		$('#query-three-back').html('');
		if($('#query-one').val()==$('#query-three').val()||$('#query-two').val()==$('#query-three').val()){
			$('#query-three-back').attr('style','color:red;');
			$('#query-three-back').html('不能重复选取密保问题！');
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
	    		$("#txt-step-two").attr("class", "txt-step1 w100");
	    		$("#txt-step-three").attr("class", "txt-step4");
	    		$("#form-one").attr("style", "display: none;");
	    		$("#form-two").attr("style", "display: block;");
	    		$("#form-three").attr("style", "display: none;");
	    		$("#form-four").attr("style", "display: none;");
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
	
	if($('#query-one').val()){
		param['queryOne']=$('#query-one').val();
	}else{
		$('#query-one-back').attr('style','color:red;');
		$('#query-one-back').html('请选择密保问题一！');
		return;
	}
	if($('#query-two').val()){
		param['queryTwo']=$('#query-two').val();
	}else{
		$('#query-two-back').attr('style','color:red;');
		$('#query-two-back').html('请选择密保问题二！');
		return;
	}
	if($('#query-three').val()){
		param['queryThree']=$('#query-three').val();
	}else{
		$('#query-three-back').attr('style','color:red;');
		$('#query-three-back').html('请选择密保问题三！');
		return;
	}
	if($('#answer-one').val()&&cryptoguardReg.test($('#answer-one').val())){
		param['answerOne']=$('#answer-one').val();
	}else{
		$('#answer-one-back').attr('style','color:red;');
		$('#answer-one-back').html('请输入密保答案一！');
		return;
	}
	if($('#answer-two').val()&&cryptoguardReg.test($('#answer-two').val())){
		param['answerTwo']=$('#answer-two').val();
	}else{
		$('#answer-two-back').attr('style','color:red;');
		$('#answer-two-back').html('请输入密保答案二！');
		return;
	}
	if($('#answer-three').val()&&cryptoguardReg.test($('#answer-three').val())){
		param['answerThree']=$('#answer-three').val();
	}else{
		$('#answer-three-back').attr('style','color:red;');
		$('#answer-three-back').html('请输入密保答案三！');
		return;
	}
	if($('#query-one').val()&&$('#query-two').val()&&$('#query-one').val()==$('#query-two').val()){
		alert("密保问题不可重复选取！");
		return;
	}
	if($('#query-one').val()&&$('#query-three').val()&&$('#query-one').val()==$('#query-three').val()){
		alert("密保问题不可重复选取！");
		return;
	}
	if($('#query-three').val()&&$('#query-two').val()&&$('#query-three').val()==$('#query-two').val()){
		alert("密保问题不可重复选取！");
		return;
	}
	
	if(tableOne){
		$("#form-one").attr("style", "display: none;");
		$("#form-two").attr("style", "display: none;");
		$("#form-three").attr("style", "display: block;");
		$("#form-four").attr("style", "display: none;");
	}
	$('#quary-one-proving').html(codeData[param.queryOne]);
	$('#answer-one-proving').html(param.answerOne);
	$('#quary-two-proving').html(codeData[param.queryTwo]);
	$('#answer-two-proving').html(param.answerTwo);
	$('#quary-three-proving').html(codeData[param.queryThree]);
	$('#answer-three-proving').html(param.answerThree);
	
}
//添加密码保护问题
function changeCryptoguard(){
	$.ajax({
	    url:addCryptoguardUrl,
	    dataType:"json",
	    data:param,
	    type:"POST",
	    success:function(data){
	    	if(data=='1'){
	    		$("#greed-line").attr("class", "green-line");
	    		$("#icon-step-one").attr("class", "icon-step1");
	    		$("#icon-step-two").attr("class", "icon-step2");
	    		$("#icon-step-three").attr("class", "icon-step2");
	    		$("#txt-step-one").attr("class", "txt-step1");
	    		$("#txt-step-two").attr("class", "txt-step1 w100");
	    		$("#txt-step-three").attr("class", "txt-step1");
	    		$("#form-one").attr("style", "display: none;");
	    		$("#form-two").attr("style", "display: none;");
	    		$("#form-three").attr("style", "display: none;");
	    		$("#form-four").attr("style", "display: block;");
			}
	    	if(data=='-1'){
	    		alert('添加密码保护失败！');
	    		window.setTimeout(function(){
					window.location.href=root+"/account/safetySet.action";
				}, 5000);
			}
	    	if(data=='-2'){
	    		alert('密保问题或答案有误，请返回修改！');
	    	}
	    }
	});
}
//获取密保问题的码表信息
function getCryptoguard(){
	$.ajax({
	    url:getCryptoguardQueryCodeUrl,
	    dataType:"json",
	    data:{},
	    type:"POST",
	    success:function(data){
	    	if(data){
	    		codeData = data;
	    		for(var key in data){
	    			$('#query-one').append('<option value=\"'+key+'\">'+data[key]+'</option>');
					$('#query-two').append('<option value=\"'+key+'\">'+data[key]+'</option>');
					$('#query-three').append('<option value=\"'+key+'\">'+data[key]+'</option>');
	    		} 
	    	}
	    }
	});
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

