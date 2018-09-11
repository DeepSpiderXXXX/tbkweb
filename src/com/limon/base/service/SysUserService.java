package com.limon.base.service;

import java.util.Map;
import com.limon.base.model.SysUser;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface SysUserService {
	/**
	 * 根据用户名和密码查询用户信息
	 * @param map
	 * @return
	 */
    public SysUser getUserByUserNameAndPassword(Map<String,String> map);
    /**
	 * 根据用户ID修改密码
	 * @param map
	 * @return
	 */
    public boolean updatePassword(String userid,String newpassword);
    
    /**
	 * 根据用户ID群组ID
	 * @param map
	 * @return
	 */
    public boolean updateQzidByDpid(Integer dpid,Integer qzid);
    
    public SysUser getUserByUkey(String ukey);
}
