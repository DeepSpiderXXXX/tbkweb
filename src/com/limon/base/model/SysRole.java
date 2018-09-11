package com.limon.base.model;

import java.io.Serializable;
import java.util.List;

import com.limon.base.model.SysMenu;

/**
 * @author gqf
 *
 * 2015-7-2
 */
public class SysRole implements Serializable {
	private static final long serialVersionUID = 4659940206933478868L;
	private Integer id;
	private String rolename;
	private String description;
	private List<SysMenu> menulist;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public List<SysMenu> getMenulist() {
		return menulist;
	}
	public void setMenulist(List<SysMenu> menulist) {
		this.menulist = menulist;
	}
}
