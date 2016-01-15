/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var ChangeOrgParent = function() {
		this.init();
	};
	ChangeOrgParent.prototype = {
		config : {			
			queryUrl : root+'/orgJson/getListPage.action',
			queryObjUrl : root + '/orgJson/getObjectById.action',
			subHeight : subHeight,
			subWidth : subWidth
		},
		cache : {
			subPageId : null,
			pid : null,
			grid : null,
			dialog : null,
			accordion : null,
			tab :null,
			searchParms : {}
		},
		init : function(){
			
			var str = window.location.href;
			var es = /\?id=/;
			es.exec(str);
			var bindId = es.exec(str)?RegExp.rightContext:'';
			this.render(bindId);
			this.loadGird(bindId);
		},
		render : function(bindId){
			var that = this;
			
			if(bindId){
				JAjax(that.config.queryObjUrl,{'model.uaaOrg.id':bindId}, 'json', function(data){
					if(data){
						//$('#pTreeIdText').text(data.uaaOrg.orgShortName);
						//$('#pTreeId').val(bindId);
						//alert(JSON.stringify(data))
						$(data.topologys).each(function(){
							if(this.category=='JCFWPT'){
								that.cache.pid=this.parentOrgid;
								return;
							}
						});
					}
				}, function(){JSWK.clewBox('查询当前上级时发生异常','clew_ico_fail',failShowTime);}, false);
			}
			
			$('#orgType').val($('#orgType', parent.document).val());
			 //布局
	        $('#layout1').ligerLayout({leftWidth: '210', allowLeftResize: false, height: '100%',heightDiff:-1,space:4, onHeightChanged: that._heightChanged });
	        $('.l-layout-header').show();
		    
		    $('#pageloading').hide();	
		    $('.l-layout-header-toggle').css('z-index','2');
		   
		},
		//高度改变
		_heightChanged : function(options){
			var that=window.changeOrgParent;
	        if(that.cache.tab){
	          that.cache.tab.addHeight(options.diff);	
	        }
	        if (that.cache.accordion && options.middleHeight - 24 > 0){
	          that.cache.accordion.setHeight(options.middleHeight - 24);	
	        }  
	    },

		loadGird : function(bindId){
			var that = this;
			//生成表格上面的按钮菜单///////////////////////////////
			var option='';
			var opdata = MALQ_CODE.getCodeData('PLATFORM_TYPE');
			$(opdata).each(function(){
				if(this.code=='JCFWPT')
					option+="<option value='"+this.code+"' selected>"+this.name+"</option>";
				else
					option+="<option value='"+this.code+"'>"+this.name+"</option>";
			});
			var myitems =[];
			myitems.push({line:true});
			myitems.push({text:'拓扑类别:'});
			myitems.push({select:true, elementID:"categoryID",elementWidth:"150px", elementOption:option});
			
			var options = {};
			var columns = [
							{ display: '组织简称', name: 'orgShortName', align: 'left', width:'30%',
								render : function(row){
									if(row.uaaOrg.id =='root')
										return row.uaaOrg.orgShortName;
									else
										return '<a href="javascript:window.changeOrgParent.showOrgDetail(\''+ row.uaaOrg.id + '\');">'+row.uaaOrg.orgShortName+'</a>';							   
								}
							},
							{ display: '组织名称', name: 'orgName', align: 'left', width:'40%', isSort:false,
								render : function(row){
									 return row.uaaOrg.orgName;							   
								}
							},
							{ display: '组织编码', name: 'orgCode', align: 'left', width:'15%', isSort:false,
								render :function(row){
									return row.uaaOrg.orgCode;
								}
							}      				
							
			                ];
			 			 
			 options['columns']=columns;
			 options['checkbox']=true;
			 options['frozenCheckbox']=false;
			 options['sortName']='orgCode';
			 options['onSuccess']=function(data,grid){
				 if(bindId){
					 var tem =[];
					 if('admin'==parent.parent.INDEX_USER_TYPE)
						 tem.push({'uaaOrg':{id:'root',orgName:'root','orgShortName':'root',orgCode:''}});
					 $(data.Rows).each(function(){
						 var row = this;
						if(row.uaaOrg.id==bindId){
							return true;
						}
						tem.push(row);
					 });
					 data.Rows = tem; 
				 } 
			 };
			 //options['tree']={columnName: 'orgShortname'};
			 //options['usePager']=false;
			 var parms = [];
			 parms.push({name: 'requestParam.equal.queryType', value:'changeParent'});
			 parms.push({name: 'requestParam.equal.orgType', value:$('#orgType', parent.document).val()});
			 if(bindId)
				 parms.push({name: 'requestParam.notequal.orgid', value:bindId});
			 if(that.cache.pid)
				 parms.push({name: 'requestParam.notequal.pid', value:that.cache.pid});
			 options['parms']=parms; //设置默认进来的请求参数 
			 options['toolbar']={ items: myitems};//表格上面菜单
			 MALQ_UI.setGridOptions(options, that.config.queryUrl);
			 		 
			$('#orgGridList').ligerGrid(options);		 
			that.cache.grid = $('#orgGridList').ligerGetGridManager();	        
	        $('#orgGridList').css('top','3px');
	        $('#pageloading').hide();//加载图片
		},
		
		//一般查询
		search: function(){
			var that=this;
			var parms = that.cache.grid.get('parms');
			
			var tem=[];
			$(parms).each(function(){
				if(this.name=='requestParam.like.orgName')
					return true;
    			tem.push(this);
			});
			parms = tem;
			var orgName = $('#orgName_find').val();
	    	if(orgName){
	    		parms.push({name:'requestParam.like.orgName',value:'%'+orgName+'%'});
	    	}
	    	//alert(JSON.stringify(parms));
	    	that.cache.grid.set('parms', parms);     	
	    	that.cache.grid.loadData(true);
	    },
	  //高级查询
	    advancedSearch: function(){
	    	var that = window.changeOrgParent; 
	    	var parms = that.cache.grid.get('parms');
	    	that.cache.grid.showMyFilter(parms, that);     	     	
	     },
	     showOrgDetail:function(id){
	     	var that=this;  	
	      	var url = 'org-info.jsp?id='+id;
	      	that.cache.dialog = MALQ_UI.open(url, subHeight, subWidth, '组织详细信息');      	
	     },
	     
	     f_select : function(){
	    	 var that = window.changeOrgParent;
	    	 var rows = that.cache.grid.getCheckedRows();
	    	 if(rows && rows.length==1){
	    		 var category = $('#categoryID').val();
	    		 var row = rows[0];
	    		 row['category']=category;
	    		 return row;
	    	 }else{
	    		 JSWK.clewBox('有且只有一个上级, 请选择一个组织','clew_ico_fail',failShowTime);
	    	 }
	    	 return;
	     }
	};
	$(document).ready(function() {
		window.changeOrgParent = new ChangeOrgParent();
	});
})();      