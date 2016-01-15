<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/js/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/utils/DateUtil.js?v=g_version" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>

<!-- UI resource -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/account/inc/css/common/base.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/account/inc/css/common/common.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/account/inc/css/common/new_common.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/account/inc/css/pc/payment_pages.css">
<script src="<%=request.getContextPath()%>/pages/account/inc/scripts/checkbox.js"></script>
<script src="<%=request.getContextPath()%>/pages/account/inc/scripts/jquery.js"></script>
<script src="<%=request.getContextPath()%>/pages/account/inc/scripts/jquery.select.js"></script>
<script src="<%=request.getContextPath()%>/js/DateUtil.js"></script>
<script src="<%=request.getContextPath()%>/js/component.js"></script>
<script src="<%=request.getContextPath()%>/js/mask/mask.js" type="text/javascript"></script>
<!-- alert resource -->
<script src="<%=request.getContextPath()%>/js/artDialog/artDialog.source.js?skin=default"></script>
<script src="<%=request.getContextPath()%>/js/artDialog/plugins/iframeTools.source.js"></script>
<script src="<%=request.getContextPath()%>/js/artDialog/_doc/highlight/highlight.pack.js"></script>
<script src="<%=request.getContextPath()%>/js/artDialog/_doc/highlight/languages/javascript.js"></script>
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

var ctx = "${pageContext.request.contextPath}";
</script>



<!-- 分页 样式-->
<style type="text/css">
.page {
    display: inline-block;
    height: 37px; 
    width:100%;
    margin-bottom: 30px;
    margin-left: 30px;
    padding: 5px 5px;
}
.page a {
	background-color: #FFF; 
    float: left;
    font-size: 12px;
    margin-left: 5px;
    padding: 6px 8px 6px 10px;
    text-decoration: none;
    height:16px;
    line-height:16px;
    width:16px;
    border:1px solid #ccc;;
    text-align: center;
    color:#2e6ab1;
}

.page a:hover {
	
	background-color: #3585e1;
 	border:1px solid #ccc;
 	color:#fff
}

.page input {
    font-size: 12px;
    margin-left: 0px;
    padding: 5px 5px;
    text-align: center;
    width: 30px;
}
.page .GO {
    background-color: #dddddd;
    cursor: pointer;
    width: 60px;
    padding: 5px 5px;
}

</style>
