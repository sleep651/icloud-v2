package com.teamsun.security.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TbSysUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbSysUser implements java.io.Serializable {

	// Fields

	private Long userid;
	private TbSysOrg tbSysOrg;
	private String logonName;
	private String passwd;
	private String name;
	private String sex;
	private String email;
	private String userCd;
	private String statusCd;
	private Date lastUpdDate;
	private Set tbSysUserRoles = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbSysUser() {
	}

	/** minimal constructor */
	public TbSysUser(Long userid, TbSysOrg tbSysOrg) {
		this.userid = userid;
		this.tbSysOrg = tbSysOrg;
	}

	/** full constructor */
	public TbSysUser(Long userid, TbSysOrg tbSysOrg, String logonName, String passwd, String name,
			String sex, String email, String userCd, String statusCd, Date lastUpdDate,
			Set tbSysUserRoles) {
		this.userid = userid;
		this.tbSysOrg = tbSysOrg;
		this.logonName = logonName;
		this.passwd = passwd;
		this.name = name;
		this.sex = sex;
		this.email = email;
		this.userCd = userCd;
		this.statusCd = statusCd;
		this.lastUpdDate = lastUpdDate;
		this.tbSysUserRoles = tbSysUserRoles;
	}

	// Property accessors

	public Long getUserid() {
		return this.userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public TbSysOrg getTbSysOrg() {
		return this.tbSysOrg;
	}

	public void setTbSysOrg(TbSysOrg tbSysOrg) {
		this.tbSysOrg = tbSysOrg;
	}

	public String getLogonName() {
		return this.logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserCd() {
		return this.userCd;
	}

	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}

	public String getStatusCd() {
		return this.statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public Date getLastUpdDate() {
		return this.lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public Set getTbSysUserRoles() {
		return this.tbSysUserRoles;
	}

	public void setTbSysUserRoles(Set tbSysUserRoles) {
		this.tbSysUserRoles = tbSysUserRoles;
	}

}