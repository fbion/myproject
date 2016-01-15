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
		}
};
