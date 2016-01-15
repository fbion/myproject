/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2014年11月17日 14:33:27 
 */
(function() {
	var ParamAdd = function() {
		this.init();
	};
	ParamAdd.prototype = {
		config : {
			addUrl : root + '/paramJson/addParam.action',
			modeifyUrl : root + '/paramJson/updateParam.action',
			queryObjUrl : root + '/paramJson/getParamById.action',
			//检查属性是否唯一
			checkExistUrl : root+'/paramJson/checkExist.action'
		},
		cache : {
			dialog_001 : null,
			form : null,
			tags : []
		},
		init : function() {
			this.initHtml();
			this.initLayoutFrom();
			this.initFormData();
		},

		initHtml : function() {
			var html = '';
			html += '<div id="myPanel"><form id="form1">';
			html += '<div id="paramform" style="clear:both;"></div>';
			html += '</form></div>';
			$('#layout').html(html);
		},

		initLayoutFrom : function() {
			var that = this;

			var fields = [ {
				name : 'id',
				type : 'hidden'
			},
			/* 已经忽略验证required:true仅为必填项标识解决ie8不兼容问题 */
			{
				label : '参数名称',
				name : 'paramName',
				validate : {
					required : true
				},
				attr : {
					//regExp : '^([\w\W]*){1,120}$',
					//message : '1到120个字符',
					minlength:1,
					maxlength:120
				},
				type : 'text',
				group : '基础信息',
				groupicon : root + '/images/group.gif'
			}, {
				label : '数据类型',
				name : 'paramDataType',
				validate : {
					required : true,
				},
				attr:{
					minlength:1,
					maxlength:30
				},
				newline : true,
				type : 'combobox'
			}, {
				label : '参数值',
				name : 'paramValue',
				validate : {
					required : true
				},
				attr : {
					//regExp : '^([\w\W]*){1,500}$',
					//message : '1到500个字母、数字、_、-、中文 组合',
					minlength:1,
					maxlength:500
				},
				newline : true,
				type : 'textarea',
				width : 545
			}, {
				label : '备注',
				name : 'paramDesc',
				type : 'textarea',
				width : 545
			}

			];

			that.cache.form = $('#paramform').ligerForm({
				labelCss : 'labelcontainer',
				fieldCss : 'fieldcontainer',
				inputWidth : 180,
				labelWidth : 100,
				labelCss : 'myLabelCss',
				space : 80,
				fields : fields,
				validate : true
			});
			$('#paramDesc').attr("maxLength", "300");
			var w = 140;
			MALQ_CODE.getSelectByCodeType($("input[ligeruiid='paramDataType']"), "PARAM_DATA_TYPE", w, null);

		},

		initFormData : function() {
			var that = this;
			var id = parent.paramList.cache.editId;
			if (parent.window.paramList.cache.subPageId === 'add') {// 增加页面
				$("input[ligeruiid='paramDataType']").ligerComboBox().selectValue('String');
				$("input[ligeruiid='paramName']").focus();
			} else {
				JAjax(that.config.queryObjUrl, {
					'id' : id
				}, 'json', function(data) {
					if (data) {
						$("input[ligeruiid='paramDataType']").ligerComboBox().selectValue(data.paramDataType);
						that.cache.form.setData(data.dataObject);
					}
				}, function() {
					JSWK.clewBox('查询参数信息时发生异常', 'clew_ico_fail', failShowTime);
				}, true);

			}
			 
		},
		f_save : function() {
			showMask();
			var that=this;
			 $.metadata.setType('attr', 'validate');
			if (!CSM_VALIDATE.validateForm()){
				var element = $("input[ligeruiid='paramDataType']"); 
				if($("input[ligeruiid='paramDataType']").ligerComboBox().getValue()==''){
					element.parent().addClass("l-text-invalid l-text-over");
					element.parent().ligerTip({ content: '请选择数据类型！', target:element[0]});
					return false;
				}
				return;// 自定义验证
			}
			var params = {};
			var formData = that.cache.form.getData();
			for ( var key in formData) {
				if (key.length > 6 && key.substring(key.length - 6) == '__Text') {
					continue;
				}
				params[key] = formData[key];
			}
			//alert(JSON.stringify(params));
			//验证数据绑定配置是否重复!
			var url=that.config.checkExistUrl;
            JAjax(url, params, 'json', function(data){
            	hideMask();
            	var element = $("input[ligeruiid='paramName']"); 
            	if($.trim(data)=='true'){
             		element.parent().addClass("l-text-invalid");
             		element.parent().ligerTip({ content: '参数名称不可重复！', target:element[0]});
            	}else if($.trim(data)=='false'){
            		element.parent().removeClass("l-text-invalid");
            		var url = '';
        			if (parent.window.paramList.cache.subPageId === 'add') {
        				if($("input[ligeruiid='paramDataType']").ligerComboBox().getValue()==''){
        					parent.window.paramList.alert('请选择数据类型！', 'clew_ico_fail');
        					$("input[ligeruiid='paramDataType']").focus();
        					return false;
        				}
        				url = that.config.addUrl;
        			} else {
        				url = that.config.modeifyUrl;
        				var id = liger.get('paramName').getValue();
        				if (id)
        					params['model.paramName'] = id;
        				else {
        					parent.window.paramList.alert('参数获取失败！', 'clew_ico_fail');
        					return;
        				}
        			}
        			JAjax(url, params, 'json', function(data) {
        				hideMask();
        				if (data) {
        					parent.window.paramList.refreshGrid();
        					parent.window.paramList.alert('操作成功！', 'clew_ico_succ');
        					parent.window.paramList.cache.dialog.close();
        				} else {
        					parent.window.paramList.alert('处理失败', 'clew_ico_fail');

        				}
        			}, function() {
        				hideMask();
        				JSWK.clewBox('提交数据时发生异常', 'clew_ico_fail', failShowTime);
        			}, true);
            	}
			}, 
			function(){
				hideMask();
				JSWK.clewBox('验证数据时发生异常','clew_ico_fail',failShowTime);
			}, true);
		}
	};
	$(document).ready(function() {
		window.paramAdd = new ParamAdd();
	});
})();