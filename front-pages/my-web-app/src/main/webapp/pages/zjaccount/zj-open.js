/*******************************************************************************
 * 账户管理-中交账户开户
 */
(function() {
	var ZJopen = function() {
		this.init();
	};
	ZJopen.prototype = {
		config : {
			openlUrl : root + '/ZJAccount/open.action',
			subHeight : subHeight,
			subWidth : subWidth,
		},
		cache : {
			accordion : null,
			tab : null,
		},
		init : function() {
			this.render();
			this.loadCss();
		},
		render : function() {
			var that = this;
			// 布局
			$('#layout1').ligerLayout({
				leftWidth : 210,
				allowLeftResize : false,
				height : '100%',
				heightDiff : -1,
				space : 4,
				onHeightChanged : that._heightChanged
			});
			$('.l-layout-header').show();
			$('.l-layout-header-toggle').css('z-index', '2');
			$("#pageloading").hide();

		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.zjopen;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
		loadCss : function(){
		 	var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '中交账户-开户', imgUrl);
			$('#step1').css('width',(parent.window.zjcreate.config.subWidth-100));
			$('form').ligerForm();
		},
		f_save:function(){
			var that=this;
			var params=[];
			params.push({
				name : 'serviceProviderCode',
				value : $('#serviceProviderCode').val()
			});
			params.push({
				name : 'ownerUserId',
				value : $('#ownerUserId').val()
			});
			params.push({
				name : 'ownerLoginName',
				value : $('#ownerLoginName').val()
			});
			params.push({
				name : 'payPassword',
				value : $('#payPassword').val()
			});
			params.push({
				name : 'mobileNo',
				value : $('#mobileNo').val()
			});
			params.push({
				name : 'accountType',
				value : '0'
			});
			JAjax(that.config.openlUrl,params, 'json', function(data){   
          	if(data){           
          		parent.window.zjcreate.refreshGrid();
          		parent.window.zjcreate.alert(data.message, 'clew_ico_succ');
          		parent.window.zjcreate.cache.dialog.close(); 
          	}else{
          		 JSWK.clewBox(data.message,'clew_ico_fail',failShowTime);
          	}
          	
			}, function(){JSWK.clewBox(data.message,'clew_ico_fail',failShowTime);}, true);
		},
		success_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_succ');
		},
		fail_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
		}
	};

	$(document).ready(function() {
		window.zjopen = new ZJopen();
	});
})();