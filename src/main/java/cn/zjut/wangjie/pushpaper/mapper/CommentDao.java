package cn.zjut.wangjie.pushpaper.mapper;


import cn.zjut.wangjie.pushpaper.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CommentDao {
    @Insert("insert into comment values(null , #{paperId} , #{userId} , #{content} , #{addAt} )")
    int addComment(Comment comment);

    @Select("select name , content , comment.add_at from comment inner join user on user_id = uid where  paper_id = #{paperId} order by add_at desc")
    List<Comment> listCommentByPaperId(Integer paperId);

    @Select("select comment.paper_id , article , authors ,content , website from comment inner join paperinfo on comment.paper_id = paperinfo.paper_id where user_id = #{userId} order by add_at desc ")
    List<Comment> listCommentByUserId(@Param("userId") Integer userId );


}
