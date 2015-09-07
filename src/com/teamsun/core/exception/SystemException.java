package com.teamsun.core.exception;

/**
 * 系统异常
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Best-Star
 * </p>
 * 
 * @author Guojf
 * @version 1.0
 */

public class SystemException extends BaseException {

	private static final long serialVersionUID = 1L;

	public SystemException(String errorMessage) {
		super(errorMessage);
	}

	public SystemException(Exception sourceException) {
		super(sourceException);
	}

	public SystemException(String errorMessage, Exception sourceException) {
		super(errorMessage, sourceException);

	}
}
