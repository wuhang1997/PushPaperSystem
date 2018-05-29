package cn.zjut.wangjie.pushpaper;

import cn.zjut.wangjie.pushpaper.processor.ICMLPageProcessor;
import cn.zjut.wangjie.pushpaper.processor.KDDPageProcessor;
import cn.zjut.wangjie.pushpaper.util.ModifyFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

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
    private ICMLPageProcessor icmlPageProcessor;
    @Autowired
    private KDDPageProcessor kddPageProcessor;
    @Test
    public void testICMLCrawer(){

        ModifyFileUtil.modifyFile("D:/logs/paperPush/urls/icml.cc.urls.txt","https://icml.cc/Conferences/2017/Schedule?type=Poster","");
        Spider.create(icmlPageProcessor).addUrl("https://icml.cc/Conferences/2017/Schedule?type=Poster")
                .setScheduler(new FileCacheQueueScheduler("D:/logs/paperPush/urls"))
                .thread(10)
                .run();
    }
    @Test
    public void testPath(){
        log.info(        this.getClass().getClassLoader().getResource("").getPath());
    }

    @Test
    public void testKDDCrawer(){
        Spider.create(kddPageProcessor).addUrl("http://www.kdd.org/kdd2017/accepted-papers").thread(10).run();
    }
}
