package com.AnAnInfoWebMagic.task;

import com.AnAnInfoWebMagic.dao.SmsDao;
import com.AnAnInfoWebMagic.util.Constants;
import com.AnAnInfoWebMagic.util.SpringUtil;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;
/*短信发送*/
public class QuartzJobAastorySms implements QuartzTask {
	private Logger LOGGER = LoggerFactory.getLogger(getClass().getName());
	@Override
	public void work(String[] args) {
		/*读取数据短信*/
		SmsDao smsDao = (SmsDao) SpringUtil.getBean("SmsDao");
		JSONArray jsonSms  = smsDao.getSms();

		if(jsonSms!=null && !jsonSms.isEmpty()) {
			for (int i = 0; i < jsonSms.size(); i++) {
				JSONObject json = jsonSms.getJSONObject(i);
				Integer smsId = json.getInt("id");
				String smsTelnum = json.getString("telnum");
				String smsTemplate = json.getString("sms_template_code");
				String smsText = json.getString("text");

				/*调用阿里发送短信*/
				String AliDaYuUrl = Constants.CONFIG.getProperty("ALIDAYU_API_URL");
				String AliDaYuAppKey = Constants.CONFIG.getProperty("ALIDAYU_API_APPKEY");
				String AliDaYuSecret = Constants.CONFIG.getProperty("ALIDAYU_API_SECRET");
				TaobaoClient client = new DefaultTaobaoClient(AliDaYuUrl, AliDaYuAppKey, AliDaYuSecret);
				AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();

				//		req.setExtend("123456"); //会员ID
				req.setSmsType("normal");//短信类型
				req.setSmsFreeSignName("签名测试");//短信签名 阿里大于“管理中心-短信签名管理”中的可用签名

				JSONObject rsJson = new JSONObject();
				rsJson.put("code", smsText);
				req.setSmsParamString(rsJson.toString());
				req.setRecNum(smsTelnum);
				req.setSmsTemplateCode(smsTemplate); //模板ID 阿里大于“管理中心-短信模板管理”中的可用模板

				String strStatus = "0";
				String strMsg = "";
//				try {
//					AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//					System.out.println(rsp.getBody());
//					LOGGER.debug(rsp.getBody());
//				} catch (Exception e) {
//					LOGGER.error("阿里发送短信失败" + e.getMessage());
//					strStatus = "1";
//					strMsg = e.getMessage();
//				}
				/*处理发送短信结果*/
				smsDao.dealSmsResult(smsId,strStatus,strMsg);
				smsDao.delSmsById(smsId);
			}
		}
	}

}
