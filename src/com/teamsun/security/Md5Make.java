/**
 * Copyright By 2009 TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.security;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CPNAIS
 * @author ht
 * @since 2009-3-31
 * @version 1.0 
 */
public class Md5Make {
	private static final Logger logger = LoggerFactory.getLogger(Md5Make.class);
	
	public static String crypt(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5"); //所用的加密算法
            md.update(str.getBytes());
            byte[] digestArr = md.digest(); //计算摘要,加密
            String aaa = byte2hex(digestArr);
            return aaa;
        }
        catch (Exception e) {
            return "error!";
        }
 
    }
	
	public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            }
            else {
                hs = hs + stmp;
                // if(n<b.length-1)
                // hs = hs + ":";
            }
        }
        return hs.toUpperCase();
    }
}
