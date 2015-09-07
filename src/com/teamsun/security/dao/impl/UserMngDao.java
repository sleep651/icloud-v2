package com.teamsun.security.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.teamsun.core.domain.Pager;
import com.teamsun.security.dao.IUserMngDao;
import com.teamsun.security.domain.CurrUserVO;
import com.teamsun.security.exception.UserMngException;

public class UserMngDao implements IUserMngDao {
	private static final Logger logger = LoggerFactory.getLogger(UserMngDao.class);
	private JdbcTemplate jdbcOraTemplate;
	public UserMngDao(Class clazz) {
	}

	public UserMngDao() {
	}



	public JdbcTemplate getJdbcOraTemplate() {
		return jdbcOraTemplate;
	}

	public void setJdbcOraTemplate(JdbcTemplate jdbcOraTemplate) {
		this.jdbcOraTemplate = jdbcOraTemplate;
	}

	/* 
	 * @see com.teamsun.security.dao.IUserMngDao#getSelectByObject(java.lang.String)
	 */
	public List<CurrUserVO> getSelectByObject(String sql)
			throws UserMngException {
		// TODO Auto-generated method stub
		 List retList =this.jdbcOraTemplate.query(sql, new BeanPropertyRowMapper(CurrUserVO.class)); 
		  return retList;
	}
	/* 
	 * @see com.teamsun.security.dao.IUserMngDao#qryByList(java.lang.String)
	
	public List<LogerVO> qryByList(String sql) throws UserMngException{
		List retList = this.jdbcOraTemplate.query(sql, new BeanPropertyRowMapper(LogerVO.class)); 
		logger.info(sql, retList.size());
		return retList;
	} */
	/* 
	 * @see com.teamsun.security.dao.IUserMngDao#qryCtrlMenuBylist(java.lang.String)
	 */
	public List qryCtrlMenuBylist(String sql) throws UserMngException{
		List retList = this.jdbcOraTemplate.queryForList(sql);
        return retList;
	}
	public long qryValueBylong(String sql)throws UserMngException{

		long value = this.jdbcOraTemplate.queryForLong(sql);
		return value;
	}

	public int exeSql(String sql) {
		int retint=this.jdbcOraTemplate.update(sql);
		return retint;
	}

	public List qryBySqlObj(String sql, Class clazz) {
		List retList = this.jdbcOraTemplate.query(sql, new BeanPropertyRowMapper(clazz)); 
		return retList;
	}

	public Pager browse(int pageNo, int pageSize, String selSql ,Class clazz) {
		
		Pager pager = qryForPager(pageNo, pageSize, selSql ,clazz);
		return pager;
	}
	
	public Pager qryForPager(int pageNo, int pageSize, String selSql ,Class clazz) {
		int v_endrownum   = pageNo * pageSize;
		int v_startrownum = v_endrownum - pageSize + 1;
		
		String v_table="("+selSql+") vtb";
		

		String countsql="SELECT COUNT(ROWNUM) FROM "+v_table;
		int totalRow = jdbcOraTemplate.queryForInt(countsql);
		
		String selSqlend=" SELECT * FROM (SELECT vtb.*, rownum rn FROM  "+v_table+" WHERE rownum <= to_char(" +
				v_endrownum +"))  WHERE rn >= to_char(" +v_startrownum+	")";
		
		List resultList = jdbcOraTemplate.query(selSqlend,new BeanPropertyRowMapper(clazz));
		Pager pager = new Pager();
		pager.setPageNo(pageNo);
		pager.setPageSize(pageSize);
		int totalpages=(totalRow%pageSize==0)?(totalRow/pageSize):(totalRow/pageSize +1);
		pager.setTotalPages(totalpages);
		pager.setResultList(resultList);
		
		return pager;
	}
}
