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
					item['text'] = perm.name;
  					item['icon'] = perm.iconUrl;
  					myFuns.push(item);
	  			}
	  		});	  		
	  		return myFuns;
	  	},
	  	getIndexTemFunItemsFromSrvice: function(){
			var re =[];	  		
  			$.ajax({
	    		   type: "POST",
	    		   url:  root+'/login/getMyPageFunction.action',
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
