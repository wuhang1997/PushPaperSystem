package cn.zjut.wangjie.pushpaper.processor;

import cn.zjut.wangjie.pushpaper.constant.PaperType;
import cn.zjut.wangjie.pushpaper.mapper.PaperInfoDao;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.service.PaperAuthorService;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import cn.zjut.wangjie.pushpaper.service.elasticsearch.ELPaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-06-11 14:53
 **/
@Slf4j
@Component
public class KDD2016PageProcessor implements PageProcessor {
    @Autowired
    private ELPaperService elPaperService;
    @Autowired
    private PaperInfoDao paperInfoDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PaperAuthorService paperAuthorService;
    @Autowired
    private PaperService paperService;
    @Value("${paperPath}")
    private String filePath;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        List<String> paperUrls = page.getHtml().xpath("//table[@class='table table-hover table-striped table-bordered']/tbody/tr").links().all();
        if (paperUrls != null) {
            for (String strUrl : paperUrls) {
                page.addTargetRequest(strUrl);
            }
        }
        if (page.getUrl().regex("kdd2016/subtopic/view").match()){
           String article = page.getHtml().xpath("//div[@class='page-header']/h3/strong/text()").toString();
            System.out.println(article);
            String authors = page.getHtml().xpath("//div[@class='page-header']/p/strong/text()").toString();
            authors = authors.replace("*","");
            authors = authors.replaceAll(",","");
            System.out.println(authors);

            String paperAbstract = page.getHtml().xpath("//section[@class='bs-docs-section']/p/text()").all().get(0);
            System.out.println(paperAbstract);
            String pdfUrl = page.getHtml().xpath("//section[@class='bs-docs-section']/a").links().all().get(0);
            System.out.println(pdfUrl);
            PaperInfo paperInfo = new PaperInfo();
            paperInfo.setArticle(article);
            paperInfo.setAuthors(authors);
            paperInfo.setPaperAbstract(paperAbstract);
            paperInfo.setPdfUrl(pdfUrl);
            paperInfo.setWebsite(PaperType.KDD);
            paperInfo.setPaperUrl(page.getUrl().toString());
            if (article!= null && authors != null && paperAbstract!=null && pdfUrl!=null){
                paperInfo.setComplete(1);
            }else {
                paperInfo.setComplete(0);
            }
            if (pdfUrl!=null){
                paperInfo.setHasPDF(false);
            }else{
                paperInfo.setHasPDF(true);
            }
            paperInfo.setClick(0);
            paperInfo.setScore(1.0);
            paperInfo.setYear(2016);
            paperInfo.setAddTime(System.currentTimeMillis());
            System.out.println(paperInfo);
            if (paperService.isPaperExist(paperInfo.getPaperUrl())){
                paperService.updatePaperInfo(paperInfo);
                paperInfo = paperService.getpaperInfoByPaperUrl(paperInfo.getPaperUrl());

            }else {
                paperInfoDao.addPaperInfo(paperInfo);
            }
            elPaperService.savePaperInfo(paperInfo);
            paperAuthorService.addPaperAuthor(paperInfo);
            redisTemplate.opsForList().rightPush("newPaperToPush",paperInfo.getPaperId());
            redisTemplate.expire("newPaperToPush",10L,TimeUnit.MINUTES);
        }
    }
    @Override
    public Site getSite() {
        return site;
    }

}

