/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var merchantDetail = function() {
		this.init();
	};
	merchantDetail.prototype = {
		config : {
			queryObjectUrl : root + '/merchant/getMerchantById.action',
			// 检查属性是否唯一
			// 取商户类型
			querySimpleCodeUrl : root + '/simpleCode/findMerchant.action',
			subHeight : 400,
			subWidth : subWidth
		},
		cache : {
			dialog : null,
		},
		init : function() {
			this.initMerchant();
			this.loadCss();
		},
		initMerchant : function() {
			var that = this;
			var id = parent.window.merchantList.cache.id;
			JAjax(that.config.queryObjectUrl, {
				'id' : id
			}, 'json', function(data) {
				if (data) {
					$('#storeName').text(data.storeName);// 商户名称
					$("#contactUser").text(data.contactUser);// 联系人
					$("#contactPhone").text(data.contactPhone);// 联系人电话
					// 商户状态
					var storeStatus = MALQ_CODE.getCodeName("3000", data.storeStatus);
					$("#storeStatus").text(storeStatus);
					// 商户类型
					var storeType = MALQ_CODE.getCodeName("2000", data.storeType)==''?data.storeType: MALQ_CODE.getCodeName("2000", data.storeType);
					$("#storeType").text(storeType);
					// storeCode 商户状态
					$('#storeCode').text(data.storeCode);
					// privateKey 秘钥
					//$('#privateKey').text(data.privateKey);
					// regTime 接入时间
					var regTime = getSmpFormatDateByLong(data.regTime, true);
					$('#regTime').text(regTime);
					// opUser 操作人
					$('#opUser').text(data.opUser);
				}
			}, function() {JSWK.clewBox('查询商户对象失败', 'clew_ico_fail', failShowTime);}, true);
			$('#pageloading').hide();
		},
		loadCss : function() {
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '商户管理-商户详情展示', imgUrl);
			$('#step1').css('width',(parent.window.merchantList.config.subWidth-100));
		},
	};

	$(document).ready(function() {
		window.merchantDetail = new merchantDetail();
	});
})();