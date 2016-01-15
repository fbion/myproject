/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var ParamList = function() {
		this.init();
	};
	ParamList.prototype = {
		config : {
			queryUrl : root + '/paramJson/queryParamList.action',
			delUrl : root + '/paramJson/deleteParam.action',
			querySimpleCodeUrl : root + '/simpleCode/findMerchant.action'
		},
		cache : {
			subPageId : null,
			grid : null,
			dialog : null,
			accordion : null,
			tab : null,
			editId : null
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
			var w = 140;
			MALQ_CODE.getSelectByCodeType($("#paramDataType"), "PARAM_DATA_TYPE", w, null);
			$("#paramDataType").ligerComboBox({
				width : w
			});
			$("#paramDataType").ligerGetComboBoxManager().selectValue('');

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
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.tyzfpt.upp.App.paramadd' ]);
			// 为添加按钮响应事件
			$(funItems).each(function() {
				myitems.push({
					line : true
				});
				if ('com.tyzfpt.upp.App.paramadd' == this.permId) {
					this['click']=that.add;
					this['icon']='add';
				}
				myitems.push(this);
			});

			// 获取操作列功能权限
			funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.tyzfpt.upp.App.paramedit', 'com.tyzfpt.upp.App.paramdelete' ]);
			// ////////////生成表格上面的按钮菜单////////////////////////////
			var options = {};
			var columns = [ {
				display : '参数名称',
				name : 'paramName',
				align : 'left',
				isSort : true,
				render : function(row) {
					return row.paramName;
				}
			}, {
				display : '数据类型',
				name : 'paramDataType',
				align : 'center',
				isSort : true,
				type: 'combobox', 
				editor: MALQ_UI.getCodeComboboxForEditor("PARAM_DATA_TYPE"),
				render : function(row) {
					 return MALQ_CODE.getCodeName("PARAM_DATA_TYPE",row.paramDataType)===''?row.paramDataType:MALQ_CODE.getCodeName("PARAM_DATA_TYPE",row.paramDataType);
				}
			}, {
				display : '参数值',
				name : 'paramValue',
				align : 'left',
				isSort : true,
				render : function(row) {
					return row.paramValue;
				}
			}, {
				display : '描述',
				name : 'paramDesc',
				align : 'left',
				isSort : false,
				type:'none',//高级查询条件过滤掉此项
				render : function(row) {
					return row.paramDesc;
				}
			},{
				display : '创建时间',
				name : 'createTime',
				align : 'center',
				isSort : true,
				type:'none',//高级查询条件过滤掉此项
				render : function(row) {
					return getSmpFormatDateByLong(row.createTime,true);
				}
			}, {
				display : '操作',
				align : 'center',
				isSort : false,
				render : function(row) {
					var str = '';
					// 通过权限唯一标示生成操作列按钮
					$(funItems).each(function() {
						if ('com.tyzfpt.upp.App.paramedit' == this.permId) {
							str += '<a href="javascript:window.paramList.modeify(\'' + row.id + '\')">修改</a>&nbsp;&nbsp;';
						}

						if ('com.tyzfpt.upp.App.paramdelete' == this.permId) {
							str += '<a href="javascript:window.paramList.del(\'' + row.id + '\')">删除</a>&nbsp;&nbsp;';
						}
					});
					return str;
				}
			} ];

			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'createTime';
			options['sortOrder']='desc';
			options['rownumbers']=true;
			options['toolbar'] = {
				items : myitems
			};// 表格上面菜单
			MALQ_UI.setGridOptions(options, that.config.queryUrl);
			$('#paramGridList').ligerGrid(options);
			that.cache.grid = $('#paramGridList').ligerGetGridManager();
			$('#paramGridList').css('top', '0px');
			$('#pageloading').hide();// 加载图片
		},

		// 刷新Grid内容
		refreshGrid : function() {
			var that = window.paramList;
			that.cache.grid.loadData(true);
		},
		// 一般查询
		search : function() {
			var that = this;
			var parms = {};
			var paramName = $('#paramName').val();
			var paramDataType = $("#paramDataType").ligerGetComboBoxManager().getValue();
			if (paramName != "") {
				parms['like[paramName]'] = '%' + $("#paramName").val() + '%';
			}
			if (paramDataType != "") {
				parms['equal[paramDataType]'] = $("#paramDataType").ligerGetComboBoxManager().getValue();
			}
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		// 重置查询条件
		reset : function() {
			$('#paramName').val('');
			$("#paramDataType").ligerGetComboBoxManager().selectValue('');
		},
		// 高级查询
		advancedSearch : function() {
			var that = this;
			var parms = that.cache.grid.get('parms');
			var tem = [];
			$(parms).each(function() {
				if (this.name == 'like[paramName]' || this.name == 'equal[paramDataType]' || this.name == 'like[paramValue]')
					return true;
				tem.push(this);
			});
			parms = tem;
			that.cache.grid.showMyFilter(parms, that);
		},
		// 增加
		add : function() {
			var that = window.paramList;
			that.cache.subPageId = 'add';
			var title = '添加参数';
			var url = 'paramAdd.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth, that.success, that.cancel, title);
		},
		success : function(item, dialog) {
			dialog.frame.window.paramAdd.f_save();
		},
		cancel : function(item, dialog) {
			dialog.close();
		},
		// 在修改页面显示要修改的记录的信息
		modeify : function(id) {
			var that = this;
			that.cache.subPageId = 'edit';
			var title = '修改参数';
			that.cache.editId = id;
			var url = 'paramAdd.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth, that.success, that.cancel, title);
		},
		del : function(id, text) {
			var that = this;
			$.ligerDialog.confirm(text ? text : '删除的参数不可恢复，是否继续？', function(yes) {
				if (yes) {
					JAjax(that.config.delUrl, {
						'ids' : id
					}, 'json', function(data) {
						if (data) {
							JSWK.clewBox(data.message, 'clew_ico_succ', failShowTime);
							that.cache.grid.loadData();// 刷新grid
						} else {
							JSWK.clewBox('删除参数信息失败', 'clew_ico_fail', failShowTime);
						}
					}, function() {
						JSWK.clewBox('提交数据时发生异常', 'clew_ico_fail', failShowTime);
					}, true);
				}
			});
		},
		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		}
	};
	$(document).ready(function() {
		window.paramList = new ParamList();
	});
})();