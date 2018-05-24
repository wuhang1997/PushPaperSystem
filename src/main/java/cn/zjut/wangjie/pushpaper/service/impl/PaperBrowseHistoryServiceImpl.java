package cn.zjut.wangjie.pushpaper.service.impl;

import cn.zjut.wangjie.pushpaper.mapper.PaperBrowseHistoryDao;
import cn.zjut.wangjie.pushpaper.pojo.PaperBrowseHistory;
import cn.zjut.wangjie.pushpaper.service.PaperBrowseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-24 17:01
 **/
@Service
public class PaperBrowseHistoryServiceImpl implements PaperBrowseHistoryService {
    @Autowired
    private PaperBrowseHistoryDao paperBrowseHistoryDao;
    @Override
    public int add(PaperBrowseHistory paperBrowseHistory) {
        return paperBrowseHistoryDao.addPaperBrowserHistory(paperBrowseHistory);
    }

    @Override
    public List<PaperBrowseHistory> getPaperBrowserHistoryByUid(Integer uid) {
        return paperBrowseHistoryDao.getPaperBrowserHistoryByUid(uid);
    }
}
