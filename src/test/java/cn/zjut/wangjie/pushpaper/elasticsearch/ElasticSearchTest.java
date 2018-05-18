package cn.zjut.wangjie.pushpaper.elasticsearch;

import cn.zjut.wangjie.pushpaper.mapper.ELPaperInfoRepository;
import cn.zjut.wangjie.pushpaper.mapper.PaperInfoDao;
import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.service.PaperService;
import cn.zjut.wangjie.pushpaper.service.elasticsearch.ELPaperService;
import lombok.extern.java.Log;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @program: PushPaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-05 19:36
 **/
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@Log
public class ElasticSearchTest {
    @Autowired
    private ELPaperService elPaperService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private ELPaperInfoRepository elPaperInfoRepository;
    @Autowired
    private PaperInfoDao paperInfoDao;

    @Test
    public void testElasticsearch(){
       // PaperInfo paperInfo = paperService.getPaperInfoById(12);
       // elPaperInfoRepository.save(paperInfo);
      //  elPaperService.savePaperInfo(paperInfo);
      // elPaperInfoRepository.save(paperInfo);
      //  List<PaperInfo> paperInfoList = elPaperService.searchPaperInfo("ICML");

       // System.out.println("paperInfo:"+paperInfoList.toString());
       // log.info("\npaperInfoList.size:"+paperInfoList.size());
        //log.info("\npaperInfo:"+paperInfoList.toString());
        List<PaperInfo> paperInfoList = paperInfoDao.getAllPaper();
        for (PaperInfo p :paperInfoList
                ) {
            elPaperInfoRepository.save(p);
        }

    }
    @Test
    public void getAllPaperInfo(){

        Iterable<PaperInfo> iterable =elPaperInfoRepository.findAll();

        Iterator iterator = iterable.iterator();
        int count = 0;
        while(iterator.hasNext()){
            count ++ ;
            iterator.next();
            log.info("\ncount:"+count);
        }
        log.info("\nsize:"+count);
    }

/*    @Test
    public void getOnePaperInfoTest(){
        Optional<PaperInfo> optionalPaperInfo = elPaperInfoRepository.findById(14);
        PaperInfo paperInfo = optionalPaperInfo.get();
        log.info("\npaperInfo"+paperInfo);
    }*/


    @Test
    public void getPage(){
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageSize(5);
        pageDTO.setCurrentPage(4);
        //416,422
        pageDTO = elPaperService.searchPaperInfoListByPage(pageDTO," Alexander Kolesnikov, Christoph H. Lampert ; ");
        log.info("\nsize"+pageDTO.getContentList().size());
        for (Object p:pageDTO.getContentList()
             ) {
            log.info("\n"+p.toString());
        }


    }
    @Test
    public void getPaperInfoListBySearch(){
        List<PaperInfo> paperInfoList = elPaperService.searchPaperInfo("Manik Varma");
        for (PaperInfo p:paperInfoList
             ) {
            log.info("\n paperInfo :"+p.toString());
        }
    }

    @Test
    public void testESPepository(){
     /*   List<PaperInfo> paperInfoList = elPaperInfoRepository.querytttByArticle("PixelCNN Models with Auxiliary Variables for Natural Image Modeling");
        log.info("\nsize:"+paperInfoList.size());
        for (Object p:paperInfoList
                ) {
            log.info("\n"+p.toString());

        }*/

        Iterable<PaperInfo> paperInfoIterable=elPaperInfoRepository.search(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("article", "PixelCNN Models")));
        Iterator iterator = paperInfoIterable.iterator();
        int count = 0;
        while(iterator.hasNext()){
            count ++ ;

            log.info("\npaper:"+ iterator.next().toString());
            log.info("\ncount:"+count);
        }
        log.info("\nsize:"+count);
    }
    @Test
    public void testRecommend() {
        List<PaperInfo> paperInfoList = elPaperService.recommend();
        log.info(""+paperInfoList.size());
        for (PaperInfo p :paperInfoList
                ) {
            log.info(""+p.getPaperId());
        }


    }

}
