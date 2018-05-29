package cn.zjut.wangjie.pushpaper.mapper;


import cn.zjut.wangjie.pushpaper.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CommentDao {
    @Insert("insert into comment values(null , #{paperId} , #{userId} , #{content} , #{addAt} ")
    int addNote(Comment comment);

    @Select("select * from comment where  paper_id = #{paperId}")
    List<Comment> listCommentByPaperId(Integer paperId);

    @Select("select * from comment where user_id = #{userId} and paper_id = #{paperId}")
    List<Comment> listCommentByPaperIdAndUserId(@Param("userId") Integer userId , @Param("paperId") Integer paperId);


}
