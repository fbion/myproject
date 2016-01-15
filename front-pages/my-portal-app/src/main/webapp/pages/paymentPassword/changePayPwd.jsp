<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String mobileNo = (String)session.getAttribute("mobileNo");
	String accountNo = (String)session.getAttribute("accountNo");
%>

<html>
<head>
	<title>车旺-个人中心-安全设置-修改交易密码</title>
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
	<script type="text/javascript" src="<%=request.getContextPath()%>/pages/paymentPassword/changePayPwd.js" ></script>
</head>
<body>	
	<!--right-->
	<div class="w800 fr" style="float: left; margin: 0;padding: 0;">
		<div class="mycar">
			<div class="pub-tit">修改支付密码</div>
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
							<p class="f16 mb5">修改支付密码</p>
						</li>
						<li class="txt-step3" id="txt-step-three">
							<p class="f16 mb5">修改成功</p>
						</li>
					</ul>
				</div>
				<div class="form account-form">
					<div id="form-one">
						<table>
							<tr>
								<th width="115"><span class="r-star"></span>手机号：</th>
								<td><span class="mobileNo"></span></td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<th>
									<span class="r-star"></span>图形验证码：
								</th>
								<td>
									<input class="i-text" type="text" id="proving-img-text" />
								</td>
								<td>
									<img alt="看不清楚，换一张" src="<%=request.getContextPath()%>/imgCheckCode" onclick="reCode(this);" style="margin-left: 5px;"/>
								</td>
								<td id="proving-img-text-back"></td>
							</tr>
							<tr>
								<th>
									<span class="r-star"></span>短信验证码：
								</th>
								<td>
									<input class="i-text" type="text" id="proving-note-code" />
								</td>
								<td>
									<input class="a-btn" type="button" value="获取短信验证码" id="get-proving-note-code">
								</td>
								<td id="proving-note-text-back"></td>
							</tr>
							<tr>
								<th>
									<span class="r-star"></span>支付密码：
								</th>
								<td>
									<input class="i-text" type="password" id="proving-pay-pwd" />
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
									<a id="to_changePwd_home" class="a-td">重新选择验证方式</a>
								</td>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
					<div id="form-two" style="display: none">
						<table>
							<tr>
								<th width="115"><span class="r-star"></span>绑定手机号：</th>
								<td><span class="mobileNo"></span></td>
								<td></td>
							</tr>
							<tr>
								<th><span class="r-star"></span>密码强度：</th>
								<td class="acc-strength">
									<span id="pay-pwd-strength_L" style="background-color: #CFCFCF">弱</span>
									<span id="pay-pwd-strength_M" style="background-color: #CFCFCF">中</span>
									<span id="pay-pwd-strength_H" style="background-color: #CFCFCF">强</span>
								</td>
								<td></td>
							</tr>
							<tr>
								<th><span class="r-star"></span>新的支付密码：</th>
								<td>
									<input class="i-text grayTips" type="password" id="new-pay-pwd" />
									<span class="acc-what ml10" id="helpButton">
										<span class="tips" id="helpContext" style="display:none;">
											<em class="arrow"></em>
											支付密码为6~8位数字、字母、字符<br/>
											不要使用连续或者相同的数字<br/>
											为保障安全，建议不要使用银行密码，生日数字
										</span>
									</span>
								</td>
								<td id="new-pay-pwd-back"></td>
							</tr>
							<tr>
								<th class="w160">
									<span class="r-star"></span>确认新的支付密码：
								</th>
							    <td>
							    	<input class="i-text" type="password" id="pay-pwd-proving" />
							    </td>
							    <td id="pay-pwd-proving-back"></td>
							</tr>
							<tr>
								<th>
									<span class="r-star"></span>&nbsp;
								</th>
								<td>
									<a id="table-two-button" class="a-btn2">确定</a>
								</td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>
				<div id="form-three" style="display: none">
					<div class="account-prompt">
	    				<p class="one">设置成功，请牢记新的支付密码</p>
					</div>
					<div class="account-bot-btn" id="back-home"><input type="button" value="返回资金账户首页"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>