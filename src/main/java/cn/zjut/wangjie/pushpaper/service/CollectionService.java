package cn.zjut.wangjie.pushpaper.service;

import cn.zjut.wangjie.pushpaper.pojo.Collection;

/**
 * @program: pushpaper
 * @description: 论文收藏
 * @author: WangJie
 * @create: 2018-05-10 17:16
 **/
public interface CollectionService {
    int addCollection(Collection collection);
    int deleteCollection(Collection collection);
    boolean isCollection(Collection collection);
}
