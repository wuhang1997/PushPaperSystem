package cn.zjut.wangjie.pushpaper.scheduledTasks;

import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.math.BigDecimal;
import java.util.*;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-06-03 21:51
 **/
@Component
@Slf4j
public class PaperRankTask {


    private ServletContext servletContext;

    private RedisTemplate redisTemplate;

    private PaperService paperService;

    @Autowired
    public PaperRankTask(ServletContext servletContext,RedisTemplate redisTemplate , PaperService paperService){
        this.servletContext = servletContext;
        this.redisTemplate = redisTemplate;
        this.paperService = paperService;
    }
    @Scheduled(cron = "0 * * * * ?")
    public void refreshTop10Papers(){
        Set<Integer> top10PaperIds = redisTemplate.opsForZSet().reverseRange("paperRank",0 , 9);

        List<PaperInfo> paperInfoList = new ArrayList<>(10);
        PaperInfo paperInfo;
        for (Integer paperId: top10PaperIds){
            paperInfo = paperService.getPaperInfoById(paperId);
            paperInfoList.add(paperInfo);
            System.out.println(paperId);
        }

        servletContext.setAttribute("top10Paper",paperInfoList);
        System.out.println("top10刷新了");
    }

    @Scheduled(cron = "0 0/10 * * * ?")
    public void updatePaperRank(){
        Set<Integer> paperIds = redisTemplate.opsForZSet().reverseRange("paperRank",0 , -1);
        Long nowTime = System.currentTimeMillis();
        Cursor<ZSetOperations.TypedTuple<Integer>> cursor = redisTemplate.opsForZSet().scan("paperRank", ScanOptions.NONE);
        Long oldTime;
        double newScore;
        Set<ZSetOperations.TypedTuple<Integer>>  papersRank = redisTemplate.opsForZSet().reverseRangeWithScores("paperRank", 0, -1);
        for(ZSetOperations.TypedTuple<Integer> item:papersRank){
            oldTime = (long)redisTemplate.opsForValue().get("rank"+item.getValue());
            newScore = (item.getScore()+0.0001)*Math.exp(-0.1*(Math.abs((nowTime-oldTime)/1000)));
            redisTemplate.opsForZSet().add("paperRank", item.getValue(),newScore);
            redisTemplate.opsForValue().set("rank"+item.getValue(),nowTime);
            paperService.updatePaperScore(item.getValue(),newScore);
        }

    }
}
