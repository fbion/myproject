(function() {
	var PaymentDetail = function() {
		this.init();
	};
	PaymentDetail.prototype = {
		config : {
			queryObjectUrl : root + '/paymentTrade/queryTradeList.action',
			subHeight : subHeight,
			subWidth : subWidth,
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null
		},
		init : function() {
			this.render();
			this.initData();
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
		initData : function() {
			var that = this;
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '交易管理-交易订单列表-订单详细信息', imgUrl);
			$('#step1').css('width',(parent.window.queryList.config.subWidth-100));
			var tradeExternalNo = parent.window.queryList.cache.tradeExternalNo;
			var parms = {};
			parms['equal[tradeExternalNo]'] =tradeExternalNo;
			JAjax(that.config.queryObjectUrl, parms, 'json', function(data) {
				if (data) {
					var list = data.data;
					var obj = list[0];
					$("#createSubareaTime").text(getSmpFormatDateByLong(obj.createSubareaTime,true));
					
					$("#createTime").text(getSmpFormatDateByLong(obj.createTime,true));
					
					$("#payConfirmDate").text(getSmpFormatDateByLong(obj.payConfirmDate,true));
					
					var orderAmount = convertFen2Yuan(parseInt(obj.orderAmount));
					$("#orderAmount").text(orderAmount);
					
					var payPoundScale = convertFen2Yuan(parseInt(obj.payPoundScale));
					$("#payPoundScale").text(payPoundScale);
					
					var serviceProviderCode =  MALQ_CODE.getCodeName("PAYMENT_PLATFORM",obj.serviceProviderCode);
					$("#serviceProviderCode").text(serviceProviderCode);
					
					$("#payChannel").text(obj.payChannel);
					
					$("#externalNo").text(obj.externalNo);
					
					$("#paySettleStatus").text(obj.paySettleStatus);
					
					$("#payCheckStatus").text(obj.payCheckStatus);
					
					var tradeStatus =  MALQ_CODE.getCodeName("PAY_TRADE_STATUS",obj.tradeStatus);
					$("#tradeStatus").text(tradeStatus);
					
					$("#payId").text(obj.payId);
					
					$("#bankCode").text(obj.bankCode);
					
					$("#bankName").text(obj.bankName);
					
					$("#paymentType").text(obj.paymentType);
					
					$("#tradeExternalNo").text(obj.tradeExternalNo);
					
					var tradeType =  MALQ_CODE.getCodeName("PAY_TRADE_TYPE",obj.tradeType);
					$("#tradeType").text(tradeType);
					
					$("#bookAccountStatus").text(obj.bookAccountStatus);
					
					$("#payOrderId").text(obj.payOrderId);
					
					$("#cardType").text(obj.cardType);
					
					$("#accountNo").text(obj.accountNo);
					
					$("#collectMoneyAccountNo").text(obj.collectMoneyAccountNo);
					
					$("#bankCardNo").text(obj.bankCardNo);
					
					$("#batchInternalNo").text(obj.batchInternalNo);
					
					$("#clentType").text(obj.clentType);
				}
			}, function() {JSWK.clewBox('查询详情失败', 'clew_ico_fail', failShowTime);}, true);
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.PaymentDetail;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
	};

	$(document).ready(function() {
		window.paymentDetail = new PaymentDetail();
	});
})();