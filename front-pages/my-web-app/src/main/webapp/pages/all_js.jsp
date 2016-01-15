<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="<%=application.getContextPath()%>/js/jquery/jquery-1.5.2.js" type="text/javascript"></script>
<script type="text/javascript">
   var root = "<%=application.getContextPath()%>";
   var failShowTime = 4000;
   var subWidth=Math.round($(window).width() - ($(window).width() * 0.25));
   var subHeight=Math.round($(window).height() - ($(window).height() * 0.20));
</script>
<script src="<%=application.getContextPath()%>/js/jquery/jquery-validation/jquery.validate.min.js" type="text/javascript"></script> 
<script src="<%=application.getContextPath()%>/js/jquery/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/js/jquery/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/js/csm/csm_validate.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/js/jquery/jquery.autocomplete.js" type="text/javascript"></script> 
<script src="<%=application.getContextPath()%>/js/jquery/jquery.form.js?v=g_version" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/js/ligerUI_1.2.4/js/ligerui.all.js?v=g_version" type="text/javascript"></script> 
<script src="<%=application.getContextPath()%>/js/ligerUI_1.2.4/js/ligerGrid.showFilter.csm.js?v=g_version" type="text/javascript"></script> 
<script src="<%=application.getContextPath()%>/js/util_ajax.js?v=g_version" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/js/csm/csm-all.js?v=g_version" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/js/jquery/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/js/DateUtil.js?v=g_version" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/component.js?v=g_version" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/js/jquery.passwordStrength.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/js/SimpleValidator.js" type="text/javascript"></script>
<script src="<%=application.getContextPath()%>/pages/mask.js" type="text/javascript"></script>
<%-- <div id="jmask" style="position:fixed; left:0; top:0; width:100%; height:100%; display: none; z-index: 9999">	
    <div style="position:absolute; width:100%; height:100%; z-index:9999; background-color:#000; opacity:0.5; filter:alpha(opacity=50);"></div>
    <div class="" style="position: absolute; left:50%; top:50%; margin-left:-100px; width:200px; z-index:9999; color:#fff;">
        <img src="<%=request.getContextPath()%>/images/loading.gif"/>
	</div>
</div> --%>