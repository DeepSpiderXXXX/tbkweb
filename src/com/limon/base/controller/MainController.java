package com.limon.base.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.limon.base.model.FileInfo;
import com.limon.base.model.ResultInfo;
import com.limon.base.model.SysLog;
import com.limon.base.model.SysMenu;
import com.limon.base.model.SysUser;
import com.limon.base.model.TbkBanner;
import com.limon.base.model.TbkDaySend;
import com.limon.base.model.TbkPoint;
import com.limon.base.model.TbkProduct;
import com.limon.base.model.TbkSr;
import com.limon.base.model.TbkType;
import com.limon.base.model.TypeNum;
import com.limon.base.service.SysLogService;
import com.limon.base.service.SysMenuService;
import com.limon.base.service.TbkProductService;
import com.limon.base.taobao.DataokeAPI;
import com.limon.base.taobao.DataokeProduct;
import com.limon.base.taobao.Gaoy;
import com.limon.base.taobao.Group;
import com.limon.base.taobao.TaobaokeAPI;
import com.limon.base.taobao.TlmData;
import com.limon.util.CommonUtil;
import com.limon.util.DateUtil;

/**
 * @author gqf
 *
 * 主页相关流程
 * 2015-2-10 上午10:32:56
 */
@Controller
public class MainController extends BaseController{
	@Autowired
	private TbkProductService tbkProductService;
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysMenuService sysMenuService;
	
	private static String mypid="mm_65772869_40086169_151064267";
	private static DecimalFormat df = CommonUtil.getDoubleFormat();
	private Double aftertax=0.8;
	
