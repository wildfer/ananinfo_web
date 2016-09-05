package com.AnAnInfoWebMagic.util;

import com.AnAnInfoWebMagic.app.AnAnInfoWebMagicTaskMain;

/**
 * Created by wxb on 2015/4/27.
 */
public class SpringUtil {

    public static Object getBean(String beanName){
        return AnAnInfoWebMagicTaskMain.SPRING_CONTEXT.getBean(beanName);
    }
}
