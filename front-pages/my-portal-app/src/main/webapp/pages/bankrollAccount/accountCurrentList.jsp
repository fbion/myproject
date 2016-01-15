<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String accountNo = (String)session.getAttribute("accountNo");
	response.setHeader( "Pragma ", "No-Cache "); 
	response.setHeader( "Cache-Control ", "No-Cache "); 
	response.setDateHeader( "Expires ",   0);
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
	<title>车旺-个人中心-资金账户-账户交易记录</title>
	<script type="text/javascript">
		var accountNo = '<%=accountNo%>';
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/buildTable.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/bankrollAccount/accountCurrentList.js" ></script>
	<style type="text/css">
		.buildTable tr{
			height: 30px;
			font-size: 13px;
		}
	</style>
</head>
<body>
	<!--right-->
	<div class="rightsideBar w800 fr" style="float: left; margin: 0;padding: 0;">
		<!--交易明细-->
		<div class="">
			<div class="rightsideBar_title">
				<h3 class="fb f14 inline fl">账户交易记录</h3>
			</div>
			<div class="h80 sideBar_deta sideBar_bg border_rightBar p20 ">
			    <ul>
				    <li class="li li_tow">
					    <div class="lab color_sizeB">起止日期</div>
						<div class="datetime datetimelang input_bar">
						    <input id="select_start_Time" name="select_start_Time" class="mr5" type="text" value="" onfocus="dateFocus()">至
						    <input id="stlect_end_Time" name="stlect_end_Time" class="ml5" type="text" value="" onfocus="dateFocus()">
						</div>
					</li>
					<li class="li litab">
						<a id="dateAll" class="litab_a litab_curr">全部</a> 
					    <a id="today" class="litab_b">今天</a> 
						<a id="oneMonth" class="litab_b">最近1个月</a> 
						<a id="quarterOfYear" class="litab_b">3个月</a> 
						<a id="oneYear" class="litab_c">1年</a>
					</li>
				</ul>
			</div>
			<div class="put_away h80" id="panel">
			    <ul>
				    <li class="li litab">
					    <div class="lab color_sizeB">交易类型</div>
						<div class="datetime datetimelang input_bar">
						    <a id="recorded" class="litab_a">收入</a>
						    <a id="typeAll" class="litab_b litab_curr">全部</a> 
						    <a id="deduction" class="litab_c">支出</a>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<!---->
		<div class="pt20" style="position: relative;">
			<div class="rightsideBar_title2 "></div>
			<div class="border_rightBar table_border" id="dataGrid" style="width: 800px;height: 500px;"></div>
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
								<input type="button" id="remarksButton" value="提交" style="width: 80px;height: 30px;margin-top:15px;margin-left: 260px;background-color: #EE5C42"/>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>