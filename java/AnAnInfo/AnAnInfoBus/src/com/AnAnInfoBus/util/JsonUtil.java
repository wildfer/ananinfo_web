package com.AnAnInfoBus.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 闪淘项目帮助类
 * @author wxb
 *
 */
public class JsonUtil {
	
	/**
	 * 获取指定范围的随机数
	 * @param min 最小值
	 * @param max 最大值
	 * @return
	 * @throws Exception 
	 */
	public static int getRandomNumber(int min,int max) throws Exception{
		if(min>=max){
			throw new Exception("the param \"min\" can't be greater than the param \"max\"");
		}
		Random random = new Random();
		return random.nextInt(max-min+1)+min;
	}
	
	//对json特殊字符作处理
	public static String stripJson(String s){
		StringBuffer sb = new StringBuffer ();     
        for (int i=0; i<s.length(); i++) {     
      
            char c = s.charAt(i);     
            switch (c) {
            case '\b':      //退格
                sb.append("\\b");     
                break;     
            case '\f':      //走纸换页
                sb.append("\\f");     
                break;     
            case '\n':     
                sb.append("\\n"); //换行    
                break;     
            case '\r':      //回车
                sb.append("\\r");     
                break;     
            case '\t':      //横向跳格
                sb.append("\\t");     
                break;     
            default:     
                sb.append(c);    
            }
        }
        return sb.toString();
	}
	
	/**
	 * 移除json里的null值对象
	 * @param json
	 * @return
	 */
	public static JSONObject removeNullJsonElement(JSONObject json){
		JSONObject newJson = new JSONObject();
		if(json!=null){
			Set<String> keySet = json.keySet();
			for(String key : keySet){
				Object o = json.get(key);
				if(o instanceof JSONObject){
					JSONObject subJson = (JSONObject)o;
					if(!subJson.isNullObject()){
						subJson = removeNullJsonElement(subJson);
						newJson.put(key, String.valueOf(subJson));
					}
				}else if(o instanceof JSONArray){
					o = removeNullJsonElement((JSONArray)o);
					newJson.put(key, o);
				}
				else{
					newJson.put(key, String.valueOf(o));
				}
			}
		}
		return newJson;
	}
	
	/**
	 * 移除json数组里的null值对象
	 * @param array
	 * @return
	 */
	public static JSONArray removeNullJsonElement(JSONArray array){
		if(array!=null && array.size()>0){
			for(int i=0;i<array.size();i++){
				if(array.get(i) instanceof JSONArray){
					JSONArray subArray = array.getJSONArray(i);
					removeNullJsonElement(subArray);
				}else if(array.get(i) instanceof JSONObject){
					JSONObject o = array.getJSONObject(i);
					o = removeNullJsonElement(o);
					array.set(i, o);
				}
			}
		}
		return array;
	}
	
	/**
	 * 将JSON对象中所有元素的值转换为string类型
	 * @param json
	 * @return
	 */
	public static JSONObject convertAllElementsToString(JSONObject json){
		Set<String> keySet = json.keySet();
		for(String key:keySet){
			Object e = json.get(key);
			if(e instanceof JSONObject){
				e = convertAllElementsToString((JSONObject)e);
				json.put(key, e);
			}else if(e instanceof JSONArray){
				e = convertAllElementsToString((JSONArray)e);
				json.put(key, e);
			}else{
				json.put(key, e.toString());
			}
		}
		return json;
	}
	
	/**
	 * 将JSON数组中所有元素的值转换为string类型
	 * @param array
	 * @return
	 */
	public static JSONArray convertAllElementsToString(JSONArray array){
		JSONArray tempArray = new JSONArray();
		for(int i=0;i<array.size();i++){
			Object o = array.get(i);
			if(o instanceof JSONObject){
				o = convertAllElementsToString((JSONObject)o);
				tempArray.add(o);
			}else if(o instanceof JSONArray){
				o = convertAllElementsToString((JSONArray)o);
				tempArray.add(o);
			}else{
				tempArray.add(o.toString());
			}
		}
		return tempArray;
	}
	
	public static void main(String[] args){
		String str = "[{'abc':1,'a':null,'b':'123','aa':{'ff':'kk','s':null}},{'abc':1,'a':null,'b':'123','aa':{'ff':'kk','s':null}}]";
		JSONArray array = JSONArray.fromObject(str);
//		System.out.println(json.toString());
//		System.out.println(json.get("a")==null);
		array = removeNullJsonElement(array);
		System.out.println(array.toString());
//		System.out.println(newJson.get("a")==null);
	}
}
