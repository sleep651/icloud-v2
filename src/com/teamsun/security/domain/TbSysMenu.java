package com.teamsun.security.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * TbSysMenu entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbSysMenu implements java.io.Serializable {

	// Fields

	private Long menuid;
	private TbSysMenu tbSysMenu;
	private String menuCd;
	private String menuName;
	private String menuRemark;
	private String menuurl;
	private Long seqNum;
	private String statusCd;
	private String menutarget;
	private String menucls;
	private String isleaf;
	private Set tbSysRoleMenus = new HashSet(0);
	private Set tbSysFuncs = new HashSet(0);
	private Set tbSysMenus = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbSysMenu() {
	}

	/** minimal constructor */
	public TbSysMenu(Long menuid) {
		this.menuid = menuid;
	}

	/** full constructor */
	public TbSysMenu(Long menuid, TbSysMenu tbSysMenu, String menuCd, String menuName,
			String menuRemark, String menuurl, Long seqNum, String statusCd, String menutarget,
			String menucls, String isleaf, Set tbSysRoleMenus, Set tbSysFuncs, Set tbSysMenus) {
		this.menuid = menuid;
		this.tbSysMenu = tbSysMenu;
		this.menuCd = menuCd;
		this.menuName = menuName;
		this.menuRemark = menuRemark;
		this.menuurl = menuurl;
		this.seqNum = seqNum;
		this.statusCd = statusCd;
		this.menutarget = menutarget;
		this.menucls = menucls;
		this.isleaf = isleaf;
		this.tbSysRoleMenus = tbSysRoleMenus;
		this.tbSysFuncs = tbSysFuncs;
		this.tbSysMenus = tbSysMenus;
	}

	// Property accessors

	public Long getMenuid() {
		return this.menuid;
	}

	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}

	public TbSysMenu getTbSysMenu() {
		return this.tbSysMenu;
	}

	public void setTbSysMenu(TbSysMenu tbSysMenu) {
		this.tbSysMenu = tbSysMenu;
	}

	public String getMenuCd() {
		return this.menuCd;
	}

	public void setMenuCd(String menuCd) {
		this.menuCd = menuCd;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuRemark() {
		return this.menuRemark;
	}

	public void setMenuRemark(String menuRemark) {
		this.menuRemark = menuRemark;
	}

	public String getMenuurl() {
		return this.menuurl;
	}

	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}

	public Long getSeqNum() {
		return this.seqNum;
	}

	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}

	public String getStatusCd() {
		return this.statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getMenutarget() {
		return this.menutarget;
	}

	public void setMenutarget(String menutarget) {
		this.menutarget = menutarget;
	}

	public String getMenucls() {
		return this.menucls;
	}

	public void setMenucls(String menucls) {
		this.menucls = menucls;
	}

	public String getIsleaf() {
		return this.isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public Set getTbSysRoleMenus() {
		return this.tbSysRoleMenus;
	}

	public void setTbSysRoleMenus(Set tbSysRoleMenus) {
		this.tbSysRoleMenus = tbSysRoleMenus;
	}

	public Set getTbSysFuncs() {
		return this.tbSysFuncs;
	}

	public void setTbSysFuncs(Set tbSysFuncs) {
		this.tbSysFuncs = tbSysFuncs;
	}

	public Set getTbSysMenus() {
		return this.tbSysMenus;
	}

	public void setTbSysMenus(Set tbSysMenus) {
		this.tbSysMenus = tbSysMenus;
	}

}