package com.AnAnInfoCmd.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Administrator on 2015/4/15.
 */
public class DBUtil {
    private static DataSource ds = null;

    static {
        try{
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("DruidDb.properties");
            Properties props = new Properties();
            props.load(in);
            ds = DruidDataSourceFactory.createDataSource(props);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    //自动提交，不支持事务
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    //支持事务
    public static Connection getTxConnection() throws SQLException {
        Connection c=ds.getConnection();
        c.setAutoCommit(false);
        return c;
    }
}
