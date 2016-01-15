/**
 * �ͻ��������˽�������
 * @param u
 * @param d
 * @param h
 * @param fn
 * @param er
 * @param sync
 * @param scope
 * @param time
 * @param mask
 * 
 * @author  by malq 
 * @date    2012-06-11
 */
function JAjax(u, d, h, fn, er, sync, scope, time){		
	var str = u.substring(u.lastIndexOf('/'));
	var mask = u.indexOf('packageServiceJson')>0 && (str.indexOf('add')>0 || str.indexOf('update')>0 || str.indexOf('pay') || str.indexOf('showLoading'))>0?true:false;
    //去掉参数所有空格
	var dd=[];
    $(d).each(function(){
    	if(this.name){
    		dd.push({name:this.name, value:$.trim(this.value)});
    	}else{
    		for(var key in this){
    			dd.push({name:key, value:$.trim(this[key])});
    		}
    	}
    });
    d=dd;
    //alert(JSON.stringify(d))
	$.ajax({
        		url : u,
        		type : "POST",
        		timeout : time || 30000,
        		data : d ||{},
        		dataType : h || "html",
        		cache : false,
        		async : (sync == true || sync == false) ? sync : true,
        		beforeSend : function(){      			
        			if(mask == true){
        				//setTimeout(function(){
        					$.blockUI({ 
        						overlayCSS:{backgroundColor:'#F8F8FF'},//#F8F8FF,#EDEDED
    	        				message: "<div class='csm-grid-loading'></div><div class='csm-grid-loading-mes'>处理中，请稍候...</div>",
    	        				css : {width:'150',left:'40%',textAlign:'left'}					
    	        			});  
        				//},100);      				
        			}	        			      			
        		},       		
        		success : function(req, err){
        			if(mask == true)
        				$.unblockUI();        			
        			if (fn)
        				if (fn instanceof Function){
        					if (!scope){
        						fn.call(this, req);
        					}else{
        						fn.call(scope, req);
        					}
        				}
        		},
        		error:function(e, s){
        			if(mask == true)
        				$.unblockUI();
        			if(e && e.status=="403"){
        				JSWK.clewBox("抱歉，您没有操作该功能的权限",'clew_ico_fail',failShowTime);
        				return;
        			}else if(e && e.status=="500"){//Internal Server Error
        				var resObj=jQuery.parseJSON(e.responseText);
        				if(resObj)
        					JSWK.clewBox(resObj.message,'clew_ico_fail',failShowTime);
        				var forwardPath = e.getResponseHeader("forwardPath");
        				if(forwardPath){
        				  alert('您所请求的资源不合法或者发生错误!');
        				  window.location = forwardPath;
        				}
        				return;
        			}
        			if (er)
        				if (er instanceof Function){
        					er.call(this, e);
        				}
        		}
        	});
};

