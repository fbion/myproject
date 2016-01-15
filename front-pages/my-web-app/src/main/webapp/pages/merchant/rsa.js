/**
 * 密钥生成
 */
(function() {
	var Ras = function() {
		this.init();
	};
	Ras.prototype = {
		config : {
			queryObjectUrl : root + '/caculate/caculateRSA.action',
			subHeight : subHeight,
			subWidth : subWidth,
		},
		cache : {
			dialog : null,
			privateKey:null,
			publicKey:null
		},
		init : function() {
			this.getUrl();
			this.loadCss();
		},
		loadCss : function(){
			var imgUrl = '../../images/spanIoc.png';		 
			MALQ_CODE.titleBlock($('#step1'), '账户管理-生成密钥', imgUrl);
			$('#step1').css('width',(subWidth-100));
		 },
		getUrl : function() {
			showMask();
			var that = this;
			JAjax(that.config.queryObjectUrl, '', 'json', function(data){  
				if(data){
					var key = data.publicKey;
					var privateKey = data.privateKey;
					$('#publickey').val(key);
            		$('#privatekey').val(privateKey);
            		that.cache.publicKey = key;
            		that.cache.privateKey = privateKey;
            		hideMask();
				}
			});
		},

	};
	$(document).ready(function() {
		window.ras = new Ras();
	});
})();