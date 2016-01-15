/**
 * 获得GTC时间
 * @param val : yyyy-MM-dd
 * @param type : 值为2则表示获取到当日24点的毫秒时间戳;值为非2则表示获取当日0点的时间
 */
function getGTC(val, type) {
	var array = val.split("-");
	var now = null;
	now = new Date(array[0], array[1] - 1, array[2]);
	if (type == 2) {
		var s = 86400000;
		return now.getTime() + s;
	}
	return now.getTime();
}

/**
 * 日期格式化 yyyy-MM-dd HH:mm:ss
 */
Date.prototype.Format = function(formatStr)   
{   
 var str = formatStr;   
 var Week = ['日','一','二','三','四','五','六'];  

 str=str.replace(/yyyy|YYYY/,this.getFullYear());   
 str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));   

 str=str.replace(/MM/,this.getMonth()>9?this.getMonth().toString():'0' + this.getMonth());   
 str=str.replace(/M/g,this.getMonth());   

 str=str.replace(/w|W/g,Week[this.getDay()]);   

 str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
 str=str.replace(/d|D/g,this.getDate());   

 str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
 str=str.replace(/h|H/g,this.getHours());   
 str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
 str=str.replace(/m/g,this.getMinutes());   

 str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
 str=str.replace(/s|S/g,this.getSeconds());   

 return str;   
};

 
/**
 * 时间戳转日期格式
 */
function stamp2datetime(stamp) {
	if (stamp != "" && stamp != null) {
		var d = new Date(parseFloat(stamp));
		return d.getFullYear()
				+ "-"
				+ (((d.getMonth() + 1) >= 10) ? (d.getMonth() + 1) : ("0" + (d.getMonth() + 1)))
				+ "-"
				+ ((d.getDate() >= 10) ? d.getDate() : ("0" + d.getDate()))
				+ " "
				+ ((d.getHours() >= 10) ? d.getHours() : ("0" + d.getHours()))
				+ ":"
				+ ((d.getMinutes() >= 10) ? d.getMinutes() : ("0" + d.getMinutes()))
				+ ":"
				+ ((d.getSeconds() >= 10) ? d.getSeconds() : ("0" + d.getSeconds()));
	}
}

/**
 * 日期转时间戳
 * @param str_time 如"2014-09-01 11:24:32"
 * @returns 如1409541872000
 */
function date2stamp(str_time){
	if(str_time.length<11){
		str_time += " 0:0:0";
	}
	
    var new_str = str_time.replace(/:/g,'-');
    new_str = new_str.replace(/ /g,'-');
    var arr = new_str.split("-");
    var datum = new Date(Date.UTC(arr[0],arr[1]-1,arr[2],arr[3]-8,arr[4],arr[5]));
    return strtotime = datum.getTime();
}