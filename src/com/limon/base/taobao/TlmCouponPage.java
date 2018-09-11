package com.limon.base.taobao;

import java.util.Map;

public class TlmCouponPage {
	private Map<String,Double> tkSpecialCampaignIdRateMap;
	private String pictUrl;
	private String title;
	private String auctionUrl;
	private String auctionId;
	private String couponLink;
	private String couponLinkTaoToken;
	private String couponAmount;
	private String zkPrice;
	private String tkRate;
	private String id;
	private String nick;
	private String couponInfo;
	private Integer includeDxjh;
	private Integer biz30day;
	public Integer getBiz30day() {
		return biz30day;
	}
	public void setBiz30day(Integer biz30day) {
		this.biz30day = biz30day;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTkRate() {
		return tkRate;
	}
	public void setTkRate(String tkRate) {
		this.tkRate = tkRate;
	}
	public String getZkPrice() {
		return zkPrice;
	}
	public void setZkPrice(String zkPrice) {
		this.zkPrice = zkPrice;
	}
	public String getPictUrl() {
		return pictUrl;
	}
	public void setPictUrl(String pictUrl) {
		this.pictUrl = pictUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuctionUrl() {
		return auctionUrl;
	}
	public void setAuctionUrl(String auctionUrl) {
		this.auctionUrl = auctionUrl;
	}
	public String getAuctionId() {
		return auctionId;
	}
	public void setAuctionId(String auctionId) {
		this.auctionId = auctionId;
	}
	public String getCouponLink() {
		return couponLink;
	}
	public void setCouponLink(String couponLink) {
		this.couponLink = couponLink;
	}
	public String getCouponLinkTaoToken() {
		return couponLinkTaoToken;
	}
	public void setCouponLinkTaoToken(String couponLinkTaoToken) {
		this.couponLinkTaoToken = couponLinkTaoToken;
	}
	public String getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}
	public Map<String, Double> getTkSpecialCampaignIdRateMap() {
		return tkSpecialCampaignIdRateMap;
	}
	public void setTkSpecialCampaignIdRateMap(
			Map<String, Double> tkSpecialCampaignIdRateMap) {
		this.tkSpecialCampaignIdRateMap = tkSpecialCampaignIdRateMap;
	}
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
}
