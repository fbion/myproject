/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月23日
 */

(function($) {
	$.fn.treeSelect=function(args){
		var _treeSelect=new treeSelect();
		_treeSelect._defaultParams={};
		$.extend(_treeSelect._defaultParams, args);
		_treeSelect._defaultParams["thisObj"]=this.get(0);
		_treeSelect._init();
		return _treeSelect;
	};
	var treeSelect=function(){};
	treeSelect.prototype={
		_init:function(){
			this._render();
			this._initTree();
		},
		_render:function(){
			var g=this; 
			var p=this._defaultParams;
			var d =this._defaultParams.thisObj;
			var _html ="";
			var searchHtml = '&nbsp;<input type="text" id="treeSelect-search-input" style="width: '+(p.width-80)+'px;margin-left: 5px;" /> &nbsp; <input type="button" id="treeSelect-search-btn" value="查询"/>';	
			_html +="<div style='margin-top: 10px;'>"+(p.isHideSearch ? "": searchHtml)+"</div><div style='margin-top: 0px;margin-left: 5px;'><div id='treeSelect-tree'>加载数据失败...</div></div>";
			$(d).css("width",this._defaultParams.width+"px");
			$(d).css("top",(this._defaultParams.top?this._defaultParams.top:0)+"px");
			var tabHtml = "<div title='"+p.title+"'><div class='l-grid-body l-grid-body2 l-scroll' style='height:"+($(window).height()-40)+"px;width:100%;'>"+_html+"</div></div>";
			$(d).html(tabHtml); 
			$(d).ligerTab({
				changeHeightOnResize : true,
				contextmenu : false
			});
			$("#treeSelect-search-btn").bind('click', function() {
				g._initTree();
			});
		},
		
		_initTree:function(){
			var g=this, p=this._defaultParams;
			p.params = p.params || {};
			var name=$('#treeSelect-search-input').val();
			if(name){
				p.params['like[name]']='%' +name +'%';
			}else{
				p.params['like[name]']='';
			}
			
			var e = $('#treeSelect-tree');
			e.empty();
			e.ligerTree({
				slide : true,
				nodeWidth : p.width-60,
				checkbox : p.checkbox || false,
				idFieldName :p.idFieldName || 'id',
			    parentIDFieldName :p.parentIDFieldName || 'parentId',
				textFieldName : p.textFieldName || 'name',
				attribute : p.attribute || [ 'name', 'id' ],
				//data : p.data || [],
				onBeforeExpand : function(node){
					if(p.onBeforeExpand){
						p.onBeforeExpand(node, e.ligerGetTreeManager());
					}else{
						g.__loadData(e.ligerGetTreeManager(), node.target, p.url,{'equal[pid]': node.data.id});
					}	
				},
				onSelect : function(node){
				},
				onClick : function(node){
					p.onClick.call(this,node);
				},
				onError : function() {
					e.text('数据加载失败！');
				}
			});
			
			g.__loadData(e.ligerGetTreeManager(), null, p.url, p.params);
			
		},
		
		__loadData: function (tree, node, url, param){
			var g = this;
			if(g._defaultParams.data && g._defaultParams.data.length>0){
				tree.data=[];
				tree.setData(g._defaultParams.data);
			}else{
				tree.loading.show();
	            var ajaxtype = param ? 'post' : 'get';
	            param = param || [];
	            //请求服务器
	            $.ajax({
	                type: ajaxtype,
	                url: url,
	                data: param,
	                dataType: 'json',
	                timeout :  180000,
	                success: function (data){
	                    if (!data) return;
	                    //下面这个判断以后会有变化改进(如判断出错则TREE会有重复，且无根节点)
	                    if(url===root+'/simpleCode/findAllCode.action'){
	                    	var newdata=[];
		                    $(data).each(function(){
		                    	var dd = tree?tree.getDataByID(this.id):null;
		                    	if(this.id=='0' || dd)return true;
		                    	dd = this;
		                    	var old = MALQ_CODE.getParentCodeData();
		                    	var isChild = true;
		                    	$(old).each(function(){
		                    		if(dd.id==this.pid){
		                    			isChild=false;return;
		                    		}
		                    	});
		                    	if(isChild){
									this['isexpand']=true;
		                    	}else{
		                    		this['children']=[];
									this['isexpand']=false;
		                    	}
								newdata.push(this);
		    				});
		                    //alert(JSON.stringify(g._defaultParams.rootData));
		                    if(!tree.getDataByID('0'))newdata.push({id:'0',code:'root',name:'码表信息',pid:'rootroot', isexpand:true});
		                    data = newdata;
	                    }else{
	                    	var newdata=[];
	                    	$(data).each(function(){
	                    		var system=this;
	                    		var isadd=true;
			                    $(newdata).each(function(){
			                    	if(this.id===system.platform){
			                    		isadd=false;return;
			                    	}
			                    });
			                    if(isadd){
			                    	newdata.push({id:system.platform,platform:'root',name:MALQ_CODE.getCodeName('PLATFORM_TYPE', system.platform), isexpand:true});
			                    }
								newdata.push(system);
			    			});
	                    	data = newdata;
	                    }
	                    //alert(JSON.stringify(data));
	                    tree.loading.hide();
	                    tree.append(node, data);
	                    tree.trigger('success', [data]);
	                    if(data && data.length==1 && data[0]['pid'] && data[0]['pid']=='rootroot'){
	                    	//没有数据是将图标样子修改，以前是叶子节点图标，现在改为文件夹图标
	                    	var aa=$("#treeSelect-tree .l-body .l-tree-icon-leaf");
	                    	aa.removeClass("l-tree-icon-leaf");
	                    	aa.addClass("l-tree-icon-folder-open");  //l-box l-tree-icon l-tree-icon-folder-open 
	                    }
	                },
	                error: function (XMLHttpRequest, textStatus, errorThrown){
	                    try{
	                        tree.loading.hide();
	                        tree.trigger('error', [XMLHttpRequest, textStatus, errorThrown]);
	                    }
	                    catch (e){

	                    }
	                }
	            });
			}            
        }
	};
})(jQuery);