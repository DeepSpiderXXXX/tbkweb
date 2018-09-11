package com.limon.base.controller;

import com.limon.base.model.TbkProduct;
import com.limon.base.model.Ukey;
import com.limon.base.taobao.Group;
import com.limon.base.taobao.TbkItem;
import com.limon.base.taobao.TbkItemRecommend;

public class APIResponse {
	public Integer result;
	public String errormsg;
	public TbkProduct product;
	public Group group;
	public TbkItem item;
	public String tkl;
	public TbkItemRecommend itemr;
	public Ukey ukey;
	public Ukey getUkey() {
		return ukey;
	}
	public void setUkey(Ukey ukey) {
		this.ukey = ukey;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public TbkProduct getProduct() {
		return product;
	}
	public void setProduct(TbkProduct product) {
		this.product = product;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public TbkItem getItem() {
		return item;
	}
	public void setItem(TbkItem item) {
		this.item = item;
	}
	public TbkItemRecommend getItemr() {
		return itemr;
	}
	public void setItemr(TbkItemRecommend itemr) {
		this.itemr = itemr;
	}
	public String getTkl() {
		return tkl;
	}
	public void setTkl(String tkl) {
		this.tkl = tkl;
	}
}
