package cn.zjut.wangjie.pushpaper;

import cn.zjut.wangjie.pushpaper.processor.ICMLCrawer;
import cn.zjut.wangjie.pushpaper.util.ModifyFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.PriorityScheduler;

/**
 * @program: pushpaper
 * @description: 爬虫测试
 * @author: WangJie
 * @create: 2018-05-18 17:07
 **/

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@Slf4j
public class CrawerTest {
    @Autowired
    private ICMLCrawer icmlCrawer;
    @Test
    public void testICMLCrawer(){

        ModifyFileUtil.modifyFile("D:/logs/paperPush/urls/icml.cc.urls.txt","https://icml.cc/Conferences/2017/Schedule?type=Poster","");
        Spider.create(icmlCrawer).addUrl("https://icml.cc/Conferences/2017/Schedule?type=Poster")
                .setScheduler(new FileCacheQueueScheduler("D:/logs/paperPush/urls"))
                .thread(10)
                .run();
    }
    @Test
    public void testPath(){
        log.info(        this.getClass().getClassLoader().getResource("").getPath());
    }
}
