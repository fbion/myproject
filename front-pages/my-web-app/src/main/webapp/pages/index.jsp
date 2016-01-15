<%@page import="com.ctfo.upp.util.Converter"%>
<%@page import="com.ctfo.upp.view.beans.Index"%>
<%@ page import="com.ctfo.csm.uaa.intf.support.Operator" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String root = request.getContextPath();
    Index index = (Index)session.getAttribute(Converter.SESSION_INDEX);
    if(index == null){
        response.sendRedirect(root+"/login/login.action?systemSgin=com.ctfo.upp.webApp");
        return;
    }
%> 
<%
	Operator operator = request.getSession().getAttribute("user")==null?null:(Operator)request.getSession().getAttribute("user");	
	//String op_password_status = operator ==null || operator.getUserPasswd()==null?"":operator.getUserPasswd();
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    
    <title>统一支付结算系统</title>
   
    
    <link href="<%=root%>/css/all.css" rel="stylesheet" type="text/css" />

    <script src="<%=root%>/js/jquery/jquery-1.5.2.min.js" type="text/javascript"></script>      
      
    <script src="<%=root%>/js/ligerUI_1.2.4/js/ligerui.min.js?v=g_version" type="text/javascript"></script>      
    <script src="<%=root%>/js/jquery/jquery.form.js" type="text/javascript"></script>
    <script src="<%=root%>/js/csm/csm-all.js?v=g_version" type="text/javascript"></script>
    <script src="<%=root%>/js/util_ajax.js?v=g_version" type="text/javascript"></script>
    <script src="<%=root%>/pages/index.js?v=g_version" type="text/javascript"></script>  
    
     <!-- 导航菜单的 css js -->
    <link href="<%=root%>/css/wijmoUI/rocket/jquery-wijmo.css" rel="stylesheet" type="text/css" /> 
    <link href="<%=root%>/css/wijmoUI/wijmo/jquery.wijmo.wijmenu.css" rel="stylesheet" type="text/css" />   
    <script src="<%=root%>/js/wijmoUI/jquery-ui-1.8.18.custom.min.js" type="text/javascript"></script>
    <script src="<%=root%>/js/wijmoUI/jquery.wijmo.wijmenu.js" type="text/javascript"></script>
    
    <script src="<%=root%>/js/csm/areaData.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=root%>/js/csm/codeData.js?v=g_version" type="text/javascript" charset="utf-8"></script>
    <script src="<%=application.getContextPath()%>/js/jquery/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
	<script src="<%=application.getContextPath()%>/js/jquery/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
	<script src="<%=application.getContextPath()%>/js/jquery/jquery-validation/messages_cn.js" type="text/javascript"></script>
	<script src="<%=application.getContextPath()%>/pages/password.js" type="text/javascript"></script>
	<script src="<%=application.getContextPath()%>/pages/mask.js" type="text/javascript"></script>
    <script type="text/javascript">
    
         var root = "<%=root%>";
         
         //系统信息
         var INDEX_SYS_ID="<%=index.getSystemId()%>";
         var INDEX_SYS_SIGN="<%=index.getSystemSign()%>";
         var INDEX_SYS_NAME="<%=index.getSystemName()%>";
         var INDEX_SYS_PLATFORM="<%=index.getPlatform()%>";
         var INDEX_SYS_PLATFORMNAME="<%=index.getPlatformName()%>";
         //用户信息
         var INDEX_USER_ID="<%=index.getUserId()%>";
         var INDEX_USER_LOGIN="<%=index.getUserLogin()%>";
         var INDEX_USER_NAME="<%=index.getUserName()%>";
         var INDEX_USER_TYPE="<%=index.getUserType()%>";
         var INDEX_USER_OWNINGORG="<%=index.getUserOwningOrgId()%>";
         var INDEX_USER_ORGID="<%=index.getOrgId()%>";
         var INDEX_USER_ORGNAME="<%=index.getOrgName()%>";
         
         var INDEX_USER_ROLEID="<%=index.getRoleId()%>";
         var INDEX_USER_ROLENAME="<%=index.getRoleName()%>";
         
         var INDEX_USER_PASSWORD="<%=index.getUserPasswd()%>";
    </script>
    <!--  <script type="text/javascript">
     alert(INDEX_USER_PASSWORD);
    	var INDEX_USER_PASSWORD_STATUS=INDEX_USER_PASSWORD;
		if(INDEX_USER_PASSWORD_STATUS != ''){
			top.location="password.jsp";		
		}		
    </script>  -->

</head>

<body style="padding:0px; padbackground:#EAEEF5;"> 
 
<!-- <div id="pageloading" style="border: 1px;color: red;"></div> -->

 
   <!-- 顶部页面 -->
   <jsp:include page="top.jsp" />
 
   <!-- 主页面 -->
   <div id="layout1" style="width:99.2%; margin:0 auto; margin-top:0px; "> 
                
        <div position="center" id="framecenter"> 
            <div tabid="home" title="首页" >
                <iframe frameborder="0" name="home_iframe" id="home_iframe" src="<%=root%>/pages/welcome.jsp"></iframe>
            </div> 
        </div>        
   </div>
    <div id="jmask" style="position:fixed; left:0; top:0; width:100%; height:100%; display: none; z-index: 9999">	
    <div style="position:absolute; width:100%; height:100%; z-index:9999; background-color:#000; opacity:0.5; filter:alpha(opacity=50);"></div>
    <div class="" style="position: absolute; left:50%; top:50%; margin-left:-100px; width:200px; z-index:9999; color:#fff;">
        <img src="<%=request.getContextPath()%>/images/loading.gif"/>
	</div>
	</div>
     
    <!-- 底部页面  -->
   <jsp:include page="bottom.jsp" />
   
</body>
</html>
