/**
 * Created by yuanx on 2015/1/15.
 * 大图滑动
 */
define(function(require,exports,module){
	var $ = require("jquery");
	var w;//窗口宽度
	var container;//slider容器
	var util = require("common/util");
	var sliderTimer;
	var curIndex;//当前图片下标
	var len;//slider大小
	var option = {
		container:"#slider",
		index:0,
		auto:true,
		time:5000
	};//默认配置
	/**
	 * 渲染界面
	 */
	function render(op){
		option = $.extend(option,op);
		container = $(option["container"]);
		container.hover(function(){
			$(".main-banner-btn-left").show();
			$(".main-banner-btn-right").show();
		},function(){
			$(".main-banner-btn-left").hide();
			$(".main-banner-btn-right").hide();
		});
		init();
		slide(option["index"]);
		curIndex = option["index"];
		if(option["auto"]){
			autoSlide();
		}
	}

	/**
	 * 自动滑动
	 */
	function autoSlide(){
		sliderTimer = setInterval(function(){
			var nextIndex;
			if(curIndex<len-1){
				nextIndex = curIndex + 1;
			}
			else{
				nextIndex = 0;
			}
			slide(nextIndex);
		},option["time"]);
	}

	/**
	 * 加载大图
	 */
	function loadImg(img,index){
		img.onload = function(){
			container.children("ul").find("li:eq("+index+")").css("backgroundImage","url('"+img.src+"')");
			container.children("ul").find("li:eq("+index+")").find("img").remove();
		}
	}
	/**
	 * 初始化
	 * @private
	 */
	function init(){
		w = $(window).width();
		w = w<1200?1200:w;
		var slider_pic = container.children("ul").find("li");
		len = slider_pic.size();
		container.children("ul").find("li").each(function(){
			$(this).width(w);
			$(this).find("a").html("<img src='static/v2/images/loading.gif'/>");
			var img = new Image();
			img.src = $(this).attr("data-img");
			loadImg(img,$(this).index());
			//$(this).css("backgroundImage","url('"+$(this).attr("data-img")+"')");
		});
		var banner_pointer = $("<div class='main-banner-pointer'></div>");
		var $ul = $("<ul></ul>");
		for(var i = 0;i<len;i++){
			var $li = $("<li></li>");
			if(i==option["index"]){
				$li.addClass("cur").appendTo($ul);
			}
			else{
				$li.appendTo($ul);
			}
			$li.bind("click",function(){
				var i = $(this).index();
				clearInterval(sliderTimer);
				slide(i);
			});
		}
		$ul.addClass("clearfix").appendTo(banner_pointer);
		var l = -(20*len+2)/2;
		banner_pointer.css("marginLeft",(l)+"px");
		$("<a class='main-banner-btn-left'></a>").bind("click",slideLeft).appendTo(container);
		$("<a class='main-banner-btn-right'></a>").bind("click",slideRight).appendTo(container);
		banner_pointer.appendTo(container);
	}

	/**
	 * 左滑动
	 */
 	function slideLeft(){
		var next;
		if(curIndex>0){
			next = curIndex -1;
		}
		else{
			next = len - 1;
		}
		clearInterval(sliderTimer);
		slide(next);
	}

	/**
	 * 右滑动
	 */
	function slideRight(){
		var next;
		if(curIndex<len-1){
			next = curIndex + 1;
		}
		else{
			next = 0;
		}
		clearInterval(sliderTimer);
		slide(next);
	}
	/**
	 * 滑动到某个位置
	 * @param i
	 * @private
	 */
	function slide(i){
		if(container.children("ul").is(":animated")){
			return;
		}
		var l = -w*i;
		container.children("ul").animate({left:l+"px"});
		var sliderPointer = container.children(".main-banner-pointer").find("ul li");
		sliderPointer.removeClass("cur");
		sliderPointer.eq(i).addClass("cur");
		curIndex = i;
	}


	/**
	 * 随窗口变化调整slider
	 */
	function adjustSlider(){
		w = $(window).width();
		w = w<1200?1200:w;
		var index = $(".main-banner-pointer ul li.cur").index();
		var slider = container.children("ul");
		slider.find("li").width(w);
		slider.css("left",(-w*index)+"px");
	}
	/**
	 * 窗口大小变化时
	 */
	$(window).resize(function(){
		util.throttle(adjustSlider);
	});
	window.onload = function(){
		adjustSlider();//兼容 for IE8
	};
	module.exports.render = render;
});