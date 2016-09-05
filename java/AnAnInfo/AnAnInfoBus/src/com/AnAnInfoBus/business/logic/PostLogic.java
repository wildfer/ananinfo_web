package com.AnAnInfoBus.business.logic;

import com.AnAnInfoBus.bean.*;
import com.AnAnInfoBus.business.dao.*;
import com.AnAnInfoBus.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("PostLogic")
public class PostLogic extends Logic {
	private static Logger LOGGER = LoggerFactory.getLogger(PostLogic.class.getName());

	@Resource(name = "AaiPostDao")
	private AaiPostDao aaiPostDao;

	/**
	 * 查询帖子
	 *
	 * @param params
	 * @return
	 * @throws CustomException
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String getPostList(JSONObject params) throws CustomException, Exception {
		Integer ipage = params.getInt("page");
		Integer icount = params.getInt("count");
		//Integer itype = params.getInt("type");// type

		//获取帖子列表
		ArrayList<Aai_postbean> portList = aaiPostDao.getPostList(ipage, icount);

		//强制输出组装，防止对外输出错误
		JSONArray portArray = new JSONArray();
		if (!portList.isEmpty()) {
			for (Aai_postbean post : portList) {
				JSONObject  jsonPost = JSONObject.fromObject(post);
				portArray.add(jsonPost);
			}
		}
		JSONObject retJson = new JSONObject();
		retJson.put("page", ipage);
		retJson.put("count", icount);
		retJson.put("post_list", portArray);
		return retJson.toString();
	}
}

