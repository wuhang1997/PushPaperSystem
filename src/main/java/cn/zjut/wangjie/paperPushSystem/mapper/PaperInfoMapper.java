package cn.zjut.wangjie.paperPushSystem.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.zjut.wangjie.paperPushSystem.pojo.PageDTO;
import cn.zjut.wangjie.paperPushSystem.pojo.PaperInfo;



@Repository 
public interface PaperInfoMapper {
	
	int addPaperInfo(PaperInfo paper);
	List<PaperInfo> getPaperList(PageDTO pageDto);
	int countPaperByWebsite(String website);
}
