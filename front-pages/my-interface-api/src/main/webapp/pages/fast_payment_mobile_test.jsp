<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%
String root = request.getContextPath();
root += "".equals(root)?"/interface":"";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>运营管理平台－支付中心－一键支付移动端外部接口测试</title>
</head>

<body style="height: 90%; padding: 20px;">
<fieldset>
<legend>一键支付移动端外部接口测试</legend>
	<form name="submitForm" method="post" action="<%=root %>/upp/fastPaymentMobileController/payment">
		<div id="tableDiv" style="overflow: auto; height:360px;">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" align="center">
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商户号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="storeCode" id="storeCode" ltype="text"  validate="{required:true,minlength:1,maxlength:30}" />
					</td>
				</tr>
				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>所属用户ID:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="userId" id="userId" ltype="text" value="90e63c0e-f2d2-48b2-8b87-2fa1c9dd4750"  validate="{required:true,minlength:1,maxlength:30}" />
					</td>
				</tr>
				
				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>内部付款账号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="accountNo" id="accountNo" ltype="text" value="MAMOBILE1001"  validate="{required:true,minlength:1,maxlength:30}" />
					</td>
				</tr>
				
				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>订单金额:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="orderAmount" id="orderAmount" ltype="text"  validate="{required:true,minlength:1,maxlength:30}" />
					</td>
				</tr>
			
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>终端类型:</td>
					<td align="left" class="l-table-edit-td">
						<select type="select" name="clentType" id="clentType" ltype="text" validate="{required:true,minlength:1,maxlength:30}">
						   <option value="0">IMEI</option>
						   <option value="1" selected>MAC</option>
						   <option value="2">UUID</option>
						   <option value="3">other</option>
						</select>
					</td>
				</tr>				
							
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>终端标识:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="clentId" id="clentId" ltype="text" value="05-16-DC-59-C2-34" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商品类别:</td>
					<td align="left" class="l-table-edit-td">
						<select type="select" name="productCatalog" id="productCatalog" ltype="text" validate="{required:true,minlength:1,maxlength:30}">
						   <option value="1">虚拟产品</option>
						   <option value="3">公共事业缴费</option>
						   <option value="4">手机充值</option>
						   <option value="6">公益事业</option>
						   <option value="7">实物电商 </option>
						   <option value="8">彩票业务</option>
						   <option value="10">行政教育</option>
						   <option value="11">线下服务业</option>
						   <option value="13">微信实物电商</option>
						   <option value="14">微信虚拟电商</option>
						   <option value="15">保险行业</option>
						   <option value="16">基金行业</option>
						   <option value="17">电子票务</option>
						   <option value="18">金融投资</option>
						   <option value="19">大额支付</option>
						   <option value="20">其他</option>
						   <option value="21">旅游机票</option>
						   <option value="22">畅付D</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>商品名称:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="productName" id="productName" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>UA:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="userUa" id="userUa" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				
				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>Client IP:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="userIp" id="userIp" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				
				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>标识类型:</td>
					<td align="left" class="l-table-edit-td">
						<select type="select" name="identityType" id="identityType" ltype="text" validate="{required:true,minlength:1,maxlength:30}">
						   <option value="0">IMEI</option>
						   <option value="1">MAC地址</option>
						   <option value="2">用户ID</option>
						   <option value="3">用户Email</option>
						   <option value="4">用户手机号 </option>
						   <option value="5">用户身份证号</option>
						   <option value="6">用户纸质订单协议号</option>
						</select>
					</td>
				</tr>
				
				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>标识ID:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="identityId" id="identityId" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				
				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>后台回调URL:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="callbackUrl" id="callbackUrl" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				
				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>前台回调URL:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="fcallbackUrl" id="fcallbackUrl" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				
				<tr>
					<td align="right" class="l-table-edit-td"><span style="color: red;">*&nbsp;</span>业务订单号:</td>
					<td align="left" class="l-table-edit-td">
						<input type="text" name="workOrderNo" id="workOrderNo" ltype="text" validate="{required:true,minlength:1,maxlength:20}" />
					</td>
				</tr>
				
				
				<tr>
					<td colspan="5" align="center">
						<input type="submit" value="支付" id="Button1" class="l-button l-button-submit" />
					</td>
				</tr>
				
				
			</table>
		</div>
	</form>
</fieldset>

</body>
</html>