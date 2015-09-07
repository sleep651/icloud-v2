package com.apps.mobile.domain;

/**
 * 
 * 类描述： 用户定制应用列表信息实体 
 * 创建人：XiongFaMing   
 * 创建时间：2014-12-5 上午11:14:41
 */
public class Customization {
	private String chart_id;		//图表id
	private String tab_id;	//TAB标识
	private String chart_name;
	private String chart_png;
	private String user_id;
	private String pos;
	public String getChart_id() {
		return chart_id;
	}
	public void setChart_id(String chart_id) {
		this.chart_id = chart_id;
	}
	public String getTab_id() {
		return tab_id;
	}
	public void setTab_id(String tab_id) {
		this.tab_id = tab_id;
	}
	public String getChart_name() {
		return chart_name;
	}
	public void setChart_name(String chart_name) {
		this.chart_name = chart_name;
	}
	public String getChart_png() {
		return chart_png;
	}
	public void setChart_png(String chart_png) {
		this.chart_png = chart_png;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	
	
}
