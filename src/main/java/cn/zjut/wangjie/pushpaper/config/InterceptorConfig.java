package cn.zjut.wangjie.pushpaper.config;

import cn.zjut.wangjie.pushpaper.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: bonus-point-cloud
 * @description:
 * @author: WangJie
 * @create: 2018-05-08 11:28
 **/
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {



    private final LoginInterceptor loginInterceptor;
    @Autowired
    public InterceptorConfig(LoginInterceptor loginInterceptor ){
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/comment/add")
                .addPathPatterns("/paper-browse-history")
                .addPathPatterns("/paper-collection");

        super.addInterceptors(registry);
    }
}
