package com.teamsun.security.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * TbSysRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbSysRole implements java.io.Serializable {

	// Fields

	private Long roleid;
	private String roleName;
	private String roleDesc;
	private String levCd;
	private Set tbSysRoleFuncs = new HashSet(0);
	private Set tbSysUserRoles = new HashSet(0);
	private Set tbSysRoleMenus = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbSysRole() {
	}

	/** minimal constructor */
	public TbSysRole(Long roleid) {
		this.roleid = roleid;
	}

	/** full constructor */
	public TbSysRole(Long roleid, String roleName, String roleDesc, String levCd,
			Set tbSysRoleFuncs, Set tbSysUserRoles, Set tbSysRoleMenus) {
		this.roleid = roleid;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.levCd = levCd;
		this.tbSysRoleFuncs = tbSysRoleFuncs;
		this.tbSysUserRoles = tbSysUserRoles;
		this.tbSysRoleMenus = tbSysRoleMenus;
	}

	// Property accessors

	public Long getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getLevCd() {
		return this.levCd;
	}

	public void setLevCd(String levCd) {
		this.levCd = levCd;
	}

	public Set getTbSysRoleFuncs() {
		return this.tbSysRoleFuncs;
	}

	public void setTbSysRoleFuncs(Set tbSysRoleFuncs) {
		this.tbSysRoleFuncs = tbSysRoleFuncs;
	}

	public Set getTbSysUserRoles() {
		return this.tbSysUserRoles;
	}

	public void setTbSysUserRoles(Set tbSysUserRoles) {
		this.tbSysUserRoles = tbSysUserRoles;
	}

	public Set getTbSysRoleMenus() {
		return this.tbSysRoleMenus;
	}

	public void setTbSysRoleMenus(Set tbSysRoleMenus) {
		this.tbSysRoleMenus = tbSysRoleMenus;
	}

}