package com.rest.agent.dao.entity;

public class JQPage {
	 /**
     * 页码
     */
    private int pageNum;
    /**
     * 页面大小	
     */
    private int pageSize;
    /**
     * 排序
     */
    private String order;
    
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
