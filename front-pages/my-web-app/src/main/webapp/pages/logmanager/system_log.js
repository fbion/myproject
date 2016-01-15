/**
 * 日志查询
 * 
 * @author  by malq 
 * @date    2013-09-17
 */
$.ligerDefaults.Filter.operators.csm__date=['LessThan','GreaterThan'];//高级查询条件

var logSysData=[{text:'InterfaceApp',id:'InterfaceApp'},
                {text:'CashierApp',id:'CashierApp'},
                {text:'AccountService',id:'AccountService'},
                {text:'BaseService',id:'BaseService'},
                //{text:'GateWayService',id:'GateWayService'},
                {text:'FastGateWayService',id:'FastGateWayService'},
                {text:'WebGateWayService',id:'WebGateWayService'},
                {text:'MobileApi',id:'MobileApi'},
                {text:'ProtalApp',id:'ProtalApp'},
                {text:'webApp',id:'webApp'}];

var Log4jManager = function(){
	this.log4jGrid=null;
	this.queryUrl = root+'/logmanager/querySystemLog.action';
	this.getLogUrl = root+'/logmanager/querySystemLogDetail.action';
	
	this.tab = null;
    this.accordion = null;
    this.tree = null;
	
	this.log_level=[{text:'ERROR',id:'ERROR'},{text:'WARN',id:'WARN'},{text:'INFO',id:'INFO'},{text:'DEBUG',id:'DEBUG'}];
	
	this.log_sysTem=logSysData;
};

