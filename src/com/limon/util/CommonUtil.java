package com.limon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.serializer.SerializerFeature;


/**
 * @author gqf
 * 通用工具类2015-2-12 下午03:55:10
 */
public class CommonUtil {
	/**
	 * fastjson转换参数配置
	 */
	public static SerializerFeature[] features = {
		SerializerFeature.WriteMapNullValue, // 输出空置字段
		SerializerFeature.WriteNullStringAsEmpty, // list字段如果为null，输出为[]，而不是
	};
	
	public static DecimalFormat getDoubleFormat(){
		DecimalFormat df=new DecimalFormat("#0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df;
	}
	
	/**
	 * 32位MD5加密算法
	 * 
	 * @param plainText
	 * @return
	 */
	public static String md5(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			re_md5 = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}

	/**
	 * 获取客户端IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {

		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")
					|| ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * 以key-value形式获取config.properties配置文件中的内容
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfig(String key) {
		Properties p = new Properties();
		try {
			p.load(CommonUtil.class.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}
	
	/**
	 * 以key-value形式获取config.properties配置文件中的内容
	 * 
	 * @param key
	 * @return
	 */
	public static String getDBConfig(String key) {
		Properties p = new Properties();
		try {
			p.load(CommonUtil.class.getResourceAsStream("/database.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}
	
	/**
	 * 判断字符串是否位数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 获取带毫秒的时间戳
	 * @return
	 */
	public static SimpleDateFormat getDateFormatMillisecond(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf;
	}
	
	/**
	 * 转换文件大小
	 * @param fileS
	 * @return
	 */
	public static String formatFileSize(long fileS) {
	    DecimalFormat df = new DecimalFormat("#.00");
	    String fileSizeString = "";
	    if (fileS < 1024) {
	        fileSizeString = df.format((double) fileS) + "B";
	    } else if (fileS < 1048576) {
	        fileSizeString = df.format((double) fileS / 1024) + "K";
	    } else if (fileS < 1073741824) {
	        fileSizeString = df.format((double) fileS / 1048576) + "M";
	    } else {
	        	fileSizeString = df.format((double) fileS / 1073741824) + "G";
	    }
	    return fileSizeString;
	}
	
	/** 
	 * 复制单个文件 
	* @param oldPath String 原文件路径 如：c:/fqf.txt 
	 * @param newPath String 复制后路径 如：f:/fqf.txt 
	 * @return boolean 
	 */ 
	 public static void copyFile(String oldPath, String newPath) { 
		 try { 
			 int bytesum = 0; 
			 int byteread = 0; 
			 File oldfile = new File(oldPath); 
			 if (oldfile.exists()) { //文件存在时 
				 InputStream inStream = new FileInputStream(oldPath); //读入原文件 
				 FileOutputStream fs = new FileOutputStream(newPath); 
				 byte[] buffer = new byte[1444]; 
				 while ( (byteread = inStream.read(buffer)) != -1) { 
					 bytesum += byteread; //字节数 文件大小 
					 System.out.println(bytesum); 
					 fs.write(buffer, 0, byteread); 
				 } 
				 inStream.close(); 
			 } 
		 }catch (Exception e) { 
			 System.out.println("复制单个文件操作出错"); 
			 e.printStackTrace(); 
		 } 
	 } 

	 /**
	  * 获取6位随机验证码
	  * @return
	  */
	 public static Integer getRandom() {
		Random random=new Random();
		int number=random.nextInt(1000000);
		return number;
	 }
}
