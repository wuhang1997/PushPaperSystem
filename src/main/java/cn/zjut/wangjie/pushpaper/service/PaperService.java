package cn.zjut.wangjie.pushpaper.service;




import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;

import java.util.Collection;
import java.util.List;

public interface PaperService {
	List<PaperInfo> getPaperList(PageDTO pageDto);

	List<String> getAllPaperArticle();
	int countPaper(PageDTO pageDTO);
	PaperInfo getPaperInfoById(Integer id);

	PaperInfo getpaperInfoByPaperUrl(String paperUrl);

	List<PaperInfo> getAllPaper();
	void pushRecommendPaper();
	void pushNewPaperToAllUser();

	int addClick(Integer paperId);

	List<PaperInfo> getAllPaperScore();

	List<PaperInfo> getPaperByPaperIds(Collection paperIds);
	int updatePaperScore(Integer paperId , Double score);

	List<PaperInfo> listUnCompletePaper();

	boolean isPaperExist(String paperUrl);
	int updatePaperInfo(PaperInfo paperInfo);


}
