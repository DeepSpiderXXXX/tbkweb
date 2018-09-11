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

import com.limon.base.model.SysLog;
import com.limon.base.model.SysMenu;
import com.limon.base.model.SysRole;
import com.limon.base.model.SysUser;
import com.limon.base.service.SysLogService;
import com.limon.base.service.SysRoleService;
import com.limon.base.service.SysUserMngService;
import com.limon.base.service.SysUserService;
import com.limon.util.DateUtil;
/**
 * @author gqf
 *
 * 系统设置
 * 2015-2-25 下午02:40:46
 */
@Controller
@RequestMapping("/sysset")
public class SystemSetController extends BaseController{
	@Autowired
	private SysLogService sysLogService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserMngService sysUserMngService;
	/**
	 * 日志查询
	 * @return
	 */
	@RequestMapping("/loglist")
    public String loglist(HttpServletRequest request, HttpServletResponse response){
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
		String logcontent=this.getParaString("logcontent",request);
		String logtype=this.getParaString("logtype",request);
		String logstime=this.getParaString("logstime",request);
		if(logstime.equals("")){
			logstime=DateUtil.getLastWeek();
		}
		String logetime=this.getParaString("logetime",request);
		if(logetime.equals("")){
			logetime=DateUtil.getToday();
		}
		
		//查询条件设置
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("logclass","INFO");
		map.put("logcontent",logcontent);
		map.put("logtype",logtype);
		if(!logstime.equals("")){
			map.put("logstime",logstime+" 00:00:00");
		}else{
			map.put("logstime",logstime);
		}
		if(!logetime.equals("")){
			map.put("logetime",logetime+" 23:59:59");
		}else{
			map.put("logetime",logetime);
		}
		map.put("pageStart",page.getPagestart());
		map.put("pageSize",page.getPageSize());
		
		//分页数据查询
		Integer totalRecord=sysLogService.getLogListCount(map);
		List<SysLog> loglist=sysLogService.getLogList(map);
		
		//分页信息设置
		page.setTotalRecord(totalRecord);
		page.setList(loglist);
		
		//返回页面参数
		request.setAttribute("logcontent", logcontent);
		request.setAttribute("logtype",logtype);
		request.setAttribute("logstime",logstime);
		request.setAttribute("logetime",logetime);
		request.setAttribute("page",page);
		
        return "manage/sysset/loglist";
	}
	
	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping("/tochangepassword")
    public String tochangepassword(HttpServletRequest request, HttpServletResponse response){
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
		//返回页面参数
		String rs="";
		request.setAttribute("userid", user.getId());
		request.setAttribute("rs", rs);
        return "manage/sysset/changepassword";
	}
	
	/**
	 * 修改密码保存
	 * @return
	 */
	@RequestMapping("/changepassword")
    public String changepassword(HttpServletRequest request, HttpServletResponse response){
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
		String userid=this.getParaString("userid",request);
		String newpassword=this.getParaString("password",request);
		boolean result=false;
		//管理员登录修改密码
		result=sysUserService.updatePassword(userid,newpassword);
		String rs="0";
		if(result){
			rs="0";
		}else{
			rs="1";
		}
		request.setAttribute("rs",rs);
        return "manage/sysset/changepassword";
	}
	
	/**
	 * 角色列表
	 * @return
	 */
	@RequestMapping("/rolelist")
    public String rolelist(HttpServletRequest request, HttpServletResponse response){
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
		String rolename=this.getParaString("rolename",request);
		//查询条件设置
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("rolename",rolename);
		map.put("pageStart",page.getPagestart());
		map.put("pageSize",page.getPageSize());
		//分页数据查询
		Integer totalRecord=sysRoleService.getRoleListCount(map);
		List<SysRole> rolelist=sysRoleService.getRoleList(map);
		//分页信息设置
		page.setTotalRecord(totalRecord);
		page.setList(rolelist);
		//返回页面参数
		request.setAttribute("rolename",rolename);
		request.setAttribute("page",page);
		request.setAttribute("rs",rs);
        return "manage/sysset/rolelist";
	}
	
	/**
	 * 角色添加页面
	 * @return
	 */
	@RequestMapping("/toaddrole")
    public String toaddrole(HttpServletRequest request, HttpServletResponse response){
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
		List<SysMenu> menulist=sysRoleService.getAllMenuList();
		request.setAttribute("menulist",menulist);
        return "manage/sysset/addrole";
	}
	
