package com.AnAnInfoBus.util;

/**
 * Created by Administrator on 2015/4/22.
 */
public class CustomException extends  Exception{

    //公共错误
    public static final int PHONE_NULL =1100;          //手机号不能为空
    public static final int PHONE_WRONG = 1104; //手机号错误
    public static final int VERIFYCODE_WRONG = 1105; //验证码错误
    public static final int PHONE_REGISTERED = 1107; // 手机号码已注册
    public static final int SYS_ERROR = 1111; //系统错误，请重试
    public static final int EMOJI_UNSUPPORT = 1011; //不支持表情

    public static final int PHONE_VERIFYCODE_EXPIRED = 1125; //手机验证码已失效

    public static final int GET_VERIFYCODE_TOO_FAST = 1129; //获取验证码过快
    public static final int NICKNAME_EXIST = 1133; //昵称已存在
    public static final int RECEIVER_CANT_NULL = 1134; //收货人不能为空
    public static final int ADDRESS_CANT_NULL = 1135; //详细地址不能为空
    public static final int CITY1_CANT_NULL = 1136; //地址一级城市不能为空
    public static final int CITY2_CANT_NULL = 1137; //地址二级城市不能为空

    public static final int IDPHOTO_MUST_UPLOAD = 140; //身份证照片必须上传
    public static final int ID_NUMBER_WRONG = 1141; //身份证号有误！

    public static final int PARAM_MISS = 1147; //参数缺失
      public static final int SIGN_IN_FAIL = 1150; //登录出错


    public static final int INVALID_JSONOBJECT=1164; //无效的JSON对象
//
    public static final int INVALID_USER_ID = 1200; //无效的用户ID

    public static final int ORDER_STATUS_INCORRECT = 1517; //订单状态不正确,请返回订单列表并刷新
    public static final int INVALID_ORDER = 1518; //无效的订单

    public static final int DATA_ERROR = 1523; //数据出错
//    public static final int ADDRESS_NOT_EXISTS = 1524; //收货地址信息不存在
    public static final int USER_NOT_SIGN_IN = 1001; //用户未登录
    public static final int LACK_NECESSARY_PARAM = 11002; //缺少必要参数
    public static final int BUSI_ERROR = 1525; //业务错误
    public static final int ATTACH_ERROR = 2001; //添加附件详细失败
    public static final int CART_ADD_ERROR = 2002; //添加购物车失败
    public static final int CATE_ADD_ERROR= 1530;//ms_category新增错误
    public static final int MEDIA_ADD_ERROR= 2001;//添加媒体失败
    public static final int ADD_ADD_ERROR= 2002;//添加地址失败
    public static final int PAYLOG_ADD_ERROR= 2003;//添加余额流水日志失败
    public static final int COLLECT_ADD_ERROR= 2004;//添加收藏失败
    public static final int TOKEN_ADD_ERROR= 2005;//添加用户token失败
    public static final int USER_ADD_ERROR= 2006;//添加用户失败
    public static final int ORDERADDR_ADD_ERROR= 2007;//增加订单收货地址
    public static final int ORDERDETAIL_ADD_ERROR= 2008;//增加订单详情失败
    public static final int SNAP_ADD_ERROR= 2009;//增加快照失败
    public static final int CORDER_ADD_ERROR= 2010;//增加子订单失败
    public static final int ORDER_STATE_MOD_ERROR= 2011;//添加订单状态修改历史失败
    public static final int ORDER_ADD_ERROR= 2011;//增加订单失败
    public static final int ALIPAY_ADD_ERROR= 2012;//添加支付宝交易支付日志失败
    public static final int WEIXIN_ADD_ERROR= 2013;//添加微信支付交易支付日志失败
    public static final int PAY_ADD_ERROR= 2014;//添加交易支付日志失败
    public static final int CODE_CREATE_ERROR= 2015;//生成验证码失败
    public static final int TJ_CREATE_ERROR= 2016;//添加接口调用统计失败
    public static final int UTJ_CREATE_ERROR= 2017;//添加用户注册登录统计失败
    public static final int ADDRESS_LONG = 2018;//收货人字符太长
    public static final int ADDRESS_DETAIL_LONG = 2019;//详细地址字符太长
    public static final int ADDRESS_NOT_EXIST = 2020;//地址不存在
    public static final int ADDRESS_NOT_LOOK = 2021;//不能查看别人的收货人地址
    public static final int ADDRESS_NOT_DEL = 2022;//不能删除别人的收货人地址
    public static final int ADDRESS_NOT_DEFAULT = 2023;//用户没有默认地址
    public static final int IDT_NOT_DEL = 2024;//不能查看别人的身份证图片
    public static final int IDT_NOT_EXIST = 2025;//身份证图片不存在
    public static final int ADDRESS_NOT_UPDATE = 2026;//不能修改别人的收货人地址
    public static final int PIC_TYPE_ERROR = 2027;//不能修改别人的收货人地址
    public static final int NUM_NOT_ZERO = 2028;//数量不能小于等于0
    public static final int PRODUCT_NOT_EFFECT = 2029;//商品已下架
    public static final int PRODUCT_NOT_OK= 2030;//商品数据异常
    public static final int CART_NOT_EXIST= 2031;//购物车不存在
    public static final int CART_NOT_DEL= 2032;//不能删除别人的购物车
    public static final int ORDER_NOT_EXIST= 2033;//订单不存在
    public static final int ORDER_NOT_QRY= 2034;//不能查询别人的订单
    public static final int CRASH_NOT_EXIST= 2035;//流水不存在
    public static final int ORDER_STATE_FAIL= 2036;//订单状态不正确,请返回订单列表并刷新
    public static final int LINKHAITAO_ERROR= 2037;//[linkhaitao_tj_order]非法请求
    public static final int STAETTIME_ERROR= 2038;//开始日期格式有误
    public static final int ENDTIME_ERROR= 2039;//结束日期格式有误
    public static final int STAETTIME_ERROR1= 2040;//开始日期不能晚于结束日期
    public static final int TIME_ERROR= 2041;//查询时间段不能超过7天
    public static final int CART_EMPTY= 2042;//购物车是空的

