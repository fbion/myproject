/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var ReceiptRechargeAdd = function() {
		this.init();
	};
	ReceiptRechargeAdd.prototype = {
		config : {
			addUrl : root + '/receipt/add.action',
			modeifyUrl : root + '/receipt/modify.action',
			queryObjUrl : root + '/receipt/getById.action',
			converNum2Str:root+'/recharge/convert.action' ,
			queryById : root + '/receipt/queryById.action',
			uploadUrl:root+'/receipt/upload.action',
			queryVoucher:root+'/recharge/queryVoucher.action',
			findUser : root + '/receipt/findUser.action',
			subHeight:subHeight, 
			subWidth:subWidth
		},
		cache : {
			form : null,
			dialog_001 : null,
			imgUrlArr:[],
			imgUrl : null,
			voucherFileName:null,
			money : 10000000,
			imgUrl1 : null,
			voucherFileName1 : null
		},
		init : function(){
			this.initSelectData();
			this.initAddOrEdit();
			this.loadCss();
			this.render();
			this.initPageData();
		},
		//初始化银行选择框
		initSelectData:function(){
			var that = this;
			var w = 160;
			//3.初始化付款方式
//			MALQ_CODE.getSelectByCodeType($("#payType"), "OFFLINE_PAY_TYPE", 130, null);
//			$("#payType").ligerComboBox({width : 130});
//			$("#payType").ligerGetComboBoxManager().selectValue('');
			//取当前登录用户名
			//发送请求取得当前登录用户
			JAjax(that.config.findUser, null, '', function(data){
				$("#applyName").val(data);
			});
			//所属地区 
			MALQ_CODE.getCodeSelect_p($("#applyPersonPost"),w,null);
		},
		//初始化账户编码选择框
		initPageData : function (){
			 var that = this;
			 $('#insideAccountNo').ligerComboBox({
	               onBeforeOpen: that._selectStore, 
	               valueFieldID: 'storeText001',width:180
	         });
			 $('#insideAccountNo').ligerGetComboBoxManager();		
		 },
		 //初始化点击商户编码input框时打开新页面
		 _selectStore:function(){	
			 var that=rechargeAdd;
			 var url = 'merchant-list.jsp';
			 var title = '账户管理-普通账户列表';
			 that.cache.dialog_001 = MALQ_UI.open_button(url, subHeight+20, subWidth+200, that.f_selectStoreOK, 
					 function(item, dialog){dialog.close();},
					 title);
			 return false;
		 },
		 //点击商户编码操作
		 f_selectStoreOK: function (item, dialog){
			 var fn = dialog.frame.accountList.f_select || dialog.frame.window.accountList.f_select; 
	         var data = fn();
	         if(data){
	        	 var insideAccountNo='';
	        	 var ownerUserNo = '';
	        	 $(data.ids).each(function(){
	        		 insideAccountNo= this.id;
	        		 ownerUserNo = this.ownerUserNo;
	        	 });
	        	 $('#insideAccountNo').val(insideAccountNo?insideAccountNo:"");
	        	 $('#ownerUserNo').val(ownerUserNo?ownerUserNo:"");
	        	 dialog.close();
	         }        
	     },
		 //点击汇款金额小写完成时，自动填充汇款金额大写
		 convert:function(){
				var that = this;
				$("#tradeAmountUpper").val('');
				var tradeAmount=$("#tradeAmount").val();
				if(tradeAmount!=''&&!isNaN(tradeAmount)){
					JAjax(that.config.converNum2Str, {name:'equal[tradeAmount]',value:tradeAmount}, '', function(data){
						$("#tradeAmountUpper").val(data+'整');
					});
				}
			},
		render : function(){
			var that = this;
		    //初始化页面检验规则
		    $.metadata.setType('attr', 'validate');
	        $('#form1').validate({
	            rules: {
//	            	payType:{
//	            		required:true, 
//	            	//	minlength:1
//	            	},
	            	tradeAmount:{
	            		number:true,
	            		required:true,
	        		 },
	        		 customerName:{
	        			 required:true, 
	        		 },
	        		 remitterName:{
	        			 required:true
	        		 }
	        	},
	        	messages: {
	        		tradeAmount:{remote: '不能为空'}
	        	},
	        	errorPlacement: function (lable, element){
	        		CSM_VALIDATE.errorPlacement(lable, element);
		        },
	            success: function (lable){
	            	CSM_VALIDATE.success(lable);
	            },
	            submitHandler: function(){
	            	showMask();
	            	 var voucherFileName = "";//因数据库字段只有一个，所以讲下面两个地址都放入此字段。
	            	 var image1 = "";//汇款凭证地址
	            	 var image2 = "";//油卡分配凭证
	            	 var src1 = $("#payPicId_file1").find('img').first();
	            	 var src2 = $("#payPicId_file2").find('img').first();
	            	 var fileSrc = ".sinoiov.";
            	   	 if(src1.length==0){
            			 hideMask();
            			 JSWK.clewBox("请上传汇款凭证",'clew_ico_fail',failShowTime);
            			 $("#accountPay").removeAttr("disabled");
            			 return 
            		 }else if(src1[0].src.indexOf(fileSrc)>=0){
            			 image1 = src1[0].src;//凭证图片地址
            		 }else{
            			 JSWK.clewBox("请按要求上传汇款凭证",'clew_ico_fail',failShowTime);
            			 hideMask();
            			 return
            		 }
            	   	 if(src2.length==0){
            			 hideMask();
            			 JSWK.clewBox("请上传油卡分配凭证",'clew_ico_fail',failShowTime);
            			 return 
            		 }else if(src2[0].src.indexOf(fileSrc)>=0){
            			 image2 = src2[0].src;//凭证图片地址
            		 }else{
            			 JSWK.clewBox("请按要求上传油卡分配凭证",'clew_ico_fail',failShowTime);
            			 hideMask();
            			 return
            		 }
            	   	 voucherFileName = image1+","+image2;
            	   	 var customerName = $("#customerName").val();
	                 var stepNo = '4304';//申请步骤号  4302财务审批 4304出纳到款确认
	              //   var payType =  $("#payType").ligerGetComboBoxManager().getValue();//付款方式
	                 var applyPersonPost = $("#applyPersonPost").ligerGetComboBoxManager().getValue();//所属地区
	                 var remitterName = $("#remitterName").val();//付款人
	                 var tradeAmount = $("#tradeAmount").val();
	                 var params = {
		            'model.customerName': customerName,
		            'model.stepNo': stepNo,
		     //       'model.payType': payType,
		            'model.applyPersonPost': applyPersonPost,
		            'model.remitterName': remitterName,
		            'model.tradeAmount': tradeAmount,
		            'model.voucherFileName':voucherFileName
		         	};
	                 
	                 var url;
	                 if($("#id").val()==''){
	                	 url = that.config.addUrl;  
	                 }else{
	                	 params['model.id'] = $("#id").val();
	                	 url = that.config.modeifyUrl;
	                 }
                      JAjax(url, params, 'json', function(data){   
                      	if(data){    
                      		hideMask();
                      		parent.window.receiptList.cache.grid.loadData();
                      		parent.window.receiptList.alert('操作成功！', 'clew_ico_succ');
                      		parent.window.receiptList.cache.dialog.close();                		
                      	}else{
                      		 hideMask();
                      		 JSWK.clewBox(data,'clew_ico_fail',failShowTime);
                      	}
                      	
         				}, function(){hideMask();JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
	            }
	        });
		},
		f_save:function(){
			$('#form1').submit();
		},
		initAddOrEdit:function(){
			var that = this;
		    //取值判断是增加还是修改
	        var pageId =parent.window.receiptList.cache.subPageId; 
	        //取值判断是增加还是修改
	        if(pageId == 'edit'){
	        	var id = parent.window.receiptList.cache.id;
	        	$("#id").val(id);
        	    JAjax(that.config.queryById,{'id':id}, 'json', function(data){
        		   if(data){
						$('#remitterName').val(data.remitterName);//付款人
					    $('#remitterBankcardNo').val(data.remitterBankcardNo);//银行卡号
					    $('#payeeName').val(data.payeeName);//收款人名称
					    $('#payeeNo').val(data.payeeNo);//收款人账号
					    $('#amountArriveTime').val(getSmpFormatDateByLong(data.amountArriveTime,false));//记账日期	
					    $('#tradeAmount').val(data.tradeAmount);//汇款金额小写	
					    $('#tradeAmountUpper').val(data.tradeAmountUpper);	//汇款金额大写
					    $('#description').val(data.description);	//用途
					    $('#insideAccountNo').val(data.storeId);	//账户编码
					    $('#ownerUserNo').val(data.ownerUserNo);//会员编码
					    //开户行
					    var w = 140;
						MALQ_CODE.getSelectByCodeType($("#remitterBankCode"), "AUTH", w, null);
						$("#remitterBankCode").ligerGetComboBoxManager().selectValue(data.remitterBankCode);//开户行
						MALQ_CODE.getSelectByCodeType($("#payType"), "OFFLINE_PAY_TYPE", w, null);
						$("#payType").ligerGetComboBoxManager().selectValue(data.payType);//开户行
        		   } 
        	   }, function(){JSWK.clewBox('查询充值数据失败','clew_ico_fail',failShowTime);}, true);  
        	   //查询凭证数据
        	    JAjax(that.config.queryVoucher,{'applyId':id}, 'text', function(data){
         		   if(data){
         			  $("#voucherFileName").val(data); 
         			 $("#passImg_img").attr("src",data);
 					 $("#imgHref").show();
         		   } 
         	   }, function(){JSWK.clewBox('查询充值数据失败','clew_ico_fail',failShowTime);}, true); 
        	    
			}
		 },
		 queryBigImg:function(){
			var o=document.getElementById("passImg_img");
			showBigImg(o);
		 },
		 showImg : function(type){
			 	var that = this;
			 	var url = "";
				var that = window.rechargeAdd;
				if(type=="1"){
					url = that.cache.imgUrl;
				}else{
					url = that.cache.imgUrl1;
				}
				parent.window.receiptList.showImg(url);
		},
		 loadCss : function(){
			 	var imgUrl = '../../images/spanIoc.png';		 
				MALQ_CODE.titleBlock($('#step1'), '小票充值申请', imgUrl);
				$('#step1').css('width',(parent.window.receiptList.config.subWidth-100));
				MALQ_CODE.titleBlock($('#step2'), '上传凭证:图片大小不超过3M，每种限上传1张，支持JPG、JPEG、PNG格式', imgUrl);
				$('#step2').css('width',(parent.window.receiptList.config.subWidth-100));
				$(".l-dialog-buttons").remove();
				$('form').ligerForm();
		 },
		 reset : function(){
			 if($("#id").val()==''){
				 window.location='rechargeAdd.jsp';
			 }else{
				 initAddOrEdit();
			 }
			
		 },
		 upload : function() {
			var that = window.receiptRechargeAdd;
			that.cache.subPageId = 'upload';
			var title = '上传汇款凭证';
			var url = 'rechargeUpload.jsp';
			that.cache.dialog_001 = MALQ_UI.open_button(url, subHeight, subWidth, that.success, that.cancel, title);
		 },
		_uploadFile:function(){
			var that=this;
			$.each(that.cache.imgUrlArr,function(idx,item){   
				  $("#voucherFileName").val(item.value); 
				  $("#passImg_img").attr("src",item.value);
				  $("#imgHref").show();
			});
		},
		
		success : function(item, dialog) {
			//dialog.frame.window.rechargeUpload.upload();
			dialog.frame.window.rechargeUpload.uploadComplate();
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
		var uploadUrl = '/receipt/upload.action';
		// 上传凭证图片
		var headPicImg1 = new CtfoFileUpload({
			containerId : 'payPicId_file1',
			isAutoRender : true,
			uploadUrl: uploadUrl,
			fileName : 'payImg1'
		});
		headPicImg1.init();
		var headPicImg2 = new CtfoFileUpload({
			containerId : 'payPicId_file2',
			isAutoRender : true,
			uploadUrl: uploadUrl,
			fileName : 'payImg2'
		});
		headPicImg2.init();
		window.receiptRechargeAdd = new ReceiptRechargeAdd();
	});
})();
