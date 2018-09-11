package com.limon.base.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.limon.base.model.SysUser;
import com.limon.base.model.TbkGroup;
import com.limon.base.service.TbkGroupService;
import com.limon.util.DateUtil;

@Controller
@RequestMapping("/group")
public class GroupController extends BaseController{
	@Autowired
	private TbkGroupService tbkGroupService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request, HttpServletResponse response){
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
		//查询参数接收
		String rs=this.getParaString("rs",request);
		String groupname=this.getParaString("groupname",request);
		//查询条件设置
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("uid",user.getId());
		map.put("groupname",groupname);
		map.put("pageStart",page.getPagestart());
		map.put("pageSize",page.getPageSize());
		//分页数据查询
		Integer totalRecord=tbkGroupService.getTbkGroupListCount(map);
		List<TbkGroup> glist=tbkGroupService.getTbkGroupList(map);
		
		//分页信息设置
		page.setTotalRecord(totalRecord);
		page.setList(glist);
		
		//返回页面参数
		request.setAttribute("groupname",groupname);
		request.setAttribute("page",page);
		request.setAttribute("rs",rs);
        return "manage/group/list";
	}
	
	/**
	 * 新增页面
	 * @return
	 */
	@RequestMapping("/toadd")
    public String toadd(HttpServletRequest request, HttpServletResponse response){
		return "manage/group/add";
	}
	
	/**
	 * 导入页面
	 * @return
	 */
	@RequestMapping("/toimport")
    public String toimport(HttpServletRequest request, HttpServletResponse response){
		return "manage/group/import";
	}
	
	/**
	 * 导入保存
	 * @return
	 */
	@RequestMapping("/saveimport")
    public String saveimport(HttpServletRequest request, HttpServletResponse response){
		String rs="1";
		request.setAttribute("rs", rs);
		return "manage/group/import";
	}
	
	/**
	 * 新增
	 * @return
	 */
	@RequestMapping("/save")
    public String save(HttpServletRequest request, HttpServletResponse response){
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
		String rs="0";
		try {
			String groupname=this.getParaString("groupname",request);
			String pid=this.getParaString("pid",request);
			String createtime=DateUtil.getTodayTime();
			String parentid=this.getParaString("parentid",request);
			String fc=this.getParaString("fc",request);
			String wxname=this.getParaString("wxname",request);
			String stime=this.getParaString("stime",request);
			String etime=this.getParaString("etime",request);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("groupname", groupname);
			map.put("pid", pid);
			map.put("parentid", parentid);
			map.put("fc",fc);
			map.put("wxname",wxname);
			map.put("createtime", createtime);
			map.put("uid",user.getId());
			map.put("stime",stime);
			map.put("etime",etime);
			tbkGroupService.saveTbkGroup(map);
			rs="1";
		} catch (Exception e) {
			rs="0";
			e.printStackTrace();
		}
		request.setAttribute("rs", rs);
        return "manage/group/add";
	}
	
	/**
	 * 编辑页面
	 * @return
	 */
	@RequestMapping("/toedit")
    public String toedit(HttpServletRequest request, HttpServletResponse response){
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
		String id=this.getParaString("id",request);
		TbkGroup g=tbkGroupService.getTbkGroupById(id);
		request.setAttribute("group", g);
		return "manage/group/edit";
	}
	
	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping("/update")
    public String update(HttpServletRequest request, HttpServletResponse response){
		String rs="0";
		try {
			Integer id=this.getParaInteger("id",request);
			String groupname=this.getParaString("groupname",request);
			String parentid=this.getParaString("parentid",request);
			String fc=this.getParaString("fc",request);
			String pid=this.getParaString("pid",request);
			String wxname=this.getParaString("wxname",request);
			String stime=this.getParaString("stime",request);
			String etime=this.getParaString("etime",request);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("groupname", groupname);
			map.put("pid", pid);
			map.put("id", id);
			map.put("wxname",wxname);
			map.put("parentid", parentid);
			map.put("fc",fc);
			map.put("stime",stime);
			map.put("etime",etime);
			tbkGroupService.updateTbkGroup(map);
			rs="1";
		} catch (Exception e) {
			rs="0";
			e.printStackTrace();
		}
		request.setAttribute("rs", rs);
        return "manage/group/edit";
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping("/del")
    public String del(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		String rs="0";
		String currentPage = this.getParaString("currentPage",request);
		Integer id=this.getParaInteger("id",request);
		String groupname=this.getParaString("groupname",request);
		try {
			tbkGroupService.deleteTbkGroup(id);
			rs="1";
		} catch (Exception e) {
			rs="0";
			e.printStackTrace();
		}
		return "redirect:list?currentPage=" + currentPage + "&title="+ URLEncoder.encode(groupname, "UTF-8")+ "&rs=" + rs ;
	}
}
