package cn.zjut.wangjie.pushpaper.scheduledTasks;

import cn.zjut.wangjie.pushpaper.processor.ICMLPageProcessor;
import cn.zjut.wangjie.pushpaper.util.ModifyFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

import java.io.File;

/**
 * @program: pushpaper
 * @description: 检查是否有新的ICML论文，有的话爬取下来
 * @author: WangJie
 * @create: 2018-05-19 14:31
 **/
@Component
@Slf4j
public class NewPaperCheckTask {
    @Autowired
    private ICMLPageProcessor icmlPageProcessor;
    @Scheduled(cron = "0 56 18 * * ?")
    public void NewICMLPaperCheck(){
        String filePath ="D:/logs/paperPush/urls/icml.cc.urls.txt";
        File file = new File(filePath);
        if (file.exists()){
            ModifyFileUtil.modifyFile("D:/logs/paperPush/urls/icml.cc.urls.txt","https://icml.cc/Conferences/2017/Schedule?type=Poster","");
        }
        Spider.create(icmlPageProcessor).addUrl("https://icml.cc/Conferences/2017/Schedule?type=Poster")
                .setScheduler(new FileCacheQueueScheduler("D:/logs/paperPush/urls"))
                .thread(10)
                .run();

    }
}
