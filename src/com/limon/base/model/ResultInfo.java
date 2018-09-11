package com.limon.base.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public class ResultInfo implements Serializable {
	private static final long serialVersionUID = 6117654782467757297L;
	private Integer page;
	private Integer totalPage;
	private List<TbkProduct> plist;
	private List<TbkProduct> rlist;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public List<TbkProduct> getPlist() {
		return plist;
	}
	public void setPlist(List<TbkProduct> plist) {
		this.plist = plist;
	}
	public List<TbkProduct> getRlist() {
		return rlist;
	}
	public void setRlist(List<TbkProduct> rlist) {
		this.rlist = rlist;
	}
}
