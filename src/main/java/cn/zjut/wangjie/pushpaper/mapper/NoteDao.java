package cn.zjut.wangjie.pushpaper.mapper;

import cn.zjut.wangjie.pushpaper.pojo.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteDao {
    @Insert("insert into note values(null , #{paperId} , #{userId} ,#{article}, #{content} , #{addAt} , #{updateAt})")
    int addNote(Note note);

    @Delete("delete from note  where id = #{id}")
    int deleteNote (Integer id);

    @Select("select * from note where user_id = #{userId} and paper_id = #{paperId}")
    List<Note> listNoteByPaperIdAndUserId(Note note);

    @Select("select id ,note.paper_id , note.user_id , add_at , content , note.article ,paperinfo.article paperArticle , authors , website  from note inner join paperinfo on note.paper_id = paperinfo.paper_id where id = #{noteId}")
    Note getNoteById(Integer noteId);

    @Update("update note set article = #{article} , content = #{content} , update_at = #{updateAt} where user_id = #{userId} and paper_id = #{paperId}")
    int updateNote(Note note);

    @Select("select id ,note.paper_id , note.user_id , add_at , content , note.article ,paperinfo.article paperArticle, authors , website  from note inner join paperinfo on note.paper_id = paperinfo.paper_id where user_id = #{userId}")
    List<Note> listNotesByUserId(Integer userId);
}
