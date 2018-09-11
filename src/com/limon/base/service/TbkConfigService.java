package com.limon.base.service;

import java.util.List;
import java.util.Map;

import com.limon.base.model.TbkConfig;


/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface TbkConfigService {
	/**
	 * 根据配置key查询配置
	 * @param configkey
	 * @return
	 */
	public TbkConfig getTbkConfig(String configkey);
	/**
	 * 查询配置列表
	 * @param map
	 * @return
	 */
	public List<TbkConfig> getTbkConfigs(Map<String,Object> map);
	/**
	 * 查询配置数
	 * @param map
	 * @return
	 */
	public Integer getTbkConfigsCount(Map<String,Object> map);
	/**
	 * 查询配置列表无分页
	 * @param map
	 * @return
	 */
	public List<TbkConfig> getTbkConfigsNoPage(Map<String,Object> map);
	/**
	 * 添加配置
	 * @param PropConfig
	 * @return
	 */
	public Integer insertTbkConfig(Map<String,Object> map);
	/**
	 * 通过id获取配置 
	 * @param map
	 * @return
	 */
	public TbkConfig getTbkConfigById(Map<String,Object> map);
	/**
	 * 修改配置
	 * @param PropConfig
	 * @return
	 */
	public Integer updateTbkConfigById(Map<String,Object> map);
	/**
	 * 删除配置
	 * @param map
	 * @return
	 */
	public Integer deleteTbkConfigById(Map<String,Object> map);
}
