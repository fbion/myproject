/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var OrgInfo = function() {
		this.init();
	};
	OrgInfo.prototype = {
		config : {
			queryObjectUrl : root + '/orgJson/getObjectById.action',
			queryBindSystemUrl : root +'/orgJson/queryBindSystem.action'
			},
		init : function() {
  		  	var str = window.location.href;
			var es = /\?id=/;
			es.exec(str);
			var id = RegExp.rightContext;	
			this.initTitleBlock();
			this.render(id);
			this.initBindSysteData(id);
		},
		render : function(id) {
			var that = this;
			JAjax(that.config.queryObjectUrl, {'model.uaaOrg.id':id}, 'json', function(data){
				//alert(JSON.stringify(data));
				if(data){
		    		  var uaaOrg = data.uaaOrg;
		    		  var address = '';
		    		  if(uaaOrg && uaaOrg.businessDistrictCode){
		    			  var dd = $.trim(data.uaaOrg.businessDistrictCode);
		    			  var addr = uaaOrg.businessAddr?uaaOrg.businessAddr:'';
		 					if(dd && dd.substring(2,6)=='0000'){
		 						address = MALQ_CODE.getZoneNameByCode(dd)+' '+addr;
		 					}else if(dd && dd.substring(4,6)=='00'){	 						
		 						address = MALQ_CODE.getZoneNameByCode(dd.substring(0,2)+'0000', dd)+' '+addr;
		 					}else if(dd){
		 						address = MALQ_CODE.getZoneNameByCode(dd.substring(0,2)+'0000',dd.substring(0,4)+'00',dd)+' '+addr;
		 					}	
		    		  }
		    		  $('#orgAddrress').text(address);
		    		  
		    		var orgType = uaaOrg.orgType;
	 				var orgTypeName = MALQ_CODE.getCodeName('UAA_ORG_TYPE', orgType);
	 				var tem = '';
	 				$(data.tags).each(function(){
			    		tem += MALQ_CODE.getCodeName(uaaOrg.orgType, this.tag+'')+',';
	 				});
	 				tem = tem.length>0?tem.substring(0,tem.length-1):tem;
	 				$('#orgType').text(orgTypeName+" ["+tem+"]");
	 				
	 				$('#orgName').text(uaaOrg.orgName);
	 				$('#orgCode').text(uaaOrg.orgCode);
	 				$('#orgShortName').text(uaaOrg.orgShortName);
	 				$('#businessPerson').text(uaaOrg.businessPerson);
	 				$('#businessNo').text(uaaOrg.businessNo);
	 				$('#businessPhone').text(uaaOrg.businessPhone);
	 				$('#businessMail').text(uaaOrg.businessMail);
	 				
	 				var pid = data.topologys && data.topologys.length>0?data.topologys[0].parentOrgid:'';
					if(pid){
						$('#pTreeId').val(pid);
						JAjax(that.config.queryObjectUrl, {'model.uaaOrg.id':pid}, 'json', function(pdata){
							if(pdata){
								$('#pTreeIdText').text(pdata.uaaOrg.orgShortName);	
							}
						}, function(){JSWK.clewBox('获取上级组织失败','clew_ico_fail',failShowTime);}, true);
					}
		    	 }
			});
		},
		
		initBindSysteData : function(id){
			var that = this;
			//关联系统设置  
			$('#listbox1').ligerListBox({
			       columns: [
			                  { header: '名字', name: 'name'},
			                  { header: '所属平台', name: 'platformName'}
			                ],
			       slide: true,
			       width: subWidth-300,
			       height: subHeight-150,
			  });
			$('#listbox1').css('background','url(../../css/ligerUI/skins/Gray/images/ui/loading2.gif) no-repeat 0 0 ');		 
			 JAjax(that.config.queryBindSystemUrl, {'id':id}, 'json', function(data) {
				//alert(JSON.stringify(data));
				 if(data && data.yList && data.yList.length>0){
					$(data.yList).each(function(){
						this['platformName']=MALQ_CODE.getCodeName('PLATFORM_TYPE', this.platform);
					}); 
					$('#listbox1').css('background','');
					liger.get('listbox1').setData(data.yList);
				 }
			}, function(){JSWK.clewBox('查询绑定权限信息时发生异常','clew_ico_fail',failShowTime);}, true);
		},
		
		initTitleBlock : function(){		 
				var imgUrl = '../../images/spanIoc.png';		 
				MALQ_CODE.titleBlock($('#split_div'), '基本信息', imgUrl);
				MALQ_CODE.titleBlock($('#split_div_togglebtn'), '关联系统', imgUrl);
				//MALQ_CODE.titleBlock($('#split_div_togglebtn'), '关联系统', imgUrl, $('#otherDiv'));	 
				$('#split_div').css('width',(subWidth-100));			
				$('#split_div_togglebtn').css('width',(subWidth-100));
				$('#split_div_null').css('width',(subWidth-100));
			 },
	};
	$(document).ready(function() {
		window.orgInfo = new OrgInfo();
	});
})();