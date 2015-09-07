package com.teamsun.security.domain;

/**
 * TbSysTerm entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbSysTerm implements java.io.Serializable {

	// Fields

	private Long termid;
	private TbSysOrg tbSysOrg;
	private String termipAddr;
	private String termmacAddr;
	private String statusCd;

	// Constructors

	/** default constructor */
	public TbSysTerm() {
	}

	/** minimal constructor */
	public TbSysTerm(Long termid) {
		this.termid = termid;
	}

	/** full constructor */
	public TbSysTerm(Long termid, TbSysOrg tbSysOrg, String termipAddr, String termmacAddr,
			String statusCd) {
		this.termid = termid;
		this.tbSysOrg = tbSysOrg;
		this.termipAddr = termipAddr;
		this.termmacAddr = termmacAddr;
		this.statusCd = statusCd;
	}

	// Property accessors

	public Long getTermid() {
		return this.termid;
	}

	public void setTermid(Long termid) {
		this.termid = termid;
	}

	public TbSysOrg getTbSysOrg() {
		return this.tbSysOrg;
	}

	public void setTbSysOrg(TbSysOrg tbSysOrg) {
		this.tbSysOrg = tbSysOrg;
	}

	public String getTermipAddr() {
		return this.termipAddr;
	}

	public void setTermipAddr(String termipAddr) {
		this.termipAddr = termipAddr;
	}

	public String getTermmacAddr() {
		return this.termmacAddr;
	}

	public void setTermmacAddr(String termmacAddr) {
		this.termmacAddr = termmacAddr;
	}

	public String getStatusCd() {
		return this.statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

}