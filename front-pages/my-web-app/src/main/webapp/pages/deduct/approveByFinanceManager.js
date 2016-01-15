//审批完成页面（step4）
function approveComplete() {
	var title = "审批完成页面";
	dialog_add = $.ligerDialog.open({
		url : 'approveComplete.action',
		width : 1000,
		showMax : true,
		showToggle : true,
		showMin : false,
		isDrag : true,
		isResize : true,
		modal : true,
		title : title
	}).max();
}

$(function(){
	$('#jsave').click(function(){
		//parent.$.ligerDialog.close();
		approveComplete();
	});
});