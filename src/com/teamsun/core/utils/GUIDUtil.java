package com.teamsun.core.utils;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GUIDUtil {

	private static final Logger logger = LoggerFactory.getLogger(GUIDUtil.class);

	private static Random rand;

	private static SecureRandom secureRand;

	private static String localhost;

	static {
		secureRand = new SecureRandom();
		rand = new Random(secureRand.nextLong());
		try {
			localhost = InetAddress.getLocalHost().toString();
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.toString());
			}
			localhost = "localhost";
		}

	}

	public static String buildMd5GUID(boolean secure) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] digest = messageDigest.digest(buildRawGUID(secure)
					.getBytes());
			return new String(Hex.encodeHex(digest));
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.toString());
			}
		}
		return null;
	}

	public static String buildSimpleGUID(boolean secure) {
		return new String(Hex.encodeHex(buildRawGUID(secure).getBytes()));
	}

	public static String buildRawGUID(boolean secure) {
		StringBuffer sb = new StringBuffer();
		sb.append(localhost);
		sb.append(":");
		sb.append(System.currentTimeMillis());
		sb.append(":");
		sb.append(random(secure));
		return sb.toString();
	}
	public static String buildTimeGUID(boolean secure) {
		StringBuffer sb = new StringBuffer();

		sb.append(System.currentTimeMillis());

		return sb.toString();
	}
	public static long random(boolean secure) {
		if (secure) {
			return secureRand.nextLong();
		}
		return rand.nextLong();
	}
   /*public static void main(String[] args) {
	 logger.debug( buildMd5GUID(false)); 
	 logger.debug( buildSimpleGUID(true)); 
	 logger.debug( buildRawGUID(true)); 
	 logger.debug(buildTimeGUID(true));

	 logger.debug(String.valueOf(random(true)));
	 
	 logger.debug(DateUtil.genTimeStampString(new Date()));
	}*/
}
