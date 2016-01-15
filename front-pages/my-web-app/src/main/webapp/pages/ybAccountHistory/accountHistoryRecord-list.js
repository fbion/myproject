/**
*易宝账户历史
*/
(function() {
	var QueryList = function() {
		this.init();
	};
	QueryList.prototype = {
			config : {
				queryUrl : root + '/accountHistory/queryAccountHistory.action'
				
			},
			cache : {
				grid : null,
				accordion : null,
				tab : null,
				forwardTradeExternalNo:null
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
				var options = {};
				var columns = [{
					display : '交易发生时间',
					name : 'tradeTime',
					align : 'center',
					width:'10%',
					isSort : true,
					render : function(row) {
						if(row.tradeTime==null){
							return '<font color="#FF0000">--</font>';
						}else{
							return  getSmpFormatDateByLong(row.tradeTime,true);
						}
					}
				},{
					display : '账户类型',
					name : 'accountType',
					align : 'left',
					width:'5%',
					isSort : true,
					render : function(row) {
						return row.accountType;
					}
					
				},{
					display : '交易金额',
					name : 'amount',
					align : 'center',
					width:'6%',
					isSort : true,
					render : function(row) {
						return  row.amount;
					}
				},{
					display : '手续费',
					name : 'payPoundScale',
					align : 'center',
					width:'5%',
					isSort : true,
					render : function(row) {
						return  row.payPoundScale;
					}
				},{
					display : '实际发生金额',
					name : 'realityTradeAmount',
					align : 'center',
					width:'6%',
					isSort : true,
					render : function(row) {
						return  row.realityTradeAmount;
					}
				},{
					display : '交易后余额',
					name : 'tradeFinishBalance',
					align : 'center',
					width:'9%',
					isSort : true,
					render : function(row) {
						return  row.tradeFinishBalance;
					}
				},{
					display : '交易描述',
					name : 'tradeDesc',
					align : 'center',
					width:'6%',
					isSort : true,
					render : function(row) {
						return  row.tradeDesc;
					}
				},{
					display : '流水号',
					name : 'tradeExternalNo',
					align : 'center',
					width:'6%',
					isSort : true,
					render : function(row) {
						return  row.tradeExternalNo;
					}
				},{
					display : '交易类型',
					name : 'tradeAmount',
					align : 'center',
					width:'6%',
					isSort : true,
					render : function(row) {
						return  row.tradeType;
					}
				},{
					display : '状态',
					name : 'tradeAmount',
					align : 'center',
					width:'6%',
					isSort : true,
					render : function(row) {
						return  row.tradeStatus;
					}
				},{
					display : '交易额',
					name : 'tradeAmount',
					align : 'center',
					width:'6%',
					isSort : true,
					render : function(row) {
						return  row.tradeAmount;
					}
				},{
					display : '确认时间',
					name : 'tradeExternalNo',
					align : 'center',
					width:'10%',
					isSort : true,
					render : function(row) {
						if(row.payConfirmDate==null){
							return '<font color="#FF0000">--</font>';
						}else{
							return  getSmpFormatDateByLong(row.payConfirmDate,true);
						}
					}
				},{
					display : '同步状态',
					name : 'tradeExternalNo',
					align : 'center',
					width:'6%',
					isSort : true,
					render : function(row) {
						return  row.isClearing;
					}
				}];
				//amount,payPoundScale,realityTradeAmount,tradeFinishBalance
				options['columns'] = columns;
				options['checkbox'] = false;
				options['rownumbers']=true;
				options['frozenCheckbox'] = false;
				
				MALQ_UI.setGridOptions(options, that.config.queryUrl);
				$('#gridList').ligerGrid(options);
				that.cache.grid = $('#gridList').ligerGetGridManager();
				$('#gridList').css('top', '0px');
				$('#pageloading').hide();// 加载图片
			},
			
			// 刷新Grid内容
			refreshGrid : function() {
				var that = window.queryList;
				that.cache.grid.loadData(true);
			},
			// 一般查询
			search : function() {
				var that = this;
				var parms = {};
				var insideAccountNo=$("#insideAccountNo").val();//内部账户号
				var accountTimeStart=$('#accountDate').val();
				


				if (insideAccountNo != "") {//转出账户
					parms['insideAccountNo'] =insideAccountNo;
				}else{
					alert("账号不能为空");
					return false;
				}
				if (accountTimeStart) {//交易开始时间-记账时间
					parms['startTime'] = accountTimeStart;
				}else{
					alert("开始日期不能为空");
					return false;
				}


				that.cache.grid.set('parms', parms);
				that.cache.grid.changePage('first');
				that.cache.grid.loadData(true);
			},
			// 一般查询
			beforeQuery : function(flag) {
				var that = this;
				var parms = {};
				var insideAccountNo=$("#insideAccountNo").val();//内部账户号
				var accountTimeStart=$('#accountDate').val();
				if(flag=='1'){
					var accountTimeStartLong = date2stamp(startDate(accountTimeStart))-86400000;;
				}else{
					var accountTimeStartLong = date2stamp(startDate(accountTimeStart))+86400000;
				}
				accountTimeStart=getSmpFormatDateByLong(accountTimeStartLong, false);
				
				
				$("#accountDate").val(accountTimeStart);

				if (insideAccountNo != "") {//转出账户
					parms['insideAccountNo'] =insideAccountNo;
				}else{
					alert("账号不能为空");
					return false;
				}
				if (accountTimeStart) {//交易开始时间-记账时间
					parms['startTime'] = accountTimeStart;
				}else{
					alert("开始日期不能为空");
					return false;
				}


				that.cache.grid.set('parms', parms);
				that.cache.grid.changePage('first');
				that.cache.grid.loadData(true);
			},
			// 一般查询
			sendSign : function(flag) {
				var that = this;
				var parms = {};			
				var accountTimeStart=$('#accountDate').val();
				var sendLastSignUrl = root + '/accountHistory/sendLastTradeSign.action?startTime='+accountTimeStart;
				if (accountTimeStart) {//交易开始时间-记账时间
					
				}else{
					alert("开始日期不能为空");
					return false;
				}
				
				 $.ligerDialog.confirm('约定要发送同步结束标识？', function (yes){
					  if(yes){  
						 
						$.ajax({
					  		   type: "GET",
					  		   url:  sendLastSignUrl,
					  		   data: {'sendDate':accountTimeStart},
					  		   dataType: 'json',
					  		   success: function(data){
					  			   alert('发送完成');
					  		   }
					  	 });
					  }	
				 });
			},
			// 重置查询条件
			reset : function() {
				self.parent.indexPage.systemToNewPermSystemName="";
				window.location='accountHistoryRecord-list.jsp';
			}
	};
	$(document).ready(function() {
		window.queryList = new QueryList();

	});
})();