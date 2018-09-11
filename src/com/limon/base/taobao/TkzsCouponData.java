package com.limon.base.taobao;

public class TkzsCouponData {
	private String itemId;
	private String title;
	private String picUrl;
	private String activityId;
	private Integer amount;
	private String jihuaLink;
	private String commissionRate;
	private Double price;
	private String planType;
	private String longPicUrl;
	public String getLongPicUrl() {
		return longPicUrl;
	}
	public void setLongPicUrl(String longPicUrl) {
		this.longPicUrl = longPicUrl;
	}
	public String getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(String commissionRate) {
		this.commissionRate = commissionRate;
	}
	public String getJihuaLink() {
		return jihuaLink;
	}
	public void setJihuaLink(String jihuaLink) {
		this.jihuaLink = jihuaLink;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
}
