package com.limon.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.limon.base.model.SysLog;
import com.limon.base.service.SysLogService;


/**
 * @author gqf
 * 
 * 日志工具类
 * 2015-2-10 下午01:59:02
 */
@Component
public class LogUtil {
	
	@Autowired
	private SysLogService sysLogService;
	private static LogUtil logUtil;  
	
	/**
	 * 系统日志
	 * @param logtype  该参数类型有2种 1-系统日志  2-操作日志
	 * @param logcontent
	 * @return 
	 */
	public static int logSystem(String logcontent){
		SysLog sysLog=new SysLog();
		sysLog.setContent(logcontent);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		sysLog.setLogtime(sdf.format(new Date()));
		sysLog.setLogtype(1);
		sysLog.setLoguser("系统日志");
		sysLog.setLogip("127.0.0.1");
		int result =logUtil.sysLogService.saveSysLog(sysLog);
		return result;
	}
	
	/**
	 * 操作日志
	 * @param logtype  该参数类型有2种 1-系统日志  2-操作日志
	 * @param logcontent
	 * @param loguser
	 * @param logip
	 * @return
	 */
	public static int logOperation(String logcontent,String username,String ipaddr){
		SysLog sysLog=new SysLog();
		sysLog.setContent(logcontent);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		sysLog.setLogtime(sdf.format(new Date()));
		sysLog.setLogtype(2);
		sysLog.setLoguser(username);
		sysLog.setLogip(ipaddr);
		int result =logUtil.sysLogService.saveSysLog(sysLog);
		return result;
	}
	
	/**
	 * 登录日志
	 * @param logclass 该参数类型有三种级别ERROR DEBUG INFO
	 * @param logtype  日志类型 1-系统日志  2-操作日志 3-登录日志
	 * @param logcontent
	 * @param loguser
	 * @param logip
	 * @return
	 */
	public static int logLogin(String logcontent,String username,String ipaddr){
		SysLog sysLog=new SysLog();
		sysLog.setContent(logcontent);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		sysLog.setLogtime(sdf.format(new Date()));
		sysLog.setLogtype(3);
		sysLog.setLoguser(username);
		sysLog.setLogip(ipaddr);
		int result =logUtil.sysLogService.saveSysLog(sysLog);
		return result;
	}
	
	 @PostConstruct  
	 public void init() {  
		 logUtil = this;  
		 logUtil.sysLogService = this.sysLogService;  
	 } 
}
