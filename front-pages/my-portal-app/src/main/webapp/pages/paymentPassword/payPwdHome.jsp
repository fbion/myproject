<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String mobileNo = (String)session.getAttribute("mobileNo");
	String accountNo = (String)session.getAttribute("accountNo");
%>

<html>
<head>
	<title>车旺-个人中心-安全设置-修改交易密码首页</title>
	<jsp:include page="/pages/all_js.jsp" />
	<jsp:include page="/pages/all_css.jsp" />
	<script type="text/javascript">
		//重载验证码  
		function reCode(obj){
			obj.src = "<%=request.getContextPath()%>/imgCheckCode?d=" + new Date();
		}
		$(document).ready(function(){
			/* 滑动/展开 */
			$("div.menu-header").click(function(){									   
				var arrow = $(this).find("span.arrow");
				if(arrow.hasClass("up")){
					arrow.removeClass("up");
					arrow.addClass("down");
					arrow.innerHTML="展开"
				}else if(arrow.hasClass("down")){
					arrow.removeClass("down");
					arrow.addClass("up");
					arrow.innerHTML="收起"
				}
				$(this).parent().find("ul.menu").slideToggle();
			});
		});
		var mobileNo = '<%=mobileNo%>';
		var accountNo = '<%=accountNo%>';
	</script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/account/question.css" type="text/css" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/pwdStringency.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/utils/md5.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/paymentPassword/payPwdHome.js" ></script>
</head>
<body>
	<!--right-->
	<div class="w800 fr" style="float: left; margin: 0;padding: 0;">
		<div class="pub-tit"><span class="t-style">重置支付密码</span></div>
		<div class="ques-cont">
			<ul>
				<li>您正在为资金账户重置支付密码，请选择重置方式</li>
				<li>
					<img src="../images/account/icon2.png" class="pt29">
					<div>
						我忘记支付密码了<span class="span1">忘记支付密码 可选择此方式</span>
					</div>
					<div class="menu-header">
						<span class="arrow up"> </span>
					</div>
					<ul class="menu cl" style="display:none;">
						<li>
							<div class="pt40 pl35">通过“验证手机短信+验证安全问题”重置支付密码</div>
							<input id="resetPayPwd" class="tjbtn tjbtn2 ml90" type="button" value="立即重置">
						</li>
						<li class="cl">
							<div  class="pt40 pl35">未设置安全问题，或者忘记安全问题答案</div>
							<div class="ml160">
							<span class="span1 span2">请拨打客服热线</span><span class="span3">4000966666</span>
							</div>
						</li>
					</ul>
				</li>
				<li class="cl">
					<img src="../images/account/icon4.png">
					<div>
						我记得原支付密码<span class="span1">通过“验证原支付密码”方式修改</span>
					</div>
					<input id="changePayPwd" class="tjbtn tjbtn2 ml195" type="button" value="立即重置">
				</li>
			</ul>
		</div>
	</div>	
</body>
</html>