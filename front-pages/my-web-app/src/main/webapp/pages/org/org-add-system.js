/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var OrgAdd = function() {
		this.init();
	};
	OrgAdd.prototype = {
		config : {
			addUrl : root + '/orgJson/add.action',
			modeifyUrl : root + '/orgJson/update.action',
			queryObjUrl : root + '/orgJson/getObjectById.action',
			queryBindSystemUrl : root +'/orgJson/queryBindSystem.action',
			delUrl : root + '/orgJson/delete.action'
		},
		cache : {			
			dialog_001 : null,
			form : null,
			tags : []
		},
		init :function(){
			this.initHtml();
			this.initLayoutFrom();
			this.initFormData();
			//this.initPageDate();
			//this.initTitleBlock();
			//this.render();			
		},
		
		initHtml : function(){			
			var html='';
			html+='<div title="组织信息" id="myPanel"><form id="form1">';
			html+='			 <div id="orgform" style="clear:both;"></div>';
			if(parent.orgList.cache.subPageId=='add'){
					html+='          <div style="margin:4px;float:left;padding-left: 10px;">';
					html+='		         <div id="listbox1"></div>';  
					html+='		     </div>';
					html+='		     <div style="margin:4px; float:left;margin-top: 100px" class="middle">';
					html+='		         <input type="button" onclick="window.orgAdd.moveToRight()" value=">" />';
					html+='		          <input type="button" onclick="window.orgAdd.moveToLeft()" value="&lt;" />';
					html+='		          <input type="button" onclick="window.orgAdd.moveAllToRight()" value=">>" />';
					html+='		         <input type="button" onclick="window.orgAdd.moveAllToLeft()" value="&lt;&lt;" />';
					html+='		     </div>';
					html+='		     <div style="margin:4px;float:left;">';
					html+='		        <div id="listbox2"></div>'; 
					html+='		     </div>';
			}
			html+='</form></div>';
            
			$('#layout').html(html);
		},
		
		initLayoutFrom : function(){
			var that = this;
			
			var fields =[
							{ name: 'id', type: 'hidden' },
							{ name: 'orgCode_exist', type: 'hidden' },
							{ name: 'category', type: 'hidden' },
							{ name: 'orgType', type: 'hidden' },
							{ name: 'pTreeId', type: 'hidden' },
							
							{ label: '组织名称', name: 'orgName', validate: {required:true,minlength:2,maxlength:60}, type: 'text', group: '基础信息', groupicon: root+'/images/group.gif' },
							{ label: '组织类型', name: 'orgType', validate: {required:true,minlength:1,maxlength:60}, newline: false, type: 'combobox', 
								textField: 'orgType__Text',
								editor: {
									data: [],
									onBeforeOpen:that._selectOrgType,
									valueFieldID: 'orgTypeText001',
									width:140
									//selectBoxWidth:400,
									//selectBoxHeight:200
							    }
							},
							{ label: '组织简称', name: 'orgShortName', validate: {required:true,minlength:2,maxlength:15}, type: 'text'},
							{ label: '上级组织', name: 'pTreeId', newline: false, type: 'combobox', 
								textField: 'pTreeId__Text', 
								editor: {
									data: [],
									onBeforeOpen:that._selectOrgParent, 
									valueFieldID:'pTreeIdText001',
									width:140
								}
							},
							{ label: '组织编码', name: 'orgCode', newline: true, type: 'text',validate:{required:false,maxlength:32}},
							{ label: '联系人', name: 'businessPerson', newline: false, type: 'text',validate:{required:false,maxlength:16}},
							{ label: '营业执照', name: 'businessNo', newline: true, type: 'text',validate:{required:false,maxlength:32}},
							{ label: '联系电话', name: 'businessPhone', newline: false, type: 'text',validate:{required:false,maxlength:16}},
							
							{ label: '电子邮箱', name: 'businessMail', type: 'text',validate:{required:false,email:true}},
							
							{ label: '组织地址', name: 'corpProvince',space: 1, 
								type: 'combobox', textField: 'corpProvince__Text', 
								editor: {
									data: MALQ_CODE.getZoneData('0'),
									isMultiSelect: false,
									onSelected: function (value){
											liger.get('corpCity').setData(MALQ_CODE.getZoneData(value));
											liger.get('corpDistrict').setData(null);
										}	                
								}
							},
							{name: 'corpCity', newline: false, labelWidth: 1, space: 1, 
								type: 'combobox', textField: 'corpCity__Text', 
								editor: {
									data:[],
									onSelected: function (value){
											liger.get('corpDistrict').setData(MALQ_CODE.getZoneData(value));
									}
								}
							},
							{name: 'corpDistrict', newline: false, labelWidth: 1, space: 1, 
								type: 'combobox', textField: 'corpDistrict__Text', 
								editor: {data:[]}
							},
							{ name: 'businessAddr', newline: false,validate:{required:false,maxlength:32},width: 180, labelWidth: 1, space: 1, type: 'text'}
							//{ label: '日期 ', name: 'AddTime',    newline: true, type: 'date' },
							//{ label: '折扣', name: 'QuantityPerUnit',    newline: false, type: 'number' },
							//{ label: '单价', name: 'UnitPrice',    newline: true, type: 'number' },
			             
			             ];
			
			if(parent.orgList.cache.subPageId=='add'){
				//fields.push({group: '关联系统', groupicon: root+'/images/group.gif' }); 
				//fields.push({ label: '已关联系统',name: 'yBindData', type:'span'});
				//fields.push({ label: '未关联系统', name: 'nBindData', labelAlign: 'right', labelWidth: 160, newline: false, type:'span'}); 
				fields.push({ label: '已关联系统',name: 'yBindData', type:'span', group: '关联系统', groupicon: root+'/images/group.gif'});
				fields.push({ label: '未关联系统', name: 'nBindData', labelAlign: 'right', labelWidth: 160, newline: false, type:'span'});
				
				//that.cache.form.set({fields:fields});//关联系统信息
			}
			
//			var tab = {
//		            items : [
//	                {
//	                    title: '第一个页签', fields: [
//	                           { display: "库存量", name: "UnitsInStock", newline: true, type: "digits"  },
//	                            { display: "订购量", name: "UnitsOnOrder", newline: false, type: "digits" }
//	                    ]
//	                },
//	                {
//	                    title: '第二个页签(备注)(隐藏label)', fields: [
//	                            { display: "备注", name: "Remark", newline: true, type: "textarea", width: 470, validate:{}, hideLabel:true }
//	                    ]
//	                }
//	            ]
//	        }
		
			that.cache.form = $('#orgform').ligerForm({ 
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
		
			if(parent.orgList.cache.subPageId=='add'){
				//关联系统设置  
				$('#listbox1,#listbox2').ligerListBox({
				       isShowCheckBox: true,
				       isMultiSelect: true,
				       columns: [
				                  { header: '名字', name: 'name'},
				                  { header: '所属平台', name: 'platformName'}
				                ],
				       //render: function(item){return '<span>'+item.data.name+'</span><span style="float:right;">'+item.data.platform+'</span>';}, //显示html自定义函数 
				       //idFieldName :'id',
				       //textFieldName :'name',
				       //parentIDFieldName :'platform'         
				       slide: true,
				       width: 360,
				       height: 300
				   });
			}
		},
		
		initFormData : function(){
			var that=this;
			var orgId = parent.orgList.cache.editId;
			if(parent.window.orgList.cache.subPageId==='add'){//增加页面
				//绑定的系统
				$('#listbox1').css('background','url(../../css/ligerUI/skins/Gray/images/ui/loading2.gif) no-repeat 0 0 ');
				$('#listbox2').css('background','url(../../css/ligerUI/skins/Gray/images/ui/loading2.gif) no-repeat 0 0 ');
				JAjax(that.config.queryBindSystemUrl, {'id':''}, 'json', function(data){
					var y_data=data && data.yList && data.yList.length>0?that.structureTree(data.yList):null;
					var n_data=data && data.nList && data.nList.length>0?that.structureTree(data.nList):null;				
					$('#listbox1').css('background','');
					liger.get('listbox1').setData(y_data);
					$('#listbox2').css('background','');
			        liger.get('listbox2').setData(n_data);
				}, function(){JSWK.clewBox('查询绑定系统信息时发生异常','clew_ico_fail',failShowTime);}, true);
			}else{	
				JAjax(that.config.queryObjUrl, {'model.uaaOrg.id':orgId}, 'json', function(data){
					if(data){
						that.cache.form.setData(data.uaaOrg);
						var tem = data.uaaOrg.businessDistrictCode;
						if(tem){
							liger.get('corpProvince').selectValue(tem.substring(0,2)+'0000');
							liger.get('corpCity').selectValue(tem.substring(0,4)+'00');
							liger.get('corpDistrict').selectValue(data.uaaOrg.businessDistrictCode);
						}
						if(data.topologys && data.topologys.length>0){
							$(data.topologys).each(function(){
								if(this.category =='JCFWPT'){
									JAjax(that.config.queryObjUrl, {'model.uaaOrg.id':this.parentOrgid}, 'json', function(data){
										that.cache.form.setData({
							        		 //pTreeId: data.uaaOrg.id,
							        		 //category: 'JCFWPT',
							        		 pTreeId__Text:data.uaaOrg.orgShortName
							             });
										$("input[name='pTreeId__Text']").css('color','#555555');
										liger.get('pTreeId').setDisabled();
									}, function(){JSWK.clewBox('查询绑定系统信息时发生异常','clew_ico_fail',failShowTime);}, true);
									return;
								}
							});
						}else{
							liger.get('pTreeId').setDisabled();
						}						
						
						var tags = data.tags;
						if(tags){
							that.cache.tags = tags;
							//$('#orgType').val(uaaOrg.orgType);
							var text = '';
							$(tags).each(function(){
								text += MALQ_CODE.getCodeName(data.uaaOrg.orgType, this.tag+'')+',';
							});
							text = text.length>0?text.substring(0,text.length-1):text;
							that.cache.form.setData({orgType__Text:text});
							//$('#orgTypeText').val(text);
						}						
						
					}
				}, function(){JSWK.clewBox('查询绑定系统信息时发生异常','clew_ico_fail',failShowTime);}, true);
				
			}		
		},
		
		structureTree : function(data){
			$(data).each(function(){
				this['platformName']=MALQ_CODE.getCodeName('PLATFORM_TYPE', this.platform);
			});
			return data;
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
			var that=this;
			that.cache.form.initValidate();//初始化验证
			if(!that.cache.form.valid())return;//基本验证
						
			//动态验证(唯一性等)
			
			//alert(JSON.stringify(that.cache.form.getData()));
			//alert(JSON.stringify(that.cache.form.get('fields')));
			//{"id":"","orgCode_exist":"","category":"","orgName":"jjjjj","orgType":"INNER_UAA_ORG","orgTypeText":"内部部门","orgShortName":"kkii","pTreeId":"","pTreeIdText":"","orgCode":"jj","businessPerson":"","businessNo":"kjk","businessPhone":"","businessMail":"kl","corpDistrict":"山海关区","businessAddr":"kjioo"}
           
			var params={};
            var formData = that.cache.form.getData();
            for(var key in formData){
            	if(key.length>6 && key.substring(key.length-6)=='__Text'){
            		continue;
            	}
            	if(key=='orgCode_exist' || key=='category' || key=='pTreeId' || key=='orgType' || key=='corpDistrict' || key=='corpCity' || key=='corpProvince'){
            		continue;
            	}
            	params['model.uaaOrg.'+key]=formData[key];
            }
            
            //组织类型
            if($('#orgType').val()){
            	params['model.uaaOrg.orgType']=$('#orgType').val();
            }
            
			//省市县
            if($('#corpDistrict').val()){
    			params['model.uaaOrg.businessDistrictCode']=$('#corpDistrict').val();
    		}else if($('#corpCity').val()){
    			params['model.uaaOrg.businessDistrictCode']=$('#corpCity').val();
    		}else if($('#corpProvince').val()){
    			params['model.uaaOrg.businessDistrictCode']=$('#corpProvince').val();
    		}
			
            var tags = that.cache.tags;
            if(tags.length>0){
            	$(tags).each(function(i){
            		var tag = this.tag || this;
            		params['model.tags['+i+'].tag']=tag+'';
            	});
            	params['model.uaaOrg.orgType']=$('#orgType').val();
            }
            
            var pid = $('#pTreeId').val();
            var category=$('#category').val();
            if(pid && category){
            	params['model.topologys[0].parentOrgid']=pid;//上级
	            params['model.topologys[0].category']=category;//拓扑类型
            }else if('stuff'==parent.parent.INDEX_USER_TYPE && 'INNER_UAA_ORG'==$('#orgType').val()){
            	params['model.topologys[0].parentOrgid']=parent.parent.INDEX_USER_ORGID;//上级
	            params['model.topologys[0].category']='JCFWPT';//拓扑类型
            }
                        
            var url = '';
            if(parent.window.orgList.cache.subPageId==='add'){
            	url = that.config.addUrl;
            	//取关联的系统
            	var bindSystem = liger.get('listbox1').data;
            	if(!bindSystem || bindSystem.length<1){
            		JSWK.clewBox('必须选择至少一个关联系统!','clew_ico_fail',2000);
            		return ;
            	}
	    		$(bindSystem).each(function(i){
	    			params['systemIds['+i+']']=this.id;
	    		});
            }else{
            	url = that.config.modeifyUrl;
            	var id=$('#id').val();
            	if(id)
            		params['model.uaaOrg.id']=id;
            	else{
            		parent.window.orgList.alert('参数获取失败！','clew_ico_fail');return;
            	}
            }
            //alert(url+'::'+JSON.stringify(params));return; 
            JAjax(url, params, 'json', function(data){
                if(data && data =='操作成功'){	                                		
                      parent.window.orgList.refreshGrid();
                      parent.window.orgList.alert('操作成功！','clew_ico_succ');	                                		
                      parent.window.orgList.cache.dialog.close();
                 }else{
                       parent.window.orgList.alert(data,'clew_ico_fail');
                 }	                                	
        	}, function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);  
          
        },
        
        moveToLeft : function(){
            var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
            var selecteds = box2.getSelectedItems();
            if (!selecteds || !selecteds.length) return;
            box2.removeItems(selecteds);
            box1.addItems(selecteds);
        },
        moveToRight : function(){
            var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
            var selecteds = box1.getSelectedItems();
            if (!selecteds || !selecteds.length) return;
            box1.removeItems(selecteds);
            box2.addItems(selecteds);
        },
        moveAllToLeft : function(){ 
            var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
            var selecteds = box2.data;
            if (!selecteds || !selecteds.length) return;
            box1.addItems(selecteds);
            box2.removeItems(selecteds); 
        },
        moveAllToRight : function(){ 
            var box1 = liger.get("listbox1"), box2 = liger.get("listbox2");
            var selecteds = box1.data;
            if (!selecteds || !selecteds.length) return;
            box2.addItems(selecteds);
            box1.removeItems(selecteds);
           
        },
		
//		render : function(){
//			var that=this;
//			
//		   $.metadata.setType('attr', 'validate');
//	       $('#form1').validate({ 
//	            errorPlacement: function (lable, element){
//	        		CSM_VALIDATE.errorPlacement(lable, element);
//		        },
//	            success: function (lable){
//	            	CSM_VALIDATE.success(lable);
//	            },	            
//	            submitHandler: function (form){
//	                $('form .l-text,.l-textarea').ligerHideTip();
//	                var params={};
//	                var input = $('form input');
//	                $(input).each(function(){
//	                	if(this.name && this.name.substring(0,6)=='model.'){		
//	                		params[this.name]=this.value;
//	                	}
//	                });
//	                
//	                //省市县
//	                if($('#corpDistrict_val').val()){
//            			params['model.uaaOrg.businessDistrictCode']=$('#corpDistrict_val').val();
//            		}else if($('#corpCity_val').val()){
//            			params['model.uaaOrg.businessDistrictCode']=$('#corpCity_val').val();
//            		}else if($('#corpProvince_val').val()){
//            			params['model.uaaOrg.businessDistrictCode']=$('#corpProvince_val').val();
//            		}
//	                var tags = that.cache.tags;
//	                if(tags.length>0){
//	                	$(tags).each(function(i){
//	                		var tag = this.tag || this;
//	                		params['model.tags['+i+'].tag']=tag+'';
//	                	});
//	                	params['model.uaaOrg.orgType']=$('#orgType').val();
//	                }
//	                
//	                var pid = $('#pTreeId').val();
//	                var category=$('#category').val();
//	                if(pid && category){
//	                	params['model.topologys[0].parentOrgid']=pid;//上级
//	 	                params['model.topologys[0].category']=category;//拓扑类型
//	                }else if('stuff'==parent.parent.INDEX_USER_TYPE && 'INNER_UAA_ORG'==$('#orgType').val()){
//	                	params['model.topologys[0].parentOrgid']=parent.parent.INDEX_USER_ORGID;//上级
//	 	                params['model.topologys[0].category']='JCFWPT';//拓扑类型
//	                }
//	               
//	                var url = '';
//	                if(parent.window.orgList.cache.subPageId==='add'){
//	                	url = that.config.addUrl;
//	                }else{
//	                	url = that.config.modeifyUrl;
//	                	var id=$('#id').val();
//	                	if(id)
//	                		params['model.uaaOrg.id']=id;
//	                	else{
//	                		parent.window.orgList.alert('参数获取失败！','clew_ico_fail');return;
//	                	}
//	                	
//	                }
//	              //alert(url+'::'+JSON.stringify(params));return; 
//	              JAjax(url, params, 'json', function(data){
//                        if(data && data =='操作成功'){	                                		
//                              parent.window.orgList.refreshGrid();
//                              parent.window.orgList.alert('操作成功！','clew_ico_succ');	                                		
//                              parent.window.orgList.cache.dialog.close();
//                         }else{
//                               parent.window.orgList.alert(data,'clew_ico_fail');
//                         }	                                	
//                	}, function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);  
//	            } 
//	        });
//	       
//	       $('#form1').ligerForm({inputWidth:140}); 
//		},
		
		
//		 initPageDate : function (){
//			 var that = this;
//			 
//			 $('#pTreeId').val($('#pTreeId', parent.document).val());	
//	  		 //$('#pTreeIdText').text($('#pTreeIdText', parent.document).text()); 
//			 $('#pTreeIdText').ligerComboBox({
//	               onBeforeOpen: that._selectOrgParent, valueFieldID: 'pTreeIdText001',width:140
//	         });
//			 
////			 $('#orgTypeText').ligerComboBox({
////	               onBeforeOpen: that._selectOrgType, valueFieldID: 'orgTypeText001',width:140
////	         });
//			 
//			//初始化省份下拉框信息		 		 			
//			//var e1 = $('#corpProvince');
//			//var e2 = $('#corpCity');
//			//var e3 = $('#corpDistrict');
//			//MALQ_CODE.getZoneComboBox(e1, e2, e3, 140);
//			
//		 },
		 
		 _selectOrgParent:function(){
			 var that=window.orgAdd;
			 var orgType = that.cache.form.getData().orgType;
			 if(!orgType){
				 JSWK.clewBox('请先选择组织类型','clew_ico_fail',failShowTime);
				 return false;
			 }
			 var url = 'changeOrgParent.jsp';
			 var title = '选择上级组织';
			 that.cache.dialog_001 = MALQ_UI.open_button(url, subHeight, subWidth, 
					 function(item, dialog){
						 var fn = dialog.frame.changeOrgParent.f_select || dialog.frame.window.changeOrgParent.f_select;
				         var data = fn();
				         if(data){
				        	 //$('#pTreeId').val(data.uaaOrg.id);
				        	 //$('#category').val(data.category);
					         //$('#pTreeIdText').val(data.uaaOrg.orgShortName);
				        	 that.cache.form.setData({
				        		 pTreeId: data.uaaOrg.id,
				        		 category: data.category,
				        		 pTreeId__Text:data.uaaOrg.orgShortName
				             }); 
					         dialog.close();
				         } 
			 		 },
					 function(item, dialog){dialog.close();}, 
					 title);
	         return false; 
		 },
		 
		 _selectOrgType:function(){
			 var that=window.orgAdd;
			 var url = 'selectOrgType.jsp';
			 var title = '选择组织类型';
			 that.cache.dialog_001 = MALQ_UI.open_button(url, 200, 400, 
					 function(item, dialog){
						 var fn = dialog.frame.f_select || dialog.frame.window.f_select; 
				         var data = fn();
				         if(data){
				        	 that.cache.tags=[];
				        	 that.cache.tags = data.id.split(',');
				        	 //$('#orgType').val(data.type);
				        	 //$('#orgTypeText').val(data.name);
				        	 that.cache.form.setData({
				        		 orgType: data.type,
				        		 orgType__Text:data.name
				             });
				        	 dialog.close();
				         }
			 		},
					 function(item, dialog){dialog.close();}, 
					 title);
			 return false; 
		 }
		 
//		 f_selectOK: function (item, dialog){
//			 var that = window.orgAdd;
//			 var fn = dialog.frame.f_select || dialog.frame.window.f_select; 
//	         var data = fn();
//	         if(data){
//	        	 that.cache.tags=[];
//	        	 that.cache.tags = data.id.split(',');
//	        	 //$('#orgType').val(data.type);
//	        	 //$('#orgTypeText').val(data.name);
//	        	 that.cache.form.setData({
//	        		 orgType: data.type,
//	        		 orgTypeText:data.name
//	             });
//	        	 dialog.close();
//	         }
//	                
//	     }
//	     
//		 initTitleBlock : function(){		 
//			var imgUrl = '../../images/spanIoc.png';		 
//			MALQ_CODE.titleBlock($('#split_div'), '基本信息', imgUrl);
//			MALQ_CODE.titleBlock($('#split_div_togglebtn'), '关联系统', imgUrl, $('#otherDiv'));
//			//MALQ_CODE.titleBlock($('#split_div_togglebtn'), '关联系统', imgUrl, $('#otherDiv'));	 
//			$('#split_div').css('width',(parent.window.orgList.config.subWidth-100));			
//			$('#split_div_togglebtn').css('width',(parent.window.orgList.config.subWidth-100));
//			$('#split_div_null').css('width',(parent.window.orgList.config.subWidth-100));
//		 },
//		 selected_dd : function(){
//			 $('#Button1').click();
//		 }	
			
	};
	$(document).ready(function() {
		window.orgAdd = new OrgAdd();
	});
})();