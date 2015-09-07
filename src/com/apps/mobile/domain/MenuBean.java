package com.apps.mobile.domain;

public class MenuBean {
	private String menu_id;		//菜单ID
	private String menu_name;	//菜单名称
	private String super_menu;	//父ID（根目录的父ID是0）
	private String menu_lvl;	//菜单层次（menu_lvl=0的不是菜单，是报表的表头）
	private String menu_pic_name;	//菜单对应的图片名称（与本地图片库中的图片名称对应）
	private String menu_pos;	//菜单组内排序
	private String menu_style;	//菜单样式（保留，暂时不启用）
	private String isnew;		//标示是否是新增菜单（保留，暂时不启用）
	private String rep_id;//接口ID（对应查找更新日期）
	private String remark;//菜单描述
	
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getSuper_menu() {
		return super_menu;
	}
	public void setSuper_menu(String super_menu) {
		this.super_menu = super_menu;
	}
	public String getMenu_lvl() {
		return menu_lvl;
	}
	public void setMenu_lvl(String menu_lvl) {
		this.menu_lvl = menu_lvl;
	}
	public String getMenu_pic_name() {
		return menu_pic_name;
	}
	public void setMenu_pic_name(String menu_pic_name) {
		this.menu_pic_name = menu_pic_name;
	}
	public String getMenu_pos() {
		return menu_pos;
	}
	public void setMenu_pos(String menu_pos) {
		this.menu_pos = menu_pos;
	}
	public String getMenu_style() {
		return menu_style;
	}
	public void setMenu_style(String menu_style) {
		this.menu_style = menu_style;
	}
	public String getIsnew() {
		return isnew;
	}
	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}
	public String getRep_id() {
		return rep_id;
	}
	public void setRep_id(String rep_id) {
		this.rep_id = rep_id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
