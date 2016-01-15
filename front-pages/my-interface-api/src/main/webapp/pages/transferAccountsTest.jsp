<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>运营管理平台－支付中心－转账</title>
    <style>
    	 .l-table-edit {margin-left: 20px;}
	     .l-table-edit-td{ padding:6px;}    
	     .l-button-submit{width:80px; float:left; margin-left:25%; padding-bottom:2px;}
	     .l-button-reset{width:80px; float:left; margin-left:60px; padding-bottom:2px;}
	     .l-verify-tip{ left:230px; top:120px;}
	</style>
	
	<jsp:include page="all_css.jsp" />
	<jsp:include page="all_js.jsp" />
	
	<script type="text/javascript">
    (function() {
    	var Recharge = function() {
    		this.init();
    	};
    	Recharge.prototype = {
    		config : {
    			rechargeUrl : '<%=root%>/upp/paymentTradeController/transferAccounts'
    		},
    		cache : {
    			dialog : null
    		},
    		init : function(){
    			this.render();
    			this.loadCss();
    		},
    		
    		//添加用户信息
			suerDialog:function (){	
				var that = this;
				that.cache.dialog = MALQ_UI.open_button_03('suerPage.jsp', 
						200, 300, 
						function(item, dialog){alert("跳转到完成订单页");}, 
						function(item, dialog){alert("跳转到未完成订单页面");}, 
						'已完成支付','支付遇到问题','支付确认' );
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
    	            	//alert(JSON.stringify(form));
    	                JAjax(that.config.rechargeUrl, form, 'json', function(data){ 
    	                	if(data){           
    	                		  alert(data.data.url);
    	                		  if(data.data.url){
    	                			  document.forms[0].target = "_blank";
        	      	            	  document.forms[0].action = data.data.url;
        	  					      //document.forms[0].nextMethod.value = 'btnGoPay';
        	  					      document.forms[0].submit();
        	  					    that.suerDialog(); 
    	                		  }
    	                		  
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
    		window.recharge = new Recharge();
    	});
    })();
    
    </script>
</head>
<body style="height: 90%; padding: 20px;">
<center><h2>转账</h2></center>
<hr />


	<form name="form1" method="post" action="" id="form1" >
		<input type="hidden" id="id" name="id" /> 
		<div id="tableDiv" style="overflow: auto; height: 100%">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>用户ID:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="userId" id="userId" ltype="text" validate="{required:true,minlength:1,maxlength:60}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>付款账号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="accountNo" id="accountNo" ltype="text" validate="{required:true,minlength:1,maxlength:60}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>收款账号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="collectMoneyAccountNo" id="collectMoneyAccountNo" ltype="text" validate="{required:true,minlength:1,maxlength:60}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>业务订单号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="workOrderNo" id="workOrderNo" ltype="text" validate="{required:true,minlength:1,maxlength:60}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>转账金额:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="orderAmount" id="orderAmount" ltype="text" validate="{required:true,minlength:1,maxlength:60}" />
					</td>
				</tr>				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商户编号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="storeCode" id="storeCode" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>支付平台编号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="ServiceProviderCode" id="ServiceProviderCode" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				
				<tr>
					<td colspan="2" class="l-table-edit-td">
						
					</td>
				</tr>	
				
				<tr>
					<td colspan="2" align="center" class="l-table-edit-td">					
						       <input type="submit" value="保存" id="Button1" class="l-button l-button-submit" />					
					</td>
				</tr>
			</table>	
		</div>
		<input type="hidden" id="orderType" name="orderType" value="2"/>
	</form>
	<div style="display: none"></div>
</body>
</html>