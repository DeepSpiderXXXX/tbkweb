package com.limon.base.service;

import java.util.List;
import java.util.Map;

import com.limon.base.model.SysConfig;


/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface SysConfigService {
	/**
	 * 根据配置key查询配置
	 * @param configkey
	 * @return
	 */
	public SysConfig getSysConfig(String configkey);
	/**
	 * 查询配置列表
	 * @param map
	 * @return
	 */
	public List<SysConfig> getSysConfigs(Map<String,Object> map);
	/**
	 * 查询配置数
	 * @param map
	 * @return
	 */
	public Integer getSysConfigsCount(Map<String,Object> map);
	/**
	 * 查询配置列表无分页
	 * @param map
	 * @return
	 */
	public List<SysConfig> getSysConfigsNoPage(Map<String,Object> map);
	/**
	 * 添加配置
	 * @param PropConfig
	 * @return
	 */
	public Integer insertSysConfig(Map<String,Object> map);
	/**
	 * 通过id获取配置 
	 * @param map
	 * @return
	 */
	public SysConfig getSysConfigById(Map<String,Object> map);
	/**
	 * 修改配置
	 * @param PropConfig
	 * @return
	 */
	public Integer updateSysConfigById(Map<String,Object> map);
	/**
	 * 删除配置
	 * @param map
	 * @return
	 */
	public Integer deleteSysConfigById(Map<String,Object> map);
}