Log4jManager.prototype = {
	init:function(){	
		var that = this;
		
		var height = $(".l-layout-center").height();
		//布局
        $("#layout1").ligerLayout({
            rightWidth: 220,
            isRightCollapse: true, 
            height: height,                   
            heightDiff: -34,
            space: 4,
            onHeightChanged: that.f_heightChanged
        });

        //Tab
        that.tab = $("#framecenter").ligerTab({ height: height, dragToMove :true });

        //面板
        that.accordion = $("#accordion2").ligerAccordion({ height: height - 24, speed: null });

        $(".l-link").hover(function (){
            $(this).addClass("l-link-over");
        }, function (){
            $(this).removeClass("l-link-over");
        });
        
        that.log_level.unshift({text:'请选择...',id:''});
        MALQ_CODE.getSelectByData($('#level'), that.log_level,130,120);
		$('#level').val('请选择...');
		
		that.log_sysTem.unshift({text:'请选择...',id:''});
		MALQ_CODE.getSelectByData($('#systemName'), that.log_sysTem,130,150);
		$('#systemName').val('请选择...');
		
		$('form').ligerForm({inputWidth:130});	
					
	 }, 
	 
	 f_heightChanged : function(options){
		 var that = this;
         if (that.tab)
             that.tab.addHeight(options.diff);
         if (that.accordion && options.middleHeight - 24 > 0)
             that.accordion.setHeight(options.middleHeight - 24);
     },
     
     initTree:function(){			 
		 var that = this;
		 
		 
		 var treeData =[					             
			               { isexpand: "true", text: "日志管理", children: [
			                                                            { url: '/pages/logmanager/logSet.jsp', text: '系统日志管理',systemid:'__-__system_log_set'},
			                                                            { url: '/pages/logmanager/mobileLogSet.jsp', text: '手机服务端日志管理',systemid:'__-__mobileApi_log_set'},
			                                                            { url: '/pages/logmanager/interfaceLogSet.jsp', text: '接口发布日志管理',systemid:'__-__interface_log_set'},
			                                                            { url: '/pages/logmanager/gatewayLogSet.jsp', text: '网关日志管理',systemid:'__-__gateway_log_set'}
			                                                            ]}					
			];
		 
			$("#tree").ligerTree({
				data : treeData,
				checkbox: false,
				slide: false,
				nodeWidth: 120,
				attribute: ['nodename', 'url'],
				onSelect: function (node){
					if (!node.data.url) return;
					MALQ_UI.open(root+node.data.url, subHeight-40, subWidth-150, node.data.text);
				}
			});
           
           that.tree = $("#tree").ligerGetTreeManager();
	 },
	 		
	showGrid:function(){ 
		var that = this; 
		//////////////生成表格上面的按钮菜单///////////////////////////////
		var myitems = [{click:that.allShow,icon:'communication'}];
		//////////////生成表格上面的按钮菜单////////////////////////////
		
		var options = {};
		var columns = [{
						display : '记录时间',
						name : 'logTime',
						minWidth : 180, 
						width:'15%', 
						align : 'center', 
						type: 'date', 
						editor: MALQ_UI.getDateForEditor(true)
						},
						{
						display : '系统标识',
						name : 'systemName',
						minWidth : 80, 
						width:'10%', 
						align : 'left', 
						type: 'combobox',
						editor: MALQ_UI.getDataComboboxForEditor(that.log_systemName)
						},
						
						{
						display : 'IP',
						name : 'ip',
						minWidth : 80, 
						width:'10%',
						align : 'left'
						}, 
						
						//{display : '所属线程',name : 'thread',minWidth : 30, width:'10%', align : 'left'},
						
						{
						display : '日志级别',
						name : 'level',
						minWidth : 80, 
						width:'10%',
						align : 'center',
						type: 'combobox',
						editor: MALQ_UI.getDataComboboxForEditor(that.log_level),									
						render: function(row){
							if(row.level == 'ERROR')
							return '<span style="color:red">'+row.level+'</span>';
						else if(row.level == 'WARN')
							return '<span style="color:#999933">'+row.level+'</span>';
						else
							return '<span>'+row.level+'</span>';
							}
						},
		
						{
							display : '执行位置',
							name : 'location', 
							isSort:false, 
							minWidth : 80, 
							width:'20%', 
							align : 'left'
						},
						
						{
						display : '日志内容',
						name : 'message', 
						isSort:false,
						minWidth : 80, 
						width:'25%', 
						align : 'left'
						}];
						options['columns']=columns;
						options['checkbox']=false;
						options['sortName']='logTime';
						options['sortOrder']='desc';
						options['toolbar']={items:myitems};
						options['detail']={height:'auto', onShowDetail: that.show};
						 //options['parms']=[{name: 'requestParam.inMap.userType', value: '0,5,6'}];//设置默认进来的请求参数   
									
						MALQ_UI.setGridOptions(options,  that.queryUrl);		
						that.log4jGrid = $("#gridDiv").ligerGrid(options);						 
						that.log4jGrid =$("#gridDiv").ligerGetGridManager();
						   						            
						$('#gridDiv').css('width','100%'); 
						$('#gridDiv').css('top','3px'); 
						
						$('#accordion2').css('height',$('#gridDiv').height());	
						$('#pageloading').hide();//加载图片	
	},


	f_search : function (){
		var parms = [];
		var systemName = $('#queryDiv').find('#systemName').val();
		if(systemName && systemName != '请选择...'){
			parms.push({name : 'equal[systemName]', value : systemName});      		
		}

		var level = $('#queryDiv').find('#level').val();	      	
		if(level && level != '请选择...'){
			parms.push({name : 'equal[level]', value : level});         		
		}
		
		//logTime
		var beforTime = $('#queryDiv').find('#beforTime').val();	      	
		if(beforTime){        		
			parms.push({name : 'startwith[logTime]', value :beforTime});         		
		}
		var endTime = $('#queryDiv').find('#endTime').val();	      	
		if(endTime){         		
			parms.push({name : 'endwith[logTime]', value : endTime});         		
		}
	
		log4jManager.log4jGrid.set('parms', parms);
		log4jManager.log4jGrid.changePage('first');   	
    	log4jManager.log4jGrid.loadData(true);
    },
   
    ff_search : function (){
	 	var parms = {};
	 	log4jManager.log4jGrid.showMyFilter(parms);  
    },
    
    allShow: function(){
    	var rows = $('.l-grid-row-cell-detailbtn');
    	$(rows).each(function(){
    		this.click();
    	});
    	
    },
   
    show : function (row, p){
    	var that = log4jManager;	        	
    	$(p).append('<img src="../loading.gif" height="30" width="30"/>');        	
    	JAjax(that.getLogUrl,{'id':row.id}, "json", function(data){ 
    		if(data[0]){
    			//alert(JSON.stringify(data[0]));						
    			$(p).text('');
    			$(p).append($('<div>执行位置：' + data[0].location + '</div>').css('margin', 10)); 
    			var message = data[0].message;						
    			message = message.replace(new RegExp(/(\r\n)|(\n)/g),'<br/>');
    			message = message.replace(new RegExp(/(\t)/g),'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');			        	         	
    			$(p).append($('<div>日志内容：' + message + '</div>').css('margin', 10));
    			var stackTrace = data[0].stackTrace;
    			stackTrace = stackTrace.replace(new RegExp(/(\r\n)|(\n)/g),'<br/>');
    			stackTrace = stackTrace.replace(new RegExp(/(\t)/g),'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');			        	
    			if(data[0].level == 'ERROR')
    				$(p).append($('<div>堆栈信息：<span style="color:red">' + stackTrace + '</span></div>').css('margin', 10));
    			else if(data[0].level == 'WARN')
    				$(p).append($('<div>堆栈信息：<span style="color:#999933">' + stackTrace + '</span></div>').css('margin', 10));
    			else
    				$(p).append($('<div>堆栈信息：' + stackTrace + '</div>').css('margin', 10));
	
    		}else{
    			$(p).text('没有数据信息');
    		}   			 			
    	}, 
    function(){$(p).text('初始化用户信息异常!');}, true);
    	   	
   }
};

$(function(){			
    var log4jManager=new Log4jManager();	
    log4jManager.init();           
    log4jManager.initTree();           
    log4jManager.showGrid(); 
    
    window.log4jManager=log4jManager;	
    
});