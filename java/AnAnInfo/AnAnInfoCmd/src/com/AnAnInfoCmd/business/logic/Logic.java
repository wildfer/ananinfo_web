package com.AnAnInfoCmd.business.logic;

import com.AnAnInfoCmd.util.CustomException;

import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Administrator on 2015/5/11.
 */
public class Logic {
    private static Logger LOGGER = LoggerFactory.getLogger(Logic.class.getName());
    //判断bean中是否有字段为空，为空则抛出异常
    public void isNull(Object obj) throws CustomException {
        Map<String, String> propertys= null;
        try {
            propertys = BeanUtils.describe(obj);
            Set property=propertys.keySet();
            Iterator<String> it = property.iterator();
            while (it.hasNext()) {
                String key=it.next();
                String value=propertys.get(key);
                if(value==null ){
                    throw new CustomException(CustomException.SYSTEM_ERROR,key+"参数为空");
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYSTEM_ERROR,"参数校验错误");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYSTEM_ERROR,"参数校验错误");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYSTEM_ERROR,"参数校验错误");
        }
    }

    //判断bean中是否有字段为空，为空则抛出异常
    public void isNull(Object obj,String[] fileds) throws CustomException {
        Map<String, String> propertys= null;
        List list=Arrays.asList(fileds);
        try {
            propertys = BeanUtils.describe(obj);
            Set property=propertys.keySet();
            Iterator<String> it = property.iterator();
            while (it.hasNext()) {
                String key=it.next();
                if(list.contains(key)){
                    String value=propertys.get(key);
                    if(value==null ){
                        LOGGER.error(key+"参数为空");
                        throw new CustomException(CustomException.SYSTEM_ERROR,key+"参数为空");
                    }
                }

            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYSTEM_ERROR,"参数校验错误");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYSTEM_ERROR,"参数校验错误");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYSTEM_ERROR,"参数校验错误");
        }
    }

    //根据json对象来判断某些字段是否为空
    public void isNull(JSONObject json,String[] fields)throws CustomException{
    	if(json==null){
    		throw new CustomException(CustomException.SYSTEM_ERROR,"格式化后json为空，请检查！");
    	}
        for(int i=0;i<fields.length;i++){
            String key = fields[i];
            Object o = json.get(key);
            if(o==null || "".equals(o.toString())){
                LOGGER.error(key+"参数为空");
                throw new CustomException(CustomException.SYSTEM_ERROR,key+"参数为空");
            }
        }

    }
}
