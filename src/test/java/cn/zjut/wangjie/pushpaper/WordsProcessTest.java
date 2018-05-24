package cn.zjut.wangjie.pushpaper;

import cn.zjut.wangjie.pushpaper.process.WordsProcess;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

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
    @Test
    public void stopWordsTest(){
        System.out.println(WordsProcess.StopWords().toString());
    }

    @Test
    public void writeToTextFileTest(){
        List<String> paperArticleList = paperService.getAllPaperArticle();
        WordsProcess.writeWordsDependency(paperArticleList);
    }
}
