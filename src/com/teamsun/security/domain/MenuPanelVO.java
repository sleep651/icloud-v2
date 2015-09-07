package com.teamsun.security.domain;

public class MenuPanelVO {
	private String id	     ;// number	y			
	private String title	 ;//   varchar2(100)	y			
	private String treeid	 ;// varchar2(40)	y			
	private String roottext;//	varchar2(100)	y			
	private String url	   ;//   varchar2(400)	y			
	private String roleid	 ;// number	y			
	private String loadtype ;//装载类型 0：autoload 1:iframe
	public String getLoadtype() {
		return loadtype;
	}
	public void setLoadtype(String loadtype) {
		this.loadtype = loadtype;
	}
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
	public String getTreeid() {
		return treeid;
	}
	public void setTreeid(String treeid) {
		this.treeid = treeid;
	}
	public String getRoottext() {
		return roottext;
	}
	public void setRoottext(String roottext) {
		this.roottext = roottext;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
}
