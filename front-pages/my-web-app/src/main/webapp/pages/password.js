/**
 * 修改密码js
 * 
 * @author  by malq 
 * @date    2012-06-11
 */
var Password=function(){
   this.passAlertWindow=null;
   this.subWindow=null;
   this.passwordStong=0;
   this.modifyUrl=root+'/login/modifyPassWord.action';
   this.logoutUrl=root + '/timeout.jsp';
};

Password.prototype = {
	
	//页面初始化操作
	init:function(){
		$('#mydiv').css('width', 520).css('height', 360);
		var mes='';
		if(parent.indexPage.subWindow)
			mes = '<h2>为保障您的安全，修改密码后，请重新登录</h2>';
		else
			mes = '<h2>您的密码过于简单，为保障您的安全，请修改您的密码</h2>';
			
		$('#messageDiv').html(mes);		
		// 在鼠标聚焦到输入框的时候更改样式
		$("#oldPass").focus();
		$('#upDatePass input').bind('focus',function() {
			var item = $(this).parent().parent();
			if(item.find("#login_form_ok").css("display")=="none"){
				item.find('#login_form_hint').show();
			}
		}).bind('blur',function(){
			var item = $(this).parent().parent();
			item.find('#login_form_hint').hide();
		});
		
		//密码框禁止复制粘贴
		$("input:password").bind("copy cut paste",function(e){
			return false;
		});
		
		var modifyPasswd = $("#isModifyPasswd").val();
		if(!modifyPasswd){
			$("#logoutDiv").show();
		}else{
			$("#commitNewPassDiv").css('padding-left','140px');
			$("#logoutDiv").hide();
		}
		
	},
  //测试某个字符是属于哪一类.
    charMode:function(iN){
	    if (iN>=48 && iN <=57) //数字
	    	return 1;
	    if (iN>=65 && iN <=90) //大写字母
	    	return 2;
	    if (iN>=97 && iN <=122) //小写
	    	return 4;
	    else
	    	return 8; //特殊字符
    },
    //计算出当前密码当中一共有多少种模式
    bitTotal: function (num){
    	modes=0;
	    for (var i=0;i<4;i++){
	    	if (num & 1) modes++;
	    	num>>>=1;
	    }
	    return modes;
    },
    //返回密码的强度级别
    checkStrong: function(sPW){
    	if (sPW.length<=4)
    		return 0; //密码太短
    	Modes=0;
	    for (var i=0;i<sPW.length;i++){
	    //测试每一个字符的类别并统计一共有多少种模式.
	    	Modes|=password.charMode(sPW.charCodeAt(i));
	    }
	    return password.bitTotal(Modes);
    },
  //当用户放开键盘或密码输入框失去焦点时,根据不同的级别显示不同的颜色
    pwStrength:function(pwd){
	    O_color="#eeeeee";
	    L_color="#FF0000";
	    M_color="#FF9900";
	    H_color="#33CC00";
	    if (pwd==null||pwd==''){
	    	Lcolor=Mcolor=Hcolor=O_color;
	    }else{
		    S_level=password.checkStrong(pwd);
		    switch(S_level) {
			    case 0:
			    	Lcolor=Mcolor=Hcolor=O_color;
			    	password.passwordStong=0;
			    case 1:
				    Lcolor=L_color;
				    Mcolor=Hcolor=O_color;
				    password.passwordStong=1;
				    break;
			    case 2:
				    Lcolor=Mcolor=M_color;
				    Hcolor=O_color;
				    password.passwordStong=2;
				    break;
			    default:
			    	Lcolor=Mcolor=Hcolor=H_color;
			    	password.passwordStong=3;
		    }
	    }
	    $("#strength_L").css('background', Lcolor);
	    $("#strength_M").css('background', Mcolor);
	    $("#strength_H").css('background', Hcolor);	
	    
	    return;
    },
    //输入校验
   validate:function(){
	   var _this = this;
	   var obj = arguments[0];
	   if(obj.id=="newPass"){
		   var _value = obj.value;
		   _this.pwStrength(_value);
		   if(!_value){
			      $('#newPassDiv').find('#login_form_hint').hide();
		    	  $('#newPassDiv').find('#login_form_ok').text('新密码不能为空').css('color', 'red').show();return;
		      }else if(_value && !(_value.length>5 && _value.length<21)){
		    	  $('#newPassDiv').find('#login_form_hint').hide();
		    	  $('#newPassDiv').find('#login_form_ok').text('密码长度不符合要求，长度6-20个字符').css('color', 'red').show();return;    	 
		      }else{
		    	  $('#newPassDiv').find('#login_form_ok').hide();
		      }
	   }
   },
   logoutSystem:function(){
	   top.location= password.logoutUrl;
   },
   
    //提交修改密码
    commitUpdatePass: function(){   
      var  oldPass=$("#oldPass").val();
      var  newPass=$("#newPass").val();
      var  confimPass=$("#confimPass").val(); 
      
      if(!oldPass){
    	  $('#oldPassDiv').find('#login_form_ok').text('原始密码不能为空').css('color', 'red').show();return;
      }else{
    	  $('#oldPassDiv').find('#login_form_ok').hide();
      }
      
      if(!newPass){
    	  $('#newPassDiv').find('#login_form_ok').text('新密码不能为空').css('color', 'red').show();return;
      }else if(newPass && !(newPass.length>5 && newPass.length<21)){
    	  $('#newPassDiv').find('#login_form_ok').text('密码长度不符合要求，长度6-20个字符').css('color', 'red').show();return;    	 
      }else{
    	  $('#newPassDiv').find('#login_form_ok').hide();
      }
      
      if(password.passwordStong < 2){
    	  $('#newPassDiv').find('#login_form_ok').text('密码过于简单，请重新设置').css('color', 'red').show();return;
      }else{
    	  $('#newPassDiv').find('#login_form_ok').hide();
      }
      
      if(newPass != confimPass){
    	  $('#confimPassDiv').find('#login_form_ok').text('两次输入的密码不一致，请重新输入').css('color', 'red').show();return;
      }else{
    	  $('#confimPassDiv').find('#login_form_ok').hide();
      }
      var oldPasswd = hex_md5(oldPass);
      var newPasswd = hex_md5(newPass);
      var params = {
    		  "userId":userId,
    		  "oldPasswd":oldPasswd,
    		  "newPasswd":newPasswd
      };
      
     /* JAjax(password.modifyUrl,params, function(data){
    	  if('1' == data){
   			 JSWK.clewBox('密码修改成功！','clew_ico_succ',1000);  
   			 setTimeout(function(){
   				 var modifyPasswd = $("#isModifyPasswd").val();
   				 if(!modifyPasswd){
   	   				 top.location= password.logoutUrl;
   	   			  }else{
   	   				self.parent.indexPage.subWindow.close();
   	   			  }
   				 
   			 }, 1000);
   			
    	  }else{
    		  JSWK.clewBox(data,'clew_ico_fail',2000);  
    	  }
      });*/
   
      /*JAjax(password.modifyUrl,params, 'json',function(data){
			alert(22);
			alert(data);
      },function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',1000);}, true);*/
      $.ajax({

          type: "post",

          url: password.modifyUrl,

          data: params,

          dataType: "json",

          success: function(data){
        	  		if("1"==data){
        	  			JSWK.clewBox('密码修改成功！','clew_ico_succ',1000);  
        	   			 setTimeout(function(){
        	   				 /*var modifyPasswd = $("#isModifyPasswd").val();
        	   				 if(!modifyPasswd){
        	   	   				 top.location= password.logoutUrl;
        	   	   			  }else{
        	   	   				self.parent.indexPage.subWindow.close();
        	   	   			  }*/
        	   				top.location= password.logoutUrl;
        	   			 }, 1000);
        	  		}else{
        	  			JSWK.clewBox('密码修改失败！','clew_ico_fail',1000);  
        	  		}
                   },
          error : function(data){
        	  JSWK.clewBox('提交数据时发生异常','clew_ico_fail',1000)
          }

      });

    }
};

$(function(){
	var password=new Password();
	window.password=password;  
	password.init();//初始化页面	
});

             