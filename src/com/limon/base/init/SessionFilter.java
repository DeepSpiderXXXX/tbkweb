package com.limon.base.init;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author gqf
 * 
 * 登录验证过滤
 * 2015-2-10 上午10:32:56
 */
public class SessionFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, javax.servlet.FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getRequestURI().equals(request.getContextPath()+"/")){
			//首页登陆
			response.sendRedirect(request.getContextPath()+"/index");
		}else{
			filterChain.doFilter(request, response);
		}
	}

}