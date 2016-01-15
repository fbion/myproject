
(function() {
	var CommonUserModifyMobile = function() {
		this.init();
	};
	CommonUserModifyMobile.prototype = {
		config : {
			modifyUrl : root + '/account/modifyMobile.action',
			subHeight:subHeight, 
			subWidth:subWidth
		},
		cache : {
			form : null,
			dialog_001 : null,
			imgUrlArr:[],
			imgUrl : null,
			money : 10000000,
			voucherFileName:null,
			type : '',
			id : ''
		},
		init : function(){
			this.render();
		},
		render : function(){
			var _this = this;
		    //初始化页面检验规则
		    $.metadata.setType('attr', 'validate');
	        $('#form').validate({
	            submitHandler: function(){
	            	if (_this.invalidParam()) {
	            		parent.window.accountList.alert('新手机号不合法', 'clew_ico_fail');
	            		return;
	            	}
	            	showMask();
	            	var url = _this.config.modifyUrl;
	            	var params = {
	            			'mobileNo' : $('#newMobileNo').val(),
	            			'accountNo' : window.parent.accountList.cache.accountNo
	            			
	            	};
                     JAjax(url, params, 'json', function(data){
                    	 hideMask();
                      	if(data == '1'){
                      		parent.window.accountList.cache.grid.loadData();
                      		parent.window.accountList.alert('操作成功！', 'clew_ico_succ');
                      		parent.window.accountList.cache.dialog.close();                		
                      	}else{
                      		parent.window.accountList.alert('操作失败！', 'clew_ico_fail');
                      		 parent.window.accountList.cache.dialog.close();    
                      	}
         			 }, function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
	            }
	        });
		},
		f_save:function(){
			$('#form').submit();
		},
		invalidParam : function() {
			if ($('#newMobileNo').val() == null || $('#newMobileNo').val() == '') {
				return true;
			} else {
				if ($('#newMobileNo').val().length != 11) {
					return true;
				} else {
					return false;
				}
			}
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
		window.commonUserModifyMobile = new CommonUserModifyMobile();
		$('#oldMobileNo').val(window.parent.accountList.cache.hideMobileNo);
		$('#oldMobileNo').attr('disabled', 'disabled');
	});
})();

