package com.limon.base.taobao;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.TaobaoObject;
import com.taobao.api.internal.mapping.ApiListField;


/**
 * 淘宝客商品
 *
 * @author top auto create
 * @since 1.0, null
 */
public class TbkCoupon extends TaobaoObject {

	private static final long serialVersionUID = 8393253368847716452L;

	/**
	 * 优惠券开始时间
	 */
	@ApiField("coupon_start_time")
	private String coupon_start_time;

	/**
	 * 优惠券结束时间
	 */
	@ApiField("coupon_end_time")
	private String coupon_end_time;

	/**
	 * 商品优惠券推广链接
	 */
	@ApiField("coupon_click_url")
	private String coupon_click_url;

	/**
	 * 店铺名称
	 */
	@ApiField("shop_title")
	private String shop_title;

	/**
	 * 优惠券总量
	 */
	@ApiField("coupon_total_count")
	private Long coupon_total_count;

	/**
	 * 卖家类型，0表示集市，1表示商城
	 */
	@ApiField("user_type")
	private Long user_type;

	/**
	 * 折扣价
	 */
	@ApiField("zk_final_price")
	private String zk_final_price;

	/**
	 * 优惠券剩余量
	 */
	@ApiField("coupon_remain_count")
	private Long coupon_remain_count;

	/**
	 * 佣金比率(%)
	 */
	@ApiField("commission_rate")
	private String commission_rate;

	/**
	 * 商品小图列表
	 */
	@ApiListField("small_images")
	@ApiField("string")
	private List<String> smallImages;

	/**
	 * 商品标题
	 */
	@ApiField("title")
	private String title;

	/**
	 * 后台一级类目
	 */
	@ApiField("category")
	private String category;

	/**
	 * 商品ID
	 */
	@ApiField("num_iid")
	private Long num_iid;

	/**
	 * 30天销量
	 */
	@ApiField("volume")
	private Long volume;

	/**
	 * 卖家昵称
	 */
	@ApiField("nick")
	private String nick;

	/**
	 * 卖家id
	 */
	@ApiField("seller_id")
	private String seller_id;
	
	/**
	 * 商品主图
	 */
	@ApiField("pict_url")
	private String pict_url;
	
	/**
	 * 优惠券面额
	 */
	@ApiField("coupon_info")
	private String coupon_info;
	
	/**
	 * 商品详情页链接地址
	 */
	@ApiField("item_url")
	private String item_url;

	public String getCoupon_start_time() {
		return coupon_start_time;
	}

	public void setCoupon_start_time(String coupon_start_time) {
		this.coupon_start_time = coupon_start_time;
	}

	public String getCoupon_end_time() {
		return coupon_end_time;
	}

	public void setCoupon_end_time(String coupon_end_time) {
		this.coupon_end_time = coupon_end_time;
	}

	public String getCoupon_click_url() {
		return coupon_click_url;
	}

	public void setCoupon_click_url(String coupon_click_url) {
		this.coupon_click_url = coupon_click_url;
	}

	public String getShop_title() {
		return shop_title;
	}

	public void setShop_title(String shop_title) {
		this.shop_title = shop_title;
	}

	public Long getCoupon_total_count() {
		return coupon_total_count;
	}

	public void setCoupon_total_count(Long coupon_total_count) {
		this.coupon_total_count = coupon_total_count;
	}

	public Long getUser_type() {
		return user_type;
	}

	public void setUser_type(Long user_type) {
		this.user_type = user_type;
	}

	public String getZk_final_price() {
		return zk_final_price;
	}

	public void setZk_final_price(String zk_final_price) {
		this.zk_final_price = zk_final_price;
	}

	public Long getCoupon_remain_count() {
		return coupon_remain_count;
	}

	public void setCoupon_remain_count(Long coupon_remain_count) {
		this.coupon_remain_count = coupon_remain_count;
	}

	public String getCommission_rate() {
		return commission_rate;
	}

	public void setCommission_rate(String commission_rate) {
		this.commission_rate = commission_rate;
	}

	public List<String> getSmallImages() {
		return smallImages;
	}

	public void setSmallImages(List<String> smallImages) {
		this.smallImages = smallImages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getNum_iid() {
		return num_iid;
	}

	public void setNum_iid(Long num_iid) {
		this.num_iid = num_iid;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getPict_url() {
		return pict_url;
	}

	public void setPict_url(String pict_url) {
		this.pict_url = pict_url;
	}

	public String getCoupon_info() {
		return coupon_info;
	}

	public void setCoupon_info(String coupon_info) {
		this.coupon_info = coupon_info;
	}

	public String getItem_url() {
		return item_url;
	}

	public void setItem_url(String item_url) {
		this.item_url = item_url;
	}


}
