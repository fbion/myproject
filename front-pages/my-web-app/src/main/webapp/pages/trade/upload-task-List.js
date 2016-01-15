/**
 * 下载任务列表js
 */
(function() {
	var QueryList = function() {
		this.init();
	};
	QueryList.prototype = {
		config : {
			queryUrl : root + '/generaltrade/queryTaskList.action',
			uploadUrl : root + '/generaltrade/uploadFile.action'
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null,
			orderNo : null
		},
		init : function() {
			this.render();
			this.initSelectData();
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
		initSelectData:function(){
			// 1.初始化科目
			var w=140;
			MALQ_CODE.getSelectByData($("#status"), [{id:'1',text:'处理中'},{id:'2',text:'已完成'}], w, null);
			$("#status").ligerComboBox({width : w});
			$("#status").ligerGetComboBoxManager().selectValue('');
			
		},
		loadGird : function() {
			var that = this;
			var options = {};
			var myitems = [];
			var columns = [{
				display : '任务号',
				name : 'taskId',
				align : 'center',
				width:'13%',
				isSort : true
			},{
				display : '任务名称',
				name : 'taskName',
				align : 'center',
				width:'15%',
				isSort : true
			},{
				display : '任务状态',
				name : 'status',
				align : 'center',
				width:'15%',
				isSort : true,
				render : function(row) {
					return row.status=='2'?'已完成':'处理中';
				}
			},{
				display : '创建时间',
				name : 'createTime',
				align : 'center',
				width:'15%',
				isSort : true,
				render : function(row) {
					return getSmpFormatDateByLong(parseInt(row.createTime), true);
				}
			},{
				display : '创建人',
				name : 'createOp',
				align : 'center',
				width:'15%',
				isSort : true
			},{
				display : '操作',
				align : 'center',
				width:'10%',
				isSort : false,
				render : function(row) {
					if(row.status=='2')
						return "<a href=\"" + row.filePath + "\" target=\"_blank\">下载</a>";
					else
						return '';
				}
			} ];
			options['columns'] = columns;
			options['checkbox'] = false;
			options['frozenCheckbox'] = false;
			options['rownumbers']=true;
			options['sortName'] = 'createTime';
			options['sortOrder']='desc';
			
			MALQ_UI.setGridOptions(options, that.config.queryUrl);
			$('#gridList').ligerGrid(options);
			that.cache.grid = $('#gridList').ligerGetGridManager();
			$('#gridList').css('top', '0px');
			$('#pageloading').hide();// 加载图片
		},

		// 一般查询
		search : function() {
			var that = this;
			var parms = {};
			var taskId=$('#taskId').val();
			var status=$("#status").ligerGetComboBoxManager().getValue();
			var taskName=$("#taskName").val();
			var createTimeStart=$("#createTimeStart").val();
			var createTimeEnd=$("#createTimeEnd").val();	
			if (taskId != "") {
				parms['equal[taskId]'] = taskId;
			}
			if (status != "") {
				parms['equal[status]'] = status;
			}
			if (taskName != "") {//订单状态
				parms['like[taskName]'] = taskName;
			}
			if (createTimeStart) {//交易开始时间-记账时间
				parms['startwith[createTime]'] = date2stamp(startDate(createTimeStart));
			}
			if (createTimeEnd) {//交易结束时间-记账时间
				parms['endwith[createTime]'] = date2stamp(endDate(createTimeEnd));
			}
			that.cache.grid.set('parms', parms);
			that.cache.grid.changePage('first');
			that.cache.grid.loadData(true);
		},
		
		// 重置查询条件
		reset : function() {
			self.parent.indexPage.systemToNewPermSystemName="";
			window.location='upload-task-List.jsp';
		},
		alert : function(mes, css) {
			JSWK.clewBox(mes, css, failShowTime);
		}
	};

	$(document).ready(function() {
		window.queryList = new QueryList();
	});
})();