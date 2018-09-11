package com.limon.base.service;

import java.util.List;
import java.util.Map;

import com.limon.base.model.SysLog;


/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface SysLogService {
	/**
	 * 记录日志
	 * @param sysLog
	 * @return
	 */
	public int saveSysLog(SysLog sysLog);
	/**
	 * 查询登录次数
	 * @param username
	 * @return
	 */
	public int getLogTimes(String username);
	/**
	 * 查询登录信息
	 * @param username
	 * @return
	 */
	public SysLog getLastLogInfo(String username);
	/**
	 * 日志查询
	 * @param map
	 * @return
	 */
	public List<SysLog> getLogList(Map<String,Object> map);
	/**
	 * 日志查询总数
	 * @param map
	 * @return
	 */
	public Integer getLogListCount(Map<String,Object> map);
}
