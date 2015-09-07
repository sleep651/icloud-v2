package com.teamsun.security.domain;

/**
 * TbSysUserRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbSysUserRole implements java.io.Serializable {

	// Fields

	private Long recid;
	private TbSysUser tbSysUser;
	private TbSysRole tbSysRole;

	// Constructors

	/** default constructor */
	public TbSysUserRole() {
	}

	/** full constructor */
	public TbSysUserRole(Long recid, TbSysUser tbSysUser, TbSysRole tbSysRole) {
		this.recid = recid;
		this.tbSysUser = tbSysUser;
		this.tbSysRole = tbSysRole;
	}

	// Property accessors

	public Long getRecid() {
		return this.recid;
	}

	public void setRecid(Long recid) {
		this.recid = recid;
	}

	public TbSysUser getTbSysUser() {
		return this.tbSysUser;
	}

	public void setTbSysUser(TbSysUser tbSysUser) {
		this.tbSysUser = tbSysUser;
	}

	public TbSysRole getTbSysRole() {
		return this.tbSysRole;
	}

	public void setTbSysRole(TbSysRole tbSysRole) {
		this.tbSysRole = tbSysRole;
	}

}