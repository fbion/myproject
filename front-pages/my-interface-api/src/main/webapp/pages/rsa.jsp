<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>生成密钥对</title>

<jsp:include page="all_css.jsp" />
<jsp:include page="all_js.jsp" />
	
 <script type="text/javascript">
    (function() {
    	var Caculate = function() {
    		this.init();
    	};
    	Caculate.prototype = {
    		config : {
    			caculateUrl : '<%=root%>/upp/caculate/caculateRSA'
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
    		    //初始化页面检验规则
    		    $.metadata.setType('attr', 'validate');
    	        $('#form1').validate({
    	        	
    	        	errorPlacement: function (lable, element){
    	        		CSM_VALIDATE.errorPlacement(lable, element);
    		        },
    	            success: function (lable){
    	            	CSM_VALIDATE.success(lable);
    	            },
    	            
    	             submitHandler: function(){ 
    	            	
    	            	var form = $("#form1").serializeArray();
    	                JAjax(that.config.caculateUrl, form, 'json', function(data){ 
    	                	if(data){
    	                		  $('#publickey').val(data.publicKey);
    	                		  $('#privatekey').val(data.privateKey);
    	                	}else{
    	                		 JSWK.clewBox(data,'clew_ico_fail',failShowTime);
    	                	}
    	                	
    					}, function() {JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
    	            } 
    	        });
    	       
    		},
    	
    		 loadCss : function(){
    			 $('form').ligerForm();
    		 }
    	};
    	
    	$(document).ready(function() {
    		window.caculate = new Caculate();
    	});
    })();
    
    </script>	
</head>
<body style="height: 90%; padding: 20px;">

<center><h2>生成密钥对</h2></center>
<hr />
	
	<form id="form1" action="" method="post">
		<input type="hidden" id="code" name="code"/>
		<ul>
			<li><span><input type="submit"  value="生成RSA密钥对" title="生成RSA密钥对"></span></li>
		</ul>
	</form>
	<ul>
		<li class="l-table-edit-td"><span>公钥：</span></li>
		<li class="l-table-edit-td"><textarea id="publickey" rows="5" cols="120"></textarea></li>
		<li class="l-table-edit-td"><span>私钥：</span></li>
		<li class="l-table-edit-td"><textarea id="privatekey" rows="10" cols="120"></textarea></li>
	</ul>
</body>
</html>