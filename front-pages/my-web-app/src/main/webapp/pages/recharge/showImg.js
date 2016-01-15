/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var ShowImg = function() {
		this.init();
	};
	ShowImg.prototype = {
		config : {
			subHeight:subHeight, 
			subWidth:subWidth
		},
		cache : {
			imgUrl : null
		},
		init : function(){
			that = this;
			that.cache.imgUrl =  parent.window.queryList.cache.imgUrl;
			var container = $$("idContainer"), src = that.cache.imgUrl,
			options = {
			
			};
			if( $.browser.msie && ($.browser.version == "8.0" )){
				options.mode = "canvas";
			};
			it = new ImageTrans( container, options );
			it.load(src);
			
			//垂直翻转
			$$("idVertical").onclick = function(){ it.vertical(); };
			//水平翻转
			$$("idHorizontal").onclick = function(){ it.horizontal(); };
			//左旋转
			$$("idLeft").onclick = function(){ it.left(); };
			//右旋转
			$$("idRight").onclick = function(){ it.right(); };
			//重置
			$$("idReset").onclick = function(){ it.reset(); };
			
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
		window.showImg = new ShowImg();
	});
})();