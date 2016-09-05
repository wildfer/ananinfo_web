package com.AnAnInfoBus.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2015/4/27.
 */
public class SpringUtil {
    public static ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static Object getBean(String beanName){
        return ctx.getBean(beanName);
    }
}
