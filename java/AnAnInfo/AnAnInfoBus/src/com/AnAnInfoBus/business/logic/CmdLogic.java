package com.AnAnInfoBus.business.logic;

import com.AnAnInfoBus.bean.Aai_system_cmdbean;
import com.AnAnInfoBus.business.dao.AaiSystemCmdDao;
import com.AnAnInfoBus.util.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("CmdLogic")
public class CmdLogic extends Logic {
	private static Logger LOGGER = LoggerFactory.getLogger(CmdLogic.class.getName());
	
	@Resource(name="AaiSystemCmdDao")
	private AaiSystemCmdDao aaiSystemCmdDao;

	public Aai_system_cmdbean getCmdInfoByCmd(String cmd) throws CustomException, Exception{
		return aaiSystemCmdDao.getByCmd(cmd);
	}
}
