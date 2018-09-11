package com.limon.base.dao;

import java.util.List;
import java.util.Map;

import com.limon.base.model.TbkConfig;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface TbkConfigDao {
	public TbkConfig getTbkConfig(String configkey);
	public Integer getIsUseTbkConfig(Map<String,Object> map);
	public List<TbkConfig> getTbkConfigs(Map<String,Object> map);
	public Integer getTbkConfigsCount(Map<String,Object> map);
	public List<TbkConfig> getTbkConfigsNoPage(Map<String,Object> map);
	public Integer insertTbkConfig(Map<String,Object> map);
	public TbkConfig getTbkConfigById(Map<String,Object> map);
	public Integer updateTbkConfigById(Map<String,Object> map);
	public Integer deleteTbkConfigById(Map<String,Object> map);
}
