/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var PaymentAdd = function() {
		this.init();
	};
	PaymentAdd.prototype = {
		config : {
			addUrl : root + '/payment/addPayment.action',
			modeifyUrl : root + '/payment/modifyPayment.action',
			queryById : root + '/payment/queryById.action'
		},
		cache : {
			account_code : null,
			dialog : null,
			grid : null,
			dialog_001 : null,
		},
		init : function() {
			this.initAddOrEdit();
			this.render();
			this.loadCss();
			this.initPageData();
		},
		// 初始化账户编码选择框
		initPageData : function() {
			var that = this;
			$('#insideAccountNo').ligerComboBox({
				onBeforeOpen : that._selectStore,
				valueFieldID : 'storeText001',
				width : 180
			});
			$('#insideAccountNo').ligerGetComboBoxManager();
		},
		// 初始化点击商户编码input框时打开新页面
		_selectStore : function() {
			var that = paymentAdd;
			var url = 'merchant-list.jsp';
			var title = '账户管理-普通账户列表';
			that.cache.dialog_001 = MALQ_UI.open_button(url, subHeight + 20, subWidth + 200, that.f_selectStoreOK, function(item, dialog) {
				dialog.close();
			}, title);
			return false;
		},
		// 点击商户编码操作
		f_selectStoreOK : function(item, dialog) {
			var fn = dialog.frame.accountList.f_select || dialog.frame.window.accountList.f_select;
			var data = fn();
			if (data) {
				var insideAccountNo = '';
				$(data.ids).each(function() {
					insideAccountNo = this.id;
				});
				$('#insideAccountNo').val(insideAccountNo ? insideAccountNo : "");
				dialog.close();
			}
		},
		render : function() {
			var that = this;
			// 初始化页面检验规则
			$.metadata.setType('attr', 'validate');
			$('#form1').validate({
				rules : {
				},
				messages : {
				},
				errorPlacement : function(lable, element) {
					CSM_VALIDATE.errorPlacement(lable, element);
				},
				success : function(lable) {
					CSM_VALIDATE.success(lable);
				},
				submitHandler : function() {
					showMask();
					//var ltime = date2stamp($('#applyTime').val());// 将付款时间转换为时间戳
					var params = {
						'model.description' : $('#description').val(), // 原因
						'model.storeId' : $('#insideAccountNo').val(),// 商户编码
						'model.tradeAmount' : $('#tradeAmount').val(),// 扣款金额
						'model.ownerUserNo' : $('#ownerUserNo').val(),
						//'model.remitterName' : $('#remitterName').val(),// 付款人
						'model.payType' : '4202',
						//'model.applyTime' : ltime,// 付款时间，可能需要转换为毫秒数
						//'model.amountArriveTime' : date2stamp($('#amountArriveTime').val()),// 资金到账时间，需要转为毫秒数
						'model.applyStatus' : '4100',// 申请状态
						'model.stepNo' : '4301',// 申请步骤号
					};
					var url;
					if($("#id").val()==''){
	                	 url = that.config.addUrl;  
	                 }else{
	                	 params['model.id'] = $("#id").val();
	                	 url = that.config.modeifyUrl;
	                 }
					JAjax(url, params, 'json', function(data) {
						hideMask();
						if (data) {
							parent.window.paymentList.cache.grid.loadData();
							parent.window.paymentList.alert('操作成功！', 'clew_ico_succ');
							parent.window.paymentList.cache.dialog.close();
							alert(data);
						} else {
							JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
						}

					}, function() {
						hideMask();
						JSWK.clewBox('提交数据时发生异常', 'clew_ico_fail', failShowTime);
					}, true);
				}
			});

		},

		initAddOrEdit : function() {
			var that = this;
			// 取值判断是增加还是修改
			var pageId = parent.window.paymentList.cache.subPageId;
			// 取值判断是增加还是修改
			if (pageId == 'edit') {
				var id = parent.window.paymentList.cache.id;
				$("#id").val(id);
				JAjax(that.config.queryById, {
					'id' : id
				}, 'json', function(data) {
					if (data) {
						$('#remitterName').val(data.remitterName);// 付款人
						$("#description").val(data.description);// 扣款原因

						$("#applyTime").val(getSmpFormatDateByLong(data.applyTime, true));// 付款时间
						$("#amountArriveTime").val(getSmpFormatDateByLong(data.amountArriveTime, true));// 资金到账时间

//						$("#fundsConfirmId").val(data.fundsConfirmTime);// 资金确认人
//						$("#fundsConfirmTime").val(getSmpFormatDateByLong(data.fundsConfirmTime, true));// 资金确认时间

						$("#tradeAmount").val(convertFen2Yuan(data.tradeAmount));// 扣款金额
						$("#insideAccountNo").val(data.storeId);// 商户ID
					}
				}, function() {
					JSWK.clewBox('查询充值数据失败', 'clew_ico_fail', failShowTime);
				}, true);
			}
		},
		f_save : function() {
			$('#form1').submit();
		},
		loadCss : function() {
			$('form').ligerForm();
			var imgUrl = '../../images/spanIoc.png';
			MALQ_CODE.titleBlock($('#step1'), '线下流程管理-扣款审核申请', imgUrl);
			$('#step1').css('width', (parent.window.paymentList.config.subWidth - 100));
			$("#applyTime").removeAttr('style');
			$("#amountArriveTime").removeAttr('style');
			$("#fundsConfirmTime").removeAttr('style');
		},
		reset : function() {
			window.location = 'payment-add.jsp';
		},
	};

	$(document).ready(function() {
		window.paymentAdd = new PaymentAdd();
	});
})();