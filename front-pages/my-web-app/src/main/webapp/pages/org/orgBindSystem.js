/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var OrgBindSystem = function() {
		this.init();
	};
	OrgBindSystem.prototype = {
		config : {
			queryBindSystemUrl : root +'/orgJson/queryBindSystem.action'
		},
		cache : {			
			dialog_001 : null
		},
		init :function(){
			this.initHtml();
			this.render();
			this.initFormData();		
		},
		
		initHtml : function(){			
			var html='';
			html+='<div title="组织信息" id="myPanel">';
			
		
					html+='          <div style="margin:4px;float:left;padding-left: 10px;">';
					html+='		         <div>已关联系统列表</div>';  
					html+='		         <div id="listbox1"></div>';  
					html+='		     </div>';
					html+='		     <div style="margin:4px; float:left;margin-top: 100px" class="middle">';
					html+='		         <input type="button" onclick="window.orgBindSystem.moveToRight()" value=">" />';
					html+='		         <input type="button" onclick="window.orgBindSystem.moveToLeft()" value="&lt;" />';
					html+='		         <input type="button" onclick="window.orgBindSystem.moveAllToRight()" value=">>" />';
					html+='		         <input type="button" onclick="window.orgBindSystem.moveAllToLeft()" value="&lt;&lt;" />';
					html+='		     </div>';
					html+='		     <div style="margin:4px;float:left;">';
					html+='		        <div>未关联系统列表</div>'; 
					html+='		        <div id="listbox2"></div>'; 
					html+='		     </div>';
			
			html+='</div>';
            
			$('#layout').html(html);
		},
		
		render : function(){
			//关联系统设置  
			$('#listbox1,#listbox2').ligerListBox({
			       isShowCheckBox: true,
			       isMultiSelect: true,
			       columns: [
			                  { header: '名字', name: 'name'},
			                  { header: '所属平台', name: 'platformName'}
			                ],
			       slide: true,
			       width: 400,
			       height: subHeight,
			   });
		},
		
		initFormData : function(){
			var that=this;
			//var orgId = $('#subPageOrgId', parent.document).val();
			var orgId = parent.orgList.cache.subPageId;
				//绑定的系统
				$('#listbox1').css('background','url(../../css/ligerUI/skins/Gray/images/ui/loading2.gif) no-repeat 0 0 ');
				$('#listbox2').css('background','url(../../css/ligerUI/skins/Gray/images/ui/loading2.gif) no-repeat 0 0 ');
				JAjax(that.config.queryBindSystemUrl, {'id':orgId}, 'json', function(data){
					var y_data=data && data.yList && data.yList.length>0?that.structureTree(data.yList):null;
					var n_data=data && data.nList && data.nList.length>0?that.structureTree(data.nList):null;				
					$('#listbox1').css('background','');
					liger.get('listbox1').setData(y_data);
					$('#listbox2').css('background','');
			        liger.get('listbox2').setData(n_data);
				}, function(){JSWK.clewBox('查询绑定系统信息时发生异常','clew_ico_fail',failShowTime);}, true);
			
		},
		
		structureTree : function(data){
			$(data).each(function(){
				this['platformName']=MALQ_CODE.getCodeName('PLATFORM_TYPE', this.platform);
			});
			return data;
		 },
		 
		f_save : function(){
			var params={}; 
			//取关联的系统
        	var bindSystem = liger.get('listbox1').data;
        	
            $(bindSystem).each(function(i){
            	params['systemIds['+i+']']=this.id;
            });
			return params;  
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
           
        }
		
	};
	$(document).ready(function() {
		window.orgBindSystem = new OrgBindSystem();
	});
})();