package cn.zjut.wangjie.pushpaper.service.impl;

import cn.zjut.wangjie.pushpaper.mapper.CollectionDao;
import cn.zjut.wangjie.pushpaper.pojo.Collection;
import cn.zjut.wangjie.pushpaper.service.CollectionService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-10 17:21
 **/
@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionDao collectionDao;
    @Override
    public int addCollection(Collection collection) {

        return collectionDao.addCollection(collection);
    }

    @Override
    public int deleteCollection(Collection collection) {
        return collectionDao.deleteCollection(collection);
    }

    @Override
    public boolean isCollection(Collection collection) {
        if (collectionDao.countCollectionByPaperIdAndUserId(collection)==1)
            return true;
        return false;
    }
}
