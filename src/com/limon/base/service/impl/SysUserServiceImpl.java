package com.limon.base.service.impl;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limon.base.dao.SysUserDao;
import com.limon.base.model.SysUser;
import com.limon.base.service.SysUserService;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserDao sysUserDao;

	public SysUser getUserByUserNameAndPassword(Map<String, String> map) {
		SysUser user=sysUserDao.getUserByUserNameAndPassword(map);
		return user;
	}
	
	public boolean updatePassword(String userid, String newpassword) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userid", userid);
		map.put("newpassword", newpassword);
		Integer r=sysUserDao.updatePassword(map);
		if(r>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean updateQzidByDpid(Integer dpid, Integer qzid) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("dpid", dpid);
		map.put("qzid", qzid);
		Integer r=sysUserDao.updateQzidBydpid(map);
		if(r>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public SysUser getUserByUkey(String ukey) {
		return sysUserDao.getUserByUkey(ukey);
	}
}