/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月22日 下午2:33:27 
 */

(function($) {
	$.fn.orgTreeSelect=function(args){
		var _orgTreeSelect=new orgTreeSelect();
		_orgTreeSelect._defaultParams={};
		_orgTreeSelect.currentTabid = 'tabitem1';
		_orgTreeSelect._initParams();
		$.extend(_orgTreeSelect._defaultParams, args);
		_orgTreeSelect._defaultParams["thisObj"]=this.get(0);
		_orgTreeSelect._init();
		return _orgTreeSelect;
	};
	var orgTreeSelect=function(){};
	orgTreeSelect.prototype={
		_initParams:function(p){
			$.extend(this._defaultParams,{
				innerTitle : '内部组织',
				externalTitle : '外部组织',
				url : root+'/orgJson/getList.action',
				params:[],
				clicktime:500,
				node:null,
				//innerParams : [{name:'requestParam.equal.orgType', value:'INNER_UAA_ORG'}],
				//externalParams : [{name:'requestParam.equal.orgType',value:'EXTERNAL_UAA_ORG'}],
				width : 200,
				//idFieldName :'id',
				//textFieldName :'orgShortName',
				//parentIDFieldName :'pid',
				attribute :  [ 'name', 'id' ],
				onClick : function(node){
				}
			});
		},
		_init:function(){
			this._render();
			this._loadInnerOrg();
		},
		_render:function(){
			var g=this; 
			var p=this._defaultParams;
			var d =this._defaultParams.thisObj;
			var w = this._defaultParams.width;
			var h = this._defaultParams.height?this._defaultParams.height:$(window).height();
			var innerHtml ="",externalHtml ="";
			innerHtml +="<div style='margin-top: 10px;'>"+g._getSearchHtml('inner')+"</div><div style='margin-top: 10px;margin-left: 5px;'><div id='orgTreeSelect-innerTree'>加载内部组织树失败...</div></div>";
			externalHtml +="<div style='margin-top: 10px;'>"+g._getSearchHtml('external')+"</div><div style='margin-top: 10px;margin-left: 5px;'><div id='orgTreeSelect-externalTree'>加载外部组织树失败...</div></div>";
			$(d).css("width",w+"px");
			$(d).css("top",(this._defaultParams.top?this._defaultParams.top:0)+"px");
			var tabHtml = "<div title='"+p.innerTitle+"' >" +
								"<div id='innerScroll' class='l-scroll' style='overflow:auto;height:"+h+"px;width:100%;'>"+innerHtml+"</div>" +
						  "</div>" +
						  "<div title='"+p.externalTitle+"' >" +
								"<div id='externalScroll' class='l-scroll' style='overflow:auto;height:"+h+"px;width:100%;'>"+externalHtml+"</div>" +
						  "</div>";
			$(d).html(tabHtml);
			$(d).ligerTab({
				contextmenu : false,
				onBeforeSelectTabItem : function(tabid){
					g.currentTabid = tabid;
					if (tabid == 'tabitem1') {
						g._loadInnerOrg(g);
					}else{
						g._loadExternalOrg();
					}
				}
			});
			
			$('#orgTreeSelect-inner-search-btn').bind('click', function() {
				var params=[{name:'equal[orgType]', value:'INNER_UAA_ORG'}];
				var name=$('#orgTreeSelect-inner-search-input').val();
				var defaultId = parent.parent.INDEX_USER_ORGID?parent.parent.INDEX_USER_ORGID:parent.parent.parent.INDEX_USER_ORGID;
				if(name){
					params.push({name:'like[orgShortName]',value:'%' +name +'%'});
				}else{
					params.push({name:'equal[defaultId]', value:defaultId});
				}
				params.push({name:'rows', value:50});
				params.push({name:'page', value:1});
				p.params=params;
				g.__initTree('orgTreeSelect-innerTree', p);
				
			});
			$('#orgTreeSelect-external-search-btn').bind('click', function() {
				var params=[{name:'equal[orgType]', value:'EXTERNAL_UAA_ORG'}];
				var name=$('#orgTreeSelect-external-search-input').val();
				if(name){
					params.push({name:'like[orgShortName]',value:'%' +name +'%'});
				}
				params.push({name:'rows', value:50});
				params.push({name:'page', value:1});
				p.params=params;
				g.__initTree('orgTreeSelect-externalTree', p);
			});
			
			//var scroll_h = 0; //滚动距离总长(注意不是滚动条的长度)
			//var scroll_t = 0;   //滚动到的当前位置
			var inner_dh = $('#innerScroll').height();
			$('#innerScroll').scroll(function(){
				g._treeScrollLoadData({
							scroll_h:$(this)[0].scrollHeight,
							scroll_t:$(this)[0].scrollTop,
							scroll_dh:inner_dh,
							treeId:'orgTreeSelect-innerTree'
					});
			});
			
			var external_dh = $('#externalScroll').height();
			$('#externalScroll').scroll(function(){
				g._treeScrollLoadData({
					scroll_h:$(this)[0].scrollHeight,
					scroll_t:$(this)[0].scrollTop,
					scroll_dh:external_dh,
					treeId:'orgTreeSelect-externalTree'
				});
			});
		},
		
		
		_getSearchHtml : function(flag){
			var _html = '&nbsp;<input type="text" id="orgTreeSelect-'+flag+'-search-input" style="width: '+(this._defaultParams.width-80)+'px;margin-left: 5px;" /> &nbsp; <input type="button" id="orgTreeSelect-'+flag+'-search-btn" value="查询"/>';
			return _html;
		},
		
		_treeScrollLoadData : function(o){
			var g = this;
			var p=this._defaultParams;
			if(o.scroll_t>0 && o.scroll_t + o.scroll_dh + 60 >= o.scroll_h){
				//alert('滚动条到底部了');
				var params = [];
				$(p.params).each(function(){
					if(this.name=='page'){
						this.value=this.value+1;
					}
					params.push(this);
				});
				p.params=params;
				g.__loadData(o.treeId, p.node, p.url, p.params);
			}    
		},
		
		_loadInnerOrg:function(g){
			var p=this._defaultParams;
			var defaultId = parent.parent.INDEX_USER_ORGID?parent.parent.INDEX_USER_ORGID:parent.parent.parent.INDEX_USER_ORGID;
			var params=[
			            	{name:'equal[orgType]', value:'INNER_UAA_ORG'},
			            	{name:'equal[defaultId]', value:defaultId}  
			            ];
			params.push({name:'rows', value:50});
			params.push({name:'page', value:1});
			p.params=params;
			this.__initTree('orgTreeSelect-innerTree', p);
			if(g)
				this._defaultParams.onClickTab.call(this,'innerTab');
		},		
		_loadExternalOrg : function(){
			var p=this._defaultParams;
			var params=[{name:'equal[orgType]',value:'EXTERNAL_UAA_ORG'}];
			params.push({name:'rows', value:50});
			params.push({name:'page', value:1});
			p.params=params;
			this.__initTree('orgTreeSelect-externalTree', p);
			this._defaultParams.onClickTab.call(this,'externalTab');
		},
		
		_refreshTree : function(){
			var that = this;
			var p=this._defaultParams;
			var orgType='INNER_UAA_ORG';
			$(p.params).each(function(){
				if(this.name='equal[orgType]')
					orgType=this.value;
			});
			if(orgType=='EXTERNAL_UAA_ORG')
				that._loadExternalOrg();
			else
				that._loadInnerOrg();
			//var treeId = orgType=='EXTERNAL_UAA_ORG'?'orgTreeSelect-externalTree':'orgTreeSelect-innerTree';
			//that.__initTree(treeId, p);
			//that.__loadData(treeId, p.node, p.url, p.params);
		},
		__initTree : function(treeId, p){
			var that=this;
			$("#"+treeId).empty();
			$("#"+treeId).ligerTree({
				slide : true,
				checkbox : false,
				nodeWidth : p.width-60,
				idFieldName :'id',
				textFieldName :'orgShortName',
				parentIDFieldName :'pid',			
				onBeforeExpand: function(node){
					//alert(JSON.stringify(node.data))
					//if (node.data.children && node.data.children.length == 0){
						var now = new Date().getTime();
						var per = p.clicktime;
						if((now-per)>500){//
							p.clicktime=now;
							
							var parent=$('#'+treeId).ligerGetTreeManager().getDataByID(node.data.pid);
							if(parent && parent.children.length>0){
								$(parent.children).each(function(){
									if ($('#'+this.id+' > div > div').hasClass("l-box l-expandable-open"))	
										$('#'+this.id+' > div > div.l-box.l-expandable-open').click();
								});
							}else{
								$('#'+treeId).ligerGetTreeManager().collapseAll();
							}
							var id = node.data.id;
							var orgType='INNER_UAA_ORG';
							$(p.params).each(function(){
								if(this.name=='equal[orgType]'){
									orgType=this.value;
								}
							});
							var params = [];
							params.push({name:'rows', value:50});
							params.push({name:'page', value:1});
							params.push({name:'equal[pid]', value:id});
							params.push({name:'equal[orgType]', value:orgType});
							p.params=params;
							p.node=node.target;//alert(p.url+"::"+JSON.stringify(p.params))
			            	that.__loadData(treeId, node.target, p.url, p.params); 
						}	
		            //}
				},
				onSelect : function(node) {
					//p.onSelect.call(this,node);
				},
				onClick : function(node){
					var now = new Date().getTime();
					var per = p.clicktime;
					//alert((now-per))
					if((now-per)>500){
						p.clicktime=now;
						p.onClick.call(this,node);
					}
				},
				onError : function() {
					$('#'+treeId).text('组织数据加载失败！');
				}
			});
			
			var tree = $('#'+treeId).ligerGetTreeManager();
			if(tree){
				tree.data = [];
			}
			p.node=null;
			that.__loadData(treeId, null, p.url, p.params);
			return tree;
		},
		
		__loadData: function (treeId, node, url, param){
			//alert(treeId+"::"+node+"::"+url+"::"+JSON.stringify(param))
				var that = this;
	            var g = $('#'+treeId).ligerGetTreeManager();
	            g.loading.show();
	            var ajaxtype = param ? 'post' : 'get';
	            param = param || [];
	            //请求服务器
	            $.ajax({
	                type: ajaxtype,
	                url: url,
	                data: param,
	                dataType: 'json',
	                success: function (data){
	                    if (!data) return;
	                    //alert(JSON.stringify(data));
	                    var newdata=[];
	                    $(data).each(function(){
	                    	var row = this;
	                    	var dd = g?g.getDataByID(row.uaaOrg.id):null;
	                    	if(dd)return true;
	                    	var pp = row.topologys;
	                    	row['pid']=(pp && pp[0])?pp[0].parentOrgid:'';
	                    	that.__setTreeShow(row);
	                    	newdata.push(row);
	    				});	                   
	                    g.loading.hide();
	                    g.append(node, newdata);
	                    g.trigger('success', [newdata]);
	                },
	                error: function (XMLHttpRequest, textStatus, errorThrown){
	                    try{
	                        g.loading.hide();
	                        g.trigger('error', [XMLHttpRequest, textStatus, errorThrown]);
	                    }
	                    catch (e){

	                    }
	                }
	            });
	        },
	        __setTreeShow : function(row){	
	        	if(row.uaaOrg.id==parent.parent.INDEX_USER_ORGID){
            		row['isexpand']=true;
            	}else{
            		row['children']=[];
            		row['isexpand']=false;
            	}
	        	
	        	row['id']=row.uaaOrg.id;
            	row['orgShortName']=row.uaaOrg.orgShortName;
            	var iconName = (row.tags && row.tags.length>0)?row.tags[0].tag:'';
            	if(iconName){
            		row['icon']='../../images/icons/org/'+iconName+'_close.png';
            	}
            	return row;
	        }
		
	};
})(jQuery);
