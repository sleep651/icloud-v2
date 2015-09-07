package com.teamsun.core;

import java.util.HashMap;
import java.util.Map;


public class Constants {
	public static final String WEIHU_USER="CIM8888888";//系统维护员，有系统启停功能
	public static final Long  WEIHU_MENUID=5L;//启停时的维护菜单
	public static final Long WEIHU_STAT_USE=0L;//系统使用状态
	public static final Long WEIHU_STAT_STOP=1L;//系统停止状态
	
	public static Map<String, String> CNSTMAP = new HashMap();
	static{
		CNSTMAP.put("T98_NO_ONLINE_BIZ_MAN","非联网网点经营手工数据表");
		CNSTMAP.put("T98_POSTSAV_ASSET_MAN","邮储资产手工数据表");
		CNSTMAP.put("T98_SAVPOINT_TYPE_MAN","邮储网点类别统计手工数据表");
	}
}

