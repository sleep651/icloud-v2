package com.apps.mobile.domain;

public class MarketGroupBean {
	private String market_id;	//营销活动ID
	private String market_name;	//营销活动名称
	private String is_class;    //新增加，是否需要过滤条件，1是，0否
	private String market_cnt;	//新增加，营销数量
	public String getMarket_cnt() {
		return market_cnt;
	}
	public void setMarket_cnt(String market_cnt) {
		this.market_cnt = market_cnt;
	}
	public String getMarket_id() {
		return market_id;
	}
	public void setMarket_id(String market_id) {
		this.market_id = market_id;
	}
	public String getMarket_name() {
		return market_name;
	}
	public void setMarket_name(String market_name) {
		this.market_name = market_name;
	}
	public String getIs_class() {
		return is_class;
	}
	public void setIs_class(String is_class) {
		this.is_class = is_class;
	}
}
