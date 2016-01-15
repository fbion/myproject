var JSWK = {	
    clewBox: function(e, d, c) {
    	$("#JSWK_clewBoxDiv").remove();
        if (Jid("JSWK_clewBoxDiv")) {
            var b = "<span id='mode_clew' style='position:absolute;z-index: 10000;' class='clewBox_layer'>";
            b += "<span class='" + d + "'></span>" + e + "<span class='clew_end'></span>";
            b += "</span></div>";
            Jid("JSWK_clewBoxDiv").innerHTML = b;
            Jid("JSWK_clewBoxDiv").style.display = "";
        } else {
            var a = document.createElement("div");
            a.setAttribute("id", "JSWK_clewBoxDiv");
            a.className = "clewBox_layer_wrap";
            var b = "<span id='mode_clew' style='z-index: 10000;' class='clewBox_layer'>";
            b += "<span class='" + d + "'></span>" + e + "<span class='clew_end'></span>";
            b += "</span></div>";
            a.innerHTML = b;
            document.body.appendChild(a);
        }
        setTimeout(function() {
            if (Jid("JSWK_clewBoxDiv")) {
                Jid("JSWK_clewBoxDiv").style.display = "none";
            }
        },
        c ? c: 2000);
    },
    
	getRadio: function(id) {
		return "<div style=\"position:relative;top:30%;\"><input type=\"radio\" value=\""+id+"\" name=\"selectRadio\"  id=\"selectRadio\"/></div>";
	},
	getCheckbox: function(id) {
		return "<div style=\"position:relative;top:30%;\"><input type=\"checkbox\" value=\""+id+"\" name=\"selectCheckbox\" id=\"selectCheckbox\"/></div>";
	},
    
    setSelectValue: function(id, value) {
    	$('#'+id).val(value);  	
    	var text = $('#'+id).find("option:selected").text();
    	var newId = '#'+id+'_txt';
    	$(newId).val(text);
    	// $('#status_txt_val').val('2');    	
    }
	,
    //初始化车辆管理 车牌颜色 code表码值  htmlId select id  id修改时已经选择的表码值
    initSelect : function(code,htmlId,id){
    	JAjax(root + '/codeJson/getBaseCodeList.action', {"requestParam.equal.typeCode":code}, "json", function(data){ 
    		var str = "<option value=''>请选择</option>";
    		for(var i = 0; i < data.Rows.length;i++){
    			if(data.Rows[i].code == id){
    				str = str+"<option value='"+data.Rows[i].code+"' selected>"+data.Rows[i].name+"</option>";
    			}else{
    				str = str+"<option value='"+data.Rows[i].code+"'>"+data.Rows[i].name+"</option>";
    			}
    		}
    		$(htmlId).html(str);
    	}, function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',CSM_failAlertTime);}, false);
    }	
   
};

function Jid(a) {
    if (typeof a == "string") {
        var b = document.getElementById(a);
        //b = JSWK.nodeProtoType(b);
        return b;
    } else {
        return a;
    }
}

//此文件中所有方法都依赖于ligerUI,升级ligerUI时,应该考虑此文件内容与新版本的兼容
var MALQ_UI = {
	
	setGridOptions : function(p, url){
		var that = this;
		that._setDataOptions(p, url);
		that._setPageOptions(p);
	},
	_setDataOptions : function(p, url){
		p['root']='Rows';//数据源字段名
		p['record']='Total';//数据总计
		p['dataAction']='server';//表示数据从服务器来的，而不是来自js文件
		p['url']=url;//数据url
	},
	_setPageOptions : function(p){		
		 p['pageSize']=p.pageSize?p.pageSize:30;//默认每页条数
		 p['width']=p.width?p.width:'100%';//grid 宽度
		 p['height']=p.height?p.height:'100%';//grid 高度	 		 
		 p['pageSizeOptions']=p.pageSizeOptions?p.pageSizeOptions:[10, 15, 20, 30, 50, 100];//每页显示条数
		 p['pageParmName']='requestParam.page';//
		 p['pagesizeParmName']='requestParam.rows';//
		 p['sortnameParmName']='requestParam.order';//
		 p['sortorderParmName']='requestParam.sort';//
	},
		
	
	open: function(url, height, width, title){
		var that = this;		
    	var options = {};   	
    	that._base(url, height, width, title, options);   	
    	that._no_task(options);
    	    	
    	return $.ligerDialog.open(options);
	},
	_base : function(url, height, width, title, options){
		height = height ? height+50: 400;
    	width = width ? width: 600; 
    	options['title'] = title;
    	options['url'] = url;
    	options['height'] = height;
    	options['width'] = width;
    	options['isHidden'] = false;    	
	},
	_no_task: function(options) {   		  	
    	options['showToggle'] = true;
    	options['isDrag'] = false;
    	options['isResize'] = true;
    	//return options;
    },
   _buttons: function(options, success, cancel, stext, ctext){	   
	   var buttons = [];
	   if(success){
		   buttons.push({ text: stext?stext:'确定', onclick: success });
	   }
	   if(cancel){
		   buttons.push({ text: ctext?ctext:'取消', onclick: cancel });
	   }	   
	   options['buttons'] = buttons; 
   },
    
    open_button: function(url, height, width, success, cancel, title){
    	var that = this;
    	var options = {};
    	options['name'] = 'winselector';
    	that._base(url, height, width, title, options);   	
    	that._no_task(options);
    	that._buttons(options, success, cancel);    	
    	return $.ligerDialog.open(options);
    },
    open_myButton: function(options){    	
    	options['isHidden']=false;
    	options['isDrag']=false;
    	options['name']='winselector';  
    	
    	return $.ligerDialog.open(options);
    },
    open_button_02: function(url, height, width, success, cancel, title){
    	var that = this;
    	var options = {};    	
    	that._base(url, height, width, title, options);   	
    	that._no_task(options);   	
    	options['name']='winselector';
    	options['show']=true;
    	that._buttons(options, success, cancel); 
    	//options['buttons']=[{ text: '确定', onclick: success },{ text: '取消', onclick: cancel }];
    	    	
    	return $.ligerDialog.open(options);
    },
    
    open_button_03: function(url, height, width, success, cancel, successName, cancelName, title){   	
    	var that = this;
    	var options = {};    	
    	that._base(url, height, width, title, options);   	
    	that._no_task(options); 
    	that._buttons(options, success, cancel, successName, cancelName);
    	//successName = successName?successName:'确定';
    	//cancelName = cancelName?cancelName:'取消';
    	//options['buttons']=[{ text: successName, onclick: success },{ text: cancelName, onclick: cancel }];
    	options['name']='winselector'+new Date();
    	return $.ligerDialog.open(options);
    },
    
    open_button_01: function(url, height, width, success, successName, title){ 
    	var that = this;
    	var options = {};    	
    	that._base(url, height, width, title, options);   	
    	that._no_task(options);   	 
    	options['name']='winselector';
    	successName = successName?successName:'确定';
    	options['buttons']=[{ text: successName, onclick: success }];   	    	
    	return $.ligerDialog.open(options);
    },
    
    open_button_no_task: function(url, height, width, success, successName, title){
    	var that = this;
    	var options = {};    	
    	that._base(url, height, width, title, options);   	
    	that._no_task(options); 
    	options['name']='winselector';
    	successName = successName?successName:'确定';
    	options['buttons']=[{ text: successName, onclick: success }];
    	return $.ligerDialog.open(options);
    },
    
   confirm_yes : function(content, title, callback){   
    	if (typeof (title) == "function")
        {
            callback = title;
            type = null;
        }
        var btnclick = function (item, Dialog)
        {
            Dialog.close();
            if (callback)
            {
                callback(item.type == 'ok');
            }
        };
        p = {
            type: 'warn',
            content: content,
            buttons: [{ text: $.ligerDefaults.DialogString.yes, onclick: btnclick, type: 'ok' }]
        };
        if (typeof (title) == "string" && title != "") p.title = title;
        $.extend(p,{
            showMax: false,
            showToggle: false,
            showMin: false,
            allowClose: false
        });
        return $.ligerDialog(p);
    },
    
    dateEdit : function(e,f,st,sd){    	
    	e.ligerDateEditor({ 
    		format: (f && f.length>0) ? f : 'yyyy-MM-dd',
    		showTime: st ? true : false
    		//label: '格式化日期', 
    		//labelWidth: 100, 
    		//labelAlign: 'center' 
    	});
    	sd = (sd && sd.length>4) ? sd : e.ligerGetDateEditorManager().getFormatDate(new Date());
    	e.ligerGetDateEditorManager().setValue(sd);    	
    },
    
    getCodeComboboxForEditor : function(type){
    	var that = this;
    	var data = MALQ_CODE.getCodeData(type);
    	return that.getComboboxForEditor(data, 'name', 'code');
    },
    getDataComboboxForEditor : function(data){
    	var that = this;   	
    	return that.getComboboxForEditor(data, 'text', 'id');
    },
    getComboboxForEditor : function(data, text, value){
    	data = (data && data.length>0)?data:[];
    	text = text?text:'text';
    	value = value?value:'id';
    	var options ={
    			type: 'combobox',
    			data:data,
    			slide: false,		        						
    			textField :text,
    			valueField :value        						
    	};
    	return options;
    },
    getDateForEditor : function(showTime){    	
    	showTime = (showTime && showTime==true)?showTime:false;
    	var options ={
    		type: 'date', 
    		options: {showTime: showTime}     						
    	};
    	return options;
    },
    getNumberForEditor : function(type, oType){    	
    	type = type?type:'number';   	
    	oType = oType?oType:'int';   	
    	var options ={
    			type: type, 
    			options: {type: oType}    						
    	};
    	return options;
    }  
};

///this is csm code util
var MALQ_CODE = {
		  	
	  	getZoneComboBox:function(e1, e2, e3, boxWidth, boxHeight){
			var that = this;
			var w = (boxWidth && boxWidth>0)? boxWidth :180;
			var h = (boxHeight && boxHeight>0)? boxHeight :180;
			
	  		var firstData = that.getZoneData('0');
	  		//firstData.unshift({'text':'请选择','id':''});
	  		var secondData = null;
	  		var thridData = null;
	  		
	  		e1.ligerComboBox({data: null, selectBoxWidth: w,selectBoxHeight: h});
	  		e2.ligerComboBox({data: null, selectBoxWidth: w,selectBoxHeight: h});
	  		e3.ligerComboBox({data: null, selectBoxWidth: w,selectBoxHeight: h});
	  		
	  		e1.ligerComboBox({ data: firstData,
	  			onSelected: function (value){  				
	  				secondData = that.getZoneData(value);
	  				//secondData.unshift({'text':'请选择','id':''});
	  				//liger.get($(e2).attr('id')+'').setData(secondData);
	  				e2.ligerGetComboBoxManager().setData(secondData);
	  				e3.ligerGetComboBoxManager().setData(null);
	  			}  		 
	  		 });	  		
	  		e2.ligerComboBox({ data: secondData,
	  			onSelected: function (value){  				
	  				thridData = that.getZoneData(value);
	  				//thridData.unshift({'text':'请选择','id':''});
	  				e3.ligerGetComboBoxManager().setData(thridData);
	  			} 	  			  		
	  		});
	  		e3.ligerComboBox({data: thridData,
	  			onSelected: function (value){
	  			} 	  		
	  		});
	  	},
	  	getCodeSelect_pc:function(e1, e2, boxWidth, boxHeight){
	  		var that = this;
	  		var w = (boxWidth && boxWidth>0)? boxWidth :180;
	  		var h = (boxHeight && boxHeight>0)? boxHeight :180;
	  		
	  		var firstData = that.getZoneData('0');
	  		var secondData = null;
	  		e1.ligerComboBox({ data: firstData,selectBoxWidth: w,selectBoxHeight: h,
	  			onSelected: function (value){  				
	  				secondData = that.getZoneData(value);	  				
	  				e2.ligerGetComboBoxManager().setData(secondData);	  				
	  				//e2.val('请选择');
	  			}  		 
	  		});	  		
	  		e2.ligerComboBox({ data: secondData, selectBoxWidth: w,selectBoxHeight: h,
	  			onSelected: function (value){  					  				
	  			} 	  			  		
	  		});
	  		
	  		//e1.val('请选择');
	  		//e2.val('请选择');	  		
	  	},
	  	getCodeSelect_p:function(e1, boxWidth, boxHeight){
	  		var that = this;
	  		var w = (boxWidth && boxWidth>0)? boxWidth :180;
	  		var h = (boxHeight && boxHeight>0)? boxHeight :180;	  		
	  		var firstData = that.getZoneData('0');
	  		e1.ligerComboBox({ data: firstData,selectBoxWidth: w,selectBoxHeight: h,
	  			onSelected: function (value){	  				
	  			}  		 
	  		});	  		
	  		
	  		//e1.val('请选择');	  		  		
	  	},
	  	
	  	
	  	getZoneData : function(pCode){
	  		var that = this;
	  		var myData = [];
	  		var data = MALQ_CODE.getParentZoneData();
	  		pCode = pCode?$.trim(pCode):'';
	  		$(data).each(function(){
	  			if(this.pid == pCode){
					myData.push(this);
	  			}
	  		});
	  		myData.sort(that.sortfunction);
	  		return myData;
	  	},	  	
	  	sortfunction: function(x,y){
	  		return x.id - y.id;
	  	},
	  	
	  	getZoneObjByCode : function(code){  		
	  		var data = MALQ_CODE.getParentZoneData();
	  		var obj;
	  		code = code?$.trim(code):'';
	  		$(data).each(function(){
	  			if(this.id == code){	  				
	  				obj = this;
	  				return false;
	  			}
	  		});
	  		return obj;
	  	},
	  	getZoneNameByCode : function(c1, c2, c3){  		  		
	  		var data = MALQ_CODE.getParentZoneData();
	  		var value = "";
	  		var isEnd = 0;
	  		c1=c1?$.trim(c1):'';
	  		c2=c2?$.trim(c2):'';
	  		c3=c3?$.trim(c3):'';
	  		$(data).each(function(){  			
	  			if((c1!='' && c2!='' && c3!='') && (this.id == c1 || this.id == c2 || this.id == c3)){	  				
	  				value += this.text;
	  				if(isEnd++ > 2)
	  					return false;
	  			}else if((c1!='' && c2!='') && (this.id == c1 || this.id == c2)){
	  				value += this.text;
	  				if(isEnd++ > 1)
	  					return false;
	  			}else if(c1!='' && (this.id == c1)){
	  				value += this.text;
	  				return false;
	  			}
	  		});
	  		return value;
	  	},
	  	
	  	
	  	getThreeCodeSelect : function(e1, e2, e3, type, boxWidth, boxHeight){
			var that = this;
			var w = (boxWidth && boxWidth>0)? boxWidth :180;
			var h = (boxHeight && boxHeight>0)? boxHeight :180;
			
	  		var firstData = that.getCodeData(type);			
	  		firstData.unshift({'name':'请选择','code':''});
	  		var secondData = null;
	  		var thridData = null;
	  		var fourData = null;
	  		e1.ligerComboBox({ data: firstData, textField :"name", valueField :"code", selectBoxWidth: w, selectBoxHeight: h,
	  			onSelected: function (value){  				
	  				secondData = that.getCodeData(value);
	  				secondData.unshift({'name':'请选择','code':''});
	  				e2.ligerGetComboBoxManager().setData(secondData);
	  				e3.val('');
	  			}  		 
	  		 });	  		
	  		e2.ligerComboBox({ data: secondData, textField :"name", valueField :"code", selectBoxWidth: w,selectBoxHeight: h,
	  			onSelected: function (value){  				
	  				thridData = that.getCodeData(value);
	  				thridData.unshift({'name':'请选择','code':''});
	  				e3.ligerGetComboBoxManager().setData(thridData);
	  			} 	  			  		
	  		});
	  		e3.ligerComboBox({data: thridData, textField :"name", valueField :"code", selectBoxWidth: w, selectBoxHeight: h,
	  			onSelected: function (value){ 
	  				//fourData = that.getZoneData(data, value);	  				
	  				//e4.ligerGetComboBoxManager().setData(fourData);
	  			} 	  		
	  		});	  		
	  	},
	  	
	  	getCodeName : function(typeCode, code){ 
	  		var value = "";
	  		var data = MALQ_CODE.getParentCodeData();	
	  		typeCode = typeCode?$.trim(typeCode):'';
	  		code = code?$.trim(code):'';
	  		$(data).each(function(){
	  			if($.trim(this.typeCode)==typeCode && $.trim(this.code) == code){
	  				value = this.name;
	  				return false;
	  			}
	  		});
	  		return value;
	  	},
	  	getCodeNameByPid : function(pid){ 
	  		var value = "";
	  		//alert(pid);
	  		var data = MALQ_CODE.getParentCodeData();
	  		pid = pid?$.trim(pid):'';
	  		$(data).each(function(){
	  			if($.trim(this.id)==pid){
	  				value = this.name;
	  				return false;
	  			}
	  		});
	  		return value;
	  	},
	  	
	  	
	  	//根据typeCode 和 code 返回当前码表对象
	  	getCode : function(typeCode, code){
	  		var result=null;
	  		var data = MALQ_CODE.getParentCodeData();
	  		typeCode = typeCode?$.trim(typeCode):'';
	  		code = code?$.trim(code):'';
	  		$(data).each(function(){
	  			if($.trim(this.typeCode) == typeCode && $.trim(this.code) == code){
	  				result=this;
	  				return false;
	  			}
	  		});
	  		return result;
	  	},
	  	getSelectByCodeType : function(e1, type, boxWidth, boxHeight){
	  		var that = this;
	  		var w = (boxWidth && boxWidth>0)? boxWidth :180;
			var h = (boxHeight && boxHeight>0)? boxHeight :180;
	  		var data = that.getCodeData(type);
	  		//data.unshift({'name':'请选择','code':''});
	  		e1.ligerComboBox({
	  			data: data,
	  			textField :'name',
	  			valueField :'code',
	  			autocomplete: true,
	  			selectBoxWidth: w,
	  			selectBoxHeight: h
	  		});
	  	},
	  	/*Added By yikangfeng*/
	  	getSelectByCodeTypeChecked : function(e1, tyep, boxWidth, boxHeight,defaultItem,checked,selectedHandler){
	  		var that = this;
	  		var w = (boxWidth && boxWidth>0)? boxWidth :180;
			var h = (boxHeight && boxHeight>0)? boxHeight :180;
	  		var data = that.getCodeData(tyep);
	  		data.unshift(((defaultItem==null||defaultItem==undefined)?{'name':'请选择','code':''}:defaultItem));
	  		e1.ligerComboBox({
	  			data: data,
	  			textField :"name",
	  			valueField :"code",
	  			selectBoxWidth: w,
	  			selectBoxHeight: h,
	  			onSelected:selectedHandler
	  		});
	  		if(checked!=null&&checked!=undefined)//不能直接写if(checked) 因为''会被当做不合法
	  		   e1.ligerGetComboBoxManager().selectValue(checked);
	  	},
	  	getSelectByCodeTypeCheckBox : function(e1, tyep, boxWidth, boxHeight){
	  		var that = this;
	  		var w = (boxWidth && boxWidth>0)? boxWidth :180;
			var h = (boxHeight && boxHeight>0)? boxHeight :180;
	  		var data = that.getCodeData(tyep);
	  		data.unshift({'name':'请选择','code':''});
	  		e1.ligerComboBox({
	  			data: data,
	  			textField :"name",
	  			valueField :"code", 
	  			isMultiSelect: true,   //是否多选
	  	        isShowCheckBox: true,  //是否选择复选框
	  			selectBoxWidth: w,
	  			selectBoxHeight: h
	  		});
	  	},
	  	/*Added By yikangfeng*/
	  	getSelectByCodeTypeCheckBoxChecked:function(e1, tyep, boxWidth, boxHeight,defaultItem,checked){
	  		var that = this;
	  		var w = (boxWidth && boxWidth>0)? boxWidth :180;
			var h = (boxHeight && boxHeight>0)? boxHeight :180;
	  		var data = that.getCodeData(tyep);
	  		data.unshift(((defaultItem==null||defaultItem==undefined)?{'name':'请选择','code':''}:defaultItem));
	  		e1.ligerComboBox({
	  			data: data,
	  			textField :"name",
	  			valueField :"code",
	  			isMultiSelect: true,   //是否多选
	  	        isShowCheckBox: true,  //是否选择复选框
	  			selectBoxWidth: w,
	  			selectBoxHeight: h
	  		});
	  		if(checked!=null&&checked!=undefined)//不能直接写if(checked) 因为''会被当做不合法
	  		   e1.ligerGetComboBoxManager().selectValue(checked);
	  	},
	  	getSelectByData : function(e1, data, boxWidth, boxHeight){
	  		var that = this;
	  		var w = (boxWidth && boxWidth>0)? boxWidth :180;
	  		var h = (boxHeight && boxHeight>0)? boxHeight :180;
	  		e1.ligerComboBox({data: data,selectBoxWidth: w, selectBoxHeight: h});
	  	},
	  	setSelectInitValue : function(id, initName, initValue){
	  		$('#'+id).val(initName);
			$('#'+id+'_val').val(initValue);
	  	},
	  
	  	getCodeDataById : function(id){
	  		var that = this;
	  		var result;
	  		var data = MALQ_CODE.getParentCodeData();
	  		id = id?$.trim(id):'';
	  		$(data).each(function(){
	  			if(this.id == id){
	  				result=this;
	  				return false;
	  			}
	  		});
	  		return result;
	  	},
	  	getCodeData : function(typeCode){
	  		var that = this;
	  		var result = [];
	  		var data = MALQ_CODE.getParentCodeData();
	  		typeCode = typeCode?$.trim(typeCode):'';
	  		$(data).each(function(){
	  			if($.trim(this.typeCode) == typeCode){
	  				result.push(this);
	  			}
	  		});
	  		result.sort(that.sortCode);
	  		return result;
	  	},
	  	sortCode: function(x,y){
	  		return x.code - y.code;
	  	},
	  	
	  	
	  	addJSCacheData : function(obj){
	  		if(window && window.CodeData){
	  			window.CodeData.push(obj);
	  		}else if(parent.window && parent.window.CodeData){
	  			parent.window.CodeData.push(obj);
	  		}else if(parent.parent.window && parent.parent.window.CodeData){
	  			parent.parent.window.CodeData.push(obj);
	  		}else if(parent.parent.parent.window && parent.parent.parent.window.CodeData){
	  			parent.parent.parent.window.CodeData.push(obj);
	  		}else if(parent.parent.parent.parent.window && parent.parent.parent.parent.window.CodeData){
	  			parent.parent.parent.parent.window.CodeData.push(obj);
	  		}else if(parent.parent.parent.parent.parent.window && parent.parent.parent.parent.parent.window.CodeData){
	  			parent.parent.parent.parent.parent.window.CodeData.push(obj);
	  		}	  			  		
	  	},
	  	updateJSCacheDate : function(obj){
	  		var data = MALQ_CODE.getParentCodeData();
	  		$(data).each(function(i){
	  			if(obj.id == this.id){
	  				data.splice(i, 1);
  					data.push(obj);
  					return false;
  				}
	  		});
	  		if(window && window.CodeData){
	  			window.CodeData=data;
	  		}else if(parent.window && parent.window.CodeData){
	  			parent.window.CodeData=data;
	  		}else if(parent.parent.window && parent.parent.window.CodeData){
	  			parent.parent.window.CodeData=data;
	  		}else if(parent.parent.parent.window && parent.parent.parent.window.CodeData){
	  			parent.parent.parent.window.CodeData=data;
	  		}else if(parent.parent.parent.parent.window && parent.parent.parent.parent.window.CodeData){
	  			parent.parent.parent.parent.window.CodeData=data;
	  		}else if(parent.parent.parent.parent.parent.window && parent.parent.parent.parent.parent.window.CodeData){
	  			parent.parent.parent.parent.parent.window.CodeData=data;
	  		}	  		
	  		
	  	},
	  	delJSCacheData : function(delData){
	  		var data = MALQ_CODE.getParentCodeData();
	  		var id;
	  		$(delData).each(function(){
	  			id = this.id;
	  			$(data).each(function(i){
	  				if(id == this.id){
	  					data.splice(i, 1);return false;
	  				}
		  		});
	  		});
	  		if(window && window.CodeData){
	  			window.CodeData=data;
	  		}else if(parent.window && parent.window.CodeData){
	  			parent.window.CodeData=data;
	  		}else if(parent.parent.window && parent.parent.window.CodeData){
	  			parent.parent.window.CodeData=data;
	  		}else if(parent.parent.parent.window && parent.parent.parent.window.CodeData){
	  			parent.parent.parent.window.CodeData=data;
	  		}else if(parent.parent.parent.parent.window && parent.parent.parent.parent.window.CodeData){
	  			parent.parent.parent.parent.window.CodeData=data;
	  		}else if(parent.parent.parent.parent.parent.window && parent.parent.parent.parent.parent.window.CodeData){
	  			parent.parent.parent.parent.parent.window.CodeData=data;
	  		}	  		
	  	},
	  	
	  	getParentZoneData: function(){
	  		var redata = null;
	  		if(window && window.ZoneData){
	  			redata = window.ZoneData;
	  		}else if(parent.window && parent.window.ZoneData){
	  			redata = parent.window.ZoneData;
	  		}else if(redata==null && parent.parent.window && parent.parent.window.ZoneData){
	  			redata = parent.parent.window.ZoneData;
	  		}else if(redata==null && parent.parent.parent.window && parent.parent.parent.window.ZoneData){
	  			redata = parent.parent.parent.window.ZoneData;
	  		}else if(redata==null && parent.parent.parent.parent.window && parent.parent.parent.parent.window.ZoneData){
	  			redata = parent.parent.parent.parent.window.ZoneData;
	  		}else if(redata==null && parent.parent.parent.parent.parent.window && parent.parent.parent.parent.parent.window.ZoneData){
	  			redata = parent.parent.parent.parent.parent.window.ZoneData;
	  		}	  		
	  		//alert('util:'+JSON.stringify(redata));
	  		
	  		return redata;
	  	},
	  	getParentCodeData: function(){
	  		var redata = null;
	  		if(window && window.CodeData){
	  			redata = window.CodeData;
	  		}else if(parent.window && parent.window.CodeData){
	  			redata = parent.window.CodeData;
	  		}else if(redata==null && parent.parent.window && parent.parent.window.CodeData){
	  			redata = parent.parent.window.CodeData;
	  		}else if(redata==null && parent.parent.parent.window && parent.parent.parent.window.CodeData){
	  			redata = parent.parent.parent.window.CodeData;
	  		}else if(redata==null && parent.parent.parent.parent.window && parent.parent.parent.parent.window.CodeData){
	  			redata = parent.parent.parent.parent.window.CodeData;
	  		}else if(redata==null && parent.parent.parent.parent.parent.window && parent.parent.parent.parent.parent.window.CodeData){
	  			redata = parent.parent.parent.parent.parent.window.CodeData;
	  		}	  		
	  		//alert('util:'+JSON.stringify(redata));	
	  		
	  		return redata;
	  	},
	  	
	  	/**
	  	 * 正则
	  	 * @param str
	  	 * @returns
	  	 */
	  	//////////////////////////////////////
	  	_isInteger : function(val){
	        return (/^\+?[1-9][0-9]*$/).test(val);//正整数 	
	  	},
	  	
	  	
	  	
	  	
	  	
	  	
	  	////////////////////////////////////
	  	
	  	formatNumber : function(str){
	  		var reg = /\./;
	  		reg.exec(str); 
	        var tem=RegExp.rightContext; 
	        if(tem.length==1){
	        	str = str+"0";
	        }else if(tem.length==2){
	        	str = str+"";
	        }else{
	        	str = str+".00";
	        }
	        return str;
	  	},
	  	
	  	getDDByDateAndMM : function(str,n){
	  		var that = this;
	  		var dd = 0;
	  		var mm = Number(that.getArrByStrDate(str)[2]);
	  		n = n?n:1;
	  		//alert("sss:"+str+"::"+n+"::"+mm);
	  		for(var i=n; i>0; i--){
	  			if(mm < 12){
	  				dd += that.getDDByMM(mm);
	  				mm++;
	  			}else if(mm == 12){
	  				dd +=31;
	  				mm = 1;
	  			}else{
	  				return 'NaN';
	  			}		
	  		}
	  		return dd;
	  	},
	  	
	  	//
	  	getArrByStrDate : function(strDate){
	  		var yy,mm,dd;
	  		yy = strDate.substring(0,4);
	  		mm = strDate.substring(5,7);
	  		dd = strDate.substring(8,10);  		
	  		//var reg = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
	  		//var arr = strDate.match(reg);
	  		//alert(strDate+"::"+yy+"::"+mm+"::"+dd);	  		
		  	return [strDate,yy,mm,dd];
	  	},
	  //将当前时间转成字符串
	  	date2str_date : function(date, format){
	  		var that = this;	  			  		
	  		format = format ? format : 'yyyy-MM-dd'; //如果不传模式，默认为yyyy-MM-dd
	  	    var yyyy = date.getFullYear();
	  	    var MM = (date.getMonth()+1) >= 10 ?(date.getMonth()+1)+"":"0"+(date.getMonth()+1);
	  	    var dd = date.getDate() >= 10?date.getDate()+"":"0"+date.getDate();
	  	    var HH = date.getHours() >= 10?date.getHours()+"":"0"+date.getHours();
	  	    var mm = date.getMinutes()>= 10?date.getMinutes()+"":"0"+date.getMinutes();
	  	    var ss = date.getSeconds()>= 10?date.getSeconds()+"":"0"+date.getSeconds();
	  	    var mss = date.getMilliseconds()+"";
	  	    
	  	    var reStr = format.replace("yyyy",yyyy);
	  	    reStr = reStr.replace("MM",MM);
	  	    reStr = reStr.replace("dd",dd);
	  	    reStr = reStr.replace("HH",HH);
	  	    reStr = reStr.replace("mm",mm);
	  	    reStr = reStr.replace("ss",ss);
	  	    reStr = reStr.replace("S",mss);
	  	    return reStr;
	  	},
	  	
	  	getDDByMM : function(mm){
	  		var that = this;	  	  
	  		var dd;
			switch (mm){
				case 2:
					dd = 28;break;
				case 4:
					dd = 30;break;
				case 6:
					dd = 30;break;		  	 
				case 9:
					dd = 30;break;		  	 
				case 11:
					dd = 30;break;
				default:
					dd = 31;
			}
			return dd;
	  	},
	  	
	  	//计算给定日期后的N天，如果不给定日期，默认为当前时间
	  	str2date : function(str,n){
	  	  var that = this;
	  	  str = str ? str : that.date2str(0);	  	  
	  	  var dd, mm, yy;
	  	  var arr;
	  	  var reg = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
	  	  if (arr = str.match(reg)){	  		  
	  	    yy = Number(arr[1]);
	  	    mm = Number(arr[2])-1;
	  	    dd = Number(arr[3]);
	  	  }else{
	  		return that.date2dateBystr(n);
	  	  }
	  	  
	  	  return that.date2str(yy, mm, dd, n);
	  	}, 

	  //计算当前日期后的N天
	  	date2dateBystr : function(n){
	  	 var that = this;
	  	 var s, d, t, t2;
	  	 t = new Date().getTime();	  	 
	  	 return that._date2str(t, n);
	  	   
	  	},
	  //计算给定日期后的N天，如果不给定日期，默认为当前时间
	  	date2str : function(yy, mm, dd, n){
	  		var that = this;
	  		//alert("yy:"+yy +"mm:" + mm +"dd:" + dd+"n:" + n);
	  	    var t = Date.UTC(yy, mm, dd);
	  	    if(t && t>1)
	  	    	return that._date2str(t, n);
	  	    else
	  	    	return that.date2dateBystr(n);  	     	   
	  	},
	  
	  	_date2str : function(t, n){
	  		var s, t2, d;
	  		t2 = n * 1000 * 3600 * 24;
  		  	t+= t2;
  		  	d = new Date(t);
  		  	s = d.getUTCFullYear() + "-";
  		  	s += ("00"+(d.getUTCMonth()+1)).slice(-2) + "-";
  		  	s += ("00"+d.getUTCDate()).slice(-2);
  		  	return s;
	  	},
	  	_date2str_d : function(d){
	  		var s;
	  		d = d ? d : new Date();
	  		s = d.getUTCFullYear() + "-";
	  		s += ("00"+(d.getUTCMonth()+1)).slice(-2) + "-";
	  		s += ("00"+d.getUTCDate()).slice(-2);
	  		return s;
	  	},
	  	
	  	utcToDate:function(n_utc, format){
		 	var that = this;
		    if (!n_utc || n_utc == null || n_utc == "null" || n_utc == "无") 
		        return "";
		    format = (format && format!='')?format:'yyyy-MM-dd HH:mm:ss';
		    var date = new Date();
		    //date.setTime((parseInt(n_utc) + (8 * 3600 * 1000)));
		    date.setTime(parseInt(n_utc));
		    return that.date2str_date(date, format);
	},
	titleBlock : function(e, text, imgUrl, extendable){
		text = text?text:'数据信息';		 
		imgUrl = imgUrl?imgUrl:'../../images/spanIoc.png';
		
		
		var l = '<div class="navline"></div>';
		var t = '<div class="togglebtn"></div>';
		if(extendable){
			var s = '<div id="block_malq_2013-03-07" ><img src="'+imgUrl+'" onclick="this.blockExtendable('+extendable+','+this+');" style="cursor:pointer" alt="'+text+'"/><span class="span" style="cursor:pointer">'+text+'</span></div>';		 
			e.html(s+t+l);
			e.addClass('searchtitle');			
			this.blockExtendable(extendable, $('.searchtitle .togglebtn'));						
			this.blockExtendable(extendable, $('#block_malq_2013-03-07'));
		}else{
			var s = '<div ><img src="'+imgUrl+'" alt="'+text+'"/><span class="span">'+text+'</span></div>';		 
			e.html(s+l);
			e.addClass('searchtitle');
		}		 		
	},
	//搜索框 收缩/展开
	blockExtendable : function(e, ce){		
		 ce.live('click', function (){
		     if ($('.searchtitle .togglebtn').hasClass('togglebtn-down')){
		     	$('.searchtitle .togglebtn').removeClass('togglebtn-down');
		     	e.hide();
		     }else {
		     	$('.searchtitle .togglebtn').addClass('togglebtn-down');
		     	e.show();
		     }		      
		 }); 
	}	  	
};
/**
 * this is csm page function
 * 
 * @author  by malq 
 * @date    2012-12-06
 */
var MALQ_FUNCTION = {
		  		  	
		getPageFunction : function(tabid,permIds){
	  		var myFuns = [];
	  		if(!tabid || !permIds || !permIds.length){
	  			return myFuns;
	  		}
	  		var that = this;
	  		var funs = that.getIndexTemFunItems();
	  		if(!funs){
	  			funs = that.getIndexTemFunItemsFromSrvice();
	  		}
	  		$(funs).each(function(){
	  			if(this.parentId==tabid && $.inArray(this.permId, permIds)>-1){
	  				var item = {};
					item['permId'] = this.permId;
					item['text'] = this.name;
  					item['icon'] = this.iconUrl;
  					myFuns.push(item);
	  			}
	  		});	  		
	  		return myFuns;
	  	},
	  	getIndexTemFunItemsFromSrvice: function(){
			var re =[];	  		
  			$.ajax({
	    		   type: "POST",
	    		   url:  root+'/loginJson/getMyPageFunction.action',
	    		   dataType: "json",	    		
	    		   cache : false,
	        	   async : false,	    		   	    		   
	    		   success: function(data){
	    			   if(data){
	    				   parent.INDEX_TEM_FUN_ITEMS=data;
	    				   re = data;
	    			   }
	    		   },
	    		   error : function(e, s){        			
	    			   $.ligerDialog.alert('get page function error!');	
	    		   }
	    		}); 
	  		return re;
	  	},
	  	getIndexTemFunItems: function(){	  	
	  		if(parent.INDEX_TEM_FUN_ITEMS){
	  			return parent.INDEX_TEM_FUN_ITEMS;
	  		}else if(parent.parent.INDEX_TEM_FUN_ITEMS){
	  			return parent.parent.INDEX_TEM_FUN_ITEMS;
	  		}else if(parent.parent.parent.INDEX_TEM_FUN_ITEMS){
	  			return parent.parent.parent.INDEX_TEM_FUN_ITEMS;
	  		}else if(parent.parent.parent.parent.INDEX_TEM_FUN_ITEMS){
	  			return parent.parent.parent.parent.INDEX_TEM_FUN_ITEMS;
	  		}else if(parent.parent.parent.parent.parent.INDEX_TEM_FUN_ITEMS){
	  			return parent.parent.parent.parent.parent.INDEX_TEM_FUN_ITEMS;
	  		}else{
	  			return null;
	  		}	  			  		
	  	},
	  	getIndexSysSign: function(){	  	
	  		if(parent.INDEX_SYS_SIGN){
	  			return parent.INDEX_SYS_SIGN;
	  		}else if(parent.parent.INDEX_SYS_SIGN){
	  			return parent.parent.INDEX_SYS_SIGN;
	  		}else if(parent.parent.parent.INDEX_SYS_SIGN){
	  			return parent.parent.parent.INDEX_SYS_SIGN;
	  		}else if(parent.parent.parent.parent.INDEX_SYS_SIGN){
	  			return parent.parent.parent.parent.INDEX_SYS_SIGN;
	  		}else if(parent.parent.parent.parent.parent.INDEX_SYS_SIGN){
	  			return parent.parent.parent.parent.parent.INDEX_SYS_SIGN;
	  		}else{
	  			return null;
	  		}	  			  		
	  	},
	  	getIndexSysId: function(){	  	
	  		if(parent.INDEX_SYS_ID){
	  			return parent.INDEX_SYS_ID;
	  		}else if(parent.parent.INDEX_SYS_ID){
	  			return parent.parent.INDEX_SYS_ID;
	  		}else if(parent.parent.parent.INDEX_SYS_ID){
	  			return parent.parent.parent.INDEX_SYS_ID;
	  		}else if(parent.parent.parent.parent.INDEX_SYS_ID){
	  			return parent.parent.parent.parent.INDEX_SYS_ID;
	  		}else if(parent.parent.parent.parent.parent.INDEX_SYS_ID){
	  			return parent.parent.parent.parent.parent.INDEX_SYS_ID;
	  		}else{
	  			return null;
	  		}	  			  		
	  	}
	  	
};

/**
 * 此文件中所有方法都依赖于ligerUI,升级ligerUI时,应该考虑此文件内容与新版本的兼容
 * 
 * @author  by malq 
 * @date    2012-09-24
 */
var MALQ_TREE = {
	
		loadData: function (g, node, url, param){
			var that = this;
            //var g = this, p = this.options;
			g.loading = $("<div class='l-tree-loading'></div>");
            g.loading.show();
            var ajaxtype = param ? "post" : "get";
            param = param || [];
            //请求服务器
            $.ajax({
                type: ajaxtype,
                url: url,
                data: param,
                dataType: 'json',
                success: function (data){
                    if (!data) return;
                    $(data).each(function(){ 
                    	if(that.isChildren(this)){
                    		this['isexpand']='true';
                    	}else{
                    		this['children']=[];
    						this['isexpand']='false';
                    	}                    	             	
					}); 
                    g.loading.hide();
                    g.append(node, data);
                    g.trigger('success', [data]);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown){
                    try{
                        g.loading.hide();
                        g.trigger('error', [XMLHttpRequest, textStatus, errorThrown]);
                    }catch (e){
                    	g.loading.hide();
                    }
                }
            });
        },
        
        isChildren : function(obj){
        	var result = true;
        	var data = MALQ_CODE.getParentCodeData();
        	 $(data).each(function(){ 
        		 if(this.pid == obj.id){
        			 result = false;
        			 return false;
        		 }
        	 });
        	return result; 
        },
        
        //未完全选中
        getIncomplete: function (g){
            //var g = this, p = this.options;
            if (!g.options.checkbox) return null;
            var nodes = [];
            $(".l-checkbox-incomplete", g.tree).parent().parent("li").each(function (){
                var treedataindex = parseInt($(this).attr("treedataindex"));
                nodes.push({ target: this, data: g._getDataNodeByTreeDataIndex(g.data, treedataindex) });
            });
            return nodes;
        }
      
};

