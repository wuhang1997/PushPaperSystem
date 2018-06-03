package cn.zjut.wangjie.pushpaper.listener;

import cn.zjut.wangjie.pushpaper.constant.PaperType;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;
import java.util.Set;

/**
 * @program: PushPaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-09 19:17
 **/
@Component
public class InitPlatformListener implements ServletContextListener {
    @Autowired
    private PaperService paperService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        int icmlNum = paperService.countPaperByWebsite(PaperType.ICML);
        servletContextEvent.getServletContext().setAttribute("ICMLNum", icmlNum);

        List<PaperInfo> paperScoreList = paperService.getAllPaperScore();
        long time = System.currentTimeMillis();
        for (PaperInfo paperInfo : paperScoreList) {
            redisTemplate.opsForZSet().add("paperRank", paperInfo.getPaperId(),paperInfo.getScore());
            redisTemplate.opsForValue().set("rank"+paperInfo.getPaperId(),time);
        }
        Set<Integer> top10PaperIds = redisTemplate.opsForZSet().reverseRange("paperRank",0 , 9);

        List<PaperInfo> paperInfoList = paperService.getPaperByPaperIds(top10PaperIds);

        servletContextEvent.getServletContext().setAttribute("top10Paper",paperInfoList);


    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
