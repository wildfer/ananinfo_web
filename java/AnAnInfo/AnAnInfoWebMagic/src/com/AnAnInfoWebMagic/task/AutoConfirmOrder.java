//package com.shantao.task;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.shantao.dao.SettingDao;
//import com.shantao.logic.OrderLogic;
//import com.shantao.util.SpringUtil;
//
///**
// * 自动确认订单，发货时间大于60天，订单状态为SENT2
// * @author Administrator
// *
// */
//public class AutoConfirmOrder implements QuartzTask {
//	private static Logger LOGGER = LoggerFactory.getLogger(AutoConfirmOrder.class.getName());
//
//	@Override
//	public void work(String[] args) {
//		LOGGER.info("开始执行订单自动确认任务");
//		OrderLogic orderLogic= (OrderLogic)SpringUtil.getBean("OrderLogic");
//		orderLogic.autoConfirmOrder();
//		LOGGER.info("订单自动确认任务执行结束");
//	}
//
//}
