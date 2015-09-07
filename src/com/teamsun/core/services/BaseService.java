package com.teamsun.core.services;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.teamsun.core.dao.IBaseDao;
import com.teamsun.core.domain.Pager;

@Component
public class BaseService implements IBaseService{
	
	protected static Logger logger =null ;  
	protected static String className =null ;
	public BaseService() {
		super();
		className = getClass().getName();
	    logger=LoggerFactory.getLogger(className);
	}
	
	@Autowired
	@Qualifier("baseDao")//因此接口有多个实现类，在此要指定一个实现
	public IBaseDao baseDao;
	

	public int exeBySql(String sql) {
		int i = 0;
		try {
			baseDao.exeBySql(sql);
			i=1;
		} catch (RuntimeException e) {
			logger.error("",e);
		}
		return i;
	}

	public Object qryForFieldValue(String sql,Class clazz) {
		return null;
	}

	public List qryForList(String sql) {
		
		return baseDao.qryForList(sql);
	}

	public Object qryForLob(String sql, String fieldName, String lobType) {
		return null;
	}

	public List qryForObjList(String sql, Class clazz) {
		return baseDao.qryForObjList(sql, clazz);
	}

	public Pager qryForPager(int pageNo, int pageSize, String sql, Class clazz, String dialect) {
		
		return this.baseDao.qryForPager(pageNo, pageSize, sql, clazz, dialect);
	}

    public Pager qryForPager(int pageNo, int pageSize, String sql, Object obj[] , Class clazz, String dialect) {
		
		return this.baseDao.qryForPager(pageNo, pageSize, sql, obj,clazz, dialect);
	}

	public Map qryForSelectMap(String sql, Class clazz) {
		return null;
	}

	public int saveLob(String sql, String filepath, String fieldName, String lobType) {
		return 0;
	}

	public IBaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	
}

