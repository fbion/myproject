<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>组织合并页面</title>
<jsp:include page="../all_css.jsp" />
<jsp:include page="../all_js.jsp" />
<script src="mergeOrg.js?v=g_version" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">

</script>
</head>
<body style="padding: 10px; padding-left: 10px;">
	
	<div style="width: 100%; height: 90%">
		<div class="l-scroll"  style="width: 350px; height: 100%; margin: 10px; margin-left: 150px; float: left; border: 0px solid #ccc; overflow: none;">
			<div style="height: 20px;text-align:center;font-size:14px"><b>删除组织</b></div>
			<div id="delDiv" style="height: 95%;padding:15px; border: 1px solid #ccc; overflow: auto;" >
				<div id="split_div" class="searchtitle">
					<div><img src="../../images/spanIoc.png" alt="基本信息"/><span class="span">基本信息</span></div>
					<div class="navline"></div>
				</div>
				<ul id="org_01"></ul>
				
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
			</div>
		</div>
		<div style="width: 30px; height: 20px; margin: 1px; margin-top: 150px; margin-left: 40px; float: left;">
				<img src="../../images/change.png" alt="组织对换" title="组织对换"  onClick="window.mergeOrg._change();" />
		</div>
		<div class="l-scroll" style="width: 350px; height: 100%; margin: 10px; margin-right: 150px; float: right; border: 0px solid #ccc; overflow: none;">
			<div style="height: 20px;text-align:center;font-size:14px"><b>保留组织</b></div>
			<div  id="retainDiv"  style="height: 95%;padding:15px; border: 1px solid #ccc; overflow: auto;" >
				<div id="split_div" class="searchtitle">
					<div><img src="../../images/spanIoc.png" alt="基本信息" /><span class="span">基本信息</span></div>
					<div class="navline"></div>
				</div>
				<ul id="org_02"></ul>
				
				<div id="split_div" class="searchtitle">
					<div><img src="../../images/spanIoc.png" alt="部署系统" /><span class="span">部署系统</span></div>
					<div class="navline"></div>
				</div>
				<ul id="deploysys_02"></ul>
				
				<div id="split_div" class="searchtitle">
					<div><img src="../../images/spanIoc.png" alt="绑定外部系统" /><span class="span">绑定外部系统</span></div>
					<div class="navline"></div>
				</div>
				<ul id="bindsys_02"></ul>
			</div>
		</div>
	</div>
</body>
</html>