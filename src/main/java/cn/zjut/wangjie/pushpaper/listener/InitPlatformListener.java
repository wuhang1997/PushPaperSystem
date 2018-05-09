package cn.zjut.wangjie.pushpaper.listener;

import cn.zjut.wangjie.pushpaper.constant.PaperType;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @program: PushPaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-09 19:17
 **/
@Component
public class InitPlatformListener implements HttpSessionListener {
    @Autowired
    private PaperService paperService;
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int icmlNum = paperService.countPaperByWebsite(PaperType.ICML);
        se.getSession().setAttribute("ICMLNum",icmlNum);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
