package com.limon.base.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limon.base.dao.TbkGroupDao;
import com.limon.base.model.TbkGroup;
import com.limon.base.service.TbkGroupService;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
@Service("TbkGroupService")
public class TbkGroupServiceImpl implements TbkGroupService {
	
	@Autowired
	private TbkGroupDao tbkGroupDao;

	@Override
	public void deleteTbkGroup(Integer id) {
		tbkGroupDao.deleteTbkGroup(id);
	}

	@Override
	public List<TbkGroup> getTbkGroupList(Map<String, Object> map) {
		return tbkGroupDao.getTbkGroupList(map);
	}

	@Override
	public Integer getTbkGroupListCount(Map<String, Object> map) {
		return tbkGroupDao.getTbkGroupListCount(map);
	}

	@Override
	public void saveTbkGroup(Map<String, Object> map) {
		tbkGroupDao.saveTbkGroup(map);
	}

	@Override
	public void updateTbkGroup(Map<String, Object> map) {
		tbkGroupDao.updateTbkGroup(map);
	}

	@Override
	public TbkGroup getTbkGroupById(String id) {
		return tbkGroupDao.getTbkGroupById(id);
	}

}