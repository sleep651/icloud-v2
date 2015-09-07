/**
 * Copyright By 2007 TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.security.dao.impl;

import com.teamsun.core.domain.Pager;
import com.teamsun.core.exception.BaseException;
import com.teamsun.security.dao.IOrgMngDao;

/**
 * Teamsun
 * 
 * @author guojf
 * @since 2007-7-4
 * @version 1.0
 */
public class OrgMngDao implements IOrgMngDao {

	public Pager browseAllParentOrgByName(String name, int isRoot, int pageNo, int pageSize)
			throws BaseException {
		return null;
	}

	public Pager browseAllParentOrgByNameAndUid(String name, Integer deptOid, int pageNo,
			int pageSize) throws BaseException {
		return null;
	}

	public Pager browseChildOrgByParentOid(String name, Integer parentOid, int pageNo, int pageSize)
			throws BaseException {
		return null;
	}
	
	
}
