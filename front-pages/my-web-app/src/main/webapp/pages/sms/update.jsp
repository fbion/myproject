<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>短信模板--更新</title>
<jsp:include page="/pages/all_css.jsp" />
<jsp:include page="/pages/all_js.jsp" /> 
<script src="<%=application.getContextPath()%>/pages/sms/update.js"></script>
</head>

 <body>
  <form id="form1">
	<table border="0" width="100%">
		<tr>
			<td align="center">
				<br>
				<h2>短信模板更新</h2>
				<br><br>
			</td>
		</tr>
		<tr>
			<td>
				<table border="0" width="100%">
					<tr>
						<td style="width:100px;height:30px;text-align:left">
							&nbsp;&nbsp;&nbsp;&nbsp;
							模板编码
						</td>
						<td style="width:500px;height:30px;text-align:left" vertical-align="center">
							 <input id="templateCode" value="${sms.templateCode}" type="text" /> 
							 <input id="code" value="${sms.templateCode}" type="hidden"/>
						</td>
					</tr>
					<tr>
						<td style="width:100px;height:30px;text-align:left">
							&nbsp;&nbsp;&nbsp;&nbsp;
							模板名称
						</td>
						<td style="width:500px;height:30px;text-align:left" vertical-align="center">
							 <input id="templateName" type="text" value="${sms.templateName}" class="liger-textbox"  style="width:300px"/> 
						</td>
					</tr>
					<tr>
						<td style="width:100px;height:30px;text-align:left">
							&nbsp;&nbsp;&nbsp;&nbsp;
							模板内容
						</td>
						<td style="width:500px;height:50px;text-align:left" vertical-align="center">
						  <textarea name="templateContent" id="templateContent"  cols="45" rows="5" style="width:300px"  >${sms.templateContent}</textarea>
						</td>
					</tr>
					<tr>
						<td style="width:100px;height:30px;text-align:left">
							&nbsp;&nbsp;&nbsp;&nbsp;
							模板状态
						</td>
						<td style="width:500px;height:50px;text-align:left" vertical-align="center">
							<select id="status">
								<option value="1" ${sms.status==1?'selected="selected"': ''}>启用</option>
								<option value="0" ${sms.status==0?'selected="selected"': ''}>禁用</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="height:30px;">
                        	&nbsp; 
						</td> 
						<td style="height:30px;">
							<br/>
							<input id="jsave" type="submit" value="  保存  "/>
						</td> 
					</tr>
				</table>
			
			
			</td>
		</tr>
	</table>
	</form>
 </body>
</html>
