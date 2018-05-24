package cn.zjut.wangjie.pushpaper.controller;


import cn.zjut.wangjie.pushpaper.pojo.User;
import cn.zjut.wangjie.pushpaper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/userController")
public class UserController {
	@Resource private UserService userService;
	@Autowired
	private HttpServletRequest request;
	@RequestMapping("login.action")
	public String login(User user  ) {
		System.out.println(user.toString());
		User newUser= userService.login(user);
		if(newUser!=null) {
			request.getSession().setAttribute("user", newUser);
			return "main";
        }
		else {
			request.getSession().setAttribute("result", "账户名或密码错误");
			return "login";
		}
	}
	@RequestMapping("register.action")
	public String register(User user) {
		if(userService.register(user)) {
			request.getSession().setAttribute("user", user);
			return "main";
		}else{
			return "regist";
		}
	}
	
	@RequestMapping("checkEmail.action")
	public String checkEmail(String email,HttpServletRequest request) {
		if(userService.isEmalExist(email)) {
			return "exist";
		}else{
			return "notExist";
		}
	}
	@GetMapping("/logout")
	public String logout(){
		request.getSession().setAttribute("user",null);
		return "login";
	}
	
}
