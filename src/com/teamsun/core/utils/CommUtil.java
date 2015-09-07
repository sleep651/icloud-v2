package com.teamsun.core.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * 
 * <p>
 * Title:通过工具类,处理一些数据转换及常用功能
 * </p>
 * <p>
 * Description: 包括一些类型转换,数据格式化,生成一些常用的html文本等
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友 张钰
 * @version 1.0
 */
@SuppressWarnings("unused")
public class CommUtil {
	private static final java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
			"yyyy-MM-dd");

	private static final java.text.SimpleDateFormat cnDateFormat = new java.text.SimpleDateFormat(
			"yyyy年MM月dd日");

	private static final java.text.SimpleDateFormat timeFormat = new java.text.SimpleDateFormat(
			"hh:mm:ss");

	private static final java.text.SimpleDateFormat cnTimeFormat = new java.text.SimpleDateFormat(
			"hh点mm分ss秒");

	private static final java.text.SimpleDateFormat longDateFormat = new java.text.SimpleDateFormat(
			"yyyy-MM-dd H:m:s");

	private static final java.text.SimpleDateFormat cnLongDateFormat = new java.text.SimpleDateFormat(
			"yyyy年MM月dd日 H点m分s秒");

	private static final CommUtil util = new CommUtil();

	public static CommUtil getInstance() {
		return util;
	}

	public static java.text.SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public static String formatDate(String format, Object v) {
		if (v == null)
			return null;
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(v);
	}

	public static String format(Object v) {
		if (v == null)
			return null;
		String ret = "";
		try {
			ret = dateFormat.format(v);
		} catch (java.lang.IllegalArgumentException e) {

		}
		return ret;
	}

	public static String sortTime(Object v) {
		if (v == null)
			return null;
		String ret = "";
		try {
			ret = timeFormat.format(v);
		} catch (java.lang.IllegalArgumentException e) {

		}
		return ret;
	}

	public static String cnSortTime(Object v) {
		if (v == null)
			return null;
		String ret = "";
		try {
			ret = cnTimeFormat.format(v);
		} catch (java.lang.IllegalArgumentException e) {

		}
		return ret;	
	}

	public static String cnLongDate(Object v) {
		if (v == null)
			return null;
		String ret = "";
		try {
			ret = cnLongDateFormat.format(v);
		} catch (java.lang.IllegalArgumentException e) {

		}
		return ret;
	}

	public static String longDate(Object v) {
		if (v == null)
			return null;
		String ret = "";
		try {
			ret = longDateFormat.format(v);
		} catch (java.lang.IllegalArgumentException e) {

		}
		return ret;		
	}

	public static java.util.Date formatDate(String s) {
		java.util.Date d = null;
		try {
			d = dateFormat.parse(s);
		} catch (Exception e) {
		}
		return d;
	}

	public static java.util.Date cnFormatDate(String s) {
		java.util.Date d = null;
		try {
			d = cnDateFormat.parse(s);
		} catch (Exception e) {
		}
		return d;
	}

	public static String null2String(Object s) {
		return s == null ? "" : s.toString();
	}

	public static int null2Int(Object s) {
		int v = 0;
		if (s != null) {
			try {
				v = Integer.parseInt(s.toString());
			} catch (Exception e) {
			}
		}
		return v;
	}

	public static String round(double inNumber, int param) {
		String format = "#.";
		for (int i = 0; i < param; i++) {
			format = format.concat("#");
		}
		// 去掉多余小数点
		if (param == 0) {
			format = format.substring(0, format.toString().length() - 1);
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat(format);
		return df.format(inNumber);
	}

	public static String getRandString(int length) {
		StringBuffer s = new StringBuffer("" + (new java.util.Date().getTime()));
		Random r = new Random(10);
		s.append(Math.abs(r.nextInt()));
		if (s.toString().length() > length)
			s.substring(0, length);
		return s.toString();
	}

	public static boolean hasLength(String str) {
		return StringUtils.hasLength(str);
	}

	public static String getOnlyID() {
		String strRnd;
		double dblTmp;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMDDhhmmss");
		// ---- 4random
		dblTmp = java.lang.Math.random() * 100000;
		while (dblTmp < 10000) {
			dblTmp = java.lang.Math.random() * 100000;
		}
		strRnd = String.valueOf(dblTmp).substring(0, 4);
		String s = df.format(new Date()) + strRnd;
		return s;
	}

	/**
	 * @deprecated 使用TagUtil的showPageHtml方法代替 显示页码
	 * @param currentPage
	 * @param pages
	 * @return 分布htmp字符串
	 */
	public static String showPageHtml(int currentPage, int pages) {
		String s = "";
		if (currentPage > 1) {
			s += "<a href=# onclick='return gotoPage(1)'>首页</a> ";
			s += "<a href=# onclick='return gotoPage(" + (currentPage - 1)
					+ ")'>上一页</a> ";
		}

		int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
		if (beginPage < pages) {
			s += "第　";
			for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++) {
				if (i == currentPage)
					s += "<font color=red>" + i + "</font> ";
				else
					s += "<a href=# onclick='return gotoPage(" + i + ")'>" + i
							+ "</a> ";
			}
			s += "页　";
		}
		if (currentPage < pages) {
			s += "<a href=# onclick='return gotoPage(" + (currentPage + 1)
					+ ")'>下一页</a> ";
			s += "<a href=# onclick='return gotoPage(" + pages + ")'>末页</a> ";
		}
		// s+=" 转到<input type=text size=2>页";
		return s;
	}

	/**
	 * @deprecated 使用TagUtil中同名的showPublishPageHtml方法代替
	 * @param path
	 * @param currentPage
	 * @param pages
	 * @return　以静态方式发布的页面分页连接
	 */
	public static String showPublishPageHtml(String path, int currentPage,
			int pages) {
		String s = "";
		boolean isDir = false;
		if (path.endsWith("/"))
			isDir = true;
		if (currentPage > 1) {
			s += "<a href=" + path + (isDir ? "1" : "") + ".html>首页</a> ";
			s += "<a href=" + path
					+ (currentPage - 1 > 1 ? (currentPage - 1) + "" : "")
					+ ".html>上一页</a> ";
		}
		int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
		if (beginPage < pages) {
			s += "第　";
			for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++) {
				if (i == currentPage)
					s += "<font color=red>" + i + "</font> ";
				else
					s += "<a href=" + path
							+ (i > 1 ? i + "" : (isDir ? i + "" : ""))
							+ ".html>" + i + "</a> ";
			}
			s += "页　";
		}
		if (currentPage < pages) {
			s += "<a href=" + path + (currentPage + 1) + ".html>下一页</a> ";
			s += "<a href=" + path + pages + ".html>末页</a> ";
		}
		// s+=" 转到<input type=text size=2>页";
		return s;
	}

	/**
	 * @deprecated 使用TagUtil中的options方法代替
	 * @param items
	 *            列表名称
	 * @param value
	 *            选定值
	 * @return 得到列表框值的结果集
	 */
	public static String getSelectOptions(String[][] items, String value) {
		String s = "";
		for (int i = 0; i < items.length; i++) {
			s += "<option value='" + items[i][0] + "' "
					+ (items[i][0].equals(value) ? "selected" : "") + ">"
					+ items[i][1] + "</option>";
		}
		return s;
	}

	/**
	 * @deprecated 使用TagUtil中的options方法代替
	 * @param min
	 * @param max
	 * @param value
	 * @return 得到列表框值的结果集
	 */
	public static String getSelectOptions(int min, int max, int value) {
		String s = "";
		for (int i = min; i <= max; i++) {
			s += "<option value='" + i + "' " + (i == value ? "selected" : "")
					+ ">" + i + "</option>";
		}
		return s;
	}

	public static String toChinese(String strvalue) {
		try {
			if (strvalue == null)
				return null;
			else {
				strvalue = new String(strvalue.getBytes("ISO8859_1"), "GBK");
				return strvalue;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static String toChinese(String strvalue, String charset) {
		try {
			if (charset == null || charset.equals(""))
				return toChinese(strvalue);
			else {
				strvalue = new String(strvalue.getBytes("ISO8859_1"), charset);
				return strvalue;
			}
		} catch (Exception e) {
			return null;
		}
	}

/*	public static Iterator CopyIterator(Class classType, Iterator src) {
		return CopyList(classType, src).iterator();
	}

	public static List CopyList(Class classType, Iterator src) {
		List tag = new ArrayList();
		while (src.hasNext()) {
			Object obj = null, ormObj = src.next();
			try {
				obj = classType.newInstance();
				BeanWrapper wrapper = new BeanWrapper(obj);
				BeanWrapper wrapper1 = new BeanWrapper(ormObj);
				PropertyDescriptor descriptors[] = wrapper
						.getPropertyDescriptors();
				for (int i = 0; i < descriptors.length; i++) {
					String name = descriptors[i].getName();
					wrapper.setPropertyValue(name, wrapper1
							.getPropertyValue(name));
				}

			} catch (Exception e) {
			}
			if (obj != null)
				tag.add(obj);
		}
		return tag;
	}

	public static void Map2Obj(Map map, Object obj) {
		BeanWrapper wrapper = new BeanWrapper(obj);
		PropertyDescriptor descriptors[] = wrapper.getPropertyDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			Object v = map.get(name);
			if (v != null && descriptors[i].getWriteMethod() != null) {
				wrapper.setPropertyValue(name, v);
			}
		}
	}

	public static void Obj2Map(Object obj, Map map) {
		if (map == null)
			map = new java.util.HashMap();
		BeanWrapper wrapper = new BeanWrapper(obj);
		PropertyDescriptor descriptors[] = wrapper.getPropertyDescriptors();
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			try {
				if (descriptors[i].getReadMethod() != null) {
					map.put(name, wrapper.getPropertyValue(name));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}*/

	public static List toRowChildList(List list, int perNum) {
		List l = new java.util.ArrayList();
		if (list == null)
			return l;
		for (int i = 0; i < list.size(); i += perNum) {
			List cList = new ArrayList();
			for (int j = 0; j < perNum; j++) {
				if (i + j < list.size())
					cList.add(list.get(i + j));
			}
			l.add(cList);
		}
		return l;
	}

	public static List toRowDivList(List list, int perNum) {
		List l = new java.util.ArrayList();
		if (list == null)
			return l;
		for (int i = 0; i < list.size();) {
			List cList = new ArrayList();
			for (int j = 0; j < perNum; j++, i++) {
				if (i < list.size())
					cList.add(list.get(i));
			}
			l.add(cList);
		}
		return l;
	}

	/*
	 * 增加pagination和pagination_ajax
	 */
/*	public static void saveIPageList2WebForm(IPageList pList, WebForm form) {
		if (pList != null) {
			form.addResult("list", pList.getResult());
			form.addResult("pages", new Integer(pList.getPages()));
			form.addResult("rows", new Integer(pList.getRowCount()));
			form.addResult("page", new Integer(pList.getCurrentPage()));
			form.addResult("pageSize", new Integer(pList.getPageSize()));
			form.addResult("pagination", TagUtil.showPageHtml(pList
					.getCurrentPage(), pList.getPages()));
			form.addResult("gotoPageHTML", CommUtil.showPageHtml(pList
					.getCurrentPage(), pList.getPages()));
			form.addResult("pagination_ajax", TagUtil.showPageHtmlAjax(pList
					.getCurrentPage(), pList.getPages()));
		}
	}*/

	public static String substring(String s, int maxLength) {
		if (!hasLength(s))
			return s;
		if (s.length() <= maxLength)
			return s;
		return s.substring(0, maxLength) + "...";
	}

	/*
	 * 将Long型数据转换成时间格式的字符串，例如：formatLongToTimeStr(100000)=0小时1分钟40秒
	 */
	public static String formatLongToTimeStr(Long l) {
		String str = "";
		int hour = 0;
		int minute = 0;
		int second = 0;

		second = l.intValue() / 1000;

		if (second > 60) {
			minute = second / 60;
			second = second % 60;
		}
		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		}
		return (null2String(hour) + "小时" + null2String(minute) + "分钟"
				+ null2String(second) + "秒");
	}
	
	public static String[] getMapKeyValue(Map map){
	       Iterator ite = map.entrySet().iterator();
	        while (ite.hasNext())
	        {
	            Map.Entry obj = (Map.Entry) ite.next();
	            Object key = obj.getKey();
	            Object value = obj.getValue();
	        }
			return null;
	}
}
