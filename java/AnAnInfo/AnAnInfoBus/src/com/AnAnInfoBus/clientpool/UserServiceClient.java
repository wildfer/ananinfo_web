package com.AnAnInfoBus.clientpool;


import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.AnAnInfoBus.thrift.EcShopService;
import com.AnAnInfoBus.thrift.ParamsIn;
import com.AnAnInfoBus.thrift.ParamsOut;
import com.AnAnInfoBus.util.CustomException;
import com.AnAnInfoBus.util.SpringUtil;

/**
 * 服务客户端调用
 * Created by lt on 2015/10/27.
 */
public class UserServiceClient {
    private static Logger LOGGER = LoggerFactory.getLogger(UserServiceClient.class.getName());
    //单点调用命令服务
    public static ParamsOut invokeCmd(ParamsIn strJsonIn) throws CustomException {
        CmdServiceClientBean cmdServiceClientBean = (CmdServiceClientBean) SpringUtil.getBean("CmdServiceClientBean");
        TTransport transport = null;
        ParamsOut paramsOut = new ParamsOut();
        try {
            transport = new TSocket(cmdServiceClientBean.getServiceIP(), cmdServiceClientBean.getServicePort(),cmdServiceClientBean.getConTimeOut());
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            EcShopService.Client client = new EcShopService.Client(protocol);
            transport.open();
            paramsOut = client.EcShopJson(strJsonIn);
            if (paramsOut.getErrorCode() != 0) {
                LOGGER.debug(paramsOut.getErrorCode()+paramsOut.getMessage());
                throw new CustomException(paramsOut.getErrorCode(), paramsOut.getMessage());
            }
        } catch (TTransportException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            throw new CustomException(CustomException.SOCKET_ERROR, e.getMessage()+"连接错误");
        } catch (TException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            throw new CustomException(CustomException.SOCKET_ERROR, e.getMessage()+"连接错误");
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
        return paramsOut;
    }
    //单点调用命令服务
    public static ParamsOut invokeCmdNoErr(ParamsIn strJsonIn) throws CustomException {
        CmdServiceClientBean cmdServiceClientBean = (CmdServiceClientBean) SpringUtil.getBean("CmdServiceClientBean");
        TTransport transport = null;
        ParamsOut paramsOut = new ParamsOut();
        try {
            transport = new TSocket(cmdServiceClientBean.getServiceIP(), cmdServiceClientBean.getServicePort(),cmdServiceClientBean.getConTimeOut());
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            EcShopService.Client client = new EcShopService.Client(protocol);
            transport.open();
            paramsOut = client.EcShopJson(strJsonIn);
        } catch (TTransportException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            throw new CustomException(CustomException.SOCKET_ERROR, e.getMessage()+"连接错误");
        } catch (TException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            throw new CustomException(CustomException.SOCKET_ERROR, e.getMessage()+"连接错误");
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
        return paramsOut;
    }


}