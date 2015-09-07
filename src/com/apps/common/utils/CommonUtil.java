/**
 * Copyright By 2009 TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.apps.common.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CPNAIS
 * @author ht
 * @since 2009-4-1
 * @version 1.0 
 */
public class CommonUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(CommonUtil.class);
	
	/**
	 * 今天
	 * @return
	 */
	public static String getToday(){
		SimpleDateFormat daytime = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		String today = daytime.format(calendar.getTime());
		logger.debug("取当天日期【{}】",today);
		return today;
	}
	
	public static String getCurMonth(){
		SimpleDateFormat daytime = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
		String curMonth = daytime.format(calendar.getTime());
		logger.debug("取当前月份【{}】",curMonth);
		return curMonth;
	}
	
	/**
	 * 判断是否是当前月
	 * @param month
	 * @return
	 */
	public static Boolean isCurMonth(String month){
		SimpleDateFormat daytime = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
		String curMonth = daytime.format(calendar.getTime());
		return month.equals(curMonth);
	}
	
	/**
	 * 昨天
	 * @return
	 */
	public static String getYesterday(){
		SimpleDateFormat daytime = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,  -1);
		String today = daytime.format(calendar.getTime());
		logger.debug("取昨天日期【{}】",today);
		return today;
	}
	public static String getSomeday(int i){
		SimpleDateFormat daytime = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,  i);
		String today = daytime.format(calendar.getTime());
		logger.debug("取昨天日期【{}】",today);
		return today;
	}
	public static String getDayByOfSomeMonth(String curday,int month) throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date   mydate=formatter.parse(curday);
        Calendar calendar = Calendar.getInstance();   
        calendar.setTime(mydate);
        calendar.add(Calendar.MONTH ,month);
        return  formatter.format(calendar.getTime());
	}
	
	/**
	 * 动态日期
	 * @return
	 
	public static String getStateday(int day){
		SimpleDateFormat daytime = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE,  -day);
		String today = daytime.format(calendar.getTime());
		logger.debug("动态日期【{}】",today);
		return today;
	}*/
	

    public static String getBeforeMonth(int amount) {  
    	SimpleDateFormat daytime = new SimpleDateFormat("yyyyMM");
    	Calendar cal = Calendar.getInstance();  
    	cal.setTime(cal.getTime());
    	cal.add(Calendar.MONTH, -amount);          
    	String month = daytime.format(cal.getTime());
    	return month;
    	
    }
	/**
	 * 本月第一天
	 * @return
	 */
	public static String getFirstMonthDay(){
		SimpleDateFormat daytime = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
		String today = daytime.format(calendar.getTime());
		logger.debug("取本月第一天日期【{}】",today);
		return today+"01";
	}
	
	/**
	 * 前60天
	 * @return
	 */
	public static String getSixtyDayAgo(){
		SimpleDateFormat daytime = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONDAY)-2);
		calendar.add(Calendar.DATE,  -60);
		String today = daytime.format(calendar.getTime());
		logger.debug("前60天【{}】",today);
		return today;
	}
	
	/**
	 * 根据输入月份和往前倒退月数取得日期
	 * @param year
	 * @param month
	 * @param i
	 * @return
	 */
	public static String getActualMonthBefore(int year,int month,int i){
		SimpleDateFormat daytime = new SimpleDateFormat("yyyyMM");
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, 1);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONDAY)-(i+1));
		return daytime.format(calendar.getTime());
	}
	/**
	 * 获得相邻的月份
	 * @param month
	 * @param i
	 * @return
	 * @throws ParseException
	 */
	public static String getNearbyMonth(String month,int i) throws ParseException
	{
		SimpleDateFormat   formatter=new   SimpleDateFormat("yyyyMM");  
        Date   mydate=formatter.parse(month);
        Calendar calendar = Calendar.getInstance();   
        calendar.setTime(mydate);
        calendar.add(Calendar.MONTH ,i);
        return  formatter.format(calendar.getTime());
        
	}

	/**    
	 * 得到上月的最后一天    
	 *     
	 * @return    
	 */     
	public static String getLastMonthLastDay() { 
		
    	Calendar cal = Calendar.getInstance();  
    	cal.setTime(cal.getTime());
    	cal.add(Calendar.MONTH, -1);          
    	
    	SimpleDateFormat daytime = new SimpleDateFormat("yyyyMMdd");
    	cal.set(Calendar.DAY_OF_MONTH, cal      
	            .getActualMaximum(Calendar.DAY_OF_MONTH));      
	    return daytime.format(cal.getTime());   
	}  
	/**    
	 * 得到指定月的最后一天    
	 *     
	 * @return    
	 */  
	public static String getMonthLastDay(String curmonth, int month) throws ParseException 
	{ 
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		Date   mydate=formatter.parse(curmonth);
    	Calendar cal = Calendar.getInstance();  
    	cal.setTime(mydate);
    	cal.add(Calendar.MONTH, month);          
    	
    	SimpleDateFormat daytime = new SimpleDateFormat("yyyyMMdd");
    	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));      
	    return daytime.format(cal.getTime());   
	}  
	
	public static String splitString(String str,int len,String elide) {    
	        if (str == null) {    
	               return "";    
	         }    
	        byte[] strByte = str.getBytes();    
	        int strLen = strByte.length;
	        //System.out.println(strLen);
	        int elideLen = (elide.trim().length() == 0) ? 0 : elide.getBytes().length;    
	        if (len >= strLen || len < 1) {    
	               return str;    
	         }    
	        if (len - elideLen > 0) {    
	                len = len - elideLen;    
	         }    
	        int count = 0;    
	        for (int i = 0; i < len; i++) {    
	               int value = (int) strByte[i];    
	               if (value < 0) {    
	                       count++;    
	                }    
	         }    
	        if (count % 2 != 0) {    
	                len = (len == 1) ? len + 1 : len - 1;    
	         }    
	        return new String(strByte, 0, len) + elide.trim();    
	  }
    public static String getNewLatn_id(String latn_id)
    {
    	String ret = latn_id;
		if(latn_id==null ||"".equals(latn_id)|| "0".equals(latn_id))
		{
			ret = "931";
		}
		return ret;
    }	
}
