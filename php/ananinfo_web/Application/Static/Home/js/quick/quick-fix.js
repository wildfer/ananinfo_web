/**
 * Created by yuanx on 2015/2/3.
 */
define(function(require,exports,module){
	var $ = require("jquery");
	require("pie");
	if (window.PIE) {
		$('.quick-store-list li').each(function() {
			PIE.attach(this);
		});
		$('.direct-detail-user img').each(function() {
			PIE.attach(this);
		});
		$(".direct-detail-editor img").each(function(){
			PIE.attach(this);
		});
		$(".index-goods-floor h1 em").each(function(){
			PIE.attach(this);
		});
	}
});