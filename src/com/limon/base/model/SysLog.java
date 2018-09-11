package com.limon.base.model;

import java.io.Serializable;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public class SysLog implements Serializable {
	private static final long serialVersionUID = 6117654782467757297L;
	private Integer id;
	private String content;
	private Integer logtype;
	private String logtime;
	private String loguser;
	private Integer loguserid;
	private String logip;
	private String logos;
	private String logimei;
	private String logimsi;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getLogtype() {
		return logtype;
	}
	public void setLogtype(Integer logtype) {
		this.logtype = logtype;
	}
	public String getLogtime() {
		return logtime;
	}
	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
	public String getLoguser() {
		return loguser;
	}
	public void setLoguser(String loguser) {
		this.loguser = loguser;
	}
	public String getLogip() {
		return logip;
	}
	public void setLogip(String logip) {
		this.logip = logip;
	}
	public String getLogos() {
		return logos;
	}
	public void setLogos(String logos) {
		this.logos = logos;
	}
	public String getLogimei() {
		return logimei;
	}
	public void setLogimei(String logimei) {
		this.logimei = logimei;
	}
	public String getLogimsi() {
		return logimsi;
	}
	public void setLogimsi(String logimsi) {
		this.logimsi = logimsi;
	}
	public Integer getLoguserid() {
		return loguserid;
	}
	public void setLoguserid(Integer loguserid) {
		this.loguserid = loguserid;
	}
}
