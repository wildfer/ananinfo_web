/**
 * Created by yuanx on 2015/1/22.
 */
define(function(require,exports,module){
	var $ = require("jquery");
	var container;
	var startIndex = 0;//当前图片下标
	var len;
	/**
	 * 初始化
	 */
	function init(){
		var slider =  container.children("ul");
		len = slider.find("li").size();
		slider.find("li").bind("click",selPic);
	}

	/**
	 * 界面渲染
	 */
	function render(op){
		container = $(op.container);
		init();
	}

	/**
	 * 小图滑动
	 * @param i
	 */
	function slide(index){
		var slider =  container.children("ul");
		var h = slider.find("li").height()+2;
		var t;
		t = (-h*index);
		slider.animate({top:t+"px"});
		startIndex = index;
	}

	/**
	 * 选择图片
	 */
	function selPic(){
		var url = $(this).attr("data-big-img");
		var i = $(this).index();
		$(".special-goods-pic-big img").attr("src",url);
		var nextIndex;
		if(i==startIndex&&i>=2){
			nextIndex = startIndex - 2;
			slide(nextIndex);
		}
		else if(i==startIndex&&i<2){
			nextIndex = 0;
			slide(nextIndex);
		}
		else if(i==(startIndex+3)&&(i<(len-2))){
			nextIndex = startIndex+2;
			slide(nextIndex);
		}
		else if(i==(startIndex+3)&&(i>=(len-2))){
			nextIndex = len-4;
			slide(nextIndex);
		}
		var slider =  container.children("ul");
		slider.find("li").removeClass("cur");
		slider.find("li").eq(i).addClass("cur");
	}

	module.exports.render = render;
});