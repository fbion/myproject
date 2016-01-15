//数据对象模板
var getMobileProvingCodeUrl = root+'/modileTelephoneNoteProving/getMobileNoProving.action';
var getMobileProvingCodeAgainUrl = root+'/modileTelephoneNoteProving/getMobileNoProvingAgain.action';
var ProvingIdentityUrl = root+'/paymentPassword/provingPayPwd.action';
var changeMobileNoUrl = root+'/paymentPassword/updateMobileNo.action';
var tableOne = false;
var noteProvingclick=true;
var noteProvingclickAgain=true;
var tokenNum=null;
$(function() {
	//更改导航栏内容
	parent.$("#naveBar").append("<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a href=\"#\">修改绑定手机号</a></li>");
	//添加手机号码
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
	//修改内容获取短信验证码
	$("#get-set-note-code").click(function(){
		noteProvingclick=true;
		var imgText = $('#set-img-text').val();
		if(!imgText){
			$('#set-img-text-back').attr('style','color:red;');
			$('#set-img-text-back').html('请输入图形验证码！');
			return;
		}
		if(!imgReg.test(imgText)){
			$('#set-img-text-back').attr('style','color:red;');
			$('#set-img-text-back').html('请正确输入图形验证码！');
			return;
		}
		if(noteProvingclickAgain){
			noteProvingAgain(imgText,$('#get-set-note-code'),$('#set-mobileNo').val());
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
	//修改图片输入状态
	$("#set-img-text").blur(function(){
		if($("#set-img-text").val()&&imgReg.test($("#set-img-text").val())){
			$('#set-img-text-back').html('');
		}else{
			$('#set-img-text-back').attr('style','color:red;');
			$('#set-img-text-back').html('请正确输入图形验证码！');
		}
	});
	//修改短信验证码输入状态
	$("#set-note-code").blur(function(){
		if($("#set-note-code").val()&&mobileCodeReg.test($("#set-note-code").val())){
			$('#set-note-text-back').html('');
		}else{
			$('#set-note-text-back').attr('style','color:red;');
			$('#set-note-text-back').html('请正确输入短信验证码！');
		}
	});
	//新手机号码输入状态
	$("#set-mobileNo").blur(function(){
		if($("#set-mobileNo").val()&&mobileNoReg.test($("#set-mobileNo").val())){
			$('#set-mobileNo-back').html('');
		}else{
			$('#set-mobileNo-back').attr('style','color:red;');
			$('#set-mobileNo-back').html('请正确输入手机号码！');
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
	    		$("#formOne").attr("style", "display: none;");
	    		$("#formTwo").attr("style", "display: block;");
	    		$("#formThree").attr("style", "display: none;");
	    		$('#set-img-button').attr("src",root+"/imgCheckCode?d=" + new Date());
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
	var newMobileNo = $('#set-mobileNo').val();
	var mobileCode = $('#set-note-code').val();
	var imgText = $('#set-img-text').val();
	if(!imgText||!imgReg.test(imgText)){
		$('#set-img-text-back').attr('style','color:red;');
		$('#set-img-text-back').html('请正确输入图形验证码！');
		return;
	}
	if(!mobileCode||!mobileCodeReg.test(mobileCode)){
		$('#set-note-text-back').attr('style','color:red;');
		$('#set-note-text-back').html('请正确输入短信验证码！');
		return;
	}
	if(!newMobileNo||!mobileNoReg.test(newMobileNo)){
		$('#set-mobileNo-back').attr('style','color:red;');
		$('#set-mobileNo-back').html('请正确输入手机号码！');
		return;
	}
	$.ajax({
	    url:changeMobileNoUrl,
	    dataType:"json",
	    data:{'newMobileNo':newMobileNo,'mobileCode':mobileCode,'tokenNum':tokenNum,'imgProvingText':imgText},
	    type:"POST",
	    success:function(data){
	    	if(data=="1"&&tableOne){
	    		$("#greed-line").attr("class", "green-line");
	    		$("#icon-step-one").attr("class", "icon-step1");
	    		$("#icon-step-two").attr("class", "icon-step1");
	    		$("#icon-step-three").attr("class", "icon-step3");
	    		$("#txt-step-one").attr("class", "txt-step1");
	    		$("#txt-step-two").attr("class", "txt-step1");
	    		$("#txt-step-three").attr("class", "txt-step1");
	    		$("#formOne").attr("style", "display: none;");
	    		$("#formTwo").attr("style", "display: none;");
	    		$("#formThree").attr("style", "display: block;");
	    		var reg = /(\d{3})\d{4}(\d{4})/;
	    		var nmobile=newMobileNo.replace(reg,"$1****$2");
	    		$('#new-mobileNo').html(nmobile);
	    	}
	    	if(data=="-1"){
	    		alert("修改绑定手机号码失败！");
	    		window.setTimeout(function(){
					window.location.href=root+"/account/safetySet.action";
				}, 5000);
	    	}
	    	if(data=="-2"){
	    		$('#set-note-text-back').attr('style','color:red;');
				$('#set-note-text-back').html('请正确输入短信验证码！');
	    	}
	    	if(data=="-3"){
	    		$('#set-mobileNo-back').attr('style','color:red;');
				$('#set-mobileNo-back').html('请正确输入手机号码！');
	    	}
	    	if(data=="-7"){
	    		$('#set-img-text-back').attr('style','color:red;');
	    		$('#set-img-text-back').html('请正确输入图形验证码！');
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
//获取短信验证码
function noteProvingAgain(imgText,NoteObject,mobile){
	$.ajax({
	    url:getMobileProvingCodeAgainUrl,
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
						noteProvingclickAgain=false;
						NoteObject.val("获取成功"+data+"s");
					}else{
						noteProvingclickAgain=true;
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
						noteProvingclickAgain=false;
						NoteObject.val("获取失败"+data+"s");
					}else{
						noteProvingclickAgain=true;
						NoteObject.val("获取短信验证码");
						window.clearTimeout(intervaljob);
					}
				}, 1000); 
			}
	    }
	});
}