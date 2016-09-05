package com.AnAnInfoCmd.service;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import com.AnAnInfoCmd.thrift.*;
import com.AnAnInfoCmd.business.logic.AnAnInfoCmdLogic;
import com.AnAnInfoCmd.util.CustomException;
import com.AnAnInfoCmd.util.SpringUtil;

/**
 * Created by lt on 2015/4/9. 服务端接口分发类
 */
public class ServiceThriftInterface implements EcShopService.Iface{

    private static Logger LOGGER = LoggerFactory.getLogger(ServiceThriftInterface.class.getName());

    public ServiceThriftInterface(){
        ConvertUtils.deregister();
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        ConvertUtils.register(new BigIntegerConverter(null), BigInteger.class);
        ConvertUtils.register(new BooleanConverter(null), Boolean.class);
        ConvertUtils.register(new ByteConverter(null), Byte.class);
        ConvertUtils.register(new CharacterConverter(null), Character.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new ShortConverter(null), Short.class);
        ConvertUtils.register(new DateLocaleConverter(), Date.class);

    }
    private void  structToBean(Object struct,Object bean) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map propertys= BeanUtils.describe(struct);
        BeanUtils.populate(bean, propertys);
    }

    /*JSON通用调用接口*/
//    @Override
//    public ParamsOut ShantaoCallJson(ParamsIn strJsonIn) throws TException {
//        Jsoninterface jsoninterface = new Jsoninterface();
//        ParamsOut r=new ParamsOut(0,"","true");
//        try {
//            r.setJsonOut(jsoninterface.Process(strJsonIn.getStrCmdID(), strJsonIn.getJsonIn()));
//        }catch (STException e) {
//            e.printStackTrace();
//            r=new ParamsOut(e.getCode(),e.getMessage(),"false");
//            LOGGER.error(e.getMessage()+e.getCode() );
//        }catch (Exception e) {
//            e.printStackTrace();
//            r=new ParamsOut(STException.SYSTEM_ERROR,e.toString(),"false");
//            LOGGER.error(e.getMessage());
//        }
//        return r;
//
//    }

    @Override
    public ParamsOut EcShopJson(ParamsIn strJsonIn) throws TException {
        ParamsOut r=new ParamsOut(0,"","true");
        try {
            AnAnInfoCmdLogic ecShopCmdLogic = (AnAnInfoCmdLogic) SpringUtil.getBean("AnAnInfoCmdLogic");
            //EcShopCmdLogic ecShopCmdLogic = new EcShopCmdLogic();
            r = ecShopCmdLogic.cmdProcess(strJsonIn.getStrCmdID(), strJsonIn);
        }catch (CustomException e) {
            e.printStackTrace();
            r=new ParamsOut(e.getCode(),e.getMessage(),"false");
            LOGGER.error(e.getMessage()+e.getCode() );
        }catch (Exception e) {
            e.printStackTrace();
            r=new ParamsOut(CustomException.SYSTEM_ERROR,"系统繁忙，请稍后再试","false");
            LOGGER.error(e.getMessage());
        }
        return r;
    }
    /*测试接口*/
    @Override
    public ParamsOut EcShoptest(ParamsIn strJsonIn) throws TException {
        ParamsOut paramsOut = new ParamsOut();
        paramsOut.setErrorCode(0);
        paramsOut.setMessage("操作成功");
        paramsOut.setJsonOut(strJsonIn.getStrCmdID());
        return paramsOut;
    }
}

