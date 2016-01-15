/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var OrgBindData = function() {
		this.init();
	};
	OrgBindData.prototype = {
		config : {
			queryObjectUrl : root + '/orgMergeJson/getBindData.action',
		},
		init : function() {
  		  	var str = window.location.href;
			var es = /\?idAndtype=/;
			es.exec(str);
			var idAndTypeAndName = RegExp.rightContext;	
			var params = idAndTypeAndName.split(";");
			var snapid = params[0];
			var datatype = params[1];
			var systemname=unescape(params[2]);
			
			this.render(snapid,datatype,systemname);
			this.ignoreCancleButton();
		},
		ignoreCancleButton:function(){
			$(".l-dialog-btn-inner",parent.document).each(function(){
				if($(this).text()=="确定"){
					$(this).parent().css("display","none");
					return true;
				}
			});
		},
		render : function(id,type,name) {
			var that = this;
			
			$("#systemName").text(name);
			$("#dataType").text(type);
			
			JAjax(that.config.queryObjectUrl, {'snapShotId':id}, 'json', function(pdata){
				if(pdata){
					$("#content").text(pdata);
				}
			}, function(){JSWK.clewBox('获取外部绑定数据失败！','clew_ico_fail',failShowTime);}, true);
		}
	};
	$(document).ready(function() {
		window.orgBindData = new OrgBindData();
	});
})();