package com.AnAnInfoBus.business.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("AaiTagsDao")
public class AaiTagsDao extends Dao {
	private static Logger LOGGER = LoggerFactory.getLogger(AaiTagsDao.class.getName());

	public AaiTagsDao(){
		this.tablename = "aai_tags";
	}

}
