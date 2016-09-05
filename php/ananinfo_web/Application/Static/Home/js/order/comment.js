/**
 * Created by yuanx on 2015/2/3.
 * 评价晒单
 */
define(function(require,exports,module){
	require("form");
	var $ =require("jquery");
	$(function () {
		$("a[name='btn-order-show']").click(function () {
			var c = $(this).parent().parent().next();
			if (c.is(":hidden")) {
				c.show();
			}
			else {
				c.hide();
			}
		});
		$(".star-list-edit li").click(function () {
			var i = $(this).index();
			$(this).parent().find("li").each(function (j) {
				if (j <= i) {
					$(this).removeClass("empty");
				}
				else {
					$(this).addClass("empty");
				}
			});
		});
	});
	$(".member-photo-list").delegate('.closeX', 'click',
		function() {
			if (!confirm("确人删除吗?")) {
				return false
			}
			var _this = $(this);
			var size = _this.parent().parent().children("li").size() - 1;
			_this.parent().parent().siblings().find(".pictures-nums-show").html(size.toString() + '/10');
			_this.parent().remove()
		});
	$(".paging-btn").on('click',
		function() {
			var pageGo = parseInt($.trim($("#gopage").val()));
			if (isNaN(pageGo)) {
				return false
			}
			var url = window.location.href;
			var page = parseInt($("#page-info").attr('page'));
			var total = parseInt($("#page-info").attr('pages'));
			if (total == 1 || pageGo < 1 || pageGo > total) {
				return false
			}
			window.location.href = url.indexOf('page=') != -1 ? (url.replace(/page=\d+/g, 'page=' + pageGo)) : (url + '&page=' + pageGo)
		});
	$(".paging-input").on('keyup',
		function(e) {
			var code = e.keyCode || e.which;
			code == 13 && $(".paging-btn").click()
		});
	var isUploading = {};//是否正在上传中
	$(".pictures-submit").bind('click',
		function() {
			var _this = $(this);
			for(var key in isUploading){
				if(isUploading[key]){
					_this.siblings(".rst-show").css({
						"color": "red"
					}).html(' ---提示：图片上传中，请等待图片上传完成再提交!');
					return;
				}
			}
			var o = {};
			o.optype = 'addComments';
			o.oid = parseInt($.trim(_this.attr("oid")));
			o.pid = parseInt($.trim(_this.attr("pid")));
			o.star = 5 - _this.parent().parent().siblings(".member-input-row").eq(0).find(".star-list-edit li.empty").length;
			o.content = _this.parent().parent().siblings(".member-input-row").eq(1).find(".member-experience-input").val();
			var temp = _this.parent().parent().siblings(".member-input-row").eq(2).find(".pictures-list li img");
			o.pictures = [];
			var tempStr;
			temp.length > 0 && temp.each(function() {
				tempStr = $.trim($(this).attr("src"));
				tempStr.length > 0 && o.pictures.push(tempStr)
			});
			_this.siblings(".rst-show").html('');
			if (o.star == 0) {
				_this.siblings(".rst-show").css({
					"color": "red"
				}).html('  ---提示：请先评分！');
				return false
			}
			if (o.content.length == 0) {
				_this.siblings(".rst-show").css({
					"color": "red"
				}).html(' ---提示：“心得”内容不能为空！');
				return false
			}
			var url = '/member.php?mod=order&op=comments';
			$.post(url, o,
				function(data) {
					if (typeof data.status != 'undefined' && data.status == 1) {
						_this.siblings(".rst-show").css({
							"color": "green"
						}).html('---提示：操作成功！');
						window.location.href = window.location.href;
						return true
					} else {
						_this.siblings(".rst-show").css({
							"color": "red"
						}).html('---提示：操作失败！');
						return false
					}
				},
				'json')
		});
	window.obj = '';
	$(".upload_fileup").change(function() {
		var filename = $(this).val();
		var suffix = filename.substring(filename.lastIndexOf(".")+1);
		if(suffix!="jpg"&&suffix!="png"&&suffix!="gif"&&suffix!="jpeg"){
			st_alert("文件格式不正确，仅支持jpg、jpeg、png、gif格式！");
			return;
		}
		window.obj = $(this);
		//var showspan = $(this).parent().parent().parent().parent().siblings(".member-input-row").find('. ');
		var picList = $(this).parent().parent().siblings(".pictures-list");
		if (picList.find("li img").size() >= 10) {
			st_alert("限制10张图片！");
			return false
		}
		var upload_name = "uploading"+parseInt(Math.random()*10000000);
		$("<li id='"+upload_name+"'><span title='删除本图' class='closeX'>&times;</span><a><img src='/static/v2/images/uploading.gif' class='loading_img'/></a></li>").appendTo(picList);
		$(this).parent().append("<input type='hidden' name='uploadId' value='"+upload_name+"' />");
		$(this).parent().ajaxSubmit({
			timeout:   60000,
			beforeSend:function(){
				$(this).prop("targetDomId",upload_name);
				isUploading[upload_name] =true;
			},
			success:function(){

			},
			complete: function(xhr) {
				isUploading[$(this).prop("targetDomId")] = false;
				if(xhr.statusText =='timeout'){
					$("#"+$(this).prop("targetDomId")).find("a").html("<b class='upload-warn'>图片过大，上传超时！</b>");
				}
				else if (xhr.status!=200 && xhr.status!=0) {
					alert('处理失败.请稍后再试');
				}
				else {
					data = $.parseJSON(xhr.responseText);
					if(data.error==1){
						st_alert(data.errMsg,"","error");
						$("#"+$(this).prop("targetDomId")).remove();
						return;
					}
					var uploadDom = $("#"+data.uploadId);
					var data = $.parseJSON(xhr.responseText);
					var htmlStr = '<span title="删除本图" class="closeX">&times;</span><a href="'+ data.url + '" target="_blank"><img src="' + data.url + '!productsmall" /></a>';
					var picList = uploadDom.parent();
					uploadDom.html(htmlStr);
					uploadDom.attr("data-success","ok");
					var showlength = picList.find("li[data-success='ok'] img").size();
					picList.prev().find(".pictures-nums-show").html(showlength.toString() + '/10');
				}
			}
		});
	});
});