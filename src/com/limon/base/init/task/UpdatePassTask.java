package com.limon.base.init.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.limon.base.service.TbkProductService;
import com.limon.base.taobao.DataokeAPI;
import com.limon.base.taobao.DataokeProduct;
import com.limon.util.DateUtil;

/**
 * 定时更新产品库
 * @author gqf
 *
 */
@Component
public class UpdatePassTask extends TimerTask{
	@Autowired
	private TbkProductService tbkProductService;
	
	@Override
	public void run() {
		System.out.println(DateUtil.getTodayTime()+":开始清空商品");
		tbkProductService.deleteTbkProductOutTime();
		System.out.println(DateUtil.getTodayTime()+":结束清空商品");

		//更新实时销量榜
		List<DataokeProduct> dlist=DataokeAPI.getTop100();
		System.out.println(DateUtil.getTodayTime()+":开始更新销量榜");
		int p=999999999;
		for(DataokeProduct d:dlist){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("point", p);
			map.put("id",d.getID());
			tbkProductService.updateTbkProductPointRandom(map);
			p--;
		}
		System.out.println(DateUtil.getTodayTime()+":结束更新销量榜");
	}
	
	public static void main(String[] args) {
		UpdatePassTask upt=new UpdatePassTask();
		upt.run();
	}
	
	public TbkProductService getTbkProductService() {
		return tbkProductService;
	}

	public void setTbkProductService(TbkProductService tbkProductService) {
		this.tbkProductService = tbkProductService;
	}
}
