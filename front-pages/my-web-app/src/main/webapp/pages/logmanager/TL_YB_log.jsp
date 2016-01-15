<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>网关交互接口服务日志</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/logmanager/TL_YB_log.js" ></script>
</head>
<body style="padding: 4px; overflow: hidden;">
	<div>
		<table style="margin-left: 100px;">
			<tr>
				<td height="80px" width="60px">名称:</td>
				<td width="200px">
					<input type="text" id="name" style="height: 25px; width: 150px" />
				</td>
				<td width="60px">发送时间:</td>
				<td width="400px">
					<input type="text" id="requestStarttime" style="height: 25px; width: 150px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>-
					<input type="text" id="requestEndtime" style="height: 25px; width: 150px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
				</td>
				<td width="60px">响应时间:</td>
				<td width="400px">
					<input type="text" id="starttime" style="height: 25px; width: 150px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>-
					<input type="text" id="endtime" style="height: 25px; width: 150px" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
				</td>
				<td>
					<input type="button" id="submitbutton" value="查询" style="height: 25px; width: 50px"/>
				</td>
			</tr>
		</table>
	</div>
	<!--2. 列表页面 -->
	<div id="grid"></div>
	</div>
</body>
</html>