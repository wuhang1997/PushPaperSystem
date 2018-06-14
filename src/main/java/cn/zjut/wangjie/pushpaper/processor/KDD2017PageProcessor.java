package cn.zjut.wangjie.pushpaper.processor;

import cn.zjut.wangjie.pushpaper.constant.PaperType;
import cn.zjut.wangjie.pushpaper.mapper.PaperInfoDao;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.service.PaperAuthorService;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import cn.zjut.wangjie.pushpaper.service.elasticsearch.ELPaperService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
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
 * @create: 2018-05-29 09:34
 **/
@Slf4j
@Component
public class KDD2017PageProcessor implements PageProcessor {
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
        if (page.getUrl().regex("/papers/view/").match()){
            String article = page.getHtml().xpath("//div[@class='entry-content notopmargin']/h3/text()").toString();

            String authors = page.getHtml().xpath("//div[@class='entry-content notopmargin']/p/strong/text()").toString();


            String paperAbstract = page.getHtml().xpath("//div[@class='entry-content notopmargin']/p/text()").all().get(1);

            String pdfUrl = page.getHtml().xpath("//div[@class='entry-content notopmargin']").links().all().get(0).toString();
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
                paperInfo.setHasPDF(true);
            }else{
                paperInfo.setHasPDF(false);
            }
            paperInfo.setClick(0);
            paperInfo.setScore(25.0);
            paperInfo.setYear(2017);
            paperInfo.setAddTime(System.currentTimeMillis());

            if (paperService.isPaperExist(paperInfo.getPaperUrl())){
                PaperInfo paperInfoOld = paperService.getpaperInfoByPaperUrl(paperInfo.getPaperUrl());
                if (paperInfo.isUpdate(paperInfoOld)) {
                    paperService.updatePaperInfo(paperInfo);
                    elPaperService.savePaperInfo(paperInfo);
                    paperAuthorService.addPaperAuthor(paperInfo);
                    redisTemplate.opsForList().rightPush("updatePaperToPush",paperInfoOld.getPaperId());
                    redisTemplate.expire("updatePaperToPush",10L,TimeUnit.MINUTES);

                }



            }else {
                paperInfoDao.addPaperInfo(paperInfo);
                elPaperService.savePaperInfo(paperInfo);
                paperAuthorService.addPaperAuthor(paperInfo);
                redisTemplate.opsForList().rightPush("newPaperToPush",paperInfo.getPaperId());
                redisTemplate.expire("newPaperToPush",10L,TimeUnit.MINUTES);

            }
            
        }
    }
    @Override
    public Site getSite() {
        return site;
    }

}
