package cn.zjut.wangjie.pushpaper.service;


import cn.zjut.wangjie.pushpaper.pojo.User;

public interface UserService {
	boolean register(User user);
	User login(User user);
	boolean isEmalExist(String email);

}
