<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";

String chptPrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANIjCEAy/jOSJYyCGXwa5af+Co8/cr9K3W8QmjpSKKffjiumc8X09A24GTwRnVrhQALVYQcVvgkZEk7ON3ktkZ3yqwJvlvsHky1VXh+54vqYiik0QpDrQv/IKh+20+pyg7+X9UgHhezNskflemUfEKXv1XQLr/kW6qoh2It6cIBhAgMBAAECgYEAiNrb8sPDHl8eKUZJxrPVwfUHd4fA+aiOUlkjuLvtUnecJf2ABPqlP5IYjTCDpL5ya+YADRcx7sbvT3eqVTdCT8ZKZxswt1sq7wBieEvxZW9YfnPSVAGiVBeyrjHm71OVTj/i7ddggbP1CMs3OqCV8qgBeTF9Mi5l5qTPjLhcJuECQQD/YMsaYVV/AA8DMFH6ek/ZdZrBsS2uezqB1DksV4d6aRqs5NVpSgNLJ/xQc4J/KF7PLz1whGvIhK7AWW0mm3QVAkEA0qYI5mT6c6Djc0QlyBVlifCYNcsHKKM4rFF5i21Arq/JoT9pmHM0ryLBhbI+nuVwfSvWjQZeCJ5zj7b8NldyHQJAEmhzu28QrpreeihdgGSYpOApS5Tt6gnP6dPWWy/kABrbZWMJTxGasywqr9Hjsi7CxsRs9VNWQZnuzsaja4kBEQJBAJn9IIJ0jRYcJknsJoZ3Bcxp4otoiSou784gnW7Mhj1takODXMlCZuAk0z3OSLLV3X7TnjtnzTQVyTrlNMjw8g0CQQCvJzD8MUuYMv31BXQ2+O7YZNSjuyefhWX4GfnP5LhRtdksPNWa96r/sT2vc6VPQ5nofLHo7d8NnxwqxBmbS0PZ";
String chptPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSIwhAMv4zkiWMghl8GuWn/gqPP3K/St1vEJo6Uiin344rpnPF9PQNuBk8EZ1a4UAC1WEHFb4JGRJOzjd5LZGd8qsCb5b7B5MtVV4fueL6mIopNEKQ60L/yCofttPqcoO/l/VIB4XszbJH5XplHxCl79V0C6/5FuqqIdiLenCAYQIDAQAB";
String chptCode = "201501201602491637563";
String uppPrivateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAPGE6DHyrUUAgqep/oGqMIsrJddJNFI1J/BL01zoTZ9+YiluJ7I3uYHtepApj+Jyc4Hfi+08CMSZBTHi5zWHlHQCl0WbdEkSxaiRX9t4aMS13WLYShKBjAZJdoLfYTGlyaw+tm7WG/MR+VWakkPX0pxfG+duZAQeIDoBLVfL++ihAgMBAAECgYAw2urBV862+5BybA/AmPWy4SqJbxR3YKtQj3YVACTbk4w1x0OeaGlNIAW/7bheXTqCVf8PISrA4hdL7RNKH7/mhxoX3sDuCO5nsI4Dj5xF24CymFaSRmvbiKU0Ylso2xAWDZqEs4Le/eDZKSy4LfXA17mxHpMBkzQffDMtiAGBpQJBAPn3mcAwZwzS4wjXldJ+Zoa5pwu1ZRH9fGNYkvhMTp9I9cf3wqJUN+fVPC6TIgLWyDf88XgFfjilNKNz0c/aGGcCQQD3WRxwots1lDcUhS4dpOYYnN3moKNgB07Hkpxkm+bw7xvjjHqI8q/4Jiou16eQURG+hlBZlZz37Y7P+PHF2XG3AkAyng/1WhfUAfRVewpsuIncaEXKWi4gSXthxrLkMteM68JRfvtb0cAMYyKvr72oY4Phyoe/LSWVJOcW3kIzW8+rAkBWekhQNRARBnXPbdS2to1f85A9btJP454udlrJbhxrBh4pC1dYBAlz59v9rpY+Ban/g7QZ7g4IPH0exzm4Y5K3AkBjEVxIKzb2sPDe34Aa6Qd/p6YpG9G6ND0afY+m5phBhH+rNkfYFkr98cBqjDm6NFhT7+CmRrF903gDQZmxCspY";
String uppPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDxhOgx8q1FAIKnqf6BqjCLKyXXSTRSNSfwS9Nc6E2ffmIpbieyN7mB7XqQKY/icnOB34vtPAjEmQUx4uc1h5R0ApdFm3RJEsWokV/beGjEtd1i2EoSgYwGSXaC32ExpcmsPrZu1hvzEflVmpJD19KcXxvnbmQEHiA6AS1Xy/vooQIDAQAB";
String uppCode = "201501211146539567052";

