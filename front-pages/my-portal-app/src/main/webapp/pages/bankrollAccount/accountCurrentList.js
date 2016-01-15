//数据对象模板
var ajaxAccountInfoQueryUrl = root+'/account/queryAccount.action';
var queryUrl = root+'/account/queryAccountCurrent.action';
var updateRemarksUrl = root+'/account/updateRemarks.action';
var findRemarksUrl = root+'/account/findRemarks.action';
var dataSelect;
var accountType;
var accountStatus;
var getgrid={};
var _id;
var OpenWindow;
$(function() {
	getgrid=getGrid();
	myTable(getgrid,$('#dataGrid'));
	
	parent.$("#naveBar").html("<li class=\"fl pr2 color_chip\"><a onclick=\"cheWangHomePage()\">车旺首页</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"cheWangAccountCenter()\">账户中心</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"goAccountHome()\">资金帐户</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"goAccountCurrentList()\">资金交易记录</a></li>");
	
	$("#today").click(function(){
		dateImputHtmlChange();
		dateChangeCss();
		$("#today").removeClass().addClass("litab_b litab_curr");
		dateSelect('today');
		queryAccountCurrent(accountType,dataSelect,accountNo,accountStatus);
	});
	$("#oneMonth").click(function(){
		dateImputHtmlChange();
		dateChangeCss();
		$("#oneMonth").removeClass().addClass("litab_b litab_curr");
		dateSelect('oneMonth');
		queryAccountCurrent(accountType,dataSelect,accountNo,accountStatus);
	});
	$("#quarterOfYear").click(function(){
		dateImputHtmlChange();
		dateChangeCss();
		$("#quarterOfYear").removeClass().addClass("litab_b litab_curr");
		dateSelect('quarterOfYear');
		queryAccountCurrent(accountType,dataSelect,accountNo,accountStatus);
	});
	$("#oneYear").click(function(){
		dateImputHtmlChange();
		dateChangeCss();
		$("#oneYear").removeClass().addClass("litab_c litab_curr");
		dateSelect('oneYear');
		queryAccountCurrent(accountType,dataSelect,accountNo,accountStatus);
	});
	$("#dateAll").click(function(){
		dateImputHtmlChange();
		dateChangeCss();
		$("#dateAll").removeClass().addClass("litab_a litab_curr");
		dateSelect(null);
		queryAccountCurrent(accountType,dataSelect,accountNo,accountStatus);
	});
	
	$("#recorded").click(function(){
		typeChangeCss();
		$("#recorded").removeClass().addClass("litab_a litab_curr");
		accountTypeSelect('recorded');
		queryAccountCurrent(accountType,dataSelect,accountNo,accountStatus);
	});
	$("#deduction").click(function(){
		typeChangeCss();
		$("#deduction").removeClass().addClass("litab_c litab_curr");
		accountTypeSelect('deduction');
		queryAccountCurrent(accountType,dataSelect,accountNo,accountStatus);
	});
	$("#typeAll").click(function(){
		typeChangeCss();
		$("#typeAll").removeClass().addClass("litab_b litab_curr");
		accountTypeSelect(null);
		queryAccountCurrent(accountType,dataSelect,accountNo,accountStatus);
	});
	//关闭备注弹窗
	$("#closeButton").click(function(){
		$('#remarksDiv').attr("style","display:none");
	});
	//备注提交
	$("#remarksButton").click(function(){
		remarksSubmit();
	});
});

function dateChangeCss(){
	$("#dateAll").removeClass().addClass("litab_a");
	$("#today").removeClass().addClass("litab_b");
	$("#oneMonth").removeClass().addClass("litab_b");
	$("#quarterOfYear").removeClass().addClass("litab_b");
	$("#oneYear").removeClass().addClass("litab_c");
}

function typeChangeCss(){
	$("#recorded").removeClass().addClass("litab_a");
	$("#deduction").removeClass().addClass("litab_c");
	$("#typeAll").removeClass().addClass("litab_b");
}

function statusChangeCss(){
	$("#businessSuccess").removeClass().addClass("litab_a");
	$("#businessLose").removeClass().addClass("litab_c");
}

function dateImputHtmlChange(){
	$("#select_start_Time").val('');
	$("#stlect_end_Time").val('');
}

function dateSelect(date) {
	dataSelect = date;
}

