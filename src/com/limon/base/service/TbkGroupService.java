package com.limon.base.service;

import java.util.List;
import java.util.Map;

import com.limon.base.model.TbkGroup;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface TbkGroupService {
    public List<TbkGroup> getTbkGroupList(Map<String,Object> map);
    public Integer getTbkGroupListCount(Map<String,Object> map);
    public void updateTbkGroup(Map<String,Object> map);
    public void saveTbkGroup(Map<String,Object> map);
    public void deleteTbkGroup(Integer id);
	public TbkGroup getTbkGroupById(String id);
}
