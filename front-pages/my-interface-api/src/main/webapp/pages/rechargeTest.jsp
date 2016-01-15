<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>运营管理平台－支付中心－充值</title>
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
    			rechargeUrl : '<%=root%>/upp/paymentTradeController/recharge.action'
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
    	            	 //alert(that.config.rechargeUrl+"==="+JSON.stringify(form));
    	                JAjax(that.config.rechargeUrl, form, 'json', function(data){ 
    	                	if(data && data.data=="1" && data.url){
    	                			  alert(data.url);
    	                			  document.forms[0].target = "_blank";
        	      	            	  document.forms[0].action = data.url;
        	  					      //document.forms[0].nextMethod.value = 'btnGoPay';
        	  					      document.forms[0].submit();
        	  					    that.suerDialog();     	                		  
    	                	}else{
    	                		 JSWK.clewBox(data.error,'clew_ico_fail',failShowTime);
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

<center><h2>充值</h2></center>
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
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>accountNo:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="accountNo" id="accountNo" ltype="text" validate="{required:true,minlength:1,maxlength:60}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>充值金额:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="amount" id="amount" ltype="text" validate="{required:true,minlength:1,maxlength:60}" />
					</td>
				</tr>				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商户编号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="storeCode" id="storeCode" ltype="text" validate="{required:true,minlength:1,maxlength:60}" />
					</td>
				</tr>				
							
				<tr>
					<td align="right" class="l-table-edit-td">终端类型:</td>
					<td align="left" class="l-table-edit-td">
						<input name="clentType" type="radio" id="PC"  value="PC" checked="checked" /> PC
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="clentType" type="radio" id="MOBILE" value="MOBILE"/> MOBILE
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">支付渠道:</td>
					<td align="left" class="l-table-edit-td">
						<input name="payChannel" type="radio" id="NET" value="NET" checked="checked" /> 网银
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="payChannel" type="radio" id="FASTPAY" value="FASTPAY"/> 快捷
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input name="payChannel" type="radio" id="WAP" value="WAP"/> WAP
					</td>
				</tr>
				
				<tr>
					<td align="right" class="l-table-edit-td">银行信息:</td>
					<td align="left" class="l-table-edit-td">
					<div>
						<ul>
							<li><input type="radio" id="中国工商银行" name="radio_bank" checked="checked" value="ICBC-NET-B2C">中国工商银行－ICBC-NET-B2C</li>
						    <li><input type="radio" id="中国建设银行" name="radio_bank" value="CCB-NET-B2C">中国建设银行－CCB-NET-B2C</li>
						    <li><input type="radio" id="中国农业银行" name="radio_bank" value="ABC-NET-B2C">中国农业银行－ABC-NET-B2C</li>
						    <li><input type="radio" id="中国银行" name="radio_bank" value="BOC-NET-B2C">中国银行－BOC-NET-B2C</li>
						    <li><input type="radio" id="中国招商银行" name="radio_bank" value="CMBCHINA-NET-B2C">中国招商银行－CMBCHINA-NET-B2C</li>
						    <li><input type="radio" id="中国民生银行" name="radio_bank" value="CMBC-NET-B2C">中国民生银行－CMBC-NET-B2C</li>
						    <li><input type="radio" id="中国交通银行" name="radio_bank" value="BOCO-NET-B2C">中国交通银行－BOCO-NET-B2C</li>
						    <li><input type="radio" id="中国邮政储蓄银行" name="radio_bank" value="POST-NET-B2C">中国邮政储蓄银行－POST-NET-B2C</li>
						    <li><input type="radio" id="中国光大银行" name="radio_bank" value="CEB-NET-B2C">中国光大银行－CEB-NET-B2C</li>
						    
						</ul>
						</div>
						
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">银行信息:</td>
					<td align="left" class="l-table-edit-td">
						<div>
						<input type="text" id="bankCardCode" name="bankCardCode" value="ICBC-NET-B2C"/> 
						<input type="text" id="bankCardType" name="bankCardType" value="DEBIT"/> 
						<input type="text" id="bankCardName" name="bankCardName" value="中国工商银行"/> 
						<script type="text/javascript">
						$('input:radio[name="radio_bank"]').click(function(){
							$('input:radio[name="radio_bank"]').each(function(index,domEle){					
								if('radio_bank'==domEle.name && domEle.checked){
									$('#bankCardCode').val(domEle.value);
									$('#bankCardType').val('DEBIT');
									$('#bankCardName').val(domEle.id);	
									return;
								}
						});
						
						
							
							
						});
						</script>
						</div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">备注:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="remarks" id="remarks" />
					</td>
				</tr>
				
				<tr>
					<td colspan="2" class="l-table-edit-td">
						
					</td>
				</tr>
					
				<tr>
					<td colspan="2" align="center" class="l-table-edit-td">	
						 <input type="submit" value="提交" id="Button1" class="l-button l-button-submit" />					
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