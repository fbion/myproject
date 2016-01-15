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
    <link href="<%=root %>/css/togglebtn.style.css" rel="stylesheet" type="text/css" />
    
    
    <script src="<%=root %>/js/jquery-1.5.2.min.js" type="text/javascript"></script>
 	
	<script src="<%=root %>/js/ligerui.min.js" type="text/javascript"></script> 
	
	 <script src="<%=root %>/js/util_ajax.js" type="text/javascript"></script>
	 
	<script src="<%=root %>/js/csm-all.min.js" type="text/javascript"></script>
	 	        
    <script src="<%=root %>/js/ligerGrid.showFilter.csm.js" type="text/javascript"></script> 
            
    <script src="logSet.js" type="text/javascript" charset="utf-8"></script>
           
    <style type="text/css"> 
    	
   </style>
   <script type="text/javascript">
     var root = "<%=request.getContextPath()%>";
    </script>
   
</head>

<body style="padding: 4px; padding-left:30px;padding-top:10px;overflow: hidden;">


     <form name="form1"  id="form1">
     <input type="hidden" id="id" name="id"/>
    
     <div id="tableDiv" style="overflow: auto; border: 0px solid #f5f5f5;">
     
     	<div id="requiredDiv" style="width: 100%;"> 
     	   		
			<table cellpadding="0" cellspacing="0" align="left">			
				<tr style="height: 20px;">
					<td colspan="1">
						<div id="split_div"></div>
					</td>
				</tr>
				
	            <tr>                
	                <td style="padding-left: 10%">
	                		<div id="logSetGrid"></div>                
	                </td>                 
	            </tr>
	          
            
            <tr>
            	<td colspan="1"><div id="split_div_togglebtn"></div></td>
            </tr>
                	     		
	     	</table> 	
     	</div>
     	
     	
     	
     	<div id="otherDiv" style="width: 100%;" >    		
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="left">	
				<tr style="height: 20px;">
	            	<td colspan="1"><div id="split_div_null"></div></td>
	       		</tr>	
	       		
	       			
				<tr>                           
                	<td style="padding-left: 10%">
                		<table>            		
                			<tr>
	                			<td width="90">清除日期</td>
	                			<td  width="180"><input name="handleTime" type="text" id="handleTime" /></td>
	                			<td  align="center"  width="90"><input value="清  除"  type="button"  id="logdel"  onclick="logSet.delLog();" /></td>
                			</tr>
                		</table>         
                	</td>
            	</tr>        
           

	     	</table> 	
     	</div>
     	
    
   </div> 
   
  </form>
  
  
  
</body>

</html>
