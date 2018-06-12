package cn.zjut.wangjie.pushpaper.scheduledTasks;

import cn.zjut.wangjie.pushpaper.constant.PaperType;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.processor.ICML2016PageProcessor;
import cn.zjut.wangjie.pushpaper.processor.ICML2017PageProcessor;
import cn.zjut.wangjie.pushpaper.processor.KDD2016PageProcessor;
import cn.zjut.wangjie.pushpaper.processor.KDD2017PageProcessor;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import cn.zjut.wangjie.pushpaper.util.ModifyFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;

import java.io.File;
import java.util.List;

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
    private ICML2017PageProcessor icml2017PageProcessor;
    @Autowired
    private KDD2017PageProcessor kdd2017PageProcessor;
    @Autowired
    private ICML2016PageProcessor icml2016PageProcessor;
    @Autowired
    private KDD2016PageProcessor kdd2016PageProcessor;
    @Autowired
    private PaperService paperService;
    @Value("${paperPath}")
    private String path;
    @Scheduled(cron = "0 27 10 * * ?")
    public void NewPaperCheck(){

        //ICML2017
        ModifyFileUtil.modifyFile(path+"urls/icml.cc.urls.txt","https://icml.cc/Conferences/2017/Schedule?type=Poster","");
        Spider.create(icml2017PageProcessor).addUrl("https://icml.cc/Conferences/2017/Schedule?type=Poster")
                .setScheduler(new FileCacheQueueScheduler(path+"urls"))
                .thread(10)
                .run();
        //ICML2016
        ModifyFileUtil.modifyFile(path+"urls/ICML2016/proceedings.mlr.press.urls.txt","http://proceedings.mlr.press/v48/","");
        Spider.create(icml2016PageProcessor).addUrl("http://proceedings.mlr.press/v48/")
                .setScheduler(new FileCacheQueueScheduler(path+"urls/ICML2016"))
                .thread(10).run();
        //KDD 2017
        ModifyFileUtil.modifyFile(path+"urls/KDD2017/www.kdd.org.urls.txt","http://www.kdd.org/kdd2017/accepted-papers","");
        Spider.create(kdd2017PageProcessor).addUrl("http://www.kdd.org/kdd2017/accepted-papers")
                .setScheduler(new FileCacheQueueScheduler(path+"urls/KDD2017"))
                .thread(10)
                .run();
        //KDD 2016
        ModifyFileUtil.modifyFile(path+"urls/KDD2016/www.kdd.org.urls.txt","http://www.kdd.org/kdd2016/program/accepted-papers","");
        Spider.create(kdd2016PageProcessor).addUrl("http://www.kdd.org/kdd2016/program/accepted-papers")
                .setScheduler(new FileCacheQueueScheduler(path+"urls/KDD2016"))
                .thread(10)
                .run();

        List<PaperInfo> paperInfoList = paperService.listUnCompletePaper();
        for (PaperInfo paperInfo : paperInfoList){
            if (paperInfo.getWebsite().equals(PaperType.ICML)){
                if (paperInfo.getYear()==2017){
                    Spider.create(icml2017PageProcessor).addUrl(paperInfo.getPaperUrl())
                            .run();
                }
                if (paperInfo.getYear()==2016){
                    Spider.create(icml2016PageProcessor).addUrl(paperInfo.getPaperUrl())
                            .run();
                }
            }
            if (paperInfo.getWebsite().equals(PaperType.KDD)){
                if (paperInfo.getYear()==2017){
                    Spider.create(kdd2017PageProcessor).addUrl(paperInfo.getPaperUrl())
                            .run();
                }
                if (paperInfo.getYear()==2016){
                    Spider.create(kdd2016PageProcessor).addUrl(paperInfo.getPaperUrl())
                            .run();
                }
            }
        }

    }
}
