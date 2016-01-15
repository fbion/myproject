/**
 * 日志查询
 * 
 * @author  by malq 
 * @date    2013-09-17
 */

$.ligerDefaults.Filter.operators.csm__date=['LessThan','GreaterThan'];//高级查询条件
var Log4jManager = function(){
	 this.log4jGrid=null;
	 this.queryUrl = root+'/logJson/queryListPage.action';
	 this.getLogUrl = root+'/logJson/getObjectById.action';
	
	 this.subHeight = Math.round($(window).height() - ($(window).height() * 0.20));
	 this.subWidth  = Math.round($(window).width() - ($(window).width() * 0.25));
	 
	 this.log_level=[{text:'请选择...',id:''},{text:'ERROR',id:'ERROR'},{text:'WARN',id:'WARN'},{text:'INFO',id:'INFO'},{text:'DEBUG',id:'DEBUG'}];
	 this.log_systemName = parent.logSysData;
	 	
};


Log4jManager.prototype = {
		init:function(){	
			var that = this;						
			 //布局
	        $("#layout1").ligerLayout({height: '8%'});

	        MALQ_CODE.getSelectByData($('#level'), that.log_level,130,120);
			$('#level').val('请选择...');
			var sysTem=[];
			 $(that.log_systemName).each(function(i){
				 sysTem.push(this);
			 });
			sysTem.unshift({text:'请选择...',id:''});
			MALQ_CODE.getSelectByData($('#systemName'), sysTem,130);
			$('#systemName').val('请选择...');
			
	       
			$('form').ligerForm({inputWidth:130});				
				 
			$('#pageloading').hide();					
		 }, 
		 		
		  
		 showGrid:function(){
			 
			 var that = this;
			 
			 //////////////生成表格上面的按钮菜单///////////////////////////////
			 	var myitems = [{click:that.allShow,icon:'communication'}];
			 
			//////////////生成表格上面的按钮菜单////////////////////////////
			 	
			 	var options = {};
				var columns = [
										{display : '记录时间',name : 'logTime',minWidth : 180, width:'15%', align : 'center', type: 'date', editor: MALQ_UI.getDateForEditor(true)}, 																
										
										{display : '系统标识',name : 'systemName',minWidth : 80, width:'10%', align : 'left', type: 'combobox',editor: MALQ_UI.getDataComboboxForEditor(that.log_systemName)},
										
										{display : 'IP',name : 'ip',minWidth : 80, width:'10%',align : 'left'}, 
										
										//{display : '所属线程',name : 'thread',minWidth : 30, width:'10%', align : 'left'},
										
										{display : '日志级别',name : 'level',minWidth : 80, width:'10%',align : 'center',type: 'combobox',editor: MALQ_UI.getDataComboboxForEditor(that.log_level),									
											render: function(row){
												if(row.level == 'ERROR')
													return '<span style="color:red">'+row.level+'</span>';
												else if(row.level == 'WARN')
													return '<span style="color:#999933">'+row.level+'</span>';
												else
													return '<span>'+row.level+'</span>';
											}
										},
										
										{display : '执行位置',name : 'location', isSort:false, minWidth : 80, width:'20%', align : 'left'},
										
										{display : '日志内容',name : 'message', isSort:false,minWidth : 80, width:'25%', align : 'left'}		
				               ];
				
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
		       
	        	$('#pageloading').hide();//加载图片	            
	            $('#gridDiv').css('width','100%'); 
	            $('#gridDiv').css('top','3px'); 
		 },


		 f_search : function (){
			 	var parms = {};
 
//	        	var name = $('#queryDiv').find('#systemName').val();	      	
//	        	if(name){
//	        		//alert('infoApp'.search('^.*'+ name+'.*$'));
//	        		parms['requestParam.like.systemName'] = '^.*'+ name +'.*$';	        		
//	        	}
	        	var name = $('#queryDiv').find('#systemName').val();	      	
	        	if(name && name != '请选择...'){
	        		parms['requestParam.equal.systemName']=name;        		
	        	}
	        	
	        	var level = $('#queryDiv').find('#level').val();	      	
	        	if(level && level != '请选择...'){
	        		parms['requestParam.equal.level']=level;	        		
	        	}
	        	
	        	log4jManager.log4jGrid.set('parms', parms);
	        		        	        	
	        	//alert(JSON.stringify(log4jManager.log4jGrid.get('parms')));
	        	
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
						$(p).append($('<div>执行位置：' + data.location + '</div>').css('margin', 10)); 
						var message = data.message;						
						message = message.replace(new RegExp(/(\r\n)|(\n)/g),'<br/>');
						message = message.replace(new RegExp(/(\t)/g),'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');			        	         	
			        	$(p).append($('<div>日志内容：' + message + '</div>').css('margin', 10));
			        	var stackTrace = data.stackTrace;
			        	stackTrace = stackTrace.replace(new RegExp(/(\r\n)|(\n)/g),'<br/>');
			        	stackTrace = stackTrace.replace(new RegExp(/(\t)/g),'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');			        	
			        	if(data.level == 'ERROR')
			        		$(p).append($('<div>堆栈信息：<span style="color:red">' + stackTrace + '</span></div>').css('margin', 10));
			        	else if(data.level == 'WARN')
			        		$(p).append($('<div>堆栈信息：<span style="color:#999933">' + stackTrace + '</span></div>').css('margin', 10));
			        	else
			        		$(p).append($('<div>堆栈信息：' + stackTrace + '</div>').css('margin', 10));
						
					}else{
						$(p).text('没有数据信息');
					}   			 			
	    		}, function(){$(p).text('初始化用户信息异常!');}, true);
	        	
	        	    	
	        },
	       
	       //删除
	       del : function (){	 
	    	   var that = this;
	    	  	    	  
	           $.ligerDialog.confirm('确定要删除吗？', function (yes){
	        	   if(yes){ 	        		   
	                    JAjax(log4jManager.delUrl,{'ids':ids}, 'json', function(data){
	                    	if(data){	                    		
	                    		JSWK.clewBox(data,'clew_ico_succ');
	                    		log4jManager.log4jGrid.loadData();//刷新grid        					
	                    	}
	                    	                          			
	                	}, function(){JSWK.clewBox('删除数据时发生异常','clew_ico_fail',CSM_failAlertTime);}, true);
	        	   }
	        	  
	           });
	       }
	      
};

 
 
$(function(){			
    var log4jManager=new Log4jManager();	
    log4jManager.init();           
    log4jManager.showGrid();   
    window.log4jManager=log4jManager;			
});
    	             
       
        
        