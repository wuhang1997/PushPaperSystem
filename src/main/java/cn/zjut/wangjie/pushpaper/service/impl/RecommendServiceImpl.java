package cn.zjut.wangjie.pushpaper.service.impl;

import cn.zjut.wangjie.pushpaper.mapper.RecommendDao;
import cn.zjut.wangjie.pushpaper.pojo.Recommend;
import cn.zjut.wangjie.pushpaper.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-06-02 21:48
 **/
@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    private RecommendDao recommendDao;
    @Override
    public List<Recommend> listRecommendByUserId(Integer userId) {
        return recommendDao.listRecommendByUserId(userId);
    }
}