    public static final int CART_NOT_COMPUTE= 2044;//不能结算别人的购物车
    public static final int PRODUCT_ERROR1= 2045;//价格有变化，请刷新价格后再结算
    public static final int PRODUCT_ERROR2= 2046;//不可售，请在购物车中删掉此商品再结算
    public static final int PRODUCT_ERROR3= 2047;//商品列表是空的
    public static final int ADDRESS_NOT_GOOD= 2048;//收货地址不正确
    public static final int CART_NOT_UPDATE= 2049;//不能更新别人的购物车
    public static final int PAY_ERROR= 2050;//未支付完应支付的金额
    public static final int TAX_ERROR= 2051;// 没有待补缴的税费
    public static final int PRODUCT_NOT_EXISTS = 1513;
    public static final int CART_NOT_UPDATE1 = 2052;//不能更新别的媒体的购物车
    public static final int PRODUCT_EMPTY = 2053;//购买商品数量超出限制
    public static final int PRODUCT_LIMIT = 2054;//库存无货
    public static final int PRODUCT_LIMIT1 = 2055;//购买商品数量超出库存量
    public static final int HELP_QUERY_ERROR = 2056; //业务错误
    public static final int SOCKET_ERROR=11010;  //连接错误
    public static final int SERVICE_CALL_ERROR=2001;  //服务调用错误
    public static final int CMDID_ERROR=2003;  //命令错误
    public static final int ALPLAY_CALLBACK_ORDER_ERROR= 3010;  //订单状态不正确
    public static final int PAY_NOW_TOTAL_ERROR = 3011;  //订单金额异常
    public static final int PRODUCT_NOT_FOUND= 6001;//未找到相关产品
    
    String message;
    int    code;

    public CustomException(int ErrorCode,String ErrorMessagr) {
        this.message = ErrorMessagr;
        this.code=ErrorCode;
    }
    public String getMessage(){
        return message;
    }

    public int getCode(){
        return code;
    }
}