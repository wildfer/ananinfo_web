//package com.shantao.task;
//
//import com.shantao.thrift.ParamsIn;
//import com.shantao.thrift.ParamsOut;
//import com.shantao.util.RequestShantaoService;
//import com.shantao.util.SpringUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**订单工作分配给客服人员
// * Created by lt on 2015/8/14
// */
//
//public class QuartzJobTaskDefault implements QuartzTask{
//    private static Logger LOGGER = LoggerFactory.getLogger(QuartzJobTaskDefault.class.getName());
//
//    public void work(String[] args){
//        System.out.println("开始执行任务：" + QuartzJobTaskDefault.class.getName());
//        LOGGER.debug("开始执行任务：" + QuartzJobTaskDefault.class.getName());
//
//
//        //组装请求消息
//        ParamsIn in = new ParamsIn();
//        in.setStrCmdID("addActive_log");
//        in.setJsonIn("{'active_id':'1111111','active_detail_id':'444555'}");
//        //执行请求，并获取返回结果
//        RequestShantaoService requestShantaoService = (RequestShantaoService) SpringUtil.getBean("requestShantaoService");
//        ParamsOut out = requestShantaoService.doRequest(in);
//        System.out.println(out.getErrorCode());
//
//        System.out.println(out.getMessage());
//        System.out.println(out.getJsonOut());
//
//
//
//        System.out.println("任务:"+QuartzJobTaskDefault.class.getName()+",执行完毕\n");
//        LOGGER.debug("任务:"+QuartzJobTaskDefault.class.getName()+",执行完毕");
//    }
//
//}
