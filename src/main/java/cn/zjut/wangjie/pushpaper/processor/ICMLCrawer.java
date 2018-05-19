package cn.zjut.wangjie.pushpaper.processor;


import cn.zjut.wangjie.pushpaper.constant.PaperType;
import cn.zjut.wangjie.pushpaper.mapper.PaperInfoDao;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
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

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ICMLCrawer implements PageProcessor{


    @Autowired
    private ELPaperService elPaperService;
    @Autowired
    private PaperInfoDao paperInfoDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${paperPath}")
    private String filePath;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub

        List<String> urls = page.getHtml().xpath("//a[@class='btn btn-default btn-xs href_PDF']").links().all();

        if (urls != null) {
            for (String strUrl : urls) {
                page.addTargetRequest(strUrl);
            }
        }


        if (page.getUrl().regex("proceedings").match()) {


            PaperInfo paperInfo = new PaperInfo();
            paperInfo.setWebsite(PaperType.ICML);
            paperInfo.setArticle(page.getHtml().xpath("//article[@class='post-content']/h1/text()").toString());
            paperInfo.setPaperUrl(page.getUrl().toString());
            paperInfo.setAuthors(page.getHtml().xpath("//div[@class='authors']/text()").toString());
            paperInfo.setPaperAbstract(page.getHtml().xpath("//div[@class='abstract']/text()").toString());
            List<String> pdfUrls=page.getHtml().xpath("//div[@id='extras']").links().all();
            if(pdfUrls==null||pdfUrls.size()==0) {
                paperInfo.setHasPDF(false);
            }else {
                paperInfo.setHasPDF(true);
                paperInfo.setPdfUrl(pdfUrls.get(0));
                try {
                    paperInfo.setPdfFile(DownloadFileUtil.getFileNameFromUrl(pdfUrls.get(0)));
                   // DownloadFileUtil.download(pdfUrls.get(0), filePath);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    log.debug("下载PDF{}出错"+paperInfo.getPdfUrl());

                }
                if(pdfUrls.size()>1) {
                    paperInfo.setSuppPDFUrl(pdfUrls.get(1));
                    try {
                        paperInfo.setSuppPDFFile(DownloadFileUtil.getFileNameFromUrl(pdfUrls.get(1)));
                       // DownloadFileUtil.download(pdfUrls.get(1), filePath);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        log.debug("下载supp_pdf{}出错"+paperInfo.getSuppPDFUrl());
                    }
                }
            }
           // paperInfoDao.addPaperInfo(paperInfo);

            redisTemplate.opsForList().rightPush("newPaperToPush",paperInfo.getPaperId());
            redisTemplate.expire("newPaperToPush",12L,TimeUnit.HOURS);
           // log.info("\npaper："+paperInfo.toString());
           // elPaperService.savePaperInfo(paperInfo);



        }

	}
	

	@Override
	public Site getSite() {
		return site;
	}


}
