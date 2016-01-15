/**
 * 此文件中所有方法都依赖于ligerUI,升级ligerUI时,应该考虑此文件内容与新版本的兼容
 * 
 * @author  by malq 
 * @date    2012-08-11
 */
var MALQ_UI = {
	
	setGridOptions : function(p, url){
		var that = this;
		that._setDataOptions(p, url);
		that._setPageOptions(p);
	},
	_setDataOptions : function(p, url){
		p['root']='data';//数据源字段名
		p['record']='total';//数据总计
		p['dataAction']='server';//表示数据从服务器来的，而不是来自js文件
		p['url']=url;//数据url
	},
	_setPageOptions : function(p){		
		 p['pageSize']=p.pageSize?p.pageSize:30;//默认每页条数
		 p['width']=p.width?p.width:'100%';//grid 宽度
		 p['height']=p.height?p.height:'100%';//grid 高度	 		 
		 p['pageSizeOptions']=p.pageSizeOptions?p.pageSizeOptions:[10, 15, 20, 30, 50, 100];//每页显示条数
		 p['pageParmName']='page';//
		 p['pagesizeParmName']='rows';//
		 p['sortnameParmName']='order';//
		 p['sortorderParmName']='sort';//
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
