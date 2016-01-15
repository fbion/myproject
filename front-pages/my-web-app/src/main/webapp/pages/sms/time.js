/**
 * All rights Reserved, Sinoiov Technology Co., LTD. Copyright(C) 2012-2014
 * 商户管理-商户回调新增/修改js
 */
(function() {
	var Time = function() {
		this.init();
	};
	Time.prototype = {
		config : {
			updateUrl : root + '/smsPerson/addTime.action',
			queryObjectUrl : root + '/smsPerson/getSmsPerson.action',
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
					startTime : {
						required : true
					},
					endTime : {
						required : true
					},
					day : {
						required : true,
					}
					
				},
				messages: {
					day:{number:'请选择至少一天'}
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
					var chk_value =[]; 
					$("input[name='day']:checked").each(function(){
						chk_value.push($(this).val()); 
					});
					var startTime = $("#startTime").val();
					var endTime = $("#endTime").val();
					params = {
						'model.startTime' : startTime,
						'model.endTime' : endTime,
						'model.day' : chk_value,
						'model.uuid' : parent.window.personList.cache.uuid,
					};
					temData['startTime'] = startTime;
					temData['endTime'] = endTime;
					temData['day'] = chk_value;
					temData['uuid'] = parent.window.personList.cache.uuid;
					var url;
					url = that.config.updateUrl;
					JAjax(url, params, 'json', function(data) {
						hideMask();
						if (data) {
							parent.window.personList.cache.grid.loadData();
							parent.window.personList.success_alert(data.message);
							parent.window.personList.cache.dialog.close();
						} else {
							JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
						}

					}, function() {
						hideMask();
						JSWK.clewBox('提交数据时发生异常', 'clew_ico_fail', failShowTime);
					}, true);
				},
			});
		},
		initAddOrEdit : function() {
			var that = this;
			var id = parent.window.personList.cache.uuid;
			JAjax(that.config.queryObjectUrl, {
					'id' : id
				}, 'json', function(data) {
					if (data) {
						$('#startTime').val(data.startTime);
						$('#endTime').val(data.endTime);
						//checkBox选中
						var day = data.day;
						var chk_value = [];
						chk_value = day.split(",");
						$("input[name='day']:checkbox").each(function(){
							for(var i=0;i<chk_value.length;i++){
								if($(this).val()==chk_value[i]){
									var id = this.id;
									//$.ligerui.get(id).setValue("true");
									$(this).attr("checked","checked");
								}
							}
						});
						
					}
				}, function() {
					JSWK.clewBox('查询失败', 'clew_ico_fail', failShowTime);
				}, true);
		},
		f_save:function(){
			$('#form1').submit();
		},
		loadCss : function() {
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '联系人信息', imgUrl);
			$('#step1').css('width',(parent.window.personList.config.subWidth-100));
			$('form1').ligerForm();
		},
	};

	$(document).ready(function() {
		window.time = new Time();
	});
})();