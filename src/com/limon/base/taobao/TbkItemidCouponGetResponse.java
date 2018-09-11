package com.limon.base.taobao;

import java.util.List;
import com.taobao.api.internal.mapping.ApiField;
import com.taobao.api.internal.mapping.ApiListField;

import com.taobao.api.TaobaoResponse;

/**
 * TOP API:  taobao.tbk.itemid.coupon.get response.
 * 
 * @author top auto create
 * @since 1.0, null
 */
public class TbkItemidCouponGetResponse extends TaobaoResponse {

	private static final long serialVersionUID = 3628748862441663685L;

	/** 
	 * 淘宝客优惠券
	 */
	@ApiListField("results")
	@ApiField("tbk_coupon")
	private List<TbkCoupon> results;

	/** 
	 * 搜索到符合条件的结果总数
	 */
	@ApiField("total_results")
	private Long totalResults;


	public void setResults(List<TbkCoupon> results) {
		this.results = results;
	}
	public List<TbkCoupon> getResults( ) {
		return this.results;
	}

	public void setTotalResults(Long totalResults) {
		this.totalResults = totalResults;
	}
	public Long getTotalResults( ) {
		return this.totalResults;
	}
	


}
