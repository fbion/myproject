(function() {
	var DealList = function() {
		this.init();
	};
	DealList.prototype = {
		config : {
			// 通过ID查找充值明细
			queryById : root + '/payment/queryById.action',
			addApprovalUrl : root +'/recharge/addPaymentApproval.action'
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null
		},
		init : function() {
			this.render();
			this.loadData();
		},
		render : function() {
			var that = this;
			// 布局
			$('#layout1').ligerLayout({
				leftWidth : '210',
				allowLeftResize : false,
				height : '100%',
				heightDiff : -1,
				space : 4,
				onHeightChanged : that._heightChanged
			});
			$('.l-layout-header').show();
			$('.l-layout-header-toggle').css('z-index', '2');
			$('#pageloading').hide();
		},
		loadData : function() {
			var that = this;
			var imgUrl = '../../images/spanIoc.png';
			MALQ_CODE.titleBlock($('#step1'), '扣款信息', imgUrl);
			$('#step1').css('width', (parent.window.paymentList.config.subWidth - 100));
			MALQ_CODE.titleBlock($('#step2'), '业务经理审批', imgUrl);
			$('#step2').css('width', (parent.window.paymentList.config.subWidth - 100));
			// 通过ID查找充值数据
			var id = parent.window.paymentList.cache.id;
			JAjax(that.config.queryById, {
				"id" : id
			}, 'json', function(data) {
				if (data) {
					$("#description").text(data.description);// 扣款原因
					$("#storeId").text(data.storeId);// 账户号
					$("#tradeAmount").text(convertFen2Yuan(data.tradeAmount));// 扣款金额
					$("#applyName").text(data.applyName);// 申请人
					$("#applyTime").text(getSmpFormatDateByLong(data.applyTime, true));// 申请时间
					$("#id").val(data.id);
				}
				;
			});
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.dealList;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
		f_save : function() {
			showMask();
			var that=this;
			var id = $("#id").val();
		/*	var stepNo = "";//步骤号
			var applyStatus = "";// 申请状态
*/			var approvalResult = $("input[name='approvalResult']:checked").val();
		
			var approvalSuggest = $("#approvalSuggest").val();
			var params = {
					'model.identifier' : '2',
					'model.approvalSuggest' : approvalSuggest,
					'model.approvalResult' : approvalResult,
					'model.approvalUserId':'4301',
					'model.id':id,
			};
			JAjax(that.config.queryById, {"id":id}, 'json', function(rechargeData){
				if(rechargeData.stepNo=='4301'){
						hideMask();
						if(approvalResult=='不同意'&&(approvalSuggest==null||approvalSuggest=="")){
							parent.window.paymentList.alert('请填写处理意见', 'clew_ico_fail');
							return false;
						}else{
							JAjax(that.config.addApprovalUrl, params, 'json', function(data){
								hideMask();
								if(data.message=="操作成功"){
									parent.window.paymentList.cache.grid.loadData();
									parent.window.paymentList.alert('操作成功！', 'clew_ico_succ');
								}else{
									parent.window.paymentList.alert('处理失败', 'clew_ico_fail');
								}
								parent.window.paymentList.cache.dialog.close();
							});
						}
					}else{
						hideMask();
						parent.window.queryList.alert('此记录已审批', 'clew_ico_succ');
						parent.window.queryList.cache.dialog.close();
				}
			});
		}
	};
	$(document).ready(function() {
		window.dealList = new DealList();
	});
})();