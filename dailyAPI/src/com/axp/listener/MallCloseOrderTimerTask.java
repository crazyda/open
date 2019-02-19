package com.axp.listener;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import com.axp.dao.DateBaseDAO;
import com.axp.dao.ReGoodsorderItemDao;
import com.axp.domain.ReGoodsorder;
import com.axp.domain.ReGoodsorderItem;
import com.axp.service.IOrderPayService;
import com.axp.util.DateUtil;
import com.axp.util.QueryModel;
import com.axp.util.ToolSpring;

/***
 * Title:
 * Description:
 * Company:
 * @author hzc* @date
 */
public class MallCloseOrderTimerTask extends TimerTask {
	public DateBaseDAO datebaseDao;
	public ReGoodsorderItemDao itemDao;
	public IOrderPayService orderPayService;

	public MallCloseOrderTimerTask() {
		
		
		
	}

	/*
	 * 被调用具体的方法
	 */
	public void run() {
		//checkOrder();
	}

	private void checkOrder() {
		datebaseDao = (DateBaseDAO)ToolSpring.getBean("dateBaseDAO");
		itemDao = (ReGoodsorderItemDao)ToolSpring.getBean("reGoodsorderItemDao");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -15);
		Date limit = cal.getTime();
		try{
			QueryModel model = new QueryModel();
			model.combPreEquals("isValid", true);
			model.combPreEquals("status", ReGoodsorder.dai_shou_huo);
			model.combCondition("isBack in ("+ReGoodsorder.ke_tui_dan+","+ReGoodsorder.bu_ke_tui_dan+")");
			model.combCondition("createTime<'"+DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",limit)+"'");
			String orders = datebaseDao.findHQLGroupConcat(ReGoodsorderItem.class, model, "id");
			if("-1".equals(orders)){
				return;
			}
			update(orders);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private void update(String orders){
		orderPayService = (IOrderPayService)ToolSpring.getBean("orderPayService");
		orderPayService.updateItemByAuto(orders);
	}
}