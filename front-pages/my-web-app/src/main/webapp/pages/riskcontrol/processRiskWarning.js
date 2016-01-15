/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2015年1月16
 */
(function() {
	var DoRiskWarning = function() {
		this.init();
	};
	DoRiskWarning.prototype = {
		config : {
			addUrl : root + '/riskmanager/processWarning.action',
			modeifyUrl : root + '/riskmanager/updateRiskRule.action',
			lastValueUrl : root + '/riskmanager/queryRiskRule.action',
			subHeight:subHeight, 
			subWidth:subWidth
		},
		cache : {
			form : null,
			dialog_001 : null,
			imgUrlArr:[],
			imgUrl : null,
			money : 10000000,
			voucherFileName:null
		},
		init : function(){
			this.render();
		},
		notNull : function(val) {
			if (val != null && val != '' && val != 'undefined') {
				return true;
			} else {
				return false;
			}
		},
		
		render : function(){
			var _this = this;
		    //初始化页面检验规则
		    $.metadata.setType('attr', 'validate');
	        $('#riskForm').validate({
	            submitHandler: function(){
	            	showMask();
	            	var url = _this.config.addUrl;
	            	var params = {
	            			"ids" : parent.window.recordList.cache.ids,
	            			"processResult" : $("#processResult").val()
	            	};
                     JAjax(url, params, 'json', function(data){
                    	 hideMask();
                      	if(data){
                      		parent.window.recordList.cache.grid.loadData();
                      		parent.window.recordList.alert('操作成功！', 'clew_ico_succ');
                      		parent.window.recordList.cache.dialog.close();                		
                      	}else{
                      		 JSWK.clewBox(data,'clew_ico_fail',failShowTime);
                      	}
         			 }, function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
	            }
	        });
		},
		
		f_save:function(){
			$('#riskForm').submit();
		},
		reset : function(){
			 if($("#id").val()==''){
				 window.location='rechargeAdd.jsp';
			 }else{
			 }
			
		 },
		
		success : function(item, dialog) {
			dialog.frame.window.rechargeUpload.uploadComplate();
		},
		cancel : function(item, dialog) {
			dialog.close();
		},
		success_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_succ');
		},
		fail_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
		},
	};
	
	$(document).ready(function() {
//		alert(parent.window.queryList.cache.test);
		window.doRiskWarning = new DoRiskWarning();
		
	});
})();

