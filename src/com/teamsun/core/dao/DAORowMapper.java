package com.teamsun.core.dao;


import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSetMetaData;

import com.teamsun.core.utils.StringUtils;

/**
 * @author guojf 调用方法 :其中只要 new DAORowMapper（xxxxVO.class）;xxxxVO:是要获得的结果集的pojo;
 *         规则是：1：一般是选出字段数据要和xxxxVO中的属性一致，2：字段字 和 xxxxVO中的属性要一致，在这VO：全取小写字段名
 *         public List findAll() { String sql = "select * from " + tableName;
 *         return jdbcTemplate.query(sql, new DAORowMapper(UserVO.class)); }
 */
public class DAORowMapper implements RowMapper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(DAORowMapper.class);

	@SuppressWarnings("unchecked")
	private Class rowObjectClass;

	@SuppressWarnings("unchecked")
	public DAORowMapper(Class rowObjectClass) {
		this.rowObjectClass = rowObjectClass;
	}

	public Object mapRow(ResultSet rs, int index) throws SQLException {
		Object object;
		try {
			object = rowObjectClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		ResultSetWrappingSqlRowSetMetaData wapping = new ResultSetWrappingSqlRowSetMetaData(
				rs.getMetaData());
		for (int i = 1; i <= wapping.getColumnCount(); i++) {
			String name = wapping.getColumnName(i);
			Object value = rs.getObject(i);
			setFieldVlaue(object, name, value);
		}

		return object;
	}

	/*
	 * 注意field的访问问题 操作规则：要求表里的字段字和VO中的一致：在这是统一在VO中用小写
	 */

	@SuppressWarnings("unchecked")
	public static void setFieldVlaue(Object obj, String fieldName, Object value) {
		Class c = obj.getClass();
		try {
			Field field;
			try {
				/*
				 * 在此可加入一些VO与表中属性－－－＞字段的名称对应关系，通用默认是全小写字段名为属性名 也要扩展：去下划线等
				 */
				fieldName = chgField(fieldName);// 字段先整理一下．１：去下划线，２：变小写

				/*
				 * 处理VO对象中的字段，和数据库中处理过后的字段时行匹配:规则：也是１：去下划线，２：变小写
				 * 方法：把VO中处理后的字段名做KEY，处理后的值做VALUE 放入Map中 用key去比较，用value去反射
				 */
				Field[] objfields = c.getDeclaredFields();
				Map compMap = new HashMap();
				for (int i = 0; i < objfields.length; i++) {
					Field objfield = objfields[i];
					String oobjfieldNm=objfield.getName();
					String objfieldNm=chgField(oobjfieldNm);
					compMap.put(objfieldNm, oobjfieldNm);
				}
                /*从处理好后的Map中取对象中真实的属性名*/
				if(null!=compMap.get(fieldName)){
					fieldName=compMap.get(fieldName).toString();
					field = c.getDeclaredField(fieldName);
					field.setAccessible(true);
					if (!(value == null || value.equals(""))) {
						value = transValue(value);
						field.set(obj, value);
					}
				}else{
					logger.warn("当前反射用的字段属性和数据库中的字段不匹配：　"+fieldName);
				}

			} catch (NoSuchFieldException e) {
				return;
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 作用对象类型，oracle---->java 转换
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Object transValue(Object obj) {
		try {
			String fieldtypename = obj.getClass().getName();
			if (fieldtypename.equals("java.math.BigDecimal")) {
				obj = Long.parseLong(obj.toString());
			} else if (fieldtypename.equals("java.lang.String")) {
				obj = String.valueOf(obj);
			} else if (fieldtypename.equals("java.util.Date")) {
				obj = Date.parse(obj.toString());
			} else if (fieldtypename.equals("java.lang.Integer")) {
				obj = Integer.parseInt(obj.toString());
			} else if (fieldtypename.equals("java.lang.Long")) {
				obj = Long.parseLong(obj.toString());
			} else if (fieldtypename.equals("java.sql.Timestamp")) {
				obj = Timestamp.parse(obj.toString());
			} else {
				fieldtypename.equals("Object");
				obj = (Object) obj;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public static Object getFieldVlaue(Object obj, String fieldName,
			Object value) {
		Class c = obj.getClass();
		try {
			Field field;
			try {
				field = c.getDeclaredField(fieldName);
				field.setAccessible(true);
			} catch (NoSuchFieldException e) {
				return null;
			}
			return field.get(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static List findNotNullField(Object obj) {
		Class c = obj.getClass();
		List list = new ArrayList(10);
		try {
			Field[] fields = c.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				if (fields[i].get(obj) != null) {
					list.add(fields[i]);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public static String chgField(String strfield) {
		String retstr = "";
		try {
			retstr = StringUtils.deleteAny(strfield, "_");

		} catch (Exception e) {
			logger.error("字段在:chgField 中有异常，可能是字段名为null");
			throw new RuntimeException(e);
		}
		return retstr.toLowerCase();
	}

}
