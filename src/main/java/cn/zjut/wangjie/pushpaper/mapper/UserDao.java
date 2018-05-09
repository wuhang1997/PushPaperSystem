package cn.zjut.wangjie.pushpaper.mapper;

import cn.zjut.wangjie.pushpaper.pojo.User;
import cn.zjut.wangjie.pushpaper.sql.UserSQLFactory;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserDao {

	@InsertProvider(type=UserSQLFactory.class , method = "addUserSQL")
	@Options(keyColumn = "uid", keyProperty = "uid", useGeneratedKeys = true)
	int addUser(User user);

	@SelectProvider(type = UserSQLFactory.class , method = "findUserSQL")
	User findUser(User user);

}
