
(function() {
	var CommonUserRiskLimitAmount = function() {
		this.init();
	};
	CommonUserRiskLimitAmount.prototype = {
		config : {
			addUrl : root + '/account/saveAccountRiskRule.action',
			queryRiskRuleUrl : root + '/account/queryRiskRuleByAccNo.action',
			subHeight:subHeight, 
			subWidth:subWidth
		},
		cache : {
			form : null,
			dialog_001 : null,
			imgUrlArr:[],
			imgUrl : null,
			money : 10000000,
			voucherFileName:null,
			type : '',
			id : ''
		},
		init : function(){
			this.render();
			this.initComboBox();
			this.selectLastValue();
		},
		notNull : function(val) {
			if (val != null && val != '' && val != 'undefined') {
				return true;
			} else {
				return false;
			}
		},
		selectLastValue : function() {
			var _this = this;
			var url = _this.config.queryRiskRuleUrl;
			var params = {};
			params['model.acc_no'] = parent.window.accountList.cache.accountNo;
			
			JAjax(url, params, 'json', function(data){
				if (data != null && data != '') {
					_this.cache.type = 'modify';
					_this.cache.id = data.id;
					if (_this.notNull(data.acc_deduct_once)) {
						$("#deduct_limit_once").ligerGetComboBoxManager().selectValue(data.acc_deduct_once);
					}
					if (_this.notNull(data.acc_deduct_daily)) {
						$("#deduct_limit_daily").ligerGetComboBoxManager().selectValue(data.acc_deduct_daily);
					}
					if (_this.notNull(data.acc_deduct_times_daily)) {
						$("#deduct_limit_daily_times").ligerGetComboBoxManager().selectValue(data.acc_deduct_times_daily);
					}
					if (_this.notNull(data.acc_withdraw_once)) {
						$("#withdraw_limit_once").ligerGetComboBoxManager().selectValue(data.acc_withdraw_once);
					}
					if (_this.notNull(data.acc_withdraw_daily)) {
						$("#withdraw_limit_daily").ligerGetComboBoxManager().selectValue(data.acc_withdraw_daily);
					}
					if (_this.notNull(data.acc_withdraw_times_daily)) {
						$("#withdraw_limit_daily_times").ligerGetComboBoxManager().selectValue(data.acc_withdraw_times_daily);
					}
					if (_this.notNull(data.acc_transfer_once)) {
						$("#transfer_limit_once").ligerGetComboBoxManager().selectValue(data.acc_transfer_once);
					}
					if (_this.notNull(data.acc_transfer_daily)) {
						$("#transfer_limit_daily").ligerGetComboBoxManager().selectValue(data.acc_transfer_daily);
					}
					if (_this.notNull(data.acc_transfer_times_daily)) {
						$("#transfer_limit_daily_times").ligerGetComboBoxManager().selectValue(data.acc_transfer_times_daily);
					}
					if (_this.notNull(data.acc_risk_status)) {
						$("#acc_risk_status").ligerGetComboBoxManager().selectValue(data.acc_risk_status);
					}
				}
			});
			
		},
		
		initComboBox:function(){
			var w = '132';
			//deduct
			MALQ_CODE.getSelectByCodeType($("#deduct_limit_once"), "ACC_DEDUCT_ONCE", w, null);
			$("#deduct_limit_once").ligerComboBox({width : w});
			$("#deduct_limit_once").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#deduct_limit_daily"), "ACC_DEDUCT_DAILY", w, null);
			$("#deduct_limit_daily").ligerComboBox({width : w});
			$("#deduct_limit_daily").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#deduct_limit_daily_times"), "ACC_DEDUCT_TMES_D", w, null);
			$("#deduct_limit_daily_times").ligerComboBox({width : w});
			$("#deduct_limit_daily_times").ligerGetComboBoxManager().selectValue('');
			
			//withdraw
			MALQ_CODE.getSelectByCodeType($("#withdraw_limit_once"), "ACC_TX_ONCE", w, null);
			$("#withdraw_limit_once").ligerComboBox({width : w});
			$("#withdraw_limit_once").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#withdraw_limit_daily"), "ACC_TX_DAILY", w, null);
			$("#withdraw_limit_daily").ligerComboBox({width : w});
			$("#withdraw_limit_daily").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#withdraw_limit_daily_times"), "ACC_TX_TIMES_D", w, null);
			$("#withdraw_limit_daily_times").ligerComboBox({width : w});
			$("#withdraw_limit_daily_times").ligerGetComboBoxManager().selectValue('');
			
			//transfer
			MALQ_CODE.getSelectByCodeType($("#transfer_limit_once"), "ACC_TRANS_ONCE", w, null);
			$("#transfer_limit_once").ligerComboBox({width : w});
			$("#transfer_limit_once").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#transfer_limit_daily"), "ACC_TRANS_DAILY", w, null);
			$("#transfer_limit_daily").ligerComboBox({width : w});
			$("#transfer_limit_daily").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#transfer_limit_daily_times"), "ACC_TRANS_TMES_D", w, null);
			$("#transfer_limit_daily_times").ligerComboBox({width : w});
			$("#transfer_limit_daily_times").ligerGetComboBoxManager().selectValue('');			
			
			MALQ_CODE.getSelectByCodeType($("#acc_risk_status"), "RISK_STATUS", w, null);
			$("#acc_risk_status").ligerComboBox({width : w});
			$("#acc_risk_status").ligerGetComboBoxManager().selectValue('');		
		},
		
		packAmountInfo : function(params) {
			params['model.acc_deduct_once'] = $("#deduct_limit_once").ligerGetComboBoxManager().getValue();
			params['model.acc_deduct_daily'] = $("#deduct_limit_daily").ligerGetComboBoxManager().getValue();
			params['model.acc_deduct_times_daily'] = $("#deduct_limit_daily_times").ligerGetComboBoxManager().getValue();
			params['model.acc_withdraw_once'] = $("#withdraw_limit_once").ligerGetComboBoxManager().getValue();
			params['model.acc_withdraw_daily'] = $("#withdraw_limit_daily").ligerGetComboBoxManager().getValue();
			params['model.acc_withdraw_times_daily'] = $("#withdraw_limit_daily_times").ligerGetComboBoxManager().getValue();
			params['model.acc_transfer_once'] = $("#transfer_limit_once").ligerGetComboBoxManager().getValue();
			params['model.acc_transfer_daily'] = $("#transfer_limit_daily").ligerGetComboBoxManager().getValue();
			params['model.acc_transfer_times_daily'] = $("#transfer_limit_daily_times").ligerGetComboBoxManager().getValue();
			params['model.acc_risk_status'] = $("#acc_risk_status").ligerGetComboBoxManager().getValue();	
			params['model.acc_no'] = parent.window.accountList.cache.accountNo;
			return params;
		},
		render : function(){
			var _this = this;
		    //初始化页面检验规则
		    $.metadata.setType('attr', 'validate');
	        $('#riskForm').validate({
	            submitHandler: function(){
	            	//新增，验证是否提交全部为空
	            	if (_this.cache.type != 'modify') {
	            		if (!_this.hasValue()) {
	            			JSWK.clewBox('数据不能全部为空','clew_ico_fail',failShowTime);
	            			return;
	            		}
	            	}
	            	showMask();
	            	var url = _this.config.addUrl + "?type=" + _this.cache.type;
	            	var params = {};
	            	params = _this.packAmountInfo(params);
	            	if (_this.cache.type == 'modify') {
	            		params['model.id'] = _this.cache.id;
	            	} 
                     JAjax(url, params, 'json', function(data){
                    	 hideMask();
                      	if(data){
                      		parent.window.accountList.cache.grid.loadData();
                      		parent.window.accountList.alert('操作成功！', 'clew_ico_succ');
                      		parent.window.accountList.cache.dialog.close();                		
                      	}else{
                      		 JSWK.clewBox(data,'clew_ico_fail',failShowTime);
                      	}
         			 }, function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
	            }
	        });
		},
		hasValue : function() {
			var valCount = 0;
			$('input[type=text]').each(function(i){
				var input = $(this);
				var val = $('#' + input.attr('id')).ligerGetComboBoxManager().getValue()
				if (val != '' && val != null && val != 'undefined') {
					valCount++;
				}
			});
			return ((valCount > 0) ? true : false);
		},
		f_save:function(){
			$('#riskForm').submit();
		},
		reset : function(){
			 if($("#id").val()==''){
				 window.location='rechargeAdd.jsp';
			 }else{
			 }
			
		 },
		
		success : function(item, dialog) {
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

		window.commonUserRiskLimitAmount = new CommonUserRiskLimitAmount();
		Jid("limitGuideLine").style.display = 'block';
	});
})();

