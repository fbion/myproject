/** 
 *<p>Title:物流交易平台</p> 
 *<p>Description:图片上传组件</p>
 *<p>Copyright: Copyright (c) 2014</p> 
 *<p>Company:北京中交兴路车联网科技有限公司</p> 
 *使用方法1：var fileLoad=new CtfoFileUpload({id:"fileId",callback:function(fileName){}});
			  fileLoad.init();
 *使用方法二：var fileLoad=new CtfoFileUpload({"isAutoRender":"true","containerId":"容器Id","fileName":"form提交用到的name"});
			  fileLoad.init();
 *isAutoRender:是否自动渲染，containerId：自动渲染容器Id
 * @author刘庭海
 * @version 1.1 2014-02-25 
 */
var CtfoFileUpload = function(o) {
	this.options = o;
	this.container = {};
	this.picHidden = {};
};
CtfoFileUpload.prototype = {
	cache : {},
	init : function() {
		this._render();
	},
	update : function(src) {

	},
	_initEvent : function() {

	},
	_getPath : function() {
		var pathName = location.pathname.split("/");
		var webName = "";
		if (pathName[2] && pathName[2] == "pages") {
			webName = "/" + pathName[1];
		}
		var url = location.protocol + '//' + location.host + webName;
		return url;
	},
	_render : function() {
		var self = this;
		if (this.options.isAutoRender) {
			var s = [];
			this.options.id = "fileUpload" + Math.round(Math.random() * 100000);
			s.push('<input class="w150 h24 attachmentBean" name="file" value="上传照片" id="' + this.options.id + '" type="file"  >');
			s.push('<input name="' + this.options.fileName + '" value="" type="hidden">');
			var max_tip = '';
			if (self.options.max) {
				max_tip = '，最多<span class="color_red">' + self.options.max + '</span>张';
			} else {
				self.options.max = 1;
				max_tip = '';
			}
			s.push('<input name="upload" value="" type="button" class="fileBtn" >' + max_tip + '');
			s.push('<div name="container" class="picPreview"></div>');
			self.options.container = $("#" + this.options.containerId);
			self.options.container.append(s.join(" "));
			self.options.picContainer = self.options.container.find("div[name='container']");
			self.options.picHidden = self.options.container.find("input[type='hidden']");
			self.options.container.delegate(".attachmentBean",'change', function() {
				var fileValue = $("#" + self.options.id).val().toLowerCase();
				if (fileValue == "") {
					alert("请选择图片！");
					return;
				}
				if (!/\.(jpg|jpeg|png)$/.test(fileValue)) {
					alert("图片类型必须是jpeg,jpg,png中的一种！");
					return false;
				}
				//最大上传图片数量
				if (self.options.max) {
					var _val = self.options.picHidden.val();
					if (_val && _val.split(",").length + 1 > parseInt(self.options.max)) {
						alert("超过最大上传图片数量！");
						return;
					}
				}
				self._updalod(self.options.container.find("input[name='upload']"));
			});
		} else {
			self._updalod();
		}
	},
	_updalod : function(obj) {
		var self = this;
		var url = this.options.uploadUrl;
		if (!url) {
			url = '/noAuth/file/upload.htm';
		}
		url = self._getPath() + url;
		url = url.replace("/pages", "");
		var o = $(obj).parent();
		o.find("div[name='container']").append('<span class="previewpic">上传中</span>');
		
		var t = 0;
		var sit = setInterval(function(){
			if(t<99){
				o.find("div[name='container']").find('.previewpic').text(t++ +"%");
			}else{
				clearInterval(sit);
			}
		},20);
		
		$.ajaxFileUpload({
			url : url,//用于文件上传的服务器端请求地址
			secureuri : false,//一般设置为false
			fileElementId : self.options.id,//文件上传空间的id属性  <input type="file" id="file" name="file" />
			dataType : 'json',//返回值类型 一般设置为json
			callback : self.options.callback,
			success : function(data, status) //服务器成功响应处理函数
			{
				data = data.replace("min_","");
				if(data=="-1"){
					alert("文件大小超过最大限制！");
					return ;
				}else if(data=="-4" || data==""){
					alert("文件大小超过最大限制！");
					return ;
				}else{
					//自动渲染
					if (self.options.isAutoRender) {
						var o = $(obj).parent();
						o.find("div[name='container']").find('.previewpic').remove();
						//隐藏域存储
						var s = [];
						var fileId = data.split("/")[data.split("/").length - 1].split(".")[0];
						s.push("<span><img id='" + fileId + "' src='" + data + "'>");
						s.push("<a class='hand'  onclick='deleteImg(this,\"" + fileId + "\",\"" + self.options.fileName + "\");' ></a></span>");
						
						o.find("div[name='container']").append(s.join(""));
						//去掉前面的绝对路径
						var imgs = data.split("/");
						var img = imgs[imgs.length - 1];
						var hdv = o.find("input[type='hidden']").val();
						if (hdv) {
							hdv = "," + hdv;
						}
						var v = img + hdv;
						o.find("input[type='hidden']").val(v);
						// alert("上传成功！");
					} else {
						this.callback.call(this, data, status);
					}
				}				
			},
			error : function(data, status, e) {
				//服务器响应失败处理函数
				alert(e);
			}
		});
	},
	/**
	 * imgs :图片路径数组
	 */
	showUpdateImages : function(arrImgs) {
		if (!arrImgs || arrImgs.length < 1) {
			return;
		}
		var self = this;
		var arrHtml = [];
		var arrPath = [];
		var picHidden = $("input[name='" + arrImgs[0].fileName + "'][type='hidden']");
		var container = picHidden.parent().find("div[name='container']");
		container.empty();
		picHidden.val("");
		$(arrImgs).each(function() {
			if (this.src.indexOf(".") > -1) {
				var str = this.src.split(".")[this.src.split(".").length - 2];
				var fileId = str.split("/")[str.split("/").length - 1];
				arrHtml.push("<img id='" + fileId + "' src='" + this.rootUrl + this.src + "'>");
				arrHtml.push("<a class='hand'  onclick='deleteImg(this,\"" + fileId + "\",\"" + this.fileName + "\");' ></a>");
				var imgs = this.src.split("/");
				var img = imgs[imgs.length - 1];
				arrPath.push(img);
			}
		});
		container.append(arrHtml.join(""));
		picHidden.val(arrPath.join(","));
	}
};
/**
 * 删除图片
 */
function deleteImg(obj, imgId, fileName) {
	$("#" + imgId).remove();
	var inputObj = $("input[name='" + fileName + "']");
	var s = inputObj.val().split(",");
	$(s).each(function(i) {
		if (this.indexOf(imgId) > -1) {
			s.splice(i, 1);
		}
	});
	$(obj).remove();
	inputObj.val(s.join(","));
}