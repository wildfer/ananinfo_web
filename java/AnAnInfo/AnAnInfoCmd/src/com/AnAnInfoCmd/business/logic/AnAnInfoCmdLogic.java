package com.AnAnInfoCmd.business.logic;

import com.AnAnInfoCmd.thrift.ParamsIn;
import com.AnAnInfoCmd.thrift.ParamsOut;
import com.AnAnInfoCmd.bean.Aai_system_cmdbean;
import com.AnAnInfoCmd.bean.Aai_system_servicebean;
import com.AnAnInfoCmd.business.dao.Aai_system_serviceDao;
import com.AnAnInfoCmd.business.dao.Aai_system_cmdDao;
import com.AnAnInfoCmd.clientpool.UserServiceClient;
import com.AnAnInfoCmd.util.CustomException;
import com.AnAnInfoCmd.util.SpringUtil;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by lt on 2015/10/27 命令字处理
 */
@Service("AnAnInfoCmdLogic")
public class AnAnInfoCmdLogic {
    private static Logger LOGGER = LoggerFactory.getLogger(AnAnInfoCmdLogic.class.getName());
    @Resource(name="Aai_system_cmdDao")
    private Aai_system_cmdDao ms_cmdDao;
    @Resource(name="Aai_system_serviceDao")
    private Aai_system_serviceDao ms_serviceDao;
    //命令字处理
    //@Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public ParamsOut cmdProcess(String strCmd,ParamsIn strJsonIn) throws CustomException,TException {

        if (strCmd == null || strCmd.equals("")){
            throw new CustomException(CustomException.PARAM_ERROR,"参数错误");
        }
        //Ecshop_serviceDao ecshop_serviceDao = (Ecshop_serviceDao) SpringUtil.getBean("Ecshop_serviceDao");
        //Ecshop_cmdDao ecshop_cmdDao = (Ecshop_cmdDao) SpringUtil.getBean("Ecshop_cmdDao");
        Aai_system_cmdbean cmdbean = ms_cmdDao.getCmdInfoById(strCmd);
        Aai_system_servicebean servicebean = ms_serviceDao.getInfoBySerName(cmdbean.getService_name());
        //调用远端服务
        LOGGER.debug("call service:"+strJsonIn.getStrCmdID()+":"+servicebean.getService_name()+":"+servicebean.getService_ip());
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:connectionProviderQry/applicationContext-client.xml");
//        UserServiceClient userServiceClient = (UserServiceClient) context.getBean(UserServiceClient.class);
        UserServiceClient userServiceClient = new UserServiceClient();
        //ParamsOut paramsOut = userServiceClient.invoke(strJsonIn,ecshop_servicebean.getService_name());
        ParamsOut paramsOut = userServiceClient.invokeSinger(strJsonIn,servicebean.getService_ip(),
        		servicebean.getService_port(),servicebean.getTime_out());

        return paramsOut;
    }
}
