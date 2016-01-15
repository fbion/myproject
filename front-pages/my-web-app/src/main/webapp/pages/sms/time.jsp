<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>定时提醒</title>
<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />
<script type="text/javascript" src="time.js?v=g_version"></script>
</head>
<body style="padding: 2px;">
	<div id="tableDiv" style="overflow: auto; height: 400px;">
		<form name="form1" method="post" id="form1">
			<input type="hidden" id="uuid" name="uuid" />
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td colspan="4">
						<div id="step1"></div>
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>时间:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="startTime" id="startTime" ltype="text" validate="{required:true}" class="time" onfocus="WdatePicker({dateFmt:'HH:mm:ss',qsEnabled:true,quickSel:['00:00','00:30','01:00','01:30','02:00','02:30','03:00','03:30','04:00','04:30','05:00','05:30','06:00','06:30','07:00','07:30','08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30','12:00','12:30','13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30','17:00','17:30','18:00','18:30','19:00','19:30','20:00','20:30','21:00','21:30','22:00','22:30','23:00','23:30','00:59'],minDate:'#F{$dp.$D(\'endTime\',{H:-1,m:-30})||\'0:00\'}',maxDate:'#F{$dp.$D(\'endTime\',{m:-30})}'})" />~
						<input type="text" name="endTime" id="endTime" ltype="text" validate="{required:true}"  class="time" onfocus="WdatePicker({dateFmt:'HH:mm:ss',qsEnabled:true,quickSel:['00:00','00:30','01:00','01:30','02:00','02:30','03:00','03:30','04:00','04:30','05:00','05:30','06:00','06:30','07:00','07:30','08:00','08:30','09:00','09:30','10:00','10:30','11:00','11:30','12:00','12:30','13:00','13:30','14:00','14:30','15:00','15:30','16:00','16:30','17:00','17:30','18:00','18:30','19:00','19:30','20:00','20:30','21:00','21:30','22:00','22:30','23:00','23:30','00:59'],minDate:'#F{$dp.$D(\'startTime\',{m:+30})||\'0:00\'}'})"/>
					</td>
				</tr>
				<tr>
					
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"  valign="top"><span style="color: red;">*&nbsp;</span>日期:</td>
					<td align="left" class="l-table-edit-td">
						<input id="day_1" type="checkbox" name="day" value="星期一" /><label for="day_1">周一</label>
						<input id="day_2" type="checkbox" name="day" value="星期二"/><label for="day_2">周二</label>
						<input id="day_3" type="checkbox" name="day" value="星期三"/><label for="day_3">周三</label>
						<input id="day_4" type="checkbox" name="day" value="星期四"/><label for="day_4">周四</label>
						<input id="day_5" type="checkbox" name="day" value="星期五"/><label for="day_5">周五</label>
						<input id="day_6" type="checkbox" name="day" value="星期六"/><label for="day_6">周六</label>
						<input id="day_7" type="checkbox" name="day" value="星期日"/><label for="day_7">周日</label>
					</td> 
				</tr>
			</table>
		</form>
	</div>
	<div style="display: none"></div>
</body>
</html>