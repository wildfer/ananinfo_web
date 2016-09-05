package com.AnAnInfoCmd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2015/5/27.
 */
public class ServiceProxy implements InvocationHandler {

    private Object target;
    private static Logger LOGGER = LoggerFactory.getLogger(ServiceProxy.class.getName());

    public Object bind(Object target) {
        this.target = target;
        //取得代理对象
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);   //要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)
    }

    @Override

    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        Object result=null;
        LOGGER.debug(method.getName()+"开始调用");
        String argstr="";
        for(int i=0;i<args.length;i++){
            argstr +=args[i]+" ";
        }
        LOGGER.debug(method.getName()+"参数:"+argstr);
        result=method.invoke(target, args);
        LOGGER.debug(method.getName()+"结束调用:"+result);
        return result;
    }
}
