package cn.zjut.wangjie.pushpaper.mapper;

import cn.zjut.wangjie.pushpaper.pojo.Note;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface NoteDao {
    @Insert("insert into note values(null , #{paperId} , #{userId} , #{content} , #{addAt} , #{updateAt}")
    int addNote(Note note);

    @Update("update note set content = #{content} , update_at = #{updateAt} where userId = #{userId} and paper_id = #{paperId}")
    int updateNote(Note note);

    @Delete("delete note  where userId = #{userId} and paper_id = #{paperId}")
    int deleteNote (Note note);
}
