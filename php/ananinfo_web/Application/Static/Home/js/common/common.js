/**
 * Created by yuanx on 2015/1/30.
 * 所以界面通用js
 */
define(function(require,exports,module){
	var $ = require("jquery");
	var t ;
	$(function(){
		if(isNavFloat) {
			t = $(".main-nav-wrap").offset().top;
			$(window).bind("scroll", function () {
				mainNavFloat();
			});
		}
		$(".header-search-box").on('keyup',function(e){var code=e.keyCode||e.which;code==13&&$("#header-search-btn").click();});
		$("#header-search-btn").click(function(){
			var keywords = encodeURIComponent($.trim($("#input-search").val()));
			window.location.href = '/search/'+keywords;
			if(keywords.length>0){
				$("#input-search").blur();
				$("#header-s1").show();
				$("#header-s1 .ui-progressbar-thumb").animate({width:"100%"},5000);
				$("#header-t1").show();
			}
		});
	});
	/**
	 * 主导航飘动
	 */
	function mainNavFloat(){
		var ct = $(window).scrollTop();
		if(ct>=t){
			$(".main-nav-wrap").addClass("main-nav-wrap-fixed");
			$(".main-nav-fill-block").show();
		}
		else{
			$(".main-nav-wrap").removeClass("main-nav-wrap-fixed");
			$(".main-nav-fill-block").hide();
		}
	}
		/**
	 * 只允许中文
	 */
	$(".zh-cn").bind('blur',function(){
		this.value = this.value.replace(/[^\u4e00-\u9fa5]/gi,"");
	});
});