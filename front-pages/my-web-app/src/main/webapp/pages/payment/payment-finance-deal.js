(function() {
	var FinaceDeal = function() {
		this.init();
	};
	FinaceDeal.prototype = {
		config : {
			queryById : root + '/payment/queryById.action',
			queryDealById : root + '/approveInfo/findByRechargeApplyId.action',
			addApprovalUrl : root + '/recharge/addPaymentApproval.action'
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
			MALQ_CODE.titleBlock($('#step3'), '财务经理审批', imgUrl);
			$('#step3').css('width', (parent.window.paymentList.config.subWidth - 100));
			// 通过ID查找充值数据
			var id = parent.window.paymentList.cache.id;
			JAjax(that.config.queryById, {
				"id" : id
			}, 'json', function(data) {
				if (data) {
					$("#remitterName").text(data.remitterName);// 付款人名称
					$("#description").text(data.description);// 扣款原因
					//$("#amountArriveTime").text(getSmpFormatDateByLong(data.amountArriveTime, true));// 资金到账时间
					$("#storeId").text(data.storeId);// 账户号
					$("#tradeAmount").text(convertFen2Yuan(data.tradeAmount));// 扣款金额
					$("#approvalPersonName").text(data.approvalPersonName);// 申请人
					$("#applyTime").text(getSmpFormatDateByLong(data.applyTime, true));// 申请时间
					$("#id").val(data.id);
					// 获取业务经理审批结果
					JAjax(that.config.queryDealById, {
						"id" : id,
						"approvalUserId" : "4301"
					}, 'json', function(dealData) {
						var dealDate = dealData[0];
//						var temdd = data.amountArriveTime?data.operTime:new Date().getTime(); 
						var temdd = data.operTime?data.operTime:new Date().getTime();
						$("#dealAmountArriveTime").text(getSmpFormatDateByLong(parseInt(temdd), true));// 确认时间
						$("#dealApprovalPersonName").text(dealDate.approvalPersonName);// 审批人名称
						$("#approvalSuggest").text(dealDate.approvalSuggest);// 审批意见
						if(dealDate.approvalResult == '1'){
							$("#approvalResult").text("同意");
						}else if(dealDate.approvalResult == '-1'){
							$("#approvalResult").text("不同意");
						}else{
							$("#approvalResult").text(dealDate.approvalResult );//审批结果
						}
					});
				};
			});
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.finaceDeal;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
		f_save : function() {
			showMask();
			var that = window.finaceDeal;
			var id = $("#id").val();
		/*	var stepNo = "";// 步骤号
			var applyStatus = "";// 申请状态 4101 已审核
*/			var approvalResult = $("input[name='financeApprovalSuggest']:checked").val();// 是否同意
			
			var approvalSuggest = $("#financeApprovalSuggest").val();// 审核意见
			var params = {
				'model.identifier' : '1',
				'model.approvalSuggest' : approvalSuggest,
				'model.approvalResult' : approvalResult,
				'model.approvalUserId' : '4302',
				'model.id' : id,
			
			};
			JAjax(root + '/payment/queryById.action', {
				"id" : id
			}, 'json', function(finceData) {
				if (finceData.stepNo == '4302') {
					if (approvalResult == '不同意' && (approvalSuggest == null || approvalSuggest == "")) {
						hideMask();
						parent.window.paymentList.alert('请填写处理意见', 'clew_ico_fail');
						return false;
					} else {
						JAjax(that.config.addApprovalUrl, params, 'json', function(data) {
							hideMask();
							if (data.message == "操作成功") {
								parent.window.paymentList.cache.grid.loadData();
								parent.window.paymentList.alert('操作成功！', 'clew_ico_succ');
							}else {
								parent.window.paymentList.alert('处理失败', 'clew_ico_fail');
							}
							parent.window.paymentList.cache.dialog.close();
						});
					}
				} else {
					hideMask();
					parent.window.paymentList.alert('不能重复审批！', 'clew_ico_succ');
					parent.window.paymentList.cache.dialog.close();
				}
			});
		}
	};
	$(document).ready(function() {
		window.finaceDeal = new FinaceDeal();
	});
})();