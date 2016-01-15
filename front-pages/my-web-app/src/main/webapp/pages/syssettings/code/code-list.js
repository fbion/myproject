/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var CodeList = function() {
		this.init();
	};
	CodeList.prototype = {
		config : {
			queryTreeUrl : root + '/simpleCode/findAllCode.action',
			queryGridUrl : root + '/simpleCode/getCodeList.action',
			modifyStatusUrl : root + '/simpleCode/modifyStatus.action',
			subHeight : subHeight,
			subWidth : subWidth,
			permissions : [ {
				name : '增加',
				pageUrl : 'addCode.action',
				iconUrl : 'add'
			} ]
		},
		cache : {
			grid : null,
			tree : null,
			accordion : null,
			tab : null,
			node : null,
			dialog : null,
			parms : {},
			subPageId : null,
			pCodeId : null
		},
		init : function() {
			this.render();
			this.loadGird();
		},
		render : function() {
			var that = this;
			$('#pCodeId').val('0');
			$('#pCode').val('root');
			$('#pCodeName').text('码表信息');
			// 布局
			$('#layout1').ligerLayout({
				leftWidth : 210,
				allowLeftResize : false,
				height : '100%',
				heightDiff : -1,
				space : 4,
				onHeightChanged : that._heightChanged
			});

			$('.l-layout-header').show();

			that.initTree();

			$('.l-layout-header-toggle').css('z-index', '2');
			$('#pageloading').hide();

		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.codeList;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},

		initTree : function() {
			var that = this;
			that.cache.accordion1=$('#accordion1').treeSelect({
				title : '码表信息',
				top : '-25',
				url : that.config.queryTreeUrl,
				// params:{'requestParam.equal.pid':'0'},
				idFieldName : 'id',
				textFieldName : 'name',
				parentIDFieldName : 'pid',
				attribute : [ 'name', 'id' ],
				onClick : function(node) {
					that.cache.node = node;
					if (!node.data.name)
						return;
					var code = node.data.code;
					if (!code) {
						$(data).each(function() {
							if (this.id == node.data.id)
								code = this.code;
						});
					}
					$('#pCodeId').val(node.data.id);
					$('#pCode').val(code);
					$('#pCodeName').text(node.data.name);
					$('#queryCodeDiv').find('#query1').click();// 触发查询按钮

				}
			});
		},

		loadGird : function() {
			var that = this;
			// ////////////生成表格上面的按钮菜单///////////////////////////////
			var myitems = [];
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.tyzfpt.upp.App.code.add' ]);
			$(funItems).each(function() {
				myitems.push({
					line : true
				});
				var item = {};
				var click = null;
				if ('com.tyzfpt.upp.App.code.add' == this.permId) {
					click = that.add;
				}
				item['text'] = this.text;
				item['click'] = click;
				item['icon'] = 'add';
				myitems.push(item);
			});
			funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.tyzfpt.upp.App.code.edit', 'com.tyzfpt.upp.App.code.delete', 'com.tyzfpt.upp.App.code.editstatus' ]);
			// ////////////生成表格上面的按钮菜单////////////////////////////
			var options = {};
			var columns = [ {
				display : '编码值',
				name : 'code',
				width : '15%',
				align : 'center'
			}, {
				display : '编码名称',
				name : 'name',
				width : '15%',
				align : 'center'
			}, {
				display : '类型编码',
				name : 'typeCode',
				width : '8%',
				align : 'center'
			}, {
				display : '类型名称',
				name : 'typeName',
				width : '10%',
				align : 'center',
				render : function(row) {
					var str = $('#pCodeName').text() ? $('#pCodeName').text() : MALQ_CODE.getCodeNameByid(row.pid);
					return str ? str : '***';
				}
			}, {
				display : '状态',
				name : 'status',
				width : '8%',
				align : 'center',
				type: 'combobox', 
				editor: MALQ_UI.getDataComboboxForEditor([{id:'1', text:'启用'},{id:'0',text:'停用'}]),
				render : function(row) {
					return row.status == '1' ? '启用' : '停用';
				}
			}, {
				display : '描述',
				name : 'description',
				width : '25%',
				align : 'left',
				type : 'none'
			}, {
				display : '操作',
				align : 'center',
				width : '15%',
				render : function(row) {
					var str = '';
					$(funItems).each(function() {
						if (this.permId == 'com.tyzfpt.upp.App.code.edit')
							str += '<a href="javascript:window.codeList.modify(\'' + row.id + '\')">修改</a>&nbsp;&nbsp;';
						if (this.permId == 'com.tyzfpt.upp.App.code.delete')
							str += '<a href="javascript:window.codeList.del(\'' + row.id + '\',\'' + row.status + '\')">删除</a>&nbsp;&nbsp;';
						if (this.permId == 'com.tyzfpt.upp.App.code.editstatus')
							str += '<a href="javascript:window.codeList.modifyStatus(\'' + row.id + '\',\'' + row.status + '\')">修改状态</a>&nbsp;&nbsp;';
					});
					return str;
				}
			} ];
			options['enabledEdit'] = true;
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'name';
			options['rownumbers']=true;
			options['parms'] = [ {
				name : 'equal[pid]',
				value : '0'
			} ]; // 设置默认进来的请求参数
			options['toolbar'] = {
				items : myitems
			};// 表格上面菜单

			MALQ_UI.setGridOptions(options, that.config.queryGridUrl);
			$('#codeGrid').ligerGrid(options);
			that.cache.grid = $('#codeGrid').ligerGetGridManager();
			$('#codeGrid').css('top', '0px');
			$('#pageloading').hide();// 加载图片
		},

		// 查询
		search : function() {
			var that = this;
			var parms = {};
			var tem = [];
			parms = tem;

			var codeName = $('#queryCodeDiv').find('#codeName').val();
			var code = $('#queryCodeDiv').find('#code').val();
			var pid = $('#queryCodeDiv').find('#pCodeId').val();
			if (pid) {
				parms.push({
					name : 'equal[pid]',
					value : pid
				});
			}
			if (codeName) {
				parms.push({
					name : 'like[name]',
					value : '%' + codeName + '%'
				});
			}
			if (code) {
				parms.push({
					name : 'like[code]',
					value : '%' + code + '%'
				});
			}
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		// 高级查询
		advancedSearch : function() {
			var that = this;
			var parms = that.cache.grid.get('parms');
			var tem = [];
			$(parms).each(function() {
				// alert(this.name);
				if (this.name == 'like[name]' || this.name == 'like[code]' || this.name == 'equal[pid]')
					return true;
				tem.push(this);
			});
			parms = tem;
			var searchParms = {
				parms : [ {
					eName : 'pCodeId',
					name : 'equal[pid]'
				} ]
			};
			that.cache.grid.showMyFilter(parms, searchParms);
		},
		// 添加信息
		add : function() {
			var that = window.codeList;
			that.cache.pCodeId = $('#queryCodeForm').find('#pCodeId').val();
			that.cache.pCode = $('#queryCodeForm').find('#pCode').val();
			that.cache.pCodeName = $('#queryCodeForm').find('#pCodeName').text();
			that.cache.subPageId = 'add';
			var title = '添加编码信息';
			var url = 'code-add.jsp';
			that.cache.dialog = MALQ_UI.open(url, that.config.subHeight, that.config.subWidth, title);
		},
		// 修改信息
		modify : function(id) {
			var that = window.codeList;
			that.cache.subPageId = 'edit';
			that.cache.pCodeId = $('#queryCodeForm').find('#pCodeId').val();
			that.cache.pCode = $('#queryCodeForm').find('#pCode').val();
			that.cache.pCodeName = $('#queryCodeForm').find('#pCodeName').text();
			var title = '修改编码信息';
			var url = 'code-add.jsp';
			that.cache.codeId = id;
			that.cache.dialog = MALQ_UI.open(url, that.config.subHeight, that.config.subWidth, title);
		},

		// 修改状态
		modifyStatus : function(id, status) {
			var that = this;
			var title = '修改编码状态';// code-edit-status
			var html = '<div align="center" style="padding: 30px;">';
			html += '<div style="height: 30px;"><input type="radio"  name="myCodeStatus" value="1"/>启用</div>';
			html += '<div style="height: 30px;"><input type="radio"  name="myCodeStatus" value="0"/>停用</div>';
			html += '</div>';
			var options = {
				target : html,
				name : 'winselector',
				isHidden : false,
				height : 200,
				width : 400,
				title : title
			};
			MALQ_UI._no_task(options);
			MALQ_UI._buttons(options, function(item, dialog) {
				var mystatus = status;
				$("input[type=radio][name=myCodeStatus]").each(function() {
					if (this.checked)
						mystatus = this.value;
				});
				JAjax(that.config.modifyStatusUrl, {
					'model.id' : id,
					'model.status' : mystatus
				}, 'json', function(data) {
					JSWK.clewBox(data.message, data.message == '操作成功' ? 'clew_ico_succ' : 'clew_ico_fail');
					that.cache.grid.loadData(true);// 刷新grid
					dialog.close();
				}, null, true);
			}, function(item, dialog) {
				dialog.close();
			});
			$.ligerDialog.open(options);
			$("input[type=radio][name=myCodeStatus]").each(function() {
				if ($(this).val() == status)
					$(this).attr("checked", "checked");
			});
		},

		// 删除
		del : function(id, status) {
			var that = window.codeList;

			if (status == '0') {
				$.ligerDialog.confirm('确定要删除吗？', function(yes) {
					if (yes) {
						JAjax(root + '/simpleCode/deleteCode.action', {
							'id' : id
						}, 'json', function(data) {
							if (data) {
								JSWK.clewBox(data.message, 'clew_ico_succ', failShowTime);
								that.flushCodeList({'id':id},'delete');//刷新缓存  
								that.cache.grid.loadData();// 刷新grid
							} else {
								JSWK.clewBox('删除编码信息失败', 'clew_ico_fail', failShowTime);
							}
						}, function() {
							JSWK.clewBox('删除编码信息失败', 'clew_ico_fail', failShowTime);
						}, true);
					}
				});
			} else {
				JSWK.clewBox('系统不允许删除状态为启用的信息!', 'clew_ico_fail', failShowTime);
			}

		},

		flushCodeList : function(row, op) {
		 	if(parent.window.CodeData && parent.window.CodeData.length>0){
        		if(op=='add'){
        			row['id']='upp-tem-'+new Date().getTime();
        			var rootParent=parent.window.CodeData;
        			rootParent.push(row);
        		}else if(op=='delete'){
        			var tem = [];
        			$(parent.window.CodeData).each(function(){
        				if(this.id==row.id)
        					return true;
        				tem.push(this);
        			});
        			parent.window.CodeData=tem;
        		}else if(op=='edit'){
        			$(parent.window.CodeData).each(function(){
        				if(this.id==row.id){
        					if(row.code && row.code != this.code)
        						this['code']=row.code;
        					if(row.name && row.name != this.name)
        						this['name']=row.name;
        					if(row.sort && row.sort != this.sort)
        						this['sort']=row.sort;
        					if(row.description && row.description != this.description)
        						this['description']=row.description;
        					return false;
        				}
        			});
        		}
        	}	
        	//刷新本页面
        	this.cache.accordion1._initTree();
		},

		reset : function() {
			$('#codeName').val('');
			$('#code').val('');
		},
		success_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_succ');
		},
		fail_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
		},
	};

	$(document).ready(function() {
		window.codeList = new CodeList();

	});
})();