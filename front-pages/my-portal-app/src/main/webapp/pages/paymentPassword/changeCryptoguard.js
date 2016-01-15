var provingPayPwdAndCryptoguardUrl = root+'/paymentPassword/provingPayPwdAndCryptoguard.action';
var changeCryptoguardUrl = root+'/paymentPassword/changeTryptoguard.action';
var getCryptoguardQueryCodeUrl = root+'/paymentPassword/getCryptoguardQueryCode.action';
var getCryptoguardUrl = root+'/paymentPassword/getCryptoguard.action';
var tableOne=false;
var param={};
var codeData={};
$(function() {
	//更改导航栏内容
	parent.$("#naveBar").append("<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a href=\"#\">修改密保</a></li>");
	//加载绑定手机号
	if(mobileNo){
		var reg = /(\d{3})\d{4}(\d{4})/;
		var mobile=mobileNo.replace(reg,"$1****$2");
		$(".mobileNo").html(mobile);
	}
	//获取密保问题的码表信息
	getCryptoguard();
	//获取密保问题数列
	getCryptoguardList();
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
		$('#submit-button').unbind("click"); 
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
	//从新选择效验方式
	$("#to_changeSafety_home").click(function(){
		window.location.href=root+"/paymentPassword/changeCryptoguardHomePage.action";
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
});

//下一步跳转至表格2
function gotoTableTwo(){
	var queryOne = $('#cryptoguard-query').val();
	var answerOne = $('#cryptoguard-answer').val();
	var payPwd = $('#proving-pay-pwd').val();
	if(!queryOne){
		$('#cryptoguard-query-back').attr('style','color:red;');
		$('#cryptoguard-query-back').html('请选择密保问题！');
		return;
	}
	if(!answerOne||!cryptoguardReg.test(answerOne)){
		$('#cryptoguard-answer-back').attr('style','color:red;');
		$('#cryptoguard-answer-back').html('请正确输入密保答案！');
		return;
	}
	if(!payPwd||!pwdReg.test(payPwd)){
		$('#proving-pay-pwd-back').attr('style','color:red;');
		$('#proving-pay-pwd-back').html('请正确输入支付密码！');
		return;
	}
	$.ajax({
	    url:provingPayPwdAndCryptoguardUrl,
	    dataType:"json",
	    data:{'payPwd':hex_md5(payPwd),'queryOne':queryOne,'answerOne':answerOne},
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
	    		$("#txt-step-three").attr("class", "txt-step3");
	    		$("#form-one").attr("style", "display: none;");
	    		$("#form-two").attr("style", "display: block;");
	    		$("#form-three").attr("style", "display: none;");
	    		$("#form-four").attr("style", "display: none;");
	    	}
	    	if(data=="-1"){
	    		alert('身份验证失败！');
	    	}
	    	if(data=="-4"){
	    		alert('账户被锁定！');
	    	}
	    	if(data=="-3"){
	    		$('#proving-pay-pwd-back').attr('style','color:red;');
				$('#proving-pay-pwd-back').html('请正确输入支付密码！');
	    	}
	    	if(data=="-5"){
	    		$('#cryptoguard-answer-back').attr('style','color:red;');
	    		$('#cryptoguard-answer-back').html('请正确输入问题答案！');
	    	}
	    	if(data=="-6"){
	    		$('#cryptoguard-query-back').attr('style','color:red;');
	    		$('#cryptoguard-query-back').html('请正确选择密保问题！');
	    	}
	    }
	});
}
//下一步跳转至表格3
function gotoTableThree(){

//	if($('#query-one').val()&&$('#answer-one').val()&&cryptoguardReg.test($('#answer-one').val())){
//		param['queryOne']=$('#query-one').val();
//		param['answerOne']=$('#answer-one').val();
//	}
//	if($('#query-two').val()&&$('#answer-two').val()&&cryptoguardReg.test($('#answer-two').val())){
//		param['queryTwo']=$('#query-two').val();
//		param['answerTwo']=$('#answer-two').val();
//	}
//	if($('#query-three').val()&&$('#answer-three').val()&&cryptoguardReg.test($('#answer-three').val())){
//		param['queryThree']=$('#query-three').val();
//		param['answerThree']=$('#answer-three').val();
//	}
	
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
//重置密码保护问题
function changeCryptoguard(){
	$.ajax({
	    url:changeCryptoguardUrl,
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
	    		alert('修改密码保护失败！');
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
	    		codeData=data;
	    		for(var key in data){
	    			$('#query-one').append('<option value=\"'+key+'\">'+data[key]+'</option>');
					$('#query-two').append('<option value=\"'+key+'\">'+data[key]+'</option>');
					$('#query-three').append('<option value=\"'+key+'\">'+data[key]+'</option>');
	    		} 
	    	}
	    }
	});
}
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