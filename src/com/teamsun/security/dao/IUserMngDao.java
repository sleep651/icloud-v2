/**
 * Copyright By 2008 ChinaPost_TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.security.dao;

import java.util.List;

import com.teamsun.core.domain.Pager;
import com.teamsun.security.domain.CurrUserVO;
import com.teamsun.security.exception.UserMngException;


/**
 * CPCIM
 * @author GuoJingFu
 * @since Jun 26, 2008
 * @version 2.0 
 * TODO:
 */
public interface IUserMngDao{
	/**
	 * liyan
	 * Jul 4, 2008 3:57:00 PM
	 * @param sql
	 * @return
	 * @throws UserMngException
	 * TODO: 查询当前用户信息列表
	 */
	public List<CurrUserVO> getSelectByObject(String sql) throws UserMngException;
	/**
	 * liyan
	 * Jul 4, 2008 3:57:04 PM
	 * @param sql
	 * @return
	 * @throws UserMngException
	 * TODO:查询当前用户的日志信息
	 
	public List<LogerVO> qryByList(String sql) throws UserMngException;*/
	/**
	 * liyan
	 * Jul 4, 2008 3:57:08 PM
	 * @param sql
	 * @return
	 * @throws UserMngException
	 * TODO:查询当前用户的可控信息
	 */
	public List qryCtrlMenuBylist(String sql) throws UserMngException;
	/**
	 * liyan
	 * Jul 5, 2008 9:30:42 AM
	 * @param sql
	 * @return
	 * @throws UserMngException
	 * TODO:由sql查出长整型值
	 */
	public long qryValueBylong(String sql)throws UserMngException;
	
	public int exeSql(String sql);
	
	public List qryBySqlObj(String sql,Class clazz);
	
	public Pager browse(int pageNo, int pageSize, String selSql ,Class clazz);
}
