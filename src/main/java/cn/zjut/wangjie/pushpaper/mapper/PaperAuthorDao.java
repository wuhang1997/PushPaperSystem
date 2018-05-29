package cn.zjut.wangjie.pushpaper.mapper;

import cn.zjut.wangjie.pushpaper.pojo.PaperAuthor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PaperAuthorDao {

    @Insert("insert into paper_author values (null , #{authorId}, #{paperId})")
    int addPaperAuthor(@Param("authorId")Integer authorId , @Param("paperId")Integer paperId);

    @Select("select * from paper_author where author_id = #{authorId} and paper_id = #{paperId} ")
    PaperAuthor getPaperAuthorByAuthorIdAndPaperId(@Param("authorId")Integer authorId , @Param("paperId")Integer paperId);
}
