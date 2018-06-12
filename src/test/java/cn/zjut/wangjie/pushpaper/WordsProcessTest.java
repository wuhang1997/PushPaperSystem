package cn.zjut.wangjie.pushpaper;

import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.process.WordsProcess;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-24 13:15
 **/
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@Slf4j
public class WordsProcessTest {

    @Autowired
    private PaperService paperService;
    @Value("${paperPath}")
    private String path;
    @Test
    public void stopWordsTest(){
        System.out.println(WordsProcess.StopWords().toString());
    }

    @Test
    public void writeToTextFileTest(){

        List<PaperInfo> paperInfoList = paperService.getAllPaper();
        List<String>texts = new ArrayList<>(500);
        for (PaperInfo paperInfo : paperInfoList){
            texts.add(paperInfo.getArticle() + " " +paperInfo.getArticle());
        }
        WordsProcess.writeWordsDependency(texts,path);
    }
    @Test
    public void writeArticleToTextFileTest(){


        List<String >articles = paperService.getAllPaperArticle();
        WordsProcess.writeWordsDependency(articles,path);
    }
    @Test
    public  void paperRankTest(){
        Random random = new Random();

    }
}
