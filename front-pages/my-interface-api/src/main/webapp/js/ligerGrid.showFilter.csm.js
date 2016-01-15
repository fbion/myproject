/**
 * 扩展 ligerGrid 的 搜索功能(高级自定义查询).参照:ligerGrid.showFilter.js
 * @author malq 
 * @version 2.0 
 * @date 2012-12-15
 */
var isExtFilds =[];
$.ligerui.controls.Grid.prototype.showMyFilter = function (parms, defaultParms)
{
    var g = this, p = this.options, defaultParms = defaultParms ? defaultParms : {};
    
    $.ligerDefaults.Filter.operators['string'] = ($.ligerDefaults.Filter.operators.csm__string)?$.ligerDefaults.Filter.operators.csm__string:['Like' , 'EqualTo', 'NotEqualTo'];
    $.ligerDefaults.Filter.operators['text'] = ($.ligerDefaults.Filter.operators.csm__text)?$.ligerDefaults.Filter.operators.csm__text:['Like' , 'EqualTo', 'NotEqualTo'];
	
    $.ligerDefaults.Filter.operators['number'] =($.ligerDefaults.Filter.operators.csm__number)?$.ligerDefaults.Filter.operators.csm__number:['EqualTo', 'NotEqualTo', 'GreaterThan', 'GreaterThanOrEqualTo', 'LessThan', 'LessThanOrEqualTo'];
    $.ligerDefaults.Filter.operators['int'] =($.ligerDefaults.Filter.operators.csm__int)?$.ligerDefaults.Filter.operators.csm__int:['EqualTo', 'NotEqualTo', 'GreaterThan', 'GreaterThanOrEqualTo', 'LessThan', 'LessThanOrEqualTo'];
    $.ligerDefaults.Filter.operators['float'] =($.ligerDefaults.Filter.operators.csm__float)?$.ligerDefaults.Filter.operators.csm__float:['EqualTo', 'NotEqualTo', 'GreaterThan', 'GreaterThanOrEqualTo', 'LessThan', 'LessThanOrEqualTo'];
    $.ligerDefaults.Filter.operators['date'] = ($.ligerDefaults.Filter.operators.csm__date)?$.ligerDefaults.Filter.operators.csm__date:['EqualTo', 'NotEqualTo', 'GreaterThan', 'GreaterThanOrEqualTo', 'LessThan', 'LessThanOrEqualTo'];
    
    $.ligerDefaults.Filter.operators['combobox'] = ($.ligerDefaults.Filter.operators.csm__combobox)?$.ligerDefaults.Filter.operators.csm__combobox:['EqualTo', 'NotEqualTo'];
  
    //alert(JSON.stringify($.ligerDefaults.Filter.operators.csm__number));
    
    $.ligerDefaults.FilterString = {strings:{
        'and': '并且',
        'or': '或者',      
        'EqualTo': '相等',
        'NotEqualTo': '不相等',       
        'Like': '相似',
        'GreaterThan': '大于',
        'GreaterThanOrEqualTo': '大于或等于',
        'LessThan': '小于',
        'LessThanOrEqualTo': '小于或等于',       
        'startwith': '以..开始',
        'endwith': '以..结束',
        'in': '包括在...',
        'notin': '不包括...',
        'addgroup': '增加分组',
        'addrule': '增加条件',
        'deletegroup': '删除分组'
    }};
    
    if (g.winfilter)
    {
        g.winfilter.show();
        return;
    }
    var filtercontainer = $('<div id="' + g.id + '_filtercontainer"></div>').width(380).height(120).hide();  
    var filter = filtercontainer.ligerFilter({ fields: getFields() });
    return g.winfilter = $.ligerDialog.open({
        width: 620, height: 308,
        target: filtercontainer, 
		isDrag: false, 
		showToggle: true, 
		isResize: true,
		top: 50,
        buttons: [
            { text: '确定', onclick: function (item, dialog) { loadData(parms); dialog.hide(); } },
            { text: '取消', onclick: function (item, dialog) { dialog.hide(); } }
            ]
    }); 
    
    function getMyFields(){
    	var fields = getFields();   	
    	//fields: [],
        //字段类型 - 运算符 的对应关系
        var operators = {};
        //自定义输入框(如下拉框、日期)
        var editors = {};
    	//{ fields: getFields() }
    	//alert('parms:'+JSON.stringify(fields));
    }

    
    //将grid的columns转换为filter的fields
    function getFields(){
        var fields = [];
        //如果是多表头，那么g.columns为最低级的列
        $(g.columns).each(function (){ 
        	//alert(JSON.stringify(this));
        	if(undefined == this.name || ''==this.name || null == this.name || 'none'==this.type)
        		return true;
        	
        	if(this.type == 'number_2')isExtFilds.push(this.name);
        	
            var o = { name: this.name, display: this.display };
            var isNumber = this.type == 'int' || this.type == 'number' || this.type == 'float' || this.type == 'number_2';
            var isDate = this.type == 'date';
            var isCombobox = this.type =='combobox';
            if (isNumber) o.type = 'number';
            if (isDate) o.type = 'date';
            if (isCombobox) o.type = 'combobox';
           
            if (this.editor){           	
                o.editor = this.editor;          	
            }
            fields.push(o);
        });
        return fields;
    }

    
    function loadData(parms){  
    	
    	//alert(parms);
    	//alert(g.dataAction);    	
        var data = filter.getData();
       
        //if (g.dataAction == 'server')
        //{
            //服务器过滤数据
            loadServerData(data,parms);
        //}
        //else
        //{
            //本地过滤数据
            //loadClientData(data);
        //}
    }

    /**
     * 从服务器加载数据
     * @param  data
     * @author malq
     * @date   2012-06-16
     */
    function loadServerData(data, parms){    
    	
    	//////////////////////////////////////////////
    	if(data && data.rules && data.rules.length){
    		var value, name;
    		$(data.rules).each(function(i){
    			var that = this;			
    			if(!(that.type == 'number' && that.value ==0) && !that.value)return true;   			   			
    			//if(!this.value && this.value!=0)return true; 
    			
    			var key = 'requestParam'+'.rules['+i+']';
    			var tem=[];
    			$(parms).each(function(){
    				if(this.name===(key+'.field')||this.name===(key+'.op') || this.name===(key+'.type')||this.name===(key+'.value')){
        				return true;
        			}
        			tem.push(this);
    			});
    			parms = tem;
    			
    			parms.push({name:key+'.field', value:that.field});
    			parms.push({name:key+'.type', value:that.type});
    			
    			name = (that.op =='startwith' || that.op =='endwith')?'Like':that.op;
    			value = changeOpAndValue(that);
    			value = that.op =='Like'?'%'+value+'%'
    					:that.op =='endwith'?'%'+value
    					:that.op =='startwith'?value+'%'
    					:value;
    			
    			parms.push({name:key+'.op', value:name});
    			parms.push({name:key+'.value', value:value});
    		});
    	} 
   
    	//加上预设默认值
    	if(defaultParms && defaultParms.parms){
    		$(defaultParms.parms).each(function(){
    			var value = $('#'+this.eName).val();
    			var name = this.name;
    			var is = true;
    			$(parms).each(function(){
    				if(this.name==name){
    					is=false;
    					this.value=value;
    					return false;
    				}
    			});
    			if(is){
    				parms.push({name:name, value:value});
    			}
    		});
    	}   	
    	//alert(JSON.stringify(defaultParms));	
    	//alert(JSON.stringify(parms));	
    	g.set('parms', parms);//设置查询参数    
    	g.changePage('first');
        g.loadData();
    }
    
   
    function changeOpAndValue(rule){
    	//alert(Math.round(rule.value* 100));
    	//alert(JSON.stringify(rule));    	
    	if(rule.type == 'number' || rule.type == 'int'){
    		var value;
    		$(isExtFilds).each(function(){ 
    			if(this == rule.field){
    				value = Math.round(rule.value*100);
    				return false;
    			}
    		});  		
    		if(value)
    			return value;
    		else 
    			return rule.value;
    	}    		
    	else if(rule.type == 'date')
    		return rule.value.getTime();
    	else
    		return rule.value;   	
    }
    
    /**
     * 本地加载数据
     * @param data
     * @author malq
     * @date   2012-06-16
     */
    function loadClientData(data){
        var fnbody = ' return  ' + filterTranslator.translateGroup(data);
        g.loadData(new Function('o', fnbody));
    } 

};