	/**
	 * 角色添加
	 * @return
	 */
	@RequestMapping("/saverole")
    public String saverole(HttpServletRequest request, HttpServletResponse response){
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
			String rolename=this.getParaString("rolename",request);
			String description=this.getParaString("description",request);
			String menuids=this.getParaString("menuids",request);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", "0");
			map.put("rolename", rolename);
			map.put("description", description);
			sysRoleService.sysRoleAdd(map);
			Integer roleid=Integer.parseInt((String) map.get("id"));
			if(roleid!=null&&roleid>0){
				Map<String,Object> mmap=new HashMap<String,Object>();
				String[] mids=menuids.split(",");
				for(int i=0;i<mids.length;i++){
					mmap.put("roleid", roleid);
					mmap.put("menuid", mids[i]);
					sysRoleService.sysRoleMenuAdd(mmap);
				}
			}
			rs="1";
		} catch (Exception e) {
			rs="0";
			e.printStackTrace();
		}
		request.setAttribute("rs", rs);
		return "manage/sysset/addrole";
	}
	
	/**
	 * 角色修改页面
	 * @return
	 */
	@RequestMapping("/toeditrole")
    public String toeditrole(HttpServletRequest request, HttpServletResponse response){
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
		SysRole role=sysRoleService.getSysRoleById(id);
		List<SysMenu> mlist=sysRoleService.getMenuList(id);
		role.setMenulist(mlist);
		List<SysMenu> menulist=sysRoleService.getAllMenuList();
		request.setAttribute("menulist",menulist);
		request.setAttribute("role",role);
        return "manage/sysset/editrole";
	}
	
	/**
	 * 角色修改
	 * @return
	 */
	@RequestMapping("/updaterole")
    public String updaterole(HttpServletRequest request, HttpServletResponse response){
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
			String id=this.getParaString("id",request);
			String rolename=this.getParaString("rolename",request);
			String description=this.getParaString("description",request);
			String menuids=this.getParaString("menuids",request);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id",id);
			map.put("rolename", rolename);
			map.put("description", description);
			sysRoleService.sysRoleUpdate(map);
			//删除菜单信息
			sysRoleService.sysRoleMenuDel(id);
			//增加菜单信息
			Map<String,Object> mmap=new HashMap<String,Object>();
			String[] mids=menuids.split(",");
			for(int i=0;i<mids.length;i++){
				mmap.put("roleid", id);
				mmap.put("menuid", mids[i]);
				sysRoleService.sysRoleMenuAdd(mmap);
			}
			rs="1";
		} catch (Exception e) {
			rs="0";
			e.printStackTrace();
		}
		
		request.setAttribute("rs", rs);
		return "manage/sysset/editrole";
	}
	
	/**
	 * 角色删除
	 * @return
	 */
	@RequestMapping("/delrole")
    public String delrole(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
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
		String currentPage = this.getParaString("currentPage",request);
		String id=this.getParaString("id",request);
		String rolename=this.getParaString("rolename",request);
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", id);
			Integer roleUserCount=sysRoleService.getUserRoleCount(id);
			if(roleUserCount>0){
				rs="4";
			}else{
				sysRoleService.sysRoleDel(id);
				rs="1";
			}
		} catch (Exception e) {
			rs="0";
			e.printStackTrace();
		}
		return "redirect:rolelist?currentPage=" + currentPage + "&rolename="+ URLEncoder.encode(rolename, "UTF-8") + "&rs=" + rs ;
	}
	
	
	
	/**
	 * 用户列表
	 * @return
	 */
	@RequestMapping("/userlist")
    public String userlist(HttpServletRequest request, HttpServletResponse response){
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
		String username=this.getParaString("username",request);
		//查询条件设置
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("username",username);
		map.put("pageStart",page.getPagestart());
		map.put("pageSize",page.getPageSize());
		//分页数据查询
		Integer totalRecord=sysUserMngService.getSysUserListCount(map);
		List<SysUser> loglist=sysUserMngService.getSysUserList(map);
		
		//分页信息设置
		page.setTotalRecord(totalRecord);
		page.setList(loglist);
		
		//返回页面参数
		request.setAttribute("username",username);
		request.setAttribute("page",page);
		request.setAttribute("rs",rs);
        return "manage/sysset/userlist";
	}
	
	/**
	 * 用户添加页面
	 * @return
	 */
	@RequestMapping("/toadduser")
    public String toadduser(HttpServletRequest request, HttpServletResponse response){
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
		List<SysRole> rolelist=sysUserMngService.getAllRoleList();
		String stime=DateUtil.getToday();
		String etime=DateUtil.getNextWeek();
		
		request.setAttribute("ukey", "");
		request.setAttribute("stime", stime);
		request.setAttribute("etime", etime);
		request.setAttribute("rolelist", rolelist);
        return "manage/sysset/adduser";
	}
	
	/**
	 * 用户添加
	 * @return
	 */
	@RequestMapping("/saveuser")
    public String saveuser(HttpServletRequest request, HttpServletResponse response){
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
			Integer roleid=this.getParaInteger("roleid",request);
			String username=this.getParaString("username",request);
			String password=this.getParaString("password",request);
			String realname=this.getParaString("realname",request);
			String ukey=this.getParaString("ukey",request);
			String stime=this.getParaString("stime",request);
			String etime=this.getParaString("etime",request);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", "0");
			map.put("username", username);
			map.put("password", password);
			map.put("realname", realname);
			map.put("ukey", ukey);
			map.put("stime", stime+" 00:00:00");
			map.put("etime", etime+" 23:59:59");
			map.put("createtime", DateUtil.getTodayTime());
			sysUserMngService.sysUserAdd(map);
			Integer userid=Integer.parseInt((String) map.get("id"));
			if(userid!=null&&userid>0){
				Map<String,Object> rolemap=new HashMap<String,Object>();
				rolemap.put("userid", userid);
				rolemap.put("roleid", roleid);
				sysUserMngService.sysUserRoleAdd(rolemap);
			}
			rs="1";
		} catch (Exception e) {
			rs="0";
			e.printStackTrace();
		}
		request.setAttribute("rs", rs);
		return "manage/sysset/adduser";
	}
	
	/**
	 * 用户修改页面
	 * @return
	 */
	@RequestMapping("/toedituser")
    public String toedituser(HttpServletRequest request, HttpServletResponse response){
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
		SysUser u=sysUserMngService.getSysUserById(id);
		List<SysRole> rolelist=sysUserMngService.getAllRoleList();
		request.setAttribute("rolelist", rolelist);
		request.setAttribute("user",u);
        return "manage/sysset/edituser";
	}
	
	/**
	 * 用户修改
	 * @return
	 */
	@RequestMapping("/updateuser")
    public String updateuser(HttpServletRequest request, HttpServletResponse response){
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
			String id=this.getParaString("id",request);
			Integer roleid=this.getParaInteger("roleid",request);
			String realname=this.getParaString("realname",request);
			String password=this.getParaString("password",request);
			String ukey=this.getParaString("ukey",request);
			String stime=this.getParaString("stime",request);
			String etime=this.getParaString("etime",request);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id",id);
			map.put("realname", realname);
			map.put("password", password);
			map.put("roleid", roleid);
			map.put("ukey", ukey);
			map.put("stime", stime +" 00:00:00");
			map.put("etime", etime +" 23:59:59");
			map.put("userid", id);
			if(password.equals("")){
				sysUserMngService.sysUserUpdateNoPwd(map);
			}else{
				sysUserMngService.sysUserUpdate(map);
			}
			Integer rid=sysUserMngService.getUserRoleById(id);
			if(rid!=null&&rid>0){
				sysUserMngService.sysUserRoleUpdate(map);
			}else{
				sysUserMngService.sysUserRoleAdd(map);
			}
			rs="1";
		} catch (Exception e) {
			rs="0";
			e.printStackTrace();
		}
		
		request.setAttribute("rs", rs);
		return "manage/sysset/edituser";
	}
	
	/**
	 * 用户删除
	 * @return
	 */
	@RequestMapping("/deluser")
    public String deluser(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
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
		String currentPage = this.getParaString("currentPage",request);
		String id=this.getParaString("id",request);
		String username=this.getParaString("username",request);
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", id);
			sysUserMngService.sysUserDel(id);
			sysUserMngService.sysUserRoleDel(Integer.parseInt(id));
			rs="1";
		} catch (Exception e) {
			rs="0";
			e.printStackTrace();
		}
		return "redirect:userlist?currentPage=" + currentPage + "&username="+ URLEncoder.encode(username, "UTF-8") + "&rs=" + rs;
	}
	
	/**
	 * 用户名校验
	 * @return
	 */
	@RequestMapping("/validUserName")
    public void validUserName(HttpServletRequest request, HttpServletResponse response){
		try{
			String testname=this.getParaString("param",request);
			Integer num=sysUserMngService.getIsUsedUserName(testname);
			if(num==0){
				response.getWriter().write("y");
			}else{
				response.getWriter().write("用户名已存在");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }

}
