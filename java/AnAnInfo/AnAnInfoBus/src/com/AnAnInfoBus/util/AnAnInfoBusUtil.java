package com.AnAnInfoBus.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.AnAnInfoBus.bean.*;
import com.AnAnInfoBus.business.dao.*;
import com.AnAnInfoBus.clientpool.UserServiceClient;
import com.AnAnInfoBus.thrift.ParamsIn;
import com.AnAnInfoBus.thrift.ParamsOut;



public class AnAnInfoBusUtil {
	private static org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(AnAnInfoBusUtil.class.getName());
	
	/**
	 * 访问其他服务
	 * 
	 * @param cmd
	 * @param jsonIn
	 * @return
	 * @throws CustomException
	 */
	public static ParamsOut requestOtherService(String cmd, String jsonIn)
			throws CustomException {
		ParamsIn paramsIn = new ParamsIn();
		paramsIn.setStrCmdID(cmd);
		paramsIn.setJsonIn(jsonIn.toString());

		ParamsOut paramsOut = UserServiceClient.invokeCmd(paramsIn);
		return paramsOut;
	}
	
	/**
	 * 判断是否是手机号
	 * @param str
	 * @return
	 */
	public static boolean isPhoneNumber(String str){
		if(str!=null){
			str = str.trim();
		}
		if (!isNumber(str)) {
			return false;
		}
		// 中国，长度11位，1开头
		if (str.length() != 11 || !str.startsWith("1")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断字符串是可以转换整数
	 * 
	 * @param str
	 * @return
	 */
	/* 超长数字，判断错误
	public static boolean isNumber(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		try {
			Long num = Long.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}*/

	//正则判断数字
	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("^[1-9]\\d*$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	/**
	 * 生成交易20位
	 * @return
	 * @throws CustomException
	 */
	public static String getUniqueID() {
		String trade_no = "";
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		trade_no += sdf.format(now);
		trade_no += RandomUtil.getRandom(5, RandomUtil.TYPE.NUMBER);
		return trade_no;
	}
	/**
	 * 取时间戳，10位
	 * 
	 * @param d
	 */
	public static String getTimestamp(Date d) {
		String time = String.valueOf(d.getTime());
		return time.substring(0, 10);
	}
	
	/**
	 * 获取指定范围的随机数
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
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
	 * 发送短信(并不是立即发送，先添加到发送队列表)
	 * @param strCountry
	 * @param strPhone
	 * @param strType
	 * @param content
	 * @param iPlantime
	 * @throws CustomException
	 */
//	@Transactional(propagation= Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
//	public static void sendSms(String strCountry, String strPhone,
//			String content, Integer iPlantime) throws CustomException{
//		if(strCountry==null || "".equals(strCountry)){
//			strCountry = "86";
//		}
//		if(strCountry.startsWith("+")){
//			strCountry = strCountry.substring(1);
//		}
//		Ms_sms_queueDao ms_sms_queueDao = (Ms_sms_queueDao)SpringUtil.getBean("Ms_sms_queueDao");
//		Ms_sms_queuebean bean = new Ms_sms_queuebean();
//		if(isPhoneNumber(strPhone)){
//			bean.setCountry(strCountry);
//			bean.setTo(strPhone);
//			bean.setMessage(content);
//			bean.setCtime(0);
//			bean.setPlantime(iPlantime);// 0立即发送
//			bean.setLevel(0);// 等级最高
//			ms_sms_queueDao.addMsSmsQueue(bean);
//		}
//	}
	
	/**
	 * 获取异常的详细信息
	 * @param e
	 * @return
	 */
	public static String getDetailMsgOfException(Exception e){
		String msg = "";
		StackTraceElement[] stes = e.getStackTrace();
		msg += e.getClass().getName()+":\n";
		for(StackTraceElement ste:stes){
			msg += "\tat "+ste.getClassName()+"."+ste.getMethodName()
					+"("+ste.getFileName()+":"+ste.getLineNumber()+")\n";
		}
		return msg;
	}
	
	/**
	 * 生成token
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String generateToken(String ip,Long ctime) throws Exception {
		String encryptStr = AES.encrypt(ip + ctime, Constants.CONFIG.getProperty("aes.key"));
		
		String token = MD5.md5(MD5.md5(encryptStr
				+ "minishop"));
		return token;
	}
	
	/**
	 * 根据token获取用户uid
	 * 
	 * @param token
	 * @return
	 * @throws Exception 
	 * @throws CustomException
	 */
//	public static Integer getUidByToken(String token){
//		Ms_member_tokenDao tokenDao = (Ms_member_tokenDao) SpringUtil
//				.getBean("Ms_member_tokenDao");
//		try {
//			Ms_member_tokenbean tokenBean = tokenDao.getByToken(token);
//			if(tokenBean==null || tokenBean.getStatus()!=0){
//				//如果状态不是正常
//				return null;
//			}else{
//				return tokenBean.getUid();
//			}
//		} catch (Exception e) {
//			return null;
//		}
//	}
	
	/**
	 * emoji表情转换
	 * 
	 * @param content
	 * @param type
	 *            1,转换为其他字符，2，转换为emoji表情,3,过滤表情
	 * @return
	 * @throws CustomException
	 */
	public static String convertEmoji(String content, String type)
			throws CustomException {
		try {
			if("3".equals(type)){
				content = filterEmoji(content);
			}
			else if("2".equals(type)){
				
				if(content.startsWith("#####")){
					content = content.replace("#####", "");
					content = URLDecoder.decode(content);
				}
				content = revertEmoji(content);
				//还原\x
				content = content.replaceAll("\\\\###x", "\\\\x");
			}
			else if ("1".equals(type)) {
				//处理特殊的\x情形
				content = content.replaceAll("\\\\x", "\\\\###x");
				content = convertEmoji(content);				
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("data", content);
//				map.put("type", type);
//				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//				Set<String> keySet = map.keySet();
//				for (String key : keySet) {
//					nvps.add(new BasicNameValuePair(key, map.get(key)));
//				}
//				
//				content = HttpRequest.httpClientPost(
//						getSiteURL()
//								+ BaijieConstants.CONFIG
//										.getProperty("emoji.convert.url"),
//						new UrlEncodedFormEntity(nvps, "UTF-8"), "UTF-8", 1000);
				if ("1".equals(type)) {
					// // content = "#####"+URLEncoder.encode(content);
					// content = content.replaceAll("\\", "\\\\");
					// content = content.replaceAll("\\\\", "\\\\\\\\");
//					System.out.println(content);
				}
			}
//			System.out.println(content);
		} catch (Exception e) {
			LOGGER.error("emoji表情转换错误,"+e.getMessage());
			throw new CustomException(CustomException.SYS_ERROR, "emoji表情转换错误");
		}
		return content;
	}
	
	/**
	 * 过滤掉表情
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		if(source==null || "".equals(source)){
			return "";
		}
//		System.out.println("过滤前："+source);
        StringBuilder buf = new StringBuilder();

        int len = source.length();
        
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            
            if (!isEmojiCharacter(codePoint)) {
//            	System.out.println("字符【"+codePoint+"】不是emoji字符");
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
 
                buf.append(codePoint);
            } else {
//            	System.out.println("字符【"+codePoint+"】是emoji字符");
            }
        }
//        System.out.println("过滤后："+buf.toString());
        return buf.toString();
	}
	
	/**
	 * 是否是emoji字符
	 * 
	 * @param codePoint
	 * @return
	 */
	public static boolean isEmojiCharacter(char codePoint) {
		return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
	}
	
	/**
	 * 是否包含emoji字符
	 * 
	 * @param source
	 * @return
	 */
	public static boolean containsEmoji(String source) {
		if (StringUtils.isBlank(source)) {
			return false;
		}

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isEmojiCharacter(codePoint)) {
				// do nothing，判断到了这里表明，确认有表情字符
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 恢复表情字符
	 * @param content
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String revertEmoji(String content) throws UnsupportedEncodingException{
		StringBuffer resultBuf = new StringBuffer();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < content.length(); i++) {
			String str1 = content.substring(i, i + 1);
			if(i+1<content.length()){
				String str2 = content.substring(i, i+2);
				if(str2.equals("\\x")){
					str2 = content.substring(i, i+4);
					buf.append(str2);
					i += 3;
				}else{
					if(buf.length()>0){
						String c = hex2Str(buf.toString());
						resultBuf.append(c);
						buf = new StringBuffer();
					}
					resultBuf.append(str1);
				}
			}else{
				if(buf.length()>0){
					String c = hex2Str(buf.toString());
					resultBuf.append(c);
					buf = new StringBuffer();
				}
				resultBuf.append(str1);
			}
		}
		if(buf.length()>0){
			String c = hex2Str(buf.toString());
			resultBuf.append(c);
			buf = new StringBuffer();
		}
		return resultBuf.toString();
	}
	
	public static String hex2Str(String str) throws UnsupportedEncodingException {
        String strArr[] = str.split("\\\\"); // 分割拿到形如 xE9 的16进制数据
        byte[] byteArr = new byte[strArr.length - 1];
        for (int i = 1; i < strArr.length; i++) {
            Integer hexInt = Integer.decode("0" + strArr[i]);
            byteArr[i - 1] = hexInt.byteValue();
        }
 
        return new String(byteArr, "UTF-8");
    }
	
	public static String convertEmoji(String content) throws UnsupportedEncodingException{
		System.out.println("---------------");
		System.out.println("转换前："+content);
		StringBuffer resultBuf = new StringBuffer();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < content.length(); i++) {
			char codePoint = content.charAt(i);
			
			if (isEmojiCharacter(codePoint)) {
				buf.append(codePoint);
//				System.out.println("添加字符后："+buf.length());
//				String hexString  = Integer.toHexString(codePoint);
//				String unicodeStr = "\\u"+hexString;
//				System.out.println(unicodeStr);
//				String str = loadConvert(unicodeStr);
//				System.out.println(str);
			}else{				
				if(buf.length()>0){
					String hexStr = str2Hex(buf.toString());
//					System.out.println(hexStr);
					resultBuf.append(hexStr);
					buf = new StringBuffer();
				}
				resultBuf.append(codePoint);
			}
			
		}
		if(buf.length()>0){
			String hexStr = str2Hex(buf.toString());
//			System.out.println(hexStr);
			resultBuf.append(hexStr);
			buf = new StringBuffer();
		}
		System.out.println("转换后："+resultBuf.toString());
		System.out.println("---------------");
		return resultBuf.toString();
	}
	
	public static String str2Hex(String str) throws UnsupportedEncodingException {
        String hexRaw = String.format("%x", new BigInteger(1, str.getBytes("UTF-8")));
        char[] hexRawArr = hexRaw.toCharArray();
        StringBuilder hexFmtStr = new StringBuilder();
        final String SEP = "\\x";
        for (int i = 0; i < hexRawArr.length; i++) {
        	StringBuilder hexFmtStr_1 = new StringBuilder();
        	hexFmtStr_1.append(hexRawArr[i]).append(hexRawArr[++i]);
        	String x = hexFmtStr_1.toString().toUpperCase();
            hexFmtStr.append(SEP).append(x);
        }
        return hexFmtStr.toString();
    }
	
	/**
	 * 获取身份证照片加密地址
	 * @param imgUrl
	 * @return
	 * @throws CustomException
	 * @throws Exception
	 */
	public static String getIdtImgUrl(String imgUrl,String token) throws CustomException, Exception{
			String code = AES.encrypt(imgUrl,
					Constants.CONFIG.getProperty("aes.key"));
			code = URLEncoder.encode(code, "utf-8");
			imgUrl = Constants.API_URL + "?m=minishop&c=read_pic&identity=" + code+"&token="+token;
		return imgUrl;
	}
	
	/**
	 * 获取图片链接
	 * 
	 * @param imgUrl
	 * @return
	 * @throws CustomException
	 */
	public static String getImgUrl(String imgUrl) throws CustomException,Exception {
		if (imgUrl == null || "".equals(imgUrl)) {
			return "";
		} else if (!imgUrl.toLowerCase().startsWith("http")) {
			if (imgUrl.indexOf("identity") != -1) {
				String code = AES.encrypt(imgUrl,
						Constants.CONFIG.getProperty("aes.key"));
				imgUrl = Constants.API_URL + "?m=minishop&c=read_pic&identity=" + code;
			} else {
				imgUrl = Constants.UPYUN_URL + imgUrl;
			}
		}
		return imgUrl;
	}
	
	//身份证号码验证
    public static void isIDT(String idt_number) throws CustomException {
        Pattern pattern = Pattern.compile("^(\\d{17}|\\d{14})(\\d|X|x)$");
        Matcher matcher = pattern.matcher(idt_number);
        if(!matcher.matches()){
            throw new CustomException(CustomException.ID_NUMBER_WRONG,       "身份证号有误");
        }
    }


	/**
	 * 转换币种标识
	 * @param unit
	 * @return
	 */
	public static String transferUnit(String unit){
		if(unit.equals("USD")){
			return "$";
		}else if(unit.equals("CNY")){
			return "¥";
		}else if(unit.equals("GBP")){
			return "£";
		}else if(unit.equals("JPY")){
			return "¥JP";
		}else if(unit.equals("EUR")){
			return "€";
		}else if(unit.equals("AUD")){
			return "$A";
		}else{
			return "";
		}
	}

}
