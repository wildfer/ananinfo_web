package com.AnAnInfoCmd.clientpool;

import com.AnAnInfoCmd.thrift.EcShopService;
import com.AnAnInfoCmd.thrift.ParamsIn;
import com.AnAnInfoCmd.thrift.ParamsOut;
import com.AnAnInfoCmd.util.CustomException;
import com.AnAnInfoCmd.util.SpringUtil;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 服务客户端调用
 * Created by lt on 2015/10/27.
 */
public class UserServiceClient {
    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceClient.class.getName());
    //单点服务端
    public ParamsOut invokeSinger(ParamsIn strJsonIn,String strServiceIP,int iPort,int iTimeOut) throws CustomException,TException {
        TTransport transport = null;
        ParamsOut paramsOut = new ParamsOut();
        try {
            transport = new TSocket(strServiceIP, iPort,iTimeOut);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            EcShopService.Client client = new EcShopService.Client(protocol);
            transport.open();
            paramsOut = client.EcShopJson(strJsonIn);
        } catch (TTransportException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } catch (TException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
        return paramsOut;
    }
}