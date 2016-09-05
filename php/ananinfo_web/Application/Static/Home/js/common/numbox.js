/**
 * Created by yuanx on 2015/1/8.
 */
define(function(require,exports,module){
	var tempInput;//临时输入
	var num_reg = /^[0-9]*[1-9][0-9]*$/;//非负整数正则
	var defaults = {//默认配置
		min_num:1
	};
	var opt;
	var $ = require("jquery");
	//设置是否可点击
	function setEnable(){
		var $this = $(".sku-num-box");
		var num = parseInt($(".sku-num-box input").val());
		if (num <= opt.min_num) {
			$this.find(".sku-num-reduce").addClass("sku-num-disabled");
		}
		else{
			$this.find(".sku-num-reduce").removeClass("sku-num-disabled");
		}
		if (num >= opt.max_num) {
			$this.find(".sku-num-plus").addClass("sku-num-disabled");
		}
		else{
			$this.find(".sku-num-plus").removeClass("sku-num-disabled");
		}
		if(num<=opt.max_num){
			$("#warn").empty();
		}
	}
	//增加
	function plus(){
		var $input = $(this).parent().parent().find("input");
		var num = parseInt($input.val());
		if(num>=opt.max_num){
			$("#warn").html("宝贝数量不能超过最大可购买数量!").show().delay(2000).fadeOut(500);
			return;
		}
		num = num + 1;
		$input.val(num);
		setEnable();
	}
	//减少
	function reduce(){
		var $input = $(this).parent().parent().find("input");
		var num = parseInt($input.val());
		if(num<=opt.min_num){
			if(opt.min_num>1){
				$("#warn").html("宝贝数量不能低于起买数量!").show().delay(2000).fadeOut(500);
			}
			return;
		}
		num = num - 1;
		$input.val(num);
		setEnable();
	}
	/**
	 * 文本框按下
	 */
	function inputKeyDown(){
		var $this = $(".sku-num-box");
		var num = parseInt($this.find("input").val());
		if (num_reg.test($this.find("input").val())) {
			tempInput = $this.find("input").val();
		}
	}
	/**
	 * 文本框松开
	 */
	function inputKeyUp(){
		var $this = $(".sku-num-box");
		var $val = $this.find("input").val();
		var num = parseInt($val);
		if (!num_reg.test($val)) {
			if($val==""){
				$this.find("input").val(1);
			}
			else{
				$this.find("input").val(tempInput);
			}
		}
		setEnable();
		if(num>opt.max_num){
			$("#warn").show().html("您所填写的宝贝数量超过最大可购买数量!");
		}
		else if(num<opt.min_num&&num>0){
			$("#warn").show().html("您所填写的宝贝数量低于起卖数量!");
		}
	}
	module.exports.render  = function (p){
		opt = $.extend(defaults, p);
		var $this = $(".sku-num-box");
		$this.find("input").bind("keydown",inputKeyDown);
		$this.find("input").bind("keyup",inputKeyUp);
		$this.find(".sku-num-reduce").bind("click",reduce);
		$this.find(".sku-num-plus").bind("click",plus);
		setEnable();
	};
});