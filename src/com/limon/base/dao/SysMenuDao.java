package com.limon.base.dao;

import java.util.List;

import com.limon.base.model.SysMenu;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface SysMenuDao {
    public List<SysMenu> getRoleMenuList(Integer roleid);
    public List<SysMenu> getRoleMenuListWap(Integer roleid);
}
