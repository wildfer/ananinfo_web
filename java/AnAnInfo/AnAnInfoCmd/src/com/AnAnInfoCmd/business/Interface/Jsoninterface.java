package com.AnAnInfoCmd.business.Interface;

import com.AnAnInfoCmd.bean.*;
import com.AnAnInfoCmd.business.logic.*;
import com.AnAnInfoCmd.util.CustomException;
import com.AnAnInfoCmd.util.JsonUtil;
import com.AnAnInfoCmd.util.SpringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.text.StrMatcher;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lt on 2015/4/9. JSON通用接口处理类
 */
public class Jsoninterface {
	private static Logger LOGGER = LoggerFactory.getLogger(Jsoninterface.class
			.getName());
	public static String ERR_NOTCMD = "9999";

	public String Process(String strCmd, String jsonParamIn)
			throws CustomException, Exception {
		// 根据开始字符判断是对象还是对象数组
		JSONObject json = null;
		JSONArray jsonArray = null;
		jsonParamIn = JsonUtil.stripJson(jsonParamIn);
		if (jsonParamIn.startsWith("{")) {
			json = JSONObject.fromObject(jsonParamIn);
		} else if (jsonParamIn.startsWith("[")) {
			jsonArray = JSONArray.fromObject(jsonParamIn);
		}
		String jsonOut = new String();
		if (strCmd.equals("test")) {
			test(json);
		} else {
			throw new CustomException(CustomException.CMDID_ERROR, "命令字错误");
		}
		return jsonOut;
	}
	private void test(JSONObject json) throws CustomException, Exception {
		String test = json.getString("test");
	}

}