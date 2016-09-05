/**
 * Created by yuanx on 2015/1/8.
 */
define(function (require, exports, module) {
    var $ = require("jquery");
    //刷新评论页面
    function refreshComment(pid, page) {
        $.ajax({
            url: '/index.php?mod=product&op=get_comment',
            type: 'post',
            dataType: 'html',
            data: {'product_id': pid, 'curpage': page},
        })
            .done(function (data) {
                //切换评论内容
                $(".direct-detail-praise-main").empty();
                var PageViewer = require("common/photo-viewer");
                var dateObj = $(data);
				dateObj.find(".direct-detail-comment-con").each(function(){
					var p = new PageViewer($(this));
				});

                $(".direct-detail-praise-main").append(dateObj);
                //锁定评论顶端
                var comment_position = $(".direct-detail-praise").offset().top - 54;
                $("html,body").animate({scrollTop: comment_position});

                clickUseful();
            });
    }

    //点击有用
    function clickUseful() {
        $(".direct-detail-comment-top p a").each(function () {
            var cid = $(this).attr('cid');
            var cur = $(this);

            if (cur.attr('click') == 0) {
                cur.bind('click', function () {
                    $.ajax({
                        url: '/index.php?mod=product&op=update_comment_useful',
                        type: 'post',
                        dataType: 'json',
                        data: {'uid': uid, 'cid': cid},
                    })
                        .done(function (data) {
                            switch (data.status) {
                                case -1:
                                    //需登陆后操作
                                    if (!uid) {
                                        $(".dialog").show();
                                        $(".win-bg").show();
                                        return false;
                                    }
                                    break;
                                case -2:
                                    //弹窗提示
                                    var button = {
                                        "知道了":{
                                            imp:true,
                                            clickFun:function() {
                                                closeWin();
                                            }
                                        }
                                    }

                                    st_dialog('您已经点过有用了,谢谢您对55闪淘的支持!', '提示', 'notice', button);
                                    return false;
                                    break;

                                default:
                                    cur.prev().show().stop().animate({top: "-15px"}, 200).fadeIn(200).fadeOut(200);
                                    cur.find("i").text(parseInt(cur.find("i").text()) + 1);
                                    cur.css("color", "#999");
                                    cur.css("cursor", "text");
                                    cur.unbind('click');
                                    break;
                            }
                        });
                });
            }
        });
    }

    var url = $(".paging-btn").attr('url');

    //URL跳转
    if (url) {
        $(".paging-btn").click(function () {
            var page = $('#gopage').val();

            if (page == '') {
                return false;
            }

            window.location.href = url + '&page=' + page;
        });

        $("#gopage").keyup(function (event) {
            var preg = /[^\d]+/;

            if (preg.test($(this).val())) {
                $(this).val('');
            }

            var code = event.keyCode || event.which;
            if (code == 13) {
                $(".paging-btn").trigger('click');
            }
        });
    } else {
        //控制页面文本框输入
        $(".direct-detail-praise-main").delegate('#gopage', 'keyup', function (event) {
            var preg = /[^\d]+/;
            if (preg.test($(this).val())) {
                $(this).val('');
            }

            var code = event.keyCode || event.which;
            if (code == 13) {
                $(".paging-btn").trigger('click');
            }
        });

        $(".direct-detail-praise-main").delegate('#page-container .paging-box>a', 'click', function (event) {
            var page = $(this).attr('page');
            var pid = $("#product_id").val();
            refreshComment(pid, page);
        });

        $(".direct-detail-praise-main").delegate('.paging-btn', 'click', function (event) {
            var page = $('#gopage').val();
            var pid = $("#product_id").val();
            if (page == '') {
                return false;
            }

            refreshComment(pid, page);
        });
    }

    module.exports.clickUseful = clickUseful;
});