package com.AnAnInfoBus.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * 重写spring的sql里的类型与java类型的映射，将tinyint映射为Integer(原来是映射为的boolean)
 * @author wxb
 *
 */
public class MyColumnMapRowMapper extends ColumnMapRowMapper {
		
		public Map<String, Object> mapRow(ResultSet rs,int rowNum)throws SQLException{
			ResultSetMetaData rsd = rs.getMetaData();
			int columnCount = rsd.getColumnCount();
			Map<String, Object> mapOfColValues = this.createColumnMap(columnCount);
			for(int i=1;i<=columnCount;i++){
				String key = this.getColumnKey(JdbcUtils.lookupColumnName(rsd, i));
				Object obj = null;
				if(rsd.getColumnTypeName(i).toLowerCase().startsWith("tinyint")
						|| rsd.getColumnTypeName(i).toLowerCase().startsWith("int")
						|| rsd.getColumnTypeName(i).toLowerCase().startsWith("smallint")
						|| rsd.getColumnTypeName(i).toLowerCase().startsWith("mediumint")
						|| rsd.getColumnTypeName(i).toLowerCase().startsWith("integer")){
					obj = rs.getInt(i);
				}				
				else{
					obj = this.getColumnValue(rs, i);
					if(obj instanceof String){
						obj = ((String)obj).replaceAll("''", "'");
					}
				}
				mapOfColValues.put(key, obj);
			}
			return mapOfColValues;
		}
}
