package com.limon.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limon.base.dao.SysConfigDao;
import com.limon.base.model.SysConfig;
import com.limon.base.service.SysConfigService;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {
	
	@Autowired
	private SysConfigDao sysConfigDao;
	public SysConfig getSysConfig(String configKey){
		SysConfig config = sysConfigDao.getSysConfig(configKey);
		return config;
	}
	
	public List<SysConfig> getSysConfigs(Map<String, Object> map) {
		List<SysConfig> configs = sysConfigDao.getSysConfigs(map);
		return configs;
	}

	public Integer getSysConfigsCount(Map<String, Object> map) {
		Integer count = sysConfigDao.getSysConfigsCount(map);
		return count;
	}

	public List<SysConfig> getSysConfigsNoPage(Map<String, Object> map) {
		List<SysConfig> configs = sysConfigDao.getSysConfigsNoPage(map);
		return configs;
	}

	public Integer insertSysConfig(Map<String,Object> map) {
		Integer inte=sysConfigDao.getIsUseSysConfig(map);
		if(inte==0){
			sysConfigDao.insertSysConfig(map);
		}
		return inte;
	}

	public SysConfig getSysConfigById(Map<String,Object> map){
		SysConfig config = sysConfigDao.getSysConfigById(map);
		return config;
	}

	public Integer updateSysConfigById(Map<String,Object> map) {
		Integer inte=sysConfigDao.getIsUseSysConfig(map);
		if(inte==0){
			sysConfigDao.updateSysConfigById(map);
		}
		return inte;
	}

	public Integer deleteSysConfigById(Map<String, Object> map) {
		Integer inte = sysConfigDao.deleteSysConfigById(map);
		return inte;
	}

}
