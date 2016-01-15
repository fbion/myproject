/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var PaymentList = function() {
		this.init();
	};
	PaymentList.prototype = {
		config : {
			queryUrl : root + '/payment/queryPayment.action',
			delUrl : root + '/payment/deleteParam.action',
			querySimpleCodeUrl : root + '/simpleCode/findMerchant.action'
		},
		cache : {
			subPageId : null,
			grid : null,
			dialog : null,
			accordion : null,
			tab : null,
			editId : null,
			id: null,
			stepNo : null
		},
		init : function() {
			this.render();
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
			// 获取码表对应码值的下拉列表
			var w = 128;
//			MALQ_CODE.getSelectByCodeType($("#payType"), "OFFLINE_PAY_TYPE_KK", w, null);
//			$("#payType").ligerComboBox({
//				width : w
//			});
//			$("#payType").ligerGetComboBoxManager().selectValue('');
			//申请状态
			MALQ_CODE.getSelectByCodeType($("#applyStatus"), "OFFLINE_APPLY_STATUS", w, null);
			$("#applyStatus").ligerComboBox({
				width : w
			});
			$("#applyStatus").ligerGetComboBoxManager().selectValue('');
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.PaymentList;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
		loadGird : function() {
			var that = this;
			// ////////////生成表格上面的按钮菜单///////////////////////////////
			var myitems = [];
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.tyzfpt.upp.App.deductmenuAdd' ]);
			// 为添加按钮响应事件
			$(funItems).each(function() {
				myitems.push({
					line : true
				});
				if ('com.tyzfpt.upp.App.deductmenuAdd' == this.permId) {
					this['click']=that.add;
					this['icon']='add';
				}
				myitems.push(this);
			});

			// 获取操作列功能权限
			funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.tyzfpt.upp.App.deductmenuDispose','com.tyzfpt.upp.App.deductmenufinancialDispose','com.tyzfpt.upp.App.deductmenuDetail','com.tyzfpt.upp.App.deductmenuEdit']);
			var options = {};
			var columns = [ {
				display : '会员编号',
				name : 'ownerUserNo',
				align : 'center',
				width:'13%',
				isSort : false,
			},{ 
				display : '账户号',
				name : 'storeId',
				align : 'center',
				width:'15%',
				isSort : false,
				render : function(row){
					var str = row.storeId;
					// 通过权限唯一标示生成操作列按钮
					$(funItems).each(function() {
						if('com.tyzfpt.upp.App.deductmenuDetail'==this.permId/* && row.applyStatus=='4102'*/){
							str ='<a href="javascript:window.paymentList.detail(\'' + row.id + '\',\'' + row.stepNo + '\')">' + row.storeId + '</a>';
						}
						
					});
					return str;
				}
			}, {
				display : '付款人姓名',
				name : 'remitterName',
				align : 'center',
				width:'13%',
				isSort : true,
				render : function(row) {
					return row.remitterName;
				}
			}, /*{
				display : '付款类型',
				name : 'payType',
				align : 'center',
				width:'5%',
				isSort : true,
				render : function(row) {
					return MALQ_CODE.getCodeName("OFFLINE_PAY_TYPE_KK",row.payType);
				}
			},*/ {
				display : '扣款时间',
				name : 'applyTime',
				align : 'center',
				width:'12%',
				isSort : true,
				type:'none',//高级查询条件过滤掉此项
				render : function(row) {
					return getSmpFormatDateByLong(row.applyTime,true);
				}
			},/*{
				display : '资金到账时间',
				name : 'amountArriveTime',
				align : 'center',
				width:'10%',
				isSort : true,
				type : 'none',
				render : function(row){
					return getSmpFormatDateByLong(row.amountArriveTime,true);
				}
			},*/ {
				display : '扣款金额(元)',
				name : 'tradeAmount',
				align : 'right',
				isSort : true,
				width:'12%',
				type : 'none',
				render : function(row){
					return convertFen2Yuan(row.tradeAmount);
				}
			},/*{
				display : '资金确认人',
				name : 'fundsConfirmId',
				align : 'center',
				isSort : false,
				width:'8%',
				type : 'none',
				render : function(row){
					return row.fundsConfirmId;
				}
			},{
				display : '资金确认时间',
				name : 'fundsConfirmTime',
				align : 'center',
				isSort : true,
				width:'10%',
				type : 'none',
				render : function(row){
					return getSmpFormatDateByLong(row.fundsConfirmTime,true);
				}
			},*/{
				display : '申请状态',
				name : 'applyStatus',
				align : 'center',
				isSort : true,
				width:'10%',
				type : 'none',
				render : function(row){
					return MALQ_CODE.getCodeName("OFFLINE_APPLY_STATUS",row.applyStatus);
				}
			},/*{
				display : '处理步骤号',
				name : 'stepNo',
				align : 'center',
				isSort : true,
				width:'8%',
				type : 'none',
				render : function(row){
					return MALQ_CODE.getCodeName("OFFLINE_APPLY_STEP",row.stepNo);
					}
			},*/{
				display : '当前处理人',
				name : 'approvalPersonName',
				align : 'center',
				isSort : false,
				width:'13%',
				type : 'none',
				render : function(row){
					return row.approvalPersonName;
				}
			},{
				display : '操作',
				align : 'center',
				isSort : false,
				width:'11%',
				render : function(row) {
					var str = '';
					// 通过权限唯一标示生成操作列按钮
					$(funItems).each(function() {
						if ('com.tyzfpt.upp.App.deductmenuDispose' == this.permId && row.stepNo=='4301') {
							str += '<a href="javascript:window.paymentList.dispose(\'' + row.id + '\',\'' + row.stepNo + '\')">处理</a>&nbsp;&nbsp;';
						}
						if('com.tyzfpt.upp.App.deductmenufinancialDispose'==this.permId && row.stepNo=='4302'){
							str += '<a href="javascript:window.paymentList.financialDispose(\'' + row.id + '\',\'' + row.stepNo + '\')">财务处理</a>&nbsp;&nbsp;';
						}
						/*if('com.tyzfpt.upp.App.deductmenuDetail'==this.permId && row.applyStatus=='4102'){
							str +='<a href="javascript:window.paymentList.detail(\'' + row.id + '\',\'' + row.stepNo + '\')">详情</a>&nbsp;&nbsp;';
						}*/
//						if ('com.tyzfpt.upp.App.deductmenuEdit' == this.permId && row.applyStatus == '4103') {
//							str += '<a href="javascript:window.paymentList.modeify(\'' + row.id + '\',\'' + row.approvalPersonName + '\')">修改</a>&nbsp;&nbsp;';
//						}
					});
					return str;
				}
			} ];

			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'name';
			options['sortName'] = 'applyTime';
			options['sortOrder']='desc';			
			options['toolbar'] = {
				items : myitems
			};// 表格上面菜单
			options['parms'] =[{
				name:'equal[tradeType]',
				value:'4002'
			}];
			MALQ_UI.setGridOptions(options, that.config.queryUrl);
			$('#paymentGridList').ligerGrid(options);
			that.cache.grid = $('#paymentGridList').ligerGetGridManager();
			$('#paymentGridList').css('top', '0px');
			$('#pageloading').hide();// 加载图片
		},

		// 刷新Grid内容
		refreshGrid : function() {
			var that = window.paymentList;
			that.cache.grid.loadData(true);
		},
		// 一般查询
		search : function() {
			var that = this;
			var parms = that.cache.grid.get('parms');
			var tem = [];
			$(parms).each(function() {
				if (this.name == 'like[storeId]' ||this.name == 'like[remitterName]'
					||this.name == 'equal[tradeAmount]'||this.name == 'like[fundsConfirmId]'||this.name == 'equal[applyStatus]'
					||this.name == 'startwith[applyTime]'||this.name == 'endwith[applyTime]'||this.name == 'startwith[amountArriveTime]'||this.name == 'endwith[amountArriveTime]'
				)
					return true;
				tem.push(this);
			});
			parms = tem;
			var storeId = $('#storeId').val();
//			var payType = $("#payType").ligerGetComboBoxManager().getValue();
			var remitterName = $('#remitterName').val();
			var tradeAmount = $("#tradeAmount").val();//扣款金额
			var fundsConfirmId = $("#fundsConfirmId").val();//扣款确认人
			var applyStatus = $("#applyStatus").ligerGetComboBoxManager().getValue();//申请状态
			var applyTimeStart = date2stamp(startDate($("#applyTimeStart").val()));//付款开始时间
			var applyTimeEnd = date2stamp(endDate($("#applyTimeEnd").val()));//付款结束时间
			var amountArriveTimeStart = date2stamp(startDate($("#amountArriveTimeStart").val()));//资金到账开始时间
			var amountArriveTimeEnd = date2stamp(endDate($("#amountArriveTimeEnd").val()));//资金到账结束时间
			if (storeId) {
				parms.push({
					name : 'like[storeId]',
					value : '%'+storeId+'%'
				});
			}
//			if (payType) {
//				parms.push({
//					name : 'equal[payType]',
//					value : payType
//				});
//			}
			if(remitterName){
				parms.push({
					name : 'like[remitterName]',
					value : '%'+remitterName+'%' 
				});
			}
			if(tradeAmount){
				parms.push({
					name : 'equal[tradeAmount]',
					value :  tradeAmount*100  
				});
			}
			if(fundsConfirmId){
				parms.push({
					name : 'like[fundsConfirmId]',
					value :  '%' + fundsConfirmId + '%' 
				});
			}
			if(applyStatus){
				parms.push({
					name : 'equal[applyStatus]',
					value :  applyStatus 
				});
			}
			if(applyTimeStart){
				parms.push({
					name : 'startwith[applyTime]',
					value :   applyTimeStart  
				});
			}
			if(applyTimeEnd){
				parms.push({
					name : 'endwith[applyTime]',
					value :   applyTimeEnd  
				});
			}
			if(amountArriveTimeStart){
				parms.push({
					name : 'startwith[amountArriveTime]',
					value :   amountArriveTimeStart  
				});
			}
			if(amountArriveTimeEnd){
				parms.push({
					name : 'endwith[amountArriveTime]',
					value :   amountArriveTimeEnd  
				});
			}
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		
		},
		// 重置页面
		reset : function() {
			window.location='payment-list.jsp';
		},
		// 处理业务审批
		dispose : function(id,stepNo) {
			var that = window.paymentList;
			that.cache.id=id;
			that.cache.stepNo = stepNo;
			var title = "线下流程管理-扣款-业务经理审批";
			var url = "payment-deal.jsp";
			that.cache.dialog = MALQ_UI.open_button(url, subHeight-50, subWidth-100, that.dealSuccess, that.cancel, title);
		},
		dealSuccess : function(item, dialog) {
			dialog.frame.window.dealList.f_save();
		},
		//财务审批
		financialDispose : function(id,stepNo) {
			var that = window.paymentList;
			that.cache.id=id;
			that.cache.stepNo = stepNo;
			var title = "线下流程管理-扣款-财务经理审批";
			var url = "payment-finance-deal.jsp";
			that.cache.dialog = MALQ_UI.open_button(url, subHeight-50, subWidth-100, that.financeSuccess, that.cancel, title);
		},
		financeSuccess : function(item, dialog) {
			dialog.frame.window.finaceDeal.f_save();
		},
		//详情
		detail : function(id,stepNo) {
			var that = window.paymentList;
			that.cache.id=id;
			that.cache.stepNo = stepNo;
			var title = "线下流程管理-扣款申请审批详情";
			var url = "payment-detail.jsp";
			that.cache.dialog = MALQ_UI.open_button(url, subHeight-50, subWidth-100, null, that.cancel, title);
		},
		// 增加
		add : function() {
			var that = window.paymentList;
			that.cache.subPageId = 'add';
			var title = '扣款审核申请-新增';
			var url = 'payment-add.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight-50, subWidth-100, that.addSuccess, that.cancel, title);
		},
		// 在修改页面显示要修改的记录的信息
		modeify : function(id,dealName) {
			var userLogin_name=self.parent.indexPage.userLogin_name;
			if($.trim(userLogin_name)!=$.trim(dealName)){
				$.ligerDialog.alert('您没有权限操作此功能！', '提示', 'error');
				return;
			}
			var that = window.paymentList;
			that.cache.subPageId = 'edit';
			var title = '扣款审核申请-修改';
			that.cache.id = id;
			var url = 'payment-add.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight-50, subWidth-100, that.addSuccess, that.cancel, title);
		},
		addSuccess:function(item, dialog) {
			dialog.frame.window.paymentAdd.f_save();
		},
		cancel : function(item, dialog) {
			dialog.close();
		},
		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		}
	};
	$(document).ready(function() {
		window.paymentList = new PaymentList();
	});
})();