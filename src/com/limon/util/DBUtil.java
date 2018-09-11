package com.limon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.limon.base.taobao.Group;

public class DBUtil {
	private static String db_url=CommonUtil.getDBConfig("mysql_url")+"&user="+CommonUtil.getDBConfig("mysql_username")+"&password="+CommonUtil.getDBConfig("mysql_password");
	private static String db_jdbc="com.mysql.jdbc.Driver";
	
	/**
	 * 获取数据库配置
	 * @param configkey
	 * @return
	 */
	public static String getConfig(String configkey){
		String configvalue="";
		try{
			Class.forName(db_jdbc);
		    Connection conn = DriverManager.getConnection(db_url);
            Statement stat = conn.createStatement();
            String sql="select * from robot_config where configkey='"+configkey+"'";
            System.out.println(sql);
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()) { // 将查询到的数据打印出来
            	configvalue=rs.getString("configvalue");
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return configvalue;
	}
	
	/**
	 * 获取数据库配置
	 * @param configkey
	 * @return
	 */
	public static String getTbkConfig(String ckey){
		String configvalue="";
		try{
			Class.forName(db_jdbc);
		    Connection conn = DriverManager.getConnection(db_url);
            Statement stat = conn.createStatement();
            String sql="select * from tbk_config where ckey='"+ckey+"'";
            System.out.println(sql);
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()) { // 将查询到的数据打印出来
            	configvalue=rs.getString("cvalue");
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return configvalue;
	}
	
	/**
	 * 获取群组pid
	 * @param grouname
	 * @return
	 */
	public static Group getGroupPid(String grouname){
		Group g=null;
		try{
			Class.forName(db_jdbc);
		    Connection conn = DriverManager.getConnection(db_url);
            Statement stat = conn.createStatement();
            String sql="select * from tbk_group where groupname='"+grouname+"'";
            System.out.println(sql);
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()) { // 将查询到的数据打印出来
            	g=new Group();
            	g.setId(rs.getInt("id"));
            	g.setGroupname(rs.getString("groupname"));
            	g.setPid(rs.getString("pid"));
            	g.setOrdernum(rs.getInt("ordernum"));
            	g.setPointtimes(rs.getInt("pointtimes"));
            	g.setSrfee(rs.getDouble("srfee"));
            	g.setXgfee(rs.getDouble("xgfee"));
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return g;
	}
	
}
