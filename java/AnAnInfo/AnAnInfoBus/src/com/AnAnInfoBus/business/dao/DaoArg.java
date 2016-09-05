package com.AnAnInfoBus.business.dao;

/**
 * Created by Administrator on 2015/11/14.
 */
public class DaoArg {
    public String sql;
    public Object[] args;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public DaoArg(String sql, Object[] args){
        this.sql=sql;
        this.args=args;
    }
}
