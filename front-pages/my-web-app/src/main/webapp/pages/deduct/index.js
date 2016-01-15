var mygrid;//整个GRID
var opt_id; //选中的ID

function query(){
	
}

//扣款审核申请窗口
function applyfor() {
	var title = "扣款审核申请窗口";
	dialog_add = $.ligerDialog.open({
		url : 'applyfor.action',
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


//初始化菜单按钮
function initToolbar(){
	var toolbar = { items: [ 
								{ text: '查询', click: query ,icon: 'search'},
								{ line: true },
								{ text: '扣款申请审核', click: applyfor, icon: 'add' },
							]
          };
	return toolbar;
}


//表格
function getGrid(jdata) {
	var grid = {
		columns : [
				{
					display : '商户编号',
					name : 'storeId',
					align : 'center',
					width : '8%',
					isSort : false
				},
				{
					display : '付款人姓名',
					name : 'remitterName',
					align : 'center',
					width : '8%',
					isSort : false
				},
				{
					display : '付款类型',
					name : 'tradeType',
					width : '8%',
					align : 'center',
					isSort : false
				},
				{
					display : '付款时间',
					name : 'applyTime',
					width : '9%',
					align : 'center',
					isSort : false 
				},
				{
					display : '资金到账时间',
					name : 'amountArriveTime',
					width : '9%',
					align : 'center',
					isSort : false 
				},
				{
					display : '扣款金额',
					name : 'tradeAmount',
					width : '8%',
					align : 'center',
					isSort : false 
				},
				{
					display : '资金确认人',
					name : 'fundsConfirmId',
					width : '8%',
					align : 'center',
					isSort : false 
				},
				{
					display : '资金确认时间',
					name : 'fundsConfirmTime',
					width : '8%',
					align : 'center',
					isSort : false 
				},
				{
					display : '申请状态',
					name : 'applyStatus',
					width : '8%',
					align : 'center',
					isSort : false 
				},
				{
					display : '步骤处理号',
					name : 'stepNo',
					width : '8%',
					align : 'center',
					isSort : false 
				},
				{
					display : '当前处理人',
					name : 'approvalPersonId',
					width : '8%',
					align : 'center',
					isSort : false 
				},
				{
					display : '操作',
					name : 'status',
					width : '8%',
					align : 'center',
					isSort : false 
				} ],
		data : jdata,
		pageSize : 20,
		width : '100%',
		height : '99%',
		rownumbers : true,
		fixedCellHeight : true,
		sortOrder : "desc",
		toolbar: initToolbar()

	};

	return grid;
}




// 页面加载
$(function() {
	mygrid = $("#mygrid").ligerGrid(getGrid(jtestData));
	$("#pageloading").hide();

});