String json = "{\"identityType\":\"2\",\"identityId\":\"ea32affa-3e4-41c7-962d-9addd9d3e7fe\",\"clentType\":\"2\","+
	"\"accountNo\":\"MA1423460374157329\","+
	"\"callbackURL\":\"http://www.4000966666.com\","+
	"\"userLoginName\":\"malqtest\",\"merchantOrderRemark\":\"油卡充值\","+
	"\"merchantOrderAmount\":\"0.01\",\"clentId\":\"ea32affa-3ef1-41c7-962d-9addd9d3e7fe\","+
	"\"merchantOrderNo\":\"YW1421346329048\",\"fcallbackURL\":\"http://upp.sinoiov.net/xxx\",\"userId\":\"ea32affa-3ef1-41c7-94d-9addd9d3e7fe\",\"productCatalog\":\"1\",\"userUa\":\"http://icard.sinoiov.net/images/ua.gif\",\"productName\":\"油卡充值\"}";
	
	
String myurl = "http://cashier2.test-95155.com/accountController/cashier.action";
	
String url = com.ctfo.upp.http.HttpUtils.getCashierDeductMoneyUrl(json, myurl, chptPrivateKey, uppPublicKey, chptCode);	
	
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>运营管理平台－支付中心－业务订单测试</title>
    <style>
    	 .l-table-edit {margin-left: 20px;}
	     .l-table-edit-td{ padding:6px;}    
	     .l-button-submit{width:80px; float:left; margin-left:25%; padding-bottom:2px;}
	     .l-button-reset{width:80px; float:left; margin-left:60px; padding-bottom:2px;}
	     .l-verify-tip{ left:230px; top:120px;}
	</style>
	
	<jsp:include page="all_css.jsp" />
	<jsp:include page="all_js.jsp" />
	
    <script type="text/javascript">
     function to_chisher(){
    	 var url ="<%=url%>";
    
    	 //alert(url);
    	 
    	 window.location.href = url;
     }
    
    </script>
    
</head>
<body style="height: 90%; padding: 20px;">

<center><h2>油卡充值</h2></center>
<hr />

		<input type="hidden" id="id" name="id" /> 
		<div id="tableDiv" style="overflow: auto; height: 100%">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				
				<tr>
					<td align="center" class="l-table-edit-td">用户账号：</td>
					<td align="left" class="l-table-edit-td">MA1423460374157329</td>
				</tr>
				<tr>
					<td align="center" class="l-table-edit-td">业务订单号：</td>
					<td align="left" class="l-table-edit-td">YW1421346329048</td>
				</tr>
				<tr>
					<td align="center" class="l-table-edit-td">充值金额：</td>
					<td align="left" class="l-table-edit-td">0.01</td>
				</tr>
				<tr>
					<td colspan="2" align="center" class="l-table-edit-td">					
						       <input type="button" onclick="to_chisher();" value="提交订单" id="Button1" class="l-button l-button-submit" />					
					</td>
				</tr>
			</table>
		</div>
	
	
	<div style="display: none"></div>
</body>

</html>