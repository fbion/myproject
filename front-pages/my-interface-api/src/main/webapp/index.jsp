<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>运营管理平台－支付中心－接口发布系统</title>
    <link />
    <style>
		ul li{height: 30px;}
	</style>
    <script type="text/javascript">
      <!--
      //window.location.href="<%=root%>/login/login.action?systemSgin=com.ctfo.upp.portalApp";
      //-->
    </script>
    
</head>

<body> 
<center><h2>测试接口</h2></center>
<hr />

	<div id="test">
		<ul>
			<li><a href="<%=root%>/pages/rsa.jsp">生成公钥和密钥RSA</a></li>
			
			<li><a href="<%=root%>/pages/account_create.jsp">账户管理---开户</a></li>
			<li><a href="<%=root%>/pages/account_query.jsp">账户管理---查询账户信息</a></li>
			<li><a href="<%=root%>/pages/account_lock.jsp">账户管理---锁定账户</a></li>
			<li><a href="<%=root%>/pages/account_updatePassword.jsp">账户管理---交易密码</a></li>
			
			
			<li><a href="<%=root%>/pages/auth_submit.jsp">实名认证---提交实名认证申请</a></li>
			<li><a href="<%=root%>/pages/auth_modifyAuthenApplyInfo.jsp">实名认证---修改草稿状态下的实名认证申请信息</a></li>
			<li><a href="<%=root%>/pages/auth_queryAuthenState.jsp">实名认证---测试查询已通过实名认证信息接口</a></li>
			
			<li><a href="<%=root%>/pages/fast_payment_pc_test.jsp">一键支付---PC端支付</a></li>
			<li><a href="<%=root%>/pages/fast_payment_mobile_test.jsp">一键支付---MOBILE端支付</a></li>
			<li><a href="<%=root%>/pages/fast_payment_trade_query_test.jsp">一键支付---交易查询</a></li>
			
			<li><a href="<%=root%>/pages/rechargeTest.jsp">充值</a></li>
			<li><a href="<%=root%>/pages/transferAccountsTest.jsp">转账</a></li>
			<li><a href="<%=root%>/pages/withdrawCashTest.jsp">提现</a></li>
			<li><a href="<%=root%>/pages/withdrawCashRapidTest.jsp">快速提现</a></li>
			
			
			<li ><a href="<%=root%>/pages/test.jsp">与第三方对帐</a></li>
			<li ><a href="<%=root%>/pages/clearingAndSettlementTest.jsp">与商户对帐</a></li>
			<li ><a href="<%=root%>/pages/OffLineAndOrderBalance.jsp">线下交易对帐</a></li>
			<li ><a href="<%=root%>/pages/account_current.jsp">账户流水查询</a></li>
			
			<li ><a href="<%=root%>/pages/yw_order_test.jsp">油卡充值测试</a></li>
		</ul>
	</div>

</body>
</html>
