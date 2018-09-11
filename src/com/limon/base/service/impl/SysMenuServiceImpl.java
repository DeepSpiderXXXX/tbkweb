package com.limon.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limon.base.dao.SysMenuDao;
import com.limon.base.model.SysMenu;
import com.limon.base.service.SysMenuService;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
	
	@Autowired
	private SysMenuDao sysMenuDao;

	public List<SysMenu> getRoleMenuList(Integer roleid){
		return sysMenuDao.getRoleMenuList(roleid);
	}
}
