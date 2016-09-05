package com.AnAnInfoBus.business.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("AaiPostTagsDao")
public class AaiPostTagsDao extends Dao {
	private static Logger LOGGER = LoggerFactory.getLogger(AaiPostTagsDao.class.getName());

	public AaiPostTagsDao(){
		this.tablename = "aai_post_tags";
	}

}
