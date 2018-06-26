/**
 * AJAX消息通讯
 */
;(function(window) {

	var messager = {
		
		/**
		 * 向服务器发送消息 post
		 * 
		 * @param cfg
		 */
		send : function(cfg) {

			var me = this;

			me.base(cfg);

		},

		/**
		 * 向服务器发送消息 get
		 */
		get : function(url, callback, sync) {			
			var me = this;
			me.base({
				type : 'get',
				url : url,
				sync : sync,
				onSuccess : callback
			});
		},
		
		base : function(cfg) {
			$.ajax({
				type : cfg.type == undefined || cfg.type == null
						? "POST"
						: cfg.type,
				url : cfg.url,
				dataType : cfg.dataType == undefined
						|| cfg.dataType == null ? "json" : cfg.dataType,
				cache : false,
				data : cfg.data,
				async : cfg.sync == undefined || cfg.sync == null
						? true
						: !cfg.sync,
				beforeSend : function() {
					if (cfg.onBefore != undefined) {
						cfg.onBefore();
					}
				},
				success : function(data) {
					if (cfg.onSuccess != undefined) {
						cfg.onSuccess(data);
					}
				},
				error : function() {
					if (cfg.onError != undefined) {
						cfg.onError();
					}
				}
			});
		}		

	}

	// RequireJS && SeaJS
	if (typeof define === 'function') {
		define(function() {
			return messager;
		});
	}else{
		window.messager = messager;
	}
})(window);