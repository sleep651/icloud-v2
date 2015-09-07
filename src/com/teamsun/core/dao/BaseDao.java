package com.teamsun.core.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.teamsun.core.domain.Pager;
import com.teamsun.security.domain.UserVO;

@Component
public class BaseDao implements IBaseDao{
	protected static Logger logger =null ;  
	protected static String className =null ;
	public BaseDao() {
		super();
		className = getClass().getName();
	    logger=LoggerFactory.getLogger(className);
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 数据库连接
	 */
	private static Connection conn = null;

	/**
	 * SQL语句对象
	 */
	private static Statement stmt = null;

	public List qryforList(String sql) {
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserVO.class));
	}

    public Connection getConn(){
		try {
			conn = jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
			logger.error("",e);
		}
		return conn;
    };
    
	public Pager qryForPager(int pageNo, int pageSize, String selSql ,Class clazz) {
		int v_endrownum   = pageNo * pageSize;
		int v_startrownum = v_endrownum - pageSize + 1;
	
		String v_table="("+selSql+") vtb";
		String countsql="SELECT COUNT(ROWNUM) FROM "+v_table;
		int totalRow = jdbcTemplate.queryForInt(countsql);
		String selSqlend=" SELECT * FROM (SELECT vtb.*, rownum rn FROM  "+v_table+" WHERE rownum <= to_char(" +
				v_endrownum +"))  WHERE rn >= to_char(" +v_startrownum+	")";
		List resultList = jdbcTemplate.query(selSqlend,new BeanPropertyRowMapper(clazz));
		Pager pager = new Pager();
		pager.setPageNo(pageNo);
		pager.setPageSize(pageSize);
		pager.setTotalPages(totalRow%pageSize==0?totalRow/pageSize:totalRow/pageSize +1);
		pager.setResultList(resultList);
		return pager;
	}
	
		
	public Pager qryForPager(int pageNo, int pageSize, String selSql ,Object obj[],Class clazz) {
		int v_endrownum   = pageNo * pageSize;
		int v_startrownum = v_endrownum - pageSize + 1;
	
		String v_table="("+selSql+") vtb";
		String countsql="SELECT COUNT(ROWNUM) FROM "+v_table;
		int totalRow = jdbcTemplate.queryForInt(countsql, obj);
		String selSqlend=" SELECT * FROM (SELECT vtb.*, rownum rn FROM  "+v_table+" WHERE rownum <= to_char(" +
				v_endrownum +"))  WHERE rn >= to_char(" +v_startrownum+	")";
		logger.debug("selSqlend== "+selSqlend);
		List resultList = jdbcTemplate.query(selSqlend,obj,new BeanPropertyRowMapper(clazz));
		Pager pager = new Pager();
		pager.setPageNo(pageNo);
		pager.setPageSize(pageSize);
		pager.setTotalPages(totalRow/pageSize +1);
		pager.setResultList(resultList);
		return pager;
	}
	
