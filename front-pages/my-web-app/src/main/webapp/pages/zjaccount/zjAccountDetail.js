(function() {
	var AccountDetail = function() {
		this.init();
	};
	AccountDetail.prototype = {
		config : {
			queryObjectUrl : root + '/ZJAccount/getDetailById.action',
			subHeight : subHeight,
			subWidth : subWidth,
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null
		},
		init : function() {
			this.render();
			this.initData();
		},
		render : function() {
			var that = this;
			// 布局
			$('#layout1').ligerLayout({
				leftWidth : '210',
				allowLeftResize : false,
				height : '100%',
				heightDiff : -1,
				space : 4,
				onHeightChanged : that._heightChanged
			});
			$('.l-layout-header').show();
			$('.l-layout-header-toggle').css('z-index', '2');
			$('#pageloading').hide();
		},
		initData : function() {
			var that = this;
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '账户管理-中交账户列表-账户详细信息', imgUrl);
			$('#step1').css('width',(parent.window.zjcreate.config.subWidth-100));
//			var id = parent.window.zjcreate.cache.id;
			var id = parent.window.zjaccountList.cache.id;
			JAjax(that.config.queryObjectUrl, {'id' : id}, 'json', function(data) {
				if (data) {
					$('#insideAccountNo').text(data.insideAccountNo);
					$("#ownerLoginName").text(data.ownerLoginName);

					$("#totalBalance").text(convertFen2Yuan(data.totalBalance));
					$('#mobileNo').text(data.mobileNo);
					
					var accountStatus = MALQ_CODE.getCodeName("ACCOUNT_STATUS", data.accountStatus);
					$('#accountStatus').text(accountStatus);
					var createTime = getSmpFormatDateByLong(data.createTime, true);
					$('#createTime').text(createTime);
				}
			}, function() {JSWK.clewBox('查询详情失败', 'clew_ico_fail', failShowTime);}, true);
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.accountDetail;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
	};

	$(document).ready(function() {
		window.accountDetail = new AccountDetail();
	});
})();