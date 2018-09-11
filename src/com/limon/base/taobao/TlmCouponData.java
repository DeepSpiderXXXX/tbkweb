package com.limon.base.taobao;

import java.util.List;

public class TlmCouponData {
	private TlmCouponPaginator paginator;
	private List<TlmCouponPage> pageList;

	public TlmCouponPaginator getPaginator() {
		return paginator;
	}

	public void setPaginator(TlmCouponPaginator paginator) {
		this.paginator = paginator;
	}

	public List<TlmCouponPage> getPageList() {
		return pageList;
	}

	public void setPageList(List<TlmCouponPage> pageList) {
		this.pageList = pageList;
	}

}
