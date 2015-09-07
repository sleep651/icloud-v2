package com.apps.mobile.domain;

public class HomeReportBean {
	private String rep_id;
	private String rep_name;
	private String max_date;
	private String data_source;//数据来源：ORA1,DB2
	public String getRep_id() {
		return rep_id;
	}
	public void setRep_id(String rep_id) {
		this.rep_id = rep_id;
	}
	public String getRep_name() {
		return rep_name;
	}
	public void setRep_name(String rep_name) {
		this.rep_name = rep_name;
	}
	public String getMax_date() {
		return max_date;
	}
	public void setMax_date(String max_date) {
		this.max_date = max_date;
	}
	public String getData_source() {
		return data_source;
	}
	public void setData_source(String data_source) {
		this.data_source = data_source;
	}
}
