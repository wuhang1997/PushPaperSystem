package cn.zjut.wangjie.pushpaper.service.impl;


import cn.zjut.wangjie.pushpaper.mapper.PaperInfoDao;
import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
	private PaperInfoDao paperInfoDao;
	@Override
	public List<PaperInfo> getPaperList(PageDTO pageDTO) {
		
		return paperInfoDao.getPaperList(pageDTO);
	}
	@Override
	public int countPaperByWebsite(String website) {
		
		return paperInfoDao.countPaperByWebsite(website);
	}

	@Override
	public PaperInfo getPaperInfoById(Integer id) {
		return paperInfoDao.getPaperInfoById(id);
	}

	@Override
	public String turnPaperInfoToString(Integer id) {
		PaperInfo paperInfo = paperInfoDao.getPaperInfoById(id);
		StringBuilder paperSB = new StringBuilder();
		paperSB.append("Article:");
		paperSB.append(paperInfo.getArticle());
		paperSB.append("\n");
		paperSB.append("Authors:");
		paperSB.append(paperInfo.getAuthors());
		paperSB.append("\n");
		paperSB.append("From:");
		paperSB.append(paperInfo.getWebsite());
		paperSB.append("\n");
		paperSB.append("Abstract:");
		paperSB.append(paperInfo.getPaperAbstract());

		paperSB.append("http://localhost:8090/paperController/showPaperInfo?id="+paperInfo.getPaperId());

		return paperSB.toString();
	}


}
