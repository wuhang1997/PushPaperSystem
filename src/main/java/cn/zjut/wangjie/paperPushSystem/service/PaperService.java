package cn.zjut.wangjie.paperPushSystem.service;

import java.util.List;

import cn.zjut.wangjie.paperPushSystem.pojo.PageDTO;
import cn.zjut.wangjie.paperPushSystem.pojo.PaperInfo;

public interface PaperService {
	List<PaperInfo> getPaperList(PageDTO pageDto);
	int countPaperByWebsite(String website);
}
