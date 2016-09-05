package com.AnAnInfoWebMagic.task;

import com.AnAnInfoWebMagic.thrift.ParamsIn;
import com.AnAnInfoWebMagic.thrift.ParamsOut;
import com.AnAnInfoWebMagic.util.RequestShantaoService;
import com.AnAnInfoWebMagic.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by wxb on 2015/7/13 0013.
 */

public class QuartzJobTest implements QuartzTask{
    private Logger LOGGER = LoggerFactory.getLogger(getClass().getName());
    
    public void work(String[] args){
        System.out.println("开始执行任务：" + QuartzJobTest.class.getName());
        LOGGER.debug("开始执行任务：" + QuartzJobTest.class.getName());
        
        //组装请求消息
        ParamsIn in = new ParamsIn();
        in.setStrCmdID("addActive_log");
        in.setJsonIn("{'active_id':'1111111','active_detail_id':'444555'}");
        //执行请求，并获取返回结果
        RequestShantaoService requestShantaoService = (RequestShantaoService) SpringUtil.getBean("requestShantaoService");
        ParamsOut out = requestShantaoService.doRequest(in);
        System.out.println(out.getErrorCode());

        System.out.println(out.getMessage());
        System.out.println(out.getJsonOut());

        System.out.println("任务:"+QuartzJobTest.class.getName()+",执行完毕\n");
        LOGGER.debug("任务:"+QuartzJobTest.class.getName()+",执行完毕");
    }

}
