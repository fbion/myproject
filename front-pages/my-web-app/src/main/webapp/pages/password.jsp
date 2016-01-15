<%@page import="com.ctfo.upp.view.beans.Index"%>
<%@page import="com.ctfo.upp.util.Converter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<% String root = request.getContextPath();
String userId = (String)request.getAttribute("userId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	
    <title>修改密码</title>

    <jsp:include page="all_css.jsp" />
   
    <script src="<%=root%>/js/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>  
     <script src="<%=root%>/js/ligerUI_1.2.4/js/ligerui.all.js" type="text/javascript"></script>   
    
    <script src="<%=root%>/js/jquery/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=root%>/js/jquery/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=root%>/js/jquery/jquery-validation/messages_cn.js" type="text/javascript"></script>
	 
	<script src="<%=root%>/js/util_ajax.js?v=g_version" type="text/javascript"></script> 
	 
	<script src="<%=root%>/js/csm/md5.js" type="text/javascript"></script>  
	
   
    
    <script src="<%=root%>/js/csm/alert.js?v=g_version" type="text/javascript"></script>
     
   
    <script type="text/javascript">
      
   		var root = "<%=root%>"; 
   		var userId = "<%=userId%>";
   		var isModifyPasswd = false;
   		if(!userId || userId=='null'){
   			isModifyPasswd = true;
   			$("#isModifyPasswd").val("true");
   			userId = parent.parent.INDEX_USER_ID;
   			$("#userId").val(userId);
   		}
    </script>
     <script src="<%=root%>/pages/password.js" type="text/javascript"></script> 
    <style type="text/css">
		.b1,.b2,.b3,.b4,.b1b,.b2b,.b3b,.b4b,.b{display:block;overflow:hidden;}
		.b1,.b2,.b3,.b1b,.b2b,.b3b{height:1px;}
		.b2,.b3,.b4,.b2b,.b3b,.b4b,.b{border-left:1px solid #999;border-right:1px solid #999;}
		.b1,.b1b{margin:0 5px;background:#999;}
		.b2,.b2b{margin:0 3px;border-width:2px;}
		.b3,.b3b{margin:0 2px;}
		.b4,.b4b{height:2px;margin:0 1px;}
		.d1{background:#F7F8F9;}
		.k {height:300px;}
	</style>
</head>

<body style="padding:0px;background:#EAEEF5;overflow-x:auto;"> 

<div align="center" style="margin:auto;">
 <input type="hidden" id="isModifyPasswd" name="isModifyPasswd" value="false"/>
 <input type="hidden" id="userId" name="userId">
 <div id="messageDiv" style="height: 40px;"><h2>您的密码过于简单，为保障您的安全，请修改您的密码！</h2></div>
<div id="mydiv">
	<b class="b1"></b><b class="b2 d1"></b><b class="b3 d1"></b><b class="b4 d1"></b>
		<div class="b d1 k">
			<div id="updatePassForIndex" align="left" style="padding-left: 5%;padding-top: 10px;">
			     <form id="upDatePass" method="post" >
			       <table align="center" style="line-height: 45px; margin-left: 10px;">
			          <tr>
			             <td width="60" align="left">原始密码：</td>
			             <td width="410" align="left">
			                <div id="oldPassDiv">
				                <input id="oldPass" name="oldPass" type="password" />
				                <span style="color: red;">*</span>
				                <span id="login_form_ok" style="display: none;"></span>
								<span id="login_form_hint"  style="display: none;">请输入原始密码，字母加数字或符号的组合</span>		
							</div>
			            </td>
			          </tr>
			          
			          <tr>
			             <td>新的密码：</td>
			             <td>
			                <div id="newPassDiv">
				                <input id="newPass" name="newPass" type="password" onKeyUp="password.validate(this);" onBlur="password.validate(this);"/>
								<span style="color: red;">*</span>
								<span id="login_form_ok" style="display: none;"></span>
								<span id="login_form_hint"  style="display: none;">请输入新的密码，字母加数字或符号的组合</span>
						    </div>
			             </td>
			          </tr>
			          <tr>
			             <td>密码强度：</td>
			             <td>
			             	<table style="line-height: 22px;display:inline;" width="180" border="0" cellspacing="0" cellpadding="1" bordercolor="#cccccc">
								<tr height="20" align="center" bgcolor="#eeeeee">
									<td width="60" id="strength_L">弱</td>
									<td width="60" id="strength_M">中</td>
									<td width="60" id="strength_H">强</td>
								</tr>
							</table>              
			             </td>
			          </tr>
			          <tr>
			             <td>确认密码：</td>
			             <td>
			                <div id="confimPassDiv">
				                <input id="confimPass" name="confimPass" type="password"/>
				                <span style="color: red;">*</span>
				                <span id="login_form_ok" style="display: none;"></span>
								<span id="login_form_hint"  style="display: none;">请输入确认密码，字母加数字或符号的组合</span>
							</div>
			             </td>
			          </tr>
			          
			          <tr style="height: 20px;"><td></td></tr>
			          
			          <tr style="margin-top: 20px;">
			             <td colspan="2" align="center">
		             	      <div >
			             	      <div id="commitNewPassDiv" style="float:left;padding-left: 80px;">
			             	        <input id="commitNewPass" value="提交" type="button" onclick="password.commitUpdatePass();" class="l-button"/>
			             	      </div>
			             	      <div id="logoutDiv"  style="float:left;padding-left: 30px;"  >
			             	          <input id="logout" value="退出" type="button" onclick="password.logoutSystem();" class="l-button"/>
			             	      </div>
		             	      </div>
			             </td>
			          </tr>
			         
			       </table>
			     
			     </form>
			  </div>
		</div>
	<b class="b4b d1"></b><b class="b3b d1"></b><b class="b2b d1"></b><b class="b1b"></b>
</div>


</div>




</body>
</html>
