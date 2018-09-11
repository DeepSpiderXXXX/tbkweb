package com.limon.base.taobao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.limon.util.CommonUtil;
import com.limon.util.DateUtil;

public class DataokeAPI {
	public static String appkey="dego2fja4v";
	public static String v="2";
	private static String mysql_url=CommonUtil.getDBConfig("mysql_url");
	private static String mysql_username=CommonUtil.getDBConfig("mysql_username");
	private static String mysql_password=CommonUtil.getDBConfig("mysql_password");
	private static String mysql_driver=CommonUtil.getDBConfig("mysql_driver");
	
	/**
	 * 获取单品详情
	 * @return
	 */
	public static DataokeProduct getDetail(String pid){
		String url="http://api.dataoke.com/index.php?r=port/index&appkey="+appkey+"&v="+v+"&id="+pid;
		DataokeResultOne d=null;
		try{
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(url);
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			//System.out.println(s);
			if(!s.equals("")&&s.indexOf("DOCTYPE HTML PUBLIC")<0){
				d=JSON.parseObject(s,DataokeResultOne.class);
				System.out.println(s);
			}else{
				System.out.println(DateUtil.getTodayTime()+":大淘客联盟查询JSON异常");
			}
		}catch(Exception e){
			e.printStackTrace();
		} 
		if(d!=null){
			return d.getResult();
		}else{
			return null;
		}
		
	}

