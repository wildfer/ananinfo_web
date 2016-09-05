package com.AnAnInfoBus.business.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("zzzDao")
public class zzzDao extends Dao {
	private static Logger LOGGER = LoggerFactory.getLogger(zzzDao.class.getName());

	public zzzDao(){
		this.tablename = "aai_postDao";
	}

}
