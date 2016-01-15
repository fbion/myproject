<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>易宝支付完成</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="<%=root %>/css/bootstrap.min.css">  
	<link rel="stylesheet" type="text/css" href="<%=root %>/css/style.css">
	
	<script type="text/javascript">
		function _orderPage(){
			window.location="http://zf_success.sinoiov.com";
		}
	</script>
	
</head>
<body>
<div data-role="page">
    <div class="header">
	    <div class="header_top">
		    <img src="<%=root %>/images/icon.png"><span>支付完成</span>
		</div>
		<div class="header_mid">我们会尽快处理您的充值请求，如有问题请联系服务热线400-096-6666。</div>
		<div class="header_bot">
		    <ul>
			    <li>支付金额：<span class="header_corlor">${amount}</span></li>
			    <li>支付状态：<span>支付完成<span></li>
			    <li>支付时间：<span>${payTime}<span></li>
			</ul>
		</div>
	</div>
	<div class="button">
	    <div class="button_one"><input type="button" onclick="_orderPage();" value="完成"></div>
		<!-- <div class="button_two"><input type="button" value="继续充值"></div> -->
	</div>
</div>
</body>
</html>