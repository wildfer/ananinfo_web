package com.AnAnInfoCmd.business.dao;


import com.AnAnInfoCmd.bean.Aai_system_servicebean;
import com.AnAnInfoCmd.util.CustomException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/27.
 */
@Repository("Aai_system_serviceDao")
public class Aai_system_serviceDao extends Dao {

    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcT;
    public Aai_system_serviceDao(){
    	this.tablename = "ms_system_service";
    }
    
    public Aai_system_servicebean getInfoBySerName(String strSerName) throws CustomException{
        try {
            Map<String, Object> hashMap=jdbcT.queryForMap("SELECT t.* FROM "+this.tablename
            		+" t  WHERE t.service_name = ? ", new Object[]{strSerName});
            Aai_system_servicebean servicebean = new Aai_system_servicebean();
            BeanUtils.populate(servicebean, hashMap);
            return servicebean;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.DATABASE_RUN_ERROR, e.getMessage()+"数据错误");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new CustomException(CustomException.DATABASE_RUN_ERROR, e.getMessage()+"数据错误");
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            throw new CustomException(CustomException.DATABASE_RUN_ERROR, e.getMessage()+" 未找到数据service:"+strSerName);
        }
    }
}
