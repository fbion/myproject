/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var MerchantList = function() {
		this.init();
	};
	MerchantList.prototype = {
		config : {
			initkeyUrl : root + '/merchant/initkeys.action',
			queryGridUrl : root + '/merchant/getMerchantList.action',
			modifyStatusUrl : root + '/merchant/modifyStatusMerchant.action',
			exportExcelAllUrl: root + '/merchant/exportExcelAll.action',
			subHeight : subHeight,
			subWidth : subWidth,
			permissions : [ {
				name : '商户接入',
				pageUrl : 'addMerchant.action',
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
			id : null
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
			//初始化搜索栏商户类型选择框
			var w = 132;
	  		MALQ_CODE.getSelectByCodeType($("#storeType"), "2000", w, null);
			$("#storeType").ligerComboBox({width:w });
			$("#storeType").ligerGetComboBoxManager().selectValue('');
			//初始化搜索栏 商户状态选择框
	  		MALQ_CODE.getSelectByCodeType($("#storeStatus"), "3000", w, null);
			$("#storeStatus").ligerComboBox({width:w });
			$("#storeStatus").ligerGetComboBoxManager().selectValue('');
			
			var that = this;
			// ////////////生成表格上面的按钮菜单///////////////////////////////
			var myitems = [];
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, [ 'com.tyzfpt.upp.App.storequerymenuAdd','init-private-public-key']);
			$(funItems).each(function() {
				myitems.push({
					line : true
				});
				var item = {};
				var click = null;
				if ('com.tyzfpt.upp.App.storequerymenuAdd' == this.permId) {
					click = that.add;
				}else if('init-private-public-key'==this.permId){
					click = that.initKeys;
				}
				item['text'] = this.text;
				item['click'] = click;
				item['icon'] = 'add';
				myitems.push(item);
			});
			var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, ['com.tyzfpt.upp.App.storequerymenudel',
			                                                                      'com.tyzfpt.upp.App.storequerymenusecurity','com.tyzfpt.upp.App.storequerymenuedit',
			                                                                      'com.tyzfpt.upp.App.storequerymenucallback','com.tyzfpt.upp.App.storequerymenumodifystatus',
			                                                                      'com.tyzfpt.upp.App.storequerymenuExport']);
			// ////////////生成表格上面的按钮菜单////////////////////////////
			var options = {};
			var columns = [ {
				display : '商户名称',
				name : 'storeName',
				width:'15%',
				align:'left',
				render : function(row){
					var str = '';
					str += '<a href="javascript:window.merchantList.detail(\'' + row.id + '\')" class="a-line" title="点我查看详细信息">'+row.storeName+'</a>';
					return str;
				}
			}, {
				display : '商户编号',
				name : 'storeCode',
				width:'15%',
				align : 'left',
			}, {
				display : '商户类型',
				name : 'storeType',
				width:'5%',
				align : 'left',
				render : function(row) {
					return MALQ_CODE.getCodeName("2000",row.storeType)==''?row.storeType:MALQ_CODE.getCodeName("2000",row.storeType);
		}
			}, {
				display : '联系人',
				name : 'contactUser',
				width:'10%',
				align : 'left',
			}, {
				display : '联系电话',
				name : 'contactPhone',
				width:'10%',
				align : 'left',
			}, {
				display : '状态',
				name : 'storeStatus',
				type : 'combobox',
				width:'5%',
				align : 'left',
				render : function(row) {
							return MALQ_CODE.getCodeName("3000",row.storeStatus);
			}
			},
//			{
//				display : '操作人',
//				name : 'opUser',
//				width:'10%',
//				align : 'left',
//			},		
			{
				display : '接入时间',
				name : 'regTime',
				width:'12%',
				align : 'left',
				render : function(row) {
							return getSmpFormatDateByLong(row.regTime,true);
						}
			},{
				display : '操作',
				width:'20%',
				render : function(row) {
					var str = '';
					$(funItems).each(function() {
						if (this.permId == 'com.tyzfpt.upp.App.storequerymenuedit')
							str += '<a href="javascript:window.merchantList.modify(\'' + row.id + '\')">修改</a>&nbsp;&nbsp;';
						if (this.permId == 'com.tyzfpt.upp.App.storequerymenusecurity') {
							str += '<a href="javascript:window.merchantList.security(\'' + row.id + '\')">秘钥</a>&nbsp;&nbsp;';
						}
						if (this.permId == 'com.tyzfpt.upp.App.storequerymenudel')
							str += '<a href="javascript:window.merchantList.del(\'' + row.id + '\',\'' + row.storeStatus + '\')">删除</a>&nbsp;&nbsp;';
						if (this.permId == 'com.tyzfpt.upp.App.storequerymenucallback')
							str += '<a href="javascript:window.merchantList.callback(\''+  row.id + '\')">回调</a>&nbsp;&nbsp;';
						if (this.permId == 'com.tyzfpt.upp.App.storequerymenumodifystatus' && row.storeStatus=='3001')
							str += '<a href="javascript:window.merchantList.modifyStatus(\'' + row.id + '\',\'' + row.storeStatus + '\')">禁用</a>&nbsp;&nbsp;';
						if(this.permId == 'com.tyzfpt.upp.App.storequerymenumodifystatus' && row.storeStatus=='3003')
							str += '<a href="javascript:window.merchantList.modifyStatus(\'' + row.id + '\',\'' + row.storeStatus + '\')">启用</a>&nbsp;&nbsp;';
//						if(this.permId == 'com.tyzfpt.upp.App.storequerymenuExport')
//							str += '<a href="javascript:window.merchantList.exportExcel(\'' + row.id + '\')">导出</a>&nbsp;&nbsp;';
						
					});
					return str;
				}
			} ];
			options['enabledEdit'] = true;
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'regTime';
			options['sortOrder']='desc';
			options['toolbar'] = {
				items : myitems
			};// 表格上面菜单

			MALQ_UI.setGridOptions(options, that.config.queryGridUrl);
			$('#merchantGrid').ligerGrid(options);
			that.cache.grid = $('#merchantGrid').ligerGetGridManager();
			$('#merchantGrid').css('top', '0px');
			$('#pageloading').hide();// 加载图片
		},
		detail : function(id){
			var that = window.merchantList;
			that.cache.subPageId = 'edit';
			var title = '商户详情';
			var url = 'merchant-detail.jsp';
			that.cache.id = id;
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth,null, that.cancel, title);
		},
		// 查询
		search : function() {
			var that = this;
			var parms = that.cache.grid.get('parms');
			var tem = [];
			$(parms).each(function() {
				if (this.name == 'like[storeName]' || this.name == 'like[storeCode]'||this.name == 'equal[storeType]'
					||this.name == 'like[contactUser]'||this.name == 'like[contactPhone]'||this.name == 'equal[storeStatus]'
					||this.name == 'startwith[regTime]'||this.name == 'endwith[regTime]'
				)
					return true;
				tem.push(this);
			});
			parms = tem;

			var storeName = $('#storeName').val();
			var storeCode = $('#storeCode').val();
			var storeType = $("#storeType").ligerGetComboBoxManager().getValue();//商户类型
			var contactUser = $("#contactUser").val();//联系人
			var contactPhone = $("#contactPhone").val();//联系人电话
			var storeStatus = $("#storeStatus").ligerGetComboBoxManager().getValue();//商户状态
			var regTimeStart = date2stamp(startDate($("#regTimeStart").val()));//开始时间
			var regTimeEnd = date2stamp(endDate($("#regTimeEnd").val()));//结束时间
			if (storeName) {
				parms.push({
					name : 'like[storeName]',
					value : '%'+storeName+'%'
				});
			}
			if (storeCode) {
				parms.push({
					name : 'like[storeCode]',
					value : '%' + storeCode + '%'
				});
			}
			if(storeType){
				parms.push({
					name : 'equal[storeType]',
					value :  storeType 
				});
			}
			if(contactUser){
				parms.push({
					name : 'like[contactUser]',
					value :  '%' + contactUser + '%' 
				});
			}
			if(contactPhone){
				parms.push({
					name : 'like[contactPhone]',
					value :  '%' + contactPhone + '%' 
				});
			}
			if(storeStatus){
				parms.push({
					name : 'equal[storeStatus]',
					value :  storeStatus 
				});
			}
			if(regTimeStart){
				parms.push({
					name : 'startwith[regTime]',
					value :   regTimeStart  
				});
			}
			if(regTimeEnd){
				parms.push({
					name : 'endwith[regTime]',
					value :   regTimeEnd  
				});
			}
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		exportAllExcel : function(){
			var that = this;
			var storeName = $('#storeName').val();
			var storeCode = $('#storeCode').val();
			var storeType = $("#storeType").ligerGetComboBoxManager().getValue();//商户类型
			var contactUser = $("#contactUser").val();//联系人
			var contactPhone = $("#contactPhone").val();//联系人电话
			var storeStatus = $("#storeStatus").ligerGetComboBoxManager().getValue();//商户状态
			var regTimeStart = date2stamp($("#regTimeStart").val());//开始时间
			var regTimeEnd = date2stamp($("#regTimeEnd").val());//结束时间
			var paramHtml = '';
			if (storeName) {
				paramHtml += '<input type="hidden" name="like[storeName]" value="%'+storeName+'%" />';
			}
			if (storeCode) {
				paramHtml+='<input type="hidden" name="like[storeCode]" value="%'+storeCode+'%" />';
			}
			if(storeType){
				paramHtml+='<input type="hidden" name="equal[storeType]" value="'+storeType+'" />';
			}
			if(contactUser){
				paramHtml+='<input type="hidden" name="like[contactUser]" value="%'+contactUser+'%" />';
			}
			if(contactPhone){
				paramHtml+='<input type="hidden" name="like[contactPhone]" value="%'+contactPhone+'%" />';
			}
			if(storeStatus){
				paramHtml+='<input type="hidden" name="equal[storeStatus]" value="'+storeStatus+'" />';
			}
			if(regTimeStart){
				paramHtml+='<input type="hidden" name="startwith[regTime]" value="'+regTimeStart+'" />';
			}
			if(regTimeEnd){
				paramHtml+='<input type="hidden" name="endwith[regTime]" value="'+regTimeEnd+'" />';
			}
			//排序
			paramHtml+='<input type="hidden" name="order" value="storeName" />';
			paramHtml+='<input type="hidden" name="sort" value="asc" />';
	    	$("#excelExoprt-div-id").html('<form id="excelExoprt-form-id" action="'+that.config.exportExcelAllUrl+'" method="post" target="_blank" >'+paramHtml+'</form>');
	    	$("#excelExoprt-form-id").submit();
		},
		// 修改信息
		modify : function(id) {
			var that = window.merchantList;
			that.cache.subPageId = 'edit';
			var title = '修改商户信息';
			var url = 'merchant.jsp';
			that.cache.id = id;
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth,
				function(item, dialog) {
					dialog.frame.window.merchant.f_save();
				}, 
				that.cancel, title);
		},
		
		//秘钥
		security : function(id) {
			var that = window.merchantList;
			that.cache.subPageId = 'security';
			var title = '商户秘钥';
			var url = 'merchant-security.jsp';
			that.cache.id = id;
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth,
				function(item, dialog) {
					dialog.frame.window.merchant.f_save();
				}, 
				that.cancel, title);			
		},

		// 修改状态
		modifyStatus : function(id, storeStatus) {
			var that = this;
			var param = "";
			if(storeStatus=='3001'||storeStatus=='3002'){
				param = "你确定要禁用此用户吗？";
				storeStatus = "3003";
			}else{
				param = "你确定要启用此用户吗？";
				storeStatus = "3001";
			}
			
			$.ligerDialog.confirm(param, function(yes) {
				if (yes) {
					showMask();
					JAjax(that.config.modifyStatusUrl, {
						'model.id' : id,
						'model.storeStatus' : storeStatus
					}, 'json', function(data) {
						hideMask();
						if (data) {
							JSWK.clewBox(data.message, 'clew_ico_succ', failShowTime);
							that.cache.grid.loadData();// 刷新grid
						} else {
							JSWK.clewBox('修改商户状态失败', 'clew_ico_fail', failShowTime);
						}
					}, function() {
						hideMask();
						JSWK.clewBox('修改商户状态失败', 'clew_ico_fail', failShowTime);
					}, true);
				}
			});
		},
		// 添加信息
		add : function() {
			var that = window.merchantList;
			that.cache.subPageId = 'add';
			var title = '商户管理-商户接入';
			var url = 'merchant.jsp';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth,
				function(item, dialog) {
					dialog.frame.window.merchant.f_save();
				},
				that.cancel, title);
		},
		// 删除
		del : function(id, status) {
			var that = window.merchantList;
				$.ligerDialog.confirm('确定要删除吗？', function(yes) {
					if (yes) {
						showMask();
						JAjax(root + '/merchant/deleteMerchant.action', {
							'id' : id
						}, 'json', function(data) {
							hideMask();
							if (data) {
								JSWK.clewBox(data.message, 'clew_ico_succ', failShowTime);
								that.cache.grid.loadData();// 刷新grid
							} else {
								JSWK.clewBox('删除商户信息失败', 'clew_ico_fail', failShowTime);
							}
						}, function() {
							hideMask();
							JSWK.clewBox('删除商户信息失败', 'clew_ico_fail', failShowTime);
						}, true);
					}
				});

		},
		//回调
		callback : function(id){
			var that = window.merchantList;
			var title = '查看回调信息';
			var url = 'callback-list.jsp';
			that.cache.id = id;
			that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth,null,that.cancel, title);
		},
		//导出
		exportExcel : function(id){
			if(''!=id){
				window.location = root + "/merchant/exportExcel.action?id="+id;;
			}else{
				alert("操作失败");
			}
		},
		// 刷新Grid内容
		refreshGrid : function() {
			var that = this;
			that.cache.grid.loadData(true);
		},
		reset : function() {
			window.location='merchant-list.jsp';
		},
		success_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_succ');
		},
		fail_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
		},
//		successEdit:function(item, dialog) {
//			dialog.frame.window.merchantEdit.f_save();
//		},
//		success : function(item, dialog) {
//			dialog.frame.window.merchant.f_save();
//		},
		cancel : function(item, dialog) {
			dialog.close();
		},
		
		initKeys : function(){
			var that = window.merchantList;
			$.ligerDialog.confirm('初始化加密密钥后不能回恢，确定要初始化密钥吗？', function(yes) {
				if (yes) {
					showMask();
					JAjax(that.config.initkeyUrl, {}, 'json', function(data) {
						hideMask();
						if (data && data == 'SUCCESS'){
							JSWK.clewBox(data.message, 'clew_ico_succ', failShowTime);
							that.cache.grid.loadData();// 刷新grid
						}else{
							JSWK.clewBox('初始化密钥失败', 'clew_ico_fail', failShowTime);
						}
					}, function() {
						hideMask();
						JSWK.clewBox('初始化密钥失败', 'clew_ico_fail', failShowTime);
					}, true);
				}
			});
		}
	};

	$(document).ready(function() {
		window.merchantList = new MerchantList();

	});
})();