	@RequestMapping("/main")
    public String main(HttpServletRequest request, HttpServletResponse response){
		SysUser user=this.getLoginUser(request);
		if(user==null){
			try{
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("<script>top.location.href='"+request.getContextPath()+"/login?error=1"+"'</script>");
				return null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		List<SysMenu> menulist=sysMenuService.getRoleMenuList(user.getRoleid());
		//当前登录用户
		request.setAttribute("loginUser",user);
		//根据用户角色取有权限的菜单列表
		request.setAttribute("menulist",menulist);
        return "/mainindex";
    }
	
	@RequestMapping("/p")
    public String person(HttpServletRequest request, HttpServletResponse response){
		Integer id=this.getParaInteger("id",request);
		Integer flag=this.getParaInteger("flag",request);
		Integer isshow=1;
		if(id==1&&flag!=86){
			isshow=0;
		}
		Group group=tbkProductService.getGroupById(id);
		request.setAttribute("isshow",isshow);
		request.setAttribute("group",group);
        return "/person";
    }
	
	@RequestMapping("/welcome")
    public String welcome(HttpServletRequest request, HttpServletResponse response){
		SysUser user=this.getLoginUser(request);
		if(user==null){
			try{
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write("<script>top.location.href='"+request.getContextPath()+"/login?error=1"+"'</script>");
				return null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		Integer logtimes=sysLogService.getLogTimes(user.getUsername());
		SysLog lastlog=sysLogService.getLastLogInfo(user.getUsername());
		//当前用户登录次数
		request.setAttribute("logtimes",logtimes);
		//用户最近一次登录信息
		request.setAttribute("lastlog",lastlog);
		request.setAttribute("user",user);
        return "/welcome";
    }
	
	@RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response){
		String browser = "pc";
		Integer stg=this.getParaInteger("stg",request);
        // 判断访问设备
		if(request.getHeader("user-agent").indexOf("MicroMessenger")!=-1){
			browser = "wx";
		}else if(request.getHeader("user-agent").indexOf("Android")!=-1||
				 request.getHeader("user-agent").indexOf("iPhone")!=-1){
			browser = "mobile";
		}
		String searchkey=this.getParaString("searchkey",request);
		String type=this.getParaString("type",request);
		String colum=this.getParaString("colum",request);
		String sort=this.getParaString("sort",request);
		Integer g=this.getParaInteger("g",request);
		if(colum==null||colum.equals("")){
			colum="point";
		}
		if(sort==null||sort.equals("")){
			sort="desc";
		}
		if(g==0){
			g=1;
		}
		
		Integer flag=this.getParaInteger("flag",request);
		Integer isshow=1;
		if(g==1&&stg==1&&flag!=86){
			isshow=0;
		}
		
		Group group=tbkProductService.getGroupById(g);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("searchkey",searchkey);
		map.put("type",type);
		map.put("colum",colum);
		map.put("sort",sort);
		map.put("pageStart",page.getPagestart());
		map.put("pageSize",page.getPageSize());
		
		Integer fc=100-(group.getFc());
		List<TbkProduct> plist=tbkProductService.getTbkProductList(map);
		
		//佣金推广页加佣金比率计算
		if(stg==1){
			for(TbkProduct p:plist){
				Integer tpid=tbkProductService.getTbkDaySendByPid(p.getId());
				p.setTpid(tpid);
				p.setYjbl(df.format(((fc*p.getSrbl())/100)*aftertax));
				p.setYgsr(df.format((((fc*p.getSrbl())/100)/100)*p.getSprice()*aftertax));
			}
		}
		
		Integer totalRecord=tbkProductService.getTbkProductListCount(map);
		
		//分页信息设置
		page.setTotalRecord(totalRecord);
		page.setList(plist);
		List<TbkBanner> blist=tbkProductService.getBannerList();
		
		//返回页面参数
		request.setAttribute("searchkey", searchkey);
		request.setAttribute("type", type);
		request.setAttribute("colum", colum);
		request.setAttribute("sort", sort);
		request.setAttribute("page",page);
		request.setAttribute("plist",plist);
		request.setAttribute("blist",blist);
		request.setAttribute("browser",browser);
		request.setAttribute("g", g);
		request.setAttribute("stg", stg);
		request.setAttribute("flag", flag);
		request.setAttribute("fc",fc);
		
		int all=0;

		List<TbkType> tlist=tbkProductService.getTypeList();
		for(TbkType tt:tlist){
			Integer c=tbkProductService.getTbkProductCountByType(tt.getTypename());
			if(c!=null){
				tt.setCount(c);
			}else{
				tt.setCount(0);
			}
			all+=c;
		}
		request.setAttribute("tlist", tlist);
		request.setAttribute("all", all);
		
		if(isshow==1){
			return "/index";
		}else{
			return "/error";
		}
    }
	
	@RequestMapping("/loaddata")
    public void loaddata(HttpServletRequest request, HttpServletResponse response){
		try {
			Integer stg=this.getParaInteger("stg",request);
        	String searchkey=URLDecoder.decode(this.getParaString("searchkey",request),"utf-8");
    		String type=URLDecoder.decode(this.getParaString("type",request),"utf-8");
    		String colum=this.getParaString("colum",request);
    		String sort=this.getParaString("sort",request);
    		String g=this.getParaString("g",request);
    		if(colum==null||colum.equals("")){
    			colum="point";
    		}
    		if(sort==null||sort.equals("")){
    			sort="desc";
    		}
    		if(g==null||g.equals("")||g.equals("0")){
    			g="1";
    		}
    		
    		Group group=tbkProductService.getGroupById(Integer.parseInt(g));
    		
    		page.setCurrentPage(page.getCurrentPage()+1);
    		Map<String,Object> map=new HashMap<String,Object>();
    		map.put("searchkey",searchkey);
    		map.put("type",type);
    		map.put("colum",colum);
    		map.put("sort",sort);
    		map.put("pageStart",page.getPagestart());
    		map.put("pageSize",page.getPageSize());
    		Integer fc=100-(group.getFc());
    		List<TbkProduct> plist=tbkProductService.getTbkProductList(map);
    		
    		//佣金推广页加佣金比率计算
    		if(stg==1){
        		for(TbkProduct p:plist){
        			Integer tpid=tbkProductService.getTbkDaySendByPid(p.getId());
    				p.setTpid(tpid);
        			p.setYjbl(df.format(((fc*p.getSrbl())/100)*aftertax));
        			p.setYgsr(df.format((((fc*p.getSrbl())/100)/100)*p.getSprice()*aftertax));
        		}
    		}
    		
    		Integer totalRecord=tbkProductService.getTbkProductListCount(map);
    		
    		//分页信息设置
    		page.setTotalRecord(totalRecord);

    		ResultInfo ri=new ResultInfo();
    		ri.setPage(page.getCurrentPage());
    		ri.setTotalPage(page.getTotalPage());
    		ri.setPlist(plist);
    		String json=JSON.toJSONString(ri);
        	response.setContentType("application/json");
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@RequestMapping("/find")
    public String find(HttpServletRequest request, HttpServletResponse response){
		String browser = "pc";
        // 判断访问设备
		if(request.getHeader("user-agent").indexOf("MicroMessenger")!=-1){
			browser = "wx";
		}else if(request.getHeader("user-agent").indexOf("Android")!=-1||
				 request.getHeader("user-agent").indexOf("iPhone")!=-1){
			browser = "mobile";
		}
		String searchkey=this.getParaString("searchkey",request);
		Integer g=this.getParaInteger("g",request);
		if(g==0){
			g=1;
		}
		Group group=tbkProductService.getGroupById(g);
		String cookie=TaobaokeAPI.getCookie();
		Integer fc=100-(group.getFc());
		List<TbkProduct> plist=TaobaokeAPI.searchGoodsfromtblm(searchkey, page.getCurrentPage(), cookie);
		
		for(TbkProduct p:plist){
			p.setYjbl(df.format(((fc*p.getSrbl())/100)*aftertax));
			p.setYgsr(df.format((((fc*p.getSrbl())/100)/100)*p.getSprice()*aftertax));
		}
		
		Integer totalRecord=1;
		if(plist.size()>0){
			totalRecord=plist.get(0).getItems();
		}
		
		//分页信息设置
		page.setTotalRecord(totalRecord);
		//返回页面参数
		request.setAttribute("searchkey", searchkey);
		request.setAttribute("page",page);
		request.setAttribute("plist",plist);
		request.setAttribute("browser",browser);
		request.setAttribute("g", g);
		request.setAttribute("fc",fc);
		
		return "/find";
    }
	
	@RequestMapping("/loadfind")
    public void loadfind(HttpServletRequest request, HttpServletResponse response){
		try {
        	String searchkey=URLDecoder.decode(this.getParaString("searchkey",request),"utf-8");
    		String g=this.getParaString("g",request);
    		if(g==null||g.equals("")||g.equals("0")){
    			g="1";
    		}
    		
    		Group group=tbkProductService.getGroupById(Integer.parseInt(g));
    		String cookie=TaobaokeAPI.getCookie();
    		page.setCurrentPage(page.getCurrentPage()+1);
    		Integer fc=100-(group.getFc());
    		List<TbkProduct> plist=TaobaokeAPI.searchGoodsfromtblm(searchkey, page.getCurrentPage(), cookie);
    		for(TbkProduct p:plist){
    			p.setYjbl(df.format(((fc*p.getSrbl())/100)*aftertax));
    			p.setYgsr(df.format((((fc*p.getSrbl())/100)/100)*p.getSprice()*aftertax));
    		}
    		Integer totalRecord=1;
    		if(plist.size()>0){
    			totalRecord=plist.get(0).getItems();
    		}
    		//分页信息设置
    		page.setTotalRecord(totalRecord);

    		ResultInfo ri=new ResultInfo();
    		ri.setPage(page.getCurrentPage());
    		ri.setTotalPage(page.getTotalPage());
    		ri.setPlist(plist);
    		String json=JSON.toJSONString(ri);
        	response.setContentType("application/json");
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@RequestMapping("/lmtk")
    public String lmtk(HttpServletRequest request, HttpServletResponse response){
		String browser = "pc";
        // 判断访问设备
		if(request.getHeader("user-agent").indexOf("MicroMessenger")!=-1){
			browser = "wx";
		}else if(request.getHeader("user-agent").indexOf("Android")!=-1||
				 request.getHeader("user-agent").indexOf("iPhone")!=-1){
			browser="mobile";
		}
		String pid=this.getParaString("id",request);
		Integer gid=this.getParaInteger("g",request);
		String pname="";
		try {
			pname = URLDecoder.decode(this.getParaString("pname",request),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String imgurl=this.getParaString("imgurl", request);
		String price=this.getParaString("price", request);
		String sprice=this.getParaString("sprice", request);
		String tkrate=this.getParaString("srbl", request);
		
		//查找当日缓存
		TbkProduct tp=tbkProductService.getTlmSearch(pid, gid, DateUtil.getToday());
		
		//查找group
		Group group=tbkProductService.getGroupById(gid);
		if(tp==null){
			String cookie=TaobaokeAPI.getCookie();
			tp=new TbkProduct();
			tp.setImgurl(imgurl);
			tp.setPname(pname);
			tp.setPrice(Double.parseDouble(price));
			tp.setSprice(Double.parseDouble(sprice));
			Gaoy gy=TaobaokeAPI.getdxjh(pid, cookie);
			if(gy.getJhyj()!=null){
				tp.setSrbl(gy.getJhyj());
				tp.setIsjh(2);
			}else{
				tp.setSrbl(Double.parseDouble(tkrate));
				tp.setIsjh(0);
			}
			TlmData tld=TaobaokeAPI.gettblmurl(pid,group.getPid(), cookie);
			tp.setCtkl(tld.getCouponLinkTaoToken());
			tp.setClong(tld.getCouponShortLinkUrl());
			
			//存入缓存
			tp.setPid(Long.parseLong(pid));
			tp.setTkrate(tp.getSrbl()+"");
			tp.setGid(gid);
			tp.setCreatedate(DateUtil.getToday());
			tbkProductService.saveTlmSearch(tp);
		}
		
		if(tp!=null){
			tp.setSrbl(Double.parseDouble(tp.getTkrate()));
			Integer fc=100-(group.getFc());
			tp.setYjbl(df.format(((fc*tp.getSrbl())/100)*aftertax));
			tp.setYgsr(df.format((((fc*tp.getSrbl())/100)/100)*tp.getSprice()*aftertax));
		}
		
		request.setAttribute("tp",tp);
		request.setAttribute("g",gid);
		request.setAttribute("browser", browser);
		if(tp!=null){
			return "/lmtk";
		}else{
			return "/error";
		}
    }
	
	@RequestMapping("/lmtkbuy")
    public String lmtkbuy(HttpServletRequest request, HttpServletResponse response){
		String browser = "pc";
        // 判断访问设备
		if(request.getHeader("user-agent").indexOf("MicroMessenger")!=-1){
			browser = "wx";
		}else if(request.getHeader("user-agent").indexOf("Android")!=-1||
				 request.getHeader("user-agent").indexOf("iPhone")!=-1){
			browser="mobile";
		}
		String pid=this.getParaString("id",request);
		Integer gid=this.getParaInteger("g",request);
		String pname="";
		try {
			pname = URLDecoder.decode(this.getParaString("pname",request),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String imgurl=this.getParaString("imgurl", request);
		String price=this.getParaString("price", request);
		String sprice=this.getParaString("sprice", request);
		String tkrate=this.getParaString("srbl", request);
		
		//查找当日缓存
		TbkProduct tp=tbkProductService.getTlmSearch(pid, gid, DateUtil.getToday());
		
		//查找group
		Group group=tbkProductService.getGroupById(gid);
		if(tp==null){
			String cookie=TaobaokeAPI.getCookie();
			tp=new TbkProduct();
			tp.setImgurl(imgurl);
			tp.setPname(pname);
			tp.setPrice(Double.parseDouble(price));
			tp.setSprice(Double.parseDouble(sprice));
			Gaoy gy=TaobaokeAPI.getdxjh(pid, cookie);
			if(gy.getJhyj()!=null){
				tp.setSrbl(gy.getJhyj());
				tp.setIsjh(2);
			}else{
				tp.setSrbl(Double.parseDouble(tkrate));
				tp.setIsjh(0);
			}
			TlmData tld=TaobaokeAPI.gettblmurl(pid,group.getPid(), cookie);
			tp.setCtkl(tld.getCouponLinkTaoToken());
			tp.setClong(tld.getCouponShortLinkUrl());
			
			//存入缓存
			tp.setPid(Long.parseLong(pid));
			tp.setTkrate(tp.getSrbl()+"");
			tp.setGid(gid);
			tp.setCreatedate(DateUtil.getToday());
			tbkProductService.saveTlmSearch(tp);
		}
		
		if(tp!=null){
			tp.setSrbl(Double.parseDouble(tp.getTkrate()));
			Integer fc=100-(group.getFc());
			tp.setYjbl(df.format(((fc*tp.getSrbl())/100)*aftertax));
			tp.setYgsr(df.format((((fc*tp.getSrbl())/100)/100)*tp.getSprice()*aftertax));
		}
		
		request.setAttribute("tp",tp);
		request.setAttribute("g",gid);
		request.setAttribute("browser", browser);
		if(tp!=null){
			return "/lmtkbuy";
		}else{
			return "/error";
		}
    }
	

	@RequestMapping("/findbuy")
    public String findbuy(HttpServletRequest request, HttpServletResponse response){
		String browser = "pc";
        // 判断访问设备
		if(request.getHeader("user-agent").indexOf("MicroMessenger")!=-1){
			browser = "wx";
		}else if(request.getHeader("user-agent").indexOf("Android")!=-1||
				 request.getHeader("user-agent").indexOf("iPhone")!=-1){
			browser = "mobile";
		}
		String searchkey=this.getParaString("searchkey",request);
		Integer g=this.getParaInteger("g",request);
		if(g==0){
			g=1;
		}
		Group group=tbkProductService.getGroupById(g);
		String cookie=TaobaokeAPI.getCookie();
		Integer fc=100-(group.getFc());
		List<TbkProduct> plist=TaobaokeAPI.searchGoodsfromtblm(searchkey, page.getCurrentPage(), cookie);
		
		for(TbkProduct p:plist){
			p.setYjbl(df.format(((fc*p.getSrbl())/100)*aftertax));
			p.setYgsr(df.format((((fc*p.getSrbl())/100)/100)*p.getSprice()*aftertax));
		}
		
		Integer totalRecord=1;
		if(plist.size()>0){
			totalRecord=plist.get(0).getItems();
		}
		
		//分页信息设置
		page.setTotalRecord(totalRecord);
		//返回页面参数
		request.setAttribute("searchkey", searchkey);
		request.setAttribute("page",page);
		request.setAttribute("plist",plist);
		request.setAttribute("browser",browser);
		request.setAttribute("g", g);
		request.setAttribute("fc",fc);
		
		return "/findbuy";
    }
	
	@RequestMapping("/loadfindbuy")
    public void loadfindbuy(HttpServletRequest request, HttpServletResponse response){
		try {
        	String searchkey=URLDecoder.decode(this.getParaString("searchkey",request),"utf-8");
    		String g=this.getParaString("g",request);
    		if(g==null||g.equals("")||g.equals("0")){
    			g="1";
    		}
    		
    		Group group=tbkProductService.getGroupById(Integer.parseInt(g));
    		String cookie=TaobaokeAPI.getCookie();
    		page.setCurrentPage(page.getCurrentPage()+1);
    		Integer fc=100-(group.getFc());
    		List<TbkProduct> plist=TaobaokeAPI.searchGoodsfromtblm(searchkey, page.getCurrentPage(), cookie);
    		for(TbkProduct p:plist){
    			p.setYjbl(df.format(((fc*p.getSrbl())/100)*aftertax));
    			p.setYgsr(df.format((((fc*p.getSrbl())/100)/100)*p.getSprice()*aftertax));
    		}
    		Integer totalRecord=1;
    		if(plist.size()>0){
    			totalRecord=plist.get(0).getItems();
    		}
    		//分页信息设置
    		page.setTotalRecord(totalRecord);

    		ResultInfo ri=new ResultInfo();
    		ri.setPage(page.getCurrentPage());
    		ri.setTotalPage(page.getTotalPage());
    		ri.setPlist(plist);
    		String json=JSON.toJSONString(ri);
        	response.setContentType("application/json");
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	@RequestMapping("/manage")
    public String manage(HttpServletRequest request, HttpServletResponse response){
		String rs=this.getParaString("rs",request);
		List<TbkBanner> blist=tbkProductService.getBannerListAll();
		List<TbkType> tlist=tbkProductService.getTypeList();
		request.setAttribute("rs",rs);
		request.setAttribute("blist",blist);
		request.setAttribute("tlist",tlist);
        return "/manage";
    }
	
	@RequestMapping("/updatebanner")
    public String updatebanner(HttpServletRequest request, HttpServletResponse response){
		List <FileInfo> filelist=uploadFiles(new CommonsMultipartResolver(request.getSession().getServletContext()),"banner",request);
		List<TbkBanner> blist=tbkProductService.getBannerListAll();
		for(int i=0;i<blist.size();i++){
			//参数处理
			String bannerimg=filelist.get(i).getFilePath();
			String bannerurl=this.getParaString("bannerurl"+(i+1),request);
			String bannercolor=this.getParaString("bannercolor"+(i+1),request);
			String bannersort=this.getParaString("bannersort"+(i+1),request);
			String bannerisshow=this.getParaString("bannerisshow"+(i+1),request);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("aimg",bannerimg);
			map.put("aurl",bannerurl);
			map.put("acolor",bannercolor);
			map.put("sort",bannersort);
			map.put("isshow",bannerisshow);
			map.put("isshow",bannerisshow);
			map.put("id", (i+1));
			tbkProductService.updateTbkBanner(map);
		}
        return "redirect:manage?rs=5";
    }
	
	@RequestMapping("/tourl")
    public String tourl(HttpServletRequest request, HttpServletResponse response){
		System.out.println(DateUtil.getTodayTime()+":开始转换-----------------------------------------");

		String tburl=this.getParaString("tburl",request);
		//网址处理
		//tburl=TaobaokeAPI.getTbkUrl(tburl);
		Integer g=this.getParaInteger("g",request);
		if(g==null||g==0){
			g=1;
		}
		Group group=tbkProductService.getGroupById(g);
		TbkProduct tp=null;
		if(tburl!=null&&!tburl.equals("")){
			if(tburl.indexOf("￥")!=-1){
				tburl=tburl.substring(tburl.indexOf("￥")+1,tburl.lastIndexOf("￥"));
			}else if(tburl.indexOf("€")!=-1){
				tburl=tburl.substring(tburl.indexOf("€")+1,tburl.lastIndexOf("€"));
			}
			System.out.println(DateUtil.getTodayTime()+":1、处理后的tkl="+tburl+"");
			
			tburl=DataokeAPI.getUrlByTkl(tburl);
			System.out.println(DateUtil.getTodayTime()+":2、处理后的url="+tburl+"");
			String tpid=getPidFromUrl(tburl);
			System.out.println(DateUtil.getTodayTime()+":4、处理后的pid="+tpid+"");
			
			//String tpid=DataokeAPI.getUrlByTklApi("￥"+tburl+"￥");
			tp=TaobaokeAPI.getBestProduct(tpid, group);
			if(tp!=null){
				Integer fc=100-(group.getFc());
				tp.setYjbl(df.format(((fc*tp.getSrbl())/100)*aftertax));
				tp.setYgsr(df.format((((fc*tp.getSrbl())/100)/100)*tp.getSprice()*aftertax));
			}
			
		}
		System.out.println(DateUtil.getTodayTime()+":结束转换-----------------------------------------");
		
		request.setAttribute("tp",tp);
		request.setAttribute("g",g);
		request.setAttribute("tburl",tburl);
        return "/tourl";
    }
	
	public String getPidFromUrl(String url){
		try {
			if(url.indexOf("item.taobao.com/item.htm")!=-1||url.indexOf("detail.tmall.com/item.htm")!=-1){
				url=TaobaokeAPI.filterItemTaobao(url);
				System.out.println(DateUtil.getTodayTime()+":3、item.taobao.com");	
			}else if(url.indexOf("m.taobao.com")!=-1){
	        	url=url.substring(url.indexOf("m.taobao.com/i"),url.indexOf(".htm?"));
	        	url="http://item.taobao.com/item.htm?id="+url.replace("m.taobao.com/i","").replace(".htm?","");
				System.out.println(DateUtil.getTodayTime()+":3、m.taobao.com");
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
				HttpClient dclient=new HttpClient();
				GetMethod dg=new GetMethod(url);
		        dclient.executeMethod(dg);
		        String ds=dg.getResponseBodyAsString();

		        Pattern dp = Pattern.compile("itemId=.*?(\"|&)");
				Matcher dm = dp.matcher(ds);
				if(dm.find()) {
					url=dm.group(0).substring(7,dm.group(0).length()-1);           
			    } 
				url="http://item.taobao.com/item.htm?id="+url;
				System.out.println(DateUtil.getTodayTime()+":3、s.click.taobao.com");	
	        }else if(url.indexOf("uland.taobao.com")!=-1){
	        	HttpClient dclient=new HttpClient();
				GetMethod dg=new GetMethod(url.replace("coupon/edetail?","cp/coupon?"));
		        dclient.executeMethod(dg);
		        String s=dg.getResponseBodyAsString();
		        JSONObject jsonall = JSON.parseObject(s);
		        String result=jsonall.getString("result");
		        JSONObject jsonres = JSON.parseObject(result);
		        String item=jsonres.getString("item");
		        JSONObject jsonitem = JSON.parseObject(item);
		        url=jsonitem.getString("itemId");
		        url="http://item.taobao.com/item.htm?id="+url;
				System.out.println(DateUtil.getTodayTime()+":3、uland.taobao.com");
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
        return url.replace("http://item.taobao.com/item.htm?id=","");
	}
	
	@RequestMapping("/xq")
    public String detail(HttpServletRequest request, HttpServletResponse response){
		String browser = "pc";
        // 判断访问设备
		if(request.getHeader("user-agent").indexOf("MicroMessenger")!=-1){
			browser = "wx";
		}else if(request.getHeader("user-agent").indexOf("Android")!=-1||
				 request.getHeader("user-agent").indexOf("iPhone")!=-1){
			browser="mobile";
		}
		String pid=this.getParaString("id",request);
		Integer gid=this.getParaInteger("g",request);
		Integer stg=this.getParaInteger("stg",request);
		//查找group
		Group group=tbkProductService.getGroupById(gid);
		//查找商品
		TbkProduct tp=tbkProductService.getTbkProductByNiId(pid);

		if(tp!=null){
			//产品计划申请
			if(tp.getIsjh()==1){
				boolean res=DataokeAPI.getDataokeGy(tp.getDtkid()+"", tp.getPid()+"", group.getPid(), tp.getActivityid(),"1",DataokeAPI.getdtkcookie());
				System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",定向计划申请"+res);
				tp.setClong(DataokeAPI.getCoupon2to1(tp.getActivityid(),tp.getPid()+"",group.getPid()));
			}else if(tp.getIsjh()==2){
				//鹊桥
				if(tp.getClong().indexOf("activityId=")!=-1&&tp.getClong().indexOf("itemId=")!=-1){
					//没有转换过链接，开始转换
					TlmData td=TaobaokeAPI.getqqhdurl(tp.getPid()+"", group.getPid(),TaobaokeAPI.getCookie());
					if(td!=null){
						if(td.getCouponLink()!=null&&!td.getCouponLink().equals("")){
							TaobaokeAPI.udpateqqhdLink(tp.getPid()+"",td.getCouponLink().replace(group.getPid(),mypid));
							System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",鹊桥链接转换成功");
							tp.setClong(td.getCouponLink());
						}else{
							System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",鹊桥链接转换成功,但没有优惠券");
							tp.setClong(tp.getQuanlink());//领券
							tp.setTbklong(td.getClickUrl());//购买
							tp.setTkl(td.getTaoToken());//购买口令
							tp.setIsjh(5);
						}
					}else{
						System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",鹊桥链接转换失败");
						tp.setClong(DataokeAPI.getCoupon2to1(tp.getActivityid(),tp.getPid()+"",group.getPid()));
					}
				}else{
					tp.setClong(tp.getClong().replace(mypid, group.getPid()));
					System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",鹊桥链接已转换");
				}
			}else if(tp.getIsjh()==3){
				//营销计划
				if(tp.getClong().indexOf("activityId=")!=-1&&tp.getClong().indexOf("itemId=")!=-1){
					//没有转换过链接，开始转换
					TlmData td=TaobaokeAPI.gettblmurl(tp.getPid()+"", group.getPid(),TaobaokeAPI.getCookie());
					if(td!=null){
						if(td.getCouponLink()!=null&&!td.getCouponLink().equals("")){
							TaobaokeAPI.udpateqqhdLink(tp.getPid()+"",td.getCouponLink().replace(group.getPid(),mypid));
							System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",营销计划链接转换成功");
							tp.setClong(td.getCouponLink());
						}else{
							System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",营销计划链接转换成功,但没有优惠券");
							tp.setClong(tp.getQuanlink());//领券
							tp.setTbklong(td.getClickUrl());//购买
							tp.setTkl(td.getTaoToken());//购买口令
							tp.setIsjh(5);
						}
					}else{
						System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",营销计划链接转换失败");
						tp.setClong(DataokeAPI.getCoupon2to1(tp.getActivityid(),tp.getPid()+"",group.getPid()));
					}
				}else{
					tp.setClong(tp.getClong().replace(mypid, group.getPid()));
					System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",营销计划已转换");
				}
			}else{
				System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",通用计划无需处理");
			}
			
			//佣金比率转换
			Integer fc=100-(group.getFc());
			tp.setYjbl(df.format(((fc*tp.getSrbl())/100)*aftertax));
			tp.setYgsr(df.format((((fc*tp.getSrbl())/100)/100)*tp.getSprice()*aftertax));
		}
		request.setAttribute("tp",tp);
		request.setAttribute("stg",stg);
		request.setAttribute("browser", browser);
		if(tp!=null){
			return "/detail";
		}else{
			return "/error";
		}
    }
	
	@RequestMapping("/tk")
    public String tk(HttpServletRequest request, HttpServletResponse response){
		String browser = "pc";
        // 判断访问设备
		if(request.getHeader("user-agent").indexOf("MicroMessenger")!=-1){
			browser = "wx";
		}else if(request.getHeader("user-agent").indexOf("Android")!=-1||
				 request.getHeader("user-agent").indexOf("iPhone")!=-1){
			browser="mobile";
		}
		String pid=this.getParaString("id",request);
		Integer gid=this.getParaInteger("g",request);
		Integer stg=this.getParaInteger("stg",request);
		//查找group
		Group group=tbkProductService.getGroupById(gid);
		//查找商品
		TbkProduct tp=tbkProductService.getTbkProductByNiId(pid);
		
		if(tp!=null){
			//产品计划申请
			if(tp.getIsjh()==1){
				boolean res=DataokeAPI.getDataokeGy(tp.getDtkid()+"", tp.getPid()+"", group.getPid(), tp.getActivityid(),"1",DataokeAPI.getdtkcookie());
				System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",定向计划申请"+res);
				tp.setClong(DataokeAPI.getCoupon2to1(tp.getActivityid(),tp.getPid()+"",group.getPid()));
			}else if(tp.getIsjh()==2){
				//鹊桥
				if(tp.getClong().indexOf("activityId=")!=-1&&tp.getClong().indexOf("itemId=")!=-1){
					//没有转换过链接，开始转换
					TlmData td=TaobaokeAPI.getqqhdurl(tp.getPid()+"", group.getPid(),TaobaokeAPI.getCookie());
					if(td!=null){
						if(td.getCouponLink()!=null&&!td.getCouponLink().equals("")){
							TaobaokeAPI.udpateqqhdLink(tp.getPid()+"",td.getCouponLink().replace(group.getPid(),mypid));
							System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",鹊桥链接转换成功");
							tp.setClong(td.getCouponLink());
						}else{
							System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",鹊桥链接转换成功,但没有优惠券");
							tp.setClong(tp.getQuanlink());//领券
							tp.setTbklong(td.getClickUrl());//购买
							tp.setTkl(td.getTaoToken());//购买口令
							tp.setIsjh(5);
						}
					}else{
						System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",鹊桥链接转换失败");
						tp.setClong(DataokeAPI.getCoupon2to1(tp.getActivityid(),tp.getPid()+"",group.getPid()));
					}
				}else{
					tp.setClong(tp.getClong().replace(mypid, group.getPid()));
					System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",鹊桥链接已转换");
				}
			}else if(tp.getIsjh()==3){
				//营销计划
				if(tp.getClong().indexOf("activityId=")!=-1&&tp.getClong().indexOf("itemId=")!=-1){
					//没有转换过链接，开始转换
					TlmData td=TaobaokeAPI.gettblmurl(tp.getPid()+"", group.getPid(),TaobaokeAPI.getCookie());
					if(td!=null){
						if(td.getCouponLink()!=null&&!td.getCouponLink().equals("")){
							TaobaokeAPI.udpateqqhdLink(tp.getPid()+"",td.getCouponLink().replace(group.getPid(),mypid));
							System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",营销计划链接转换成功");
							tp.setClong(td.getCouponLink());
						}else{
							System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",营销计划链接转换成功,但没有优惠券");
							tp.setClong(tp.getQuanlink());//领券
							tp.setTbklong(td.getClickUrl());//购买
							tp.setTkl(td.getTaoToken());//购买口令
							tp.setIsjh(5);
						}
					}else{
						System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",营销计划链接转换失败");
						tp.setClong(DataokeAPI.getCoupon2to1(tp.getActivityid(),tp.getPid()+"",group.getPid()));
					}
				}else{
					tp.setClong(tp.getClong().replace(mypid, group.getPid()));
					System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",营销计划已转换");
				}
			}else{
				System.out.println(DateUtil.getTodayTime()+":"+tp.getPid()+",通用计划无需处理");
			}
			
			//佣金比率转换
			Integer fc=100-(group.getFc());
			tp.setYjbl(df.format(((fc*tp.getSrbl())/100)*aftertax));
			tp.setYgsr(df.format((((fc*tp.getSrbl())/100)/100)*tp.getSprice()*aftertax));

			//淘口令转换
			String tkl="";
			if(tp.getIsjh()==5){
				tkl=TaobaokeAPI.getTkl(tp.getClong(),tp.getPname(),tp.getImgurl());
			}else{
				tkl=TaobaokeAPI.getTkl(tp.getClong(),tp.getPname(),tp.getImgurl());
			}
			tp.setCtkl(tkl);
			System.out.println(DateUtil.getTodayTime()+":用户"+gid+"的淘口令="+tkl);
		}
		
		
		if(tp!=null){
			Integer isgroupsend=0;
			TbkProduct p=TaobaokeAPI.getGroupSend(tp.getId());
			if(p!=null){
				isgroupsend=1;
			}
			
			request.setAttribute("isgroupsend",isgroupsend);
			request.setAttribute("tp",tp);
			request.setAttribute("g",gid);
			request.setAttribute("stg",stg);
			request.setAttribute("browser", browser);
			return "/tk";
		}else{
			return "/error";
		}
    }
	
	@RequestMapping("/daysend")
    public String daysend(HttpServletRequest request, HttpServletResponse response){
		String browser = "pc";
        // 判断访问设备
		if(request.getHeader("user-agent").indexOf("MicroMessenger")!=-1){
			browser = "wx";
		}else if(request.getHeader("user-agent").indexOf("Android")!=-1||
				 request.getHeader("user-agent").indexOf("iPhone")!=-1){
			browser="mobile";
		}
		Integer gid=this.getParaInteger("g",request);
		List<TbkDaySend> sendlist=new ArrayList<TbkDaySend>();
		if(gid!=null&&gid!=0){
			Group g=tbkProductService.getGroupById(gid);
			
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("pageStart", 0);
			map.put("pageSize", 100);
			sendlist=tbkProductService.getTbkDaySend(map);
			for(TbkDaySend tds:sendlist){
				String curl=DataokeAPI.getCoupon2to1(tds.getActivityid(),tds.getPid()+"",g.getPid());
				String ctkl=TaobaokeAPI.getTkl(curl,tds.getPname(),tds.getImgurl());
				tds.setCtkl(ctkl);
			}
			
		}
		request.setAttribute("sendlist",sendlist);
		request.setAttribute("g",gid);
		request.setAttribute("browser", browser);
		return "/daysend";
    }
	
	@RequestMapping("/addsend")
    public void addsend(HttpServletRequest request, HttpServletResponse response){
		String rs="toomany";
		try {
			Integer id=this.getParaInteger("id",request);
			Integer g=this.getParaInteger("g", request);
			Integer c=TaobaokeAPI.getGroupSendId(id);
			if(c==0){
				TbkProduct tp=TaobaokeAPI.getProductById(id);
				Integer num=TaobaokeAPI.getGroupSendType(tp.getType());
				if(num<6){
					tbkProductService.saveTbkGroupSend(id);
					System.out.println(DateUtil.getTodayTime()+":id="+id+"加入群发列表成功(代理"+g+")");
					rs="success";
				}else{
					System.out.println(DateUtil.getTodayTime()+":id="+id+"加入群发列表超量(代理"+g+")");
				}
			}else{
				rs="has";
			}
			response.getWriter().write(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public void saveProduct(DataokeProduct dp){
		//获取类型
		List<TbkType> tlist=tbkProductService.getTypeList();
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
    	
		TbkProduct tp=new TbkProduct();
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
		tp.setTbkshort("");
		tp.setTotalcnum(dp.getQuan_receive()+dp.getQuan_surplus());
		tp.setType(typename);
		
		Map<String,Object> map=new HashMap<String,Object>();
        map.put("pid",dp.getGoodsID());
        map.put("type",typename);
        TbkProduct p=tbkProductService.getTbkProductByPid(map);
        if(p==null){
        	tbkProductService.saveTbkProductObj(tp);
        }else{
        	tp=new TbkProduct();
        	tp.setId(p.getId());
        	tp.setCreatedate(DateUtil.getToday());
        	tbkProductService.updateTbkProductDate(tp);
        }
	}

	/**
	 * 2级代理订单查询及佣金计算
	 * @param group
	 * @param date
	 * @return
	 */
	public List<TbkSr> addDlsr(Group group,String sdate,String edate,String orderno){
		List<TbkSr> srlist=new ArrayList<TbkSr>();
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("stime",sdate+" 00:00:00");
		m.put("etime",edate+" 23:59:59");
		m.put("orderno", orderno);
		List<Group> gadlist=tbkProductService.getGroupByPid(group.getId());
		for(Group gp:gadlist){
			//1级代理查询
			m.put("adname",gp.getWxname());
			List<TbkSr> gslist=tbkProductService.getTbkSrListByTime(m);
			for(TbkSr sr:gslist){
				Double yg=Double.parseDouble(sr.getYg());
				Double yj=Double.parseDouble(sr.getYj());
				//1级代理分成25%
				Double dfc=group.getDlfc()/100.0;
				sr.setYg(df.format(yg*dfc));
				sr.setYj(df.format(yj*dfc));
				srlist.add(sr);
			}
			/************************************/
			if(gp.getIszd()!=1){
				List<Group> sadlist=tbkProductService.getGroupByPid(gp.getId());
				for(Group g:sadlist){
					//2级代理查询
					m.put("adname",g.getWxname());
					List<TbkSr> sslist=tbkProductService.getTbkSrListByTime(m);
					for(TbkSr sr:sslist){
						Double yg=Double.parseDouble(sr.getYg());
						Double yj=Double.parseDouble(sr.getYj());
						//2级代理分成15%
						Double dfc=group.getNdlfc()/100.0;
						sr.setYg(df.format(yg*dfc));
						sr.setYj(df.format(yj*dfc));
						srlist.add(sr);
					}
				}
			}
		}
		return srlist;
	}
	
	/**
	 * 自己的订单查询及佣金计算
	 * @param group
	 * @param date
	 * @return
	 */
	public List<TbkSr> addSelfDlsr(Group group,String sdate,String edate,String orderno){
		List<TbkSr> srlist=new ArrayList<TbkSr>();
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("stime",sdate+" 00:00:00");
		m.put("etime",edate+" 23:59:59");
		m.put("orderno", orderno);
		List<Group> g1list=tbkProductService.getGroupByPid(group.getId());
		for(Group g1:g1list){
			//1级代理查询
			m.put("adname",g1.getWxname());
			List<TbkSr> s1list=tbkProductService.getTbkSrListByTime(m);
			for(TbkSr sr:s1list){
				Double yg=Double.parseDouble(sr.getYg());
				Double yj=Double.parseDouble(sr.getYj());
				//1级代理分成
				Double dfc=g1.getFc()/100.0;
				sr.setYg(df.format(yg*dfc));
				sr.setYj(df.format(yj*dfc));
				srlist.add(sr);
			}
			
			/************************************/
			List<Group> g2list=tbkProductService.getGroupByPid(g1.getId());
			for(Group g2:g2list){
				//2级代理查询
				m.put("adname",g2.getWxname());
				List<TbkSr> s2list=tbkProductService.getTbkSrListByTime(m);
				for(TbkSr sr:s2list){
					Double yg=Double.parseDouble(sr.getYg());
					Double yj=Double.parseDouble(sr.getYj());
					//2级代理分成25%
					Double dfc=group.getDlfc()/100.0;
					sr.setYg(df.format(yg*dfc));
					sr.setYj(df.format(yj*dfc));
					srlist.add(sr);
				}
				
				/************************************/
				List<Group> g3list=tbkProductService.getGroupByPid(g2.getId());
				for(Group g3:g3list){
					//3级代理查询
					m.put("adname",g3.getWxname());
					List<TbkSr> s3list=tbkProductService.getTbkSrListByTime(m);
					for(TbkSr sr:s3list){
						Double yg=Double.parseDouble(sr.getYg());
						Double yj=Double.parseDouble(sr.getYj());

						//查询上级代理是否是直代
						Group gl3=tbkProductService.getGroupById(g3.getParentid());
						Double dfc=0.0;
						if(gl3.getIszd()==1){
							//直代按25%分成
							dfc=group.getDlfc()/100.0;
						}else{
							//3级代理分成20%
							dfc=group.getNdlfc()/100.0;
						}
						
						sr.setYg(df.format(yg*dfc));
						sr.setYj(df.format(yj*dfc));
						srlist.add(sr);
					}
					
					/************************************/
					List<Group> g4list=tbkProductService.getGroupByPid(g3.getId());
					for(Group g4:g4list){
						//4级代理查询
						m.put("adname",g4.getWxname());
						List<TbkSr> s4list=tbkProductService.getTbkSrListByTime(m);
						for(TbkSr sr:s4list){
							Double yg=Double.parseDouble(sr.getYg());
							Double yj=Double.parseDouble(sr.getYj());
							
							//查询上级代理是否是直代
							Group gl4=tbkProductService.getGroupById(g4.getParentid());
							Double dfc=0.0;
							if(gl4.getIszd()==1){
								//直代按25%分成
								dfc=group.getDlfc()/100.0;
							}else{
								//4级代理分成20%
								dfc=group.getNdlfc()/100.0;
							}

							sr.setYg(df.format(yg*dfc));
							sr.setYj(df.format(yj*dfc));
							srlist.add(sr);
						}
						
						/************************************/
						List<Group> g5list=tbkProductService.getGroupByPid(g4.getId());
						for(Group g5:g5list){
							//5级代理查询
							m.put("adname",g5.getWxname());
							List<TbkSr> s5list=tbkProductService.getTbkSrListByTime(m);
							for(TbkSr sr:s5list){
								Double yg=Double.parseDouble(sr.getYg());
								Double yj=Double.parseDouble(sr.getYj());

								//查询上级代理是否是直代
								Group gl5=tbkProductService.getGroupById(g5.getParentid());
								Double dfc=0.0;
								if(gl5.getIszd()==1){
									//直代按25%分成
									dfc=group.getDlfc()/100.0;
								}else{
									//5级代理分成20%
									dfc=group.getNdlfc()/100.0;
								}
								
								sr.setYg(df.format(yg*dfc));
								sr.setYj(df.format(yj*dfc));
								srlist.add(sr);
							}
							
							/************************************/
							List<Group> g6list=tbkProductService.getGroupByPid(g5.getId());
							for(Group g6:g6list){
								//6级代理查询
								m.put("adname",g6.getWxname());
								List<TbkSr> s6list=tbkProductService.getTbkSrListByTime(m);
								for(TbkSr sr:s6list){
									Double yg=Double.parseDouble(sr.getYg());
									Double yj=Double.parseDouble(sr.getYj());

									//查询上级代理是否是直代
									Group gl6=tbkProductService.getGroupById(g6.getParentid());
									Double dfc=0.0;
									if(gl6.getIszd()==1){
										//直代按25%分成
										dfc=group.getDlfc()/100.0;
									}else{
										//6级代理分成20%
										dfc=group.getNdlfc()/100.0;
									}
									
									sr.setYg(df.format(yg*dfc));
									sr.setYj(df.format(yj*dfc));
									srlist.add(sr);
								}
							}
						}
					}
				}
			}
		}
		return srlist;
	}
	
	/**
	 * 收入列表按时间排序
	 * @param srlist
	 * @return
	 */
	public List<TbkSr> sortList(List<TbkSr> srlist){
		Collections.sort(srlist, new Comparator<TbkSr>() {
			@Override
			public int compare(TbkSr f1, TbkSr f2) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d1=new Date();
				Date d2=new Date();
				try {
					d1=sdf.parse(f1.getCreatetime());
					d2=sdf.parse(f2.getCreatetime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				if(d1.after(d2)) {
					return -1;
				}
				return 1;
			}
		});
		return srlist;
	}
	
	//今日报表
	@RequestMapping("/sr")
    public String sr(HttpServletRequest request, HttpServletResponse response){
		Integer id=this.getParaInteger("id",request);
		Integer flag=this.getParaInteger("flag",request);
		String orderno=this.getParaString("orderno",request);
		Integer isshow=1;
		if(id==1&&flag!=86){
			isshow=0;
		}
		
		Integer status=this.getParaInteger("status",request);
		String s="";
		if(status==1){
			s="订单付款";
		}else if(status==2){
			s="订单结算";
		}else if(status==3){
			s="订单失效";
		}
		
		Group group=tbkProductService.getGroupById(id);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("stime",DateUtil.getToday()+" 00:00:00");
		map.put("etime",DateUtil.getToday()+" 23:59:59");
		map.put("adname",group.getWxname());
		map.put("orderno",orderno);
		map.put("status",s);
		List<TbkSr> srlist=tbkProductService.getTbkSrListByTime(map);
		Double js=0.0;
		Double ygj=0.0;
		Integer fk=0;
		for(TbkSr sr:srlist){
			Double yg=Double.parseDouble(sr.getYg())*((100.0-Double.parseDouble(group.getFc()+""))/100);
			Double yj=Double.parseDouble(sr.getYj())*((100.0-Double.parseDouble(group.getFc()+""))/100);
			sr.setYg(df.format(yg)+"");
			sr.setYj(df.format(yj)+"");
			ygj+=yg;//预估金额
			js+=yj;//结算金额
			fk+=1;//付款数
		}
		
		//查询下级代理
		List<Group> glist=tbkProductService.getGroupByPid(group.getId());
		
		//代理佣金计算
		Double djs=0.0;
		Double dygj=0.0;
		
		//代理收入及订单
		if(group.getId()==1){
			//自己的查询
			List<TbkSr> list=addSelfDlsr(group,DateUtil.getToday(),DateUtil.getToday(),orderno);
			for(TbkSr sr:list){
				
				if(s!=null&&!s.equals("")){
					if(sr.getStatus().equals(s)){
						srlist.add(sr);
						dygj+=Double.parseDouble(sr.getYg());
						djs+=Double.parseDouble(sr.getYj());
						fk+=1;
					}
				}else{
					srlist.add(sr);
					dygj+=Double.parseDouble(sr.getYg());
					djs+=Double.parseDouble(sr.getYj());
					fk+=1;
				}
			}
		}else{
			//2级代理查询
			List<TbkSr> list=addDlsr(group,DateUtil.getToday(),DateUtil.getToday(),orderno);
			for(TbkSr sr:list){
				
				if(s!=null&&!s.equals("")){
					if(sr.getStatus().equals(s)){
						srlist.add(sr);
						dygj+=Double.parseDouble(sr.getYg());
						djs+=Double.parseDouble(sr.getYj());
						fk+=1;
					}
				}else{
					srlist.add(sr);
					dygj+=Double.parseDouble(sr.getYg());
					djs+=Double.parseDouble(sr.getYj());
					fk+=1;
				}
			}
		}
		
		//收入列表按时间排序
		srlist=sortList(srlist);
		
		ygj+=dygj;
		js+=djs;
		TbkPoint tp=new TbkPoint();
		tp.setFk(fk+"");
		tp.setXg(df.format(ygj)+"");
		tp.setSr(df.format(js)+"");
		request.setAttribute("srlist",srlist);
		request.setAttribute("tp",tp);
		request.setAttribute("group",group);
		request.setAttribute("dygj",df.format(dygj));
		request.setAttribute("djs",df.format(djs));
		request.setAttribute("glist",glist);
		request.setAttribute("flag",flag);
		request.setAttribute("id",id);
		request.setAttribute("orderno",orderno);
		request.setAttribute("status", status);
		if(isshow==1){
			return "/sr";
		}else{
			return "/error";
		}
    }
	
	//本月报表
	@RequestMapping("/dd")
    public String dd(HttpServletRequest request, HttpServletResponse response){
		Integer id=this.getParaInteger("id",request);
		Integer flag=this.getParaInteger("flag",request);
		String orderno=this.getParaString("orderno",request);
		Integer isshow=1;
		if(id==1&&flag!=86){
			isshow=0;
		}
		
		Integer status=this.getParaInteger("status",request);
		String s="";
		if(status==1){
			s="订单付款";
		}else if(status==2){
			s="订单结算";
		}else if(status==3){
			s="订单失效";
		}
		
		Group group=tbkProductService.getGroupById(id);

		Map<String,Object> map=new HashMap<String,Object>();
		map.put("stime",DateUtil.getMonthFirst()+" 00:00:00");
		map.put("etime",DateUtil.getMonthLast()+" 23:59:59");
		map.put("adname",group.getWxname());
		map.put("orderno",orderno);
		map.put("status",s);
		List<TbkSr> srlist=tbkProductService.getTbkSrListByTime(map);
		Double js=0.0;
		Double ygj=0.0;
		Integer fk=0;
		for(TbkSr sr:srlist){
			Double yg=Double.parseDouble(sr.getYg())*((100.0-Double.parseDouble(group.getFc()+""))/100);
			Double yj=Double.parseDouble(sr.getYj())*((100.0-Double.parseDouble(group.getFc()+""))/100);
			sr.setYg(df.format(yg)+"");
			sr.setYj(df.format(yj)+"");
			ygj+=yg;//预估金额
			js+=yj;//结算金额
			fk+=1;//付款数
		}
		
		//查询下级代理
		List<Group> glist=tbkProductService.getGroupByPid(group.getId());
		
		//代理佣金计算
		Double djs=0.0;
		Double dygj=0.0;
		//主页面查询所有代理
		if(group.getId()==1){
			//自己的查询
			List<TbkSr> list=addSelfDlsr(group,DateUtil.getMonthFirst(),DateUtil.getMonthLast(),orderno);
			for(TbkSr sr:list){
				if(s!=null&&!s.equals("")){
					if(sr.getStatus().equals(s)){
						srlist.add(sr);
						dygj+=Double.parseDouble(sr.getYg());
						djs+=Double.parseDouble(sr.getYj());
						fk+=1;
					}
				}else{
					srlist.add(sr);
					dygj+=Double.parseDouble(sr.getYg());
					djs+=Double.parseDouble(sr.getYj());
					fk+=1;
				}
			}
		}else{
			//2级代理查询
			List<TbkSr> list=addDlsr(group,DateUtil.getMonthFirst(),DateUtil.getMonthLast(),orderno);
			for(TbkSr sr:list){
				if(s!=null&&!s.equals("")){
					if(sr.getStatus().equals(s)){
						srlist.add(sr);
						dygj+=Double.parseDouble(sr.getYg());
						djs+=Double.parseDouble(sr.getYj());
						fk+=1;
					}
				}else{
					srlist.add(sr);
					dygj+=Double.parseDouble(sr.getYg());
					djs+=Double.parseDouble(sr.getYj());
					fk+=1;
				}
			}
		}
		
		//收入列表按时间排序
		srlist=sortList(srlist);
		
		ygj+=dygj;
		js+=djs;
		TbkPoint tp=new TbkPoint();
		tp.setFk(fk+"");
		tp.setXg(df.format(ygj)+"");
		tp.setSr(df.format(js)+"");
		request.setAttribute("srlist",srlist);
		request.setAttribute("tp",tp);
		request.setAttribute("group",group);
		request.setAttribute("dygj",df.format(dygj));
		request.setAttribute("djs",df.format(djs));
		request.setAttribute("glist",glist);
		request.setAttribute("flag",flag);
		request.setAttribute("id",id);
		request.setAttribute("orderno",orderno);
		request.setAttribute("status", status);
		if(isshow==1){
			 return "/dd";
		}else{
			 return "/error";
		}
       
    }
	
	//上个月报表
	@RequestMapping("/ldd")
    public String ldd(HttpServletRequest request, HttpServletResponse response){
		Integer id=this.getParaInteger("id",request);
		Integer flag=this.getParaInteger("flag",request);
		String orderno=this.getParaString("orderno",request);
		
		Integer isshow=1;
		if(id==1&&flag!=86){
			isshow=0;
		}
		
		Integer status=this.getParaInteger("status",request);
		String s="";
		if(status==1){
			s="订单付款";
		}else if(status==2){
			s="订单结算";
		}else if(status==3){
			s="订单失效";
		}
		
		Group group=tbkProductService.getGroupById(id);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("stime",DateUtil.getLastMonthFirst()+" 00:00:00");
		map.put("etime",DateUtil.getLastMonthLast()+" 23:59:59");
		map.put("adname",group.getWxname());
		map.put("orderno",orderno);
		map.put("status",s);
		List<TbkSr> srlist=tbkProductService.getTbkSrListByTime(map);
		Double js=0.0;
		Double ygj=0.0;
		Integer fk=0;
		for(TbkSr sr:srlist){
			Double yg=Double.parseDouble(sr.getYg())*((100.0-Double.parseDouble(group.getFc()+""))/100);
			Double yj=Double.parseDouble(sr.getYj())*((100.0-Double.parseDouble(group.getFc()+""))/100);
			sr.setYg(df.format(yg)+"");
			sr.setYj(df.format(yj)+"");
			ygj+=yg;//预估金额
			js+=yj;//结算金额
			fk+=1;//付款数
		}
		
		//子代理佣金查询
		List<Group> glist=tbkProductService.getGroupByPid(group.getId());
		
		//代理佣金计算
		Double djs=0.0;
		Double dygj=0.0;
		//主页面查询所有代理
		if(group.getId()==1){
			//自己的查询
			List<TbkSr> list=addSelfDlsr(group,DateUtil.getLastMonthFirst(),DateUtil.getLastMonthLast(),orderno);
			for(TbkSr sr:list){
				
				if(s!=null&&!s.equals("")){
					if(sr.getStatus().equals(s)){
						srlist.add(sr);
						dygj+=Double.parseDouble(sr.getYg());
						djs+=Double.parseDouble(sr.getYj());
						fk+=1;
					}
				}else{
					srlist.add(sr);
					dygj+=Double.parseDouble(sr.getYg());
					djs+=Double.parseDouble(sr.getYj());
					fk+=1;
				}
			}
		}else{
			//2级代理查询
			List<TbkSr> list=addDlsr(group,DateUtil.getLastMonthFirst(),DateUtil.getLastMonthLast(),orderno);
			for(TbkSr sr:list){
				
				if(s!=null&&!s.equals("")){
					if(sr.getStatus().equals(s)){
						srlist.add(sr);
						dygj+=Double.parseDouble(sr.getYg());
						djs+=Double.parseDouble(sr.getYj());
						fk+=1;
					}
				}else{
					srlist.add(sr);
					dygj+=Double.parseDouble(sr.getYg());
					djs+=Double.parseDouble(sr.getYj());
					fk+=1;
				}
			}
		}
		
		//收入列表按时间排序
		srlist=sortList(srlist);
		
		ygj+=dygj;
		js+=djs;
		TbkPoint tp=new TbkPoint();
		tp.setFk(fk+"");
		tp.setXg(df.format(ygj)+"");
		tp.setSr(df.format(js)+"");
		request.setAttribute("srlist",srlist);
		request.setAttribute("tp",tp);
		request.setAttribute("group",group);
		request.setAttribute("dygj",df.format(dygj));
		request.setAttribute("djs",df.format(djs));
		request.setAttribute("glist",glist);
		request.setAttribute("flag",flag);
		request.setAttribute("id",id);
		request.setAttribute("orderno",orderno);
		request.setAttribute("status", status);
		if(isshow==1){
			 return "/ldd";
		}else{
			 return "/error";
		}
      
    }
	
	//获取产品分类
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
}
