package cn.zjut.wangjie.pushpaper.mapper;

import cn.zjut.wangjie.pushpaper.pojo.Collection;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-10 17:22
 **/
@Mapper
public interface CollectionDao {
    @Insert("insert into collection values(null,#{paperId},#{userId},#{addAt})")
    int addCollection (Collection collection);
    @Delete("delete from collection where paper_id=#{paperId} and user_id=#{userId}")
    int deleteCollection(Collection collection);
    @Select("select count(*) from collection where paper_id=#{paperId} and user_id=#{userId}")
    int countCollectionByPaperIdAndUserId(Collection collection);
}
