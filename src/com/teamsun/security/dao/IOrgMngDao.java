/**
 * 
 */
package com.teamsun.security.dao;

import com.teamsun.core.domain.Pager;
import com.teamsun.core.exception.BaseException;



/**
 * Teamsun
 * @author guojf
 * @since 2007-7-4
 * @version 1.0 
 */
public interface IOrgMngDao{
	public Pager browseAllParentOrgByName(String name,int isRoot, int pageNo, int pageSize) throws BaseException ;
	
	public Pager browseChildOrgByParentOid(String name,Integer parentOid, int pageNo, int pageSize) throws BaseException ;
	
	public Pager browseAllParentOrgByNameAndUid(String name,Integer deptOid, int pageNo, int pageSize) throws BaseException ;

}
