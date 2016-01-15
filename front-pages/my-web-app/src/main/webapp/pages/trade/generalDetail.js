(function() {
	var GeneralDetail = function() {
		this.init();
	};
	GeneralDetail.prototype = {
		config : {
//			queryObjectUrl : root + '/generaltrade/queryList.action', 
			queryObjectUrl : root + '/generaltrade/queryById.action',
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
			MALQ_CODE.titleBlock($('#step1'), '账户管理-普通账户流水-流水详细信息', imgUrl);
			$('#step1').css('width',(parent.window.queryList.config.subWidth-100));
			var tradeExternalNo = parent.window.queryList.cache.tradeExternalNo;
			var parms = {};
			parms['equal[tradeExternalNo]'] =tradeExternalNo;
			JAjax(that.config.queryObjectUrl, parms , 'json', function(data) {
				if (data) {
//					data = data.data;
					var obj = data[0];
					$("#insideExternalNo").text(obj.insideExternalNo);//内部账户流水号
					
					var bookaccountType = obj.bookAccountType;
					bookaccountType = bookaccountType.toUpperCase();
					bookaccountType = MALQ_CODE.getCodeName("BOOK_ACCOUNT_TYPE",bookaccountType);
					$("#bookaccountType").text(bookaccountType);//记账类型
					
					$("#insideAccountNo").text(obj.insideAccountNo);//内部账号
					
					var accountSubject = obj.orderTypeLuc;
					accountSubject = accountSubject.toUpperCase();
					accountSubject = MALQ_CODE.getCodeName("ORDER_SUBJECT",accountSubject);
					$("#accountSubject").text(accountSubject);//科目
					
					$("#accountTime").text(getSmpFormatDateByLong(parseInt(obj.accountTime),true));//记账时间
					
//					$("#accountMoney").text(convertFen2Yuan(obj.bookAccountMoney));//交易金额
					$("#accountMoney").text(obj.bookAccountMoney);//交易金额
					
					$("#accountDate").text(getSmpFormatDateByLong(parseInt(obj.bookAccountTime), true));//记账日期
					
					$("#tradeExternalNo").text(obj.tradeExternalNo);//内部交易流水号
					
					$("#externalAccountNo").text(obj.externalAccountNo);//外部账户对应账号标识
					
					$("#operatorId").text(obj.operatorId);//操作人标识
					
					$("#accountSubareaTime").text(getSmpFormatDateByLong(obj.accountSubareaTime,true));//记账分区时间
					
//					$("#storeCode").text(obj.storeCode);//商户编号
					$("#storeCode").text(obj.ownerLoginName);//商户编号
					
//					$("#currentMoney").text(convertFen2Yuan(obj.bookCurrentMoney));//交易后账户余额
					$("#currentMoney").text(obj.bookCurrentMoney);//交易后账户余额
				}
			}, function() {JSWK.clewBox('查询详情失败', 'clew_ico_fail', failShowTime);}, true);
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.generalDetail;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
	};

	$(document).ready(function() {
		window.generalDetail = new GeneralDetail();
	});
})();