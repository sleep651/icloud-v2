package com.teamsun.core.dao;

import java.util.List;
import java.util.Map;

import com.teamsun.core.domain.Pager;

public interface IBaseDao {
	 public int exeBySql(String sql);
	 public List qryForObjList(String sql, Class clazz);
	 public List qryForList(String sql);
	 public Object queryForObject(String sql, Class clazz);
	/* 通过一条sql  查得唯一个值内容*/
	 public Object qryForFieldValue(String sql);
	 /* 
	  * 为下拉框专用（dwr 使用方便） 
	  * 通过一条sql  查得一组[key,value] 界面select专用
	  * */ 
	 public Map qryForSelectMap(String sql, Class clazz);
	 public Pager qryForPager(int pageNo, int pageSize, String sql, Class clazz, String dialect);
	 public Pager qryForPager(int pageNo, int pageSize, String sql, Object obj[] ,Class clazz, String dialect);
	 public Object qryForLob(String sql,String fieldName,String lobType);	
	 public int saveLob(String sqlselect,String sqlinst,String sqlupdate, String filepath, String fieldName, String lobType) throws Exception;
	public int exeBySql(String sql, Object obj[]);
}

