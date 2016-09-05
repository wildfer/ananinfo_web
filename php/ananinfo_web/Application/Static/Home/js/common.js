/**
 * Created by yuanx on 14-8-28.
 */
/**
 * 函数节流（保证函数在请求停止一段时间后再执行，节省性能）
 * @param method
 * @param context
 */
function throttle ( method , context ){
	clearTimeout ( method.tId );
	method.tId = setTimeout ( function () {
		method.call ( context );
	} , 50);
}
/**
 * 顶部bar显示隐藏
 */
function showTopBar(){
	var showH = 65;

	if($(window).scrollTop()>showH){
		$(".search-bar").css({top:"0px"});
		$("#nav_menu").css("top","70px");
	}
	else{
		$(".search-bar").css({top:"-88px"});
		if($(".list_content").offset().top) {
			$("#nav_menu").css("top", $(".list_content").offset().top + "px");
		}
	}
}
/**
 * 详细页导航浮动
 */
function navFloat(){
	if($(window).scrollTop()>tbH){
		if(!$(".tab-bar").hasClass("tab-bar-fixed")){
			$(".tab-bar").addClass("tab-bar-fixed");
			$("#fillBlock").show();
		}
	}
	else{
		$(".tab-bar").removeClass("tab-bar-fixed");
		$("#fillBlock").hide();
	}
}
var tbH;//详细页导航开始浮动位置
$(function(){
	var toUp = false,toDown=false;
	$(".left_menu li:not(.h_title)").hover(function(){
		$(this).addClass("selected");
		$(this).find("a").stop().animate({marginLeft:"50px"},"fast");
	},function(){
		$(this).removeClass("selected");
		$(this).find("a").stop().animate({marginLeft:"32px"},"fast");
	});
	$(window).scroll(function(){
		throttle(showTopBar);
	});
	if($(".tab-bar").offset())
	{
		tbH = $(".tab-bar").offset().top;
	}
	//详细页导航浮动
	$(window).scroll(function(){
		navFloat();
	});
	$(".input .txt").focus(function(){
		$(this).css("color","#333333");
		$(this).css("borderColor","#7abd54");
	}).blur(function(){
		$(this).css("color","#333333");
		$(this).css("borderColor","#dddddd");
	});
	//头部搜索框
	$(".input_box .txt").focus(function(){
		if ($(this).val()=="请输入宝贝关键词或者商品链接") {
			$(this).val("");
			$(this).css('color', '#000');
		}
	}).keydown(function(){
		if($(this).val()=="请输入宝贝关键词或者商品链接"){
			$(this).val("");
			$(this).css('color', '#cacaca');
		}else{
			$(this).css('color', '#000');
		}
	}).blur(function(){
		if($(this).val().length==0){
			$(this).val("请输入宝贝关键词或者商品链接");
			$(this).css('color', '#cacaca');
		}
	});
	$(".btn-tax-win , .prime-pantry").hover(function(){
		$(this).find(".tax-win").show();
	},function(){
		$(this).find(".tax-win").hide();
	});
	$(".tax-win").hover(function(){
		$(this).show();
	},function(){
		$(this).hide();
	});

	//详细界面tab切换
	$(".goods-des .tab-bar ul li").each(function(i){
		$(this).click(function(){
			$(".goods-des .tab-bar ul li").removeClass("cur");
			$(this).addClass("cur");
			var tc = $(".tab-con");
			var st = tc.eq(i).offset().top;
			st = st -62;
			$("body,html").animate({scrollTop:(st)+"px"},"normal");
		});
	});
});
String.prototype.trim=function() {  
    return this.replace(/(^\s*)|(\s*$)/g,'');  
};  

function moneyround ( price )
{
	return parseFloat(price).toFixed(0);
}
/**
 * 显示金额转换，因为存在多币种
 * 共有三处 
 * system项目的function_common
 * shantao项目的common.js
 * shantao项目的function_common
 * @param float $source_price        	
 * @param string $currency        	
 * @param float $rate        	
 * @return number
 */
function display_convert(source_price,price_currency,display_currency,is_profix){
	if(price_currency == undefined || price_currency.trim() == ''){
		source_currency = 'USD';
	}
	if(display_currency == undefined || display_currency.trim() == ''){
		to_currency = 'CNY';
	}
	var rate = 1;
	if(SETTING){
		var key = price_currency + '2' + display_currency;
		rate = SETTING[key]
		if(rate == undefined){
			rate = 1;
		}
	}
	var char = '';
	if(is_profix){
		switch (display_currency){
			case 'CNY':
				char = '¥';
				break;
			case 'USD':
				char = '$';
				break;
		}
	}
	return price_format(source_price * rate,0,true,char);
}
function price_format(price, format, flag,display){
	
	price = $.isNumeric(price) ? price : 0;

    switch(format){
		case 1: // 直接取整
   			price = parseInt(price);
            break;
		default: // 四舍五入保留两位小数
 			price = moneyround(price);
    }

    return flag ? display + price : price;
}