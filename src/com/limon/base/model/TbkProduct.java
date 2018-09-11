package com.limon.base.model;

import java.io.Serializable;

/**
 * @author gqf
 *
 * 2015-2-10 上午10:32:56
 */
public class TbkProduct implements Serializable {
	private static final long serialVersionUID = 6117654782467757297L;
	private Integer id;
	private Integer dtkid;
	private Long pid;
	private Integer tpid;
	private String imgurl;
	private String pname;
	private String sname;
	private Double price;
	private Double sprice;
	private Double cprice;
	private String activityid;
	private Integer salenum;
	private String tbkshort;
	private String tbklong;
	private String tkl;
	private Integer totalcnum;
	private Integer leftcnum;
	private String ccontent;
	private String ctkl;
	private String clong;
	private String cshort;
	private String cstime;
	private String cetime;
	private String type;
	private Integer point;
	private String createdate;
	private Double srbl;
	private Double yhbl;
	private Double yj;
	private Integer isshow;
	private String ygsr;
	private String yjbl;
	private Integer isjh;
	private Integer issend;
	private Integer istg;
	private String introduce;
	private Integer items;
	private Integer gid;
	private String tkrate;
	private String quanlink;
	public String getQuanlink() {
		return quanlink;
	}
	public void setQuanlink(String quanlink) {
		this.quanlink = quanlink;
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public String getTkrate() {
		return tkrate;
	}
	public void setTkrate(String tkrate) {
		this.tkrate = tkrate;
	}
	public Integer getItems() {
		return items;
	}
	public void setItems(Integer items) {
		this.items = items;
	}
	public Integer getIstg() {
		return istg;
	}
	public void setIstg(Integer istg) {
		this.istg = istg;
	}
	public Integer getIssend() {
		return issend;
	}
	public void setIssend(Integer issend) {
		this.issend = issend;
	}
	public Integer getIsjh() {
		return isjh;
	}
	public void setIsjh(Integer isjh) {
		this.isjh = isjh;
	}
	public String getYgsr() {
		return ygsr;
	}
	public void setYgsr(String ygsr) {
		this.ygsr = ygsr;
	}
	public String getYjbl() {
		return yjbl;
	}
	public void setYjbl(String yjbl) {
		this.yjbl = yjbl;
	}
	public Double getSrbl() {
		return srbl;
	}
	public void setSrbl(Double srbl) {
		this.srbl = srbl;
	}
	public Double getYhbl() {
		return yhbl;
	}
	public void setYhbl(Double yhbl) {
		this.yhbl = yhbl;
	}
	public Double getYj() {
		return yj;
	}
	public void setYj(Double yj) {
		this.yj = yj;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getSprice() {
		return sprice;
	}
	public void setSprice(Double sprice) {
		this.sprice = sprice;
	}
	public Integer getSalenum() {
		return salenum;
	}
	public void setSalenum(Integer salenum) {
		this.salenum = salenum;
	}
	public String getTbkshort() {
		return tbkshort;
	}
	public void setTbkshort(String tbkshort) {
		this.tbkshort = tbkshort;
	}
	public String getTbklong() {
		return tbklong;
	}
	public void setTbklong(String tbklong) {
		this.tbklong = tbklong;
	}
	public String getTkl() {
		return tkl;
	}
	public void setTkl(String tkl) {
		this.tkl = tkl;
	}
	public Integer getTotalcnum() {
		return totalcnum;
	}
	public void setTotalcnum(Integer totalcnum) {
		this.totalcnum = totalcnum;
	}
	public Integer getLeftcnum() {
		return leftcnum;
	}
	public void setLeftcnum(Integer leftcnum) {
		this.leftcnum = leftcnum;
	}
	public String getCcontent() {
		return ccontent;
	}
	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}
	public String getCtkl() {
		return ctkl;
	}
	public void setCtkl(String ctkl) {
		this.ctkl = ctkl;
	}
	public String getClong() {
		return clong;
	}
	public void setClong(String clong) {
		this.clong = clong;
	}
	public String getCshort() {
		return cshort;
	}
	public void setCshort(String cshort) {
		this.cshort = cshort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getCstime() {
		return cstime;
	}
	public void setCstime(String cstime) {
		this.cstime = cstime;
	}
	public String getCetime() {
		return cetime;
	}
	public void setCetime(String cetime) {
		this.cetime = cetime;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public Double getCprice() {
		return cprice;
	}
	public void setCprice(Double cprice) {
		this.cprice = cprice;
	}
	public Integer getIsshow() {
		return isshow;
	}
	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}
	public String getActivityid() {
		return activityid;
	}
	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}
	public Integer getDtkid() {
		return dtkid;
	}
	public void setDtkid(Integer dtkid) {
		this.dtkid = dtkid;
	}
	public Integer getTpid() {
		return tpid;
	}
	public void setTpid(Integer tpid) {
		this.tpid = tpid;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
}
