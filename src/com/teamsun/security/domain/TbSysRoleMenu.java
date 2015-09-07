package com.teamsun.security.domain;

/**
 * TbSysRoleMenu entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbSysRoleMenu implements java.io.Serializable {

	// Fields

	private Long recid;
	private TbSysRole tbSysRole;
	private TbSysMenu tbSysMenu;

	// Constructors

	/** default constructor */
	public TbSysRoleMenu() {
	}

	/** full constructor */
	public TbSysRoleMenu(Long recid, TbSysRole tbSysRole, TbSysMenu tbSysMenu) {
		this.recid = recid;
		this.tbSysRole = tbSysRole;
		this.tbSysMenu = tbSysMenu;
	}

	// Property accessors

	public Long getRecid() {
		return this.recid;
	}

	public void setRecid(Long recid) {
		this.recid = recid;
	}

	public TbSysRole getTbSysRole() {
		return this.tbSysRole;
	}

	public void setTbSysRole(TbSysRole tbSysRole) {
		this.tbSysRole = tbSysRole;
	}

	public TbSysMenu getTbSysMenu() {
		return this.tbSysMenu;
	}

	public void setTbSysMenu(TbSysMenu tbSysMenu) {
		this.tbSysMenu = tbSysMenu;
	}

}