//package com.shantao.task;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.shantao.logic.OrderLogic;
//import com.shantao.util.SpringUtil;
//
///**
// * 自动关闭超时订单和提醒用户
// * @author Administrator
// *
// */
//public class AutoCloseOrderAndSmsNotify implements QuartzTask {
//	private static Logger LOGGER = LoggerFactory.getLogger(AutoCloseOrderAndSmsNotify.class.getName());
//
//	@Override
//	public void work(String[] args) {
//		LOGGER.info("开始执行自动关闭订单任务");
//		OrderLogic orderLogic= (OrderLogic)SpringUtil.getBean("OrderLogic");
//		try{
//			orderLogic.autoCloseOrder();
//		}catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error("执行自动关闭订单任务出错\n"+e.getMessage());
//		}
//		LOGGER.info("自动关闭订单任务执行结束");
//
//	}
//
//}
