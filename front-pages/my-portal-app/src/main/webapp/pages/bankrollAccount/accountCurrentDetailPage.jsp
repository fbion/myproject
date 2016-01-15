<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String id = (String)request.getAttribute("id");
%>
<jsp:include page="/pages/all_js.jsp" />
<html lang="en-US">
<head>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common/base.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common/common.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common/share.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/common/new_common.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/oil/oil_pages.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/center/center_pages.css"> 
	<meta charset="UTF-8">
	<title>车旺-个人中心-账户交易详情</title>
	<script type="text/javascript">
		var id = '<%=id%>';
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/bankrollAccount/accountCurrentDetailPage.js" ></script>
</head>
<body>
	<!--right-->
	<div class="rightsideBar w800 fr" style="float: left; margin: 0;padding: 0;">
		<div class="rightsideBar_title">
			<h3 class="fb f14 inline fl">账户交易详情</h3>
		</div>
		<div class="mian_rightsideBar pt30 pb30 tc">
		    <div class="Barprompt_main tc">
			    <img class="fl" src="<%=request.getContextPath()%>/images/account/suc.png">
				<ul class="fl Barprompt">
				    <li class="lh200"><span id="businessStatus" class="f16 color_ff">交易成功！</span></li>
				    <li class="lh200">订单号：<span id="orderANumber"></span></li>
					<li class="lh200">交易金额：<span id="accountMoney"></span></li>
					<li class="lh200">交易类型：<span id="bookAccountType"></span></li>
					<li class="lh200">交易时间：<span id="accountTime"></span></li>
					<li class="lh200">备注：<span id="commentsOnAForm"></span></li>
				</ul>
				<div class="clear"></div>
			</div>
			<div class="Barprompt_main tc pl200 none">
			    <img class="fl" src="../images/center/suc2.png">
				<ul class="fl Barprompt">
				    <li class="lh200"><span class="f16 color_red">交易失败！</span></li>
				    <li class="lh200 color_red">失败原因：交易失败情况，除过上述几种类型 的明细外，需要体现 失败原因</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
	</div>
</body>
</html>