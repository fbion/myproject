/**
 * 账户管理-中交账户-充值
 */
(function() {
	var ZJRecharge = function() {
		this.init();
	};
	ZJRecharge.prototype = {
		config : {
			rechargeUrl : root + '/ZJAccount/recharge.action',
			queryObjectUrl : root + '/ZJAccount/getDetailById.action',
			getStoreCodeUrl:root+'/ZJAccount/getStoreCode.action',
			subHeight : subHeight,
			subWidth : subWidth,
		},
		cache : {
			accordion : null,
			tab : null,
			dialog : null
		},
		init : function() {
			this.render();
			this.loadCss();
			this.initData();
			this.submitData();
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
			var that = window.zjrecharge;
			if (that.cache.tab) {
				that.cache.tab.addHeight(options.diff);
			}
			if (that.cache.accordion && options.middleHeight - 24 > 0) {
				that.cache.accordion.setHeight(options.middleHeight - 24);
			}
		},
		loadCss : function(){
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '中交账户-充值', imgUrl);
			$('#step1').css('width',(parent.window.zjaccountList.config.subWidth-100));
			$('form').ligerForm();
			$('#form1').removeAttr("class");
			$('#bankCardType').val('DEBIT');
			var w = 180;
			MALQ_CODE.getSelectByCodeType($("#bank"), "RECHARGE", w, null);
			$("#bank").ligerGetComboBoxManager().selectValue('');
			$("#bank").ligerComboBox({
				isMultiSelect: false,
				onSelected:function(value,text){
					$("#bankCardCode").val(value);
					$("#bankCardName").val(text);
				},
			});
		},
		// 添加用户信息
		suerDialog:function (){	
			var that = this;
			that.cache.dialog = MALQ_UI.open_button_03('suerPage.jsp', 200, 300,that.rechargeSuccess,null,'已完成支付',null,'支付确认' );
		},
		rechargeSuccess:function(item, dialog){
			parent.window.zjaccountList.refreshGrid();
			dialog.close();
			parent.window.zjaccountList.cache.dialog.close();
		},
		initData:function (){
			var that=this;
			var id = parent.window.zjaccountList.cache.id;
			JAjax(that.config.queryObjectUrl, {'id' : id}, 'json', function(data) {
				if (data) {
					$("#userId").val(data.id);
					$('#accountNo').val(data.insideAccountNo);
				}
			}, function() {JSWK.clewBox('查询详情失败', 'clew_ico_fail', failShowTime);}, true);
			JAjax(that.config.getStoreCodeUrl, null, 'json', function(data) {
				if (data) {
					$('#storeCode').val(data);
				}
			}, function() {JSWK.clewBox('查询详情失败', 'clew_ico_fail', failShowTime);}, true);
		},
		submitData:function (){
			var that = this;
		    // 初始化页面检验规则
		    $.metadata.setType('attr', 'validate');
	        $('#form1').validate({
	        	errorPlacement: function (lable, element){
	        		CSM_VALIDATE.errorPlacement(lable, element);
		        },
	            success: function (lable){
	            	CSM_VALIDATE.success(lable);
	            },
	             submitHandler: function(){
	            	 
	            	var form = $("#form1").serializeArray(); 
	                JAjax(that.config.rechargeUrl, form, 'json', function(data){ 
	                	if(data && data.data=="1" && data.url){
	                			  document.forms[0].target = "_blank";
    	      	            	  document.forms[0].action = data.url;
    	  					      document.forms[0].submit();
    	  					    that.suerDialog();     	                		  
	                	}else{
	                		 JSWK.clewBox(data.error,'clew_ico_fail',failShowTime);
	                	}
	                	
					}, function() {JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
	             } 
	        });
		},
		f_save:function(){
			$("#form1").submit();
		},
		success_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_succ');
		},
		fail_alert : function(data) {
			JSWK.clewBox(data, 'clew_ico_fail', failShowTime);
		}
	};

	$(document).ready(function() {
		window.zjrecharge = new ZJRecharge();
	});
})();