<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>日志查询</title>
    <link href="../../css/all.css" rel="stylesheet" type="text/css" />
	<jsp:include page="../all_js.jsp" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/logmanager/system_log.js" ></script>
	<script type="text/javascript">
		var root = "<%=request.getContextPath()%>";
    </script>
</head>

<body style="padding: 4px; overflow: hidden;">


<div id="pageloading"></div>
	<div id="layout1" style="width: 100%; margin: 0 auto; margin-top: 4px;">
		
		<div id="myttt" position="center">
		<div id="accordion1" class="conBoxNoBorder">
				<form id="queryForm">
				<div id="queryDiv">
					<input type="hidden" id="id" name="id" />
					<table style="top:0px;" class="l-table-edit">
						<tr>
							<td width="10%" align="right" class="l-table-edit-td" nowrap="nowrap"><label for="beforTime">记录时间：</label></td>
							
							<td width="10%" align="right" class="l-table-edit-td" nowrap="nowrap">
								<input id="beforTime" name="beforTime" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate" /> 
							</td>
							<td width="1%" class="l-table-edit-td">-</td>
							<td width="10%" class="l-table-edit-td" nowrap="nowrap">
								<input name="endTime" id="endTime" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate" />
							</td>
							
							<td width="10%" align="right" class="l-table-edit-td">系统标识：</td>
							<td width="10%" class="l-table-edit-td"><input type="text" id="systemName" name="systemName" /></td>
							
							<td width="10%" align="right" class="l-table-edit-td">日志级别：</td>
							<td width="10%" class="l-table-edit-td"><input type="text" id="level" name="level" /></td>
														
							<td width="15%" align="center" class="l-table-edit-td"><input id="query1" type="button" onclick="log4jManager.f_search();" value="查询"  class="l-button l-button-submit"/></td>
							<!-- 
							<td width="35%" align="left" class="l-table-edit-td"><input id="query2" type="button" onclick="log4jManager.ff_search();" value="高级查询"  class="l-button l-button-submit"/></td>
							 -->
						</tr>					
					</table>
				</div>		
			</form> 
			
			<!-- 表格内容  -->
			<div id="gridDiv" class="grid"></div>
			</div>
		</div>
		
		<div position="right"  title="日志管理" id="accordion2" style="height: 100%"> 
	        <div title="日志管理">
	                <ul id="tree" style="margin-top:3px;"></ul>
	        </div>               
    	</div> 
		
	</div>
	
	
	
</body>
</html>

