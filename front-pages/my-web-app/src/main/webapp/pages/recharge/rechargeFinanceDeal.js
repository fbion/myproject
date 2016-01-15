(function() {
	var FinaceDeal= function() {
		this.init();
	};
	FinaceDeal.prototype = {
		config : {
			queryUrl : root + '/recharge/queryFinaceDeal.action',
			queryById : root + '/recharge/queryById.action',
			queryDealById : root + '/approveInfo/findByRechargeApplyId.action',
			addApprovalUrl : root +'/receipt/addApproval.action',
			queryVoucher:root+'/recharge/queryVoucher.action'
		},
		cache : {
			grid : null,
			accordion : null,
			tab : null,
			imgUrl : null,
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
		},
		loadData:function(){
			var that = this;
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '付款凭证', imgUrl);
			$('#step1').css('width',(parent.window.queryList.config.subWidth-100));
			MALQ_CODE.titleBlock($('#step2'), '账户信息', imgUrl);
			$('#step2').css('width',(parent.window.queryList.config.subWidth-100));
			MALQ_CODE.titleBlock($('#step3'), '申请人信息', imgUrl);
			$('#step3').css('width',(parent.window.queryList.config.subWidth-100));
			MALQ_CODE.titleBlock($('#step4'), '出纳-到款确认', imgUrl);
			$('#step4').css('width',(parent.window.queryList.config.subWidth-100));
			MALQ_CODE.titleBlock($('#step5'), '财务经理-审批', imgUrl);
			$('#step5').css('width',(parent.window.queryList.config.subWidth-100));
			//通过ID查找充值数据
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
					$("#approvalPersonName").text(data.approvalPersonName);
					//获取出纳-到款确认审批结果
					JAjax(that.config.queryDealById, {"id":id,"approvalUserId":"4304"}, 'json', function(dealData){
						var dealDate = dealData[0];
						$("#dealApprovalPersonName").text(dealDate.approvalPersonName);//审批人名称
						$("#operTime").text(getSmpFormatDateByLong(dealDate.operTime,true));//审批操作日期
						$("#approvalSuggest").text(dealDate.approvalSuggest);//审批意见
						$("#approvalResult").text(dealDate.approvalResult);//审批结果
					});
				};
			});
			JAjax(that.config.queryVoucher, {"applyId":id}, 'text', function(data){
				if(data){
					/* $("#passImg_img").attr("src",data);
					 $("#imgHref").show();*/
					that.cache.imgUrl = data;
				}
			});
		},
		queryBigImg:function(){
			var o=document.getElementById("passImg_img");
			showBigImg(o);
		},
		showImg : function(){
			var that = window.finaceDeal;
			var url = that.cache.imgUrl;
			parent.window.queryList.showImg(url);
		},
		// 高度改变
		_heightChanged : function(options) {
			var that = window.finaceDeal;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
		f_save : function(){
			showMask();
			var that = this;
			var id = $("#id").val();
			var stepNo = "";//步骤号
			var applyStatus = "";//申请状态 4101 已审核  
			var approvalResult = $("input[name='financeApprovalSuggest']:checked").val();//是否同意
			
			var approvalSuggest = $("#financeApprovalSuggest").val();//审核意见
			var params = {
					'model.identifier':'1',
					'model.id':id,
					'model.approvalSuggest' : approvalSuggest,
					'model.approvalResult' : approvalResult,
					'model.approvalUserId':'4302',
					'model.stepNo':stepNo,
					'model.applyStatus':applyStatus
			};
			 JAjax(root + '/payment/queryById.action', {"id":id}, 'json', function(finceData){ 
				 if(finceData.stepNo=='4302'){
					 if(approvalResult=='不同意'&&(approvalSuggest==null||approvalSuggest=="")){
						hideMask();
						parent.window.queryList.alert('请填写处理意见', 'clew_ico_fail');
						return false;
					}else{
						JAjax(that.config.addApprovalUrl, params, 'json', function(data){
							if(data.message=="操作成功"){
								hideMask();
								parent.window.queryList.cache.grid.loadData();
								parent.window.queryList.alert('操作成功！', 'clew_ico_succ');
							}else{
								hideMask();
								parent.window.queryList.alert(data.message, 'clew_ico_fail');
							}
							parent.window.queryList.cache.dialog.close();
							hideMask();
						});
					}
			}else{
				hideMask();
				parent.window.queryList.alert('不能重复审批！', 'clew_ico_succ');
				parent.window.queryList.cache.dialog.close();
			}
		  });
		}
	};
	$(document).ready(function() {
		window.finaceDeal = new FinaceDeal();
	});
})();