package com.limon.base.service;

import java.util.List;

import com.limon.base.model.SysMenu;
/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface SysMenuService {
	/**
	 * 根据角色取菜单权限
	 * @return
	 */
	public List<SysMenu> getRoleMenuList(Integer roleid);
}
