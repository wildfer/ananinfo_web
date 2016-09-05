//package com.shantao.logic;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.security.NoSuchAlgorithmException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Date;
//
//import javax.annotation.Resource;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.http.ParseException;
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.stereotype.Service;
//
//import com.shantao.app.ShantaotaskMain;
//import com.shantao.bean.FeiyangConfig;
//import com.shantao.bean.TrackInfoURL;
//import com.shantao.dao.PackageDao;
//import com.shantao.util.HttpRequest;
//import com.shantao.util.SpringUtil;
//
//@Service("PackageLogic")
//public class PackageLogic {
//	private static Logger LOGGER = LoggerFactory.getLogger(PackageLogic.class.getName());
//	@Resource(name="PackageDao")
//	private PackageDao packageDao;
//
//
//	/**
//	 * 更新物流信息
//	 * 其中金海淘和飞洋都是通过html解析出来的，如果其所属网站有改版或改动，需要重新测试并做相应改动
//	 */
//	public void updateTrackInfo() {
//		//先查出最近30*3天发货,并且最近更新时间大于3小时的包裹
//		JSONArray array = packageDao.getAll4Update();
//		if(array!=null && !array.isEmpty()){
//			for(int i=0;i<array.size();i++){
//				JSONObject json = array.getJSONObject(i);
//				Integer zy_company = json.getInt("zy_company");
//				String track_number = json.getString("track_number");
//				Integer id = json.getInt("id");
//				String code = json.getString("code");
//				String trackInfo = "";
//
//				switch (zy_company) {
//				case 1:
//					//金海淘
//					try {
//						trackInfo = getJHTTrackInfo(track_number);
//					} catch (IOException e) {
//						e.printStackTrace();
//						LOGGER.error("获取运单["+track_number+"(转运公司ID："+zy_company+")]的物流信息出错");
//					}
//					break;
//				case 9:
//					//飞扬转运
//					try {
//						trackInfo = getFYTrackInfo(track_number);
//					}catch (IOException e) {
//						e.printStackTrace();
//						LOGGER.error("获取运单["+track_number+"(转运公司ID："+zy_company+")]的物流信息出错");
//					}
//					break;
//				default:
//					//利用kuaidi100查询物流信息
//					try {
//						if(!"".equals(code)){
//							trackInfo = getTrackInfoFromKd100(code,track_number);
//						}
//					}catch (IOException e) {
//						e.printStackTrace();
//						LOGGER.error("获取运单["+track_number+"(转运公司ID："+zy_company+")]的物流信息出错");
//					}
//					break;
//				}
//				if(trackInfo!=null && !"".equals(trackInfo)){
//					LOGGER.debug("获取运单["+track_number+"(转运公司ID："+zy_company+")]的物流信息成功，物流信息："+trackInfo);
//					packageDao.updateTrackInfo(id, trackInfo);
//				}
//			}
//		}
//	}
//
//	/**
//	 * 根据订单号获取金海淘物流信息，直接解析html获得
//	 * @param track_number
//	 * @return
//	 * @throws IOException
//	 */
//	public String getJHTTrackInfo(String track_number) throws IOException{
//		TrackInfoURL trackInfoURL = TrackInfoURL.getInstance();
//		String apiUrl = trackInfoURL.getJht(track_number);
//		//解析html用Jsoup
//		Connection connection = Jsoup.connect(apiUrl);
//		connection.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//		Document doc = connection.get();
//		Elements newsHeadlines = doc.getElementsByClass("OrderTrackConRight");
//
//		Element orderTrackConRight = newsHeadlines.get(0);
//		Element tableElement =orderTrackConRight.child(1);
//
//		Elements rows = tableElement.child(0).children();
//		JSONArray array = new JSONArray();
//		for(int i=2;i<rows.size();i++){
//			Element row = rows.get(i);
//			Elements cells = row.children();
//			JSONObject json = new JSONObject();
//			String time = cells.get(0).text();
//			String des = cells.get(1).text();
//			json.put("time", time);
//			json.put("des", des);
//			array.add(json);
//			//修改包裹签收状态
//			if(des.indexOf("已签收")!=-1){
//				packageDao.updateIscheck(track_number,1);
//			}
//		}
//		return array.toString();
//	}
//
//	/**
//	 * 获取飞洋转运的物流信息
//	 * API查询搞不定，改用html解析
//	 * @param track_number
//	 * @return
//	 * @throws NoSuchAlgorithmException
//	 * @throws IOException
//	 * @throws UnsupportedEncodingException
//	 * @throws ParseException
//	 */
//	public String getFYTrackInfo(String track_number) throws IOException {
//		TrackInfoURL trackInfoURL = TrackInfoURL.getInstance();
//		FeiyangConfig config = FeiyangConfig.getInstance();
//		String apiUrl = trackInfoURL.getFyzy(track_number).trim();
//		//解析html用Jsoup
//		Connection connection = Jsoup.connect(apiUrl);
//		connection.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//		Document doc = connection.get();
//
//		Elements tables = doc.getElementsByTag("table");
//		Element trackInfoTable = tables.get(7);
//		Elements rows = trackInfoTable.child(0).children();
//		JSONArray array = new JSONArray();
//		for(int i=1;i<rows.size();i++){
//			Element row = rows.get(i);
//			Elements cells = row.children();
//			if(cells.size()!=3){
//				continue;
//			}
//			JSONObject json = new JSONObject();
//			String time = cells.get(0).text();
//			String des = cells.get(2).text();
//			json.put("time", time);
//			json.put("des", des);
//			array.add(json);
//			//修改包裹签收状态
//			if(des.indexOf("投递并签收")!=-1){
//				packageDao.updateIscheck(track_number,1);
//			}
//		}
//        String info = array.toString();
//
//		return info;
//	}
//
//	/**
//	 * 通过快递100来获取物流信息
//	 * @param code 物流公司代码
//	 * @param track_number 运单号
//	 * @return
//	 * @throws IOException
//	 */
//	public String getTrackInfoFromKd100(String code,String track_number) throws IOException{
//		TrackInfoURL trackInfoURL = TrackInfoURL.getInstance();
//		String apiUrl = trackInfoURL.getKd100(code, track_number);
//		//解析html用Jsoup
//		String info = HttpRequest.sendGet(apiUrl, 30*1000, "UTF-8");
//		System.out.println(info);
//		JSONObject json = JSONObject.fromObject(info);
//		String staus = json.getString("status");
//		//状态值200是正常，其他为有错
//		if("200".equals(staus)){
//			JSONArray dataArray = json.getJSONArray("data");
//			JSONArray array = new JSONArray();
//			for(int i=0;i<dataArray.size();i++){
//				JSONObject dataJson = dataArray.getJSONObject(i);
//				JSONObject rsJson = new JSONObject();
//				rsJson.put("time", dataJson.get("time"));
//				rsJson.put("des", dataJson.get("context"));
//				array.add(rsJson);
//			}
//			//因为返回的是按时间倒序，这里做下处理，对时间按正序排
//			Collections.sort(array, new Comparator() {
//				public int compare(Object o1, Object o2) {
//					JSONObject json1 = JSONObject.fromObject(o1);
//					JSONObject json2 = JSONObject.fromObject(o2);
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					try {
//						String time1 = json1.getString("time");
//						Date d1 = sdf.parse(time1);
//						String time2 = json2.getString("time");
//						Date d2 = sdf.parse(time2);
//						if(d1.before(d2)){
//							return -1;
//						}else{
//							return 1;
//						}
//					} catch (java.text.ParseException e) {
//						e.printStackTrace();
//					}
//					return 0;
//				}
//			});
//			//修改包裹签收状态
//			if("3".equals(json.getString("state"))){
//				packageDao.updateIscheck(track_number,1);
//			}
//			return array.toString();
//		}else{
//			return null;
//		}
//	}
//
//	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ParseException{
//		ShantaotaskMain.SPRING_CONTEXT = new ClassPathXmlApplicationContext("applicationContext.xml");
//		long start = Calendar.getInstance().getTimeInMillis();
//		PackageLogic ins = (PackageLogic) SpringUtil.getBean("PackageLogic");
//		String info = ins.getFYTrackInfo("DD934488878US");
//		long end = Calendar.getInstance().getTimeInMillis();
//		System.out.println(info);
//		System.out.println("查询耗时 "+(end-start)+"ms");
//	}
//}
