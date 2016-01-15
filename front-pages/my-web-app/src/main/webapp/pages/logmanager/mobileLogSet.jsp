<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<% 
String root = request.getContextPath();
%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>日志管理</title>
    <link href="../../css/all.css" rel="stylesheet" type="text/css" />
	<jsp:include page="../all_js.jsp" />
	<script type="text/javascript" src="<%=root%>/pages/logmanager/mobileLogSet.js" ></script>
	<script type="text/javascript">
		var root = "<%=root%>";
    </script>
</head>

<body style="padding: 4px; padding-left:30px;padding-top:10px;overflow: hidden;">


     <form name="form1"  id="form1">
     <input type="hidden" id="id" name="id"/>
    
     <div id="tableDiv" style="overflow: auto; border: 0px solid #f5f5f5;">
     
     	<div id="requiredDiv" style="width: 100%;"> 
     	
     		<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
						
				<tr style="height: 20px;">
					<td colspan="2">
						<div id="split_div"></div>
					</td>
				</tr>
				
				<tr>
					<td align="right" class="l-table-edit-td">编码:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="code" id="code" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td">状态:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="status" id="status" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>日期:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="handleTime" id="handleTime" />
					</td>
				</tr>
				<tr style="height: 20px;"><td colspan="2"></td></tr>
	         	<tr>                           
                	<td  colspan="2" align="center"  width="90"><input value="清 除之前日志"  type="button"  id="logdel"  onclick="logSet.delLog();" /></td>
            	</tr>        
       
            <tr>
            	<td colspan="2"><div id="split_div_togglebtn"></div></td>
            </tr>
                	     		
	     	</table> 	
     	</div>
     	
   </div> 
   
  </form>
  
  
  
</body>

</html>
