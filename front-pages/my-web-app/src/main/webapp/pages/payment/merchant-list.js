//普通账户列表
(function() {
	var AccountList = function() {
		this.init();
	};
	AccountList.prototype = {
		config : {
			queryGridUrl : root + '/account/queryAccount.action',
			subHeight : subHeight,
			subWidth : subWidth,
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null,
			node : null,
			dialog : null,
			parms : {},
			checkedStoreId:[],
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
			var that = window.accountList;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
		loadGird : function() {
			var that = this;
			var options = {};
			var columns = [ {
				display : "账户号",
				name : "insideAccountNo",
				width : '18%',
				align : "left",
				isSort : false,
				render : function(row) {
					return row.insideAccountNo;
				}
			}, {
				display : "账户所属",
				name : "ownerLoginName",
				align : "left",
				width : '18%',
				isSort : false
			},  {
				display : "账户总余额(元)",
				name : "totalBalance",
				align : "right",
				width : '10%',
				isSort : false,
				render : function(item) {
					return item.totalBalance;
//					return convertFen2Yuan(item.totalBalance);
				}
			}, {
				display : "可用余额(元)",
				name : "usableBalance",
				align : "right",
				width : '10%',
				isSort : false,
				render : function(item) {
					return item.usableBalance;
//					return convertFen2Yuan(item.usableBalance);
				}
			}, {
				display : "冻结余额(元)",
				name : "frozenBalance",
				align : "right",
				width : '10%',
				isSort : false,
				render : function(item) {
					return item.frozenBalance;
//					return convertFen2Yuan(item.frozenBalance);
				}
			}, {
				display : "手机号",
				name : "mobileNo",
				align : "center",
				width : '10%',
				isSort : false
			}, {
				display : "账户状态",
				name : "accountStatus",
				align : "center",
				width : '10%',
				isSort : true,
				render : function(row) {
					return MALQ_CODE.getCodeName("ACCOUNT_STATUS", row.accountStatus);
				}
			}, {
				display : "创建时间",
				name : "createTime",
				align : "center",
				width : '18%',
				isSort : true,
//				render : function(item) {
//					return getSmpFormatDateByLong(item.createTime, true);
//				}
			} ];
			options['enabledEdit'] = true;
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'createTime';
			options['sortOrder'] = 'desc';
			options['rownumbers'] = true;
			options['frozenRownumbers'] = true;
			options['parms']=[{
				name : 'equal[accountStatus]',
				value : '1'
			},{// 默认不查中交的账户
				name : 'notequal[accountType]',
				value : '0'
			}
			];
			options['onSelectRow']=that.__onSelectRow;
			MALQ_UI.setGridOptions(options, that.config.queryGridUrl);
			$('#grid').ligerGrid(options);
			that.cache.grid = $('#grid').ligerGetGridManager();
			$('#pageloading').hide();
		},
		search : function() {
			var that = this;
			var parms = [];
			if ($('#mobileNo').val()) {
				parms.push({
					name : 'like[mobileNo]',
					value : '%' + $('#mobileNo').val() + '%'
				});
			}
			if ($('#insideAccountNo').val()) {
				parms.push({
					name : 'like[insideAccountNo]',
					value : '%' + $('#insideAccountNo').val() + '%'
				});
			}

			if (date2stamp($("#beforTime").val())) {
				parms.push({
					name : 'startwith[createTime]',
					value : date2stamp(startDate($("#beforTime").val()))
				});
			}
			if (date2stamp($("#endTime").val())) {
				parms.push({
					name : 'endwith[createTime]',
					value : date2stamp(endDate($("#endTime").val()))
				});
			}
			parms.push({
				name : 'equal[accountStatus]',
				value : '1'
			});
			parms.push({
				name : 'notequal[accountType]',
				value : '0'
			});
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		f_select : function() {
			var ids = window.accountList.cache.checkedStoreId;
			if (ids.length == 0) {
				JSWK.clewBox("请选择行数据！", 'clew_ico_fail', failShowTime);
			} else {
				return {
					ids : ids
				};
			}
		},
		__onSelectRow : function(data, rowindex, rowobj) {
			var that = window.accountList;
			that.cache.checkedStoreId.push({
				id : data.insideAccountNo,
				name : data.ownerLoginName,
				ownerUserNo : data.ownerUserNo
			});
		},
		reset : function() {
			window.location = 'merchant-list.jsp';
		}

	};

	$(document).ready(function() {
		window.accountList = new AccountList();
	});
})();