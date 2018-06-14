package cn.zjut.wangjie.pushpaper;

import cn.zjut.wangjie.pushpaper.constant.PaperType;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.processor.ICML2016PageProcessor;
import cn.zjut.wangjie.pushpaper.processor.ICML2017PageProcessor;
import cn.zjut.wangjie.pushpaper.processor.KDD2016PageProcessor;
import cn.zjut.wangjie.pushpaper.processor.KDD2017PageProcessor;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import us.codecraft.webmagic.Spider;

import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-24 18:00
 **/
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@Slf4j
public class PaperPushTest {
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

    @Test
    public void testPushNewPaper() {

        paperService.pushNewPaperToAllUser();
    }
    @Test
    public void testRecommendPaper(){
        paperService.pushRecommendPaper();
    }

    @Test
    public void testUpdatePaper(){

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
                            .thread(1)
                            .run();
                }
            }
        }
    }


}
