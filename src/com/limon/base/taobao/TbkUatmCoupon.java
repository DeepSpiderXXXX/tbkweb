package com.limon.base.taobao;

import java.util.List;

public class TbkUatmCoupon {
	private long total_num;
	private List<TbkCoupon> infolist;
	public long getTotal_num() {
		return total_num;
	}
	public void setTotal_num(long total_num) {
		this.total_num = total_num;
	}
	public List<TbkCoupon> getInfolist() {
		return infolist;
	}
	public void setInfolist(List<TbkCoupon> infolist) {
		this.infolist = infolist;
	}
}
