package com.limon.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limon.base.dao.SysLogDao;
import com.limon.base.model.SysLog;
import com.limon.base.service.SysLogService;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
	
	@Autowired
	private SysLogDao sysLogDao;
	
	public int saveSysLog(SysLog sysLog) {
		return sysLogDao.saveSysLog(sysLog);
	}

	public SysLog getLastLogInfo(String username) {
		Map<String,Object> map=new HashMap<String,Object> ();
		map.put("username", username);
		return sysLogDao.getLastLogInfo(map);
	}

	public int getLogTimes(String username) {
		Map<String,Object> map=new HashMap<String,Object> ();
		map.put("username", username);
		return sysLogDao.getLogTimes(map);
	}

	public Integer getLogListCount(Map<String, Object> map) {
		return sysLogDao.getLogListCount(map);
	}

	public List<SysLog> getLogList(Map<String, Object> map) {
		return sysLogDao.getLogList(map);
	}

}
