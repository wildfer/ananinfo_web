//package com.shantao.task;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.shantao.logic.PackageLogic;
//import com.shantao.util.SpringUtil;
//
//public class AutoUpdateTrackInfo implements QuartzTask {
//	private static Logger LOGGER = LoggerFactory.getLogger(AutoUpdateTrackInfo.class.getName());
//	@Override
//	public void work(String[] args) {
//		LOGGER.info("开始执行自动更新运单物流信息任务");
//		PackageLogic packageLogic= (PackageLogic)SpringUtil.getBean("PackageLogic");
//		packageLogic.updateTrackInfo();
//		LOGGER.info("自动更新运单物流信息任务执行结束");
//	}
//
//}
