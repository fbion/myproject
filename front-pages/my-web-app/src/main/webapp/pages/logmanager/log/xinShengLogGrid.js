/**
 * 日志查询
 * 
 * @author  by malq 
 * @date    2013-09-17
 */

$.ligerDefaults.Filter.operators.csm__date=['LessThan','GreaterThan'];//高级查询条件
var XinShengLogManager = function(){
	 this.xinShengLogGrid=null;
	 this.queryUrl = root+'/logJson/queryListPage.action';
	 this.getLogUrl = root+'/logJson/getObjectById.action';
	
	 this.subHeight = Math.round($(window).height() - ($(window).height() * 0.20));
	 this.subWidth  = Math.round($(window).width() - ($(window).width() * 0.25));
	 
	 this.dbName='log';
	 this.tableName='TL_transSettle_webservice_logs';
	 
	 this.states=[{id:'',text:'请选择...'},{id:'SUCCESS',text:'成功'},{id:'FAILED',text:'失败'}];
	
};


XinShengLogManager.prototype = {
		init:function(){	
			var that = this;						
			 //布局
	        $("#layout1").ligerLayout({height: '8%'});

	        MALQ_CODE.getSelectByData($('#state'), that.states,130,120);
			$('#state').val('请选择...');
			
	       
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
											{display : '接口编号',name : 'serviceCode',minWidth : 120, width:'10%', align : 'left'	
											},
											{display : '接口名称',name : 'serviceName',minWidth : 120, width:'10%', align : 'left'},
											
											{display : '请求时间',name : 'requestTime',minWidth : 120, width:'10%',align : 'center', type: 'date', editor: MALQ_UI.getDateForEditor(true),
												render : function(row){
													return (row.requestTime)?MALQ_CODE.utcToDate(row.requestTime,'yyyy-MM-dd HH:mm:ss'):'';
												}
											},
											
											{display : '请求状态',name : 'state', isSort:false, minWidth : 80, width:'10%', align : 'center', type: 'combobox',editor: MALQ_UI.getDataComboboxForEditor(that.states)},
											
											{display : '请求参数',name : 'requestXml', minWidth : 150, width:'30%',align : 'left',
												render : function(row){							
													return row.requestXml.replace(/</ig,'&lt;').replace(/>/ig,'&gt;');
												}
											}, 
											
											{display : '响应参数',name : 'responseXml',minWidth : 150, width:'30%', align : 'left', 
												render : function(row){
													return row.responseXml.replace(/</ig,'&lt;').replace(/>/ig,'&gt;');
												}
											}		
				               ];
				
				 options['columns']=columns;
				 options['checkbox']=false;
				 options['sortName']='requestTime';
				 options['sortOrder']='desc';
				 options['toolbar']={items:myitems};
				 options['detail']={height:'auto', onShowDetail: that.show};
				 options['parms']=[{name: 'dbName', value: that.dbName},{name:'tableName', value:that.tableName}];
							
				 MALQ_UI.setGridOptions(options,  that.queryUrl);		
				 that.xinShengLogGrid = $("#gridDiv").ligerGrid(options);						 
		         that.xinShengLogGrid =$("#gridDiv").ligerGetGridManager();						 
	        		        	
	        	$('#pageloading').hide();
	            $('#gridDiv').css('width','100%'); 
	            $('#gridDiv').css('top','3px'); 
		 },


		 f_search : function (){
			 var that = this;
			 var parms = {dbName:that.dbName, tableName:that.tableName};
 
	        	var name = $('#queryDiv').find('#serviceCode').val();	      	
	        	if(name){
	        		//parms['requestParam.like.action'] = '^.*'+ name +'.*$';	        		
	        		parms['requestParam.equal.serviceCode'] = name;	        		
	        	}
	        	
	        	var level = $('#queryDiv').find('#state_val').val();	      	
	        	if(level && level != '请选择...'){
	        		parms['requestParam.equal.state']=level;	        		
	        	}
	        	
	        	that.xinShengLogGrid.set('parms', parms);
	        		        	        	
	        	//alert(JSON.stringify(that.xinShengLogGrid.get('parms')));
	        	
	        	that.xinShengLogGrid.changePage('first'); 
	        	
	        	that.xinShengLogGrid.loadData(true);
	        },
	       
	        ff_search : function (){
			 	var that = this;
			 	var parms = [{name: 'dbName', value: that.dbName},{name:'tableName', value:that.tableName}];
			 	that.xinShengLogGrid.showMyFilter(parms);  
	        },
	        
	        allShow: function(){
	        	var rows = $('.l-grid-row-cell-detailbtn');
	        	$(rows).each(function(){
	        		this.click();
	        	});
	        	
	        },
	       
	        show : function (row, p){
	        	var that = xinShengLogManager;	        	
	        	$(p).append('<img src="../loading.gif" height="30" width="30"/>');        	
	        	JAjax(that.getLogUrl,{'id':row.id, 'dbName': that.dbName, 'tableName':that.tableName}, "json", function(data){ 
					if(data){
						//alert(JSON.stringify(data));						
						$(p).text('');
						//$(p).append($('<div>接口编号：' + data.action + ' </div>').css('margin', 10)); 
						//$(p).append($('<div>发布时间：' + data.handleTime + '</div>').css('margin', 10)); 
						//$(p).append($('<div>状态：' + data.state + '</div>').css('margin', 10));
						var html = '<div>'
												+'<div style="float: left;width:49%;">请求参数：</br><textarea rows="18" cols="95">' + data.requestXml + '</textarea></div>'
												+'<div style="float: right;width:49%;">响应参数：</br><textarea rows="18" cols="95">' + data.responseXml + '</textarea></div>'	
										+'</div>';
						
						$(p).append($(html).css('margin', 10));
						
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
	                    JAjax(xinShengLogManager.delUrl,{'ids':ids}, 'json', function(data){
	                    	if(data){	                    		
	                    		JSWK.clewBox(data,'clew_ico_succ');
	                    		xinShengLogManager.xinShengLogGrid.loadData();//刷新grid        					
	                    	}
	                    	                          			
	                	}, function(){JSWK.clewBox('删除数据时发生异常','clew_ico_fail',CSM_failAlertTime);}, true);
	        	   }
	        	  
	           });
	       }
	      
};

 
 
$(function(){			
    var xinShengLogManager=new XinShengLogManager();	
    xinShengLogManager.init();           
    xinShengLogManager.showGrid();   
    window.xinShengLogManager=xinShengLogManager;			
});
    	             