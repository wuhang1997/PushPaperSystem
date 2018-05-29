package cn.zjut.wangjie.pushpaper.mapper;

import cn.zjut.wangjie.pushpaper.pojo.Author;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AuthorDao {

    @Insert("insert into author values(null , #{name})")
    @Options(keyColumn = "author_id" , keyProperty = "authorId" , useGeneratedKeys = true)
    int addAuthor(Author author);

    @Select("select * from author where name = #{name} ")
    Author getAuthorByName(String name);
}
