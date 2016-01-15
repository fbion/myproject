<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>运营管理平台－支付中心－支付确认</title>
    	
	<jsp:include page="all_css.jsp" />
	<jsp:include page="all_js.jsp" />
	
	
    <script type="text/javascript">
    (function() {
    	var Skip = function() {
    		this.init();
    	};
    	Skip.prototype = {
    		config : {
    			rechargeUrl : '<%=root%>/upp/paymentTrade/recharge'
    		},
    		cache : {
    			dialog : null
    		},
    		init : function(){
    			this.render();
    			this.loadCss(); 
    			
    		},
    		render : function(){
    			var that = this;
    		   
    	            	
    		},
    	
    		 loadCss : function(){
    			 $('form').ligerForm();
    		 }
    	};
    	
    	$(document).ready(function() {
    		window.skip = new Skip();
    	});
    })();
    
    </script>
    
</head>
<body style="height: 90%; padding: 20px;">

<div>请您在新打开的页面进行支付，支付完成前请不要关闭该窗口</div>

	<div style="display: none"></div>
</body>

</html>