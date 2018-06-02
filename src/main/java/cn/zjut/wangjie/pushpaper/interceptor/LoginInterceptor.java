package cn.zjut.wangjie.pushpaper.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: pushpaper
 * @description: 对需要用户登录的操作进行拦截检测是否登录
 * @author: WangJie
 * @create: 2018-06-02 12:46
 **/
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getSession().getAttribute("user") == null){
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
