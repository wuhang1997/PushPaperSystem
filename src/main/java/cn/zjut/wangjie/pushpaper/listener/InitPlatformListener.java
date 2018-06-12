package cn.zjut.wangjie.pushpaper.listener;

import cn.zjut.wangjie.pushpaper.constant.PaperType;
import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
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


        PageDTO pageDTO = new PageDTO();
        pageDTO.setYear(2017);
        pageDTO.setContentFlag(PaperType.ICML);
        int icml2017Num = paperService.countPaper(pageDTO);
        servletContextEvent.getServletContext().setAttribute("ICML2017Num", icml2017Num);

        pageDTO.setContentFlag(PaperType.KDD);
        int kdd2017Num = paperService.countPaper(pageDTO);
        servletContextEvent.getServletContext().setAttribute("KDD2017Num", kdd2017Num);

        pageDTO.setYear(2016);
        int kdd2016Num = paperService.countPaper(pageDTO);
        servletContextEvent.getServletContext().setAttribute("KDD2016Num", kdd2016Num);

        pageDTO.setContentFlag(PaperType.ICML);
        int icml2016Num = paperService.countPaper(pageDTO);
        servletContextEvent.getServletContext().setAttribute("ICML2016Num", icml2016Num);


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
