/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月23日
 */

(function($) {
	
	$.fn.orgSelectTree=function(args){
		var _orgSelectTree =new orgSelectTree();
		_orgSelectTree._defaultParams={};
		_orgSelectTree._initParams();
		_orgSelectTree.tree ={};
		_orgSelectTree.data = [];
		_orgSelectTree.selectAll = false;
		$.extend(_orgSelectTree._defaultParams,args);
		_orgSelectTree._defaultParams["thisObj"]=this.get(0);
		_orgSelectTree._init();
		return _orgSelectTree;
	};
	
	var orgSelectTree=function(){};
	orgSelectTree.prototype={
		_initParams:function(){
			$.extend(this._defaultParams,{
				id :"a1",
				url : "org_data1.json",
				width : 400,
				height : 300,
				attribute :  [ 'name', 'id' ],
				idFieldName :'id',
				params: {},
			    textFieldName :'name',
			    parentIDFieldName :'parentId',
			    paramName : "treeIds",
				onClick : function(node){
				}
			});
		},
		_init:function(){
			this._render();
			this._loadTreeData();
		},
		_render:function(){
			var g=this; 
			var p=g._defaultParams;
			var d =g._defaultParams.thisObj;
			var _html = '<div style="width:'+p.width+'px; height: '+p.height+'"><input type="hidden" name="'+p.paramName+'" id="selectOrgTree-paramName-'+p.id+'" />';
			_html +='<div style="margin-bottom: 10px;"> <table><tr><td>';
			_html +='&nbsp;&nbsp;<input type="text"  id="selectOrgTree-'+p.id+'-searchName" style="width:'+(p.width-60)+'px;""  />';
			_html +='</td><td valign="bottom">&nbsp; <input type="button" id="selectOrgTree-'+p.id+'-searchBtn" value="查询" /></td></tr></table></div>';
			_html +='<div style="height: '+(p.height-20)+'px;border: 1px solid #ccc;overflow: auto;box-shadow: 0 0 1px #000 inset;">';
			_html +='<div style="margin-left: 5px;margin-top: 10px;width:'+p.width+'px;" id="selectOrgTree-tree-'+p.id+'"></div>';
			_html +='</div></div>';
			_html +='<div style="margin-top: 10px;"><input type="checkbox" id="selectOrgTree-'+p.id+'-checkbox" value="0" />&nbsp; 全选</div>';
			_html+='</div>';
			$(d).html(_html);
			$("#selectOrgTree-'+p.id+'-searchBtn").bind('click', function() {
				var params = g._defaultParams.params || {};
				params['equal[name]'] = $.trim($("#selectOrgTree-"+p.id+"-searchName").val());
				g._loadData(g._defaultParams.url, params, "selectOrgTree-tree-"+g._defaultParams.id);
			});
			 $("#selectOrgTree-"+p.id+"-checkbox").bind("click", function () {
				 if($(this).attr("checked")){
					 g.selectAll=true;
					 $("#selectOrgTree-paramName-"+g._defaultParams.id).val('SelectTreeAll');
//					 for(var i =0 ; i<g.data.length;i++){
//						 g.tree.selectNode(this.id);
//					 }
				 }else{
					 g.selectAll=false;
					 var my_ids = "";
		        	var checked = g.tree.getChecked(); 
		        	if(checked){
		        		$(checked).each(function(){  
		            		my_ids +=this.data.id+',';
		            	});
		        		var incomplete = MALQ_TREE.getIncomplete(this);       	
		            	$(incomplete).each(function(){       		
		            		my_ids +=this.data.id+',';
		            	});     	
		        	}
			        $("#selectOrgTree-paramName-"+g._defaultParams.id).val(my_ids.substring(0, my_ids.length-1));
//					 for(var i =0 ; i<g.data.length;i++){
//						 g.tree.cancelSelect(this.id);
//					 }
				 }
	         });
		},
		_loadTreeData:function(){
			this._loadData(this._defaultParams.url,this._defaultParams.params,"selectOrgTree-tree-"+this._defaultParams.id);
		},
		_loadData : function(url,params,treeId){
			var g = this;
			$("#"+treeId).empty();
			var width = g._defaultParams.width-30;
			$("#"+treeId).ligerTree({
				slide : true,
				checkbox : true,
				nodeWidth : width,
				idFieldName :g._defaultParams.idFieldName || 'id',
			    parentIDFieldName :g._defaultParams.parentIDFieldName || 'parentId',
				textFieldName : g._defaultParams.textFieldName || 'name',
				attribute : g._defaultParams.attribute || [ 'name', 'id' ],
				onSelect : function(node) {
				},
				onClick : function(node){
					g._defaultParams.onClick.call(this,node);
					if(g.selectAll){
		        		$("#selectOrgTree-paramName-"+g._defaultParams.id).val('SelectTreeAll');
//		        		g.tree.selectNode(node);
		        		return;
		        	}
					var my_ids = "";
		        	var checked = this.getChecked(); 
		        	if(checked){
		        		$(checked).each(function(){  
		            		my_ids +=this.data.id+',';
		            	});
		        		var incomplete = MALQ_TREE.getIncomplete(this);       	
		            	$(incomplete).each(function(){       		
		            		my_ids +=this.data.id+',';
		            	});     	
		        	}
		        	$("#selectOrgTree-paramName-"+g._defaultParams.id).val(my_ids.substring(0, my_ids.length-1));
				},
				onError : function() {
					$("#"+treeId).text("数据加载失败！");
				}
			});
			g.tree = $("#"+treeId).ligerGetTreeManager();
			if(g.tree){
				g.tree.clear();
				g.tree.data = [];
			}
			JAjax(url,params, "json", function(data){
				g.data= data;
				g.tree.setData(data);
				$("#"+treeId).css("width",width);
			}, null, true);
		}
	};
})(jQuery);
