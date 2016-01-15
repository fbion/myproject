var noteProvingclick="1";
var intervaljob;
var getMobileProvingCodeUrl = root+'/modileTelephoneNoteProving/getMobileNoProving.action';
var formSubmitUrl=root+'/account/createAccount.action';
var createAccountSuccessUrl=root+'/account/provingAccount.action';

$(function() {
	
	$("#mobileNo").val(mobileNo);
	
	$("#noteProving").click(function(){
		if(noteProvingclick=="1"){
			if(provingImgCode()){
				noteProving();
			}
		}
	});
	$("#submitButton").click(function(){
		showMask();
		formSubmit();
	});
	//验证密码强度
	$("#payPassword").keyup(function(){
		provingPWDStringency($('#payPassword').val());
	});
	//验证密码
	$("#payPassword").blur(function(){
		newPassword();
	});
	//验证重复密码
	$("#payPasswordComfirm").blur(function(){
		newPasswordRepeat();
	});
	
});


function newPassword(){
	
	Pwd = $('#payPassword').val();
    if(Pwd&&provingPwd(Pwd)){
		$('#newPasswordSpan').attr("style","color:red");
    	$('#newPasswordSpan').html('密码安全等级过低，请重新设置！');
	}else if(Pwd&&!pwdReg.test(Pwd)){
		$('#newPasswordSpan').attr("style","color:red");
    	$('#newPasswordSpan').html('请正确输入密码！');
	}else{
		$('#newPasswordSpan').html('');
	}
}

function newPasswordRepeat(){
	pwdr = $('#payPasswordComfirm').val();
	pwd = $('#payPassword').val();
	if(pwdr&&!pwd==pwdr){
		$('#newPasswordRepeatSpan').attr("style","color:red");
    	$('#newPasswordRepeatSpan').html('两次输入不一致！');
	}
}

//测试图片验证码是否为空
function provingImgCode(){
	if($("#imgCheckCode").val() == null || $("#imgCheckCode").val() == ""){
		alert('请先输入图片验证码！');
		return false;
	}else{
		return true;
	}
}
//获取短信验证码
function noteProving(){
	var mobileNo = $("#mobileNo").val();
	$.ajax({
	    url:getMobileProvingCodeUrl,
	    dataType:"json",
	    data:{'mobileNo':mobileNo,'accountNo':'openAccount','imgProvingText':$("#imgCheckCode").val()},
	    type:"POST",
	    success:function(data){
	    	if(data&&data.result=="-2"){
				alert("图形验证码错误！"); 
				return;
			}
			if(data&&data.result=="1"){
				var dateTime = 60;
				intervaljob=window.setInterval(function(){showalertSuccess(dateTime--);}, 1000); 
			}
			if(data&&data.result=="-1"){
				var dateTime = 5;
				intervaljob=window.setInterval(function(){showalertError(dateTime--);}, 1000); 
			}
	    },
	    error:function(data){
			var dateTime = 5;
			intervaljob=window.setInterval(function(){showalertSystemError(dateTime--);}, 1000);
	    }
	});
}

function showalertSuccess(data){
	if(data>0){
		noteProvingclick="2";
		$("#noteProving").html("获取成功"+data+"s");
	}else{
		noteProvingclick="1";
		$("#noteProving").html("获取验证码");
		window.clearTimeout(intervaljob);
	}
}

function showalertError(data){
	if(data>0){
		noteProvingclick="2";
		$("#noteProving").html("获取失败"+data+"s");
	}else{
		noteProvingclick="1";
		$("#noteProving").html("获取验证码");
		window.clearTimeout(intervaljob);
	}
}

function showalertSystemError(data){
	if(data>0){
		noteProvingclick="2";
		$("#noteProving").html("系统错误"+data+"s");
	}else{
		noteProvingclick="1";
		$("#noteProving").html("获取验证码");
		window.clearTimeout(intervaljob);
	}
}

function formSubmit(){
	
	var params = {};
	var payPassword=$('#payPassword').val();
	var payPasswordComfirm=$('#payPasswordComfirm').val();
	var mobileNo=$('#mobileNo').val();
	var smsCheckCode=$('#smsCheckCode').val();
	var imgCheckCode=$('#imgCheckCode').val();
	if(payPassword){
		params['payPassword']=hex_md5(payPassword);
	}else{
		alert('支付密码不能为空！');
		hideMask();
		return false;
	}
	if(payPasswordComfirm){
		params['payPasswordComfirm']=hex_md5(payPasswordComfirm);
	}else{
		alert('确认支付密码不能为空！');
		hideMask();
		return false;
	}
	
	if(payPassword!=payPasswordComfirm){
		alert('两次密码不一致，请您重新输入！');
		hideMask();
		return false;
	}
	
	if(mobileNo){
		params['mobileNo']=mobileNo;
	}else{
		alert('手机号不能为空！');
		hideMask();
		return false;
	}
	if(imgCheckCode){
		params['imgCheckCode']=imgCheckCode;
	}else{
		alert('图片验证码不能为空！');
		hideMask();
		return false;
	}
	if(smsCheckCode){
		params['smsCheckCode']=smsCheckCode;
	}else{
		alert('短信验证码不能为空！');
		hideMask();
		return false;
	}
	$.ajax({
	    url:formSubmitUrl,
	    dataType:"json",
	    data:params,
	    type:"POST",
	    success:function(data){
	    	if(data=="-2"){
				alert('图形验证码错误！');
				hideMask();
			}else if(data=="1"){
				hideMask();
				alert('开通支付账户成功！');
				window.setTimeout(function(){
					window.location.href=createAccountSuccessUrl;
				}, 2000); 
			}else if(data=="-3"){
				alert('短信验证码错误！');
				hideMask();
			}else if(data=="-4"){
				alert('用户的资金账户已经存在！');
				hideMask();
			}else if(data=="-1"){
				alert('系统错误开户失败！');
				hideMask();
			}else if(data=="-5"){
				alert('该手机号码已被其它账号绑定！');
				hideMask();
			}
	    }
	});
//	$.post(formSubmitUrl, params, function(data){
//		if(data=="-2"){
//			alert('图形验证码错误！');
//			hideMask();
//		}else if(data=="1"){
//			hideMask();
//			alert('开通支付账户成功！');
//			window.setTimeout(function(){
//				window.location.href=createAccountSuccessUrl;
//			}, 2000); 
//		}else if(data=="-3"){
//			alert('短信验证码错误！');
//			hideMask();
//		}else if(data=="-4"){
//			alert('用户的资金账户已经存在！');
//			hideMask();
//		}else if(data=="-1"){
//			alert('系统错误开户失败！');
//			hideMask();
//		}
//	});	
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