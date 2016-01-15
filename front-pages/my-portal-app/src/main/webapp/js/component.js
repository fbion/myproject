/**
 * 解决IE显示字段为NULL的问题
 */
function delAttrNull(objAttr){
	return objAttr==null?'':objAttr;
};

/**
 * 替换所有
 */
String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
};


//查看大图
function showBigImg(o){
	 $("#showBigImg").html("<img  src=\""+o.src.replace('min_','max_')+"\"/>").show().offset({top:($(window).height()-$("#showBigImg").outerHeight())/2+$(document).scrollTop(),left:($(window).width()-$("#showBigImg").outerWidth())/2+$(document).scrollLeft()});
};
//图片预览
function showImg(o) {
	var fileID = o.id;
	var Browser_Agent = window.navigator.userAgent;
	if (Browser_Agent.indexOf("Firefox") != -1) {
		var imgsrc = o.value.toLowerCase();
		if (imgsrc.substring(imgsrc.lastIndexOf(".")) == ".bmp"||imgsrc.substring(imgsrc.lastIndexOf(".")) == ".jpg" || imgsrc.substring(imgsrc.lastIndexOf(".")) == ".jpeg" || imgsrc.substring(imgsrc.lastIndexOf(".")) == ".gif" || imgsrc.substring(imgsrc.lastIndexOf(".")) == ".png") {
			$("#" + fileID + "_img").attr("src", window.URL.createObjectURL(o.files[0]));
			$("#" + fileID + "_img").css("display", "block");
			$("#" + fileID + "_showDIV").click(function() {
				$("#showBigImg").html("<img width=\"700\" height=\"500\" src=\"" + window.URL.createObjectURL(o.files[0]) + "\"/>").show().offset({
					top : ($(window).height() - $("#showBigImg").outerHeight()) / 2 + $(document).scrollTop(),
					left : ($(window).width() - $("#showBigImg").outerWidth()) / 2 + $(document).scrollLeft()
				});
			});
		}
	} else if (Browser_Agent.indexOf("MSIE") != -1) {
		var ietype = 0;
		var msie = Browser_Agent.indexOf("MSIE ");
		if (msie > 0) {
			ietype = parseInt(Browser_Agent.substring(msie + 5, Browser_Agent.indexOf(".", msie)));
		}
		if (ietype > 6) {
			o.select();
			var imgSrc = document.selection.createRange().text;
			var imgSrcLower = imgSrc.toLowerCase();
			if (imgSrcLower.substring(imgSrcLower.lastIndexOf(".")) == ".bmp" || imgSrcLower.substring(imgSrcLower.lastIndexOf(".")) == ".jpg" || imgSrcLower.substring(imgSrcLower.lastIndexOf(".")) == ".jpeg" || imgSrcLower.substring(imgSrcLower.lastIndexOf(".")) == ".gif" || imgSrcLower.substring(imgSrcLower.lastIndexOf(".")) == ".png") {
				var imgDivId = fileID + "_showDIV";
				$("#" + fileID + "_showDIV").focus();
				$("#" + fileID + "_showDIV").click(function() {
					var bigPreview = document.getElementById("showBigImg");
					bigPreview.innerHTML = "";
					var imgBigDiv = document.createElement("div");
					document.body.appendChild(imgBigDiv);
					imgBigDiv.style.width = 700;
					imgBigDiv.style.height = 500;
					imgBigDiv.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = scale)";
					imgBigDiv.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
					bigPreview.appendChild(imgBigDiv);
					bigPreview.style.cursor = "pointer";
					$("#showBigImg").show().offset({
						top : ($(window).height() - $("#showBigImg").outerHeight()) / 2 + $(document).scrollTop(),
						left : ($(window).width() - $("#showBigImg").outerWidth()) / 2 + $(document).scrollLeft()
					});
				});
				var newPreview = document.getElementById(imgDivId);
				newPreview.innerHTML = "";
				var imgDiv = document.createElement("div");
				document.body.appendChild(imgDiv);
				imgDiv.style.width = "110px";
				imgDiv.style.height = "55px";
				imgDiv.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = scale)";
				imgDiv.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
				newPreview.appendChild(imgDiv);
				newPreview.style.width = "110px";
				newPreview.style.height = "55px";
				newPreview.style.cursor = "pointer";
			}
		} else {
			var imgSrcLower = o.value.toLowerCase();
			if (imgSrcLower.substring(imgSrcLower.lastIndexOf(".")) == ".bmp" || imgSrcLower.substring(imgSrcLower.lastIndexOf(".")) == ".jpg" || imgSrcLower.substring(imgSrcLower.lastIndexOf(".")) == ".jpeg" || imgSrcLower.substring(imgSrcLower.lastIndexOf(".")) == ".gif" || imgSrcLower.substring(imgSrcLower.lastIndexOf(".")) == ".png") {
				$("#" + fileID + "_showDIV").html("<img id=\"" + fileID + "_img\" style=\"cursor: pointer;\"  width=\"110\" height=\"55\" src=\"" + o.value + "\"/>");
				$("#" + fileID + "_showDIV").click(function() {
					$("#showBigImg").html("<img width=\"700\" height=\"500\" src=\"" + o.value + "\"/>").show().offset({
						top : ($(window).height() - $("#showBigImg").outerHeight()) / 2 + $(document).scrollTop(),
						left : ($(window).width() - $("#showBigImg").outerWidth()) / 2 + $(document).scrollLeft()
					});
				});
			}
		}
	}
};

 
//判断是否为图片格式
function isValidateImg(str){
	var test = /^.+\.(?:png|jpg|bmp|gif|jpeg)$/g;
	var re = new RegExp(test);
	if(!re.test(str)){
		return false;
	}
	return true;
};
/***
 * 分转元，保留两位小数
 * @param fen
 * @returns {String}
 */
function convertFen2Yuan(fen){
	var ret='0.00';
	if(fen){
		ret = fmoney(fen/100, 2);
	}
	return ret;
}
/***
 * 分转元，保留N位小数
 * @param s
 * @returns {String}
 */
function fmoney(s, n){
   n = n > 0 && n <= 20 ? n : 2;
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
   var l = s.split(".")[0].split("").reverse(),
   r = s.split(".")[1];
   var t = "";
   for(var i = 0; i < l.length; i ++ ) {
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
   }
   return t.split("").reverse().join("") + "." + r;
}