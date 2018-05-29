package cn.zjut.wangjie.pushpaper;

import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.service.PaperAuthorService;
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
 * @create: 2018-05-29 13:44
 **/
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@Slf4j
public class PaperAuthorTest {
    @Autowired
    private PaperAuthorService paperAuthorService;
    @Autowired
    private PaperService paperService;
    @Test
    public void addPaperAuthorTest(){
        List<PaperInfo> paperInfoList = paperService.getAllPaper();
        for(PaperInfo paperInfo : paperInfoList){
            System.out.println(paperInfo.getPaperId());
            paperAuthorService.addPaperAuthor(paperInfo);
        }

    }
    @Test
    public void strReplaceTest(){
        String str = " Lerrel Pinto, James Davidson, Rahul Sukthankar, Abhinav Gupta ; ";
        str.trim();
        str = str.replace(";",",");
        System.out.println(str);
    }
}
