package cn.zjut.wangjie.pushpaper.service;

import cn.zjut.wangjie.pushpaper.pojo.Recommend;

import java.util.List;

public interface RecommendService {
    List<Recommend> listRecommendByUserId(Integer userId);
}
