package com.AnAnInfoWebMagic.task;

import com.AnAnInfoWebMagic.webMagicPro.ImgPipeline;
import com.AnAnInfoWebMagic.webMagicPro.LogicWebMagicMzitu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;

public class QuartzJobWebMagic implements QuartzTask {
	private Logger LOGGER = LoggerFactory.getLogger(getClass().getName());
	@Override
	public void work(String[] args) {
		LOGGER.info("爬虫任务开始");
//		PackageLogic packageLogic= (PackageLogic)SpringUtil.getBean("PackageLogic");
//		packageLogic.updateTrackInfo();
		Spider.create(new LogicWebMagicMzitu())
				//从"https://github.com/code4craft"开始抓
				.addUrl("http://www.mzitu.com/73339")
				//.scheduler(new FileCacheQueueScheduler("/data/temp/webmagic/cache/"))
				//设置Pipeline，将结果以json方式保存到文件
				.addPipeline(new ImgPipeline("E:\\tmp"))
				//开启个线程抓取
				.thread(1)
				//启动爬虫
				.run();

		LOGGER.info("爬虫任务结束");
	}

}
