(function() {
	var QueryList = function() {
		this.init();
	};
	QueryList.prototype = {
		config : {
			//通过ID查找充值明细
			queryById :root + '/payment/queryById.action',
			//通过ID查找审批结果
			queryDealById :root + '/approveInfo/findByRechargeApplyId.action'
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null
		},
		init : function() {
			this.render();
			this.initTitleBlock();
			this.initPageData();
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
		initTitleBlock:function(){
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '扣款申请信息', imgUrl);
			$('#step1').css('width',(parent.window.paymentList.config.subWidth-100));
			MALQ_CODE.titleBlock($('#step2'), '业务经理-审批', imgUrl);
			$('#step2').css('width',(parent.window.paymentList.config.subWidth-100));
			MALQ_CODE.titleBlock($('#step3'), '财务经理-审批', imgUrl);
			$('#step3').css('width',(parent.window.paymentList.config.subWidth-100));
		},
		initPageData:function(){
			var that = this;
			var id = parent.window.paymentList.cache.id;
			JAjax(that.config.queryById, {"id":id}, 'json', function(data){
				if(data){
					$("#remitterName").text(data.remitterName);
             		$("#description").text(data.description);
             		var amountArriveTime = data.amountArriveTime;
             		if(amountArriveTime){
             			$("#amountArriveTime").text(getSmpFormatDateByLong(data.amountArriveTime,true));
             		}
             		var fundsConfirmTime = data.fundsConfirmTime;
             		if(fundsConfirmTime){
             			$("#fundsConfirmTime").text(getSmpFormatDateByLong(data.fundsConfirmTime));
             		}
             		var payType = MALQ_CODE.getCodeName("OFFLINE_PAY_TYPE_KK",data.payType);
             		$("#payType").text(payType);
             		
             		$("#storeId").text(data.storeId);
             		$("#tradeAmount").text(convertFen2Yuan(data.tradeAmount));
             		$("#applyName").text(data.applyName);
             		$("#applyTime").text(getSmpFormatDateByLong(data.applyTime,true));
             		
					JAjax(that.config.queryDealById, {"id":id,"approvalUserId":"4301"}, 'json', function(dealData){
						//业务经理审批
						var dealDate = dealData[0];
						if(dealDate){
						//业务经理审批
						$("#dealApprovalPersonName").text(dealDate.approvalPersonName);//审批人名称
						$("#dealOperTime").text(getSmpFormatDateByLong(parseInt(dealDate.operTime), true));//审批操作日期
						$("#dealApprovalSuggest").text(dealDate.approvalSuggest);//审批意见
						if(dealDate.approvalResult == '1'){
							$("#dealApprovalResult").text("同意");
						}else if(dealDate.approvalResult == '-1'){
							$("#dealApprovalResult").text("不同意");
						}else{
							$("#dealApprovalResult").text(dealDate.approvalResult );//审批结果
						}
						}
					});
					//获取财务审批结果
					JAjax(that.config.queryDealById, {"id":id,"approvalUserId":"4302"}, 'json', function(dealFinanceData){
						var financeDate = dealFinanceData[0];
						//财务审批
						if(financeDate){
						$("#financeApprovalPersonName").text(financeDate.approvalPersonName);//审批人名称
						$("#financeOperTime").text(getSmpFormatDateByLong(parseInt(financeDate.operTime), true));//审批操作日期
						$("#financeApprovalSuggest").text(financeDate.approvalSuggest);//审批意见
						if(financeDate.approvalResult == '1'){
							$("#financeApprovalResult").text("同意");
						}else if(financeDate.approvalResult == '-1'){
							$("#financeApprovalResult").text("不同意");
						}else{
							$("#financeApprovalResult").text(financeDate.approvalResult );//审批结果
						}
						$("#financialTitleTr").show();
						$("#financialAgreeTr").show();
						$("#financialSuggestTr").show();
						}
					});
				};
			});
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.queryList;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		}
	};
	$(document).ready(function() {
		window.queryList = new QueryList();
	});
})();