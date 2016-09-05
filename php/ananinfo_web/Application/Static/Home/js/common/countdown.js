/**
 * Created by yuanx on 2015/1/22.
 * 通用倒计时
 */
define(function(require,exports,module){
	var $ = require("jquery");
	var Timer = require("timer");
	var util = require("common/util");
	/**
	 * 倒计时
	 * @param target dom选择器表达式
	 * @param time 时间间隔
	 * @param now 当前时间戳
	 * @param endtime 结束时间戳
	 * @param method 调用方法
	 * @constructor
	 */
	function countdown(target,time,now,endtime,method){
		count();
		setInterval(count,time);
		function count(){
			now = parseInt(now) + time;
			$(target).each(function(){
				var n = new Date();
				var e = new Date();
				n.setTime(parseInt(now));
				e.setTime(parseInt(endtime));
				var t = new Timer(n,e);
				$(this).html(util.call(t,method));
			});
		}
	}
	module.exports.timer = Timer;
	module.exports.countdown = countdown;
});

/**
 * 计时器
 *
 */
define("timer",function(require,exports,module) {
	/**
	 *
	 * @param n 当前时间
	 * @param d 结束时间
	 * @constructor
	 */
	function Timer(now,d) {
		this.differTime = d.getTime() - now.getTime();
		if (this.differTime < 0) {
			this.differTime = 0;
		}
		this.d = parseInt((this.differTime / 1000) / 3600 / 24);//天
		this.h = parseInt((this.differTime / 1000) / 3600);//小时
		this.h = this._fillZero(this.h);
		this.m = parseInt(((this.differTime / 1000) % 3600) / 60);//分钟
		this.m = this._fillZero(this.m);
		this.s = parseInt(((this.differTime / 1000) % 3600) % 60);//秒
		this.s = this._fillZero(this.s);
		this.ps = parseInt(((this.differTime / 100) % 36000) % 600 % 10);//小数秒
		this.ps = this.ps;
		this.pps = parseInt(((this.differTime / 10) % 360000) % 6000 % 10%10);//百分秒
		this.pps = this.pps;
	}

	Timer.prototype.toString0 = function () {
		if (this.differTime == 0) {
			return "已结束";
		}
		return this.h + ":" + this.m + ":" + this.s + "." + this.ps;
	}

	Timer.prototype.toString1 = function () {
		if (this.differTime == 0) {
			return "已结束";
		}
		this.h = this.h % 24;
		return "<em>" + this.d + "</em>" + "天" + "<em>" + this.h + "</em>" + "时" + "<em>" + this.m + "</em>" + "分" + "<em>" + this.s + "</em>" + "秒";
	}

	Timer.prototype.special_61 = function () {
		if (this.differTime == 0) {
			return "已结束";
		}
		this.h = this.h % 24;
		return "<b>" + this.d + "</b>" + "天" + "<b>" + this.h + "</b>" + "时" + "<b>" + this.m + "</b>" + "分" + "<b>" + this.s + "</b>" + "秒";
	}


	Timer.prototype.toStringindex = function () {
		return this.h + ":" + this.m + ":" + this.s + "." + this.ps+this.pps;
	}

	Timer.prototype.toString2 = function () {
		this.h = this.h % 24;
		return this.d + "天" + this.h +  "时" +  this.m  + "分" + this.s +  "秒";
	}

	Timer.prototype.toString3 = function () {
		if (this.differTime == 0) {
			return "已结束";
		}

		this.h = this.h % 24;
		return "<b>" + this.d + "</b>天<b>" + this.h + "</b>时<b>" + this.m + "</b>分<b>" + this.s + "</b>秒"
	}

	Timer.prototype.toString4 = function () {
		return this.h + '小时' + this.m + '分' + this.s + '秒';
	}

	Timer.prototype._fillZero = function (n) {
		if (n < 10) {
			return "0" + n;
		}
		return n;
	}

	Timer.prototype.getTime = function () {
		var t = new Array();
		var s_h = '';

		if (this.h>10000) {
			s_h += "<em>" + parseInt(this.h / 10000 ) + "</em>";	
		};
		if (this.h>1000) {
			s_h += "<em>" + parseInt(this.h % 10000 / 1000 ) + "</em>";	
		};
		if (this.h>100) {
			s_h += "<em>" + parseInt(this.h % 1000 / 100 ) + "</em>";	
		};
		s_h += "<em>" + parseInt(this.h % 100 / 10) + "</em><em class='nomr'>" + (this.h % 10) + "</em>：";

		t.push(s_h);
		var s_m = "<em>" + parseInt(this.m / 10) + "</em><em class='nomr'>" + (this.m % 10) + "</em>：";
		t.push(s_m);
		var s_s = "<em>" + parseInt(this.s / 10) + "</em><em class='nomr'>" + (this.s % 10) + "</em>";
		t.push(s_s);
		return t;
	}
	module.exports = Timer;
});