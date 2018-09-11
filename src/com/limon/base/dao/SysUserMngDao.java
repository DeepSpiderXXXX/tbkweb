package com.limon.base.dao;

import java.util.List;
import java.util.Map;

import com.limon.base.model.SysRole;
import com.limon.base.model.SysUser;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface SysUserMngDao {
	public List<SysUser> getSysUserList(Map<String,Object> map);
	public Integer getSysUserListCount(Map<String,Object> map);
	public SysUser getSysUserById(String id);
	public Integer sysUserAdd(Map<String, Object> map);
	public Integer sysUserUpdate(Map<String, Object> map);
	public Integer sysUserRoleUpdate(Map<String, Object> map);
	public Integer sysUserDel(String id);
	public List<SysRole> getAllRoleList();
	public Integer sysUserRoleAdd(Map<String, Object> map);
	public Integer getIsUsedUserName(String username);
	public Integer sysUserUpdateNoPwd(Map<String, Object> map);
	public Integer getUserRoleById(String id);
	public void sysUserRoleDel(Integer id);
}