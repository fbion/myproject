/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2015
 * 2015年3月2日 下午2:33:27 
 */
(function() {
	var Merchant = function() {
		this.init();
	};
	Merchant.prototype = {
		config : {
			addUrl : root+'/merchant/addMerchant.action',
			editUrl : root + '/merchant/editMerchant.action',
			queryObjectUrl : root + '/merchant/getMerchantById.action',
			//检查属性是否唯一
			chackerUrl : root+'/merchant/checkExist.action',
			//取商户类型
			querySimpleCodeUrl : root+'/simpleCode/findMerchant.action'
		},
		cache : {			
			dialog_001 : null,
			form : null,
			publicKey:null,
			privateKey:null,
			tags : []
		},
		init :function(){
			this.initHtml();
			this.initLayoutFrom();
			this.initFormData();		
		},
		
		initHtml : function(){			
			var html='';
			html+='<div title="商户信息" id="myPanel"><form id="form1">';
			html+='			 <div id="myform" style="clear:both;"></div>';
			html+='</form></div>';
    
			$('#layout').html(html);
		},
		
		initLayoutFrom : function(){
			var that = this;
			
			var fields =[
							{ name: 'id', type: 'hidden' },
							{ name: 'storeCode', type: 'hidden' },
							{ name: 'existStoreName', type: 'hidden' },
							
							{ label: '商户名称', name: 'storeName', validate: {required:true,minlength:2,maxlength:60}, type: 'text', group: '基本信息', groupicon: root+'/images/group.gif' },
							{ label: '商户类型', name: 'storeType', validate: {required:true,minlength:1,maxlength:8}, newline: false, type: 'combobox', 
								textField: 'storeType__Text',
								editor: {
									data: MALQ_CODE.getCodeData('2000'),
									isMultiSelect: false,
									valueFieldID: 'storeTypeText001',
									textField :'name',
						  			valueField :'code', 
									width:140
									//selectBoxWidth:400,
									//selectBoxHeight:200
							    }
							},
							{ label: '联系人', name: 'contactUser', validate: {required:true,minlength:2,maxlength:30}, type: 'text'},
							{ label: '联系电话', name: 'contactPhone', validate: {required:true,minlength:11,maxlength:11}, newline: false, type: 'text'},
							{ label: '状态', name: 'storeStatus', validate: {required:true,minlength:1,maxlength:8}, type: 'combobox', 
								textField: 'storeStatus__Text',
								editor: {
									data: MALQ_CODE.getCodeData('3000'),
									textField :'name',
						  			valueField :'code',
									isMultiSelect: false,
									valueFieldID: 'storeStatusText001',
									width:140
							    }
							},
							{ label: '商户编号', name: 'storeCode', validate: {required:true}, newline: false, type: 'text'},
			             ];
			
//			fields.push({ label: '商户公钥', name: 'publicKey', type: 'textarea', width:'400', validate:{required:true,maxlength:512}});
//			//做权限控制
//			if(true){			
//				fields.push({ label: '商户私钥', name: 'privateKey', type: 'textarea', width:'400', validate:{required:false,maxlength:2048}});
//			}
//			
//			if(parent.window.merchantList.cache.subPageId==='add'){
//				fields.push({ label: '<a href="javascript:window.merchant.secretKey()">生成秘钥对</a>', 
//					labelWidth:'500', name: '___span1', type:'span',  rightToken:' '});
//				fields.push({ label: '<input id="check1" name="check1" class="check1" type="checkbox" checked="checked" />&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:window.merchant.showClause()">我同意【服务协议】和【隐私条款】</a>', 
//					name: '___span2', type:'span', labelWidth:'500', rightToken:' ', group: '其它信息', groupicon: root+'/images/group.gif'});
//			}else{
//				fields.push({ label: '<a href="javascript:window.merchant.secretKey()">生成新秘钥对</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:window.merchant.reset()">恢复原密钥对</a>', 
//					labelWidth:'500', name: '___span1', type:'span',  rightToken:' '});
//			}
			
			
			that.cache.form = $('#myform').ligerForm({ 
	            labelCss: 'labelcontainer',
	            fieldCss: 'fieldcontainer',
	            inputWidth: 180,
	            labelWidth: 90,
	            labelCss: 'myLabelCss',
	            space: 80,
	            fields: fields,
	            //tab : tab,
	            validate:true
	      	});
		
			//that.cache.form.set({validate:true});//验证开关
		
		},
		
		secretKey : function(){
			var that = this;
			var url = 'rsa.jsp';
			var title = '生成秘钥对';
			that.cache.dialog = MALQ_UI.open_button(url, subHeight+20,subWidth, 
					function(item, dialog){
						var publicKey = dialog.frame.window.ras.cache.publicKey;
						var privateKey = dialog.frame.window.ras.cache.privateKey;
						$('#publicKey').val(publicKey);
						//$('#privateKey').val(privateKey);
						dialog.close();
				}, null, title);
		},
		
		reset : function() {
			var that=this;
			$("#privateKey").val(that.cache.privateKey);
			$("#publicKey").val(that.cache.publicKey);
		},
		
		showClause:function(){
			var that = this;
			var url = "merchant-clause.jsp";
			var title = "服务协议与隐私条款";
			that.cache.dialog = MALQ_UI.open(url, subHeight,subWidth, title ); 
		},
		
		initFormData : function(){
			var that=this;
			if(parent.window.merchantList.cache.subPageId==='add'){//增加页面
				liger.get('storeType').selectValue('2001');
				liger.get('storeStatus').selectValue('3002');
			}else{
				var id = parent.window.merchantList.cache.id;
				JAjax(that.config.queryObjectUrl, {'id':id}, 'json', function(data){
					if(data){
						that.cache.form.setData(data);
						$('#existStoreName').val(data.storeName);
						that.cache.publickKey=data.publickKey;
						that.cache.privateKey=data.privateKey;
					}else{
						JSWK.clewBox('未查到['+id+']对应的商户信息','clew_ico_fail',failShowTime);
					}
					
				}, function(){JSWK.clewBox('查询商户信息时发生异常','clew_ico_fail',failShowTime);}, true);
				
			}		
		},
		
		
		setCss : function(){
			var dragging = false;		
		 	$('#formDesign li.l-fieldcontainer').live('mouseover', function (){
			            if (dragging) return;
			            $(this).addClass('l-fieldcontainer-over');
			   }).live('mouseout', function (){
			            $(this).removeClass('l-fieldcontainer-over');
			   }).live('click', function (){
			            if (dragging) return;
			            var selected = $(this).hasClass('l-fieldcontainer-selected');
			            $('li.l-fieldcontainer-selected').removeClass('l-fieldcontainer-selected');
			            if (!selected){ 
			                $(this).addClass('l-fieldcontainer-selected');
			            }
			    });
		},
				
		f_save : function(){
			console.log('save start');
			showMask();
			var that=this;
			that.cache.form.initValidate();//初始化验证
			
//			if(!that.cache.form.valid()){
//				return;
//			}
						
			//{"id":"","existStoreName":"","storeName":"dddd",
			//"storeType":"2001","storeType__Text":"内部商户","contactUser":"ddddd",
			//"contactPhone":"13681157875","storeStatus":"3002",
			//"storeStatus__Text":"未激活",
			//"publicKey":"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCwIuawOMHZyLfsCBLhj1UgCf7nNsJDk7oD9RqxKEjt4f/lu+WZPRvdu1c9sLSLr5Zp9m4yDsjvGTlLpsT9mJAomK+3NrV3ujY6pAnds+RmCn4fLWYmUK08owE9EGuFTwQWnyQAxqpXEL3PtBdNiQXT4Eh+Y8WqgGC+HNG8i6C7QIDAQAB",
			//"privateKey":""}
			var params={};
			var formData = that.cache.form.getData();
			var existStoreName='',storeName='';
			
			for(var key in formData){
            	if(key.length>6 && key.substring(key.length-6)=='__Text'){
            		continue;
            	}
            	if(formData[key])
            		params['model.'+key]=formData[key];
            	
            	if(formData[key] && key == 'existStoreName'){
            		existStoreName = formData[key];
            	}
            	if(formData[key] && key == 'storeName'){
            		storeName = formData[key];
            	}
            }
			//动态验证(唯一性等)
			var error=true;
			if(storeName != existStoreName){
				var pp = {'storeName':storeName, 'message':'storeName'};
				 JAjax(that.config.chackerUrl, pp, 'json', function(data){
					 error=data;
		        }, function(){hideMask();JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, false); 
			}
			if(!error){
				alert('商户名称已存在');
				return;
			}	
			
            var url = '';
            if(parent.window.merchantList.cache.subPageId==='add'){
            	url = that.config.addUrl;
            }else{
            	url = that.config.editUrl;
            	var id=$('#id').val();
            	if(id)
            		params['model.id']=id;
            	else{
            		hideMask();
            		JSWK.clewBox('参数获取失败','clew_ico_fail',failShowTime);
            		return;
            	}
            }
            //alert(url+'::'+JSON.stringify(params));return; 
            JAjax(url, params, 'json', function(data){
            	hideMask();
                if(data && data.message =='操作成功'){	                                		
                	parent.window.merchantList.refreshGrid();
            		parent.window.merchantList.success_alert('操作成功');       		
            		parent.window.merchantList.cache.dialog.close(); 
                 }else{
                	 JSWK.clewBox('保存商户信息失败','clew_ico_fail',failShowTime);
                 }	                                	
        	}, function(){hideMask();JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);  
          
        }
	};
	$(document).ready(function() {
		window.merchant = new Merchant();
	});
})();