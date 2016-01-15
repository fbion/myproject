/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var MergeOrgPreview= function() {
		this.init();
	};
	MergeOrgPreview.prototype = {			
		config : {
			getObjectByIdUrl : root +'/orgJson/getObjectById.action',
			queryBindSystemUrl : root +'/orgJson/queryBindSystem.action'
		},
		cache : {
			isCheckError:false, //
		},
		init : function() {
			this.render();
		},
		render : function() {
			var that = this;			
			var ids = parent.orgList.cache.mergeOrg;
			var retainId = ids.retainId;
			var delId = ids.delId;
			
			//init org
			that._initOrg(retainId, 'org_01');
			//init deploy system
			that._initDeploySystem(ids,'deploysys_01');
			//init bind system
			that._initOuterSystem(ids,'bindsys_01');
			
		},
		_initOrg : function(id, name){
			var that = this;
			$('#'+name).html('<img src="../../css/ligerUI/skins/Gray/images/ui/loading2.gif"/>');
			JAjax(that.config.getObjectByIdUrl, {'model.uaaOrg.id':id}, 'json', function(data) {		
				//alert(JSON.stringify(data));
				var html = that._setOrg(data,name);
				$('#'+name).html(html);
				var lis = $('#'+name).find('li');
				$(lis).each(function(){
					$(this).addClass("l-table-edit-td");
//					$(this).find('span').css('padding-left','5px');
//					$(this).find('lable').css('padding-left','50px');
				});
			}, function(){JSWK.clewBox('查询组织信息时发生异常','clew_ico_fail',failShowTime);}, true);
		},
		_setOrg : function(data,name){
			var that=this;
			var str = [];
			if(data && data.uaaOrg){
				str.push('<li>组织名称：<span>'+(data.uaaOrg.orgName?data.uaaOrg.orgName:'')+'</span></li>');
				str.push('<li>组织简称：<span>'+(data.uaaOrg.orgShortName?data.uaaOrg.orgShortName:'')+'</span></li>');
				str.push('<li>上级组织：<span id="'+name+'_pName"></span></li>');
				if(data.topologys && data.topologys.length>0){
					var pid='';
					$(data.topologys).each(function(){
						if(this.category=='JCFWPT'){
							pid=this.parentOrgid;return;
						}
					});
					JAjax(that.config.getObjectByIdUrl, {'model.uaaOrg.id':pid}, 'json', function(data2) {
						pName=data2.uaaOrg.orgName;
						$('#'+name+'_pName').text(data2.uaaOrg.orgName);
					}, function(){JSWK.clewBox('查询父组织时发生异常','clew_ico_fail',failShowTime);}, true);
				}
				
				var type='';
				if(data.uaaOrg.orgType){
					type=MALQ_CODE.getCodeName('UAA_ORG_TYPE', data.uaaOrg.orgType);
					var tag='';
					$(data.tags).each(function(){
						tag=MALQ_CODE.getCodeName(data.uaaOrg.orgType, this.tag)+',';
					});
					if(tag.length>0)
						tag=tag.substring(0, tag.length-1);
					type+=' ['+tag+']';
				}
				str.push('<li>组织类型：<span>'+type+'</span></li>');
				str.push('<li>组织编码：<span id='+name+'_orgcode>'+(data.uaaOrg.orgCode?data.uaaOrg.orgCode:'')+'</span></li>');
				//str += '<li>机构代码：<span>'+data.id+'</span></li>';
				str.push('<li>营业执照：<span id='+name+'_bizno>'+(data.uaaOrg.businessNo?data.uaaOrg.businessNo:'')+'</span></li>');
				str.push('<li>联系人：<span>'+(data.uaaOrg.businessPerson?data.uaaOrg.businessPerson:'')+'</span></li>');
				str.push('<li>联系电话：<span id='+name+'_phone>'+(data.uaaOrg.businessPhone?data.uaaOrg.businessPhone:'')+'</span></li>');
				str.push('<li>电子邮箱：<span>'+(data.uaaOrg.businessMail?data.uaaOrg.businessMail:'')+'</span></li>');
				var c = data.uaaOrg.businessDistrictCode?data.uaaOrg.businessDistrictCode:'';
				var tem = c==''?'':c.substring(2)=='0000'?MALQ_CODE.getZoneNameByCode(c):c.substring(4)=='00'?MALQ_CODE.getZoneNameByCode(c.substring(2)+'0000',c):MALQ_CODE.getZoneNameByCode(c.substring(2)+'0000',c.substring(4)+'00',c);
				str.push('<li>地址：<span>'+tem+' '+(data.uaaOrg.businessAddr?data.uaaOrg.businessAddr:'')+'</span></li>');			
			}
			return str.join('');
		},
		_initDeploySystem:function(ids,name){
			$('#'+name).html('<img src="../../css/ligerUI/skins/Gray/images/ui/loading2.gif"/>');
			var id1 = ids.retainId;
			var id2 = ids.delId;
			var _html =[];
			var systemArr = [];
			 JAjax(this.config.queryBindSystemUrl, {'id':id1}, 'json', function(data) {
				//alert(JSON.stringify(data));
				 var y_data = data.yList;
				 if(y_data && y_data.length>0){
					 $(y_data).each(function(){
						 var _thisSystem = this;
						 _html.push("<li class='l-table-edit-td' >"+_thisSystem.name+"</li>");
						 systemArr.push(_thisSystem.name);
					 });
				 }
			}, function(){JSWK.clewBox('查询绑定权限信息时发生异常','clew_ico_fail',failShowTime);}, false);
			 
			$("#"+name).html(_html.join(''));

			JAjax(this.config.queryBindSystemUrl, {'id':id2}, 'json', function(data) {
				//alert(JSON.stringify(data));
				 var y_data = data.yList;
				 if(y_data && y_data.length>0){
					 $(y_data).each(function(){
						 var _thisSystem = this.name;
						 var isExist = false;
						 //distinct
						 for(var i=0,n=systemArr.length;i<n;i++){
							 if(systemArr[i]==_thisSystem){
								 isExist = true;
								break;
							 }
						 }
						 if(!isExist){
							 isExist = false;
							 systemArr.push(_thisSystem);
						 }
					 });
				 }
				 
				 _html.length = 0;
				 if(systemArr.length>0){
					 for(var i=0,n=systemArr.length;i<n;i+=5){
						 _html.push("<li class='l-table-edit-td' >"+systemArr[i]);
						 if((i+1)<n)	 _html.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+systemArr[i+1]);
						 if((i+2)<n)     _html.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+systemArr[i+2]);
						 if((i+3)<n)     _html.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+systemArr[i+3]);
						 if((i+4)<n)     _html.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+systemArr[i+4]);
						 _html.push("</li>");
					 }
				 }
				 
				 $("#"+name).html(_html.join(''));
			}, function(){JSWK.clewBox('查询绑定权限信息时发生异常','clew_ico_fail',failShowTime);}, true);
			 
		},
		_initOuterSystem:function(ids,name){
			$('#'+name).html('<img src="../../css/ligerUI/skins/Gray/images/ui/loading2.gif"/>');

			var sysList = parent.orgList.cache.outSystems;
			var _html = [];
			for(var i=0,n=sysList.length;i<n;i+=5){
				 _html.push("<li class='l-table-edit-td' >"+sysList[i]);
				 if((i+1)<n)	 _html.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+sysList[i+1]);
				 if((i+2)<n)     _html.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+sysList[i+2]);
				 if((i+3)<n)     _html.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+sysList[i+3]);
				 if((i+4)<n)     _html.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+sysList[i+4]);
				 _html.push("</li>");
			 }
			$('#'+name).html(_html.join(''));
			
		},
		f_select: function(){
			return {flag:'submit'};
		},
		f_cancel:function(){
			return {flag:'pre'};
		}
	};
	$(document).ready(function() {
		window.mergeOrg = new MergeOrgPreview();
	});
})();