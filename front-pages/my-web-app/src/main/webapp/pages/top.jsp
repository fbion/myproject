<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<link href="../css/top/top.css" rel="stylesheet" type="text/css" />
<style>
	.mr10{margin-right:3px;margin-left: 3px;}
</style>
<script type="text/javascript">
 var logoutUrl = "<%=request.getContextPath() %>/logout.jsp";
 function logout(){
	 $.ligerDialog.confirm('您确认要退出系统吗？', function (yes){
  	   if(yes){      
  		 top.location=logoutUrl;
  	   }
     }); 
 };
 
</script>

	<div class="header">
		<input type="hidden" id="subPageOrgId" name="subPageOrgId" />
		<img src="../images/top/header/logo.png" title="统一支付结算系统" class="logo" />
		<ul class="header_info">
			<li>
				<span>当前用户：</span>
				<!-- <span class="mr10">
					<span id="topOrgName">中交兴路车联网信息科技有限公司</span>
				</span>-
				<span class="mr10">
					<span id="topRoleName">角色信息</span>
				</span>- -->
				<span class="mr10">
					<a href="#" onclick="indexPage.showUserInfo();"><span id="topUserName">平台管理员</span></a>
				</span>
			</li>
			<li>
				<!-- <a href="javascript:void(0)" class="mr10" onclick="indexPage.f_addTab('excel下载')">下载</a> -->
				<a href="#" class="mr10" onclick="indexPage.showUpdatePassPage();">修改密码</a>
				<!-- <a href="#" class="mr10" onclick="indexPage.showUpdatePassPage();">修改密码</a> -->
				<a href='javascript:void(0);' onclick="logout();">退出</a>
			</li>
		</ul>
		
		<!-- 菜单 -->
		<ul id="my_menu" ></ul>				
		
	</div>