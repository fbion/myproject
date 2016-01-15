var queryInteractiveRecordDetailUrl = root+'/logmanager/queryTL_YB_logDetail.action';
$(function() {
	queryInteractiveRecordDetail();
});

function queryInteractiveRecordDetail(){
	$.ajax({
	    url:queryInteractiveRecordDetailUrl,
	    dataType:"json",
	    data:{'id':parent.opt_id},
	    type:"POST",
	    success:function(data){
	    	$("#ID").html(data[0].id);
	    	$("#name").html(data[0].name);
	    	$("#className").html(data[0].className);
	    	$("#type").html(data[0].type);
	    	$("#requestTime").html(getSmpFormatDateByLong(data[0].requestTime*1,true));
	    	$("#responseTime").html(getSmpFormatDateByLong(data[0].responseTime*1,true));
	    	$("#request").val(data[0].request);
	    	$("#response").val(data[0].response);
	    }
	});
}