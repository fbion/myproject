/**
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 联系人列表js
 */
(function() {
	var PersonList = function() {
		this.init();
	};
	PersonList.prototype = {
		config : {
			queryGridUrl : root + '/smsPerson/ajaxPersonList.action',
			subHeight : subHeight,
			subWidth : subWidth,
			permissions : [ {
				name : '增加',
				pageUrl : 'add.action',
				iconUrl : 'add'
			} ]
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null,
			node : null,
			dialog : null,
			parms : {},
			subPageId : null,
			templateCode : null,
			uuid : null
		},
		init : function() {
			this.render();
			this.loadGird();
		},
		render : function() {
			var that = this;
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
			$('.l-layout-header-toggle').css('z-index', '2');
			$('#pageloading').hide();

		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.merchantList;
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
			myitems.push({
				line : true
			});
			var item = {};
			var click = null;
			click = that.add;
			item['text'] = '添加';
			item['click'] = click;
			item['icon'] = 'add';
			myitems.push(item);
			
			// ////////////生成表格上面的按钮菜单////////////////////////////
			var options = {};
			var columns = [ {
				display : '姓名',
				name : 'name',
				align : 'center'
			}, {
				display : '职务',
				name : 'job',
				align : 'center'
			}, {
				display : '电话',
				name : 'mobileNo',
				align : 'center'
			},{
				display : '操作',
				align : 'center',
				render : function(row) {
					var str = '';
					str += '<a href="javascript:window.personList.modify(\'' + row.uuid + '\')">修改</a>&nbsp;&nbsp;';
					str += '<a href="javascript:window.personList.del(\'' + row.uuid + '\')">删除</a>&nbsp;&nbsp;';
					str += '<a href="javascript:window.personList.updateTime(\'' + row.uuid + '\')">设置</a>&nbsp;&nbsp;';
					return str;
				}
			} ];
			
			options['enabledEdit'] = true;
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['functionCode'] = 'functionCode';
			var templateCode = parent.window.templateCode;
			that.cache.templateCode = templateCode;
			$("#templateCode").val(templateCode);
			options['parms'] = [ {
				name : 'equal[templateCode]',
				value : templateCode
			} ]; // 设置默认进来的请求参数
			options['toolbar'] = {
				items : myitems
			};// 表格上面菜单

			MALQ_UI.setGridOptions(options, that.config.queryGridUrl);
			$('#callbackGrid').ligerGrid(options);
			that.cache.grid = $('#callbackGrid').ligerGetGridManager();
			$('#callbackGrid').css('top', '0px');
			$('#pageloading').hide();// 加载图片
		},

		// 修改信息
		modify : function(id) {
			var that = window.personList;
			that.cache.subPageId = 'edit';
			var title = '修改联系人信息';
			var url = root + '/pages/sms/addSmsPerson.jsp';
			that.cache.id = id;
			that.cache.dialog = MALQ_UI.open_button(url, subHeight,subWidth,that.success,that.cancle, title);
		},
		// 删除
		del : function(id) {
			var that = window.personList;
				$.ligerDialog.confirm('确定要删除吗？', function(yes) {
					if (yes) {
						showMask();
						JAjax(root + '/smsPerson/del.action', {
							'id' : id
						}, 'json', function(data) {
							hideMask();
							if (data) {
								JSWK.clewBox(data.message, 'clew_ico_succ', failShowTime);
								that.cache.grid.loadData();// 刷新grid
							} else {
								JSWK.clewBox('删除短信联系人信息失败', 'clew_ico_fail', failShowTime);
							}
						}, function() {
							hideMask();
							JSWK.clewBox('删除短信联系人信息失败', 'clew_ico_fail', failShowTime);
						}, true);
					}
				});
		},
		// 添加信息
		add : function() {
			var that = window.personList;
			that.cache.subPageId = 'add';
			var title = '添加联系人信息';
			var url = root + '/pages/sms/addSmsPerson.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight,subWidth,that.success,that.cancel, title);
		},
		// 定时提醒
		updateTime : function(uuid) {
			var that = window.personList;
			that.cache.uuid = uuid;
			that.cache.subPageId = 'updateTime';
			var title = '定时提醒';
			var url = root + '/pages/sms/time.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight,subWidth,that.time_success,that.cancel, title);
		},
		time_success : function (item, dialog){
			dialog.frame.window.time.f_save()
		},
		//回调
		callback : function(id){
			window.location.href = root + "/merchant/callback.action?id="+id;
		},
		success_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_succ');
		},
		fail_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
		},
		success : function(item, dialog) {
			dialog.frame.window.addSmsPerson.f_save();
		},
		cancel : function(item, dialog) {
			dialog.close();
		},
	};

	$(document).ready(function() {
		window.personList = new PersonList();

	});
})();