package com.teamsun.core.exception;
public class BaseException extends RuntimeException implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Exception sourceException = null;

	public BaseException() {
		super();
	}

	public BaseException(String errorMessage, Exception sourceException) {
		super(errorMessage);
		if (sourceException != null) {
			this.sourceException = sourceException;
		}
	}

	public BaseException(String errorMessage) {
		super(errorMessage);
	}

	public BaseException(Exception sourceException) {
		this.sourceException = sourceException;
	}

	public Exception getSourceException() {
		return sourceException;
	}

	public void setSourceException(Exception sourceException) {
		this.sourceException = sourceException;
	}
}

