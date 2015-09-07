package com.teamsun.security.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TbSysOrg entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TbSysOrg implements java.io.Serializable {

	// Fields

	private Long orgId;
	private TbSysOrg tbSysOrg;
	private String orgName;
	private String postCd;
	private String statusCd;
	private String levCd;
	private Date lastUpdDate;
	private Set tbSysTerms = new HashSet(0);
	private Set tbSysUsers = new HashSet(0);
	private Set tbSysOrgs = new HashSet(0);

	// Constructors

	/** default constructor */
	public TbSysOrg() {
	}

	/** minimal constructor */
	public TbSysOrg(Long orgId) {
		this.orgId = orgId;
	}

	/** full constructor */
	public TbSysOrg(Long orgId, TbSysOrg tbSysOrg, String orgName, String postCd, String statusCd,
			String levCd, Date lastUpdDate, Set tbSysTerms, Set tbSysUsers, Set tbSysOrgs) {
		this.orgId = orgId;
		this.tbSysOrg = tbSysOrg;
		this.orgName = orgName;
		this.postCd = postCd;
		this.statusCd = statusCd;
		this.levCd = levCd;
		this.lastUpdDate = lastUpdDate;
		this.tbSysTerms = tbSysTerms;
		this.tbSysUsers = tbSysUsers;
		this.tbSysOrgs = tbSysOrgs;
	}

	// Property accessors

	public Long getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public TbSysOrg getTbSysOrg() {
		return this.tbSysOrg;
	}

	public void setTbSysOrg(TbSysOrg tbSysOrg) {
		this.tbSysOrg = tbSysOrg;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPostCd() {
		return this.postCd;
	}

	public void setPostCd(String postCd) {
		this.postCd = postCd;
	}

	public String getStatusCd() {
		return this.statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getLevCd() {
		return this.levCd;
	}

	public void setLevCd(String levCd) {
		this.levCd = levCd;
	}

	public Date getLastUpdDate() {
		return this.lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public Set getTbSysTerms() {
		return this.tbSysTerms;
	}

	public void setTbSysTerms(Set tbSysTerms) {
		this.tbSysTerms = tbSysTerms;
	}

	public Set getTbSysUsers() {
		return this.tbSysUsers;
	}

	public void setTbSysUsers(Set tbSysUsers) {
		this.tbSysUsers = tbSysUsers;
	}

	public Set getTbSysOrgs() {
		return this.tbSysOrgs;
	}

	public void setTbSysOrgs(Set tbSysOrgs) {
		this.tbSysOrgs = tbSysOrgs;
	}

}