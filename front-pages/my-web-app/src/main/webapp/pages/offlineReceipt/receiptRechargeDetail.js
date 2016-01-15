(function() {
	var DealList = function() {
		this.init();
	};
	DealList.prototype = {
		config : {
			queryById : root + '/receipt/queryById.action',
			//editStepNo : root + '/recharge/editStepNo.action',
			addApprovalUrl : root +'/receipt/addApproval.action',
			queryVoucher:root+'/receipt/queryVoucher.action',
			checkVoucherUrl : root + '/receipt/isVoucherUnique.action',
			queryDealById : root + '/approveInfo/findByRechargeApplyId.action',
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null,
			imgUrl : null,
			checkPassFlag : '1',
			checkedFlag : '1',
			identifier : '1'
		},
		init : function() {
			this.render();
			this.loadData();
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
			
			var w = 134;
			//来款渠道
			MALQ_CODE.getSelectByCodeType($("#incomeChannel"), "VOUCHER_CHANNEL", w, null);
			$("#incomeChannel").ligerComboBox({width : w});
			$("#incomeChannel").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#incomeChannelSub"), "", w, null);
			$("#incomeChannelSub").ligerComboBox({width : w});
			$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');	
			
//			MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "POSMerchantCode", w, null);
//			$("#posMerchantCode").ligerComboBox({width : w});
//			$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');
			MALQ_CODE.getSelectByCodeType($("#posMerchantCode"), "", w, null);
			$("#posMerchantCode").ligerComboBox({width : w});
			$("#incomeChannelSub").ligerGetComboBoxManager().selectValue('');
		},
		loadData:function(){
			var that = this;
			var data = new Date();
			var dataStr = data.getFullYear().toString()+"-"+(data.getMonth()+1)+"-"+data.getDate()+" "+data.getHours()+":"+data.getMinutes();
			$("#time").val(dataStr);
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '申请信息', imgUrl);
			$('#step1').css('width',(parent.window.receiptList.config.subWidth-100));
			
			MALQ_CODE.titleBlock($('#step1_1'), '小票排重', imgUrl);
			$('#step1_1').css('width',(parent.window.receiptList.config.subWidth-100));
		
			MALQ_CODE.titleBlock($('#step4'), '到账处理', imgUrl);
			$('#step4').css('width',(parent.window.receiptList.config.subWidth-100));
			
			MALQ_CODE.titleBlock($('#step5'), '油卡分配', imgUrl);
			$('#step5').css('width',(parent.window.receiptList.config.subWidth-100));
			//通过ID查找充值数据
			var id = parent.window.receiptList.cache.id;
			JAjax(that.config.queryById, {"id":id}, 'json', function(data){
				if(data){
					$("#remitterName").text(data.remitterName);
					$("#applyName").text(data.applyName);
					$("#applyPersonPost").text(MALQ_CODE.getZoneNameByCode(data.applyPersonPost));
					$("#customerName").text(data.customerName);
					$("#tradeAmount").text(data.tradeAmount);
					$("#applyTime").text(getSmpFormatDateByLong(data.applyTime,true));
					
				};
				//获取出纳-审批结果
				JAjax(that.config.queryDealById, {"id":id,"approvalUserId":"4304"}, 'json', function(dealData){
					var dealDate = dealData[0];
					if (dealDate == undefined || dealDate == '') {
					} else {
						$("#dealAmountArriveTime").text(getSmpFormatDateByLong(data.amountArriveTime,true));//确认时间
						$("#dealApprovalPersonName").text(dealDate.approvalPersonName);//审批人名称
		//				console.log('date: ' + dealDate.operTime);
						$("#dealOperTime").text(getSmpFormatDateByLong(dealDate.operTime,true));//审批操作日期
						$("#dealApprovalSuggest").text(dealDate.approvalSuggest==null?"同意":dealDate.approvalSuggest);//审批意见
						$("#dealApprovalResult").text(dealDate.approvalResult == '1' ? "待分配" : "未通过");//审批结果
					}
				});
			});
				//获取最终-审批结果
				JAjax(that.config.queryDealById, {"id":id,"approvalUserId":"4302"}, 'json', function(dealData){
					var dealDate = dealData[0];
					if (dealDate == undefined || dealDate == '') {
					} else {
						$("#dealApprovalPersonNameFinal").text(dealDate.approvalPersonName);//审批人名称
	//					console.log('date: ' + dealDate.operTime);
						$("#dealApprovalSuggestFinal").text(dealDate.approvalSuggest==null?"同意":dealDate.approvalSuggest);//审批意见
						$("#dealApprovalResultFinal").text(dealDate.approvalResult == '1' ? "已分配" : "未分配");//审批结果
					}
				});
			JAjax(that.config.queryVoucher, {"applyId":id}, 'text', function(data){
				if(data){
					/* $("#passImg_img").attr("src",data);
					 $("#imgHref").show();*/
					data = eval("(" + data + ")");
					that.cache.imgUrl = data.voucherFileName;
					that.checkVoucher(data);
				}
			});

		},
		queryBigImg:function(){
			var o=document.getElementById("passImg_img");
			showBigImg(o);
		},
		showImg : function(index){
			var that = window.dealList;
			var url = that.cache.imgUrl;
			var urlArr = url.split(",");
//			alert(urlArr[0]+"******"+urlArr[1]);
//			parent.window.queryList.showImg(url);
//			window.open("showImg"+index+".html?" + urlArr[index], '图片详情', 
//					'height=650, width=650, top=0,left=0, toolbar=no, menubar=no, scrollbars=no, '
//					 + 'resizable=no,location=no, status=no');
			window.open(urlArr[index],"_blank",'图片详情', 
					'height=650, width=650, top=0,left=0, toolbar=no, menubar=no, scrollbars=no, '
					 + 'resizable=no,location=no, status=no');
//			window.open("showImg.html?" + urlArr[1], '图片详情', 
//					'height=650, width=650, top=0,left=0, toolbar=no, menubar=no, scrollbars=no, '
//					 + 'resizable=no,location=no, status=no');
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.dealList;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
		collectVoucherInfo : function(params) {
			var incomeChannel = $("#incomeChannel").ligerGetComboBoxManager().getValue();
			if(incomeChannel&&incomeChannel!=''){
				params['model.channel'] = incomeChannel; //incomeChannelSub
				params['model.subChannel'] = $("#incomeChannelSub").ligerGetComboBoxManager().getValue();
				if (incomeChannel == 'CHANNEL_01') { //对公
					params['model.voucherTradeNo'] = $("#voucherTradeNo").val();
					params['model.voucherTime'] = $("#pubVoucherTime").val();
				}
				if (incomeChannel == 'CHANNEL_02') { //POS
					params['model.merchantCode'] = $("#posMerchantCode").ligerGetComboBoxManager().getValue();
					params['model.terminalCode'] = $("#terminalCode").val();	
					params['model.voucherCode'] = $("#voucherCode").val();
					params['model.voucherTime'] = $("#voucherTime").val();						
				}
			}
		},
		f_save : function(){
			var that = this;
			
			//检查凭证信息
			var checkPassFlag = that.cache.checkPassFlag;
			var checkedFlag = that.cache.checkedFlag;
			if (checkedFlag == '1' && checkPassFlag == '1') {
			} else {
				if ($("input[name='approvalResult']:checked").val() == '同意') {
					if (checkedFlag == '0') {
						$.ligerDialog.alert('凭证有效性未验证！', '提示', 'error');
						return;
					}
					if (checkPassFlag == '0') {
						$.ligerDialog.alert('凭证信息重复！', '提示', 'error');
						return;
					}
				}
			}
			showMask();
			var amountArriveTime = $("#time").val();//到账时间
			var flag = false;
			if(amountArriveTime==""){
				$("#times").text("请输入时间");
			}else{
				flag = true;
			}
			if(flag){
				//先修改充值里面的步骤处理号
				var id = parent.window.receiptList.cache.id;
				var stepNo = "";//步骤号
				var applyStatus = "";//申请状态 4101 已审核  
				var approvalResult = $("input[name='approvalResult']:checked").val();//是否同意
				var tradeAmount=$("#tradeAmount").text();
				
				var time = amountArriveTime;//到账时间
				var identifier=that.cache.identifier;
				var approvalSuggest = $("#approvalSuggest").val();
				var params = {
						'model.identifier':identifier,//0代表出纳,用于后台逻辑判断
						'model.id':id,
						'model.approvalSuggest' : approvalSuggest,
						'model.approvalResult' : approvalResult,
						'model.approvalUserId':'4304',
						'model.stepNo':stepNo,
						'model.applyStatus':applyStatus,
						'model.amountArriveTime':time
				};
				that.collectVoucherInfo(params);
				JAjax(that.config.queryById, {"id":id}, 'json', function(rechargeData){
//					alert('stepNo: ' + rechargeData.stepNo + "\t" + 'approvalResult: ' +approvalResult);
					if(rechargeData.stepNo=='4304'){
							if(approvalResult=='不同意'&&(approvalSuggest==null||approvalSuggest=="")){
								hideMask();
								parent.window.receiptList.alert('请填写处理意见', 'clew_ico_fail');
								return false;
							}else{								
								parent.window.receiptList.suerDeal(params);
							}
						}else{
							hideMask();
							parent.window.receiptList.alert('此记录已审批', 'clew_ico_succ');
							parent.window.receiptList.cache.dialog.close();
					}
				});
			}
		},
		validate : function(){
			var amountArriveTime =  $("#time").val();
			if(amountArriveTime==""){
				$("#times").text("请输入时间");
			}else{
				$("#times").text("");
			}
		},
		checkPOSVoucher : function() {
			var incomeChannelSub = $('#incomeChannelSub').ligerGetComboBoxManager().getValue();
			var posMerchantCode = $('#posMerchantCode').ligerGetComboBoxManager().getValue();
			var terminalCode = $('#terminalCode').val();
			var voucherCode = $('#voucherCode').val();
			var voucherTime = $('#voucherTime').val();
			
			if (incomeChannelSub != ''
					&& posMerchantCode != ''
					&& terminalCode != ''
					&& voucherCode != ''
					&& voucherTime != '') {
				return true;
			} else {
				return false;
			}
			
		},
		
		checkPUBVouch : function() {
			var voucherTradeNo = $('#voucherTradeNo').val();
			var pubVoucherTime = $('#pubVoucherTime').val();
			if (voucherTradeNo != '' && pubVoucherTime != '') {
				return true;
			} else {
				return false;
			}
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
			}
			
			$.each($('.voucherCombox'), function(i, item){
				$(this).ligerGetComboBoxManager().setDisabled(true);
			});
		}
	};
	$(document).ready(function() {
		window.dealList = new DealList();
		
		
	});
})();