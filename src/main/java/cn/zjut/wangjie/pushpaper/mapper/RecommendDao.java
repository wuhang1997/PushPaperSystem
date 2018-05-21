package cn.zjut.wangjie.pushpaper.mapper;

import cn.zjut.wangjie.pushpaper.pojo.Recommend;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-21 11:08
 **/
@Mapper
public interface RecommendDao {
    @Select("select paper_id from recommend where user_id = #{uid}")
    List<Integer> getRecommendedPaperIdsByUid(Integer uid);
    @Insert("insert into recommend values(null,#{paperId},#{userId},#{addAt})")
    int addRecommend(Recommend recommend);
}
