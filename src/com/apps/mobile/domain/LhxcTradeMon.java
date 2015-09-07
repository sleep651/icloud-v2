package com.apps.mobile.domain;

/**
 * 53013营业员积分占比查询(月) 类描述： 创建人：XiongFM 创建时间：2014-11-2 下午3:56:38
 */
public class LhxcTradeMon {

	private String statis_month;// 数据日期
	private String up_area_name;// 地市名称
	private String area_name;// 县区名称
	private String channel_name;// 营业厅名称
	private String staff_id;// 营业员工号
	private String staff_name;// 营业员名称
	private String chnl_staff_type;// 员工类型
	private String fen1;// 业务积分
	private String fen2;// 县区总积分
	private String avg_fen2;// 县区人均积分值
	private String staff_fen_rank;// 营业员积分县区内排名
	private String busi_value_per;// 员工积分占比(%)

	public String getStatis_month() {
		return statis_month;
	}

	public void setStatis_month(String statis_month) {
		this.statis_month = statis_month;
	}

	public String getUp_area_name() {
		return up_area_name;
	}

	public void setUp_area_name(String up_area_name) {
		this.up_area_name = up_area_name;
	}

	public String getArea_name() {
		return area_name;
	}

	public void setArea_name(String area_name) {
		this.area_name = area_name;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public String getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(String staff_id) {
		this.staff_id = staff_id;
	}

	public String getStaff_name() {
		return staff_name;
	}

	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}

	public String getChnl_staff_type() {
		return chnl_staff_type;
	}

	public void setChnl_staff_type(String chnl_staff_type) {
		this.chnl_staff_type = chnl_staff_type;
	}

	public String getFen1() {
		return fen1;
	}

	public void setFen1(String fen1) {
		this.fen1 = fen1;
	}

	public String getFen2() {
		return fen2;
	}

	public void setFen2(String fen2) {
		this.fen2 = fen2;
	}

	public String getAvg_fen2() {
		return avg_fen2;
	}

	public void setAvg_fen2(String avg_fen2) {
		this.avg_fen2 = avg_fen2;
	}

	public String getStaff_fen_rank() {
		return staff_fen_rank;
	}

	public void setStaff_fen_rank(String staff_fen_rank) {
		this.staff_fen_rank = staff_fen_rank;
	}

	public String getBusi_value_per() {
		return busi_value_per;
	}

	public void setBusi_value_per(String busi_value_per) {
		this.busi_value_per = busi_value_per;
	}
}
