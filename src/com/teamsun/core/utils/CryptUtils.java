/**
 * Copyright By 2008 ChinaPost_TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * cppims2
 * 
 * @author GuoJF
 * @since Dec 30, 2008
 * @version 2.0
 * @Component :Spring 注入 TODO:
 */
public class CryptUtils {
	private static final Logger logger = LoggerFactory.getLogger(CryptUtils.class);
	private MD5 md5;
	private DESPlus des;
	public MD5 getMd5() {
		return md5;
	}
	public void setMd5(MD5 md5) {
		this.md5 = new MD5();
	}
	public DESPlus getDes() {
		return des;
	}
	public void setDes(DESPlus des) {
		try {
			this.des = new DESPlus();
		} catch (Exception e) {
			logger.error("",e);
		}
	}
}
