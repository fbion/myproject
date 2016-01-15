<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String mobileNo = (String)session.getAttribute("mobileNo");
	String accountNo = (String)session.getAttribute("accountNo");
%>

<html>
<head>
	<title>车旺-个人中心-安全设置-修改密保问题</title>
	<jsp:include page="/pages/all_js.jsp" />
	<jsp:include page="/pages/all_css.jsp" />
	<script type="text/javascript">
		//重载验证码  
		function reCode(obj){
			obj.src = "<%=request.getContextPath()%>/imgCheckCode?d=" + new Date();
		}
		var mobileNo = '<%=mobileNo%>';
		var accountNo = '<%=accountNo%>';
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/utils/md5.js" ></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/paymentPassword/changeCryptoguard.js" ></script>
</head>
<body>
	<!--right-->
	<div class="w800 fr" style="float: left; margin: 0;padding: 0;">
		<div class="mycar">
			<div class="pub-tit">修改安全保护问题</div>
			<div class="pub-box pt40 pb40">
				<div class="account-process step2 "><!--四步分别对应类名step1/step2/step3/step4-->
					<div id="greed-line"></div>
					<ul class="icon-step">
						<li class="icon-step3" id="icon-step-one"><span>1</span></li>
						<li class="icon-step4" id="icon-step-two"><span>2</span></li>
						<li class="icon-step4" id="icon-step-three"><span>3</span></li>
					</ul>
					<ul class="txt-step">
						<li class="txt-step1" id="txt-step-one">
							<p class="f16 mb5">身份验证</p>
						</li>
						<li class="txt-step2 w100" id="txt-step-two">
							<p class="f16 mb5">修改安保问题</p>
						</li>
						<li class="txt-step3" id="txt-step-three">
							<p class="f16 mb5">修改成功</p>
						</li>
					</ul>
				</div>
				<div class="a-tc">您正在使用 “回答安全保护问题+验证支付密码” 进行校验，请选择您记得答案的问题进行回答</div>
				<div class="form account-form">
					<div id="form-one">
						<table>
			                <tr>
								<th class="w160"><span class="r-star"></span>请选择安全保护问题：</th>
								<td>
									<select id="cryptoguard-query" style="display: inline;border: 1px solid #d5d9de;color: #b4b4b4; display: block;height: 35px;line-height: 35px;padding: 0 10px;width: 245px;">
										<option value="">请选择</option>
									</select>
								</td>
								<td></td>
								<td id="cryptoguard-query-back"></td>
							</tr>
							<tr>
								<th>
									<span class="r-star"></span>答案：
								</th>
								<td>
									<input class="i-text" type="text" id="cryptoguard-answer"/>
								</td>
								<td></td>
								<td id="cryptoguard-answer-back"></td>
							</tr>
							<tr>
								<th>
									<span class="r-star"></span>支付密码：
								</th>
								<td>
									<input class="i-text" type="password" id="proving-pay-pwd"/>
								</td>
								<td>
									<a id="forget-pay-pwd">忘记支付密码？</a>
								</td>
								<td id="proving-pay-pwd-back"></td>
							</tr>
							<tr>
								<th>
									<span class="r-star"></span>&nbsp;
								</th>
								<td>
									<a id="table-one-button" class="a-btn2">下一步</a>
								    <a id="to_changeSafety_home" class="a-td">重新选择验证方式</a>
								</td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="form-two" style="display: none">
						<table>
							<tr>
								<th class="w160">
									<span class="r-star"></span>问题1：
								</th>
								<td class="edit-s">
									<select name="b" id="query-one" style="display: inline;border: 1px solid #d5d9de;color: #b4b4b4; display: block;height: 35px;line-height: 35px;padding: 0 10px;width: 245px;">
										<option value="">请选择</option>
									</select>
								</td>
								<td id="query-one-back"></td>
							</tr>
							<tr>
								<th>
									<span class="r-star"></span>答案：
								</th>
								<td>
									<input class="i-text" type="text" value="" id="answer-one" />
								</td>
								<td id="answer-one-back"></td>
							</tr>
							<tr>
								<th class="w160">
									<span class="r-star"></span>问题2：
								</th>
								<td class="edit-s">
									<select name="c" id="query-two" style="display: inline;border: 1px solid #d5d9de;color: #b4b4b4; display: block;height: 35px;line-height: 35px;padding: 0 10px;width: 245px;">
										<option value="">请选择</option>
									</select>
								</td>
								<td id="query-two-back"></td>
							</tr>
							<tr>
								<th>
									<span class="r-star"></span>答案：
								</th>
								<td>
									<input class="i-text" type="text" value="" id="answer-two" />
								</td>
								<td  id="answer-two-back"></td>
							</tr>
							<tr>
								<th class="w160">
									<span class="r-star"></span>问题3：
								</th>
								<td class="edit-s">
									<select name="d" id="query-three" style="display: inline;border: 1px solid #d5d9de;color: #b4b4b4; display: block;height: 35px;line-height: 35px;padding: 0 10px;width: 245px;">
										<option value="">请选择</option>
									</select>
								</td>
								<td  id="query-three-back"></td>
							</tr>
							<tr>
								<th>
									<span class="r-star"></span>答案：
								</th>
								<td>
									<input class="i-text" type="text" value="" id="answer-three" />
								</td>
								<td  id="answer-three-back"></td>
							</tr>
							<tr>
								<th>
									<span class="r-star"></span>&nbsp;
								</th>
								<td>
									<a id="table-two-button" class="a-btn2">下一步</a>
								</td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="form-three" style="display: none">
						<table class="tc">
						    <tr><td colspan="2">
							    <table width="100%">
								    <tr>
									    <td><span>问题一：</span><span id="quary-one-proving"></span></td>
									    <td><span id="answer-one-proving"></span></td>
									</tr>
								    <tr>
									    <td><span>问题二：</span><span id="quary-two-proving"></span></td>
									    <td><span id="answer-two-proving"></span></td>
									</tr>
								    <tr>
									    <td><span>问题三：</span><span id="quary-three-proving"></span></td>
									    <td><span id="answer-three-proving"></span></td>
									</tr>
								</table>
								</td>
							</tr>
							<tr class="a-left">
								<th>
									<span class="r-star"></span>&nbsp;
								</th>
								<td>
									<a id="submit-button" class="a-btn2">确定</a>
									<a id="back-change-home" class="a-td">返回修改</a>
								</td>
							</tr>
						</table>
					</div>
					<div id="form-four" style="display: none">
						<div class="account-prompt">
		    				<p class="one">修改成功，请牢记安全保护问题答案</p>
						</div>
						<div class="account-bot-btn" id="back-home"><input type="button" value="返回资金账户首页"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>