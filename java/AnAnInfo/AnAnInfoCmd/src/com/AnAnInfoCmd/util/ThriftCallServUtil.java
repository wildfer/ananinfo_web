package com.AnAnInfoCmd.util;

import com.AnAnInfoCmd.thrift.EcShopService;
import com.AnAnInfoCmd.thrift.ParamsIn;
import com.AnAnInfoCmd.thrift.ParamsOut;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Administrator on 2015/10/27.
 */
public class ThriftCallServUtil {
    public ParamsOut callServ(String strSerIP,int iPort,int iTimeOut,ParamsIn strJsonIn) {
        ParamsOut paramsOut = new ParamsOut();
        TTransport transport = null;
        try {
            transport = new TSocket(strSerIP, iPort, iTimeOut);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            EcShopService.Client client = new EcShopService.Client(protocol);

            transport.open();
            paramsOut = client.EcShopJson(strJsonIn);
            return paramsOut;

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
        return paramsOut;

    }
}
