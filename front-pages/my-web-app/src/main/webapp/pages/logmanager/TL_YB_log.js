//数据对象模板

(function() {
	var InteractiveRecord = function() {
		this.init();
	};
	InteractiveRecord.prototype = {
		config : {
			queryGridUrl : root+'/logmanager/queryTL_YB_log.action',
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
							width : "20%",
							align : "center",
							isSort : false
						},**/
						{
							display : "名称",
							name : "name",
							minWidth : 20,
							width : "20%",
							align : "left",
							isSort : false,
							render : function(item){
								return '<a href="javascript:window.interactiveRecord.interactiveRecordDetail(\'' + item.id + '\')"><span style="color:#00688B">'+item.name+'</span></a>';
							}
						},
						{
							display : "类型",
							name : "type",
							minWidth : 20,
							width : "5%",
							align : "center",
							isSort : false
						},
						{
							display : "发送时间",
							name : "requestTime",
							minWidth : 20,
							width : "15%",
							align : "center",
							isSort : false,
							render : function(item){
								if(item.requestTime){
									return getSmpFormatDateByLong(item.requestTime*1,true);
								}
							}
						},
						{
							display : "响应时间",
							name : "responseTime",
							minWidth : 20,
							width : "15%",
							align : "center",
							isSort : false,
							render : function(item){
								if(item.responseTime){
									return getSmpFormatDateByLong(item.responseTime*1,true);
								}
							}
						},
						{
							display : "发送",
							name : "request",
							minWidth : 20,
							width : "20%",
							align : "left",
							isSort : false
						},
						{
							display : "响应",
							name : "response",
							minWidth : 20,
							width : "20%",
							align : "left",
							isSort : false
						}
					];
			//options['enabledEdit'] = true;
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['sortName'] = 'responseTime';
			options['sortOrder']='desc';
			MALQ_UI.setGridOptions(options, that.config.queryGridUrl);
			$('#grid').ligerGrid(options);
			that.cache.grid = $('#grid').ligerGetGridManager();
			$('#pageloading').hide();
		},
		search : function() {
			var that = this;
			var parms=[];
			if ($('#name').val()) {
				parms.push({
					name : 'like[name]',
					value : '%' + $('#name').val() + '%'
				});
			}
			if (date2stamp($("#starttime").val())) {
				parms.push({
					name : 'startwith[responseTime]',
					value : date2stamp(startDate($("#starttime").val()))
				});
			}
			if (date2stamp($("#endtime").val())) {
				parms.push({
					name : 'endwith[responseTime]',
					value : date2stamp(endDate($("#endtime").val()))
				});
			}
			if (date2stamp($("#requestStarttime").val())) {
				parms.push({
					name : 'startwith[requestTime]',
					value : date2stamp(startDate($("#requestStarttime").val()))
				});
			}
			if (date2stamp($("#requestEndtime").val())) {
				parms.push({
					name : 'endwith[requestTime]',
					value : date2stamp(endDate($("#requestEndtime").val()))
				});
			}
			if(date2stamp($("#starttime").val())-date2stamp($("#endtime").val())>0){
				alert("开始时间不能超过结束时间！");
				return;
			}
			if(date2stamp($("#requestStarttime").val())-date2stamp($("#requestEndtime").val())>0){
				alert("开始时间不能超过结束时间！");
				return;
			}
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		interactiveRecordDetail:function (id) {
			var url = root + "/pages/logmanager/TL_YB_logDetail.jsp";
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