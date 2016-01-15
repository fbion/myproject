/**
 * 此文件中所有方法都依赖于ligerUI,升级ligerUI时,应该考虑此文件内容与新版本的兼容
 * 
 * @author  by malq 
 * @date    2012-09-24
 */
var MALQ_TREE = {
	
		loadData: function (g, node, url, param){
			var that = this;
            //var g = this, p = this.options;
			g.loading = $("<div class='l-tree-loading'></div>");
            g.loading.show();
            var ajaxtype = param ? "post" : "get";
            param = param || [];
            //请求服务器
            $.ajax({
                type: ajaxtype,
                url: url,
                data: param,
                dataType: 'json',
                success: function (data){
                    if (!data) return;
                    $(data).each(function(){ 
                    	if(that.isChildren(this)){
                    		this['isexpand']='true';
                    	}else{
                    		this['children']=[];
    						this['isexpand']='false';
                    	}                    	             	
					}); 
                    g.loading.hide();
                    g.append(node, data);
                    g.trigger('success', [data]);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown){
                    try{
                        g.loading.hide();
                        g.trigger('error', [XMLHttpRequest, textStatus, errorThrown]);
                    }catch (e){
                    	g.loading.hide();
                    }
                }
            });
        },
        
        isChildren : function(obj){
        	var result = true;
        	var data = MALQ_CODE.getParentCodeData();
        	 $(data).each(function(){ 
        		 if(this.pid == obj.id){
        			 result = false;
        			 return false;
        		 }
        	 });
        	return result; 
        },
        
        //未完全选中
        getIncomplete: function (g){
            //var g = this, p = this.options;
            if (!g.options.checkbox) return null;
            var nodes = [];
            $(".l-checkbox-incomplete", g.tree).parent().parent("li").each(function (){
                var treedataindex = parseInt($(this).attr("treedataindex"));
                nodes.push({ target: this, data: g._getDataNodeByTreeDataIndex(g.data, treedataindex) });
            });
            return nodes;
        }
      
};
