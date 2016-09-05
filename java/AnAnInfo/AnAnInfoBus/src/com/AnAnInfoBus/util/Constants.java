package com.AnAnInfoBus.util;

import com.AnAnInfoBus.bean.*;
import com.AnAnInfoBus.business.dao.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class Constants {
	public static Properties CONFIG = new Properties();
	public static HashMap<String, Aai_system_cmdbean> CMD_MAP = new HashMap<String, Aai_system_cmdbean>();

	//日志类型
	public static final String LOG_TYPE_JAVAERROR = "java_error"; //java错误日志
	public static final String LOG_TYPE_LOGIN = "login"; //登录日志
	public static final String LOG_TYPE_REGISTER = "register"; //注册日志
	public static final String LOG_TYPE_LOGOUT = "logout"; //注册日志
	public static final String LOG_TYPE_FINISH_PAY_ORDER_TARIFF = "finish_pay_order_tariff"; //完成税费补缴

	//验证码类型
	public static int VERUFY_CODE_TYPE_LOGIN = 1; //登录时的验证码

	//收藏类型
	public static final String COLLECT_TYPE_PRODUCT = "PROD";
	public static final String COLLECT_TYPE_SPECIAL = "SPECIAL";

	//一些常用参数，采用此方法加载的参数，有修改的话必须重启服务
	public static String MINISHOP_URL;
	public static String UPYUN_URL;
	public static String API_URL;

	//购物车类型，暂时只有商品
	public static final String CART_TYPE_PRODUCT = "PRODUCT";

	//附件类型
	public static final String ATTACHMENT_TYPE_IDENTITY = "i"; //身份证图片

	//交易类型
	public static final String PAY_TYPE_ORDER = "order"; //订单
	public static final String PAY_TYPE_TARIFF = "tariff"; //税费补缴

	//支付状态
	public static final String PAY_STATUS_PAYING = "PAYING"; //支付中
	public static final String PAY_STATUS_PAIED = "PAIED"; //支付完成

	//支付来源（方式）
	public static final String PAY_SOURCE_ALIPAY = "alipay";
	public static final String PAY_SOURCE_WECHAT = "wechatpay";

	//订单状态
	public static final String ORDER_STATUS_ARRIVED = "ARRIVED"; //转运已入库，待发货
	public static final String ORDER_STATUS_BOUGHT = "BOUGHT"; //已采购
	public static final String ORDER_STATUS_FAIL = "FAIL"; //交易失败
	public static final String ORDER_STATUS_NEW = "NEW"; //新订单，待付款
	public static final String ORDER_STATUS_OK = "OK"; //交易成功
	public static final String ORDER_STATUS_PAIED = "PAIED"; //已付款,待处理
	public static final String ORDER_STATUS_SENT1 = "SENT1"; //商家已发货，送达仓库中
	public static final String ORDER_STATUS_SENT2 = "SENT2"; //已提交物流，待确认

	//用户余额流水日志类型
	public static final String BALANCE_LOG_TYPE_RECHARGE = "RECHARGE";
	public static final String BALANCE_LOG_TYPE_PAY_ORDER = "PAY_ORDER";
	public static final String BALANCE_LOG_TYPE_TAKE_OUT = "TAKE_OUT";
	public static final String BALANCE_LOG_TYPE_GIFT = "GIFT";
	public static final String BALANCE_LOG_TYPE_PAYMENT_FIX = "PAYMENT_FIX";
	public static final String BALANCE_LOG_TYPE_RETURN_ORDER = "RETURN_ORDER";
	public static final String BALANCE_LOG_TYPE_PAY_TARIFF = "PAY_TARIFF";

	public static HashMap<String, String> ORDER_NAME_MAP = new HashMap<String, String>();

	public static final int PRICE_SCALE = 0;
	public static final int TARIFF_SCALE = 2;

	static {
		try {
			InputStream in = Constants.class.getClassLoader().getResourceAsStream("config.properties");
			CONFIG.load(in);
			in.close();

			//加载常用参数值
			AaiSystemSettingDao settingDao = (AaiSystemSettingDao) SpringUtil.getBean("AaiSystemSettingDao");
			MINISHOP_URL = settingDao.getByKey("minishop_url").getSvalue();
			UPYUN_URL = settingDao.getByKey("upyun").getSvalue();
			API_URL = settingDao.getByKey("minishop_api_url").getSvalue();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
