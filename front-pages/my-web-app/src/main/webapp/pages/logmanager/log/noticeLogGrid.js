/**
 * 日志查询
 * 
 * @author  by malq 
 * @date    2013-09-17
 */

$.ligerDefaults.Filter.operators.csm__date=['LessThan','GreaterThan'];//高级查询条件
var WebApiLogManager = function(){
	 this.webApiLogGrid=null;
	 this.queryUrl = root+'/logJson/queryListPage.action';
	 this.getLogUrl = root+'/logJson/getObjectById.action';
	
	 this.subHeight = Math.round($(window).height() - ($(window).height() * 0.20));
	 this.subWidth  = Math.round($(window).width() - ($(window).width() * 0.25));
	 
	 this.dbName='mongo_ceshi';
	 this.tableName='webservice_log';
	 
	 this.states=[
        			{text:'请选择...',id:''},
        			{text:'成功',id:'SUCCESS'},
        			{text:'失败',id:'FAIL'}
        		];
	
};


WebApiLogManager.prototype = {
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
											{display : '接口编号',name : 'action',minWidth : 120, width:'15%', align : 'left',
												render: function(row){
													var str = row.action+'_';
													$(that.log_type).each(function(){
														if(this.id == row.action){
															str += this.text;
															return false;
														}
													});
													return str;
												}
											},
											
											{display : '请求时间',name : 'handleTime',minWidth : 120, width:'10%',align : 'center'},
											
											{display : '请求状态',name : 'state', isSort:false, minWidth : 80, width:'10%', align : 'left'},
											
											{display : '请求参数',name : 'request',minWidth : 150, width:'30%',align : 'left'}, 
											
											{display : '响应参数',name : 'response',minWidth : 150, width:'30%', align : 'left'}			
				               			];
				
				 options['columns']=columns;
				 options['checkbox']=false;
				 options['sortName']='handleTime';
				 options['sortOrder']='desc';
				 options['toolbar']={items:myitems};
				 options['detail']={height:'auto', onShowDetail: that.show};
				 options['parms']=[{name: 'dbName', value: that.dbName},{name:'tableName', value:that.tableName}];
							
				 MALQ_UI.setGridOptions(options,  that.queryUrl);		
				 that.webApiLogGrid = $("#gridDiv").ligerGrid(options);						 
		         that.webApiLogGrid =$("#gridDiv").ligerGetGridManager();
		         	
	        	$('#pageloading').hide();       
	            $('#gridDiv').css('width','100%'); 
	            $('#gridDiv').css('top','3px'); 
		 },


		 f_search : function (){
			 	var parms = {};
 
	        	var name = $('#queryDiv').find('#action_val').val();	      	
	        	if(name){
	        		//parms['requestParam.like.action'] = '^.*'+ name +'.*$';	        		
	        		parms['requestParam.equal.action'] = name;	        		
	        	}
	        	
	        	var level = $('#queryDiv').find('#level').val();	      	
	        	if(level && level != '请选择...'){
	        		parms['requestParam.equal.level']=level;	        		
	        	}
	        	
	        	webApiLogManager.webApiLogGrid.set('parms', parms);
	        		        	        	
	        	//alert(JSON.stringify(webApiLogManager.webApiLogGrid.get('parms')));
	        	
	        	webApiLogManager.webApiLogGrid.changePage('first'); 
	        	
	        	webApiLogManager.webApiLogGrid.loadData(true);
	        },
	       
	        ff_search : function (){
			 	var parms = {};
			 	webApiLogManager.webApiLogGrid.showMyFilter(parms);  
	        },
	        
	        allShow: function(){
	        	var rows = $('.l-grid-row-cell-detailbtn');
	        	$(rows).each(function(){
	        		this.click();
	        	});
	        	
	        },
	       
	        show : function (row, p){
	        	var that = webApiLogManager;	        	
	        	$(p).append('<img src="../loading.gif" height="30" width="30"/>');        	
	        	JAjax(that.getLogUrl,{'id':row.id, 'dbName': that.dbName, 'tableName':that.tableName}, "json", function(data){ 
					if(data){
						//alert(JSON.stringify(data));						
						$(p).text('');
						//$(p).append($('<div>接口编号：' + data.action + ' </div>').css('margin', 10)); 
						//$(p).append($('<div>发布时间：' + data.handleTime + '</div>').css('margin', 10)); 
						//$(p).append($('<div>状态：' + data.state + '</div>').css('margin', 10));
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
	                    JAjax(webApiLogManager.delUrl,{'ids':ids}, 'json', function(data){
	                    	if(data){	                    		
	                    		JSWK.clewBox(data,'clew_ico_succ');
	                    		webApiLogManager.webApiLogGrid.loadData();//刷新grid        					
	                    	}
	                    	                          			
	                	}, function(){JSWK.clewBox('删除数据时发生异常','clew_ico_fail',CSM_failAlertTime);}, true);
	        	   }
	        	  
	           });
	       }
	      
};

 
 
$(function(){			
    var webApiLogManager=new WebApiLogManager();	
    webApiLogManager.init();           
    webApiLogManager.showGrid();   
    window.webApiLogManager=webApiLogManager;			
});
    	             