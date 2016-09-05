/**
 * Created by yuanx on 2015/1/15.
 * 大图滑动
 */
define(function(require,exports,module){
	var $ = require("jquery");
	var w;//滑动宽度
	var container;//slider容器
	var main;//slider对象
	var startIndex = 0;//当前图片开始下标
	var len;//slider大小
	var option = {
		container:"#slider",
		index:0
	};//默认配置

	/**
	 * 渲染界面
	 */
	function render(op){
		option = $.extend(option,op);
		container = $(option["container"]);
		main = container.children("div");
		init();
		slide(option["index"]);
		startIndex = option["index"];
	}

	/**
	 * 初始化
	 * @private
	 */
	function init(){
		w = 80;
		len =main.find("li").size();
		if(len<=5){
			$(".direct-slider-left").css("visibility","hidden");
			$(".direct-slider-right").css("visibility","hidden");
		}
		var pointer = container.children("a");
		pointer.eq(0).click(function(){
			slideLeft();
		});
		pointer.eq(1).click(function(){
			slideRight();
		});
		main.find("li").click(function(){
			main.find("li").removeClass("cur");
			$(this).addClass("cur");
			$(".direct-detail-goods-pic img").attr("src",$(this).attr("data-big-img"));
			var i = $(this).index();
			if(i==startIndex){
				if(i>=2){
					slide(startIndex-2);
				}
				else{
					slide(0);
				}
			}
			else if(i==startIndex+4){
				if(i<=len-3){
					slide(startIndex+2);
				}
				else{
					slide(len-5);
				}
			}
		});
	}

	/**
	 * 滑动到某个位置
	 * @param i
	 * @private
	 */
	function slide(i){
		if(container.find("ul").is(":animated")){
			return;
		}
		var l = -(w+10)*i;
		main.children("ul").animate({left:l+"px"});
		startIndex = i;
	}
	/**
	 * 左滑动
	 */
	function slideLeft(){
		var nextIndex;
		if(startIndex>=5){
			nextIndex = startIndex-5;
		}
		else{
			nextIndex = 0;
		}
		slide(nextIndex);
	}

	/**
	 * 右滑动
	 */
	function slideRight(){
		var nextIndex;
		if(len-(startIndex+5)>=5){
			nextIndex = startIndex + 5;
		}
		else{
			nextIndex = len - 5;
		}
		slide(nextIndex);
	}

	module.exports.render = render;
});