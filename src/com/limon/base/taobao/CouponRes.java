package com.limon.base.taobao;

import java.util.List;

public class CouponRes {
	private List<CouponData> data;
	private String status;
	public List<CouponData> getData() {
		return data;
	}
	public void setData(List<CouponData> data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
