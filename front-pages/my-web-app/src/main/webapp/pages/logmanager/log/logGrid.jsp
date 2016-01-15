<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<% 
String root = request.getContextPath();
%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>日志查询</title>
    
    <link href="<%=root %>/css/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
    <link href="<%=root %>/css/ligerUI/skins/Gray/css/all.css" rel="stylesheet" type="text/css" />     
    <link href="<%=root %>/css/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
    
    
    <script src="<%=root %>/js/jquery-1.5.2.min.js" type="text/javascript"></script>
 	
	<script src="<%=root %>/js/ligerui.min.js" type="text/javascript"></script> 
	
	 <script src="<%=root %>/js/util_ajax.js" type="text/javascript"></script>
	 
	<script src="<%=root %>/js/csm-all.min.js" type="text/javascript"></script>
	 	        
    <script src="<%=root %>/js/ligerGrid.showFilter.csm.js" type="text/javascript"></script> 
            
    <script src="logGrid.js" type="text/javascript" charset="utf-8"></script>
           
    <style type="text/css"> 
    	
   </style>
   <script type="text/javascript">
     var root = "<%=request.getContextPath()%>";
    </script>
   
</head>

<body style="padding: 4px; overflow: hidden;">

   
<div>

<div class="l-loading" style="display: block" id="pageloading">
	<!-- 正在加载内容的图片  -->
</div>



<div id="layout1" style="width:100%; margin:0 auto; margin-top:10px;">
	  		
		<form id="queryForm">
			<div id="queryDiv">
				<input type="hidden" id="id" name="id" />
				<table style="top:0px;" class="l-table-edit">
					<tr>
						<td width="15%" align="right" class="l-table-edit-td">系统标识：</td>
						<td width="10%" class="l-table-edit-td"><input type="text" id="systemName" name="systemName" /></td>
						
						<td width="10%" align="right" class="l-table-edit-td">日志级别：</td>
						<td width="15%" class="l-table-edit-td"><input type="text" id="level" name="level" /></td>
													
						<td width="15%" align="center" class="l-table-edit-td"><input id="query1" type="button" onclick="log4jManager.f_search();" value="查询"  class="l-button l-button-submit"/></td>
						<td width="35%" align="left" class="l-table-edit-td"><input id="query2" type="button" onclick="log4jManager.ff_search();" value="高级查询"  class="l-button l-button-submit"/></td>
						
					</tr>					
					
				</table>
			</div>		
		</form> 
			 
	 
</div>

<!-- <div id="layout2" style="width:100%; margin:0 auto; margin-top:1px;">  -->
	<div id="gridDiv" class="grid" style="margin: 0; padding: 0;width: 100%;">
		<!-- 表格内容  -->
   </div>


 
<div style="display: none;"></div>
 
 
</div>


</body>

</html>

