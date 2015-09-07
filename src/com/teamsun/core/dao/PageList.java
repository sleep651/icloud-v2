/**
 * Copyright By 2009 TeamSun Co. Ltd.  
 * All right reserved. 
 */
package com.teamsun.core.dao;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GSCD
 * 页面分页方法(适用小数据量的分页)
 * @author ht
 * @since 2009-4-5
 * @version 1.0 
 */
public class PageList<T> {
	private static final Logger logger = LoggerFactory
			.getLogger(PageList.class);
	private int pageNo= 1; //当前页
	private int pageSize = 10;//每页记录数
	private int startRow;//开始记录数
	private int totalRows=-1;//总记录数 
	private int totalPages;//总页数 
	private List<T> resultList;//分页内容
	
	public PageList(List<T> resultList,int pageNo,int pageSize){
		this.pageSize = pageSize;
		this.resultList = resultList;
		this.totalRows = resultList.size();
		int v_endrownum   = pageNo * pageSize;
		this.startRow = v_endrownum - pageSize ;
		this.pageNo = pageNo;
		this.totalPages = (this.totalRows%this.pageSize==0)?(this.totalRows/this.pageSize):(this.totalRows/this.pageSize +1);
		logger.debug("当前页【"+pageNo+"】,每页记录数【"+pageSize+"】,开始记录数【"+startRow+"】,总记录数【"+totalRows+"】,总页数【"+totalPages+"】");
	}

	public List<T> getPageList(){
		int endRow = startRow+pageSize;
		if(endRow>totalRows)
			endRow = totalRows;
		logger.debug("返回数组中第【"+startRow +"】个到【"+ endRow+"】个元素");
		return  this.resultList.subList(startRow, endRow);
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public int getTotalPages() {
		return totalPages;
	}
	
}
