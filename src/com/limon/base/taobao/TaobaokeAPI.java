package com.limon.base.taobao;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSON;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.JavaScriptPage;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.limon.base.model.TbkProduct;
import com.limon.base.model.TbkType;
import com.limon.util.CommonUtil;
import com.limon.util.DateUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.request.TbkItemRecommendGetRequest;
import com.taobao.api.request.TbkUatmFavoritesGetRequest;
import com.taobao.api.request.TbkUatmFavoritesItemGetRequest;
import com.taobao.api.request.WirelessShareTpwdCreateRequest;
import com.taobao.api.request.TbkTpwdCreateRequest;
import com.taobao.api.request.WirelessShareTpwdCreateRequest.GenPwdIsvParamDto;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkItemRecommendGetResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import com.taobao.api.response.TbkUatmFavoritesGetResponse;
import com.taobao.api.response.TbkUatmFavoritesItemGetResponse;
import com.taobao.api.response.WirelessShareTpwdCreateResponse;

public class TaobaokeAPI{
	private static String mysql_url=CommonUtil.getDBConfig("mysql_url");
	private static String mysql_username=CommonUtil.getDBConfig("mysql_username");
	private static String mysql_password=CommonUtil.getDBConfig("mysql_password");
	private static String mysql_driver=CommonUtil.getDBConfig("mysql_driver");
	private static String mypid="mm_65772869_40086169_151064267";
	private static DecimalFormat df = CommonUtil.getDoubleFormat();
	/**
	 * 获取淘宝联盟选品库分类
	 * @return
	 */
	public static TbkUatmType getTbkUatmType() {
		DefaultTaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",CommonUtil.getConfig("taobao_appkey"),CommonUtil.getConfig("taobao_appsecret"));
		TbkUatmFavoritesGetRequest req = new TbkUatmFavoritesGetRequest();
		req.setPageNo(1L);
		req.setPageSize(50L);
		req.setFields("favorites_title,favorites_id,type");
		req.setType(-1L);
		TbkUatmFavoritesGetResponse rsp;
		TbkUatmType tut=new TbkUatmType();
		try {
			rsp = client.execute(req);
			if(rsp.isSuccess()){
				tut.setTotal_num(rsp.getTotalResults());
				tut.setTypelist(rsp.getResults());
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return tut;
	}
	
	/**
	 * 获取淘宝联盟选品库商品详情
	 * @return
	 */
	public static TbkUatmInfo getTbkUatmInfo(long typeid) {
		DefaultTaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",CommonUtil.getConfig("taobao_appkey"),CommonUtil.getConfig("taobao_appsecret"));
		TbkUatmFavoritesItemGetRequest req = new TbkUatmFavoritesItemGetRequest();
		req.setPlatform(1L);
		req.setPageSize(100L);
		req.setAdzoneId(72742703L);
		req.setUnid("3456");
		req.setFavoritesId(typeid);
		req.setPageNo(1L);
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,click_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type");
		TbkUatmFavoritesItemGetResponse rsp;
		TbkUatmInfo tui=new TbkUatmInfo();
		try {
			rsp = client.execute(req);
			if(rsp.isSuccess()){
				tui.setTotal_num(rsp.getTotalResults());
				tui.setInfolist(rsp.getResults());
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return tui;
	}
	
	/**
	 * 生成淘口令
	 * @return
	 * @throws ApiException 
	 */
	public static String getTkl(String url,String text,String logo) {
		String tkl="";
		DefaultTaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",CommonUtil.getConfig("taobao_appkey"),CommonUtil.getConfig("taobao_appsecret"));
		TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
		req.setUserId("71032302");
		req.setText(text);
		req.setUrl(url);
		req.setLogo(logo);
		req.setExt("{}");
		TbkTpwdCreateResponse rsp;
		try {
			rsp = client.execute(req);
			if(rsp.isSuccess()){
				tkl=rsp.getData().getModel();
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return tkl;
	}
	/**
	 * 生成淘口令
	 * @return
	 * @throws ApiException 
	 */
	public static String getTkl2(String url,String text,String logo) {
		String tkl="";
		DefaultTaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",CommonUtil.getConfig("taobao_appkey"),CommonUtil.getConfig("taobao_appsecret"));
		WirelessShareTpwdCreateRequest req = new WirelessShareTpwdCreateRequest();
		GenPwdIsvParamDto obj1 = new GenPwdIsvParamDto();
		obj1.setExt("{}");
		obj1.setLogo(logo);
		obj1.setUrl(url);
		obj1.setText(text);
		obj1.setUserId(71032302L);
		req.setTpwdParam(obj1);
		WirelessShareTpwdCreateResponse rsp =null;
		try {
			rsp = client.execute(req);
			//System.out.println(rsp.getBody());
			if(rsp.isSuccess()){
				tkl=rsp.getModel();
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return tkl;
	}

	/**
	 * 获取淘宝客商品
	 * @return
	 */
	public static TbkItem getTbkItemInfo(String searchkey,long pageno,long pagesize) {
		DefaultTaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",CommonUtil.getConfig("taobao_appkey"),CommonUtil.getConfig("taobao_appsecret"));
		TbkItemGetRequest req = new TbkItemGetRequest();
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,click_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type");
		req.setQ(searchkey);
		req.setSort("tk_total_sales_desc");
		req.setEndTkRate(1000l);
		req.setPageNo(pageno);
		req.setPageSize(pagesize);
		
		TbkItemGetResponse rsp;
		TbkItem ti=new TbkItem();
		try {
			rsp = client.execute(req);
			if(rsp.isSuccess()){
				ti.setTotal_num(rsp.getTotalResults());
				ti.setInfolist(rsp.getResults());
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return ti;
	}
	
	/**
	 * 获取淘宝客关联商品
	 * @return
	 */
	public static TbkItemRecommend getTbkItemRecommendInfo(long numIid,long count) {
		DefaultTaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest",CommonUtil.getConfig("taobao_appkey"),CommonUtil.getConfig("taobao_appsecret"));
		TbkItemRecommendGetRequest req = new TbkItemRecommendGetRequest();
		req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,click_url,seller_id,volume,nick,shop_title,zk_final_price_wap,event_start_time,event_end_time,tk_rate,status,type");
		req.setNumIid(numIid);
		req.setCount(count);
		
		TbkItemRecommendGetResponse rsp;
		TbkItemRecommend ti=new TbkItemRecommend();
		try {
			rsp = client.execute(req);
			if(rsp.isSuccess()){
				ti.setTotal_num(count);
				ti.setInfolist(rsp.getResults());
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return ti;
	}
	
	public static String getTypeByDtypeid(Integer cid){
		String type="";
		switch (cid){
			case 1:
				type="女装";
				break;
			case 9:
				type="男装";
				break;
			case 10:
				type="内衣";
				break;	
			case 2:
				type="母婴";
				break;
			case 3:
				type="化妆品";
				break;
			case 4:
				type="居家";
				break;
			case 5:
				type="鞋包配饰";
				break;
			case 6:
				type="美食";
				break;
			case 7:
				type="文体车品";
				break;
			case 8:
				type="数码家电";
				break;
			default:
				type="";
		}
		return type;
	}
	
	public static String getTbkUrl(String url){
		try {
			/***********htmlunit设置****************/
			java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
		    java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
			WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);//设置浏览器的User-Agent
        	webClient.getOptions().setRedirectEnabled(true);
        	webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS'
        	webClient.getOptions().setUseInsecureSSL(true);//启用SSL
        	webClient.getOptions().setCssEnabled(false);//是否启用CSS
        	webClient.getOptions().setThrowExceptionOnScriptError(false);
        	webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常
        	webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			/*****************************************/
        	if(!url.startsWith("http")){
				if(url.indexOf("http")<url.indexOf("复制")){
					url=url.substring(url.indexOf("http"),url.indexOf("复制"));
				}else{
					url=url.replaceAll("[\u4e00-\u9fa5]", "").replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|【.*?】|￥.*?￥", "");
				}
				url=url.replaceAll("[\u4e00-\u9fa5]", "").replaceAll(",","").replaceAll("，","").replaceAll(";","").replaceAll("；","").replaceAll("-","").trim();
			}
        	System.out.println(DateUtil.getTodayTime()+":URL获取"+url);
        	
			if(url!=null&&!url.equals("")&&url.indexOf("s.click.taobao.com")<0){
				if(url.indexOf("item.taobao.com/item.htm")!=-1||url.indexOf("detail.tmall.com/item.htm")!=-1){
					url=filterItemTaobao(url);
					System.out.println(DateUtil.getTodayTime()+":第一步item.taobao.com处理"+url);	
				}else{
					//使用FireFox读取网页
			        HttpClient client = new HttpClient();
			        GetMethod g=new GetMethod(url);
			        client.executeMethod(g);
			        url=g.getResponseBodyAsString();
					//System.out.println(url);
			        String[] ss=url.split(";");
			        for(String t:ss){
						if(t.indexOf("目标地址")!=-1){
							Pattern p = Pattern.compile("'.*?'");
					        Matcher m = p.matcher(t);
					        if(m.find()) {
					            url=m.group(0).substring(1,m.group(0).length()-1);            
					        } 
						}
					}
			        
			        System.out.println(DateUtil.getTodayTime()+":第一步c.b0yp.com处理"+url);
			        
			        if(url.indexOf("item.taobao.com/item.htm")!=-1||url.indexOf("detail.tmall.com/item.htm")!=-1){
			        	url=filterItemTaobao(url);
				        System.out.println(DateUtil.getTodayTime()+":第二步item.taobao.com处理"+url);
			        }else if(url.indexOf("m.taobao.com")!=-1){
			        	url=url.substring(url.indexOf("m.taobao.com/i"),url.indexOf(".htm?"));
			        	url="http://item.taobao.com/item.htm?id="+url.replace("m.taobao.com/i","").replace(".htm?","");
						System.out.println(DateUtil.getTodayTime()+":第二步m.taobao.com处理"+url);
			        }else if(url.indexOf("s.click.taobao.com")!=-1){
				        //打开s.click.taobao.com
			        	HttpClient sclient=new HttpClient();
			        	GetMethod sg=new GetMethod(url);
				        sclient.executeMethod(sg);
				        String s=sg.getResponseBodyAsString();
				        //System.out.println(s);
				        Pattern p = Pattern.compile("URL=.*?\"");
						Matcher m = p.matcher(s);
						if(m.find()) {
							url=m.group(0).substring(4,m.group(0).length()-1);            
					    } 
						System.out.println(DateUtil.getTodayTime()+":第二步s.click.taobao.com处理"+url);
						
						HttpClient dclient=new HttpClient();
						GetMethod dg=new GetMethod(url);
				        dclient.executeMethod(dg);
				        String ds=dg.getResponseBodyAsString();
				        //System.out.println(ds);
				        Pattern dp = Pattern.compile("itemId=.*?(\"|&)");
						Matcher dm = dp.matcher(ds);
						if(dm.find()) {
							url=dm.group(0).substring(7,dm.group(0).length()-1);           
					    } 
						url="http://item.taobao.com/item.htm?id="+url;
						System.out.println(DateUtil.getTodayTime()+":第三步s.click.taobao.com处理"+url);	
			        }else if(url.indexOf("uland.taobao.com")!=-1){
			        	//打开uland.taobao.com
			        	HtmlPage spage=webClient.getPage(url);
			        	HtmlAnchor anchor = (HtmlAnchor) spage.getByXPath("//*[@class=\"item-detail\"]").get(0);  
				    	spage = anchor.click();
				    	
			        	url=spage.getUrl().toString();
			        	url=filterItemTaobao(url);
						System.out.println(DateUtil.getTodayTime()+":第三步uland.taobao.com处理"+url);
			        }
			        System.out.println(DateUtil.getTodayTime()+":最终输出："+url);
				}
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url.trim();
	}
	
	/**
	 * 处理item.taobao.com链接
	 */
	public static String filterItemTaobao(String url){
		if(url.indexOf("item.taobao.com/item.htm")!=-1||url.indexOf("detail.tmall.com/item.htm")!=-1){
			if(url.indexOf("?id=")!=-1){
				String[] ss=url.split("&");
				for(String s:ss){
					if(s.indexOf("?id=")!=-1){
						url=s;
					}
				}
			}else if(url.indexOf("&id=")!=-1){
				String[] ss=url.split("&");
				for(String s:ss){
					String[] ll=s.split("=");
					if(ll.length==2&&ll[0].equals("id")){
						url="http://item.taobao.com/item.htm?"+s;
					}
				}
			}
		}
		return url;
	}
	
	public static List<CouponData> getCouponByUrl(String url){
		List<CouponData> coupons=new ArrayList<CouponData>();
		try{
			url=url.replace("detail.tmall.com","item.taobao.com");
			String item=url.split("\\?")[1];
			
			String apiurl="http://zhushou3.taokezhushou.com/api/v1/coupons_base/2087940950?item_"+item;
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			get.setRequestHeader("Connection","no-cache");
			get.setRequestHeader("Accept","*/*");
			get.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			CouponRes rs=JSON.parseObject(s,CouponRes.class);
			
			coupons=rs.getData();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return coupons;
	}
	
	public static TkzsCouponData getCouponBytkzsUrl(String url){
		TkzsCouponData cd=null;
		try{
			url=url.replace("detail.tmall.com","item.taobao.com");
			String item=url.split("\\?")[1].replace("id=","");
			
			String apiurl="http://www.taokezhushou.com/detail/"+item;
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			get.setRequestHeader("Connection","no-cache");
			get.setRequestHeader("Accept","*/*");
			get.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			//System.out.println(s);
			if(s.indexOf("var info =")!=-1&&s.indexOf("var detailhot =")!=-1){
				s=s.substring(s.indexOf("var info ="),s.indexOf("var detailhot ="));
				s=s.replace("var info =", "").replace(";","").trim();
				//System.out.println(s);
				cd=JSON.parseObject(s,TkzsCouponData.class);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return cd;
	}
	
	public static TlmData getTklByTblm(String url,String pid,String cookie){
		TlmData td=null;
		try{
			String[] ps=pid.split("_");
			String[] ts=cookie.split(";");
			String tbtoken="";
			for(String t:ts){
				if(t.indexOf("_tb_token_")!=-1){
					tbtoken=t.split("=")[1];
				}
			}
			String apiurl="http://pub.alimama.com/urltrans/urltrans.json?siteid="+ps[2]+"&adzoneid="+ps[3]+"&promotionURL="+url+"&t="+new Date().getTime()+"&pvid=&_tb_token_="+tbtoken+"&_input_charset=utf-8";
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			get.setRequestHeader("Connection","keep-alive");
			get.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			get.setRequestHeader("Upgrade-Insecure-Requests","1");
			get.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.setRequestHeader("Cookie", cookie);
			
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			//System.out.println(s);
			TlmInfo ti=JSON.parseObject(s,TlmInfo.class);
			if(ti!=null){
				td=ti.getData();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return td;
	}
	
	/**
	 * 在淘宝联盟中搜索商品
	 */
	public static TlmNumId searchfromtblm(String url,String cookie){
		TlmNumId tni=null;
		Integer num=0;
		String id="";
		String imgurl="";
		String pname="";
		String price="";
		String sprice="";
		String cmount="0";
		String tkrate="";
		String nick="";
		String couponInfo="";
		Integer includeDxjh=0;
		try{
			String[] ts=cookie.split(";");
			String tbtoken="";
			for(String t:ts){
				if(t.indexOf("_tb_token_")!=-1){
					tbtoken=t.split("=")[1];
				}
			}

			String apiurl="http://pub.alimama.com/items/search.json?q="+url+"&_t="+new Date().getTime()+"&auctionTag=&perPageSize=40&shopTag=&t="+new Date().getTime()+"&_tb_token_="+tbtoken+"&pvid=";
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			get.setRequestHeader("Connection","keep-alive");
			get.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			get.setRequestHeader("Upgrade-Insecure-Requests","1");
			get.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.setRequestHeader("Cookie", new Date().getTime()+"");
			
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			System.out.println(DateUtil.getTodayTime()+":淘宝联盟搜索"+s);
			TlmCouponInfo ti=JSON.parseObject(s,TlmCouponInfo.class);
			if(ti!=null&&ti.getData()!=null&&ti.getData().getPaginator()!=null&&ti.getData().getPaginator().getLength()!=null){
				num=ti.getData().getPaginator().getLength();
				if(num>0&&ti.getData().getPageList()!=null&&ti.getData().getPageList().size()>0){
					id=ti.getData().getPageList().get(0).getAuctionId();
					imgurl=ti.getData().getPageList().get(0).getPictUrl();
					pname=ti.getData().getPageList().get(0).getTitle();
					price=ti.getData().getPageList().get(0).getZkPrice();
					tkrate=ti.getData().getPageList().get(0).getTkRate();
					cmount=ti.getData().getPageList().get(0).getCouponAmount();
					nick=ti.getData().getPageList().get(0).getNick();
					
					sprice=df.format(Double.parseDouble(price)-Double.parseDouble(cmount));
					includeDxjh=ti.getData().getPageList().get(0).getIncludeDxjh();
					couponInfo=ti.getData().getPageList().get(0).getCouponInfo();

					tni=new TlmNumId();
					tni.setNum(num);
					tni.setAid(id);
					tni.setImgurl(imgurl);
					tni.setPname(pname);
					tni.setPrice(price);
					tni.setCamount(cmount);
					tni.setSprice(sprice);
					tni.setTkrate(tkrate);
					tni.setNick(nick);
					tni.setCouponInfo(couponInfo);
					tni.setIncludeDxjh(includeDxjh);
				}
			}else{
				System.out.println(DateUtil.getTodayTime()+":淘宝联盟查询结果为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tni;
	}
	
	/**
	 * 在淘宝联盟中搜索商品
	 */
	public static List<TbkProduct> searchGoodsfromtblm(String skey,Integer page,String cookie){
		List<TbkProduct> list=new ArrayList<TbkProduct>();
		try{
			String[] ts=cookie.split(";");
			String tbtoken="";
			for(String t:ts){
				if(t.indexOf("_tb_token_")!=-1){
					tbtoken=t.split("=")[1];
				}
			}

			String apiurl="http://pub.alimama.com/items/search.json?q="+URLEncoder.encode(skey,"utf-8")+"&_t="+new Date().getTime()+"&toPage="+page+"&dpyhq=1&sortType=9&auctionTag=&perPageSize=12&shopTag=yxjh%2Cdpyhq&t="+new Date().getTime()+"&_tb_token_="+tbtoken+"&pvid=";
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			get.setRequestHeader("Connection","keep-alive");
			get.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			get.setRequestHeader("Upgrade-Insecure-Requests","1");
			get.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.setRequestHeader("Cookie", cookie);
			
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			System.out.println(DateUtil.getTodayTime()+":淘宝联盟搜索"+s);
			TlmCouponInfo ti=JSON.parseObject(s,TlmCouponInfo.class);
			if(ti!=null&&ti.getData()!=null&&ti.getData().getPaginator()!=null&&ti.getData().getPaginator().getLength()!=null){
				Integer num=ti.getData().getPaginator().getLength();
				Integer items=ti.getData().getPaginator().getItems();
				if(num>0&&ti.getData().getPageList()!=null&&ti.getData().getPageList().size()>0){
					for(TlmCouponPage p:ti.getData().getPageList()){
						/*
						List<Double> d=new ArrayList<Double>();
						//通用佣金
						d.add(Double.parseDouble(p.getTkRate()));
						for (Map.Entry<String, Double> m :p.getTkSpecialCampaignIdRateMap().entrySet())  {  
				            //计划佣金
							d.add(m.getValue());
				        }
						
						Collections.sort(d, new Comparator<Double>() {
							@Override
							public int compare(Double f1, Double f2) {
								if(f1>f2) {
									return -1;
								}
								return 1;
							}
						});
						*/
						TbkProduct tp=new TbkProduct();
						tp.setPid(Long.parseLong(p.getAuctionId()));
						tp.setImgurl(p.getPictUrl());
						tp.setPname(p.getTitle().replaceAll("<span class=H>","").replaceAll("</span>", ""));
						tp.setPrice(Double.parseDouble(p.getZkPrice()));
						tp.setSname(p.getNick());
						tp.setSprice(Double.parseDouble(p.getZkPrice())-Double.parseDouble(p.getCouponAmount()));
						tp.setSrbl(Double.parseDouble(p.getTkRate()));
						tp.setCprice(Double.parseDouble(p.getCouponAmount()));
						tp.setSalenum(p.getBiz30day());
						tp.setItems(items);
						list.add(tp);
					}
				}
			}else{
				System.out.println(DateUtil.getTodayTime()+":淘宝联盟查询结果为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	public static String getJoindCamp(String shopww,String cookie){
		String pubcampid=null;
		try{
			String[] ts=cookie.split(";");
			String tbtoken="";
			for(String t:ts){
				if(t.indexOf("_tb_token_")!=-1){
					tbtoken=t.split("=")[1];
				}
			}
			String apiurl="http://pub.alimama.com/campaign/joinedCampaigns.json?toPage=1&nickname="+URLEncoder.encode(shopww)+"&perPageSize=40&t="+new Date().getTime()+"&pvid=&_tb_token_="+tbtoken+"&_input_charset=utf-8";
			//System.out.println(apiurl);
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			get.setRequestHeader("Connection","keep-alive");
			get.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			get.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.setRequestHeader("Cookie", cookie);
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			//System.out.println(DateUtil.getTodayTime()+":获取计划列表"+s);
			TlmCouponInfo ti=JSON.parseObject(s,TlmCouponInfo.class);
			if(ti!=null&&ti.getData()!=null&ti.getData().getPageList()!=null&&ti.getData().getPageList().size()>0){
				pubcampid=ti.getData().getPageList().get(0).getId();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return pubcampid;
	}

	public static TlmData exitCamp(String shopcampid,String cookie){
		TlmData td=null;
		try{
			String[] ts=cookie.split(";");
			String tbtoken="";
			for(String t:ts){
				if(t.indexOf("_tb_token_")!=-1){
					tbtoken=t.split("=")[1];
				}
			}
			String apiurl="http://pub.alimama.com/campaign/exitCampaign.json";
			//System.out.println(apiurl);
			HttpClient sclient=new HttpClient();
			PostMethod post = new PostMethod(apiurl); 
			post.setRequestHeader("Connection","keep-alive");
			post.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			post.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			post.setRequestHeader("Cookie", cookie);
		
			// 填入各个表单域的值  
			NameValuePair[] data = {  
				   new NameValuePair("pubCampaignid", shopcampid),  
				   new NameValuePair("_tb_token_", tbtoken),  
				   new NameValuePair("t", new Date().getTime()+"")};  
			// 将表单的值放入postMethod中  
			post.setRequestBody(data);
			
			sclient.executeMethod(post);
			String rs=post.getResponseBodyAsString();
			//System.out.println(rs);
		}catch(Exception e){
			e.printStackTrace();
		}
		return td;
	}
	
	public static Integer joinCamp(String campid,String keepid,String cookie){
		Integer rs=0;
		try{
			String[] ts=cookie.split(";");
			String tbtoken="";
			for(String t:ts){
				if(t.indexOf("_tb_token_")!=-1){
					tbtoken=t.split("=")[1];
				}
			}
			String sapiurl="http://pub.alimama.com/pubauc/applyForCommonCampaign.json";
			//System.out.println(apiurl);
			HttpClient sclient=new HttpClient();
			PostMethod post = new PostMethod(sapiurl); 
			post.setRequestHeader("Connection","keep-alive");
			post.setRequestHeader("Accept","application/json, text/javascript, */*; q=0.01");
			post.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			post.setRequestHeader("Cookie", cookie);
			post.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			post.setRequestHeader("Referer","http://pub.alimama.com/promo/search/index.htm?spm=a219t.7900221/1.1998910419.de727cf05.JDub46&toPage=1&queryType=2");
		
	        // 填入各个表单域的值  
	        NameValuePair[] data = {  
	               new NameValuePair("campId", campid),  
	               new NameValuePair("keeperid", keepid),  
	               new NameValuePair("applyreason", "大淘客联盟申请计划，万人齐推，长期合作，请速过！"),  
	               new NameValuePair("_tb_token_", tbtoken),  
	               new NameValuePair("t", new Date().getTime()+""),
	               new NameValuePair("pvid","")
	        };  
	        // 将表单的值放入postMethod中  
	        post.setRequestBody(data);
			
			sclient.executeMethod(post);
			String s=post.getResponseBodyAsString();
			if(s.indexOf("\"ok\":true")!=-1||s.indexOf("您已经在申请该计划或您已经申请过该掌柜计划")!=-1){
				rs=1;
			}else{
				System.out.println(s);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 *立即推广获取连接
	 */
	public static TlmData gettblmurl(String id,String pid,String cookie){
		TlmData td=null;
		try{
			String[] ps=pid.split("_");
			String[] ts=cookie.split(";");
			String tbtoken="";
			for(String t:ts){
				if(t.indexOf("_tb_token_")!=-1){
					tbtoken=t.split("=")[1];
				}
			}
			String apiurl="http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="+id+"&adzoneid="+ps[3]+"&siteid="+ps[2]+"&scenes=1&t="+new Date().getTime()+"&_tb_token_="+tbtoken+"&pvid=";
			//System.out.println(apiurl);
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			get.setRequestHeader("Connection","keep-alive");
			get.setRequestHeader("Accept","application/json, text/javascript, */*; q=0.01");
			get.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.setRequestHeader("Cookie", cookie);
			get.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			get.setRequestHeader("Referer","http://pub.alimama.com/promo/search/index.htm?spm=a219t.7900221/1.1998910419.de727cf05.JDub46&toPage=1&queryType=2");
			
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			System.out.println(s);
			TlmInfo ti=null;
			if(s.indexOf("<!doctype html>")<0&&s.indexOf("<!DOCTYPE html>")<0){
				ti=JSON.parseObject(s,TlmInfo.class);
			}else{
				System.out.println(DateUtil.getTodayTime()+":获取推广JSON异常");
			}
			if(ti!=null&&ti.getData()!=null){
				td=ti.getData();
				System.out.println(DateUtil.getTodayTime()+":获取推广链接成功");
			}else{
				System.out.println(DateUtil.getTodayTime()+":获取推广链接失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return td;
	}

	/**
	 * 转换鹊桥链接
	 */
	 
	public static TlmData getqqhdurl(String id,String pid,String cookie){
		TlmData td=null;
		try{
			String[] ps=pid.split("_");
			String[] ts=cookie.split(";");
			String tbtoken="";
			for(String t:ts){
				if(t.indexOf("_tb_token_")!=-1){
					tbtoken=t.split("=")[1];
				}
			}
			String apiurl="http://pub.alimama.com/common/code/getAuctionCode.json?auctionid="+id+"&adzoneid="+ps[3]+"&siteid="+ps[2]+"&scenes=3&channel=tk_qqhd&t="+new Date().getTime()+"&_tb_token_="+tbtoken+"&pvid=";
			//System.out.println(apiurl);
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			get.setRequestHeader("Connection","keep-alive");
			get.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			get.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.setRequestHeader("Cookie", cookie);
			get.setRequestHeader("Referer","http://pub.alimama.com/promo/search/index.htm?spm=a219t.7900221/1.1998910419.de727cf05.JDub46&toPage=1&queryType=2");
			
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			TlmInfo ti=null;
			if(s.indexOf("<!doctype html>")<0&&s.indexOf("<!DOCTYPE html>")<0){
				System.out.println("鹊桥链接："+s);
				ti=JSON.parseObject(s,TlmInfo.class);
			}else{
				System.out.println(DateUtil.getTodayTime()+":pid="+id+",获取鹊桥JSON异常");
			}
			if(ti!=null&&ti.getData()!=null){
				td=ti.getData();
				System.out.println(DateUtil.getTodayTime()+":pid="+id+",获取鹊桥链接成功");
			}else{
				System.out.println(DateUtil.getTodayTime()+":pid="+id+",获取鹊桥链接失败");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return td;
	}
	
	/**
	 * 在淘宝联盟中搜索商品
	 */
	public static TlmNumId searchyxjhtblm(String url,String cookie){
		TlmNumId tni=null;
		Integer num=0;
		String id="";
		String imgurl="";
		String pname="";
		String price="";
		String sprice="";
		String cmount="0";
		String tkrate="";
		String nick="";
		String couponInfo="";
		Integer includeDxjh=0;
		try{
			String[] ts=cookie.split(";");
			String tbtoken="";
			for(String t:ts){
				if(t.indexOf("_tb_token_")!=-1){
					tbtoken=t.split("=")[1];
				}
			}

			String apiurl="http://pub.alimama.com/items/search.json?q="+url+"&_t="+new Date().getTime()+"&auctionTag=&perPageSize=40&shopTag=yxjh&t="+new Date().getTime()+"&_tb_token_="+tbtoken+"&pvid=";
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			get.setRequestHeader("Connection","keep-alive");
			get.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			get.setRequestHeader("Upgrade-Insecure-Requests","1");
			get.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.setRequestHeader("Cookie", cookie);
			
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			//System.out.println(DateUtil.getTodayTime()+":淘宝联盟搜索"+s);
			TlmCouponInfo ti=JSON.parseObject(s,TlmCouponInfo.class);
			if(ti!=null&&ti.getData()!=null&&ti.getData().getPaginator()!=null&&ti.getData().getPaginator().getLength()!=null){
				num=ti.getData().getPaginator().getLength();
				if(num>0&&ti.getData().getPageList()!=null&&ti.getData().getPageList().size()>0){
					id=ti.getData().getPageList().get(0).getAuctionId();
					imgurl=ti.getData().getPageList().get(0).getPictUrl();
					pname=ti.getData().getPageList().get(0).getTitle();
					price=ti.getData().getPageList().get(0).getZkPrice();
					tkrate=ti.getData().getPageList().get(0).getTkRate();
					cmount=ti.getData().getPageList().get(0).getCouponAmount();
					nick=ti.getData().getPageList().get(0).getNick();
					
					sprice=df.format(Double.parseDouble(price)-Double.parseDouble(cmount));
					includeDxjh=ti.getData().getPageList().get(0).getIncludeDxjh();
					couponInfo=ti.getData().getPageList().get(0).getCouponInfo();

					tni=new TlmNumId();
					tni.setNum(num);
					tni.setAid(id);
					tni.setImgurl(imgurl);
					tni.setPname(pname);
					tni.setPrice(price);
					tni.setCamount(cmount);
					tni.setSprice(sprice);
					tni.setTkrate(tkrate);
					tni.setNick(nick);
					tni.setCouponInfo(couponInfo);
					tni.setIncludeDxjh(includeDxjh);
				}
			}else{
				//System.out.println(DateUtil.getTodayTime()+":淘宝联盟查询结果为空");
			}
			Thread.sleep(666);
		}catch(Exception e){
			e.printStackTrace();
		}
		return tni;
	}
	
	/**
	 * 查询鹊桥活动
	 *
	 */
	public static Double searchqqhd(String url,String cookie){
		Double yj=null;
		try{
			String[] ts=cookie.split(";");
			String tbtoken="";
			for(String t:ts){
				if(t.indexOf("_tb_token_")!=-1){
					tbtoken=t.split("=")[1];
				}
			}
			
			String apiurl="http://pub.alimama.com/items/channel/qqhd.json?q="+url+"&channel=qqhd&perPageSize=50&t="+new Date().getTime()+"&_tb_token_="+tbtoken;
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			get.setRequestHeader("Connection","keep-alive");
			get.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			get.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.setRequestHeader("Cookie", cookie);
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			TlmCouponInfo tci=null;
			if(s.indexOf("<!doctype html>")<0&&s.indexOf("<!DOCTYPE html>")<0){
				tci=JSON.parseObject(s,TlmCouponInfo.class);
			}else{
				System.out.println(DateUtil.getTodayTime()+":查询鹊桥JSON异常");
			}

			if(tci!=null&&tci.getData()!=null&&tci.getData().getPageList()!=null&&tci.getData().getPageList().size()>0){
				yj=Double.parseDouble(tci.getData().getPageList().get(0).getTkRate());
			}else{
				System.out.println(DateUtil.getTodayTime()+":查询鹊桥活动data为空");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return yj;
	}
	
	public static Gaoy getdxjh(String pid,String cookie){
		CampData td=null;
		Gaoy g=new Gaoy();
		try{
			String[] ts=cookie.split(";");
			String tbtoken="";
			for(String t:ts){
				if(t.indexOf("_tb_token_")!=-1){
					tbtoken=t.split("=")[1];
				}
			}
			String apiurl="http://pub.alimama.com/pubauc/getCommonCampaignByItemId.json?itemId="+pid+"&t="+new Date().getTime()+"&_tb_token_="+tbtoken+"&pvid=";
			//System.out.println(apiurl);
			HttpClient client=new HttpClient();
			GetMethod get=new GetMethod(apiurl);
			get.setRequestHeader("Connection","keep-alive");
			get.setRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			get.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			get.setRequestHeader("Cookie", cookie);
			
			client.executeMethod(get);
			String s=get.getResponseBodyAsString();
			s=s.replace("{}","[]");
			System.out.println(s);
			CampInfo ci=null;
			if(s.indexOf("<!doctype html>")<0&&s.indexOf("<!DOCTYPE html>")<0){
				ci=JSON.parseObject(s,CampInfo.class);
			}else{
				System.out.println(DateUtil.getTodayTime()+":pid="+pid+",查询定向计划JSON异常");
			}
			if(ci!=null){
				List<CampData> cdlist=new ArrayList<CampData>();
				List<CampData> uncdlist=new ArrayList<CampData>();
				for(CampData cd:ci.getData()){
					//System.out.println(cd.isExist()+"=="+cd.getCommissionRate()+"==="+cd.getCampaignID()+"==="+cd.getCampaignName()+"==="+cd.getShopKeeperID()+"==="+cd.getProperties());
					if(cd.getProperties().equals("否")){
						cdlist.add(cd);
					}else{
						uncdlist.add(cd);
					}
				}
				
				Collections.sort(cdlist, new Comparator<CampData>() {
					@Override
					public int compare(CampData f1, CampData f2) {
						if(f1.getCommissionRate()>f2.getCommissionRate()) {
							return -1;
						}
						return 1;
					}
				});
				
				if(cdlist.size()>0){
					//自动审核
					td=cdlist.get(0);
				}else{
					//人工审核
					if(uncdlist.size()>0){
						//td=uncdlist.get(0);
					}
				}
			}
			
			//如果有高佣计划自动申请
			if(td!=null){
				//查询已加入计划
				TlmNumId tni=TaobaokeAPI.searchfromtblm("http://detail.tmall.com/item.htm?id="+pid, cookie);
				if(tni!=null){
					Double tyyj=Double.parseDouble(tni.getTkrate());
					Double jhyj=td.getCommissionRate();
					if(td.isExist()==false){
						if(jhyj>tyyj){
							//计划佣金大于通用佣金申请计划
							Integer rs=TaobaokeAPI.joinCamp(td.getCampaignID(),td.getShopKeeperID(), cookie);
							if(rs==1){
								g.setJhyj(jhyj);
								System.out.println(DateUtil.getTodayTime()+":pid="+pid+",申请定向计划成功,比率："+jhyj);
							}else{
								System.out.println(DateUtil.getTodayTime()+":pid="+pid+",申请定向计划失败");
							}
						}else{
							g.setTyyj(tyyj);
							System.out.println(DateUtil.getTodayTime()+":pid="+pid+",计划佣金小于通用佣金,不申请计划,比率："+tyyj);
						}
					}else{
						if(jhyj>tyyj){
							//计划佣金大于通用佣金申请计划
							g.setJhyj(jhyj);
							System.out.println(DateUtil.getTodayTime()+":pid="+pid+",已经在定向计划中,比率："+jhyj);
						}else{
							//先查询已加入的计划列表获取pubcampid
							String pubcampid=TaobaokeAPI.getJoindCamp(tni.getNick(), cookie);
							//退出商家计划
							TaobaokeAPI.exitCamp(pubcampid, cookie);
							g.setJhyj(tyyj);
							System.out.println(DateUtil.getTodayTime()+":pid="+pid+",计划佣金小于通用佣金,退出计划,比率："+tyyj);
						}
					}
				}else{
					System.out.println(DateUtil.getTodayTime()+":pid="+pid+",非淘客商品");
				}
			}else{
				System.out.println(DateUtil.getTodayTime()+":pid="+pid+",没有定向计划");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return g;
	}
	
	/**
	 * 大淘客商品获取定向计划
	 * @param dp
	 * @param cookie
	 */
	public static void getdtkdxjh(DataokeProduct dp,String cookie){
		if(dp.getJihua_link()!=null&&!dp.getJihua_link().equals("")&&!dp.getJihua_link().equals("0")&&dp.getJihua_link().indexOf("campaignId=")!=-1&&dp.getJihua_link().indexOf("shopkeeperId=")!=-1){
    		//定向计划
    		String campid=dp.getJihua_link().split("campaignId=")[1].split("&")[0];
    		String keepid=dp.getJihua_link().split("shopkeeperId=")[1].split("&")[0];
    		//加入定向计划
    		Integer rs=TaobaokeAPI.joinCamp(campid, keepid, cookie);
    		if(rs==1){
    			System.out.println(DateUtil.getTodayTime()+":pid="+dp.getGoodsID()+",申请计划成功,比率："+dp.getCommission_jihua());
    			//更新数据库为定向计划
				TaobaokeAPI.udpateDxjh(dp.getGoodsID(),dp.getCommission_jihua()+"",2);
    		}else{
    			TaobaokeAPI.deleteProductPid(dp.getGoodsID());
    			System.out.println(DateUtil.getTodayTime()+":pid="+dp.getGoodsID()+",申请计划失败,删除");
    		}
    	}else{
    		//通用佣金
    		TlmNumId tni=TaobaokeAPI.searchfromtblm("http://detail.tmall.com/item.htm?id="+dp.getGoodsID(), cookie);
    		if(tni!=null){
        		Double tkrate=Double.parseDouble(tni.getTkrate());
        		//通用佣金大于计划佣金
        		if(tkrate>dp.getCommission_jihua()){
        			String pubcampid=TaobaokeAPI.getJoindCamp(tni.getNick(), cookie);
    				//退出定向计划
    				TaobaokeAPI.exitCamp(pubcampid, cookie);
    				//更新数据库为通用
    				TaobaokeAPI.udpateDxjh(dp.getGoodsID(),dp.getCommission_jihua()+"",0);
    				System.out.println(DateUtil.getTodayTime()+":pid="+dp.getGoodsID()+",通用佣金大于计划佣金,退出计划,比率："+dp.getCommission_jihua());
        		}else{
        			System.out.println(DateUtil.getTodayTime()+":pid="+dp.getGoodsID()+",通用佣金,比率："+dp.getCommission_jihua());
        		}
    		}
    	}
	}
	
	public static TbkProduct getBestProduct(String pid,Group group){
		TbkProduct tp=null;
		String cookie=getCookie();
		String url="http://item.taobao.com/item.htm?id="+pid;
		try{
			//淘宝联盟查询商品
			TlmNumId tni=searchfromtblm(url,cookie);
			if(tni!=null){
				System.out.println(DateUtil.getTodayTime()+":pid="+pid+",淘宝联盟找到商品");
				//淘宝联盟中找到商品,点击立即推广
				TlmData td=gettblmurl(pid,group.getPid(),cookie);
				//联盟商品转换本地商品
				if(td!=null){
					tp=saveProductFromTblm(tni,td);
					if(tni.getIncludeDxjh()>0){
						//查询鹊桥
						Double yj=searchqqhd(url,cookie);
						if(yj!=null){
							tp.setIsjh(2);
							//是鹊桥活动，获取鹊桥链接
							TlmData t=getqqhdurl(pid,group.getPid(),cookie);
							if(t!=null){
								//有优惠券
								if(t.getCouponLink()!=null&&!t.getCouponLink().equals("")){
									tp.setClong(t.getCouponLink());
									tp.setCtkl(t.getCouponLinkTaoToken());
								}else{
									tp.setClong(t.getClickUrl());
									tp.setCtkl(t.getTaoToken());
								}
								tp.setSrbl(yj);
							}
						}else{
							TlmNumId tid=searchyxjhtblm(url,cookie);
							if(tid!=null){
								//营销计划
								TlmData t=TaobaokeAPI.gettblmurl(tp.getPid()+"", group.getPid(),cookie);
								if(t.getCouponLink()!=null&&!t.getCouponLink().equals("")){
									tp.setClong(t.getCouponLink());
									tp.setCtkl(t.getCouponLinkTaoToken());
								}else{
									tp.setClong(t.getClickUrl());
									tp.setCtkl(t.getTaoToken());
								}
								tp.setSrbl(Double.parseDouble(tid.getTkrate()));
							}else{
								//通用佣金查询定向计划
								Gaoy gy=getdxjh(pid,cookie);
								if(gy.getJhyj()!=null){
									tp.setIsjh(1);
									tp.setSrbl(gy.getJhyj());
								}else if(gy.getTyyj()!=null){
									tp.setIsjh(0);
									tp.setSrbl(gy.getTyyj());
								}
							}
						}
					}else{
						tp.setIsjh(3);
					}
				}
				
				if(tp==null){
					System.out.println(DateUtil.getTodayTime()+":pid="+pid+",淘宝联盟转换失败");
				}else{
					System.out.println(DateUtil.getTodayTime()+":pid="+pid+",淘口令："+tp.getCtkl());
					System.out.println(DateUtil.getTodayTime()+":pid="+pid+",下单链接："+tp.getClong());
				}
			}else{
				System.out.println(DateUtil.getTodayTime()+":pid="+pid+",非淘宝联盟商品");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return tp;
	}
	
	public static String getCookie(){
		String cookie="";
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="select * from tbk_config where ckey='tblmcookie'";
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
	
	public static void updateCookie(String cookie){
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="update tbk_config set cvalue='"+cookie+"' where ckey='tblmcookie'";
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getConfig(String ckey){
		String cookie="";
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="select * from tbk_config where ckey='"+ckey+"'";
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
	
	public static void updatePageno(String ckey,String cvalue){
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="update tbk_config set cvalue='"+cvalue+"' where ckey='"+ckey+"'";
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void udpateqqhdLink(String pid,String clong){
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="update tbk_product set clong='"+clong+"',tbklong='"+clong+"' where pid='"+pid+"'";
            System.out.println(sql);
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void udpateCreatedate(String pid,String date){
		
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="update tbk_product set createdate='"+date+"' where pid='"+pid+"'";
            //System.out.println(sql);
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void udpateTg(String pid){
		
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="update tbk_product set istg=1 where dtkid='"+pid+"'";
            //System.out.println(sql);
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void udpateDxjh(String pid,String srbl,Integer isjh){
		
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="update tbk_product set srbl="+srbl+",isjh="+isjh+" where pid='"+pid+"'";
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void udpateHascheckjh(String pid,Integer isjh){
		
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="update tbk_product set isjh="+isjh+" where pid='"+pid+"'";
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void deleteYesterday(){
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="delete from tbk_product where createdate!='"+DateUtil.getToday()+"' and createdate!='"+DateUtil.getLastDay(-1)+"'";
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void deleteProductPid(String pid){
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="delete from tbk_product where pid='"+pid+"'";
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 从淘宝联盟保存商品
	 * @param dp
	 * @return
	 */
	public static TbkProduct saveProductFromTblm(TlmNumId tni,TlmData td){
		TbkProduct tp=null;
		try{
			//获取类型
			List<TbkType> tlist=getTypeList();
			Map<String,Object> m=new HashMap<String,Object>();
			for(TbkType tn:tlist){
				m.put(tn.getTypename(),tn);
			}
			//获取分类名称
			String typename="";
			int random = (int) Math.round(Math.random()*100);
			Double srbl=Double.parseDouble(tni.getTkrate());
			//计算点击排序
			int point=(int)(1*srbl*random);
	    	
			tp=new TbkProduct();
			tp.setPid(Long.parseLong(tni.getAid()));
			tp.setCcontent(tni.getCouponInfo());
			//只保存优惠券链接
			if(td.getCouponLink()!=null&&!td.getCouponLink().equals("")){
				tp.setClong(td.getCouponShortLinkUrl());
				tp.setCtkl(td.getCouponLinkTaoToken());
			}else{
				tp.setClong(td.getClickUrl());
				tp.setCtkl(td.getTaoToken());
			}
			tp.setCprice(Double.parseDouble(tni.getCamount()));
			tp.setActivityid("");
			tp.setCreatedate(DateUtil.getToday());
			tp.setImgurl(tni.getImgurl());
			tp.setIsshow(1);
			tp.setLeftcnum(99);
			tp.setPname(tni.getPname());
			tp.setPoint(point);
			tp.setPrice(Double.parseDouble(tni.getPrice()));
			tp.setSalenum(99);
			tp.setSname(tni.getPname().substring(0,6));
			tp.setSprice(Double.parseDouble(df.format(tp.getPrice()-tp.getCprice())));
			tp.setSrbl(Double.parseDouble(tni.getTkrate()));
			tp.setTotalcnum(100);
			tp.setType(typename);
			tp.setDtkid(0);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return tp;
	}
	
	/**
	 * 从大淘客保存商品
	 * @param dp
	 * @return
	 */
	public static TbkProduct saveProductFromDtk(DataokeProduct dp){
		TbkProduct tp=null;
		try{
			//获取类型
			List<TbkType> tlist=getTypeList();
			Map<String,Object> m=new HashMap<String,Object>();
			for(TbkType tn:tlist){
				m.put(tn.getTypename(),tn);
			}
			//获取分类名称
			String typename=getTypeByDtypeid(Integer.parseInt(dp.getCid()));
			int random = (int) Math.round(Math.random()*100);
			Double srbl=dp.getCommission_jihua();
			TbkType tt=(TbkType)m.get(typename);
			//计算点击排序
			int point=(int)(tt.getSort()*srbl*random);
	    	
			tp=new TbkProduct();
			tp.setPid(Long.parseLong(dp.getGoodsID()));
			tp.setCcontent(dp.getQuan_condition());
			tp.setClong(DataokeAPI.getCoupon2to1(dp.getQuan_id(), dp.getGoodsID(), mypid));
			tp.setCprice(Double.parseDouble(dp.getQuan_price()));
			tp.setActivityid(dp.getQuan_id());
			tp.setCreatedate(DateUtil.getToday());
			tp.setImgurl(dp.getPic());
			tp.setIsshow(1);
			tp.setLeftcnum(dp.getQuan_surplus());
			tp.setPname(dp.getTitle());
			tp.setPoint(point);
			tp.setPrice(Double.parseDouble(dp.getOrg_Price()));
			tp.setSalenum(dp.getSales_num());
			tp.setSname(dp.getD_Title());
			tp.setSprice(Double.parseDouble(dp.getPrice()));
			tp.setSrbl(srbl);
			tp.setTbklong(DataokeAPI.getCoupon2to1(dp.getQuan_id(), dp.getGoodsID(), mypid));
			tp.setTotalcnum(dp.getQuan_receive()+dp.getQuan_surplus());
			tp.setType(typename);
			tp.setDtkid(dp.getID());
			tp.setCstime(DateUtil.getTodayTime());
			tp.setCetime(DateUtil.getTodayTime());
			tp.setIstg(0);
			tp.setIsjh(0);
			
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="insert into tbk_product(pid,imgurl,pname,sname,price,sprice,salenum,tbkshort,tbklong,tkl,totalcnum,leftcnum,ccontent,ctkl,clong,cshort,type,cstime,cetime,point,createdate,srbl,yhbl,yj,isshow,cprice,activityid,dtkid)";
            sql+="values ('"+tp.getPid()+"','"+tp.getImgurl()+"','"+tp.getPname()+"','"+tp.getSname()+"',"+tp.getPrice()+","+tp.getSprice()+","+tp.getSalenum()+",'"+tp.getTbkshort()+"','"+tp.getTbklong()+"','"+tp.getTkl()+"',"+tp.getTotalcnum()+","+tp.getLeftcnum()+",";
            sql+="'"+tp.getCcontent()+"','"+tp.getCtkl()+"','"+tp.getClong()+"','"+tp.getCshort()+"','"+tp.getType()+"','"+tp.getCstime()+"','"+tp.getCetime()+"',"+tp.getPoint()+",'"+tp.getCreatedate()+"',"+tp.getSrbl()+","+tp.getYhbl()+","+tp.getYj()+","+tp.getIsshow()+","+tp.getCprice()+",'"+tp.getActivityid()+"',"+tp.getDtkid()+")";
            //System.out.println(sql);
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return tp;
	}
	
	/**
	 * 从大淘客保存商品
	 * @param dp
	 * @return
	 */
	public static TbkProduct saveProductFromDtkPoint(DataokeProduct dp){
		TbkProduct tp=null;
		try{
			//获取类型
			List<TbkType> tlist=getTypeList();
			Map<String,Object> m=new HashMap<String,Object>();
			for(TbkType tn:tlist){
				m.put(tn.getTypename(),tn);
			}
			//获取分类名称
			String typename=getTypeByDtypeid(Integer.parseInt(dp.getCid()));
			Double srbl=dp.getCommission_jihua();
			//计算点击排序
			Date date=new Date();
			int point=(int)(date.getTime()/10000);
	    	
			tp=new TbkProduct();
			tp.setPid(Long.parseLong(dp.getGoodsID()));
			tp.setCcontent(dp.getQuan_condition());
			tp.setClong(DataokeAPI.getCoupon2to1(dp.getQuan_id(), dp.getGoodsID(), mypid));
			tp.setCprice(Double.parseDouble(dp.getQuan_price()));
			tp.setActivityid(dp.getQuan_id());
			tp.setCreatedate(DateUtil.getToday());
			tp.setImgurl(dp.getPic());
			tp.setIsshow(1);
			tp.setLeftcnum(dp.getQuan_surplus());
			tp.setPname(dp.getTitle());
			tp.setPoint(point);
			tp.setPrice(Double.parseDouble(dp.getOrg_Price()));
			tp.setSalenum(dp.getSales_num());
			tp.setSname(dp.getD_Title());
			tp.setSprice(Double.parseDouble(dp.getPrice()));
			tp.setSrbl(srbl);
			tp.setTbklong(DataokeAPI.getCoupon2to1(dp.getQuan_id(), dp.getGoodsID(), mypid));
			tp.setTotalcnum(dp.getQuan_receive()+dp.getQuan_surplus());
			tp.setType(typename);
			tp.setDtkid(dp.getID());
			tp.setCstime(DateUtil.getTodayTime());
			tp.setCetime(DateUtil.getTodayTime());
			tp.setIstg(0);
			
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="insert into tbk_product(pid,imgurl,pname,sname,price,sprice,salenum,tbkshort,tbklong,tkl,totalcnum,leftcnum,ccontent,ctkl,clong,cshort,type,cstime,cetime,point,createdate,srbl,yhbl,yj,isshow,cprice,activityid,dtkid)";
            sql+="values ('"+tp.getPid()+"','"+tp.getImgurl()+"','"+tp.getPname()+"','"+tp.getSname()+"',"+tp.getPrice()+","+tp.getSprice()+","+tp.getSalenum()+",'"+tp.getTbkshort()+"','"+tp.getTbklong()+"','"+tp.getTkl()+"',"+tp.getTotalcnum()+","+tp.getLeftcnum()+",";
            sql+="'"+tp.getCcontent()+"','"+tp.getCtkl()+"','"+tp.getClong()+"','"+tp.getCshort()+"','"+tp.getType()+"','"+tp.getCstime()+"','"+tp.getCetime()+"',"+tp.getPoint()+",'"+tp.getCreatedate()+"',"+tp.getSrbl()+","+tp.getYhbl()+","+tp.getYj()+","+tp.getIsshow()+","+tp.getCprice()+",'"+tp.getActivityid()+"',"+tp.getDtkid()+")";
            //System.out.println(sql);
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return tp;
	}
	
	public static void udpateCreatedatePoint(String pid,String date){
		
		try{
			Date d=new Date();
			int point=(int)(d.getTime()/10000);
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="update tbk_product set createdate='"+date+"',point="+point+" where pid='"+pid+"'";
            //System.out.println(sql);
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 从大淘客保存商品
	 * @param dp
	 * @return
	 */
	public static TbkProduct saveProductFromTp(TbkProduct tp){
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="insert into tbk_product(pid,imgurl,pname,sname,price,sprice,salenum,tbkshort,tbklong,tkl,totalcnum,leftcnum,ccontent,ctkl,clong,cshort,type,cstime,cetime,point,createdate,srbl,yhbl,yj,isshow,cprice,activityid,dtkid)";
            sql+="values ('"+tp.getPid()+"','"+tp.getImgurl()+"','"+tp.getPname()+"','"+tp.getSname()+"',"+tp.getPrice()+","+tp.getSprice()+","+tp.getSalenum()+",'"+tp.getTbkshort()+"','"+tp.getTbklong()+"','"+tp.getTkl()+"',"+tp.getTotalcnum()+","+tp.getLeftcnum()+",";
            sql+="'"+tp.getCcontent()+"','"+tp.getCtkl()+"','"+tp.getClong()+"','"+tp.getCshort()+"','"+tp.getType()+"','"+tp.getCstime()+"','"+tp.getCetime()+"',"+tp.getPoint()+",'"+tp.getCreatedate()+"',"+tp.getSrbl()+","+tp.getYhbl()+","+tp.getYj()+","+tp.getIsshow()+","+tp.getCprice()+",'"+tp.getActivityid()+"',"+tp.getDtkid()+")";
            //System.out.println(sql);
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return tp;
	}

	/**
	 * 获取所有商品类型
	 * @return
	 */
	public static List<TbkType> getTypeList(){
		List<TbkType> list=new ArrayList<TbkType>();
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="select * from tbk_type";
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()) { // 将查询到的数据打印出来
            	TbkType t=new TbkType();
            	t.setSort(rs.getInt("sort"));
            	t.setTypename(rs.getString("typename"));
            	t.setTaobaoType(rs.getString("taobaotype"));
            	list.add(t);
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static Group getGroup(Integer gid){
		Group g=null;
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="select * from tbk_group where id="+gid;
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()) { // 将查询到的数据打印出来
            	g=new Group();
            	g.setDlfc(rs.getInt("dlfc"));
            	g.setEtime(rs.getString("etime"));
            	g.setFc(rs.getInt("fc"));
            	g.setGroupname(rs.getString("groupname"));
            	g.setId(rs.getInt("id"));
            	g.setIszd(rs.getInt("iszd"));
            	g.setNdlfc(rs.getInt("ndlfc"));
            	g.setParentid(rs.getInt("parentid"));
            	g.setPid(rs.getString("pid"));
            	g.setStime(rs.getString("stime"));
            	g.setWxname(rs.getString("wxname"));
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return g;
	}
	
	public static TbkProduct getProductFromDB(String pid){
		TbkProduct tp=null;
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="select * from tbk_product where pid='"+pid+"'";
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()) { // 将查询到的数据打印出来
            	tp=new TbkProduct();
            	tp.setActivityid(rs.getString("activityid"));
            	tp.setCcontent(rs.getString("ccontent"));
            	tp.setCetime(rs.getString("cetime"));
            	tp.setClong(rs.getString("clong"));
            	tp.setCprice(rs.getDouble("cprice"));
            	tp.setCreatedate(rs.getString("createdate"));
            	tp.setCshort(rs.getString("cshort"));
            	tp.setCstime(rs.getString("cstime"));
            	tp.setCtkl(rs.getString("ctkl"));
            	tp.setDtkid(rs.getInt("dtkid"));
            	tp.setId(rs.getInt("id"));
            	tp.setImgurl(rs.getString("imgurl"));
            	tp.setIsjh(rs.getInt("isjh"));
            	tp.setIsshow(rs.getInt("isshow"));
            	tp.setLeftcnum(rs.getInt("leftcnum"));
            	tp.setPid(rs.getLong("pid"));
            	tp.setPname(rs.getString("pname"));
            	tp.setPoint(rs.getInt("point"));
            	tp.setPrice(rs.getDouble("price"));
            	tp.setSalenum(rs.getInt("salenum"));
            	tp.setSname(rs.getString("sname"));
            	tp.setSprice(rs.getDouble("sprice"));
            	tp.setSrbl(rs.getDouble("srbl"));
            	tp.setTbklong(rs.getString("tbklong"));
            	tp.setTbkshort(rs.getString("tbkshort"));
            	tp.setTkl(rs.getString("tkl"));
            	tp.setTotalcnum(rs.getInt("totalcnum"));
            	tp.setType(rs.getString("type"));
            	tp.setYhbl(rs.getDouble("yhbl"));
            	tp.setYj(rs.getDouble("yj"));
            	tp.setIstg(rs.getInt("istg"));
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return tp;
	}
	
	
	
	public static TbkProduct getProductByPidAndTypeFromDB(String pid,String type){
		TbkProduct tp=null;
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="select * from tbk_product where pid='"+pid+"' and type='"+type+"'";
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()) { // 将查询到的数据打印出来
            	tp=new TbkProduct();
            	tp.setActivityid(rs.getString("activityid"));
            	tp.setCcontent(rs.getString("ccontent"));
            	tp.setCetime(rs.getString("cetime"));
            	tp.setClong(rs.getString("clong"));
            	tp.setCprice(rs.getDouble("cprice"));
            	tp.setCreatedate(rs.getString("createdate"));
            	tp.setCshort(rs.getString("cshort"));
            	tp.setCstime(rs.getString("cstime"));
            	tp.setCtkl(rs.getString("ctkl"));
            	tp.setDtkid(rs.getInt("dtkid"));
            	tp.setId(rs.getInt("id"));
            	tp.setImgurl(rs.getString("imgurl"));
            	tp.setIsjh(rs.getInt("isjh"));
            	tp.setIsshow(rs.getInt("isshow"));
            	tp.setLeftcnum(rs.getInt("leftcnum"));
            	tp.setPid(rs.getLong("pid"));
            	tp.setPname(rs.getString("pname"));
            	tp.setPoint(rs.getInt("point"));
            	tp.setPrice(rs.getDouble("price"));
            	tp.setSalenum(rs.getInt("salenum"));
            	tp.setSname(rs.getString("sname"));
            	tp.setSprice(rs.getDouble("sprice"));
            	tp.setSrbl(rs.getDouble("srbl"));
            	tp.setTbklong(rs.getString("tbklong"));
            	tp.setTbkshort(rs.getString("tbkshort"));
            	tp.setTkl(rs.getString("tkl"));
            	tp.setTotalcnum(rs.getInt("totalcnum"));
            	tp.setType(rs.getString("type"));
            	tp.setYhbl(rs.getDouble("yhbl"));
            	tp.setYj(rs.getDouble("yj"));
            	tp.setIstg(rs.getInt("istg"));
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return tp;
	}
	
	
	
	
	public static TbkProduct getProductById(Integer id){
		TbkProduct tp=null;
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="select * from tbk_product where id="+id;
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()) { // 将查询到的数据打印出来
            	tp=new TbkProduct();
            	tp.setActivityid(rs.getString("activityid"));
            	tp.setCcontent(rs.getString("ccontent"));
            	tp.setCetime(rs.getString("cetime"));
            	tp.setClong(rs.getString("clong"));
            	tp.setCprice(rs.getDouble("cprice"));
            	tp.setCreatedate(rs.getString("createdate"));
            	tp.setCshort(rs.getString("cshort"));
            	tp.setCstime(rs.getString("cstime"));
            	tp.setCtkl(rs.getString("ctkl"));
            	tp.setDtkid(rs.getInt("dtkid"));
            	tp.setId(rs.getInt("id"));
            	tp.setImgurl(rs.getString("imgurl"));
            	tp.setIsjh(rs.getInt("isjh"));
            	tp.setIsshow(rs.getInt("isshow"));
            	tp.setLeftcnum(rs.getInt("leftcnum"));
            	tp.setPid(rs.getLong("pid"));
            	tp.setPname(rs.getString("pname"));
            	tp.setPoint(rs.getInt("point"));
            	tp.setPrice(rs.getDouble("price"));
            	tp.setSalenum(rs.getInt("salenum"));
            	tp.setSname(rs.getString("sname"));
            	tp.setSprice(rs.getDouble("sprice"));
            	tp.setSrbl(rs.getDouble("srbl"));
            	tp.setTbklong(rs.getString("tbklong"));
            	tp.setTbkshort(rs.getString("tbkshort"));
            	tp.setTkl(rs.getString("tkl"));
            	tp.setTotalcnum(rs.getInt("totalcnum"));
            	tp.setType(rs.getString("type"));
            	tp.setYhbl(rs.getDouble("yhbl"));
            	tp.setYj(rs.getDouble("yj"));
            	tp.setIstg(rs.getInt("istg"));
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return tp;
	}
	
	public static void updateGroupSend(Integer tpid,Integer robotid){
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="update tbk_group_send set robot"+robotid+"=1 where tpid="+tpid;
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Integer getGroupSendType(String type){
		Integer num=0;
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="select count(*) as num from tbk_group_send a,tbk_product b where a.tpid=b.id and robot1=0 and robot2=0 and robot3=0 and robot4=0 and robot5=0 and robot6=0 and robot7=0 and robot8=0 and b.type='"+type+"'";
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()) { // 将查询到的数据打印出来
            	num=rs.getInt("num");
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return num;
	}
	
	public static Integer getGroupSendId(Integer tpid){
		Integer num=0;
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="select count(tpid) as num from tbk_group_send where tpid="+tpid;
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()) { // 将查询到的数据打印出来
            	num=rs.getInt("num");
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return num;
	}
	
	public static TbkProduct getGroupSend(Integer tpid){
		TbkProduct tp=null;
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="select b.* from tbk_group_send a,tbk_product b where a.tpid=b.id and b.id="+tpid;
            ResultSet rs = stat.executeQuery(sql);
            if(rs.next()) { // 将查询到的数据打印出来
            	tp=new TbkProduct();
            	tp.setActivityid(rs.getString("activityid"));
            	tp.setCcontent(rs.getString("ccontent"));
            	tp.setCetime(rs.getString("cetime"));
            	tp.setClong(rs.getString("clong"));
            	tp.setCprice(rs.getDouble("cprice"));
            	tp.setCreatedate(rs.getString("createdate"));
            	tp.setCshort(rs.getString("cshort"));
            	tp.setCstime(rs.getString("cstime"));
            	tp.setCtkl(rs.getString("ctkl"));
            	tp.setDtkid(rs.getInt("dtkid"));
            	tp.setId(rs.getInt("id"));
            	tp.setImgurl(rs.getString("imgurl"));
            	tp.setIsjh(rs.getInt("isjh"));
            	tp.setIsshow(rs.getInt("isshow"));
            	tp.setLeftcnum(rs.getInt("leftcnum"));
            	tp.setPid(rs.getLong("pid"));
            	tp.setPname(rs.getString("pname"));
            	tp.setPoint(rs.getInt("point"));
            	tp.setPrice(rs.getDouble("price"));
            	tp.setSalenum(rs.getInt("salenum"));
            	tp.setSname(rs.getString("sname"));
            	tp.setSprice(rs.getDouble("sprice"));
            	tp.setSrbl(rs.getDouble("srbl"));
            	tp.setTbklong(rs.getString("tbklong"));
            	tp.setTbkshort(rs.getString("tbkshort"));
            	tp.setTkl(rs.getString("tkl"));
            	tp.setTotalcnum(rs.getInt("totalcnum"));
            	tp.setType(rs.getString("type"));
            	tp.setYhbl(rs.getDouble("yhbl"));
            	tp.setYj(rs.getDouble("yj"));
            	tp.setIstg(rs.getInt("istg"));
            }
            rs.close();
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return tp;
	}
	
	public static void saveGroupSend(Integer tpid){
		try{
			Class.forName(mysql_driver);
		    Connection conn = DriverManager.getConnection(mysql_url+"&user="+mysql_username+"&password="+mysql_password);
            Statement stat = conn.createStatement();
            String sql="insert into tbk_group_send(tpid,createtime) values("+tpid+",now())";
            stat.executeUpdate(sql);
            stat.close();
            conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws ApiException {
		String s=getTkl2("https://shop.m.taobao.com/shop/coupon.htm?seller_id=2130776634&activity_id=31ea9de4e2364f60a29293d9789dbccb","主打商品的是","//img.alicdn.com/bao/uploaded/i3/542298673/TB20Xy1XOnB11BjSsphXXXpaXXa_!!542298673.jpg");
		//getqqhdurl("556959739247","mm_65772869_40086169_151064267",getCookie());
		System.out.println(s);
	}
	
}
