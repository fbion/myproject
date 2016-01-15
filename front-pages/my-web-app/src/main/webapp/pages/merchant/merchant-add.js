/**
 * 商户管理-商户接入
 */
(function() {
	var Merchant = function() {
		this.init();
	};
	Merchant.prototype = {
		config : {
			addUrl : root+'/merchant/addMerchant.action',
			editUrl :  root+'/simpleCode/editCode.action',
			queryObjectUrl : root+'/simpleCode/getCodeById.action',
			//检查属性是否唯一
			chackerUrl : root+'/merchant/checkExist.action',
			//取商户类型
			querySimpleCodeUrl : root+'/simpleCode/findMerchant.action',
			subHeight : subHeight,
			subWidth : subWidth
		},
		cache : {
			dialog : null
		},
		init : function(){
			this.initAddOrEdit();
			this.render();
			this.loadCss();
		},
		render : function(){
			var that = this;
		    //初始化页面检验规则
		    $.metadata.setType('attr', 'validate');
	        $('#form1').validate({
	            rules: {
	            	storeName:{
	        			remote:{
	        				url: that.config.chackerUrl,
	        				type: 'POST', 
	        				dataType: 'json', 
	        				data:{ //要传递的数据
	        				    'storeName' : function(){return $("#storeName").val();},
	        				    'message' : 'storeName'
	        				 }
	        			   }
	        		 },
	        		 contactPhone : {
	        			required : true,
	        			number : true,
	        			minlength:11,
	        			maxlength:11
	        		 },
	        		 check1:{
	        			 required:true
	        		 }
	        	},
	        	messages: {
	        		storeName:{remote: '商户名称已存在'},
	        		check1:{required:'请阅读用户保密协议'}
	        	},
	        	errorPlacement: function (lable, element){
	        		CSM_VALIDATE.errorPlacement(lable, element);
		        },
	            success: function (lable){
	            	CSM_VALIDATE.success(lable);
	            },
	            submitHandler: function(){
	            	showMask();
	                params = {
	        			'model.storeName': $('#storeName').val(), //商户名称
	        			'model.storeType': $("#storeType").ligerGetComboBoxManager().getValue(),//商户类型
	        			'model.contactUser': $('#contactUser').val(),//联系人
	        			'model.contactPhone': $('#contactPhone').val(),//联系人电话
	        			'model.storeStatus': '3002',//商户状态
	        			'model.privateKey':$("#privateKey").val(),//秘钥
	        			
	            	};
	                var url;
	                url = that.config.addUrl;
	                JAjax(url, params, 'json', function(data){   
	                	hideMask();
	                	if(data){
	                		parent.window.merchantList.refreshGrid();
	                		parent.window.merchantList.success_alert(data.message);       		
	                		parent.window.merchantList.cache.dialog.close(); 
	                	}else{
	                		 JSWK.clewBox(data,'clew_ico_fail',failShowTime);
	                	}
	                	
					}, function(){hideMask();JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
	            }
	        });
	       
		},
		f_save:function(){
			$('#form1').submit();
		},
		showClause:function(){
			var that = this;
			var url = "merchant-clause.jsp";
			var title = "服务协议与隐私条款";
			that.cache.dialog = MALQ_UI.open_button(url, subHeight,subWidth,null,that.cancel, title ); 
		},
		secretKey : function(){
			var that = this;
			var url = "rsa.jsp";
			var title = "生成秘钥对";
			that.cache.dialog = MALQ_UI.open_button(url, subHeight+20,subWidth, that.success, null, title);
		},
		success : function(item, dialog) {
			var key = dialog.frame.window.ras.cache.publicKey;
			$("#privateKey").val(key);
			dialog.close();
		},
		// 显示秘钥
		privateKey : function(platformIdentity, privateKey) {
			var title = '商户秘钥及编号';// code-edit-status
			var html = '<div align="center" style="padding: 30px;">';
			html += '<div style="height: 30px;">商户编号：<span>'+platformIdentity+'</span></div>';
			html += '<div style="height: 30px;">商户秘钥：<span>'+privateKey+'</span></div>';
			html += '</div>';
			var options = {
				target : html,
				name : 'winselector',
				isHidden : false,
				height : 300,
				width : 600,
				title : title
			};
			MALQ_UI._no_task(options);
			MALQ_UI._buttons(options, function(item, dialog) {
				window.merchant.reset();
			});
			$.ligerDialog.open(options);
		},
		initAddOrEdit:function(){
			//获取码表对应码值的下拉列表（商户类型）
			//同意隐私协议及服务条款默认为选中
			$("#check1").attr("checked","checked");
			var w = 140;
	  		MALQ_CODE.getSelectByCodeType($("#storeType"), "2000", w, null);
			$("#storeType").ligerComboBox({width:w });
		 },
		 operationCheckBox : function(){
			 $.ligerui.get("check1").setValue("true");//将checkBox设为选中
		 },
		 loadCss : function(){
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '商户管理-商户接入', imgUrl);
			$('#step1').css('width',(parent.window.merchantList.config.subWidth-100));
			$('form').ligerForm();
		 },
		 reset : function(){
			 window.location='merchant-add.jsp';
		 },
		 success_alert : function(data) {
				JSWK.clewBox(data, 'clew_ico_succ');
		},
		fail_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
		},
		cancel : function(item, dialog) {
			dialog.frame.window.merchantClause.comply();
			dialog.close();
		},
	};
	
	$(document).ready(function() {
		window.merchant = new Merchant();
	});
})();