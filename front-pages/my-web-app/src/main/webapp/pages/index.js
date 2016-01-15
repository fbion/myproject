/**
 * 首系统框架首页面
 * 
 * @author  by malq 
 * @date    2012-06-11
 */

var Index=function(){
   this.subWindow=null;
   this.tab=null;	
   this.accordion = null;
   this.menuData = null;
   //系统管理 跳转到 权限新增全局变量
   this.systemToNewPermFlag = false;
   this.systemToNewPermSystemId = null;
   this.systemToNewPermSystemName = null;
  //组织页跳转角色列表并新增角色
   this.orgAddRole_Id = null;
   this.orgAddRole_Name = null;
   this.userLogin_name=INDEX_USER_LOGIN;
};

Index.prototype = {
	//页面初始化操作
	init:function(){
		var that=this;
		 //布局
        $("#layout1").ligerLayout({leftWidth:190, height:'100%', heightDiff:-34, space:4, onHeightChanged: that.f_heightChanged });
        var height = $(".l-layout-center").height();
        //Tab
        $("#framecenter").ligerTab({ height: height, dragToMove :true});
        that.tab = $("#framecenter").ligerGetTabManager();        
        //面板
        $("#accordion1").ligerAccordion({ height: height - 24, speed: null });
        that.accordion = $("#accordion1").ligerGetAccordionManager();
        
        $(".l-link").hover(function (){
            $(this).addClass("l-link-over");
        }, function (){
            $(this).removeClass("l-link-over");
        });        
        
        $("#pageloading").hide();
	},
	resize:function(){
		var width=$(window).width();
		//把滚动条的宽度计算在内，防止右侧多余空白
		if (document.documentElement.clientWidth < document.documentElement.offsetWidth-4){
			width+=document.documentElement.offsetWidth-document.documentElement.clientWidth;
		}
		//最小分辨率（1224*768）设置
		if(width<1224){
			$(document.body).css({width:'1224px'});
		}else{
			$(document.body).css({width:'100%'});
		}
	},
	//高度改变
	f_heightChanged : function(options){
		var that=this;
        if(that.tab){
          that.tab.addHeight(options.diff);	
        }
        if (that.accordion && options.middleHeight - 24 > 0){
          that.accordion.setHeight(options.middleHeight - 24);	
        }  
    },
    //初始化导航菜单
	initMenu: function(){
		var that=this;
		
	 //生成导航菜单
        $.ajax({
  		   type: "POST",
  		   url:  root+'/login/getMenuList.action',
  		   //data: {'system.systemid':INDEX_SYS_SIGN, 'system.id':INDEX_SYS_ID},
  		   data: {'id':INDEX_USER_ROLEID},
  		   dataType: 'json',
  		   success: function(data){ 			   
  			   //alert(JSON.stringify(data));
  			   
  			   if(!(data && data.length>0)){
  				 $.ligerDialog.alert('当前用户和角色没有本系统菜单权限!', '提示', 'error');
  				 return;  
  			   }
  			
  			   var lodata=eval(data); 
  			   that.menuData = lodata;
  			  
 		      var separa = '<li style="margin-top:-11px;width:1px;"><img src="'+root+'/images/top/header/header_nav_separator.png"/></li>';
 		      $("#my_menu").append(separa);
 		      $(lodata).each(function(i){
 		    	 if(this.parentId==='0'){ 		    		
 		    		var li=$('<li style="width:86px;">');
 		    		if(this.id==='INDEX_00001'){
 		    			$(li).append('<span class="menuspan"><a href="#" style=""><p><img src="'+root+this.iconUrl+'" alt=""></p><p style="width:87px;text-align:center;">'+this.name+'</p></a>');
 		    		}else{
 		    			$(li).append('<span class="menuspan"><a href="#" style=""><p><img src="'+root+this.iconUrl+'" alt=""></p><p style="width:87px;text-align:center;">'+this.name+'</p></a>');
 		    		}
 		    		
 		    		if(that.isChildern(this.id, lodata)){
 		    			var ul = $("<ul>");
 		    			that.getChildren(this.id, ul, lodata);
 		    			$(li).append($(ul));
 		    		}
 		    		$("#my_menu").append($(li));
 		    		$("#my_menu").append(separa);
 		    	 }
 		    	  
 		      });
 		     $("#my_menu").wijmenu();
 		     $("body > div.header > div").css('width','85%');
  		   }
  		}); 

	},
	initFunctions:function(){
		window.INDEX_TEM_FUN_ITEMS = [];
		$.ajax({
 		   type: "POST",
 		   url:  root+'/login/getMyPageFunction.action',
 		   dataType: "json",	    		
 		   cache : false,
 		   success: function(data){
 			   if(data){
 				  INDEX_TEM_FUN_ITEMS = data;
 			   }
 		   },
 		   error : function(e, s){        			
 			   $.ligerDialog.alert('get page function error!');	
 		   }
 		}); 
	},
	//递归组装导航菜单 
	getChildren : function(id, ul, data){
		var that = this;
		$(data).each(function(i){ 
			if(id == this.parentId){
				var li=$("<li>");
				if(that.isChildern(this.id, data)){
		     		$(li).append("<a href=\"#\">"+this.name+"</a>");
		     	}else{
		     		$(li).append("<a href=javascript:indexPage.f_addTab('"+this.id+"');>"+this.name+"</a>");
		     	}
				$(ul).append($(li));
				var subul = $("<ul>");
				that.getChildren(this.id, subul, data);
			}
		});
		return ul;
	},
	
	//是否有子菜单
	isChildern : function(id, data){
		var result = false;
		$(data).each(function(){ 
			if(id == this.parentId){
				result = true;
				return false;
			}
		});
		return result;
	},
        
	//增加Tab
    f_addTab : function(tabid){
    	var that = this;
    	var text = "";
    	var url = "";
    	$(that.menuData).each(function(){ 
    		if(this.id==tabid){
    			text=this.name;
    			url=this.pageUrl;
    			return false;
    		}
    	});
    	if(url!=null){
    		url = url.substring(0,1)==='/'?root+url:root+'/'+url;
    	}
		//添加tab
		this.tab.addTabItem({tabid : tabid, text: text, url: url});
    },
    f_closeTab:function(tabid){
    	this.tab.removeTabItem(tabid);
    },
    //初始化代码信息-已失效
    initAllCodeInfo: function(){
    	if(window.CodeData && window.CodeData.length>0){    		
    		window.CodeData = window.CodeData; 
    	}else{   		
    		var url = root+'/simpleCode/findAllCode.action';	
        	JAjax(url,{}, 'json', function(data){
        		 window.CodeData = data;
        	}, function(){JSWK.clewBox('初始化代码信息异常!','clew_ico_fail',4000);}, true);
    	}
    },
    //初始化区划信息
    initAreaCodeInfo: function(){
    	var that = this; 
    	if(window.ZoneData && window.ZoneData.length>0){   		 
    		var data = that.getZoneData(window.ZoneData);
    		if(data.length>0)
    			window.ZoneData = data;
    	}else{   		   		
    		var url = root+'/codeJson/getAreaCode.action';	
    		JAjax(url,{}, "json", function(data){   			
    			data = that.getZoneData(data);
    			window.ZoneData = data;  			
    		}, function(){JSWK.clewBox('初始化代码信息异常!','clew_ico_fail',4000);}, true);
    	}
    },
    
    getZoneData: function(data){
    	var pid,code,tem;    		
		var zd = [];
		$(data).each(function(){
			if(this.pid && this.pid.length>0){
				return true;  			    			
			}
			code = $.trim(this.c);
			if('0000' == code.substring(code.length-4,code.length))
				pid = '0';
			else if('00' == code.substring(code.length-2,code.length))
				pid = code.substring(0,2)+'0000';
			else
				pid = code.substring(0,4)+'00';
			tem = {};
			tem['id']=code;
			tem['text']=$.trim(this.n);
			tem['pid']=pid;								
			zd.push(tem);
		});
		return zd;
    },
    
    getUserInfo: function(){
    	
    	$("#topOrgName").text(INDEX_USER_ORGNAME).attr('title',INDEX_USER_ORGNAME);
    	$("#topRoleName").text(INDEX_USER_ROLENAME).attr('title',INDEX_USER_ROLENAME);
    	$("#topUserName").text(INDEX_USER_NAME).attr('title',INDEX_USER_NAME); 
   
    },
    showUserInfo: function(){    
    	indexPage.subWindow = $.ligerDialog.open({url:root+'/pages/indexUserInfo.jsp',height:350, width:600,isHidden:false,isDrag:false,title:'当前用户信息'});
    	//MALQ_UI.open('indexUserInfo.jsp', 300, 600, '用户信息');
    },
    
    showOrgInfo: function(){
    	var url = root+'/pages/org/org-info.jsp?id='+INDEX_USER_ORGID;
    	$('#subPageOrgId').val(INDEX_USER_ORGID);
    	var subWidth=Math.round($(window).width() - ($(window).width() * 0.25));
    	var subHeight=Math.round($(window).height() - ($(window).height() * 0.20));
    	indexPage.subWindow = $.ligerDialog.open({url: url, height:subHeight, width:subWidth,isHidden:false,isDrag:false,title:'当前组织信息'});
    	//MALQ_UI.open('indexUserInfo.jsp', 300, 600, '用户信息');
    },
    
    //显示修改密码的页面
    showUpdatePassPage:function(){
    	indexPage.subWindow = indexPage.subWindow = $.ligerDialog.open({url:root+'/pages/password.jsp',height:450, width:800,isHidden:false,isDrag:false,title:'密码修改'});
    	//MALQ_UI.open('password.jsp', 400, 800, '密码修改');
    },
    
    //组织合并任务模块
    addOrgMergeTaskTab:function(){
    	var url = root+"/pages/org/org-merge-list.jsp";
		//添加tab
		this.tab.addTabItem({tabid : "tab_org_merge_task", text: "组织合并任务", url: url});
    }
    
};

$(function(){
	var indexPage=new Index();
	window.indexPage=indexPage;  
	indexPage.resize();//设置页面初始化大小
	indexPage.init();//初始化页面基本结构
	indexPage.initMenu();//初始化导航菜单
	indexPage.initFunctions();//初始化功能权限数据
	indexPage.initAreaCodeInfo();//初始区划信息	
	indexPage.initAllCodeInfo();//初始代码表信息	
	indexPage.getUserInfo();	 
	$(window).resize(function() {
		indexPage.resize();
	});
});

             