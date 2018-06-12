package cn.zjut.wangjie.pushpaper.processor;

import cn.zjut.wangjie.pushpaper.constant.PaperType;
import cn.zjut.wangjie.pushpaper.mapper.PaperInfoDao;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.service.PaperAuthorService;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import cn.zjut.wangjie.pushpaper.service.elasticsearch.ELPaperService;
import cn.zjut.wangjie.pushpaper.util.DownloadFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-06-11 13:49
 **/
@Component
@Slf4j
public class ICML2016PageProcessor implements PageProcessor {

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
        // TODO Auto-generated method stub

        List<String> urls = page.getHtml().xpath("//p[@class='links']").links().all();

        if (urls != null) {
            for (String strUrl : urls) {
                page.addTargetRequest(strUrl);
            }
        }


        if (page.getUrl().regex("html").match()) {


            PaperInfo paperInfo = new PaperInfo();
            paperInfo.setWebsite(PaperType.ICML);
            paperInfo.setArticle(page.getHtml().xpath("//article[@class='post-content']/h1/text()").toString());
            paperInfo.setPaperUrl(page.getUrl().toString());
            paperInfo.setAuthors(page.getHtml().xpath("//div[@class='authors']/text()").toString());
            paperInfo.setPaperAbstract(page.getHtml().xpath("//div[@class='abstract']/text()").toString());
            List<String> pdfUrls = page.getHtml().xpath("//div[@id='extras']").links().all();
            boolean downloadSuccess = true ;
            if (pdfUrls == null || pdfUrls.size() == 0) {
                paperInfo.setHasPDF(false);
            } else {
                paperInfo.setHasPDF(true);
                paperInfo.setPdfUrl(pdfUrls.get(0));
                try {
                    paperInfo.setPdfFile(DownloadFileUtil.getFileNameFromUrl(pdfUrls.get(0)));
                    DownloadFileUtil.download(pdfUrls.get(0), filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                    downloadSuccess =false;
                    log.debug("下载PDF{}出错" + paperInfo.getPdfUrl());

                }
                if (pdfUrls.size() > 1) {
                    paperInfo.setSuppPDFUrl(pdfUrls.get(1));
                    try {
                        paperInfo.setSuppPDFFile(DownloadFileUtil.getFileNameFromUrl(pdfUrls.get(1)));
                        DownloadFileUtil.download(pdfUrls.get(1), filePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                        downloadSuccess = false;
                        log.debug("下载supp_pdf{}出错" + paperInfo.getSuppPDFUrl());
                    }
                }
            }
            paperInfo.setClick(0);
            paperInfo.setScore(1.0);
            paperInfo.setYear(2016);
            paperInfo.setAddTime(System.currentTimeMillis());
            paperInfo.checkComplete();
            if (!downloadSuccess){
                paperInfo.setComplete(0);
            }
            paperInfoDao.addPaperInfo(paperInfo);

            paperAuthorService.addPaperAuthor(paperInfo);
         /*   Random random = new Random();
            int id = random.nextInt(200);
            paperInfo.setPaperId(id + 1);


            redisTemplate.opsForList().rightPush("newPaperToPush", paperInfo.getPaperId());
            redisTemplate.expire("newPaperToPush", 10L, TimeUnit.MINUTES);*/
            // log.info("\npaper："+paperInfo.toString());
            // elPaperService.savePaperInfo(paperInfo);
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

