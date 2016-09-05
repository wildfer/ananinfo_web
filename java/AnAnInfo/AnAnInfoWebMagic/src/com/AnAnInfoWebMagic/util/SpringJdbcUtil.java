package com.AnAnInfoWebMagic.util;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

public class SpringJdbcUtil {
	private static MyColumnMapRowMapper myMapper = new MyColumnMapRowMapper();
	
	/**
	 * 为解决spring里默认将tinyint映射为boolean型问题，查询时用自定义的类型映射类MyColumnMapRowMapper
	 * @param sql
	 * @param jdbcT
	 * @return
	 */
	public static List<Map<String, Object>> queryForList(String sql,JdbcTemplate jdbcT){
		List<Map<String, Object>> list = (List<Map<String, Object>>)jdbcT.query(sql, new RowMapperResultSetExtractor(myMapper));
		return list;
	}
}
