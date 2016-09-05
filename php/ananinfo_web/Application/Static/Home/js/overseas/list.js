define(function(require,exports,module){
    var $ = require("jquery");
    $("ul#extra-order li a").each(function() {
        var order = $("#extra-order").attr("order");
        var dasc = $("#extra-order").attr("dasc");
        if ($(this).attr("order") == order) {
            //选中样式
            $(this).parent().addClass("cur");
            //上下箭头
            var ilable = $(this).siblings('i');
            
            if (order==4) {
                $(this).after('<i class="' + (dasc * 1 == 1 ? 'down' : 'up') + '"></i>');
            }else{
                $(this).after('<i class="' + (dasc * 1 == 1 ? 'up' : 'down') + '"></i>');    
            }
            //
            return false;
        }
    });

    $('.paging-btn').bind('click', function() {
        var pageGo = parseInt($.trim($("#gopage").val()));
        if (isNaN(pageGo)) {
            return false;
        }

        var url = $(this).attr('url');
        var page = parseInt($("#page-info").attr('page'));
        var total = parseInt($("#page-info").attr('pages'));

        if (total == 1 || pageGo < 1 || pageGo > total) {
            return false;
        }
 
         var goUrl = '';
         if (url.indexOf('page=') != -1) {
             goUrl = url.replace(/page=\d+/g, 'page=' + pageGo);
         } else if (url.indexOf('?') != -1) {
             goUrl = url + '&page=' + pageGo;
         } else {
             goUrl = url + '?page=' + pageGo;
         }
		 goUrl= goUrl.replace('#listanchor','');
		 goUrl+='#listanchor';
		 window.location.href = goUrl;

    });

    $(".paging-input").on('keyup', function(e) {
        var code = e.keyCode || e.which;
        code == 13 && $(".paging-btn").click();
    });

    $(".btn-custom-page").bind('click', function() {
        var url = window.location.href;
        var page = parseInt($("#page-info").attr('page'));
        var total = parseInt($("#page-info").attr('pages'));
        if (total == 1) {
            return false;
        }
        var pageGo;
        var indexThis = $(".btn-custom-page").index(this);
        if (indexThis == 0) {
            pageGo = (page - 1 > 0) ? (page - 1) : 1;
        } else {
            pageGo = (page + 1 <= total) ? (page + 1) : total;
        }
        if (page === pageGo) {
            return false;
        }
        var goUrl = '';
        if (url.indexOf('page=') != -1) {
            goUrl = url.replace(/page=\d+/g, 'page=' + pageGo);
        } else if (url.indexOf('?') != -1) {
            goUrl = url + '&page=' + pageGo;
        } else {
            goUrl = url + '?page=' + pageGo;
        }
		goUrl= goUrl.replace('#listanchor','');
		goUrl+='#listanchor';
        window.location.href = goUrl;
    });
    $("ul#extra-order li a").bind('click', function() {
        var order0 = $.trim($("#extra-order").attr('order'));
        var order = $.trim($(this).attr('order'));

        var preurl = $.trim($("#extra-order").attr("pre"));
        var symbol = preurl.indexOf('?') != -1 ? '&' : '?';
        var url = preurl + symbol + 'order=' + order;
        var ilable = $(this).siblings('i');

        var cls = ilable.hasClass("up") ? "down" : "up";
        ilable.removeClass().addClass(cls);
        if (order0 != order) {
            var dasc = 0;
        }else if(order==4){
            var dasc = ilable.hasClass("down") ? 1 : 0;
        }else{
            var dasc = ilable.hasClass("up") ? 1 : 0;
        }
        url += '&dasc=' + dasc;
        url= url.replace('#listanchor','');
		url+='#listanchor';
        window.location.href = url;
    });
	//分类冒号
	var htmlStr = '<a class="extra-colon">:</a>';
	$(".direct-list-type-row h2").each(function(i){
		$(this).append(htmlStr);
	});
	
});