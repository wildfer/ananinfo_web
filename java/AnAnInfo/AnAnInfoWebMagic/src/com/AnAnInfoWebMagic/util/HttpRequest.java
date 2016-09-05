package com.AnAnInfoWebMagic.util;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpRequest {
    private static Logger LOGGER = LoggerFactory.getLogger(HttpRequest.class.getName());
    /**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url,int iTimeOut,String charset) {
        String result = "";
        BufferedReader in = null;
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
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
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
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param,int iTimeOut,String charset) {
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
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
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
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            LOGGER.error("发送GET请求出现异常！" + e);
            e.printStackTrace();
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
    		String responseCharset,int iTimeout) throws ParseException, IOException{
    	DefaultHttpClient httpclient = new DefaultHttpClient();
    	httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, iTimeout);
    	HttpPost post = new HttpPost(url);
    	post.setEntity(requestEntity);
    	HttpResponse response = httpclient.execute(post); 
        HttpEntity entity = response.getEntity();
        String info = EntityUtils.toString(entity,responseCharset);
        return info;
    }
    
    /**
     * 发送https的post请求，除了client要做一些特殊处理外，跟普通http没区别
     * @return
     */
    public static String httpsClientPost(String url,UrlEncodedFormEntity requestEntity,
    		String responseCharset,int iTimeout) throws ParseException, IOException{
    	DefaultHttpClient httpclient = getHttpsClient();
    	httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, iTimeout);
    	HttpPost post = new HttpPost(url);
    	post.setEntity(requestEntity);
    	HttpResponse response = httpclient.execute(post); 
        HttpEntity entity = response.getEntity();
        String info = EntityUtils.toString(entity,responseCharset);
        return info;
    }
    
    public static DefaultHttpClient getHttpsClient()   
    {  
        try {  
            SSLContext ctx = SSLContext.getInstance("TLS");  
            X509TrustManager tm = new X509TrustManager() {  
  
                @Override  
                public void checkClientTrusted(  
                        java.security.cert.X509Certificate[] chain,  
                        String authType)  
                        throws java.security.cert.CertificateException {  
                }  
  
                @Override  
                public void checkServerTrusted(  
                        java.security.cert.X509Certificate[] chain,  
                        String authType)  
                        throws java.security.cert.CertificateException {  
                }  
  
                @Override  
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
                    return null;  
                }  
                  
            };  
            DefaultHttpClient client = new DefaultHttpClient();  
            ctx.init(null, new TrustManager[] { tm }, null);  
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);  
              
            ClientConnectionManager ccm = client.getConnectionManager();  
            SchemeRegistry sr = ccm.getSchemeRegistry();  
            //设置要使用的端口，默认是443  
            sr.register(new Scheme("https", 443, ssf));  
            return client;  
        } catch (Exception ex) {    
            return null;  
        }  
    } 
}