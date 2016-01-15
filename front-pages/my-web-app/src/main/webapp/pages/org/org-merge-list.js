/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var OrgMergeList = function() {
		this.init();
	};
	OrgMergeList.prototype = {
			config : {			
				queryUrl : root+'/orgMergeJson/getListPage.action',
				delUrl : root+'/orgMergeJson/delete.action',
			},
			cache : {
				subPageId : null,
				orgType: null,
				grid : null,
				dialog : null,
				accordion : null,
				tree:null,
				tab :null,
				pTreeIdText : null,
				pTreeId : null,
				editId : null,
				searchParms : {},
			},
			init : function(){
				this.render();
				this.loadGird();
			},
			render : function(){
				var that = this;
				//布局
				$('#layout1').ligerLayout({leftWidth: '210', allowLeftResize: false, height: '100%',heightDiff:-1,space:4, onHeightChanged: that._heightChanged });
				$('.l-layout-header').show();
				$('.l-layout-header-toggle').css('z-index','2');
				//$(window).scrollTop() + $(window).height()
				
				$("#taskStatus").ligerComboBox({
		            data:[{id:'',text:'请选择'},{id:'SUCCESS',text:'合并成功'},{id:'PENDING',text:'进行中'},{id:'FAIL',text:'合并失败'}],
		            valueField:'id',
		            textField:'text',
		            selectBoxWidth:80,
		            autocomplete:true,
		            width:80
		        });
				
				$('#taskStatus').ligerGetComboBoxManager().selectValue("");
			},
			//高度改变
			_heightChanged : function(options){
				var that=window.orgMergeList;
				if(that.cache.tab){
					that.cache.tab.addHeight(options.diff);	
				}
				if (that.cache.accordion && options.middleHeight - 24 > 0){
					that.cache.accordion.setHeight(options.middleHeight - 24);	
				}  
			},
			
			loadGird : function(){
				var that = this;
				
				var funItems = [];
				funItems.push({id:'id_task_delete',permId:'task-delete', text:'删除'});
				funItems.push({id:'id_task_error_info',permId:'task-error-info',text:'查看原因'});
				
				var options = {};
				var columns = [
				               { display: '任务名称', name: 'taskName', align: 'left', width:'40%',
				            	   render : function(row){
				            		   return row.taskName;
				            	   }
				               },
				               { display: '类型', name: 'taskType', align: 'center', width:'10%',isSort:false,
				            	   render : function(row){
				            		   return row.taskType;				   
				            	   }
				               },
				               { display: '合并时间', name: 'issueDate', align: 'center', width:'15%',
				            	   render :function(row){
				            		   return row.issueDate;
				            	   }
				               },       				
				               { display: '状态',name:'taskStatus',  align: 'center', width:'10%',isSort:false,
				            	   render :function(row){
				            		   if(row.taskStatus=="PENDING"){
				            			   return "合并进行中";
				            		   }else if(row.taskStatus=="FAIL"){
				            			   return "合并失败";
				            		   }else if(row.taskStatus=="SUCCESS"){
				            			   return "合并成功";
				            		   }
				            	   }
				               },       				
				               { display: '操作', align: 'center', width:'10%',isSort:false,
				            	   render : function(row) {
				            		   var str = '';
				            		   //通过权限唯一标示生成操作列按钮
				            		   $(funItems).each(function() {
				            			   if ('task-delete' == this.permId && row.taskStatus!='PENDING') {
				            				   str+='<a href="javascript:window.orgMergeList.del(\''+row.id+'\')">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;';
				            			   }
				            			   if ('task-error-info' == this.permId && row.taskStatus=='FAIL') {
				            				   str+='<a href="javascript:window.orgMergeList.errorView(\''+row.description+'\')">失败原因</a>&nbsp;&nbsp;';
				            			   }
				            		   });
				            		   return str;
				            	   }
				               }
				               ];

				options['columns']=columns;
				options['checkbox']=true;
				options['sortName']='issuceDate';
				options['frozenCheckbox']=false;

				MALQ_UI.setGridOptions(options, that.config.queryUrl);

				$('#orgGridList').ligerGrid(options);		 
				that.cache.grid = $('#orgGridList').ligerGetGridManager();	        
				$('#orgGridList').css('top','3px');
				$('#pageloading').hide();//加载图片
			},
			//刷新Grid内容
			refreshGrid:function(){
				var that = window.orgMergeList;
				that.cache.grid.loadData(true);
				that.cache.mytree._refreshTree();
			}, 
			//一般查询
			search: function(){
				var that = this;

				var taskName = $('#taskName').val();
				if(taskName){
					that.__setGridParms('requestParam.like.taskName', '%'+taskName+'%');
				}else{
					that.__setGridParms('requestParam.like.taskName', '');
				}
				
				var taskStatus=$("#taskStatus").ligerComboBox().getValue();
				if(taskStatus){
					that.__setGridParms('requestParam.equal.taskStatus', taskStatus);
				}else{
					that.__setGridParms('requestParam.equal.taskStatus', taskStatus);
				}
				that.cache.grid.changePage('first'); 	
				that.cache.grid.loadData(true);
			},
			//高级查询
			advancedSearch: function(){
				var that = this;
				var parms = that.cache.grid.get('parms');
				that.cache.grid.showMyFilter(parms,that);         	     	
			},
			__setGridParms : function(name, value){
				var that = this;
				var parms = that.cache.grid.get('parms');
				var tem=[];
				$(parms).each(function(){
					if(this.name==name)
						return true;
					tem.push(this);
				});
				parms = tem;
				parms.push({name:name, value:value});
				that.cache.grid.set('parms', parms);
			},
			del:function(id){
				var that=this;
				$.ligerDialog.confirm('确定要删除吗？', function (yes){
					if(yes){        		  
						JAjax(that.config.delUrl,{'model.id':id}, 'json', function(data){
							if(data){
								JSWK.clewBox('删除成功！','clew_ico_succ');	
								that.refreshGrid();         		
							}else{
								JSWK.clewBox('删除失败！','clew_ico_fail',failShowTime);
							}                          			
						}, function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
					}
				});
			},
			//失败原因查看
			errorView :function(msg){
				$.ligerDialog.alert(msg, '失败原因', 'none');
			},
			alert:function(mes, css){
				JSWK.clewBox(mes, css, failShowTime);
			},
			resetText:function(){
				$("#taskName").val("");
				$('#taskStatus').ligerGetComboBoxManager().selectValue("");
			}
	};
	Date.prototype.Format = function(fmt)   
	{ //author: meizz   
	  var o = {   
	    "M+" : this.getMonth()+1,                 //月份   
	    "d+" : this.getDate(),                    //日   
	    "h+" : this.getHours(),                   //小时   
	    "m+" : this.getMinutes(),                 //分   
	    "s+" : this.getSeconds(),                 //秒   
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
	    "S"  : this.getMilliseconds()             //毫秒   
	  };   
	  if(/(y+)/.test(fmt))   
	    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	  for(var k in o)   
	    if(new RegExp("("+ k +")").test(fmt))   
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	  return fmt;   
	};
	
	$(document).ready(function() {
		window.orgMergeList = new OrgMergeList();
	});
})();      