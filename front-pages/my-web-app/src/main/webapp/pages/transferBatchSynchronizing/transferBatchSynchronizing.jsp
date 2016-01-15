<%@ page language="java" contentType="text/html; charset=UTF-8" isErrorPage="true" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title>交易同步</title>
	<jsp:include page="/pages/all_css.jsp" />
	<jsp:include page="/pages/all_js.jsp" />
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js" ></script>
	<style type="text/css">
		body{
			width:800px;
			margin: 0px auto;
		}
		.title_td{
			text-align: right;		
		}
		.context_td{
			text-align: left;	
		}
		table{
			width: 700px;
		}
		tr{
			height: 45px;
		}
		h3{
			text-align: center;
		}
		div{
			width: 700px;
			height: 200px;
			margin: 0px auto;
		}
	</style>
</head>



<body>
	<div class="div_1">
		<h3>以交易流水同步</h3>
		<form action="<%=request.getContextPath()%>/transferBatchSynchronizingByPTradeNos.action" method="post">
			<table width="800" border="0" align="center" cellpadding="0"		cellspacing="0">
				<tr>
					<td class="title_td">
						参数:	
					</td>
					<td class="context_td">
						<input type="text" name="paramList" style="width: 250px;"/>
						<span>参数以“，”分割。</span>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><input type="submit" value="开始同步" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="div_2">
		<h3>以交易时间同步</h3>
		<form action="<%=request.getContextPath()%>/transferBatchSynchronizingByTime.action" method="post">
			<table>
				<tr>
					<td class="title_td">
						起始时间:
					</td>
					<td class="context_td">
						<input type="text" name="startTime" style="width: 250px;"/>&nbsp;-
						<input type="text" name="endTime" style="width: 250px;"/>
						<span>交易时间为必填项</span>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><input type="submit" value="开始同步" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="div_3">
		<h3>发送最后一次交易标识</h3>
		<form action="<%=request.getContextPath()%>/sendLastTradeSign.action" method="post">
			<table>
				<tr>
					<td width="10%"  nowrap="nowrap"><label for="accountTimeStart">标识:</label>
						
					</td>
					<td class="context_td">
						<input type="text" name="sendLastTradeSign" style="width: 150px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
						<span>标识为必填项</span>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><input type="submit" value="发送标识" /></td>
				</tr>
			</table>
		</form>
		<div class="div_4">
		<h3>交易同步</h3>
		<form action="<%=request.getContextPath()%>/accountReconciliationByTime.action" method="post">
			<table>
				<tr>
					<td width="10%"  nowrap="nowrap"><label for="accountTimeStart">起止时间:</label></td>
					<td align="left" nowrap="nowrap" colspan="6">
						<input type="text" name="startTime" style="width: 100px;" />&nbsp;-
						<input type="text" name="endTime" style="width: 100px;" />
						<span></span>
					</td>
				</tr>
				<tr>
					<td width="10%"  nowrap="nowrap">
						<label for="accountTimeStart">平台编码:</label>
					</td>
					<td class="context_td">
						<input type="text" name="numberSign" style="width: 250px;"/>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><input type="submit" value="开始同步" /></td>
				</tr>
			</table>
		</form>	
		<h3>解锁第三方支付账户：</h3>
		<form action="<%=request.getContextPath()%>/unlockThirdPartPayAccount.action" method="post">
			<table>
				<tr>
					<td width="10%"  nowrap="nowrap">
						<label for="accountTimeStart">
							标识:
						</label>	
					</td>
					<td class="context_td">
						<input type="text" name="accountNo" style="width: 150px;" />
						<span>账户必填</span>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><input type="submit" value="发送标识" /></td>
				</tr>
			</table>
		</form>
		<h3>查询第三方支付账户</h3>
		<form action="<%=request.getContextPath()%>/queryThirdPartPayAccount.action" method="post">
			<table>
				<tr>
					<td width="10%"  nowrap="nowrap">
						<label for="accountTimeStart">
							标识:
						</label>	
					</td>
					<td class="context_td">
						<input type="text" name="accountNo" style="width: 150px;" />
						<span>账户必填</span>
					</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><input type="submit" value="发送标识" /></td>
				</tr>
			</table>
		</form>
		<table border="0" >
				<tr>
					<td class="context_td">
						账户号
					</td>
					<td class="context_td">
						余额
					</td>
					<td class="context_td">
						可用
					</td>
					<td class="context_td">
						状态
					</td>
					<td class="context_td">
						操作
					</td>
					
				</tr>	
				<tr>
					<td class="context_td">
						${accountNo}
					</td>
					<td class="context_td">
						${balance}
					</td>
					<td class="context_td">
						${unTakeCashBalance}
					</td>
					<td class="context_td">
						${status}
					</td>
					<td class="context_td">
					<script>
						if(${status}=='FROZEN'){
							<a href='<%=request.getContextPath()%>/unlockThirdPartPayAccount.action?accountNo=${accountNo}'>解锁</a>
						}
					</script>
					</td>
				</tr>
				
			</table>
	</div>
</div>

</body>
</html>