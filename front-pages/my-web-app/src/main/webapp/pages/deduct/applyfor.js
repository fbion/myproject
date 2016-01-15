//业务经理-审批页面（step2）
function approveByBusinessManager() {
	var title = "业务经理-审批页面";
	dialog_add = $.ligerDialog.open({
		url : 'approveByBusinessManager.action',
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
		approveByBusinessManager();
	});
});