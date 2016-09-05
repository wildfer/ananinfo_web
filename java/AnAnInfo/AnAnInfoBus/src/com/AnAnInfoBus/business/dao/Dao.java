package com.AnAnInfoBus.business.dao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.AnAnInfoBus.util.CustomException;
import com.AnAnInfoBus.util.JsonUtil;
import com.AnAnInfoBus.util.SpringJdbcUtil;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2015/4/22.
 */
public class Dao {
	private static Logger LOGGER = LoggerFactory.getLogger(Dao.class.getName());
	@Resource(name = "jdbcTemplate")
	public JdbcTemplate jdbcT;
	public String tablename;

	/*
	 * 
	 * private ArrayList sqlList=new ArrayList();
	 * 
	 * public ArrayList getSqlList() { return sqlList; }
	 * 
	 * public void addSql(String sql) { this.sqlList.add(sql);; }
	 */

	/*
	 * bean转换insertsql tablename:表名 excludekey:不需要写入的字段
	 */
	public String beanToInsertsql(Object bean, String tablename,
			String[] excludekey) throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		Map<String, String> propertys = BeanUtils.describe(bean);

		Set property = propertys.keySet();
		Iterator<String> it = property.iterator();

		String keysql = "";
		String valuesql = "";
		while (it.hasNext()) {
			String key = it.next();

			String value = propertys.get(key);
			value = StringEscapeUtils.escapeSql(value);
			if (!Arrays.asList(excludekey).contains(key)
					&& !key.equalsIgnoreCase("class")
					&& value!=null) {
				
				key = "`" + key + "`";

				keysql += key + ",";
				if (value == null || value.equalsIgnoreCase("null")) {
					valuesql += value + ",";
				} else {
					valuesql += "'" + value + "',";
				}
			}
		}
		if (!keysql.equals("")) {
			keysql = keysql.substring(0, keysql.length() - 1);
		}
		if (!valuesql.equals("")) {
			valuesql = valuesql.substring(0, valuesql.length() - 1);
		}
		String sql = "insert into `" + tablename + "` (" + keysql + ") values ("
				+ valuesql + ")";
		return sql;
	}

	/**
	 * 此方法采用prestatement方式
	 * @param bean
	 * @param tablename
	 * @param excludekey
	 * @return
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	public DaoArg beanToPreInsertsql(Object bean, String tablename,
								  String[] excludekey) throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		Map<String, String> propertys = BeanUtils.describe(bean);

		Set property = propertys.keySet();
		Iterator<String> it = property.iterator();

		String keysql = "";
		String valuesql = "";
		ArrayList<String> valuelist=new ArrayList();
		while (it.hasNext()) {
			String key = it.next();

			String value = propertys.get(key);
			if (!Arrays.asList(excludekey).contains(key)
					&& !key.equalsIgnoreCase("class")
					&& value!=null) {
//				if (key.equalsIgnoreCase("dec") || key.equalsIgnoreCase("add")
//						|| key.equalsIgnoreCase("desc")
//						|| key.equalsIgnoreCase("status")
//						|| key.equalsIgnoreCase("key")
//						|| key.equalsIgnoreCase("to")
//						|| key.equalsIgnoreCase("name")
//						|| key.equalsIgnoreCase("when")
//						|| key.equalsIgnoreCase("level")
//						|| key.equalsIgnoreCase("type")
//						|| key.equalsIgnoreCase("mod")
//						|| key.equalsIgnoreCase("default")) {// sql关键字特殊处理
				key = "`" + key + "`";
//				}
				keysql += key + ",";
				valuesql +=  "?,";
				valuelist.add(value);
			}
		}
		if (!keysql.equals("")) {
			keysql = keysql.substring(0, keysql.length() - 1);
		}
		if (!valuesql.equals("")) {
			valuesql = valuesql.substring(0, valuesql.length() - 1);
		}
		String sql = "insert into `" + tablename + "` (" + keysql + ") values ("
				+ valuesql + ")";
		return new DaoArg(sql,valuelist.toArray());
	}

	public String beanToUpdatesql(Object bean, String tablename,
			String[] excludekey, String where) throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		Map<String, String> propertys = BeanUtils.describe(bean);

		Set property = propertys.keySet();
		Iterator<String> it = property.iterator();

		String sql = "";

		while (it.hasNext()) {
			String key = it.next();
			String value = propertys.get(key);
			value = StringEscapeUtils.escapeSql(value);
			if (!Arrays.asList(excludekey).contains(key)
					&& !key.equalsIgnoreCase("class")) {

				if (value == null || value.equalsIgnoreCase("null")) {

				} else {
					
					key = "`" + key + "`";
					sql += key + " = ";
					sql += "'" + value + "',";
				}
			}
		}
		if (!sql.equals("")) {
			sql = sql.substring(0, sql.length() - 1);
		}

		sql = "update " + tablename + " set " + sql + " where " + where;

		return sql;
	}
	
	/**
	 * 此方法采用prestatement方式
	 * @param bean
	 * @param tablename
	 * @param excludekey
	 * @return
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	public DaoArg beanToPreUpdatesql(Object bean, String tablename,
			String[] excludekey, DaoArg where) throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		Map<String, String> propertys = BeanUtils.describe(bean);

		Set property = propertys.keySet();
		Iterator<String> it = property.iterator();
		ArrayList valuelist=new ArrayList();
		String sql = "";

		while (it.hasNext()) {
			String key = it.next();
			String value = propertys.get(key);
			if (!Arrays.asList(excludekey).contains(key)
					&& !key.equalsIgnoreCase("class")) {

				if (value == null || value.equalsIgnoreCase("null")) {

				} else {
					key = "`" + key + "`";

					sql += ("".equals(sql)?"":",")+key + " = ?";
					valuelist.add(value);
				}
			}
		}

		sql = "update " + tablename + " set " + sql + " where " + where.getSql();
		Object[] whereArgs = where.getArgs();
		if(whereArgs!=null){
			for(int i=0;i<whereArgs.length;i++){
				valuelist.add(whereArgs[i]);
			}
		}
		return new DaoArg(sql,valuelist.toArray());
	}

	/*
	 * 根据实体bean class和条件获取对应表的所有字段值并转化为实体bean集合返回
	 * added by wxb 2015-8-14
	 */
	public ArrayList queryDataForBeanByCondition(Class beanClass, String condition,
			String tableName) throws Exception {
		ArrayList listBean = new ArrayList();
		try{
		Method[] methods = beanClass.getDeclaredMethods();
		

		String sql = "select * from " + tableName + " where " + condition;

		List list = jdbcT.queryForList(sql);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object bean = beanClass.newInstance();
				Map<String, Object> map = (Map<String, Object>) list.get(i);
				Iterator<String> it = map.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					Object value = map.get(key);

					for (Method m : methods) {
						if (m.getName().equalsIgnoreCase("set" + key)) {
							String className = m.getParameterTypes()[0]
									.getSimpleName();
							if ("Integer".equals(className)) {
								if(value!=null){
									//转换boolean
									if(value.toString().equalsIgnoreCase("false")){
										value="0";
									}else if(value.toString().equalsIgnoreCase("true")){
										value="1";
									}
									m.invoke(bean, Integer.valueOf(value.toString()));
								}

							} else if ("Double".equals(className)) {
								m.invoke(bean, Double.valueOf(value.toString()));
							} else if ("Float".equals(className)) {
								m.invoke(bean, Float.valueOf(value.toString()));
							} else if ("Long".equals(className)) {
								m.invoke(bean, Long.valueOf(value.toString()));
							} else if ("BigDecimal".equals(className)) {
								m.invoke(bean, new BigDecimal(value.toString()));
							}else {
								m.invoke(bean,
										m.getParameterTypes()[0].cast(value));
							}
						}
					}
				}
				listBean.add(bean);
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询出错："+e.getMessage());
			throw new CustomException(CustomException.SYS_ERROR, "查询出错");
		}
		return listBean;
	}
	
	/**
	 * 根据查询条件直接转化为bean，只能用于单表查询
	 * @param beanClass
	 * @param sql
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public Object queryForBean(Class beanClass,String sql,Object[] values) throws Exception {	
		try{
			Method[] methods = beanClass.getDeclaredMethods();
			List list = SpringJdbcUtil.queryForList(sql, values, jdbcT);
			if (list != null && list.size() > 0) {
				Object bean = beanClass.newInstance();
				Map<String, Object> map = (Map<String, Object>) list.get(0);
				Iterator<String> it = map.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					Object value = map.get(key);

					for (Method m : methods) {
						
						if (m.getName().equalsIgnoreCase("set" + key)) {
							if (value != null) {
								String className = m.getParameterTypes()[0]
										.getSimpleName();
								if ("Integer".equals(className)) {
										// 转换boolean
										if (value.toString().equalsIgnoreCase(
												"false")) {
											value = "0";
										} else if (value.toString()
												.equalsIgnoreCase("true")) {
											value = "1";
										}
										m.invoke(bean, Integer.valueOf(value
												.toString()));

								} else if ("Double".equals(className)) {
									m.invoke(bean,
											Double.valueOf(value.toString()));
								} else if ("Float".equals(className)) {
									m.invoke(bean,
											Float.valueOf(value.toString()));
								} else if ("Long".equals(className)) {
									m.invoke(bean,
											Long.valueOf(value.toString()));
								} else if ("BigDecimal".equals(className)) {
									m.invoke(bean,
											new BigDecimal(value.toString()));
								} else {
									m.invoke(bean, m.getParameterTypes()[0]
											.cast(value));
								}
							}else{
								m.invoke(bean,m.getParameterTypes()[0]
										.cast(value));
							}
						}
					}
				}
				
				return bean;
			}else{
				return null;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询出错："+e.getMessage());
			throw new CustomException(CustomException.SYS_ERROR, "查询出错");
		}
	}
	
	/**
	 * 获取表的所有字段
	 * @param tableName
	 * @return
	 * @throws CustomException
	 */
	public List<String> getAllColumnsByTableName(String tableName) throws CustomException{
		String schema = null;
		DruidPooledConnection conn= null;
		try {
			conn = (DruidPooledConnection)jdbcT.getDataSource().getConnection();
			//JDBC4DatabaseMetaData meta = (JDBC4DatabaseMetaData) conn.getMetaData();
			
			//ResultSet rs = meta.getCatalogs();
			schema = conn.getCatalog();
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage()+"系统错误,请重试");
			throw new CustomException(CustomException.SYS_ERROR,       "系统错误,请重试");
		}finally{
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {					
					e.printStackTrace();
					throw new CustomException(CustomException.SYS_ERROR,       "系统错误,请重试");
				}
			}
		}
		ArrayList<String> colList = new ArrayList<String>();
		String sqlString = "select column_name from information_schema.columns" +
				" where table_schema='"+schema+"' and table_name='"+tableName+"'";
		List<Map<String, Object>> list = jdbcT.queryForList(sqlString);
		if (list != null && list.size() > 0) {
			for(Map<String, Object> map:list){
				String colNameString = map.get("column_name").toString();
				colList.add(colNameString.toLowerCase());
			}
		}		
		return colList;
	}
	
	public String tanslateMySqlKey(String key){
		if (key.equalsIgnoreCase("dec")
				|| key.equalsIgnoreCase("add")
				|| key.equalsIgnoreCase("desc")
				|| key.equalsIgnoreCase("status")
				|| key.equalsIgnoreCase("key")
				|| key.equalsIgnoreCase("to")
				|| key.equalsIgnoreCase("name")
				|| key.equalsIgnoreCase("when")
				|| key.equalsIgnoreCase("level")
				|| key.equalsIgnoreCase("type")
				|| key.equalsIgnoreCase("mod")){
			return "`"+key+"`";
		}else{
			return key;
		}
	}
	
	/**
	 * 用json数据生成插入sql，json必须是单层的对象
	 * @param json
	 * @param tablename
	 * @return
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	public String jsonToInsertsql(JSONObject json, String tablename) throws IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		Iterator<String> it = json.keySet().iterator();

		String keysql = "";
		String valuesql = "";
		while (it.hasNext()) {
			String key = it.next();

			String value = json.get(key).toString();
			value = StringEscapeUtils.escapeSql(value);
			//不特殊处理关键字，所有都用"``"包起来
			key = "`" + key + "`";
			keysql += key + ",";
			if (value == null || value.equalsIgnoreCase("null")) {
				valuesql += value + ",";
			} else {
				valuesql += "'" + value + "',";
			}
		}
		if (!keysql.equals("")) {
			keysql = keysql.substring(0, keysql.length() - 1);
		}
		if (!valuesql.equals("")) {
			valuesql = valuesql.substring(0, valuesql.length() - 1);
		}
		String sql = "insert into `" + tablename + "` (" + keysql + ") values ("
				+ valuesql + ")";
		return sql;
	}
	
	/**
	 * 执行更新操作的sql
	 * @param sql
	 * @param operateDesc
	 * @throws CustomException
	 */
	public void executeUpdate(String sql,String operateDesc) throws CustomException{
		try{
			this.jdbcT.update(sql);
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage()+operateDesc+"发生错误");
			throw new CustomException(CustomException.SYS_ERROR, operateDesc+"发生错误");
		}
	}
	
	/**
	 * 执行更新操作的sql
	 * @param daoArg
	 * @param operateDesc
	 * @throws CustomException
	 */
	public void executeUpdate(DaoArg daoArg,String operateDesc) throws CustomException{
		try{
			this.jdbcT.update(daoArg.getSql(),daoArg.args);
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage()+operateDesc+"发生错误");
			throw new CustomException(CustomException.SYS_ERROR, operateDesc+"发生错误");
		}
	}
	
	/**
	 * 执行更新操作的sql(带参数)
	 * @param sql
	 * @param operateDesc
	 * @throws CustomException
	 */
	public void executeUpdate(String sql,Object[] args,String operateDesc) throws CustomException{
		try{
			this.jdbcT.update(sql,args);
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage()+operateDesc+"发生错误");
			throw new CustomException(CustomException.SYS_ERROR, operateDesc+"发生错误");
		}
	}
	
	/**
	 * 批量执行sql
	 * @param sqlList
	 * @param operateDesc
	 * @throws CustomException 
	 */
	public void batchExecuteUpdate(ArrayList<String> sqlList,String operateDesc) throws CustomException{
		try{
			String[] sqlArray = new String[sqlList.size()];
			for(int i=0;i<sqlList.size();i++){
				sqlArray[i] = sqlList.get(0);
			}
			this.jdbcT.batchUpdate(sqlArray);
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage()+operateDesc+"发生错误");
			throw new CustomException(CustomException.SYS_ERROR, operateDesc+"发生错误");
		}
	}
	
	/**
	 * 查询结果转换为JSONArray，sql不带参数
	 * @param sql
	 * @return
	 * @throws CustomException
	 */