var filterTranslator = {
    
    translateGroup : function (group)
    {
        var out = [];
        if (group == null) return ' 1==1 ';
        var appended = false;
        out.push('(');
        if (group.rules != null)
        {
            for (var i in group.rules)
            {
                var rule = group.rules[i];
                if (appended)
                    out.push(this.getOperatorQueryText(group.op));
                out.push(this.translateRule(rule));
                appended = true;
            }
        }
        if (group.groups != null)
        {
            for (var j in group.groups)
            {
                var subgroup = group.groups[j];
                if (appended)
                    out.push(this.getOperatorQueryText(group.op));
                out.push(this.translateGroup(subgroup));
                appended = true;
            }
        }
        out.push(')');
        if (appended == false) return ' 1==1 ';
        return out.join('');
    },

    translateRule : function(rule)
    {
    	///alert(444);
        var out = [];
        if (rule == null) return ' 1==1 ';
        if (rule.op == 'like' || rule.op == 'startwith' || rule.op == 'endwith')
        {
            out.push('/');
            if (rule.op == 'startwith')
                out.push('^');
            out.push(rule.value);
            if (rule.op == 'endwith')
                out.push('$');
            out.push('/i.test(');
            out.push('o["');
            out.push(rule.field);
            out.push('"]');
            out.push(')');
            return out.join('');
        }
        out.push('o["');
        out.push(rule.field);
        out.push('"]');
        out.push(this.getOperatorQueryText(rule.op));
        out.push('"');
        out.push(rule.value);
        out.push('"');
        return out.join('');
    },


    getOperatorQueryText : function(op)
    {
        switch (op)
        {
            case 'equal':
                return ' == ';
            case 'notequal':
                return ' != ';
            case 'greater':
                return ' > ';
            case 'greaterorequal':
                return ' >= ';
            case 'less':
                return ' < ';
            case 'lessorequal':
                return ' <= ';
            case 'and':
                return ' && ';
            case 'or':
                return ' || ';
            default:
                return ' == ';
        }
    }

};