/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月24
 */
(function($) {
	$.fn.multipleSelect=function(args){
		var _multipleSelect=new multipleSelect();
		_multipleSelect._defaultParams={};
		_multipleSelect.leftTree ={};
		_multipleSelect.rightTree ={};
		_multipleSelect.selectedNodeIds = {};
		_multipleSelect._initParams();
		$.extend(_multipleSelect._defaultParams,args);
		_multipleSelect._defaultParams["thisObj"]=this.get(0);
		_multipleSelect._init();
		return _multipleSelect;
	};
	var multipleSelect=function(){};
	multipleSelect.prototype={
		_initParams:function(){
			$.extend(this._defaultParams,{
				leftTitle : '未选择',
				rightTitle : '已选择',
				width : 500,
				height : 300,
				isNew : false,
				leftUrl : "org_data1.json",
				rightUrl : "org_data2.json",
				addBindUrl : "org_data2.json",
				delBindUrl : "org_data1.json",
				attribute :  [ 'name', 'id' ],
				idFieldName :'id',
			    textFieldName :'name',
			    parentIDFieldName :'parentId',
				onClick : function(node){
				}
			});
		},
		_init:function(){
			this._render();
			this._initEvent();
			this._renderLeftTree();
			this._renderRightTree();
		},
		_render:function(){
			var g=this; 
			var p=this._defaultParams;
			var d =this._defaultParams.thisObj;
			var _html = '<div style="width:'+p.width+'px; height: '+p.height+'">';
			_html += g._getLeftHtml(p)+g._getCenterHtml(p)+g._getRightHtml(p);
			_html+='</div>';
			$(d).html(_html);
		},
		_initEvent:function(){
			var g=this; 
			$("#multipleSelect-right-btn").bind('click', function() {
				if(g._defaultParams.isNew){
					g._addNewBind();
				}else{
					g._addBind();
				}
			});
//			$("#multipleSelect-right-btn").bind('click', function() {
//			});
//			$("#multipleSelect-left-btn").bind('click', function() {
//			});
			$("#multipleSelect-left-btn").bind('click', function() {
				if(g._defaultParams.isNew){
					g._delNewBind();
				}else{
					g._delBind();
				}
			});
//			$("#multipleSelect-left-search-btn").bind('click', function() {
//				g._renderLeftTree();
//			});
//			$("#multipleSelect-right-search-btn").bind('click', function() {
//				g._renderRightTree();
//			});
			
		},
		_getLeftHtml:function(p){
			var _html='<div class="l-scroll" style="width:'+(p.width-100)/2+'px; height: 100%; margin: 10px;float: left; border: 0px solid #ccc; overflow: none;">';
			_html +='<div style="height: 20px;text-align:center;">'+p.leftTitle+'</div>';
			_html +='<div style="height: '+(p.height-20)+'px;border: 1px solid #ccc;overflow: auto;box-shadow: 0 0 1px #000 inset;">';
//			_html +='<div style="margin: 10px;width:160px;">'+this._getSearchHtml(p,"left")+'</div>';
			_html +='<ul style="margin-left: 5px;margin-top: 10px;" id="multipleSelect-leftTree"></ul>';
			_html +='</div></div>';
			return _html;
		},
		_getSearchHtml : function(p,flag){
			var _html = '<input type="text" id="multipleSelect-'+flag+'-search-input" style="width: 100px;margin-left: 5px;" /> &nbsp; <input type="button" id="multipleSelect-'+flag+'-search-btn" value="查询"/>';
			return _html;
		},
		_getCenterHtml:function(p){
			var _html='<div style="width: 50px;margin-left: 10px;margin-top: '+(p.height-70)/2+'px;float: left;">';
			_html +='<input type="button" value="&nbsp;&gt;&gt;&nbsp;&nbsp;" id="multipleSelect-right-btn" /><br/><br/>';
//			_html +='<input type="button" value=" &nbsp; &gt; &nbsp;&nbsp; " id="multipleSelect-right-btn" /><br/><br/>';
//			_html +='<input type="button" value=" &nbsp; &lt; &nbsp;&nbsp; " id="multipleSelect-left-btn" /><br/><br/>';
			_html +='<input type="button" value="&nbsp;&lt;&lt;&nbsp;&nbsp;" id="multipleSelect-left-btn" />';
			_html +='</div>';
			return _html;
		},
		_getRightHtml:function(p){
			var _html='<div class="l-scroll" style="width:'+(p.width-100)/2+'px; height: 100%; margin: 10px;float: left; border: 0px solid #ccc; overflow: none;">';
			_html +='<div style="height: 20px;text-align:center;">'+p.rightTitle+'</div>';
			_html +='<div style="height: '+(p.height-20)+'px; border: 1px solid #ccc;overflow: auto;box-shadow: 0 0 1px #000 inset;">';
//			_html +='<div style="margin: 10px;width:160px;">'+this._getSearchHtml(p,"right")+'</div>';
			_html +='<ul style="margin-left: 5px;margin-top: 10px;" id="multipleSelect-rightTree"></ul>';
			_html +='</div></div>';
			return _html;
		},
		_renderLeftTree : function(){
			var params={};
			var name=$('#multipleSelect-left-search-input').val();
			if(name){
				params['like[name]']='%' +name +'%';
			}else{
				params['equal[pid]']=0;
			}	
			this.leftTree = this._renderTree(this._defaultParams.leftUrl, params, "multipleSelect-leftTree", this._defaultParams);
		},
		_renderRightTree : function(){
			var params={};
			var name=$('#multipleSelect-right-search-input').val();
			if(name){
				params['like[name]']='%' +name +'%';
			}else{
				params['equal[pid]']=0;
			}	
			this.rightTree = this._renderTree(this._defaultParams.rightUrl, params, "multipleSelect-rightTree", this._defaultParams);
		},
		_renderTree : function(url,params,treeId,p){
			$("#"+treeId).empty();
			$("#"+treeId).ligerTree({
				slide : true,
				checkbox : true,
				nodeWidth : 100,
				idFieldName :this._defaultParams.idFieldName || 'id',
			    parentIDFieldName :this._defaultParams.parentIDFieldName || 'parentId',
				textFieldName : this._defaultParams.textFieldName || 'name',
				attribute : this._defaultParams.attribute || [ 'name', 'id' ],
				onSelect : function(node) {
				},
				onClick : function(node){
					p.onClick.call(this,node);
				},
				onError : function() {
					$("#"+treeId).text("数据加载失败！");
				}
			});
			return this._loadTreeData(treeId,url, params);
		},
		_loadTreeData : function(treeId,url,params){
			var tree = $("#"+treeId).ligerGetTreeManager();
			if(tree){
				tree.data = [];
			}
			tree.loadData(null,url,params);
			return tree;
		},
		_addBind : function(){
			var g=this; 
			var p=this._defaultParams;
        	var checked = g.leftTree.getChecked(); 
        	if(checked ==''){
        		return;
        	}
        	var my_ids = "";
        	var temY =[];    
        	$(checked).each(function(){  
        		temY.push(this.data);
        		my_ids +=this.data.id+',';
        		  	
        	});
        	//加上未完全选中的
        	var incomplete = MALQ_TREE.getIncomplete(g.leftTree);       	
        	$(incomplete).each(function(){       		
        		my_ids +=this.data.id+',';
        	});        	
        	my_ids = my_ids.substring(0, my_ids.length-1);
        	
        	g.rightTree.clear();//清除       	
        	g.rightTree.loadData(0, p.addBindUrl, {"ids":my_ids});//重新加载
        	//删除nTree
        	$(temY).each(function(){
        		g.leftTree.remove(this);
        	});	
      },
      _delBind : function(){
    	  	var g=this; 
			var p=this._defaultParams;
        	var checked = g.rightTree.getChecked(); 
        	if(checked ==''){
        		return;
        	}
        	var my_ids = "";
        	var temN =[];    
        	$(checked).each(function(){  
        		temN.push(this.data);
        		my_ids +=this.data.id+',';
        		  	
        	});
        	my_ids = my_ids.substring(0, my_ids.length-1);
        	
        	g.leftTree.clear();//清除       	
        	g.leftTree.loadData(0, p.delBindUrl, {"ids":my_ids});//重新加载
        	//删除yTree
        	$(temN).each(function(){
        		g.rightTree.remove(this);
        	});	
      },
      _existNode : function(g,descData,node){
	   		 for(var i in descData){
	   			 if(descData[i].id == node.id){
	   				 return true;
	   			 }else{
	   				 if(descData[i].children && descData[i].children.length > 0){
	   					 if(!!g._existNode(g,descData[i].children,node)){
	   						 return true;
	   					 }
					}
	   			 }
	   		 }
		},
      _addNewBind : function(){
    	  	var g=this; 
	      	var checked = g.leftTree.getChecked(); 
	      	if(checked ==''){
	      		return;
	      	}
	      	var temY =[];    
	      	$(checked).each(function(){  
	      		temY.push(this.data);
	      	});
	      //删除nTree
	      	$(temY).each(function(){
	      		g.leftTree.remove(this);
	      	});	
	      	
	      	var descData = [];
        	for(var i in temY){
        		if(descData.length ==0){
        			descData.push(temY[i]);
        		}else{
        			if(!g._existNode(g,descData,temY[i])){
        				descData.push(temY[i]);
        			}
        		}
        	}
	      	
	      	var treeData = g.rightTree.getData();
	      	$(descData).each(function(){
	      		treeData.push(this);
	      	});	
	      	g.rightTree.clear();
        	g.rightTree.data =[];
        	g.rightTree.setData(treeData);
        	var arr=[],ids=[];
        	g._getTreeData2Arr(g,arr,ids,treeData);
        	g.selectedNodeIds = ids.join(",");
      },
      _getTreeData2Arr : function(g,arr,ids,treeData){
    	  if(treeData && treeData.length >0){
    		  for(var i in treeData){
    			  arr.push(treeData[i]);
    			  ids.push(treeData[i].id);
    			  if(treeData[i].children && treeData[i].children.length > 0){
    				  g._getTreeData2Arr(g,arr,ids,treeData[i].children);
    			  }
    		  }
    	  }
      },
      _delNewBind : function(){
		 var g=this; 
		 var p=this._defaultParams;
		  
		var checked = g.rightTree.getChecked(); 
      	if(checked ==''){
      		return;
      	}
      	var temN =[];    
      	$(checked).each(function(){  
      		temN.push(this.data);
      	});
      //删除yTree
    	$(temN).each(function(){
    		g.rightTree.remove(this);
    	});	
      	
      	var treeData = g.rightTree.getData();
      	var arr =[],ids =[];
      	g._getTreeData2Arr(g,arr,ids,treeData);
      	g.leftTree.clear();
      	g.leftTree.data =[];
      	JAjax(p.leftUrl, {}, "json", function(data) {
      		g.leftTree.setData(data);
      		$(arr).each(function(){
          		g.leftTree.remove(this);
        	});
      	}, null, true); 
      	g.selectedNodeIds = ids.join(",");
      }
	};
})(jQuery);