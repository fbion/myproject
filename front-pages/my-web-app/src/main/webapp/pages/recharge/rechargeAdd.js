/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var RechargeAdd = function() {
		this.init();
	};
	RechargeAdd.prototype = {
		config : {
			addUrl : root + '/recharge/add.action',
			modeifyUrl : root + '/recharge/modify.action',
			queryObjUrl : root + '/recharge/getById.action',
			converNum2Str:root+'/recharge/convert.action' ,
			queryById : root + '/recharge/queryById.action',
			uploadUrl:root+'/recharge/upload.action',
			queryVoucher:root+'/recharge/queryVoucher.action',
			subHeight:subHeight, 
			subWidth:subWidth
		},
		cache : {
			form : null,
			dialog_001 : null,
			imgUrlArr:[],
			imgUrl : null,
			money : 10000000,
			voucherFileName:null
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
			var w = 180;
			MALQ_CODE.getSelectByCodeType($("#remitterBankCode"), "AUTH", w, null);
			$("#remitterBankCode").ligerGetComboBoxManager().selectValue('');
			//3.初始化付款方式
			MALQ_CODE.getSelectByCodeType($("#payType"), "OFFLINE_PAY_TYPE", w, null);
			$("#payType").ligerComboBox({width : w});
			$("#payType").ligerGetComboBoxManager().selectValue('');
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
	            	remitterBankCode:{
	            		required: true, 
	            		minlength:1
	            	},
	            	payType:{
	            		required:true, 
	            		minlength:1
	            	},
	            	tradeAmount:{
	            		number:true,
	            		required:true,
	        		 },
	        		 remitterBankcardNo:{
	        			 number:true,
	        			 required:true
	        		 },
	        		 payeeNo:{
	        			 number:true,
	        			 required:true
	        		 },
	        		 insideAccountNo:{
	        			 required : true,
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
	            	 //自动添加汇款金额大写
	                 var remitterName = $('#remitterName').val(); //付款人
	                 var remitterBankCode = $("#remitterBankCode").ligerGetComboBoxManager().getValue();//付款人开户行
	                 var remitterBankcardNo = $('#remitterBankcardNo').val();//付款人账号
	                 var payeeName = $('#payeeName').val();//收款人
	                 var payeeNo = $("#payeeNo").val();//收款人账号
	                 var tradeType = '4001';//充值类型   4001充值   4002 扣款
	                 var amountArriveTime = date2stamp($('#amountArriveTime').val());//将记账日期转换为时间戳
	                 var tradeAmount = $('#tradeAmount').val();//汇款金额小写
	                 var tradeAmountUpper = $('#tradeAmountUpper').val();//汇款金额大写
	                 var description = $("#description").val();///用途
	                 var insideAccountNo = $("#insideAccountNo").val();//账户编码
	                 var applyStatus = '4100';//申请状态,4100已申请
	                 var stepNo = '4304';//申请步骤号  4302财务审批 4304出纳到款确认
	                 var payType =  $("#payType").ligerGetComboBoxManager().getValue();//付款方式
	                 var applyTime = new Date().getTime();//充值申请时间
	                 var ownerUserNo = $("#ownerUserNo").val();
//	                 if(parseInt(tradeAmount)<that.cache.money){
//	                	 stepNo = '4304';
//	                 }else{
//	                	 stepNo = '4302';
//	                 }
	                 var voucherFileName=$("#voucherFileName").val();
	                 var params = {
		            'model.remitterName': remitterName,
	     			'model.remitterBankCode': remitterBankCode,
	     			'model.remitterBankcardNo': remitterBankcardNo,//付款人账号
	     			'model.payeeName':payeeName ,//收款人
	     			'model.payeeNo': payeeNo,//收款人账号
	     			'model.tradeType' : tradeType,//充值类型   4001充值   4002 扣款
	     			'model.amountArriveTime': amountArriveTime,//记账日期
	     			'model.tradeAmount': tradeAmount,//汇款金额小写
	     			'model.tradeAmountUpper': tradeAmountUpper,//汇款金额大写
	     			'model.description' : description,//用途
	     			'model.storeId' : insideAccountNo,//账户编码
	     			'model.applyStatus': applyStatus,//申请状态,4100已申请
	     			'model.stepNo': stepNo,//申请步骤号
	     			'model.applyTime' : applyTime, //申请日期
	     			'model.payType' : payType,
	     			'model.voucherFileName':voucherFileName,
	     			'model.insideAccountNo':insideAccountNo,
	     			'model.ownerUserNo' : ownerUserNo
		         	};
	                 if(window.rechargeAdd.cache.imgUrl==null){
	                	 JSWK.clewBox("请上传汇款凭证",'clew_ico_fail',failShowTime);
	                	 hideMask();
	                	 return ;
	                 }
	                 var url;
	                 if($("#id").val()==''){
	                	 url = that.config.addUrl;  
	                 }else{
	                	 params['model.id'] = $("#id").val();
	                	 url = that.config.modeifyUrl;
	                 }
                      JAjax(url, params, 'json', function(data){   
                    	hideMask();
                      	if(data){           
                      		parent.window.queryList.cache.grid.loadData();
                      		parent.window.queryList.alert('操作成功！', 'clew_ico_succ');
                      		parent.window.queryList.cache.dialog.close();                		
                      	}else{
                      		 JSWK.clewBox(data,'clew_ico_fail',failShowTime);
                      	}
                      	
         				}, function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
	            }
	        });
		},
		f_save:function(){
			$('#form1').submit();
		},
		initAddOrEdit:function(){
			var that = this;
		    //取值判断是增加还是修改
	        var pageId =parent.window.queryList.cache.subPageId; 
	        //取值判断是增加还是修改
	        if(pageId == 'edit'){
	        	var id = parent.window.queryList.cache.id;
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
		 showImg : function(){
				var that = window.rechargeAdd;
				var url = that.cache.imgUrl;
				parent.window.queryList.showImg(url);
		},
		 loadCss : function(){
			 	var imgUrl = '../../images/spanIoc.png';		 
				MALQ_CODE.titleBlock($('#step1'), '付款人信息', imgUrl);
				$('#step1').css('width',(parent.window.queryList.config.subWidth-100));
				MALQ_CODE.titleBlock($('#step2'), '收款人信息', imgUrl);
				$('#step2').css('width',(parent.window.queryList.config.subWidth-100));
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
			var that = window.rechargeAdd;
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
		window.rechargeAdd = new RechargeAdd();
	});
})();
//文件上传后的回调函数
function uploadCallback(data){
	hideMask();
	if(data != '0'){
		window.rechargeAdd.success_alert('上传汇款凭证成功');
		window.rechargeAdd.cache.imgUrlArr.push({name:'voucherFileName',value:data});
		window.rechargeAdd.cache.imgUrl = data;
		window.rechargeAdd._uploadFile();
		window.rechargeAdd.cache.dialog_001.close();
	}else{
		JSWK.clewBox('上传汇款凭证失败', 'clew_ico_fail', failShowTime);
	}
}