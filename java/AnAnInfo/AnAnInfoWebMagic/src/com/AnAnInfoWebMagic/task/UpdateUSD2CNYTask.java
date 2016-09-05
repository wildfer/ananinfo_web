//package com.shantao.task;
//
//import java.math.BigDecimal;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.shantao.util.HttpRequest;
//import com.shantao.dao.SettingDao;
//import com.shantao.util.SpringUtil;
//
//public class UpdateUSD2CNYTask implements QuartzTask {
//	private static Logger LOGGER = LoggerFactory.getLogger(UpdateUSD2CNYTask.class.getName());
//	private SettingDao settingDao;
//	@Override
//	public void work(String[] args) {
//		settingDao = (SettingDao)SpringUtil.getBean("SettingDao");
//		//从参数表取抓取url,上浮百分比
//		String requestUrl = settingDao.getParamValue("USD2CNY_URL");
//		Float upRateFloat = Float.valueOf(settingDao.getParamValue("USD2CNY_UPRATE"));
//		Integer timeOutInteger = Integer.valueOf(settingDao.getParamValue("HTTP_WAIT_TIMEOUT"));
//		//抓取
//        HttpRequest httpRequest = new HttpRequest();
//        LOGGER.info("开始从以下地址抓取美元汇率："+requestUrl);
//       String strResult = httpRequest.sendGet(requestUrl, timeOutInteger,"UTF-8");
//       Integer oneDollarIndex = strResult.indexOf("1美元=");
//       String cnyString = strResult.substring(oneDollarIndex+3+1, oneDollarIndex+3+7);
//       try{
//    	   BigDecimal dd = new BigDecimal(cnyString);
//    	   dd = dd.multiply(new BigDecimal(1+upRateFloat));
//
//    	   String usd2cny = dd.setScale(2,BigDecimal.ROUND_HALF_UP).toPlainString();
//    	   settingDao.updateParamValue("USD2CNY",usd2cny);
//    	   LOGGER.info("抓取并更新美元汇率成功");
//       }catch (NumberFormatException e) {
//    	   LOGGER.error("格式化美元汇率失败，抓取到的字符："+cnyString);
//    	   e.printStackTrace();
//       }
//
//	}
//
//}
