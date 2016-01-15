//提现js
$(function(){
	loadData();
	resultValidate();
});

function loadData(){
	$("#totalBalance").val(parent._totalBalance);
	$("#usableBalance").val(parent._usableBalance);
	$("#mobileNo").val(parent._mobileNo);
	var w = 100;
	MALQ_CODE.getCodeSelect_pc($("#branchBankProvince"),$("#branchBankCity"),w,null);
}
