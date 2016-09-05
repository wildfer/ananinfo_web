package com.AnAnInfoBus.business.dao;

import com.AnAnInfoBus.bean.Aai_system_cmdbean;
import com.AnAnInfoBus.util.CustomException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository("AaiSystemCmdDao")
public class AaiSystemCmdDao extends Dao {
	private static Logger LOGGER = LoggerFactory.getLogger(AaiSystemCmdDao.class.getName());

	public AaiSystemCmdDao(){
		this.tablename = "aai_system_cmd";
	}

	/**
	 * 查询萌店所有的服务命令字
	 * @return
	 * @throws CustomException
	 * @throws Exception
	 */
	public List<Aai_system_cmdbean> getAllMedianCmd() throws CustomException,Exception{
		List<Aai_system_cmdbean> list = new ArrayList<Aai_system_cmdbean>();
		String sql = "select * from "+ tablename + " where service_name='EcShop_Mengdian'";
		JSONArray array = this.queryForJSONArray(sql, new Object[]{});
		if(array!=null && array.size()>0){
			for(int i=0;i<array.size();i++){
				JSONObject json = array.getJSONObject(i);
				Aai_system_cmdbean bean = new Aai_system_cmdbean();
				BeanUtils.populate(bean, json);
				list.add(bean);
			}
		}
		return list;
	}

	/**
	 * 根据命令字查询详细信息
	 * @param cmd
	 * @return
	 * @throws CustomException
	 * @throws Exception
	 */
	public Aai_system_cmdbean getByCmd(String cmd) throws CustomException,Exception{
		String sql = "select * from "+ tablename + " where cmd_id=?";
		JSONObject json = this.queryForJSONObject(sql, new Object[]{cmd});
		if(json == null){
			return null;
		}
		Aai_system_cmdbean bean = new Aai_system_cmdbean();
		BeanUtils.populate(bean, json);
		return bean;
	}
}
