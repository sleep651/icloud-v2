package com.teamsun.security.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.teamsun.security.dao.IMenuMngDao;
import com.teamsun.security.exception.MenuMngException;


public class MenuMngDao  implements IMenuMngDao{
	private JdbcTemplate jdbcTemplate;
    
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	/* 
	 * @see com.teamsun.security.dao.IMenuMngDao#qryMenuByList(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List qryMenuByList(String sql) throws MenuMngException {
		// TODO Auto-generated method stub
         List lst = null;//this.jdbcTemplate.query(sql, new DAORowMapper(MenuVO.class));
		return lst;
	}

	/* 
	 * @see com.teamsun.security.dao.IMenuMngDao#qryMenuIdByLong(java.lang.String)
	 */
	public Long qryIdByLong(String sql) throws MenuMngException {
		// TODO Auto-generated method stub
		Long menuId =this.jdbcTemplate.queryForLong(sql);
		return menuId;
	}
	
	
}
