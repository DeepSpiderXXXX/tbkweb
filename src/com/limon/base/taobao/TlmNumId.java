package com.limon.base.taobao;

public class TlmNumId {
	private Integer num;
	private String aid;
	private String imgurl;
	private String pname;
	private String price;
	private String sprice;
	private String camount;
	private String tkrate;
	private String nick;
	private String couponInfo;
	private Integer includeDxjh;
	
	public String getCouponInfo() {
		return couponInfo;
	}
	public void setCouponInfo(String couponInfo) {
		this.couponInfo = couponInfo;
	}
	public Integer getIncludeDxjh() {
		return includeDxjh;
	}
	public void setIncludeDxjh(Integer includeDxjh) {
		this.includeDxjh = includeDxjh;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getTkrate() {
		return tkrate;
	}
	public void setTkrate(String tkrate) {
		this.tkrate = tkrate;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSprice() {
		return sprice;
	}
	public void setSprice(String sprice) {
		this.sprice = sprice;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getCamount() {
		return camount;
	}
	public void setCamount(String camount) {
		this.camount = camount;
	}
}
