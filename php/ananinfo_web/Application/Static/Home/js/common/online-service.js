/**
 * Created by yuanx on 2015/2/4.
 */
define(function(require,exports,module){
	require("cookie");
	var $ = require("jquery");
	$(function(){
		$(".toggle").click(function(){
			if($(".online-service").css("right")=="0px"){
				$(".online-service").animate({right:"-105px"},"normal");
				$(".toggle").css("backgroundPosition","-29px -48px");
				$.cookie("r","-105px");
			}
			else{
				$(".online-service").animate({right:"0px"},"normal");
				$(".toggle").css("backgroundPosition","0px -48px");
				$.cookie("r","0px");
			}
		});
		/**
		 * 初始化侧栏位置
		 */
		if($.cookie("r")){
			$(".online-service").css("right", $.cookie("r"));
			if($.cookie("r")=="0px"){
				$(".toggle").css("backgroundPosition","0px -48px");
			}
			else{
				$(".toggle").css("backgroundPosition","-29px -48px");
			}
		}
	});
});