/*	public Pager qryForTDPager(int pageNo, int pageSize, String selSql,
			Class clazz) {
		
		String v_table="("+selSql+") vtb";
		String countsql="SELECT COUNT(*) FROM "+v_table;
		int totalRow = jdbcTeradate.queryForInt(countsql);
        int midCount= totalRow-(pageNo-1)*pageSize;
		var1:  每页显示的记录数 pageSize
		var2:  结果集总记录数-(页码-1)*每页显示记录数 
		var3:  任意查询SQL
		String selSqlend="SELECT TOP  " +pageSize +	" * FROM (SELECT TOP " +midCount +	"  *	FROM	(" +
				selSql +" ) vt	ORDER	BY 1 DESC ) AS a ORDER	BY 1 ASC";

		
		List resultList =jdbcTeradate.query(selSqlend,new DAORowMapper(clazz));
		Pager pager = new Pager();
		pager.setPageNo(pageNo);
		pager.setPageSize(pageSize);
		pager.setTotalPages(totalRow/pageSize +1);
		pager.setResultList(resultList);
		
		return pager;
	}*/

	
	
	public String readClob(Connection conn, String sqlclob,String contextField) {
		String content = "";
		try {
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			ResultSet rs_clob = stmt.executeQuery(sqlclob);
			CLOB contents = null;
			while (rs_clob.next()) { // 取出CLOB对象
				contents = (oracle.sql.CLOB) rs_clob.getClob(contextField);
			}
			BufferedReader a = new BufferedReader(contents.getCharacterStream()); // 以字符流的方式读入BufferedReader
			String str = "";

			while ((str = a.readLine()) != null) {
				content = content.concat(str); // 最后以String的形式得到
			}
			conn.commit();
			/*
			 * BufferedWriter out = new BufferedWriter(new
			 * FileWriter("e:/test.txt")); out.write(content);//写入文件
			 * out.close();
			 */
			conn.setAutoCommit(true);
			conn.close();
		} catch (Exception e) {
			logger.error("出现异常:",e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				logger.error("回滚出现异常！",e);
			}
		}
		return content;
	}

	/* 1、往数据库中插入一个新的CLOB对象 */
	@SuppressWarnings("deprecation")
	public static void clobInsert(String infile) throws Exception {
		/* 设定不自动提交 */
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			/* 插入一个空的CLOB对象 */
			stmt.executeUpdate("INSERT INTO TEST_CLOB VALUES (’111’, EMPTY_CLOB())");
			/* 查询此CLOB对象并锁定 */
			ResultSet rs = stmt
					.executeQuery("SELECT CLOBCOL FROM TEST_CLOB WHERE ID=’111’ FOR UPDATE");
			while (rs.next()) {
				/* 取出此CLOB对象 */
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				/* 向CLOB对象中写入数据 */
				BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
				BufferedReader in = new BufferedReader(new FileReader(infile));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			/* 正式提交 */
			conn.commit();
		} catch (Exception ex) {
			/* 出错回滚 */
			conn.rollback();
			throw ex;
		}
		/* 恢复原提交状态 */
		conn.setAutoCommit(defaultCommit);
	}

	/* 2、修改CLOB对象（是在原CLOB对象基础上进行覆盖式的修改） */
	@SuppressWarnings("deprecation")
	public static void clobModify(String infile) throws Exception {
		/* 设定不自动提交 */
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			/* 查询CLOB对象并锁定 */
			ResultSet rs = stmt
					.executeQuery("SELECT CLOBCOL FROM TEST_CLOB WHERE ID=’111’ FOR UPDATE");
			while (rs.next()) {
				/* 获取此CLOB对象 */
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				/* 进行覆盖式修改 */
				BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
				BufferedReader in = new BufferedReader(new FileReader(infile));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			/* 正式提交 */
			conn.commit();
		} catch (Exception ex) {
			/* 出错回滚 */
			conn.rollback();
			throw ex;
		}
		/* 恢复原提交状态 */
		conn.setAutoCommit(defaultCommit);
	}

	/* 3、替换CLOB对象（将原CLOB对象清除，换成一个全新的CLOB对象） */
	@SuppressWarnings("deprecation")
	public static void clobReplace(String infile) throws Exception {
		/* 设定不自动提交 */
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			/* 清空原CLOB对象 */
			stmt.executeUpdate("UPDATE TEST_CLOB SET CLOBCOL=EMPTY_CLOB() WHERE ID=’111’");
			/* 查询CLOB对象并锁定 */
			ResultSet rs = stmt
					.executeQuery("SELECT CLOBCOL FROM TEST_CLOB WHERE ID=’111’ FOR UPDATE");
			while (rs.next()) {
				/* 获取此CLOB对象 */
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				/* 更新数据 */
				BufferedWriter out = new BufferedWriter(clob.getCharacterOutputStream());
				BufferedReader in = new BufferedReader(new FileReader(infile));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			/* 正式提交 */
			conn.commit();
		} catch (Exception ex) {
			/* 出错回滚 */
			conn.rollback();
			throw ex;
		}
		/* 恢复原提交状态 */
		conn.setAutoCommit(defaultCommit);
	}

	/* 4、CLOB对象读取 */
	public static void clobRead(String outfile) throws Exception {
		/* 设定不自动提交 */
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			/* 查询CLOB对象 */
			ResultSet rs = stmt.executeQuery("SELECT * FROM TEST_CLOB WHERE ID=’111’");
			while (rs.next()) {
				/* 获取CLOB对象 */
				oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob("CLOBCOL");
				/* 以字符形式输出 */
				BufferedReader in = new BufferedReader(clob.getCharacterStream());
				BufferedWriter out = new BufferedWriter(new FileWriter(outfile));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				out.close();
				in.close();
			}
		} catch (Exception ex) {
			conn.rollback();
			throw ex;
		}
		/* 恢复原提交状态 */
		conn.setAutoCommit(defaultCommit);
	}

	/* 1、 向数据库中插入一个新的BLOB对象 */
	@SuppressWarnings("deprecation")
	public static void blobInsert(String infile) throws Exception {
		/* 设定不自动提交 */
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			/* 插入一个空的BLOB对象 */
			stmt.executeUpdate("INSERT INTO TEST_BLOB VALUES (’222’, EMPTY_BLOB())");
			/* 查询此BLOB对象并锁定 */
			ResultSet rs = stmt
					.executeQuery("SELECT BLOBCOL FROM TEST_BLOB WHERE ID=’222’ FOR UPDATE");
			while (rs.next()) {
				/* 取出此BLOB对象 */
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				/* 向BLOB对象中写入数据 */
				BufferedOutputStream out = new BufferedOutputStream(blob.getBinaryOutputStream());
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(infile));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			/* 正式提交 */
			conn.commit();
		} catch (Exception ex) {
			/* 出错回滚 */
			conn.rollback();
			throw ex;
		}
		/* 恢复原提交状态 */
		conn.setAutoCommit(defaultCommit);
	}

	/* 2、修改BLOB对象（是在原BLOB对象基础上进行覆盖式的修改） */
	@SuppressWarnings("deprecation")
	public static void blobModify(String infile) throws Exception {
		/* 设定不自动提交 */
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			/* 查询BLOB对象并锁定 */
			ResultSet rs = stmt
					.executeQuery("SELECT BLOBCOL FROM TEST_BLOB WHERE ID=’222’ FOR UPDATE");
			while (rs.next()) {
				/* 取出此BLOB对象 */
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				/* 向BLOB对象中写入数据 */
				BufferedOutputStream out = new BufferedOutputStream(blob.getBinaryOutputStream());
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(infile));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			/* 正式提交 */
			conn.commit();
		} catch (Exception ex) {
			/* 出错回滚 */
			conn.rollback();
			throw ex;
		}
		/* 恢复原提交状态 */
		conn.setAutoCommit(defaultCommit);
	}

	/* 3、替换BLOB对象（将原BLOB对象清除，换成一个全新的BLOB对象） */
	@SuppressWarnings("deprecation")
	public static void blobReplace(String infile) throws Exception {
		/* 设定不自动提交 */
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			/* 清空原BLOB对象 */
			stmt.executeUpdate("UPDATE TEST_BLOB SET BLOBCOL=EMPTY_BLOB() WHERE ID=’222’");
			/* 查询此BLOB对象并锁定 */
			ResultSet rs = stmt
					.executeQuery("SELECT BLOBCOL FROM TEST_BLOB WHERE ID=’222’ FOR UPDATE");
			while (rs.next()) {
				/* 取出此BLOB对象 */
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				/* 向BLOB对象中写入数据 */
				BufferedOutputStream out = new BufferedOutputStream(blob.getBinaryOutputStream());
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(infile));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			/* 正式提交 */
			conn.commit();
		} catch (Exception ex) {
			/* 出错回滚 */
			conn.rollback();
			throw ex;
		}
		/* 恢复原提交状态 */
		conn.setAutoCommit(defaultCommit);
	}

	/* 4、BLOB对象读取 */
	public static void blobRead(String outfile) throws Exception {
		/* 设定不自动提交 */
		boolean defaultCommit = conn.getAutoCommit();
		conn.setAutoCommit(false);
		try {
			/* 查询BLOB对象 */
			ResultSet rs = stmt.executeQuery("SELECT BLOBCOL FROM TEST_BLOB WHERE ID=’222’");
			while (rs.next()) {
				/* 取出此BLOB对象 */
				oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob("BLOBCOL");
				/* 以二进制形式输出 */
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outfile));
				BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			/* 正式提交 */
			conn.commit();
		} catch (Exception ex) {
			/* 出错回滚 */
			conn.rollback();
			throw ex;
		}
		/* 恢复原提交状态 */
		conn.setAutoCommit(defaultCommit);
	}

	public int exeBySql(String sql) {
		return jdbcTemplate.update(sql);
	}

	public Object qryForFieldValue(String sql) {
		//jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper());
		Object retObj = jdbcTemplate.queryForInt(sql);
		return retObj;
	}

	public List qryForList(String sql) {
		return jdbcTemplate.queryForList(sql);
	}
	@SuppressWarnings("unused")
	public Object qryForLob(String sql, String fieldName, String lobType) {
		Object obj = new Object();
		try {
			this.readClob(jdbcTemplate.getDataSource().getConnection(), sql,fieldName);
		} catch (SQLException e) {
			logger.error("",e);
		}
		return null;
	}

	public List qryForObjList(String sql, Class clazz) {
	
		List retList = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper(clazz));
		return  retList;
	}

	public Object queryForObject(String sql, Class clazz) {
		
		Object retObj = this.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(clazz));
		return  retObj;
	}

	public Pager qryForPager(int pageNo, int pageSize, String sql, Class clazz, String dialect) {
		
		return this.qryForPager(pageNo, pageSize, sql, clazz);
	}

    public Pager qryForPager(int pageNo, int pageSize, String sql, Object obj[],Class clazz, String dialect) {
		
		return this.qryForPager(pageNo, pageSize, sql, obj, clazz);
	}

	public Map qryForSelectMap(String sql, Class clazz) {
		return null;
	}
	@SuppressWarnings({ "unused", "deprecation" })
	public int saveLob(String sqlselect,String sqlinst,String sqlupdate, String filepath, String fieldName, String lobType) throws Exception {
		/* 设定不自动提交 */
		Connection conn = this.getConn();
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		boolean defaultCommit;
		try {
			defaultCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			/* 插入一个空的BLOB对象 */
			//stmt.executeUpdate("INSERT INTO TEST_BLOB VALUES (’222’, EMPTY_BLOB())");
			stmt.executeUpdate(sqlinst);
			/* 查询此BLOB对象并锁定 */
			ResultSet rs = stmt.executeQuery(sqlselect+" FOR UPDATE");
			while (rs.next()) {
				/* 取出此BLOB对象 */
				if("CLOB".equals(fieldName)){
					oracle.sql.CLOB clob = (oracle.sql.CLOB) rs.getClob(fieldName);
					  out = new BufferedOutputStream(new FileOutputStream(fieldName));
				}else{
					oracle.sql.BLOB blob = (oracle.sql.BLOB) rs.getBlob(fieldName);
					out = new BufferedOutputStream(blob.getBinaryOutputStream());
					
				}
				/* 向BLOB对象中写入数据 */
				in = new BufferedInputStream(new FileInputStream(filepath));
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
				in.close();
				out.close();
			}
			/* 正式提交 */
			conn.commit();
		} catch (Exception ex) {
			/* 出错回滚 */
			conn.rollback();
			throw ex;
		}
		/* 恢复原提交状态 */
		conn.setAutoCommit(defaultCommit);
	
		return 0;
	
}
	/**
	 * 绑定sql的查询
	 * @param sql
	 * @param obj
	 * @return
	 * @author ht
	 */
	public int exeBySql(String sql,Object obj[]){
		logger.debug("exec sql ["+sql+"]");
		logger.debug("obj = ["+obj+"]");
		return jdbcTemplate.update(sql, obj);
	}


}