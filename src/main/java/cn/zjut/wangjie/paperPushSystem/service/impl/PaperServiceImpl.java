package cn.zjut.wangjie.paperPushSystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zjut.wangjie.paperPushSystem.mapper.PaperInfoMapper;
import cn.zjut.wangjie.paperPushSystem.pojo.PageDTO;
import cn.zjut.wangjie.paperPushSystem.pojo.PaperInfo;
import cn.zjut.wangjie.paperPushSystem.service.PaperService;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
	private PaperInfoMapper paperInfoMapper;
	@Override
	public List<PaperInfo> getPaperList(PageDTO pageDTO) {
		
		return paperInfoMapper.getPaperList(pageDTO);
	}
	@Override
	public int countPaperByWebsite(String website) {
		
		return paperInfoMapper.countPaperByWebsite(website);
	}

}
