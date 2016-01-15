/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var BankCardList = function() {
		this.init();
	};
	BankCardList.prototype = {
		config : {
			queryGridUrl : root + '/bankCard/queryAllBankCard.action',
			exportExcel : root + '/bankCard/exportExcel.action',
			subHeight : subHeight,
			subWidth : subWidth,
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
			var w = '133';
			MALQ_CODE.getSelectByCodeType($("#accCardType"), "BANK_CARD_TYPE", w,null);
			$("#accCardType").ligerComboBox({width : w});
			$("#accCardType").ligerGetComboBoxManager().selectValue('');
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.bankCardList;
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
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.tyzfpt.upp.App.account.bankcardmenuExport']);
			// ////////////生成表格上面的按钮菜单////////////////////////////
			var options = {};
			var columns = [ {
				display : '账户号',
				name : 'ACCOUNT_ID',
				align : 'left',
				render : function(row){
					return row.ACCOUNT_ID;
				}
			}, {
				display : '账户所属',
				name : 'STORE_NAME',
				align : 'left',
				render : function(row){
					return row.STORE_NAME;
				}
			}, {
				display : '银行卡账号',
				name : 'PART_BANK_ACCOUNT_NO',
				align : 'left'
			}, {
				display : '是否为主卡',
				name : 'IS_MAIN_CARD',
				align : 'center'
			}, {
				display : '卡类型',
				name : 'ACC_CARD_TYPE',
				align : 'center',
				render : function(row){
					return MALQ_CODE.getCodeName("BANK_CARD_TYPE",row.ACC_CARD_TYPE)==null?row.ACC_CARD_TYPE:MALQ_CODE.getCodeName("BANK_CARD_TYPE",row.ACC_CARD_TYPE);
				}
			},{
				display : '创建时间',
				name : 'CREATE_TIME',
				align : 'center',
					render : function(row){
						return getSmpFormatDateByLong(parseInt(row.CREATE_TIME),true);
					}
			},{
				display : '操作',
				align : 'center',
				render : function(row) {
					var str = '';
					$(funItems).each(function() {
						if (this.permId == 'com.tyzfpt.upp.App.account.bankcardmenuExport')
							str += '<a href="javascript:window.bankCardList.exportExcel(\'' + row.ID + '\')">导出</a>&nbsp;&nbsp;';
					});
					return str;
				}
			} ];
			options['enabledEdit'] = true;
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'branchBankName';
			options['toolbar'] = {
				items : myitems
			};// 表格上面菜单

			MALQ_UI.setGridOptions(options, that.config.queryGridUrl);
			$('#bankCardGrid').ligerGrid(options);
			that.cache.grid = $('#bankCardGrid').ligerGetGridManager();
			$('#bankCardGrid').css('top', '0px');
			$('#pageloading').hide();// 加载图片
		},

		// 查询
		search : function() {
			var that = this;
			var parms = that.cache.grid.get('parms');
			var tem = [];
			parms = tem ;
			var accCardType = $('#queryBankDiv').find('#accCardType').val();//卡类型
			var accountId = $('#queryBankDiv').find('#accountId').val();//账户编码
			var branchBankName = $('#queryBankDiv').find('#branchBankName').val();//银行名称
			var createTimeStart = date2stamp($("#createTimeStart").val());//创建时间 （时间段开始）
			var createTimeEnd = date2stamp($("#createTimeEnd").val());//创建时间（时间段结束）
			if (accCardType) {
				parms.push({
					name : 'equal[bank.acc_card_type]',
					value :  accCardType 
				});
				
			}
			if (accountId) {
				parms.push({
					name : 'like[bank.account_id]',
					value : '%' + accountId + '%'
				});
			}
			if (branchBankName) {
				parms.push({
					name : 'like[bank.branch_bank_name]',
					value : '%' + branchBankName + '%'
				});
			}
			if (createTimeStart) {
				parms.push({
					name : 'startwith[bank.create_time]',
					value :  createTimeStart 
				});
			}
			if (createTimeEnd) {
				parms.push({
					name : 'endwith[bank.create_time]',
					value :  createTimeEnd 
				});
				
			}
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		exportExcel : function(id){
			var that = this;
			var paramHtml = '';
			if (id) {
				paramHtml += '<input type="hidden" name="equal[bank.id]" value="'+id+'" />';
			}
			
	    	$("#excelExoprt-div-id").html('<form id="excelExoprt-form-id" action="'+that.config.exportExcel+'" method="post" target="_blank" >'+paramHtml+'</form>');
	    	$("#excelExoprt-form-id").submit();
		},
		exportExcelAll : function(){
			var that = this;
			var accCardType = $('#queryBankDiv').find('#accCardType').val();//卡类型
			var accountId = $('#queryBankDiv').find('#accountId').val();//账户编码
			var branchBankName = $('#queryBankDiv').find('#branchBankName').val();//银行名称
			var createTimeStart = date2stamp($("#createTimeStart").val());//创建时间 （时间段开始）
			var createTimeEnd = date2stamp($("#createTimeEnd").val());//创建时间（时间段结束）
			var paramHtml = '';
			if(accCardType){
				paramHtml+='<input type="hidden" name="equal[bank.acc_card_type]" value="'+accCardType+'" />';
			}
			if(accountId){
				paramHtml+='<input type="hidden" name="like[bank.account_id]" value="%'+accountId+'%" />';
			}
			if(branchBankName){
				paramHtml+='<input type="hidden" name="like[bank.branch_bank_name]" value="%'+branchBankName+'%" />';
			}
			if(createTimeStart){
				paramHtml+='<input type="hidden" name="startwith[bank.create_time]" value="'+createTimeStart+'" />';
			}
			if(createTimeEnd){
				paramHtml+='<input type="hidden" name="endwith[bank.create_time]" value="'+createTimeEnd+'" />';
			}
			
	    	$("#excelExoprt-div-id").html('<form id="excelExoprt-form-id" action="'+that.config.exportExcel+'" method="post" target="_blank" >'+paramHtml+'</form>');
	    	$("#excelExoprt-form-id").submit();
		},
		reset : function() {
			window.location='bankcard-list.jsp';
		},
		success_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_succ');
		},
		fail_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
		},
	};

	$(document).ready(function() {
		window.bankCardList = new BankCardList();

	});
})();