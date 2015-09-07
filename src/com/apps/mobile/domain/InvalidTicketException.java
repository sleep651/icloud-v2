package com.apps.mobile.domain;

public class InvalidTicketException extends Exception {

	private static final long serialVersionUID = -2765510042172906814L;

	public InvalidTicketException() {
    }

    public InvalidTicketException(String errorCode) {
        super(errorCode);
    }

    public InvalidTicketException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
