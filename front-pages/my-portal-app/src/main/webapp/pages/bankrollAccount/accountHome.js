//数据对象模板
var ajaxAccountInfoQueryUrl = root+'/account/queryAccount.action';
var queryUrl = root+'/account/queryAccountCurrent.action';
var accountPasswordTypeUrl = root+'/account/accountPasswordType.action';
var updateRemarksUrl = root+'/account/updateRemarks.action';
var findRemarksUrl = root+'/account/findRemarks.action';
var judgePaymentPwdQueryUrl = root+'/account/judgePaymentPwdQuery.action';
var quartzGrid;
var payPasswordType = null;
var mygrid = {};
var _id;
var judgePayPwd = false;
var judgePayPwdQuery = false;
$(function() {
	//修改导航栏
	parent.$("#naveBar").html("<li class=\"fl pr2 color_chip\"><a onclick=\"cheWangHomePage()\">车旺首页</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"cheWangAccountCenter()\">账户中心</a></li>"
			+"<li class=\"fl pl2 pr2 color_chip\">&gt;</li>"
			+"<li class=\"fl pl2 pr2\"><a onclick=\"goAccountHome()\">资金帐户</a></li>");
	if(accountNo!=null&&accountNo!='null'&&accountNo!=''){
		//获取账户信息
		ajaxAccountInfoQuery();
		//判断是否是原始密码
		accountPasswordType();
		//查询是否设置密保问题
		judgePaymentPwdQuery();
		//创建的表格
		getgrid=getGrid();
		myTable(getgrid,$('#dataGrid'));
		//条件查询支出
		$("#queryAccountCurrentDEDUCTION").click(function(){
			$("#queryAccountCurrentDEDUCTION").removeClass().addClass("curr");
			$("#queryAccountCurrentRECORDED").removeClass().addClass("f14");
			$("#queryAccountCurrentTypeAll").removeClass().addClass("f14");
			queryAccountCurrent('deduction');
		});
		//条件查询收入
		$("#queryAccountCurrentRECORDED").click(function(){
			$("#queryAccountCurrentRECORDED").removeClass().addClass("curr");
			$("#queryAccountCurrentDEDUCTION").removeClass().addClass("f14");
			$("#queryAccountCurrentTypeAll").removeClass().addClass("f14");
			queryAccountCurrent('recorded');
		});
		//条件查询全部
		$("#queryAccountCurrentTypeAll").click(function(){
			$("#queryAccountCurrentTypeAll").removeClass().addClass("curr");
			$("#queryAccountCurrentRECORDED").removeClass().addClass("f14");
			$("#queryAccountCurrentDEDUCTION").removeClass().addClass("f14");
			queryAccountCurrent(null);
		});
		//跳转到安全设置页面
		$("#safetySet").click(function(){
			window.location.href=root+"/account/safetySet.action";
		});
		//修改绑定手机号码事件
		$("#changeMobileNo").click(function(){
			window.location.href=root+"/paymentPassword/chengeMobileNoHomePage.action";
		});
		
		//跳转到更多流水页面
		$("#queryAccountCurrentAll").click(function(){
			queryAccountCurrentAll();
		});
		//跳转到充值页面
		$("#recharge").click(function(){
			recharge();
		});
		//关闭备注弹窗
		$("#closeButton").click(function(){
			$('#remarksDiv').attr("style","display:none");
		});
		//备注提交
		$("#remarksButton").click(function(){
			remarksSubmit();
		});
	}else{
		alert("登录信息有误，请重新登录！");
	}
});

/* Ajax查询账户余额方法 */
function ajaxAccountInfoQuery() {
	var random = Math.random();
	$.ajax({
		url : ajaxAccountInfoQueryUrl,
		data : {'accountNo':accountNo,"random":random},
		dataType : 'JSON',
		success : function(data){
			if(data){
				if(data.totalBalance){
					$("#totalBalance").html(data.totalBalance/100);
				}else{
					$("#totalBalance").html("0.00");
				}
				if(data.usableBalance){
					$("#usableBalance").html(data.usableBalance/100);
				}else{
					$("#usableBalance").html("0.00");
				}
				if(data.mobileNo){
					var reg = /(\d{3})\d{4}(\d{4})/;
					var mobileNo = data.mobileNo;
					var mobile=mobileNo.replace(reg,"$1****$2");
					$("#mobileNo").html(mobile);
				}
			}
		}
	});
}

/* Ajax查询账户密码判断 */
function accountPasswordType() {
	$.ajax({
		url : accountPasswordTypeUrl,
		data : {'accountNo':accountNo},
		dataType : 'JSON',
		success : function(data){
			if(data&&data.accountPasswordType){
				if(data.accountPasswordType=="1"){
					judgePayPwd = true;
					//根据交易密码和密保问题判断安全等级强度
					judgeSafety();
				}
			}
		}
	});
}
//查询是否设置密保问题
function judgePaymentPwdQuery(){
	$.ajax({
		url : judgePaymentPwdQueryUrl,
		data : {'accountNo':accountNo},
		dataType : 'JSON',
		success : function(data){
			if(data&&data.pwdQuery){
				if(data.pwdQuery=="1"){
					judgePayPwdQuery = true;
					//根据交易密码和密保问题判断安全等级强度
					judgeSafety();
				}
			}
		}
	});
}

//判断安全等级
function judgeSafety(){
	if(judgePayPwd&&judgePayPwdQuery){
		$("#strength_L").attr('style','background-color: #228B22;');
		$("#strength_M").attr('style','background-color: #228B22;');
		$("#strength_H").attr('style','background-color: #228B22;');
	}else if(judgePayPwd){
		$("#strength_L").attr('style','background-color: #EEAD0E;');
		$("#strength_M").attr('style','background-color: #EEAD0E;');
	}
}

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
//						return "网银充值";
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
		pageSizeOptions : [ 10, 15, 20, 30, 50, 100 ],
		pageSize : 10,
		parms : {'insideAccountNo':accountNo,'page':1,'rows':10}
	};
	return grid;
}

function queryAccountCurrent(data) {
	
	var parms={};
	
	parms['insideAccountNo']=accountNo;
	if(data&&data!=null){
		parms['bookaccountType']=data;
	}
	parms['page']=1;
	parms['rows']=10;
	getgrid.parms = parms;
	myTable(getgrid,$('#dataGrid'));
}
//跳转到更多页面
function queryAccountCurrentAll() {
	window.location.href=root+"/account/AccountCurrentList.action";
}
//跳转到支付页面
function recharge() {
	
	parent.location.href=cashierUrl;
}
//跳转至详情页面
function accountCurrentDetail(id) {
	window.location.href=root+"/account/accountCurrentDetail.action?id="+id;
}
//打开备注弹窗
function commentsOnAForm(id) {
	_id = id;
	$('#remarksDiv').attr("style","position:absolute;left:50%;top:50%;margin-left:-225px;margin-top:-150px;width:450px;height:270px;z-index:1;border:solid 1px #CCCCCC;background-color:#ffffff;dispaly:inline");
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
			alert('很抱歉！获取备注内容错误！');
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