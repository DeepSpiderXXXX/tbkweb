package com.limon.base.dao;

import java.util.List;
import java.util.Map;

import com.limon.base.model.SysLog;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface SysLogDao {
    public int saveSysLog(SysLog sysLog);
    public int getLogTimes(Map<String,Object> map);
    public SysLog getLastLogInfo(Map<String,Object> map);
	public List<SysLog> getLogList(Map<String,Object> map);
	public Integer getLogListCount(Map<String,Object> map);
}
