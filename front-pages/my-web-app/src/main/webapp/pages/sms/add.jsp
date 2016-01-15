<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>短信模板--添加</title>
<jsp:include page="/pages/all_css.jsp" />
<jsp:include page="/pages/all_js.jsp" /> 
<script src="<%=application.getContextPath()%>/pages/sms/add.js"></script>
</head>

 <body>
 <form id="form1">
		<table border="0" width="100%">
			<tr>
				<td align="center">
					<br>
					<h2>短信模板添加</h2>
					<br><br>
				</td>
			</tr>
			<tr>
				<td>
					<table border="0" width="100%">
						<tr>
							<td style="width:100px;height:30px;text-align:left">
								&nbsp;&nbsp;&nbsp;&nbsp;
								模板名称
							</td>
							<td style="width:500px;height:50px;text-align:left" vertical-align="center">
								 <input id="templateName" type="text" class="liger-textbox" style="width:300px" validate="{required:true}"/> 
							</td>
						</tr>
						<tr>
							<td style="width:100px;height:30px;text-align:left">
								&nbsp;&nbsp;&nbsp;&nbsp;
								模板内容
							</td>
							<td style="width:500px;height:50px;text-align:left" vertical-align="center">
							  <textarea name="templateContent" id="templateContent" cols="45" rows="5"  style="width:299px;height:150px" ></textarea>
							</td>
						</tr>
						<tr>
							<td style="width:100px;height:30px;text-align:left">
								&nbsp;&nbsp;&nbsp;&nbsp;
								模板状态
							</td>
							<td style="width:500px;height:50px;text-align:left" vertical-align="center">
								<select id="status">
									<option value="1" selected="selected">启用</option>
									<option value="0">禁用</option>
								</select>
							</td>
						</tr>
						<tr>
							<td style="height:30px;" colspan="2">
	                        	&nbsp;&nbsp;&nbsp;&nbsp;
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
