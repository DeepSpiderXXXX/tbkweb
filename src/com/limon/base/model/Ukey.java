package com.limon.base.model;

public class Ukey {
	private String ukey;
	private Integer status;//0不存在 1-在有效期 2-已过期 3-未开始 4-其他
	public String getUkey() {
		return ukey;
	}
	public void setUkey(String ukey) {
		this.ukey = ukey;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
