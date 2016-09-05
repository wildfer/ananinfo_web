package com.AnAnInfoWebMagic.util;

import com.AnAnInfoWebMagic.thrift.ParamsIn;
import com.AnAnInfoWebMagic.thrift.ParamsOut;
import com.AnAnInfoWebMagic.thrift.ShantaoService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2015/7/13 0013.
 */
public class RequestShantaoService {
    private static Logger LOGGER = LoggerFactory.getLogger(RequestShantaoService.class.getName());
    // 服务端端口号
    private  String SERVER_IP ="127.0.0.1";
    private  int SERVER_PORT 	   =9090;

    public int getSERVER_PORT() {
        return SERVER_PORT;
    }

    public void setSERVER_PORT(int SERVER_PORT) {
        this.SERVER_PORT = SERVER_PORT;
    }

    public String getSERVER_IP() {
        return SERVER_IP;
    }

    public void setSERVER_IP(String SERVER_IP) {
        this.SERVER_IP = SERVER_IP;
    }

    public  ParamsOut doRequest(ParamsIn in){
        TSocket clientTransport = null;
        try{
            clientTransport = new TSocket(SERVER_IP,SERVER_PORT);
            clientTransport.setTimeout(30000);
            ShantaoService.Client client = new ShantaoService.Client(new TBinaryProtocol(clientTransport));

            //打开连接
            if(!clientTransport.isOpen()) {
                clientTransport.open();
            }
            ParamsOut out = client.ShantaoCallJson(in);

            return out;
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关闭连接
            if(clientTransport!=null){
                clientTransport.close();
            }
        }
        return null;
    }
}
