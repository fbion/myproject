(function() {
	var QueryList = function() {
		this.init();
	};
	QueryList.prototype = {
		config : {
			queryUrl : root + '/riskmanager/queryRiskItems.action',
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
			type : null,
			riskType : null
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
			//初始化风控指标
			var w = '133';
			MALQ_CODE.getSelectByCodeType($("#riskType"), "RISK_TYPE", w, null);
			$("#riskType").ligerComboBox({width : w});
			$("#riskType").ligerGetComboBoxManager().selectValue('');
			
			//风控指标状态
			MALQ_CODE.getSelectByCodeType($("#riskStatus"), "RISK_STATUS", w, null);
			$("#riskStatus").ligerComboBox({width : w});
			$("#riskStatus").ligerGetComboBoxManager().selectValue('');
			
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
			var myitems = [];
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, ['com.tyzfpt.upp.App.AddRiskRule', 
			                                                                      'com.tyzfpt.upp.App.ModifyRiskRule', 
			                                                                      'com.tyzfpt.upp.App.QueryRiskRule']);
			// 为添加按钮响应事件
			$(funItems).each(function() {
				
				if ('com.tyzfpt.upp.App.AddRiskRule' == this.permId) {
					myitems.push({
						line : true
					});
					
					this['click']=that.add;
					this['icon']='add';
					myitems.push(this);
				}	
			});

			// ////////////生成表格上面的按钮菜单////////////////////////////
			var options = {};
			
			var columns = [{
				display : '编号',
				name : 'no',
				align : 'center',
				width:'10%',
				isSort : true
				
			},{
				display : '风控指标',
				name : 'riskItem',
				align : 'center',
				width:'20%',
				isSort : true
				
			},{
				display : '创建时间',
				name : 'createTime',
				align : 'center',
				width:'20%',
				isSort : true,
				render : function(row) {
					return utcToDate(row.createTime);
				}
			},{
				display : '创建人',
				name : 'creator',
				align : 'center',
				width:'20%',
				isSort : true,
				render : function(row) {
					return row.creator;
				}
			},{
				display : '状态',
				name : 'status',
				align : 'center',
				width:'10%',
				isSort : true,
				render : function(row) {
					return row.status;
				}
			},{
				display : '操作',
				align : 'center',
				width:'20%',
				isSort : false,
				render : function(row) {	
					var str = '';
					$(funItems).each(function() {
						if ('com.tyzfpt.upp.App.ModifyRiskRule' == this.permId) {
							str+= '<a href="javascript:window.queryList.modifyRiskRule(\'' + row.type + '\')">修改</a>&nbsp;&nbsp;';
						}if ('com.tyzfpt.upp.App.QueryRiskRule' == this.permId) {
							str+= '<a href="javascript:window.queryList.queryRiskRule(\'' + row.type + '\')">查看</a>&nbsp;&nbsp;';
						}
					});
					return str;
				}
			}];
			
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'name';
			options['toolbar'] = {
				items : myitems
			};// 表格上面菜单
			var parms = {};
			options['parms']=parms;
			MALQ_UI.setGridOptions(options, that.config.queryUrl);
			$('#gridList').ligerGrid(options);
			that.cache.grid = $('#gridList').ligerGetGridManager();
			$('#gridList').css('top', '0px');
			$('#pageloading').hide();// 加载图片
		},
		//修改风控规则
		modifyRiskRule : function(type) {
			var _this = this;
			_this.cache.type = 'modify';
			_this.cache.riskType = type;
			_this.add('modify');
		},
		//查看风控规则
		queryRiskRule : function(type) {
			var _this = this;
			_this.cache.type = 'view';
			_this.cache.riskType = type;
			_this.add('view');
		},
		// 刷新Grid内容
		refreshGrid : function() {
			var that = window.queryList;
			that.cache.grid.loadData(true);
		},
	
		search : function() {
			var that = this;
			var riskType = $('#riskType').ligerGetComboBoxManager().getValue();
			var riskStatus = $('#riskStatus').ligerGetComboBoxManager().getValue();
			
			var timeStart = date2stamp(startDate($('#riskTimeStart').val()));
			var timeEnd = date2stamp(endDate($('#riskTimeEnd').val()));
			
			var parms = {};
			
			if (riskType != "") {//纬度
				parms['equal[riskType]'] = riskType;
			}
			if (riskStatus != "") {//状态
				parms['equal[riskStatus]'] = riskStatus;
			}
			if (timeStart) {//开始时间
				parms['startwith[timeStart]'] = timeStart;
			}
			if (timeEnd) {//结束时间
				parms['endwith[timeEnd]'] = timeEnd;
			}
			
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		// 重置查询条件
		reset : function() {
			window.location='riskRule.jsp';
		},

		// 增加
		add : function(val) {
			
			var that = window.queryList;
			if (val != 'view' && val != 'modify') {
				that.cache.type = null;
				that.cache.riskType = null;
			}
			that.cache.subPageId = 'add';
			var title = '增加风控规则';
			var url = 'riskAdd.jsp';
			subHeight = 420;
			if ('view' == val) {
				that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth, false, that.cancel, title);
			} else {
				that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth, that.success, that.cancel, title);
			}
			
		},
		success : function(item, dialog) {
			dialog.frame.window.riskRuleAdd.f_save();
		},
		cancel : function(item, dialog) {
			dialog.close();
		},
		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		}
	};

	$(document).ready(function() {
		window.queryList = new QueryList();
	});
})();