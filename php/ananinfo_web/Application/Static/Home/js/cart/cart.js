/**
 * 购物车JS
 */
define(function(require,exports,module){
	var $ = require("jquery");
	var util = require("common/util");

	var old_count;

	$(function() {
		//商品刷新
		if (need_refresh) {
			$.ajax({
				url:"/member.php?mod=cart", 
				data:{'mod':'cart', 'op':'ajax_refresh_price'}
			}).always(function(){
				window.location.reload();
			})
		}
		//END

		$("#shipping_type_save,#shipping_type_fase").click(function(event) {
			if($("#shipping_type_save").attr("checked")){
				$(".note-row").eq(0).show().eq(1).hide();
			}else{
				$(".note-row").eq(1).show().eq(0).hide();
			}
		});
		$("#remove_select").click(function() {
			ids = [];
			$(".ids:checked").each(function() {
				ids.push($(this).val());
			});
			if(confirm('你确定要删除选中的商品吗？')==false){
				return false;
			}
			$.ajax({
				url : '/member.php?mod=cart&op=remove_array',
				data : {
					ids : ids
				},
				dataType : 'json',
				type : 'post'
			}).done(function(data) {
				if (data==true) {
					for (var i = ids.length - 1; i >= 0; i--) {
						$("#cart_product_"+ids[i]).remove();
					};
					$(".checkbox_store_all").each(function(index, el) {
						if ($("tr.body[store_id='"+$(this).val()+"']").length==0) {
							$(this).parent().parent().remove();
						}
					});
					checkcount();
					calc_price();
				}
			});
			return false;
		});
		$('.st_form_btn').val('去结算').click(function(event) {
			if ($('.overdue .ids:checked').length>0) {
				alert("购物车中有产品已经过期或缺少库存\n请处理后继续操作");
				return false;
			};
			if ($(this).val()!='去结算') {
				return false;
			}
			ids = [];
			$(".ids:checked").each(function() {
				ids.push($(this).val());
			});
			ids = ids.join(',');
			$(this).val('正在检查库存......');
			$('.overdue').removeClass('overdue');
			var return_status = false;
			$.ajax({
				url: 'member.php?mod=cart&op=add_order_before',
				type: 'GET',
				async: false,
				dataType: 'json',
				data: {ids: ids},
			})
			.done(function(data) {
				if (data.status==true) {
					if ( data['info'].length>0 ) {
						for (var i = data['info'].length - 1; i >= 0; i--) {
							$("tr.body[skuid='" + data['info'][i] + "']").addClass('overdue');
						};
						alert("购物车中有产品已经过期或缺少库存\n请处理后继续操作");
					}else{
						return_status = true;
					}
				}else{
					alert('错误的ID');
				}
			});
			
			$(this).val('去结算');
			return return_status;
		});

		$('.remove_this').click(function(event) {
			ids = [];
			ids.push($(this).attr('value'))
			if(confirm('你确定要删除这个商品吗？')==false){
				return false;
			}
			$.ajax({
				url : '/member.php?mod=cart&op=remove_array',
				data : {
					ids : ids
				},
				dataType : 'json',
				type : 'post'
			}).done(function(data) {
				if (data==true) {
					for (var i = ids.length - 1; i >= 0; i--) {
						$("#cart_product_"+ids[i]).remove();
					};
					$(".checkbox_store_all").each(function(index, el) {
						if ($("tr.body[store_id='"+$(this).val()+"']").length==0) {
							$(this).parent().parent().remove();
						};
					});
					checkcount();
					calc_price();
				}
			});
		});

		$("input[name='shipping_type']").click(function(event) {
			calc_price();
		});
		$(".ids").change(function(){
			var store_id = $(this).attr('store_id');
			var item_checked = $(".ids[store_id='"+store_id+"']:checked").length;
			var store_checkbox = $('.checkbox_store_all[value='+store_id+']');
			store_checkbox.prop('checked',item_checked!=0);
			calc_price();
		});
		$("#checkboxall").change(function() {
			$("input[type='checkbox']").prop("checked",$(this).is(":checked"));
			calc_price();
		});
		$(".checkbox_store_all").change(function() {
			$(".ids[store_id='"+$(this).val()+"']").prop("checked",$(this).is(":checked"));
			calc_price();
		});
		$(".sku-num-reduce").click(function() {
			old_count= parseInt($(this).parent().prev().val());
			var val = old_count - 1;
			if(val > 0){
				$(this).parent().prev().val(val);
				change_quantity($(this),$(this).parent().prev());
			}
			return true;
		});
		$(".sku-num-box input").blur(function(){
			change_quantity($(this),$(this));
		});
		$(".sku-num-plus").click(function(e) {
			old_count= parseInt($(this).parent().prev().val());
			var val = old_count + 1;
			$(this).parent().prev().val(val);
			change_quantity($(this),$(this).parent().prev());
		});
		calc_store_shipping_fee();
	});
	function change_quantity(_this,obj,number) {
		obj.parents('tr').removeClass('overdue');
		if ( _this.attr('disabled')!=undefined ) {
			obj.val(old_count)
			return false;
		}else{
			_this.attr('disabled','disabled');
		}
		count = parseInt(number)>0 ? parseInt(number) : obj.val();
		id = obj.attr("id").split('_')[1];
		$.ajax({
			url : '/member.php?mod=cart&op=change_quantity',
			data : {
				quantity : count,
				id : id
			},
			dataType : 'json',
			type : 'post'
		}).done(function(data){
			if (data.status==true) {
				obj.parents('tr').attr('quantity',count);
				var price = parseFloat(obj.parents('tr').attr('price'));
				var currency = obj.parents('tr').attr('currency');
				var total = util.display_convert(price,currency,'CNY',false) * count ;//总价人命币
				obj.parents('td').next().html("¥"+total);
				calc_price();
			}else{
				obj.val(old_count);
			}
			_this.removeAttr('disabled');
		})
		
	}
	function get_shipping_fee () {
	 	var shipping_type = $("input[name='shipping_type']:checked").val();
		var fee = 0;
		ids = [];
		$(".ids:checked").each(function() {
			ids.push($(this).val());
		});
		$.ajax({
			url: '/member.php?mod=cart&op=calc_shipping_fee',
			type: 'post',
			dataType: 'json',
			async:false,
			data: {ids: ids,uid:uid,type:shipping_type},
		})
		.done(function(data) {
			if (data.status==true) {
				$("#product_shipping_fee").text(data.info.total_fee);
				$("#shipping").text(data.info.shipping);
				$("#shipping_abroad").text(data.info.shipping_abroad);
				fee = parseFloat(data.info.total_fee);
			}else{
				// 恢复默认运费
			}
			
		})
		.fail(function(data) {
			// 恢复默认运费
		});
		return fee;
	}
	function calc_price(){
		var shipping_fee = get_shipping_fee();
		total = 0;
		price = 0;
		count = 0;
		$(".ids:checked").each(function(){
			var _this = $('#cart_product_' + $(this).val());
			//汇总数据采集
			count += parseInt(_this.attr("quantity"));
			price += util.display_convert(parseFloat(_this.attr("price")),_this.attr("currency"),'CNY',false);
			total += parseFloat( util.display_convert(parseFloat(_this.attr("price")),_this.attr("currency"),'CNY',false) * parseFloat(_this.attr("quantity")) );
		});

		var sum = util.moneyround(shipping_fee + total );
		$("#product_total").text(util.moneyround(total));
		$("#product_count").text(count);
		$("#sum_price").text(sum);
		if (sum==0) {
			$('.st_form_btn').prop('disabled',true).addClass('btn-disabled');
		}else{
			$('.st_form_btn').prop('disabled',false).removeClass('btn-disabled')
		}
		calc_store_shipping_fee();
	}

	function calc_store_shipping_fee()
	{
		$('.storeline').each(function(index, el) {
			$(this).find('.showlimit,.showprimepantry').hide();
			var free_shipping_amount = parseFloat($(this).attr('free_shipping_amount'));
			var store_id = $(this).attr('store_id');
			var shipping = parseFloat($(this).attr('shipping'));
			var amount = 0;
			var has_app = false;
			if( free_shipping_amount==0 || free_shipping_amount==99999){
				$(this).find('.showlimit').hide();
				return true;
			}
			$('.body[store_id='+store_id+']').each(function(index, el) {
				if($(this).find('.ids:checked')){
					var qua = parseInt($(this).attr('quantity'));
					var price = parseFloat($(this).attr('price2'));
					var has_app_tmp = $(this).attr('has_app');
					if (has_app_tmp==1) {
						has_app = true;
					};
					amount +=  price*qua; 
				}
			});
			if (has_app) {
				$(this).find('.showprimepantry').show();
				return true;
			};
			if (free_shipping_amount<amount || amount==0) {
				$(this).find('.showlimit').hide();
				return true;
			}
			var less = parseFloat(free_shipping_amount-amount).toFixed(2);
			$(this).find('.showlimit i').html("$"+less).parent().show();
			return true;
		});
	}
	function checkcount()
	{
		var itemcount = $(".ids").length;
		if (itemcount<=0) {
			window.location.reload();
		}
	}
});