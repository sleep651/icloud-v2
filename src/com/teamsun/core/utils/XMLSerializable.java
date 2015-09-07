package com.teamsun.core.utils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Clob;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.teamsun.core.exception.SystemException;

/**
 * ����XML�Ĺ�����
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Best-Star</p>
 * @author Guojf
 * @version 1.0
 */
public abstract class XMLSerializable
    implements Serializable {
    public XMLSerializable() {}

    public void init(Properties properties) throws SystemException {
        for (Enumeration e = properties.propertyNames(); e.hasMoreElements(); ) {
            String key = (String) e.nextElement();
            try {
                Method getMethod = null;
                String subName = key.substring(0, 1).toUpperCase() +
                    key.substring(1);
                try {
                    getMethod = this.getClass().getMethod("get" + subName,
                        new Class[] {});
                }
                catch (java.lang.NoSuchMethodException n) {
                    getMethod = this.getClass().getMethod("is" + subName,
                        new Class[] {});

                }
                Class type = getMethod.getReturnType();
                Method setMethod = this.getClass().getMethod("set" + subName,
                    new Class[] {type});
                setMethod.invoke(this,
                                 new Object[] {StringUtils.convert(type,
                    properties.getProperty(key))});
            }
            catch (java.lang.NoSuchMethodException n) {}
            catch (java.lang.IllegalAccessException iae) {
                throw new SystemException(iae);
            }
            catch (java.lang.reflect.InvocationTargetException ite) {
                throw new SystemException(ite);
            }
        }
    }

    public String toXML() throws SystemException {
        return toXML(null);
    }

    /**
     * ������ת����XML
     * @param name
     * @param array
     * @return
     * @throws SystemException
     */
    public String arrayToXML(String name, Object[] array) throws
        SystemException {
        StringBuffer xml = new StringBuffer();
        String nodeName = name == null ? "Array" : name;
        if (array != null) {
            xml.append("<");
            xml.append(nodeName);
            xml.append(" ClassName = \"");
            xml.append(array.getClass().getComponentType().getName());
            xml.append("\">\n");
            try {
                for (int ai = 0; ai < array.length; ai++) {
                    try {
                        if (array[ai] != null) {
                            xml.append(array[ai].getClass().getMethod("toXML",
                                new Class[] {}).invoke(
                                array[ai], new Object[] {}));
                        }
                    }
                    catch (NoSuchMethodException e) {
                        xml.append("<Element>\n<![CDATA[");
                        xml.append(array[ai].toString());
                        xml.append("]]>\n</Element>\n");
                    }
                }
            }
            catch (java.lang.IllegalAccessException e) {
                throw new SystemException(e);
            }
            catch (java.lang.reflect.InvocationTargetException e) {
                throw new SystemException(e);
            }
            xml.append("</");
            xml.append(nodeName);
            xml.append(">\n");
        }
        return xml.toString();
    }

    /**
     * ��ӳ��(Map)ת����XML
     * @param name
     * @param map
     * @return
     * @throws SystemException
     */
    @SuppressWarnings("unused")
    public String mapToXML(String name, Map map) throws SystemException {
        StringBuffer xml = new StringBuffer();
        String nodeName = name == null ? "Array" : name;
        try {
            if (map != null) {
                xml.append("<");
                xml.append(nodeName);
                xml.append(" ClassName=\"");
                xml.append(map.getClass().getName());
                xml.append("\">\n");
                Iterator iterator = map.keySet().iterator();
                
				int v = 0;
                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    Object value = map.get(key);
                    if (value.getClass().isArray()) {
                        xml.append(arrayToXML( (String) key, (Object[]) value));
                    }
                    else if (Class.forName("java.util.Collection").
                             isAssignableFrom(
                        value.getClass())) {
                        xml.append(collectionToXML( (String) key,
                            (Collection) value));
                    }
                    else if (Class.forName("java.util.Map").isAssignableFrom(
                        value.
                        getClass())) {
                        xml.append(mapToXML( (String) key, (Map) value));
                    }
                    else {
                        try {
                            xml.append(value.getClass().getMethod("toXML",
                                new Class[] {}).invoke(value, new Object[] {}));
                        }
                        catch (NoSuchMethodException e) {
                            xml.append("<");
                            xml.append( (String) key);
                            xml.append(" ClassName=\"");
                            xml.append(value.getClass().getName());
                            xml.append("\"><![CDATA[");
                            xml.append(value.toString());
                            xml.append("]]>");
                            xml.append("</");
                            xml.append( (String) key);
                            xml.append(">\n");
                        }
                    }
                }
                xml.append("</");
                xml.append(nodeName);
                xml.append(">\n");
            }
        }
        catch (ClassNotFoundException e) {
            throw new SystemException(e);
        }
        catch (java.lang.IllegalAccessException e) {
            throw new SystemException(e);
        }
        catch (java.lang.reflect.InvocationTargetException e) {
            throw new SystemException(e);
        }

        return xml.toString();

    }

    /**
     * �Ѽ���(Collection)ת����XML
     * @param name
     * @param collection
     * @return
     * @throws SystemException
     */
    public String collectionToXML(String name, Collection collection) throws
        SystemException {
        StringBuffer xml = new StringBuffer();
        String nodeName = name == null ? "Collection" : name;
        try {
            if (collection != null) {
                xml.append("<");
                xml.append(nodeName);
                xml.append(" ClassName=\"");
                xml.append(collection.getClass().getName());
                xml.append("\">\n");
                Iterator iterator = collection.iterator();
                while (iterator.hasNext()) {
                    Object value = iterator.next();
                    if (value.getClass().isArray()) {
                        xml.append(arrayToXML(null, (Object[]) value));
                    }
                    else if (Class.forName("java.util.Collection").
                             isAssignableFrom(
                        value.getClass())) {
                        xml.append(collectionToXML(null, (Collection) value));
                    }
                    else if (Class.forName("java.util.Map").isAssignableFrom(
                        value.
                        getClass())) {
                        xml.append(mapToXML(null, (Map) value));
                    }
                    else {
                        try {
                            xml.append(value.getClass().getMethod("toXML",
                                new Class[] {}).invoke(value, new Object[] {}));
                        }
                        catch (NoSuchMethodException e) {
                            xml.append("<Element ClassName=\"");
                            xml.append(value.getClass().getName());
                            xml.append("\">");
                            xml.append("<![CDATA[");
                            xml.append(value.toString());
                            xml.append("]]>");
                            xml.append("</Element>\n");
                        }
                    }
                }
                xml.append("</");
                xml.append(nodeName);
                xml.append(">\n");
            }
        }
        catch (ClassNotFoundException e) {
            throw new SystemException(e);
        }
        catch (java.lang.IllegalAccessException e) {
            throw new SystemException(e);
        }
        catch (java.lang.reflect.InvocationTargetException e) {
            throw new SystemException(e);
        }

        return xml.toString();
    }

    /**
     *
     * @param name
     * @return
     * @throws SystemException
     */
    public String toXML(String name) throws SystemException {
        StringBuffer xml = new StringBuffer();
        String elementName = name == null ? "Element" : name;
        xml.append("<" + elementName + " ClassName=\"" +
                   this.getClass().getName() +
                   "\" ");
        if (name != null) {
            xml.append("Name=\"" + name + "\"");
        }
        xml.append(">\n");
        Method[] method = this.getClass().getMethods();
        String nodeName = "";
        for (int i = 0; i < method.length; i++) {
            if (method[i].getName().startsWith("get") ||
                method[i].getName().startsWith("is")) {
                try {
                    if (method[i].getName().startsWith("get")) {
                        nodeName = method[i].getName().substring(3);
                    }
                    else {
                        nodeName = method[i].getName().substring(2);
                    }
                    this.getClass().getMethod("set" + nodeName,
                                              new Class[] {method[i].
                                              getReturnType()});

                    if (Class.forName("java.util.Collection").isAssignableFrom(
                        method[i].
                        getReturnType())) {
                        Collection collection = (Collection) method[i].invoke(this,
                            new Object[] {});
                        if (collection != null) {
                            xml.append(collectionToXML(nodeName, collection));
                        }
                    }
                    else if (Class.forName("java.util.Map").isAssignableFrom(
                        method[i].
                        getReturnType())) {
                        Map map = (Map) method[i].invoke(this, new Object[] {});
                        if (map != null) {
                            xml.append(mapToXML(nodeName, map));
                        }
                    }
                    else if (Class.forName("java.sql.Clob").isAssignableFrom(
                        method[i].
                        getReturnType())) {
                        Clob clob = (Clob) method[i].invoke(this, new Object[] {});
                        if (clob != null) {
                            xml.append(clobToXML(clob));
                        }
                    }
                    else if (!nodeName.equalsIgnoreCase("Class")) {
                        Object obj = method[i].invoke(this, new Object[] {});
                        if (obj != null) {
                            if (obj.getClass().isArray()) {
                                xml.append(arrayToXML(nodeName, (Object[]) obj));
                            }
                            else {
                                try {
                                    xml.append(obj.getClass().getMethod("toXML",
                                        new Class[] {Class.forName(
                                        "java.lang.String")}).invoke(
                                        obj, new Object[] {nodeName}));
                                }
                                catch (NoSuchMethodException e) {
                                    xml.append("<");
                                    xml.append(nodeName);
                                    xml.append(">\n");
                                    xml.append("<![CDATA[");
                                    xml.append(obj.toString());
                                    xml.append("]]>\n");
                                    xml.append("</");
                                    xml.append(nodeName);
                                    xml.append(">\n");
                                }
                            }

                        }

                    }
                }
                catch (java.lang.NoSuchMethodException n) {}
                catch (java.lang.ClassNotFoundException e) {
                    throw new SystemException(e);
                }
                catch (java.lang.IllegalAccessException e) {
                    throw new SystemException(e);
                }
                catch (java.lang.reflect.InvocationTargetException e) {
                    throw new SystemException(e);
                }
            }

        }
        xml.append("</" + elementName + ">\n");
        return xml.toString();
    }

    /**
     * ��clobת����XML
     * @param clob
     * @return
     * @throws SystemException
     */
    public String clobToXML(java.sql.Clob clob) throws SystemException {
        return "<![CDATA[" + StringUtils.converClobToString(clob) + "]]>";
    }
}
