package com.AnAnInfoWebMagic.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class AnAnInfoWebMagicTaskUtil {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	/**
	 * 获取某个区间的随机整数
	 * 
	 * @param min
	 * @param max
	 * @return
	 * @throws Exception
	 */
	public static int getRandomNumber(int min, int max) throws Exception {
		if (min >= max) {
			throw new Exception(
					"the param \"min\" can't be greater than the param \"max\"");
		}
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

	/**
	 * 根据post参数生成md5的签名，很多外部api需要
	 * 算法是先把参数根据字母顺序排序，再组合成post参数字符串，再加上额外的字符串，最后用md5加密
	 * 
	 * @param map
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String generateHttpPostParamsMD5Sign(Map<String, String> map,
			String extraStr) throws NoSuchAlgorithmException {

		String paramString = "";
		Set<String> keySet = map.keySet();
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(keySet);
		Collections.sort(list);
		for (String key : list) {
			paramString += ("".equals(paramString) ? "" : "&") + key + "="
					+ map.get(key);
		}
		paramString += extraStr;
		
		String sign = md5(paramString);
		return sign;
	}

	/**
	 * md5加密
	 * 
	 * @param origin 待加密字符串
	 * @return 加密后字符串
	 */
	public static String md5(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}

		return resultString;
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}

		return resultSb.toString();
	}
	
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;

		return hexDigits[d1] + hexDigits[d2];
	}

}
