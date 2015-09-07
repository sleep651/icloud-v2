package com.teamsun.core.domain;

import java.util.List;
import java.util.Map;

public class ObjectVO {
	private Integer fid; //select id
	private String fkey; //select 的key
	private String fvalue;//select 的value
	private String isTrue;//select 的是否已选中
	private List  selList;//select 的List
	private Map  selMap;//select 的List
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public String getFkey() {
		return fkey;
	}
	public void setFkey(String fkey) {
		this.fkey = fkey;
	}
	public String getFvalue() {
		return fvalue;
	}
	public void setFvalue(String fvalue) {
		this.fvalue = fvalue;
	}
	public String getIsTrue() {
		return isTrue;
	}
	public void setIsTrue(String isTrue) {
		this.isTrue = isTrue;
	}
	public List getSelList() {
		return selList;
	}
	public void setSelList(List selList) {
		this.selList = selList;
	}
	public Map getSelMap() {
		return selMap;
	}
	public void setSelMap(Map selMap) {
		this.selMap = selMap;
	}
}
