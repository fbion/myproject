/*
*
*obj为表格生成的位置对象
*gird位生成表格的参数
*var grid = {
*		columns : [{
*			display : "时间",
*			name : "accountTime",
*			width : "8%",
*			align : "center",
*			render : function(item){
*			}
*			},{
*				display : "名称",
*				name : "productName",
*				width : "4%",
*				align : "center",
*			}],
*		contentType:'application/json;charset=UTF-8',
*		url : Url,
*		sortName :'accountTime',
*		sortOrder :"desc",
*		pageSizeOptions : [ 10, 15, 20, 30],
*		pageSize : 15,
*		usePager : 'layoutTwo',
*		parms :{}}
*/
var param = null;
var myGird = null;
var myObj = null;
var myPage = 1;

function myTable(gird,obj){
	myGird = gird;
	myObj = obj;
	param = myGird.parms;
	initParam();
	buildtal();
}

function buildtal(){
	
	var columns = myGird.columns;
	var usePager = myGird.usePager;
	var pageSizeOpteins = myGird.pageSizeOptions;
	var pageSize = myGird.pageSize;
	myObj.html('');
	myObj.append('<table class=\"buildTable\" style=\"width: 100%; padding: 0; margin: 0;\"></table>');
	$('.buildTable').append('<tr class=\"htableTr\"></tr>');
	for(var i=0;i<columns.length;i++){
		var thWidth = '5%';
		var tdAlign = 'center';
		if(columns[i].width){
			thWidth = columns[i].width;
		}
		if(columns[i].align){
			tdAlign = columns[i].align;
		}
		$('.htableTr').append('<th style=\"width: '+thWidth+'; height: 100%; text-align: '+tdAlign+'; padding: 0;margin: 0;\">'+columns[i].display+'</th>');
	}
	
	$.ajax({
		url : myGird.url,
		type: "GET",
		contentType: myGird.contentType,
		data : param,
		dataType : 'JSON',
		success : function(data){
			var objJson = null;
			if(data&&data.data){
				objJson = data.data;
			}
			if(objJson){
				for(var i=0;i<objJson.length;i++){
					$('.buildTable').append('<tr class=\"btableTr'+i+'\"></tr>');
					for(var j=0;j<columns.length;j++){
						var tdAlign = 'center';
						var tdValue = objJson[i][columns[j].name];
						if(columns[j].align){
							tdAlign = columns[j].align;
						}
						if(columns[j].render){
							tdValue = columns[j].render(objJson[i]);
						}
						if(!tdValue||tdValue=='undefined'){
							tdValue = '';
						}
						$('.btableTr'+i).append('<td align=\"'+tdAlign+'\">'+tdValue+'</td>');
					}
				}
			}
			//一种分页方式
			if(usePager=='layoutOne'&&objJson&&objJson.length>0){
				$('.buildTable').append('<tr class=\"ftableTr\"></tr>');
				$('.ftableTr').append('<td class=\"ftableTd\" colspan=\"'+columns.length+'\" style=\"text-align: center\"></td>');
				$('.ftableTr td').append('<div class=\"pageDiv\"></div>');
				$('.ftableTr td div').append('每页<select class=\"pageSize\" style=\"display:inline;\"></select>条数据'
						+'<a href=\"javascript:window.getIntoPage(\'homePage\')\" style=\"margin-right: 5px; margin-left: 5px;\">首页</a>'
						+'<a href=\"javascript:window.getIntoPage(\'previousPage\')\" style=\"margin-right: 5px\">上一页</a>'
						+'<input typt=\"text\" value=\"'+myPage+'\" class=\"currentPage\" style=\"width: 30px\"/>'
						+'<a href=\"javascript:window.getIntoPage(\'nextPage\')\" style=\"margin-right: 5px\">下一页</a>'
						+'<a href=\"javascript:window.getIntoPage(\'lastPage\')\" style=\"margin-right: 5px\">末页</a>'
						+'<a href=\"javascript:window.getIntoPage(\'jump\')\" style=\"margin-right: 30px\">刷新</a>'
						+'<span>显示从<span class=\"start\">0</span>到<span class=\"last\">0</span>，总<span class=\"total\">0</span>条，每页显示<span class=\"size\">0</span></span>'
						);
				myPage = $('.currentPage').val();
				if(pageSizeOpteins){
					for(var i=0;i<pageSizeOpteins.length;i++){
						$('.pageSize').append('<option value='+pageSizeOpteins[i]+'>'+pageSizeOpteins[i]+'</option>');
					}
				}
				if(pageSize){
					$('.pageSize').val(pageSize);
					$('.size').html(pageSize);
				}
				if(data){
					$('.start').html(data.start+1);
					if(eval(data.start+'+'+$('.pageSize').val())<data.total){
						$('.last').html(eval(data.start+'+'+$('.pageSize').val()));
					}else{
						$('.last').html(data.total);
					}
					$('.total').html(data.total);
				}
			}
			//分页结束
			//另一个分页方式
			if(usePager=='layoutTwo'&&objJson&&objJson.length>0){
				var paginalNum = Math.ceil(data.total/pageSize);
				$('.buildTable').append('<tr class=\"ftableTr\" style=\"height:50px;padding-top:20px;\"></tr>');
				$('.ftableTr').append('<td class=\"ftableTd\" colspan=\"'+columns.length+'\" style=\"text-align: center\"></td>');
				$('.ftableTr td').append('<div class=\"pageDiv\" style=\"float:right;margin-right:10px;\"></div>');
				$('.ftableTr td div').append('<ul class=\"pageUl\"></ul>');
				$('.pageUl').append('<a><li class=\"previousPage\" onclick="gotoPage('+(myPage-1)+')">&lt;</li></a>');
				for(var i=1;i<=paginalNum;i++){
					var pn = 3;
					if((myPage-pn)<=1&&i<=myPage){
						$('.pageUl').append('<a><li class=\"'+i+'Page\" onclick="gotoPage('+i+')">'+i+'</li></a>');
					}else if((myPage-pn)>2&&i==1){
						$('.pageUl').append('<a><li class=\"'+i+'Page\" onclick="gotoPage('+i+')">'+i+'</li></a>');
						$('.pageUl').append('<a><li class=\"centerPage\">...</li>');
					}else if((myPage-pn)==2&&i<=myPage){
						$('.pageUl').append('<a><li class=\"'+i+'Page\" onclick="gotoPage('+i+')">'+i+'</li></a>');
					}else if((myPage-pn)>2&&(myPage-pn)<=i&&myPage>=i){
						$('.pageUl').append('<a><li class=\"'+i+'Page\" onclick="gotoPage('+i+')">'+i+'</li></a>');
					}else if((myPage+pn)>=paginalNum&&i>myPage){
						$('.pageUl').append('<a><li class=\"'+i+'Page\" onclick="gotoPage('+i+')">'+i+'</li></a>');
					}else if((myPage+pn)<(paginalNum-1)&&(myPage+pn)>=i&&myPage<i){
						$('.pageUl').append('<a><li class=\"'+i+'Page\" onclick="gotoPage('+i+')">'+i+'</li></a>');
					}else if((myPage+pn)<(paginalNum-1)&&i==paginalNum){
						$('.pageUl').append('<a><li class=\"centerPage\">...</li>');
						$('.pageUl').append('<a><li class=\"'+i+'Page\" onclick="gotoPage('+i+')">'+i+'</li></a>');
					}else if((myPage+pn)==(paginalNum-1)&&i>myPage){
						$('.pageUl').append('<a><li class=\"'+i+'Page\" onclick="gotoPage('+i+')">'+i+'</li></a>');
					}
				}
				$('.pageUl').append('<a><li class=\"nextPage\" onclick="gotoPage('+(myPage+1)+')">&gt;</li></a>');
				$('.pageUl li').attr('style','width: 30px;height:30px;border: solid #EE7621 1px;float:left;margin-right:10px;color:#006400;line-height:30px;');
				$('.'+myPage+'Page').css({backgroundColor:'#EE7621'});
				if(myPage==1){
					$('.previousPage').attr('onclick','');
					$('.previousPage').css({backgroundColor:'#BDBDBD'});
				}
				if(myPage==paginalNum){
					$('.nextPage').attr('onclick','');
					$('.nextPage').css({backgroundColor:'#BDBDBD'});
				}
				//分页结束
			}			
		}
	});
}

