var queryInteractiveRecordDetailUrl = root+'/logmanager/queryInteractiveRecordDetail.action';
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
	    	$("#createTime").html(getSmpFormatDateByLong(data[0].createTime*1,true));
	    	$("#saveDate").html(data[0].saveDate);	    	
	    	$("#operCode").html(data[0].operCode);
	    	$("#storeCode").html(data[0].storeCode);
	    	$("#recordSystem").html(data[0].recordSystem);	    	
	    	$("#jsonData").html('<textarea rows="5" cols="100">'+(data[0].jsonData?data[0].jsonData:"") +'</textarea>');
	    	$("#returnData").html('<textarea rows="5" cols="100">'+(data[0].returnData?data[0].returnData:"")+'</textarea>');
	    }
	});
}