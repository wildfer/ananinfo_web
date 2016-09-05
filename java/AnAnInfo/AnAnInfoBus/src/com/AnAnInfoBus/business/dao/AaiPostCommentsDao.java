package com.AnAnInfoBus.business.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("Aai_postDao")
public class AaiPostCommentsDao extends Dao {
	private static Logger LOGGER = LoggerFactory.getLogger(AaiPostCommentsDao.class.getName());

	public AaiPostCommentsDao(){
		this.tablename = "aai_post_Comments";
	}

}