function initParam(){
	var pageId = null;
	var rowsId = null;
	myPage = 1;
	for(var item in param){
		if(item=='page'){
			pageId = '1';
		}
		if(item=='rows'){
			rowsId = '1';
		}
	}
	if(pageId!='1'){
		param['page']=1;
	}
	if(rowsId!='1'){
		param['rows']=myGird.pageSize;	
	}
	if(myGird.sortName&&myGird.sortOrder){
		param['sort']=myGird.sortOrder;
	}else{
		param['sort']='desc';
	}
	if(myGird.sortName){
		param['order']=myGird.sortName;
	}
}

function getIntoPage(data){
	if(data=='homePage'){
		param['page']=1;
		param['rows']=$('.pageSize').val();
		myPage=1;
	}
	if(data=='previousPage'){
		if(myPage>1){
			param['page']=myPage*1-1;
			param['rows']=$('.pageSize').val();
			myPage=myPage*1-1;
		}
	}
	if(data=='nextPage'){
		if(myPage<Math.ceil(eval($('.total').html()+'/'+$('.pageSize').val()))){
			param['page']=myPage*1+1;
			param['rows']=$('.pageSize').val();
			myPage=myPage*1+1;
		}
	}
	if(data=='lastPage'){
		param['page']=Math.ceil(eval($('.total').html()+'/'+$('.size').html()));
		param['rows']=$('.pageSize').val();
		myPage=Math.ceil(eval($('.total').html()+'/'+$('.pageSize').val()));
	}
	if(data=='jump'){
		if(0<$('.currentPage').val()<=Math.ceil(eval($('.total').html()+'/'+$('.pageSize').val()))){
			param['page']=$('.currentPage').val();
			param['rows']=$('.pageSize').val();
			myPage=$('.currentPage').val();
		}
	}
	myGird.pageSize=$('.pageSize').val();
	buildtal();
	$('.currentPage').val(myPage);
}

function gotoPage(data){
	param['page']=data;
	myPage = data;
	buildtal();
}
