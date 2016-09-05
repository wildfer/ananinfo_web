/**
 * Created by aqbccd on 2015/2/2.
 */
define(function(require,exports,module){
	var $ = require("jquery");
	$(function(){
		$("<div class='back-top' id='bt'></div>").bind("click",function(){
			$("body,html").animate({scrollTop:0},500);
			$(".back-top").animate({bottom:$(window).height()+"px"},500);
		}).appendTo("body");
		$(window).bind("scroll",function(){
			var top = $(window).scrollTop();
			if(top>0){
				if($(".back-top").is(":animated")||($(".back-top").css("bottom")=="100px")){
					return;
				}
				$(".back-top").css("bottom","-63px").animate({bottom:"100px"},"normal");
			}
			else{
				$(".back-top").css("bottom",$(window).height()+"px");
			}
		});
	});
})