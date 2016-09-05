package com.AnAnInfoWebMagic.app;

import com.AnAnInfoWebMagic.task.QuartzTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lt on 2015/8/14.
 */
public class Factory {
    private static Logger LOGGER = LoggerFactory.getLogger(Factory.class.getName());
    public static QuartzTask getInstance(String className){
        try{
            return (QuartzTask)Class.forName("com.AnAnInfoWebMagic.task."+className).newInstance();
        } catch(Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}
