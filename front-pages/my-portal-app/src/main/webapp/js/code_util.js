/**
 * this is  code util
 * 
 * @author  by xubao 
 * @date    2014-12-8
 */
var CODE = {
	  	getCodeName : function(codeTyep, code){ 
	  		var value = "";
	  		var data = this.getCodeData(codeTyep);
	  		$(data).each(function(){
	  			if($.trim(this.code) == $.trim(code)){
	  				value = this.name;
	  				return false;
	  			}
	  		});
	  		return value;
	  	},
	  	//根据typeCode 和 code 返回当前码表对象
	  	getCode : function(codeTyep, code){
	  		var thisRef = this;
	  		var result;
	  		var data = thisRef.getCodeData(codeTyep);
	  		$(data).each(function(){
	  			if($.trim(this.code) == $.trim(code)){
	  				result=this;
	  				return false;
	  			}
	  		});
	  		return result;
	  	},
	  	/**
	  	 *  初始化码表类型选择框
	  	 * @param selectId 码表select元素id.
	  	 * @param codeTyep 码表类型
	  	 * @param code 默认选中项
	  	 */
	  	getSelectByCodeType : function(selectId, codeTyep, code){
	  		var that = this;
	  		var option = "<option value=''>请选择</option>";
	  		var data = that.getCodeData(codeTyep);
	  		$(data).each(function(index){
	  			if(this.code == code){
					option +="<option value='"+this.code+"' selected>"+this.name+"</option>";
				}else{
					option +="<option value='"+this.code+"'>"+this.name+"</option>";
				}
	  		});
	  		$("#" + selectId).html(option);
	  	},
	  	/**
	  	 *  初始化码表类型选择框
	  	 * @param selectId 码表select元素id.
	  	 * @param codeTyep 码表类型
	  	 * @param code 默认选中项
	  	 */
	  	getSelectNoHintByCodeType : function(selectId, codeTyep, code){
	  		var that = this;
	  		var option = "";
	  		var data = that.getCodeData(codeTyep);
	  		$(data).each(function(index){
	  			if(this.code == code){
	  				option +="<option value='"+this.code+"' selected>"+this.name+"</option>";
	  			}else{
	  				option +="<option value='"+this.code+"'>"+this.name+"</option>";
	  			}
	  		});
	  		$("#" + selectId).html(option);
	  	},
	  	getCodeDateFormServer: function(codeTyep){
			var that = this;
			var result = [];
	  		JAjax(basePath + 'code/getBaseCodeList.action',{"requestParam.equal.typeCode" : codeTyep}, "json", function(data){
	  	  		if(data && data.data){
	  	  			$(data.data).each(function(){
	  	  				result.push(this);
	  	  				result.sort(that.sortCode);
	  	  			});
	  	  		}else{
	  	  			alert(data?data.message:"系统异常!");
	  	  		}
		      }, function(){
		    	  alert('获取码表类型为:' + codeTyep + '的信息异常!');
		      }, false,null,20000);//同步获取服务器中的码表值
	  		return result;
		},
		getCodeData: function(codeTyep){
			if(this.getParentCodeData() && this.getParentCodeData() != null && this.getParentCodeData().get(codeTyep) && this.getParentCodeData().get(codeTyep) != null) {
				return this.getParentCodeData().get(codeTyep);
			} else {
				var that = this;
		  		var result = [];
		  		var data = that.getCodeDateFormServer(codeTyep);
		  		$(data).each(function() {
		  			if($.trim(this.typeCode) == $.trim(codeTyep)){
		  				result.push(this);
		  			}
		  		});
		  		result.sort(that.sortCode);
		  		var map = new Map();
		  		map.put(codeTyep, result);
				window.CodeData = map;
		  		return result;
			}
		},
		//根据code值进行排序
	  	sortCode: function(x,y){
	  		return x.code - y.code;
	  	},
		/**
		 * 获取CodeData
		 */
		getParentCodeData: function(){
	  		var redata = null;
	  		if(window && window.CodeData){
	  			redata = window.CodeData;
	  		}else if(redata==null && parent.window && parent.window.CodeData){
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
//	  		if(!redata){ //如果没有,则从数据库中查询出来
//	  			this.initAllCode();
//	  			redata = window.CodeData;
//	  		}
	  		return redata;
	  	}
};

/**
 * js Map 实现
 * @returns {Map}
 */
function Map() {
    this.keys = [];
    this.data = {};

    /**
     * 放入一个键值对
     * @param {String} key
     * @param {Object} value
     */
    this.put = function(key, value) {
        if (this.data[key] == null) {
            this.keys.push(key);
        }
        this.data[key] = value;
    };

    /**
     * 获取某键对应的值
     * @param {String}  key
     * @return {Object} value
     */
    this.get = function(key) {
        return this.data[key];
    };

    /**
     * 是否包含该键
     */
    this.contain = function(key) {
        var value = this.data[key];
        if (value)
            return true;
        else
            return false;
    };
    
    /**
     * 删除一个键值对
     * @param {String} key
     */
    this.remove = function(key) {
        for(var index=0;index<this.keys.length;index++){
            if(this.keys[index]==key){
                this.keys.splice(index,1);
                break;
            }
        }
        //this.keys.remove(key);
        this.data[key] = null;
    };
    /**
     * 遍历Map,执行处理函数
     * @param {Function} 回调函数 function(key,value,index){..}
     */
    this.each = function(fn) {
        if (typeof fn != 'function') {
            return;
        }
        var len = this.keys.length;
        for ( var i = 0; i < len; i++) {
            var k = this.keys[i];
            fn(k, this.data[k], i);
        }
    };

    /**
     * 获取键值数组(类似Java的entrySet())
     * @return 键值对象{key,value}的数组
     */
    this.entrys = function() {
        var len = this.keys.length;
        var entrys = new Array(len);
        for ( var i = 0; i < len; i++) {
            entrys[i] = {
                key : this.keys[i],
                value : this.data[i]
            };
        }
        return entrys;
    };

    /**
     * 判断Map是否为空
     */
    this.isEmpty = function() {
        return this.keys.length == 0;
    };
    /**
     * 获取键值对数量
     */
    this.size = function() {
        return this.keys.length;
    };

    /**
     * 重写toString
     */
    this.toString = function() {
        var s = "{";
        for ( var i = 0; i < this.keys.length; i++, s += ',') {
            var k = this.keys[i];
            s += k + "=" + this.data[k];
        }
        s += "}";
        return s;
    };
}