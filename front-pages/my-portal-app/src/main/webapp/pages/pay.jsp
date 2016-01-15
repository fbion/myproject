<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fragment" uri="http://www.95155.com/fragment"%>
<%@page language="java" import="com.ctfo.upp.root.utils.EnvironmentUtil"%>
<html lang="en-US">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>车旺个人中心-资金账户</title>
	<fragment:include url="/inc/h.html"/>
	<%
		String cheWangHomePage=EnvironmentUtil.getInstance().getPropertyValue("CHE_WANG_HOME_PAGE");
		String cheWangAccountCenter=EnvironmentUtil.getInstance().getPropertyValue("CHE_WANG_ACCOUNT_CENTER");
		String root = request.getContextPath();
	%>
	<script type="text/javascript">
		var root="<%=root%>";
		function goAccountCurrentList(){
			document.getElementById("iframeOne").src=root+"/account/AccountCurrentList.action";
		}
		function cheWangHomePage(){
			window.location.href="<%=cheWangHomePage%>";
		}
		function cheWangAccountCenter(){
			window.location.href="<%=cheWangAccountCenter%>";
		}
		function goAccountHome(){
			document.getElementById("iframeOne").src=root+"/account/provingAccount.action";
		}
		function goSafetySet(){
			document.getElementById("iframeOne").src=root+"/account/safetySet.action";
		}
	</script>
</head>
<body>
	<!-- 窄版页头 -->
	<fragment:include url="/page-head.html"/>
	<!-- 首页宽版页头 -->
 	<%-- <fragment:include url="/page-head-h.html"/> --%>
 	<!-- 频道内页导航栏 -->
	<fragment:include url="/navigator-orange.html"/>
	<div class="main">
    	<!-- 导航菜单 -->
		<div class="authen_con_chip">
			<ul class="pa" id="naveBar">
			</ul>
		</div>
		<!-- 导航菜单结束-->
		<!--content-->
		<div id="main_content" class="main w960 mauto cf help_pages_main">
		    <!--left-->
		    <!-- 左侧主菜单 -->
		    <fragment:include url="/main-menu.html"/>
			<iframe id="iframeOne" marginwidth=0 marginheight=0  frameborder=0 width=890 scrolling="yes" height=880 src="<%=request.getContextPath()%>/account/provingAccount.action"></iframe>
		</div>
	</div>
	<fragment:include url="/page-foot.html"/>
</body>
</html>