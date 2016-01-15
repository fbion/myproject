
var LogSet=function(){	
	this.grid=null;
	this.queryUrl = root+'/logmanager/querySystemLogSetList.action';
	this.delUrl = root + '/logmanager/deleteLogs.action';
	this.saveUrl = root + '/logmanager/saveOrUpdateLogSet.action';
	this.updateUrl = root + '/logmanager/saveOrUpdateLogSet.action';
	
};

LogSet.prototype={
		
	 //初始化页面信息
	 init:function(){
		 var that = this;
		 var Rows=[];
		 		 
		 var columns=[
		              				{display : "系统标识", align : 'left', name : "systemName", width : 120},
		              				{ display: '日志级别', columns:
		              					[		                          		                           
				                           { display: 'DEBUG', name: 'level',  width : 90,  
				                        	   render : function(row){
				                        		   		return '<input onchange="logSet.setLogLevel(\''+row.id+'\',\''+row.systemName+'\',\'DEBUG\');" id="'+row.systemName+'_radio" name="'+row.systemName+'_radio" type="radio" '+(row.level=='DEBUG'?'checked="checked"':'')+'/>';
				                        	  } 
				                           },
				                           { display: 'INFO', name: 'level',  width : 90,  
				                        	   render : function(row){
				                        		   return '<input onchange="logSet.setLogLevel(\''+row.id+'\',\''+row.systemName+'\',\'INFO\');" id="'+row.systemName+'_radio" name="'+row.systemName+'_radio" type="radio" '+(row.level=='INFO'?'checked="checked"':'')+'/>';
				                        	   } 
				                           },
				                           { display: 'WARN', name: 'level', width :90, 
				                        	   render : function(row){
				                        		   return '<input onchange="logSet.setLogLevel(\''+row.id+'\',\''+row.systemName+'\',\'WARN\');" id="'+row.systemName+'_radio" name="'+row.systemName+'_radio" type="radio" '+(row.level=='WARN'?'checked="checked"':'')+'/>';
				                        	   } 
				                           },
				                           { display: 'ERROR', name: 'level', width : 90, 
				                        	   render : function(row){
				                        		   return '<input onchange="logSet.setLogLevel(\''+row.id+'\',\''+row.systemName+'\',\'ERROR\');" id="'+row.systemName+'_radio" name="'+row.systemName+'_radio" type="radio" '+(row.level=='ERROR'?'checked="checked"':'')+'/>';
				                        	   } 
				                           }
			                       ]
			                   }
		              ];
		 var options = {};
		 options['columns']=columns;
		 options['height']=230;
		 options['width']=550;
		 options['usePager']=false;
		 options['columns']=columns;
		 //options['toolbar']={items:[{click:that.initLogLevel,icon:'communication',text:'初始化日志级别'}]};

		 JAjax(that.queryUrl,{}, 'json', function(data){
		    //alert(JSON.stringify(data)); 
           	if(data && data.data.length>0){
	           	 options['data']={Rows:data.data};
	           	 that.grid = $("#logSetGrid").ligerGrid(options);						 
	           	 that.grid = $("#logSetGrid").ligerGetGridManager();
           	}else{
	           	 $(parent.logSysData).each(function(){
	    			 var rdd={};
	    			 rdd['systemName']=this.id;
	    			 rdd['level']='INFO';    			
	    			 Rows.push(rdd);	    			
	    			 var parms ={'model.systemName':this.id,'model.level':'INFO'};
	    			 JAjax(that.saveUrl, parms, 'json', function(data){			 
	    			 }, function(){JSWK.clewBox('初始化日志设置时发生异常','clew_ico_fail',3000);}, true);	    			 
	    		 });  
	           	 var data ={Rows:Rows};
	           	 options['data']=data;
	           	 that.grid = $("#logSetGrid").ligerGrid(options);						 
	           	 that.grid = $("#logSetGrid").ligerGetGridManager();
           	}   
		 }, function(){JSWK.clewBox('查询数据时发生异常','clew_ico_fail',3000);}, true);
				 
		  $('#handleTime').ligerDateEditor({format: 'yyyy-MM-dd'});
		 
	 },
	 setLogLevel : function(id, name, val){
		 var that = this;
		 var parms ={'model.id':id,'model.systemName':name,'model.level':val};
		 JAjax(that.updateUrl, parms, 'json', function(data){
		 }, null, true);
	 },

	 delLog : function(){
		 var that = this;
		 var logTime = $('#handleTime').val();
		 if(!logTime){
			 $.ligerDialog.alert('请输入日期, 系统只允许清除3天前的日志信息', '提示', 'warn');return;
		 }
		 var long = new Date(MALQ_CODE.date2dateBystr(-3)).getTime();
		 var long2 = new Date(logTime).getTime();
		 if(long2 > long){
			 $.ligerDialog.alert('系统只允许清除3天前的日志信息', '提示', 'warn');return;		 
		 }		
        //alert(handleTime);return;
		 $.ligerDialog.confirm('日志信息被删除后将不可恢复, 确定要删除吗？', function (yes){
      	   if(yes){ 
      		 var systemName=$('#systemName').val();
      		 var level=$('#level').val();
      		 var parms ={'dbName':'system_logs', 'dateStr':logTime+' 00:00:00', 'systemName':systemName, 'level':level};
                  JAjax(that.delUrl, parms, 'json', function(data){
                  	if(data){
                  		//$.ligerDialog.alert(data, '提示', 'warn');return;
                  		JSWK.clewBox(data,'clew_ico_succ');                							
                  	}             	                          			
              	}, function(){JSWK.clewBox('删除数据时发生异常','clew_ico_fail',CSM_failAlertTime);}, true);
      	   }
      	  
         });
	 },
	
	 initTitleBlock : function(){	
		 var that = this;
		 var imgUrl = '../../images/settings.gif';		 
		 MALQ_CODE.titleBlock($('#split_div'), '日志级别设置', imgUrl);
		 MALQ_CODE.titleBlock($('#split_div_togglebtn'), '日志信息清除', imgUrl, $('#otherDiv'));	 
		 //$('#otherDiv').hide();
		$('#split_div').css('width',(that.subWidth-100));			
		$('#split_div_togglebtn').css('width',(that.subWidth-100));
		$('#split_div_null').css('width',(that.subWidth-100));
		
	 }

};

$(function (){	
   var logSet=new LogSet(); 
   window.logSet=logSet;
   logSet.init();
   logSet.initTitleBlock();  
}); 