	/**
	 * top100榜单数据
	 * @return
	 */
	public static List<DataokeProduct> getTop100(){
		String url="http://api.dataoke.com/index.php?r=Port/index&type=top100&appkey="+appkey+"&v="+v;
		List<DataokeProduct> dplist=new ArrayList<DataokeProduct>();
		try{
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(url);
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			DataokeResult d=JSON.parseObject(s,DataokeResult.class);
			dplist=d.getResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return dplist;
	}
	
	/**
	 * 实时跑量榜API接口数据
	 * @return
	 */
	public static List<DataokeProduct> getPaoliang(){
		String url="http://api.dataoke.com/index.php?r=Port/index&type=paoliang&appkey="+appkey+"&v="+v;
		List<DataokeProduct> dplist=new ArrayList<DataokeProduct>();
		try{
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(url);
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			DataokeResult d=JSON.parseObject(s,DataokeResult.class);
			dplist=d.getResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return dplist;
	}
	
	/**
	 * 全站商品接口数据
	 * @return
	 */
	public static List<DataokeProduct> getTotal(Integer page){
		String url="http://api.dataoke.com/index.php?r=Port/index&type=total&appkey="+appkey+"&v="+v+"&page="+page;
		List<DataokeProduct> dplist=new ArrayList<DataokeProduct>();
		try{
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(url);
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			DataokeResult d=JSON.parseObject(s,DataokeResult.class);
			dplist=d.getResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return dplist;
	}
	
	/**
	 * 全站商品接口数据
	 * @return
	 */
	public static DataokeResult getTotalFirst(){
		String url="http://api.dataoke.com/index.php?r=Port/index&type=total&appkey="+appkey+"&v="+v+"&page="+1;
		DataokeResult d=null;
		try{
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(url);
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			System.out.println(s);
			d=JSON.parseObject(s,DataokeResult.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * 已选商品接口数据
	 * @return
	 */
	public static List<DataokeProduct> getSelect(){
		String url="http://api.dataoke.com/index.php?r=goodsLink/www&type=www_quan&appkey="+appkey+"&v="+v+"&page="+1;
		List<DataokeProduct> dplist=new ArrayList<DataokeProduct>();
		try{
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(url);
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			DataokeResult d=JSON.parseObject(s,DataokeResult.class);
			Integer totalpage=getTotalPage(d.getData().getTotal_num(),200);
			dplist=d.getData().getResult();
			if(totalpage>1){
				for(int i=2;i<totalpage+1;i++){
					List<DataokeProduct> list=getSelectPage(i);
					for(DataokeProduct dp:list){
						dplist.add(dp);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return dplist;
	}
	
	/**
	 * 已选商品接口数据
	 * @return
	 */
	public static List<DataokeProduct> getSelectPage(Integer page){
		String url="http://api.dataoke.com/index.php?r=goodsLink/www&type=www_quan&appkey="+appkey+"&v="+v+"&page="+page;
		List<DataokeProduct> dplist=new ArrayList<DataokeProduct>();
		try{
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(url);
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			DataokeResult d=JSON.parseObject(s,DataokeResult.class);
			dplist=d.getData().getResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return dplist;
	}
	
	/**
	 * 二合一转换
	 * @param args
	 */
	public static String getCoupon2to1(String actid,String itemid,String pid){
		String url="https://uland.taobao.com/coupon/edetail?activityId="+actid+"&pid="+pid+"&itemId="+itemid;
		return url;
	}
	
	public static Integer getTotalPage(Integer totalRecord,Integer pageSize) {
		Integer totalPage= (totalRecord + pageSize - 1) / pageSize;
		totalPage= totalPage<=0 ? 1:totalPage;
		return totalPage; 
	}
	
	@SuppressWarnings("deprecation")
	public static String loginDtk(){
		String logincookie=null;
		try{
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
			post.addHeader("Cookie","UM_distinctid=15ac18fb7b6314-0b0e4979285541-576d3c76-100200-15ac18fb7b71a1; not-any-more=1; ASPSESSIONIDCQBTDTCR=CAKONLFAMFFIJOKCCAKFLLNI; ASPSESSIONIDQQARTTDS=LHDODMFAMIMGEGELDAPHBHPG; upe=a830e12d; dtk_web=p1ai6b52s5tt4ail5t4g8cqbl7; ASPSESSIONIDASAQAQAS=IEHJANFAMFCGEEJBHAAAAFGF; ASPSESSIONIDAQBSCSCR=HCMBGPFADBLNAFNMLCDKGIPG; CNZZDATA1257179126=1794127301-1489305497-null%7C1493218305");
			
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
	        nvps.add(new BasicNameValuePair("username", "15545587532"));  
	        nvps.add(new BasicNameValuePair("password", "mylove1217"));  
	        nvps.add(new BasicNameValuePair("vc", ""));  
	        nvps.add(new BasicNameValuePair("ref", ""));  
	        post.setEntity(new UrlEncodedFormEntity(nvps));  
			HttpResponse rsp=client.execute(post);
			HttpEntity rspEntity=rsp.getEntity();
			System.out.println(DateUtil.getTodayTime()+":大淘客登录返回："+EntityUtils.toString(rspEntity));
	
			List<Cookie> list=client.getCookieStore().getCookies();
			for(Cookie c:list){
				logincookie+=c.getName()+"="+c.getValue()+";";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return logincookie;
	}
	
	/**
	 * 加入推广计划
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static String addTg(String id,String logincookie){
		String res="";
		try{
			//加入推广
			DefaultHttpClient client=new DefaultHttpClient();
			
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
			nvpst.add(new BasicNameValuePair("id", id));  
	        postt.setEntity(new UrlEncodedFormEntity(nvpst));  
	        
			HttpResponse rspt=client.execute(postt);
			HttpEntity rspEntityt=rspt.getEntity();
			res=EntityUtils.toString(rspEntityt);
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 获取失效商品
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static List<String> getPass(String logincookie){
		List<String> idlist=new ArrayList<String>();
		try{
			//加入推广
			DefaultHttpClient client=new DefaultHttpClient();
			
			HttpGet get=new HttpGet("http://www.dataoke.com/ucenter/favorites_quan.asp?type1=pass");
			get.addHeader("Host", "www.dataoke.com");
			get.addHeader("Connection", "keep-alive");
			get.addHeader("Accept", "text/plain, */*; q=0.01");
			get.addHeader("Origin", "http://www.dataoke.com");
			get.addHeader("X-Requested-With", "XMLHttpRequest");
			get.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			get.addHeader("Referer","http://www.dataoke.com/qlist/");
			get.addHeader("Cookie","UM_distinctid=15aff3c3cfd38b-0530e6dbc-424e002e-15f900-15aff3c3cfe2fa; ASPSESSIONIDAQCTBDSS=NGJOPEGCBEMLCPOINMGOIGKF; ASPSESSIONIDAQBTDATS=KOMIOEGCMHNNDLGMDAGKAGMJ; ASPSESSIONIDCSDQBCTS=CEFEFHGCOFCLFLGIPOOIJAAB; token=e5313251f8b6aea9a91153e73460d27c; random=2585;"+logincookie+"ASPSESSIONIDASCSADST=AKKJKJGCNNBBJHMPNPBBIAKF;CNZZDATA1257179126=1565039183-1490337283-http%253A%252F%252Fwww.dataoke.com%252F%7C1490337283;");
			
			HttpResponse rspt=client.execute(get);
			HttpEntity rspEntityt=rspt.getEntity();
			String res=EntityUtils.toString(rspEntityt);
			//System.out.println(res);
			String regex = "<a.+?href=\"(.+?)\".*>(.+)</a>";
			Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
			Matcher ma = pa.matcher(res);
		    while (ma.find()){
		      if(ma.group().indexOf("quan_title")!=-1){
		    	  String s=ma.group();
		    	  s=s.substring(s.indexOf("item"),s.indexOf("target"));
		    	  s=s.replace("item?id=", "").replace("\"","");
		    	  idlist.add(s);
		      }
		    }
		    //一键删除大淘客商品
		    delPass(logincookie);
		}catch(Exception e){
			e.printStackTrace();
		}
		return idlist;
	}
	
	/**
	 * 一键清空失效商品
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static String delPass(String logincookie){
		String res="";
		try{
			//加入推广
			DefaultHttpClient client=new DefaultHttpClient();
			
			HttpGet get=new HttpGet("http://www.dataoke.com/ucenter/all_del_quan.asp?act=del&leibie=2&zh_que_bt=%C8%B7%C8%CF%C9%BE%B3%FD");
			get.addHeader("Host", "www.dataoke.com");
			get.addHeader("Connection", "keep-alive");
			get.addHeader("Accept", "text/plain, */*; q=0.01");
			get.addHeader("Origin", "http://www.dataoke.com");
			get.addHeader("X-Requested-With", "XMLHttpRequest");
			get.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			get.addHeader("Referer","http://www.dataoke.com/qlist/");
			get.addHeader("Cookie","UM_distinctid=15aff3c3cfd38b-0530e6dbc-424e002e-15f900-15aff3c3cfe2fa; ASPSESSIONIDAQCTBDSS=NGJOPEGCBEMLCPOINMGOIGKF; ASPSESSIONIDAQBTDATS=KOMIOEGCMHNNDLGMDAGKAGMJ; ASPSESSIONIDCSDQBCTS=CEFEFHGCOFCLFLGIPOOIJAAB; token=e5313251f8b6aea9a91153e73460d27c; random=2585;"+logincookie+"ASPSESSIONIDASCSADST=AKKJKJGCNNBBJHMPNPBBIAKF;CNZZDATA1257179126=1565039183-1490337283-http%253A%252F%252Fwww.dataoke.com%252F%7C1490337283;");
			
			HttpResponse rspt=client.execute(get);
			HttpEntity rspEntityt=rspt.getEntity();
			res=EntityUtils.toString(rspEntityt,"gbk");
			System.out.println(DateUtil.getTodayTime()+":一键清空失效商品成功");
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 一键申请高佣
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static boolean getDataokeGy(String dtkid,String gid,String pid,String quanid,String dx,String logincookie){
		boolean rs=false;
		try{
			//加入推广
			DefaultHttpClient client=new DefaultHttpClient();
			
			HttpPost postt=new HttpPost("http://zhushou.dataoke.com/ext/index.php?r=api/goods/NewSaveLink");
			postt.addHeader("Host", "zhushou.dataoke.com");
			postt.addHeader("RSrc", "dtkhelper");
			postt.addHeader("Accept", "text/plain, */*; q=0.01");
			postt.addHeader("Origin", "http://www.dataoke.com");
			postt.addHeader("X-Requested-With", "XMLHttpRequest");
			postt.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			postt.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			postt.addHeader("Referer","http://www.dataoke.com/qlist/");
			postt.addHeader("Cookie","UM_distinctid=15aff3c3cfd38b-0530e6dbc-424e002e-15f900-15aff3c3cfe2fa; ASPSESSIONIDAQCTBDSS=NGJOPEGCBEMLCPOINMGOIGKF; ASPSESSIONIDAQBTDATS=KOMIOEGCMHNNDLGMDAGKAGMJ; ASPSESSIONIDCSDQBCTS=CEFEFHGCOFCLFLGIPOOIJAAB; token=e5313251f8b6aea9a91153e73460d27c; random=2585;"+logincookie+"ASPSESSIONIDASCSADST=AKKJKJGCNNBBJHMPNPBBIAKF;CNZZDATA1257179126=1565039183-1490337283-http%253A%252F%252Fwww.dataoke.com%252F%7C1490337283;");
			
			List<NameValuePair> nvpst = new ArrayList<NameValuePair>();  
			nvpst.add(new BasicNameValuePair("type", "2"));  
			nvpst.add(new BasicNameValuePair("uid", "504663"));  
			nvpst.add(new BasicNameValuePair("gid", dtkid));  
			nvpst.add(new BasicNameValuePair("link", ""));  
			nvpst.add(new BasicNameValuePair("code", "7999ab42"));  
			nvpst.add(new BasicNameValuePair("goodsid", gid));  
			nvpst.add(new BasicNameValuePair("pid", pid));  
			nvpst.add(new BasicNameValuePair("quan_id", quanid));  
			nvpst.add(new BasicNameValuePair("dx", dx));  
	        postt.setEntity(new UrlEncodedFormEntity(nvpst));  
	        
			HttpResponse rspt=client.execute(postt);
			HttpEntity rspEntityt=rspt.getEntity();
			String res=EntityUtils.toString(rspEntityt,"gbk");
			if(res.indexOf("SUCCESS")!=-1){
				rs=true;
			}
			//System.out.println(DateUtil.getTodayTime()+":申请淘客高佣结果="+res);
			Thread.sleep(500);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	@SuppressWarnings("deprecation")
	public static String getUrlByTkl(String tkl){
		String tokenstr="";
		try{
			/**
			 * 我的三个接口：
				http://www.ynina.com/pro/api/tkl?tkl=￥Lsba0gaIzBH￥
				http://www.ynina.com/pro/api/tklToUrl?tkl=￥Lsba0gaIzBH￥
				http://www.ynina.com/pro/api/tklToItemId?tkl=￥Lsba0gaIzBH￥
			 */
			DefaultHttpClient client=new DefaultHttpClient();
			HttpPost post=new HttpPost("http://www.taokouling.com/index.php?m=api&a=taokoulingjm");
			List<NameValuePair> nvpst = new ArrayList<NameValuePair>();  
			nvpst.add(new BasicNameValuePair("username", "ccron"));  
			nvpst.add(new BasicNameValuePair("password", "mylove1217"));  
			nvpst.add(new BasicNameValuePair("text", tkl.replaceAll("￥",""))); 
	        post.setEntity(new UrlEncodedFormEntity(nvpst));  
			HttpResponse rsp=client.execute(post);
			String s=EntityUtils.toString(rsp.getEntity());
			TbkBean tb=JSON.parseObject(s, TbkBean.class);
			tokenstr=tb.getUrl();
		}catch(Exception e){
			e.printStackTrace();
		}
		return tokenstr;
	}
	
	public static String getUrlByTklApi(String tkl){
		String itemid="0";
		String apiurl="http://www.ynina.com/pro/api/tklToItemId?tkl="+tkl;
		try{
			/**
			 * 我的三个接口：
				http://www.ynina.com/pro/api/tkl?tkl=￥Lsba0gaIzBH￥
				http://www.ynina.com/pro/api/tklToUrl?tkl=￥Lsba0gaIzBH￥
				http://www.ynina.com/pro/api/tklToItemId?tkl=￥Lsba0gaIzBH￥
			 */
			DefaultHttpClient client=new DefaultHttpClient();
			HttpGet get=new HttpGet(apiurl);
			HttpResponse rsp=client.execute(get);
			String s=EntityUtils.toString(rsp.getEntity());
			JSONObject jsonObj = JSON.parseObject(s);  
			itemid=jsonObj.getString("itemId");
		}catch(Exception e){
			e.printStackTrace();
		}
		return itemid;
	}
	
	public static String getdtkcookie(){
		String cookie="";
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="select * from tbk_config where ckey='dtkcookie'";
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()) { // 将查询到的数据打印出来
            	cookie=rs.getString("cvalue");
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return cookie;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String tburl="【我剁手都要买的宝贝（欧式防滑四季通用沙发垫布艺简约现代夏季沙发罩沙发套全包沙发巾），快来和我一起瓜分红包】，复制这条信息￥S02N0105cP3￥后打开[表情]手机淘宝[表情]";
		tburl=tburl.substring(tburl.indexOf("￥")+1,tburl.lastIndexOf("￥"));
		System.out.println(tburl);
		String s=getUrlByTkl(tburl);
		System.out.println(s);
	}
}
