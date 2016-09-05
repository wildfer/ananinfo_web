package com.AnAnInfoCmd.business.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import com.AnAnInfoCmd.bean.Aai_system_cmdbean;
import com.AnAnInfoCmd.util.CustomException;



@Repository("Aai_system_cmdDao")
public class Aai_system_cmdDao extends Dao {
	private static Logger LOGGER = LoggerFactory.getLogger(Aai_system_cmdDao.class.getName());
	
	public Aai_system_cmdDao(){
		this.tablename = "ms_system_cmd";
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
	
	public Aai_system_cmdbean getCmdInfoById(String strCmdId) throws CustomException{
        try {
            Map<String, Object> hashMap=jdbcT.queryForMap("SELECT t.* FROM " + tablename +
            		" t  WHERE t.status = 1 and t.cmd_id = ? ", new Object[]{strCmdId});
            Aai_system_cmdbean cmdbean = new Aai_system_cmdbean();
            BeanUtils.populate(cmdbean, hashMap);
            return cmdbean;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.DATABASE_RUN_ERROR, e.getMessage()+"数据错误");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.DATABASE_RUN_ERROR, e.getMessage()+"数据错误");
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            throw new CustomException(CustomException.DATABASE_RUN_ERROR, e.getMessage()+" 未找到命令字ecshop_cmd:"+strCmdId);
        }
    }
}
