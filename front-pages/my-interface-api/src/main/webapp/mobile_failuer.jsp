<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>

<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>易宝支付失败</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" type="text/css" href="<%=root %>/css/bootstrap.min.css">  
	<link rel="stylesheet" type="text/css" href="<%=root %>/css/style.css">
	<script type="text/javascript">
		function _orderPage(){
			window.location="http://zf_failuer.sinoiov.com";
		}
	</script>
</head>
<body>
<div data-role="page">
    <div class="header">
	    <div class="header_top">
		    <img src="<%=root %>/images/icon2.png"><span>支付失败！</span>
		</div>
		<div class="header_bot">
		    <dl>
			    <dt>可能是以下情况导致：</dt>
			    <dd>1、网络连接问题出错。</dd>
			    <dd>2、您的余额不足以完成本次支付。</dd>
			</dl>
			<dl class="mt1">
			    <dt>您还可以：</dt>
			    <dd>1、使用账户余额进行支付。</dd>
			    <dd>2、登录车旺平台www.95155.com使用网银支付。</dd>
			</dl>
		</div>
	</div>
	<div class="button">
	    <div class="button_one"><input type="button" onclick="_orderPage();" value="完成"></div>
		<!-- <div class="button_two"><input type="button" value="再次尝试支付"></div> -->
	</div>
</div>
</body>
</html>