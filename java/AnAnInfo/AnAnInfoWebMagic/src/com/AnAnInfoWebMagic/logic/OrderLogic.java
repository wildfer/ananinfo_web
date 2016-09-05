//package com.shantao.logic;
//
//import java.util.Date;
//
//import javax.annotation.Resource;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.shantao.dao.OrderDao;
//import com.shantao.dao.SettingDao;
//import com.shantao.thrift.ParamsIn;
//import com.shantao.thrift.ParamsOut;
//import com.shantao.util.RequestShantaoService;
//import com.shantao.util.ShantaoTaskUtil;
//import com.shantao.util.SpringUtil;
//
//@Service("OrderLogic")
//public class OrderLogic {
//	private static Logger LOGGER = LoggerFactory.getLogger(OrderLogic.class
//			.getName());
//	@Resource(name = "OrderDao")
//	private OrderDao orderDao;
//	@Resource(name = "SettingDao")
//	private SettingDao settingDao;
//
//	/**
//	 * 自动确认订单，发货时间大于60天，订单状态为SENT2
//	 *
//	 */
//	@Transactional(propagation = Propagation.REQUIRED)
//	public void autoConfirmOrder() {
//		String time = String.valueOf(new Date().getTime());
//		Integer now = Integer.parseInt(time.substring(0, 10));
//		// 查询收货时间小于今天，订单状态为SENT2的订单id，有可能有确认收货时间的，但是状态还为SENT2的
//		JSONArray array = orderDao.findAllSent2Order();
//		if (array != null && !array.isEmpty()) {
//			for (int i = 0; i < array.size(); i++) {
//				JSONObject json = array.getJSONObject(i);
//				String orderid = json.getString("id");
//				// 如果已有收货时间但是状态仍为SENT2，或者状态为SENT2，且已过了60天，则自动确认
//				if (json.getInt("time_finish") != 0
//						|| json.getInt("time_sent2") < (now - 60 * 86400)) {
//					orderDao.updateOrderStatus(orderid, "SENT2", "OK",
//							"系统自动确认订单", 0);
//				}
//			}
//		}
//	}
//
//	/**
//	 * @throws Exception
//	 * 关闭超时的订单，超时时间为3小时
//	 * @throws
//	 */
//	public void autoCloseOrder() throws Exception {
//		// 先查出超时的订单列表
//		JSONArray array = orderDao.findAllTimeOutOrder();
//		//查询订单超时关闭的时间
//		Integer timeout =  Integer.valueOf(settingDao.getParamValue("ORDER_TIMEOUT"));
//		String time = String.valueOf(new Date().getTime());
//		Integer now = Integer.parseInt(time.substring(0, 10));
//		if (array != null && !array.isEmpty()) {
//			for(int i=0;i<array.size();i++){
//				JSONObject json = array.getJSONObject(i);
//				Integer time_create = json.getInt("time_create");
//				String order_id = json.getString("id");
//				Integer uid = json.getInt("uid");
//				String username = json.getString("username");
//				if(time_create<(now-timeout)){
//					//关闭订单，并且发送站内短信给用户
//					closeOrder(order_id,uid);
//				}
//				String phone = json.getString("phone");
//				Integer valid_phone = json.getInt("valid_phone");
//				Integer status_notice_payment = json.getInt("status_notice_payment");
//				if(!"".equals(phone) && valid_phone==1 && status_notice_payment==0){
//					//发送短信并更新提醒状态
//					smsNotifyOrderTimeout(order_id,username,phone);
//				}
//			}
//		}
//
//	}
//
//	/**
//	 * 修改订单状态为FAIL，并且发送站内短信给用户
//	 * @param order_id
//	 * @param username
//	 * @param phone
//	 * @throws Exception
//	 */
//	@Transactional(propagation = Propagation.REQUIRED)
//	public void closeOrder(String order_id,Integer uid){
//		String time = String.valueOf(new Date().getTime());
//		Integer now = Integer.parseInt(time.substring(0, 10));
//		//修改订单状态
//		orderDao.updateOrderStatus(order_id, "NEW", "FAIL", "超时关闭订单", 0);
//		//发站内短消息
//		RequestShantaoService shantaoService = (RequestShantaoService)SpringUtil.getBean("requestShantaoService");
//		ParamsIn paramsIn = new ParamsIn();
//		paramsIn.setStrCmdID("addNotification");
//		String content = "您的订单"+order_id+"因超时被关闭!对此造成的影响,我们深感抱歉.";
//		paramsIn.setJsonIn("{\"fromid\":0,\"toid\":"+uid+",\"content\":\""+content+"\",\"createtime\":"+now+"}");
//		ParamsOut out = shantaoService.doRequest(paramsIn);
//		if(out.getErrorCode()!=0){
//			LOGGER.error("调用闪淘服务addNotification，出现错误，错误信息："+out.getMessage());
//		}
//	}
//
//	/**
//	 * 发送短信并更新提醒状态，这是一个事务
//	 */
//	@Transactional(propagation = Propagation.REQUIRED)
//	public void smsNotifyOrderTimeout(String order_id,String username,String phone){
//		//更新订单短信提醒状态
//		orderDao.updateOrderStatus_notice_payment(1, order_id);
//		//发送短信
//		String content = "亲爱的"+username+"，您在3小时前创建的订单["+order_id+"]不知道是什么原因没有完成付款呢？如果需要帮助，请联系客服哦!";
//		try {
//			ShantaoTaskUtil.sendSms(phone, content, new Date());
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error("发送订单短信超时提醒失败，电话号码："+phone+"，用户："+username+",订单id:"+order_id);
//		}
//	}
//
//}
