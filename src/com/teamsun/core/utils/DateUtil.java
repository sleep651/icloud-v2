/**
 * Copyright By 2007 TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.core.utils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Teamsun ChinaPost Cpcim
 * @author guojf
 * @since Apr 18, 2008
 * @version 2.0 
 * @TODO:
 */
@SuppressWarnings("unused")
public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	public static Date stringToDate(String dateString, String dataFormat) {
		if (isEmpty(dateString)) {
			return null;
		}

		if (dateString.length() != 14) {
			return null;
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(dataFormat);

		try {
			return dateFormat.parse(dateString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	/**
	 * 将日期转换为中文表示方式的字符串(格式为 yyyy年MM月dd日 HH:mm:ss).
	 */
	public static String dateToChineseString(Date date) {
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy年MM月dd日 HH:mm:ss");

		return dateFormat.format(date);
	}

	/**
	 * 将日期转换为中文表示方式的字符串(格式为 yyyy年MM月dd日 HH:mm:ss).
	 */
	public static String currdateToChineseString() {
		Date date= new Date();
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy年MM月dd日 HH:mm:ss");

		return dateFormat.format(date);
	}
	/**
	 * 将日期转换为 14 位的字符串(格式为yyyyMMddHHmmss).
	 */
	public static String dateTo14String(Date date) {
		if (date == null) {
			return null;
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmss");

		return dateFormat.format(date);
	}
	/**
	 * 将日期转换为 17 位的字符串(格式为yyyy-MM-dd HHmmss).
	 */
	public static String dateTo19String(Date date) {
		if (date == null) {
			return null;
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		return dateFormat.format(date);
	}
	/**
	 * 将 14 位的字符串(格式为yyyyMMddHHmmss)转换为日期.
	 */
	public static Date string14ToDate(String input) {
		if (isEmpty(input)) {
			return null;
		}

		if (input.length() != 14) {
			return null;
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmss");

		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 将 19 位的字符串(格式为yyyy-MM-dd HH:mm:ss)转换为日期.
	 */	
	public static Date string19ToDate(String input) {
		if (isEmpty(input)) {
			return null;
		}

		if (input.length() != 19) {
			return null;
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	/**
	 * 格式化日期到日时分秒时间格式的显示. d日 HH:mm:ss
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToDHMSString(java.util.Date date) {
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"d日 HH:mm:ss");

		return dateFormat.format(date);

	}

	/**
	 * 格式化日期到时分秒时间格式的显示.
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToHMSString(java.util.Date date) {
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"HH:mm:ss");

		return dateFormat.format(date);

	}

	/**
	 * 将时分秒时间格式的字符串转换为日期.
	 *
	 * @param input
	 * @return
	 */
	public static Date parseHMSStringToDate(String input) {
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"HH:mm:ss");

		try {
			return dateFormat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 格式化日期到 Mysql 数据库日期格式字符串的显示.
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToMysqlString(java.util.Date date) {
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		return dateFormat.format(date);

	}

	/**
	 * 将 Mysql 数据库日期格式字符串转换为日期.
	 *
	 * @param input
	 * @return
	 */
	public static Date parseStringToMysqlDate(String input) {
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		try {
			return dateFormat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 返回时间字符串, 可读形式的, M月d日 HH:mm 格式. 2004-09-22, LiuChangjiong
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToMMddHHmm(java.util.Date date) {
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"M月d日 HH:mm");

		return dateFormat.format(date);
	}

	/**
	 * 返回时间字符串, 可读形式的, yy年M月d日HH:mm 格式. 2004-10-04, LiuChangjiong
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToyyMMddHHmm(java.util.Date date) {
		if (date == null) {
			return "";
		}

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yy年M月d日HH:mm");

		return dateFormat.format(date);
	}
	public static boolean isEmpty(String input) {
		return (input == null || input.length() == 0);
	}
	
	/**
	 * 生成一个 18 位的 yyyyMMddHHmmss.SSS 格式的日期字符串.
	 *
	 * @param date
	 *            Date
	 * @return String
	 */
	public static String genTimeStampString(Date date) {
		java.text.SimpleDateFormat df = new java.text.SimpleDateFormat(
				"yyyyMMddHHmmssSSS");
		return df.format(date);
	}
    /**
     * 把精简格式日期（例：1978-10-21）转化为中文描述（例：1978年10月21日）。
     * 注意：
     * 1、此方法不验证日期正确性
     * 2、dateString还可以适用于只有年月（例：1978-10）或者只有年（例：1978）的情况
     * 3、此方法缺省分隔符为“-”
     * @param dateString 精简格式日期字符串
     * @return 中文日期字符串
     */
    public static String formatDateStringToChinese(String dateString) {
        return formatDateStringToChinese(dateString, "-");
    }

    /**
     * 把精简格式日期（例：1978-10-21）转化为中文描述（例：1978年10月21日）。
     * 注意：
     * 1、此方法不验证日期正确性
     * 2、dateString还可以适用于只有年月（例：1978-10）或者只有年（例：1978）的情况
     * @param dateString 精简格式日期字符串
     * @param separator 分隔符号（例：1978-10-21 分隔符为“-”，1978/10/21 分隔符为“/”）
     * @return 中文日期字符串
     */
    public static String formatDateStringToChinese(String dateString,
        String separator) {
        StringBuffer chinese = null;
        if (dateString != null) {
            StringTokenizer stk = new StringTokenizer(dateString, separator);
            if (stk.hasMoreTokens()) {
                //年
                chinese = new StringBuffer();
                chinese.append(stk.nextToken());
                chinese.append("年");
                if (stk.hasMoreTokens()) {
                    //月
                    chinese.append(stk.nextToken());
                    chinese.append("月");
                    if (stk.hasMoreTokens()) {
                        //日
                        chinese.append(stk.nextToken());
                        chinese.append("日");
                    }
                }

            }
            return chinese.toString();
        }
        return null;
    }

	/**
	 * 格式化日期到制定的格式字符串的显示.
	 * okey2k
	 * Jul 10, 2008 4:37:06 PM
	 * @param date
	 * @param dateFmt
	 * @return
	 */
	public static Date formatDateToCustfmt(java.util.Date date,String dateFmt) {
		if (date == null) {
			return null;
		}
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				dateFmt);
			return java.sql.Date.valueOf(dateFormat.format(date));

	}
	  
   /**
    * 罗宁
    * 2008-7-16 上午09:31:45
    * @param  currDate
    * @return true月末,false不是月末 
    * TODO:判断日期是否为月末
    */
	public static boolean isEndOfMonth(Date currDate) {
		Calendar calendar = Calendar.getInstance();
	
		calendar.setTime(currDate);
		if (calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			return true;
		} else {
			return false;
		}
	} 
	
	public static String getStrNowDate() {
		java.util.Date tdate = new java.util.Date();
		String nowtime = new Timestamp(tdate.getTime()).toString();
		nowtime = nowtime.substring(0, 10);
		String nowYear = nowtime.substring(0, 4);
		String nowMonth = nowtime.substring(5, 7);
		String nowDay = nowtime.substring(8, 10);
		String nowdate = nowYear + nowMonth + nowDay;
		return nowdate;

	}

	/**
	 * ����˵��ȡ��ǰ���� (��ʽ 2003-08-01)
	 * 
	 */
	public static String getNowDate() {
		java.util.Date tdate = new java.util.Date();
		String nowtime = new Timestamp(tdate.getTime()).toString();
		nowtime = nowtime.substring(0, 10);
		return nowtime;
	}
}
