package cn.zjut.wangjie.paperPushSystem.service;

import cn.zjut.wangjie.paperPushSystem.pojo.User;

public interface UserService { 
	boolean register(User user);
	User login(User user);
	boolean isEmalExist(String email);

}
