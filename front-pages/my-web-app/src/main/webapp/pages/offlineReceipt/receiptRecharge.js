(function() {
	var ReceiptList = function() {
		this.init();
	};
	ReceiptList.prototype = {
		config : {
			queryUrl : root + '/receipt/queryList.action',
			addApprovalUrl : root +'/receipt/addApproval.action',
			covertMoney:root+'/recharge/converamount.action',
			findUser : root + '/receipt/findUser.action',
			exportExcelUrl : root + '/receipt/exportExcelAll.action'
		},
		cache : {
			subPageId : null,
			grid : null,
			dialog : null,
			accordion : null,
			tab : null,
			editId : null,
			id : null,
			imgUrl : null,
			dialogImg : null,
			userName : null
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
			// 1.初始化付款人开户行combobox信息
			var w = '133';
			// 2.初始化申请状态combobox
			MALQ_CODE.getSelectByCodeType($("#applyStatus"), "OFF_RECIPT_STATUS", w, null);
			$("#applyStatus").ligerComboBox({width : w});
			$("#applyStatus").ligerGetComboBoxManager().selectValue('');
		/*	//3.初始化付款方式
			MALQ_CODE.getSelectByCodeType($("#payType"), "OFFLINE_PAY_TYPE", w, null);
			$("#payType").ligerComboBox({width : w});
			$("#payType").ligerGetComboBoxManager().selectValue('');*/
			
			//付款方式
//			MALQ_CODE.getSelectByCodeType($("#payWay"), "OFFLINE_PAY_TYPE", w, null);
//			$("#payWay").ligerComboBox({width : w});
//			$("#payWay").ligerGetComboBoxManager().selectValue('');			
			
			//来款渠道
			MALQ_CODE.getSelectByCodeType($("#incomeChannel"), "VOUCHER_CHANNEL", w, null);
			$("#incomeChannel").ligerComboBox({width : w});
			$("#incomeChannel").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "", w, null);
			$("#incomeChannelSub").ligerComboBox({width : w});
			$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');
			//所属地区 
			MALQ_CODE.getCodeSelect_p($("#applyPersonPost"),w,null);
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
		loadGird : function() {
			var that = this;
			// ////////////生成表格上面的按钮菜单///////////////////////////////
			var myitems = [];
			
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, ['com.tyzfpt.upp.App.rechargeReceiptAdd','com.tyzfpt.upp.App.rechargeReceiptExport']);
			
			// 为添加按钮响应事件
			$(funItems).each(function() {
				var click = null;
				myitems.push({
					line : true
				});
				if ('com.tyzfpt.upp.App.rechargeReceiptAdd' == this.permId) {
					this['click']=that.add;
					this['icon']='add';
					myitems.push(this);
				}
				if('com.tyzfpt.upp.App.rechargeReceiptExport' == this.permId){
					click = that.exportAll;
					this['click']=click;
					this['icon']=this.icon;
					myitems.push(this);
				}
				
				
			});
			// ////////////生成表格上面的按钮菜单////////////////////////////
			funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, ['com.tyzfpt.upp.App.rechargeReceiptDeal','com.tyzfpt.upp.App.rechargeReceiptDetail','com.tyzfpt.upp.App.rechargeReceiptDealFinal']);
			var options = {};
			var columns = [ {
				display : '申请流水号',
				name : 'applyNo',
				width:'12%',
				align : 'center',
				isSort : false,
//				render : function(row){
//					var str = row.applyNo;
//					$(funItems).each(function() {
//						if('com.tyzfpt.upp.App.rechargeReceiptDetail'==this.permId /*&& (row.applyStatus=='4102' || row.applyStatus == '4103')*/ ){
//							str ='<a href="javascript:window.receiptList.detail(\'' + row.id + '\')">'row.applyNo'</a>';
//						};
//					});
//					return str;
//				}
				render : function(row){
					/*var str= '<a href="javascript:window.queryList.detail(\'' + row.id + '\')" class="a-line" title="点我查看详细信息">'+row.storeId+'</a>';
					return str;*/
					var str = row.applyNo;
					// 通过权限唯一标示生成操作列按钮
					$(funItems).each(function() {
						if('com.tyzfpt.upp.App.rechargeReceiptDetail'==this.permId /*&& (row.applyStatus=='4102' || row.applyStatus == '4103')*/ ){
							str ='<a href="javascript:window.receiptList.detail(\'' + row.id + '\')">' + row.applyNo + '</a>';
						}
						
					});
					return str ;
				}
			},{
				display : '业务员',
				name : 'applyName',
				width:'7%',
				align : 'center',
				isSort : false
			},{
				display : '业务员所属地区',
				name : 'applyPersonPost',
				width:'8%',
				align : 'center',
				isSort : false,
				render : function(row){
					return MALQ_CODE.getZoneNameByCode(row.applyPersonPost);
				}
			},{
				display : '来款渠道',
				name : 'channel',
				width:'5%',
				align : 'center',
				isSort : false,
				render : function(row){
					return MALQ_CODE.getCodeName("VOUCHER_CHANNEL",row.channel);
					}
			},{
				display : '',
				name : 'subChannel',
				width : '5%',
				align : 'center',
				isSort : false,
				render : function(row){
						var str = "";
						if(row.channel!=null && row.channel!=""){
							if(row.channel=="CHANNEL_01"){
								str = MALQ_CODE.getCodeName("PUB_BANK",row.subChannel);
							}else if(row.channel=="CHANNEL_02"){
								str = MALQ_CODE.getCodeName("CHANNEL_02",row.subChannel);
							}
						}
					return str
					}
			},{
				display : '客户名称',
				name : 'customerName',
				align : 'center',
				width:'12%',
				isSort : false
			},  {
				display : '充值申请时间',
				name : 'applyTime',
				width:'8%',
				align : 'center',
				isSort : true,
				render : function(row) {
					var time = row.applyTime;
					return  getSmpFormatDateByLong(parseInt(time),true);
				}
			},{
				display : '来款金额(元)',
				name : 'tradeAmount',
				width:'8%',
				align : 'right',
				isSort : true,
				render : function(row) {
					return convertFen2Yuan(row.tradeAmount);
				}
			}, {
				display : '付款方式',
				name : 'payType',
				align : 'center',
				width:'5%',
				isSort : true,
				render : function(row){
				return MALQ_CODE.getCodeName("OFFLINE_PAY_TYPE",row.payType);
				}
			}, {
				display : '处理状态',
				name : 'applyStatus',
				width:'5%',
				align : 'center',
				isSort : true,
				render : function(row){
					if(row.applyStatus=='4103'){
						return '<span style="color:red">'+MALQ_CODE.getCodeName("OFF_RECIPT_STATUS",row.applyStatus)+'</span>';
					}else if(row.applyStatus=='4104'){
						return '<span style="color:red">'+MALQ_CODE.getCodeName("OFF_RECIPT_STATUS",row.applyStatus)+'</span>';
					}else{
						return MALQ_CODE.getCodeName("OFF_RECIPT_STATUS",row.applyStatus);
						//return row.applyStatus;
					}
				}
			}, {
				display : '当前处理人',
				name : 'approvalPersonName',
				width:'8%',
				align : 'center',
				isSort : false,
			},{
				display : '操作',
				align : 'center',
				width:'8%',
				isSort : false,
				render : function(row) {	
					var str = '';
					// 通过权限唯一标示生成操作列按钮
					$(funItems).each(function() {
						if ('com.tyzfpt.upp.App.rechargeReceiptDeal' == this.permId && row.stepNo=='4304') {//出纳-处理
							str+= '<a href="javascript:window.receiptList.deal(\'' + row.id + '\')">处理</a>&nbsp;&nbsp;';
						}
						if ('com.tyzfpt.upp.App.rechargeReceiptDealFinal' == this.permId && row.stepNo=='4302') {//分配-处理
							str+= '<a href="javascript:window.receiptList.finaceDeal(\'' + row.id + '\')">处理</a>&nbsp;&nbsp;';
						}
					});
					return str;
				}
			} ];

			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
//			options['sortName'] = 'name';
			//options['rownumbers']=true;
			options['sortName'] = 'applyTime';
			options['sortOrder']='desc';			
			options['toolbar'] = {
				items : myitems
			};// 表格上面菜单
			var parms = {};
			parms['equal[tradeType]'] ='4003';//初始化查询充值申请判断类型
			options['parms']=parms;
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
			var applyName = $('#applyName').val();
			var applyPersonPost=$('#applyPersonPost').ligerGetComboBoxManager().getValue();
			var customerName=$('#customerName').val();
			var applyStatus=$('#applyStatus').ligerGetComboBoxManager().getValue();
			
			var applyTimeStart=$("#applyTimeStart").val();
			var applyTimeEnd=$("#applyTimeEnd").val();
			
			var channel = $('#incomeChannel').ligerGetComboBoxManager().getValue(); //来款渠道
			var subChannel = $('#incomeChannelSub').ligerGetComboBoxManager().getValue(); //来款子渠道
			
			if (channel != "") {
				parms['equal[channel]'] = channel;
			}
			if (subChannel != "") {
				parms['equal[subChannel]'] = subChannel;
			}
			if (customerName != "") {
				parms['like[customerName]'] = '%' +customerName+ '%';
			}
			if (applyPersonPost != "") {
				parms['equal[applyPersonPost]'] = applyPersonPost;
			}
			if (applyName != "") {
				parms['equal[applyName]'] = applyName;
			}
			if (applyStatus != "") {
				parms['equal[applyStatus]'] = applyStatus;
			}
			if (applyTimeStart != "") {
				parms['startwith[applyTime]'] = date2stamp(startDate($("#applyTimeStart").val()));;
			}
			if (applyTimeEnd != "") {
				parms['endwith[applyTime]'] = date2stamp(endDate($("#applyTimeEnd").val()));
			}
			parms['equal[tradeType]'] ='4003';//初始化查询充值申请判断类型
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		exportAll : function(){
			var that = this;
			var parms = {};
			var applyName = $('#applyName').val();
			var applyPersonPost=$('#applyPersonPost').ligerGetComboBoxManager().getValue();
			var customerName=$('#customerName').val();
			var applyStatus=$('#applyStatus').ligerGetComboBoxManager().getValue();
			
			var applyTimeStart=$("#applyTimeStart").val();
			var applyTimeEnd=$("#applyTimeEnd").val();
			
			var channel = $('#incomeChannel').ligerGetComboBoxManager().getValue(); //来款渠道
			var subChannel = $('#incomeChannelSub').ligerGetComboBoxManager().getValue(); //来款子渠道
			
			if (channel != "") {
				parms['equal[channel]'] = channel;
			}
			if (subChannel != "") {
				parms['equal[subChannel]'] = subChannel;
			}
			if (customerName != "") {
				parms['like[customerName]'] = '%' +customerName+ '%';
			}
			if (applyPersonPost != "") {
				parms['equal[applyPersonPost]'] = applyPersonPost;
			}
			if (applyName != "") {
				parms['equal[applyName]'] = applyName;
			}
			if (applyStatus != "") {
				parms['equal[applyStatus]'] = applyStatus;
			}
			if (applyTimeStart != "") {
				parms['startwith[applyTime]'] = date2stamp(startDate($("#applyTimeStart").val()));;
			}
			if (applyTimeEnd != "") {
				parms['endwith[applyTime]'] = date2stamp(endDate($("#applyTimeEnd").val()));
			}
			parms['equal[tradeType]'] ='4003';//初始化查询充值申请判断类型
			
			JAjax(window.receiptList.config.exportExcelUrl, parms , 'json', function(data){}, null, true);
			
			JSWK.clewBox('线下小票充值列表已导出，请到下载任务列表中下载!', 'clew_ico_succ', failShowTime);
		},
		// 重置查询条件
		reset : function() {
			window.location='receiptRecharge.jsp';
		},
		// 高级查询
		advancedSearch : function() {
			var that = this;
			var parms = that.cache.grid.get('parms');
			var tem = [];
			$(parms).each(function() {
				if (this.name == 'like[storeId]' || this.name == 'equal[tradeAmount]' || this.name == 'equal[applyStatus]'|| this.name == 'equal[remitterBankCode]'|| this.name == 'like[remitterBankcardNo]'|| this.name == 'equal[payType]'|| this.name == 'startwith[applyTime]'|| this.name == 'endwith[applyTime]')
					return true;
				tem.push(this);
			});
			parms = tem;
			that.cache.grid.showMyFilter(parms, that);
		},
		showImg : function(imgUrl){
			var that = window.queryList;
			var title = '查看凭证';
			var url = 'showImg.jsp';
			that.cache.imgUrl = imgUrl;
			that.cache.dialogImg = MALQ_UI.open_button(url, subHeight, 1000, null, that.cancel, title);
		},
		// 增加
		add : function() {
			var that = window.receiptList;
			that.cache.subPageId = 'add';
			that.cache.userName = $("#user").val();
			var title = '充值审核申请';
			var url = 'receiptRechargeAdd.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth, that.success, that.cancel, title);
		},
		success : function(item, dialog) {
			dialog.frame.window.receiptRechargeAdd.f_save();
		},
		cancel : function(item, dialog) {
			dialog.close();
		},
		// 在修改页面显示要修改的记录的信息
		modeify : function(id,dealName,applyName) {
			var userLogin_name=self.parent.indexPage.userLogin_name;
			if($.trim(dealName)==$.trim(applyName)){
				if($.trim(userLogin_name)!=$.trim(dealName)){
					$.ligerDialog.alert('您没有权限操作此功能！', '提示', 'error');
					return;
				}
			}else{
				if($.trim(userLogin_name)!=$.trim(applyName)){
					$.ligerDialog.alert('您没有权限操作此功能！', '提示', 'error');
					return;
				}
			}
			var that = this;
			that.cache.subPageId = 'edit';
			var title = '修改';
			that.cache.id = id;
			var url = 'rechargeAdd.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth,that.success, that.cancel, title);
		},
		// 处理
		deal : function(id) {
		//	console.log('id: ' + id);
			var that = window.receiptList;
			that.cache.subPageId = 'deal';
			that.cache.id = id;
			var title = '出纳-处理';
			var url = 'receiptRechargeDeal.jsp';
			that.cache.dialog = MALQ_UI.open_button_02(url, subHeight, subWidth,
					function(item, dialog){
						dialog.frame.window.dealList.f_save();
					},
						that.cancel
					, title);
		},
		suerDeal : function(params){
			var that = window.receiptList;
			that.cache.dialog.close();
			JAjax(that.config.addApprovalUrl, params, 'json', function(data){
				hideMask();
				that.cache.grid.loadData();
				if(data.message=="操作成功"){
					that.alert('操作成功！', 'clew_ico_succ');
				}else{
					that.alert(data.message, 'clew_ico_fail');
				}
			});
		},
		//财务经理审批
		finaceDeal:function(id){
			var that = window.receiptList;
			that.cache.subPageId = 'financeDeal';
			that.cache.id = id;
			var title = '结算-审批';
			var url = 'receiptRechargeDealFinal.jsp';
			that.cache.dialog = MALQ_UI.open_button_02(url, subHeight, subWidth,that.finaceDealSuccess,that.cancel, title);

		},
		finaceDealSuccess : function(item, dialog){
			dialog.frame.window.dealFinalList.f_save();
		},
		// 查看
		detail : function(id) {
//			console.log("id: " + id);
			var that = window.receiptList;
			that.cache.subPageId = 'detail';
			var title = '审批详细信息';
			that.cache.id = id;
			var url = 'receiptRechargeDetail.jsp';
			that.cache.dialog = MALQ_UI.open_button_02(url, subHeight, subWidth,null,that.cancel, title);
		},
		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		}
	};

	$(document).ready(function() {
		window.receiptList = new ReceiptList();
		
		$("#incomeChannel").change(function(){
			var w = 134;
			if (this.value == '对公来款') {
				MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "PUB_BANK", w, null);
				$("#incomeChannelSub").ligerComboBox({width : w});
				$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');
			} else if (this.value == 'POS来款') {
				MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "CHANNEL_02", w, null);
				$("#incomeChannelSub").ligerComboBox({width : w});
				$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');
			} else {
				MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "", w, null);
				$("#incomeChannelSub").ligerComboBox({width : w});
				$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');
			}
		});
	});
})();