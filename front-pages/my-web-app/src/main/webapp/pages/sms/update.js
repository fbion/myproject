$(function(){
	  $.metadata.setType('attr', 'validate');
    $('#form1').validate({
        rules: {
        	templateCode : {
        		required : true,
        		remote:{
    				url: root+"/sms/checkSmsCode.action",
    				type: 'POST', 
    				dataType: 'json', 
    				data:{ //要传递的数据
    				    'templateCode' : function(){return $("#templateCode").val();},
    				    'message' : 'templateCode'
    				 }
    			   }
        	},
    		 templateName:{
    			required : true
    		 },
    		templateContent:{
    			required : true
    		}
    	},
    	messages: {
    		templateCode:{remote: '此编码已存在'},
    	},
    	errorPlacement: function (lable, element){
    		CSM_VALIDATE.errorPlacement(lable, element);
	        },
        success: function (lable){
        	CSM_VALIDATE.success(lable);
        },
        submitHandler: function(){
        	showMask();
        	var templateContent = $.trim($('#templateContent').val());
          	var reg=new RegExp("<_>","g"); //创建正则RegExp对象  
          	var newstr=templateContent.replace(reg,"{_}");
           var  params = {
        		    code : $.trim($('#code').val()),
        		    templateCode : 	$.trim($('#templateCode').val()),
    				templateName : 	$.trim($('#templateName').val()),
    				templateContent :newstr,
    				status : $('#status').val()
        	};
          var flag = false;
          if($('#templateName').val()!=null && ""!=$('#templateName').val()){
          	flag = true;
          }else{
          	$.ligerDialog.warn('模板名称不能为空');
          }
          if(flag){
        	    var url = root + '/sms/ajaxUpdate.action';
         	   $.ajax({

     	          type: "post",

     	          url: url,

     	          data: params,

     	          dataType: "json",

     	          success: function(data){
     	        	  				 hideMask();
				     	        	 if(data==1){
				 	    				$.ligerDialog.waitting('短信模板更新成功！'); setTimeout(function () {
				 	    					$.ligerDialog.closeWaitting();
				 	    					window.parent.location.href='index.action';
				 	    				}, 2000);
				 	    			} else{
				 	    				$.ligerDialog.warn('对不起，短信模板更新失败！');
				 	    			}
     	                   },
     	          error : function(e,s){
     	        	 hideMask();
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
           			}else if(e && e.statusText=="parsererror"){//Filter Error
   	        			var resObj=e.responseText;
   	        			if(resObj)
   	        				//alert(resObj);
   	        				JSWK.clewBox(resObj,'clew_ico_fail',1000);
   	        			var forwardPath = e.getResponseHeader("forwardPath");
   	        			if(forwardPath){
   	        				JSWK.clewBox('您所请求的资源不合法或者发生错误','clew_ico_fail',failShowTime);
   	        				window.location = forwardPath;
   	        			}
   	        			return;
           			}
     	        	/*  JSWK.clewBox('提交数据时发生异常','clew_ico_fail',1000)*/
     	          }

     	      });
	          /*	$.post(url, params, function(data){
	          		if(data==1){
	    				$.ligerDialog.waitting('短信模板更新成功！'); setTimeout(function () {
	    					$.ligerDialog.closeWaitting();
	    					window.parent.location.href='index.action';
	    				}, 2000);
	    			} else{
	    				$.ligerDialog.warn('对不起，短信模板更新失败！');
	    			}
	    		});*/
          }
        }
    });
});