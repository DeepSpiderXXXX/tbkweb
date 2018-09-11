package com.limon.base.controller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSON;
import com.limon.base.model.TbkProduct;
import com.limon.base.model.Ukey;
import com.limon.base.taobao.Group;

public class TestAPI {
	public static String url="http://localhost/tbk/api";
	
	public static void main(String[] args) {
		
	}
	
	public static TbkProduct search(String skey,Integer start,String ukey,String gname){
		TbkProduct tp=new TbkProduct();
		try{
			String apiurl=url+"/search";
			
			HttpClient client=new HttpClient();
			PostMethod post=new PostMethod(apiurl);
			post.addParameter("skey",skey);
			post.addParameter("start",start+"");
			post.addParameter("ukey",ukey);
			post.addParameter("gname", gname);
			post.getParams().setContentCharset("utf-8"); 
			client.executeMethod(post);
			String s=post.getResponseBodyAsString();
			System.out.println(s);
		}catch(Exception e){
			e.printStackTrace();
		}
		return tp;
	}
	
	public static Group findGroup(String ukey,String gname){
		Group tp=null;
		try{
			String apiurl=url+"/findgroup";
			
			HttpClient client=new HttpClient();
			PostMethod post=new PostMethod(apiurl);
			post.addParameter("ukey",ukey);
			post.addParameter("gname", gname);
			post.getParams().setContentCharset("utf-8"); 
			client.executeMethod(post);
			String s=post.getResponseBodyAsString();
			System.out.println(s);
			APIResponse rsp=JSON.parseObject(s,APIResponse.class);
			if(rsp.getResult()==1){
				tp=rsp.getGroup();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tp;
	}
	
	public static Ukey getukey(String ukey){
		Ukey key=null;
		try{
			String apiurl=url+"/getukey?ukey="+ukey;
			
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			System.out.println(s);
			APIResponse rsp=JSON.parseObject(s,APIResponse.class);
			if(rsp.getResult()==1){
				key=rsp.getUkey();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return key;
	}
	
}
