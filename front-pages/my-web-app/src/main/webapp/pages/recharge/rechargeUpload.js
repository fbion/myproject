/*
 * All rights Reserved, Sinoiov Technology Co., LTD.
 * Copyright(C) 2012-2014
 * 2013年1月14日 下午2:33:27 
 */
(function() {
	var RechargeUpload = function() {
		this.init();
	};
	RechargeUpload.prototype = {
		config : {
			uploadUrl:root+'/recharge/upload.action'
		},
		init:function(){
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '上传汇款凭证', imgUrl);
		},
		uploadCallback : function(result){
			// 成功或失败的标志，由你决定
			if (result == 1) {

				alert("1成功了");

			}else

			  alert("2失败了");

		
		},
		upload:function(){
			var that=this;
			var voucherFileName=$("#fileName").val();
			if(voucherFileName==null||voucherFileName==''){
				JSWK.clewBox('请上传汇款凭证','clew_ico_fail',failShowTime);
				$("#fileName").focus();
				return false;
			}else if(!isValidateImg(voucherFileName)){
				JSWK.clewBox("汇款凭证图片格式不正确!系统支持JPG/GIF/PNG/BMP类型图片！", "clew_ico_fail", failShowTime);
				$("#fileName").focus();
				return false;
			}else{
				showMask();
				var options = {
						url :that.config.uploadUrl,
						type : "post",
						error : function() {
							hideMask();
							JSWK.clewBox('上传汇款凭证出现异常！', 'clew_ico_fail', failShowTime);
						},
						success : function(data) {
							hideMask();
							if(data != '0'){
								parent.window.rechargeAdd.success_alert('上传汇款凭证成功');
								parent.window.rechargeAdd.cache.imgUrlArr.push({name:'voucherFileName',value:data});
								parent.window.rechargeAdd.cache.imgUrl = data;
								parent.window.rechargeAdd._uploadFile();
								parent.window.rechargeAdd.cache.dialog_001.close();
							}else{
								JSWK.clewBox('上传汇款凭证失败', 'clew_ico_fail', failShowTime);
							}
						}
					};
					$("#uploadForm").ajaxForm(options).submit();
			}
		},
		uploadComplate : function(){
			showMask();
			$("#uploadForm").submit();
		}
	};
	
	$(document).ready(function() {
		window.rechargeUpload = new RechargeUpload();
	});
})();
