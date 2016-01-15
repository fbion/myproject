/**
 * 商户管理-商户接入修改
 */
(function() {
	var MerchantEdit = function() {
		this.init();
	};
	MerchantEdit.prototype = {
		config : {
			editUrl : root + '/merchant/editMerchant.action',
			queryObjectUrl : root + '/merchant/getMerchantById.action',
			// 检查属性是否唯一
			chackerUrl : root + '/merchant/checkExist.action',
			subHeight : subHeight,
			subWidth : subWidth
		},
		cache : {
			dialog : null,
			publickKey:null
		},
		init : function() {
			this.initAddOrEdit();
			this.loadCss();
			this.render();
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
	        				data:{ 'storeName':function(){ 
	        					if($("#storeName").val()==$("#existStoreName").val()){
	        						return "";
	        					}else
	        						return $("#storeName").val(); 
	        					},
	        				       'message':'storeName'
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
	                params = {
	                	'model.id' : $("#id").val(),// 商户ID
	        			'model.storeName': $('#storeName').val(), //商户名称
	        			'model.storeType': $("#storeType").ligerGetComboBoxManager().getValue(),//商户类型
	        			'model.contactUser': $('#contactUser').val(),//联系人
	        			'model.contactPhone': $('#contactPhone').val(),//联系人电话
	        			'model.privateKey':$("#privateKey").val(),//秘钥
	        			
	            	};
	                var url;
	                url = that.config.editUrl;
	                JAjax(url, params, 'json', function(data){   
	                	if(data){
	                		parent.window.merchantList.refreshGrid();
	                		parent.window.merchantList.success_alert(data.message);       		
	                		parent.window.merchantList.cache.dialog.close();
	                	}else{
	                		 JSWK.clewBox(data,'clew_ico_fail',failShowTime);
	                	}
	                	
					}, function(){JSWK.clewBox('提交数据时发生异常，可能是您使用了非法字符。','clew_ico_fail',failShowTime);}, true);
	            }
	        });
	       
		},
		f_save:function(){
			$("#form1").submit();
		},
		initAddOrEdit : function() {
			var that = this;
			var id = parent.window.merchantList.cache.id;
			JAjax(that.config.queryObjectUrl, {
				'id' : id
			}, 'json', function(data) {
				if (data) {
					$('#storeName').val(data.storeName);// 商户名称
					$('#existStoreName').val(data.storeName);//已存在的商户名称
					$("#contactUser").val(data.contactUser);// 联系人
					$("#contactPhone").val(data.contactPhone);// 联系人电话
					$("#storeStatusTD").text(MALQ_CODE.getCodeName("3000",data.storeStatus));
					// 商户类型
					var w=178;
					MALQ_CODE.getSelectByCodeType($("#storeType"), "2000", w, null);
					$("#storeType").ligerComboBox({
						width : w
					});
					$("#storeType").ligerGetComboBoxManager().selectValue(data.storeType);
					$('#id').val(data.id);
					// 秘钥
					$("#privateKey").val(data.privateKey);
					that.cache.publickKey=data.privateKey;
				}
			}, function() {
				JSWK.clewBox('查询商户对象失败', 'clew_ico_fail', failShowTime);
			}, true);
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
		loadCss : function() {
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '商户管理-商户接入', imgUrl);
			$('#step1').css('width',(parent.window.merchantList.config.subWidth-100));
			$('form').ligerForm();
		},
		reset : function() {
			var that=this;
			$("#privateKey").val(that.cache.publickKey);
		}
	};

	$(document).ready(function() {
		window.merchantEdit = new MerchantEdit();
	});
})();