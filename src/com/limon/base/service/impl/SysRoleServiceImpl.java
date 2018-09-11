package com.limon.base.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limon.base.dao.SysRoleDao;
import com.limon.base.model.SysMenu;
import com.limon.base.model.SysRole;
import com.limon.base.service.SysRoleService;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
@Service("SysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	
	@Autowired
	private SysRoleDao sysRoleDao;

	public List<SysRole> getRoleList(Map<String, Object> map) {
		return sysRoleDao.getRoleList(map);
	}

	public Integer getRoleListCount(Map<String, Object> map) {
		return sysRoleDao.getRoleListCount(map);
	}

	public SysRole getSysRoleById(String id) {
		return sysRoleDao.getSysRoleById(id);
	}

	public Integer sysRoleAdd(Map<String, Object> map) {
		return sysRoleDao.sysRoleAdd(map);
	}

	public Integer sysRoleDel(String id) {
		return sysRoleDao.sysRoleDel(id);
	}

	public Integer sysRoleUpdate(Map<String, Object> map) {
		return sysRoleDao.sysRoleUpdate(map);
	}

	public Integer getUserRoleCount(String id) {
		return sysRoleDao.getUserRoleCount(id);
	}

	public List<SysMenu> getMenuList(String roleid) {
		return sysRoleDao.getMenuList(roleid);
	}

	public Integer sysRoleMenuAdd(Map<String, Object> map) {
		return sysRoleDao.sysRoleMenuAdd(map);
	}

	public Integer sysRoleMenuDel(String roleid) {
		return sysRoleDao.sysRoleMenuDel(roleid);
	}

	public List<SysMenu> getAllMenuList() {
		return sysRoleDao.getMenuListAll();
	}

}