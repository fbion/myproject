<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>运营管理平台－支付中心－用户流水查询</title>
    <style>
    	 .l-table-edit {margin-left: 20px;}
	     .l-table-edit-td{ padding:6px;}    
	     .l-button-submit{width:80px; float:left; margin-left:25%; padding-bottom:2px;}
	     .l-button-reset{width:80px; float:left; margin-left:60px; padding-bottom:2px;}
	     .l-verify-tip{ left:230px; top:120px;}
	</style>
	
	<jsp:include page="all_css.jsp" />
	<jsp:include page="all_js.jsp" />
	
    <script type="text/javascript"></script>
    
</head>
<body style="height: 90%; padding: 20px;">

<center><h2>提现</h2></center>
<hr />


	<form name="form1" method="post" action="<%=root%>/upp/accountController/queryCurrentAccount" id="form1" >
		<input type="hidden" id="id" name="id" /> 
		<div id="tableDiv" style="overflow: auto; height: 100%">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td">开始时间:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="startTime" id="startTime"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">结束时间:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="endTime" id="endTime"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">类型:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="bookAccountType" id="bookAccountType"/>
					</td>
				</tr>				
					
				<tr>
					<td colspan="2" align="center" class="l-table-edit-td">					
						       <input type="submit" value="保存" id="Button1" class="l-button l-button-submit" />					
					</td>
				</tr>
			</table>
		</div>
	</form>
	
	
	<!-- <div class="pay_btn"><img class="hand" alt="立即支付" src="http://epay.bj.chinamobile.com/internetFee_new/style/201304/images/btn32-99.png" width="116" height="36"
                          onmouseover="this.src='http://epay.bj.chinamobile.com/internetFee_new/style/201304/images/btn32-1.png'" id="YHKJF_YRCZ_LJZF"
                          onclick="btnSubmit()"
                          onmouseout="this.src='http://epay.bj.chinamobile.com/internetFee_new/style/201304/images/btn32-99.png'"/></div>
	
	 -->
	
	<div style="display: none"></div>
</body>

</html>