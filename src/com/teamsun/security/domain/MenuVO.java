package com.teamsun.security.domain;


public class MenuVO {
	private Long menuid;
	private String menu_cd;
	private String menu_name;
	private String menu_remark;
	private String menuurl ;
	private Long seq_num ;
	private String status_cd ;
	private Long own_menuid ;
	private String menutarget ;
	private String menucls ;
	private String isleaf  ;
	
	public Long getMenuid() {
		return menuid;
	}
	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}
	public String getMenu_cd() {
		return menu_cd;
	}
	public void setMenu_cd(String menu_cd) {
		this.menu_cd = menu_cd;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenu_remark() {
		return menu_remark;
	}
	public void setMenu_remark(String menu_remark) {
		this.menu_remark = menu_remark;
	}
	public String getMenuurl() {
		return menuurl;
	}
	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}
	public Long getSeq_num() {
		return seq_num;
	}
	public void setSeq_num(Long seq_num) {
		this.seq_num = seq_num;
	}
	public String getStatus_cd() {
		return status_cd;
	}
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}

	public Long getOwn_menuid() {
		return own_menuid;
	}
	public void setOwn_menuid(Long own_menuid) {
		this.own_menuid = own_menuid;
	}
	public String getMenutarget() {
		return menutarget;
	}
	public void setMenutarget(String menutarget) {
		this.menutarget = menutarget;
	}
	public String getMenucls() {
		return menucls;
	}
	public void setMenucls(String menucls) {
		this.menucls = menucls;
	}
	public String getIsleaf() {
		return isleaf;
	}
	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}
}

