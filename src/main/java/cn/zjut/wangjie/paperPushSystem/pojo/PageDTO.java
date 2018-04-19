package cn.zjut.wangjie.paperPushSystem.pojo;

import java.util.List;

public class PageDTO {
	private Integer begin;
	private Integer currentPage;
	private Integer pageSize;
	private List contentList;
	private Integer totalPage;
	private String contentFlag;
	
	public void calculatTotalPage(int totalPaper) {
		totalPage = totalPaper/pageSize;
		if(totalPaper%pageSize>0) {
			totalPage++;
		}
	}
	public void calculatBegin() {
		begin=pageSize*(currentPage-1);
	}
	
	public Integer getBegin() {
		return begin;
	}
	public void setBegin(Integer begin) {
		this.begin = begin;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List getContentList() {
		return contentList;
	}
	public void setContentList(List contentList) {
		this.contentList = contentList;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public String getContentFlag() {
		return contentFlag;
	}
	public void setContentFlag(String contentFlag) {
		this.contentFlag = contentFlag;
	}
	@Override
	public String toString() {
		return "PageDTO [begin=" + begin + ", currentPage=" + currentPage + ", pageSize=" + pageSize + ", contentList="
				+ contentList + ", totalPage=" + totalPage + ", contentFlag=" + contentFlag + "]";
	}
	
}