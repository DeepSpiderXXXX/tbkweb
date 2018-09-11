package com.limon.base.dao;

import java.util.List;
import java.util.Map;

import com.limon.base.model.SysConfig;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface SysConfigDao {
	public SysConfig getSysConfig(String configkey);
	public Integer getIsUseSysConfig(Map<String,Object> map);
	public List<SysConfig> getSysConfigs(Map<String,Object> map);
	public Integer getSysConfigsCount(Map<String,Object> map);
	public List<SysConfig> getSysConfigsNoPage(Map<String,Object> map);
	public Integer insertSysConfig(Map<String,Object> map);
	public SysConfig getSysConfigById(Map<String,Object> map);
	public Integer updateSysConfigById(Map<String,Object> map);
	public Integer deleteSysConfigById(Map<String,Object> map);
}
