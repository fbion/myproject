(function() {
	var QueryDetail = function() {
		this.init();
	};
	
	var getFundsConfirmTime = function(time){
		if (time == null || time == 'undefined' || time == 0) {
			return '';
		} else {
			return getSmpFormatDateByLong(time);
		}
	};
	
	QueryDetail.prototype = {
		config : {
			//通过ID查找充值明细
			queryById : root + '/recharge/queryById.action',
			//通过充值明细ID查找审批结果
			queryDealById : root + '/approveInfo/findByRechargeApplyId.action',
			queryVoucher:root+'/recharge/queryVoucher.action',
			//查找阈值
			queryProperties : root + '/recharge/findProperties.action',
			checkVoucherUrl : root + '/recharge/isVoucherUnique.action'
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null,
			imgUrl : null,
			voucherCheckResult : '0'
		},
		init : function() {
			this.render();
			this.initTitleBlock();
			this.initPageData();
			this.initCombox();
		},
		initCombox : function() {
			var w = 134;
			//来款渠道
			MALQ_CODE.getSelectByCodeType($("#incomeChannel"), "VOUCHER_CHANNEL", w, null);
			$("#incomeChannel").ligerComboBox({width : w});
			$("#incomeChannel").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "", w, null);
			$("#incomeChannelSub").ligerComboBox({width : w});
			$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');	
			
			MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "POSMerchantCode", w, null);
			$("#posMerchantCode").ligerComboBox({width : w});
			$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');			
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
		initTitleBlock:function(){
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '付款凭证', imgUrl);
			$('#step1').css('width',(parent.window.queryList.config.subWidth-100));
			
			MALQ_CODE.titleBlock($('#step1_1'), '凭证信息', imgUrl);
			$('#step1_1').css('width',(parent.window.queryList.config.subWidth-100));
			
			MALQ_CODE.titleBlock($('#step2'), '账户信息', imgUrl);
			$('#step2').css('width',(parent.window.queryList.config.subWidth-100));
			MALQ_CODE.titleBlock($('#step3'), '申请人信息', imgUrl);
			$('#step3').css('width',(parent.window.queryList.config.subWidth-100));
			MALQ_CODE.titleBlock($('#step4'), '出纳-到款确认', imgUrl);
			$('#step4').css('width',(parent.window.queryList.config.subWidth-100));
			MALQ_CODE.titleBlock($('#step5'), '财务经理-审批', imgUrl);
			$('#step5').css('width',(parent.window.queryList.config.subWidth-100));
		},
		initPageData:function(){
			var that = this;
			var id = parent.window.queryList.cache.id;
			JAjax(that.config.queryById, {"id":id}, 'json', function(data){
				if(data){
					$("#remitterName").text(data.remitterName);//付款人名称
					$("#remitterBankcardNo").text(data.remitterBankcardNo);//付款人账号
					$("#remitterBankCode").text(MALQ_CODE.getCodeName("AUTH",data.remitterBankCode));//付款人开户行
					$("#payeeName").text(data.payeeName);//收款人名称
					$("#payeeNo").text(data.payeeNo);//收款人账号
					$("#amountArriveTime").text(getSmpFormatDateByLong(data.amountArriveTime,true));//记账日期
					$("#tradeAmount").text(data.tradeAmount);//汇款金额小写
					$("#tradeAmountUpper").text(data.tradeAmountUpper);//汇款金额大写
					$("#id").val(data.id);
					$("#description").text(data.description);
					$("#storeId").text(data.storeId);
					$("#applyTime").text(getSmpFormatDateByLong(data.applyTime,true));
					$("#approvalPersonName").text(data.applyName);
					$("#fundsConfirmId").text(data.fundsConfirmId);
					//$("#fundsConfirmTime").text(getSmpFormatDateByLong(data.fundsConfirmTime));
					$("#fundsConfirmTime").text(getFundsConfirmTime(data.fundsConfirmTime));
					//获取出纳-到款确认审批结果
					JAjax(that.config.queryDealById, {"id":id,"approvalUserId":"4304"}, 'json', function(dealData){
						var dealDate = dealData[0];
						if (dealDate == undefined || dealDate == '') {
						} else {
							$("#dealAmountArriveTime").text(getSmpFormatDateByLong(data.amountArriveTime,true));//确认时间
							$("#dealApprovalPersonName").text(dealDate.approvalPersonName);//审批人名称
//							console.log('date: ' + dealDate.operTime);
							$("#dealOperTime").text(getSmpFormatDateByLong(dealDate.operTime,true));//审批操作日期
							$("#dealApprovalSuggest").text(dealDate.approvalSuggest==null?"同意":dealDate.approvalSuggest);//审批意见
							if(dealDate.approvalResult == '1'){
								$("#dealApprovalResult").text("同意");
							}else if(dealDate.approvalResult == '-1'){
								$("#dealApprovalResult").text("不同意");
							}else{
								$("#dealApprovalResult").text(dealDate.approvalResult );//审批结果
							}
						}
					});
					//查找阈值
					var threshold = 0;
					JAjax(that.config.queryProperties,{"key":"OFFLINE_CHECK_AMOUNT"},"json",function(valueData){
						threshold = valueData;
					});
					if(parseInt(data.tradeAmount)>=threshold){
					//获取财务审批结果
					JAjax(that.config.queryDealById, {"id":id,"approvalUserId":"4302"}, 'json', function(dealFinanceData){
						var financeDate = dealFinanceData[0];
						if (financeDate == undefined || financeDate == '') {
						} else {
							//财务审批
							$("#financeApprovalPersonName").text(financeDate.approvalPersonName);//审批人名称
							$("#financeOperTime").text(getSmpFormatDateByLong(financeDate.operTime,true));//审批操作日期
							$("#financeApprovalSuggest").text(financeDate.approvalSuggest);//审批意见
							if(financeDate.approvalResult == '1'){
								$("#dealApprovalResult").text("同意");
							}else if(financeDate.approvalResult == '-1'){
								$("#dealApprovalResult").text("不同意");
							}else{
								$("#dealApprovalResult").text(financeDate.approvalResult );//审批结果
							}
							
							$("#finalTr1").show();
							$("#finalTr2").show();
							$("#finalTr3").show();
						}
					});
					}
				};
			});
			JAjax(that.config.queryVoucher, {"applyId":id}, 'text', function(data){
				if(data){
					/* $("#passImg_img").attr("src",data);
					 $("#imgHref").show();*/
					data = eval('(' + data + ')');
					that.cache.imgUrl = data.voucherFileName;
					that.checkVoucher(data);
				}
			});
		},
		//判定凭证信息是否重复
		checkVoucher : function(data) {
			var incomeChannel = data.channel;//来款渠道
			var incomeChannelSub = data.subChannel;//子渠道
			var _this = this;
			var checkUrl = _this.config.checkVoucherUrl;
			var param = {};
			if ('CHANNEL_01' == incomeChannel) { //PUB
				param['equal[applyId]'] = data.applyId;
				param['equal[channel]'] = incomeChannel;
				param['equal[subChannel]'] = incomeChannelSub;
				param['equal[voucherTradeNo]'] = data.voucherTradeNo;
				param['equal[pubVoucherTime]'] = data.voucherTime;
				
				var pos = $('.posVoucher');
				$.each(pos, function(i, item){
					$(this).hide('slow');
				});
				
				var pub = $('.pubVoucher');
				$.each(pub, function(i, item){
					$(this).show('slow');
				});
				$("#incomeChannel").ligerGetComboBoxManager().selectValue(data.channel);
				
				var w = 134;
				MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "PUB_BANK", w, null);
				$("#incomeChannelSub").ligerComboBox({width : w});
				
				$("#incomeChannelSub").ligerGetComboBoxManager().selectValue(data.subChannel);
				$("#voucherTradeNo").text(data.voucherTradeNo);  //交易流水号
				$('#pubVoucherTime').text(data.voucherTime); //时间戳
				
//        		JAjax(checkUrl, param, 'json', function(d){
////        			_this.cache.voucherCheckResult = d;
//        			 //对公
//					var pos = $('.posVoucher');
//					$.each(pos, function(i, item){
//						$(this).hide('slow');
//					});
//					
//					var pub = $('.pubVoucher');
//					$.each(pub, function(i, item){
//						$(this).show('slow');
//					});
//					$("#incomeChannel").ligerGetComboBoxManager().selectValue(data.channel);
//					
//					var w = 134;
//					MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "PUB_BANK", w, null);
//					$("#incomeChannelSub").ligerComboBox({width : w});
//					
//					$("#incomeChannelSub").ligerGetComboBoxManager().selectValue(data.subChannel);
//					$("#voucherTradeNo").text(data.voucherTradeNo);  //交易流水号
//					$('#pubVoucherTime').text(data.voucherTime); //时间戳
////					$('#voucherCheckResult').text(_this.checkVoucher(data));
//					$('#voucherCheckResult').text(d == '1' ? "重复提交" : "不重复");	
//                }, 
//                function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, 
//                true);
				
			} else if ('CHANNEL_02' == incomeChannel) { //POS
				param['equal[applyId]'] = data.applyId;
				param['equal[channel]'] = incomeChannel;
				param['equal[subChannel]'] = incomeChannelSub;
				
				param['equal[merchantCode]'] = data.merchantCode
				param['equal[terminalCode]'] = data.terminalCode;
				param['equal[voucherCode]'] = data.voucherCode;
				param['equal[voucherTime]'] = data.voucherTime;	
				
				var pos = $('.posVoucher');
				$.each(pos, function(i, item){
					$(this).show('slow');
				});
				var pub = $('.pubVoucher');
				$.each(pub, function(i, item){
					$(this).hide('slow');
				});
//				console.log('data.channel： ' + data.channel + 'data.subChannel: ' + data.subChannel + 'data.merchantCode: ' + data.merchantCode);
				$("#incomeChannel").ligerGetComboBoxManager().selectValue(data.channel);
				var w = 134;
				
				MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "CHANNEL_02", w, null);
				$("#incomeChannelSub").ligerComboBox({width : w});
				
				$("#incomeChannelSub").ligerGetComboBoxManager().selectValue(data.subChannel);
				
				if (data.subChannel == 'POS_01') {
					MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "POS_MER_YS", w, null);
					$("#posMerchantCode").ligerComboBox({width : w});
					$("#posMerchantCode").ligerGetComboBoxManager().selectValue('');
				}
				if (data.subChannel == 'POS_02') { //易宝
					MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "POS_MER_YB", w, null);
					$("#posMerchantCode").ligerComboBox({width : w});
					$("#posMerchantCode").ligerGetComboBoxManager().selectValue('');
				}
				if (data.subChannel == 'POS_03') { //通联
					MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "POS_MER_TL", w, null);
					$("#posMerchantCode").ligerComboBox({width : w});
					$("#posMerchantCode").ligerGetComboBoxManager().selectValue('');
				}
				$("#posMerchantCode").ligerGetComboBoxManager().selectValue(data.merchantCode);//商户编号
				$('#terminalCode').text(data.terminalCode); //终端号
				$('#voucherCode').text(data.voucherCode);	//凭证号
				$('#voucherTime').text(data.voucherTime); //日期
				
