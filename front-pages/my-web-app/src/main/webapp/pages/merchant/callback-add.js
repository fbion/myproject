/**
 * All rights Reserved, Sinoiov Technology Co., LTD. Copyright(C) 2012-2014
 * 商户管理-商户回调新增/修改js
 */
(function() {
	var CallbackAdd = function() {
		this.init();
	};
	CallbackAdd.prototype = {
		config : {
			addUrl : root + '/callback/callbackAdd.action',
			editUrl : root + '/callback/callbackEdit.action',
			queryObjectUrl : root + '/callback/getCallbackById.action',
			// 检查属性是否唯一
			chackerUrl : root + '/simpleCode/checkExist.action'
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
			var that = this;
			$.metadata.setType('attr', 'validate');
			$('#form1').validate({
				rules : {
					functionDesc : {
						required : true
					},
					url : {
						required : true
					}
				},
				errorPlacement : function(lable, element) {
					CSM_VALIDATE.errorPlacement(lable, element);
				},
				success : function(lable) {
					CSM_VALIDATE.success(lable);
				},
				submitHandler : function() {
					showMask();
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
						hideMask();
						if (data) {
							parent.window.callbackList.cache.grid.loadData();
							parent.window.callbackList.success_alert(data.message);
							parent.window.callbackList.cache.dialog.close();
						} else {
							JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
						}

					}, function() {
						hideMask();
						JSWK.clewBox('提交数据时发生异常，可能是您使用了非法字符。', 'clew_ico_fail', failShowTime);
					}, true);
				},
			});
		},
		initAddOrEdit : function() {
			var that = this;
			var storeId = $('#storeId', parent.document).val();
			$("#form1").find("#storeId").val(storeId);
			var pageId = parent.window.callbackList.cache.subPageId;
			if (pageId == 'edit') {
				showMask();
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
					hideMask();
				}, function() {
					hideMask();
					JSWK.clewBox('查询回调对象失败', 'clew_ico_fail', failShowTime);
				}, true);
			}
		},
		f_save:function(){
			$('#form1').submit();
		},
		loadCss : function() {
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '商户管理-回调', imgUrl);
			$('#step1').css('width',(parent.window.callbackList.config.subWidth-100));
			$('form').ligerForm();
		},
		reset : function() {
			window.location = 'callback-add.jsp';
		}
	};

	$(document).ready(function() {
		window.callbackAdd = new CallbackAdd();
	});
})();