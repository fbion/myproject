/**
 * 账户管理-中交账户列表-预警
 */
//判断数值类型，包括整数和浮点数
jQuery.validator.addMethod("isNumber", function(value, element) {       
     return this.optional(element) || /^[-\+]?\d+$/.test(value) || /^[-\+]?\d+(\.\d+)?$/.test(value);       
}, "匹配数值类型，包括整数和浮点数");
(function() {
	var AlarmAmountAccount = function() {
		this.init();
	};
	AlarmAmountAccount.prototype = {
		config : {
			queryObjectUrl : root + '/account/getDetailById.action',
			addUrl : root + '/ZJAccount/addAlarmAmount.action',
			editUrl : root + '/ZJAccount/editAlarmAmount.action',
			queryParamUrl : root + '/ZJAccount/queryAlarmAmount.action',
			subHeight : subHeight,
			subWidth : subWidth
		},
		cache : {
			dialog : null,
			id:null
		},
		init : function() {
			this.render();
			this.loadCss();
			this.loadData();
		},
		loadData:function(){
			var that = this;
			var id = parent.window.zjaccountList.cache.id;
			JAjax(that.config.queryObjectUrl, {'id' : id}, 'json', function(data) {
				if (data) {
					$('#insideAccountNo').val(data.insideAccountNo);
					$("#insideAccountNoTD").text(data.insideAccountNo);
					$("#ownerLoginNameTD").text(data.ownerLoginName);
					JAjax(that.config.queryParamUrl, {name : 'model.paramName',value : data.insideAccountNo}, 'json', function(paramObj) {
						if (paramObj) {
							$("#remainder").val(paramObj.paramValue);
							$("#paramId").val(paramObj.id);
						} else {
							JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
						}
					}, function() {JSWK.clewBox('提交数据时发生异常', 'clew_ico_fail', failShowTime);}, true);
					
				}
			}, function() {JSWK.clewBox('查询详情失败', 'clew_ico_fail', failShowTime);}, true);
			
		},
		render : function() {
			var that = this;
			// 初始化页面检验规则
			$.metadata.setType('attr', 'validate');
			$('#form1').validate({
				rules : {
					remainder : {
						required : true,
						isNumber:true
					}
				},
				messages : {
					remainder : {
						required : '阀值金额不能为空',
						isNumber:'金额为整数/浮点数'
					}
				},
				errorPlacement : function(lable, element) {
					CSM_VALIDATE.errorPlacement(lable, element);
				},
				success : function(lable) {
					CSM_VALIDATE.success(lable);
				},
				submitHandler : function() {
					var params = {};
					params = {
						'model.paramName' : $('#insideAccountNo').val(), // 账户号
						'model.paramDataType':'String',
						'model.paramValue' : $("#remainder").val(),
						'model.id':$("#paramId").val()
					// 阀值金额
					};
					var url='';
					if($("#paramId").val()!=''){
						url = that.config.editUrl;
					}else{
						url = that.config.addUrl;
					}
					JAjax(url, params, 'json', function(data) {
						if (data) {
							parent.window.zjaccountList.refreshGrid();
							parent.window.zjaccountList.success_alert(data.message);
							parent.window.zjaccountList.cache.dialog.close();
						} else {
							JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
						}
					}, function() {JSWK.clewBox('提交数据时发生异常', 'clew_ico_fail', failShowTime);}, true);
				}
			});

		},
		f_save : function() {
			$('#form1').submit();
		},
		loadCss : function() {
			var imgUrl = '../../images/spanIoc.png';
			MALQ_CODE.titleBlock($('#step1'), '账户管理-中交账户列表-预警设置', imgUrl);
			$('#step1').css('width', (parent.window.zjaccountList.config.subWidth - 100));
			$('form').ligerForm();
		}
	};

	$(document).ready(function() {
		window.alarmAmountAccount = new AlarmAmountAccount();
	});
})();