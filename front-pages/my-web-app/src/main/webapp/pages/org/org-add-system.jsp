<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>组织新增页面</title>

<link href="../../css/all.2.0.css" rel="stylesheet" type="text/css" />
<link href="../../LigerUI_1.2.4/skins/Tab/css/all.css" rel="stylesheet" type="text/css" />

<jsp:include page="../all_js.jsp" />

<script src="org-add-system.js?v=g_version" type="text/javascript" charset="utf-8"></script>


<style type="text/css">
        .middle input {
            display: block;width:30px; margin:2px;
        }
        .myLabelCss {margin:2px;left: 10px;}
        
        .l-form .l-fieldcontainer { padding:0; border:1px solid #FFFFFF; }

.l-form .l-fieldcontainer-over {
	border:1px dashed #ff6a00; cursor:pointer;
}

/* .l-form .l-fieldcontainer-selected {
	border:1px solid #ff6a00; cursor:pointer;
} */
.l-form .l-fieldcontainer-over .labelcontainer, .l-form .l-fieldcontainer-over .fieldlcontainer {
	 
}

.fieldproxy {
	border:1px dashed #d3d3d3; background:#FAFAFA;  position:absolute; z-index:99;
}
.l-form .l-fieldcontainer-r {
	border:1px solid #d3d3d3; background:#FAFAFA;  
} 

.l-textarea{ width:99%; margin:0px; height:80px;}
    </style>

</head>
<body style="overflow: visible; padding: 10px; padding-left: 40px;">

<div id="layout" style="width:99.2%; margin:0 auto; margin-top:4px; ">
        
</div> 
<!-- 
		<div id="tableDiv" style="overflow: auto; border: 0px solid #f5f5f5;">
		
		<form name="form1" method="post" action="orgJson/add.action" id="form1">
			<input name="model.uaaOrg.id" type="hidden" id="id" /> 
			<input name="category" type="hidden" id="category" /> 
			<input name="orgCode_exist" type="hidden" id="orgCode_exist" />
		
			<div id="requiredDiv" style="width: 100%;">
				<table cellpadding="0" cellspacing="0" class="l-table-edit"
					align="left">
					<tr>
						<td colspan="4"><div id="split_div"></div></td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td" style="width: 15%;"><span
							style="color: red;">*&nbsp;</span>组织名称:</td>
						<td align="left" class="l-table-edit-td" style="width: 30%;">
							<input name="model.uaaOrg.orgName" type="text" id="orgName" ltype="text" validate="{required:true,minlength:2,maxlength:60}" />							
						</td>
						
						<td align="right" class="l-table-edit-td" style="width: 15%;"><span
							style="color: red;">*&nbsp;</span>组织类型:</td>
						<td align="left" class="l-table-edit-td" style="width: 40%;">
							<input name="orgTypeText" type="text" id="orgTypeText" style="top: 1px;left:1px;" ltype="text" validate="{required:true,minlength:1,maxlength:60}" />							
							<input name="model.uaaOrg.orgType" type="hidden" id="orgType" />							
						</td>						
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td"><span
							style="color: red;">*&nbsp;</span>组织简称:</td>
						<td align="left" class="l-table-edit-td">
							<input name="model.uaaOrg.orgShortName" type="text" id="orgShortName" ltype="text" validate="{required:true,minlength:2,maxlength:15}" />
						</td>

						<td align="right" class="l-table-edit-td">上级名称:</td>
						<td align="left" class="l-table-edit-td">
							<input name="pTreeIdText" type="text" id="pTreeIdText" />
							<input name="pTreeId" type="hidden" id="pTreeId" />
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">组织编码:</td>
						<td align="left" class="l-table-edit-td">
							<input name="model.uaaOrg.orgCode" type="text" id="orgCode" ltype="text" validate="{minlength:3,maxlength:20}"/>
							<span id="orgCode_warn"></span>
						</td>
						<td align="right" class="l-table-edit-td">联系人:</td>
						<td align="left" class="l-table-edit-td">
							<input name="model.uaaOrg.businessPerson" id="businessPerson" type="text" validate="{required:false,minlength:1,maxlength:15}"/>
						</td>
					</tr>
					<tr>
						<td align="right" class="l-table-edit-td">营业执照:</td>
						<td align="left" class="l-table-edit-td">
						<input name="model.uaaOrg.businessNo" type="text" id="businessNo" ltype="text" validate="{minlength:1,maxlength:10, digits:true}" />
						</td>
						<td align="right" class="l-table-edit-td">联系电话:</td>
						<td align="left" class="l-table-edit-td">
								<input name="model.uaaOrg.businessPhone" type="text" id="businessPhone" ltype="text" validate="{required:false,minlength:1,maxlength:13}" />
						</td>
					</tr>
					
					<tr>
						<td align="right" class="l-table-edit-td">电子邮箱:</td>
						<td align="left" class="l-table-edit-td">
							<input name="model.uaaOrg.businessMail" type="text" id="businessMail" ltype="text" validate="{required:false,minlength:3,maxlength:30}" />
						</td>
						<td align="right" class="l-table-edit-td"></td>
						<td align="left" class="l-table-edit-td"></td>
					</tr>
					
					<tr>
						<td align="right" class="l-table-edit-td">组织地址:</td>
						<td align="left" class="l-table-edit-td" colspan="4">
							<table>
								<tr>
									<td><input name="corpProvince" type="text" id="corpProvince" /></td>
									<td><input name="corpCity" type="text" id="corpCity" /></td>
									<td><input name="corpDistrict" type="text" id="corpDistrict" /></td>
									<td><input name="model.uaaOrg.businessAddr" type="text" id="businessAddr"
										ltype="text" validate="{required:false,minlength:1,maxlength:30}" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="4"><div id="split_div_togglebtn"></div></td>
					</tr>
				</table>
			</div>
			
			<div style="display: none;">
				<input type="submit" value="保存" id="Button1"
					class="l-button l-button-submit" />
			</div>
			<div style="display: none;">
				<input type="reset" value="关闭" id="closeButton1"
					class="l-button l-button-reset" />
			</div>
			
		</form>	
			
		<div id="otherDiv" style="width: 100%;" >    		
					<table cellpadding="0" cellspacing="0" class="l-table-edit">	
						<tr>
			            	<td colspan="4"><div id="split_div_null"></div></td>
			       		</tr>
			       		
			       		 <tr>
			                <td align="right" width="350" class="l-table-edit-td">
								<div class="l-scroll"
									style="height: 300px;width:250px; margin: 10px; margin-right: 1px; float: right; border: 0px solid #ccc; overflow: none;">
									<div style="height: 20px;" align="left">已关联系统:</div>
									<ul id="yTree"
										style="height: 95%; text-align: left; border: 1px solid #ccc; overflow: none; OVERFLOW: auto;"></ul>
								</div>
							</td>
			               
			                <td align="center" width="30px" class="l-table-edit-td">	              
								<div style="height: 20px; margin: 1px; margin-top: 0px;">
									<div>
										<input type="button" value="&lt;&lt;" id="Button1" onClick="window.bindSystem.addBind();" />
									</div>
									</br>
									<div>
										<input type="button" value=">>" id="Button1" onClick="window.bindSystem.delBind();" />
									</div>
								</div>
			                </td>
			                
			                <td align="left" colspan="2" class="l-table-edit-td">
			                	<div class="l-scroll"
									style="height: 300px;width:250px; margin: 10px; margin-left: 10px; float: left; border: 0px solid #ccc; overflow: none;">
									<div style="height: 20px;">未关联系统:</div>
									<ul id="nTree"
										style="height: 95%;border: 1px solid #ccc; overflow: none; OVERFLOW: auto;"></ul>
								</div>
			                </td>
			            </tr> 
			            
			            <tr style="height:50px;">
			            	<td colspan="4" align="center">	</td>
			           </tr>
			       </table>
       </div>
			
			
			

			
	</div>	 -->
</body>
</html>