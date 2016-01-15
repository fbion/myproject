/**
 * 支付交易订单查询js
 */
(function() {
	var QueryList = function() {
		this.init();
	};
	QueryList.prototype = {
		config : {
			queryUrl : root + '/paymentTrade/queryTradeList.action',
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null,
			forwardTradeExternalNo:null,
			tradeExternalNo : null,
			orderId : null
		},
		init : function() {
			this.render();
			this.initSelectData();
			this.loadGird();
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
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.paramList;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
		initSelectData:function(){
			// 1.初始化科目
			var w=140;
			MALQ_CODE.getSelectByCodeType($("#tradeType"), "ORDER_SUBJECT", w, null);
			$("#tradeType").ligerComboBox({width : w});
			$("#tradeType").ligerGetComboBoxManager().selectValue('');
			// 2.初始化订单状态
			MALQ_CODE.getSelectByCodeType($("#tradeStatus"), "PAY_ORDER_STATUS", w, null);
			$("#tradeStatus").ligerComboBox({width : w});
			$("#tradeStatus").ligerGetComboBoxManager().selectValue('');
			//交易同步状态
			w = 140;
			MALQ_CODE.getSelectByCodeType($("#isClearing"), "IS_CLEARING", w, null);
			$("#isClearing").ligerComboBox({width : w});
			$("#isClearing").ligerGetComboBoxManager().selectValue('');
		},
		loadGird : function() {
			var that = this;
			var options = {};
			var columns = [{
				display : '流水号',
				name : 'tradeExternalNo',
				align : 'left',
				width:'12%',
				isSort : true,
				render : function(row) {
					var str= '<a href="javascript:window.queryList.detail(\'' + row.tradeExternalNo + '\')" class="a-line" title="点我查看详细信息">'+row.tradeExternalNo+'</a>';
					return str;
				}
			},{
				display : '易宝订单号',
				name : 'payNo',
				align : 'left',
				width:'10%',
				isSort : true,
				render : function(row) {
					return row.payNo;
				}
			},{
				display : '科目',
				name : 'tradeType',
				align : 'center',
				width:'6%',
				isSort : true,
				render : function(row) {
					return MALQ_CODE.getCodeName("ORDER_SUBJECT",row.tradeType);
				}
			},  {
				display : '转出账户',
				name : 'accountNo',
				align : 'left',
				width:'12%',
				isSort : true,
				render : function(row) {
					return row.accountNo;
				}
			}, {
				display : '转入账户',
				name : 'collectMoneyAccountNo',
				align : 'left',
				width:'12%',
				isSort : true,
				render : function(row) {
					return row.collectMoneyAccountNo;
				}
			},  {
				display : '交易金额（元）',
				name : 'orderAmount',
				align : 'right',
				width:'8%',
				isSort : true,
				render : function(row){
					return convertFen2Yuan(parseInt(row.orderAmount));
				}
			},{
				display : '交易时间',
				name : 'payConfirmDate',
				align : 'center',
				width:'10%',
				isSort : true,
				render : function(row) {
						if(row.payConfirmDate!=0){
							return  getSmpFormatDateByLong(parseInt(row.payConfirmDate),true);
						}
				}
			},{
				display : '交易状态',
				name : 'tradeStatus',
				align : 'center',
				width:'8%',
				isSort : true,
				render : function(row) {
					return MALQ_CODE.getCodeName("PAY_ORDER_STATUS",row.tradeStatus);
				}
			} ,{
				display : '交易同步状态',
				name : 'isClearing',
				align : 'center',
				width:'12%',
				isSort : true,
				render : function(row) {
					return MALQ_CODE.getCodeName("IS_CLEARING",row.isClearing);
				}
			},{
				display : '外部流水',
				name : 'externalNo',
				align : 'center',
				width:'17%',
				isSort : true,
				render : function(row) {
					return row.externalNo;
				}
			},{
				display : '关联订单',
				name : 'orderId',
				align : 'center',
				width:'10%',
				isSort : true,
				render : function(row) {
					var str= '<a href="javascript:window.queryList.detailOrder(\'' + row.orderId + '\')" class="a-line" title="点我查看详细信息">'+'关联订单'+'</a>';
					return str;
				}
			}];
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['rownumbers']=true;
			options['sortName'] = 'payConfirmDate';
			options['sortOrder']='desc';
			var tradeExternalNo=self.parent.indexPage.systemToNewPermSystemName;
			if(tradeExternalNo){
				$("#tradeExternalNo").val(tradeExternalNo);
				options['parms'] = [{
					name : 'equal[tradeExternalNo]',
					value : tradeExternalNo
				}];
			}
			MALQ_UI.setGridOptions(options, that.config.queryUrl);
			$('#gridList').ligerGrid(options);
			that.cache.grid = $('#gridList').ligerGetGridManager();
			$('#gridList').css('top', '0px');
			$('#pageloading').hide();// 加载图片
		},

		// 刷新Grid内容
		refreshGrid : function() {
			var that = window.queryList;
			that.cache.grid.loadData(true);
		},
		detail : function(tradeExternalNo){
			var that = this;
			var title = '交易管理-支付交易查询-支付交易详情页';
			var url = 'paymentDetail.jsp';
			that.cache.tradeExternalNo = tradeExternalNo;
			that.cache.dialog = MALQ_UI.open(url, subHeight, subWidth, title);
		},
		detailOrder : function(orderId){

			var that = this;
			var title = '交易管理-支付交易查询-支付交易详情页';
			var url = 'orderDetail.jsp';
			that.cache.orderId = orderId;
			that.cache.dialog = MALQ_UI.open(url, subHeight, subWidth, title);
		},
		// 一般查询
		search : function() {
			var that = this;
			var parms = {};
			var isClearing=$('#isClearing').ligerGetComboBoxManager().getValue();//交易同步状态
			var tradeExternalNo=$("#tradeExternalNo").val();//内部交易流水号
			var tradeType=$('#tradeType').ligerGetComboBoxManager().getValue();//科目
			var tradeStatus=$('#tradeStatus').ligerGetComboBoxManager().getValue();//订单状态
			var insideAccountNo=$("#insideAccountNo").val();//转入账户
			var accountNo = $('#accountNo').val();//转出账户
			var accountTimeStart=date2stamp(startDate($('#accountTimeStart').val()));
			var accountTimeEnd=date2stamp(endDate($('#accountTimeEnd').val()));
			var payNo = $("#payNo").val();//payNo
			
			if(payNo!=""){
				parms['equal[payNo]'] =payNo;
			}
			
			if (tradeExternalNo != "") {//内部交易流水号
				parms['equal[tradeExternalNo]'] =tradeExternalNo;
			}
			if (isClearing != "") {//内部交易流水号
				parms['equal[isClearing]'] =isClearing;
			}
			if (accountNo != "") {//转出账户
				parms['equal[accountNo]'] =accountNo;
			}
			if (tradeType != "") {//科目
				parms['equal[tradeType]'] = tradeType;
			}
			if (tradeStatus != "") {//订单状态
				parms['equal[tradeStatus]'] = tradeStatus;
			}
			if (insideAccountNo != "") {//转入账户
				parms['equal[collectMoneyAccountNo]'] =insideAccountNo;
			}
			if (accountTimeStart) {//交易开始时间-记账时间
				parms['startwith[payConfirmDate]'] = accountTimeStart;
			}
			if (accountTimeEnd) {//交易结束时间-记账时间
				parms['endwith[payConfirmDate]'] = accountTimeEnd;
			}
			parms['orderByClause']='createTime desc';
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		// 重置查询条件
		reset : function() {
			self.parent.indexPage.systemToNewPermSystemName="";
			window.location='paymentTrade-List.jsp';
		},
		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		}
	};

	$(document).ready(function() {
		window.queryList = new QueryList();
	});
})();