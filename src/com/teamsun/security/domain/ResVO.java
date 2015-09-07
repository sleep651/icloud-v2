package com.teamsun.security.domain;

public class ResVO {

	private String r_id;

	private String p_rid;

	private String r_name;

	private String r_desc;

	private String r_type;

	private String linkaddress;

	private Integer r_order;

	private String status;
	
	private String role_id;

	private String user_id;

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getR_id() {
		return this.r_id;
	}

	public void setR_id(String r_id) {
		this.r_id = r_id;
	}

	public String getP_rid() {
		return this.p_rid;
	}

	public void setP_rid(String p_rid) {
		this.p_rid = p_rid;
	}

	public String getR_name() {
		return this.r_name;
	}

	public void setR_name(String r_name) {
		this.r_name = r_name;
	}

	public String getR_desc() {
		return this.r_desc;
	}

	public void setR_desc(String r_desc) {
		this.r_desc = r_desc;
	}

	public String getR_type() {
		return this.r_type;
	}

	public void setR_type(String r_type) {
		this.r_type = r_type;
	}

	public String getLinkaddress() {
		return this.linkaddress;
	}

	public void setLinkaddress(String linkaddress) {
		this.linkaddress = linkaddress;
	}

	public Integer getR_order() {
		return this.r_order;
	}

	public void setR_order(Integer r_order) {
		this.r_order = r_order;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	


	public String toString() {
		StringBuffer sb = new StringBuffer("ResVO={\n");
		sb.append("r_id=" + this.r_id + ",\n");
		sb.append("p_rid=" + this.p_rid + ",\n");
		sb.append("r_name=" + this.r_name + ",\n");
		sb.append("r_desc=" + this.r_desc + ",\n");
		sb.append("r_type=" + this.r_type + ",\n");
		sb.append("linkaddress=" + this.linkaddress + ",\n");
		sb.append("r_order=" + this.r_order + ",\n");
		sb.append("status=" + this.status + ",\n");
		sb.append("u_id=" + this.role_id + "\n}\n");
		return sb.toString();
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
