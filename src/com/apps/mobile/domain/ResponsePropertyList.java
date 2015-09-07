package com.apps.mobile.domain;

import java.util.List;

/**
 * 返回集合类对象
 * @Author: Jinpeng Li
 * @Time: 2013-5-17 下午2:19:00
 */
public class ResponsePropertyList<T> {

    private Short status;
    private String message;
    /**
     * 返回的集合对象
     */
    private List<T> entityList;
    /**
     * 当前页码(可选)
     */
    private Integer pageNo;
    /**
     * 每页显示数量(可选)
     */
    private Integer pageSize;
    /**
     * 总条数(可选)
     */
    private Long totalCount;

    public ResponsePropertyList() {
    }

    public ResponsePropertyList(Short status, String message) {
        this.status = status;
        this.message = message;
        this.entityList = null;
    }

    public ResponsePropertyList(Short status, String message, List<T> entityList) {
        this.status = status;
        this.message = message;
        this.entityList = entityList;
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

    public List<T> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<T> entityList) {
        this.entityList = entityList;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
