package cn.zjut.wangjie.pushpaper.mapper;

import cn.zjut.wangjie.pushpaper.pojo.PaperBrowseHistory;
import cn.zjut.wangjie.pushpaper.pojo.Recommend;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-24 16:47
 **/
@Mapper
public interface PaperBrowseHistoryDao {
    @Select("select paperinfo.paper_id , article , authors , add_at ,website from paper_browse_history inner join paperinfo on paper_browse_history.paper_id = paperinfo.paper_id where user_id = #{uid} order by add_at desc")
    List<PaperBrowseHistory> getPaperBrowserHistoryByUid(Integer uid);
    @Insert("insert into paper_browse_history values(null,#{paperId},#{userId},#{addAt})")
    int addPaperBrowserHistory(PaperBrowseHistory paperBrowseHistory);
}
