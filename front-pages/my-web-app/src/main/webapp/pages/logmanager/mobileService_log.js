/**
 * 日志查询
 * 
 * @author  by malq 
 * @date    2013-09-17
 */
$.ligerDefaults.Filter.operators.csm__date=['LessThan','GreaterThan'];//高级查询条件


var Log4jManager = function(){
	this.log4jGrid=null;
	this.queryUrl = root+'/logmanager/queryMobileLog.action';
	this.getLogUrl = root+'/logmanager/queryMobileLogDetail.action';
	
	this.tab = null;
    this.accordion = null;
   
	this.log_level=[{text:'failed',id:'failed'},{text:'success',id:'success'}];
	
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
		
		$('form').ligerForm({inputWidth:130});	
					
	 }, 
	 
	 f_heightChanged : function(options){
		 var that = this;
         if (that.tab)
             that.tab.addHeight(options.diff);
         if (that.accordion && options.middleHeight - 24 > 0)
             that.accordion.setHeight(options.middleHeight - 24);
     },
     
	 		
	showGrid:function(){ 
		var that = this; 
		//////////////生成表格上面的按钮菜单///////////////////////////////
		var myitems = [{click:that.allShow,icon:'communication'}];
		//////////////生成表格上面的按钮菜单////////////////////////////
		
		var options = {};
		var columns = [{
						display : '记录时间',
						name : 'handleTime',
						minWidth : 180, 
						width:'15%', 
						align : 'center', 
						type: 'date'
						},
						{
						display : '接口编码',
						name : 'action',
						minWidth : 80, 
						width:'10%', 
						align : 'left', 
						type: 'combobox'
						},
						{
						display : '调用状态',
						name : 'state',
						minWidth : 80, 
						width:'10%',
						align : 'center',
						type: 'combobox',
						//editor: MALQ_UI.getDataComboboxForEditor(that.log_level),									
						render: function(row){
							if(row.state == 'failed')
								return '<span style="color:red">'+row.state+'</span>';
							else if(row.state == 'success')
								return '<span style="color:#999933">'+row.state+'</span>';
							else
								return '<span>'+row.state+'</span>';
							}
						},
		
						{
							display : '请求内容',
							name : 'request', 
							isSort:false, 
							minWidth : 80, 
							width:'20%', 
							align : 'left'
						},
						
						{
						display : '响应内容',
						name : 'response', 
						isSort:false,
						minWidth : 80, 
						width:'25%', 
						align : 'left'
						}];
						options['columns']=columns;
						options['checkbox']=false;
						options['sortName']='handleTime';
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
			parms.push({name : 'equal[action]', value : systemName});      		
		}

		var bizzOrderNo = $('#queryDiv').find('#bizzOrderNo').val();	      	
		if(bizzOrderNo){
			parms.push({name : 'equal[bizzOrderNo]', value : bizzOrderNo});         		
		}
		
		//logTime
		var beforTime = $('#queryDiv').find('#beforTime').val();	      	
		if(beforTime){        		
			parms.push({name : 'startwith[handleTime]', value :beforTime});         		
		}
		var endTime = $('#queryDiv').find('#endTime').val();	      	
		if(endTime){         		
			parms.push({name : 'endwith[handleTime]', value : endTime});         		
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
    		if(data){
    			//alert(JSON.stringify(data));						
    			$(p).text('');
    			//$(p).append($('<div>ID：' + data.id + '</div>').css('margin', 10)); 
    			var html = '<div>'
					+'<div style="float: left;width:49%;">请求参数：</br><textarea rows="20" cols="95">' + JsonUtil.convertToString(eval('(' +data.request + ')')) + '</textarea></div>'
					+'<div style="float: right;width:49%;">响应参数：</br><textarea rows="20" cols="95">' + JsonUtil.convertToString(eval('(' + data.response + ')')) + '</textarea></div>'	
					+'</div>';

    			$(p).append($(html).css('margin', 10));
    			
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
    log4jManager.showGrid();
    window.log4jManager=log4jManager;	
    
});