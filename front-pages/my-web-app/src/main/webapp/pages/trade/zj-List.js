(function() {
	var QueryList = function() {
		this.init();
	};
	QueryList.prototype = {
		config : {
			queryUrl : root + '/zjtrade/queryList.action',
			exportExcel : root + '/zjtrade/exportExcelAll.action'
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null,
			tradeExternalNo : null,
			para : {},
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
			$("#BOOKACCOUNTTYPE").ligerComboBox({width : w});
			$("#BOOKACCOUNTTYPE").ligerGetComboBoxManager().selectValue('');
			// 2.初始化科目
			MALQ_CODE.getSelectByCodeType($("#ACCOUNTSUBJECT"), "ORDER_SUBJECT", w, null);
			$("#ACCOUNTSUBJECT").ligerComboBox({width : w});
			$("#ACCOUNTSUBJECT").ligerGetComboBoxManager().selectValue('');
		},
		loadGird : function() {
			var that = this;
			// ////////////生成表格上面的按钮菜单///////////////////////////////
			var myitems = [];
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.sinoiov.upp.webApp.zjaccount.sumDeduction','com.sinoiov.upp.webApp.zjaccount.sumRecorded', 'com.sinoiov.upp.webApp.zjaccountBook.allExport' ]);
			$(funItems).each(function() {
				myitems.push({
					line : true
				});
				var item = {};
				if('com.sinoiov.upp.webApp.zjaccount.sumDeduction'==this.permId){
					item = {text : this.text+'：<span id="sumDeduction">统计中...</span>'};
				}else if('com.sinoiov.upp.webApp.zjaccount.sumRecorded'==this.permId){
					item = {text : this.text+'：<span id="sumRecorded">统计中...</span>'};
				}else{
					var click = null;
					if ('com.sinoiov.upp.webApp.zjaccountBook.allExport' == this.permId)
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
			var columns = [ {
				display : '流水号',
				name : 'tradeExternalNo',
				align : 'center',
				width:'14%',
				isSort : true,
				render : function(row){
					var str= '<a href="javascript:window.queryList.detail(\'' + row.id + '\')" class="a-line" title="点我查看详细信息">'+row.tradeExternalNo+'</a>';
					return str;
				}
			},{
				display : '账户号',
				name : 'insideAccountNo',
				align : 'center',
				width:'14%',
				isSort : true
			}, {
				display : '账户名称',
//				name : 'storeCode',
				name : 'ownerLoginName',
				align : 'center',
				width:'14%',
				isSort : true
			}, {
				display : '记账类型',
				name : 'bookAccountType',
				align : 'center',
				width:'5%',
				isSort : true,
				render : function(row) {
					return MALQ_CODE.getCodeName("BOOK_ACCOUNT_TYPE",row.bookAccountType.toLocaleUpperCase());
				}
			},  {
				display : '科目',
//				name : 'accountSubject',
				name : 'orderTypeLuc',
				align : 'center',
				isSort : true,
				width:'8%',
				render : function(row) {
//					return MALQ_CODE.getCodeName("ORDER_SUBJECT",row.accountSubject);
					return MALQ_CODE.getCodeName("ORDER_SUBJECT",row.orderTypeLuc);
				}
			},  
			{
				display : '交易金额（元）',
//				name : 'accountMoney',
				name : 'bookAccountMoney',
				align : 'center',
				width:'8%',
				isSort : true
//				render : function(row) {
//					
//					return convertFen2Yuan(row.bookAccountMoney);
//				}
			}, {
				display : '交易时间',
				name : 'accountTime',
				align : 'center',
				width:'14%',
				isSort : true,
				render : function(row) {
					return getSmpFormatDateByLong(parseInt(row.accountTime), true);
				}
			}, {
				display : '交易后总余额（元）',
//				name : 'currentMoney',
				name : 'bookCurrentMoney',
				align : 'right',
				width:'8%',
				isSort : true
//				render : function(row) {
//					return convertFen2Yuan(row.bookCurrentMoney);
//				}
			},{
				display : '操作',
				align : 'center',
				width:'13%',
				isSort : true,
				render : function(row) {
					//var str="<a href=\"javascript:window.queryList.detail('" + row.id + "')\">明细</a>&nbsp;&nbsp;&nbsp;&nbsp;";
					var str="<a href=\"javascript:window.queryList.exportExcel('" + row.id + "')\">导出</a>";
				return str;
				}
			} ];

			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'accountTime';
			options['sortOrder']='desc';
			if(myitems && myitems.length>2)
			options['onAfterShowData'] = function(data) {
				$.ajax( {    
				    url:root + '/zjtrade/queryCount.action',
				    type:'post',  
				    data:{'equal[accountType]':'0'},
				    cache:false,    
				    dataType:'json',    
				    success:function(data) {
				    	if(data!=-1 && data!=null){
				    		var deduction = data.paySumBalance;
				    		var recorded = data.incomeSumBalance;
				    		//deduction = convertFen2Yuan(deduction);
				    		//recorded = convertFen2Yuan(recorded);
							$('#sumDeduction').html('<span style="color: red;">'+deduction+'</span>');
							$('#sumRecorded').html('<span style="color: green;">'+recorded+'</span>');
				    	}else{
				    		JSWK.clewBox('查询账户总余额异常', 'clew_ico_fail', 2000);
				    	}
						
				    },
				    error:function(data) {
				    	JSWK.clewBox('查询账户总余额异常', 'clew_ico_fail', 2000);
				    }
				});
				};
				options['toolbar'] = {
						items : myitems
					};
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
			var TRADEEXTERNALNO = $('#TRADEEXTERNALNO').val();
			var BOOKACCOUNTTYPE=$('#BOOKACCOUNTTYPE').ligerGetComboBoxManager().getValue();
			var ACCOUNTSUBJECT=$('#ACCOUNTSUBJECT').ligerGetComboBoxManager().getValue();
			var INSIDEACCOUNTNO=$("#INSIDEACCOUNTNO").val();
			var ACCOUNTTIMESTART=$('#ACCOUNTTIMESTART').val();
			var ACCOUNTTIMEEND=$('#ACCOUNTTIMEEND').val();
			if (TRADEEXTERNALNO != "") {//流水号
				parms['equal[tradeExternalNo]'] =TRADEEXTERNALNO;
			}
			if (BOOKACCOUNTTYPE != "") {//记账类型
				parms['equal[bookaccountType]'] =BOOKACCOUNTTYPE.toLowerCase();
			}
			if (ACCOUNTSUBJECT != "") {//科目
				parms['equal[accountSubject]'] = ACCOUNTSUBJECT;
			}
			if (INSIDEACCOUNTNO != "") {//账户编码
				parms['like[insideAccountNo]'] ='%' +INSIDEACCOUNTNO+ '%';
			}
			if (ACCOUNTTIMESTART != "") {//交易开始时间-记账时间
				parms['startwith[accountTime]'] = date2stamp(startDate(ACCOUNTTIMESTART));
			}
			if (ACCOUNTTIMEEND != "") {//交易结束时间-记账时间
				parms['endwith[accountTime]'] = date2stamp(endDate(ACCOUNTTIMEEND));
			}
			that.cache.para = parms;
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		// 重置查询条件
		reset : function() {
			$('#TRADEEXTERNALNO').val('');
			$('#BOOKACCOUNTTYPE').ligerGetComboBoxManager().selectValue('');
			$('#ACCOUNTSUBJECT').ligerGetComboBoxManager().selectValue('');
			$("#INSIDEACCOUNTNO").val('');
			$('#ACCOUNTTIMESTART').val('');
			$('#ACCOUNTTIMEEND').val('');
		},
		// 导出
		exportExcel : function(id) {
			var that = this;
			$.ligerDialog.confirm('确定要导出中交账户交易流水信息吗？', function(yes) {
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
			var tradeExternalNo = $('#TRADEEXTERNALNO').val();//流水号
			var bookaccountType = $('#BOOKACCOUNTTYPE').ligerGetComboBoxManager().getValue();//记账类型 equal
			var accountSubject = $('#ACCOUNTSUBJECT').ligerGetComboBoxManager().getValue();//科目   equal
			var insideAccountNo = $("#INSIDEACCOUNTNO").val();//账户编码
			var startTime = date2stamp($("#ACCOUNTTIMESTART").val());//开始时间
			var endTime = date2stamp(endDate($('#ACCOUNTTIMEEND').val()));//结束时间
			var paramHtml = '';
			if (tradeExternalNo) {
				paramHtml += '<input type="hidden" name="equal[tradeExternalNo]" value="'+tradeExternalNo+'" />';
			}
			if (bookaccountType) {
				paramHtml+='<input type="hidden" name="equal[bookaccountType]" value="'+bookaccountType.toLowerCase()+'" />';
			}
			if(accountSubject){
				paramHtml+='<input type="hidden" name="equal[accountSubject]" value="'+accountSubject+'" />';
			}
			if(insideAccountNo){
				paramHtml+='<input type="hidden" name="like[insideAccountNo]" value="'+insideAccountNo+'" />';
			}
			if(startTime){
				paramHtml+='<input type="hidden" name="startwith[accountTime]" value="'+startTime+'" />';
			}
			if(endTime){
				paramHtml+='<input type="hidden" name="endwith[accountTime]" value="'+endTime+'" />';
			}
			parms['equal[accountType]'] = '0';
	    	/*$("#excelExoprt-div-id").html('<form id="excelExoprt-form-id" action="'+that.config.exportExcel+'" method="post" target="_blank" >'+paramHtml+'</form>');
	    	$("#excelExoprt-form-id").submit();*/
			JAjax(that.config.exportExcel, parms , 'json', function(data){}, null, true);
			
			JSWK.clewBox('导出中交账户流水已提交，请到下载任务列表中下载!', 'clew_ico_succ', failShowTime);
		},
		//明细
		detail : function(id) {
			var that = this;
			that.cache.subPageId = 'detail';
			var title = '账户管理-中交账户交易流水-详情页';
			var url = 'zjDetail.jsp';
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