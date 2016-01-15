/**
 * this is csm page validate
 * 
 * @author  by malq 
 * @date    2014-02-26
 */
var CSM_VALIDATE = {
		  		  	
		errorPlacement : function(lable, element){
			if (element.hasClass('l-textarea')){
                element.addClass('l-textarea-invalid');
            }
            else if (element.hasClass('l-text-field')){		                
                element.parent().addClass('l-text-invalid');
            }
            $(element).removeAttr('title').ligerHideTip();
            $(element).attr('title', lable.html()).ligerTip();
		},
		success: function (lable){
        	var element = $('#' + lable.attr('for'));
            if (element.hasClass('l-textarea')){
                element.removeClass('l-textarea-invalid');
            }
            else if (element.hasClass('l-text-field')){
                element.parent().removeClass('l-text-invalid');
            }
            $(element).removeAttr('title').ligerHideTip();
        },
		csmErrorPlacement : function(element, message){
			if (element.hasClass('l-textarea')){
				element.addClass('l-textarea-invalid');
			}
			else if (element.hasClass('l-text-field')){		                
				element.parent().addClass('l-text-invalid');
			}
			$(element).removeAttr('title').ligerHideTip();
			$(element).attr('title', message).ligerTip();
		},
		csmSuccess: function (element){
			if (element.hasClass('l-textarea')){
				element.removeClass('l-textarea-invalid');
			}
			else if (element.hasClass('l-text-field')){
				element.parent().removeClass('l-text-invalid');
			}
			$(element).removeAttr('title').ligerHideTip();
		},
		validateForm:function(){
			var failds=$(".l-text-field,.l-textarea");
			var i=0;
			$(failds).each(function(){
				var flag=true;
				var maxLenth=$(this).attr('maxLength')=='-1'?null:$(this).attr('maxLength');
				var minLenth=$(this).attr('minLength')=='-1'?null:$(this).attr('minLength');
				var maxByteLenth=$(this).attr('maxByteLength')=='-1'?null:$(this).attr('maxByteLength');
				var minByteLenth=$(this).attr('minByteLength')=='-1'?null:$(this).attr('minByteLength');
				var regxStr = $(this).attr('regExp')=='-1'?null:$(this).attr('regExp');
				var message=$(this).attr('message')=='-1'?null:$(this).attr('message');
				if(message == undefined || message == null){
					message='';
					if(maxLenth || minLenth || maxByteLenth || minByteLenth){
						var min = minLenth ? (minByteLenth ? '最少['+minByteLenth+']个字节' : '最少['+minLenth+']个字符') : '';
						var max = maxLenth ? (maxByteLenth ? '最多['+maxByteLenth+']个字节' : '最多['+maxLenth+']个字符') : '';
						if(min != '')
							message = min;	
						if(max != '' ){
							if(message != '')
								message = message + ',';
							message = message + max;
						}
					}
				}
				if(flag && maxLenth){
					flag = this.value.length<=maxLenth;
				}
				if(flag && minLenth){
					flag = this.value.length>=minLenth;
				}
				if(flag && maxByteLenth){
					flag = this.value.length<=maxByteLenth;
				}
				if(flag && minByteLenth){
					flag = this.value.length>=minByteLenth;
				}
				if(flag && regxStr){
					var regX = new RegExp(regxStr);
					flag = regX.test(this.value);
				}
				if(!flag){
					i++;
					CSM_VALIDATE.csmErrorPlacement($(this),message);
				}else{
					CSM_VALIDATE.csmSuccess($(this));
				}
						
			});
			if(i>0){
				return false;
			}else{
				return true;
			}
		},
		initValidate:function(){
			$(".l-text-field,.l-textarea").each(function(){
				$(this).change(function (){
					CSM_VALIDATE.csmSuccess($(this));
					var maxLenth=$(this).attr('maxLength')=='-1'?null:$(this).attr('maxLength');
					var minLenth=$(this).attr('minLength')=='-1'?null:$(this).attr('minLength');
					var maxByteLenth=$(this).attr('maxByteLength')=='-1'?null:$(this).attr('maxByteLength');
					var minByteLenth=$(this).attr('minByteLength')=='-1'?null:$(this).attr('minByteLength');
					var regxStr = $(this).attr('regExp')=='-1'?null:$(this).attr('regExp');
					var message=$(this).attr('message')=='-1'?null:$(this).attr('message');
					if(message == undefined || message == null){
						message='';
						if(maxLenth || minLenth || maxByteLenth || minByteLenth){
							var min = minLenth ? (minByteLenth ? '最少['+minByteLenth+']个字节' : '最少['+minLenth+']个字符') : '';
							var max = maxLenth ? (maxByteLenth ? '最多['+maxByteLenth+']个字节' : '最多['+maxLenth+']个字符') : '';
							if(min != '')
								message = min;	
							if(max != '' ){
								if(message != '')
									message = message + ',';
								message = message + max;
							}
						}
					}
					var flag=true;
					if(flag && maxLenth){
						flag = this.value.length<=maxLenth;
					}
					if(flag && minLenth){
						flag = this.value.length>=minLenth;
					}
					if(flag && maxByteLenth){
						flag = this.value.replace(/[^\x00-\xff]/g,"**").length<=maxByteLenth;
					}
					if(flag && minByteLenth){
						flag = this.value.replace(/[^\x00-\xff]/g,"**").length>=minByteLenth;
					}
					if(flag && regxStr){
						var regX = new RegExp(regxStr);
						flag = regX.test(this.value);
					}
					
					if(!flag){
						CSM_VALIDATE.csmErrorPlacement($(this),message);
					}
				});
			});
		}
		
};
