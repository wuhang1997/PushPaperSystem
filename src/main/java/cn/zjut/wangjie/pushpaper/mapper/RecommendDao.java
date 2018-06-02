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
    @Select("select paperinfo.paper_id , article , authors ,website, add_at ,website from recommend inner join paperinfo on recommend.paper_id = paperinfo.paper_id where user_id = #{userId}")
    List<Recommend>listRecommendByUserId(Integer userId);
}
