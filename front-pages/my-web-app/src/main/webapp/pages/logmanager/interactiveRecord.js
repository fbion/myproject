//数据对象模板

(function() {
	var InteractiveRecord = function() {
		this.init();
	};
	InteractiveRecord.prototype = {
		config : {
			queryGridUrl : root+'/logmanager/queryInteractiveRecord.action',
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
			var columns = [ 
			   			/**{
							display : "ID",
							name : "id",
							minWidth : 20,
							width : "25%",
							align : "center",
							isSort : false
						},**/
						{
							display : "创建时间",
							name : "createTime",
							minWidth : 20,
							width : "15%",
							align : "center",
							isSort : false,
							render : function(item){
								var ss = item.createTime?getSmpFormatDateByLong(item.createTime*1,true):"查看";
								return '<a href="javascript:window.interactiveRecord.interactiveRecordDetail(\'' + item.id + '\')"><span style="color:#00688B">'+ss+'</span></a>';
							}
							
						},
						{
							display : "接口方法",
							name : "operCode",
							minWidth : 20,
							width : "20%",
							align : "left",
							isSort : false
						},
						/**{
							display : "业务订单号",
							name : "bizzOrderNo",
							minWidth : 20,
							width : "15%",
							align : "left",
							isSort : false
						},**/
						{
							display : "收到参数",
							name : "jsonData",
							minWidth : 20,
							width : "30%",
							align : "left",
							isSort : false
						},
						{
							display : "响应参数",
							name : "returnData",
							minWidth : 20,
							width : "30%",
							align : "left",
							isSort : false
						}
						/**,						
						{
							display : "详情",
							minWidth : 20,
							width : "10%",
							align : "center",
							isSort : false,
							render : function(item){
								return '<a href="javascript:window.interactiveRecord.interactiveRecordDetail(\'' + item.id + '\')"><span style="color:#00688B">查看</span></a>';
							}
						}**/
					];
			//options['enabledEdit'] = true;
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'createTime';
			options['sortOrder']='desc';
			MALQ_UI.setGridOptions(options, that.config.queryGridUrl);
			$('#grid').ligerGrid(options);
			that.cache.grid = $('#grid').ligerGetGridManager();
			$('#pageloading').hide();
		},
		search : function() {
			var that = this;
			var parms=[];
			if ($('#operCode').val()) {
				parms.push({
					name : 'like[operCode]',
					value : '%' + $('#operCode').val() + '%'
				});
			}
			if (date2stamp($("#starttime").val())) {
				parms.push({
					name : 'startwith[createTime]',
					value : date2stamp(startDate($("#starttime").val()))
				});
			}
			if (date2stamp($("#endtime").val())) {
				parms.push({
					name : 'endwith[createTime]',
					value : date2stamp(endDate($("#endtime").val()))
				});
			}
			if ($('#bizzOrderNo').val()) {
				parms.push({
					name : 'equal[bizzOrderNo]',
					value : $('#bizzOrderNo').val()
				});				
			}
			if(date2stamp($("#starttime").val())-date2stamp($("#endtime").val())>0){
				alert("开始时间不能超过结束时间！");
				return;
			}
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		interactiveRecordDetail:function (id) {
			var url = root + "/pages/logmanager/interactiveRecordDetail.jsp";
			var title = "详细信息";
			opt_id = id;
			dialog_add = $.ligerDialog.open({
				url : url,
				height : subHeight,
				width : subWidth,
				showMax : false,
				showToggle : false,
				showMin : false,
				isDrag : false,
				isResize : false,
				modal : true,
				title : title
			});
		}
	};

	$(document).ready(function() {
		window.interactiveRecord = new InteractiveRecord();
	});
})();

$(function() {
	$("#submitbutton").click(function(){
		window.interactiveRecord.search();
	});
});