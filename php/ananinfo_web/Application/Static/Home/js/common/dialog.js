/**
 * Created by yuanx on 2015/2/2.
 * 弹窗封装
 */
(function (factory) {
	if (typeof define === 'function') {
		// using CMD; register as anon module
		define(function(require, exports, module) {
			var $ = require("jquery");
			factory(window,$);
		});
	} else {
		// no CMD; invoke directly
		factory(window,jQuery);
	}
})(function(window,$){
	var template = "<div class=\"m-dialog\">"+
	"<div style=\"display: block;\" class=\"container\">"+
	"<div class=\"title\"><h3 id='mTitle'>提示</h3><a href=\"javascript:\" class=\"btn-close\"></a></div>"+
	"<div class=\"m-content\">"+
	"<div class=\"pop-car-win\">"+
	"<div class=\"pop-content\">"+
	"<h4><b id='popIco'></b><span id='popCon'></span></h4>"+
	"<div class=\"btn-box\"></div>"+
	"</div></div></div></div></div>";

	/**
	 * 提示弹出框
	 * @param msg 弹出框信息
	 * @param title 弹出框标题
	 * @param type 弹出框类型
	 */
	window.st_alert = function(msg,title,type){
		st_dialog(msg,title,type,{
			"确定":{
				clickFun:function() {
					closeWin();
				}
			}
		});
	}

	/**
	 * 询问弹出框
	 * @param msg 弹出框信息
	 * @param title 弹出框标题
	 * @param fn 点击确定取消后的回调函数
	 */
	window.st_confirm = function(msg,title,fn){
		st_dialog(msg,title,"ask",	{
			"确定":{
				clickFun:function() {
					fn(true);
					closeWin();
				}
			},
			"取消":{
				clickFun:function() {
					fn(false);
					closeWin();
				}
			}
		});
	}

	/**
	 * 自定义弹窗
	 * @param msg 弹窗主要信息
	 * @param title 弹窗标题
	 * @param buttons 弹窗按钮
	 */
	window.st_dialog = function(msg,title,type,buttons){
		msg = msg||"提示信息";
		title = title||"提示";
		type = type||"warn";
		var dom = $(template);
		dom.find("#mTitle").text(title);
		dom.find("#popCon").text(msg);
		dom.find("#popIco").addClass(type);
		dom.find(".btn-close").bind("click",closeWin);
		/**
		 * 弹窗按钮
		 */
		for(var b in buttons){
			var b_dom = $("<a class='car-btn'></a>");
			b_dom.text(b);
			if(buttons[b].imp){//红色按钮
				b_dom.addClass("imp-btn");
			}
			b_dom.bind("click",buttons[b].clickFun);
			dom.find(".btn-box").append(b_dom);
		}
		dom.show().appendTo("body");
		$("<div class='win-bg'></div>").show().appendTo("body");
	}

	/**
	 * 关闭弹窗
	 */
	window.closeWin = function() {
		$(".m-dialog").remove();
		$(".win-bg").remove();
	}
});