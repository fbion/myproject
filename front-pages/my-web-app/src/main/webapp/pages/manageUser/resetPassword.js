/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var ResetPass = function() {
		this.init();
	};
	ResetPass.prototype = {
		init : function(){
			this.render();
			this.loadCss();
		},
		loadCss : function(){
		 	var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '重置后新密码', imgUrl);
			$('#step1').css('width',subWidth);
		},
		render : function(){
		   $("#accountNOTd").text("账户号："+parent.window.accountList.cache.accountNo);
		   $("#newPassSpan").html("新密码：<span style='color:blue;font-size:16px;font-weight:800;'>"+parent.window.accountList.cache.pass+"</span>");
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
		window.resetPass = new ResetPass();
	});
})();