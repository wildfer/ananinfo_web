package com.AnAnInfoCmd.util;

/**
 * Created by Administrator on 2015/4/22.
 */
public class CustomException extends  Exception{  
  //公共错误
    public static final int SYSTEM_ERROR=61000;          //未知异常
    public static final int DATABASE_RUN_ERROR=61001;    //数据库错误
    public static final int SERVICE_NOTFOUND=61002;   //为找到服务
    public static final int DAO_UP_ERROR=61003;   //DAO新增错误
    public static final int DAO_SQL_CREATE=61004;  //sql自动创建错误
    public static final int CMDID_ERROR=61005;  //命令错误
    public static final int PARAMS_ERROR=61006;  //参数错误
    public static final int DATA_OVERLAP=61007;  //数据重复
    public static final int PARAM_ERROR=61008;  //参数错误
    
    String message;
    int    code;

    public CustomException(int ErrorCode,String ErrorMessagr) {
        this.message = ErrorMessagr;
        this.code=ErrorCode;
    }
    public String getMessage(){
        return message;
    }

    public int getCode(){
        return code;
    }
}