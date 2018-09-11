package com.limon.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.limon.base.model.SysUser;
import com.limon.base.service.SysUserService;
import com.limon.util.CommonUtil;
import com.limon.util.LogUtil;

/**
 * @author gqf
 *
 * 登录相关流程
 * 2015-2-10 上午10:32:56
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController{
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("login")
    public String login(HttpServletRequest request, HttpServletResponse response){
		String error=this.getParaString("error",request);
		String message="";
		if(error.equals("1")){
			message="登录信息失效，请重新登录";
		}
		request.setAttribute("message",message);
        return "login";
    }
	
	@RequestMapping("loginCheck")
    public String loginCheck(HttpServletRequest request, HttpServletResponse response){
		String username=this.getParaString("username",request);
		String password=this.getParaString("password",request);
		Map<String,String> map=new HashMap<String,String>();
		map.put("username", username);
		//明文密码，客户的奇葩要求
		map.put("password", password);
		
		SysUser user=sysUserService.getUserByUserNameAndPassword(map);
		
		if(user==null){
			//返回错误提示信息
			request.setAttribute("message","用户名或密码错误");
			//返回登录页
			return "login";
		}else{		
			//登录信息存入session
			request.getSession().setAttribute("loginUser", user);
			//记录登录日志
			String logcontent="用户 "+user.getUsername() +" 登录";
				
			if(!logcontent.equals("")){
				LogUtil.logLogin(logcontent,user.getUsername(),CommonUtil.getIpAddr(request));
			}
			
			//跳转到首页
			return "redirect:/main";
		}
    }
	
	@RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
		//删除登录信息
		request.getSession().removeAttribute("loginUser");
		//退出消息
		String message="您已安全退出";
		
		request.setAttribute("message",message);
        return "login";
    }
}
