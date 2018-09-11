package com.limon.base.dao;

import java.util.Map;
import com.limon.base.model.SysUser;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public interface SysUserDao {
    public SysUser getUserByUserNameAndPassword(Map<String,String> map);
    public Integer updatePassword(Map<String,Object> map);
    public Integer updateQzidBydpid(Map<String,Object> map);
    public SysUser getUserByUkey(String ukey);
}