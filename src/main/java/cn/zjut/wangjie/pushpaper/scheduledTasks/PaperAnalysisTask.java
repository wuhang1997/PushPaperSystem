package cn.zjut.wangjie.pushpaper.scheduledTasks;

import cn.zjut.wangjie.pushpaper.process.WordsProcess;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-24 13:41
 **/
@Component
@Slf4j
public class PaperAnalysisTask {

    @Autowired
    private PaperService paperService;
    @Scheduled(cron = "0 29 9 * * ?")
    public void paperArticleAnalysisTask(){
        List<String> paperArticleList = paperService.getAllPaperArticle();
        WordsProcess.writeWordsDependency(paperArticleList);
    }
}
