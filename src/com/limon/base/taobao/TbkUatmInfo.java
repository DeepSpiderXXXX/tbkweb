package com.limon.base.taobao;

import java.util.List;

import com.taobao.api.domain.UatmTbkItem;

public class TbkUatmInfo {
	private long total_num;
	private List<UatmTbkItem> infolist;
	public long getTotal_num() {
		return total_num;
	}
	public void setTotal_num(long total_num) {
		this.total_num = total_num;
	}
	public List<UatmTbkItem> getInfolist() {
		return infolist;
	}
	public void setInfolist(List<UatmTbkItem> infolist) {
		this.infolist = infolist;
	}
}
