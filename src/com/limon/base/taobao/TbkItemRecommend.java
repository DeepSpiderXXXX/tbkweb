package com.limon.base.taobao;

import java.util.List;

import com.taobao.api.domain.NTbkItem;

public class TbkItemRecommend {
	private long total_num;
	private List<NTbkItem> infolist;
	public long getTotal_num() {
		return total_num;
	}
	public void setTotal_num(long total_num) {
		this.total_num = total_num;
	}
	public List<NTbkItem> getInfolist() {
		return infolist;
	}
	public void setInfolist(List<NTbkItem> infolist) {
		this.infolist = infolist;
	}
}
