<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
	<meta name="format-detection" content="telephone=no" />
	<title>中交收银台</title>
	<link rel="stylesheet" href="<%=root%>/css/mobilecallback.css">
	
	<script src="<%=root%>/js/jquery/jquery-1.5.2.js" type="text/javascript"></script>
    <script type="text/javascript" src="/paymobile/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/paymobile/js/yeepay-1.0.js"></script>
</head>
<body>
	<style type="text/css">
		*{
			-webkit-tap-highlight-color: rgba(255,0,0,0);
		}
	</style>
	
	<div class="title_logo"><img src="<%=root%>/images/title_logo.png" width="84"></div>
	
	<!--
	<div class="msg-bar2">检测到您的IP地址发生变化，请注意支付安全</div>
	-->
	<div class="shop_info">
    	<span class="icon_down">&nbsp;</span>
		<table class="list_more" style="display:none;">
        	<tr>
	        	<th class="font-red tl font16">￥&nbsp;0.01</th>
	        	<td>&nbsp;</td>
        	</tr>
			<tr>
				<th>
					<label>商户名称：</label>
				</th>
				<td>
					一键支付测试环境示例商户
				</td>
			</tr>
			<tr>
				<th>
					<label>
						商品名称：
					</label>
				</th>
				<td>
					油卡充值
				</td>
			</tr>
			<tr>
				<th>
					<label>订单编号：</label>
				</th>
				<td>
					70020150203162441714
				</td>
			</tr>
		</table>
        <table class="list">
        	<tr>
        		<th class="font-red tl font16">￥&nbsp;0.01</th>
        	</tr>
			<tr>
				<td>油卡充值</td>
			</tr>
        </table>	
	</div>
	<div class="main-wrap">
		<form id="form" action="/paymobile/wap/first/cardinfo" method="post">
	        <div class="bank_list_div">
	            <input type="submit" id="submit" style="display:none">
	            <input type="button" class="input-button-orange" onclick="check(document.getElementById('form'))" value="返回商户">
	        </div>
        </form>
	</div>
	
	
    <div id="waiting_form" class="win_form_div">
        <div class="win_form">
        	<div class="loading">
             	正在处理，请耐心等待...
            </div>
         </div>
    </div>
    <div id="loading_form" class="win_form_div">
        <div class="win_form">
        	<div class="loading">
             	正在支付，请耐心等待...
            </div>
         </div>
    </div>
    
    <!--弹出窗口-->
    <script src="/paymobile/js/main.js"></script>
    
    <script type="text/javascript">
    	
    	var needSmsCheck = true;
    	
    	function showError(msg,item){
    		for(var i=1; i!=3; i++){
    			$(".item"+i).removeClass("error");
    		}
    		if(msg){
    			$("#error_form .err_div").html(msg);
    		}
    		if(item){
    			$(".item"+item).addClass("error");
    		}
    		openWin('error_form');
    	}
    	function changeSmsCode(data){
    		needSmsCheck = data;
    		if(needSmsCheck){
    			$('#smscode').show();
    		}else{
    			$('#smscode').hide();
    		}
    	}
    	
    	window.onload=function(){
    		
    	};
    	
    	function check(form){
    		var data = form.id_phone.value;
    		if(data=="-1"){
    			var no = form.cardNo.value;
	    		if(!no){
	    			showError('请输入信用卡或储蓄卡卡号',2);
	    			return false;
	    		}
	    		openWin('waiting_form',true);
	    		$("#submit").click();
	    		return true;
    		}else{
   				if(needSmsCheck && !form.verifycode.value){
	    			showError("请输入短信验证码",1);
	    			return false;
	    		}
	    		if(form.verifycode.value.length>6){
	    			showError("短信验证码为6位数字",1);
	    			return false;
	    		}
	    		form.bindid.value = data.split("-")[0];
	    		form.action="/paymobile/pay/bind/pay";
	    		openWin('loading_form',true);
	    		submit("submit");
				return true;
    		}
		}
		
    	function sendSmsCode(){
			if(!needSmsCheck){
				showError("无需进行短信验证",1);
	    		return false;
			}
			var data = $("#form input[name='id_phone']").val();
			data = data.split("-");
			var phone = data[2];
			if(!phone){
    			showError("请输入银行预留手机号",4);
    			return false;
    		}
			var cardspeciesid = data[1];
			var bindid = data[0];
			openWin('waiting_form',true);
			$.ajax({
				type:"post",
				url:"/paymobile/wap/ajax/smscode/send",
				data:{phone:phone,cardspeciesid:cardspeciesid,bindid:bindid,token:"7706f463-6bd0-4929-a9f4-5e9542c793b9"},
				success:function(result){
					closeWin('waiting_form');
					if(result.status==5){
						var f3 = phone.substring(0,3);
						var l4 = phone.substring(phone.length-4);
						$("#phone-f3").html(f3);
						$("#phone-l4").html(l4);
						openWin('sms_form');
						smsCountDown("sendSmsCode();");
						return;
					}
					if(result.msg){
						showError(result.msg+"，异常码"+result.error,4);
					}
				},
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					closeWin('waiting_form');
					showError("系统异常，请重试",4);
				},
				dataType:"json"});
		}
    </script>
</body>
</html>