//        		JAjax(checkUrl, param, 'json', function(d){
//        			_this.cache.voucherCheckResult = d;
//        			 //POS
//					var pos = $('.posVoucher');
//					$.each(pos, function(i, item){
//						$(this).show('slow');
//					});
//					var pub = $('.pubVoucher');
//					$.each(pub, function(i, item){
//						$(this).hide('slow');
//					});
////					console.log('data.channel： ' + data.channel + 'data.subChannel: ' + data.subChannel + 'data.merchantCode: ' + data.merchantCode);
//					$("#incomeChannel").ligerGetComboBoxManager().selectValue(data.channel);
//					var w = 134;
//					if (data.channel == 'CHANNEL_01') {
//						MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "PUB_BANK", w, null);
//						$("#incomeChannelSub").ligerComboBox({width : w});
//						$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');
//					}
//					if (data.channel == 'CHANNEL_02') {
//						MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "CHANNEL_02", w, null);
//						$("#incomeChannelSub").ligerComboBox({width : w});
//						$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');
//					}
//					$("#incomeChannelSub").ligerGetComboBoxManager().selectValue(data.subChannel);
//					
//					if (data.subChannel == 'POS_01') {
//						MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "POS_MER_YS", w, null);
//						$("#posMerchantCode").ligerComboBox({width : w});
//						$("#posMerchantCode").ligerGetComboBoxManager().selectValue('');
//					}
//					if (data.subChannel == 'POS_02') { //易宝
//						MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "POS_MER_YB", w, null);
//						$("#posMerchantCode").ligerComboBox({width : w});
//						$("#posMerchantCode").ligerGetComboBoxManager().selectValue('');
//					}
//					if (data.subChannel == 'POS_03') { //通联
//						MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "POS_MER_TL", w, null);
//						$("#posMerchantCode").ligerComboBox({width : w});
//						$("#posMerchantCode").ligerGetComboBoxManager().selectValue('');
//					}
//					$("#posMerchantCode").ligerGetComboBoxManager().selectValue(data.merchantCode);//商户编号
//					$('#terminalCode').text(data.terminalCode); //终端号
//					$('#voucherCode').text(data.voucherCode);	//凭证号
//					$('#voucherTime').text(data.voucherTime); //日期
//					$('#voucherCheckResult').text(d == '1' ? "重复提交" : "不重复");					
//				
//                }, 
//                function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, 
//                true);
			}
			
			$.each($('.voucherCombox'), function(i, item){
				$(this).ligerGetComboBoxManager().setDisabled(true);
			});
		},
		//显示凭证信息
		showVoucherInfo : function(data) {
			alert('showVoucherInfo');
			var _this = this;
			var incomeChannel = data.channel;
			if (incomeChannel != '') {
				if (incomeChannel == 'CHANNEL_01') { //对公
					var pos = $('.posVoucher');
					$.each(pos, function(i, item){
						$(this).hide('slow');
					});
					
					var pub = $('.pubVoucher');
					$.each(pub, function(i, item){
						$(this).show('slow');
					});
					
					var w = 134;
					MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "AUTH", w, null);
					$("#incomeChannelSub").ligerComboBox({width : w});
					
					$("#incomeChannel").ligerGetComboBoxManager().selectValue(data.channel);
					$("#incomeChannelSub").ligerGetComboBoxManager().selectValue(data.subChannel);
					
					$("#voucherTradeNo").text(data.voucherTradeNo);  //交易流水号
					$('#pubVoucherTime').text(data.voucherTime); //时间戳
					var result = _this.cache.voucherCheckResult;
//					$('#voucherCheckResult').text(_this.checkVoucher(data));
					$('#voucherCheckResult').text(_this.cache.voucherCheckResult == '1' ? "重复" : "不重复");	
					
				}
				
				if (incomeChannel == 'CHANNEL_02') { //POS
					var pos = $('.posVoucher');
					$.each(pos, function(i, item){
						$(this).show('slow');
					});
					var pub = $('.pubVoucher');
					$.each(pub, function(i, item){
						$(this).hide('slow');
					});
//					console.log('data.channel： ' + data.channel + 'data.subChannel: ' + data.subChannel + 'data.merchantCode: ' + data.merchantCode);
					$("#incomeChannel").ligerGetComboBoxManager().selectValue(data.channel);
					var w = 134;
					MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "CHANNEL_02", w, null);
					$("#incomeChannelSub").ligerComboBox({width : w});
					$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');
					
					$("#incomeChannelSub").ligerGetComboBoxManager().selectValue(data.subChannel);
					
					if (data.subChannel == 'POS_01') {
						MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "POS_MER_YS", w, null);
						$("#posMerchantCode").ligerComboBox({width : w});
						$("#posMerchantCode").ligerGetComboBoxManager().selectValue('');
					}
					if (data.subChannel == 'POS_02') { //易宝
						MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "POS_MER_YB", w, null);
						$("#posMerchantCode").ligerComboBox({width : w});
						$("#posMerchantCode").ligerGetComboBoxManager().selectValue('');
					}
					if (data.subChannel == 'POS_03') { //通联
						MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "POS_MER_TL", w, null);
						$("#posMerchantCode").ligerComboBox({width : w});
						$("#posMerchantCode").ligerGetComboBoxManager().selectValue('');
					}
					$("#posMerchantCode").ligerGetComboBoxManager().selectValue(data.merchantCode);//商户编号
					$('#terminalCode').text(data.terminalCode); //终端号
					$('#voucherCode').text(data.voucherCode);	//凭证号
					$('#voucherTime').text(data.voucherTime); //日期
//					$('#voucherTime').text(getSmpFormatDateByLong(parseInt(data.voucherTime), true));
//					var result = _this.cache.voucherCheckResult;
//					alert(result);
					$('#voucherCheckResult').text(_this.cache.voucherCheckResult == '1' ? "重复" : "不重复");					
				}
				
			}
			
			$.each($('.voucherCombox'), function(i, item){
				$(this).ligerGetComboBoxManager().setDisabled(true);
			});
		},
		
		queryBigImg:function(){
			var o=document.getElementById("passImg_img");
			showBigImg(o);
		},
		showImg : function(){
			var that = window.queryDetail;
			var url = that.cache.imgUrl;
//			parent.window.queryList.showImg(url);
			window.open("showPic.html?" + url, '图片详情', 
					'height=650, width=650, top=0,left=0, toolbar=no, menubar=no, scrollbars=no, '
					 + 'resizable=no,location=no, status=no');
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.queryDetail;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		}
	};
	$(document).ready(function() {
		window.queryDetail = new QueryDetail();
	});
})();