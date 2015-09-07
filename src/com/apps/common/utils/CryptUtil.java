package com.apps.common.utils;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import javax.crypto.spec.IvParameterSpec;

public class CryptUtil {
	private static final CryptUtil instance = new CryptUtil();
	private static final String key = "ICLOUD-2012-515P";
	private static final String KEYX = "ICLOUD-KEY201210";
	//private static final String KEYX = "some 16 byte key";
	
	private CryptUtil() {

	}

	public static CryptUtil getInstance() {
		return instance;
	}

	public static String getKey() {
		return key;
	}

	public static final byte[] IV = { 65, 1, 2, 23, 4, 5, 6, 7, 32, 21, 10, 11, 12, 13, 84, 45 };
	
	private Key initKeyForAES(String key) throws NoSuchAlgorithmException {
//		if (null == key || key.length() == 0) {
//			throw new NullPointerException("key not is null");
//		}
//		SecretKeySpec key2 = null;
//		try {
//			KeyGenerator kgen = KeyGenerator.getInstance("AES");
//			 kgen.init(128, new SecureRandom(key.getBytes()));
//			SecretKey secretKey = kgen.generateKey();
//			byte[] enCodeFormat = secretKey.getEncoded();
//			key2 = new SecretKeySpec(enCodeFormat, "AES");
//		} catch (NoSuchAlgorithmException ex) {
//			throw new NoSuchAlgorithmException();
//		}
//		return key2;
		
		String keyx = KEYX;
		byte[] keyBytes = null;
		try {
			keyBytes = keyx.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");
		return skeySpec;

	}

	/**
	 * 
	 * @param content
	 * @param key
	 * @return
	 */
	public String encryptAES(String content, String key) {
		try {
			SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);
//			Cipher cipher = Cipher.getInstance("AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// byte[] byteContent = content.getBytes("utf-8");
			byte[] byteContent = content.getBytes();
//			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(IV));
			byte[] result = cipher.doFinal(byteContent);
			return asHex(result); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param content
	 * @param key
	 * @return
	 */
	public String decryptAES(String content, String key) {
		try {
			SecretKeySpec secretKey = (SecretKeySpec) initKeyForAES(key);
//			Cipher cipher = Cipher.getInstance("AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			
//			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(IV));
			byte[] result = cipher.doFinal(asBytes(content));
			return new String(result); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param buf
	 * @return
	 */
	public String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;
		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}

	/**
	 * 
	 * @param hexStr
	 * @return
	 */
	public byte[] asBytes(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public void testCrypt() {
		CryptUtil crypt = CryptUtil.getInstance();
		String content = "89860111811001179447";
		System.out.println("simple platform: " + content);
		String dcontent = crypt.encryptAES(content, getKey());
		System.out.println("after encrypt: " + dcontent);
		System.out.println("after decrypt: " + crypt.decryptAES(dcontent, getKey()));
	}
	 public static void main(String[] args)  {  
	        CryptUtil crypt = CryptUtil.getInstance();  
	        //gscdm_client_001@teamsun1505
	        //30c65b5da6f90f803ca24475046755de87daa7611c0140cfc95c4f900e1d53d0
	        //a8597721371b13c0e2e06105fa68ee8a
	        String content = "01478";
	        System.out.println("原文:"+content);  
	        String dcontent = crypt.encryptAES(content, getKey());  
//	        String dcontent="8ec659781b3e81805bcaa72c5bca4762";
	        System.out.println("加密后:"+dcontent);  
	        System.out.println("解密后:"+crypt.decryptAES(dcontent, getKey()));  
	    }  	
}
