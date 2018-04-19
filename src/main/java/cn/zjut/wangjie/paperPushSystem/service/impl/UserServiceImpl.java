package cn.zjut.wangjie.paperPushSystem.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zjut.wangjie.paperPushSystem.mapper.UserMapper;
import cn.zjut.wangjie.paperPushSystem.pojo.User;
import cn.zjut.wangjie.paperPushSystem.service.UserService;
import cn.zjut.wangjie.paperPushSystem.util.MD5Util;

@Service
public class UserServiceImpl implements UserService {
	@Resource private UserMapper userMapper;
	 

	public boolean register (User user) {
		user.setPassword(MD5Util.md5(user.getPassword()));
		if(userMapper.addUser(user)==1) {
			return true;
		}
		else return false;
	}


	public User login(User user) {
		user.setPassword(MD5Util.md5(user.getPassword()));
		User newUser =userMapper.findUserByEmailAndPassword(user);
		if(newUser!=null){
			
			return newUser;
		}
		else return null;
	}
	

	public boolean isEmalExist(String email) {
		User user = userMapper.findUserByEmail(email);		
		return user==null?false:true;
	
	}



	

}
