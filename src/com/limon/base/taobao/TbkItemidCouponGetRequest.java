package com.limon.base.taobao;

import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

/**
 * TOP API: taobao.tbk.itemid.coupon.get
 * 
 * @author top auto create
 * @since 1.0, 2016.07.25
 */
public class TbkItemidCouponGetRequest extends BaseTaobaoRequest<TbkItemidCouponGetResponse> {
	
	

	/** 
	* 1：PC，2：无线，默认：1
	 */
	private Long platform;

	/** 
	* 商品ID串，用逗号分割，从taobao.tbk.item.coupon.get接口获取num_iid字段，最大40个。（如果传入了没有优惠券的宝贝num_iid，则优惠券相关的字段返回为空，请做好容错）
	 */
	private String num_iids;

	/** 
	* 三方pid，满足mm_xxx_xxx_xxx格式
	 */
	private String pid;


	public Long getPlatform() {
		return platform;
	}

	public String getNum_iids() {
		return num_iids;
	}

	public void setNum_iids(String num_iids) {
		this.num_iids = num_iids;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String string) {
		this.pid = string;
	}

	public String getApiMethodName() {
		return "taobao.tbk.itemid.coupon.get";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("platform", this.platform);
		txtParams.put("num_iids", this.num_iids);
		txtParams.put("pid", this.pid);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<TbkItemidCouponGetResponse> getResponseClass() {
		return TbkItemidCouponGetResponse.class;
	}

	@Override
	public void check() throws ApiRuleException {
		
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}