package com.limon.base.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.limon.base.model.SysUser;
import com.limon.base.model.TbkProduct;
import com.limon.base.model.Ukey;
import com.limon.base.service.SysUserService;
import com.limon.base.service.TbkProductService;
import com.limon.base.taobao.Group;
import com.limon.base.taobao.TaobaokeAPI;
import com.limon.base.taobao.TbkItem;
import com.limon.base.taobao.TbkItemRecommend;

@Controller
@RequestMapping("/api")
public class HttpController extends BaseController{
	@Autowired
	private TbkProductService tbkProductService;
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response){
		try{
			Integer start=this.getParaInteger("start",request);//换一换的次数
			String skey=this.getParaString("skey",request);//查询关键字
			String gname=this.getParaString("gname",request);//群组名称
			String ukey=this.getParaString("ukey",request);//用户授权key
			APIResponse rsp=new APIResponse();
			
			//用户身份验证
			SysUser u=sysUserService.getUserByUkey(ukey);
			if(u!=null){
				//查找群信息
				Group g=tbkProductService.getGroupByNameAndUid(gname, u.getId());
				if(g!=null){
					//在产品库搜索产品
					TbkProduct tp=tbkProductService.searchTbkProduct(skey,start);
					//验证产品查询结果
					if(tp!=null){
						tp=TaobaokeAPI.getBestProduct(tp.getPid()+"", g);
						rsp.setResult(1);
						rsp.setErrormsg("");
						rsp.setProduct(tp);
						rsp.setGroup(g);
					}else{
						rsp.setResult(0);
						rsp.setErrormsg("商品未找到");
					}
				}else{
					rsp.setResult(0);
					rsp.setErrormsg("请在平台添加群信息");
				}
			}else{
				rsp.setResult(0);
				rsp.setErrormsg("ukey不存在");
			}
			//返回数据	
			String pjson=JSON.toJSONString(rsp);
			PrintWriter pw=response.getWriter();
			pw.write(pjson);
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/findgroup")
	public void findgroup(HttpServletRequest request, HttpServletResponse response){
		try{
			String gname=this.getParaString("gname",request);//群组名称
			String ukey=this.getParaString("ukey",request);//用户授权key
			APIResponse rsp=new APIResponse();
			
			//用户身份验证
			SysUser u=sysUserService.getUserByUkey(ukey);
			if(u!=null){
				//查找群信息
				Group g=tbkProductService.getGroupByNameAndUid(gname, u.getId());
				if(g!=null){
					rsp.setResult(1);
					rsp.setGroup(g);
				}else{
					rsp.setResult(0);
					rsp.setErrormsg("请在平台添加群信息");
				}
			}else{
				rsp.setResult(0);
				rsp.setErrormsg("ukey不存在");
			}
			
			//返回数据	
			String pjson=JSON.toJSONString(rsp);
			PrintWriter pw=response.getWriter();
			pw.write(pjson);
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getgroup")
	public void getgroup(HttpServletRequest request, HttpServletResponse response){
		try{
			Integer gid=this.getParaInteger("gid",request);//群组名称
			String ukey=this.getParaString("ukey",request);//用户授权key
			APIResponse rsp=new APIResponse();
			
			//用户身份验证
			SysUser u=sysUserService.getUserByUkey(ukey);
			if(u!=null){
				//查找群信息
				Group g=tbkProductService.getGroupById(gid);
				if(g!=null){
					rsp.setResult(1);
					rsp.setGroup(g);
				}else{
					rsp.setResult(0);
					rsp.setErrormsg("请在平台添加群信息");
				}
			}else{
				rsp.setResult(0);
				rsp.setErrormsg("ukey不存在");
			}
			
			//返回数据	
			String pjson=JSON.toJSONString(rsp);
			PrintWriter pw=response.getWriter();
			pw.write(pjson);
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getukey")
	public void getukey(HttpServletRequest request, HttpServletResponse response){
		try{
			String ukey=this.getParaString("ukey",request);//用户授权key
			APIResponse rsp=new APIResponse();
			
			//用户身份验证
			SysUser u=sysUserService.getUserByUkey(ukey);
			if(u!=null){
				Ukey key=new Ukey();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if(u.getStime()!=null&&u.getEtime()!=null){
					Date stime=sdf.parse(u.getStime());
					Date etime=sdf.parse(u.getEtime());
					Date now=new Date();
					if(now.getTime()>=stime.getTime()&&now.getTime()<=etime.getTime()){
						key.setStatus(1);
						key.setUkey(u.getUkey());
					}else if(now.getTime()<stime.getTime()){
						key.setStatus(3);
						key.setUkey(u.getUkey());
					}else if(now.getTime()>etime.getTime()){
						key.setStatus(2);
						key.setUkey(u.getUkey());
					}else{
						key.setStatus(4);
						key.setUkey(u.getUkey());
					}
				}else{
					key.setStatus(1);
					key.setUkey(u.getUkey());
				}
				rsp.setUkey(key);
				rsp.setResult(1);
				rsp.setErrormsg("");
			}else{
				rsp.setResult(0);
				rsp.setErrormsg("ukey不存在");
			}
			
			//返回数据	
			String pjson=JSON.toJSONString(rsp);
			PrintWriter pw=response.getWriter();
			pw.write(pjson);
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getitem")
	public void getitem(HttpServletRequest request, HttpServletResponse response){
		try{
			String ukey=this.getParaString("ukey",request);//用户授权key
			String skey=this.getParaString("skey",request);//用户授权key
			Integer pageno=this.getParaInteger("pageno",request);//用户授权key
			Integer pagesize=this.getParaInteger("pagesize",request);//用户授权key
			APIResponse rsp=new APIResponse();
			
			//用户身份验证
			SysUser u=sysUserService.getUserByUkey(ukey);
			if(u!=null){
				TbkItem ti=TaobaokeAPI.getTbkItemInfo(skey, pageno, pagesize);
				rsp.setResult(1);
				rsp.setErrormsg("");
				rsp.setItem(ti);
			}else{
				rsp.setResult(0);
				rsp.setErrormsg("ukey不存在");
			}
			
			//返回数据	
			String pjson=JSON.toJSONString(rsp);
			PrintWriter pw=response.getWriter();
			pw.write(pjson);
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@RequestMapping("/getrecommend")
	public void getrecommend(HttpServletRequest request, HttpServletResponse response){
		try{
			String ukey=this.getParaString("ukey",request);//用户授权key
			String numiid=this.getParaString("numiid",request);//用户授权key
			Integer count=this.getParaInteger("count",request);//用户授权key
			APIResponse rsp=new APIResponse();
			
			//用户身份验证
			SysUser u=sysUserService.getUserByUkey(ukey);
			if(u!=null){
				TbkItemRecommend ti=TaobaokeAPI.getTbkItemRecommendInfo(Long.parseLong(numiid), count);
				rsp.setResult(1);
				rsp.setErrormsg("");
				rsp.setItemr(ti);
			}else{
				rsp.setResult(0);
				rsp.setErrormsg("ukey不存在");
			}
			
			//返回数据	
			String pjson=JSON.toJSONString(rsp);
			PrintWriter pw=response.getWriter();
			pw.write(pjson);
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getkl")
	public void getkl(HttpServletRequest request, HttpServletResponse response){
		try{
			String ukey=this.getParaString("ukey",request);//用户授权key
			String url=this.getParaString("url",request);//用户授权key
			String logo=this.getParaString("logo",request);//用户授权key
			String text=this.getParaString("text",request);//用户授权key
			APIResponse rsp=new APIResponse();
			
			//用户身份验证
			SysUser u=sysUserService.getUserByUkey(ukey);
			if(u!=null){
				String ti=TaobaokeAPI.getTkl(url, text, logo);
				rsp.setResult(1);
				rsp.setErrormsg("");
				rsp.setTkl(ti);
			}else{
				rsp.setResult(0);
				rsp.setErrormsg("ukey不存在");
			}
			
			//返回数据	
			String pjson=JSON.toJSONString(rsp);
			PrintWriter pw=response.getWriter();
			pw.write(pjson);
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
