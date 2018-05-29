package cn.zjut.wangjie.pushpaper.service;



import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;

import java.util.List;

public interface PaperService {
	List<PaperInfo> getPaperList(PageDTO pageDto);
	List<String> getAllPaperArticle();
	int countPaperByWebsite(String website);
	PaperInfo getPaperInfoById(Integer id);

	List<PaperInfo> getAllPaper();
	void pushRecommendPaper();
	void pushNewPaperToAllUser();
}
