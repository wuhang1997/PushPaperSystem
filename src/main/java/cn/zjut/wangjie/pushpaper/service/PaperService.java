package cn.zjut.wangjie.pushpaper.service;



import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;

import java.util.List;

public interface PaperService {
	List<PaperInfo> getPaperList(PageDTO pageDto);
	int countPaperByWebsite(String website);
	PaperInfo getPaperInfoById(Integer id);
}
