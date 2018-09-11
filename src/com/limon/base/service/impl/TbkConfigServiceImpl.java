package com.limon.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limon.base.dao.TbkConfigDao;
import com.limon.base.model.TbkConfig;
import com.limon.base.service.TbkConfigService;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
@Service("tbkConfigService")
public class TbkConfigServiceImpl implements TbkConfigService {
	
	@Autowired
	private TbkConfigDao tbkConfigDao;
	public TbkConfig getTbkConfig(String configKey){
		TbkConfig config = tbkConfigDao.getTbkConfig(configKey);
		return config;
	}
	
	public List<TbkConfig> getTbkConfigs(Map<String, Object> map) {
		List<TbkConfig> configs = tbkConfigDao.getTbkConfigs(map);
		return configs;
	}

	public Integer getTbkConfigsCount(Map<String, Object> map) {
		Integer count = tbkConfigDao.getTbkConfigsCount(map);
		return count;
	}

	public List<TbkConfig> getTbkConfigsNoPage(Map<String, Object> map) {
		List<TbkConfig> configs = tbkConfigDao.getTbkConfigsNoPage(map);
		return configs;
	}

	public Integer insertTbkConfig(Map<String,Object> map) {
		Integer inte=tbkConfigDao.getIsUseTbkConfig(map);
		if(inte==0){
			tbkConfigDao.insertTbkConfig(map);
		}
		return inte;
	}

	public TbkConfig getTbkConfigById(Map<String,Object> map){
		TbkConfig config = tbkConfigDao.getTbkConfigById(map);
		return config;
	}

	public Integer updateTbkConfigById(Map<String,Object> map) {
		tbkConfigDao.updateTbkConfigById(map);
		return 1;
	}

	public Integer deleteTbkConfigById(Map<String, Object> map) {
		Integer inte = tbkConfigDao.deleteTbkConfigById(map);
		return inte;
	}

}
