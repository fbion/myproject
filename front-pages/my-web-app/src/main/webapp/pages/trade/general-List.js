(function() {
	var QueryList = function() {
		this.init();
	};
	QueryList.prototype = {
		config : {
			queryUrl : root + '/generaltrade/queryList.action',
			exportExcel : root + '/generaltrade/exportExcelAll.action'
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null,
			tradeExternalNo : null
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
			// 1.初始记账类型
			var w = 140;
			MALQ_CODE.getSelectByCodeType($("#BOOKACCOUNTTYPE"), "BOOK_ACCOUNT_TYPE", w, null);
			$("#BOOKACCOUNTTYPE").ligerComboBox({width : 128});
			$("#BOOKACCOUNTTYPE").ligerGetComboBoxManager().selectValue('');
			// 2.初始化科目
			MALQ_CODE.getSelectByCodeType($("#ACCOUNTSUBJECT"), "ORDER_SUBJECT_NEW", w, null);
			$("#ACCOUNTSUBJECT").ligerComboBox({width : w});
			$("#ACCOUNTSUBJECT").ligerGetComboBoxManager().selectValue('');
		},
		loadGird : function() {
			var that = this;
			
			// ////////////生成表格上面的按钮菜单///////////////////////////////
			var myitems = [];
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.sinoiov.upp.webApp.account.sumDeduction','com.sinoiov.upp.webApp.account.sumRecorded', 'com.sinoiov.upp.webApp.accountBook.allExport' ]);
			$(funItems).each(function() {
				myitems.push({
					line : true
				});
				var item = {};
				if('com.sinoiov.upp.webApp.account.sumDeduction'==this.permId){
					item = {text : this.text+'：<span id="sumDeduction">统计中...</span>'};
				}else if('com.sinoiov.upp.webApp.account.sumRecorded'==this.permId){
					item = {text : this.text+'：<span id="sumRecorded">统计中...</span>'};
				}else{
					var click = null;
					if ('com.sinoiov.upp.webApp.accountBook.allExport' == this.permId)
						click = that.exportExcelAll;
					item['text'] = this.text;
					item['click'] = click;
					item['icon'] = this.icon;
				}
				myitems.push(item);
			});
			funItems = [];
			// ////////////生成表格上面的按钮菜单////////////////////////////	
			var options = {};
			var columns = [{
				display : '流水号',
				name : 'tradeExternalNo',
				align : 'center',
				width:'19%',
				isSort : true,
				render : function(row){
//					var str= '<a href="javascript:window.queryList.detail(\'' + row.tradeExternalNo + '\')" class="a-line" title="点我查看详细信息">'+row.tradeExternalNo+'</a>';
					var str= '<a href="javascript:window.queryList.detail(\'' + row.id + '\')" class="a-line" title="点我查看详细信息">'+row.tradeExternalNo+'</a>';
					return str;
				}
			},{
				display : '会员编号',
				name : 'ownerUserNo',
				align : 'center',
				width:'9%',
				isSort : true
			},{
				display : '账户号',
				name : 'insideAccountNo',
				align : 'center',
				width:'14%',
				isSort : true
			},  {
				display : '记账类型',
//				name : 'bookAccountTypr',
				name : 'bookAccountType',
				align : 'center',
				width:'9%',
				isSort : true,
				render : function(row) {
//					alert(row.bookAccountType);
					return MALQ_CODE.getCodeName('BOOK_ACCOUNT_TYPE',row.bookAccountType.toLocaleUpperCase());
				}
			},  {
				display : '科目',
				name : 'orderTypeLuc',
				align : 'center',
				isSort : true,
				width:'7%',
				render : function(row){
					var str = '账户转账';
					var orderSubject = row.orderTypeLuc;
					if(orderSubject){
						var list = MALQ_CODE.getCodeData('ORDER_SUBJECT_NEW');
						$(list).each(function(){
							var my = this;
							var sub = my.code.split(',');
							$(sub).each(function(){
								if(this == orderSubject){
									str = my.name;return;
								}
							});
							if(str)return;	
						});
					}	
					return str;
				}
			}, {
				display : '交易金额（元）',
//				name : 'accountMoney',
				name : 'bookAccountMoney',
				align : 'center',
				width:'10%',
				isSort : true,
				render : function(row) {
					return row.bookAccountMoney;
//					return convertFen2Yuan(row.bookAccountMoney);
				}
			}, {
				display : '交易时间',
				name : 'accountTime',
				align : 'center',
				isSort : true,
				width:'12%',
				render : function(row) {
					return getSmpFormatDateByLong(parseInt(row.accountTime), true);
				}
			}, {
				display : '交易后总余额',
//				name : 'currentMoney',
				name : 'bookCurrentMoney',
				align : 'right',
				isSort : true,
				width:'8%',
				render : function(row) {
					return row.bookCurrentMoney;
//					return convertFen2Yuan(row.bookCurrentMoney);
				}
			},{
				display : '操作',
				align : 'center',
				isSort : false,
				width:'10%',
				render : function(row) {
					//var str="<a href=\"javascript:window.queryList.tradeDetail('" + row.id + "','" + row.tradeExternalNo + "')\">明细</a>&nbsp;&nbsp;&nbsp;&nbsp;";
						/*str+="<a href=\"javascript:window.queryList.exportExcel('" + row.id + "')\">导出</a>";*/
					//return str;
					return '';
				}
			} ];

			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'accountTime';//按那个字段排序
			options['sortOrder']='desc';//降序，升序asc
			if(myitems && myitems.length>2)
			options['onAfterShowData'] = function(data){
				var params = that.cache.parms;
				that.refreshSumDeductionRecorded(params);
			};
			options['toolbar'] = {items : myitems};
				
			MALQ_UI.setGridOptions(options, that.config.queryUrl);
			$('#gridList').ligerGrid(options);
			that.cache.grid = $('#gridList').ligerGetGridManager();
			$('#gridList').css('top', '0px');
			$('#pageloading').hide();// 加载图片
		},
		refreshSumDeductionRecorded : function(params){
			$.ajax( {    
			    url:root + '/zjtrade/queryCount.action',
			    type:'post',  
			    data: params,
			    cache:false,    
			    dataType:'json',    
			    success:function(data) {
			    	if(data!=-1 && data!=null){
			    		var deduction = data.paySumBalance;
			    		var recorded = data.incomeSumBalance;
			    		$('#sumDeduction').html('<span style="color: red;">'+deduction+'</span>');
						$('#sumRecorded').html('<span style="color: green;">'+recorded+'</span>');
			    	}else{
			    		JSWK.clewBox('统计账户流水异常', 'clew_ico_fail', 2000);
			    	}
			    },
			    error:function(data) {
			    	JSWK.clewBox('统计账户流水异常', 'clew_ico_fail', 2000);
			    }
			});
		},
		// 刷新Grid内容
		refreshGrid : function() {
			var that = window.queryList;
			that.cache.grid.loadData(true);
		},
		// 一般查询
		search : function() {
			var that = this;
			var parms={'equal[accountType]':'2'};
			var TRADEEXTERNALNO = $('#TRADEEXTERNALNO').val();
			var BOOKACCOUNTTYPE=$('#BOOKACCOUNTTYPE').ligerGetComboBoxManager().getValue();
			var ACCOUNTSUBJECT=$('#ACCOUNTSUBJECT').ligerGetComboBoxManager().getValue();
			var INSIDEACCOUNTNO=$("#INSIDEACCOUNTNO").val();
			var ACCOUNTTIMESTART=$('#ACCOUNTTIMESTART').val();
			var ACCOUNTTIMEEND=$('#ACCOUNTTIMEEND').val();
			var ownerUserNo = $('#ownerUserNo').val();
			if (ownerUserNo != "") {//会员编号
				parms['like[ownerUserNo]'] =ownerUserNo;
			}
			if (TRADEEXTERNALNO != "") {//流水号
				parms['equal[tradeExternalNo]'] =TRADEEXTERNALNO;
			}
			if (BOOKACCOUNTTYPE != "") {//记账类型
				parms['equal[bookaccountType]'] =BOOKACCOUNTTYPE.toLowerCase();;
			}
			if (ACCOUNTSUBJECT) {//科目
				parms['inMap[accountSubject]'] = ACCOUNTSUBJECT;
			}
			if (INSIDEACCOUNTNO != "") {//账户编码
//				parms['like[insideAccountNo]'] ='%' +INSIDEACCOUNTNO+ '%';
				parms['equal[insideAccountNo]'] = INSIDEACCOUNTNO ;
			}
			if (ACCOUNTTIMESTART != "") {//交易开始时间-记账时间
				parms['startwith[accountTime]'] = date2stamp(startDate(ACCOUNTTIMESTART));
			}
			if (ACCOUNTTIMEEND != "") {//交易结束时间-记账时间
				parms['endwith[accountTime]'] = date2stamp(endDate(ACCOUNTTIMEEND));
			}
			that.cache.parms = parms;
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
			//that.refreshSumDeductionRecorded(parms);
		},
		// 重置查询条件
		reset : function() {
			$('#TRADEEXTERNALNO').val('');
			$('#BOOKACCOUNTTYPE').ligerGetComboBoxManager().selectValue('');
			$('#ACCOUNTSUBJECT').ligerGetComboBoxManager().selectValue('');
			$("#INSIDEACCOUNTNO").val('');
			$('#ACCOUNTTIMESTART').val('');
			$('#ACCOUNTTIMEEND').val('');
			$('#ownerUserNo').val('');
		},
		// 导出
		exportExcel : function(id) {
			var that = this;
			$.ligerDialog.confirm('确定要导出普通账户流水信息吗？', function(yes) {
				if (yes) {
					var paramHtml = '';
					if (id) {
						paramHtml += '<input type="hidden" name="equal[id]" value="'+id+'" />';
					}
					
			    	$("#excelExoprt-div-id").html('<form id="excelExoprt-form-id" action="'+that.config.exportExcel+'" method="post" target="_blank" >'+paramHtml+'</form>');
			    	$("#excelExoprt-form-id").submit();
				}
			});

		},
		exportExcelAll : function(){
			var that = window.queryList;
			var parms = {};
			var TRADEEXTERNALNO = $('#TRADEEXTERNALNO').val();
			var BOOKACCOUNTTYPE=$('#BOOKACCOUNTTYPE').ligerGetComboBoxManager().getValue();
			var ACCOUNTSUBJECT=$('#ACCOUNTSUBJECT').ligerGetComboBoxManager().getValue();
			var INSIDEACCOUNTNO=$("#INSIDEACCOUNTNO").val();
			var ACCOUNTTIMESTART=$('#ACCOUNTTIMESTART').val();
			var ACCOUNTTIMEEND=$('#ACCOUNTTIMEEND').val();
			var ownerUserNo = $('#ownerUserNo').val(); 
			if (ownerUserNo != "") {//会员编号
				parms['like[ownerUserNo]'] ='%' +ownerUserNo+ '%';
				//parms['like[ownerUserNo]'] =ownerUserNo;
			}
			if (TRADEEXTERNALNO != "") {//流水号
				parms['equal[tradeExternalNo]'] =TRADEEXTERNALNO;
			}
			if (BOOKACCOUNTTYPE != "") {//记账类型
				parms['equal[bookaccountType]'] =BOOKACCOUNTTYPE.toLowerCase();;
			}
			if (ACCOUNTSUBJECT != "") {//科目
				parms['inMap[accountSubject]'] = ACCOUNTSUBJECT;
			}
			if (INSIDEACCOUNTNO != "") {//账户编码
//				parms['like[insideAccountNo]'] ='%' +INSIDEACCOUNTNO+ '%';
				parms['equal[insideAccountNo]'] = INSIDEACCOUNTNO ;
			}
			if (ACCOUNTTIMESTART != "") {//交易开始时间-记账时间
				parms['startwith[accountTime]'] = date2stamp(startDate(ACCOUNTTIMESTART));
			}
			if (ACCOUNTTIMEEND != "") {//交易结束时间-记账时间
				parms['endwith[accountTime]'] = date2stamp(endDate(ACCOUNTTIMEEND));
			}
			parms['equal[accountType]'] = '2';
			
			JAjax(that.config.exportExcel, parms , 'json', function(data){}, null, true);
			
			JSWK.clewBox('导出账户流水已提交，请到下载任务列表中下载!', 'clew_ico_succ', failShowTime);
		},
		//明细
		tradeDetail : function(systemId,systemName) {
			self.parent.indexPage.systemToNewPermFlag = true;
	    	self.parent.indexPage.systemToNewPermSystemId = systemId;
	    	self.parent.indexPage.systemToNewPermSystemName = systemName;
	    	self.parent.indexPage.f_closeTab("dd3f91ad-073c-4c15-839d-8159b9682fbe");
	    	self.parent.indexPage.f_addTab("dd3f91ad-073c-4c15-839d-8159b9682fbe");//对应UAA平台的交易订单查询UUID
		},
		detail : function (id){
			var that = this;
			that.cache.subPageId = 'detail';
			var title = '账户管理-普通账户交易流水-详情页';
			var url = 'generalDetail.jsp';
			that.cache.tradeExternalNo = id;
			that.cache.dialog = MALQ_UI.open(url, subHeight, subWidth, title);
		},
		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		}
	};

	$(document).ready(function() {
		window.queryList = new QueryList();
	});
})();