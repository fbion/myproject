/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月23日
 */

(function($) {
	
	$.fn.selectTree=function(args){
		var _selectTree =new selectTree();
		_selectTree._defaultParams={};
		_selectTree._initParams();
		_selectTree.tree ={};
		$.extend(_selectTree._defaultParams,args);
		_selectTree._defaultParams["thisObj"]=this.get(0);
		_selectTree._init();
		return _selectTree;
	};
	
	var selectTree=function(){};
	selectTree.prototype={
		_initParams:function(){
			$.extend(this._defaultParams,{
				id :"a1",
				title : '选择组织',
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
				},
				onBeforeShowData: null,
				onLoadDataError : null,
				onAfterShowData : null
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
			var _html = '<div style="width:'+p.width+'px; height: '+p.height+'"><input type="hidden" name="'+p.paramName+'" id="selectTree-paramName-'+p.id+'" />';
			_html +=p.title? ('<div style="height: 20px;text-align:center;">'+p.title+'</div>') : '';
			_html +='<div style="height: '+(p.height-20)+'px;border: 1px solid #ccc;overflow: auto;box-shadow: 0 0 1px #000 inset;">';
			_html +='<div style="margin-left: 5px;margin-top: 10px;width:'+p.width+'px;" id="selectTree-tree-'+p.id+'"></div>';
			_html +='</div></div>';
			_html+='</div>';
			$(d).html(_html);
		},
		_loadTreeData:function(){
			this._loadData(this._defaultParams.url,this._defaultParams.params,"selectTree-tree-"+this._defaultParams.id);
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
		        	$("#selectTree-paramName-"+g._defaultParams.id).val(my_ids.substring(0, my_ids.length-1));
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
			if(g._defaultParams.onBeforeShowData){
				g._defaultParams.onBeforeShowData(g.tree);
			}
			JAjax(url,params, "json", function(data){
				g.tree.setData(data);
				$("#"+treeId).css("width",width);
				if(g._defaultParams.onAfterShowData){
					g._defaultParams.onAfterShowData(g.tree);
				}
			}, function(er){
				g._defaultParams.onLoadDataError(er);
			}, true);
		} ,
		
		/**
		 * 初始化选择的权限
		 * */
		_getSelected:function(){
			var treeId="selectTree-tree-"+this._defaultParams.id;
			var g = this;
			var my_ids = "";
			var ligerTreeInput=$("#"+treeId).ligerGetTreeManager();
        	var checked = ligerTreeInput.getChecked(); 
        	if(checked){
        		$(checked).each(function(){  
            		my_ids +=this.data.id+',';
            	});
        		var incomplete = MALQ_TREE.getIncomplete(ligerTreeInput);       	
            	$(incomplete).each(function(){       		
            		my_ids +=this.data.id+',';
            	});     	
        	}
        	$("#selectTree-paramName-"+g._defaultParams.id).val(my_ids.substring(0, my_ids.length-1));
			
		}
		
	};
})(jQuery);
