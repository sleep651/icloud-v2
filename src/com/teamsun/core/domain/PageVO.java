package com.teamsun.core.domain;

import java.util.List;

public class PageVO {


	private int pageNo; //当前页
	private int pageSize ;//每页记录数
	private int startRow;//开始记录数
	private int totalRows;//总记录数 
	private int totalPages;//总页数 
	private List resultList;
	

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public PageVO() {
	}
	
	public PageVO(int _totalRows) {
		totalRows = _totalRows;
		totalPages=totalRows/pageSize;
		int mod=totalRows%pageSize;
		if(mod>0){
			totalPages++;
		}
		pageNo = 1;
		startRow = 0;
	}
	
	public int getStartRow() {
		return startRow;
	}
	public int getTotalPages() {
		return totalPages;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void first() {
		pageNo = 1;
		startRow = 0;
	}
	public void previous() {
		if (pageNo == 1) {
			return;
		}
		pageNo--;
		startRow = (pageNo - 1) * pageSize;
	}
	public void next() {
		if (pageNo < totalPages) {
			pageNo++;
		}
		startRow = (pageNo - 1) * pageSize;
	}
	public void last() {
		pageNo = totalPages;
		startRow = (pageNo - 1) * pageSize;
	}
	public void refresh(int _currentPage) {
		pageNo = _currentPage;
		if (pageNo > totalPages) {
			last();
		}
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}

