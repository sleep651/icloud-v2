package com.apps.mobile.domain;

public class ResponseUploadRet {
    private Short status;
    private String message;
    private String url;
    
    public ResponseUploadRet() {
    }
    
    public ResponseUploadRet(Short status, String message) {
        this.status = status;
        this.message = message;
    }
    public ResponseUploadRet(Short status, String message, String url) {
    	this.status = status;
    	this.message = message;
    	this.url = url;
    }

    public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
