/**
 * Created by yuanx on 2015/1/15.
 * 通用滚动
 */
define(function(require,exports,module){
	var $ = require("jquery");
	function SliderCommon(){

	}

	SliderCommon.prototype.curIndex = 0;

    SliderCommon.prototype.option =  {
		container:"#slider",
		index:0,
		auto:true,
		time:5000,
		leftNav:"",
		rightNav:"",
		isPointerNav:false,//是否包含原点切换
		bannerPointerClass: "direct-banner-pointer",
        afterSlide:null//滑动完成后回调
	};//默认配置

	/**
	 * 初始化
	 * @private
	 */
	SliderCommon.prototype.init = function(){
		var _this = this;
		this.container.find(this.option["leftNav"]).unbind("click").bind("click",function(){
			_this.slideLeft();
		});
		this.container.find(this.option["rightNav"]).unbind("click").bind("click",function(){
			_this.slideRight();
		});
	}

	/**
	 * 渲染
	 */
	SliderCommon.prototype.render = function(op){
		this.option = $.extend(false,this.option,op);
		this.container = $(this.option["container"]);
		this.len = this.container.children("ul").children("li").size();
		if(this.len<=1){
			return;
		}
		var _this = this;
		this.container.hover(function(){
			$(this).find(_this.option["leftNav"]).show();
			$(this).find(_this.option["rightNav"]).show();
		},function(){
			$(this).find(_this.option["leftNav"]).hide();
			$(this).find(_this.option["rightNav"]).hide();
		});
		this.init();
		if(this.option["auto"]){
			this.autoSlide();
		}
		if(this.option["isPointerNav"]){
			var banner_pointer = $("<div class='"+this.option['bannerPointerClass']+"'></div>");
			var $ul = $("<ul></ul>");
			for(var i = 0;i<this.len;i++){
				var $li = $("<li></li>");
				if(i==this.option["index"]){
					$li.addClass("cur").appendTo($ul);
				}
				else{
					$li.appendTo($ul);
				}
				$li.bind("click",function(){
					var i = $(this).index();
					clearInterval(_this.sliderTimer);
					_this.slide(i);
				});
			}
			$ul.addClass("clearfix").appendTo(banner_pointer);
			var l = -(20*this.len+2)/2;
			banner_pointer.css("marginLeft",(l)+"px");
			banner_pointer.appendTo(this.container);
		}
		this.slide(this.option["index"]);
	}
	/**
	 * 滑动
	 * @param i
	 */
	SliderCommon.prototype.slide = function (i){
		var slider = this.container.children("ul");
		if(slider.is(":animated")){
			return;
		}
		var l = - this.container.width()*i;
		slider.animate({left:l+"px"});
		if(this.option["isPointerNav"]){
			var sliderPointer = this.container.children("."+this.option["bannerPointerClass"]).find("ul li");
			sliderPointer.removeClass("cur");
			sliderPointer.eq(i).addClass("cur");
		}
		this.curIndex = i;
        /*执行回调*/
        if(this.option.afterSlide){
            this.option.afterSlide();
        }
	}
	/**
	 * 左滑动
	 */
	SliderCommon.prototype.slideLeft = function (){
		var nextIndex;
		if(this.curIndex>0){
			nextIndex = this.curIndex-1;
		}
		else{
			nextIndex = this.len-1;
		}
		clearInterval(this.sliderTimer);
		this.sliderTimer = null;
		this.slide(nextIndex);
	}

	/**
	 * 右滑动
	 */
	SliderCommon.prototype.slideRight = function (){
		var nextIndex;
		if(this.curIndex<this.len-1){
			nextIndex = this.curIndex +1;
		}
		else{
			nextIndex = 0;
		}
		clearInterval(this.sliderTimer);
		this.sliderTimer = null;
		this.slide(nextIndex);
	}

	/**
	 * 自动滑动
	 */
	SliderCommon.prototype.autoSlide = function (){
		var _this = this;
		this.sliderTimer = setInterval(function(){
			var nextIndex;
			if(_this.curIndex<_this.len-1){
				nextIndex = _this.curIndex + 1;
			}
			else{
				nextIndex = 0;
			}
			_this.slide(nextIndex);
		},_this.option["time"]);
	}

	module.exports = SliderCommon;
});