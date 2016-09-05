package com.AnAnInfoWebMagic.app;

import com.AnAnInfoWebMagic.task.QuartzTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lt on 2015/7/13 0013.
 */
public class AnAnInfoWebMagicTaskMain {
    private static Logger LOGGER = LoggerFactory.getLogger(AnAnInfoWebMagicTaskMain.class.getName());
    public static ApplicationContext SPRING_CONTEXT = null;
    public static void main(String[] args){
    	SPRING_CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");
        try{
            if (args.length<1){
                System.out.println("参数错误，必须指定运行类");
                LOGGER.error("参数错误：必须指定运行类");
                System.exit(-1);
            }
            QuartzTask quartzTask = Factory.getInstance(args[0]);
            //参数1被强制占用，具体业务参数从第二个参数开始
            quartzTask.work(args);//do something
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            LOGGER.error(ex.getMessage());
        }
    }

}
