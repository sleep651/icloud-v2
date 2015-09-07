/**
 * Copyright By 2008 ChinaPost_TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.security.exception;

import com.teamsun.core.exception.BaseException;

/**
 * CPCIM
 * @author luoning
 * @since 2008-6-27
 * @version 2.0 
 * TODO:初始密码重置模块的异常类
 */
public class PwdResetException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PwdResetException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PwdResetException(Exception sourceException) {
		super(sourceException);
		// TODO Auto-generated constructor stub
	}

	public PwdResetException(String errorMessage, Exception sourceException) {
		super(errorMessage, sourceException);
		// TODO Auto-generated constructor stub
	}

	public PwdResetException(String errorMessage) {
		super(errorMessage);
		// TODO Auto-generated constructor stub
	}
	
}
