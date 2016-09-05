/**
 * 首页商品滚动加载
 * Created by yuanx on 2015/3/17.
 */
define(function(require,exports,module){
	var $ = require("jquery");
	var util = require("common/util");
	var page = 1;
	var isAjax = false;
	var isEnd =false;//是否已到结尾
	var cd = require("common/countdown");
	var productInfo = [];//记录商品位置信息
	//为倒计时扩展一个处理方法
	cd.timer.prototype.detail_count = function(){
		if (this.differTime == 0) {
			$(".direct-detail-time").hide();
		}
		this.h = this.h % 24;
		return "" + this.d + "<i>" + "天" + "</i>" + this.h + "<i>" + "时" + "</i>" + this.m + "<i>" + "分" + "</i>" + this.s + "<i>" + "秒" +"</i>";
	}
	//商品倒计时
	var countdown = setInterval(function(){
		var st = $(window).scrollTop();
		var wh = $(window).height();
		now = parseInt(now) + 1;
		for(var i = 0;i<productInfo.length;i++){
			if(productInfo[i].top>st&&productInfo[i].top<st+wh){//在可视范围内
				var home_timer = $("#"+productInfo[i].id);
				var n = new Date();
				var e = new Date();
				var endtime = parseInt(home_timer.attr("end_time"));
				n.setTime(parseInt(now)*1000);
				e.setTime(parseInt(endtime));
				var t =new cd.timer(n,e);
				home_timer.html(t.detail_count());
				home_timer.show();
			}
		}
	},1000);

	$(window).scroll(function(){
		util.throttle(loadData);
	});

	/**
	 * 加载推荐商品
	 */
	function loadData(){
		var st = $(document).scrollTop();
		var dh = $(document).height();
		var wh = $(window).height();
		if(dh-(st+wh)<5000&&!isAjax&&!isEnd){
			isAjax = true;
			$.ajax({
				url : '/?mod=index&op=get_hot500',
				data : {
					page : page,
					count:15,
					unused:unused
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					isAjax =false;
					page++;
					var goodsData = data.data;
					if(goodsData.length==0){
						isEnd = true;
						return;
					}
					var html = $("#goodsTemplate").html();
					var goodsList = $("#main-goods");
					for(var i = 0;i<goodsData.length;i++){
						var goods = $(html);
						goods.find(".time_box_home_timer").attr("id","p"+goodsData[i].product_id);
						goods.find("a").attr("href","/product-"+goodsData[i].default_sku+".html");
						goods.find(".main-direct-pic img").attr("src",goodsData[i].img_cover);
						var discount = parseFloat(goodsData[i].sku_discount);
						if(discount>0&&discount<1) {
							goods.find(".main-direct-name strong").html("<b>"+parseInt(discount*1000)/100.0+"</b>"+"折");
						}
						else{
							goods.find(".main-direct-name strong").hide();
						}
						goods.find(".main-direct-name a").text(goodsData[i].title);
						var price = goodsData[i].price;
						goods.find(".main-direct-price b").text(price);
						goods.find(".main-direct-buy>span").text(goodsData[i].rp);
						goods.find(".num_box i").text(goodsData[i].sold);
						goods.find(".time_box_home_timer").attr("end_time",parseFloat(goodsData[i].time_offline)*1000);
						goods.find(".main-direct-des").text(goodsData[i].recommendation);
						var stock = parseInt(goodsData[i].stock);
						if(stock<=0){
							goods.find(".global-quick-sold-out").show();
						}
						goods.appendTo(goodsList);
						var info = {};
						info.id = "p"+goodsData[i].product_id;
						info.top = goods.find(".time_box_home_timer").offset().top;
						productInfo.push(info);
					}
				}
			});
		}
	}
});
