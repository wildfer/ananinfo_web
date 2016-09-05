package com.AnAnInfoBus.util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    private static Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class.getName());
    /**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param,Integer iTimeout) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + ((param==null || "".equals(param))?"":( "?" + param));
            LOGGER.debug("请求URL:"+urlNameString);
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            
            connection.setReadTimeout(iTimeout==null?Integer.valueOf(
            		Constants.CONFIG.getProperty("http.connection.timeout")):iTimeout);
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            LOGGER.error("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 返回byte[]结果
     **/
    public static byte[] sendGet_byte(String url,int iTimeOut,String responseCharset) {
        BufferedInputStream in = null;
        try {           
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            connection.setReadTimeout(iTimeOut);
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedInputStream(connection.getInputStream());
            //in = new BufferedInputStream(new InputStreamReader(connection.getInputStream(),responseCharset));
            byte[] buffer = new byte[connection.getContentLength()];
            in.read(buffer);
            return buffer;
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception 
     */
    public static String sendPost(String url, String param,int iTimeOut,String contentType) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            conn.setReadTimeout(iTimeOut);
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if(contentType!=null){
            	conn.setRequestProperty("Content-Type", contentType);
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            LOGGER.error("发送POST请求出现异常！" + e);
            e.printStackTrace();
            throw new CustomException(CustomException.SYS_ERROR,"发送POST请求出现异常！");
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * 利用httpclient发送post请求
     * @param url 请求url
     * @param requestEntity 请求的参数及一些设置
     * @param responseCharset 返回的内容的字符集
     * @param iTimeout 请求超时时间
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String httpClientPost(String url,UrlEncodedFormEntity requestEntity,
    		String responseCharset,int iTimeout) throws Exception{
    	try{
    		DefaultHttpClient httpclient = new DefaultHttpClient();
    		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, iTimeout);
    		HttpPost post = new HttpPost(url);
    		post.setEntity(requestEntity);
    		HttpResponse response = httpclient.execute(post); 
    		HttpEntity entity = response.getEntity();
    		String info = EntityUtils.toString(entity,responseCharset);
    		return info;
    	}catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("访问"+url+"出错，"+e.getMessage());
			throw e;
		}
    }
    
    public static void main(String[] args) throws Exception{
        Long now = (new java.util.Date()).getTime();
        String time=String.valueOf(now).substring(0, 10);
        String action="UserGroupByUser";
        String auth_key="3R1Fe19BN4vvGWfNdhcJ";
        String token=MD5.md5(time+action+auth_key);
        String uid="25";

        String param="id=haitao:forum_data_provider";
        param+="&time="+time;
        param+="&token="+token;
        param+="&action="+action;
        param+="&uid="+uid;

        String str = sendGet("http://bbs.dev.55haitao.com/plugin.php",
                param,1000);
        System.out.print(str);
    	//发送json格式请求
        /*
    	JSONObject json = new JSONObject();
    	String content = "";
    	json.put("sentence", content);
    	String str = sendPost("http://120.26.117.54:8896/filter/api/v1.0/getResult", 
    			json.toString(),1000, "application/json; charset=UTF-8");
    	System.out.println(str);
    	JSONObject retJson = JSONObject.fromObject(str);
    	String result = retJson.getString("result");
    	if("banned".equalsIgnoreCase(result)){
    		JSONArray keyWords = retJson.getJSONArray("keyWord");
    		for(int i=0;i<keyWords.size();i++){
    			String keyword = keyWords.getString(i);
    			content = content.replaceAll(keyword, "**");
    		}
    	}
    	System.out.println(retJson.get("keyWord").toString());
    	System.out.println(content);
    	*/
    }

}