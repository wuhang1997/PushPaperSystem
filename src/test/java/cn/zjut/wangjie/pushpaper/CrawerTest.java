package cn.zjut.wangjie.pushpaper;

import cn.zjut.wangjie.pushpaper.processor.ICML2016PageProcessor;
import cn.zjut.wangjie.pushpaper.processor.ICML2017PageProcessor;
import cn.zjut.wangjie.pushpaper.processor.KDD2016PageProcessor;
import cn.zjut.wangjie.pushpaper.processor.KDD2017PageProcessor;
import cn.zjut.wangjie.pushpaper.util.ModifyFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private ICML2017PageProcessor icml2017PageProcessor;
    @Autowired
    private KDD2017PageProcessor kdd2017PageProcessor;
    @Autowired
    private ICML2016PageProcessor icml2016PageProcessor;
    @Autowired
    private KDD2016PageProcessor kdd2016PageProcessor;
    @Value("${paperPath}")
    private String path;
    @Test
    public void testICMLCrawer(){

        ModifyFileUtil.modifyFile(path+"urls/icml.cc.urls.txt","https://icml.cc/Conferences/2017/Schedule?type=Poster","");
        Spider.create(icml2017PageProcessor).addUrl("https://icml.cc/Conferences/2017/Schedule?type=Poster")
                .setScheduler(new FileCacheQueueScheduler(path+"urls"))
                .thread(10)
                .run();
    }
    @Test
    public void testPath(){
        log.info(        this.getClass().getClassLoader().getResource("").getPath());
    }

    @Test
    public void testKDDCrawer1(){
        Spider.create(kdd2017PageProcessor).addUrl("http://www.kdd.org/kdd2017/accepted-papers").thread(10).run();
    }
    @Test
    public void testICML2016(){
        ModifyFileUtil.modifyFile(path+"urls/ICML2016/proceedings.mlr.press.urls.txt","http://proceedings.mlr.press/v48/","");

        Spider.create(icml2016PageProcessor).addUrl("http://proceedings.mlr.press/v48/")
                .setScheduler(new FileCacheQueueScheduler(path+"urls/ICML2016"))
                .thread(10).run();
    }
    @Test
    public void getCurrentTime(){
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void testKDD2017Crawer(){
        ModifyFileUtil.modifyFile(path+"urls/KDD2017/www.kdd.org.urls.txt","http://www.kdd.org/kdd2017/accepted-papers","");
        Spider.create(kdd2017PageProcessor).addUrl("http://www.kdd.org/kdd2017/accepted-papers")
                .setScheduler(new FileCacheQueueScheduler(path+"urls/KDD2017"))
                .thread(10)
                .run();
    }

    @Test
    public void testKDD2016Crawer(){
        ModifyFileUtil.modifyFile(path+"urls/KDD2016/www.kdd.org.urls.txt","http://www.kdd.org/kdd2016/program/accepted-papers","");
        Spider.create(kdd2016PageProcessor).addUrl("http://www.kdd.org/kdd2016/program/accepted-papers")
                .setScheduler(new FileCacheQueueScheduler(path+"urls/KDD2016"))
                .thread(10)
                .run();
    }

}
