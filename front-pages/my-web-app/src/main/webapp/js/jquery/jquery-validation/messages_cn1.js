/*
 * Translated default messages for the jQuery validation plugin.

 * Locale: CN
 */
jQuery.extend(jQuery.validator.messages, {
        required: "该字段不能为空",
		remote: "请修正该字段",
		email: "请输入正确格式的电子邮件",
		url: "请输入合法的网址",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "请输入合法的数字",
		digits: "只能输入整数",
		creditcard: "请输入合法的信用卡号",
		equalTo: "请再次输入相同的值",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
		minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
		rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
		range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
		max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
		min: jQuery.validator.format("请输入一个最小为 {0} 的值"),
		isIdCardNo:jQuery.validator.format("请正确输入您的身份证号码"),
		phone: "请输入正确的手机号码",
		telphone: "请输入正确的电话号码",
		cardNumber:"请正确输入您的身份证号码",
		postalcode:"请输入正确的邮政编码",
		drivingLicense:"请正确输入您的驾驶证号码",
		businessLicense:"请输入正确的营业执照号码",
		password:"密码不少于九位且必须是字母数字和特殊字符的组合运用",
		OrganizationCode:"请输入合法的组织机构代码",
		LicenseNumber:"请输入合法的车牌号码",
        notnull:"不能为空"
});
$(document).ready(function() {
	
	
	
	 $("#signupForm").validate({
	
	        messages: {
	
	       
	   firstname: "请输入姓名",
	   email: {
	    required: "请输入Email地址",
	    email: "请输入正确的email地址"
	   },
	   password: {
	    required: "请输入密码",
	    minlength: jQuery.format("密码不能小于{0}个字 符")
	   },
	   confirm_password: {
	    required: "请输入确认密码",
	    minlength: "确认密码不能小于5个字符",
	    equalTo: "两次输入密码不一致"
	   }
	  }
	    });
	});


