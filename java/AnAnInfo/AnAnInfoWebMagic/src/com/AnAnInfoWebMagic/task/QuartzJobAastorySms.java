package com.AnAnInfoWebMagic.task;

import com.AnAnInfoWebMagic.util.Constants;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;
/*短信发送*/
public class QuartzJobAastorySms implements QuartzTask {
	private Logger LOGGER = LoggerFactory.getLogger(getClass().getName());
	@Override
	public void work(String[] args) {
		/*读取数据短信*/

		/*调用阿里发送短信*/
		String AliDaYuUrl= Constants.CONFIG.getProperty("ALIDAYU_API_URL");
		String AliDaYuAppKey= Constants.CONFIG.getProperty("ALIDAYU_API_APPKEY");
		String AliDaYuSecret= Constants.CONFIG.getProperty("ALIDAYU_API_SECRET");
		TaobaoClient client = new DefaultTaobaoClient(AliDaYuUrl, AliDaYuAppKey, AliDaYuSecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();

//		req.setExtend("123456"); //会员ID
		req.setSmsType("normal");//短信类型
		req.setSmsFreeSignName("阿里大于");//短信签名 阿里大于“管理中心-短信签名管理”中的可用签名
		req.setSmsParamString("{\"code\":\"1234\",\"product\":\"alidayu\"}");
		req.setRecNum("13000000000");
		req.setSmsTemplateCode("SMS_585014");
		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
		}catch (Exception e){
			LOGGER.error("阿里发送短信失败" + e.getMessage());
		}
		/*处理发送短信结果*/

	}

}
