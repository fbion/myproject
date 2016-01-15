/**
 * 日志查询
 * 
 * @author  by malq 
 * @date    2013-09-17
 */

$.ligerDefaults.Filter.operators.csm__date=['LessThan','GreaterThan'];//高级查询条件
var TonglianLogManager = function(){
	 this.tonglianLogGrid=null;
	 this.queryUrl = root+'/logJson/queryListPage.action';
	 this.getLogUrl = root+'/logJson/getObjectById.action';
	
	 this.subHeight = Math.round($(window).height() - ($(window).height() * 0.20));
	 this.subWidth  = Math.round($(window).width() - ($(window).width() * 0.25));
	 
	 this.dbName='log';
	 this.tableName='TL_jsonrpc_logs';
	 
	 this.states=[
	              			{text:'请选择...',id:''},
	              			{text:'成功',id:'SUCCESS'},
	              			{text:'失败',id:'FAIL'}
	              		];
};


TonglianLogManager.prototype = {
		init:function(){	
			var that = this;						
			 //布局
	        $("#layout1").ligerLayout({height: '8%'});

	        MALQ_CODE.getSelectByData($('#state'), that.states,130,120);
			$('#state').val('请选择...');
			
			//MALQ_UI.dateEdit($('#handleTime'));
			//MALQ_UI.dateEdit($('#handleTime2'));
	       
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
											{display : '方法名称',name : 'method',minWidth : 120, width:'15%', align : 'left'}, 																							
											
											{display : '请求时间',name : 'requestTime',minWidth : 180, width:'15%',align : 'center',  type: 'date', editor: MALQ_UI.getDateForEditor(true), 
													render : function(row){
													return (row.requestTime)?MALQ_CODE.utcToDate(row.requestTime,'yyyy-MM-dd HH:mm:ss'):'';
												}
											},
											
											{display : '响应时间',name : 'responseTime',minWidth : 180, width:'15%',align : 'center', type: 'date', editor: MALQ_UI.getDateForEditor(true),
												render : function(row){
													return (row.responseTime)?MALQ_CODE.utcToDate(row.responseTime,'yyyy-MM-dd HH:mm:ss'):'';
												}
											},
											
											{display : '请求状态',name : 'state', isSort:false, minWidth : 80, width:'10%', align : 'left', type: 'combobox',editor: MALQ_UI.getDataComboboxForEditor(that.states),
												render: function(row){
													var str = '';
													$(that.states).each(function(){
														if(this.id == row.state){
															str += this.text;
															return false;
														}
													});
													return str;
												}
											},
											
											{display : '请求参数',name : 'request',minWidth : 150, width:'20%',align : 'left'}, 
											
											{display : '响应参数',name : 'response',minWidth : 150, width:'20%', align : 'left'}		
				               ];
				
				 options['columns']=columns;
				 options['checkbox']=false;
				 options['sortName']='requestTime';
				 options['sortOrder']='desc';
				 options['toolbar']={items:myitems};
				 options['detail']={height:'auto', onShowDetail: that.show};
				 options['parms']=[{name: 'dbName', value: that.dbName},{name:'tableName', value:that.tableName}];
							
				 MALQ_UI.setGridOptions(options,  that.queryUrl);		
				 that.tonglianLogGrid = $("#gridDiv").ligerGrid(options);						 
		         that.tonglianLogGrid =$("#gridDiv").ligerGetGridManager();
		                 		        	
	        	$('#pageloading').hide();        
	            $('#gridDiv').css('width','100%'); 
	            $('#gridDiv').css('top','3px'); 
		 },


		 f_search : function (){
			 var that = tonglianLogManager;
			 	var parms = {dbName:that.dbName, tableName:that.tableName};
			 	
	        	var name = $('#queryDiv').find('#method').val();	      	
	        	if(name){
	        		parms['requestParam.like.method'] = '^.*'+ name +'.*$';	        		        		      		
	        	}
	        	
	        	var state = $('#queryDiv').find('#state').val();        	
	        	if(state && state != '请选择...'){
	        		parms['requestParam.equal.state']=$('#queryDiv').find('#state_val').val();	        		
	        	}
	        	
	        	that.tonglianLogGrid.set('parms', parms);	        		        	        	
	        	//alert(JSON.stringify(that.tonglianLogGrid.get('parms')));	        	
	        	that.tonglianLogGrid.changePage('first');      	
	        	that.tonglianLogGrid.loadData(true);
	        },
	       
	        ff_search : function (){
	        	var that = tonglianLogManager;
			 	var parms = [{name: 'dbName', value: that.dbName},{name:'tableName', value:that.tableName}];
			 	
			 	that.tonglianLogGrid.showMyFilter(parms);  
	        },
	        
	        allShow: function(){
	        	var rows = $('.l-grid-row-cell-detailbtn');
	        	$(rows).each(function(){
	        		this.click();
	        	});
	        	
	        },
	       
	        show : function (row, p){
	        	var that = tonglianLogManager;	        	
	        	$(p).append('<img src="../loading.gif" height="30" width="30"/>');        	
	        	JAjax(that.getLogUrl,{'id':row.id, 'dbName': that.dbName, 'tableName':that.tableName}, "json", function(data){ 
					if(data){
						//alert(JSON.stringify(data));						
						$(p).text('');						
						var html = '<div>'
												+'<div style="float: left;width:49%;">请求参数：</br><textarea rows="24" cols="95">' + JsonUtil.convertToString(eval('(' +data.request + ')')) + '</textarea></div>'
												+'<div style="float: right;width:49%;">响应参数：</br><textarea rows="24" cols="95">' + JsonUtil.convertToString(eval('(' + data.response + ')')) + '</textarea></div>'	
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
	                    JAjax(tonglianLogManager.delUrl,{'ids':ids}, 'json', function(data){
	                    	if(data){	                    		
	                    		JSWK.clewBox(data,'clew_ico_succ');
	                    		tonglianLogManager.tonglianLogGrid.loadData();//刷新grid        					
	                    	}
	                    	                          			
	                	}, function(){JSWK.clewBox('删除数据时发生异常','clew_ico_fail',CSM_failAlertTime);}, true);
	        	   }
	        	  
	           });
	       }
	      
};

 
 
$(function(){			
    var tonglianLogManager=new TonglianLogManager();	
    tonglianLogManager.init();           
    tonglianLogManager.showGrid();   
    window.tonglianLogManager=tonglianLogManager;			
});
    	             