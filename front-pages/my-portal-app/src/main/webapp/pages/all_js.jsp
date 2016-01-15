<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>

<script type="text/javascript">
    	var root = "<%=root%>";
    	var mobileNoReg = /^1[0-9]{10}$/;
    	var imgReg = /^[a-zA-Z0-9]{4}$/;
    	var mobileCodeReg = /^[0-9]{6}$/;
    	var pwdReg = /^\S{6,8}$/;
    	var cryptoguardReg = /^[a-zA-Z0-9\u4e00-\u9fa5\?]{2,40}$/;
</script>

<script src="<%=root%>/js/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="<%=root%>/js/jquery/jquery.js" type="text/javascript"></script>
<script src="<%=root%>/js/jquery/jquery.select.js" type="text/javascript"></script>
<script src="<%=root%>/js/mask/mask.js" type="text/javascript"></script>
<script src="<%=root%>/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=root%>/js/utils/DateUtil.js" type="text/javascript"></script>
<script src="<%=root%>/js/code_util.js"  type="text/javascript"></script>
<!-- alert resource -->
<script src="<%=root%>/js/artDialog/artDialog.source.js?skin=default"></script>
<script src="<%=root%>/js/artDialog/plugins/iframeTools.source.js"></script>
<script src="<%=root%>/js/artDialog/_doc/highlight/highlight.pack.js"></script>
<script src="<%=root%>/js/artDialog/_doc/highlight/languages/javascript.js"></script> 
<script type="text/javascript">

artDialog.fn.shake = function (){
    var style = this.DOM.wrap[0].style,
        p = [4, 8, 4, 0, -4, -8, -4, 0],
        fx = function () {
            style.marginLeft = p.shift() + 'px';
            if (p.length <= 0) {
                style.marginLeft = 0;
                clearInterval(timerId);
            };
        };
    p = p.concat(p.concat(p));
    timerId = setInterval(fx, 13);
    return this;
};

window.alert=function(txt){
	var dialog = art.dialog.through({
	    title: '提示信息',
	    width: 220,// 必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致artDialog收缩
	    content: txt,
	    icon: 'chewang',
	});
	dialog.shake && dialog.shake();
};

</script>
<div id="jmask" style="position:fixed; left:0; top:0; width:100%; height:100%; display: none; z-index: 10000">	
    <div style="position:absolute; width:100%; height:100%; z-index:10000; background-color:#000; opacity:0.5; filter:alpha(opacity=50);"></div>
    <div class="" style="position: absolute; left:50%; top:50%; margin-left:-100px; width:200px; z-index:11; color:#fff;">
        <img src="<%=request.getContextPath()%>/images/mask/loading.gif"/>
	</div>
</div>