package cn.zjut.wangjie.pushpaper.processor;

import cn.zjut.wangjie.pushpaper.mapper.PaperInfoDao;
import cn.zjut.wangjie.pushpaper.service.elasticsearch.ELPaperService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @program: pushpaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-29 09:34
 **/
@Slf4j
@Component
public class KDDPageProcessor implements PageProcessor {
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
        List<String> paperUrls = page.getHtml().xpath("//table[@class='table table-hover table-striped table-bordered']/tbody/tr").links().all();
        if (paperUrls != null) {
            for (String strUrl : paperUrls) {
                page.addTargetRequest(strUrl);
            }
        }
        if (page.getUrl().regex("/papers/view/").match()){
            String article = page.getHtml().xpath("//div[@class='entry-content notopmargin']/h3/text()").toString();

            String authors = page.getHtml().xpath("//div[@class='entry-content notopmargin']/p/strong/text()").toString();

            String[] author = authors.split(";");
            for(int i =0 ; i< author.length;i++){
                System.out.println(author[i]);
            }
            String paperAbstract = page.getHtml().xpath("//div[@class='entry-content notopmargin']/p/text()").all().get(1);

            String pdfUrl = page.getHtml().xpath("//div[@class='entry-content notopmargin']").links().all().toString();

        }
    }
    @Override
    public Site getSite() {
        return site;
    }

}
