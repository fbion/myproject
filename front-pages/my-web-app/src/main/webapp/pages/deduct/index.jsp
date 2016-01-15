<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>扣款</title>
<jsp:include page="/pages/all_css.jsp" />
<jsp:include page="/pages/all_js.jsp" />
<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<script>
//var listDataStr = '${list}';
</script>
<script type="text/javascript" src="<%=application.getContextPath()%>/pages/deduct/index.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/deduct/jtestData.js"></script>
<script type="text/javascript">


</script>
</head>

 <body>
 <br/>
	<table style="font-size:12px" width="100%;" border="0" cellpadding="0" cellspacing="0">
		<tr >
			<td width="100" align="right" style="height:30px">商户编号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
			<td width="350" style="height:30px">
				<input id="storeId" type="text" class="liger-textbox" style="width:200px"/>
			</td>
			<td width="146" align="right" style="height:30px">付款类型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
			<td colspan="2" style="height:30px">
				<select id="tradeType">
					<option value="0">下线消费</option>
				</select>
			</td>
			<td width="146" align="right" style="height:30px"> 付款人姓名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
			<td width="353" style="height:30px">
				<input id="remitterName" type="text" class="liger-textbox" style="width:200px"/>
			</td>
          </tr>
		<tr >
		  <td align="right" style="height:30px"> 付款人开户行&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
		  <td style="height:30px">
				<select id="remitterBankCode">
					<option value="0">请选择开户行</option>
					<option value="1">建设银行</option>
					<option value="2">工商银行</option>
				</select>
		  </td>
		  <td align="right" style="height:30px"> 银行卡号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
		  <td colspan="2" style="height:30px"><input id="remitterBankcardNo" type="text" class="liger-textbox" style="width:200px"/></td>
		  <td align="right" style="height:30px"> 扣款金额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
		  <td style="height:30px"><input id="tradeAmount" type="text" class="liger-textbox" style="width:200px"/></td>
  </tr>
  <tr >
		  <td align="right" style="height:30px"> 扣款确认人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
		  <td style="height:30px"><input id="fundsConfirmId" type="text" class="liger-textbox" style="width:200px"/></td>
		  <td align="right" style="height:30px"> 申请状态&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
		  <td colspan="2" style="height:30px"><input id="applyStatus" type="text" class="liger-textbox" style="width:200px"/></td>
		  <td align="right" style="height:30px"> 充值类型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
		  <td style="height:30px"><input id="tradeType" type="text"  class="liger-textbox" style="width:200px"/></td>
  </tr>
  <tr >
		  <td align="right" style="height:30px"> 资金到账时间&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </td>
		  <td style="height:30px" colspan="2">
		  		<table>
		  			<tr>
		  				<td>
		  					<input type="text" id="amountArriveTime1"  name="amountArriveTime1" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:200px"/>
		  				</td>
		  				<td>&nbsp;&nbsp;-&nbsp;&nbsp;</td>
		  				<td>
		  					<input type="text" id="amountArriveTime2"  name="amountArriveTime2" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:200px"/>
		  				</td>
		  			</tr>
		  		</table>
		  </td>
		  <td width="100" align="right" style="height:30px"> 付款时间&nbsp; &nbsp;&nbsp;</td>
		  <td colspan="3" align="right" style="height:30px">
		    <table align="left">
		      <tr>
		        <td>
		          <input type="text" id="applyTime1" name="applyTime1" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:200px"/>
	            </td>
		        <td>&nbsp;&nbsp;-&nbsp;&nbsp;</td>
		        <td>
		          <input type="text" id="applyTime2" name="applyTime2" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:200px"/>
	            </td>
	          </tr>
    </table>	      </td>
  </tr>
</table>
 <br/>

	<div class="l-loading" style="display: block" id="pageloading"></div>
	<div id="mygrid" style="margin: 0; padding: 0"> </div>
	<div style="display: none;"></div>
	
	
 </body>
</html>
