(function() {
	var OrderDetail = function() {
		this.init();
	};
	
	var processProductCatagory = function(val) {
		//11，油卡充值 ；12，油卡返利；13：账户充值；10，其它；
		if ( val == 11) {
			return "油卡充值";
		} else if (val == 12) {
			return "油卡返利";
		} else if (val == 13) {
			return "账户充值";
		} else {
			return "其它";
		}
	};
	
	var processClentType = function(val) {
		//0、IMEI；1、MAC；2、UUID；3、other
		if (val == 0) {
			return "IMEI";
		} else if (val == 1) {
			return "MAC";
		} else if (val == 2) {
			return "UUID";
		} else {
			return "other";
		}
	};
	
	OrderDetail.prototype = {
		config : {
			queryObjectUrl : root + '/order/queryByOrderNo.action',
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
			MALQ_CODE.titleBlock($('#step1'), '交易管理-交易订单列表-订单详细信息', imgUrl);
			$('#step1').css('width',(parent.window.queryList.config.subWidth-100));
			var orderNo = parent.window.queryList.cache.orderNo;
			JAjax(that.config.queryObjectUrl, {'orderNo' : orderNo}, 'json', function(data) {
				if (data) {
					var data = data[0];
					
					$("#orderNo").text(data.orderNo);//订单号
					
					var orderStatus = MALQ_CODE.getCodeName("PAY_ORDER_STATUS",data.orderStatus);
					$("#orderStatus").text(orderStatus);//订单状态
					
					var orderSubject = MALQ_CODE.getCodeName("ORDER_SUBJECT",data.orderSubject);
					$("#orderSubject").text(orderSubject);//科目
					
					$("#tradeExternalNo").text(data.tradeExternalNo);//交易流水号
					
					$("#storeCode").text(data.storeCode);//所属商户
					
					$("#accountNo").text(data.accountNo);//转入账户
					
					$("#collectMoneyAccountNo").text(data.collectMoneyAccountNo);//转出账户
					
					var orderAmount = data.orderAmount;
					$("#orderAmount").text(orderAmount);//交易金额
					
					$("#createTime").text(getSmpFormatDateByLong(parseInt(data.createTime), true));//交易时间
					
					var serviceProviderCode =  MALQ_CODE.getCodeName("PAYMENT_PLATFORM",data.serviceProviderCode);
					$("#serviceProviderCode").text(serviceProviderCode);//支付平台
					
					$("#inOwnerLoginName").text(data.outOwnerLoginName);//转入账户所属
					
					$("#outOwnerLoginName").text(data.inOwnerLoginName);//转出账户所属
					
					$("#workOrderNo").text(data.workOrderNo);//业务订单号
					
					$("#userId").text(data.userId);
					$("#collectMoneyUserId").text(data.collectMoneyUserId);
					$("#clentType").text(processClentType(data.clentType));
					$("#payChannel").text(data.payChannel);
					$("#productCatalog").text(processProductCatagory(data.productCatalog));
					$("#productName").text(data.productName);
					$("#clentId").text(data.clentId);
					$("#userUa").text(data.userUa);
					$("#userIp").text(data.userIp);
					$("#identityId").text(data.identityId);
					$("#identityType").text(data.identityType);
					$("#callbackUrl").text(data.callbackUrl);
					$("#fCallbackUrl").text(data.fCallbackUrl);
//					$("#payConfirmDate").text(data.payConfirmDate);
					$("#payConfirmDate").text(data.payConfirmDate == '0' ? '' : (getSmpFormatDateByLong(parseInt(data.payConfirmDate), true)));
					$("#isClearing").text(data.isClearing);
					
					/*$('#insideAccountNo').text(data.insideAccountNo);
					$("#ownerLoginName").text(data.ownerLoginName);

					$("#totalBalance").text(convertFen2Yuan(data.totalBalance));
					$('#mobileNo').text(data.mobileNo);
					
					var accountStatus = MALQ_CODE.getCodeName("ACCOUNT_STATUS", data.accountStatus);
					$('#accountStatus').text(accountStatus);
					var createTime = getSmpFormatDateByLong(data.createTime, true);
					$('#createTime').text(createTime);
					
					var accountType = MALQ_CODE.getCodeName("accountType", data.accountType);
					$("#accountType").text(accountType);//账户类型
					
					$("#usableBalance").text(convertFen2Yuan(data.usableBalance));
					$("#frozenBalance").text(convertFen2Yuan(data.frozenBalance));
					
					$('#unableTakecashBalance').text(convertFen2Yuan(data.unableTakecashBalance));//不可提现金额
					if("1"==data.isOperMess){
						$('#isOperMess').text("已开通");//是否开通短信通知
					}else{
						$('#isOperMess').text("未开通");//是否开通短信通知
					}
					
					$('#partShowIdcardNo').text(data.partShowIdcardNo);//身份证号
*/				}
			}, function() {JSWK.clewBox('查询详情失败', 'clew_ico_fail', failShowTime);}, true);
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.orderDetail;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
	};

	$(document).ready(function() {
		window.orderDetail = new OrderDetail();
	});
})();