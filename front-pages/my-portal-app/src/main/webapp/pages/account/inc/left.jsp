<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="main_leftsideBar fl w150">
    <div class="leftsideBar">
	    <span><img src="<%=request.getContextPath()%>/pages/account/inc/images/pc/sideBar_icon10.png"></span>
		<span class="f16 vm pl5">个人中心</span>
	</div>
    <ul class="leftsideBar_border">
		<li class="leftsideBar_title fb f14">帐户信息</li>
		<li><a href="<%=request.getContextPath()%>}/accountController/open.action" class="leftsideBar_curr">开户</a></li>
		<li><a href="<%=request.getContextPath()%>/accountController/auth.action" >实名认证</a></li>
		<li><a href="<%=request.getContextPath()%>/accountController/accountInfo.action">帐户查询</a></li>
		<li><a href="<%=request.getContextPath()%>/accountController/passwordUpdate.action">修改交易密码</a></li>
		<!-- <li><a href="#">我的银行卡</a></li> -->
		<ul><li class="leftsideBar_title fb f14">交易信息</li>
		    <li><a href="<%=request.getContextPath()%>/accountController/tradeQuery/1.action">交易查询</a></li>
		    <li><a href="<%=request.getContextPath()%>/accountController/recharge.action">充值</a></li>
		    <li><a href="<%=request.getContextPath()%>/accountController/cashout.action">提现</a></li>
		</ul>
		<ul><li class="leftsideBar_title fb f14">收银台</li>
		    <li><a href="<%=request.getContextPath()%>/accountController/cashier.action">收银台</a></li>
		</ul>
	</ul>
</div>