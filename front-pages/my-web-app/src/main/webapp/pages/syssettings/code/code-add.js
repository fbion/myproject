/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var CodeAdd = function() {
		this.init();
	};
	CodeAdd.prototype = {
		config : {
			addUrl : root+'/simpleCode/addCode.action',
			editUrl :  root+'/simpleCode/editCode.action',
			queryObjectUrl : root+'/simpleCode/getCodeById.action',
			//检查属性是否唯一
			chackerUrl : root+'/simpleCode/checkExist.action'
		},
		cache : {
			dialog : null
		},
		init : function(){
			this.initAddOrEdit();
			this.render();
			this.loadCss();
		},
		render : function(){
			var that = this;
		    //初始化页面检验规则
		    $.metadata.setType('attr', 'validate');
	        $('#form1').validate({
	            rules: {
	            	code:{
	        			remote:{
	        				url: that.config.chackerUrl,
	        				type: 'POST', 
	        				dataType: 'json', 
	        				data:{ //要传递的数据
	        				    //'model.code': function(){return $('#code').val();},
	        				    'id' : function(){return $("#id").val();},
	        				    'pid': function(){return $('#pCodeId', parent.document).val();},
	        				    'typeCode' :function(){return $('#pCode',parent.document).val();},
	        				    'model.code' : function(){return $('#code_exist').val();},
	        				    'message' : 'code'
	        				 }
	        			   }
	        		 }
	        	},
	        	messages: {
	        		code:{remote: '编码在根节点下不唯一, 或在同一类型下已经存在'}
	        	},
	        	errorPlacement: function (lable, element){
	        		CSM_VALIDATE.errorPlacement(lable, element);
		        },
	            success: function (lable){
	            	CSM_VALIDATE.success(lable);
	            },
	            submitHandler: function(){
	            	showMask();
	                var temData = {};
	                params = {
	        			'model.typeCode': $('#typeCode').val(), 
	        			'model.typeName': $('#typeName').val(),
	        			'model.code': $('#code').val(),
	        			'model.name': $('#name').val(),
	        			'model.status': $('#status_val').val(),
	        			'model.description': $('#description').val()
	            	};
	                temData['typeCode']=$('#typeCode').val(); 
	                temData['typeName']=$('#typeName').val();
	                temData['code']=$('#code').val();
	                temData['name']=$('#name').val();
	                temData['status']=$('#status_val').val();
	                temData['description']=$('#description').val();         
	                var url;
	                if('add'==parent.window.codeList.cache.subPageId){
	                	var pid = $('#pCodeId', parent.document).val(); 
	                	params['model.pid'] = pid; 
	                	temData['pid']=pid;
	                	temData['id']=new Date().getTime();
	                	url = that.config.addUrl;                   	
	                }else{
	                	var pid = $('#pCodeId', parent.document).val();
	                	params['model.pid'] = pid; 
	                	params['model.id'] = $('#id').val(); 
	                	temData['pid']=pid;
	                	temData['id']=$('#id').val();
	                	url = that.config.editUrl;               	            	                	
	                }              
	                JAjax(url, params, 'json', function(data){   
	                	hideMask();
	                	if(data){           
	                		parent.window.codeList.cache.grid.loadData();
	                		var falg = 'add'==parent.window.codeList.cache.subPageId?'add':'edit';
	                		parent.window.codeList.flushCodeList(temData, falg);//刷新缓存 
	                		parent.window.codeList.success_alert(data.message);       		
	                		parent.window.codeList.cache.dialog.close();                		
	                	}else{
	                		 JSWK.clewBox(data,'clew_ico_fail',failShowTime);
	                	}
	                	
					}, function(){hideMask();JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
	            }
	        });
	       
		},
		
		initAddOrEdit:function(){
			var that = this;
			var pid =$('#pCodeId', parent.document).val();
			var pCode = $('#pCode', parent.document).val();
			var pCodeName = $('#pCodeName', parent.document).text();
		    if(pCode && pCodeName){
		    	$('#form1').find('#typeCode').val(pCode);		        	
		        $('#form1').find('#typeName').val(pCodeName);	       
		    }else if(0 == pid){	    	 
		    	$('#form1').find('#typeCode').val(pCode);		        	
		        $('#form1').find('#typeName').val(pCodeName);	        	
		    }
		    
		    MALQ_CODE.getSelectByData($('#status'),[{id:'1',text:'启用'},{id:'0',text:'停用'}], 180, 80);		
		  //取值判断是增加还是修改
	        var pageId =parent.window.codeList.cache.subPageId; 
	        //取值判断是增加还是修改
	        /*var pageId =parent.window.codeList.cache.subPageId;     */ 
		    /*var pageId = "add";*/
	        if(pageId == 'edit'){ 
	        	$("#statusContent").append('<input name="status_val" type="hidden" id="status_val" />');
	        	$("#statusContent").append('<span id="statusDiv" />');
	        	   var id = parent.window.codeList.cache.codeId; 
	        	   JAjax(that.config.queryObjectUrl,{'id':id}, 'json', function(data){
	        		   if(data){
							$('#name').val(data.name);
							$('#code').val(data.code);
						    $('#code_exist').val(data.code);
						    $('#description').text(data.description);
						    //var typeName = MALQ_CODE.getCodeNameByid(data.pid);
						    $('#typeName').val(data.typeName);
						    $('#typeCode').val(data.typeCode);						    
						    var status = $('#status').ligerGetComboBoxManager();
						    status.setValue(data.status=='1'?'1':'0');						    
						    status.setDisabled('');
						    $('#status').css('color','black');
						    $('#id').val(data.id); 
	        		   } 
	        	   }, function(){JSWK.clewBox('查询码表对象失败','clew_ico_fail',failShowTime);}, true);     	   
				}else{
					$('#status').ligerGetComboBoxManager().selectValue('1');
				}
		 },
		 loadCss : function(){
			 $('form').ligerForm();
		     $('#name').focus();
		 },
		 reset : function(){
			 window.location='code-add.jsp';
		 }
	};
	
	$(document).ready(function() {
		window.codeAdd = new CodeAdd();
	});
})();