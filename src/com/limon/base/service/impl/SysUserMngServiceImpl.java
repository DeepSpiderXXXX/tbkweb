package com.limon.base.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limon.base.dao.SysUserMngDao;
import com.limon.base.model.SysRole;
import com.limon.base.model.SysUser;
import com.limon.base.service.SysUserMngService;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
@Service("SysUserMngService")
public class SysUserMngServiceImpl implements SysUserMngService {
	
	@Autowired
	private SysUserMngDao sysUserMngDao;

	public SysUser getSysUserById(String id) {
		return sysUserMngDao.getSysUserById(id);
	}

	public List<SysUser> getSysUserList(Map<String, Object> map) {
		return sysUserMngDao.getSysUserList(map);
	}

	public Integer getSysUserListCount(Map<String, Object> map) {
		return sysUserMngDao.getSysUserListCount(map);
	}

	public Integer sysUserAdd(Map<String, Object> map) {
		return sysUserMngDao.sysUserAdd(map);
	}

	public Integer sysUserDel(String id) {
		return sysUserMngDao.sysUserDel(id);
	}

	public Integer sysUserUpdate(Map<String, Object> map) {
		return sysUserMngDao.sysUserUpdate(map);
	}

	public List<SysRole> getAllRoleList() {
		return sysUserMngDao.getAllRoleList();
	}

	public Integer getIsUsedUserName(String username) {
		return sysUserMngDao.getIsUsedUserName(username);
	}

	public Integer sysUserRoleAdd(Map<String, Object> map) {
		return sysUserMngDao.sysUserRoleAdd(map);
	}

	public Integer sysUserRoleUpdate(Map<String, Object> map) {
		return sysUserMngDao.sysUserRoleUpdate(map);
	}

	public Integer sysUserUpdateNoPwd(Map<String, Object> map) {
		return sysUserMngDao.sysUserUpdateNoPwd(map);
	}

	public Integer getUserRoleById(String id) {
		return sysUserMngDao.getUserRoleById(id);
	}

	@Override
	public void sysUserRoleDel(Integer id) {
		sysUserMngDao.sysUserRoleDel(id);
	}
}