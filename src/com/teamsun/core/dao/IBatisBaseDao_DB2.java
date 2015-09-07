package com.teamsun.core.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.util.Assert;

import com.ibatis.sqlmap.client.SqlMapExecutor;

@SuppressWarnings("unchecked")
public class IBatisBaseDao_DB2{

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static final String POSTFIX_COUNT = ".count";
	
	@Autowired
	@Qualifier("sqlMapClient_DB2")
	private SqlMapClientTemplate sqlMapClient_DB2;//getSqlMapClientTemplate();
	
	public SqlMapClientTemplate getSqlMapClient_DB2() {
		return sqlMapClient_DB2;
	}
	
	public void setSqlMapClient_DB2(SqlMapClientTemplate sqlMapClient) {
		this.sqlMapClient_DB2 = sqlMapClient;
	}
	
	public IBatisBaseDao_DB2() {

	}

	/**
	 * 根据ID获取对象
	 */
	public <T> T get(String nameSpace, Serializable id) {

		T o = (T) getSqlMapClient_DB2().queryForObject(nameSpace, id);
		if (o == null)
			throw new ObjectRetrievalFailureException(nameSpace, id);
		return o;
	}

	/**
	 * 获取全部对象
	 */
	public <T> List<T> getAll(String nameSpace) {
		return getSqlMapClient_DB2().queryForList(nameSpace, null);
	}

	/**
	 * 新增对象
	 */
	public void insert(String nameSapce, Object o) {
		getSqlMapClient_DB2().insert(nameSapce, o);
	}

	/**
	 * 保存对象
	 */
	public void update(String nameSpace, Object o) {
		getSqlMapClient_DB2().update(nameSpace, o);
	}

	/**
	 * 删除对象
	 */
	public void remove(String nameSapce, Object o) {
		getSqlMapClient_DB2().delete(nameSapce, o);
	}

	/**
	 * 根据ID删除对象
	 */
	public <T> void removeById(String nameSapce, Serializable id) {
		getSqlMapClient_DB2().delete(nameSapce, id);
	}

	/**
	 * map查询.
	 * 
	 * @param map
	 *            包含各种属性的查询
	 */
	public <T> List<T> find(String nameSpace, Map map) {
		return this.getSqlMapClient_DB2().queryForList(nameSpace, map);
	}

	/**
	 * str 查询.
	 * 
	 * @param str
	 */
	public <T> List<T> find(String nameSpace, String str) {
		Assert.hasText(str);
		return this.getSqlMapClient_DB2().queryForList(nameSpace, str);
	}

	/**
	 * 根据属性名和属性值查询对象.
	 * 
	 * @return 符合条件的对象列表
	 */
	public <T> List<T> findBy(String nameSpace, String name, Object value) {
		Assert.hasText(name);
		Map map = new HashMap();
		map.put(name, value);
		return find(nameSpace, map);
	}

	/**
	 * 分页查询函数
	 
	public <T> Page<T> pagedQuery(String nameSpace, Page<T> page) {
		return this.pagedQuery(nameSpace, page, null);
	}*/

	/**
	 * 分页查询函数
	 
	public <T> Page<T> pagedQuery(String nameSpace, String nameSpaceCount,
			Page<T> page) {
		return this.pagedQuery(nameSpace, nameSpaceCount, page, null);
	}*/

	/**
	 * 分页查询函数
	 
	public <T> Page<T> pagedQuery(String nameSpace, Page<T> page,
			Object parameterObject) {
		String nameSpaceCount = nameSpace.substring(0, nameSpace.lastIndexOf("."))
				+ POSTFIX_COUNT;
		return this
				.pagedQuery(nameSpace, nameSpaceCount, page, parameterObject);
	}*/

	/**
	 * 分页查询函数
	 
	public <T> Page<T> pagedQuery(String nameSpace, String nameSpaceCount,
			Page<T> page, Object parameterObject) {
		if (page.getTotalCount() == -1) {
			page.setTotalCount((Integer) (getSqlMapClientTemplate()
					.queryForObject(nameSpaceCount, parameterObject)));
		}
		if (page.getTotalCount() >= 1) {
			page.setResults(getSqlMapClientTemplate().queryForList(nameSpace,
					parameterObject, page.getStart(), page.getLimit()));
		}
		return page;
	}*/
	
	public <T>Page<T> browse(String nameSpace, String nameSpaceCount,
			Page<T> page, Object parameterObject) {
		
		if (page.getTotalRows() == -1) {
			page.setTotalRows((Integer) (getSqlMapClient_DB2()
					.queryForObject(nameSpaceCount, parameterObject)));
		}
		if (page.getTotalRows() >= 1) {
			page.setResultList(getSqlMapClient_DB2().queryForList(nameSpace,
					parameterObject, page.getStartRow(), page.getPageSize()));
		}
		logger.debug(page.getPageSize()+"");
		page.setTotalPages((page.getTotalRows()%page.getPageSize()==0)?(page.getTotalRows()/page.getPageSize()):(page.getTotalRows()/page.getPageSize() +1));
		return page;
	}
	
	/**
	 * 批量
	 * @param nameSpace
	 * @param parameterObjects
	 * @return
	 */
	public int executeBatch(final String nameSpace,final List parameterObjects){
		int ret = (Integer)this.getSqlMapClient_DB2().execute(new SqlMapClientCallback(){
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for(Object parameterObject : parameterObjects){
					executor.update(nameSpace,parameterObject);
				}
				return executor.executeBatch();
			}			
		});
		return ret;
	}



}
