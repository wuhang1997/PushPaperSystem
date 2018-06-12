package cn.zjut.wangjie.pushpaper.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageDTO {
	private Integer begin;
	private Integer currentPage;
	private Integer pageSize;
	private List contentList;
	private Integer totalPage;
	private String contentFlag;
	private String search;
	private Integer year;
	
	public void calculatTotalPage(int totalPaper) {
		totalPage = totalPaper/pageSize;
		if(totalPaper%pageSize>0) {
			totalPage++;
		}
	}
	public void calculatBegin() {
		begin=pageSize*(currentPage-1);
	}
	

	
}