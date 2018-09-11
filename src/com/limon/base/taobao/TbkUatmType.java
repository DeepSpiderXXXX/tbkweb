package com.limon.base.taobao;

import java.util.List;

import com.taobao.api.domain.TbkFavorites;

public class TbkUatmType {
	private long total_num;
	private List<TbkFavorites> typelist;
	public long getTotal_num() {
		return total_num;
	}
	public void setTotal_num(long total_num) {
		this.total_num = total_num;
	}
	public List<TbkFavorites> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<TbkFavorites> typelist) {
		this.typelist = typelist;
	}
	
}
