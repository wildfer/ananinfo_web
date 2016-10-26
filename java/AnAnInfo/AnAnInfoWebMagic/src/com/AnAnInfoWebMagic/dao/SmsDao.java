package com.AnAnInfoWebMagic.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("SmsDao")
public class SmsDao {
	private static Logger LOGGER = LoggerFactory.getLogger(SmsDao.class.getName());
	@Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcT;


	/**
	 * 查询短信
	 */
	public JSONArray getSms(){
		JSONArray array = null;
		String sql = "SELECT id,telnum,sms_template_code,`text` FROM aas_sms";
		try{
			List<Map<String, Object>> list = jdbcT.queryForList(sql, new Object[]{});
			array = JSONArray.fromObject(list);
		}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage() + "查询短信出错");
		}
		return array;
	}

	/*删除短信内容*/
	public void delSmsById(Integer id){
		String sqlString = "delete from aas_sms where id=?";
		jdbcT.update(sqlString, new Object[]{id});
	}
	/*备份发送的短信*/
	public void dealSmsResult(Integer id,String status,String strMsg){
		String sqlString = "INSERT INTO aas_sms_result(id,telnum,sms_template_code,`text`,`status`,msg,create_date) " +
				"SELECT id,telnum,sms_template_code,`text`,?,?,NOW() FROM aas_sms b where b.id = ?";
		jdbcT.update(sqlString, new Object[]{status,strMsg,id});
	}
}
