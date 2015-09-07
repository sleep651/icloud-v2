package com.apps.mobile.domain;


/**
 * 返回单个对象信息
 * @Author: Jinpeng Li
 * @Time: 2013-5-17 下午2:17:29
 */
public class ResponseProperty<T extends Object> {

    private Short status;
    private String message;
    /**
     * 返回的单个对象
     */
    private T entity;

    public ResponseProperty() {
    }

    public ResponseProperty(Short status, String message) {
        this.status = status;
        this.message = message;
        this.entity = null;
    }

    public ResponseProperty(Short status, String message, T entity) {
        this.status = status;
        this.message = message;
        this.entity = entity;
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

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
