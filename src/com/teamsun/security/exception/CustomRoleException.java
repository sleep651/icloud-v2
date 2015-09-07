package com.teamsun.security.exception;

import com.teamsun.core.exception.BaseException;

public class CustomRoleException extends BaseException{
	public CustomRoleException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomRoleException(Exception sourceException) {
		super(sourceException);
		// TODO Auto-generated constructor stub
	}

	public CustomRoleException(String errorMessage, Exception sourceException) {
		super(errorMessage, sourceException);
		// TODO Auto-generated constructor stub
	}

	public CustomRoleException(String errorMessage) {
		super(errorMessage);
		// TODO Auto-generated constructor stub
	}
}
