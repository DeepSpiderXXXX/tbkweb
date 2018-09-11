/**
 * 
 */
package com.limon.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author gqf
 *
 * 日期工具类
 * 2015-2-26 上午09:15:03
 */
public class DateUtil {
	public static String getToday(){
		Calendar c=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	public static String getTodayTime(){
		Calendar c=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());
	}
	
	public static String getTodayTimeSS(){
		Calendar c=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(c.getTime());
	}
	
	public static String getLastWeek(){
		Calendar c=Calendar.getInstance();
		c.add(Calendar.DATE,-7);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	public static String getLastDayTime(int daynum){
		Calendar c=Calendar.getInstance();
		c.add(Calendar.DATE,daynum);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(c.getTime());
	}
	public static String getLastDay(int daynum){
		Calendar c=Calendar.getInstance();
		c.add(Calendar.DATE,daynum);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	public static String getTwoDay(){
		Calendar c=Calendar.getInstance();
		c.add(Calendar.DATE,-2);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	
	public static String getNextMonth(){
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MONTH,1);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	
	public static String getNextWeek(){
		Calendar c=Calendar.getInstance();
		c.add(Calendar.DATE,7);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	
	public static String dateToStr(Date date){
		String str = "";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		str = sdf.format(date);
		return str;
	}
	public static Date strToDate(String str){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getMonthFirst(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
        return first;
	}
	
	public static String getMonthLast(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	    Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        return last;
	}
	
	public static String getLastMonthFirst(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = format.format(c.getTime());
        return first;
	}
	
	public static String getLastMonthLast(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
	    Calendar ca = Calendar.getInstance(); 
	    ca.add(Calendar.MONTH, -1);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = format.format(ca.getTime());
        return last;
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getLastMonthFirst()+"=="+DateUtil.getLastMonthLast());
	}
}
