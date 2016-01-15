<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>组织信息页面</title>

<link href="../../css/all.css" rel="stylesheet" type="text/css" />
<jsp:include page="../all_js.jsp" />

<script src="org-info.js?v=g_version" type="text/javascript" charset="utf-8"></script>
</head>
<body style="overflow: visible; padding: 10px; padding-left: 40px;">

		<div id="tableDiv" style="overflow: auto; border: 0px solid #f5f5f5;">
		
		<form name="form1" method="post" action="orgJson/add.action" id="form1">
			<input name="model.uaaOrg.id" type="hidden" id="id" /> 
			<input name="orgCode_exist" type="hidden" id="orgCode_exist" />
		
			<div id="requiredDiv" style="width: 100%;">
				<table cellpadding="0" cellspacing="0" class="l-table-edit"
					align="left">
					<tr>
						<td colspan="4"><div id="split_div"></div></td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" style="width: 15%;">组织名称:</td>
						<td align="left" class="l-table-edit-td" style="width: 40%;">
							<span id="orgName"/>							
						</td>
						<td align="right" class="l-table-edit-td" style="width: 15%;">组织编码:</td>
						<td align="left" class="l-table-edit-td" style="width: 30%;">
							<span id="orgCode"></span>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">组织简称:</td>
						<td align="left" class="l-table-edit-td">
							<span id="orgShortName" />
						</td>

						<td align="right" class="l-table-edit-td">上级名称:</td>
						<td align="left" class="l-table-edit-td">
							<span id="pTreeIdText"></span>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">组织类型:</td>
						<td align="left" class="l-table-edit-td">
							<span id="orgType" />							
						</td>
						<td align="right" class="l-table-edit-td">联系人:</td>
						<td align="left" class="l-table-edit-td">
							<span id="businessPerson" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">营业执照:</td>
						<td align="left" class="l-table-edit-td">
						<span id="businessNo" />
						</td>
						<td align="right" class="l-table-edit-td">联系电话:</td>
						<td align="left" class="l-table-edit-td">
								<span id="businessPhone" />
						</td>
					</tr>
					
					<tr>
						<td align="right" class="l-table-edit-td">电子邮箱:</td>
						<td align="left" class="l-table-edit-td">
							<span id="businessMail" />
						</td>
						<td align="right" class="l-table-edit-td"></td>
						<td align="left" class="l-table-edit-td"></td>
					</tr>
					
					<tr>
						<td align="right" class="l-table-edit-td">组织地址:</td>
						<td align="left" class="l-table-edit-td" colspan="3">
							<span id="orgAddrress" />
						</td>
					</tr>
					<tr>
						<td colspan="4"><div id="split_div_togglebtn"></div></td>
					</tr>
					<tr>
			                <td align="left"  class="l-table-edit-td" colspan="4">
									<div style="margin:4px;float:left;padding-left: 10px;">
							         	<div>已关联系统列表</div>  
							         	<div id="listbox1"></div>
							     	</div>
							</td>
			               
			            </tr> 
				</table>
			</div>
			
		</form>	
	</div>
</body>
</html>