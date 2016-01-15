<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" import="com.sinoiov.upp.portal.util.ProtalConvertUtils"%>
<%
	String cashierUrl = ProtalConvertUtils.getValue("UPP_CASHIER_APP_URL");
	String ownerLoginName = (String)session.getAttribute("ownerLoginName");
	String accountNo = (String)request.getAttribute("accountNo");
	session.setAttribute("accountNo", accountNo);
	response.setHeader( "Pragma", "no-cache"); 
    response.setHeader( "Cache-Control", "no-store"); 
    response.setDateHeader( "Expires",   0); 
%>


<jsp:include page="/pages/all_js.jsp" />
<jsp:include page="/pages/all_css.jsp" />
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/center/center_pages.css"> 
	<title>车旺个人中心-资金账户</title>
	<script type="text/javascript">
		var ownerLoginName = '<%=ownerLoginName%>';
		var cashierUrl = '<%=cashierUrl%>';
		var accountNo = '<%=accountNo%>';
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/buildTable.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/bankrollAccount/accountHome.js" ></script>
	<style type="text/css">
		.buildTable tr{
			height: 30px;
			font-size: 13px;
		}
	</style>
</head>
<body>
		
	<!--right-->
	<div class="w880 fr" style="float: left; margin: 0;padding: 0;">
		<div class="rightsideBar_title bb-n pb8">
			<h3 class="fl color_ff f22 mr20">资金账户</h3>
		</div>
		<div class="mian_rightsideBar mymoney1">
			<ul>
				<li class="fli">账户金额</li>
				<li class="f28 color_ff" id="totalBalance">获取中...</li>
				<li class="pt10"><input id="recharge" class="tjbtn tjbtn1" type="button" value="充 值"></li>
			</ul>
			<ul>
				<li class="fli">可用余额</li>
				<li class="f28 color_ff" id="usableBalance">获取中...</li>
			</ul>
			<ul>
				<li class="fli pb20">安全等级</li>
				<li class="pb17">
					<div class="strength strengthA tl">
						<span>
							<i id="strength_L" style="background-color: #FF4500;">
								弱
							</i>
							<i id="strength_M">
								中
							</i>
							<i id="strength_H">
								强
							</i>
						</span>
						<input type="hidden" id="pwdStrength" name="user.pwdStrength" value="3">
					</div>
				</li>
				<li><a id="safetySet">安全设置</a></li>
			</ul>
			<ul class="border-r-n">
				<li class="fli pb10">绑定手机号</li>
				<li class="f20 pb10" id="mobileNo" style="margin-top: 8px;margin-bottom: 8px;">获取中</li>
				<li><a id="changeMobileNo">修改</a></li>
			</ul>
		</div>
		
		
		<div class="rightsideBar_title bb-n pb8 pt30">
			<h3 class="fl color_ff f22 mr20">最近交易记录</h3>
		</div>
		<div class="menu mian_rightsideBar mymoney2">
			<div class="menu-top">
				<div class="menu-title fl">
					<a id="queryAccountCurrentTypeAll" class="curr">全部</a>
					<a id="queryAccountCurrentRECORDED" >收入</a>
					<a id="queryAccountCurrentDEDUCTION">支出</a>
				</div>
				<div class="fr more">
					<a id="queryAccountCurrentAll">更多</a>
				</div>
			</div>	
			<div class="text cl" style="position: relative;">
				<div id="dataGrid"></div>
				<!-- 备注弹窗DIV开始 -->
				<div id="remarksDiv" style="display:none;">
					<div style="width: 100%;height: 35px; border-bottom: solid 2px #EE4000" >
						<h2 style="display: inline;line-height: 35px;">编辑备注信息：</h2>
						<input type="button" value="关闭" id="closeButton" style="float: right; margin: 5px;"/>
					</div>
					<div style="width: 100%;">
						<table style="margin-left: 30px;margin-top: 30px">
							<tr>
								<td width="50" valign="top">
									备注：
								</td>
								<td>
									<textarea rows="5" cols="60" id="remarksContext" onkeyup="remarksContextNum()" style="border:solid 1px #CCCCCC"></textarea>
									<p id="remarksText">（还可以输入<span id="remarksNum" style="color:#1874CD;">50</span>个字，只能输入汉字、字母、数字。）</p>
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<input type="button" id="remarksButton" value="提交" style="width: 80px;height: 30px;margin-top:15px; margin-left: 260px;background-color: #EE5C42"/>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<!-- 备注弹窗DIV结束 -->
			</div>
		</div>
	</div>
</body>
</html>