//	public JSONArray queryForJSONArray(String sql) throws CustomException{
//		try{
//			List<Map<String, Object>> list = SpringJdbcUtil.queryForList(sql, jdbcT);
//			if(list!=null && list.size()>0){
//				return JsonUtil.removeNullJsonElement(JSONArray.fromObject(list));
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage()+"查询出错");
//			throw new CustomException(CustomException.SYS_ERROR,"查询出错");
//		}
//		return null;
//	}
	
	/**
	 * 查询结果转换为JSONArray，sql带参数
	 * @param sql
	 * @return
	 * @throws CustomException
	 */
	public JSONArray queryForJSONArray(String sql,Object[] args) throws CustomException{
		try{
			List<Map<String, Object>> list = SpringJdbcUtil.queryForList(sql,args,jdbcT);
			if(list!=null && list.size()>0){
				return JsonUtil.removeNullJsonElement(JSONArray.fromObject(list));
			}
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage()+"查询出错");
			throw new CustomException(CustomException.SYS_ERROR,"查询出错");
		}
		return null;
	}
	
	/**
	 * 单条查询结果转换为JSONObject，sql不带参数
	 * @param sql
	 * @return
	 * @throws CustomException
	 */
//	public JSONObject queryForJSONObject(String sql) throws CustomException{
//		try{
//			List<Map<String, Object>> list = SpringJdbcUtil.queryForList(sql, jdbcT);
//			if(list!=null && list.size()>0){
//				return JsonUtil.removeNullJsonElement(JSONObject.fromObject(list.get(0)));
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//			LOGGER.error(e.getMessage()+"查询出错");
//			throw new CustomException(CustomException.SYS_ERROR,"查询出错");
//		}
//		return null;
//	}
	
	/**
	 * 单条查询结果转换为JSONObject，sql带参数
	 * @param sql
	 * @return
	 * @throws CustomException
	 */
	public JSONObject queryForJSONObject(String sql,Object[] args) throws CustomException{
		try{
			List<Map<String, Object>> list = SpringJdbcUtil.queryForList(sql,args,jdbcT);
			if(list!=null && list.size()>0){
				return JsonUtil.removeNullJsonElement(JSONObject.fromObject(list.get(0)));
			}
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage()+"查询出错");
			throw new CustomException(CustomException.SYS_ERROR,"查询出错");
		}
		return null;
	}

	public int getLastInsertID() throws CustomException{
		int storeID = 0;
		try {
			storeID = jdbcT.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		}catch(Exception ex){
			ex.printStackTrace();
			LOGGER.error(ex.getMessage()+"获取ID 错误");
			throw new CustomException(CustomException.DATA_ERROR,"获取ID 错误");
		}
		return storeID;
	}

	public int getCount(String key ,String table,String condition) throws CustomException{
		int cnt = 0;
		try {
			cnt = jdbcT.queryForObject("select count("+key+") as cnt from " +table+" where "+condition, Integer.class);

		}catch(Exception ex){
			ex.printStackTrace();
			LOGGER.error(ex.getMessage()+"获取数量 错误");
			throw new CustomException(CustomException.DATA_ERROR,"获取数量 错误");
		}
		return cnt;
	}

	public boolean isExist(String key ,String table,String condition) throws CustomException{
		int cnt = 0;
		String sql = "select count("+key+") as cnt from " +table+" where "+condition;
		try {			
			cnt = jdbcT.queryForObject(sql, Integer.class);
		}catch(Exception ex){
			ex.printStackTrace();
			LOGGER.error(ex.getMessage()+"查询出错,sql:"+sql);
			throw new CustomException(CustomException.DATA_ERROR,"查询出错,sql:"+sql);
		}
		if(cnt==0){
			return false;
		}
		return true;
	}

	public String arrayToString(Object[] a){
		if (a == null)
			return "null";

		int iMax = a.length - 1;
		if (iMax == -1)
			return "";

		StringBuilder b = new StringBuilder();
		b.append("'");
		for (int i = 0; ; i++) {
			b.append(String.valueOf(a[i]));
			if (i == iMax)
				return b.append("'").toString();
			b.append("','");
		}
	}

	/**
	 * 利用表对应的映射bean，插入数据库
	 * @param bean
	 * @param tablename
	 * @param excludekey
	 * @throws Exception
	 */
	public void insertBeanToDB(Object bean, String tablename,
								  String[] excludekey) throws Exception{
		DaoArg daoArg = beanToPreInsertsql(bean, tablename,
				excludekey);
	    jdbcT.update(daoArg.getSql(), daoArg.getArgs());
	}
	
	/**
	 * 利用表对应的映射bean，更新数据库
	 * @param bean
	 * @param tablename
	 * @param excludekey
	 * @param where
	 * @throws Exception
	 */
	public void updateByBean(Object bean, String tablename,
			String[] excludekey, DaoArg where)throws Exception{
		DaoArg daoArg = this.beanToPreUpdatesql(bean, tablename, excludekey, where);
		jdbcT.update(daoArg.getSql(),daoArg.getArgs());
	}
}
