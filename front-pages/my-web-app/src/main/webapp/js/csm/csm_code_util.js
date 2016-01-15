/**
 * this is csm code util
 * 
 * @author  by malq 
 * @date    2012-08-17
 */
var MALQ_CODE = {
		  	
	  	getCodeSelect_2:function(e1, e2, e3, boxWidth, boxHeight){
			var that = this;
			var w = (boxWidth && boxWidth>0)? boxWidth :180;
			var h = (boxHeight && boxHeight>0)? boxHeight :180;
			
	  		var firstData = that.getZoneData('0');
	  		firstData.unshift({'text':'请选择','id':''});
	  		var secondData = null;
	  		var thridData = null;
	  		e1.ligerComboBox({ data: firstData,selectBoxWidth: w,selectBoxHeight: h,
	  			onSelected: function (value){  				
	  				secondData = that.getZoneData(value);
	  				secondData.unshift({'text':'请选择','id':''});
	  				e2.ligerGetComboBoxManager().setData(secondData);
	  				e3.val('');
	  				//e2.val('请选择');
	  			}  		 
	  		 });	  		
	  		e2.ligerComboBox({ data: secondData, selectBoxWidth: w,selectBoxHeight: h,
	  			onSelected: function (value){  				
	  				thridData = that.getZoneData(value);
	  				thridData.unshift({'text':'请选择','id':''});
	  				e3.ligerGetComboBoxManager().setData(thridData);
	  				//e3.val('请选择');
	  			} 	  			  		
	  		});
	  		e3.ligerComboBox({data: thridData, selectBoxWidth: w, selectBoxHeight: h,
	  			onSelected: function (value){ 
	  				//fourData = that.getZoneData(data, value);	  				
	  				//e4.ligerGetComboBoxManager().setData(fourData);
	  			} 	  		
	  		});
	  		
	  		//e1.val('请选择');
	  		//e2.val('请选择');
	  		//e3.val('请选择');
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
	  		var obj=null;
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
	  	getSelectByCodeType : function(e1, tyep, boxWidth, boxHeight){
	  		var that = this;
	  		var w = (boxWidth && boxWidth>0)? boxWidth :180;
			var h = (boxHeight && boxHeight>0)? boxHeight :180;
	  		var data = that.getCodeData(tyep);
	  		data.unshift({'name':'请选择','code':''});
	  		e1.ligerComboBox({
	  			data: data,
	  			textField :"name",
	  			valueField :"code", 
	  			selectBoxWidth: w,
	  			selectBoxHeight: h
	  		});
	  	},
	  	getSelectByData : function(e1, data, boxWidth, boxHeight){
	  		var w = (boxWidth && boxWidth>0)? boxWidth :180;
	  		var h = (boxHeight && boxHeight>0)? boxHeight :180;
	  		e1.ligerComboBox({data: data,selectBoxWidth: w, selectBoxHeight: h});
	  	},
	  	setSelectInitValue : function(id, initName, initValue){
	  		$('#'+id).val(initName);
			$('#'+id+'_val').val(initValue);
	  	},
	  
	  	getCodeDataById : function(id){
	  		var result=null;
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
	  	 var t;
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
		    // var searchbox = $(this).parent().nextAll('div.searchbox:first');
		    //searchbox.slideToggle('fast');    
		 }); 
	}
	  	
};
