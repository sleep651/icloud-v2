package com.teamsun.core.domain;

import java.util.List;
import java.util.Map;


public class BaseVO{
	private Integer i;
	private String str;
	private String arrays[];
	private List list;
	private Map  map;
	private Object obj;
	public Integer getI() {
		return i;
	}
	public void setI(Integer i) {
		this.i = i;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public String[] getArrays() {
		return arrays;
	}
	public void setArrays(String[] arrays) {
		this.arrays = arrays;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}

