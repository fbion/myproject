$(function(){
	$('#sendSycAccountReq').click(function(){
		send();
	});
});
var sendAccountSycUrl = root+'/accountContrast/accountReconciliationByTime.action';
function send() {
	$('#pageloading').css('display', 'block');
	var accountDate=$('#accountDate').val();
	$.ajax({
		   type: "POST",
		   url:  sendAccountSycUrl,
		   data: {'sendDate':accountDate},
		   success: function(data){
			   alert(data);
			   $('#pageloading').css('display', 'none');
		   }
	 });
}