/**
 * All rights Reserved, Sinoiov Technology Co., LTD. Copyright(C) 2012-2014
 * 商户管理-商户回调新增/修改js
 */
(function() {
	var AddSmsPerson = function() {
		this.init();
	};
	AddSmsPerson.prototype = {
		config : {
			addUrl : root + '/smsPerson/add.action',
			editUrl : root + '/smsPerson/modify.action',
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
					name : {
						required : true,
						maxlength:50,
					},
					job : {
						required : true,
						maxlength:50,
					},
					mobileNo : {
						required : true,
						maxlength:11,
						minlength:11,
						number : true
					}
					
				},
				messages: {
	        		mobileNo:{number:'请输入正确的手机号码'}
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
						'model.templateCode' : $('#templateCode').val(),
						'model.name' : $('#name').val(),
						'model.job' : $('#job').val(),
						'model.mobileNo' : $('#mobileNo').val(),
					};
					temData['templateCode'] = $('#templateCode').val();
					temData['name'] = $('#name').val();
					temData['job'] = $('#job').val();
					temData['mobileNo'] = $('#mobileNo').val();
					var url;
					url = that.config.addUrl;
					if ('add' == parent.window.personList.cache.subPageId) {
						url = that.config.addUrl;
					} else {
						params['model.templateCode'] = $('#templateCode').val();
						params['model.uuid'] = parent.window.personList.cache.id;
						params['model.name'] = $('#name').val();
						params['model.job'] = $('#job').val();
						params['model.mobileNo'] = $('#mobileNo').val();
						temData['templateCode'] = $('#templateCode').val();
						temData['uuid'] = parent.window.personList.cache.id;
						temData['name'] = $('#name').val();
						temData['job'] = $('#job').val();
						temData['mobileNo'] = $('#mobileNo').val();
						url = that.config.editUrl;
					}
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
						JSWK.clewBox('提交数据时发生异常，可能是您使用了非法字符。', 'clew_ico_fail', failShowTime);
					}, true);
				},
			});
		},
		initAddOrEdit : function() {
			var that = this;
			var templateCode = parent.window.personList.cache.templateCode;
			$("#form1").find("#templateCode").val(templateCode);
			var pageId = parent.window.personList.cache.subPageId;
			if (pageId == 'edit') {
				var id = parent.window.personList.cache.id;
				JAjax(that.config.queryObjectUrl, {
					'id' : id
				}, 'json', function(data) {
					if (data) {
						$('#name').val(data.name);
						$('#job').val(data.job);
						$('#mobileNo').val(data.mobileNo);
					}
				}, function() {
					JSWK.clewBox('查询回调对象失败', 'clew_ico_fail', failShowTime);
				}, true);
			}
		},
		f_save:function(){
			$('#form1').submit();
		},
		loadCss : function() {
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '联系人信息', imgUrl);
			$('#step1').css('width',(parent.window.personList.config.subWidth-100));
			$('form').ligerForm();
		},
		reset : function() {
			window.location = 'callback-add.jsp';
		}
	};

	$(document).ready(function() {
		window.addSmsPerson = new AddSmsPerson();
	});
})();