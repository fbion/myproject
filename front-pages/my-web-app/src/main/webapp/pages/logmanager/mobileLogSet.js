
var LogSet=function(){	
	this.delUrl = root + '/logmanager/deleteMobileApiServiceLogs.action';	
};

LogSet.prototype={
		
	 //初始化页面信息
	 init:function(){
		 var that = this;
		 $('#handleTime').ligerDateEditor({format: 'yyyy-MM-dd'});
		 //that.log_level.unshift({text:'请选择...',id:''});
	     MALQ_CODE.getSelectByData($('#status'), [{text:'success',id:'success'},{text:'failed',id:'failed'}],130,120);
	     $('#status').val('请选择...');	 
	  },
	 
	 delLog : function(){
		 var that = this;
		 var logTime = $('#handleTime').val();
		 if(!logTime){
			 $.ligerDialog.alert('请输入日期, 系统只允许清除30天前的日志信息', '提示', 'warn');return;
		 }
		 var long = new Date(MALQ_CODE.date2dateBystr(-30)).getTime();
		 var long2 = new Date(logTime).getTime();
		 if(long2 > long){
			 $.ligerDialog.alert('系统只允许清除30天前的日志信息', '提示', 'warn');return;		 
		 }		
        //alert(handleTime);return;
		 $.ligerDialog.confirm('日志信息被删除后将不可恢复, 确定要删除吗？', function (yes){
      	   if(yes){
      		 var status=$('#status').val();
      		 var code=$('#code').val();
      		 var parms ={'code':code, 'dateStr':logTime+' 00:00:00', 'status':status};
                  JAjax(that.delUrl, parms, 'json', function(data){
                  	if(data){
                  		JSWK.clewBox(data, 'clew_ico_succ');               							
                  	}             	                          			
              	}, function(){JSWK.clewBox('删除数据时发生异常','clew_ico_fail');}, true);
      	   }
      	  
         });
	 },
	
	 initTitleBlock : function(){	
		 var that = this;
		 var imgUrl = '../../images/settings.gif';		 
		 MALQ_CODE.titleBlock($('#split_div'), '日志信息清除', imgUrl);
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