function accountTypeSelect(date) {
	accountType = date;
}

function accountStatusSelect(date) {
	accountStatus = date;
}

function dateFocus() {
	dateChangeCss();
	WdatePicker({dateFmt:'yyyy-MM-dd'});
	if($("#select_start_Time").val()&&$("#stlect_end_Time").val()){
		queryAccountCurrent(accountType,dataSelect,accountNo,accountStatus);
	}
}

/* Ajax查询交易记录方法 */
function getGrid() {
	var grid = {
		columns : [ 
			{
				display : "时间",
				name : "accountTime",
				width : "8%",
				align : "center",
				render : function(item){
					if(item.accountTime){
						return getSmpFormatDateByLong(item.accountTime*1,true);
					}
				}
			},
			{
				display : "名称",
				name : "productName",
				width : "4%",
				align : "center",
			},
			{
				display : "支付方式",
				name : "payChannel",
				width : "4%",
				align : "center",
				render : function(item){
					if(item.payChannel=='NET'){
						return "网银支付";
					}
					if(item.payChannel=='FASTPAY'){
						return "快捷支付";
					}
					if(item.payChannel=='ACCOUNT'){
						return "账户支付";
					}
//					if(item.payChannel=='101'){
//						return "网银支付";
//					}
//					if(item.payChannel=='111'){
//						return "快捷支付";
//					}
//					if(item.payChannel=='121'){
//						return "账户充值";
//					}
//					if(item.payChannel=='131'){
//						return "线下充值";
//					}
//					if(item.payChannel=='611'){
//						return "快捷充值";
//					}
//					if(item.payChannel=='201'){
//						return "转账";
//					}
//					if(item.payChannel=='631'){
//						return "确认支付";
//					}
//					if(item.payChannel=='632'){
//						return "取消支付";
//					}
//					if(item.payChannel=='621'){
//						return "账户支付";
//					}
//					if(item.payChannel=='601'){
//						return "网银支付";
//					}
//					if(item.payChannel=='103'){
//						return "账户支付";
//					}
//					if(item.payChannel=='104'){
//						return "网银扣款";
//					}
//					if(item.payChannel=='106'){
//						return "快捷充值";
//					}
//					if(item.payChannel=='108'){
//						return "快捷充值";
//					}
//					if(item.payChannel=='112'){
//						return "线下扣款";
//					}
//					if(item.payChannel=='113'){
//						return "线下充值";
//					}
				}
			},
			{
				display : "收/支",
				name : "bookAccountType",
				width : "3%",
				align : "center",
				render : function(item){
					if(item.bookAccountType=='deduction'){
						return "支出";
					}
					if(item.bookAccountType=='recorded'){
						return "收入";
					}
					if(item.bookAccountType=='frozen'){
						return "冻结";
					}
					if(item.bookAccountType=='unfrozen'){
						return "解冻";
					}
				}
			},
			
			{
				display : "金额(元)",
				name : "bookAccountMoney",
				width : "4%",
				align : "center",
				render : function(item){
					if(item.bookAccountType=='deduction'){
						return "<span style='color:green'>-"+item.bookAccountMoney+"</span>";
					}
					if(item.bookAccountType=='recorded'){
						return "<span style='color:#FF6347'>+"+item.bookAccountMoney+"</span>";
					}
					if(item.bookAccountType=='frozen'){
						return "<span style='color:green'>-"+item.bookAccountMoney+"</span>";
					}
					if(item.bookAccountType=='unfrozen'){
						return "<span style='color:#FF6347'>+"+item.bookAccountMoney+"</span>";
					}
				}
			},
			{
				display : "状态",
				name : "",
				width : "6%",
				align : "center",
				render : function(item){
					return "交易成功";
				}
			},
			{
				display : "操作",
				width : "5%",
				align : "center",
				render : function(item){
					return '<a href="javascript:window.accountCurrentDetail(\'' + item.id + '\')">'
					+'<span style="color:#00688B">查看</span></a>&nbsp;&nbsp;&nbsp;'
					+'<a href="javascript:window.commentsOnAForm(\'' + item.orderId + '\')">'
					+'<span style="color:#00688B">备注</span></a>';
				}
			}
		],
		contentType:'application/json;charset=UTF-8',
		url : queryUrl,
		sortName :'accountTime',
		sortOrder :"desc",
		pageSizeOptions : [ 10, 15, 20, 30],
		pageSize : 15,
		usePager : 'layoutTwo',
		parms :{'insideAccountNo':accountNo}
	};
	return grid;
}

