define(function(require,exports,module) {

	var $ = require("jquery");
	var tip_text = '注册邮箱/55海淘会员帐号';
	var tip_text_array = ['注册邮箱/55闪淘帐号','55海淘帐号'];
	
    $(".win-header em").click(function() {
		$(".dialog").hide();
		$(".win-bg").hide();
    });

	$("#pwd,#verify_code_box").keydown(function(event) {
		if (event.keyCode == 13) {
			$("#submit").click();
		}
	});

	$('.login-tab li').click(function(event) {
		$('#login_type').val( $(this).index() );
	});
	$("#username").focus(function() {
		$(this).css("color", "#333333");
		if ($(this).val().trim() == tip_text) {
			$(this).val("");
		}
	});
	$("#username").blur(function() {
		if ($(this).val().trim() == tip_text
			|| $(this).val().trim() == "") {
			$(this).val(tip_text);
			$(this).css("color", '#cdcdcd');
		}
	});

	$('#verify_code').click(function(){
		refresh_verify_code();
	});
	function login(){
		$("#submit").html("正在登录....");
		$("#submit").addClass("btn-disabled");
		$("#submit").unbind("click");
		username1 = $("#username").val();
		pwd1 = $("#pwd").val();
		var memory1 = $("#memory").attr('checked') == 'checked';
		var type1 = $("#login_type").val();
		var verify_code_box1 = $('#verify_code_box').val();
		$.ajax({
			url : '/member.php',
			data : {
				'mod' : 'login',
				'op' : 'do_login',
				'username' : username1,
				'password' : pwd1,
				'memory' : memory1,
				'type' : type1,
				'verify_code' : verify_code_box1
			},
			type : "post",
			dataType : "json",
			success : function(data) {
				if (!data.status) {
					alert(data.info);
					$("#submit").html("登录");
					$("#submit").removeClass("btn-disabled");
					$("#submit").bind("click",login);
					refresh_verify_code();
				} else {
					window.location.reload();
				}
			}
		});
	}
	
	function refresh_verify_code(){
		$('#verify_code').attr('src','/misc.php?mod=veryfiy_code&' + Date.parse(new Date()));
	}
	$("#submit").bind("click",login);
	$(".login-tab ul li").each(function(i) {
		$(this).click(function() {
			$(".login-tab ul li").removeClass("cur");
			$(".login-tab ul li").eq(i).addClass("cur");
			$("#type").val(i);
			tip_text = tip_text_array[i];
			$("#username").val(tip_text_array[i]);
			$("#username").css("color", '#cdcdcd');
			$("#pwd").val('');
		});
	});
});

