//数据对象模板
var AccountCurrentQueryUrl = root+'/account/getAccountCurrent.action';
$(function() {
	parent.$("#naveBar").append("<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a href=\"#\">账户交易详情</a></li>");
	AccountCurrentQuery(id);
});

/* Ajax查询账户余额方法 */
function AccountCurrentQuery(id) {
	$.ajax({
	    url:AccountCurrentQueryUrl,
	    dataType:"json",
	    data:{'id':id},
	    type:"POST",
	    success:function(data){
				$('#orderANumber').html(data.data[0].orderNo);
				$('#accountMoney').html(data.data[0].bookAccountMoney);
				if(data.data[0].bookAccountType=='deduction'){
					$('#bookAccountType').html('支出');
				}
				if(data.data[0].bookAccountType=='recorded'){
					$('#bookAccountType').html('收入');
				}
				if(data.data[0].bookAccountType=='frozen'){
					$('#bookAccountType').html('冻结');
				}
				if(data.data[0].bookAccountType=='unfrozen'){
					$('#bookAccountType').html('解冻');
				}
				$('#accountTime').html(
					getSmpFormatDateByLong(data.data[0].bookAccountTime*1,true)
				);
				$('#commentsOnAForm').html(data.data[0].orderRemarks);
	    },
	    error:function(data){
	    	alert("系统错误!");
	    	}
	});
}