/**
 * 交易订单查询js
 */
(function() {
	var QueryList = function() {
		this.init();
	};
	QueryList.prototype = {
		config : {
			queryUrl : root + '/order/queryList.action',
			exportExcelAllUrl : root + '/order/exportExcelAll.action'
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null,
			forwardTradeExternalNo:null,
			orderNo : null
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
			MALQ_CODE.getSelectByCodeType($("#orderType"), "ORDER_SUBJECT", w, null);
			$("#orderType").ligerComboBox({width : w});
			$("#orderType").ligerGetComboBoxManager().selectValue('');
			// 2.初始化订单状态
			MALQ_CODE.getSelectByCodeType($("#orderStatus"), "PAY_ORDER_STATUS", w, null);
			$("#orderStatus").ligerComboBox({width : w});
			$("#orderStatus").ligerGetComboBoxManager().selectValue('');
		},
		loadGird : function() {
			var that = this;
			// ////////////生成表格上面的按钮菜单///////////////////////////////
			var myitems = [];
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.sinoiov.upp.webApp.orderInfo.allExport' ]);
			$(funItems).each(function() {
				myitems.push({
					line : true
				});
				var item = {};
				var click = null;
				if ('com.sinoiov.upp.webApp.orderInfo.allExport' == this.permId) {
					click = that.exportAllExcel;
				}
				item['text'] = this.text;
				item['click'] = click;
				item['icon'] = this.icon;
				myitems.push(item);
			});
			funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.sinoiov.upp.webApp.orderInfo.export', 'com.sinoiov.upp.webApp.orderInfo.accountUnfreeze']);
			// ////////////生成表格上面的按钮菜单////////////////////////////	
			var options = {};
			var columns = [{
				display : '订单号',
				name : 'orderNo',
				align : 'center',
				width:'13%',
				isSort : true,
				render : function(row) {
					var str= '<a href="javascript:window.queryList.detail(\'' + row.orderNo + '\')" class="a-line" title="点我查看详细信息">'+row.orderNo+'</a>';
					return str;
				}
			},{
				display : '业务订单号',
				name : 'workOrderNo',
				align : 'center',
				width:'9%',
				isSort : true,
				render : function(row) {
					return row.workOrderNo;
				}
			},{
				display : '会员编号',
				name : 'ownerUserNo',
				align : 'center',
				width:'10%',
				isSort : true,
			},{
				display : '流水号',
				name : 'tradeExternalNo',
				align : 'center',
				width:'12%',
				isSort : true,
				render : function(row) {
					return row.tradeExternalNo;
				}
			},{
				display : '科目',
				name : 'orderSubject',
				align : 'center',
				width:'8%',
				isSort : true,
				render : function(row) {
					return MALQ_CODE.getCodeName("ORDER_SUBJECT",row.orderSubject);
				}
			},  {
				display : '账户号',
				name : 'accountNo',
				align : 'center',
				width:'10%',
				isSort : true,
				render : function(row) {
					return row.accountNo;
				}
			}
			/*, 
			{
				display : '转入账户',
				name : 'collectMoneyAccountNo',
				align : 'center',
				width:'10%',
				isSort : true,
				render : function(row) {
					return row.collectMoneyAccountNo;
				}
			}, {
				display : '账户所属（转出）',
				name : 'inOwnerLoginName',
				align : 'left',
				width:'12%',
				isSort : true,
				render : function(row) {
					return row.inOwnerLoginName;
				}
			}, {
				display : '账户所属（转入）',
				name : 'outOwnerLoginName',
				align : 'left',
				width:'12%',
				isSort : true,
				render : function(row) {
					return row.outOwnerLoginName;
				}
			}*/, {
				display : '交易金额（元）',
				name : 'orderAmount',
				align : 'center',
				width:'5%',
				isSort : true
			},{
				display : '交易时间',
				name : 'createTime',
				align : 'center',
				width:'10%',
				isSort : true,
				render : function(row) {
					return getSmpFormatDateByLong(parseInt(row.createTime), true);
//						return row.createTime;
				}
			},{
				display : '订单状态',
				name : 'orderStatus',
				align : 'center',
				width:'5%',
				isSort : true,
				render : function(row) {
					return MALQ_CODE.getCodeName("PAY_ORDER_STATUS",row.orderStatus);
				}
			},{
				display : '操作',
				align : 'left',
				width:'15%',
				isSort : false,
				render : function(row) {
					var str = '';
					$(funItems).each(function() {
						if (this.permId == 'com.sinoiov.upp.webApp.orderInfo.export'){
							str += '<a href="javascript:window.queryList.exportExcel(\'' + row.orderNo + '\')\">导出</a>&nbsp;&nbsp;';
						}
						if (this.permId == 'com.sinoiov.upp.webApp.orderInfo.accountUnfreeze' && row.orderType=='3'){
							str += '<a href="javascript:window.queryList.unfreeze(\'' + row.accountNo + '\',\''+row.workOrderNo+'\',\''+row.tradeExternalNo+'\')">解冻</a>&nbsp;&nbsp;';
						}
					});
					
					return str;
				}
			} ];
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['rownumbers']=true;
			options['sortName'] = 'createTime';
			options['sortOrder']='desc';
			options['toolbar'] = {items : myitems};
			var tradeExternalNo=self.parent.indexPage.systemToNewPermSystemName;
			if(tradeExternalNo){
				$("#tradeExternalNo").val(tradeExternalNo);
				options['parms'] = [{
					name : 'equal[tradeExternalNo]',
					value : tradeExternalNo
				}];
				self.parent.indexPage.systemToNewPermSystemName=null;
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
		// 一般查询
		search : function() {
			var that = this;
			var parms = {};
			var orderNo=$('#orderNo').val();//订单号
			var accountNo = $('#accountNo_q').val();//转出账户
			var workOrderNo = $('#workOrderNo_q').val();//业务订单号
			var tradeExternalNo=$("#tradeExternalNo_q").val();//内部交易流水号
			var orderSubject=$('#orderType').ligerGetComboBoxManager().getValue();//科目
			var orderStatus=$('#orderStatus').ligerGetComboBoxManager().getValue();//订单状态
			//var insideAccountNo=$("#insideAccountNo").val();//转入账户
			var ownerUserNo = $('#ownerUserNo').val();//
			var accountTimeStart=date2stamp(startDate($('#accountTimeStart').val()));
			var accountTimeEnd=date2stamp(endDate($('#accountTimeEnd').val()));
			if (tradeExternalNo != "") {//内部交易流水号
				parms['equal[tradeExternalNo]'] =tradeExternalNo;
			}
			if (accountNo) {//转出账户
				parms['equal[accountNo]'] = accountNo;
			}
			if (orderNo) {//订单号
				parms['equal[orderNo]'] =orderNo;
			}
			if (orderSubject) {//科目
				parms['equal[orderSubject]'] = orderSubject;
			}
			if (orderStatus) {//订单状态
				parms['equal[orderStatus]'] = orderStatus;
			}
			//if (insideAccountNo != "") {//转入账户
			//	parms['equal[collectMoneyAccountNo]'] = insideAccountNo;
			//}
			if (workOrderNo) {//业务订单号
				parms['equal[workOrderNo]'] = workOrderNo;
			}
			if (ownerUserNo) {//会员编号
				parms['equal[ownerUserNo]'] = ownerUserNo;
			}
			if (accountTimeStart) {//交易开始时间-记账时间
				parms['startwith[createTime]'] = accountTimeStart;
			}
			if (accountTimeEnd) {//交易结束时间-记账时间
				parms['endwith[createTime]'] = accountTimeEnd;
			}
			//alert(JSON.stringify(parms));
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		exportAllExcel : function(){
			var that = window.queryList;
			var parms = {};
			var orderNo=$('#orderNo').val();//订单号
			var accountNo = $('#accountNo_q').val();//转出账户
			var workOrderNo = $('#workOrderNo_q').val();//业务订单号
			var tradeExternalNo=$("#tradeExternalNo_q").val();//内部交易流水号
			var orderSubject=$('#orderType').ligerGetComboBoxManager().getValue();//科目
			var orderStatus=$('#orderStatus').ligerGetComboBoxManager().getValue();//订单状态
			//var insideAccountNo=$("#insideAccountNo").val();//转入账户
			var ownerUserNo = $('#ownerUserNo').val();//
			var accountTimeStart=date2stamp(startDate($('#accountTimeStart').val()));
			var accountTimeEnd=date2stamp(endDate($('#accountTimeEnd').val()));
			if (tradeExternalNo) {//内部交易流水号
				parms['equal[tradeExternalNo]'] =tradeExternalNo;
			}
			if (accountNo) {//转出账户
				parms['equal[accountNo]'] = accountNo;
			}
			if (orderNo) {//订单号
				parms['equal[orderNo]'] =orderNo;
			}
			if (orderSubject) {//科目
				parms['equal[orderSubject]'] = orderSubject;
			}
			if (orderStatus) {//订单状态
				parms['equal[orderStatus]'] = orderStatus;
			}
			
			if (workOrderNo) {//业务订单号
				parms['equal[workOrderNo]'] = workOrderNo;
			}
			if (accountTimeStart) {//交易开始时间-记账时间
				parms['startwith[createTime]'] = accountTimeStart;
			}
			if (accountTimeEnd) {//交易结束时间-记账时间
				parms['endwith[createTime]'] = accountTimeEnd;
			}
			if (ownerUserNo) {//会员编号
				parms['equal[ownerUserNo]'] = ownerUserNo;
			}
			
			JAjax(that.config.exportExcelAllUrl, parms , 'json', function(data){}, null, true);
			
			JSWK.clewBox('导出交易订单列表已提交，请到下载任务列表中下载!', 'clew_ico_succ', failShowTime);
		},
		//详情
		detail : function(orderNo){
			var that = this;
			that.cache.subPageId = 'detail';
			var title = '交易管理-交易订单查询-订单详情页';
			var url = 'orderDetail.jsp';
			that.cache.orderNo = orderNo;
			that.cache.dialog = MALQ_UI.open(url, subHeight, subWidth, title);
		},
		// 重置查询条件
		reset : function() {
			self.parent.indexPage.systemToNewPermSystemName="";
			window.location='order-List.jsp';
		},
		// 导出
		exportExcel : function(id) {
			if(''!=id){
				window.location = root + "/order/exportExcel.action?orderNo="+id;;
			}else{
				alert("操作失败");
			}

		},
		//解冻
		unfreeze : function(accountNo, workOrderNo, tradeExternalNo){
			var that = window.queryList;
			var title = '解冻金额';
			var url = root+'/pages/manageUser/unfreezeAmount.jsp';
			$('#accountNo').val(accountNo);
			$('#workOrderNo').val(workOrderNo);
			$('#tradeExternalNo').val(tradeExternalNo);
			that.cache.dialog = MALQ_UI.open_button(url, subHeight,subWidth,
					function(item,dialog){
						dialog.frame.window.unfreezeAmount.f_save();
					},
					function(item, dialog) {
						dialog.close();
					},
					title);
		},
		
		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		}
	};

	$(document).ready(function() {
		window.queryList = new QueryList();
	});
})();