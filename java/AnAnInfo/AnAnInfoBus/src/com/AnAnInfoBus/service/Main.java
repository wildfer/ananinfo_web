package com.AnAnInfoBus.service;

import com.AnAnInfoBus.thrift.EcShopService;
import com.AnAnInfoBus.util.Constants;
import com.AnAnInfoBus.util.SpringUtil;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    private static  Logger LOGGER = LoggerFactory.getLogger(Main.class.getName());
    // 服务端端口号
    private static int SERVER_PORT 	   =10002;

    // 加载驱动，一次
    static {
        try {
            // 属性文件读取信息
            SERVER_PORT = Integer.valueOf(Constants.CONFIG.getProperty("thrift.port"));
            System.out.println("启动端口号："+SERVER_PORT);
            LOGGER.debug("启动端口号："+SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void startServer() {
        try {
            System.out.println("AnAnInfoBus TThreadPoolServer start ....");
            LOGGER.debug("AnAnInfoBus TThreadPoolServer start ....");
            ServiceProxy proxy = new ServiceProxy();
            EcShopService.Iface serviceProxy = (EcShopService.Iface) proxy.bind(new ServiceThriftInterface());
            TProcessor tprocessor = new EcShopService.Processor<EcShopService.Iface>(serviceProxy);


            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            TThreadPoolServer.Args ttpsArgs = new TThreadPoolServer.Args(serverTransport);
            ttpsArgs.processor(tprocessor);
            ttpsArgs.maxWorkerThreads(Integer.valueOf(Constants.CONFIG.getProperty("thrift.arg.maxWorkerThreads")));
            ttpsArgs.minWorkerThreads(Integer.valueOf(Constants.CONFIG.getProperty("thrift.arg.minWorkerThreads")));
            ttpsArgs.protocolFactory(new TBinaryProtocol.Factory());

            // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
            TServer server = new TThreadPoolServer(ttpsArgs);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Main server = new Main();
        System.out.println("首次启动时载入spring数据");
        SpringUtil.ctx =  new ClassPathXmlApplicationContext("applicationContext.xml");
        server.startServer();
    }
}
