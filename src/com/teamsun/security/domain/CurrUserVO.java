/**
 * Copyright By 2008 ChinaPost_TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.security.domain;

import java.util.List;


/**
 * template
 * @author Administrator
 * @since Aug 18, 2008
 * @version 2.0 
 * TODO:
 */
public class CurrUserVO {
	private Long userid;			  
	private String logon_name	; 
	private String name	;       
	private String passwd;	    
	private Long org_id;
	private String org_name	; 
	private Long recid;//用户角色关系ID			    
	private String role_name;	  
	private Long menuid;			  
	private Long roleid;			  
	private String menu_cd;		  
	private String menu_name;
	private String menuurl	;
	private String isleaf	;
	private String state;
	private List<String> role_id;//修改为多角色
	private String email;

	
	public List<String> getRole_id() {
		return role_id;
	}
	public void setRole_id(List<String> role_id) {
		this.role_id = role_id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public String getLogon_name() {
		return logon_name;
	}
	public void setLogon_name(String logon_name) {
		this.logon_name = logon_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public Long getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Long org_id) {
		this.org_id = org_id;
	}
	public Long getRecid() {
		return recid;
	}
	public void setRecid(Long recid) {
		this.recid = recid;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public Long getMenuid() {
		return menuid;
	}
	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}
	public Long getRoleid() {
		return roleid;
	}
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
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
	public String getMenuurl() {
		return menuurl;
	}
	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}
	public String getIsleaf() {
		return isleaf;
	}
	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	
	/**
	 * 判断是否为客户经理
	 * @return
	 */
	public Boolean isCustManager(){
		return this.getRoleid()==4||this.getRoleid()==3;
	}
	
	/**
	 * 判断是否为省局操作员
	 * @return
	 */
	public Boolean isProvEmp(){
//		return false;
		if(role_id.contains("7"))
			return true;
		if(role_id.contains("1"))
			return true;
		return this.getRoleid()==1;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 判断是否为超级管理员
	 * @return
	 */
	public Boolean isSupManager()
	{
		if(role_id.contains("0"))
			return true;
		else
			return false;
	}
}
