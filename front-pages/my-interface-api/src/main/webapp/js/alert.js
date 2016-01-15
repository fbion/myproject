/**
 * 信息显示窗口
 * 
 * @author  by malq 
 * @date    2012-06-11
 */
var JSWK = {
	
    clewBox: function(e, d, c) {
    	$("#JSWK_clewBoxDiv").remove();
        if (Jid("JSWK_clewBoxDiv")) {
            var b = "<span id='mode_clew' style='position:absolute;z-index: 10000;' class='clewBox_layer'>";
            b += "<span class='" + d + "'></span>" + e + "<span class='clew_end'></span>";
            b += "</span></div>";
            Jid("JSWK_clewBoxDiv").innerHTML = b;
            Jid("JSWK_clewBoxDiv").style.display = "";
        } else {
            var a = document.createElement("div");
            a.setAttribute("id", "JSWK_clewBoxDiv");
            a.className = "clewBox_layer_wrap";
            var b = "<span id='mode_clew' style='z-index: 10000;' class='clewBox_layer'>";
            b += "<span class='" + d + "'></span>" + e + "<span class='clew_end'></span>";
            b += "</span></div>";
            a.innerHTML = b;
            document.body.appendChild(a);
        }
        setTimeout(function() {
            if (Jid("JSWK_clewBoxDiv")) {
                Jid("JSWK_clewBoxDiv").style.display = "none";
            }
        },
        c ? c: 2000);
    },
    
	getRadio: function(id) {
		return "<div style=\"position:relative;top:30%;\"><input type=\"radio\" value=\""+id+"\" name=\"selectRadio\"  id=\"selectRadio\"/></div>";
	},
	getCheckbox: function(id) {
		return "<div style=\"position:relative;top:30%;\"><input type=\"checkbox\" value=\""+id+"\" name=\"selectCheckbox\" id=\"selectCheckbox\"/></div>";
	},
    
    setSelectValue: function(id, value) {
    	$('#'+id).val(value);  	
    	var text = $('#'+id).find("option:selected").text();
    	var newId = '#'+id+'_txt';
    	$(newId).val(text);
    	// $('#status_txt_val').val('2');    	
    }
	,
    //初始化车辆管理 车牌颜色 code表码值  htmlId select id  id修改时已经选择的表码值
    initSelect : function(code,htmlId,id){
    	JAjax(root + '/codeJson/getBaseCodeList.action', {"requestParam.equal.typeCode":code}, "json", function(data){ 
    		var str = "<option value=''>请选择</option>";
    		for(var i = 0; i < data.Rows.length;i++){
    			if(data.Rows[i].code == id){
    				str = str+"<option value='"+data.Rows[i].code+"' selected>"+data.Rows[i].name+"</option>";
    			}else{
    				str = str+"<option value='"+data.Rows[i].code+"'>"+data.Rows[i].name+"</option>";
    			}
    		}
    		$(htmlId).html(str);
    	}, function(){JSWK.clewBox('提交数据时发生异常','clew_ico_fail',CSM_failAlertTime);}, false);
    }	
   
};

function Jid(a) {
    if (typeof a == "string") {
        var b = document.getElementById(a);
        //b = JSWK.nodeProtoType(b);
        return b;
    } else {
        return a;
    }
}


//$(document).ready(function(){})可以简写成
//$(function(id, name){	
//	if (document.forms.length > 0) {   	
//        for (var i=0; i< document.forms[0].elements.length;i++) {
//            var oField = document.forms[0].elements[i];
//            //alert(oField.type);
//            if(id && oField.id == id){
//            	oField.focus();
//            	return;
//            }else if(name && oField.name == name){
//            	oField.focus();
//            	return;
//            }else if(oField.type == 'text' && oField.readonly !=true && oField.disabled !=true){            	
//                oField.focus();              
//                return; 
//            }
//        }
//    }	
//});


