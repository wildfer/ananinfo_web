package com.AnAnInfoBus.business.logic;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.AnAnInfoBus.util.CustomException;

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
                    throw new CustomException(CustomException.PARAM_MISS,key+"参数为空");
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYS_ERROR,"系统错误，请重试");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYS_ERROR,"系统错误，请重试");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYS_ERROR,"系统错误，请重试");
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
                        throw new CustomException(CustomException.PARAM_MISS,"缺少参数"+key);
                    }
                }

            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYS_ERROR,"系统错误，请重试");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYS_ERROR,"系统错误，请重试");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.SYS_ERROR,"系统错误，请重试");
        }
    }

    public void isNull(String value,int errorcode,String msg) throws CustomException {
        if(value==null || value.equals("")){
            throw new CustomException(errorcode,msg);
        }
    }

    //是否为空
    public boolean isEmpty(String str){
        if(str==null || str.equalsIgnoreCase("") ){
            return true;
        }
        return false;
    }
    //是否为空
    public boolean isEmptyStr(JSONObject jsonObject,String strKey){
        if (jsonObject.containsKey(strKey)&&!jsonObject.getString(strKey).isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    //根据json对象来判断某些字段是否为空
    public void isNull(JSONObject json,String[] fields,
    		Integer[] errorCodes,String[] msgs)throws CustomException{
    	if(json==null){
    		throw new CustomException(CustomException.SYS_ERROR,"系统错误，请重试");
    	}
        for(int i=0;i<fields.length;i++){
            String key = fields[i];
            Object o = json.get(key);
            if(o==null || "".equals(o.toString())){
                LOGGER.error(key+"参数为空");
                if(errorCodes==null){
                	throw new CustomException(CustomException.PARAM_MISS,key+"不能为空");
                }else{
                	if(errorCodes[i]==null){
                		throw new CustomException(CustomException.PARAM_MISS,key+"不能为空");
                	}else{
                		throw new CustomException(errorCodes[i],msgs[i]);
                	}
                }
            }
        }

    }

    public Integer gettime(){
        String time=(new java.util.Date()).getTime()+"";
        return Integer.parseInt(time.substring(0, 10));
    }

    public int count(JSONArray array){
        return array==null?0:array.size();
    }
}
