package cn.zjut.wangjie.pushpaper.scheduledTasks;

import cn.zjut.wangjie.pushpaper.pojo.User;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import cn.zjut.wangjie.pushpaper.service.elasticsearch.ELPaperService;
import cn.zjut.wangjie.pushpaper.util.SendEmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: bonus-point-cloud
 * @description: 定时检测过期积分
 * @author: WangJie
 * @create: 2018-05-10 09:54
 **/
@Component
@Slf4j
public class PushPaperToEmailTask {
    @Autowired
    private PaperService paperService;
    @Autowired
    private ELPaperService elPaperService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 定时推送新paper
     *
     */
    @Scheduled(cron = "0 13 14 * * ?")
    public void pushNewPaperToAllUser(){

        paperService.pushNewPaperToAllUser();

    }

    @Scheduled(cron = "0 13 14 * * ?")

    public void pushUpdatePaperToAllUser(){
        paperService.pushUpdatePaperToAllUser();
    }

    @Scheduled(cron = "0 7 14 * * ?")
    public void pushRecommendPaper(){


        paperService.pushRecommendPaper();

    }



}
