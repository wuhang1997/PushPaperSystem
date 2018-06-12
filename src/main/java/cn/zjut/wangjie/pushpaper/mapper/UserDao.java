package cn.zjut.wangjie.pushpaper.mapper;

import cn.zjut.wangjie.pushpaper.pojo.User;
import cn.zjut.wangjie.pushpaper.sql.UserSQLFactory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface UserDao {

	@InsertProvider(type=UserSQLFactory.class , method = "addUserSQL")
	@Options(keyColumn = "uid", keyProperty = "uid", useGeneratedKeys = true)
	int addUser(User user);

	@SelectProvider(type = UserSQLFactory.class , method = "findUserSQL")
	User findUser(User user);

	@Select("select count(*) from user where email = #{email}")
	int isEmailExit(String email);
	@Select("select * from user ")
	List<User> getAllUser();

	@Update("update user set name = #{name} ,email =#{email} , preferences = #{preferences} where uid = #{uid}")
	int updateUser(User user);
}
