/**
 * Created by yuanx on 2014/12/25.
 */
define(function(require,exports,module){
	var $ = require('jquery');
	/**
	 * 函数节点流
	 * @param method
	 * @param context
	 */
	module.exports.throttle = function( method , context ){
		clearTimeout ( method.tId );
		method.tId = setTimeout ( function () {
			method.call ( context );
		} , 50);
	}

	String.prototype.trim=function() {  
		return this.replace(/(^\s*)|(\s*$)/g,'');  
	};
	/**
	 * 反射调用方法
	 * @param obj
	 * @param method
	 */
	module.exports.call = function(obj,method){
		for(var p in obj){
			if(p.toString()==method.toString()){
				if(typeof obj[p]=="function"){
					return obj[p]();
				}
			}
		}
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
	module.exports.display_convert = function (source_price,price_currency,display_currency,is_profix){
		if(price_currency == undefined || price_currency.trim() == ''){
			source_currency = 'USD';
		}
		if(display_currency == undefined || display_currency.trim() == ''){
			to_currency = 'CNY';
		}
		var rate = 1;
		if(SETTING){
			var key = price_currency + '2' + display_currency;
			if(typeof SETTING[key]!='undefined'){
				rate = SETTING[key];
			}else{
				if(key == 'CNY2USD'&& typeof SETTING['USD2CNY']!='undefined'){
					rate = 1/SETTING['USD2CNY'];
				}else if(key == 'USD2CNY' &&  typeof SETTING['CNY2USD']!='undefined'){
					rate = 1/SETTING['CNY2USD'];
				}else{
					rate = 1;
				}
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

	function price_format (price, format, flag,display){
		
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
	function moneyround ( price )
	{
		return parseFloat(price).toFixed(0);
	}
	/**
	 * 模拟文本框placeholder效果
	 * @param target
	 * @param initText
	 */
	function placeholder(target,initText){
		//头部搜索框
		$(target).focus(function(){
			if ($(this).val()==initText) {
				$(this).val("");
				$(this).css('color', '#000');
			}
		}).keydown(function(){
			if($(this).val()==initText){
				$(this).val("");
				$(this).css('color', '#cacaca');
			}else{
				$(this).css('color', '#000');
			}
		}).blur(function(){
			if($(this).val().length==0){
				$(this).val(initText);
				$(this).css('color', '#cacaca');
			}
		});
	}

	/**
	 * 判断IE8
	 * @returns {boolean}
	 */
	function isIE8(){
		var browser=navigator.appName
		var b_version=navigator.appVersion
		var version=b_version.split(";");
		if(browser=="Microsoft Internet Explorer" ){
			var trim_Version=version[1].replace(/[ ]/g,"");
			return (trim_Version=="MSIE8.0");
		}
		return false;
	}
	
	//设置全局cookie
	function total_setCookie(c_name,value,expiredays){
	    var exdate=new Date();
		if (typeof(expiredays)=="undefined") {
			var expiredays = 10*365;
		} 
	    exdate.setDate(exdate.getDate()+expiredays);
	    document.cookie=c_name+ "=" +escape(value)+
	    ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())+";path=/";
	}
	//获取全局cookie
	function total_getCookie(c_name){
		if (document.cookie.length>0){
			c_start=document.cookie.indexOf(c_name + "=")
			if (c_start!=-1){ 
			    c_start=c_start + c_name.length+1 
			    c_end=document.cookie.indexOf(";",c_start)
			    if (c_end==-1) c_end=document.cookie.length
			    return unescape(document.cookie.substring(c_start,c_end))
			} 
	    }
	    return ""
	}
	module.exports.moneyround = moneyround;
	module.exports.price_format = price_format;
	module.exports.placeholder = placeholder;
	module.exports.isIE8 = isIE8;
	module.exports.total_setCookie = total_setCookie;
	module.exports.total_getCookie = total_getCookie;
});