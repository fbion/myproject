var mygrid;//整个GRID
var opt_id; //选中的ID
var templateCode;//存储此属性以供其它页面使用
//获取选中的选项IDS
function getSelectedId(rows) {
	var ids = "";
	$(rows).each(function(index) {
		ids += this.templateCode;
		if (index < rows.length - 1) {
			ids += ",";
		}
	});
	return ids;
}


String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
	if (!RegExp.prototype.isPrototypeOf(reallyDo)) {
		return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi" : "g")),
				replaceWith);
	} else {
		return this.replace(reallyDo, replaceWith);
	}
};


var listDataStr = listDataStr.replaceAll('data', 'Rows').replaceAll('total','Total');//来自jsp的list

var listData = eval('(' + listDataStr + ')')[0];

// 查询
function query() {
	var params = {
			templateCode:$('#templateCode').val(),
			templateName:$('#templateName').val()
		};
	$.post("ajaxQuery.action", params,
		  function(data){
				data = data.replaceAll('data', 'Rows').replaceAll('total','Total');
				var listData = eval('(' + data + ')')[0];
				mygrid = $("#mygrid").ligerGrid(getGrid(listData));
				$("#pageloading").hide();
		  });
	$('#pageloading').hide();// 加载图片
	
}

// 重置
function reset() {
	$('#templateCode').val('');
	$('#templateName').val('');
}

// 添加
function add() {
	var title = "添加子窗口";
	dialog_add = $.ligerDialog.open({
		url : 'add.action',
		height : 500,
		width : 600,
		showMax : false,
		showToggle : false,
		showMin : false,
		isDrag : false,
		isResize : false,
		modal : true,
		title : title
	});
}

// 状态
function jstatus(templateCode) {
	if(templateCode=="" || templateCode==null){
		JSWK.clewBox("获取模板编码为空!", "clew_ico_fail", 2000);
		return;
	}
	$.post('ajaxSet.action', {code:templateCode}, function(data){
		if(data == 1){
			$.ligerDialog.waitting('状态更新成功!'); setTimeout(function () { 
				$.ligerDialog.closeWaitting(); 
				window.location.href='index.action';
			}, 2000);
		}else{
			JSWK.clewBox("状态更新失败!", "clew_ico_fail", 3000);
		}
	});
}

// 更新
function jupdate(templateCode) {
	var url = 'update.action?code='+templateCode;
	$.ligerDialog.open({
		url : url,
		height : 500,
		width : 600,
		showMax : false,
		showToggle : false,
		showMin : false,
		isDrag : false,
		isResize : false,
		modal : true,
		title : "更新子窗口"
	});
}
// 删除
function jdelete(templateCode,status) {
	var params = {code:'',codes:''};
	if(templateCode=="" || templateCode==null){
		JSWK.clewBox("获取模板编码为空!", "clew_ico_fail", 2000);
		return;
	}
	if(status=='1'){
		JSWK.clewBox("已启用模板无法删除!", "clew_ico_fail", 2000);
		return;
	}
	params.code = templateCode;
	$.ligerDialog.confirm('确定要删除此条记录吗？', 
			function (type) {
				if(type){
					showMask();
					$.post('ajaxDelete.action', params, function(data){
						hideMask();
						if(data == 1){
							$.ligerDialog.waitting('删除成功!'); setTimeout(function () { 
								$.ligerDialog.closeWaitting(); 
								window.location.href='index.action';
							}, 2000);
						}else{
							JSWK.clewBox("删除失败!", "clew_ico_fail", 3000);
						}
					});
				}else{
					return;
				}
			}
	);
}
//初始化菜单按钮
function initToolbar(){
	var myitems=[];
	var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, ['com.tyzfpt.upp.App.smsadd']);
	$(funItems).each(function() {
		myitems.push({
			line : true
		});
		if ('com.tyzfpt.upp.App.smsadd' == this.permId) {
			this['click']=add;
			this['icon']='add';
		}
		myitems.push(this);
	});
	var toolbar = { 
		items: myitems
    };
	return toolbar;
}

// 表格
function getGrid(jdata) {
	var funItems = MALQ_FUNCTION.getPageFunction(window.frameElement.id, ['com.tyzfpt.upp.App.smsstatus','com.tyzfpt.upp.App.smsupdate','com.tyzfpt.upp.App.smsdel','com.tyzfpt.upp.App.smsPerson']);
	var grid = {
		columns : [
				{
					display : '模板编码',
					name : 'templateCode',
					align : 'center',
					width : '12%',
					isSort : true
				},
				{
					display : '模板名称',
					name : 'templateName',
					align : 'center',
					width : '15%',
					minWidth : '15%',
					isSort : true
				},
				{
					display : '内容（参数标识：<_>，短信拆分标识：>_<）',
					name : 'templateContent',
					width : '40%',
					align : 'center',
					isSort : true
				},
				{
					display : '状态',
					name : 'status',
					width : '10%',
					align : 'center',
					isSort : false,
					render : function(item) {
						return item.status == 1 ? '启用':'禁用';
					}
				},
				{
					display : '操作',
					name : 'status',
					width : '19%',
					align : 'center',
					isSort : false,
					render : function(item) {
						var btnVal = '';
						if (item.status == '0') {
							btnVal = '启用';
						} else {
							btnVal = '禁用';
						}
						var str = '';
						// 通过权限唯一标示生成操作列按钮
						//alert(JSON.stringify(funItems));
						$(funItems).each(function() {
							if('com.tyzfpt.upp.App.smsPerson' == this.permId && item.templateCode=='pay781357'){
								str += '<input type="button" value="设置" style="border:0;color:blue;background:none;cursor: pointer;" onclick="addSmsPerson(\''+item.templateCode+'\')"/> &nbsp;&nbsp;&nbsp;';
							}
							if ('com.tyzfpt.upp.App.smsstatus' == this.permId) {
								str += '<input type="button" value="' + btnVal + '" style="border:0;color:blue;background:none;cursor: pointer;" onclick="jstatus(\''+item.templateCode+'\')"/> &nbsp;&nbsp;&nbsp;';
							}
							if ('com.tyzfpt.upp.App.smsupdate' == this.permId) {
								str += '<input type="button" value="修改" style="border:0;color:blue;background:none;cursor: pointer;" onclick="jupdate(\''+item.templateCode+'\')"/> &nbsp;&nbsp;&nbsp;';
							}
							if ('com.tyzfpt.upp.App.smsdel' == this.permId) {
								str +=  '<input type="button" value="删除" style="border:0;color:blue;background:none;cursor: pointer;" onclick="jdelete(\''+item.templateCode+'\',\''+item.status+'\')"/> &nbsp;&nbsp;&nbsp;';
							}
							
						});
						return str;
					}
				} ],
		data : jdata,
		pageSize : 20,
		width : '100%',
		height : '100%',
		sortName:'templateCode',
		sortOrder:'desc',
		checkbox : false,
		rownumbers : true,
		fixedCellHeight : true,
		isSort: true,
		toolbar: initToolbar(),
	};

	return grid;
}

function addSmsPerson(code){
	var title = "联系人列表";
	templateCode = code;
	dialog_add = $.ligerDialog.open({
		url : root +'/smsPerson/personList.action',
		height : 500,
		width : 1000,
		showMax : false,
		showToggle : false,
		showMin : false,
		isDrag : false,
		isResize : false,
		modal : true,
		title : title
	});
}
// 页面加载
$(function() {
	mygrid = $("#mygrid").ligerGrid(getGrid(listData));
	$("#pageloading").hide();
});