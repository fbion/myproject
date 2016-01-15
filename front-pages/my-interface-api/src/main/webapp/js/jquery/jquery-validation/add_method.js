

jQuery.validator.addMethod("isMobile", function(value, element) {
var length = value.length;
return this.optional(element)
|| (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/
.test(value));
}, "<font color='red'>请正确填写您的手机号码</font>");

jQuery.validator.addMethod("isIdCardNo", function(value, element) {
	return this.optional(element) || isIdCardNo(value);
	}, "<font color='red'>请正确输入您的身份证号码</font>");

