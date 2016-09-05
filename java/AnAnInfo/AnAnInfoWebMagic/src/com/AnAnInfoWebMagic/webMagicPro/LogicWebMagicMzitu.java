package com.AnAnInfoWebMagic.webMagicPro;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lt on 2015/8/31 妹子图爬虫
 */
public class LogicWebMagicMzitu implements PageProcessor{
	private static Logger LOGGER = LoggerFactory.getLogger(LogicWebMagicMzitu.class.getName());
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	//private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	private Site site = Site.me().addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.71 Safari/537.36").setDomain("my.test.com");

	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	@Override
	public void process(Page page) {
		// 部分三：加入链接到队列
//		page.addTargetRequests(page.getHtml().links().regex("(https://55shantao\\.com/\\overseas/)").all());
//		page.addTargetRequests(page.getHtml().links().regex("http://www.55shantao.com/index.php?mod=overseas&op=get_list)").all());
		LOGGER.debug("页面地址"+page.getRequest().getUrl());
		//page.putField("url", page.getHtml().links().all());
		page.putField("pic",page.getHtml().xpath("//div[@class='main-image']/p/a/img/@src").toString());
//		page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//		page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//		if (page.getResultItems().get("name")==null){
//			//skip this page
//			page.setSkip(true);
//		}
//		page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));



//		String imgRegex = "http://www.meizitu.com/wp-content/uploads/20[0-9]{2}[a-z]/[0-9]{1,4}/[0-9]{1,4}/[0-9]{1,4}.jpg";
//		List<String> requests = page.getHtml().links().regex(urlPattern).all();
//		page.addTargetRequests(requests);
//		String imgHostFileName = page.getHtml().xpath("//title/text()").toString().replaceAll("[|\\pP‘’“”\\s(妹子图)]", "");
//		List<String> listProcess = page.getHtml().$("div#picture").regex(imgRegex).all();
//		//此处将标题一并抓取，之后提取出来作为文件名
//		listProcess.add(0, imgHostFileName);
//		page.putField("img", listProcess);
	}
	@Override
	public Site getSite() {
		return site;
	}

}
