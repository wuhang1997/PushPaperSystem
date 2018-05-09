package cn.zjut.wangjie.pushpaper.service.elasticsearch.impl;

import cn.zjut.wangjie.pushpaper.mapper.ELPaperInfoRepository;
import cn.zjut.wangjie.pushpaper.pojo.PageDTO;
import cn.zjut.wangjie.pushpaper.pojo.PaperInfo;
import cn.zjut.wangjie.pushpaper.service.elasticsearch.ELPaperService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;

import io.searchbox.core.Index;
import io.searchbox.core.Search;
import lombok.extern.java.Log;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @program: PushPaper
 * @description:
 * @author: WangJie
 * @create: 2018-05-05 18:59
 **/
@Service
@Log
@Transactional
public class ELPaperServiceImpl implements ELPaperService {
    @Autowired
    private JestClient jestClient;

    @Autowired
    private ELPaperInfoRepository elPaperInfoRepository;
    private static final String INDEX_NAME = "index_paper_push";

    private static final String TYPE = "paperInfo";

    @Override
    public void savePaperInfo(PaperInfo paperInfo) {
        Index index = new Index.Builder(paperInfo).id(paperInfo.getArticle()).index(INDEX_NAME).type(TYPE).build();
        try{
            jestClient.execute(index);
        }catch (IOException e){
            e.printStackTrace();
            log.info("向elasticsearch插入数据出错，插入对象为paperInfo："+paperInfo.toString());
        }
    }

    @Override
    public List<PaperInfo> searchPaperInfo(String searchStr) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery().queryName(searchStr));
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(INDEX_NAME).addType(TYPE).build();

        log.info(searchSourceBuilder.toString());
        try{
            JestResult jestResult = jestClient.execute(search);
            return jestResult.getSourceAsObjectList(PaperInfo.class);
        }catch (IOException e){
            e.printStackTrace();
            log.info("向elasticsearch查询数据出错，插入内容为："+searchStr);
        }
        return null;
    }
    public PageDTO searchPaperInfoListByPage(PageDTO pageDTO,String searchContent){


        /*
        // Function Score Query
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("article", searchContent)),
                        ScoreFunctionBuilders.weightFactorFunction(1000))
                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("authors", searchContent)),
                        ScoreFunctionBuilders.weightFactorFunction(800))
                .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("paperAbstract", searchContent)),
                        ScoreFunctionBuilders.weightFactorFunction(300));


        // 创建搜索 DSL 查询
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();

        log.info("\n searchCity(): searchContent [" + searchContent + "] \n DSL  = \n " + searchQuery.getQuery().toString());


        Page<PaperInfo> searchPageResults = elPaperInfoRepository.search(searchQuery);
       */



        //Page<PaperInfo> page =elPaperInfoRepository.findByArticleIsLikeOrAuthorsIsLikeOrPaperAbstractIsLike(searchContent,pageable);
        //Page<PaperInfo> page = elPaperInfoRepository.findByArticle(searchContent,pageable);


        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should((QueryBuilders.matchQuery("article", searchContent)))
                .should(QueryBuilders.matchQuery("authors", searchContent))
                .should(QueryBuilders.matchQuery("paperAbstract", searchContent));

        Iterable<PaperInfo> paperInfoIterable=elPaperInfoRepository.search(boolQueryBuilder);
        Iterator<PaperInfo> iterator = paperInfoIterable.iterator();
        List<PaperInfo> paperInfoList = new ArrayList<>(16);
        while(iterator.hasNext()){

            paperInfoList.add(iterator.next());

        }

        pageDTO.calculatBegin();


        int totalPages = paperInfoList.size()%pageDTO.getPageSize()>0?paperInfoList.size()/pageDTO.getPageSize()+1:paperInfoList.size()/pageDTO.getPageSize();
        pageDTO.setTotalPage(totalPages);
        pageDTO.calculatBegin();
        int end  =pageDTO.getBegin()+pageDTO.getPageSize();
        if (end>paperInfoList.size()-1){
            end = paperInfoList.size()-1;
        }
        pageDTO.setContentList(paperInfoList.subList(pageDTO.getBegin(),end));

        return pageDTO;
    }

}
