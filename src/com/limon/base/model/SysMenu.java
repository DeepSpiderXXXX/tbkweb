package com.limon.base.model;

import java.io.Serializable;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public class SysMenu implements Serializable {
	private static final long serialVersionUID = -8376413149865554302L;
	private Integer id;
	private Integer pid;
	private String name;
	private String link;
	private String ico;
	private Integer type;
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	
}
