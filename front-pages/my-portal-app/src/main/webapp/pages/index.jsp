<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付中心-前台开户</title>
<jsp:include page="/pages/account/inc/resource.jsp" />
</head>
<body>
<!--top-->
<div class="header_nav"><ul class="top-main"><li class="info"></li><li><a href="http://www.95155.com/pages/member/manager/cargo-usercenter.html" style="margin-right:0;">13121613332</a>，欢迎来到车旺！<a href="javascript:;" onclick="logout()">[退出]</a><font>|</font><a href="./车辆管理_files/车辆管理.htm" class="color_gray home">我的车辆</a><font>|</font><a href="http://www.95155.com/pages/dispatch/cargo/order-list.html">我的运单</a><font>|</font><a href="http://www.95155.com/pages/member/manager/member_message_list.html">消息(<b id="navMsgNum">0</b>)</a><font>|</font><a href="http://www.95155.com/pages/common/appdown.html"> 客户端下载</a></li></ul></div><div class="pages_bg"><div class="pages_nav"><div class="pages_logo fl"><a href="http://www.95155.com/home.html" id="nav-logo"></a></div><ul class="topNav fl pl50"><li class="fl pr25"><a href="http://www.95155.com/home.html">首页</a></li><li class="fl pr25"><a href="http://www.95155.com/pages/dispatch/cargo/find-car.html">找车</a></li><li class="fl pr25"><a href="./车辆管理_files/车辆管理.htm" class="crt">管车</a></li><li class="fl pr25"><a href="http://www.95155.com/pages/common/present.html">帮助</a></li></ul></div></div>
<div class="main">
    <!-- 导航菜单 -->
		<div class="authen_con_chip">
			<ul class="pa">
			<li class="fl pr2 color_chip">车旺首页</li>
			<li class="fl pl2 pr2 color_chip">&gt;</li>
			<li class="fl pl2 pr2"><a href="javascript:void(0)">个人中心</a></li>
			<li class="fl pl2 pr2 color_chip">&gt;</li>
			<li class="fl pl2 pr2"><a href="javascript:void(0)">我的支付信息</a></li>
			</ul>
		</div>
	<!-- 导航菜单结束-->
	<!--content-->
	<div id="main_content" class="main w960 mauto cf help_pages_main">
	    <!--left-->
		<jsp:include page="/pages/account/inc/left.jsp" />
		
		<!--right-->
		<div class="w800 fr">
			<div class="rightsideBar_title">
				<h3 class="fb f14 inline fl">开户</h3>
			</div>
			<div class="mian_rightsideBar pt30 pb30">
			    <div class=" tc">
					<ul class="Barprompt">
					    <li class="Barbankbtnli"><span class="f16 color_ff">亲爱的用户，为了更好的为您提供支付支付服务，请先开通支付账户！</span></li>
					    <li class="Barbankbtn Barbankbtnli">
					    	<input class="Barbankbtn1" type="button" value="确认开通" onclick="javascript:location.href='open.action'">
    						<input class="Barbankbtn2" type="button" value="稍后开通" onclick="javascript:history.go(-1);">
						</li>
					</ul>
					<div class="clear"></div>
				</div>
			</div>
		</div>
	</div>
</div>

</body>
</html>
