//package com.shantao.logic;
//
//import javax.annotation.Resource;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.shantao.dao.FinanceDao;
//
//@Service("AlipayLogic")
//public class AlipayLogic {
//	private static Logger LOGGER = LoggerFactory.getLogger(AlipayLogic.class.getName());
//	@Resource(name="FinanceDao")
//	private FinanceDao financeDao;
//
//	@Transactional(propagation= Propagation.REQUIRED)
//	public void alipayLogToFinance(){
//		//用户充值
//		//查出finance中最大bind_id的充值记录
////		LOGGER.info("批量插入用户充值财务流水");
////		String bind_id = financeDao.getMaxBind_idByModAndType("member", "recharge");
////		//根据最大bind_id查询alipaylog中大于此bind_id的充值记录，并插入finance
////		if(bind_id!=null){
////			financeDao.insertRechargeFinance(bind_id);
////		}
//
//		//用户购物
//		LOGGER.info("批量插入用户购物财务流水");
//		financeDao.insertPayorderFinance();
//	}
//}
