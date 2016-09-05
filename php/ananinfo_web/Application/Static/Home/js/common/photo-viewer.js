/**
 * Created by yuanx on 2015/2/5.
 * 评论图片查看
 */
define(function(require,exports,module){
	var $ =require("jquery");
	var u = require("common/util");
	/**
	 * 评论图片查看
	 * @constructor
	 */
	function PicViewer(context){
		this.curIndex = -1;//重置当前下标
		this.context = context;
		this.turnIndex = 0;
		context = context||document;
		var _this = this;
		$(".direct-detail-comment-pic a",context).click(function(){
			var imgWrap = $(this).parent().parent();
			if(_this.curIndex==$(this).index()){
				imgWrap.find(".J_Hide").trigger("click");
				return;
			}
			_this.openViewer($(this).index(),imgWrap);
		});

		//隐藏
		$(".J_Hide",context).click(function(){
			$(this).parent().parent().slideUp("fast");
			$(this).parent().parent().parent().find(".direct-detail-comment-pic a").removeClass("cur");
			_this.curIndex=-1;
			_this.changeCursor($(this).parent().parent().parent());
		});

		$(".J_StageMain",context).hover(function(){
			var imgWrap = $(this).parent().parent();
			var picList = imgWrap.find(".direct-detail-comment-pic a");
			if(picList.size()==1){
				return;
			}
			if(_this.curIndex>0&&_this.curIndex<picList.size()-1){
				imgWrap.find(".tb-r-photo-btn-next,.tb-r-photo-btn-prev").show();
			}
			else if(_this.curIndex==0){
				imgWrap.find(".tb-r-photo-btn-next").show();
				imgWrap.find(".tb-r-photo-btn-prev").hide();
			}
			else{
				imgWrap.find(".tb-r-photo-btn-next").hide();
				imgWrap.find(".tb-r-photo-btn-prev").show();
			}
		},function(){
			var imgWrap = $(this).parent().parent();
			imgWrap.find(".tb-r-photo-btn-next,.tb-r-photo-btn-prev").hide();
		});

		$(".tb-r-photo-btn-next",context).bind("click",function(){
			_this.nextSlide(_this);
		});
		$(".tb-r-photo-btn-prev",context).bind("click",function(){
			_this.prevSlide(_this);
		});
		//向左旋转
		$(".J_TurnLeft",context).bind("click",function(){
			var img = _this.context.find(".J_Imgbox img");
			if(_this.turnIndex>0){
				_this.turnIndex--;
			}
			else{
				_this.turnIndex = 3
			};
			_this.turnImg(_this);
		});
		//向右旋转
		$(".J_TurnRight",context).bind("click",function(){
			if(_this.turnIndex<3){
				_this.turnIndex++;
			}
			else{
				_this.turnIndex = 0
			}
			_this.turnImg(_this);
		});
	}

	/**
	 * 图片旋转
	 */
	PicViewer.prototype.turnImg = function(_this){
		var img = _this.context.find(".J_Imgbox img");
		if(u.isIE8()){
			img.css("filter","progid:DXImageTransform.Microsoft.BasicImage(rotation="+_this.turnIndex+")");//兼容for ie8
		}
		else{
			img.css("transform","rotate("+_this.turnIndex*90+"deg)");
		}
		var imgBox= _this.context.find(".J_Imgbox");
		if(_this.turnIndex==1||_this.turnIndex==3) {
			imgBox.width(this.h);
			imgBox.height(this.w);
			if(u.isIE8()){
				return;
			}
			imgBox.find("img").css("marginLeft", (this.h - this.w) / 2 + "px");
			imgBox.find("img").css("marginTop", (this.w - this.h) / 2 + "px");
		}
		else{
			imgBox.width(this.w);
			imgBox.height(this.h);
			imgBox.find("img").css("marginLeft","0px");
			imgBox.find("img").css("marginTop","0px");
		}
	}

	/**
	 * 打开图片查看器
	 * @param i
	 */
	PicViewer.prototype.openViewer = function(i,imgWrap){
		this.curIndex = i;
		this.turnIndex = 0;
		this.turnImg(this);
		this.changeCursor(imgWrap);
		var _this = imgWrap.find(".direct-detail-comment-pic a").eq(i);
		_this.siblings().removeClass("cur");
		_this.addClass("cur");
		var imgUrl = _this.find("img").attr("src");
		var baseUrl = imgUrl.substring(0,imgUrl.indexOf("!"));
		imgWrap.find(".J_Open").attr("href",baseUrl);
		var thisObj = this;
		var loadImg = new Image();
		loadImg.src = baseUrl+"!productmain";
		if(loadImg.complete){
			thisObj.changeImg(imgWrap,loadImg);
			return;
		}
		$(loadImg).load(function(){
			thisObj.changeImg(imgWrap,loadImg);
		});
	}
	/**
	 * 改变图片
	 */
	PicViewer.prototype.changeImg = function(imgWrap,loadImg){
		imgWrap.find(".tb-r-photo-viewer").show();
		var imgDom = imgWrap.find(".tb-r-photo-stage-imgbox img");
		imgDom.attr("src",loadImg.src);
		imgWrap.find(".tb-r-photo-stage-imgbox").stop().animate({
			width:loadImg.width,
			height:loadImg.height
		});
		this.w = loadImg.width;
		this.h = loadImg.height;
	}

	/**
	 * 改变鼠标手型
	 */
	PicViewer.prototype.changeCursor = function(imgWrap){
		var _this = this;
		imgWrap.find(".direct-detail-comment-pic a").each(function(i){
			if(i==_this.curIndex){
				$(this).css({
					cursor:"url('http://www.55shantao.com/static/v2/images/zoom_out.cur'),auto"
				});
			}
			else{
				$(this).css({
					cursor:"url('http://www.55shantao.com/static/v2/images/zoom_in.cur'),auto"
				});
			}
		})
	}
	//向前切换
	PicViewer.prototype.prevSlide = function(_this){
		var imgWrap = _this.context;
		if(imgWrap.find(".tb-r-photo-stage-imgbox img").is(":animated")){
			return;
		}
		var picList =imgWrap.find(".direct-detail-comment-pic a");
		this.openViewer(_this.curIndex-1,imgWrap);
		if(_this.curIndex>0&&_this.curIndex<picList.size()-1){
			imgWrap.find(".tb-r-photo-btn-next,.tb-r-photo-btn-prev").show();
		}
		else if(_this.curIndex==0){
			imgWrap.find(".tb-r-photo-btn-next").show();
			imgWrap.find(".tb-r-photo-btn-prev").hide();
		}
		else{
			imgWrap.find(".tb-r-photo-btn-next").hide();
			imgWrap.find(".tb-r-photo-btn-prev").show();
		}
	}
	//向后切换
	PicViewer.prototype.nextSlide = function(_this){
		var imgWrap = _this.context;
		if(imgWrap.find(".tb-r-photo-stage-imgbox img").is(":animated")){
			return;
		}
		var picList =imgWrap.find(".direct-detail-comment-pic a");
		this.openViewer(_this.curIndex+1,imgWrap);
		if(_this.curIndex>0&&_this.curIndex<picList.size()-1){
			imgWrap.find(".tb-r-photo-btn-next,.tb-r-photo-btn-prev").show();
		}
		else if(_this.curIndex==0){
			imgWrap.find(".tb-r-photo-btn-next").show();
			imgWrap.find(".tb-r-photo-btn-prev").hide();
		}
		else{
			imgWrap.find(".tb-r-photo-btn-next").hide();
			imgWrap.find(".tb-r-photo-btn-prev").show();
		}
	}
	module.exports = PicViewer;
})