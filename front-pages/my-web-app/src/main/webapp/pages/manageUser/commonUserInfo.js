//普通账户列表
var _accountNo;
(function() {
	var AccountList = function() {
		this.init();
	};
	AccountList.prototype = {
		config : {
			queryGridUrl : root + '/account/queryAccount.action',
			exportExcelUrl : root + '/account/exportExcelAll.action',
			resetPasswordUrl:root+'/account/changePWD.action',
			subHeight : subHeight,
			subWidth : subWidth,
		},
		cache : {
			subPageId:null,
			grid : null,
			accordion : null,
			tab : null,
			node : null,
			dialog : null,
			parms : {},
			id : null,
			pass:null,
			accountNo:null,
			usableBalance : null,//可用余额
			hideMobileNo : null
		},

		init : function() {
			this.loadGird();
			this.render();
			this.initSelectData();
		},
		initSelectData:function(){
			var w = '128';
			MALQ_CODE.getSelectByCodeType($("#accountStatus"), "ACCOUNT_STATUS", w, null);
			$("#accountStatus").ligerComboBox({
				width : w
			});
			$("#accountStatus").ligerGetComboBoxManager().selectValue('');

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
			var myitems = [];
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.tyzfpt.upp.App.account.ordinaryaccountExport' ]);
			$(funItems).each(function() {
				myitems.push({
					line : true
				});
				var item = {};
				var click = null;
				if ('com.tyzfpt.upp.App.account.ordinaryaccountExport' == this.permId) {
					click = that.exportAccountAll;
				}
				item['text'] = this.text;
				item['click'] = click;
				item['icon'] = this.icon;
				myitems.push(item);
			});
			myitems.push({
				text : "普通账户总余额"+'：<span id="consumeMony">统计中...</span>'
			});
			funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.tyzfpt.upp.App.account.ordinaryaccountBlock', 
			                                                'com.tyzfpt.upp.App.account.ordinaryaccountDeblock', 
			                                                'com.tyzfpt.upp.App.account.ordinaryaccountLogout', 
			                                                'com.tyzfpt.upp.App.account.ordinaryaccountReset',
			                                                'com.tyzfpt.upp.App.account.ordinaryaccountFrozen', 
			                                                'com.tyzfpt.upp.App.account.ordinaryaccountUnfreeze',
			                                                'com.tyzfpt.upp.App.account.ordinaryaccountExport',
			                                                'com.tyzfpt.upp.App.account.riskLimitAmount',
			                                                'com.tyzfpt.upp.App.account.resetSecurityProblem',
			                                                'com.tyzfpt.upp.App.account.modifyMobile']);
			var options = {};
			var columns = [ {
				display : "会员编号",
				name : "ownerUserNo",
				width:'9%',
				align : "center",
				isSort : false
			},{
				display : "账户号",
				name : "insideAccountNo",
				width:'14%',
				align : "center",
				isSort : true,
				render : function(row) {
//					var str= '<a href="javascript:window.accountList.detail(\'' + row.id + '\')" class="a-line" title="点我查看详细信息">'+row.insideAccountNo+'</a>';
					var str= '<a href="javascript:window.accountList.detail(\'' + row.insideAccountNo + '\')" class="a-line" title="点我查看详细信息">'+row.insideAccountNo+'</a>';
					return str;
				}
			}, {
				display : "账户总余额(元)",
				name : "totalBalance",
				width:'9%',
				align : "center",
				isSort : true,
				render : function(item) {
					//alert(parseInt(item.totalBalance));
//					return convertFen2Yuan(item.totalBalance);
					return item.totalBalance;
				}
			}, {
				display : "可用余额(元)",
				name : "usableBalance",
				align : "center",
				width:'9%',
				isSort : true,
				render : function(item) {
					window.accountList.cache.usableBalance = item.usableBalance;
					return item.usableBalance;
					
//					window.accountList.cache.usableBalance = convertFen2Yuan(item.usableBalance);
//					return convertFen2Yuan(item.usableBalance);
				}
			}, {
				display : "冻结余额(元)",
				name : "frozenBalance",
				align : "center",
				width:'9%',
				isSort : true,
				render : function(item) {
					return item.frozenBalance;
//					return convertFen2Yuan(item.frozenBalance);
				}
			}, {
				display : "手机号",
				name : "mobileNo",
				width:'8%',
				align : "center",
				isSort : true
			}, {
				display : "账户状态",
				name : "accountStatus",
				width:'5%',
				align : "center",
				isSort : true,
				render : function(row) {
					return MALQ_CODE.getCodeName("ACCOUNT_STATUS",row.accountStatus);
				}
			}, {
				display : "创建时间",
				name : "createTime",
				align : "center",
				width:'12%',
				isSort : true,
				render : function(item) {
					return item.createTime;
//					return getSmpFormatDateByLong(item.createTime, true);
				}
			}, {
				display : "操作",
				align : "center",
				width:'23%',
				render : function(item) {
					var str = '';
					$(funItems).each(function() {

						/*if (this.permId == 'com.tyzfpt.upp.App.account.ordinaryaccountExport')
							str += '<a href="javascript:window.accountList.exportAccount(\'' + item.id + '\')">导出</a>&nbsp;&nbsp;';*/
						if (this.permId == 'com.tyzfpt.upp.App.account.ordinaryaccountReset')
							str += '<a href="javascript:window.accountList.changePWD(\'' + item.insideAccountNo + '-'+ item.mobileNo + '\')">重置密码</a>&nbsp;&nbsp;';
						if (this.permId == 'com.tyzfpt.upp.App.account.ordinaryaccountFrozen')
							str += '<a href="javascript:window.accountList.frozen(\'' + item.insideAccountNo + '\')">冻结</a>&nbsp;&nbsp;';
						/*if (this.permId == 'com.tyzfpt.upp.App.account.ordinaryaccountUnfreeze')
							str += '<a href="javascript:window.accountList.unfreeze(\'' + item.insideAccountNo + '\')">解冻</a>&nbsp;&nbsp;';*/
						if (this.permId == 'com.tyzfpt.upp.App.account.ordinaryaccountBlock' && (item.accountStatus == '1')) {
							str += '<a href="javascript:window.accountList.lockAccount(\'' + item.insideAccountNo + '\')">锁定</a>&nbsp;&nbsp;';
						} else if (this.permId == 'com.tyzfpt.upp.App.account.ordinaryaccountDeblock' && (item.accountStatus == '2')) {
							str += '<a href="javascript:window.accountList.DelockAccount(\'' + item.insideAccountNo + '\')">解锁</a>&nbsp;&nbsp;';
						}
						if (this.permId == 'com.tyzfpt.upp.App.account.resetSecurityProblem') {
							str += '<a href="javascript:window.accountList.resetSecurityProblem(\'' + item.insideAccountNo + '\')">重置安全问题</a>&nbsp;&nbsp;';
						}
						if (this.permId == 'com.tyzfpt.upp.App.account.riskLimitAmount') {
							str += '<a href="javascript:window.accountList.riskLimitAmount(\'' + item.insideAccountNo + '\')">风控额度</a>&nbsp;&nbsp;';
						}
						if (this.permId == 'com.tyzfpt.upp.App.account.modifyMobile') {
							str += '<a href="javascript:window.accountList.modifyMobile(\'' + item.insideAccountNo + '-'+ item.mobileNo + '\')">修改手机号</a>&nbsp;&nbsp;';
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
			options['parms'] = [{//默认不查中交的账户
				name : 'equal[accountType]',
				value : '2'
			}];
			options['onAfterShowData'] = function(data) {
				var param = that.cache.parms;
				param['equal[accountType]'] = '2',
				$.ajax( {    
				    url:root + '/account/queryAccountBalance.action',
				    
//				    data:that.cache.parms,
				    data : param,
				    type:'post',    
				    cache:false,    
				    dataType:'json',    
				    success:function(data) {
				    	if(data!=-1){
//				    		var balance = convertFen2Yuan(data);
							$('#consumeMony').html(data);
				    	}else{
				    		JSWK.clewBox('查询账户总余额异常', 'clew_ico_fail', CSM_failAlertTime);
				    	}
						
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
		reset:function(){
			window.location='commonUserInfo.jsp';
		},
		search : function() {
			var that = this;
			var parms=[];
			if ($('#mobileNo').val()) {
				parms.push({
					name : 'like[mobileNo]',
					value : '%' + $('#mobileNo').val() + '%'
					
//					name : 'like[ownerLoginName]',
//					value : '%' + $('#ownerLoginName').val() + '%'
				});
			}
			if ($('#insideAccountNo').val()) {
				parms.push({
					name : 'equal[insideAccountNo]',
					value : $('#insideAccountNo').val()
					
//					name : 'like[insideAccountNo]',
//					value : '%' + $('#insideAccountNo').val() + '%'
				
				});
			}
			if ($('#ownerUserNo').val()) {
				parms.push({
					name : 'equal[ownerUserNo]',
					value : $('#ownerUserNo').val()
					
//					name : 'like[insideAccountNo]',
//					value : '%' + $('#insideAccountNo').val() + '%'
				
				});
			}
			if ($("#accountStatus").val() && $("#accountStatus").val() != "" && $("#accountStatus").val() != "请选择") {
				parms.push({
					name : 'equal[accountStatus]',
					value : $("#accountStatus").ligerGetComboBoxManager().getValue()
					
//					name : 'like[accountStatus]',
//					value : '%' +$("#accountStatus").ligerGetComboBoxManager().getValue()+ '%'
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
				value : '2'
//				name : 'notequal[accountType]',
//				value : '0'
			});
			that.cache.parms = parms;
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			$('#consumeMony').html('0.0');
			that.cache.grid.loadData(true);
		},
		// 注销账户
		cancelAccount : function(accountNo) {
			var that = window.accountList;
			$.ligerDialog.confirm('确定要注销账户吗？', function(yes) {
				if (yes) {
					JAjax(root + '/account/revokeAccount.action', {
						'accountNo' : accountNo
					}, 'json', function(data) {
						if (data && data != null) {
							JSWK.clewBox(data.message, 'clew_ico_succ', failShowTime);
							// that.flushCodeList();//刷新grid
							that.cache.grid.loadData();// 刷新grid
						} else {
							JSWK.clewBox('注销账户失败', 'clew_ico_fail', failShowTime);
						}
					}, function() {
						JSWK.clewBox('注销账户失败', 'clew_ico_fail', failShowTime);
					}, true);
				}
			});
		},

		// 锁定账户
		lockAccount : function(accountNo) {
			var that = window.accountList;
			$.ligerDialog.confirm('确定要锁定账户吗？', function(yes) {
				if (yes) {
					JAjax(root + '/account/lockAccount.action', {
						'accountNo' : accountNo
					}, 'json', function(data) {
						if (data && data.message != null) {
							JSWK.clewBox(data.message, 'clew_ico_succ', failShowTime);
							// that.flushCodeList();//刷新grid
							that.cache.grid.loadData();// 刷新grid
						} else {
							JSWK.clewBox('锁定账户失败', 'clew_ico_fail', failShowTime);
						}
					}, function() {
						JSWK.clewBox('锁定账户失败', 'clew_ico_fail', failShowTime);
					}, true);
				}
			});
		},
		// 解锁账户
		DelockAccount : function(accountNo) {
			var that = window.accountList;
			$.ligerDialog.confirm('确定要解锁账户吗？', function(yes) {
				if (yes) {
					JAjax(root + '/account/DelockAccount.action', {
						'accountNo' : accountNo
					}, 'json', function(data) {
						if (data && data.message != null) {
							JSWK.clewBox(data.message, 'clew_ico_succ', failShowTime);
							that.cache.grid.loadData();// 刷新grid
						} else {
							JSWK.clewBox('解锁账户失败', 'clew_ico_fail', failShowTime);
						}
					}, function() {
						JSWK.clewBox('解锁账户失败', 'clew_ico_fail', failShowTime);
					}, true);
				}
			});
		},

		//重置密码
		changePWD : function(accountNoAndMobile) {
			var that=this;
			$.ligerDialog.confirm('您确定要重置密码？', function(yes) {
				if (yes) {
					var array = accountNoAndMobile.split("-");
					var accountNo =  array[0];
					var mobileNo = array[1];
					JAjax(root+'/account/changePWD.action', {'accountNo' : accountNo,'mobileNo': mobileNo}, 'json', function(data) {
						if (data&&data!="") {
							window.accountList.cache.accountNo=accountNo;
							window.accountList.cache.pass=data;
//							that.resetSuccess();
							JSWK.clewBox('重置密码成功', 'clew_ico_succ', failShowTime);
						} else {
							JSWK.clewBox('重置密码失败', 'clew_ico_fail', failShowTime);
						}
					}, function() {JSWK.clewBox('重置密码异常', 'clew_ico_fail', failShowTime);}, true);
				}
			});
		},
		//密码重置成功提示页面
		resetSuccess : function() {
			var that = this;
			that.cache.subPageId = 'success';
			var title = '重置密码成功';
			var url = 'resetPassword.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, 150, 400,null, that.cancel, title);
		},
		
		detail : function(insideAccountNo){
			var that = this;
			that.cache.subPageId = 'detail';
			var title = '账户管理-普通账户列表-账户详情页';
			var url = 'commonUserInfoDetail.jsp';
			that.cache.id = insideAccountNo;
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth,null, that.cancel, title);
		},
		unfreeze : function(accountNo){
			var that = window.accountList;
			var title = '解冻金额';
			var url = 'unfreezeAmount.jsp';
			$('#accountNo').val(accountNo);
			//$('#workOrderNo').val(workOrderNo);
			//$('#tradeExternalNo').val(tradeExternalNo);
			that.cache.dialog = MALQ_UI.open_button(url, subHeight,subWidth,
					function(item,dialog){
						dialog.frame.window.unfreezeAmount.f_save();
					},
					that.cancle, 
					title);
		},
		success : function(item, dialog) {
			dialog.frame.window.frozen.save();
		},
		riskSuccess : function(item, dialog) {
			dialog.frame.window.commonUserRiskLimitAmount.f_save();
		},
		modifyMobileSuccess : function(item, dialog) {
			dialog.frame.window.commonUserModifyMobile.f_save();
		},
		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		},
		cancel : function(item, dialog) {
			dialog.close();
		},
		success_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_succ');
		},
		fail_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
		},
		//风控额度
		riskLimitAmount : function(accountNo){
			var _this = this;
			_this.cache.accountNo = accountNo;
			_this.cache.subPageId = 'commonUserRiskLimitAmount';
			var title = '账户管理-普通账户列表-风控额度';
			var url = 'commonUserRiskLimitAmount.jsp';
		    subHeight = 300;
		    subWidth = 800;
		    _this.cache.accountNo = accountNo;
		    _this.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth, _this.riskSuccess, _this.cancel, title);
		},
		
		//重置安全问题
		resetSecurityProblem : function(accountNo){
			var _this = this;
			JAjax(root+'/account/querySecurityQuestion.action', {'accountNo' : accountNo}, 'json',function(data) {
				if (data == 0) {
					JSWK.clewBox('未设置安全问题，无需重置', 'clew_ico_fail', failShowTime);
					return;
				} else {
					$.ligerDialog.confirm('您确定要重置安全问题？', function(yes) {
						if (yes) {
							JAjax(root+'/account/resetSecurityProblem.action', {'accountNo' : accountNo}, 'json', function(data) {
								if (data&&data!="") {
									JSWK.clewBox('操作成功', 'clew_ico_succ', failShowTime);
								} else {
									JSWK.clewBox('重置安全问题失败', 'clew_ico_fail', failShowTime);
								}
							}, function() {JSWK.clewBox('重置安全问题异常', 'clew_ico_fail', failShowTime);}, true);
						}
					});					
				}
			});
			
//			$.ligerDialog.confirm('您确定要重置安全问题？', function(yes) {
//				if (yes) {
//					JAjax(root+'/account/resetSecurityProblem.action', {'accountNo' : accountNo}, 'json', function(data) {
//						if (data&&data!="") {
//							JSWK.clewBox('操作成功', 'clew_ico_succ', failShowTime);
//						} else {
//							JSWK.clewBox('重置安全问题失败', 'clew_ico_fail', failShowTime);
//						}
//					}, function() {JSWK.clewBox('重置安全问题异常', 'clew_ico_fail', failShowTime);}, true);
//				}
//			});
		},
		//风控额度
		modifyMobile : function(param){
			var array = param.split("-");
			var _this = this;
			_this.cache.accountNo = array[0];
			_this.cache.hideMobileNo = array[1].substring(0, 3) + "****" + array[1].substring(7, 11);
			_this.cache.subPageId = 'modifyMobile';
			var title = '账户管理-普通账户列表-修改手机号';
			var url = 'commonUserModifyMobile.jsp';
		    subHeight = 280;
		    subWidth = 300;
		    _this.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth, _this.modifyMobileSuccess, _this.cancel, title);
		},
		frozen : function(accountNo){
			var that = this;
			that.cache.subPageId = 'frozen';
			var title = '账户管理-普通账户列表-冻结金额';
			var url = 'frozen.jsp';
		    subHeight = 250;
		    subWidth = 500;
			that.cache.accountNo = accountNo;
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth,that.success, that.cancel, title);
		},
		// 导出账户
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
		},
		exportAccountAll : function() {
			var that = window.accountList;
//			var mobileNo = $('#mobileNo').val();// 账户所属
//			var insideAccountNo = $('#insideAccountNo').val();// 账户编号
//			var accountStatus = $("#accountStatus").ligerGetComboBoxManager().getValue();// 账户状态
//			var beforTime = date2stamp($("#beforTime").val());// 开始时间
//			var endTime = date2stamp($("#endTime").val());// 结束时间
			var parms=[];
			if ($('#mobileNo').val()) {
				parms.push({
					name : 'like[mobileNo]',
					value : '%' + $('#mobileNo').val() + '%'
					
//					name : 'like[ownerLoginName]',
//					value : '%' + $('#ownerLoginName').val() + '%'
				});
			}

			if ($('#ownerUserNo').val()) {
				parms.push({
					name : 'equal[ownerUserNo]',
					value : $('#ownerUserNo').val()
					
//					name : 'like[insideAccountNo]',
//					value : '%' + $('#insideAccountNo').val() + '%'
				
				});
			}
			
			if ($('#insideAccountNo').val()) {
				parms.push({
					name : 'equal[insideAccountNo]',
					value : $('#insideAccountNo').val()
					
//					name : 'like[insideAccountNo]',
//					value : '%' + $('#insideAccountNo').val() + '%'
				
				});
			}

			if ($("#accountStatus").val() && $("#accountStatus").val() != "" && $("#accountStatus").val() != "请选择") {
				parms.push({
					name : 'equal[accountStatus]',
					value : $("#accountStatus").ligerGetComboBoxManager().getValue()
					
//					name : 'like[accountStatus]',
//					value : '%' +$("#accountStatus").ligerGetComboBoxManager().getValue()+ '%'
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
				value : '2'
//				name : 'notequal[accountType]',
//				value : '0'
			});
	    	/*$("#excelExoprt-div-id").html('<form id="excelExoprt-form-id" action="'+that.config.exportExcel+'" method="post" target="_blank" >'+paramHtml+'</form>');
	    	$("#excelExoprt-form-id").submit();*/
			JAjax(that.config.exportExcelUrl, parms , 'json', function(data){}, null, true);
			
			JSWK.clewBox('导出普通账户列表已提交，请到下载任务列表中下载!', 'clew_ico_succ', failShowTime);
			/*$("#excelExoprt-div-id").html('<form id="excelExoprt-form-id" action="' + that.config.exportExcelUrl + '" method="post" target="_blank" >' + paramHtml + '</form>');
			$("#excelExoprt-form-id").submit();*/
		}
	};

	$(document).ready(function() {
		window.accountList = new AccountList();
	});
})();