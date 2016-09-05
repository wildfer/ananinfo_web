//package com.shantao.task;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.shantao.logic.AlipayLogic;
//import com.shantao.util.SpringUtil;
//
//public class FinanceTongjiTask implements QuartzTask {
//	private static Logger LOGGER = LoggerFactory.getLogger(FinanceTongjiTask.class.getName());
//
//	@Override
//	public void work(String[] args) {
//		LOGGER.info("开始执行批量新增财务流水任务");
//		AlipayLogic alipayLogic= (AlipayLogic)SpringUtil.getBean("AlipayLogic");
//		alipayLogic.alipayLogToFinance();
//		LOGGER.info("批量新增财务流水任务执行结束");
//
//	}
//
//}
