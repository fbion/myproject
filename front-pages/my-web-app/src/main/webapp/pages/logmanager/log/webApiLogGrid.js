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
	 this.tableName='webapi_service_log';
	 
	 this.states=[{id:'',text:'请选择...'},{id:'success',text:'成功'},{id:'failed',text:'失败'}];
	 this.log_type=[{text:'请选择...',id:''},                 
	    {id:'S1001', text:'用户登录'},
	 	{id:'S1002', text:'用户登出'},
	 	{id:'S1003', text:'修改密码'},
	 	{id:'S1004', text:'查询登录信息'},
	 	{id:'S1005', text:'联名卡详情'},
	 	{id:'S1006', text:'联名卡交易记录'},
	 	{id:'S1007', text:'联名卡交易详情'},
	 	{id:'S1008', text:'车辆列表'},
	 	{id:'S1009', text:'车辆详情'},
	 	{id:'S1010', text:'账户交易记录列表'},
	 	{id:'S1011', text:'账户交易详情'},
	 	{id:'S1012', text:'查询收藏门店'},
	 	{id:'S1013', text:'删除收藏门店'},
	 	{id:'S1014', text:'查询收藏优惠'},
	 	{id:'S1015', text:'删除收藏优惠'},
	 	{id:'S1016', text:'搜索门店或优惠'},
	 	{id:'S1017', text:'查看门店详情'},
	 	{id:'S1018', text:'查看优惠详情'},
	 	{id:'S1019', text:'添加收藏门店'},
	 	{id:'S1020', text:'添加收藏优惠'},
	 	{id:'S1021', text:'意见反馈'},
	 	{id:'S1022', text:'上传位置'},
	 	{id:'S1023', text:'查询类型'},
	 	{id:'S1024', text:'版本更新'},
	 	{id:'S1025', text:'通讯录上传'}
	       ];
	
};


WebApiLogManager.prototype = {
		init:function(){	
			var that = this;						
			 //布局
	        $("#layout1").ligerLayout({height: '8%'});

	        MALQ_CODE.getSelectByData($('#action'), that.log_type,130,120);
			$('#action').val('请选择...');
			
			MALQ_CODE.getSelectByData($('#state'), that.states,130,120);
			$('#state').val('请选择...');
			
			//MALQ_UI.dateEdit($('#handleTime'));
			//MALQ_UI.dateEdit($('#handleTime2'));
//			e.ligerDateEditor({ 
//	    		format: (f && f.length>0) ? f : 'yyyy-MM-dd',
//	    		showTime: st ? true : false
//	    		//label: '格式化日期', 
//	    		//labelWidth: 100, 
//	    		//labelAlign: 'center' 
//	    	});
	    	//sd = (sd && sd.length>4) ? sd : e.ligerGetDateEditorManager().getFormatDate(new Date());
	    	//e.ligerGetDateEditorManager().setValue(sd);   
	       
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
											{display : '接口编号',name : 'action',minWidth : 120, width:'15%', align : 'left',type: 'combobox',editor: MALQ_UI.getDataComboboxForEditor(that.log_type),
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
											
											{display : '请求时间',name : 'handleTime',minWidth : 180, width:'15%',align : 'center', type: 'date', editor: MALQ_UI.getDateForEditor(true)},
											
											{display : '请求状态',name : 'state', isSort:false, minWidth : 80, width:'10%', align : 'center', type: 'combobox',editor: MALQ_UI.getDataComboboxForEditor(that.states),
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
			   var that = this;
			 	var parms = {dbName:that.dbName, tableName:that.tableName};
//	        	var name = $('#queryDiv').find('#action_val').val();	      	
//	        	if(name){
//	        		parms['requestParam.like.action'] = '^.*'+ name +'.*$';	        		        		
//	        	}
	        	
	        	var action = $('#queryDiv').find('#action').val();	      	
	        	if(action && action != '请选择...'){
	        		parms['requestParam.equal.action']=$('#queryDiv').find('#action_val').val();	        		
	        	}
	        	var state = $('#queryDiv').find('#state').val();	      	
	        	if(state && state != '请选择...'){
	        		parms['requestParam.equal.state']=$('#queryDiv').find('#state_val').val();	        		
	        	}
	        	
	        	that.webApiLogGrid.set('parms', parms);
	        		        	        	
	        	//alert(JSON.stringify(that.webApiLogGrid.get('parms')));
	        	
	        	that.webApiLogGrid.changePage('first'); 
	        	
	        	that.webApiLogGrid.loadData(true);
	        },
	       
	        ff_search : function (){
	        	var that = this;
	        	var parms = [{name: 'dbName', value: that.dbName},{name:'tableName', value:that.tableName}];
			 	that.webApiLogGrid.showMyFilter(parms);  
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
    	             
       
        
        