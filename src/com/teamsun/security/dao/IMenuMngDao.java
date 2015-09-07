package com.teamsun.security.dao;

import java.util.List;

import com.teamsun.security.exception.MenuMngException;



public interface IMenuMngDao {
	
	/**
	 * liyan
	 * Jul 2, 2008 4:17:03 PM
	 * @param strSql
	 * @return
	 * @throws MenuMngException
	 * TODO: jdbc查询菜单列表
	 */

	public List qryMenuByList(String strSql) throws MenuMngException;
	
	/**
	 * liyan
	 * Jul 2, 2008 4:17:07 PM
	 * @param strSql
	 * @return
	 * @throws MenuMngException
	 * TODO:jdbc查出一个Long型值
	 */
	public Long qryIdByLong(String strSql) throws MenuMngException;



}
