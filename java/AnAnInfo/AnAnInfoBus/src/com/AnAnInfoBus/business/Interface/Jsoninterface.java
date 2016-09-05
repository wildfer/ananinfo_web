package com.AnAnInfoBus.business.Interface;


import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.AnAnInfoBus.bean.*;
import com.AnAnInfoBus.business.dao.*;
import com.AnAnInfoBus.business.logic.CmdLogic;
import com.AnAnInfoBus.util.Constants;
import com.AnAnInfoBus.util.CustomException;
import com.AnAnInfoBus.util.JsonUtil;
import com.AnAnInfoBus.util.SpringUtil;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
		jsonParamIn = JsonUtil.stripJson(jsonParamIn);
		try{
			json = JSONObject.fromObject(jsonParamIn);
		}catch (Exception e) {
			throw new CustomException(CustomException.INVALID_JSONOBJECT, "请求参数必须是JSON对象");
		}		

//		if(json == null){
//			throw new CustomException(CustomException.SYS_ERROR, "参数格式错误");
//		}
		json = JsonUtil.removeNullJsonElement(json);
		
//		if (json != null) {
//			// 根据token，初始化用户uid
//			Integer login_media_id = null;
//			Ms_member_tokenbean tokenBean = null;
//			Ms_member_tokenDao tokenDao = (Ms_member_tokenDao) SpringUtil
//					.getBean("Ms_member_tokenDao");
//			if (json.get("token") != null
//					&& !"".equals(json.getString("token"))) {
//				String token = json.getString("token");
//
//				Ms_memberDao memberDao = (Ms_memberDao)SpringUtil
//						.getBean("Ms_memberDao");
//
//				tokenBean = tokenDao.getByToken(token);
//
//				if(tokenBean!=null && tokenBean.getStatus()!=1){
//					//暂时不需要IP限制
////					String ip = json.getString("clientip");
////					String _token = MiniShopUtil.generateToken(ip, tokenBean.getCtime());
////					if(!token.equals(_token)){
////						throw new CustomException(CustomException.SERVICE_CALL_ERROR, "非法请求!请求IP跟登录IP不一致，请重新登录");
////					}
//					Integer uid =tokenBean.getUid();
//					Ms_memberbean member =  memberDao.getByUid(uid);
//					if(member!=null && member.getStatus()!=-1){
//						json.put("uid",uid);
//						login_media_id = tokenBean.getMedia_id();
////						Ms_mediabean media = ms_mediaDao.getById(media_id);
////						if(media!=null){
////							json.put("media_id", media_id);
////						}else{
////							tokenDao.updateMedia(tokenBean.getId(),
////									ms_mediaDao.getDefaulMedia().getId());
////							json.put("media_id",
////									ms_mediaDao.getDefaulMedia().getId());
////						}
//					}else{
//						throw new CustomException(CustomException.SIGN_IN_FAIL, "账户异常，请重新登录");
//					}
//				}
//			} else {
//				json.put("token", "");
//			}
//
//
//		}
		
		String jsonOut = new String();
		if (strCmd.equals("testMiniShop")) {
			return  test(json);
		} 
		//根据数据库配置，直接调用对应的服务方法		
		String serviceBeanId = "";
		if(Constants.CMD_MAP.get(strCmd)!=null){
			Aai_system_cmdbean cmdbean = Constants.CMD_MAP.get(strCmd);
			serviceBeanId = cmdbean.getCmd_class();			
		}else{
			CmdLogic cmdLogic = (CmdLogic)SpringUtil.getBean("CmdLogic");
			Aai_system_cmdbean cmdbean = cmdLogic.getCmdInfoByCmd(strCmd);
			if(cmdbean!=null){
				Constants.CMD_MAP.put(strCmd, cmdbean);			
				serviceBeanId = cmdbean.getCmd_class();
			}else{
				throw new CustomException(CustomException.CMDID_ERROR, "命令字错误");
			}
		}
		Object serviceInstance = SpringUtil.getBean(serviceBeanId);
		Method method = serviceInstance.getClass().getMethod(strCmd, JSONObject.class);
		try{
			if(json == null){
				json = new JSONObject();
			}
			if("void".equals(method.getReturnType().getName())){
				method.invoke(serviceInstance, json);
			}else{
				jsonOut = method.invoke(serviceInstance, json).toString();
			}
		}catch (InvocationTargetException e) {
			try{
				throw (Exception)e.getCause();
			}catch (ClassCastException ex) {
				ex.printStackTrace();
				throw e;
			}
		}
		
//		if (strCmd.equals("test")) {
//			test(json);
//		} else {
//			throw new CustomException(CustomException.CMDID_ERROR, "命令字错误");
//		}
		return jsonOut;
	}
	
	private String test(JSONObject json) throws CustomException, Exception {
		String test = json.getString("test");
		return test;
	}
}