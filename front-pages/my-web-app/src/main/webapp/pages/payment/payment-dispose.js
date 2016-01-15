/**
 * 线下流程管理-扣款审批
 */
(function() {
	var PaymentDispose = function() {
		this.init();
	};
	PaymentDispose.prototype = {
		config : {
			queryObjUrl : root + '/payment/queryById.action',
			
		},
		cache : {
			dialog_001 : null,
			form : null,
			tags : [],
		},
		init : function() {
			this.initLayoutFrom();
			this.initFormData();
		},
		initLayoutFrom : function() {
			var that=this;
			var id = parent.window.paymentList.cache.id;
			 JAjax(that.config.queryObjUrl, {"id":id}, 'json', function(data){   
             	if(data){  
             		$("#id").val(data.id);
             		$("#stepNo").val(data.stepNo);
             		var html = '';
             		var salesHtml = '';
             		var financialHtml = '';
             		html += '<tr><td>扣款原因：</td>';
             		html += '<td>'+data.description+'</td></tr>';
             		html += '<tr><td>商户编码：</td>';
             		html += '<td>'+data.storeId+'</td></tr>';
             		html += '<tr><td>扣款金额(元)：</td>';
             		html += '<td>'+convertFen2Yuan(data.tradeAmount)+'</td></tr>';
             		html += '<tr><td>申请人：</td>';
             		html += '<td>'+data.approvalPersonName+'</td></tr>';
             		html += '<tr><td>申请时间：</td>';
             		html += '<td>'+getSmpFormatDateByLong(data.fundsConfirmTime,false)+'</td></tr>';
             		$("#baseTb").html(html);//添加基础信息
             		if(data.stepNo=='4301'){//业务经理审批
             			salesHtml += '<tr><td>意见：</td><td><input type="radio" name="approvalResult" value="同意" checked="checked" validate="{required:true}">同意<input type="radio" name="approvalResult" value="不同意" validate="{required:true}">不同意</td>';
             			salesHtml += '</tr><tr><td>审核意见或建议：</td><td><textarea id="approvalSuggest" name="approvalSuggest" validate="{required:true,minlength:1,maxlength:255}"></textarea></td></tr>';
    					$("#salesmanTb").html(salesHtml);//添加业务经理审批字段
             		}
             		if(data.stepNo=='4302'||data.stepNo=='4303'){//财务经理审批
             			 url = root + '/approveInfo/findByRechargeApplyId.action';
             			 JAjax(url, {"id":id}, 'json', function(approveDate){
             				var dataSales;//业务经理审批对象
             				var dataFinancial=null;//财务经理审批对象
             				if(approveDate.length>1){
             				if(approveDate[0].operTime>approveDate[1].operTime){
             					dataSales = approveDate[1];
             					dataFinancial = approveDate[0];
             				}else{
             					dataSales = approveDate[0];
             					dataFinancial = approveDate[1];
             				}
             				}else{
             					dataSales = approveDate[0];
             				}
             				salesHtml += '<tr><td>意见：</td><td>'+dataSales.approvalResult+'</td></tr>';
             				var approval = dataSales.approvalSuggest=="null"||dataSales.approvalSuggest==null?"":dataSales.approvalSuggest;
             				salesHtml += '<tr><td>审核意见或建议：</td><td>'+approval+'</td></tr>';
             				salesHtml += '<tr><td>审批人：</td><td>'+dataSales.approvalPersonName+'</td></tr>';
             				salesHtml += '<tr><td>审批时间：</td><td>'+getSmpFormatDateByLong(dataSales.operTime,false)+'</td></tr>';
             				$("#salesmanTb").html(salesHtml);
             				if(data.stepNo=='4302'){
             				financialHtml += '<fieldset>';
             				financialHtml += '<legend><h2><img src="'+root+'/images/group.gif" width="20px" height="20px">&nbsp;&nbsp;财务经理审批</h2></legend>';
             				financialHtml += '<hr style="width:100%"/>';
             				financialHtml += '<table id="financialTb" cellpadding="0" cellspacing="0"  align="left">';
             				//添加审核内容
             				financialHtml += '<tr><td>意见：</td><td><input type="radio" name="approvalResult" value="同意" checked="checked" validate="{required:true}">同意<input type="radio" name="approvalResult" value="不同意" validate="{required:true}">不同意</td>';
             				financialHtml += '</tr><tr><td>审核意见或建议：</td><td><textarea id="approvalSuggest" name="approvalSuggest" validate="{required:true,minlength:1,maxlength:255}"></textarea></td></tr>';
             				financialHtml += '</table></fieldset>';
             				$("#financialDiv").html(financialHtml);
             				}
             				if(data.stepNo=='4303'){
         					financialHtml += '<fieldset>';
             				financialHtml += '<legend><h2><img src="'+root+'/images/group.gif" width="20px" height="20px">&nbsp;&nbsp;财务经理审批</h2></legend>';
             				financialHtml += '<hr style="width:100%"/>';
             				financialHtml += '<table id="financialTb" cellpadding="0" cellspacing="0"  align="left">';
             				//添加审核内容
             				financialHtml += '<tr><td>意见：</td><td>'+dataFinancial.approvalResult+'</td>';
             				var approvalSuggest=dataFinancial.approvalSuggest=="null"||dataFinancial.approvalSuggest==null?"":dataFinancial.approvalSuggest;
             				financialHtml += '</tr><tr><td>审核意见或建议：</td><td>'+approvalSuggest+'</td></tr>';
             				financialHtml += '<tr><td>审批人：</td><td>'+dataFinancial.approvalPersonName+'</td></tr>';
             				financialHtml += '<tr><td>审批时间：</td><td>'+getSmpFormatDateByLong(dataFinancial.operTime,false)+'</td></tr>';
             				financialHtml += '</table></fieldset>';
             				$("#financialDiv").html(financialHtml);
             				$(".l-dialog-buttons").remove();
             				}
             			 });
             		}
             	}else{
             		 JSWK.clewBox(data,'clew_ico_fail',failShowTime);
             	}
             	
				}, function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
	
			 
		},
		f_save : function() {
			var url = root +'/approveInfo/addApproval.action';
			var stepNp = $("#stepNo").val();//操作步骤
			var id = $("#id").val();
			var applyStatus ;//申请状态
			var approvalResult = $("input[name='approvalResult']:checked").val();
			var approvalSuggest = $("#approvalSuggest").val();
			var params = {
					'model.rechargeApplyId' : id,
					'model.approvalSuggest' : approvalSuggest,
					'model.approvalResult' : approvalResult,
					'model.operTime': new Date().getTime()
			};
			//先通过ID查找扣款步骤号是否==4301 或者 4302
			JAjax(root + '/payment/queryById.action', {"id":id}, 'json', function(payData){ 
				if(payData.stepNo=='4301'||payData.stepNo=='4302'){
			
			//修改扣款操作步骤号和当前处理人,如果成功则将数据添加到线下交易申请详情表
				if(approvalResult=='不同意'){
						if(approvalSuggest==null||approvalSuggest==""){
							parent.window.paymentList.alert('请填写处理意见', 'clew_ico_fail');
							return false;
						}else{
							JAjax(url, params, 'json', function(data) {
								if (data) {
									if(data.message=='操作成功'){
										if((stepNp =='4301'||stepNp =='4302')&& approvalResult=='同意'){
											if(stepNp=='4301'){
											stepNp='4302';
											applyStatus='4101';
											}else{
												stepNp='4303';
												applyStatus='4102';
											}
										}else{
											stepNp='4303';
											applyStatus = '4103';
										}
										var url = root + '/payment/editStepNp.action';
										params = {'model.id':id,'model.stepNo':stepNp,'model.applyStatus':applyStatus};
										JAjax(url,params,'json',function(data){
											if(data){
												parent.window.paymentList.cache.grid.loadData();
												parent.window.paymentList.alert('操作成功！', 'clew_ico_succ');
												parent.window.paymentList.cache.dialog.close();
											}else{
												parent.window.paymentList.alert('处理失败', 'clew_ico_fail');
											}
										});
											
										};
								} else {
									parent.window.PaymentList.alert('处理失败', 'clew_ico_fail');
				
								}
							}, function() {
								JSWK.clewBox('提交数据时发生异常', 'clew_ico_fail', failShowTime);
							}, true);
						}
				}else{
					JAjax(url, params, 'json', function(data) {
						if (data) {
							if(data.message=='操作成功'){
								if((stepNp =='4301'||stepNp =='4302')&& approvalResult=='同意'){
									if(stepNp=='4301'){
									stepNp='4302';
									applyStatus='4101';
									}else{
										
										stepNp='4303';
										applyStatus='4102';
									}
								}else{
									stepNp='4303';
									applyStatus = '4103';
								}
								var url = root + '/payment/editStepNp.action';
								params = {'model.id':id,'model.stepNo':stepNp,'model.applyStatus':applyStatus};
								JAjax(url,params,'json',function(data){
									if(data){
										parent.window.paymentList.cache.grid.loadData();
										parent.window.paymentList.alert('操作成功！', 'clew_ico_succ');
										parent.window.paymentList.cache.dialog.close();
									}else{
										parent.window.paymentList.alert('处理失败', 'clew_ico_fail');
									}
								});
									
								};
						} else {
							parent.window.PaymentList.alert('处理失败', 'clew_ico_fail');
		
						}
					}, function() {
						JSWK.clewBox('提交数据时发生异常', 'clew_ico_fail', failShowTime);
					}, true);
				}
				 }else{
					 parent.window.paymentList.cache.dialog.close();
					 parent.window.PaymentList.alert('不能重复审批', 'clew_ico_fail');
				 }
			 });
		}
	};
	$(document).ready(function() {
		window.paymentDispose = new PaymentDispose();
	});
})();