/**
 * Copyright By 2008 ChinaPost_TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.core.utils;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.teamsun.core.exception.SystemException;

/**
 * @author GuoJF
 * @since Dec 30, 2008
 * @version 2.0 
 * @Component :Spring 注入
 * TODO:
 */
public class BeanUtils {
	private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
	/**
	 * Administrator_guojf
	 * Dec 30, 2008 12:21:35 AM
	 * @param args
	 * TODO:
	 */


    /**
     * 取出bean中set/get方法对应的字段，切忌不要同时使用空字段的set/get方法
     * @param beanClass
     * @return
     */
    public static Vector getProperties(Class beanClass) {
        Vector properties = new Vector();
        //Class beanClass = bean.getClass();
        Method[] methods = beanClass.getMethods();

        //遍历方法集，不适用set/get方法重载情况！
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String name = method.getName();
            Class type = method.getReturnType();
            if (name.startsWith("get") || name.startsWith("is")) {
                String property = null;
                if (name.startsWith("is")) {
                    property = name.substring(2);
                }
                else {
                    property = name.substring(3);
                }
                //检查
                try {
                    
					@SuppressWarnings("unused")
					Method setMethod = beanClass.getMethod("set" + property,
                        new Class[] {
                        type
                    });
                    properties.add(StringUtils.firstCharToLowerCase(property));
                }
                catch (SecurityException ex) {
                }
                catch (NoSuchMethodException ex) {
                	logger.info(ex.toString());
                }
            }
        }
        return properties;
    }

    /**
     * 获取Bean属性的数据类型
     * @param beanClass Bean类
     * @param name 属性名称
     * @return 数据类型的Class
     * @throws SystemException
     */
    public static Class getPropertyType(Class beanClass, String name) throws
        SystemException {
        @SuppressWarnings("unused")
		Vector properties = new Vector();
        String getName = "get" + StringUtils.firstCharToUpperCase(name);
        String isName = "is" + StringUtils.firstCharToUpperCase(name);
        Method method = null;
        try {
            try {
                method = beanClass.getMethod(getName, new Class[] {});
            }
            catch (NoSuchMethodException e) {
                method = beanClass.getMethod(isName, new Class[] {});
            }
            return method.getReturnType();
        }
        catch (NoSuchMethodException e) {
            throw new SystemException("无效的属性。", e);
        }
    }

    /**
     * 获得Bean对象实例的属性值
     * @param bean Bean对象实例
     * @param name 属性名称
     * @return 属性值对象
     * @throws SystemException
     */
    public static Object getPropertyValue(Object bean, String name) throws
        SystemException {
        Class beanClass = bean.getClass();
        String getName = "get" + StringUtils.firstCharToUpperCase(name);
        String isName = "is" + StringUtils.firstCharToUpperCase(name);
        Method method = null;
        try {
            try {
                method = beanClass.getMethod(getName, new Class[] {});
            }
            catch (NoSuchMethodException e) {
                method = beanClass.getMethod(isName, new Class[] {});
            }
            return method.invoke(bean, new Object[] {});
        }
        catch (NoSuchMethodException e) {
            throw new SystemException("无效的属性。", e);
        }
        catch (java.lang.IllegalAccessException iae) {
            throw new SystemException(iae);
        }
        catch (java.lang.reflect.InvocationTargetException ite) {
            throw new SystemException(ite);
        }
    }

    /**
     * 设置Bean对象的属性值实例
     * @param bean Bean对象实例
     * @param name 属性名称
     * @param value 属性值
     * @throws SystemException
     */
    public static void setPropertyValue(Object bean, String name, Object value
                                        ) throws
        SystemException {
        Class beanClass = bean.getClass();
        String getMethodName = "get" + StringUtils.firstCharToUpperCase(name);
        String setMethodName = "set" + StringUtils.firstCharToUpperCase(name);
        try {
            Method getMethod = beanClass.getMethod(getMethodName, new Class[] {
            });
            Method setMethod = beanClass.getMethod(setMethodName,
                new Class[] {getMethod.
                getReturnType()});
            setMethod.invoke(bean, new Object[] {value});
        }
        catch (NoSuchMethodException e) {
            throw new SystemException("无效的属性。", e);
        }
        catch (java.lang.IllegalAccessException iae) {
            throw new SystemException(iae);
        }
        catch (java.lang.reflect.InvocationTargetException ite) {
            throw new SystemException(ite);
        }
    }

    /**
     * 设置Bean对象的属性值实例
     * @param bean Bean对象实例
     * @param name 属性名称
     * @param value 属性值
     * @param propertyClass 属性值的实际类型（必须value.getClass()的子类）
     * @throws SystemException
     */
    public static void setPropertyValue(Object bean, String name, Object value,
                                        Class propertyClass) throws
        SystemException {
        if (value != null && !propertyClass.isAssignableFrom(value.getClass())) {
            throw new SystemException("value与PropertyClass类型不符。");
        }
        setPropertyValue(bean, name, value);
    }

    /**
     * 设置属性值
     * @param bean Bean对象实例
     * @param valueMap 属性值Map
     * @throws SystemException
     */
    public static void setPropertyValues(Object bean, Map valueMap) throws
        SystemException {
        Set keySet = valueMap.keySet();
        Iterator iterator = keySet.iterator();
        if (iterator.hasNext()) {
            Object key = iterator.next();
            Object value = valueMap.get(key);
            setPropertyValue(bean, key.toString(), value);
        }
    }

    /**
     * 取出bean中的指定列的值
     * @param v_bean
     * @param property
     * @return Vector
     * @throws SystemException
     */
    public static Vector getColValuesOfBean(Vector v_bean,String property)
     throws SystemException{
      Vector right_v = new Vector();
      if(v_bean!=null){
        for(int i=0;i<v_bean.size();i++){
           BasicBean bean = (BasicBean)v_bean.get(i);
           Object value = BeanUtils.getPropertyValue(bean, property);
           right_v.add(value);
        }
      }
      return right_v;
   }


}

