package com.limon.base.taobao;

public class CampData {
	private Double commissionRate;
	private String CampaignID;
	private String CampaignName;
	private String CampaignType;
	private String AvgCommission;
	private boolean Exist;
	private Integer manualAudit;
	private String ShopKeeperID;
	private String Properties;
	public Double getCommissionRate() {
		return commissionRate;
	}
	public void setCommissionRate(Double commissionRate) {
		this.commissionRate = commissionRate;
	}
	public String getCampaignID() {
		return CampaignID;
	}
	public void setCampaignID(String campaignID) {
		CampaignID = campaignID;
	}
	public String getCampaignName() {
		return CampaignName;
	}
	public void setCampaignName(String campaignName) {
		CampaignName = campaignName;
	}
	public String getCampaignType() {
		return CampaignType;
	}
	public void setCampaignType(String campaignType) {
		CampaignType = campaignType;
	}
	public String getAvgCommission() {
		return AvgCommission;
	}
	public void setAvgCommission(String avgCommission) {
		AvgCommission = avgCommission;
	}
	public boolean isExist() {
		return Exist;
	}
	public void setExist(boolean exist) {
		Exist = exist;
	}
	public Integer getManualAudit() {
		return manualAudit;
	}
	public void setManualAudit(Integer manualAudit) {
		this.manualAudit = manualAudit;
	}
	public String getShopKeeperID() {
		return ShopKeeperID;
	}
	public void setShopKeeperID(String shopKeeperID) {
		ShopKeeperID = shopKeeperID;
	}
	public String getProperties() {
		return Properties;
	}
	public void setProperties(String properties) {
		Properties = properties;
	}
}
