<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>运营管理平台－支付中心－与商户对账</title>
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
    	var ClearingAndSettlement = function() {
    		this.init();
    	};
    	ClearingAndSettlement.prototype = {
    		config : {
    			rechargeUrl : '<%=root%>/upp/clearingAndSettlementController/merchantOrderClearing',
    			rechargeAgainUtl : '<%=root%>/upp/clearingAndSettlementController/merchantClearingAgain'
    		},
    		cache : {
    			dialog : null
    		},
    		init : function(){
    			this.render();
    			this.loadCss();
    		},
    		contrast : function(){
    			var that = this;
    			 JAjax(that.config.rechargeAgainUtl, null, 'json', function(data){ 
	                	if(data){           
	                		  alert(data.data);
	                		  
	                	}else{
	                		 JSWK.clewBox(data,'clew_ico_fail',failShowTime);
	                	}
	                	
					}, function() {JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
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
    	                JAjax(that.config.rechargeUrl, form, 'json', function(data){ 
    	                	if(data){           
    	                		  alert(data.data);
    	                		  
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
    		window.clearingAndSettlement = new ClearingAndSettlement();
    	});
    })();
    
    </script>
    
</head>
<body style="height: 90%; padding: 20px;">

<center><h2>商户对账</h2></center>
<hr />


	<form name="form1" method="post" action="" id="form1" >
		<div id="tableDiv" style="overflow: auto; height: 100%">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>对账日期:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="accountDate" id="accountDate" ltype="text" validate="{required:true}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>批次号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="batchNo" id="batchNo" ltype="text" validate="{required:true}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>是否最后一批:</td>
					<td align="left" class="l-table-edit-td">
						<select name="isEnd" id="idEnd">
							<option value="0">是</option>
							<option value="1">否</option>
						</select>
					</td>
				</tr>				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>支付订单号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="list[0].orderNo" id="list[0].orderNo" ltype="text" validate="{required:true}" />
					</td>
				</tr>	
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>订单金额:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="list[0].orderAmount" id="list[0].orderAmount" ltype="text" validate="{required:true}" />
					</td>
				</tr>	
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>订单类型:</td>
					<td align="left" class="l-table-edit-td">
						<!-- <input type="text" name="list.orderType" id="list.orderType" ltype="text" validate="{required:true}" /> -->
						<select name="list[0].orderType" id="list[0].orderType">
							<option value="1">在线充值</option>
							<option value="2" selected="selected">转账(付款)</option>
							<option value="3">冻结金额</option>
							<option value="4">解冻金额</option>
							<option value="5">提现</option>
							<option value="11">线下充值</option>
							<option value="51">快速提现</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商户编码:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="list[0].storeCode" id="list[0].storeCode" ltype="text" validate="{required:true}" />
					</td>
				</tr>	
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>支付订单表ID:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="list[0].orderId" id="list[0].orderId" ltype="text" validate="{required:true}" />
					</td>
				</tr>	
				<tr>
					<td align="right" class="l-table-edit-td">内部账户号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="list[0].accountNo" id="list[0].accountNo" ltype="text" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">订单的交易状态:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="list[0].orderStatus" id="list[0].orderStatus" ltype="text" />
					</td>
				</tr>	
				<tr>
					<td align="right" class="l-table-edit-td">交易流水号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="list[0].tradeExternalNo" id="list[0].tradeExternalNo" ltype="text" />
					</td>
				</tr>	
				<tr>
					<td align="right" class="l-table-edit-td">支付订单生成时间:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="list[0].createTime" id="list[0].createTime" ltype="text" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">业务订单号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="list[0].workOrderNo" id="list[0].workOrderNo" ltype="text" />
					</td>
				</tr>
					
				<tr>
					<td colspan="2" align="center" class="l-table-edit-td">					
						       <input type="submit" value="提交" id="Button1" class="l-button l-button-submit" />					
					</td>
					<td colspan="2" align="center" class="l-table-edit-td">					
						       <input type="button" value="再次对账" id="Button1" onclick="window.clearingAndSettlement.contrast()" class="l-button l-button-submit" />					
					</td>
				</tr>
			</table>
		</div>
	</form>
	
	<div style="display: none"></div>
</body>

</html>