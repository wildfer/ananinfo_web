package com.AnAnInfoBus.business.dao;

import com.AnAnInfoBus.bean.*;
import com.AnAnInfoBus.util.CustomException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("AaiPostDao")
public class AaiPostDao extends Dao {
	private static Logger LOGGER = LoggerFactory.getLogger(AaiPostDao.class.getName());

	public AaiPostDao(){
		this.tablename = "aai_post";
	}

	/**
	 * 帖子列表
	 * @param page 页码
	 * @param count 每页显示数量
	 * @return
	 * @throws EcException
	 */
	public ArrayList<Aai_postbean> getPostList(Integer page,Integer count) throws CustomException,Exception {
		ArrayList<Aai_postbean> list= new ArrayList<Aai_postbean>();
		String sql = "select * from " + tablename;
		sql += " limit " + ((page-1)*count)+","+count;
		JSONArray array = this.queryForJSONArray(sql, new Object[]{});
		if(array!=null && array.size()>0){
			for(int i=0;i<array.size();i++){
				JSONObject json = array.getJSONObject(i);
				Aai_postbean bean = new Aai_postbean();
				BeanUtils.populate(bean, json);
				list.add(bean);
			}
		}
		return list;
	}
}
