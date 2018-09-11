package com.limon.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class TestDataoke {
	public static void main(String[] args) throws HttpException, IOException {
		//登录
		DefaultHttpClient client=new DefaultHttpClient();
		
		HttpPost post=new HttpPost("http://www.dataoke.com/loginApi");
		post.addHeader("Host", "www.dataoke.com");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		post.addHeader("Origin", "http://www.dataoke.com");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		post.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("Referer","http://www.dataoke.com/login");
		post.addHeader("Accept-Encoding", "gzip, deflate");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.addHeader("Cookie","token=e5313251f8b6aea9a91153e73460d27c; random=2585; dtk_web=vq7jfv4s3e14hfcrv56res6272; UM_distinctid=15aff3c3cfd38b-0530e6dbc-424e002e-15f900-15aff3c3cfe2fa; CNZZDATA1257179126=1565039183-1490337283-http%253A%252F%252Fwww.dataoke.com%252F%7C1490337283;");
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        nvps.add(new BasicNameValuePair("username", "15545587532"));  
        nvps.add(new BasicNameValuePair("password", "mylove1217"));  
        nvps.add(new BasicNameValuePair("vc", ""));  
        nvps.add(new BasicNameValuePair("ref", ""));  
        post.setEntity(new UrlEncodedFormEntity(nvps));  
		HttpResponse rsp=client.execute(post);
		HttpEntity rspEntity=rsp.getEntity();
		System.out.println("登录结果："+EntityUtils.toString(rspEntity));

		String logincookie="";
		List<Cookie> list=client.getCookieStore().getCookies();
		for(Cookie c:list){
			logincookie+=c.getName()+"="+c.getValue()+";";
		}

		//加入推广
		HttpPost postt=new HttpPost("http://www.dataoke.com/handle_popularize");
		postt.addHeader("Host", "www.dataoke.com");
		postt.addHeader("Connection", "keep-alive");
		postt.addHeader("Accept", "text/plain, */*; q=0.01");
		postt.addHeader("Origin", "http://www.dataoke.com");
		postt.addHeader("X-Requested-With", "XMLHttpRequest");
		postt.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
		postt.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		postt.addHeader("Referer","http://www.dataoke.com/qlist/");
		postt.addHeader("Cookie","UM_distinctid=15aff3c3cfd38b-0530e6dbc-424e002e-15f900-15aff3c3cfe2fa; ASPSESSIONIDAQCTBDSS=NGJOPEGCBEMLCPOINMGOIGKF; ASPSESSIONIDAQBTDATS=KOMIOEGCMHNNDLGMDAGKAGMJ; ASPSESSIONIDCSDQBCTS=CEFEFHGCOFCLFLGIPOOIJAAB; token=e5313251f8b6aea9a91153e73460d27c; random=2585;"+logincookie+"ASPSESSIONIDASCSADST=AKKJKJGCNNBBJHMPNPBBIAKF;CNZZDATA1257179126=1565039183-1490337283-http%253A%252F%252Fwww.dataoke.com%252F%7C1490337283;");
		
		List<NameValuePair> nvpst = new ArrayList<NameValuePair>();  
		nvpst.add(new BasicNameValuePair("act", "add_quan"));  
		nvpst.add(new BasicNameValuePair("id", "2087427"));  
        postt.setEntity(new UrlEncodedFormEntity(nvpst));  
        
		HttpResponse rspt=client.execute(postt);
		HttpEntity rspEntityt=rspt.getEntity();
		System.out.println("加推结果："+EntityUtils.toString(rspEntityt));
	}
	
	public void getSf() throws ParseException, IOException{
		//登录
		DefaultHttpClient client=new DefaultHttpClient();
		
		HttpPost post=new HttpPost("http://www.dataoke.com/loginApi");
		post.addHeader("Host", "www.dataoke.com");
		post.addHeader("Connection", "keep-alive");
		post.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		post.addHeader("Origin", "http://www.dataoke.com");
		post.addHeader("X-Requested-With", "XMLHttpRequest");
		post.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
		post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		post.addHeader("Referer","http://www.dataoke.com/login");
		post.addHeader("Accept-Encoding", "gzip, deflate");
		post.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
		post.addHeader("Cookie","token=e5313251f8b6aea9a91153e73460d27c; random=2585; dtk_web=vq7jfv4s3e14hfcrv56res6272; UM_distinctid=15aff3c3cfd38b-0530e6dbc-424e002e-15f900-15aff3c3cfe2fa; CNZZDATA1257179126=1565039183-1490337283-http%253A%252F%252Fwww.dataoke.com%252F%7C1490337283;");
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        nvps.add(new BasicNameValuePair("username", "15545587532"));  
        nvps.add(new BasicNameValuePair("password", "mylove1217"));  
        nvps.add(new BasicNameValuePair("vc", ""));  
        nvps.add(new BasicNameValuePair("ref", ""));  
        post.setEntity(new UrlEncodedFormEntity(nvps));  
		HttpResponse rsp=client.execute(post);
		HttpEntity rspEntity=rsp.getEntity();
		System.out.println("登录结果："+EntityUtils.toString(rspEntity));

		String logincookie="";
		List<Cookie> list=client.getCookieStore().getCookies();
		for(Cookie c:list){
			logincookie+=c.getName()+"="+c.getValue()+";";
		}
		
		//获取首发
		HttpPost posts=new HttpPost("http://www.dataoke.com/shoufa");
		posts.addHeader("Host", "www.dataoke.com");
		posts.addHeader("Connection", "keep-alive");
		//posts.addHeader("Accept", "text/plain, */*; q=0.01");
		posts.addHeader("Origin", "http://www.dataoke.com");
		posts.addHeader("X-Requested-With", "XMLHttpRequest");
		posts.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
		posts.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		posts.addHeader("Referer","http://www.dataoke.com/qlist/");
		posts.addHeader("Cookie","UM_distinctid=15aff3c3cfd38b-0530e6dbc-424e002e-15f900-15aff3c3cfe2fa; ASPSESSIONIDAQCTBDSS=NGJOPEGCBEMLCPOINMGOIGKF; ASPSESSIONIDAQBTDATS=KOMIOEGCMHNNDLGMDAGKAGMJ; ASPSESSIONIDCSDQBCTS=CEFEFHGCOFCLFLGIPOOIJAAB; token=e5313251f8b6aea9a91153e73460d27c; random=2585;"+logincookie+"ASPSESSIONIDASCSADST=AKKJKJGCNNBBJHMPNPBBIAKF;CNZZDATA1257179126=1565039183-1490337283-http%253A%252F%252Fwww.dataoke.com%252F%7C1490337283;");
		
		HttpResponse rsps=client.execute(posts);
		HttpEntity rspEntitys=rsps.getEntity();
		System.out.println("首发页面："+EntityUtils.toString(rspEntitys,"utf-8"));
	}
}
