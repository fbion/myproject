/*******************************************************************************
 * 账户管理-中交账户列表
 */
(function() {
	var ZJCreate = function() {
		this.init();
	};
	ZJCreate.prototype = {
		config : {
			queryGridUrl : root + '/ZJAccount/queryZJAccount.action',
			openlUrl : root + '/ZJAccount/open.action',
			subHeight : subHeight,
			subWidth : subWidth,
		},

		cache : {
			subPageId : null,
			grid : null,
			dialog : null,
			accordion : null,
			tab : null,
			id : null,
		},

		init : function() {
			this.loadGird();
			this.render();
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

		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.zjaccountList;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
		loadGird : function() {
			var that = this;
			// 权限设置
			var myitems = [];
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, ['com.tyzfpt.upp.zjaccount.open']);
			//alert("1."+JSON.stringify(funItems));
			// 为添加按钮响应事件
			$(funItems).each(function() {
				myitems.push({
					line : true
				});
				if ('com.tyzfpt.upp.zjaccount.open' == this.permId) {
					this['click']=that.add;
					this['icon']='add';
				}	
				myitems.push(this);
			});
			
			var options = {};
			var columns = [ {
				display : "账户号",
				name : "insideAccountNo",
				align : "left",
				isSort : false,
				render : function(row) {
					alert(row.insideAccountNo);
					var str= '<a href="javascript:window.zjcreate.detail(\'' + row.id + '\')" class="a-line" title="点我查看详细信息">'+row.insideAccountNo+'</a>';
					return str;
				}
			}, {
				display : "账户名称",
				name : "ownerLoginName",
				align : "left",
				isSort : false
			}, {
				display : "账户总余额(元)",
				name : "totalBalance",
				align : "right",
				isSort : false
//				render : function(item) {
//					return convertFen2Yuan(item.totalBalance);
//				}
			}, {
				display : "账户状态",
				name : "accountStatus",
				align : "center",
				isSort : false,
				render : function(row) {
					return MALQ_CODE.getCodeName("ACCOUNT_STATUS",row.accountStatus);
				}
			}, {
				display : "创建时间",
				name : "createTime",
				align : "center",
				isSort : false,
				render : function(item) {
					return item.createTime;
//					return getSmpFormatDateByLong(item.createTime, true);
				}
			}];
			options['enabledEdit'] = true;
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'createTime';
			options['sortOrder']='desc';
			options['parms'] = [{//默认不查中交的账户
				name : 'equal[accountType]',
				value : '0'
			}];
			options['toolbar'] = {
					items : myitems
			};// 表格上面菜单
			MALQ_UI.setGridOptions(options, that.config.queryGridUrl);
			$('#grid').ligerGrid(options);
			that.cache.grid = $('#grid').ligerGetGridManager();
			$('#pageloading').hide();
		},
		search : function() {
			var that = this;
			var parms = [];
			if ($('#ownerLoginName').val()) {
				parms.push({
					name : 'like[ownerLoginName]',
					value : '%' + $('#ownerLoginName').val() + '%'
				});
			}
			if ($('#insideAccountNo').val()) {
				parms.push({
					name : 'like[insideAccountNo]',
					value : '%' + $('#insideAccountNo').val() + '%'
				});
			}
			parms.push({
				name : 'equal[accountType]',
				value : '0'
			});
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		//详情
		detail : function(id){
			alert('id: ' + id);
			var that = window.zjcreate;
			var title = '账户管理-中交账户列表-账户详情页';
			var url = 'zjAccountDetail.jsp';
			that.cache.id = id;
			that.cache.dialog = MALQ_UI.open_button(url,subHeight-50, subWidth,null, that.cancel, title);
		},
		// 增加
		add : function() {
			var that = window.zjcreate;
			var title = '中交账户-开户';
			var url = 'zj-open.jsp';
			that.cache.dialog = MALQ_UI.open_button(url,subHeight-50, subWidth-200, that.success, that.cancel, title);
		},
		//中交账户充值
		recharge:function(id){
			var that = window.zjcreate;
			var title = '中交账户-充值';
			var url = 'zj-recharge.jsp';
			that.cache.id = id;
			that.cache.dialog = MALQ_UI.open_button(url,subHeight, subWidth, that.rechargeSuccess, that.cancel, title);
		},
		rechargeSuccess: function(item, dialog) {
			dialog.frame.window.zjrecharge.f_save();
		},
		success : function(item, dialog) {
			dialog.frame.window.zjopen.f_save();
		},
		cancel : function(item, dialog) {
			dialog.close();
		},
		// 刷新Grid内容
		refreshGrid : function() {
			var that = window.zjcreate;
			that.cache.grid.loadData(true);
		},
		reset : function() {
			window.location='zj-create.jsp';
		},
		success_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_succ');
		},
		fail_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
		},
		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		}
		
	};

	$(document).ready(function() {
		window.zjcreate= new ZJCreate();
	});
})();