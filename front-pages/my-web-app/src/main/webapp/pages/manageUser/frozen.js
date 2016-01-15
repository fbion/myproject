/**
 * All rights Reserved, Sinoiov Technology Co., LTD. Copyright(C) 2012-2014
 * 账户管理-冻结金额js
 */
(function() {
	var Frozen = function() {
		this.init();
	};
	Frozen.prototype = {
		config : {
			addUrl : root + '/callback/callbackAdd.action',
			// 检查属性是否唯一
			chackerUrl : root + '/account/check.action'
		},
		cache : {
			dialog : null
		},
		init : function() {
			this.initAddOrEdit();
			this.loadCss();
			this.render();
		},
		render : function() {
			var accountNo = parent.window.accountList.cache.accountNo;
			var available = parent.window.accountList.cache.usableBalance;
			var that = this;
			$.metadata.setType('attr', 'validate');
			$('#form1').validate({
				rules: {
	            	frozen:{
	            	  required:true,
	            	       min:0.1,
	            	       max:available,
	        		/*	remote:{
	        				url: that.config.chackerUrl,
	        				type: 'POST', 
	        				number : true,
	        				dataType: 'json', 
	        				data:{ //要传递的数据
	        					'accountNo': function(){return accountNo;},
	        				    'frozen' : function(){return $("#frozen").val();},
	        				    'available' : function(){return available},
	        				    
	        				 }
	        			   }*/
	        		 }
	        		
	        	},
	        	messages: {
	        		frozen:{/*remote: "有没有搞错啊"*/required:"请输入冻结金额",min:"冻结金额不能小于0.1元",max:"冻结金额不能大于可用余额"},
	        	},
				errorPlacement : function(lable, element) {
					CSM_VALIDATE.errorPlacement(lable, element);
				},
				success : function(lable) {
					CSM_VALIDATE.success(lable);
				},
				submitHandler : function() {
					var temData = {};
					params = {
						'model.storeId' : $('#storeId').val(),
						'model.functionDesc' : $('#functionDesc').val(),
						'model.url' : $('#url').val(),
					};
					temData['storeId'] = $('#storeId').val();
					temData['functionDesc'] = $('#functionDesc').val();
					temData['url'] = $('#url').val();
					var url;
					url = that.config.addUrl;
					if ('add' == parent.window.callbackList.cache.subPageId) {
						url = that.config.addUrl;
					} else {
						params['model.storeId'] = $('#storeId').val();
						params['model.id'] = $('#id').val();
						params['model.functionDesc'] = $('#functionDesc').val();
						params['model.url'] = $('#url').val();
						temData['storeId'] = $('#storeId').val();
						;
						temData['id'] = $('#id').val();
						temData['functionDesc'] = $('#functionDesc').val();
						temData['url'] = $('#url').val();
						url = that.config.editUrl;
					}
					JAjax(url, params, 'json', function(data) {
						if (data) {
							parent.window.callbackList.cache.grid.loadData();
							parent.window.callbackList.success_alert(data.message);
							parent.window.callbackList.cache.dialog.close();
						} else {
							JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
						}

					}, function() {
						JSWK.clewBox('提交数据时发生异常', 'clew_ico_fail', failShowTime);
					}, true);
				},
			});
		},
		initAddOrEdit : function() {
			var that = this;
		/*	var storeId = $('#storeId', parent.document).val();
			$("#form1").find("#storeId").val(storeId);
			var pageId = parent.window.callbackList.cache.subPageId;
			if (pageId == 'edit') {
				var id = parent.window.callbackList.cache.id;
				JAjax(that.config.queryObjectUrl, {
					'id' : id
				}, 'json', function(data) {
					if (data) {
						$('#id').val(data.id);
						$('#storeId').val(data.storeId);
						$('#functionDesc').val(data.functionDesc);
						$('#url').val(data.url);
					}
				}, function() {
					JSWK.clewBox('查询回调对象失败', 'clew_ico_fail', failShowTime);
				}, true);
			}*/
		},
		save:function(){
			$('#form1').submit();
		},
		loadCss : function() {
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '账户管理-冻结', imgUrl);
		//	$('#step1').css('width',(parent.window.accountList.config.subWidth-100));
			$('form').ligerForm();
		},
		reset : function() {
			window.location = 'frozen.jsp';
		}
	};

	$(document).ready(function() {
		window.frozen = new Frozen();
	});
})();