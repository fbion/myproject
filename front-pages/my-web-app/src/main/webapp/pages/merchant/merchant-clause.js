/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2014年11月28日 下午2:33:27 
 */
(function() {
	var merchantClause = function() {
		this.init();
	};
	merchantClause.prototype = {
		cache : {
			dialog : null,
		},
		init : function(){
			this.loadCss();
		},
		//点击同意按钮，向父页面添加checked属性，并关闭本页面。
		 comply : function(){
			 parent.window.merchant.operationCheckBox();
			 parent.window.merchant.cache.dialog.close(); 
		 },
		 loadCss : function(){
			 $('form').ligerForm();
		 },
	};
	
	$(document).ready(function() {
		window.merchantClause = new merchantClause();
	});
})();