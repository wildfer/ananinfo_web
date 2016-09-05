/**
 * 购物车JS
 */
define(function(require,exports,module){
	require("cityselect");
	require("form");
	var $ = require("jquery");
	var util = require("common/util");
	var addr_showname = {
		"name" : "姓名",
		"idt_a" : "身份证正面",
		"idt_b" : "身份证背面",
		"idt_number" : "身份证号",
		"address" : "地址",
		"mobile" : "手机号",
		"tel" : "电话",
		"area" : "电话区号",
		"email" : "邮箱",
		"postcode" : "邮编",
		"province" : "省份",
		"city" : "市区"
	};
	var addr_null = {
		"id":"0",
		"uid":"",
		"name":"",
		"idt_a":"",
		"idt_b":"",
		"idt_a_show":"/static/v2/images/icon_id_card.png",
		"idt_b_show":"/static/v2/images/icon_id_card_1.png",
		"idt_number":"",
		"address":"",
		"mobile":"",
		"tel":"",
		"email":"",
		"postcode":"",
		"ctime":"",
		"default":"",
		"province":"",
		"city":""
	};

	$("#gopay").click( function () { 
		var addr  = $("input[name='ad_selected']:checked").val()
		var order = $('#order_id').val();
		var cart  = $('#cart_ids').val();
		
		$.ajax({
			url: '/member.php?mod=order&op=check_addr_pic',
			type: 'get',
			dataType: 'json',
			data: {addr_id: addr, order_id : order, cart_ids : cart},
		})
		.done(function(data) {
			if (data.status) {
				$("#cart_to_order").submit();
			}else{
				alert(data.info);
				return;
			}
		});
		
	});


	$(function(){
		function changeFile(){

		}
		calc_pay_total();
		$('#gopay').prop('disabled',false);
		$.fn.numeral = function () {
			$(this).css("ime-mode", "disabled");
			this.bind("keypress", function (e) {
				var code = (e.keyCode ? e.keyCode : e.which);  //兼容火狐 IE   
				if (!/msie/.test(navigator.userAgent.toLowerCase()) && (e.keyCode == 0x8))  //火狐下 不能使用退格键  
				{
					return;
				}
				return code >= 48 && code <= 57 || code == 46;
			});
			this.bind("blur", function () {
				if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
					this.value = this.value.substr(0, this.value.length - 1);
				} else if (isNaN(this.value)) {
					this.value = " ";
				} 
			});
			this.bind("paste", function () {
				var s = clipboardData.getData('text');
				if (!/\D/.test(s));
				value = s.replace(/^0*/, '');
				return false;
			});
			this.bind("dragenter", function () {
				return false;
			}); //只允许输入数字，小数点
			this.bind("keyup", function () {
				this.value = this.value.replace(/[^\d.]/g, "");
				//必须保证第一个为数字而不是.
				this.value = this.value.replace(/^\./g, "");
				//保证只有出现一个.而没有多个.
				this.value = this.value.replace(/\.{2,}/g, ".");
				//保证.只出现一次，而不能出现两次以上
				this.value = this.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
			});
		};
		$('.member-address-list').on('click', '.member-address-inner-home', function(event) {
			event.stopPropagation();
			var _addr_id = $(this).attr('addr_id');
			var _this = $(this);
			if ($(this).hasClass('default')) {
				return;
			}
			$.ajax({
				url:'/member.php?mod=address&op=set_default',
				data:{'id':_addr_id},
				dataType:"json"
			}).done(function(data) {
				if(data.status==false){
					return false;
				}
				$('.member-address-inner-home.default').removeClass('default').html('设为默认');
				_this.addClass('default').html('默认');
			})
		}).on('click', '.member-address-inner-edit', function(event) {
			event.stopPropagation();
			var addr_id = $(this).attr('addr_id');
			for(var i in addrs){
				if (addrs[i].id==addr_id) {
					build_addr_form(addrs[i]);
					break;
				};
			}
		}).on('click', 'tr', function(event) {
			$('.choosed').removeClass('choosed');
			$('.ad_selected').prop('checked',false);
			$(this).addClass('choosed').find('.ad_selected').prop('checked',true);
			$('#form_addr').val($(this).find('.ad_selected').val());
		});

		$(".btn-address-add , .member-check-order-address-header>a").click(function(event) {
			build_addr_form();
		});
		$('#addr_cancel').click(function(event) {
			$('.member-check-goods-info').animate({marginTop: 0}, 200);
			$('#address_form').animate({height: 0, paddingBottom : 0},200);
		});

		// 显示保税仓提示
		$('#limit500_close').click(function(event) {
			$('#limit500_shade').hide(200);
		});
		$('#limit500').click(function(event) {
			event.stopPropagation();
		});
		$('#limit500_shade').click(function(event) {
			$(this).hide(200);
		});
		$('#addr_name').blur(function(event) {
			this.value = this.value.replace(/[^\u4e00-\u9fa5]/gi,"");
		});

		$('#addr_form').submit(function(event) {
			$('#addr_name').trigger('blur');
			var addr_data = [];
			addr_data.name = $('#addr_name').val();
			addr_data.id = $('#addr_id').val();
			if ( addr_data.id==0) {
				addr_data.idt_a = $('#addr_idt_a').val();
				addr_data.idt_b = $('#addr_idt_b').val();
			}
			
			
			addr_data.idt_number = $('#addr_idt_number').val();
			addr_data.prov = $('#addr_prov').val();
			addr_data.city = $('#addr_city').val();
			addr_data.dist = $('#addr_dist').val();
			addr_data.address = $('#addr_address').val();
			
			addr_data.postcode = $('#addr_postcode').val();
			delete addr_data.id;
			var temp='';
			for (var i in addr_data) {
				//加入身份证不是必传的判断
				if ( addr_data[i]=='' && i != 'idt_a' && i != 'idt_b'  ) {
					temp = typeof addr_showname[i]=='undefined'?'收货地区': addr_showname[i];

					st_alert(temp + '不能为空');

					return false;

				}else{
				    switch(i){
						case 'idt_number':
						    if(!(/^(\d{17}|\d{14})(\d|X|x)$/.test(addr_data[i]))){
							alert("身份证号码不对！");
							return false;
						    }
						    break;
						case 'mobile':
						    if(!(/^1[3|4|5|8|7]\d{9}$/.test(addr_data[i]))){
							alert("手机号不对！");
							return false;
						    }
						    break;
						case 'postcode':
						    if(!(/^\d{5,6}$/.test(addr_data[i]))){
								alert("邮编格式错误！");
								return false;
						    }
					    	break;
				    }
			    }
			}
			addr_data.mobile = $('#addr_mobile').val()
			addr_data.area = $('#addr_area').val()
			addr_data.tel = $('#addr_tel').val()

			var has_contact = false;
			if ( addr_data.mobile!='' && $.isNumeric(addr_data.mobile)==true && addr_data.mobile.length==11) {
				has_contact = true;
			}
			if ( addr_data.area!='' && addr_data.tel!='' && $.isNumeric(addr_data.tel)==true) {
				has_contact = true;
			}
			if (!has_contact) {
				alert('联系电话不正确,至少需要正确填一种');
				return false;
			}
			var bar = $('.bar');
			var percent = $('.percent');
			$('#addr_save').prop('disabled', true).addClass('btn-disabled').val('提交中...');
			$(this).ajaxSubmit({ 
				timeout:0,
				beforeSend: function() {
					$('#addr_cancel').hide();
					$('#gopay').prop('disabled', true);
				},
				success: function() {
				},
				complete: function(xhr) {
					if (xhr.status!=200 && xhr.status!=0) {
						switch(xhr.status*1){
							case 413:
								alert("提交失败!图片文件限制在1M以下");
								break;
							default:
								alert("提交失败"+xhr.status+"!\n不可预料异常");
								break;
						}
						
					}else if( (xhr.error != undefined && (typeof xhr.error!='function') && xhr.error!='') ||typeof xhr.responseText=='undefined'|| xhr.responseText==null || xhr.responseText=='' ){
						alert("提交失败\n不可预料异常");
					}else{
						var result = $.parseJSON(xhr.responseText);
						if (result.status==false) {
						    setBtnAble();
						    alert(result.info);
						    return false;
						}else{
							if ($('#addr_id').val()!=result.id) {//新增
								result.data.id = result.id;
								addrs.push(result.data);
							}else{//修改
								$.each(addrs, function(index, val) {
									if (val.id==result.id) {
										for(var i in result.data ){
											val[i] = result.data[i];
										}
										result.data = val;
										return;
									};
								});	
							}
						}
						
						build_addr_box(result.data);
						hide_addr_form();
					}
					setBtnAble();

				}
			});
			return false;
		});
		$('#cart_to_order').submit(function(event) {
			// alert($('#form_addr').val()=='');return false;
			if ($('#form_addr').val()=='') {
				st_alert('请选择一个收货地址', '提示',"warn");
				return false;
			};
			if ($('#order_id').val()!='') {
				return true;
			};
			// 尝试生成订单
			var order_id = '';
			var vc = $('#verify_code_box').val();
			$.ajax({
				url: '/member.php?mod=cart&op=add_order',
				type: 'get',
				dataType: 'json',
				async:false,
				data: {cart_ids: cart_ids , shipping_type : shipping_type, verify_code: vc},
			})
			.done(function(data) {
				if (data.status) {
					order_id = data.data;
				}else if (data.data=='500'){
					$('#limit500_shade').show();
				}else{
					alert(data.data);
				}
			});
			if (order_id=='') {
				return false;
			};
			$('#order_id').val(order_id);
			return true;
		});
		$(".ticket-sel-box-inner").click(function(){
			$(this).parent().find(".ticket-sel-box-combo").slideToggle("fast");
		});
		$('.ticket-sel-box-combo li').click(function(event) {
			event.stopPropagation();
			$(".ticket-sel-box-inner input").val($(this).attr('code'));
			$(this).parent().parent().hide();
			$(".ticket-sel-box em").trigger("click");
		});
		$(".ticket-sel-box-inner input").click(function(e){
			e.stopPropagation();
		});
		$(".ticket-sel-box em").click(function(){
			var _this = $(this);
			var coupon_code = _this.prev().find('input').val();
			if (coupon_code=='') {
				$(".ticket-sel-box").hide();
				$(".ticket-state-yes").show();
				//$('#choose_coupon b').html("<b>未使用</b>");
			}else{	
				if ($('#order_id').val()=='') {
					var ajax_param = {
						url: '/member.php?mod=cart&op=check_coupon',
						type: 'get',
						dataType: 'json',
						data: {cart_ids:cart_ids , coupon :coupon_code,shipping_type:shipping_type}
					};
				}else{
					var ajax_param = {
						url: '/member.php?mod=order&op=check_coupon',
						type: 'get',
						dataType: 'json',
						data: {order_id:$('#order_id').val() , coupon :coupon_code}
					};
				}
				$.ajax(ajax_param).done(function(data) {
					if (data.status==true) {
						coupon_amount = data.data;
						$('#choose_coupon b').html("¥-" + data.data);
						calc_pay_total();
						$(".ticket-sel-box").hide();
						$(".ticket-state-cancel").show();
					}else{
						alert(data.info);
					}
				});
			}
		});
		$('#credit_hongbao , #use_balance').numeral();
		$('#credit_hongbao , #use_balance').blur(function(event) {
			var _value = $(this).val()=='' ? 0 : parseFloat($(this).val()).toFixed(2);
			$(this).val(_value);
			var _max = parseFloat($(this).attr('max'));
			if(_max==0){
				$(this).val('');
				return;
			}
			if (_value==0) {
				$(this).val('')
			}

			if (_value>_max) {
				_value = _max;
				$(this).val(_max);
			}

			if ($(this).attr('name')=='credit_hongbao') {
				credit_hongbao = _value;
			}else{
				use_balance = _value;
			}
			
			calc_pay_total();
		});

		$('#use_credit_hongbao_checkbox,#use_balance_checkbox').change(function(event) {
			var value = $(this).is(':checked') ? $(this).next().attr('max') : 0;
			$(this).next().val(value);
			$(this).next().trigger('blur');
		});

		$(".ticket-state-yes").click(function(){
			$(this).hide().parent().find(".ticket-sel-box").show();
		});
		$(".ticket-state-cancel").click(function(){
			coupon_amount = 0;
			calc_pay_total();
			$(this).hide();
			$('#choose_coupon b').html("");
			$('.ticket-sel-box-inner input').val('');
			$(".ticket-state-yes").show();
		});
	});
	function calc_pay_total(){
		// total 预估总价
		// coupon_amount  优惠抵扣
		// use_balance  使用账户余额
		// credit_hongbao  使用红包
		pay_total = parseFloat(total - coupon_amount - use_balance - credit_hongbao).toFixed(2);
		pay_total = pay_total<=0 ? '0.00' : pay_total;
		$('#pay_total').html("¥" + pay_total + "元");
	}

	function build_addr_form(addr){
		if ( addr == undefined ) {
			var addr = addr_null;
			var tag = 'add';
		}else{
			var tag = 'edit' + addr.id;
		}

		if ( tag == $('#address_form').attr('tag') ) {
			var _addr_height = $('#address_form').height();
			if (_addr_height>0) {
				hide_addr_form();
			}else{
				$('#address_form').animate({height: 670, paddingBottom : 30},400);
				$('.member-check-goods-info').animate({marginTop: 10}, 400);
			}
		}else{
			$('.member-check-goods-info').animate({marginTop: 0}, 200);
			$('#address_form').animate({height: 0, paddingBottom : 0},200,function(){
				$('#addr_id').val(addr.id);
				$('#addr_name').val(addr.name);
				$('#addr_mobile').val(addr.mobile);
				 var tempx = addr.tel.match(/(\d+)-(\d+)/);
				 if(tempx && typeof tempx=='object' && tempx.length==3){
				     $('#addr_area').val(tempx[1]);
				     $('#addr_tel').val(tempx[2]);
				 }else{
				     $('#addr_area').val(addr.area);
				     $('#addr_tel').val(addr.tel);
				 }
				
				
				$('#addr_email').val(addr.email);
				$('#addr_postcode ').val(addr.postcode);
				$('#addr_address').val(addr.address);
				$('#addr_idt_a').parent().append('<input type="file" name="addr_idt_a" id="addr_idt_a">');
				$('#addr_idt_a').remove()
				$('#addr_idt_b').parent().append('<input type="file" name="addr_idt_b" id="addr_idt_b">');
				$('#addr_idt_b').remove()
				$("#addr_idt_a,#addr_idt_b").change(function(){
					var sel_note = $(this).parent().find("span");
					sel_note.text("已选择-"+$(this).val());
					sel_note.show();
				});
				if(!addr.idt_a==""){
					$('#idt_a_show').attr('data-big-img',img_prefix+addr.idt_a);
					$('#idt_a_show').attr('src','').attr('src',addr.idt_a_show);
				}
				else{
					$('#idt_a_show').attr('data-big-img',"");
					$('#idt_a_show').attr('src','').attr('src',addr.idt_a_show);
				}
				if(!addr.idt_b==""){
					$('#idt_b_show').attr('data-big-img',img_prefix+addr.idt_b);
					$('#idt_b_show').attr('src','').attr('src',addr.idt_b_show);
				}
				else{
					$('#idt_b_show').attr('data-big-img',"");
					$('#idt_b_show').attr('src','').attr('src',addr.idt_b_show);
				}
				$('#addr_idt_number').val(addr.idt_number);
				$("#addr_cityselect").citySelect({
					prov: addr.province,
					city: addr.city,
					dist: addr.dist,
					nodata:"none",
					required:false
				});
				var _title = tag=='add' ? '新增收货地址' : '修改收货地址';
				$('#address_form').attr('tag',tag).animate({height: 670, paddingBottom : 30},400).find('.member-pay-way-header>h2').html(_title);
				$('.member-check-goods-info').animate({marginTop: 10}, 400);
			});
		}
	}
	function build_addr_box(result){
		var dom     = $('#edit' + result.id);
		var img_str = '';
		if (dom.size()>0) {
			dom.find('.ad_name').html(result.name);
			dom.find('.ad_phone').html(result.mobile.length>0 ? result.mobile : result.tel);
			dom.find('.ad_prov').html( result.province );
			dom.find('.ad_city').html( result.city );
			dom.find('.ad_dist').html( result.dist );
			dom.find('.ad_address').html( result.address );
			if(result.idt_a){
				dom.find('.member_idt_a').attr("src",img_prefix+result.idt_a+"");
				dom.find('.member_idt_a').attr("data-big-img",img_prefix+result.idt_a);
			}
			if(result.idt_b){
				dom.find('.member_idt_b').attr("src",img_prefix+result.idt_b+"");
				dom.find('.member_idt_b').attr("data-big-img",img_prefix+result.idt_b);
			}
		}else{
			var onlyone = addrs.length==1 ? 'checked="checked"' : '';
			// liadd

			if (result.idt_a != undefined) {
				img_str += '<img class="member_idt_a mr5" title="点击查看大图" src="'+img_prefix+result.idt_a+'" name="img_a" data-big-img="'+img_prefix+result.idt_a+'" height="30">';
			}else{
				img_str += '<img class="member_idt_a mr5" src="/static/v2/images/icon_id_card.png"   height="30">';
			}

			if (result.idt_b != undefined) {
				img_str += '<img class="member_idt_b" title="点击查看大图" src="'+img_prefix+result.idt_b+'" name="img_b" data-big-img="'+img_prefix+result.idt_b+'" height="30">';
			}else {
				img_str += '<img class="member_idt_b" src="/static/v2/images/icon_id_card_1.png"   height="30">';
			}

			var dom = '<tr id="edit'+result.id+'">';
				dom += '<td><label><input type="radio" value="'+result.id+'" name="ad_selected" class="ad_selected" '+onlyone+'>';
				dom += '<span class="ad_name"> '+result.name+'</span></label></td>';
				dom += '<td><span class="ad_phone">'+result.mobile+'</span></td>';
				dom += '<td><span class="ad_prov">'+result.province+'</span> ';
				dom += '<span class="ad_city">'+result.city+'</span> ';
				dom += '<span class="ad_dist">'+result.dist+'</span> ';
				dom += '<span class="ad_address">'+result.address+'</span></td>' +
				'<td>'+img_str+'</td>';
				dom += '<td><a href="javascript:void(0)" class="member-address-inner-edit" addr_id="'+result.id+'" title="修改">修改</a>';
				dom += addrs.length==1 ? '<span class="member-address-inner-home default" addr_id="'+result.id+'">默认</span>' : '<a class="member-address-inner-home" addr_id="'+result.id+'">设为默认</a>';
				dom += '</td></tr>';
			$('.member-address-list').append(dom);
			$('#form_addr').val(result.id)

		}
	}
	function hide_addr_form(){
		$('.bar').width(0);
		$('.percent').html('');
		$('#addr_id').val('');
		$('#address_form').attr('tag','').animate({height: 0, paddingBottom : 0},400);
		$('.member-check-goods-info').animate({marginTop: 0}, 400);
	}
	//点击查看大图效果
	function showBigPic(t){
		if(t.attr("data-big-img")==""){
			return;
		}
		$(".pic-bg").show();
		$(".big-pic-wrap img").attr("src","").attr("src",t.attr("data-big-img"));
		$(".big-pic-wrap").show();
	}
	$('.member-check-order-address').on('click', 'img[name=img_a] , img[name=img_b]', function(event) {
		event.preventDefault();
		showBigPic($(this));
	});
	$("#idt_a_show,#idt_b_show").click(function(){
		showBigPic($(this));
	});
	$(".big-pic-wrap a").click(function(){
		$(".pic-bg").hide();
		$(".big-pic-wrap").hide();
	});
	$(".member-address-list tr").click(function(){
		$(this).find(".ad_selected").prop("checked",true);
	});
	function setBtnAble(){
		$('#addr_save').prop('disabled', false).removeClass('btn-disabled').val('保存收货地址');
		$('#addr_cancel').show();
		$('#gopay').prop('disabled', false);
	};
});