/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2015年1月16
 */
(function() {
	var RiskRuleAdd = function() {
		this.init();
	};
	RiskRuleAdd.prototype = {
		config : {
			addUrl : root + '/riskmanager/saveRiskRule.action',
			modeifyUrl : root + '/riskmanager/updateRiskRule.action',
			lastValueUrl : root + '/riskmanager/queryRiskRule.action',
			checkRepeatUrl : root + '/riskmanager/isRepeat.action',
			getRuleSettedUrl : root + '/riskmanager/queryRiskRuleSetting.action',
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
			ruleSettedVar : null
		},
		init : function(){
			this.render();
			this.initComboBox();
			this.selectLastValue();
			this.getRuleSettedVar();
			this.showSpecRiskType();
		},
		notNull : function(val) {
			if (val != null && val != '' && val != 'undefined') {
				return true;
			} else {
				return false;
			}
		},
		//获取上次风控规则设置的情况
		getRuleSettedVar : function() {
			var _this = this;
			var url = _this.config.getRuleSettedUrl;
			var params = {};	
			JAjax(url, params, 'json', function(data){
				_this.cache.ruleSettedVar = data;
			});
		},
		showSpecRiskType : function() {
			var _this = this;
			var actionType = parent.window.queryList.cache.type;
			var riskType = parent.window.queryList.cache.riskType;
			if (_this.notNull(riskType)) {
				$('#riskType').ligerGetComboBoxManager().selectValue(riskType);
				_this.showContentByRiskType(riskType);
			}
			if (_this.notNull(actionType)) {
				$('#riskType').ligerGetComboBoxManager().setDisabled(true);
			}
			
			if (actionType == 'view'){
				_this.disableSelect(riskType);
			}
		},
		
		disableSelect : function(riskType) {
			var obj = '';
			if (riskType == '1') {
				obj = $('.ip');
			} 
			if (riskType == '2') {
				obj = $('.frequency');
			}
			if (riskType == '3') {
				obj = $('.amount');
			}
			if (riskType == '4') {
				obj = $('.account');
			}			
			$.each(obj, function(i, item) {
				$(this).ligerGetComboBoxManager().setDisabled(true);
			});
		},
		selectLastValue : function() {
			var _this = this;
			var url = _this.config.lastValueUrl;
			var params = {};
			JAjax(url, params, 'json', function(data){
				if (data != null && data != '') {
					if (_this.notNull(data.ip_area)) {
						$("#ipArea").ligerGetComboBoxManager().selectValue(data.ip_area);
					}
					if (_this.notNull(data.ip_req_num)) {
						$("#ipReqDaily").ligerGetComboBoxManager().selectValue(data.ip_req_num);
					}
					if (_this.notNull(data.ip_process_method)) {
						$("#ipProcessMethod").ligerGetComboBoxManager().selectValue(data.ip_process_method);
					}
					if (_this.notNull(data.ip_risk_status)) {
						$("#ipRiskStatus").ligerGetComboBoxManager().selectValue(data.ip_risk_status);
					}
					
//					private int fre_ip_timesOfSec__times;
					if (_this.notNull(data.fre_ip_timesOfSec__times)) {
						$("#frequencyTimesSecondes_times").ligerGetComboBoxManager().selectValue(data.fre_ip_timesOfSec__times);
					}
//					private int fre_ip_timesOfSec__sec;
					if (_this.notNull(data.fre_ip_timesOfSec__sec)) {
						$("#frequencyTimesSecondes_Seconds").ligerGetComboBoxManager().selectValue(data.fre_ip_timesOfSec__sec);
					}
//					private int fre_ip_timesOfDay__times;
					if (_this.notNull(data.fre_ip_timesOfDay__times)) {
						$("#frequencyTimesDay_times").ligerGetComboBoxManager().selectValue(data.fre_ip_timesOfDay__times);
					}
//					private String 	fre_ip_timesOfDay__day;
					if (_this.notNull(data.fre_ip_timesOfDay__day)) {
						$("#frequencyTimesDay_day").ligerGetComboBoxManager().selectValue(data.fre_ip_timesOfDay__day);
					}
//					private String 	fre_ip_process_method;
					if (_this.notNull(data.fre_ip_process_method)) {
						$("#ipFreProcessMethod").ligerGetComboBoxManager().selectValue(data.fre_ip_process_method);
					}
//					
//					private int fre_acc_timesOfSec__times;
					if (_this.notNull(data.fre_acc_timesOfSec__times)) {
						$("#accountFrequencyTimesSecondes_times").ligerGetComboBoxManager().selectValue(data.fre_acc_timesOfSec__times);
					}
//					private int fre_acc_timesOfSec__sec;
					if (_this.notNull(data.fre_acc_timesOfSec__sec)) {
						$("#accountFrequencyTimesSecondes_Seconds").ligerGetComboBoxManager().selectValue(data.fre_acc_timesOfSec__sec);
					}
//					private int fre_acc_timesOfDay__times;
					if (_this.notNull(data.fre_acc_timesOfDay__times)) {
						$("#accountFrequencyTimesDay_times").ligerGetComboBoxManager().selectValue(data.fre_acc_timesOfDay__times);
					}
//					private String 	fre_acc_timesOfDay__day;
					if (_this.notNull(data.fre_acc_timesOfDay__day)) {
						$("#accountFrequencyTimesDay_day").ligerGetComboBoxManager().selectValue(data.fre_acc_timesOfDay__day);
					}
//					private String 	fre_acc_process_method;
					if (_this.notNull(data.fre_acc_process_method)) {
						$("#accFreProcessMethod").ligerGetComboBoxManager().selectValue(data.fre_acc_process_method);
					}
//					private String 	fre_risk_status;
					if (_this.notNull(data.fre_risk_status)) {
						$("#freRiskStatus").ligerGetComboBoxManager().selectValue(data.fre_risk_status);
					}
	//
//					private float amount_deduct_once;
					if (_this.notNull(data.amount_deduct_once)) {
						$("#deduct_limit_once").ligerGetComboBoxManager().selectValue(data.amount_deduct_once);
					}
//					private float amount_deduct_daily;
					if (_this.notNull(data.amount_deduct_daily)) {
						$("#deduct_limit_daily").ligerGetComboBoxManager().selectValue(data.amount_deduct_daily);
					}
//					private int amount_deduct_times_daily;
					if (_this.notNull(data.amount_deduct_times_daily)) {
						$("#deduct_limit_daily_times").ligerGetComboBoxManager().selectValue(data.amount_deduct_times_daily);
					}
//					private float amount_withdraw_once;
					if (_this.notNull(data.amount_withdraw_once)) {
						$("#withdraw_limit_once").ligerGetComboBoxManager().selectValue(data.amount_withdraw_once);
					}
//					private float amount_withdraw_daily;
					if (_this.notNull(data.amount_withdraw_daily)) {
						$("#withdraw_limit_daily").ligerGetComboBoxManager().selectValue(data.amount_withdraw_daily);
					}
//					private int amount_withdraw_times_daily;
					if (_this.notNull(data.amount_withdraw_times_daily)) {
						$("#withdraw_limit_daily_times").ligerGetComboBoxManager().selectValue(data.amount_withdraw_times_daily);
					}
//					private float amount_transfer_once;
					if (_this.notNull(data.amount_transfer_once)) {
						$("#transfer_limit_once").ligerGetComboBoxManager().selectValue(data.amount_transfer_once);
					}
//					private float amount_transfer_daily;
					if (_this.notNull(data.amount_transfer_daily)) {
						$("#transfer_limit_daily").ligerGetComboBoxManager().selectValue(data.amount_transfer_daily);
					}
//					private int amount_transfer_times_daily;
					if (_this.notNull(data.amount_transfer_times_daily)) {
						$("#transfer_limit_daily_times").ligerGetComboBoxManager().selectValue(data.amount_transfer_times_daily);
					}
//					private String 	amount_process_method;
					if (_this.notNull(data.amount_process_method)) {
						$("#amountAccProcessMethod").ligerGetComboBoxManager().selectValue(data.amount_process_method);
					}
//					private String 	amount_risk_status;
					if (_this.notNull(data.amount_risk_status)) {
						$("#amountRiskStatus").ligerGetComboBoxManager().selectValue(data.amount_risk_status);
					}
	//
//					private float acc_remain_max;
					if (_this.notNull(data.acc_remain_max)) {
						$("#accountRemainMax").ligerGetComboBoxManager().selectValue(data.acc_remain_max);
					}
//					private float acc_remain_min;
					if (_this.notNull(data.acc_remain_min)) {
						$("#accountRemainMin").ligerGetComboBoxManager().selectValue(data.acc_remain_min);
					}
//					private int acc_income_times_daily;
					if (_this.notNull(data.acc_income_times_daily)) {
						$("#accountIncomeTimesDaily").ligerGetComboBoxManager().selectValue(data.acc_income_times_daily);
					}
//					private float acc_income_amount_daily;
					if (_this.notNull(data.acc_income_amount_daily)) {
						$("#accountIncomeAmountDaily").ligerGetComboBoxManager().selectValue(data.acc_income_amount_daily);
					}
//					private int acc_outcome_times_daily;
					if (_this.notNull(data.acc_outcome_times_daily)) {
						$("#accountOutcomeTimesDaily").ligerGetComboBoxManager().selectValue(data.acc_outcome_times_daily);
					}
//					private float acc_outcome_amount_daily;
					if (_this.notNull(data.acc_outcome_amount_daily)) {
						$("#accountOutcomeAmountDaily").ligerGetComboBoxManager().selectValue(data.acc_outcome_amount_daily);
					}
//					private String 	acc_process_method;
					if (_this.notNull(data.acc_process_method)) {
						$("#accProcessMethod").ligerGetComboBoxManager().selectValue(data.acc_process_method);
					}
//					private String 	acc_risk_status;
					if (_this.notNull(data.acc_risk_status)) {
						$("#accRiskStatus").ligerGetComboBoxManager().selectValue(data.acc_risk_status);
					}
				}
			});
			
		},
		
		initComboBox:function(){
			//初始化风控指标
			var w = '133';
			MALQ_CODE.getSelectByCodeType($("#riskType"), "RISK_TYPE", w, null);
			$("#riskType").ligerComboBox({width : w});
			$("#riskType").ligerGetComboBoxManager().selectValue('');
			
			//ip地域
			MALQ_CODE.getSelectByCodeType($("#ipArea"), "IP_AREA", w, null);
			$("#ipArea").ligerComboBox({width : w});
			$("#ipArea").ligerGetComboBoxManager().selectValue('');
			
			//ip请求次数
			MALQ_CODE.getSelectByCodeType($("#ipReqDaily"), "IP_REQ_TIMES_DAILY", w, null);
			$("#ipReqDaily").ligerComboBox({width : w});
			$("#ipReqDaily").ligerGetComboBoxManager().selectValue('');
			
			//ip 纬度 处理办法
			MALQ_CODE.getSelectByCodeType($("#ipProcessMethod"), "RISK_PROCESS_METHOD", w, null);
			$("#ipProcessMethod").ligerComboBox({width : w});
			$("#ipProcessMethod").ligerGetComboBoxManager().selectValue('');
			
			//ip纬度 状态
			MALQ_CODE.getSelectByCodeType($("#ipRiskStatus"), "RISK_STATUS", w, null);
			$("#ipRiskStatus").ligerComboBox({width : w});
			$("#ipRiskStatus").ligerGetComboBoxManager().selectValue('');
			
			
			//frequency times of times/seconds for same ip
			MALQ_CODE.getSelectByCodeType($("#frequencyTimesSecondes_times"), "FRE_IP_TIMES_SEC_TIM", w, null);
			$("#frequencyTimesSecondes_times").ligerComboBox({width : w});
			$("#frequencyTimesSecondes_times").ligerGetComboBoxManager().selectValue('');
			
			//frequency seconds of times/seconds for same ip
			MALQ_CODE.getSelectByCodeType($("#frequencyTimesSecondes_Seconds"), "FRE_IP_TIMES_SEC_SEC", w, null);
			$("#frequencyTimesSecondes_Seconds").ligerComboBox({width : w});
			$("#frequencyTimesSecondes_Seconds").ligerGetComboBoxManager().selectValue('');
			
			//frequency times of times/day for same ip
			MALQ_CODE.getSelectByCodeType($("#frequencyTimesDay_times"), "FRE_IP_TIMES_D_TIMES", w, null);
			$("#frequencyTimesSecondes_Seconds").ligerComboBox({width : w});
			$("#frequencyTimesDay_times").ligerGetComboBoxManager().selectValue('');
			
			//frequency day of times/day for same ip
			MALQ_CODE.getSelectByCodeType($("#frequencyTimesDay_day"), "FRE_IP_TIMES_D_D", w, null);
			$("#frequencyTimesDay_day").ligerComboBox({width : w});
			$("#frequencyTimesDay_day").ligerGetComboBoxManager().selectValue('');
			
			//frequency process method same ip
			MALQ_CODE.getSelectByCodeType($("#ipFreProcessMethod"), "RISK_PROCESS_METHOD", w, null);
			$("#ipFreProcessMethod").ligerComboBox({width : w});
			$("#ipFreProcessMethod").ligerGetComboBoxManager().selectValue('');
			
			//frequency times of times/seconds for same account
			MALQ_CODE.getSelectByCodeType($("#accountFrequencyTimesSecondes_times"), "FRE_AC_TMES_SEC_TIM", w, null);
			$("#accountFrequencyTimesSecondes_times").ligerComboBox({width : w});
			$("#accountFrequencyTimesSecondes_times").ligerGetComboBoxManager().selectValue('');
			
			//frequency seconds of times/seconds for same account
			MALQ_CODE.getSelectByCodeType($("#accountFrequencyTimesSecondes_Seconds"), "FRE_AC_TMES_SEC_SEC", w, null);
			$("#accountFrequencyTimesSecondes_Seconds").ligerComboBox({width : w});
			$("#accountFrequencyTimesSecondes_Seconds").ligerGetComboBoxManager().selectValue('');
			
			//frequency times of times/day for same account
			MALQ_CODE.getSelectByCodeType($("#accountFrequencyTimesDay_times"), "FRE_AC_TMES_D_TIMES", w, null);
			$("#accountFrequencyTimesDay_times").ligerComboBox({width : w});
			$("#accountFrequencyTimesDay_times").ligerGetComboBoxManager().selectValue('');
			
			//frequency day of times/day for same account
			MALQ_CODE.getSelectByCodeType($("#accountFrequencyTimesDay_day"), "FRE_IP_TIMES_D_D", w, null);
			$("#accountFrequencyTimesDay_day").ligerComboBox({width : w});
			$("#accountFrequencyTimesDay_day").ligerGetComboBoxManager().selectValue('');
			
			//frequency process method same account
			MALQ_CODE.getSelectByCodeType($("#accFreProcessMethod"), "RISK_PROCESS_METHOD", w, null);
			$("#accFreProcessMethod").ligerComboBox({width : w});
			$("#accFreProcessMethod").ligerGetComboBoxManager().selectValue('');
			
			//frequency risk status
			MALQ_CODE.getSelectByCodeType($("#freRiskStatus"), "RISK_STATUS", w, null);
			$("#freRiskStatus").ligerComboBox({width : w});
			$("#freRiskStatus").ligerGetComboBoxManager().selectValue('');
			
			//AMOUNT
			//deduct
			MALQ_CODE.getSelectByCodeType($("#deduct_limit_once"), "AMT_DEDUCT_ONCE", w, null);
			$("#deduct_limit_once").ligerComboBox({width : w});
			$("#deduct_limit_once").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#deduct_limit_daily"), "AMT_DEDUCT_DAILY", w, null);
			$("#deduct_limit_daily").ligerComboBox({width : w});
			$("#deduct_limit_daily").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#deduct_limit_daily_times"), "AMT_DEDUCT_TMES_D", w, null);
			$("#deduct_limit_daily_times").ligerComboBox({width : w});
			$("#deduct_limit_daily_times").ligerGetComboBoxManager().selectValue('');
			
			//withdraw
			MALQ_CODE.getSelectByCodeType($("#withdraw_limit_once"), "AMT_TX_ONCE", w, null);
			$("#withdraw_limit_once").ligerComboBox({width : w});
			$("#withdraw_limit_once").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#withdraw_limit_daily"), "AMT_TX_DAILY", w, null);
			$("#withdraw_limit_daily").ligerComboBox({width : w});
			$("#withdraw_limit_daily").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#withdraw_limit_daily_times"), "AMT_TX_TIMES_D", w, null);
			$("#withdraw_limit_daily_times").ligerComboBox({width : w});
			$("#withdraw_limit_daily_times").ligerGetComboBoxManager().selectValue('');
			
			//transfer
			MALQ_CODE.getSelectByCodeType($("#transfer_limit_once"), "AMT_TRANS_ONCE", w, null);
			$("#transfer_limit_once").ligerComboBox({width : w});
			$("#transfer_limit_once").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#transfer_limit_daily"), "AMT_TRANS_DAILY", w, null);
			$("#transfer_limit_daily").ligerComboBox({width : w});
			$("#transfer_limit_daily").ligerGetComboBoxManager().selectValue('');
			
			MALQ_CODE.getSelectByCodeType($("#transfer_limit_daily_times"), "AMT_TRANS_TMES_D", w, null);
			$("#transfer_limit_daily_times").ligerComboBox({width : w});
			$("#transfer_limit_daily_times").ligerGetComboBoxManager().selectValue('');		
			
			//amount process method
			MALQ_CODE.getSelectByCodeType($("#amountAccProcessMethod"), "RISK_PROCESS_METHOD", w, null);
			$("#amountAccProcessMethod").ligerComboBox({width : w});
			$("#amountAccProcessMethod").ligerGetComboBoxManager().selectValue('');
			
			//amount status
			MALQ_CODE.getSelectByCodeType($("#amountRiskStatus"), "RISK_STATUS", w, null);
			$("#amountRiskStatus").ligerComboBox({width : w});
			$("#amountRiskStatus").ligerGetComboBoxManager().selectValue('');
			
			//ACCOUNT
			//账户最大值
			MALQ_CODE.getSelectByCodeType($("#accountRemainMax"), "ACC_REMAIN_MAX", w, null);
			$("#accountRemainMax").ligerComboBox({width : w});
			$("#accountRemainMax").ligerGetComboBoxManager().selectValue('');
			
			//账户最小值
			MALQ_CODE.getSelectByCodeType($("#accountRemainMin"), "ACC_REMAIN_MIN", w, null);
			$("#accountRemainMin").ligerComboBox({width : w});
			$("#accountRemainMin").ligerGetComboBoxManager().selectValue('');
			
			//账户单日进项笔数
			MALQ_CODE.getSelectByCodeType($("#accountIncomeTimesDaily"), "ACC_INCOME_TMES_DLY", w, null);
			$("#accountIncomeTimesDaily").ligerComboBox({width : w});
			$("#accountIncomeTimesDaily").ligerGetComboBoxManager().selectValue('');
			
			//账户单日进项金额
			MALQ_CODE.getSelectByCodeType($("#accountIncomeAmountDaily"), "ACC_INCOME_SUM_DAILY", w, null);
			$("#accountIncomeAmountDaily").ligerComboBox({width : w});
			$("#accountIncomeAmountDaily").ligerGetComboBoxManager().selectValue('');
			
			//账户单日出项笔数
			MALQ_CODE.getSelectByCodeType($("#accountOutcomeTimesDaily"), "ACC_OUTCOME_TMES_DLY", w, null);
			$("#accountOutcomeTimesDaily").ligerComboBox({width : w});
			$("#accountOutcomeTimesDaily").ligerGetComboBoxManager().selectValue('');
			
			//账户单日出项金额
			MALQ_CODE.getSelectByCodeType($("#accountOutcomeAmountDaily"), "ACC_OUTCOME_SUM_DLY", w, null);
			$("#accountOutcomeAmountDaily").ligerComboBox({width : w});
			$("#accountOutcomeAmountDaily").ligerGetComboBoxManager().selectValue('');
			
			//account process method
			MALQ_CODE.getSelectByCodeType($("#accProcessMethod"), "RISK_PROCESS_METHOD", w, null);
			$("#accProcessMethod").ligerComboBox({width : w});
			$("#accProcessMethod").ligerGetComboBoxManager().selectValue('');
			
			//account status
			MALQ_CODE.getSelectByCodeType($("#accRiskStatus"), "RISK_STATUS", w, null);
			$("#accRiskStatus").ligerComboBox({width : w});
			$("#accRiskStatus").ligerGetComboBoxManager().selectValue('');
			
		},
		
		packIpInfo : function(params) {
			params['model.ip_area'] = $("#ipArea").ligerGetComboBoxManager().getValue();
			params['model.ip_req_num'] = $("#ipReqDaily").ligerGetComboBoxManager().getValue();
			params['model.ip_process_method'] = $("#ipProcessMethod").ligerGetComboBoxManager().getValue();
			params['model.ip_risk_status'] = $("#ipRiskStatus").ligerGetComboBoxManager().getValue();
			params['model.ip_creator'] = INDEX_USER_NAME;
			return params;
		},
		
		packFreInfo : function(params) {
			params['model.fre_ip_timesOfSec__times'] = $("#frequencyTimesSecondes_times").ligerGetComboBoxManager().getValue();
			params['model.fre_ip_timesOfSec__sec'] = $("#frequencyTimesSecondes_Seconds").ligerGetComboBoxManager().getValue();
			params['model.fre_ip_timesOfDay__times'] = $("#frequencyTimesDay_times").ligerGetComboBoxManager().getValue();
			params['model.fre_ip_timesOfDay__day'] = $("#frequencyTimesDay_day").ligerGetComboBoxManager().getValue();
			params['model.fre_ip_process_method'] = $("#ipFreProcessMethod").ligerGetComboBoxManager().getValue();
			params['model.fre_acc_timesOfSec__times'] = $("#accountFrequencyTimesSecondes_times").ligerGetComboBoxManager().getValue();
			params['model.fre_acc_timesOfSec__sec'] = $("#accountFrequencyTimesSecondes_times").ligerGetComboBoxManager().getValue();
			params['model.fre_acc_timesOfDay__times'] = $("#accountFrequencyTimesDay_times").ligerGetComboBoxManager().getValue();
			params['model.fre_acc_timesOfDay__day'] = $("#accountFrequencyTimesDay_day").ligerGetComboBoxManager().getValue();
			params['model.fre_acc_process_method'] = $("#accFreProcessMethod").ligerGetComboBoxManager().getValue();
			params['model.fre_risk_status'] = $("#freRiskStatus").ligerGetComboBoxManager().getValue();
			params['model.fre_creator'] = INDEX_USER_NAME;
			return params;
		},
		
		packAmountInfo : function(params) {
			params['model.amount_deduct_once'] = $("#deduct_limit_once").ligerGetComboBoxManager().getValue();
			params['model.amount_deduct_daily'] = $("#deduct_limit_daily").ligerGetComboBoxManager().getValue();
			params['model.amount_deduct_times_daily'] = $("#deduct_limit_daily_times").ligerGetComboBoxManager().getValue();
			params['model.amount_withdraw_once'] = $("#withdraw_limit_once").ligerGetComboBoxManager().getValue();
			params['model.amount_withdraw_daily'] = $("#withdraw_limit_daily").ligerGetComboBoxManager().getValue();
			params['model.amount_withdraw_times_daily'] = $("#withdraw_limit_daily_times").ligerGetComboBoxManager().getValue();
			params['model.amount_transfer_once'] = $("#transfer_limit_once").ligerGetComboBoxManager().getValue();
			params['model.amount_transfer_daily'] = $("#transfer_limit_daily").ligerGetComboBoxManager().getValue();
			params['model.amount_transfer_times_daily'] = $("#transfer_limit_daily_times").ligerGetComboBoxManager().getValue();
			params['model.amount_process_method'] = $("#amountAccProcessMethod").ligerGetComboBoxManager().getValue();
			params['model.amount_risk_status'] = $("#amountRiskStatus").ligerGetComboBoxManager().getValue();
			params['model.amount_creator'] = INDEX_USER_NAME;
			return params;
		},
		packAccInfo : function(params) {
			params['model.acc_remain_max'] = $("#accountRemainMax").ligerGetComboBoxManager().getValue();
			params['model.acc_remain_min'] = $("#accountRemainMin").ligerGetComboBoxManager().getValue();
			params['model.acc_income_times_daily'] = $("#accountIncomeTimesDaily").ligerGetComboBoxManager().getValue();
			params['model.acc_income_amount_daily'] = $("#accountIncomeAmountDaily").ligerGetComboBoxManager().getValue();
			params['model.acc_outcome_times_daily'] = $("#accountOutcomeTimesDaily").ligerGetComboBoxManager().getValue();
			params['model.acc_outcome_amount_daily'] = $("#accountOutcomeAmountDaily").ligerGetComboBoxManager().getValue();
			params['model.acc_process_method'] = $("#accProcessMethod").ligerGetComboBoxManager().getValue();
			params['model.acc_risk_status'] = $("#accRiskStatus").ligerGetComboBoxManager().getValue();
			params['model.acc_creator'] = INDEX_USER_NAME;
			return params;
		},
		render : function(){
			var _this = this;
		    //初始化页面检验规则
		    $.metadata.setType('attr', 'validate');
	        $('#riskForm').validate({
	            submitHandler: function(){
	            	showMask();
	            	var url = '';
	            	var params = {};
	            	if (parent.window.queryList.cache.type == 'modify') {
	            		var type = parent.window.queryList.cache.riskType;
	            		url = _this.config.modeifyUrl + "?type=" + type;
	            		if (type == 1) {
	            			params = _this.packIpInfo(params);
	            		}
	            		if (type == 2) {
	            			params = _this.packFreInfo(params);
	            		}
	            		if (type == 3) {
	            			params = _this.packAmountInfo(params);
	            		}
	            		if (type == 4) {
	            			params = _this.packAccInfo(params);
	            		}
	            	} else {
	            		url = _this.config.addUrl;
	            		params = _this.packIpInfo(params);
	            		params = _this.packFreInfo(params);
	            		params = _this.packAmountInfo(params);
	            		params = _this.packAccInfo(params);
	            	}
	            	if (parent.window.queryList.cache.type != 'modify') {
	            		var checkRepeatUrl = _this.config.checkRepeatUrl;
	            		JAjax(checkRepeatUrl, params, 'json', function(data){
	            			hideMask();
	            			//没有新增风控纬度(风控纬度没有增减)
	                    	if (data == '1') {
	                    		JSWK.clewBox('风控规则没有改变,不能增加','clew_ico_fail',failShowTime);
	                    		return;
	                    	} else {
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
	                    }, 
	                    function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',failShowTime);}, true);
	            	} else {
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

	            }
	        });
		},
		
		f_save:function(){
			
			var ipElementCount = 0;
			var ipPM = $("#ipProcessMethod").ligerGetComboBoxManager().getValue();
			var ipRS = $("#ipRiskStatus").ligerGetComboBoxManager().getValue();		
			
			var freElementCount = 0;
			var frePM= $("#accFreProcessMethod").ligerGetComboBoxManager().getValue();
			var freRS = $("#freRiskStatus").ligerGetComboBoxManager().getValue();
			
			var amountElementCount = 0;
			var amountPM= $("#amountAccProcessMethod").ligerGetComboBoxManager().getValue();
			var amountRS = $("#amountRiskStatus").ligerGetComboBoxManager().getValue();
			
			var accElementCount = 0;
			var accPM= $("#accProcessMethod").ligerGetComboBoxManager().getValue();
			var accRS = $("#accRiskStatus").ligerGetComboBoxManager().getValue();
			
			//ip
			var ip = $('.ip');
			$.each(ip, function(i, item){
				if ($(this).attr('id') != 'ipProcessMethod' 
						&& $(this).attr('id') != 'ipRiskStatus') {
					if ($(this).ligerGetComboBoxManager().getValue() != '') {
						ipElementCount++;
					}
				}
				
			});
			if (ipElementCount > 0) {
				if (ipPM != '' && ipRS != '') {
				} else {
					JSWK.clewBox('配置有误，请检查','clew_ico_fail',failShowTime);
					return false;
				}
			} else {
				if (ipPM != '' || ipRS != '') {
					JSWK.clewBox('配置有误，请检查','clew_ico_fail',failShowTime);
					return false;
				}
			}
			
			//frequency
			var frequency = $('.frequency');
			$.each(frequency, function(i, item){
				if ($(this).attr('id') != 'accFreProcessMethod' 
						&& $(this).attr('id') != 'freRiskStatus') {
					if ($(this).ligerGetComboBoxManager().getValue() != '') {
						freElementCount++;
					}
				}
				
			});
			if (freElementCount > 0) {
				if (frePM != '' && freRS != '') {
				} else {
					JSWK.clewBox('配置有误，请检查','clew_ico_fail',failShowTime);
					return false;
				}
			} else {
				if (frePM != '' || freRS != '') {
					JSWK.clewBox('配置有误，请检查','clew_ico_fail',failShowTime);
					return false;
				}
			}
			
			//amount
			var amount = $('.amount');
			$.each(amount, function(i, item){
				if ($(this).attr('id') != 'amountAccProcessMethod' 
						&& $(this).attr('id') != 'amountRiskStatus') {
					if ($(this).ligerGetComboBoxManager().getValue() != '') {
						amountElementCount++;
					}
				}
				
			});
			if (amountElementCount > 0) {
				if (amountPM != '' && amountRS != '') {
				} else {
					JSWK.clewBox('配置有误，请检查','clew_ico_fail',failShowTime);
					return false;
				}
			} else {
				if (amountPM != '' || amountRS != '') {
					JSWK.clewBox('配置有误，请检查','clew_ico_fail',failShowTime);
					return false;
				}
			}		
			
			//account
			var account = $('.account');
			$.each(account, function(i, item){
				if ($(this).attr('id') != 'accProcessMethod' 
						&& $(this).attr('id') != 'accRiskStatus') {
					if ($(this).ligerGetComboBoxManager().getValue() != '') {
						accElementCount++;
					}
				}
				
			});
			if (accElementCount > 0) {
				if (accPM != '' && accRS != '') {
				} else {
					JSWK.clewBox('配置有误，请检查','clew_ico_fail',failShowTime);
					return false;
				}
			} else {
				if (accPM != '' || accRS != '') {
					JSWK.clewBox('配置有误，请检查','clew_ico_fail',failShowTime);
					return false;
				}
			}			
			$('#riskForm').submit();
		},
		reset : function(){
			 if($("#id").val()==''){
				 window.location='riskAdd.jsp';
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
		
		hideAllGuideLine : function() {
			Jid("ipGuideLine").style.display = 'none';
			Jid("limitGuideLine").style.display = 'none';
			Jid("frequencyGuideLine").style.display = 'none';
			Jid("accountGuideLine").style.display = 'none';
		},
		showContentByRiskType : function(riskType) {
			//ip纬度
			if (riskType == '1') {
				Jid("ipGuideLine").style.display = 'block';
				Jid("limitGuideLine").style.display = 'none';
				Jid("frequencyGuideLine").style.display = 'none';
				Jid("accountGuideLine").style.display = 'none';
			}
			
			//频率纬度
			if (riskType == '2') {
				Jid("ipGuideLine").style.display = 'none';
				Jid("limitGuideLine").style.display = 'none';
				Jid("frequencyGuideLine").style.display = 'block';
				Jid("accountGuideLine").style.display = 'none';
			}
			
			//额度纬度
			if (riskType == '3') {
				Jid("ipGuideLine").style.display = 'none';
				Jid("limitGuideLine").style.display = 'block';
				Jid("frequencyGuideLine").style.display = 'none';
				Jid("accountGuideLine").style.display = 'none';
				
			}
			//账户纬度
			if (riskType == '4') {
				Jid("ipGuideLine").style.display = 'none';
				Jid("limitGuideLine").style.display = 'none';
				Jid("frequencyGuideLine").style.display = 'none';
				Jid("accountGuideLine").style.display = 'block';
			}
		},
		hasSetted : function(type) {
			var _this = this;
			var flag = false;
			var data = _this.cache.ruleSettedVar;
			if (data != null && data.length > 0) {
				$.each(data, function(i, value){
					var array = value.split("-");
					if (array[0] == type) {
						if (array[1] == '1') {
							flag =  true;
						} 
					}
				});
			}
			return flag;
		},
	};
	
	$(document).ready(function() {
//		alert(parent.window.queryList.cache.test);
		window.riskRuleAdd = new RiskRuleAdd();
		
		$("#riskType").change(function(){
			var riskType =$('#riskType').ligerGetComboBoxManager().getValue();
			if (riskType == '' || riskType == 'undefined') {
				window.riskRuleAdd.hideAllGuideLine();
			}
			
			//ip纬度
			if (riskType == '1') {
				if (window.riskRuleAdd.hasSetted(riskType)) {
					JSWK.clewBox('此纬度已设置', 'clew_ico_fail', failShowTime);
					$('#riskType').ligerGetComboBoxManager().setValue('');
					window.riskRuleAdd.hideAllGuideLine();
				} else {
					Jid("ipGuideLine").style.display = 'block';
					Jid("limitGuideLine").style.display = 'none';
					Jid("frequencyGuideLine").style.display = 'none';
					Jid("accountGuideLine").style.display = 'none';
					$("#ipProcessMethod").ligerGetComboBoxManager().selectValue('2');
					$("#ipRiskStatus").ligerGetComboBoxManager().selectValue('1');					
				}
				
			}
			//频率纬度
			if (riskType == '2') {
				if (window.riskRuleAdd.hasSetted(riskType)) {
					JSWK.clewBox('此纬度已设置', 'clew_ico_fail', failShowTime);
					$('#riskType').ligerGetComboBoxManager().setValue('');
					window.riskRuleAdd.hideAllGuideLine();
				} else {
					Jid("ipGuideLine").style.display = 'none';
					Jid("limitGuideLine").style.display = 'none';
					Jid("frequencyGuideLine").style.display = 'block';
					Jid("accountGuideLine").style.display = 'none';
					$("#accFreProcessMethod").ligerGetComboBoxManager().selectValue('2');
					$("#freRiskStatus").ligerGetComboBoxManager().selectValue('1');
				}
				
			}
			//额度纬度
			if (riskType == '3') {
				if (window.riskRuleAdd.hasSetted(riskType)) {
					JSWK.clewBox('此纬度已设置', 'clew_ico_fail', failShowTime);
					$('#riskType').ligerGetComboBoxManager().setValue('');
					window.riskRuleAdd.hideAllGuideLine();
				} else {
					Jid("ipGuideLine").style.display = 'none';
					Jid("limitGuideLine").style.display = 'block';
					Jid("frequencyGuideLine").style.display = 'none';
					Jid("accountGuideLine").style.display = 'none';
					$("#amountAccProcessMethod").ligerGetComboBoxManager().selectValue('2');
					$("#amountRiskStatus").ligerGetComboBoxManager().selectValue('1');
				}
				
			}
			//账户纬度
			if (riskType == '4') {
				if (window.riskRuleAdd.hasSetted(riskType)) {
					JSWK.clewBox('此纬度已设置', 'clew_ico_fail', failShowTime);
					$('#riskType').ligerGetComboBoxManager().setValue('');
					window.riskRuleAdd.hideAllGuideLine();
				} else {
					Jid("ipGuideLine").style.display = 'none';
					Jid("limitGuideLine").style.display = 'none';
					Jid("frequencyGuideLine").style.display = 'none';
					Jid("accountGuideLine").style.display = 'block';
					$("#accProcessMethod").ligerGetComboBoxManager().selectValue('2');
					$("#accRiskStatus").ligerGetComboBoxManager().selectValue('1');
				}
			}
		});
		
	});
})();

