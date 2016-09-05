package com.AnAnInfoBus.business.dao;

import com.AnAnInfoBus.bean.Aai_system_settingbean;
import com.AnAnInfoBus.util.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("AaiSystemSettingDao")
public class AaiSystemSettingDao extends Dao {
	private static Logger LOGGER = LoggerFactory.getLogger(AaiSystemSettingDao.class.getName());

	public AaiSystemSettingDao(){
		this.tablename = "aai_system_setting";
	}
	
	/**
	 * 根据key获取系统参数
	 * @param key
	 * @return
	 * @throws CustomException
	 * @throws Exception
	 */
	public Aai_system_settingbean getByKey(String key) throws CustomException,Exception{
		String sql = "select * from "+tablename +" where skey=?";
		return (Aai_system_settingbean) this.queryForBean(Aai_system_settingbean.class, sql, new Object[]{key});
	}
}
