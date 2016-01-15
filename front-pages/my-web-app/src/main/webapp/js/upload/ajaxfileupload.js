/*jQuery.extend({

    createUploadIframe: function(id, uri)
	{
            var frameId = 'jUploadFrame' + id;
            $("#"+frameId).unbind().remove();
        	var io=$('<iframe id="' + frameId + '" name="' + frameId + '" width="200" height="200" />');
            if(window.ActiveXObject) {
                if(typeof uri== 'boolean'){
                	io.attr("src",'javascript:false');
                }
                else if(typeof uri== 'string'){
                    io.attr("src",uri);
                }
            }
            io.css({display:'none'});
            $("body").append(io);
            return $(frameId)[0];			
    },
    createUploadForm: function(id, fileElementId)
	{
		var formId = 'jUploadForm' + id;
		var fileId = 'jUploadFile' + id;
		var form = $('<form action="" method="POST" name="' + formId + '"  id="' + formId + '" enctype="multipart/form-data"></form>');	
		var oldElement = $('#' + fileElementId);
		var newElement = $(oldElement).clone();
		$(oldElement).attr('id', fileId);
		$(oldElement).before(newElement);
		$(oldElement).appendTo(form);
		$(form).css('display', 'none');
		$(form).appendTo('body');		
		return form;
    },

    ajaxFileUpload: function(s) {
        s = jQuery.extend({}, jQuery.ajaxSettings, s);
        var id = s.fileElementId;        
		var form = jQuery.createUploadForm(id, s.fileElementId);
		jQuery.createUploadIframe(id, s.secureuri);
		var frameId = 'jUploadFrame' + id;
		var formId = 'jUploadForm' + id;	
		
        var uploadCallback = function(isTimeout){			
			var data = $("#"+frameId).contents().find("body").text();
			if(data.indexOf("\n")>0){
				alert("上传图片失败！");
			}else{
				s.success(data, status );
			}
			$("#"+frameId).remove();
			$("#"+formId).remove();
        };
     
        try 
		{
			var form = $('#' + formId);
			$(form).attr('action', s.url);
			$(form).attr('method', 'POST');
			$(form).attr('target', frameId);
            if(form.encoding)
			{
                form.encoding = 'multipart/form-data';				
            }
            else
			{				
                form.enctype = 'multipart/form-data';
            }			
            $(form).submit();

        } catch(e) 
		{			
            jQuery.handleError(s, xml, null, e);
        }
        if(window.attachEvent){
        	 document.getElementById(frameId).attachEvent('onload', uploadCallback);
        }
        else{
            document.getElementById(frameId).addEventListener('load', uploadCallback, false);
        } 		
        return {abort: function () {}};	

    }
});

*/


$(function() {

	$.extend({
		createUploadIframe : function(id, uri) {
			var frameId = 'jUploadFrame' + id;
			$("#" + frameId).unbind().remove();
			var io = $('<iframe id="' + frameId + '" name="' + frameId
					+ '" width="300" height="300" />');
			if (window.ActiveXObject) {
				if (typeof uri == 'boolean') {
					io.attr("src", 'javascript:false');
				} else if (typeof uri == 'string') {
					io.attr("src", uri);
				}
			}
			io.css({
				display : 'none'
			});
			$("body").append(io);
			return $(frameId)[0];
		},
		createUploadForm : function(id, fileElementId) {
			var formId = 'jUploadForm' + id;
			var fileId = 'jUploadFile' + id;
			var form = $('<form  action="" method="POST" name="' + formId
					+ '"  id="' + formId
					+ '" enctype="multipart/form-data"></form>');
			var oldElement = $('#' + fileElementId);
			var newElement = $(oldElement).clone();
			$(oldElement).attr('id', fileId);
			$(oldElement).before(newElement);
			$(oldElement).appendTo(form);
			$(form).css('display', 'none');
			$(form).appendTo('body');
			return form;
		},

		ajaxFileUpload : function(s) {
			s = jQuery.extend({}, jQuery.ajaxSettings, s);
			var id = s.fileElementId;
			var form = jQuery.createUploadForm(id, s.fileElementId);
			jQuery.createUploadIframe(id, s.secureuri);
			var frameId = 'jUploadFrame' + id;
			var formId = 'jUploadForm' + id;

			var uploadCallback = function(isTimeout) {
				var data = $("#" + frameId).contents().find("body").text();
				if (data.indexOf("\n") > 0) {
					alert("上传图片失败！");
				} else {
					s.success(data, status);
				}
				$("#" + frameId).remove();
				$("#" + formId).remove();
			};

			try {
				var form = $('#' + formId);
				$(form).attr('action', s.url);
				$(form).attr('method', 'POST');
				$(form).attr('target', frameId);
				if (form.encoding) {
					form.encoding = 'multipart/form-data';
				} else {
					form.enctype = 'multipart/form-data';
				}
				$(form).submit();

			} catch (e) {
				jQuery.handleError(s, xml, null, e);
			}
			if (window.attachEvent) {
				document.getElementById(frameId).attachEvent('onload',
						uploadCallback);
			} else {
				document.getElementById(frameId).addEventListener('load',
						uploadCallback, false);
			}
			return {
				abort : function() {
				}
			};

		}
	});
});
