/**
 * 
 */
package com.limon.base.common;

import java.util.List;

/**
 * @author gqf
 *
 * 2015-2-13 下午03:23:36
 */
public class PageModel {
	//当前页
	private Integer currentPage=1;
	//总页数
	private Integer totalPage;
	//总记录数
	private Integer totalRecord=0;
	//每页显示数量
	private Integer pageSize=12;
	//当前页起始量
	private Integer pagestart=0;
	//当前页结束量
	private Integer pageend=0;
	//结果集 
    private List<?> list;
	public Integer getCurrentPage() {
		if (currentPage <= 0) {  
            currentPage = 1;  
		}  
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getTotalPage() {
		totalPage= (totalRecord + pageSize - 1) / pageSize;
		totalPage= totalPage<=0 ? 1:totalPage;
		if(totalPage==null){
			totalPage=0;
		}
		return totalPage; 
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public Integer getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Integer totalRecord) {
		if(totalRecord==null){
			totalRecord=0;
		}
		this.totalRecord = totalRecord;
	}
	public Integer getPagestart() {
		pagestart=(this.getCurrentPage()-1)*this.getPageSize();
		return pagestart;
	}
	public void setPagestart(Integer pagestart) {
		this.pagestart = pagestart;
	}
	public Integer getPageend() {
		pageend=this.getCurrentPage()*this.getPageSize();
		return pageend;
	}
	public void setPageend(Integer pageend) {
		this.pageend = pageend;
	}
}
