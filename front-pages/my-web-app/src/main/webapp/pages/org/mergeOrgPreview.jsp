<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>组织合并预览页面</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script src="mergeOrgPreview.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<style type="text/css">
	.list{width:90%;overflow:hidden;zoom:1;}
	.list li{float:left;width:350px;padding:5px}
</style>
</head>
<body style="padding: 10px; padding-left: 10px;">
	
	<div style="width: 100%; height: 90%">
		<div class="l-scroll"  style="width: 80%; height: 100%; margin: 10px; margin-left: 150px; float: left; border: 0px solid #ccc; overflow: none;">
			<div style="height: 20px;text-align:center;font-size:14px"><b>合并后结果预览</b></div>
			<div id="delDiv" style="height: 98%;padding:15px; border: 1px solid #ccc; overflow: auto;">
				<div id="split_div" class="searchtitle">
					<div><img src="../../images/spanIoc.png" alt="基本信息"/><span class="span">基本信息</span></div>
					<div class="navline"></div>
				</div>
				<ul id="org_01" class="list"></ul>
				
				<div id="split_div" class="searchtitle">
					<div><img src="../../images/spanIoc.png" alt="部署系统" /><span class="span">部署系统</span></div>
					<div class="navline"></div>
				</div>
				<ul id="deploysys_01"></ul>
				
				<div id="split_div" class="searchtitle">
					<div><img src="../../images/spanIoc.png" alt="绑定外部系统" /><span class="span">绑定外部系统</span></div>
					<div class="navline"></div>
				</div>
				<ul id="bindsys_01"></ul>
				
				<div id="split_div" class="searchtitle">
					<div class="navline"></div>
				</div>
				<span style="color:red">注：组织合并后，两个组织下的关联角色及用户，部署系统，绑定外部系统均会统一合并到保留组织的下面，不予以删除！</span>
			</div>
		</div>
	</div>
</body>
</html>