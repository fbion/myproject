/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var MergeOrg = function() {
		this.init();
	};
	MergeOrg.prototype = {			
		config : {
			getObjectByIdUrl : root +'/orgJson/getObjectById.action',
			queryBindSystemUrl : root +'/orgJson/queryBindSystem.action',
			queryOuterSystemUrl : root +'/orgJson/queryOuterSystem.action'
		},
		cache : {
			isCheckError:false, //
			delId:'', //删除id
			retainId:'', //保留id
			outSystems:[],
			isCrossOfOutSystem:false  //外部系统是否存在交集
		},
		init : function() {
			this.render();
			this.initBind();
		},
		initBind:function(){
			
			$("#delDiv").scroll(function(){
				try{
					var h = $(this).attr('scrollTop');
//					if(h<$("#retainDiv").attr('scrollTop')){
						$("#retainDiv").scrollTop(h);
//					}
					setTimeout(function(){
						//donothing
					},0);
				}catch(e){}
			});
			
			$("#retainDiv").scroll(function(){
				try{
					var h = $(this).attr('scrollTop');
					//if(h<$("#delDiv").attr('scrollTop')){
						$("#delDiv").scrollTop(h);
					//}

					setTimeout(function(){
						//donothing
					},0);
				}catch(e){}
			});
		},
		render : function() {
			var that = this;			
			var rows =parent.orgList.cache.checkOrgs; //parent.orgList.cache.grid.getCheckedRows();
			var id1 = parent.orgList.cache.mergeOrg.delId==''?rows[0].id:parent.orgList.cache.mergeOrg.delId;
			var id2 = parent.orgList.cache.mergeOrg.retainId==''?rows[1].id:parent.orgList.cache.mergeOrg.retainId;
			
			//store ids
			that.cache.delId = id1;
			that.cache.retainId = id2;
			
			//init org
			that._initOrg(id1, 'org_01');
			that._initOrg(id2, 'org_02');			
			//init deploy system
			that._initDeploySystem(id1,'deploysys_01');
			that._initDeploySystem(id2,'deploysys_02');
			//init bind system
			that._initOuterSystem(id1,'bindsys_01');
			that._initOuterSystem(id2,'bindsys_02');
			//delay 1s
			window.setTimeout(that._compare,2000);
		},
		_initOrg : function(id, name){
			var that = this;
			$('#'+name).html('<img src="../../css/ligerUI/skins/Gray/images/ui/loading2.gif"/>');
			JAjax(that.config.getObjectByIdUrl, {'model.uaaOrg.id':id}, 'json', function(data) {		
				//alert(JSON.stringify(data));
				var html = that._setOrg(data,name);
				html += '<li style="display:none"><input type="hidden" id="'+name+'_id" value="'+id+'"/></li>';
				$('#'+name).html(html);
		        var lis = $('#'+name).find('li');
				$(lis).each(function(){
					$(this).addClass("l-table-edit-td");
					$(this).find('span').css('padding-left','5px');
				});
		        
			}, function(){JSWK.clewBox('查询组织信息时发生异常','clew_ico_fail',failShowTime);}, true);
		},
		_setOrg : function(data,name){
			var that=this;
			var str = [];
			if(data && data.uaaOrg){
				str.push('<li>组织名称：<span>'+(data.uaaOrg.orgName?data.uaaOrg.orgName:'')+'</span></li>');
				str.push('<li>组织简称：<span>'+(data.uaaOrg.orgShortName?data.uaaOrg.orgShortName:'')+'</li>');
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
				
				var address = '';
				if(data.uaaOrg && data.uaaOrg.businessDistrictCode){
				  var dd = $.trim(data.uaaOrg.businessDistrictCode);
				  var addr = data.uaaOrg.businessAddr?data.uaaOrg.businessAddr:'';
					if(dd && dd.substring(2,6)=='0000'){
						address = MALQ_CODE.getZoneNameByCode(dd)+' '+addr;
					}else if(dd && dd.substring(4,6)=='00'){	 						
						address = MALQ_CODE.getZoneNameByCode(dd.substring(0,2)+'0000', dd)+' '+addr;
					}else if(dd){
						address = MALQ_CODE.getZoneNameByCode(dd.substring(0,2)+'0000',dd.substring(0,4)+'00',dd)+' '+addr;
					}	
				}
				str.push('<li>地址：<span>'+address+'</span></li>');			
			}
			return str.join('');
		},
		
		_change : function(){
			var that = this;
			var left = $("#delDiv").html();
			var right = $("#retainDiv").html();
			
			$("#delDiv").html(right);
			$("#retainDiv").html(left);
			
			var id1 = $("#org_01_id").val();
			var id2 = $("#org_02_id").val();
			//change id
			that.cache.delId = that.cache.delId==id1?id2:id1;
			that.cache.retainId =that.cache.retainId==id2?id1:id2;
		},
		_initDeploySystem:function(id,name){
			$('#'+name).html('<img src="../../css/ligerUI/skins/Gray/images/ui/loading2.gif"/>');
			 JAjax(this.config.queryBindSystemUrl, {'id':id}, 'json', function(data) {
				//alert(JSON.stringify(data));
				 var y_data = data.yList;
				 var _html =[];
				 if(y_data && y_data.length>0){
					 $(y_data).each(function(){
						 var _thisSystem = this;
						 _html.push("<li class='l-table-edit-td' >"+_thisSystem.name+"</li>");
					 });
				 }
				$("#"+name).html(_html.join(''));
			}, function(){}, true);
			
		},
		_initOuterSystem:function(id,name){
			var that = this;
			$('#'+name).html('<img src="../../css/ligerUI/skins/Gray/images/ui/loading2.gif"/>');
			JAjax(this.config.queryOuterSystemUrl, {'id':id}, 'json', function(data) {
				//alert(JSON.stringify(data));
				var _html =[];
				$(data.Rows).each(function(){
					var _row = this;
					var _name = _row.split("~")[0];
					var idAndtype = _row.split("~")[1];
					_html.push("<li class='l-table-edit-td' ><a href='javascript:window.mergeOrg.viewDatail(\""+idAndtype+"\",\""+_name+"\");'>"+_name+"</a></li>");
					that.addOutSystem(_name);
				});
				$("#"+name).html(_html.join(''));
			}, function(){}, true);
	
		},
		_compare:function(){   //对比不同项
			var _data={"org":['bizno','phone','orgcode'],"deploysys":[],"bindsys":[]};
			for(var _this in _data){
				
				var _name = _this;
				var _cols = _data[_this];
				if(_cols.length==0){
					// all
					var obj1 = $("#"+_name+"_01").find("li");
					var obj2 = $("#"+_name+"_02").find("li");
					for(var i=0,n=obj1.length;i<n;i++){
						var  thisli = $(obj1).eq(i);
						if(!window.mergeOrg._contains(thisli.text(),obj2)){
							$(thisli).css("color","red");
						}
					}
					
					for(var i=0,n=obj2.length;i<n;i++){
						var  thisli = $(obj2).eq(i);
						if(!window.mergeOrg._contains(thisli.text(),obj1)){
							$(thisli).css("color","red");
						}
					}
					
				}else{
					for(var i=0,n=_cols.length;i<n;i++){
						var obj1 = $("#"+_name+"_01_"+_cols[i]);
						var obj2 = $("#"+_name+"_02_"+_cols[i]);
						if($(obj1).text()!=$(obj2).text()){
							$(obj1).css({"color":"red"});
							$(obj2).css({"color":"red"});
							//only org
							if(_name=="org"){
								window.mergeOrg.cache.isCheckError = true;
							}
						}
					}
					
				}
			}
			
		},
		_contains:function(val,lis){
			if(!lis ||  lis.length==0 ) return false;
			
			for(var i=0,n=lis.length;i<n;i++){
				if(val==$(lis).eq(i).text()){
					return true;
				}
			}

			return false;
		},
		f_select: function(){
			return {delId:this.cache.delId,retainId:this.cache.retainId,flag:'preview'};
		},
		f_cancel:function(){
			return {flag:'cancel'};
		},
		viewDatail:function(idAndtype,name){
			try{
				var that=this;  	
//				var snapid = idAndtype.split(";")[0];
//				var datatype = idAndtype.split(";")[1];
				name = escape(name);
				var url = 'org-bind-data.jsp?idAndtype='+idAndtype+";"+name;
				var title = "绑定系统的数据类型";
				that.cache.dialog = MALQ_UI.open_button(url, subHeight, subWidth, 
						function(item, dialog){
							dialog.close();
						}, 
						function(item, dialog){dialog.close();}, title);
			}catch(e){}
		},
		addOutSystem:function(name){
			var that = this;
			var allsys = that.cache.outSystems;
			var isExist = false;
			for(var i=0,n=allsys.length;i<n;i++){
				if(allsys[i]==name){
					isExist = true;
					that.cache.isCrossOfOutSystem = true;
					break;
				}
			}
			if(!isExist){
				that.cache.outSystems.push(name);
			}
		}
	};
	$(document).ready(function() {
		window.mergeOrg = new MergeOrg();
	});
})();