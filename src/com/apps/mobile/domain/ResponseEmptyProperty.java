package com.apps.mobile.domain;


/**
 * 仅返回错误代码和消息
 * @Author: Jinpeng Li
 * @Time: 2013-5-17 下午2:16:23
 */
public class ResponseEmptyProperty {

    private Short status;
    private String message;

    public ResponseEmptyProperty() {
    }

    public ResponseEmptyProperty(Short status, String message) {
        this.status = status;
        this.message = message;
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
}
