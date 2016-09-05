package com.AnAnInfoWebMagic.webMagicPro;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.FilePersistentBase;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import java.io.IOException;
/**
 * Created by lt on 2016/8/31.
 */
public class ImgPipeline  extends FilePersistentBase implements Pipeline {
    private Logger LOGGER = LoggerFactory.getLogger(getClass().getName());
    public ImgPipeline() {
        setPath("/data/webmagic/");
    }
    public ImgPipeline(String path) {
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String fileStorePath = this.path;
        try {
           // String imgShortNameNew="(http://www.meizitu.com/wp-content/uploads/)|(jpg)";
            String imgShortNameNetmpw="test";
            CloseableHttpClient httpclient = HttpClients.createDefault();


            System.out.println("url:\t" + resultItems.getRequest().getUrl());
            Iterator iter = resultItems.getAll().entrySet().iterator();

            while(iter.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>)iter.next();

                    System.out.println((String) entry.getKey() + ":" + entry.getValue());


                        StringBuffer sb = new StringBuffer();
                        StringBuffer imgFileNameNewYuan =sb.append(fileStorePath)
                                .append("test") ;
                        //这里先判断文件夹名是否存在，不存在则建立相应文件夹
                        Path target = Paths.get(imgFileNameNewYuan.toString());
                        if(!Files.isReadable(target)){
                            Files.createDirectory(target);
                        }

                        String extName=com.google.common.io.Files.getFileExtension((String)entry.getValue());
                        StringBuffer imgFileNameNew = imgFileNameNewYuan
                                .append(((String)entry.getValue()).replaceAll(imgShortNameNetmpw, "")
                                        .replaceAll("[\\pP‘’“”]", ""))
                                .append(".")
                                .append(extName);


                        //这里通过httpclient下载之前抓取到的图片网址，并放在对应的文件中
                        HttpGet httpget = new HttpGet((String)entry.getValue());
                        HttpResponse response = httpclient.execute(httpget);
                        HttpEntity entity = response.getEntity();
                        InputStream in = entity.getContent();
                        File file = new File(imgFileNameNew.toString());
                        try {
                            FileOutputStream fout = new FileOutputStream(file);
                            int ch = 0;
                            while ((ch = in.read()) != -1) {
                                fout.write(ch);
                            }
                            fout.flush();
                            fout.close();
                        } finally {
                            in.close();
                        }
            }
            httpclient.close();
        } catch (IOException e) {
            LOGGER.warn("write file error", e);
        }
    }
}