/*******************************************************************************
 * 账户管理-中交账户列表
 */
(function() {
	var ZJAccountList = function() {
		this.init();
	};
	ZJAccountList.prototype = {
		config : {
			queryGridUrl : root + '/ZJAccount/queryZJAccount.action',
			exportExcelUrl : root + '/ZJAccount/exportExcelAll.action',
			subHeight : subHeight,
			subWidth : subWidth,
		},

		cache : {
			grid : null,
			accordion : null,
			tab : null,
			dialog : null,
			id:null,
			totalBalance : 0,
			titleName : "账户总余额",
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
			// 权限设置
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.tyzfpt.upp.App.account.zjaccountWarning', 'com.tyzfpt.upp.App.account.zjaccountWithdraw', 'com.tyzfpt.upp.App.account.zjaccountDetail','com.upp.ZJAccount.recharge.action', 'com.tyzfpt.upp.App.account.zjaccountExport' ]);
			var options = {};

			var columns = [ 
			  {
				display : "账户号",
				name : "insideAccountNo",
				align : "left",
				isSort : true,
				render : function(row) {
					var str= '<a href="javascript:window.zjaccountList.detail(\'' + row.insideAccountNo + '\')" class="a-line" title="点我查看详细信息">'+row.insideAccountNo+'</a>';
					return str;
				}
			}, {
				display : "账户名称",
				name : "ownerLoginName",
				align : "left",
				isSort : true
			},
			
			{
				display : "账户总余额(元)",
				name : "totalBalance",
				align : "right",
				isSort : true
//				render : function(item) {
//					return convertFen2Yuan(item.totalBalance);
//				}
		
			}, {
				display : "可用余额(元)",
				name : "usableBalance",
				align : "right",
				isSort : true
//				render : function(item) {
//					return convertFen2Yuan(item.usableBalance);
//				}
			}, {
				display : "冻结余额(元)",
				name : "frozenBalance",
				align : "right",
				isSort : true
//				render : function(item) {
//					return convertFen2Yuan(item.frozenBalance);
//				}
			}, {
				display : "账户状态",
				name : "accountStatus",
				align : "center",
				isSort : true,
				render : function(row) {
					return MALQ_CODE.getCodeName("ACCOUNT_STATUS",row.accountStatus);
				}
			}, {
				display : "创建时间",
				name : "createTime",
				align : "center",
				isSort : true
//				render : function(item) {
//					return getSmpFormatDateByLong(item.createTime, true);
//				}
			}, {
				display : "操作",
				align : "center",
				render : function(item) {
					var str = '';
					$(funItems).each(function() {
						if(this.permId == 'com.upp.ZJAccount.recharge.action'){
							str += '<a href="javascript:window.zjaccountList.recharge(\'' + item.id + '\')">充值</a>&nbsp;&nbsp;';
						}else if (this.permId == 'com.tyzfpt.upp.App.account.zjaccountWarning')
							str += '<a href="javascript:window.zjaccountList.alarmAccount(\'' + item.id + '\')">预警</a>&nbsp;&nbsp;';
						// 本次上线先不做
						// if (this.permId =='com.tyzfpt.upp.App.account.zjaccountWithdraw')
						// str += '<a href="javascript:window.zjaccountList.lockAccount(\'' + item.insideAccountNo + '\')">提现</a>&nbsp;&nbsp;';
//						if (this.permId == 'com.tyzfpt.upp.App.account.zjaccountDetail') {
//							//str += '<a href="javascript:window.zjaccountList.detailAccount(\'' + item.insideAccountNo + '\')">明细</a>&nbsp;&nbsp;';
//						} else if (this.permId == 'com.tyzfpt.upp.App.account.zjaccountExport') {
//							str += '<a href="javascript:window.zjaccountList.exportAccount(\'' + item.id + '\')">导出</a>&nbsp;&nbsp;';
//						}

					});
					return str;
				}
			} ];
			var myitems = [
							{
							text : that.cache.titleName+'：<span id="consumeMony">统计中...</span>',
							},
						];
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
			options['onAfterShowData'] = function(data) {
				$.ajax( {    
				    url:root + '/ZJAccount/ZJAccountBalance.action',
				    data:that.cache.parms,
				    type:'post',    
				    cache:false,    
				    dataType:'json',    
				    success:function(data) {
				    	var balance = data;//convertFen2Yuan(data);
						$('#consumeMony').html(balance);
				    },
				    error:function(data) {
				    	JSWK.clewBox('查询账户总余额异常', 'clew_ico_fail', CSM_failAlertTime);
				    }
				});
			};
			options['toolbar'] = {
					items : myitems
				};
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
			if ($("#totalBalance").val() && $("#totalBalance").val() != "") {
				parms.push({
					name : 'equal[totalBalance]',
					value : $('#totalBalance').val()
				});
			}
			if ($("#bankAccountNo").val() && $("#bankAccountNo").val() != "") {
				parms.push({
					name : 'like[bankAccountNo]',
					value : '%' + $("#bankAccountNo").val() + '%'
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
				name : 'equal[accountType]',
				value : '0'
			});
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		//详情
		detail : function(id){
			var that = this;
			that.cache.subPageId = 'detail';
			var title = '账户管理-中交账户列表-账户详情页';
			var url = 'zjAccountDetail.jsp';
			that.cache.id = id;
			that.cache.dialog = MALQ_UI.open(url, subHeight, subWidth, title);
		},
		
		
		//中交账户充值
		recharge:function(id){
			var that = this;
			var title = '中交账户-充值';
			var url = 'zj-recharge.jsp';
			that.cache.id = id;
			that.cache.dialog = MALQ_UI.open_button(url,subHeight, subWidth, 
					function(item, dialog) {
						dialog.frame.window.zjrecharge.f_save();
					}, 
					function(item, dialog) {
						dialog.close();
					}, 
					title);
		},
		
		//预警
		alarmAccount : function(id) {
			var that = this;
			that.cache.subPageId = 'alarmAccount';
			var title = '账户管理-中交账户列表-预警';
			var url = 'earlyWarning.jsp';
			that.cache.id=id;
			that.cache.dialog = MALQ_UI.open_button(url,300,800,that.success, that.cancel, title);
		},
		success:function(item, dialog){
			dialog.frame.window.alarmAmountAccount.f_save();
		},
		cancel : function(item, dialog) {
			dialog.close();
		},
		// 刷新Grid内容
		refreshGrid : function() {
			var that = this;
			that.cache.grid.loadData(true);
		},
		reset : function() {
			window.location='zhongJiaoAccount.jsp';
		},
		success_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_succ');
		},
		fail_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
		},
		// 提现
		lockAccount : function(totalBalance, usableBalance, mobileNo) {
			_totalBalance = totalBalance;
			_usableBalance = usableBalance;
			_mobileNo = mobileNo;
			var url = root + "/pages/manageUser/withdrawCash.jsp";
			var title = "提现";
			dialog_update = $.ligerDialog.open({
				url : url,
				height : 550,
				width : 650,
				showMax : false,
				showToggle : false,
				showMin : false,
				isDrag : false,
				isResize : false,
				modal : true,
				title : title
			});
		},

		// 明细跳转
		detailAccount : function(accountNo) {
			
		},
		// 导出账户
		exportAllAccount : function(accountNo) {
			var that = this;
			var insideAccountNo = $('#insideAccountNo').val();// 账户编号
			var ownerLoginName = $('#ownerLoginName').val();// 账户名称
			var totalBalance = $("#totalBalance").val();// 账户总余额
			var beforTime = date2stamp($("#beforTime").val());// 开始时间
			var endTime = date2stamp($("#endTime").val());// 结束时间
			var paramHtml = '';
			if (insideAccountNo) {
				paramHtml += '<input type="hidden" name="like[insideAccountNo]" value="%' + insideAccountNo + '%" />';
			}
			if (ownerLoginName) {
				paramHtml += '<input type="hidden" name="like[ownerLoginName]" value="%' + ownerLoginName + '%" />';
			}
			if (totalBalance) {
				paramHtml += '<input type="hidden" name="like[totalBalance]" value="' + totalBalance + '" />';
			}
			if (beforTime) {
				paramHtml += '<input type="hidden" name="startwith[createTime]" value="' + beforTime + '" />';
			}
			if (endTime) {
				paramHtml += '<input type="hidden" name="endwith[createTime]" value="' + endTime + '" />';
			}
			paramHtml += '<input type="hidden" name="equal[accountType]" value="0" />';
			$("#excelExoprt-div-id").html('<form id="excelExoprt-form-id" action="' + that.config.exportExcelUrl + '" method="post" target="_blank" >' + paramHtml + '</form>');
			$("#excelExoprt-form-id").submit();
		},
		exportAccount : function(accountNo) {
			var that = this;
			$.ligerDialog.confirm('确定要导出账户信息吗？', function(yes) {
				if (yes) {

					var id = accountNo;
					var paramHtml = '';
					if (id) {
						paramHtml += '<input type="hidden" name="equal[id]" value="' + id + '" />';
					}

					$("#excelExoprt-div-id").html('<form id="excelExoprt-form-id" action="' + that.config.exportExcelUrl + '" method="post" target="_blank" >' + paramHtml + '</form>');
					$("#excelExoprt-form-id").submit();
				}
			});
		}
	};

	$(document).ready(function() {
		window.zjaccountList = new ZJAccountList();
	});
})();