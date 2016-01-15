/**
 * All rights Reserved, Sinoiov Technology Co., LTD. Copyright(C) 2012-2014
 * 普通账户列表-解冻金额/js
 */
(function() {
	var UnfreezeAmount = function() {
		this.init();
	};
	UnfreezeAmount.prototype = {
		config : {
			//解冻账户金额
			unfreezeUrl : root + '/account/unfreezeAmount.action',
		},
		cache : {
			dialog : null,
			accountNo : null
		},
		init : function() {
			$('#accountNo').val($('#accountNo',window.parent.document).val()); 
			$('#workOrderNo').val($('#workOrderNo',window.parent.document).val()); 
			$('#tradeExternalNo').val($('#tradeExternalNo',window.parent.document).val());			
			this.loadCss();
			this.render();
		},
		render : function() {
			var that = this;
			$.metadata.setType('attr', 'validate');
			$('#form1').validate({
				rules : {
					orderAmount : {
						required : true
					},
					workOrderNo : {
						required : true
					},
					tradeExternalNo : {
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
					params = {
						'accountNo' : $('#accountNo').val(),
						'orderAmount' : $('#orderAmount').val(),
						'workOrderNo' : $('#workOrderNo').val(),
						'tradeExternalNo' : $('#tradeExternalNo').val(),
					};
					JAjax(that.config.unfreezeUrl, params, 'json', function(data) {
						hideMask();
						if (data=="1") {
							parent.window.queryList.cache.grid.loadData();
							parent.window.queryList.alert('操作成功','clew_ico_succ');
							parent.window.queryList.cache.dialog.close();
						} else {
							JSWK.clewBox("操作失败", 'clew_ico_fail', failShowTime);
						}

					}, function() {
						hideMask();
						JSWK.clewBox('提交数据时发生异常，可能是您使用了非法字符。', 'clew_ico_fail', failShowTime);
					}, true);
				},
			});
		},
		f_save:function(){
			$('#form1').submit();
		},
		loadCss : function() {
			//var imgUrl = '../../images/spanIoc.png';		 
			//MALQ_CODE.titleBlock($('#step1'), '解冻金额', imgUrl);
			//$('#step1').css('width',(parent.window.accountList.config.subWidth-100));
			$('form').ligerForm();
		},
		reset : function() {
			window.location = 'unfreezeAmount.jsp';
		}
	};

	$(document).ready(function() {
		window.unfreezeAmount = new UnfreezeAmount();
	});
})();