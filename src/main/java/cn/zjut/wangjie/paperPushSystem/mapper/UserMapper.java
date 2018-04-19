package cn.zjut.wangjie.paperPushSystem.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.zjut.wangjie.paperPushSystem.pojo.User;

@Repository
public interface UserMapper {
	int addUser(User user);
	User findUserByEmailAndPassword(User user);
	User findUserByEmail(String email);
	//int updateUser(User user);
	//int deleteUser(User user);
}
