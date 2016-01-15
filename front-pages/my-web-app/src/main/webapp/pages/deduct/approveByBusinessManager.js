//财务经理-审批页面（step3）
function approveByFinanceManager() {
	var title = "财务经理-审批页面";
	dialog_add = $.ligerDialog.open({
		url : 'approveByFinanceManager.action',
		height : 800,
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
		approveByFinanceManager();
	});
});