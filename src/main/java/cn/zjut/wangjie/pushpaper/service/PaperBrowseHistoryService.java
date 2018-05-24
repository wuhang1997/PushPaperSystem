package cn.zjut.wangjie.pushpaper.service;

import cn.zjut.wangjie.pushpaper.pojo.PaperBrowseHistory;

import java.util.List;

public interface PaperBrowseHistoryService {

    int add(PaperBrowseHistory paperBrowseHistory);

    List<PaperBrowseHistory> getPaperBrowserHistoryByUid(Integer uid);
}
