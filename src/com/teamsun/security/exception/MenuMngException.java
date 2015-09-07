package com.teamsun.security.exception;

import com.teamsun.core.exception.BaseException;

public class MenuMngException extends BaseException{
	private static final long serialVersionUID = 8799575167533828290L;

	public MenuMngException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MenuMngException(Exception sourceException) {
		super(sourceException);
		// TODO Auto-generated constructor stub
	}

	public MenuMngException(String errorMessage, Exception sourceException) {
		super(errorMessage, sourceException);
		// TODO Auto-generated constructor stub
	}

	public MenuMngException(String errorMessage) {
		super(errorMessage);
		// TODO Auto-generated constructor stub
	}
	
}
