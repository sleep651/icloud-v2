/**
 * Copyright By 2009 TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.core.domain;

/**
 * CPNAIS
 * 首页面配置信息
 * @author ht
 * @since 2009-3-25
 * @version 1.0 
 */
public class FrontPageVo {

	private String id;
	private String title;
	private String url;
	private String columnwidth;
	private String style;
	private String roleid;
	private String pos;
	private String height;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getColumnwidth() {
		return columnwidth;
	}
	public void setColumnwidth(String columnwidth) {
		this.columnwidth = columnwidth;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	
}