function queryAccountCurrent(accountType,dataSelect,accountNo,accountStatus) {
	
	var parms={};

	parms["insideAccountNo"]=accountNo;
	
	if($("#select_start_Time").val()&&$("#stlect_end_Time").val()){
		
		var startLong = date2stamp($("#select_start_Time").val());
		var endLong = date2stamp($("#stlect_end_Time").val());
		
		if(startLong-endLong>0){
			alert("开始时间不能小于结束时间！");
			dateImputHtmlChange();
			return;
		}
		parms["startLong"]=startLong;
		parms["endLong"]=endLong;
	}else if(dataSelect&&dataSelect!=null){
		var time = new Date();
		var timeDate = time.toLocaleDateString();
		if(timeDate.charAt(4)=="/"){
			var dateFormit = timeDate.replace('/','-').replace('/','-');
		}else if(timeDate.charAt(4)=="年"){
			var dateFormit = timeDate.replace('年','-').replace('月','-').replace('日','');
		}
		var endLong = date2stamp(dateFormit)+(24*60*60*1000);
		
		if(dataSelect=='today'){
			var startLong = endLong-(24*60*60*1000);
		}
		if(dataSelect=='oneMonth'){
			var startLong = endLong-(30*24*60*60*1000);
		}
		if(dataSelect=='quarterOfYear'){
			var startLong = endLong-(3*30*24*60*60*1000);
		}
		if(dataSelect=='oneYear'){
			var startLong = endLong-(365*24*60*60*1000);
		}
		parms["startLong"]=startLong;
		parms["endLong"]=endLong;
	}
		
	if(accountType){
		parms["bookaccountType"]=accountType;
	}
	if(accountStatus){
		parms["orderStatus"]=accountStatus;
	}
	getgrid.parms = parms;
	myTable(getgrid,$('#dataGrid'));
}

function accountCurrentDetail(id) {
	window.location.href=root+"/account/accountCurrentDetail.action?id="+id;
}
//打开备注弹窗
function commentsOnAForm(id) {
	_id = id;
	$('#remarksDiv').attr("style","position:absolute; left:50% ;top:50%;margin-left:-225px;margin-top:-150px;width:450px;height:270px;z-index:1;border:solid 1px #CCCCCC;background-color:#ffffff;dispaly:inline");
	$.ajax({
		url : findRemarksUrl,
		type: 'POST',
		data : {'id':id},
		dataType : 'JSON',
		success : function(data){
			if(data&&data.remarks){
				$('#remarksContext').val(data.remarks);
			}else{
				$('#remarksContext').val('');
			}
			remarksContextNum();
		},
		error: function(){
			alert('很抱歉！获取备注内容发生错误！');
		}
	});
	
}
//判断备注可输入字数
function remarksContextNum() {
	var remarks = $('#remarksContext').val();
	if(remarks.length<=50){
		$('#remarksText').html('（还可以输入<span id=\"remarksNum\" style=\"color:#1874CD;\"></span>个字，只能输入汉字、字母、数字。）');
		$('#remarksNum').html(50-remarks.length);
	}else{
		$('#remarksText').html('（已经超出<span id=\"remarksNum\" style=\"color:#EE5C42;\"></span>个字，只能输入汉字、字母、数字。）');
		$('#remarksNum').html(remarks.length-50);
	}
}
//备注提交方法
function remarksSubmit() {
	var remarks = $('#remarksContext').val();
	var reg = /^[a-zA-Z0-9\u4e00-\u9fa5,\.;\:"'!。，]{0,50}$/;
	if(remarks.length>50){
		alert("备注内容长度不能大于50！");
		return;
	}
	if(!reg.test(remarks)){
		alert("不可以输入规定以外的字符！");
		return;
	}
	if(!remarks){
		alert("备注内容不能为空！");
		return;
	}
	$.ajax({
		url : updateRemarksUrl,
		type: 'POST',
		data : {'id':_id,'remarks':remarks},
		dataType : 'JSON',
		success : function(data){
			if(data=="1"){
				$('#remarksDiv').attr("style","display:none");
			}else{
				alert('修改备注失败！');
			}
		}
	});
}