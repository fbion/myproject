/**
 * 交易订单查询js
 */
(function() {
	var ligerData = null;
	var RecordList = function() {
		this.init();
	};
	RecordList.prototype = {
		config : {
			queryUrl : root + '/riskmanager/queryWarningRecord.action'
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null,
			forwardTradeExternalNo:null,
			orderNo : null,
			ids : null,
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
			var w=140;
			
			MALQ_CODE.getSelectByCodeType($("#processStatus"), "RISK_DO_STATUS", w, null);
			$("#processStatus").ligerComboBox({width : w});
			$("#processStatus").ligerGetComboBoxManager().selectValue('');
		},
		loadGird : function() {
			var _this = this;
			var options = {};
			var myitems = [];
			
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, 
								['com.tyzfpt.upp.App.ProcessRiskWarningRecord']);
			// 为添加按钮响应事件
			$(funItems).each(function() {
				
				if ('com.tyzfpt.upp.App.ProcessRiskWarningRecord' == this.permId) {
					myitems.push({
						line : true
					});
					
					this['click']=_this.add;
					this['icon']='add';
					myitems.push(this);
				}	
			});
			var columns = [{
				name : 'id',
				hide : true,
				render : function(row) {
					return row.id;
				}
			},{
				display : '预警时间',
				name : 'warning_time',
				align : 'center',
				width:'13%',
				isSort : true,
				render : function(row) {
					return row.warning_time;
				}
			},{
				display : '风控类型',
				name : 'risk_type',
				align : 'center',
				width:'13%',
				isSort : true,
				render : function(row) {
					return row.risk_type;
				}
			},{
				display : '风控指标',
				name : 'risk_item',
				align : 'center',
				width:'9%',
				isSort : true,
				hide : true,
				render : function(row) {
					return row.risk_item;
				}
			},{
				display : '会员编号',
				name : 'member_no',
				align : 'center',
				width:'12%',
				isSort : true,
				render : function(row) {
					return row.member_no;
				}
			},{
				display : '注册手机号',
				name : 'reg_mobile',
				align : 'center',
				width:'10%',
				isSort : true,
				render : function(row) {
					return row.reg_mobile;
				}
			},  {
				display : '账户编码',
				name : 'account_no',
				align : 'center',
				width:'12%',
				isSort : true,
				render : function(row) {
					return row.account_no;
				}
			}, {
				display : '处理状态',
				name : 'process_status',
				align : 'center',
				width:'12%',
				isSort : true,
				render : function(row) {
					return (row.process_status == 1) ? "已处理" : "未处理";
				}
			}, {
				display : '处理结果',
				name : 'process_result',
				align : 'center',
				width:'5%',
				isSort : true,
				render : function(row){
					return row.process_result;
				}
			},{
				display : '处理人',
				name : 'process_person',
				align : 'center',
				width:'12%',
				isSort : true,
				render : function(row) {
						return row.process_person;
				}
			},{
				display : '详情',
				name : 'warning_info',
				align : 'center',
				width:'7%',
				isSort : true,
				render : function(row) {
					return row.warning_info;
				}
			}];
			options['columns'] = columns;
			options['checkbox'] = true;
			options['frozenCheckbox'] = false;
			options['rownumbers'] = false;
			options['toolbar'] = {
					items : myitems
				};// 表格上面菜单
			var tradeExternalNo=self.parent.indexPage.systemToNewPermSystemName;
			if(tradeExternalNo){
				$("#tradeExternalNo").val(tradeExternalNo);
				options['parms'] = [{
					name : 'equal[tradeExternalNo]',
					value : tradeExternalNo
				}];
			}
			MALQ_UI.setGridOptions(options, _this.config.queryUrl);
			ligerData = $('#gridList').ligerGrid(options);
			_this.cache.grid = $('#gridList').ligerGetGridManager();
			$('#gridList').css('top', '0px');
			$('#pageloading').hide();// 加载图片
		},

		// 增加
		add : function() {
			var _this = window.recordList;
			if (!_this.hasCheckedBox()) {
				_this.alert('请选择相应的记录进行处理', 'clew_ico_fail');
				return;
			}
			_this.cache.subPageId = 'add';
			var title = '处理预警记录';
			var url = 'processRiskWarning.jsp';
			subHeight = 400;
			subWidth = 600;
			_this.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth, _this.success, _this.cancel, title);
		},
		
		hasCheckedBox : function() {
			var _this = this;
			var rows = ligerData.getCheckedRows();
			var checkedCount = 0;
			var str = "";
			$(rows).each(function(){
		        str += this.id + ",";
		        checkedCount++;
		    });
			_this.cache.ids = str;
			return checkedCount > 0 ? true : false;
		},
		success : function(item, dialog) {
			dialog.frame.window.doRiskWarning.f_save();
		},
		cancel : function(item, dialog) {
			dialog.close();
		},
		// 刷新Grid内容
		refreshGrid : function() {
			var that = window.recordList;
			that.cache.grid.loadData(true);
		},
		// 一般查询
		search : function() {
			var that = this;
			var timeStart = date2stamp(startDate($('#starttime').val()));
			var timeEnd = date2stamp(endDate($('#endtime').val()));
			var processStatus = $('#processStatus').ligerGetComboBoxManager().getValue();
			
			var parms = {};
			if (processStatus != "") {//订单状态
				parms['equal[process_status]'] = processStatus;
			}
			if (timeStart) {//交易开始时间-记账时间
				parms['startwith[warning_time]'] = timeStart;
			}
			if (timeEnd) {//交易结束时间-记账时间
				parms['endwith[warning_time]'] = timeEnd;
			}
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		// 重置查询条件
		reset : function() {
			self.parent.indexPage.systemToNewPermSystemName="";
			window.location='riskRecord.jsp';
		},
		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		}
	};

	$(document).ready(function() {
		window.recordList = new RecordList();
	});
})();